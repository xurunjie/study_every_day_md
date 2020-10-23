package com.kedacom.tx;

import com.kedacom.condition.MacCondition;
import com.kedacom.condition.WindowsCondition;
import com.kedacom.dao.UserDao;
import com.kedacom.service.UserService;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 声明式事务
 *
 * 环境搭建:
 *      1. 导入相关依赖
 *          数据源、数据库驱动、spring-jdbc 模块
 *      2、配置数据源,JdbcTemplate(Spring 提供的简化数据库操作的工具)操作数据
 *      3、给方法上标注@Transactional 表示当前方法是一个事务方法;
 *      4、@EnableTransactionManagement 开启基于注解的事务管理功能
 *          @EnableXXX
 *      5、配置事务管理器来控制事务
 *          @Bean
 *          public PlatformTransactionManager transactionManager()
 *  原理:
 *  1)、@EnableTransactionManagement
 *      利用@TransactionManagementConfigurationSelector给容器中导入组件
 *      导入两个组件
 *      AutoProxyRegistrar
 *      ProxyTransactionManagementConfiguration
 *  2)、AutoProxyRegistrar: 给容器中注册一个InfrastructureAdvisorAutoProxyCreator组件
 *      给容器中注册一个InfrastructureAdvisorAutoProxyCreator组件;
 *      InfrastructureAdvisorAutoProxyCreator : ?
 *      利用后置处理器机制在对象创建以后,包装对象,返回一个代理对象(增强器),代理对象执行方法利用拦截器链执行调用;
 *  3)、ProxyTransactionManagementConfiguration 做了什么?
 *          1、给容器中注册事务增强器
 *              事务增强器
 *
 *
 * @author python
 */


@Configuration
@Import({UserDao.class, UserService.class})
@EnableTransactionManagement
public class TxConfig {

    @Conditional({MacCondition.class})
    @Bean("dataSource")
    public DataSource dataSourceMac() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/testmyinfo");
        dataSource.setUser("root");
        dataSource.setPassword("xu19951009");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Conditional({WindowsCondition.class})
    @Bean("dataSource")
    public DataSource dataSourceWindows() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://172.16.185.181:3309/luban");
        dataSource.setUser("kedacom");
        dataSource.setPassword("Keda!Mysql_36");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        // Spring对@Configuartion 类会特殊处理;给容器中加组件的方法,多次调用都只是从容器中找组件而已
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
