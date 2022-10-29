package com.wj.core.tools.restful.exception;

import com.wj.core.tools.restful.result.R;
import com.wj.core.tools.restful.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author wangjie
 * @version 1.0
 * @date 2021年11月29日15时31分
 */
@Slf4j
@ControllerAdvice
public class ExceptionController {

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ServiceException.class})
    @ResponseBody
    public R serviceException(ServiceException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return R.fail(e.getResultCode(), String.format("%s", e.getMessage()));
    }

    /**
     * 参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {CKSException.class})
    @ResponseBody
    public R cksException(CKSException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return R.fail(e.getResultCode(), String.format("【%s】%s", e.getResultCode().getMessage(), StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : ""));
    }

    /**
     * 权限不足
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseBody
    public R unauthorizedException(UnauthorizedException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return R.fail(e.getResultCode(), String.format("【%s】%s", e.getResultCode().getMessage(), StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : ""));
    }

    @ExceptionHandler(value = {BindException.class})
    @ResponseBody
    public R bindException(BindException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return R.fail("请求参数格式不符合预期，请检查！");
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public R exception(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            if (i == 0) {
                log.error(e.toString() + "：" + stackTrace[i]);
            } else {
                log.error("" + stackTrace[i]);
            }
        }
        e.printStackTrace();
        return R.fail(ResultCode.INTERNAL_SERVER_ERROR, "【" + ResultCode.INTERNAL_SERVER_ERROR.getMessage() + "】" + e.toString() + "：" + stackTrace[0]);
    }
}