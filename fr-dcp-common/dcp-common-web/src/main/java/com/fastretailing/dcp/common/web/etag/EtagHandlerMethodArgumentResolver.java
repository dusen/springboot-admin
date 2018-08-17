/**
 * @(#)EtagHandlerMethodArgumentResolver.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.etag;

import com.fastretailing.dcp.common.api.etag.Etag;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * <pre>
 *     Resolver for the argument of controller's Etag.<br>
 *     This resolver will extract header(If-Match) from request.<br>
 *     And set it to controller's Etag parameter.<br>
 *     Etag will be used for optimistic lock in business logic source.
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
public class EtagHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * check supported parameter type.
     * @param methodParameter parameter type
     * @return supported?
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(Etag.class);
    }

    /**
     * Create Etag instance. <br>
     * This method will get the header(If-Match)'s value from request and set it to Etag.
     * @param methodParameter methodParameter
     * @param mavContainer mavContainer
     * @param webRequest webRequest
     * @param binderFactory binderFactory
     * @return Etag instance.
     * @throws Exception Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        String etag = webRequest.getHeader("If-Match");

        // When Etag not be set, return null. Else, construct it with Etag header then return it.
        if (StringUtils.isEmpty(etag)) {
            return null;
        } else {
            return new Etag(etag);
        }
    }

}