package com.kedacom.config;

import com.kedacom.bean.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * @see PropertySource
 *      -> 读取外部配置文件中的k/v保存到运行的环境变量中
 *      -> 加载完外部的配置文件以后使用${}取出配置文件中的值
 * @author python
 */
@Configuration
@PropertySources({
        @PropertySource("classpath:config.properties")
})
public class MainConfigOfPropertyValues {
    /**
     * @see Value 注解
     *      -> 基本数值
     *      -> 支持SPEL表达式 : #{}
     *      -> 可以写 ${} ; 取出配置文件[properties]中的值(在运行环境变量里面的值)
     * @return bean
     */
    @Bean
    public Person person(){
        return new Person();
    }
}
