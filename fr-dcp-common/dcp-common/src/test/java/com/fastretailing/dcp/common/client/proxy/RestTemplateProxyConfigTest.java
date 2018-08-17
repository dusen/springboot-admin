/**
 * @(#)RestTemplateProxyConfigTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.client.proxy;

import org.apache.http.client.HttpClient;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.fastretailing.dcp.common.client.configuration.RestTemplateConfigurationProperties;

/**
 * The unit test for RestTemplate's RestTemplateProxyConfig.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class RestTemplateProxyConfigTest {

    private RestTemplateProxyConfiguration target = new RestTemplateProxyConfiguration();

    @Test
    public void testBuildHttpClientWithProxy() {

        RestTemplateConfigurationProperties mockConfig = new RestTemplateConfigurationProperties();
        mockConfig.setProxyHost("basket.oms.fastretailing.com");
        mockConfig.setProxyPort(8080);
        mockConfig.setProxyUser("mockUser");
        mockConfig.setProxyPassword("mockPassword");
        mockConfig.setConnectTimeout(100);
        mockConfig.setReadTimeout(200);

        ReflectionTestUtils.setField(target, "config", mockConfig);

        HttpClient actual = target.buildHttpClient();

        MatcherAssert.assertThat(actual, CoreMatchers.notNullValue());
    }

    @Test
    public void testBuildHttpClientWithOutProxyHost() {

        RestTemplateConfigurationProperties mockConfig = new RestTemplateConfigurationProperties();
        mockConfig.setProxyPort(8080);
        mockConfig.setProxyUser("mockUser");
        mockConfig.setProxyPassword("mockPassword");
        mockConfig.setConnectTimeout(100);
        mockConfig.setReadTimeout(200);

        ReflectionTestUtils.setField(target, "config", mockConfig);

        HttpClient actual = target.buildHttpClient();

        MatcherAssert.assertThat(actual, CoreMatchers.nullValue());
    }

    @Test
    public void testBuildHttpClientWithOutProxyPort() {

        RestTemplateConfigurationProperties mockConfig = new RestTemplateConfigurationProperties();
        mockConfig.setProxyHost("basket.oms.fastretailing.com");
        mockConfig.setProxyUser("mockUser");
        mockConfig.setProxyPassword("mockPassword");
        mockConfig.setConnectTimeout(100);
        mockConfig.setReadTimeout(200);

        ReflectionTestUtils.setField(target, "config", mockConfig);

        HttpClient actual = target.buildHttpClient();

        MatcherAssert.assertThat(actual, CoreMatchers.nullValue());
    }

    @Test
    public void testBuildHttpClientWithoutProxyUser() {

        RestTemplateConfigurationProperties mockConfig = new RestTemplateConfigurationProperties();
        mockConfig.setProxyHost("basket.oms.fastretailing.com");
        mockConfig.setProxyPort(8080);
        mockConfig.setProxyPassword("mockPassword");
        mockConfig.setConnectTimeout(100);
        mockConfig.setReadTimeout(200);

        ReflectionTestUtils.setField(target, "config", mockConfig);

        HttpClient actual = target.buildHttpClient();

        MatcherAssert.assertThat(actual, CoreMatchers.notNullValue());
    }

    @Test
    public void testBuildHttpClientWithoutProxyPassword() {

        RestTemplateConfigurationProperties mockConfig = new RestTemplateConfigurationProperties();
        mockConfig.setProxyHost("basket.oms.fastretailing.com");
        mockConfig.setProxyPort(8080);
        mockConfig.setProxyUser("mockUser");
        mockConfig.setConnectTimeout(100);
        mockConfig.setReadTimeout(200);

        ReflectionTestUtils.setField(target, "config", mockConfig);

        HttpClient actual = target.buildHttpClient();

        MatcherAssert.assertThat(actual, CoreMatchers.notNullValue());
    }

}
