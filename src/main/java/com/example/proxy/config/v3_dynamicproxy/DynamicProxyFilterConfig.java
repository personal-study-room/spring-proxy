package com.example.proxy.config.v3_dynamicproxy;


import com.example.proxy.app.v1.OrderControllerV1;
import com.example.proxy.app.v1.OrderControllerV1Impl;
import com.example.proxy.app.v1.OrderRepositoryV1;
import com.example.proxy.app.v1.OrderRepositoryV1Impl;
import com.example.proxy.app.v1.OrderServiceV1;
import com.example.proxy.app.v1.OrderServiceV1Impl;
import com.example.proxy.config.v3_dynamicproxy.handler.LogTraceFilterHandler;
import com.example.proxy.trace.logtrace.LogTrace;
import java.lang.reflect.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicProxyFilterConfig {

  private final static String[] PATTERNS = {"request*", "order*", "save*"};

  @Bean
  public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
    OrderRepositoryV1 target = new OrderRepositoryV1Impl();
    LogTraceFilterHandler handler = new LogTraceFilterHandler(target, logTrace, PATTERNS);

    return (OrderRepositoryV1) Proxy.newProxyInstance(
        OrderRepositoryV1.class.getClassLoader(),
        new Class[]{OrderRepositoryV1.class},
        handler
    );
  }

  @Bean
  public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
    OrderServiceV1 target = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
    LogTraceFilterHandler handler = new LogTraceFilterHandler(target, logTrace, PATTERNS);

    return (OrderServiceV1) Proxy.newProxyInstance(
        OrderServiceV1.class.getClassLoader(),
        new Class[]{OrderServiceV1.class},
        handler
        );
  }


  @Bean
  public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
    OrderControllerV1 target = new OrderControllerV1Impl(orderServiceV1(logTrace));
    LogTraceFilterHandler handler = new LogTraceFilterHandler(target, logTrace, PATTERNS);

    return (OrderControllerV1) Proxy.newProxyInstance(
        OrderControllerV1.class.getClassLoader(),
        new Class[]{OrderControllerV1.class},
        handler
    );
  }

}
