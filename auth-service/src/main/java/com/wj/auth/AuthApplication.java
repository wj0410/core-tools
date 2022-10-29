package com.wj.auth;


import com.wj.common.CommonApplication;
import com.wj.common.constants.CommonConstants;
import com.wj.core.tools.restful.exception.ExceptionController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        CommonApplication.run(CommonConstants.AUTH_APPLICATION_NAME,AuthApplication.class,args);
    }

    // 全局异常处理
    @Bean
    public ExceptionController exceptionController(){
        return new ExceptionController();
    }

}
