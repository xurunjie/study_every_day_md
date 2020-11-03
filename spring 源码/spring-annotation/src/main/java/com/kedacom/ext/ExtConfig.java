package com.kedacom.ext;

import com.kedacom.bean.Blue;
import com.kedacom.bean.Red;
import org.springframework.context.annotation.Bean;
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
 *      1）、写一个监听器(ApplicationListener实现类)来监听某个事件（ApplicationEvent及其子类）
 *          @EventListener
 *          原理: 使用EventListenerMethodProcessor处理器来解析方法上的@EventListener;
 *      2）、把监听器加入到容器；
 *      3）、只要容器中有相关事件的发布，我们就能监听到这个时间；
 *          ContextRefreshedEvent：容器刷新完成（所有bean都完全创建会发布这个事件）
 *          ContextClosedEvent: 关闭容器会发布这个事件
 *
 *      4)、发布一个事件:
 *          applicationContext.publishEvent()
 * 原理：
 *      ContextRefreshedEvent、IOC_EXT$1、ContextClosedEvent
 *      1)、ContextRefreshedEvent
 *          1)、容器创建对象: refresh()
 *          2)、finishRefresh();容器刷新完成
 *      2)、自己发布事件
 *      3)、
 *      [事件发布流程]
 *            3)、publishEvent(new ContextRefreshedEvent(this));
 *                  1)、获取时间多播器(派发器):getApplicationEventMulticaster()
 *                  2)、multicastEvent方法派发事件:
 *                  3)、获取所有的 ApplicationListener
 *                      for (final ApplicationListener<?> listener : getApplicationListeners(event, type))
 *                      1)、如果有Executor, 可以支持使用executor进行异步派发
 *                          Executor executor = getTaskExecutor();
 *                      2)、否则、同步的方式直接执行 listener 方法;invokeListener(listener,event)
 *                          拿到 listener 回调 onApplicationEvent 方法;
 * [事件多播器(派发器)]
 *      1)、容器创建对象: refresh()方法
 *      2)、initApplicationEventMulticaster();初始化 ApplicationEventMulticaster
 *          1)、先去容器中找有没有 id="applicationEventMulticaster";
 *          2)、如果没有this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
 *              并加入到容器中,我们就可以在其他组件要派发事件,自动注入这个 applicationEventMulticaster
 * [容器中有哪些就监听器]
 *      1)、容器创建对象;refresh();
 *          从容其中拿到所有的监听器,把他们注册到applicationEventMulticaster中
 *          // 将 listener 注册到ApplicationEventMulticaster中
 *          String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 * SmartInitializingSingleton原理：
 *      1）、IOC容器创建对象并refresh();
 *      2)、finishBeanFactoryInitialization(BeanFactory);初始化剩下的单实例bean
 *          1）、先创建所有的单实例bean;getBean()
 *          2)、获取所有创建好的单实例bean，判断是否是SmartInitializingSingleton类型；
 *              如果是调用smartSingleton.afterSingletonsInstantiated();
 *
 * @author python
 */
@Configuration
@ComponentScan("com.kedacom.ext")
@Import({Red.class})
public class ExtConfig {
    @Bean
    public Blue blue(){
        return new Blue();
    }
}
