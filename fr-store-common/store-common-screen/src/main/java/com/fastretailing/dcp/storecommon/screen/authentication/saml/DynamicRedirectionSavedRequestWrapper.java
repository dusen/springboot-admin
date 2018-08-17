/**
 * @(#)DynamicRedirectionSavedRequestWrapper.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.saml;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.Cookie;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.screen.authentication.AuthenticationUtil;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.util.StoreRequestPathVariableHolder;
import com.fastretailing.dcp.storecommon.screen.util.UriInformation;
import com.fastretailing.dcp.storecommon.screen.util.UriUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * Saved request wrapper for dynamic redirection.
 * 
 * The basic processing is left to {@link SavedRequest}, but only the redirect destination URL is
 * changed dynamically.
 */
@Slf4j
public class DynamicRedirectionSavedRequestWrapper implements SavedRequest {

    /** Serial version UID. */
    private static final long serialVersionUID = 3691486298229519932L;

    /** Replace key for store code. */
    private static final String STORE_CODE_REPLACE_KEY = "@@StoreCode@@";

    /** Replace key for brand. */
    private static final String BRAND_REPLACE_KEY = "@@Brand@@";

    /** Replace key for region. */
    private static final String REGION_REPLACE_KEY = "@@Region@@";

    /** Query key for store code. */
    private static final String QUERY_KEY_STORE_CODE = "store_code";

    /** Saved request. */
    private SavedRequest savedRequest;

    /** Login URL mapping information. */
    private Map<String, String> loginUrlMap;

    /** Default login URL. */
    private String loginDefaultUrl;

    /**
     * Constructors saved request wrapper for dynamic redirection.
     * 
     * @param savedRequest the saved request.
     * @param loginUrlMap the login URL mapping information.
     * @param loginDefaultUrl the default login URL.
     */
    public DynamicRedirectionSavedRequestWrapper(SavedRequest savedRequest,
            Map<String, String> loginUrlMap, String loginDefaultUrl) {
        this.savedRequest = savedRequest;
        this.loginUrlMap = loginUrlMap;
        this.loginDefaultUrl = loginDefaultUrl;
    }

    /**
     * Returns the redirecting URL.
     * 
     * Based on the path at the end of the URL in {@link SavedRequest}, decide the URL to be
     * redirected by using the login URL mapping information. If it does not exist on the mapping
     * information, returns the default login URL. Also keeps the query parameters.
     * 
     * @return The redirecting URL.
     */
    @Override
    public String getRedirectUrl() {

        // The URL from which the query parameter was removed.
        UriInformation uriInformation = UriUtility.getUriInformation(savedRequest.getRedirectUrl());
        String originalUrl = uriInformation.getBasePath();

        String lastPath =
                originalUrl.substring(originalUrl.lastIndexOf(UriUtility.URI_PATH_SEPARATOR));
        log.debug("Last path in original URL={}", lastPath);

        String redirectUrl = loginUrlMap.getOrDefault(lastPath, loginDefaultUrl);
        log.debug("Mapping redirect URL={}", redirectUrl);

        // Store code.
        String storeCode = getStoreCode(savedRequest);
        redirectUrl = replace(redirectUrl, STORE_CODE_REPLACE_KEY, storeCode);
        UserDetails userDetails = AuthenticationUtil.getUserDetails();
        if (userDetails != null) {
            userDetails.setStoreCode(storeCode);
        }

        // Brand.
        redirectUrl = replace(redirectUrl, BRAND_REPLACE_KEY, RequestPathVariableHolder.getBrand());
        // Region.
        redirectUrl =
                replace(redirectUrl, REGION_REPLACE_KEY, RequestPathVariableHolder.getRegion());

        // Append query parameters.
        String queryParameters = uriInformation.getQuery();
        if (queryParameters != null) {
            redirectUrl = redirectUrl + UriUtility.QUERY_PARAMETERS_SEPARATOR + queryParameters;
        }

        log.debug("Returned redirect URL={}", redirectUrl);

        return redirectUrl;

    }

    /**
     * Return the store code obtained from the query parameter.
     * 
     * @param savedRequest saved request.
     * @return the store code obtained from the query parameter.
     */
    private String getStoreCode(SavedRequest savedRequest) {
        String[] storeCodes = savedRequest.getParameterValues(QUERY_KEY_STORE_CODE);
        if (storeCodes == null) {
            log.debug("The store code was not passed from the portal.");
            return StoreRequestPathVariableHolder.getStoreCode();
        } else if (storeCodes.length == 1) {
            return storeCodes[0];
        } else {
            throw new SystemException(
                    "Multiple store codes were passed from the portal. StoreCodes="
                            + String.join(",", storeCodes));
        }
    }

    /**
     * Replace the placeholder key with the value. However, if a placeholder is included and the
     * replacement value is null or empty, it throws a SystemException.
     * 
     * @param originalString original string to be replaced.
     * @param key placeholder key.
     * @param value value to replace.
     * @return the replacement string.
     */
    private String replace(String originalString, String key, String value) {
        if (originalString.contains(key)) {
            if (StringUtils.isEmpty(value)) {
                throw new SystemException(
                        "The value to be replaced is null or empty character. originalString="
                                + originalString + " / key=" + key + " / value=" + value);
            }
            return originalString.replace(key, value);
        } else {
            return originalString;
        }
    }

    /**
     * Returns saved cookies.
     * 
     * @return the saved cookies.
     * @see DefaultSavedRequest#getCookies()
     */
    @Override
    public List<Cookie> getCookies() {
        return savedRequest.getCookies();
    }

    /**
     * Returns saved method.
     * 
     * @return the saved method.
     * @see DefaultSavedRequest#getMethod()
     */
    @Override
    public String getMethod() {
        return savedRequest.getMethod();
    }

    /**
     * Returns saved header values.
     * 
     * @param name the header name.
     * @return the saved header values.
     * @see DefaultSavedRequest#getHeaderValues(String)
     */
    @Override
    public List<String> getHeaderValues(String name) {
        return savedRequest.getHeaderValues(name);
    }

    /**
     * Returns saved header names.
     * 
     * @return the saved header names.
     * @see DefaultSavedRequest#getHeaderNames()
     */
    @Override
    public Collection<String> getHeaderNames() {
        return savedRequest.getHeaderNames();
    }

    /**
     * Returns saved locales.
     * 
     * @return the saved locales.
     * @see DefaultSavedRequest#getLocales()
     */
    @Override
    public List<Locale> getLocales() {
        return savedRequest.getLocales();
    }

    /**
     * Returns saved parameter values.
     * 
     * @param name the parameter name.
     * @return the saved parameter values.
     * @see DefaultSavedRequest#getParameterValues(String)
     */
    @Override
    public String[] getParameterValues(String name) {
        return savedRequest.getParameterValues(name);
    }

    /**
     * Returns saved parameter map.
     * 
     * @return the saved parameter map.
     * @see DefaultSavedRequest#getParameterMap()
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        return savedRequest.getParameterMap();
    }

}
