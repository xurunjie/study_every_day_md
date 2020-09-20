package com.kedacom.test;

import com.kedacom.bean.Person;
import com.kedacom.config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class MainAnnotationTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = (Person) applicationContext.getBean("person");
        System.out.println("person = " + person);

        String[] names = applicationContext.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println("name = " + name);
        }
    }
}
