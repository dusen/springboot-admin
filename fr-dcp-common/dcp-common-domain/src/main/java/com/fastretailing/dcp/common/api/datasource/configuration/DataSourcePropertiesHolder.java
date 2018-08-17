/**
 * @(#)DataSourcePropertiesHolder.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.datasource.configuration;

import lombok.Data;

import java.util.Map;

/**
 * Datasource's configuration 's holder.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class DataSourcePropertiesHolder {

    /**
     * Datasource's name.<br>
     */
    private String dataSourceName;

    /**
     * Datasource's map.<br>
     */
    private Map<String, DataSourceProperties> properties;
}
