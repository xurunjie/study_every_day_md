package com.kedacom.config;

import com.kedacom.aop.LogAspects;
import com.kedacom.aop.MathCalculator;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

/**
 * @author python
 * AOP : [动态代理]
 *  旨在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式
 * 1、 导入aop模块 : spring aop (spring-aspects)
 * 2、 定义一个业务逻辑类 MathCalculator : 在业务逻辑运行的时候将日志进行打印
 * 3、 定义一个日志切面类(LogAspects) : 切面类里面的方法需要动态感知MathCalculator.div运行到哪里然后执行
 *      通知方法 :
 *          前置通知 :  logStart -> 在目标方法(div)运行之前运行
 *          后置通知 :  logEnd -> 在目标方法(div)运行结束之后运行
 *          返回通知 :  logReturn -> 在目标方法(div)正常返回之后运行
 *          异常通知 :  logException -> 在目标方法(div)正常返回之后运行
 *          环绕通知 :  动态代理, 手动推进目标方法(joinPoint.procced())
 * 4. 给切面类标注方法标注何时何地运行(通知注解)
 * 5. 将切面类和业务逻辑类(目标方法所在类)都加入到容器中
 * 6. 必须告诉 spring哪个类是切面类(给切面类加上 Aspect 注解)
 *      -> @Aspect 注解到所在类上
 * 7. 给配置类加@EnableAspectJAutoProxy[开启基于注解的 aop 模式]
 *      在 spring 中很多@EnableXXX -> 代表开启某项功能
 *
 * 三步走:
 *      1. 将业务逻辑组件和切面类加入到容器中,告诉 spring 哪个是切面类(@Aspects)
 *      2. 在切面类上每一个通知方法上标注通知注解, 告诉 spring 何时何地运行(切入点表达式)
 *      3. 开启基于注解的 aop 模式 : @EnableAspectJAutoProxy
 * AOP 原理 : [看给容器中注册了什么组件,这个组件什么时候工作,这个组件的功能是什么]
 *      @see EnableAspectJAutoProxy
 * 1. @EnableAspectJAutoProxy是什么
 *      @see Import (AspectJAutoProxyRegistrar.class) : 给容器中导入 AspectJAutoProxyRegistrar : 实现于ImportBeanDefinitionRegistrar接口
 *          利用AspectJAutoProxyRegistrar自定义给组件注册 bean; BeanDefinetion
 *              internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
 *          给容器中注册一个AnnotationAwareAspectJAutoProxyCreator
 * 2. AnnotationAwareAspectJAutoProxyCreator:
 *      -> extend AspectJAwareAdvisorAutoProxyCreator
 *          -> extend AbstractAdvisorAutoProxyCreator
 *              -> extend AbstractAutoProxyCreator
 *                  -> extend ProxyProcessorSupport
 *                      -> implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                      关注后置处理器(在 bean 初始化完成前后做事情), 自动装配 BeanFactory
 * 3. 需要关注的事情
 *      1. AnnotationAwareAspectJAutoProxyCreator -> 使用BeanFactory干了哪些事
 *          @see AbstractAutoProxyCreator#setBeanFactory(org.springframework.beans.factory.BeanFactory)
 *              -> 存在调用 setBeanFactory
 *          @see AbstractAdvisorAutoProxyCreator#setBeanFactory(org.springframework.beans.factory.BeanFactory)
 *              -> initBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
 *              -> 调用重写了上面的 setBeanFactory 并且调用了 initBeanFactory
 *          -> AnnotationAwareAspectJAutoProxyCreator又重写了 initBeanFactory方法,则setBeanFactory调用了子类重写的initBeanFactory方法
 *      2. AnnotationAwareAspectJAutoProxyCreator -> 使用BeanProcessor干了哪些事
 *          @see AbstractAutoProxyCreator#postProcessBeforeInstantiation(java.lang.Class, java.lang.String)
 *              -> 存在后置处理器的逻辑
 * 4. 整体调用流程
 *      1)、传入配置类,创建IOC容器
 *      2)、注册配置类,调用refresh()刷新容器
 *      3)、registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建
 *          1)、先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
 *          2)、给容器中加入别的BeanPostProcessor
 *          3)、优先注册实现了 {@link org.springframework.core.PriorityOrdered}接口的BeanPostProcessor
 *          4)、再来注册实现了 {@link Ordered}接口的BeanPostProcessor
 *          5)、注册没实现优先级接口的BeanPostProcessor
 *          6)、注册BeanPostProcessor, 实际上就是创建BeanPostProcessor对象, 保存到容器中
 *              1)、创建internalAutoProxyCreator的BeanPostProcessor [AnnotationAwareAspectJAutoProxyCreator]
 *              2)、创建bean的实例
 *              3)、populateBean; 给bean的各种属性赋值
 *              4)、initializeBean; 初始化bean;
 *                  1)、 invokeAwareMethods(); 处理aware接口的方法回调
 *                  2)、 applyBeanPostProcessorsBeforeInitialization(); 应用后置处理器的BeforeInitialization方法
 *                  3)、 invokeInitMethods(); 执行自定义的初始化方法
 *                  4)、 applyBeanPostProcessorsAfterInitialization(); 执行后置处理器的AfterInitialization方法
 *              5)、BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功; --> aspectJAdvisorsBuilder
 *          7)、把BeanPostProcessor注册到BeanFactory中:
 *              beanFactory.addBeanPostProcessor(postProcessor);
 *
* ============以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程===========
 *      @see InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation(java.lang.Class, java.lang.String)
 *      AnnotationAwareAspectJAutoProxyCreator=postProcessBeforeInstantiation 是这种后置处理器
 *     4)、finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作; 创建剩下的单实例的bean
 *          1)、遍历获取容器中所有的bean,一次创建对象getBean(beanName)
 *              getBean -> doGetBean -> getSingleton() ->
 *          2)、创建Bean
 *              [AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截,如果是InstantiationAwareBeanPostProcessor,会调用postProcessBeforeInstantiation()]
 *              1)、先从缓存中获取当前bean、如果能获取到,说明bean是之前被创建过的, 直接使用, 否则再创建;
 *                  只要创建好的Bean都会被缓存起来
 *              2)、createBean();创建Bean;
 *                  -> AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前先尝试返回bean的实例
 *                  [BeanPostProcessor是再bean对象创建完成初始化前后调用的]
 *                  [InstantiationAwareBeanPostProcessor是在创建bean实例之前先尝试用后置处理器返回对象的]
 *                  1)、resolveBeforeInstantiation(beanName, mbdToUse); 解析resolveBeforeInstantiation
 *                      希望后置处理器再次能创建返回一个代理对象;如果能返回代理对象就使用,如果不能就继续第二步调用doCreateBean
 *                      1)、 后置处理器尝试返回对象;
 *                          {@code
 *                              bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 *                              拿到所有后置处理器, 如果是InstantiationAwareBeanPostProcessor;
 *                              就执行postProcessBeforeInstantiation
 *                              if (bean != null) {
 *   						        bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 *                            }
 *                          }
 *
 *                  2)、doCreateBean(beanName, mbdToUse, args)这才是真正的创建一个bean实例, 同上面的3.6
 *
 *
 * AnnotationAwareAspectJAutoProxyCreator[InstantiationAwareBeanPostProcessor]的作用:
 * 1)、每一个bean创建之前,调用postProcessBeforeInstantiation();
 *      关心MathCalculator和LogAspects的创建
 *      1)、判断当前bean是否在advisedBeans中(保存所有需要增强的bean)
 *      2)、判断当前bean是否是基础类型的Advice、Pointcut、Advisor、AopInfrastructureBean、
 * 			或者是否是切面(@Aspect)
 * 		3)、判断是否跳过
 * 	        1)、获取候选的增强器(切面里面的通知方法)[list<Advisor> candidateAdvisors]
 *              每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor;
 *              判断每一个增强器是否是AspectJPointcutAdvisor类型的;返回true
 *          2)、永远返回false
 * 2)、创建对象
 *      postProcessAfterInitialization:
 *          return wrapIfNecessary(bean, beanName, cacheKey); // 包装如果需要的情况下
 *     1)、获取当前bean的所有增强器(通知方法)
 *          1)、找到候选的所有的增强器(找哪些通知方法是需要切入当前bean的方法)
 *          2)、获取到能在bean使用的增强器
 *          3)、给增强器排序
 *     2)、保存当前bean在advisedBeans中;
 *     3)、如果当前bean需要增强, 创建当前bean的代理对象
 *          1)、获取所有增强器(通知方法)
 *          2)、保存到proxyFactory
 *          3)、创建代理对象: Spring自动决定
 *              new JdkDynamicAopProxy(config);jdk动态代理
 *              ObjenesisCglibAopProxy(config);cglib的动态代理
 *                  -> 原理是
 *                      -> 如果我们代理的接口有实现的接口的时候jdk能创建动态代理的jdk创建
 *                      -> 反之如果没有实现接口的则使用cglib创建动态代理
 *          4)、给容器中返回当前的组件使用cglib增强了的代理对象
 *          5)、以后容器中获取到的就是这个组建的代理对象,执行目标方法的时候,代理对象就会执行通知方法的流程
 *
 *  3)、目标方法执行
 *      容器中保存了组件的代理对象(cglib 增强后的对象),这个对象里面保存了详细信息(比如增强器,代理目标)
 *      1)、CglibAopProxy.DynamicAdvisedInterceptor#intercept : 拦截目标方法的执行
 *      2)、根据 ProxyFactory对象获取将要执行的目标方法的拦截器链
 *          List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *          1)、List<Object> interceptorList = new ArrayList<>(advisors.length);
 *              一个默认的org.springframework.aop.interceptor.ExposeInvocationInterceptor.ADVISOR
 *              和四个增强器InstantiationModelAwarePointcutAdvisor: expression [pointCut()];
 *          2)、遍历所有的增强器,将其转为Interceptor
 *              registry.getInterceptors(advisor);
 *          3)、将增强器转为List<MethodInterceptor>
 *                如果是 MethodInterceptor, 直接加入到集合中
 *                如果不是, 使用AdvisorAdapter将增强器转为Interceptor
 *                转换完成返回MethodInterceptor数组
 *      3)、如果没有拦截器链,直接执行目标方法
 *          拦截器链(每个同志方法又被包装为方法拦截器,利用MethodInterceptor机制)
 *      4)、如果有拦截器链,把需要执行的目标对象,目标方法,拦截器链等信息创建一个CglibMethodInvocation对象,
 *          并调用 CglibMethodInvocation.proceed()方法
 *          并调用retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();;
 *      5)、拦截器的处罚过程
 *          1)、如果没有拦截器执行目标方法,或者拦截器的索引和拦截器数组-1 大小一样(指定到了最后一个拦截器)执行目标方法;
 *          2)、链式获取每个拦截器,拦截器执行 invoke 方法,每一个拦截器等待下一个拦截器的执行完成返回以后再来执行;
 *              拦截器链的机制,保证通知方法和目标方法的执行顺序
 * 总结:
 *      1)、@EnableAspectJAutoProxy 开启 AOP 功能
 *      2)、@EnableAspectJAutoProxy 会给容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreator
 *      3)、AnnotationAwareAspectJAutoProxyCreator是一个后置处理器
 *      4)、容器的创建流程:
 *          1)、registryBeanPostProcessor() 注册后置处理器; 创建 AnnotationAwareAspectJAutoProxyCreator对象
 *          2)、finishBeanFactoryInitialization() 初始化剩下的单实例 Bean
 *              1)、创建业务逻辑组件和切面组件
 *              2)、AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程
 *              3)、组件创建完之后,判断组件是否需要增强
 *                  是: 切面的通知方法,包装成增强器(Advisor); 给业务逻辑组件创建一个代理对象(cglib);
 *      5)、执行目标方法:
 *          1)、代理对象执行目标方法
 *          2)、CglibAopProxy.DynamicAdvisedInterceptor#intercept():
 *              1)、得到目标方法的拦截器链(增强器包装成拦截器)
 *              2)、利用拦截器的链式机制,依次进入每一个拦截器进行执行
 *              3)、效果:
 *                  正常执行: 前置通知 -> 目标方法 -> 后置通知 -> 返回通知
 *                  出现异常: 前置通知 -> 目标方法 -> 后置通知 -> 异常通知
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAop {

    /**
     * 业务逻辑类加入到容器中
     * @return MathCalculator Object
     */
    @Bean
    public MathCalculator calculator(){
        return new MathCalculator();
    }

    /**
     * 切面到容器中
     * @return LogAspects Object
     */
    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
