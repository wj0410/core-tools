package io.github.wj0410.core.tools.restful.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* *
 * @Author Wangjie
 * @Description 参数非空校验注解
 * @Date 15:21 2021/3/31
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {
    Operation[] operation() default {};
    String[] custom() default {};
}