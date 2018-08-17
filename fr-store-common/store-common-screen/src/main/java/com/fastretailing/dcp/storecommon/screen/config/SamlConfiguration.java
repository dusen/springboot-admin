/**
 * @(#)SamlConfiguration.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * Configuration for SAML authentication.
 */
@Data
@Component
@ConfigurationProperties(prefix = "screen.authentication.saml")
public class SamlConfiguration {

    /**
     * Server name of identity provider.
     */
    private String idpServerName;

    /**
     * Metadata file path of identity provider.
     */
    private String idpMetadataPath;

    /**
     * Schema of service provider.
     * 
     * Set information on the public endpoint from the viewpoint of the IdP server.
     */
    private String spSchema;

    /**
     * Server name of service provider.
     * 
     * Set information on the public endpoint from the viewpoint of the IdP server.
     */
    private String spServerName;

    /**
     * HTTPS port of service provider.
     * 
     * Set information on the public endpoint from the viewpoint of the IdP server.
     */
    private Integer spHttpsPort;

    /**
     * Context path of service provider.
     * 
     * Set information on the public endpoint from the viewpoint of the IdP server.
     */
    private String spContextPath;

    /**
     * Entity base URL of service provider.
     * 
     * Entity base URL is the path obtained by concatenating schema, host name, port, and context
     * path.
     */
    private String entityBaseUrl;

    /**
     * Entity ID of service provider.
     * 
     * Entity ID is a string that uniquely identifies the service provider.
     */
    private String entityId;

    /**
     * Where to redirect user on successful login if no saved request is found in the session.
     * 
     * This is the default URL of the redirect destination when the redirect destination can not be
     * decided on the login URL map.
     */
    private String successLoginDefaultUrl;

    /**
     * Where to redirect user on successful login if no saved request is found in the session.
     * 
     * It compares with the trailing path of the requested URL before authentication, and redirects
     * to the defined URL if it matches.
     */
    private Map<String, String> loginUrlMap = new HashMap<>();

    /**
     * Where to redirect user on successful logout.
     */
    private String successLogoutUrl;

    /**
     * Where to redirect user on failed login. This value is set to null, which returns 401 error
     * code on failed login. But, in theory, this will never be used because IdP will handled the
     * failed login on IdP login page.
     */
    private String failedLoginDefaultUrl;

    /**
     * Max assertion time between assertion creation and it's usability. Default to 3000 seconds.
     */
    private int maxAssertionTime = 3000;

    /**
     * Sets maximum difference between local time and time of the assertion creation which still
     * allows message to be processed. Basically determines maximum difference between clocks of the
     * identity provider and service provider machines. Defaults to 60 seconds.
     */
    private int responseSkew = 60;

    /**
     * Maximum time between users authentication and processing of an authentication statement.
     * Defaults to 7200 seconds.
     */
    private int maxAuthenticationAge = 7200;

    /**
     * Path to grant permission without authorization check.
     */
    private String[] permitAllPaths = new String[0];

}
