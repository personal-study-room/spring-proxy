package com.example.proxy.config.v6_autoproxy;

import com.example.proxy.config.AppV1Config;
import com.example.proxy.config.AppV2Config;
import com.example.proxy.config.v4_proxyfactory.advisce.LogTraceAdvice;
import com.example.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

//  @Bean
  public Advisor advisor1(LogTrace logTrace) {
    // pointcut
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("request*", "order*", "save*");

    // advice
    LogTraceAdvice advice = new LogTraceAdvice(logTrace);

    return new DefaultPointcutAdvisor(pointcut, advice);
  }

//  @Bean
  public Advisor advisor2(LogTrace logTrace) {
    // pointcut
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("execution(* com.example.proxy.app..*(..))");

    // advice
    LogTraceAdvice advice = new LogTraceAdvice(logTrace);

    //advisor = pointcut + advice
    return new DefaultPointcutAdvisor(pointcut, advice);
  }

  @Bean
  public Advisor advisor3(LogTrace logTrace) {
    // pointcut
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("execution(* com.example.proxy.app..*(..)) && !execution(* com.example.proxy.app..noLog(..))");

    // advice
    LogTraceAdvice advice = new LogTraceAdvice(logTrace);

    //advisor = pointcut + advice
    return new DefaultPointcutAdvisor(pointcut, advice);
  }

}
