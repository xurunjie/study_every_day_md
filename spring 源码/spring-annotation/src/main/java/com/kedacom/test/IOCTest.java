package com.kedacom.test;

import com.kedacom.bean.Blue;
import com.kedacom.bean.Person;
import com.kedacom.config.MainConfig;
import com.kedacom.config.MainConfig2;
import javafx.application.Application;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

public class IOCTest {

    /**
     * @see Test 测试的注解junit 包里面的
     * 这里我们需要注意主配置类回去通过扫描获取 beans 信息, 扫描里面是可以过滤信息的
     * MainConfig
     */
    @Test
    public void test01(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        // @see ApplicationContext getBeanDefinitionNames 获取所有定义的 bean 的名字
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }


    /**
     * MainConfig2
     */
    @Test
    public void test02(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        // @see ApplicationContext getBeanDefinitionNames 获取所有定义的 bean 的名字
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        Person person1 = (Person) applicationContext.getBean("person");
        Person person2 = (Person) applicationContext.getBean("person");
        System.out.println(person1==person2);
    }


    @Test
    public void test03() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        // 获取系统环境环境变量
        Environment environment = applicationContext.getEnvironment();
        // 获取环境变量的值
        String property = environment.getProperty("os.name");
        System.out.println("property = " + property);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }

        Map<String, Person> beansOfType = applicationContext.getBeansOfType(Person.class);
        System.out.println("beansOfType = " + beansOfType);
    }
    @Test
    public void testImport(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
        Blue bean = applicationContext.getBean(Blue.class);
        Object colorFactoryBean = applicationContext.getBean("colorFactoryBean");
        Object colorFactoryBean1 = applicationContext.getBean("&colorFactoryBean");
        // 工厂Bean获取的是调用和 GetObjects 创建的对象
        System.out.println("colorFactoryBean = " + colorFactoryBean.getClass());
        System.out.println("colorFactoryBean1.getClass() = " + colorFactoryBean1.getClass());
        System.out.println(bean);
    }

}
