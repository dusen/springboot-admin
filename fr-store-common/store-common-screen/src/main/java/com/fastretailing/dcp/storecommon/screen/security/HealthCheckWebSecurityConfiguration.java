/**
 * @(#)HealthCheckWebSecurityConfiguration.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.fastretailing.dcp.storecommon.screen.util.HeaderUtility;

/**
 * Spring Security configuration for health check.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(10)
public class HealthCheckWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /** Version path for health check. */
    private static final String VERSION_PATH = "/version";

    /**
     * Defines the web based security configuration. The health check endpoint does not perform
     * authentication.
     * 
     * @param http It allows configuring web based security for specific HTTP requests.
     * @throws Exception If an error occurs.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        HeaderUtility.setSecureHeaders(http.headers());
        http.antMatcher(VERSION_PATH).authorizeRequests().antMatchers(VERSION_PATH).permitAll();
    }

}
