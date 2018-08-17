/**
 * @(#)JobExceptionHandler.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.batch.listener;

import com.fastretailing.dcp.common.batch.exception.BatchBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handling job exception.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Slf4j
public class JobExceptionHandler implements JobExecutionListener {
    /**
     * Keyword used to put processing item into job's execution context.
     */
    public static final String ERROR_ITEM = "ERROR_ITEM";

    /**
     * Callback before a job executes.
     *
     * @param jobExecution the current jobExecution
     */
    public void beforeJob(JobExecution jobExecution) {
        //do nothing
    }

    /**
     * Handling job exceptions.
     * If job throws an exception,output exception message log.
     * Callback after completion of a job. Called after both both successful and
     * failed executions.
     *
     * @param jobExecution the current jobExecution
     */
    public void afterJob(JobExecution jobExecution) {

        // Get all failure causing exceptions
        List<Throwable> exceptions = jobExecution.getAllFailureExceptions();

        // No exceptions occurred
        if (exceptions.isEmpty()) {
            return;
        }

        // Validation Exception occurred
        if (exceptions.stream().anyMatch(ex -> ex instanceof ValidationException)) {
            // TODO set job exit Status,waiting for confirm result 
            return;
        }

        // Handle business exceptions
        List<Throwable> businessExceptions = exceptions.stream()
                .filter(ex -> ex instanceof BatchBusinessException).collect(Collectors.toList());
        businessExceptions.forEach(businessEx -> {
            log.warn(businessEx.getMessage());
            ExitStatus exitStatus = ((BatchBusinessException) businessEx).getExitStatus();
            if (exitStatus != null) {
                jobExecution.setExitStatus(exitStatus);
            }
        });
        if (!businessExceptions.isEmpty()) {
            return;
        }

        // Handling system exceptions
        log.info("This job has occurred some exceptions as follow. [job-name:{}] [size:{}]",
                jobExecution.getJobInstance().getJobName(), exceptions.size());
        exceptions.forEach(ex -> log.error("exception has occurred in job.", ex));

        // Log error item
        jobExecution.getStepExecutions().forEach(stepExecution -> {
            Object errorItem = stepExecution.getExecutionContext().get(ERROR_ITEM);
            if (errorItem != null) {
                log.error("detected error on this item processing. [step:{}] [item:{}]",
                        stepExecution.getStepName(), errorItem);
            }
        });
    }
}
