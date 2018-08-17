/**
 * @(#)DozerConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.dozer.configuration;

import org.apache.commons.lang3.ArrayUtils;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * The configuration for dozer.
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Configuration
public class DozerConfiguration {

    @Autowired
    private ApplicationContext context;

    /**
     * This factory class create dozer bean mapper for business logic.
     * @return dozer bean factory
     * @throws Exception any exception
     */
    @Bean
    public DozerBeanMapperFactoryBean createDozerBeanFactory() throws Exception {

        DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();
        Resource[] resources = ArrayUtils.addAll(
                context.getResources("classpath*:/META-INF/dozer/**/*-mapping.xml"),
                context.getResources("classpath*:/dozer/**/*-mapping.xml")
        );
        mapper.setMappingFiles(resources);

        return mapper;
    }
}
