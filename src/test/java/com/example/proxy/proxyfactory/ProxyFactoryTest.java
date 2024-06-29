package com.example.proxy.proxyfactory;

import com.example.proxy.common.advice.TimeAdvice;
import com.example.proxy.common.service.ConcreteService;
import com.example.proxy.common.service.ServiceImpl;
import com.example.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class ProxyFactoryTest {

  @Test
  @DisplayName("인터페이스가 있으면 JDK 동적 프록시를 사용")
  void interfaceProxy() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    proxyFactory.addAdvice(new TimeAdvice());
    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    log.info("target class= {}", target.getClass());
    log.info("proxy class = {}", proxy.getClass());

    proxy.save();
    /**
     * 10:38:00.591 [main] INFO com.example.proxy.proxyfactory.ProxyFactoryTest -- target class= class com.example.proxy.common.service.ServiceImpl
     * 10:38:00.593 [main] INFO com.example.proxy.proxyfactory.ProxyFactoryTest -- proxy class = class jdk.proxy2.$Proxy9
     * 10:38:00.595 [main] INFO com.example.proxy.common.advice.TimeAdvice -- TimeProxy 실행
     * 10:38:00.595 [main] INFO com.example.proxy.common.service.ServiceImpl -- save 호출
     * 10:38:00.595 [main] INFO com.example.proxy.common.advice.TimeAdvice -- result time= 0
     */

    assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
    assertThat(AopUtils.isCglibProxy(proxy)).isFalse();

  }

  @Test
  @DisplayName("구체 클래스는 cglib 프록시를 사용")
  void concreteProxy() {
    ConcreteService target = new ConcreteService();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    proxyFactory.addAdvice(new TimeAdvice());
    ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

    log.info("target class= {}", target.getClass());
    log.info("proxy class = {}", proxy.getClass());

    proxy.call();
    /**
     * 10:48:52.597 [main] INFO com.example.proxy.proxyfactory.ProxyFactoryTest -- target class= class com.example.proxy.common.service.ConcreteService
     * 10:48:52.598 [main] INFO com.example.proxy.proxyfactory.ProxyFactoryTest -- proxy class = class com.example.proxy.common.service.ConcreteService$$SpringCGLIB$$0
     * 10:48:52.599 [main] INFO com.example.proxy.common.advice.TimeAdvice -- TimeProxy 실행
     * 10:48:52.600 [main] INFO com.example.proxy.common.service.ConcreteService -- ConcreteService 호출
     * 10:48:52.600 [main] INFO com.example.proxy.common.advice.TimeAdvice -- result time= 1
     */

    assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
    assertThat(AopUtils.isCglibProxy(proxy)).isTrue();

  }

  @Test
  @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
  void proxyTargetClass() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(true);
    proxyFactory.addAdvice(new TimeAdvice());
    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    log.info("target class= {}", target.getClass());
    log.info("proxy class = {}", proxy.getClass());

    proxy.save();
    /**
     * 10:52:21.323 [main] INFO com.example.proxy.proxyfactory.ProxyFactoryTest -- target class= class com.example.proxy.common.service.ServiceImpl
     * 10:52:21.325 [main] INFO com.example.proxy.proxyfactory.ProxyFactoryTest -- proxy class = class com.example.proxy.common.service.ServiceImpl$$SpringCGLIB$$0
     * 10:52:21.327 [main] INFO com.example.proxy.common.advice.TimeAdvice -- TimeProxy 실행
     * 10:52:21.327 [main] INFO com.example.proxy.common.service.ServiceImpl -- save 호출
     * 10:52:21.327 [main] INFO com.example.proxy.common.advice.TimeAdvice -- result time= 0
     */

    assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
    assertThat(AopUtils.isCglibProxy(proxy)).isTrue();

  }


}
