package com.kedacom.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * @author python
 */
@Aspect
public class LogAspects {

    @Pointcut("execution(public int com.kedacom.aop.MathCalculator.*(..))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        System.out.println("方法名" + joinPoint.getSignature()
                .getName()+ "除法运行 ... 参数列表 --> " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println("除法结束 ...");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(Object result) {
        System.out.println("除法正常返回 ...");
    }

    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        System.out.println("joinPoint.getArgs() = " + Arrays.toString(joinPoint.getArgs()) + "exception = " + exception.getMessage());
        System.out.println("除法异常 ... 异常信息 ...");
    }
}
