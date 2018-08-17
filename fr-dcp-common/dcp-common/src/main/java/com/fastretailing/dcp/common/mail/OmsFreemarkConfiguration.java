/**
 * @(#)OmsFreemarkConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.mail;

import com.fastretailing.dcp.common.mail.loader.DatabaseTemplateLoader;
import freemarker.template.Configuration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * Database MailTemplate's configuration.
 */
@Component
@MapperScan(basePackages = "com.fastretailing.dcp.common.mail.repository")
public class OmsFreemarkConfiguration {

    /**
     * DataSourceTemplate's load instance.<br>
     */
    @Autowired
    private DatabaseTemplateLoader loader;

    /**
     * Init the DataSourceTemplate's configuration.
     * @return DataSourceTemplate's configuration.
     * @throws Exception Exception
     */
    @Bean
    public FreeMarkerConfigurer initConfiguration() throws Exception {
        FreeMarkerConfigurer factory = new FreeMarkerConfigurer();
        Configuration configuration = factory.createConfiguration();
        configuration.setTemplateLoader(loader);
        factory.setConfiguration(configuration);
        return factory;
    }

}
