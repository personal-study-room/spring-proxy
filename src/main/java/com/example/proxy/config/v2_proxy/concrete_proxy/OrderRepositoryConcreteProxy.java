package com.example.proxy.config.v2_proxy.concrete_proxy;

import com.example.proxy.app.v2.OrderRepositoryV2;
import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryConcreteProxy extends OrderRepositoryV2 {

  private final OrderRepositoryV2 target;
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
