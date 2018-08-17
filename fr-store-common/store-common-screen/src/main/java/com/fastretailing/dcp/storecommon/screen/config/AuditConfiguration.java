/**
 * @(#)AuditConfiguration.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * Configuration for audit.
 */
@Data
@Component
@ConfigurationProperties(prefix = "screen.audit")
public class AuditConfiguration {

    /**
     * Exclusion path list.
     */
    private List<String> exclusionPathList = new ArrayList<>();

}
