package site.shamota.backend.todo.aop;

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

    // аспект будет выполняться для всех методов из пакета контроллеров
    @Around(("execution(* site.shamota.backend.todo.controller..*(..)))"))
    public Object profileControllerMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        // получить информацию о том, какой класс и метод выполняется
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        log.info("-------- Executing "+ className + "." + methodName + "   ----------- ");

        final StopWatch countDown = new StopWatch();

        // засекаем время
        countDown.start();
        Object result = proceedingJoinPoint.proceed(); // выполняем сам метод
        countDown.stop();

        log.info("-------- Execution time of "+ className + "." + methodName + " :: " + countDown.getTotalTimeMillis() + " ms");

        return result;
    }
}
