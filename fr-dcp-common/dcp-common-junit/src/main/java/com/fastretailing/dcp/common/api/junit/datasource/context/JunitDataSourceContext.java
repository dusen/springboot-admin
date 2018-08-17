/**
 * @(#)DataSourceContext.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.datasource.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datasource's context bean.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JunitDataSourceContext {

    /**
     * Datasource's name.<br>
     */
    private String dataSourceName;

    /**
     * Datasource's type.<br>
     */
    private String dataSourceType;
}
