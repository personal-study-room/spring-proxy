package com.example.proxy.cglib;

import com.example.proxy.cglib.code.TimeMethodInterceptor;
import com.example.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {


  @Test
  void cglib() {
    ConcreteService target = new ConcreteService();

    // Enhancer` : CGLIB는 `Enhancer` 를 사용해서 프록시를 생성한다.
    Enhancer enhancer = new Enhancer();

    // `enhancer.setSuperclass(ConcreteService.class)` : CGLIB는 구체 클래스를 상속 받아서 프록시 를 생성할 수 있다. 어떤 구체 클래스를 상속 받을지 지정한다.
    enhancer.setSuperclass(ConcreteService.class);

    // enhancer.setCallback(new TimeMethodInterceptor(target))` 프록시에 적용할 실행 로직을 할당한다.
    enhancer.setCallback(new TimeMethodInterceptor(target));

    // `enhancer.create()` : 프록시를 생성한다. 앞서 설정한
    ConcreteService proxy = (ConcreteService) enhancer.create();


    log.info("Target class = {}", target.getClass());
    log.info("proxy class = {}", proxy.getClass());

    /**
     * 09:29:14.297 [main] INFO com.example.proxy.cglib.CglibTest -- Target class = class com.example.proxy.common.service.ConcreteService
     * 09:29:14.300 [main] INFO com.example.proxy.cglib.CglibTest -- proxy class = class com.example.proxy.common.service.ConcreteService$$EnhancerByCGLIB$$c662b3cc
     */

    proxy.call();
    /**
     * 09:30:15.967 [main] INFO com.example.proxy.cglib.code.TimeMethodInterceptor -- TimeProxy 실행
     * 09:30:15.975 [main] INFO com.example.proxy.common.service.ConcreteService -- ConcreteService 호출
     * 09:30:15.975 [main] INFO com.example.proxy.cglib.code.TimeMethodInterceptor -- result time= 8
     */
  }

}
