package com.example.proxy.config.v1_proxy.interface_proxy;

import com.example.proxy.app.v1.OrderServiceV1;
import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements OrderServiceV1 {

  private final OrderServiceV1 target;
  private final LogTrace logTrace;
  @Override
  public void orderItem(String itemId) {
    TraceStatus status = null;
    try {
      status = logTrace.begin("OrderServiceV1.orderItem()");
      // target 호출
      target.orderItem(itemId);
      logTrace.end(status);

    } catch (Exception e) {
      logTrace.exception(status, e);
      throw e;
    }
  }
}
