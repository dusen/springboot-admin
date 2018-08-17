/**
 * @(#)JobExecutionLoggingListenerTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.batch.listener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;

/**
 * test class of JobExecutionLoggingListener.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class JobExecutionLoggingListenerTest {

    @InjectMocks
    protected JobExecutionLoggingListener target;

    @Mock
    protected JobExecution jobExecution;

    @Mock
    protected JobInstance jobInstance;

    /**
     * Before method.<br>
     * @throws Exception any exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(jobExecution.getJobInstance()).thenReturn(jobInstance);
        Mockito.when(jobInstance.getJobName()).thenReturn("testJob");
    }

    @Test
    public void beforeJob() throws Exception {

        Mockito.when(jobExecution.getId()).thenReturn(10L);

        // Call
        target.beforeJob(jobExecution);

        // Assert
        //Mockito.verify(target).beforeJob(jobExecution);
        Mockito.verify(jobExecution).getId();
        Mockito.verify(jobInstance).getJobName();
    }

    @Test
    public void afterJob() throws Exception {
        Mockito.when(jobExecution.getId()).thenReturn(20L);
        Mockito.when(jobExecution.getExitStatus()).thenReturn(ExitStatus.COMPLETED);

        // Call
        target.afterJob(jobExecution);

        // Assert
        Mockito.verify(jobExecution).getId();
        Mockito.verify(jobInstance).getJobName();
        Mockito.verify(jobExecution).getExitStatus();
    }


}
