package com.wj.core.tools.config;

import com.wj.core.tools.interceptor.GlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class DefaultWebMvcConfig implements WebMvcConfigurer {
    // 全局过滤器 验证标识头
    @Autowired
    GlobalInterceptor globalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor) // 自定义拦截器
                .addPathPatterns("/**"); // 拦截的api路径
    }
}
