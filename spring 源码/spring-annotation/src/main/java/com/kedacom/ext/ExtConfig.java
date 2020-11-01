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
 */
@Configuration
@ComponentScan("com.kedacom.ext")
@Import({Red.class, Blue.class})
public class ExtConfig {
}
