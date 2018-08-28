package com.lv.zupu.base.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/7/23 19:31
 * @Version: 1.0
 * modified by:
 */
@Component
@Aspect
public class LogAspect {

    @Pointcut("@annotation(com.lv.zupu.base.aspect.MyLog)")
    public void cut(){

    }

    @Before("cut()")
    public void beforeLogs(JoinPoint joinPoint){
        System.out.println("请求前置处理 方法参数："+joinPoint.getArgs());
        System.out.println("请求前置处理 方法签名："+joinPoint.getSignature());
    }

    @After("cut()&&@annotation(myLog)")
    public void afterLogs(JoinPoint joinPoint,MyLog myLog){
        System.out.println("请求后置处理 方法参数："+joinPoint.getArgs());
        System.out.println("请求后置处理 方法签名："+joinPoint.getSignature());
        System.out.println("获取注解内容:"+myLog.value()[0]);
    }

    @AfterReturning(value="cut()&&@annotation(myLog)",returning = "result")
    public String afterRuning(JoinPoint joinPoint,Object result,MyLog myLog){
        System.out.println("目标方法返回结果:"+result);
        result = "log-赵雅芝";
        System.out.println("目标方法修改后结果:"+result);
        System.out.println("获取注解内容:"+myLog.value()[0]);
        return result.toString();
    }
}
