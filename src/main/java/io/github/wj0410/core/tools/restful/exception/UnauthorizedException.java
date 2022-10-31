package io.github.wj0410.core.tools.restful.exception;

import io.github.wj0410.core.tools.restful.result.IResultCode;
import io.github.wj0410.core.tools.restful.result.ResultCode;

/**
 * 未授权异常
 */
public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 2359767895161832954L;

    private static final IResultCode resultCode = ResultCode.UNAUTHORIZED;;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }

    public IResultCode getResultCode() {
        return this.resultCode;
    }
}
