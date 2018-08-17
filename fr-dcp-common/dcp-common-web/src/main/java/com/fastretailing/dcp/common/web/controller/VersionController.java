/**
 * @(#)VersionController.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fastretailing.dcp.common.util.JsonUtility;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * VersionController.<br>
 *     the controller to get the version information property of oms package <br>
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
@RestController
@RequestMapping(value = "/version")
public class VersionController {

    /**
     * get version information.
     * @return version info
     */
    @GetMapping
    public String getVersionInformationList()  throws JsonProcessingException {
        return JsonUtility.toJson(getVersionInfo());
    }

    /**
     * make the version information and return.
     * @return  version information
     */
    @Bean
    @ConfigurationProperties("oms.library")
    public VersionInfo getVersionInfo() {
        return new VersionInfo();
    }

}
