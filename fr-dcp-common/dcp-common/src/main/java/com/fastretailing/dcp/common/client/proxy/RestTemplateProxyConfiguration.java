/**
 * @(#)RestTemplateProxyConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.client.proxy;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fastretailing.dcp.common.client.configuration.RestTemplateConfigurationProperties;

/**
 * RestTemplate's proxy configuration.<br>
 * Only for development environment<br>
 * @author Fast Retailing
 * @version $Revision$
 */
@Profile({"dev"})
@Component
public class RestTemplateProxyConfiguration {

    /**
     * RestTemplate's configuration bean.<br>
     */
    @Autowired
    private RestTemplateConfigurationProperties config;

    /**
     * Create the proxy client.<br>
     * @return proxy http client
     */
    @Bean(name = "proxyHttpClient")
    public HttpClient buildHttpClient() {

        // if in the configurations proxy's host or port were not set
        if (StringUtils.isBlank(config.getProxyHost()) || null == config.getProxyPort()) {
            return null;
        }

        HttpClientBuilder builder = HttpClientBuilder.create()
                .setProxy(new HttpHost(config.getProxyHost(), config.getProxyPort()));

        // if in the configurations proxy's username and password were set
        if (StringUtils.isNotBlank(config.getProxyUser())
                && StringUtils.isNotBlank(config.getProxyPassword())) {
            CredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(
                    new AuthScope(config.getProxyHost(), config.getProxyPort()),
                    new UsernamePasswordCredentials(
                            config.getProxyUser(),
                            config.getProxyPassword()
                    )
            );
            builder.setDefaultCredentialsProvider(provider).disableCookieManagement();
        }

        return builder.build();
    }

}
