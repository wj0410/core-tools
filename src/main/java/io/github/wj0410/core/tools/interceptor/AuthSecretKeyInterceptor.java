package io.github.wj0410.core.tools.interceptor;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.wj0410.core.tools.interceptor.contansts.SecurityConstants;
import io.github.wj0410.core.tools.interceptor.prop.AuthIgnoreProperties;
import io.github.wj0410.core.tools.redis.RedisUUID;
import io.github.wj0410.core.tools.restful.result.R;
import io.github.wj0410.core.tools.restful.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 全局拦截器
 * 所有服务统一加上过滤器，验证标识头（secretKey）
 * 让服务只能从网关调用
 */
@Component
@ConditionalOnClass(HandlerInterceptor.class)
public class AuthSecretKeyInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUUID redisUUID;
    @Autowired
    private AuthIgnoreProperties authIgnoreProperties;

    public static String TARGET = "/**";
    public static String REPLACEMENT = "";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (authIgnoreProperties.getSkipGatewayUrls().stream().map(url -> url.replace(TARGET, REPLACEMENT)).anyMatch(request.getRequestURI()::startsWith)) {
            return true;
        }
        String secretKey = request.getHeader(SecurityConstants.SECRET_KEY);
        if (StrUtil.isNotBlank(secretKey)) {
            String key = (String) redisUUID.get(SecurityConstants.SECRET_KEY);
            if (!StrUtil.isBlank(key) && secretKey.equals(key)) {
                return true;
            }
        }
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        writer.write(objectMapper.writeValueAsString(R.fail(ResultCode.UNAUTHORIZED, "illegal request")));
        return false;
    }
}

