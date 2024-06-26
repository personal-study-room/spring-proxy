package com.example.proxy.pureproxy.concreteproxy.code;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeProxy extends ConcreteLogic{

  private final ConcreteLogic concreteLogic;

  @Override
  public String operation() {
    log.info("TimeDecorator 실행");
    long startTime = System.currentTimeMillis();
    String result = super.operation();
    long endTime = System.currentTimeMillis();

    log.info("time result={}", (endTime - startTime));
    return result;
  }
}
