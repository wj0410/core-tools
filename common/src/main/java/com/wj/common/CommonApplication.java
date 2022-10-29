package com.wj.common;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

import java.util.Properties;

public class CommonApplication {

    public static ConfigurableApplicationContext run(String appName, Class source, String... args) {
        SpringApplicationBuilder builder = createSpringApplicationBuilder(appName, source, args);
        return builder.run(args);
    }

    /**
     * 这里只配置服务名
     */
    private static SpringApplicationBuilder createSpringApplicationBuilder(String appName, Class source, String[] args) {
        Assert.hasText(appName, "[appName]服务名不能为空");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(new Class[]{source});
        Properties props = System.getProperties();
        props.setProperty("spring.application.name", appName);
        return builder;
    }
}
