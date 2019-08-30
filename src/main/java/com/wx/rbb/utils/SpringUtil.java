/*
 * 文 件 名:  SpringUtil.java
 * 版    权:
 * 描    述:   <描述>
 * 版    本：       <版本号>
 * 创 建 人:  <创建人>
 * 创建时间: 2017年8月17日
 *
 */
package com.wx.rbb.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <获取实体类工具>
 *
 * @author fengshaozhuang
 *
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext sAppContext;

    /**
     * 设置上下文
     * 
     * @param appContext
     */
    private static void setAct(final ApplicationContext appContext) {
        SpringUtil.sAppContext = appContext;
    }

    /**
     * Object
     */
    @Override
    public void setApplicationContext(final ApplicationContext appContext) throws BeansException {
        SpringUtil.setAct(appContext);
    }

    /**
     * Object
     *
     * @return Object
     */
    public static ApplicationContext getApplicationContext() {
        return SpringUtil.sAppContext;
    }

    /**
     * Object
     *
     * @param name
     *            Object
     * @return Object
     */
    public static Object getBean(final String name) {
        return SpringUtil.getApplicationContext().getBean(name);
    }

    /**
     * T
     *
     * @param clazz
     *            clazz
     * @param <T>
     *            <T>
     * @return T
     */
    public static <T> T getBean(final Class<T> clazz) {
        return SpringUtil.getApplicationContext().getBean(clazz);
    }

    /**
     * T
     *
     * @param name
     *            name
     * @param clazz
     *            clazz
     * @param <T>
     *            <T>
     * @return T
     */
    public static <T> T getBean(final String name, final Class<T> clazz) {
        return SpringUtil.getApplicationContext().getBean(name, clazz);
    }
}
