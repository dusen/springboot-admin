/**
 * @(#)RestTemplateConfigTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.client;

import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.fastretailing.dcp.common.client.configuration.RestTemplateConfigurationProperties;
import com.fastretailing.dcp.common.client.handler.RestTemplateErrorHandler;
import com.fastretailing.dcp.common.client.interceptor.RestTemplateInterceptor;

/**
 * The unit test for RestTemplate's RestTemplateConfig.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class OmsRestTemplateConfigurationTest {

    private OmsRestTemplateConfiguration target = new OmsRestTemplateConfiguration();

    @Test
    public void testBuildRestTemplateWithProxy() {

        RestTemplateConfigurationProperties mockConfig = new RestTemplateConfigurationProperties();
        mockConfig.setProxyHost("basket.oms.fastretailing.com");
        mockConfig.setProxyPort(8080);
        mockConfig.setProxyPassword("mockPassword");
        mockConfig.setConnectTimeout(100);
        mockConfig.setReadTimeout(200);

        ReflectionTestUtils.setField(target, "config", mockConfig);
        ReflectionTestUtils.setField(target, "proxyHttpClient", HttpClientBuilder.create().build());
        ReflectionTestUtils.setField(target, "loggerInterceptor", new RestTemplateInterceptor());
        ReflectionTestUtils.setField(target, "errorHandler", new RestTemplateErrorHandler());

        RestTemplate actual = target.restTemplate();

        MatcherAssert.assertThat(actual, CoreMatchers.notNullValue());
    }

    @Test
    public void testBuildRestTemplateWithOutProxy() {

        RestTemplateConfigurationProperties mockConfig = new RestTemplateConfigurationProperties();
        mockConfig.setProxyHost("basket.oms.fastretailing.com");
        mockConfig.setProxyPort(8080);
        mockConfig.setProxyPassword("mockPassword");
        mockConfig.setConnectTimeout(100);
        mockConfig.setReadTimeout(200);

        ReflectionTestUtils.setField(target, "config", mockConfig);
        ReflectionTestUtils.setField(target, "loggerInterceptor", new RestTemplateInterceptor());
        ReflectionTestUtils.setField(target, "errorHandler", new RestTemplateErrorHandler());

        RestTemplate actual = target.restTemplate();

        MatcherAssert.assertThat(actual, CoreMatchers.notNullValue());
    }

}
