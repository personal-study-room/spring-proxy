package com.example.proxy.jdkdynamic.code;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeInvocationHandler implements InvocationHandler {

  // 프록시는 항상 호출할 대싱이 필요
  private final Object target;


  /**
   *
   * @param proxy the proxy instance that the method was invoked on
   *
   * @param method the {@code Method} instance corresponding to
   * the interface method invoked on the proxy instance.  The declaring
   * class of the {@code Method} object will be the interface that
   * the method was declared in, which may be a superinterface of the
   * proxy interface that the proxy class inherits the method through.
   *
   * @param args an array of objects containing the values of the
   * arguments passed in the method invocation on the proxy instance,
   * or {@code null} if interface method takes no arguments.
   * Arguments of primitive types are wrapped in instances of the
   * appropriate primitive wrapper class, such as
   * {@code java.lang.Integer} or {@code java.lang.Boolean}.
   *
   * @return
   * @throws Throwable
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    log.info("TimeProxy 실행");
    long startTime = System.currentTimeMillis();
    Object result = method.invoke(target, args);
    long endTime = System.currentTimeMillis();
    log.info("result time= {}", (endTime - startTime));
    return result;
  }
}
