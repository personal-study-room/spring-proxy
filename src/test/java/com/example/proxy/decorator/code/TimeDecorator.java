package com.example.proxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator extends AbstractDecoratorComponent {


  public TimeDecorator(Component component) {
    super(component);
  }

  @Override
  public String operation() {
    log.info("TimeDecorator 실행");
    long startTime = System.currentTimeMillis();
    String result = super.operation();
    long endTime = System.currentTimeMillis();
    log.info("TimeDecorator 종료 resultTime={}ms", (startTime - endTime));
    return result;
  }
}
