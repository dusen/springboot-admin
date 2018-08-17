/**
 * @(#)JunitDataSourceConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.datasource;

import com.fastretailing.dcp.common.api.datasource.configuration.DataSourcePropertiesHolder;
import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DataSource's configuration.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "oms.datasource")
@Data
public class JunitDataSourceConfiguration {

    /**
     * The key for the Master datasource.<br>
     */
    public static final String DATASOURCE_TYPE_WRITE = "master";

    /**
     * The key for the ReadOnly datasource.<br>
     */
    public static final String DATASOURCE_TYPE_READONLY = "readOnly";

    /**
     * The separator for the datasource's type and name.<br>
     */
    public static final String DATASOURCE_TYPE_SEPARATOR = "-";

    /**
     * Default datasource's name.<br>
     */
    private String defaultDataSourceName;

    /**
     * Datasource's storage.<br>
     */
    private List<DataSourcePropertiesHolder> dataSources;


    /**
     * Build datasource's resolver with the configuration.<br>
     * @return datasource's resolver
     */
    @Bean
    public Map<String, DataSource> junitDataSource() {

        Map<String, DataSource> map = new HashMap<>();
        dataSources.stream().forEach(
                holder -> holder.getProperties().forEach(
                    (type, prop) -> {
                        DataSource ds = DataSourceBuilder.create()
                                .driverClassName(prop.getDriverClassName())
                                .url(prop.getUrl())
                                .username(prop.getUsername())
                                .password(prop.getPassword())
                                .build();
                        map.put(getDataSourceName(holder.getDataSourceName(), type), ds);
                    }
                )
        );
        return map;
    }

    /**
     * Get the datasource's key's name.<br>
     * Datasource's key's name = datasource's name + "-" + datasource's type<br>
     * For Example :<br>
     *     db1-readOnly<br>
     *     db2-master<br>
     * @param dataSourceName datasource's name
     * @param dataSourceType datasource's type
     * @return datasource's key's name
     */
    public static String getDataSourceName(
            final String dataSourceName,
            final String dataSourceType
    ) {
        return String.join(
                JunitDataSourceConfiguration.DATASOURCE_TYPE_SEPARATOR,
                dataSourceName,
                dataSourceType
        );
    }

    /**
     * Get the default datasource's name.<br>
     * @return default datasource's name
     */
    public String getDefaultDataSourceName() {
        return this.defaultDataSourceName;
    }

}
