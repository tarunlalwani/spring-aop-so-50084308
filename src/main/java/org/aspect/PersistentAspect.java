package org.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;



@Aspect
public class PersistentAspect {

    @AfterReturning("@annotation(org.aspect.PersistentOperation) && !execution(* get*(..)) && !execution(* is*(..)) && !execution(* reset())")
    public void log(JoinPoint jp) {
        System.out.println("aspect call");
    }

}
