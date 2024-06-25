package com.example.proxy.config.v1_proxy;

import com.example.proxy.app.v1.OrderControllerV1;
import com.example.proxy.app.v1.OrderControllerV1Impl;
import com.example.proxy.app.v1.OrderRepositoryV1;
import com.example.proxy.app.v1.OrderRepositoryV1Impl;
import com.example.proxy.app.v1.OrderServiceV1;
import com.example.proxy.app.v1.OrderServiceV1Impl;
import com.example.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import com.example.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import com.example.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import com.example.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

  @Bean
  public OrderControllerV1 orderController(LogTrace logTrace) {
    // 실제 호출할 타켓을 만든다
    OrderControllerV1Impl target = new OrderControllerV1Impl(orderService(logTrace));
    // 프록시가 해당 실제 호출할 타켓을 내부에서 호출할 수 있도록 한다.
    return new OrderControllerInterfaceProxy(target, logTrace);
  }

  @Bean
  public OrderServiceV1 orderService(LogTrace logTrace) {
    OrderServiceV1Impl target = new OrderServiceV1Impl(orderRepository(logTrace));
    return new OrderServiceInterfaceProxy(target, logTrace);
  }

  @Bean
  public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
    OrderRepositoryV1Impl target = new OrderRepositoryV1Impl();
    return new OrderRepositoryInterfaceProxy(target, logTrace);
  }
}
