package com.kedacom.test;

import com.kedacom.aop.MathCalculator;
import com.kedacom.config.MainConfigOfAop;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AOPTest {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAop.class);
        MathCalculator calculator = (MathCalculator) applicationContext.getBean("calculator");
        calculator.div(1, 0);
        applicationContext.close();
    }
}
