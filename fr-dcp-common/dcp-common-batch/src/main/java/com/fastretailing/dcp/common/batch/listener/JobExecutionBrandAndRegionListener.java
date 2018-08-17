/**
 * @(#)JobExecutionBrandAndRegionListener.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.batch.listener;

import com.fastretailing.dcp.common.threadlocal.BrandAndRegionHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Initialization of BrandAndRegionHolder.
 * Save the brand and region to thread local.
 * The brand and region values come from batch execution parameters.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Slf4j
public class JobExecutionBrandAndRegionListener implements JobExecutionListener {

    /**
     * Initialization of BrandAndRegionHolder before batch execution.
     * Callback before a job executes.
     *
     * @param jobExecution the current jobExecution
     */
    public void beforeJob(JobExecution jobExecution) {
        // save the brand and region to thread local
        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(BrandAndRegionHolder.KEY_BRAND, jobExecution.getJobParameters().getString("brand"));
        pathVariableMap.put(BrandAndRegionHolder.KEY_REGION, jobExecution.getJobParameters().getString("business_region"));
        BrandAndRegionHolder.setBrandAndRegionMap(pathVariableMap);

        log.info("Initialize the BrandAndRegionHolder object from the batch execution parameters");
    }

    /**
     * Callback after completion of a job.
     *
     * @param jobExecution the current jobExecution
     */
    public void afterJob(JobExecution jobExecution) {
    }
}