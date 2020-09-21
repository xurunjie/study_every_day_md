package com.kedacom.config;

import com.kedacom.bean.Car;
import com.kedacom.bean.Cat;
import com.kedacom.bean.Dog;
import com.kedacom.postprocessor.MyBeanProcessor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * bean 的生命周期
 *  bean 的创建 -> 初始化 -> 销毁的过程
 * 容器管理 bean 的生命周期
 * 我们可以自定义初始化销毁方法; 容器在 bean 进行到当前生命周期的时候在调用我们自定义的初始化和销毁方法
 *  1) 指定初始化和销毁方法
 *      -> 指定init-method和 destroy-method
 *  2) 通过 bean 实现 InitializingBean (定义初始化逻辑)
 *      DisposableBean(定义销毁逻辑)
 *  3) 可以通过 JSR250 ;
 *      -> JSR250
 *          -> @PostConstruct ; 在 bean 创建完成并且属性赋值完成; 来执行初始化方法
 *          -> @PreDestroy ; 在 bean 创建销毁的时候执行
 * 4) BeanPostProcessor : bean 的后置处理器
 *      -> 在 bean 的初始化前后进行一些处理工作;
 *      -> postProcessBeforeInitialization
 *          -> 在初始化之前工作
 *      -> postProcessAfterInitialization
 *          -> 在初始化之后工作
 *
 * 备注2 :
 *      -> 构造(对象构造)
 *          -> 单实例 : 在容器启动的时候创建对象
 *          -> 多实例 : 在每次获取的时候创建对象
 *      BeanPostProcess.postProcessBeforeInitialization -> 在初始化动作之前
 *      -> 初始化
 *          -> 对象创建完成, 并赋值好, 调用初始化方法...
 *      BeanPostProcess.postProcessAfterInitialization -> 在初始化动作之后
 *      -> 销毁
 *          -> 单实例 : 容器关闭的时候进行销毁
 *          -> 多实例 : 容器不会管理这个 bean;容器不会调用销毁方法;
 *
 * 备注2 :
 *      -> BeanPostProcessor
 *          -> 遍历得到容器中所有的BeanPostProcessor; 挨个执行 postProcessBeforeInitialization
 *          -> 一旦返回null 跳出for循环,不会执行后面的BeanPostProcessor.postProcessBeforeInitialization
 *
 *      -> 装载bean流程
 *          -> populateBean(beanName, mbd, instanceWrapper);
 *              -> 给bean进行属性赋值
 *          -> applyBeanPostProcessorsBeforeInitialization
 *              -> 先执行前置处理器
 *          -> invokeAwareMethods(beanName, bean);
 *              -> 后执行自定义初始化方法
 *          -> applyBeanPostProcessorsAfterInitialization
 *              -> 在执行后置处理器
 *  spring底层对BeanPostProcessor的使用
 *      -> Bean赋值,注入其他组件,@Autowired,生命周期注解功能,@Async,xxx BeanPostProcessor
 */

@Configurable
@Import({Cat.class, Dog.class, MyBeanProcessor.class})
public class MainConfigOfLifeCycle {
    /**
     *  annotation @Scope("prototype")
     * @return bean
     */
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }
}
