/**
 * @(#)HttpStrictTransportSecurityNoCheckMatcher.java
 *
 *                                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.security.matcher;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * No check matcher for Strict-Transport-Security header of Spring Security.
 */
public class HttpStrictTransportSecurityNoCheckMatcher implements RequestMatcher {

    /**
     * Be sure to match.
     * 
     * Spring Security framework is designed to attach Strict-Transport-Security header only for
     * HTTPS. However, when converting protocols with ALB etc., this matcher is necessary because
     * the application is HTTP.
     * 
     * @param request HTTP request.
     * @return always returns true.
     */
    public boolean matches(HttpServletRequest request) {
        return true;
    }

}
