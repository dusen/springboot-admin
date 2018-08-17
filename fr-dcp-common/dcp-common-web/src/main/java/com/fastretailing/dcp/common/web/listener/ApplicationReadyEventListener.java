/**
 * @(#)ApplicationReadyEventListener.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.web.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fastretailing.dcp.common.util.JsonUtility;
import com.fastretailing.dcp.common.web.controller.VersionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ApplicationReadyEventListener
 *
 * output version info to log
 */
@Slf4j
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * the version information of oms package.
     */
    private static final String VERSION_INFO = "versionInfo";

    @Autowired
    private VersionInfo versionInfo;

    /**
     * output version info to log
     *
     * @param event ApplicationReadyEvent
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        try {
            Map<String, Object> logMap = new LinkedHashMap<>();
            logMap.put(VERSION_INFO, JsonUtility.toJson(versionInfo));
            log.info("", logMap);
        } catch (JsonProcessingException e) {
            log.error("An error occurred.", e);
        }

    }

}
