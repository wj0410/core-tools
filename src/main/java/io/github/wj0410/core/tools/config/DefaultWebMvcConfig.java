package io.github.wj0410.core.tools.config;

import io.github.wj0410.core.tools.interceptor.AuthSecretKeyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class DefaultWebMvcConfig implements WebMvcConfigurer {
    // 全局过滤器 验证标识头
    @Autowired
    AuthSecretKeyInterceptor authSecretKeyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authSecretKeyInterceptor) // 自定义拦截器
                .addPathPatterns("/**"); // 拦截的api路径
    }
}
