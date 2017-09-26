package com.webapplication.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class HIbernateDomainConverter {

    @Around("execution (java.util.List<com.webapplication.entity.*Entity> com.webapplication.service.*Impl.*(..))")
    public Object objectListConverter(ProceedingJoinPoint pjp) throws  Throwable{
        List obj = (List) pjp.proceed();
        ObjectMapper mapper = new ObjectMapper();

        if(obj.isEmpty())
            return obj;
        Class returnType = obj.get(0).getClass();
        String json = mapper.writeValueAsString(obj);
        return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, returnType));
    }

    @Around("execution (com.webapplication.entity.*Entity com.webapplication.service.*Impl.*(..))")
    public Object objectConverter(ProceedingJoinPoint pjp) throws Throwable {

        Object obj = pjp.proceed();
        Class returnType = ((MethodSignature) pjp.getSignature()).getMethod().getReturnType();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(returnType.cast(obj));
        return mapper.readValue(json, returnType);
    }


}
