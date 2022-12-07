package io.github.wj0410.core.tools.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public abstract class AuthUtil {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(attrs, true);//设置子线程共享
        return attrs.getRequest();
    }

    protected abstract Long getLoginUserId();

    protected abstract <T> T getLoginUser();
}
