package com.example.proxy.jdkdynamic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ReflectionTest {


  @Test
  void reflection0() {
    Hello target = new Hello();

    // 공통 로직1 시작
    log.info("start");
    String result1 = target.callA();
    log.info("result={}", result1);
    // 공통 로직1 종료

    // 공통 로직2 시작
    log.info("start");
    String result2 = target.callB();
    log.info("result={}", result2);
    // 공통 로직2 종료

    /**
     * 09:50:06.038 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest -- start
     * 09:50:06.039 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest$Hello -- callA
     * 09:50:06.039 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest -- result=A
     * 09:50:06.039 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest -- start
     * 09:50:06.039 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest$Hello -- callB
     * 09:50:06.039 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest -- result=B
     */
  }

  @Test
  void reflection1() throws Exception {
    // 클래스 정보
    Class classHello = Class.forName("com.example.proxy.jdkdynamic.ReflectionTest$Hello");

    Hello target = new Hello();

    // callA 메서드 정보
    Method methodCallA = classHello.getMethod("callA");
    Object result1 = methodCallA.invoke(target);
    log.info("result1={}", result1);

    //callB 메서드 정보
    Method methodCallB = classHello.getMethod("callB");
    Object result2 = methodCallB.invoke(target);
    log.info("result2={}", result2);
    /**
     * 09:48:37.352 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest$Hello -- callA
     * 09:48:37.354 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest -- result1=A
     * 09:48:37.354 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest$Hello -- callB
     * 09:48:37.354 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest -- result2=B
     */
  }


  @Test
  void reflection2() throws Exception {
    // 클래스 정보
    Class classHello = Class.forName("com.example.proxy.jdkdynamic.ReflectionTest$Hello");

    Hello target = new Hello();

    // callA 메서드 정보
    Method methodCallA = classHello.getMethod("callA");
    dynamicCall(methodCallA, target);

    //callB 메서드 정보
    Method methodCallB = classHello.getMethod("callB");
    dynamicCall(methodCallB, target);
    /**
     * 09:54:17.957 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest -- start
     * 09:54:17.958 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest$Hello -- callA
     * 09:54:17.958 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest -- result=A
     * 09:54:17.959 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest -- start
     * 09:54:17.959 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest$Hello -- callB
     * 09:54:17.959 [main] INFO com.example.proxy.jdkdynamic.ReflectionTest -- result=B
     */

    // !!!주의
    // 리플렉션을 사용하면 클래스와 메서드의 메타정보를 사용해서 애플리케이션을 동적으로 유연하게 만들 수 있다.
    // 하지만 리플렉션 기술은 런타임에 동작하기 때문에, 컴파일 시점에 오류를 잡을 수 없다.
  }

  private void dynamicCall(Method method, Object target)
      throws InvocationTargetException, IllegalAccessException {
    log.info("start");
    Object result = method.invoke(target);
    log.info("result={}", result);

  }

  @Slf4j
  static class Hello {

    public String callA() {
      log.info("callA");
      return "A";
    }

    public String callB() {
      log.info("callB");
      return "B";
    }
  }
}
