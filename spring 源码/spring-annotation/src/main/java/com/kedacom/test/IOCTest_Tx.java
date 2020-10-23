package com.kedacom.test;

import com.kedacom.service.BookService;
import com.kedacom.service.UserService;
import com.kedacom.tx.TxConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author python
 */
public class IOCTest_Tx {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = applicationContext.getBean(UserService.class);
        userService.insertUser();
        applicationContext.close();
    }
}
