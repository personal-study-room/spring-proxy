package com.example.proxy.advisor;

import com.example.proxy.common.advice.TimeAdvice;
import com.example.proxy.common.service.ServiceImpl;
import com.example.proxy.common.service.ServiceInterface;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class AdvisorTest {

  @Test
  void advisorTest1() {
    ServiceImpl target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    // advisor                                                    항상 적용           advice
    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

    proxyFactory.addAdvisor(advisor);

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();

    /**
     * 07:03:48.720 [main] INFO com.example.proxy.common.advice.TimeAdvice -- TimeProxy 실행
     * 07:03:48.722 [main] INFO com.example.proxy.common.service.ServiceImpl -- save 호출
     * 07:03:48.722 [main] INFO com.example.proxy.common.advice.TimeAdvice -- result time= 0
     * 07:03:48.723 [main] INFO com.example.proxy.common.advice.TimeAdvice -- TimeProxy 실행
     * 07:03:48.723 [main] INFO com.example.proxy.common.service.ServiceImpl -- find 호출
     * 07:03:48.723 [main] INFO com.example.proxy.common.advice.TimeAdvice -- result time= 0
     */
  }

  @Test
  @DisplayName("직접 만든 포인트컷")
  void advisorTest2() {
    ServiceImpl target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    // advisor                                                    항상 적용           advice
    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());

    proxyFactory.addAdvisor(advisor);

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
    /**
     * 07:27:09.035 [main] INFO com.example.proxy.advisor.AdvisorTest$MyMethodMatcher -- 포인트컷 호출 Method=save targetClass=class com.example.proxy.common.service.ServiceImpl
     * 07:27:09.036 [main] INFO com.example.proxy.advisor.AdvisorTest$MyMethodMatcher -- 포인트컷 결과 result=true
     * 07:27:09.038 [main] INFO com.example.proxy.common.advice.TimeAdvice -- TimeProxy 실행
     * 07:27:09.038 [main] INFO com.example.proxy.common.service.ServiceImpl -- save 호출
     * 07:27:09.038 [main] INFO com.example.proxy.common.advice.TimeAdvice -- result time= 0
     * 07:27:09.038 [main] INFO com.example.proxy.advisor.AdvisorTest$MyMethodMatcher -- 포인트컷 호출 Method=find targetClass=class com.example.proxy.common.service.ServiceImpl
     * 07:27:09.038 [main] INFO com.example.proxy.advisor.AdvisorTest$MyMethodMatcher -- 포인트컷 결과 result=false
     * 07:27:09.038 [main] INFO com.example.proxy.common.service.ServiceImpl -- find 호출
     */
  }

  static class MyPointcut implements Pointcut{

    @Override
    public ClassFilter getClassFilter() {
      return ClassFilter.TRUE;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
      return new MyMethodMatcher();
    }
  }


  @Slf4j
  static class MyMethodMatcher implements MethodMatcher {

    private String matchName = "save";

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
      matchName = "save";
      boolean result = method.getName().equals(matchName);
      log.info("포인트컷 호출 Method={} targetClass={}", method.getName(), targetClass);
      log.info("포인트컷 결과 result={}", result);
      return result;
    }

    @Override
    public boolean isRuntime() {
      return false;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
      return false;
    }
  }


  @Test
  @DisplayName("스프링이 제공하는 포인트컷")
  void advisorTest3() {
    ServiceImpl target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);


    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("save");
    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
    proxyFactory.addAdvisor(advisor);

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
    /**
     * 07:26:45.894 [main] INFO com.example.proxy.common.advice.TimeAdvice -- TimeProxy 실행
     * 07:26:45.899 [main] INFO com.example.proxy.common.service.ServiceImpl -- save 호출
     * 07:26:45.899 [main] INFO com.example.proxy.common.advice.TimeAdvice -- result time= 0
     * 07:26:45.900 [main] INFO com.example.proxy.common.service.ServiceImpl -- find 호출
     */
  }
}
