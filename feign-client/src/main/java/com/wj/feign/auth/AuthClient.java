package com.wj.feign.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.wj.common.constants.CommonConstants;
import com.wj.core.tools.restful.result.R;
import com.wj.feign.auth.fallback.AuthClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        value = CommonConstants.AUTH_APPLICATION_NAME,
        fallback = AuthClientFallback.class
)
public interface AuthClient {
    String API_PREFIX = "/auth";
    String GET_TOKEN = API_PREFIX + "/getToken";
    String CHECK_TOKEN = API_PREFIX + "/checkToken";
    String TOKEN_INFO = API_PREFIX + "/getTokenInfo";

    /**
     * 生成token
     *
     * @param map
     * @return
     */
    @PostMapping(GET_TOKEN)
    R<String> getToken(@RequestBody Map<String, Object> map);

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    @GetMapping(CHECK_TOKEN)
    R<Boolean> checkToken(@RequestParam("token") String token);

    /**
     * 获取token信息
     *
     * @param token
     * @return
     */
    @GetMapping(TOKEN_INFO)
    R<DecodedJWT> getTokenInfo(@RequestParam("token") String token);
}
