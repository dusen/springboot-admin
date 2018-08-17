/**
 * @(#)RoutingDataSourceResolver.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.datasource.lookup;

import com.fastretailing.dcp.common.api.datasource.context.DataSourceContextHolder;
import com.fastretailing.dcp.common.api.datasource.DataSourceConfiguration;
import com.fastretailing.dcp.common.api.datasource.context.DataSourceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

/**
 * The routes calls to one of various target DataSources based on a lookup key.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class RoutingDataSourceResolver extends AbstractRoutingDataSource {

    /**
     * Default datasource's name.<br>
     */
    @Value("${oms.datasource.defaultDataSourceName}")
    private String defaultDataSourceName;

    /**
     * Determine the current lookup key. <br>
     * This will typically be implemented to check a thread-bound transaction context.<br>
     * @return key
     */
    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceContext context = DataSourceContextHolder.getDataSourceContext();
        if (context == null || StringUtils.isEmpty(context.getDataSourceName())) {
            return DataSourceConfiguration.getDataSourceName(
                    defaultDataSourceName,
                    DataSourceConfiguration.DATASOURCE_TYPE_WRITE
            );
        } else {
            return DataSourceConfiguration.getDataSourceName(
                    context.getDataSourceName(),
                    context.getDataSourceType()
            );
        }
    }
}
