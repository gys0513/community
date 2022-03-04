package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
// @Aspect
public class AlphaAspect {
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut(){

    }

    //连接点的开始
    @Before("pointcut()")
    public void before(){
        System.out.println("before。。。。。。。。。。");
    }

    @After("pointcut()")
    public void after(){
        System.out.println("after。。。。。。。。");
    }

    @AfterReturning("pointcut()") //在返回值之后
    public void afterReturning(){
        System.out.println("afterReturning。。。。。。。");
    }

    @AfterThrowing("pointcut()") //在抛异常的时候
    public void afterThrowing(){
        System.out.println("afterThrowing。。。。。。。。。。");
    }

    @Around("pointcut()") //既在前面处理逻辑同时又在后面处理逻辑
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("目标方法被调用之前");
        Object obj = joinPoint.proceed();  //调目标对象被处理方法的逻辑
        System.out.println("目标方法被调用之后");
        return obj;
    }

}
