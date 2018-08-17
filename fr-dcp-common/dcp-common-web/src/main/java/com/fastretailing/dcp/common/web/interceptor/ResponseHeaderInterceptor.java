/**
 * @(#)ResponseHeaderInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.interceptor;

import com.fastretailing.dcp.common.api.log.MdcKeyEnum;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ResponseHeaderInterceptor.
 * The ResponseHeaderInterceptor uniform treatment response headers because of
 * in the OMS base framework, all Response's Content-Type were processed as "application/json".
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class ResponseHeaderInterceptor extends HandlerInterceptorAdapter {

    /**
     * Process the response's header.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Handler
     * @param modelAndView ModelAndView
     * @throws Exception Exception
     */
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView
    ) throws Exception {

        if (response instanceof ContentCachingResponseWrapper
                && ((ContentCachingResponseWrapper) response).getContentSize() > 0) {
            response.setHeader(HttpHeaders.CONTENT_LANGUAGE, "en-US");
        }

        response.setHeader(
                MdcKeyEnum.AMAZON_TRACE_ID.getKey(),
                String.format("Root=%s", MDC.get(MdcKeyEnum.AMAZON_TRACE_ID.getKey()))
        );

    }

}
