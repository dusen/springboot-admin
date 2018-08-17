/**
 * @(#)MappingJackson2HttpMessageConverterFactoryTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.batch.client.configuration;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class MappingJackson2HttpMessageConverterFactoryTest {

    @Test
    public void testMappingJackson2HttpMessageConverter01(){
        MappingJackson2HttpMessageConverterFactory factory =
                new MappingJackson2HttpMessageConverterFactory();

        MappingJackson2HttpMessageConverter actual = factory.mappingJackson2HttpMessageConverter();

        // assert
        MatcherAssert.assertThat(actual.getObjectMapper().getPropertyNamingStrategy(),
                CoreMatchers.is(PropertyNamingStrategy.SNAKE_CASE));
        MatcherAssert.assertThat(actual.getObjectMapper().getSerializationConfig()
                        .getDefaultPropertyInclusion().getValueInclusion(),
                CoreMatchers.is(JsonInclude.Include.NON_NULL));
    }
}
