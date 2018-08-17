/**
 * @(#)RestUrlProperty.java
 * 
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * Property for Rest URL.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
@ConfigurationProperties(prefix = "report.rest-url")
@Data
public class RestUrlProperty {

    /**
     * Report Server host.
     */
    private String reportApiHost;

    /**
     * URL parameter: version.
     */
    private String version;
}
