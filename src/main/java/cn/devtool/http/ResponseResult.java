package cn.devtool.http;

/**
 * Http标准响应对象
 *
 * @author Yujiumin
 * @version 2021/08/10
 */
public class ResponseResult<T> {

    /**
     * 默认的成响应码
     */
    private static final long DEFAULT_SUCCESS_CODE = 0L;

    /**
     * 默认的失败响应码
     */
    private static final long DEFAULT_ERROR_CODE = -1L;

    /**
     * 响应码
     */
    private Long code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 私有构造方法
     * 为防止调用方使用new关键字创建对象
     */
    private ResponseResult() {
    }

    /**
     * 使用默认成功响应码响应无数据对象
     *
     * @return
     */
    public static ResponseResult<?> success() {
        return success(DEFAULT_SUCCESS_CODE);
    }

    /**
     * 使用自定义响应码响应无数据对象
     *
     * @param code
     * @return
     */
    public static ResponseResult<?> success(long code) {
        return success(code, null);
    }

    /**
     * 使用默认成功响应码响应
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T data) {
        return success(DEFAULT_SUCCESS_CODE, data);
    }

    /**
     * 使用自定义响应码响应无数据对象
     *
     * @param code
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(long code, T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.code = code;
        responseResult.data = data;
        return responseResult;
    }

    /**
     * 使用默认失败响应码响应异常信息
     *
     * @param message
     * @return
     */
    public static ResponseResult<?> error(String message) {
        return error(DEFAULT_ERROR_CODE, message);
    }

    /**
     * 使用自定义响应码响应异常信息
     *
     * @param code
     * @param message
     * @return
     */
    public static ResponseResult<?> error(long code, String message) {
        ResponseResult<?> responseResult = new ResponseResult<>();
        responseResult.code = code;
        responseResult.message = message;
        return responseResult;
    }

    public Long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}