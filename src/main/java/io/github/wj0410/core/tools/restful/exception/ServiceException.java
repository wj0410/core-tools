package io.github.wj0410.core.tools.restful.exception;


import io.github.wj0410.core.tools.restful.result.IResultCode;
import io.github.wj0410.core.tools.restful.result.ResultCode;

/**
 * 业务异常
 *
 * @author wangjie
 * @version 1.0
 * date 2021年08月03日10时39分
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 2359767895161832954L;
    private final IResultCode resultCode;

    public ServiceException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILURE;
    }

    public ServiceException(IResultCode resultCode) {
        super();
        this.resultCode = resultCode;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = ResultCode.FAILURE;
    }

    public ServiceException(String message, IResultCode resultCode, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
    }

    public ServiceException(IResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
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