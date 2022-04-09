package cn.devtool.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Yujiumin
 * @version 2022/02/08
 */
public class DateUtils {

    private static final int MONTH_MIN_VALUE = 1;

    private static final int MONTH_MAX_VALUE = 12;

    private static final ZoneId SYSTEM_DEFAULT_ZONE = ZoneId.systemDefault();

    /**
     * 查询指定月份的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int lengthOfMonth(int year, int month) {
        if (month < MONTH_MIN_VALUE || month > MONTH_MAX_VALUE) {
            throw new UnsupportedOperationException("月份值不合法: " + month);
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(SYSTEM_DEFAULT_ZONE));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return lengthOfMonth(calendar.getTime());
    }

    /**
     * 查询指定年份总天数
     *
     * @param year
     * @return
     */
    public static int lengthOfYear(int year) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(SYSTEM_DEFAULT_ZONE));
        calendar.set(Calendar.YEAR, year);
        final LocalDate localDate = toLocalDate(calendar.getTime());
        return localDate.lengthOfYear();
    }

    /**
     * 查询当前时间所在月的天数
     *
     * @param date
     * @return
     */
    public static int lengthOfMonth(Date date) {
        final LocalDate localDate = toLocalDate(date);
        return localDate.lengthOfMonth();
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(SYSTEM_DEFAULT_ZONE).toLocalDate();
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(SYSTEM_DEFAULT_ZONE).toLocalDateTime();
    }

    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
        final ZonedDateTime zonedDateTime = localDate.atStartOfDay(SYSTEM_DEFAULT_ZONE);
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        final ZonedDateTime zonedDateTime = localDateTime.atZone(SYSTEM_DEFAULT_ZONE);
        return Date.from(zonedDateTime.toInstant());
    }

}
