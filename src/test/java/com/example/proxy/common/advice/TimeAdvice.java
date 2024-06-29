package com.example.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    log.info("TimeProxy 실행");
    long startTime = System.currentTimeMillis();

//    Object result = proxy.invoke(target, args);
    Object result = invocation.proceed(); // 타켓을 찾고, 인수를 넣어서 알아서 실행하게 된다.

    long endTime = System.currentTimeMillis();
    log.info("result time= {}", (endTime - startTime));
    return result;
  }
}
