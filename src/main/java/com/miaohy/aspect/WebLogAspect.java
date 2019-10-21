/**
 * Author : MIAOHY
 * Time :2019/10/13 15:27
 * Beauty is better than ugly!
 */
package com.miaohy.aspect;

import com.miaohy.utils.HttpContextUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WebLogAspect {
    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    //切点描述
    @Pointcut("execution(public * com.miaohy.controller.*.*(..))")
    public void controllerLog(){}

    @Before("controllerLog()")
    public void logBefore(JoinPoint joinPoint){

        logger.info("getTarget() : "+joinPoint.getTarget().toString());
        logger.info("getKind() : "+joinPoint.getKind());
        logger.info("args : "+joinPoint.getArgs());
        logger.info("getSignature() : "+joinPoint.getSignature().toLongString());
        logger.info("ip : " + HttpContextUtils.getIpAddress());
    }
    @Before("controllerLog()")
    public void after(JoinPoint joinPoint){
        //logger.info("this is after");
    }

}
