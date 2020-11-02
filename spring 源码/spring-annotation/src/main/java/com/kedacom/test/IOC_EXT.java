package com.kedacom.test;


import com.kedacom.ext.ExtConfig;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOC_EXT {
    @Test
    public void test1(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
        annotationConfigApplicationContext.publishEvent(new ApplicationEvent(new String("我发步的事件")) {});
        annotationConfigApplicationContext.close();
    }
}
