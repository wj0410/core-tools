package io.github.wj0410.core.tools.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 全局配置序列化返回
 * Long 转换成 String 防止Long精度丢失
 * 日期格式化
 * 时区等等
 */
public class DefaultJacksonConfig {
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper build = builder.createXmlMapper(false).build();

        SimpleModule simpleModule = new SimpleModule();
        // Long ==> String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        build.registerModule(simpleModule);
        build.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        build.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return build;
    }
}
