/**
 * @(#)JobExecutionLoggingListener.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.batch.listener;

import com.fastretailing.dcp.common.util.JsonUtility;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Output job start and end log.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Slf4j
public class JobExecutionLoggingListener implements JobExecutionListener {

    /**
     * Task-Seq (X-Edf-Task-Seq).
     */
    private static final String TASK_SEQ = "Task-Seq";

    /**
     * endpoint.
     */
    private static final String ENDPOINT = "endpoint";

    /**
     *  shell's parameter key(task_seq)
     *
     * Task-Seq (X-Edf-Task-Seq).
     */
    private static final String TASK_SEQ_KEY = "task_seq";

    /**
     * shell's parameter key(end_point)
     *
     * endpoint.
     */
    private static final String ENDPOINT_KEY = "end_point";

    /**
     * *version.properties file
     */
    private static final String VERSION_PROPERTIES_END_WITH = "version.properties";

    /**
     * oms.library.version.*=xxx
     */
    private static final String VERSION_PROPERTIES_KEY_START_WITH = "oms.library.version.";

    /**
     * the version information of oms package.
     */
    private static final String VERSION_INFO = "versionInfo";

    /**
     * PropertySourcesPlaceholderConfigurer
     */
    @Autowired
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer;

    /**
     * Output job start log.
     * Callback before a job executes.
     *
     * @param jobExecution the current jobExecution
     */
    public void beforeJob(JobExecution jobExecution) {

        // Task-Seq (X-Edf-Task-Seq)
        MDC.put(TASK_SEQ, jobExecution.getJobParameters().getString(TASK_SEQ_KEY));

        // endpoint
        MDC.put(ENDPOINT, jobExecution.getJobParameters().getString(ENDPOINT_KEY));

        try {
            VersionInfo versionInfo = new VersionInfo();
            Properties localProperties =
                    (Properties) (propertySourcesPlaceholderConfigurer
                            .getAppliedPropertySources().get("localProperties").getSource());
            localProperties.keySet().stream().
                    filter(key -> String.valueOf(key).startsWith(VERSION_PROPERTIES_KEY_START_WITH)).
                    forEach(key -> {
                        versionInfo.getVersion().put(
                                StringUtils.substringAfter(String.valueOf(key),
                                        VERSION_PROPERTIES_KEY_START_WITH), String.valueOf(localProperties.get(key)));
                    });

            Map<String, Object> logMap = new LinkedHashMap<>();
            logMap.put(VERSION_INFO, JsonUtility.toJson(versionInfo));
            log.info("", logMap);
        } catch (IOException e) {
            log.error("An error occurred.", e);
        }

        log.info("job started. [JobName:{}][jobExecutionId:{}]",
                jobExecution.getJobInstance().getJobName(), jobExecution.getId());
    }

    /**
     * Output job finish log.
     * Callback after completion of a job. Called after both both successful and
     * failed executions.
     *
     * @param jobExecution the current jobExecution
     */
    public void afterJob(JobExecution jobExecution) {
        log.info("job finished.[JobName:{}][jobExecutionId:{}][ExitStatus:{}]",
                jobExecution.getJobInstance().getJobName(), jobExecution.getId(),
                jobExecution.getExitStatus().getExitCode());
    }

    /**
     * VersionInfo.<br>
     *     the version information of oms package <br>
     *
     * @author Fast Retailing
     * @version $Revision$
     */
    @Data
    private class VersionInfo {

        /**
         * version info map.
         */
        private Map<String, String> version = new HashMap<>();
    }
}
