package io.github.wj0410.core.tools.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author wangjie
 * @version 1.0
 * date 2021年11月24日17时34分
 */
public class JwtUtil {
    // 签名随意写，但复杂性越高，安全性越高
    public static final String SING = "@32sada$R";
    // 过期时间设置 1天
    public final static int EXPIRE_UNIT = Calendar.DATE;
    public final static int EXPIRE_NUMBER = 1;

    /**
     * 生成token
     * @param map 属性
     * @return token
     */
    public static String createToken(Map<String, Object> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(EXPIRE_UNIT, EXPIRE_NUMBER);
        // 创建JWT builder
        JWTCreator.Builder builder = JWT.create();
        // payload
        map.forEach((k, v) -> {
            builder.withClaim(k, String.valueOf(v));
        });
        // 指定令牌的过期时间
        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SING));//签名
        return token;
    }

    /**
     * 验证token合法性
     * @param token token
     * @return boolean
     */
    public static boolean verifier(String token) {
        boolean b = true;
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        } catch (Exception e) {
            b = false;
        }
        return b;
    }

    /**
     * 获取token信息
     * @param token token
     * @return DecodedJWT
     */
    public static DecodedJWT getTokenInfo(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify;
    }
}