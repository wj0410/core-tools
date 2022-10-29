package com.wj.core.tools.restful.result;

/**
 * @author wangjie
 * @version 1.0
 * @date 2021年08月03日09时34分
 */
public enum ResultCode implements IResultCode {
    SUCCESS(200, "操作成功"),
    FAILURE(400, "业务异常"),
    INTERNAL_SERVER_ERROR(500, "服务器异常"),
    PARAM_VALID_ERROR(400, "参数校验失败"),
    PARAM_MISS(400, "缺少必要的请求参数"),
    UNAUTHORIZED(401, "权限不足");

    final int code;
    final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getCode() {
        return this.code;
    }
}