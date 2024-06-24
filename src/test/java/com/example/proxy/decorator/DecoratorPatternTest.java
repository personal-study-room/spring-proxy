package com.example.proxy.decorator;

import com.example.proxy.decorator.code.DecoratorPatternClient;
import com.example.proxy.decorator.code.RealComponent;
import org.junit.jupiter.api.Test;

public class DecoratorPatternTest {

  @Test
  void noDecorator() {
    RealComponent realComponent = new RealComponent();
    DecoratorPatternClient client = new DecoratorPatternClient(realComponent);

    client.execute();
    /**
     * 09:15:32.658 [main] INFO com.example.proxy.decorator.code.RealComponent -- RealComponent 실행
     * 09:15:32.659 [main] INFO com.example.proxy.decorator.code.DecoratorPatternClient -- result=data
     *
     * 여긴 그냥 실제 객체를 호출하는 부분
     *
     */
  }

}
