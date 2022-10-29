package com.wj.demo;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wj.common.CommonApplication;
import com.wj.common.constants.CommonConstants;
import com.wj.core.tools.interceptor.GlobalInterceptor;
import com.wj.core.tools.interceptor.prop.AuthIgnoreProperties;
import com.wj.core.tools.mybatisplus.DefaultMetaObjectHandler;
import com.wj.core.tools.redis.RedisLockHelper;
import com.wj.core.tools.redis.RedisUUID;
import com.wj.core.tools.restful.exception.ExceptionController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        CommonApplication.run("demo-service", DemoApplication.class, args);
    }

    // 全局异常处理
    @Bean
    public ExceptionController exceptionController() {
        return new ExceptionController();
    }

    // redis工具
    @Bean
    public RedisLockHelper redisLockHelper() {
        return new RedisLockHelper();
    }

    // mybatis-plus 字段填充控制器，实现公共字段自动写入
    @Bean
    public MetaObjectHandler defaultMetaObjectHandler() {
        return new DefaultMetaObjectHandler();
    }

    // ======== 全局过滤器 让服务只能从网关调用 start ========
    @Bean
    public GlobalInterceptor globalInterceptor() {
        return new GlobalInterceptor();
    }
    // === 生成 auth-secretKey
    @Bean
    public RedisUUID redisUUID() {
        return new RedisUUID();
    }
    // === 过滤url配置
    @Bean
    public AuthIgnoreProperties authIgnoreProperties() {
        return new AuthIgnoreProperties();
    }
    // ======== 全局过滤器 让服务只能从网关调用 end ========
}
