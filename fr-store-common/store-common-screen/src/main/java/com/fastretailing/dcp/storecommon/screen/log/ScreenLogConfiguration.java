/**
 * @(#)ScreenLogConfiguration.java
 *
 *                                 Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Screen log configuration.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
public class ScreenLogConfiguration {
    /**
     * Common request logging filter.
     * 
     * @return Common request logging filter.
     */
    @Bean
    public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
        CommonsRequestLoggingFilter commonsRequestLoggingFilter =
                new ScreenRequestResponseLoggingFilter();
        commonsRequestLoggingFilter.setIncludeClientInfo(true);
        commonsRequestLoggingFilter.setIncludeQueryString(true);
        commonsRequestLoggingFilter.setIncludeHeaders(true);
        commonsRequestLoggingFilter.setIncludePayload(false);
        return commonsRequestLoggingFilter;
    }

}
