package com.kedacom.test;


import com.kedacom.config.MainConfigOfAutowired;
import com.kedacom.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author python
 */
public class IOCAutowired {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);

    @Test
    public void test01() {
        printBeans(applicationContext);
        BookService bookService = (BookService) applicationContext.getBean("bookService");
        System.out.println("bookService = " + bookService);
    }

    public void printBeans(ApplicationContext applicationContext){
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
    }
}
