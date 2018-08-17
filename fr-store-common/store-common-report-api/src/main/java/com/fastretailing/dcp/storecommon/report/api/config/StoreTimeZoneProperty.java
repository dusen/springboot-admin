/**
 * @(#)ReportTimeZoneProperty.java
 * 
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * Property for getting time zone id.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
@ConfigurationProperties(prefix = "report.time-zone")
@Data
public class StoreTimeZoneProperty {

    /**
     * Table name.
     */
    private String tableName;

    /**
     * General pupose type.
     */
    private String generalPurposeType;

    /**
     * Condition field.
     */
    private String conditionField;

    /**
     * Target field.
     */
    private String targetField;
}
