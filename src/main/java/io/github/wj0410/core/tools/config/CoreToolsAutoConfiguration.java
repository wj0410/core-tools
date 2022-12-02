package io.github.wj0410.core.tools.config;

import io.github.wj0410.core.tools.interceptor.prop.AuthIgnoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类
 */
@Configuration
@EnableConfigurationProperties(AuthIgnoreProperties.class)
@ComponentScan({"io.github.wj0410.core.tools"})
public class CoreToolsAutoConfiguration {

}
