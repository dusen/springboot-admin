/**
 * @(#)DataSourceContextHolder.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.datasource.context;

/**
 * Datasource's context holder class.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class DataSourceContextHolder {

    /**
     * The Thread-Safe storage for the datasource's context.<br>
     */
    private static final ThreadLocal<DataSourceContext> contextHolder = new ThreadLocal<>();

    /**
     * Set the datasource's context.<br>
     * @param context datasource's context
     */
    public static void setDataSourceContext(DataSourceContext context) {
        contextHolder.set(context);
    }

    /**
     * Get the datasource's context.<br>
     * @return datasource's context
     */
    public static DataSourceContext getDataSourceContext() {
        return contextHolder.get();
    }

    /**
     * Clean the datasource's context.<br>
     */
    public static void cleanDataSourceContext() {
        contextHolder.remove();
    }
}
