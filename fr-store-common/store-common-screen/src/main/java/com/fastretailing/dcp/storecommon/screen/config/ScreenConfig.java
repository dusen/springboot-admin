/**
 * @(#)ScreenConfig.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import com.fastretailing.dcp.common.web.interceptor.RequestPathVariableInterceptor;
import com.fastretailing.dcp.storecommon.screen.interceptor.AuditLogInterceptor;

/**
 * Configuration for screen application.
 * 
 */
@Configuration
public class ScreenConfig extends WebMvcConfigurationSupport {

    /** Common pattern. */
    private static final String COMMON_PATTERN = "/**";
    /** Base screen path. */
    private static final String SCREEN_PATH_BASE = "/{brand}/{region}/screen";
    /** Path variable level 1. */
    private static final String PATH_VARIABLE_LEVEL1 = "/{l1}";
    /** Path variable level 2. */
    private static final String PATH_VARIABLE_LEVEL2 = "/{l1}/{l2}";
    /** Path variable level 3. */
    private static final String PATH_VARIABLE_LEVEL3 = "/{l1}/{l2}/{l3}";
    /** Path variable store coe. */
    private static final String PATH_VARIABLE_STORE_CODE = "/{storecode}";
    /** Static path. */
    private static final String STATIC_PATTERN = "/static/**";
    /** Template resource path. */
    private static final String RESOURCE_LOCATION = "classpath:/templates/static/";

    /** Configuration for audit. */
    @Autowired
    private AuditConfiguration auditConfiguration;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new AuditLogInterceptor(auditConfiguration))
                .addPathPatterns(COMMON_PATTERN)
                .excludePathPatterns(STATIC_PATTERN);
        registry.addInterceptor(new RequestPathVariableInterceptor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(STATIC_PATTERN, SCREEN_PATH_BASE + STATIC_PATTERN,
                SCREEN_PATH_BASE + PATH_VARIABLE_LEVEL1 + STATIC_PATTERN,
                SCREEN_PATH_BASE + PATH_VARIABLE_LEVEL2 + STATIC_PATTERN,
                SCREEN_PATH_BASE + PATH_VARIABLE_LEVEL3 + STATIC_PATTERN,
                SCREEN_PATH_BASE + PATH_VARIABLE_STORE_CODE + STATIC_PATTERN,
                SCREEN_PATH_BASE + PATH_VARIABLE_STORE_CODE + PATH_VARIABLE_LEVEL1 + STATIC_PATTERN,
                SCREEN_PATH_BASE + PATH_VARIABLE_STORE_CODE + PATH_VARIABLE_LEVEL2 + STATIC_PATTERN,
                SCREEN_PATH_BASE + PATH_VARIABLE_STORE_CODE + PATH_VARIABLE_LEVEL3 + STATIC_PATTERN,
                SCREEN_PATH_BASE + PATH_VARIABLE_STORE_CODE + PATH_VARIABLE_STORE_CODE
                        + STATIC_PATTERN)
                .addResourceLocations(RESOURCE_LOCATION);
    }

    /**
     * Screen locale resolver.
     * 
     * @return Locale resolver.
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new ScreenLocaleResolver();
    }

    /**
     * Locale change interceptor.
     * 
     * @return {@link LocaleChangeInterceptor}
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor();
    }
}
