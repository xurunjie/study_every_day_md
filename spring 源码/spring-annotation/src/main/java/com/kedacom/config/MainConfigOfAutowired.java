package com.kedacom.config;

import com.kedacom.bean.Boss;
import com.kedacom.bean.Car;
import com.kedacom.bean.Color;
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
 *      AutowiredAnnotationBeanPostProcessor : 解析完成自动装配功能
 *  3) @Autowired : 构造器,参数,方法,属性
 *      -> 构造器
 *          -> 无参
 *              -> 默认加在ioc容器中的组件,容奇启动会调用无参构造器创建对象,再进行初始化赋值等操作
 *          -> 有参
 *              -> 1个
 *                  -> 可以标注在构造器上,也可以标注在参数上,在只有一个构造函数的情况下,也可以不标注
 *      -> 方法
 *          -> 标注在方法上,spring容器在创建当前对象,就会调用方法,完成赋值
 *          -> 方法使用的参数,自定义类型的值从ioc容器中获取, 可以再方法上标注@Autowire也可以不标注
 *              -> {
 *                      -> 这里面的Car会自动在容器中获取
 *                      @Bean
 *                      public Color color(Car car) {
 *                          return new Color();
 *                      }
 *              }
 *      -> 属性
 *          -> 标注在属性上
 *  4) 自定义组件想要使用spring容器底层的一些组件(ApplicationContext, BeanFactory, xxx)
 *        自定义组件实现xxxAware;在创建对象的时候,会调用接口的规定方法注入相关组件;Aware
 *         -> 把spring底层的一些组件注入到自定义的bean中
 *         -> xxxAware : 功能使用xxxProcessor;
 *              ApplicationContextAware ==> ApplicationContextAwareProcessor
 *  5) profile :
 *          Spring 为我们提供的可以根据当前环境,动态的激活和切换一系列组建的功能
 *          例:
 *              开发环境、测试环境、生产环境、
 *              数据源: (/A)(/B)(/C)
 *              @Profile
 *
 * @author python
 */
@Configuration
@ComponentScans(
        @ComponentScan(value = {"com.kedacom.service", "com.kedacom.dao", "com.kedacom.bean"})
)
public class MainConfigOfAutowired {

    @Bean("bookDao2")
    public BookDao bookDao() {
        BookDao bookDao = new BookDao();
        bookDao.setLabel("2");
        return bookDao;
    }

    @Bean
    public Color color(Car car) {
        return new Color();
    }

}
