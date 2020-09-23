package com.kedacom.test;

import com.kedacom.config.MainConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * @author python
 */
public class IOCTestProfile {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.getEnvironment().setActiveProfiles("prod");

        applicationContext.register(MainConfigOfProfile.class);

        applicationContext.refresh();
        String[] names = applicationContext.getBeanNamesForType(DataSource.class);
        for (String name : names) {
            System.out.println("name = " + name);
        }
        applicationContext.close();

    }

}
