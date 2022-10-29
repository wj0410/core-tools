package com.wj.auth.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.wj.auth.service.AuthService;
import com.wj.core.tools.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String getToken(Map<String, Object> map) {
        return JwtUtil.createToken(map);
    }

    @Override
    public boolean checkToken(String token) {
        return JwtUtil.verifier(token);
    }

    @Override
    public DecodedJWT getTokenInfo(String token) {
        return JwtUtil.getTokenInfo(token);
    }
}
