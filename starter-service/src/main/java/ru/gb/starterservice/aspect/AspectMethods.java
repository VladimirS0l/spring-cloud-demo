package ru.gb.starterservice.aspect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
public class AspectMethods {

    @Pointcut("@annotation(Timer)")
    public void timerMethod() {};

    @Around("timerMethod()")
    @SneakyThrows
    public Object aroundTimerMethod(ProceedingJoinPoint proceedingJoinPoint ) {
        long start = System.nanoTime();
        Object proceed = proceedingJoinPoint.proceed();
        Class aClass = proceedingJoinPoint.getSignature().getDeclaringType();
        String method = proceedingJoinPoint.getSignature().getName();
        long finish = System.nanoTime();
        log.info("Class = {}, method = {}: {} ms" , aClass, method, (finish - start) / 1_000_000);
        return proceed;
    }
}
