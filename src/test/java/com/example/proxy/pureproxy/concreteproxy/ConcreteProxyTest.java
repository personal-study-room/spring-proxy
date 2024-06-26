package com.example.proxy.pureproxy.concreteproxy;

import com.example.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import com.example.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import com.example.proxy.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

  @Test
  void noProxy() {
    ConcreteLogic concreteLogic = new ConcreteLogic();
    ConcreteClient concreteClient = new ConcreteClient(concreteLogic);
    concreteClient.execute();
    /**
     * 08:51:37.146 [main] INFO com.example.proxy.pureproxy.concreteproxy.code.ConcreteLogic -- ConcreteLogic.operration 실행
     */
  }

  @Test
  void addProxy() {
    ConcreteLogic concreteLogic = new ConcreteLogic();
    TimeProxy timeProxy = new TimeProxy(concreteLogic);
    ConcreteClient concreteClient = new ConcreteClient(timeProxy);
    concreteClient.execute();
    /**
     * 08:51:25.225 [main] INFO com.example.proxy.pureproxy.concreteproxy.code.TimeProxy -- TimeDecorator 실행
     * 08:51:25.226 [main] INFO com.example.proxy.pureproxy.concreteproxy.code.ConcreteLogic -- ConcreteLogic.operration 실행
     * 08:51:25.226 [main] INFO com.example.proxy.pureproxy.concreteproxy.code.TimeProxy -- time result=0
     */
  }

}
