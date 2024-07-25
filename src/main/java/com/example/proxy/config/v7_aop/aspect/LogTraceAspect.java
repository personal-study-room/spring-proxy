package com.example.proxy.config.v7_aop.aspect;


import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LogTraceAspect {

  private final LogTrace logTrace;

  // log.info("getSignature={}", joinPoint.getSignature()); //join point 시그니쳐
  // log.info("getArgs={}", joinPoint.getArgs()); //전달인자
  // log.info("target={}", joinPoint.getTarget()); //실제 호출 대상
  @Around("execution(* com.example.proxy.app..*(..)) && !execution(* com.example.proxy.app..noLog(..))") // pointcut
  public Object execute(ProceedingJoinPoint joinPoint) throws Throwable { // advice
    TraceStatus status = null;
    try {

      String message = joinPoint.getSignature().toShortString();

      status = logTrace.begin(message);

      // target 호출
      Object result = joinPoint.proceed();

      logTrace.end(status);

      return result;
    } catch (Exception e) {
      logTrace.exception(status, e);
      throw e;
    }
  }
}
