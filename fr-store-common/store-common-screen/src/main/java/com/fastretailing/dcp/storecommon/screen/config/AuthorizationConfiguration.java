/**
 * @(#)AuthorizationConfiguration.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * Configuration for authorization.
 */
@Data
@Component
@ConfigurationProperties(prefix = "screen.authorization")
public class AuthorizationConfiguration {

    /**
     * Authorization exclusion path list.
     */
    private List<String> exclusionPathList = new ArrayList<>();

}
