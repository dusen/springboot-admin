/**
 * @(#)CommonApiResonseHeaderFilter.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.api.interceptor;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * All store-common requests will walk through this filter. Response header will be edited.
 *
 */
@Component
public class CommonApiResonseHeaderFilter implements Filter {

    /** HTTP security header name. */
    private static final String HTTP_SECURITY_HEADER_NAME = "Content-Security-Policy";

    /** HTTP security header value. */
    private static final String HTTP_SECURITY_HEADER_VALUE = "default-src 'self'";


    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {}

    /**
     * Do request's filter.This method was used to edit response header.
     * 
     * @param request Request.
     * @param response Response.
     * @param filterChain Filter chain.
     * @throws ServletException Servlet exception.
     * @throws IOException IO exception.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader(HTTP_SECURITY_HEADER_NAME, HTTP_SECURITY_HEADER_VALUE);

        filterChain.doFilter(servletRequest, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

}
