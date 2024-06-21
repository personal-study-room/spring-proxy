package com.example.proxy.trace.template;

import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public abstract class AbstractTemplate<T> {

  private final LogTrace logTrace;

  public T execute(String message) {
    TraceStatus status = null;
    try {
      status = logTrace.begin(message);

      // 로직호출
      T result = call();

      logTrace.end(status);
      return result;
      
    } catch (Exception e) {
      logTrace.exception(status, e);
      throw e;
    }
  }

  protected abstract T call();
}
