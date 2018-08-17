/**
 * @(#)UrlPathConfig.java
 *
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * Configuration context path for URL application.
 * 
 */
@Component
@Data
public class UrlPathConfig {

    /** URL separator. */
    private static final String URL_SEPARATOR = "/";

    /** Platform name full property. */
    @Value("${platform.name.full}")
    private String platformNameFull;

    /** Platform version property. */
    @Value("${platform.version}")
    private String platformVersion;

    /** Brand code property. */
    private String brandCode;

    /** Region code property. */
    private String regionCode;

    /** Function type property */
    private String functionType = "screen";

    /**
     * Build context path for URL.
     * 
     * @return Context URL.
     */
    public String getContextPath() {
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(URL_SEPARATOR);
        baseUrl.append(platformNameFull);
        baseUrl.append(URL_SEPARATOR);
        baseUrl.append(platformVersion);
        if (brandCode != null) {
            baseUrl.append(URL_SEPARATOR);
            baseUrl.append(brandCode);
        }

        if (regionCode != null) {
            baseUrl.append(URL_SEPARATOR);
            baseUrl.append(regionCode);
        }

        if (functionType != null) {
            baseUrl.append(URL_SEPARATOR);
            baseUrl.append(functionType);
        }
        return baseUrl.toString();
    }
}
