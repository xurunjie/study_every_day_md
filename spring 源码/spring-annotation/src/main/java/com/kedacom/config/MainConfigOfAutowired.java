package com.kedacom.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *  自动装配:
 *      -> spring利用依赖注入DI,完成对IOC容器中各个组件的依赖关系赋值
 *  1) @Autowired : 自动注入
 *      BookService {
 *          @Autowired
 *          BookDao bookDao
 *      }
 * @author python
 */
@Configuration
@ComponentScans(
        @ComponentScan(value = {"com.kedacom.service", "com.kedacom.dao"})
)
public class MainConfigOfAutowired {

}
