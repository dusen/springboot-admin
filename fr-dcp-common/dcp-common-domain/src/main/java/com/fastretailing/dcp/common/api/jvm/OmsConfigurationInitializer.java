/**
 * @(#)OmsConfigurationInitializer.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.jvm;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.Arrays;


/**
 * <pre>
 * OmsConfigurationInitializer.<br>
 *     the configuration to get the version information property of oms package <br>
 *
 * </pre>
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class OmsConfigurationInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    /**
     * the class path of oms property file.
     */
    private static final String OMS_VERSION_PROPERTIES
            = "classpath*:/*-version.properties";

    /**
     * initialize.
     * 
     * @param applicationContext the context of application
     */
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {

            boolean apiType = false;
            // save the vm options
            // get the admin api setting in vm
            Object adminSetting = applicationContext
                    .getEnvironment()
                    .getSystemProperties()
                    .get("oms.api.type");
            if (adminSetting != null && adminSetting.toString().equalsIgnoreCase("admin")) {
                apiType = true;
            }
            // save the admin api setting
            OmsJvmParameters.isAdminApi = apiType;

            // get the country setting in environment
            Object country = applicationContext
                    .getEnvironment()
                    .getSystemEnvironment()
                    .get("country");
            if (country != null) {
                // save the country setting
                OmsJvmParameters.country = country.toString();
            }

            // get the property from oms package property file
            final Resource[] versionProperties = applicationContext
                    .getResources(OMS_VERSION_PROPERTIES);
            if (versionProperties == null) {
                return;
            }
            // add the version information to property sources
            final MutablePropertySources mps =
                    applicationContext.getEnvironment().getPropertySources();
            Arrays.stream(versionProperties)
                    .forEach(versionProperty -> {
                        try {
                            mps.addLast(new ResourcePropertySource(
                                    versionProperty.getFilename(), versionProperty));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
