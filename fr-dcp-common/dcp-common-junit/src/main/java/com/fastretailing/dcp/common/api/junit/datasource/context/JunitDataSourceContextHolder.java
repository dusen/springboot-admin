/**
 * @(#)DataSourceContextHolder.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.datasource.context;

/**
 * Datasource's context holder class.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class JunitDataSourceContextHolder {

    /**
     * The Thread-Safe storage for the datasource's context.<br>
     */
    private static final ThreadLocal<JunitDataSourceContext> contextHolder = new ThreadLocal<>();

    /**
     * Set the datasource's context.<br>
     * @param context datasource's context
     */
    public static void setDataSourceContext(JunitDataSourceContext context) {
        contextHolder.set(context);
    }

    /**
     * Get the datasource's context.<br>
     * @return datasource's context
     */
    public static JunitDataSourceContext getDataSourceContext() {
        return contextHolder.get();
    }

    /**
     * Clean the datasource's context.<br>
     */
    public static void cleanDataSourceContext() {
        contextHolder.remove();
    }
}
