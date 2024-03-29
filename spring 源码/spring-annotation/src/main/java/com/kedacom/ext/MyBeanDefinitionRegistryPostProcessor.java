package com.kedacom.ext;

import com.kedacom.bean.Yellow;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry " + registry.getBeanDefinitionCount());
        //
        // RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Yellow.class);
        AbstractBeanDefinition rootBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Yellow.class).getBeanDefinition();
        registry.registerBeanDefinition("hello",rootBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor.postProcessBeanFactory " + beanFactory.getBeanDefinitionCount());
    }
}
