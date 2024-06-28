package com.example.proxy.config.v3_dynamicproxy.handler;

import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.util.PatternMatchUtils;

@RequiredArgsConstructor
public class LogTraceFilterHandler implements InvocationHandler {

  // 호출 대상
  private final Object target;

  // 필요한 적용할 기능
  private final LogTrace logTrace;

  // 필터링
  private final String[] patterns;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    TraceStatus status = null;
    try {
      String methodName = method.getName();

      if(!PatternMatchUtils.simpleMatch(patterns, methodName)){
        return method.invoke(target, args);
      }


      String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
      status = logTrace.begin(message);
      // target 호출
      Object result = method.invoke(target, args);
      logTrace.end(status);
      return result;
    } catch (Exception e) {
      logTrace.exception(status, e);
      throw e;
    }
  }
}
