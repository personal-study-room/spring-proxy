package com.example.proxy.pureproxy.proxy;

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

  }

}
