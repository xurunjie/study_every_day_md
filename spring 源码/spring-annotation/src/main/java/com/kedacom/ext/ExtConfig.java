package com.kedacom.ext;

import com.kedacom.bean.Blue;
import com.kedacom.bean.Red;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * 扩展原理:
 * 1、BeanPostProcessor : bean 的后置处理器,bean 创建对象初始化前后进行拦截工作
 * BeanFactoryPostProcessor : beanFactory 的后置处理器
 *      在 BeanFactory 标准初始化之后调用; 所有的 bean 定义已经保存加载到 beanFactory,但是 bean的实例还未被创建
 *
 * 1)、IOC 容器创建对象
 * 2)、invokeBeanFactoryPostProcessors(beanFactory);执行 BeanFactoryPostProcess;
 *      如何找到所有的BeanFactoryPostProcessor 并执行他们的方法
 *          1)、直接在BeanFactory中找到所有类型是 BeanFactoryPostProcessor的组件,并执行他们的方法
 *          2)、在初始化创建其他组件前面执行
 * 2、BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *      postProcessBeanDefinitionRegistry
 *      在所有bean 定义信息将要被夹在,bean 实例还未创建的
 *
 *      优先于 BeanFactoryPostProcessor执行;
 *      利用BeanDefinitionRegistryPostProcessor给容器中在额外添加一些组件;
 *
 * 3、原理
 *      1）、IOC创建对象 ----> 这里指的是 IOC对象
 *      2）、refresh（） -> invokeBeanFactoryPostProcessors(beanFactory)
 *      3）、从容器中获取到所有的BeanDefinitionRegistryPostProcessor 组件。 依次触发所有的postProcessBeanDefinitionRegistry
 *          1）、依次触发所有的postProcessBeanDefinition()方法
 *          2）、再来触发postProcessBeanFactory()方法BeanFactoryPostProcessor
 *      4）、再来从容器中找到BeanFactoryPostProcessor组件， 然后依次触发postProcessBeanFactory()方法
 *
 * 3、ApplicationListener; 监听容器中发布的事件。事件驱动模型
 *      public interface ApplicationListener<E extends ApplicationEvent>
 *      监听ApplicationEvent及其下面的子事件；
 *
 * 步骤：
 *      1）、写一个监听器来监听某个事件（ApplicationEvent及其子类）
 *      2）、把监听器加入到容器；
 *      3）、只要容器中有相关事件的发布，我们就能监听到这个时间；
 *          ContextRefreshedEvent：容器刷新完成（所有bean都完全创建会发布这个事件）
 *          
 * @author python
 */
@Configuration
@ComponentScan("com.kedacom.ext")
@Import({Red.class, Blue.class})
public class ExtConfig {
}
