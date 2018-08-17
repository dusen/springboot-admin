/**
 * @(#)AuthenticationConfiguration.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * Configuration for authentication.
 */
@Data
@Component
@ConfigurationProperties(prefix = "screen.authentication")
public class AuthenticationConfiguration {

    /**
     * Store code length.
     */
    private int storeCodeLength;

}
