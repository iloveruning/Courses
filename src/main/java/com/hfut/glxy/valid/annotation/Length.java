package com.hfut.glxy.valid.annotation;

import java.lang.annotation.*;

/**
 * @author chenliangliang
 * @date 2018/1/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface Length {

    String msg() default "com.cll.beanValid.annotation.Length";

    int min() default 0;

    int max() default Integer.MAX_VALUE;
}
