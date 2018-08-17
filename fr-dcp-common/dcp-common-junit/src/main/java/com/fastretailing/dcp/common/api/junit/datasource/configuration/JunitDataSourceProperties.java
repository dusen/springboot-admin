/**
 * @(#)DataSourceProperties.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.datasource.configuration;

import lombok.Data;

/**
 * DataSource's configuration bean class.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class JunitDataSourceProperties {

    /**
     * DataSource's user name.<br>
     */
    private String username;

    /**
     * DataSource's password.<br>
     */
    private String password;

    /**
     * DataSource's url.<br>
     */
    private String url;

    /**
     * DataSource's driver's name.<br>
     */
    private String driverClassName;
}
