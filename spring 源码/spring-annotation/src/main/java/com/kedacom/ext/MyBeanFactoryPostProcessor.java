package com.kedacom.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor.postProcessBeanFactory " + beanFactory);
        System.out.println("beanFactory.getBeanDefinitionCount() = " + beanFactory.getBeanDefinitionCount());
        System.out.println("beanFactory.getBeanDefinitionNames() = " + Arrays.toString(beanFactory.getBeanDefinitionNames()));
    }
}
