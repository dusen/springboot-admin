/**
 * @(#)DevelopmentWebSecurityConfiguration.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.web.filter.GenericFilterBean;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.config.DevelopmentConfiguration;
import com.fastretailing.dcp.storecommon.screen.util.HeaderUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring Security configuration for development.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(20)
@Slf4j
public class DevelopmentWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /** Version path for health check. */
    private static final String VERSION_PATH = "/version";

    /** Configuration for development. */
    @Autowired
    private DevelopmentConfiguration developmentConfiguration;

    /**
     * Defines the development mode security configuration.
     * 
     * When the development filter is valid, the authentication information of the value defined in
     * the setting file is generated.
     * 
     * When the development filter is invalid, it functions as a substantially invalid
     * SpringSecurity setting by disguising as the setting same as the setting for health check
     * endpoint.
     * 
     * @param http It allows configuring web based security for specific HTTP requests.
     * @throws Exception If an error occurs.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        HeaderUtility.setSecureHeaders(http.headers());
        if (developmentConfiguration.isDevelopModeEnabled()) {
            // Settings for development.
            log.info("[CAUTION] Authentication is running in developer mode.");
            http.addFilterAfter(new DevelopmentUserPrincipalGenerationFilter(),
                    ConcurrentSessionFilter.class);
            http.authorizeRequests().anyRequest().permitAll();
        } else {
            // Settings for health check endpoint.
            http.antMatcher(VERSION_PATH).authorizeRequests().antMatchers(VERSION_PATH).permitAll();
        }
    }

    /**
     * Filter that generates information for development.
     */
    private class DevelopmentUserPrincipalGenerationFilter extends GenericFilterBean {

        /** Dummy password. */
        private static final String DUMMY_PASSWORD = "<dummy!develop>";

        /**
         * Generates an authentication token for development, and calls the subsequent processing.
         * 
         * @param request The request to process.
         * @param response The response associated with the request.
         * @param filterChain Provides access to the next filter in the chain for this filter to
         *        pass the request and response to for further processing.
         *
         * @throws IOException If an I/O error occurs during this filter's processing of the
         *         request.
         * @throws ServletException If the processing fails for any other reason.
         */
        @Override
        public void doFilter(ServletRequest request, ServletResponse response,
                FilterChain filterChain) throws IOException, ServletException {

            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication nowAuthentication = securityContext.getAuthentication();
            if (nowAuthentication == null
                    || !UserDetails.class.isInstance(nowAuthentication.getPrincipal())) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        createUserDetails(), DUMMY_PASSWORD, createAuthorityList());
                securityContext.setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }

        /**
         * Creates user details for authentication principal.
         * 
         * @return user details.
         */
        private UserDetails createUserDetails() {
            return new UserDetails(developmentConfiguration.getStoreCode(),
                    developmentConfiguration.getUserId(), DUMMY_PASSWORD, true, true, true, true,
                    createAuthorityList());

        }

        /**
         * Creates authority list.
         * 
         * @return authority list.
         */
        private List<GrantedAuthority> createAuthorityList() {
            List<String> developmentRoles = developmentConfiguration.getRoles();
            if (developmentRoles == null) {
                return Collections.emptyList();
            }
            return developmentRoles.stream().map(SimpleGrantedAuthority::new).collect(
                    Collectors.toList());
        }

    }
}
