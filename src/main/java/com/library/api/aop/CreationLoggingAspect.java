package com.library.api.aop;

import com.library.api.model.dto.HasId;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CreationLoggingAspect {

    @AfterReturning(
            pointcut = "execution(* com.library.api.service..*.create*(..))",
            returning = "result"
    )
    public void logCreated(Object result) {
        if (result instanceof HasId hasId) {
            log.info("Объект {} создан - id= {}", result.getClass().getSimpleName(), hasId.getId());
        } else {
            log.info("Объект создан");
        }
    }
}
