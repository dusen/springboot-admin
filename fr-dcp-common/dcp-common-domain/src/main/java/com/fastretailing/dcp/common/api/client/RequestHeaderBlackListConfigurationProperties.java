/**
 * @(#)RequestHeaderBlackListConfigurationProperties.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * RequestHeaderBlackListConfigurationProperties.<br>
 * Request header blacklist's configuration.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "oms.request.header")
@Data
public class RequestHeaderBlackListConfigurationProperties {

    /**
     * Request header blacklist
     */
    private List<String> blackList;

    /**
     * Enable flag for transfer request header.
     */
    @Value("${oms.request.header.transfer-enabled:OFF}")
    private String transferEnabled;

    /**
     * Static Request header blacklist
     */
    public static List<String> staticBlackList;

    /**
     * Static enable flag for transfer request header.
     */
    public static String staticTransferEnabled;

    /**
     * Initialize black list.
     */
    @PostConstruct
    public void initializeStaticVariables() {
        RequestHeaderBlackListConfigurationProperties.staticBlackList = blackList;
        RequestHeaderBlackListConfigurationProperties.staticTransferEnabled = transferEnabled;
    }

}
