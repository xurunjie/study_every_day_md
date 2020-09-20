package com.kedacom.config;

import com.kedacom.bean.Person;
import com.kedacom.service.BookService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * 配置类==配置文件
 *
 * @see Configurable 这是告诉 spring 这是一个配置类
 * @see ComponentScan 这是一个扫描注解 支持一个配置类上面存在 n 个这个注解 -> 重复注解
 */
@Configurable
//@ComponentScan("com.kedacom")
@ComponentScan(value = "com.kedacom",
//        excludeFilters = {
//            //  excludeFilter 除什么之外都需要
//                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
//        },
        includeFilters = {
                // 包含
                //  @see FilterType.ANNOTATION 按照给定的注解
                //  @see FilterType.ASSIGNABLE_TYPE 按照给定的类型
                //  @see FilterType.ASPECTJ 按照 ASPECTJ 表达式
                //  @see FilterType.REGEX 按照正则表达式
                //  @see FilterType.CUSTOM 自定义规则
//                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class}),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
        },
//        这里我们需要记住我们上面设置的 includeFilter 只包含,要想生效只能需要把默认的过滤器禁用掉
        useDefaultFilters = false
)
public class MainConfig {

    /**
     * 给容器里面注册一个 bean, 默认是方法名作为name, 可以在 Bean 里面指定 name
     *
     * @return bean
     */
    @Bean(name = "person")
    public Person person01() {
        return new Person("小科", 20);
    }
}
