package com.example.proxy.cglib.code;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

@Slf4j
@RequiredArgsConstructor
public class TimeMethodInterceptor implements MethodInterceptor {

  private final Object target;




  /**
   * `obj` : CGLIB가 적용된 객체 `method` : 호출된 메서드 `args` : 메서드를 호출하면서 전달된 인수 `proxy` : 메서드 호출에 사용
   * `proxy` : 메서드 호출에 사용
   *
   * @param obj    "this", the enhanced object
   * @param method intercepted Method
   * @param args   argument array; primitive types are wrapped
   * @param proxy  used to invoke super (non-intercepted method); may be called as many times as
   *               needed
   * @return
   * @throws Throwable
   */
  @Override
  public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
      throws Throwable {
    log.info("TimeProxy 실행");
    long startTime = System.currentTimeMillis();

    // method.invoke 도 되지만 성능상 MethodProxy를 사용하는 것을 라이브러라 자체적으로 권장
    Object result = proxy.invoke(target, args);

    long endTime = System.currentTimeMillis();
    log.info("result time= {}", (endTime - startTime));
    return result;
  }
}
