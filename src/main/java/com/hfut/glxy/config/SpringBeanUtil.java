package com.hfut.glxy.config;
//package com.hfut.glxy.config;

/**
 * @author chenliangliang
 * @date 2017/12/25
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * 取得存储在静态变量中的ApplicationContext.
     *
     * @return
     */

    public static ApplicationContext getContext() {
        check();
        return context;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param name
     * @param <T>
     * @return
     */

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        check();
        return (T) context.getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param clazz
     * @param <T>
     * @return
     */

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        check();
        return (T) context.getBeansOfType(clazz);
    }

    private static void check() {
        if (context == null) {
            throw new IllegalStateException("applicaitonContext未注入");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null) {
            SpringBeanUtil.context = applicationContext;
        }
        System.out.println("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext=" + SpringBeanUtil.context + "========");
    }
}

