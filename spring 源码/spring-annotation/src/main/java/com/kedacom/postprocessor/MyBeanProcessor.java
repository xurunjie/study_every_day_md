package com.kedacom.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 后置处理器: 初始化前后进行处理工作
 */
public class MyBeanProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanProcessor.postProcessBeforeInitialization" + beanName + " bean = " + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanProcessor.postProcessAfterInitialization" + beanName + " bean = " + bean);
        return bean;
    }
}
