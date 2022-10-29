package com.wj.user;

import com.wj.common.CommonApplication;
import com.wj.common.constants.CommonConstants;
import com.wj.core.tools.restful.exception.ExceptionController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// 要调用的feign接口所在包路径
@EnableFeignClients({"com.wj.feign.auth","com.wj.feign.dictionary"})
public class UserApplication {

    public static void main(String[] args) {
        CommonApplication.run(CommonConstants.USER_APPLICATION_NAME, UserApplication.class, args);
    }

    // 全局异常处理
    @Bean
    public ExceptionController exceptionController() {
        return new ExceptionController();
    }

}
