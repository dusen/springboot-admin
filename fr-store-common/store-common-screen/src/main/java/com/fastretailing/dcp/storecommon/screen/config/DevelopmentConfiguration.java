/**
 * @(#)DevelopmentConfiguration.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * Configuration for development environment.
 */
@Data
@Component
@ConfigurationProperties(prefix = "screen.authentication.develop")
public class DevelopmentConfiguration {

    /** User ID. */
    private String userId;

    /** Store code. */
    private String storeCode;

    /** Role list. */
    private List<String> roles = new ArrayList<>();

    /** Development authentication flag. */
    private boolean developModeEnabled = false;

}
