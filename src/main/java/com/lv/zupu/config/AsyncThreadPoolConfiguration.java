package com.lv.zupu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
//@ConditionalOnClass(CurrentTraceContext.class)
//@ConditionalOnBean(type = "brave.Tracer")
//@AutoConfigureAfter(name = "org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration")
public class AsyncThreadPoolConfiguration {

//  @Autowired //主要为了链路跟踪
//  private CurrentTraceContext currentTraceContext;

  @Value("${hn.pool.thread-num:20}")
  private int threadNum;

  @Bean
  @ConditionalOnMissingBean
  public ExecutorService asyncThreadPool() {
    ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
    return executorService;
//    return currentTraceContext.executorService(executorService);
  }
}