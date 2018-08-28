package com.lv.zupu.config.web;

import com.lv.zupu.config.SpringContextConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;

/**
 * 前后端分离后跨域处理
 */
@Configuration
@ConditionalOnWebApplication
public class CorsConfig {
    public CorsConfig() {
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    @ConditionalOnClass({CorsFilter.class})
    public Filter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", this.buildConfig());
        return new CorsFilter(source);
    }

 /*   @Bean
    @ConditionalOnClass({OncePerRequestFilter.class})
    public FilterRegistrationBean accessLogFilter(HnJson hnJson, HnSpringMvcProperties hnSpringMvcProperties) {
        FilterRegistrationBean<AccessLogFilter> registration = new FilterRegistrationBean(AccessLogFilter.of(hnJson, hnSpringMvcProperties), new ServletRegistrationBean[0]);
        registration.setDispatcherTypes(DispatcherType.REQUEST, new DispatcherType[]{DispatcherType.ASYNC});
        registration.setOrder(2147483647);
        return registration;
    }*/
}