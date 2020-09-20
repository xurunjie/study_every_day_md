package com.kedacom.config;

import com.kedacom.bean.Color;
import com.kedacom.bean.ColorFactoryBean;
import com.kedacom.bean.Person;
import com.kedacom.bean.Red;
import com.kedacom.condition.*;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * 给容器中注册组件:
 *  1) 包扫描+组件标注注解(@Controller/@Service/@Repository/@Component)[自己写的类]
 *  2) @Bean[导入的第三方包里面的组件]
 *  3) @Import[快速给容器中导入一个组件]
 *      -> @Import(要导入到容器中的组件) => @Import(Red.class)
 *          -> 容器中就会自动注册这个组件
 *          -> id默认为组件的全类名
 *      -> ImportSelector => implements ImportSelector
 *          -> 返回需要导入的组件的全类名数组
 *      -> ImportBeanDefinitionRegistrar => implements ImportBeanDefinitionRegistrar
 *          -> 和上面两种不同的是这种事手动注册 bean 的方式
 *  4) 使用 spring 的FactoryBean(工厂 Bean)
 *      -> 默认获取到的是工厂 bean 调用 getObject 创建的对象
 *      -> 要获取工厂 Bean 本身,我们需要给 id 前面加一个&
 */

@Conditional(MacCondition.class)
@Configurable
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistry.class})
public class MainConfig2 {

    /**
     * Specifies the name of the scope to use for the annotated component/bean.
     * <p>Defaults to an empty string ({@code ""}) which implies
     * {@link ConfigurableBeanFactory#SCOPE_SINGLETON SCOPE_SINGLETON}.
     * @since 4.2
     * @see ConfigurableBeanFactory#SCOPE_PROTOTYPE
     *  -> prototype 多实例
     *      -> 多实例的情况下
     *          -> IOC 容器启动时候并不会去创建对象
     *          -> 而是每次获取的时候才会去创建对象
     * @see ConfigurableBeanFactory#SCOPE_SINGLETON
     *  -> singleton 默认单实例
     *  -> 默认是容器一加载就创建实例,每次获取的时候回去容器中拿
     *  -> 如果想获取的时候再去加载
     *      -> @see Lazy
     *          -> 只针对单实例 bean
     *          -> Lazy注解里面 value 设为 true 的时候单实例获取的时候才回去创建对象
     * org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST -> 同一次请求创建一个实例
     * org.springframework.web.context.WebApplicationContext#SCOPE_SESSION -> 同一个 session 创建一个实例
     * value
     */

    @Lazy
    @Scope
    @Bean
    public Person person() {
        System.out.println("创建 person");
        return new Person("科达", 19);
    }

    /**
     * @see org.springframework.context.annotation.Condition
     *      -> 可以注解在类上, 会按需求条件
     *      -> 里面放的是 Conditon 数组
     * 按照一定条件进行判断,满足条件给容器中注册 bean
     * 如果是 linux 系统, 注册 linus
     * 如果是 windows 系统, 注册 bill
     * 如果是 mac 系统, 注册 steven
     */
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person person01(){
        return new Person("Bill Gates", 62);
    }

    @Conditional({LinusCondition.class})
    @Bean("linus")
    public Person person02(){
        return new Person("linus", 50);
    }

    @Conditional({MacCondition.class})
    @Bean("steven")
    public Person person03(){
        return new Person("Mac", 50);
    }

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }

}
