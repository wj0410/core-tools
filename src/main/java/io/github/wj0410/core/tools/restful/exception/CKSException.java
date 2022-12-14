package io.github.wj0410.core.tools.restful.exception;

import io.github.wj0410.core.tools.restful.result.IResultCode;
import io.github.wj0410.core.tools.restful.result.ResultCode;

/**
 * 参数校验异常
 */
public class CKSException extends RuntimeException {
    private static final long serialVersionUID = 2359767895161832954L;

    private static final IResultCode resultCode = ResultCode.PARAM_VALID_ERROR;

    public CKSException(String message) {
        super(message);
    }

    public CKSException() {
        super();
    }

    public CKSException(String message, Throwable cause) {
        super(message, cause);
    }

    public CKSException(Throwable cause) {
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
