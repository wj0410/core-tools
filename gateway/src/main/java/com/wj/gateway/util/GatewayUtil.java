package com.wj.gateway.util;

/**
 * @author wangjie
 * @version 1.0
 * @date 2021年11月24日17时34分
 */
public class GatewayUtil {
    // token前缀
    public final static String BEARER = "bearer";
    public final static Integer AUTH_LENGTH = 7;

    public static String getToken(String auth) {
        if (auth != null && auth.length() > AUTH_LENGTH) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo(BEARER) == 0) {
                auth = auth.substring(7);
                return auth;
            }
        }
        return null;
    }

}