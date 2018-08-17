/**
 * @(#)ApiRestTemplateInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.client.interceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.fastretailing.dcp.common.api.client.FilteredHttpHeaderHolder;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.fastretailing.dcp.common.api.log.MdcKeyEnum;
import com.fastretailing.dcp.common.client.customization.CustomizationBufferingClientHttpResponseWrapper;
import com.fastretailing.dcp.common.client.interceptor.RestTemplateInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * Api RestTemplate's Interceptor.<br>
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Slf4j
public class ApiRestTemplateInterceptor extends RestTemplateInterceptor {

    /**
     * Intercept the given request, and return a response.<br>
     * @param request HttpRequest
     * @param body body the body of the request
     * @param execution execution the request execution
     * @return the response
     * @throws IOException IOException
     */
    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {

        Map<String, List<String>> filteredHeader =  FilteredHttpHeaderHolder.get();
        if (MapUtils.isNotEmpty(filteredHeader)) {
            request.getHeaders().putAll(filteredHeader);
        }

        this.logRequest(request, body);

        LocalDateTime start = LocalDateTime.now();
        request.getHeaders().setConnection(HTTP_HEADER_CONNECTION_CLOSE);
        request.getHeaders().set(
                MdcKeyEnum.AMAZON_TRACE_ID.getKey(),
                String.format("Root=%s", MDC.get(MdcKeyEnum.AMAZON_TRACE_ID.getKey()))
        );
        request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        ClientHttpResponse response = execution.execute(request, body);
        CustomizationBufferingClientHttpResponseWrapper resWrapper
                = new CustomizationBufferingClientHttpResponseWrapper(response);
        LocalDateTime end = LocalDateTime.now();

        this.logResponse(resWrapper, start, end);

        return resWrapper;
    }

}
