package com.example.proxy;

import com.example.proxy.config.v3_dynamicproxy.DynamicProxyFilterConfig;
import com.example.proxy.trace.logtrace.LogTrace;
import com.example.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import(AppV1Config.class) // 빈을 등록하기 위한 파일 불러오는 방법
//@Import({AppV1Config.class, AppV2Config.class})
//@Import({InterfaceProxyConfig.class})
//@Import({ConcreteProxyConfig.class})
//@Import({DynamicProxyBasicConfig.class})
@Import({DynamicProxyFilterConfig.class})
@SpringBootApplication(scanBasePackages = "com.example.proxy.app.v3")  // 주의. 왜 했을까? app의 V1, v2, v3, ... 하려고
public class ProxyApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProxyApplication.class, args);
  }

  @Bean
  public LogTrace logTrace() {
    return new ThreadLocalLogTrace();
  }
}

