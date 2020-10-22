package com.kedacom.tx;

import com.kedacom.dao.UserDao;
import com.kedacom.service.UserService;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 声明式事务
 * <p>
 * 环境搭建:
 * 1. 导入相关依赖
 * 数据源、数据库驱动、spring-jdbc 模块
 * 2、配置数据源,JdbcTemplate(Spring 提供的简化数据库操作的工具)操作数据
 */


@Configuration
@Import({UserDao.class, UserService.class})
public class TxConfig {

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/testmyinfo");
        dataSource.setUser("root");
        dataSource.setPassword("xu19951009");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        // Spring对@Configuartion 类会特殊处理;给容器中加组件的方法,多次调用都只是从容器中找组件而已
        return new JdbcTemplate(dataSource);
    }
}
