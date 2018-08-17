/**
 * @(#)JobExceptionHandlerTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.batch.listener;

import com.fastretailing.dcp.common.batch.exception.BatchBusinessException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.validator.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * test class of JobExceptionHandler.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class JobExceptionHandlerTest {

    @InjectMocks
    protected JobExceptionHandler target;

    @Mock
    protected JobExecution jobExecution;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void beforeJob() throws Exception {
        // Call
        target.beforeJob(jobExecution);

    }

    @Test
    public void afterJob01() throws Exception {

        // No exceptions occur.
        // setting
        Mockito.when(jobExecution.getAllFailureExceptions()).thenReturn(new ArrayList<>(0));

        // Call
        target.afterJob(jobExecution);

        // Assert
        Mockito.verify(jobExecution, Mockito.never()).getJobInstance();

    }

    @Test
    public void afterJob02() throws Exception {

        // Validation exceptions occurred.
        List<Throwable> exceptions = new ArrayList<>();
        exceptions.add(new ValidationException("ValidationException1"));
        // setting
        Mockito.when(jobExecution.getAllFailureExceptions()).thenReturn(exceptions);

        // Call
        target.afterJob(jobExecution);

        // Assert
        Mockito.verify(jobExecution, Mockito.never()).getJobInstance();

    }

    @Test
    public void afterJob03() throws Exception {

        // One business exception
        // setting
        JobInstance jobInstance = Mockito.mock(JobInstance.class);
        List<Throwable> exceptions = new ArrayList<>();
        exceptions.add(new BatchBusinessException("BusinessError", ExitStatus.NOOP));
        Mockito.when(jobExecution.getAllFailureExceptions()).thenReturn(exceptions);
        Mockito.when(jobExecution.getJobInstance()).thenReturn(jobInstance);
        Mockito.when(jobInstance.getJobName()).thenReturn("afterJob02");

        // Call
        target.afterJob(jobExecution);

        // Assert
        Mockito.verify(jobExecution, Mockito.never()).getJobInstance();

    }

    @Test
    public void afterJob04() throws Exception {

        // One system exception ,no error item information.
        // setting
        JobInstance jobInstance = Mockito.mock(JobInstance.class);
        List<Throwable> exceptions = new ArrayList<>();
        exceptions.add(new IllegalArgumentException("exception1"));
        Mockito.when(jobExecution.getAllFailureExceptions()).thenReturn(exceptions);
        Mockito.when(jobExecution.getJobInstance()).thenReturn(jobInstance);
        Mockito.when(jobInstance.getJobName()).thenReturn("afterJob04");

        StepExecution stepExecution1 = Mockito.mock(StepExecution.class);
        ExecutionContext executionContext1 = new ExecutionContext();
        executionContext1.put(JobExceptionHandler.ERROR_ITEM, "errorItem1");
        Mockito.when(stepExecution1.getStepName()).thenReturn("step01");
        Mockito.when(stepExecution1.getExecutionContext()).thenReturn(executionContext1);

        List<StepExecution> stepExecutions = new ArrayList<>();
        stepExecutions.add(stepExecution1);
        Mockito.when(jobExecution.getStepExecutions()).thenReturn(stepExecutions);

        // Call
        target.afterJob(jobExecution);

        // Assert
        Mockito.verify(jobExecution).getJobInstance();

    }

    @Test
    public void afterJob05() throws Exception {

        // Two exception with error item information.
        // setting
        List<Throwable> exceptions = new ArrayList<>();
        exceptions.add(new BatchBusinessException("BusinessError",ExitStatus.FAILED));
        exceptions.add(new IllegalArgumentException("exception2"));
        Mockito.when(jobExecution.getAllFailureExceptions()).thenReturn(exceptions);

        StepExecution stepExecution1 = Mockito.mock(StepExecution.class);
        ExecutionContext executionContext1 = new ExecutionContext();
        executionContext1.put(JobExceptionHandler.ERROR_ITEM, "errorItem1");
        Mockito.when(stepExecution1.getStepName()).thenReturn("step01");
        Mockito.when(stepExecution1.getExecutionContext()).thenReturn(executionContext1);

        StepExecution stepExecution2 = Mockito.mock(StepExecution.class);
        ExecutionContext executionContext2 = new ExecutionContext();
        Mockito.when(stepExecution2.getStepName()).thenReturn("step02");
        Mockito.when(stepExecution2.getExecutionContext()).thenReturn(executionContext2);

        List<StepExecution> stepExecutions = new ArrayList<>();
        stepExecutions.add(stepExecution1);
        stepExecutions.add(stepExecution2);
        Mockito.when(jobExecution.getStepExecutions()).thenReturn(stepExecutions);

        JobInstance jobInstance = Mockito.mock(JobInstance.class);
        Mockito.when(jobExecution.getJobInstance()).thenReturn(jobInstance);
        Mockito.when(jobInstance.getJobName()).thenReturn("afterJob03");

        // Call
        target.afterJob(jobExecution);

        // Assert
        Mockito.verify(jobExecution, Mockito.never()).getJobInstance();
        Mockito.verify(jobExecution).setExitStatus(Mockito.any());
        Mockito.verify(stepExecution2,Mockito.never()).getExecutionContext();

    }
}
