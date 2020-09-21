package com.kedacom.config;

import com.kedacom.dao.BookDao;
import org.springframework.context.annotation.*;

/**
 *  自动装配:
 *      -> spring利用依赖注入DI,完成对IOC容器中各个组件的依赖关系赋值
 *  1) @Autowired : 自动注入
 *      -> 默认有限按照类型区容器中找对应的组件 : applicationContext.getBean(BookDao.class)
 *      -> 如果找到多个相同类型的组件,再将属性的名称作为组件的 id 去容器中查找
 *          applicationContext.getBean("bookDao")
 *      -> @Qualifier("bookDao") : 使用@Qualifier指定需要装配的组建的 id,而不是使用属性名
 *      -> 自动装配默认一定要将属性赋值好,没有就报错;
 *          -> 想没找到也不报错, 可以使用我们 AutoWired 里面的 require 属性来赋值 @Autowired(required=false)
 *      -> @Primary : 让 spring 进行自动装配的时候,默认使用首选的 bean:
 *              也可以继续使用@Qualifier 指定需要装配的 bean 的名字
 *      BookService {
 *          @Autowired
 *          BookDao bookDao
 *      }
 *  2) spring 还支持使用@Resource(JSR250)和@Inject(JSR330)[java 规范的标注]
 *      @Resource :
 *          -> 可以和@Autowired 一样实现自动装配, 默认按照组件名称进行装配的, 可以在 @Resource(name="xxx") 可以在里面指定 name
 *          -> 没有能支持@Primary 功能没有支持, 没有支持@Autowired(required=false)
 *          -> java 的规范
 *      @Inject :
 *          -> 需要导入 javax.inject 的包, 和 Autowired 的功能一样
 *          -> 这和 Autowired 的区别是 Inject 不支持 require=false 功能
 *          -> java 的规范
 *      @Autowired :
 *          -> 是 spring 定义的
 *  3) AutowiredAnnotationBeanPostProcessor : 解析完成自动装配功能
 * @author python
 */
@Configuration
@ComponentScans(
        @ComponentScan(value = {"com.kedacom.service", "com.kedacom.dao"})
)
public class MainConfigOfAutowired {

    @Bean("bookDao2")
    public BookDao bookDao() {
        BookDao bookDao = new BookDao();
        bookDao.setLabel("2");
        return bookDao;
    }

}
