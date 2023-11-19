package com.zero.springweb.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("@annotation(ToLog)")
    public void ToLogAspect(){
        System.out.println("Logged by Aspect");
    }

}
