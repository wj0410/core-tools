package io.github.wj0410.core.tools.interceptor.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "wj.auth")
public class AuthIgnoreProperties {
    private final List<String> skipGatewayUrls = new ArrayList<>();
}
