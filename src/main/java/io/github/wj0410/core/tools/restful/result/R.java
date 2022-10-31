package io.github.wj0410.core.tools.restful.result;

import io.github.wj0410.core.tools.restful.util.ObjectUtil;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author wangjie
 * @version 1.0
 * date 2021年08月03日09时29分
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private boolean success;
    private T data;
    private String msg;

    private R(IResultCode iResultCode) {
        this(iResultCode.getCode(), null, iResultCode.getMessage());
    }

    private R(IResultCode iResultCode, String msg) {
        this(iResultCode.getCode(), null, msg);
    }

    private R(IResultCode iResultCode, T data) {
        this(iResultCode.getCode(), data, iResultCode.getMessage());
    }

    private R(IResultCode iResultCode, T data, String msg) {
        this(iResultCode.getCode(), data, msg);
    }

    private R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.code == code;
    }

    public static boolean isSuccess(@Nullable R<?> result) {
        return (Boolean) Optional.ofNullable(result).map((x) -> {
            return ObjectUtil.nullSafeEquals(ResultCode.SUCCESS.code, x.code);
        }).orElse(Boolean.FALSE);
    }

    public static boolean isNotSuccess(@org.springframework.lang.Nullable R<?> result) {
        return !isSuccess(result);
    }

    public static <T> R<T> data(T data) {
        return data(data, "操作成功");
    }

    public static <T> R<T> data(T data, String msg) {
        return data(ResultCode.SUCCESS.code, data, msg);
    }

    public static <T> R<T> data(int code, T data, String msg) {
        return new R(code, data, msg);
    }

    public static <T> R<T> success(String msg) {
        return new R(ResultCode.SUCCESS, msg);
    }

    private static <T> R<T> success(IResultCode iResultCode) {
        return new R(iResultCode);
    }

    private static <T> R<T> success(IResultCode iResultCode, String msg) {
        return new R(iResultCode, msg);
    }

    public static <T> R<T> fail(String msg) {
        return new R(ResultCode.FAILURE, msg);
    }

    public static <T> R<T> fail(IResultCode iResultCode) {
        return new R(iResultCode);
    }

    public static <T> R<T> fail(IResultCode iResultCode, String msg) {
        return new R(iResultCode, msg);
    }

    public static <T> R<T> status(boolean flag) {
        return flag ? success("操作成功") : fail("操作失败");
    }

    public static <T> R<T> status(boolean flag, String msg) {
        return flag ? success(msg) : fail("操作失败");
    }

    public R() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}