/**
 * @(#)OmsRestTemplateConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.client;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.client.configuration.RestTemplateConfigurationProperties;
import com.fastretailing.dcp.common.client.handler.RestTemplateErrorHandler;
import com.fastretailing.dcp.common.client.interceptor.RestTemplateInterceptor;
import com.fastretailing.dcp.common.hmac.interceptor.ClientRestTemplateHmacAuthenticationInterceptor;
import com.fastretailing.dcp.common.timezone.DateTimeAndTimeModuleFactory;

/**
 * RestTemplate's configuration.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class OmsRestTemplateConfiguration {

    /**
     * RestTemplate's configuration bean.<br>
     */
    @Autowired
    protected RestTemplateConfigurationProperties config;

    /**
     * Logger Interceptor.<br>
     */
    @Autowired
    protected RestTemplateInterceptor loggerInterceptor;

    /**
     * Hmac authentication Interceptor.<br>
     */
    @Autowired
    protected ClientRestTemplateHmacAuthenticationInterceptor hmacInterceptor;

    /**
     * ErrorHandler.<br>
     */
    @Autowired
    protected RestTemplateErrorHandler errorHandler;

    /**
     * Proxy HttpClient.<br>
     */
    @Autowired(required = false)
    @Qualifier("proxyHttpClient")
    protected HttpClient proxyHttpClient;

    /**
     * MappingJackson2HttpMessageConverter.<br>
     */
    @Autowired
    protected MappingJackson2HttpMessageConverter converter;

    /**
     * Create the Standard RestTemplate instance with customize
     * LocalDateTime Serializer/Deserializer.<br>
     * Add LocalDateTime Serializer/Deserializer to Jackson's message converter.<br>
     *
     * @return RestTemplate instance
     */
    @Bean("StandardRestTemplate")
    public RestTemplate standardRestTemplate() {

        RestTemplate template = createStandardRestTemplate();

        // create a jackson's objectMapper with customize LocalDateTime/LocalTime simple module
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(DateTimeAndTimeModuleFactory.createDateTimeSimpleModule());
        objectMapper.registerModule(DateTimeAndTimeModuleFactory.createTimeSimpleModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        List<HttpMessageConverter<?>> messageConverters =
                filterOutDefaultJacksonMessageConverter(template);
        messageConverters.add(new MappingJackson2HttpMessageConverter(objectMapper));

        template.setMessageConverters(messageConverters);

        return template;
    }

    /**
     * Create the Customize RestTemplate instance.<br>
     * This restTemplate use dcp customize converter.
     * <code>MappingJackson2HttpMessageConverter</code>.
     *
     * @see OmsRestTemplateConfiguration#standardRestTemplate()
     * @return RestTemplate instance
     */
    @Bean("DcpRestTemplate")
    @Primary
    public RestTemplate restTemplate() {

        RestTemplate template = standardRestTemplate();

        List<HttpMessageConverter<?>> messageConverters =
                filterOutDefaultJacksonMessageConverter(template);

        // use the dcp converter as default.
        messageConverters.add(converter);

        template.setMessageConverters(messageConverters);

        return template;
    }

    /**
     * Init the HttpRequestFactory.<br>
     *
     * @return HttpRequestFactory
     */
    protected HttpComponentsClientHttpRequestFactory initRequestFactory() {

        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory();

        factory.setConnectTimeout(config.getConnectTimeout());
        factory.setReadTimeout(config.getReadTimeout());
        if (proxyHttpClient != null) {
            factory.setHttpClient(proxyHttpClient);
        }
        return factory;
    }

    /**
     * filter out default Jackson Message Converter from standard RestTemplate.<br>
     * @param restTemplate restTemplate
     * @return filter out result
     */
    protected List<HttpMessageConverter<?>>
            filterOutDefaultJacksonMessageConverter(RestTemplate restTemplate) {
        return restTemplate.getMessageConverters().stream()
                .filter(converter -> !(converter instanceof MappingJackson2HttpMessageConverter))
                .collect(Collectors.toList());
    }

    /**
     * Create a standard RestTemplate instance.<br>
     * The generated instance contains interceptors and errorHandler as below.
     * <ul>
     * <li>RestTemplateInterceptor</li>
     * <li>ClientRestTemplateHmacAuthenticationInterceptor</li>
     * <li>RestTemplateErrorHandler</li>
     * </ul>
     *
     * @return RestTemplate instance
     */
    protected RestTemplate createStandardRestTemplate() {

        RestTemplate template = new RestTemplate(this.initRequestFactory());
        template.getInterceptors().add(loggerInterceptor);
        template.getInterceptors().add(hmacInterceptor);
        template.setErrorHandler(errorHandler);

        return template;
    }
}
