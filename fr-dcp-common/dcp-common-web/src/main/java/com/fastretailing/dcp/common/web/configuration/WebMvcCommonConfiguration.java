/**
 * @(#)WebMvcCommonConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.timezone.DateTimeAndTimeModuleFactory;
import com.fastretailing.dcp.common.web.adminapi.aop.AdminApiCheckInterceptor;
import com.fastretailing.dcp.common.web.adminapi.aop.ConsumerJsonIgnoreIntrospector;
import com.fastretailing.dcp.common.web.etag.EtagHandlerMethodArgumentResolver;
import com.fastretailing.dcp.common.web.interceptor.RequestHeaderVariableInterceptor;
import com.fastretailing.dcp.common.web.interceptor.RequestPathVariableInterceptor;
import com.fastretailing.dcp.common.web.interceptor.ResponseHeaderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * WebMvcCommonConfiguration.<br>
 *     the configuration of web mvc common  <br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
public class WebMvcCommonConfiguration extends WebMvcConfigurerAdapter {
    /**
     * Add argument resolver for Etag.
     * @param argumentResolvers Etag's argument resolver.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new EtagHandlerMethodArgumentResolver());
    }

    /**
     * Add the module for LocalDateTime and LocalTime.
     * @param objectMapper jackson mapper
     * @return MappingJackson2HttpMessageConverter instance
     */
    @Bean
    public  MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(
            ObjectMapper objectMapper) {
        // register LocalDateTime/LocalTime's simple module
        objectMapper.registerModule(DateTimeAndTimeModuleFactory.createDateTimeSimpleModule());
        objectMapper.registerModule(DateTimeAndTimeModuleFactory.createTimeSimpleModule());
        // add the annotation[ConsumerJsonIgnore]'s check
        objectMapper.setAnnotationIntrospector(new ConsumerJsonIgnoreIntrospector());
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    /**
     * Add the interceptor for save the request path variable's value.
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestHeaderVariableInterceptor());
        registry.addInterceptor(new RequestPathVariableInterceptor());
        registry.addInterceptor(new AdminApiCheckInterceptor());
        registry.addInterceptor(new ResponseHeaderInterceptor());
    }
}
