package com.wj.feign.auth.fallback;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.wj.core.tools.restful.result.R;
import com.wj.feign.auth.AuthClient;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthClientFallback implements AuthClient {
    @Override
    public R<String> getToken(Map<String, Object> map) {
        return R.fail("远程调用 getToken 失败！");
    }

    @Override
    public R<Boolean> checkToken(String token) {
        return R.fail("远程调用 checkToken 失败！");
    }

    @Override
    public R<DecodedJWT> getTokenInfo(String token) {
        return R.fail("远程调用 getTokenInfo 失败！");
    }
}
