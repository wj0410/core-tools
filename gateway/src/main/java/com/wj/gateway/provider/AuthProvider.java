/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package com.wj.gateway.provider;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置
 */
@Component
public class AuthProvider {

    public static String TARGET = "/**";
    public static String REPLACEMENT = "";
    public static String AUTH_KEY = "token";
    private static List<String> defaultSkipUrl = new ArrayList<>();

    static {
        defaultSkipUrl.add("/ignore/**");
    }

    /**
     * 默认无需鉴权的API
     */
    public static List<String> getDefaultSkipUrl() {
        return defaultSkipUrl;
    }

}
