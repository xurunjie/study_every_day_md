package com.kedacom.test;

import com.kedacom.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {
    public static void main(String[] args) {
        ApplicationContext application = new ClassPathXmlApplicationContext("classpath:beans.xml");
        Person person = (Person) application.getBean("person");
        System.out.println(person);
    }
}
