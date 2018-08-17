/**
 * @(#)UriResolver.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.uri;

import com.fastretailing.dcp.common.api.jvm.OmsJvmParameters;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     Uri resolver class.
 *     Business project will provide service in two servers.(admin server and consumer server)
 *     When access to another platform, you must choose uri from admin server and consumer server.
 *     If the service is for admin, you must access to the next platform service's admin server.
 *     This class is for auto switch URI.
 * </p>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
public class UriResolver {

    /**
     * applicationContext.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * get uri according server type and uri key.
     * @param uriKey uri key
     * @return current uri.
     */
    public String getUriAccordingToServerType(String uriKey) {

        String fullUriKey = OmsJvmParameters.isAdminApi()
                ? StringUtils.join(uriKey, ".admin")
                : StringUtils.join(uriKey, ".consumer");

        return applicationContext.getEnvironment().getProperty(fullUriKey);

    }

}
