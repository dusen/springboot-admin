/**
 * @(#)DynamicRedirectionHttpSessionRequestCache.java
 *
 *                                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.saml;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code RequestCache} which stores the {@code SavedRequest} in the HttpSession.
 *
 * Wraps the {@link SavedRequest} in the {@link DynamicRedirectionSavedRequestWrapper} and return
 * it.
 */
@Slf4j
public class DynamicRedirectionHttpSessionRequestCache extends HttpSessionRequestCache {

    /** Login URL mapping information. */
    private Map<String, String> loginUrlMap;

    /** Default login URL. */
    private String loginDefaultUrl;

    /**
     * Constructors HTTP session request cache for dynamic redirection.
     * 
     * @param loginUrlMap The login URL mapping information.
     * @param loginDefaultUrl The default login URL.
     */
    public DynamicRedirectionHttpSessionRequestCache(Map<String, String> loginUrlMap,
            String loginDefaultUrl) {
        this.loginUrlMap = loginUrlMap;
        this.loginDefaultUrl = loginDefaultUrl;
    }

    /**
     * Saves request.
     * 
     * @param request HTTP request.
     * @param response HTTP response.
     */
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {

        log.debug("Saved HttpServletRequest={}", request);
        log.debug("Saved getScheme={}", request.getScheme());
        log.debug("Saved getRequestURI={}", request.getRequestURI());
        log.debug("Saved getRequestURL={}", request.getRequestURL());
        log.debug("Saved getRemoteHost={}", request.getRemoteHost());

        super.saveRequest(request, response);

    }

    /**
     * Wraps in the {@link DynamicRedirectionSavedRequestWrapper} and returns the
     * {@link SavedRequest}.
     * 
     * @param currentRequest the current HTTP request.
     * @param response HTTP response(do not use).
     * @return Saved request wrapped in DynamicRedirectionSavedRequestWrapper.
     */
    public SavedRequest getRequest(HttpServletRequest currentRequest,
            HttpServletResponse response) {

        log.debug("Current HttpServletRequest={}", currentRequest);
        log.debug("Current getScheme={}", currentRequest.getScheme());
        log.debug("Current getRequestURI={}", currentRequest.getRequestURI());
        log.debug("Current getRequestURL={}", currentRequest.getRequestURL());
        log.debug("Current getRemoteHost={}", currentRequest.getRemoteHost());

        SavedRequest savedRequest = super.getRequest(currentRequest, response);
        SavedRequest savedRequestWrapper = new DynamicRedirectionSavedRequestWrapper(savedRequest,
                loginUrlMap, loginDefaultUrl);

        log.debug("SavedRequestWrapper={}", savedRequestWrapper);
        log.debug("Saved getRedirectUrl={}", savedRequestWrapper.getRedirectUrl());
        log.debug("Saved getHeaderNames={}", savedRequestWrapper.getHeaderNames());
        log.debug("Saved getParameterMap={}", savedRequestWrapper.getParameterMap());

        return savedRequestWrapper;
    }

}
