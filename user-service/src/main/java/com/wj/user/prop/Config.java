package com.wj.user.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件热更新第二种方式
 * 使用 @ConfigurationProperties 注解读取配置文件，就不需要加 @RefreshScope 注解。
 */
@Data
@Component
@ConfigurationProperties(prefix = "config")
public class Config {
    public String test;
}
