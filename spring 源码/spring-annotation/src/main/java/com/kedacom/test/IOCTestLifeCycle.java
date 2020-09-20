package com.kedacom.test;

import com.kedacom.bean.Car;
import com.kedacom.config.MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class IOCTestLifeCycle {
    @Test
    public void test01() {
        // 创建 IOC 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        // 容器创建完
        System.out.println("容器创建完成...");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
        Car car = (Car) applicationContext.getBean("car");
        // 关闭容器
        applicationContext.close();
    }
}
