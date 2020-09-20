package com.kedacom.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Dog {

    public Dog() {
        System.out.println("dog constructor ...");
    }

    @PostConstruct
    public void init(){
        System.out.println("Dog init ...");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Dog destroy ...");
    }
}
