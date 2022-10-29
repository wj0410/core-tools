package com.wj.auth.service;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Map;

public interface AuthService {
    String getToken(Map<String, Object> map);

    boolean checkToken(String token);

    DecodedJWT getTokenInfo(String token);
}
