/**
 * @(#)JobExecutionBrandAndRegionListenerTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.batch.listener;

import com.fastretailing.dcp.common.threadlocal.BrandAndRegionHolder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * test class of JobExecutionBrandAndRegionListener.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class JobExecutionBrandAndRegionListenerTest {

    @InjectMocks
    protected JobExecutionBrandAndRegionListener target;

    @Mock
    protected JobExecution jobExecution;

    @Mock
    protected JobParameters jobParameters;

    /**
     * Before method.<br>
     * @throws Exception any exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(jobExecution.getJobParameters()).thenReturn(jobParameters);
        Mockito.when(jobParameters.getString("brand")).thenReturn("test brand");
        Mockito.when(jobParameters.getString("business_region")).thenReturn("test region");
    }

    @Test
    public void beforeJob() throws Exception {

        // Assert
        assertThat(BrandAndRegionHolder.getBrand(), is(nullValue()));
        assertThat(BrandAndRegionHolder.getRegion(), is(nullValue()));

        // Call
        target.beforeJob(jobExecution);

        // Assert
        assertThat(BrandAndRegionHolder.getBrand(), is("test brand"));
        assertThat(BrandAndRegionHolder.getRegion(), is("test region"));

    }

}
