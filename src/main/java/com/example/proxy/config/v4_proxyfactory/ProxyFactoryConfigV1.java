package com.example.proxy.config.v4_proxyfactory;


import com.example.proxy.app.v1.OrderControllerV1;
import com.example.proxy.app.v1.OrderControllerV1Impl;
import com.example.proxy.app.v1.OrderRepositoryV1;
import com.example.proxy.app.v1.OrderRepositoryV1Impl;
import com.example.proxy.app.v1.OrderServiceV1;
import com.example.proxy.app.v1.OrderServiceV1Impl;
import com.example.proxy.config.v4_proxyfactory.advisce.LogTraceAdvice;
import com.example.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {

  @Bean
  public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
    OrderRepositoryV1Impl target = new OrderRepositoryV1Impl();

    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.addAdvisor(getAdvisor(logTrace));

    OrderRepositoryV1 proxy = (OrderRepositoryV1) proxyFactory.getProxy();
    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), target.getClass());
    return proxy;
  }

  @Bean
  public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
    OrderServiceV1Impl target = new OrderServiceV1Impl(orderRepositoryV1(logTrace));

    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.addAdvisor(getAdvisor(logTrace));

    OrderServiceV1 proxy = (OrderServiceV1) proxyFactory.getProxy();
    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), target.getClass());
    return proxy;
  }

  @Bean
  public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
    OrderControllerV1 target = new OrderControllerV1Impl(orderServiceV1(logTrace));

    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.addAdvisor(getAdvisor(logTrace));

    OrderControllerV1 proxy = (OrderControllerV1) proxyFactory.getProxy();
    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), target.getClass());
    return proxy;
  }


  private Advisor getAdvisor(LogTrace logTrace) {
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("request*", "order*", "save*");
    LogTraceAdvice advice = new LogTraceAdvice(logTrace);

    return new DefaultPointcutAdvisor(pointcut, advice);
  }

}
