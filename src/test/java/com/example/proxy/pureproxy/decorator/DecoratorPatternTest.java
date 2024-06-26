package com.example.proxy.pureproxy.decorator;

import com.example.proxy.pureproxy.decorator.code.Component;
import com.example.proxy.pureproxy.decorator.code.DecoratorPatternClient;
import com.example.proxy.pureproxy.decorator.code.MessageDecorator;
import com.example.proxy.pureproxy.decorator.code.RealComponent;
import com.example.proxy.pureproxy.decorator.code.TimeDecorator;
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

  @Test
  void messageDecorator() {
    Component realComponent = new RealComponent();
    Component messageComponent = new MessageDecorator(realComponent);
    DecoratorPatternClient client = new DecoratorPatternClient(messageComponent);

    client.execute();
    /**
     * 09:21:15.456 [main] INFO com.example.proxy.decorator.code.MessageDecorator -- MessageDecorator 실행
     * 09:21:15.458 [main] INFO com.example.proxy.decorator.code.RealComponent -- RealComponent 실행
     * 09:21:15.458 [main] INFO com.example.proxy.decorator.code.MessageDecorator -- MessageDecorator 꾸미기 적용전=data, 적용 후=****data****
     * 09:21:15.458 [main] INFO com.example.proxy.decorator.code.DecoratorPatternClient -- result=****data****
     */
  }

  @Test
  void timeDecorator() {
    Component realComponent = new RealComponent();
    Component messageComponent = new MessageDecorator(realComponent);
    Component timeDecorator = new TimeDecorator(messageComponent);
    DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);

    client.execute();
    /**
     * 09:26:27.396 [main] INFO com.example.proxy.decorator.code.TimeDecorator -- TimeDecorator 실행
     * 09:26:27.397 [main] INFO com.example.proxy.decorator.code.MessageDecorator -- MessageDecorator 실행
     * 09:26:27.398 [main] INFO com.example.proxy.decorator.code.RealComponent -- RealComponent 실행
     * 09:26:27.398 [main] INFO com.example.proxy.decorator.code.MessageDecorator -- MessageDecorator 꾸미기 적용전=data, 적용 후=****data****
     * 09:26:27.399 [main] INFO com.example.proxy.decorator.code.TimeDecorator -- TimeDecorator 종료 resultTime=-2ms
     * 09:26:27.399 [main] INFO com.example.proxy.decorator.code.DecoratorPatternClient -- result=****data****
     */
  }
}
