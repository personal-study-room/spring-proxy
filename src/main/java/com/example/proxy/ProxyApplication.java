package com.example.proxy;

import com.example.proxy.config.AppV1Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import(AppV1Config.class) // 빈을 등록하기 위한 파일 불러오는 방법
@SpringBootApplication(scanBasePackages = "com.example.proxy.app")  // 주의. 왜 했을까? app의 V1, v2, v3, ... 하려고
public class ProxyApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProxyApplication.class, args);
  }

}
