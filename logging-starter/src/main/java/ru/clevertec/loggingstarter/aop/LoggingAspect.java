package ru.clevertec.loggingstarter.aop;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Slf4j
@Aspect
@AllArgsConstructor
@Component
public class LoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void withinController() {
    }

    @Pointcut("target(org.springframework.data.jpa.repository.JpaRepository)")
    public void targetRepository() {
    }

    @Before("withinController() || targetRepository()")
    public void beforeLayer(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        log.info(joinPoint.toString() + " with args " + Arrays.toString(args));
    }

    @AfterThrowing(value = "withinController()", throwing = "exception")
    public void afterControllerThrowingException(JoinPoint joinPoint, Exception exception) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        log.info(methodName + " with exception " + exception.toString());
    }

    @AfterReturning(value = "withinController()", returning = "returnValue")
    public void afterController(JoinPoint joinPoint, Object returnValue) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String returnedValueAsString = returnValue==null?"":returnValue.toString();
        log.info(methodName + " with response " + returnedValueAsString);
    }

    @Before("target(org.springframework.data.jpa.repository.JpaRepository.*)")
    public void beforeRepository(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        log.info(joinPoint.toString() + " with args " + Arrays.toString(args));
    }
}
