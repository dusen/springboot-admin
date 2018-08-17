/**
 * @(#)RestTemplateConfigurationProperties.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.client.configuration;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * RestTemplate's configuration bean.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@Component
public class RestTemplateConfigurationProperties {

    /**
     * The connection timeout for the underlying HttpClient.<br>
     */
    @Value("${client.baseConfig.connectTimeout:5000}")
    private Integer connectTimeout;

    /**
     * The socket read timeout for the underlying HttpClient.<br>
     */
    @Value("${client.baseConfig.readTimeout:5000}")
    private Integer readTimeout;

    /**
     * Retry count(unused).<br>
     */
    @Nullable
    @Value("${client.baseConfig.retryCount:#{null}}")
    private Integer retryCount;

    /**
     * Retry interval (unused).<br>
     */
    @Nullable
    @Value("${client.baseConfig.retryInterval:#{null}}")
    private Integer retryInterval;

    /**
     * Proxy host.<br>
     */
    @Nullable
    @Value("${client.baseConfig.proxyHost:#{null}}")
    private String proxyHost;

    /**
     * Proxy port.<br>
     */
    @Nullable
    @Value("${client.baseConfig.proxyPort:#{null}}")
    private Integer proxyPort;

    /**
     * Proxy user name.<br>
     */
    @Nullable
    @Value("${client.baseConfig.proxyUser:#{null}}")
    private String proxyUser;

    /**
     * Proxy user password.<br>
     */
    @Nullable
    @Value("${client.baseConfig.proxyPassword:#{null}}")
    private String proxyPassword;

}
