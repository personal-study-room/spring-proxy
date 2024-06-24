package com.example.proxy.pureproxy.proxy;

import com.example.proxy.pureproxy.proxy.code.CacheProxy;
import com.example.proxy.pureproxy.proxy.code.ProxyPatternClient;
import com.example.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

  @Test
  void noProxyTest() {
    RealSubject realSubject = new RealSubject();
    ProxyPatternClient client = new ProxyPatternClient(realSubject);

    client.execute();
    client.execute();
    client.execute();
    /**
     * 09:09:49.523 [main] INFO com.example.proxy.pureproxy.proxy.code.RealSubject -- 실제 객체 호출
     * 09:09:50.527 [main] INFO com.example.proxy.pureproxy.proxy.code.RealSubject -- 실제 객체 호출
     * 09:09:51.533 [main] INFO com.example.proxy.pureproxy.proxy.code.RealSubject -- 실제 객체 호출
     *
     * 프록시가 적용되지 않은 상태
     * 현재 1초를 모두 기다리고 데이터가 나오고 있다.
     */
  }

  @Test
  void cacheProxyTest() {
    RealSubject realSubject = new RealSubject();
    CacheProxy cacheProxy = new CacheProxy(realSubject);
    ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

    client.execute();
    client.execute();
    client.execute();
    /**
     * 09:08:35.536 [main] INFO com.example.proxy.pureproxy.proxy.code.CacheProxy -- 프록시 호출
     * 09:08:35.537 [main] INFO com.example.proxy.pureproxy.proxy.code.RealSubject -- 실제 객체 호출
     * 09:08:36.542 [main] INFO com.example.proxy.pureproxy.proxy.code.CacheProxy -- 프록시 호출
     * 09:08:36.542 [main] INFO com.example.proxy.pureproxy.proxy.code.CacheProxy -- 프록시 호출
     *
     * 프록시를 통해서 실제 객체에 접근을 1번만 시도한다.
     * 접근 제어를 하였음
     * 캐싱 기능을 넣어서 최초 1회만 1초, 이후는 금방 데이터가 나옴
     */
  }

}
