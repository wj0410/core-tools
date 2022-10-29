package com.wj.user.controller;

import com.wj.core.tools.restful.result.R;
import com.wj.core.tools.util.AuthUtil;
import com.wj.feign.auth.AuthClient;
import com.wj.feign.dictionary.DictionaryClient;
import com.wj.user.prop.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
//@RefreshScope // nacos配置热更新
public class UserController {
    @Autowired
    private Registration registration;
    @Autowired
    private AuthClient authClient;
    @Autowired
    private DictionaryClient dictionaryClient;
    // 该属性值是从nacos配置中心拉取到的配置
//    @Value("${config.test}")
//    private String test;
    @Autowired
    private Config config;

    @GetMapping("/hello")
    public String hello() {
        return "hello,host:" + registration.getHost() + ",port:" + registration.getPort();
    }

    @GetMapping("/testConfig")
    public String testConfig() {
        String str = "读取到NACOS配置中心配置的属性";
        str += " [test:" + config.test + "]";
        return str;
    }

    @GetMapping("/login")
    public R login() {
        Map<String, Object> user = new HashMap<>();
        user.put("id", "1");
        user.put("username", "wangjie");
        user.put("password", "123456");
        return R.data("bearer " + authClient.getToken(user).getData());
    }

    @GetMapping("/getUserInfo")
    public R getUserInfo() {
        String token = AuthUtil.getRequest().getHeader("token");
        return R.data(authClient.getTokenInfo(token));
    }
}
