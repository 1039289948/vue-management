package com.yotado.core.config.annotation;

/**
 * @author wangle
 * @date 2018/11/30
 * @description 当前api需要登录的注解
 */

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SignRequired {

}
