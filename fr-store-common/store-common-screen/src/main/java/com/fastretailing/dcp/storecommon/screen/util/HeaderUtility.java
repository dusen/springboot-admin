/**
 * @(#)HeaderUtility.java
 *
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.util;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import com.fastretailing.dcp.storecommon.screen.security.matcher.HttpStrictTransportSecurityNoCheckMatcher;

/**
 * Header utility class.
 */
public class HeaderUtility {

    /**
     * Sets security HTTP response headers.
     * 
     * @param headers headers configurer.
     */
    public static void setSecureHeaders(HeadersConfigurer<HttpSecurity> headers) {

        headers.defaultsDisabled();

        headers.contentSecurityPolicy(
                "default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline';");
        headers.httpStrictTransportSecurity()
                .requestMatcher(new HttpStrictTransportSecurityNoCheckMatcher())
                .maxAgeInSeconds(31536000)
                .includeSubDomains(false);
        headers.contentTypeOptions();
        headers.xssProtection().block(true);

    }

}
