package cn.devtool;

/**
 * 雪花ID生成器
 *
 * @author Yujiumin
 * @version 2022/05/10
 */
public class SnowflakeIdGenerator {

    /**
     * 时间戳相对时间
     */
    private static final long PROJECT_EPOCH = 1640966400000L;

    /**
     * 机房标识占用位数
     */
    private static final long DATA_CENTER_BIT = 5L;

    /**
     * 机器标识占用位数
     */
    private static final long MACHINE_BIT = 5L;

    /**
     * 序列号占用位数
     */
    private static final long SEQUENCE_BIT = 12;

    /**
     * 最大机房ID
     */
    private static final long MAX_DATA_CENTER = ~(-1L << DATA_CENTER_BIT);

    /**
     * 最大机器ID
     */
    private static final long MAX_MACHINE = ~(-1L << MACHINE_BIT);

    /**
     * 同一毫秒内最大序列号值
     */
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 机器ID偏移量
     */
    private static final long OFFSET_MACHINE_BIT = SEQUENCE_BIT;

    /**
     * 机房ID偏移量
     */
    private static final long OFFSET_DATA_CENTER_BIT = OFFSET_MACHINE_BIT + MACHINE_BIT;

    /**
     * 时间戳偏移量
     */
    private static final long OFFSET_TIMESTAMP = OFFSET_DATA_CENTER_BIT + DATA_CENTER_BIT;

    /**
     * 上一次时间戳
     */
    private long lastTimestamp = -1L;

    /**
     * 机房ID
     */
    private long dataCenterId;

    /**
     * 机器ID
     */
    private long machineId;

    /**
     * 序列号
     */
    private long sequence;

    public SnowflakeIdGenerator(long dataCenterId, long machineId) {
        this(dataCenterId, machineId, 0L);
    }

    public SnowflakeIdGenerator(long dataCenterId, long machineId, long sequence) {
        if (dataCenterId < 0 || dataCenterId > MAX_DATA_CENTER) {
            throw new IllegalArgumentException("数据中心ID不合法");
        }

        if (machineId < 0 || machineId > MAX_MACHINE) {
            throw new IllegalArgumentException("机器ID不合法");
        }

        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
        this.sequence = sequence;
    }

    public synchronized long nextId() throws IllegalAccessException, InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis < lastTimestamp) {
            // 服务器时钟回拨
            throw new IllegalAccessException("服务器时钟回拨");
        }

        if (currentTimeMillis == lastTimestamp) {
            // 时钟冲突
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // 序列号超过最大值, 休眠1毫秒
                Thread.sleep(1);
                currentTimeMillis = System.currentTimeMillis();
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimeMillis;
        return generateId(currentTimeMillis);
    }

    private long generateId(long timestamp) {
        // 时间差值
        long timestampDiff = timestamp - PROJECT_EPOCH;

        // 时间戳 bit位
        final long timestampBit = timestampDiff << OFFSET_TIMESTAMP;

        // 机房ID bit位
        final long dataCenterBit = dataCenterId << OFFSET_DATA_CENTER_BIT;

        // 机器ID bit位
        final long machineBit = machineId << OFFSET_MACHINE_BIT;
        return timestampBit | dataCenterBit | machineBit | sequence;
    }
}
