/**
 * @(#)EtagResponseAdvice.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.etag;

import com.fastretailing.dcp.common.api.etag.Etag;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fastretailing.dcp.common.api.etag.EtagWrapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * <pre>
 *     When the returned object is {@link EtagWrapper},<br>
 *     This class will extract Etag from return object.<br>
 *     And set it to response header.
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
@ControllerAdvice
public class EtagResponseAdvice implements ResponseBodyAdvice<Object> {


    /**
     * Only {@link EtagWrapper} be supported.
     * @param returnType return value
     * @param converterType converterType
     * @return supported?
     */
    @Override
    public boolean supports(MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.getParameterType().equals(EtagWrapper.class)) {
            return true;
        } else if (returnType.getParameterType().equals(ResponseEntity.class)) {
            Type type = returnType.getGenericParameterType();
            if (type instanceof ParameterizedType) {
                return ((ParameterizedType) type).getActualTypeArguments()[0]
                        .equals(EtagWrapper.class);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Extract Etag from return object and set it to response header.
     * @param body Controller's return object.
     * @param returnType returnType
     * @param selectedContentType selectedContentType
     * @param selectedConverterType selectedConverterType
     * @param request request
     * @param response response
     * @return the real return object without etag.
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {
        EtagWrapper returnTypeWithEtag = (EtagWrapper) body;
        // Add etag to response header
        response.getHeaders().add("Etag",
                Optional.ofNullable(returnTypeWithEtag.getEtag()).orElse(new Etag()).toString());

        // Return the real response object.
        return returnTypeWithEtag.getEntity();
    }
}
