package com.example.proxy.jdkdynamic;

import com.example.proxy.jdkdynamic.code.AImpl;
import com.example.proxy.jdkdynamic.code.AInterface;
import com.example.proxy.jdkdynamic.code.BImpl;
import com.example.proxy.jdkdynamic.code.BInterface;
import com.example.proxy.jdkdynamic.code.TimeInvocationHandler;
import java.lang.reflect.Proxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class JdkDynamicProxyTest {

  @Test
  void dynamicA() {
    AInterface target = new AImpl();
    TimeInvocationHandler handler = new TimeInvocationHandler(target);

    // 자바 언어 차원에서 지원,
    // 동적 프록시 객체 생성.
    AInterface proxy = (AInterface) Proxy.newProxyInstance(
        AInterface.class.getClassLoader(),
        new Class[]{AInterface.class},
        handler // 핸들러의 로직을 수행
    );

    proxy.call();
    log.info("targetClass={}", target.getClass());
    //  targetClass=class com.example.proxy.jdkdynamic.code.AImpl

    log.info("proxyclass={}", proxy.getClass());
    // proxyclass=class jdk.proxy2.$Proxy8

    /**
     * 09:55:43.211 [main] INFO com.example.proxy.jdkdynamic.code.TimeInvocationHandler -- TimeProxy 실행
     * 09:55:43.212 [main] INFO com.example.proxy.jdkdynamic.code.AImpl -- A 호출
     * 09:55:43.212 [main] INFO com.example.proxy.jdkdynamic.code.TimeInvocationHandler -- result time= 0
     * 09:55:43.213 [main] INFO com.example.proxy.jdkdynamic.JdkDynamicProxyTest -- targetClass=class com.example.proxy.jdkdynamic.code.AImpl
     * 09:55:43.213 [main] INFO com.example.proxy.jdkdynamic.JdkDynamicProxyTest -- proxyclass=class jdk.proxy2.$Proxy8
     */
  }

  @Test
  void dynamicB() {
    BInterface target = new BImpl();
    TimeInvocationHandler handler = new TimeInvocationHandler(target);
    BInterface proxy = (BInterface)
        Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]
            {BInterface.class}, handler);
    proxy.call();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());
    /**
     * 08:22:52.535 [main] INFO com.example.proxy.jdkdynamic.code.TimeInvocationHandler -- TimeProxy 실행
     * 08:22:52.537 [main] INFO com.example.proxy.jdkdynamic.code.BImpl -- B 호출
     * 08:22:52.537 [main] INFO com.example.proxy.jdkdynamic.code.TimeInvocationHandler -- result time= 0
     * 08:22:52.538 [main] INFO com.example.proxy.jdkdynamic.JdkDynamicProxyTest -- targetClass=class com.example.proxy.jdkdynamic.code.BImpl
     * 08:22:52.538 [main] INFO com.example.proxy.jdkdynamic.JdkDynamicProxyTest -- proxyClass=class jdk.proxy2.$Proxy8
     */
  }

  // ** 정리 **
  /**
   * 하나의 프록시 코드 -> TimeInvocationHandler handler = new TimeInvocationHandler(target);
   * 위의 Handler 하나 가지고 해결한다.
   * 실행 순서
   * client -> $Proxy -> handler.invoke() -> target.invoke(target.class, args) <구현 클래스> -> 구현클래스.call() ->  handler 나머지 로직 -> client
   *
   * <b>1개의 프록시로 클래스의 메타 정보를 토대로 100개를 만들어야 했던 프록시 클래스를 1개만 만들어도 되도록 했다.</b>
   */

}
