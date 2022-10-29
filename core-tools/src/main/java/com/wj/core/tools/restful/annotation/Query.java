package com.wj.core.tools.restful.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* *
 * @Author Wangjie
 * @Description 查询注解，需配合Mybatis-plus使用
 * 默认查询方式为 eq
 * @Date 15:21 2021/3/31
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {
    Keyword value() default Keyword.eq;

    String column() default "";
}