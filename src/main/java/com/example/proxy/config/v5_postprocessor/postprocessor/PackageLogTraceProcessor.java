package com.example.proxy.config.v5_postprocessor.postprocessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
@RequiredArgsConstructor
public class PackageLogTraceProcessor implements BeanPostProcessor {

  private final String basePackage;
  private final Advisor advisor;

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    log.info("param beanName={} bean={}", beanName, bean.getClass());

    // 프록시 적용 대상 여부 체크
    // 프록시 적용 대상이 아니면 원본을 그대로 진행
    String packageName = bean.getClass().getPackageName();

    if (!packageName.startsWith(basePackage)) {
      return bean;
    }

    // 프록시 대상이면 프록시 만들어서 반환
    ProxyFactory proxyFactory = new ProxyFactory(bean);
    proxyFactory.addAdvisor(advisor);

    Object proxy = proxyFactory.getProxy();
    log.info("create proxy: target={} proxy={}", bean.getClass(), proxy.getClass());
    return proxy;
  }
}
