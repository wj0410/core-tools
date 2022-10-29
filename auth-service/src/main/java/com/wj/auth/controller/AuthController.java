package com.wj.auth.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.wj.auth.service.AuthService;
import com.wj.core.tools.restful.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    /**
     * 生成token
     *
     * @param map
     * @return
     */
    @PostMapping("/getToken")
    public R<String> getToken(Map<String, Object> map) {
        return R.data(authService.getToken(map));
    }

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    @GetMapping("/checkToken")
    public R<Boolean> checkToken(String token) {
        return R.status(authService.checkToken(token));
    }

    /**
     * 获取token信息
     *
     * @param token
     * @return
     */
    @GetMapping("/getTokenInfo")
    public R<DecodedJWT> getTokenInfo(String token) {
        return R.data(authService.getTokenInfo(token));
    }
}
