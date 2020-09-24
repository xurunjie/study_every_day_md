package com.kedacom.config;

import com.kedacom.aop.LogAspects;
import com.kedacom.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

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
