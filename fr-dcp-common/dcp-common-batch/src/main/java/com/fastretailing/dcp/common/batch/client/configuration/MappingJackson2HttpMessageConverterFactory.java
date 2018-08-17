/**
 * @(#)MappingJackson2HttpMessageConverterFactory.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.batch.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fastretailing.dcp.common.timezone.DateTimeAndTimeModuleFactory;

/**
 * MappingJackson2HttpMessageConverter Factory.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
public class MappingJackson2HttpMessageConverterFactory {

    /**
     * Add the module for LocalDateTime and LocalTime.
     * @return MappingJackson2HttpMessageConverter instance
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();

        // set json field type(snake case)
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        // generate json without null field
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // register LocalDateTime/LocalTime's simple module
        objectMapper.registerModule(DateTimeAndTimeModuleFactory.createDateTimeSimpleModule());
        objectMapper.registerModule(DateTimeAndTimeModuleFactory.createTimeSimpleModule());

        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
