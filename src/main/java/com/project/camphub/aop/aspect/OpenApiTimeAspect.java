package com.project.camphub.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class OpenApiTimeAspect {

    @Around("@annotation(com.project.camphub.aop.annotation.OpenApiTime)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();
        long endTime;

        try {

            log.info("[OpenApiTimeAspect] OpenApi {} 실행", methodName);

            Object result = joinPoint.proceed();

            endTime = System.currentTimeMillis();
            log.info("[OpenApiTimeAspect] OpenApi {} 종료(성공), 걸린시간 = {}", methodName, endTime-startTime);

            return result;

        } catch (Exception e) {

            endTime = System.currentTimeMillis();
            log.info("[OpenApiTimeAspect] OpenApi {} 종료(예외), 걸린시간 = {}", methodName, endTime-startTime);

            throw e;
        }

    }
}
