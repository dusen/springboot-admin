/**
 * @(#)PropertiesItemBean.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate.dto;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties item bean class.
 */
@Component
@ConfigurationProperties("default")
public class PropertiesItemBean {

    /** Properties. */
    private Map<String, Map<String, String>> properties;

    /**
     * Properties getter.
     * 
     * @return Properties value.
     */
    public Map<String, Map<String, String>> getProperties() {
        return properties;
    }

    /**
     * Properties setter.
     * 
     * @param properties Properties value.
     */
    public void setProperties(Map<String, Map<String, String>> properties) {
        this.properties = properties;
    }

    /** Bulk count. */
    private Integer bulkCount;

    /**
     * Bulk count getter.
     * 
     * @return Bulk count value.
     */
    public Integer getBulkCount() {
        return bulkCount;
    }

    /**
     * Properties setter.
     * 
     * @param bulkCount Bulk count value.
     */
    public void setBulkCount(Integer bulkCount) {
        this.bulkCount = bulkCount;
    }

}
