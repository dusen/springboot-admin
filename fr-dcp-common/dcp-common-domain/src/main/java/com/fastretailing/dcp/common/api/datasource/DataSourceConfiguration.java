/**
 * @(#)DataSourceConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.datasource;

import com.fastretailing.dcp.common.api.datasource.configuration.DataSourcePropertiesHolder;
import com.fastretailing.dcp.common.api.datasource.lookup.RoutingDataSourceResolver;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
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
@EnableTransactionManagement
@ComponentScan("com.fastretailing.dcp.common.api.datasource")
@MapperScan(basePackages = "com.fastretailing.dcp.*.api.*.repository")
@ConfigurationProperties(prefix = "oms.datasource")
@Data
public class DataSourceConfiguration implements TransactionManagementConfigurer {

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
     * DataSource's Query-Validation's SQL.<br>
     */
    private static final String QUERY_VALIDATION_SQL = "SELECT 1";

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
    public RoutingDataSourceResolver dataSource() {

        RoutingDataSourceResolver resolver = new RoutingDataSourceResolver();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSources.stream().forEach(
                holder -> holder.getProperties().forEach(
                    (type, prop) -> {
                        DataSource dataSource = this.createDataSourceBuilder()
                                .driverClassName(prop.getDriverClassName())
                                .url(prop.getUrl())
                                .username(prop.getUsername())
                                .password(prop.getPassword())
                                .build();
                        dataSourceMap.put(
                                getDataSourceName(holder.getDataSourceName(), type),
                                dataSource
                        );
                    }
                )
        );

        resolver.setDefaultTargetDataSource(
                dataSourceMap.get(
                    getDataSourceName(defaultDataSourceName, DATASOURCE_TYPE_WRITE)
                )
        );
        resolver.setTargetDataSources(dataSourceMap);

        return resolver;
    }

    /**
     * Create DataSourceBuilder instance.<br>
     * @return DataSourceBuilder
     */
    private DataSourceBuilder createDataSourceBuilder() {

        DataSourceBuilder builder = DataSourceBuilder.create();

        Map<String, String> configs = new HashMap<>();
        if (StringUtils.equals(
                builder.findType().getTypeName(),
                "com.zaxxer.hikari.HikariDataSource")) {
            configs.put("connection-test-query", QUERY_VALIDATION_SQL);
        } else {
            configs.put("validation-query", QUERY_VALIDATION_SQL);
            configs.put("test-on-borrow", "true");
        }
        Field configField = ReflectionUtils.findField(
                DataSourceBuilder.class, "properties"
        );
        configField.setAccessible(true);
        ReflectionUtils.setField(configField, builder, configs);

        return builder;
    }

    /**
     * Return the default transaction manager bean to use for annotation-driven
     * database transaction management, i.e. when processing {@code @Transactional}
     * methods.
     */
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

    /**
     * Get the datasource's key's name.<br>
     * Datasource's key's name = datasource's name + "-" + datasource's type.<br>
     * For Example :<br>
     *     db1-readOnly<br>
     *     db2-master<br>
     * @param dataSourceName datasource's name
     * @param dataSourceType datasource's type
     * @return datasource's key's name
     */
    public static String getDataSourceName(
            final String dataSourceName,
            final String dataSourceType) {
        return String.join(
            DataSourceConfiguration.DATASOURCE_TYPE_SEPARATOR,
            dataSourceName,
            dataSourceType
        );
    }

}
