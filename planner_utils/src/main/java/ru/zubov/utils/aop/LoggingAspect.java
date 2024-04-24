package ru.zubov.utils.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Log
public class LoggingAspect {

    //Аспект будет выполняться для всех методов контроллеров

    @Around("(@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)) " +
            "&& execution(* ru.zubov..*(..))")
    public Object profileControllerMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        //Получить информацию о том, какой класс и метод выполняется
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        log.info("----------Executing " + className + "." + methodName + "    -------------------");

        StopWatch stopWatch = new StopWatch();

        // засекаем время
        stopWatch.start();

        // выполняем сам метод
        Object proceed = proceedingJoinPoint.proceed();

        // останавливаем таймер
        stopWatch.stop();

        log.info("----------Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + "ms    -------------------");

        return proceed;
    }
}
