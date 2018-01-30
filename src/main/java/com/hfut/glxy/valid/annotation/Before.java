package com.hfut.glxy.valid.annotation;

import java.lang.annotation.*;

/**
 * @author chenliangliang
 * @date 2018/1/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface Before {

    String msg() default "com.cll.beanvalid.annotation.Before";
}
