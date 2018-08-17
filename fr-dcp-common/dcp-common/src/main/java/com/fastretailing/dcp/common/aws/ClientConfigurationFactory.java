/**
 * @(#)ClientConfigurationFactory.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws;

import com.amazonaws.ClientConfiguration;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * AmazonS3 proxy's configuration bean.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@Component
@ConditionalOnProperty(prefix = "client.baseConfig", name = "proxyHost")
public class ClientConfigurationFactory {

    /**
     * Proxy host.<br>
     */
    @Value("${client.baseConfig.proxyHost:#{null}}")
    private String proxyHost;

    /**
     * Proxy port.<br>
     */
    @Value("${client.baseConfig.proxyPort:-1}")
    private Integer proxyPort;

    /**
     * Proxy user name.<br>
     */
    @Value("${client.baseConfig.proxyUser:#{null}}")
    private String proxyUser;

    /**
     * Proxy user password.<br>
     */
    @Value("${client.baseConfig.proxyPassword:#{null}}")
    private String proxyPassword;

    @Bean
    public ClientConfiguration initProxyToClientConfiguration() {
        if (StringUtils.isNotEmpty(proxyHost)) {
            ClientConfiguration config = new ClientConfiguration();
            config.setProxyHost(proxyHost);
            if (!Objects.isNull(proxyPort)) {
                config.setProxyPort(proxyPort);
            }
            config.setProxyUsername(proxyUser);
            config.setProxyPassword(proxyPassword);

            return config;
        }
        return null;
    }

}
