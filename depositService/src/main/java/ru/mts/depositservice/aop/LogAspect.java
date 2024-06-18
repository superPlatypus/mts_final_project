//package ru.mts.depositservice.aop;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Arrays;
//
//@Aspect
//@Component
//public class LogAspect {
//    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
//
//
//    @Pointcut("execution(public * ru.mts.depositservice..*(..)) ")
//    public void applicationPackagePointcut() {
//    }
//
//
//    @Around("applicationPackagePointcut()")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        logger.info("Начало метода: " + joinPoint.getSignature().toShortString() + " с аргументами: " + Arrays.toString(joinPoint.getArgs()));
//
//        Object result = null;
//        try {
//            result = joinPoint.proceed();
//            logger.info("Выход из метода: " + joinPoint.getSignature().toShortString() + " с результатом: " + result);
//        } catch (Throwable e) {
//            logger.error("Исключение в методе: " + joinPoint.getSignature().toShortString() + " по причине: " + e.getMessage(), e);
//            throw e;
//        }
//
//        return result;
//    }
//
//}