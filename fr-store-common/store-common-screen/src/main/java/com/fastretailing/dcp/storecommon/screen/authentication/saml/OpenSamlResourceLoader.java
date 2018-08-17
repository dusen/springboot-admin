/**
 * @(#)OpenSamlResourceLoader.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.saml;

import java.net.URI;
import java.net.URISyntaxException;
import org.opensaml.util.resource.ClasspathResource;
import org.opensaml.util.resource.FilesystemResource;
import org.opensaml.util.resource.Resource;
import org.opensaml.util.resource.ResourceException;
import org.springframework.util.Assert;

/**
 * Resource loader for Open SAML.
 */
public class OpenSamlResourceLoader {

    /** Pseudo URL prefix for loading from the class path: "classpath:". */
    private static String CLASSPATH_URL_PREFIX = "classpath:";

    /** Relative path prefix for loading from the file path. */
    private static String RELATIVE_PATH_PREFIX = "/";

    /**
     * Returns resource appropriate for location notation.
     * 
     * @param location the resource location.
     * @return Resource appropriate for location notation.
     * @throws ResourceException Thrown if the resource path is empty or if the resource does not
     *         exist.
     */
    public static Resource getResource(String location) throws ResourceException {
        Assert.notNull(location, "Location must not be null");

        if (location.startsWith(RELATIVE_PATH_PREFIX)) {
            return new FilesystemResource(location);
        } else if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClasspathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                // Try to parse the location as a URI
                URI url = new URI(location);
                return new FilesystemResource(url);
            } catch (URISyntaxException | IllegalArgumentException e) {
                // No URI -> resolve as resource path.
                return new FilesystemResource(location);
            }
        }
    }

}
