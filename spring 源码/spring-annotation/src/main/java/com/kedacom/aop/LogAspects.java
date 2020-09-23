package com.kedacom.aop;

/**
 * @author python
 */
public class LogAspects {
    public void logStart(){
        System.out.println("除法运行 ... 参数列表 --> {}");
    }

    public void logEnd(){
        System.out.println("除法结束 ...");
    }

    public void logReturn(){
        System.out.println("除法正常返回 ...");
    }

    public void logException(){
        System.out.println("除法异常 ... 异常信息 ...");
    }
}
