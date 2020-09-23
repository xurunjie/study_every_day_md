package com.kedacom.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author python
 *
 * @Profile :指定组件在哪个环境的情况下才能被注册到容器中, 不指定, 任何环境下都能注册这个组件
 *
 *  1)、加了环境标识的bean, 只有这个环境被激活的时候才能注册到容器中, 默认是default环境@profile("default")
 *  2)、怎么指定profile
 *      -> 命令行指定 -Dspring.profiles.active=test
 *      -> 代码方式激活某种环境
 *          -> 先初始化无参对象 applicationContext = new AnnotationConfigApplicationContext()
 *          -> 初始化设置profile applicationContext.getEnvironment().setActiveProfile("prod")
 *          -> 加载配置类 applicationContext.register(MainConfigOfProfile.class)
 *          -> 刷新容器 applicationContext.refresh()
 *  3) 写在配置类上, 只有是指定的环境的时候, 整个配置类里面的所有配置才能开始生效
 */
@Profile("prod")
@Configuration
@PropertySource("classpath:dbconfig.properties")
public class MainConfigOfProfile {

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String passwd;

    @Value("${db.driverClass}")
    private String driver;

    @Profile("default")
    @Bean
    public DataSource dataSourceTest() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(passwd);
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl("jdbc:mysql//172.16.185.183:3306/test");
        return dataSource;
    }

    @Profile("prod")
    @Bean
    public DataSource dataSourceProd() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setUser(passwd);
        dataSource.setUser("jdbc:mysql//172.16.185.183:3306/test");
        dataSource.setDriverClass(driver);
        return dataSource;
    }

    @Profile("dev")
    @Bean
    public DataSource dataSourceDev() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setUser(passwd);
        dataSource.setUser("jdbc:mysql//172.16.185.183:3306/test");
        dataSource.setDriverClass(driver);
        return dataSource;
    }

}
