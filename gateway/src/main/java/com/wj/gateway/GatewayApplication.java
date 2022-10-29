package com.wj.gateway;


import com.wj.common.CommonApplication;
import com.wj.common.constants.CommonConstants;
import com.wj.core.tools.redis.RedisUUID;
import com.wj.gateway.props.AuthProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;


@EnableConfigurationProperties({AuthProperties.class})
@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients("com.wj.feign.auth")
public class GatewayApplication {

    public static void main(String[] args) {
        CommonApplication.run(CommonConstants.GATEWAY_APPLICATION_NAME, GatewayApplication.class, args);
    }

    // 解决feign调用报错
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
    @Bean
    public RedisUUID redisUUID(){
        return new RedisUUID();
    }
}
