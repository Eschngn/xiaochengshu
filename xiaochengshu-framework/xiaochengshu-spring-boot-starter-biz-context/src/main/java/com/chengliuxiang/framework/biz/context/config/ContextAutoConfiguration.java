package com.chengliuxiang.framework.biz.context.config;

import com.chengliuxiang.framework.biz.context.filter.HeaderUser2ContextFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ContextAutoConfiguration {
    @Bean
    public FilterRegistrationBean<HeaderUser2ContextFilter> filterRegistrationBean() {
        HeaderUser2ContextFilter filter = new HeaderUser2ContextFilter();
        FilterRegistrationBean<HeaderUser2ContextFilter> bean = new FilterRegistrationBean<>(filter);
        return bean;
    }
}
