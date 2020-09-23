package com.kedacom.config;

import org.springframework.context.annotation.Configuration;

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
 */
@Configuration
public class MainConfigOfAop {
}
