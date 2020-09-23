package com.kedacom.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;


@Component
public class Red implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("applicationContext -> ioc = " + applicationContext);
    }


    @Override
    public void setBeanName(String name) {
        System.out.println("bean name = " + name);
    }


    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String value = resolver.resolveStringValue("我是${os.name} -> #{28-2}");
        System.out.println("value = " + value);
    }
}
