package com.example.proxy.config.v1_proxy.interface_proxy;

import com.example.proxy.app.v1.OrderRepositoryV1;
import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositoryV1 {

  private final OrderRepositoryV1 target;
  private final LogTrace logTrace;

  @Override
  public void save(String itemId) {
    TraceStatus status = null;
    try {
      status = logTrace.begin("OrderRepositoryV1.request()");
      // target 호출
      target.save(itemId);
      logTrace.end(status);

    } catch (Exception e) {
      logTrace.exception(status, e);
      throw e;
    }
  }
}
