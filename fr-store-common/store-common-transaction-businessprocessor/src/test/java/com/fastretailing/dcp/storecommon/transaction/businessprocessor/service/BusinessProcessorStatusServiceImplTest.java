/**
 * @(#)BusinessProcessorStatusServiceImplTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.transaction.ExecutionRightAcquisitionState;
import com.fastretailing.dcp.storecommon.transaction.TransactionResource;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.config.BusinessProcessorConfiguration;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.repository.BusinessProcessorStatusMapper;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionDispatchControlInformation;

/**
 * BusinessProcessorStatusServiceImpl test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessProcessorStatusServiceImplTest {

    /** Create a mock of business receiver configuration. */
    @Mock
    private BusinessProcessorConfiguration businessProcessorConfiguration;

    /** Create a mock of partition calculation service. */
    @Mock
    private BusinessProcessorPartitionService businessProcessorPartitionCalculationService;

    /** Create a mock of database access mapper. */
    @Mock
    private BusinessProcessorStatusMapper businessProcessorControllerMapper;

    /** Test class. */
    @InjectMocks
    private BusinessProcessorStatusServiceImpl businessProcessorStatusServiceImpl = new BusinessProcessorStatusServiceImpl();

    /** Exception instance for expected. */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /** ReceiveMessage object. */
    private ReceiveMessage receiveMessage;

    /**
     * The preHandle method.
     * 
     * @throws Exception Exception that occurred.
     */
    @Before
    public void setUp() throws Exception {
        // Initialize ReceiveMessage object.
        receiveMessage = new ReceiveMessage();

        TransactionData transactionData = new TransactionData();
        Map<TransactionResource, String> resourceMap = new HashMap<>();
        resourceMap.put(TransactionResource.TRANSACTION_TYPE, "10");
        transactionData.setResourceMap(resourceMap);

        TransactionDispatchControlInformation transactionDispatchControlInformation = new TransactionDispatchControlInformation();
        transactionDispatchControlInformation.setRetryCount(2);
        transactionData
                .setTransactionDispatchControlInformation(transactionDispatchControlInformation);

        receiveMessage.setTransactionId("testTransactionId");
        receiveMessage.setTransactionData(transactionData);
    }

    /**
     * test checkExecutionRightAcquisition method when partitionNo is different.
     * 
     * @see BusinessProcessorStatusServiceImpl#checkExecutionRightAcquisition(ReceiveMessage)
     */
    @Test
    public void testCheckExecutionRightAcquisitionPartitionNoDifferent() {

        ExecutionRightAcquisitionState expect = ExecutionRightAcquisitionState.FAILED;

        List<String> acceptTransactionType = new ArrayList<String>();
        acceptTransactionType.add("10");
        acceptTransactionType.add("20");
        acceptTransactionType.add("30");

        when(businessProcessorConfiguration.getBusinessProcessName()).thenReturn("test");
        when(businessProcessorConfiguration.getAcceptingTransactionType())
                .thenReturn(acceptTransactionType);
        when(businessProcessorPartitionCalculationService
                .calculationPartitionNo(receiveMessage.getTransactionId())).thenReturn(2L);

        ExecutionRightAcquisitionState actual =
                businessProcessorStatusServiceImpl.acquireExecutionRight(receiveMessage);

        assertThat(actual, is(expect));
    }

    /**
     * test checkExecutionRightAcquisition method when no one inserted.
     * 
     * @see BusinessProcessorStatusServiceImpl#checkExecutionRightAcquisition(ReceiveMessage)
     */
    @Test
    public void testCheckExecutionRightAcquisitionPartitionInsertZero() {

        ExecutionRightAcquisitionState expect = ExecutionRightAcquisitionState.FAILED;

        List<String> acceptTransactionType = new ArrayList<String>();
        acceptTransactionType.add("10");
        acceptTransactionType.add("20");
        acceptTransactionType.add("30");

        when(businessProcessorConfiguration.getBusinessProcessName()).thenReturn("test");
        when(businessProcessorConfiguration.getAcceptingTransactionType())
                .thenReturn(acceptTransactionType);
        when(businessProcessorPartitionCalculationService
                .calculationPartitionNo(receiveMessage.getTransactionId())).thenReturn(0L);
        when(businessProcessorControllerMapper.insert(anyObject())).thenReturn(0);

        ExecutionRightAcquisitionState actual =
                businessProcessorStatusServiceImpl.acquireExecutionRight(receiveMessage);

        assertThat(actual, is(expect));

        verify(businessProcessorControllerMapper, times(1)).insert(anyObject());
    }

    /**
     * test checkExecutionRightAcquisition method when insert exception occupied.
     * 
     * @see BusinessProcessorStatusServiceImpl#checkExecutionRightAcquisition(ReceiveMessage)
     */
    @Test
    public void testCheckExecutionRightAcquisitionPartitionInsertException() {

        List<String> acceptTransactionType = new ArrayList<String>();
        acceptTransactionType.add("10");
        acceptTransactionType.add("20");
        acceptTransactionType.add("30");

        when(businessProcessorConfiguration.getBusinessProcessName()).thenReturn("test");
        when(businessProcessorConfiguration.getAcceptingTransactionType())
                .thenReturn(acceptTransactionType);
        when(businessProcessorPartitionCalculationService
                .calculationPartitionNo(receiveMessage.getTransactionId())).thenReturn(0L);
        doThrow(new SystemException()).when(businessProcessorControllerMapper).insert(anyObject());

        thrown.expect(Exception.class);
        businessProcessorStatusServiceImpl.acquireExecutionRight(receiveMessage);

        verify(businessProcessorControllerMapper, times(1)).insert(anyObject());
    }

    /**
     * test checkExecutionRightAcquisition method when success.
     * 
     * @see BusinessProcessorStatusServiceImpl#checkExecutionRightAcquisition(ReceiveMessage)
     */
    @Test
    public void testCheckExecutionRightAcquisitionPartitionSuccess() {

        ExecutionRightAcquisitionState expect = ExecutionRightAcquisitionState.ACQUIRED;

        List<String> acceptTransactionType = new ArrayList<String>();
        acceptTransactionType.add("10");
        acceptTransactionType.add("20");
        acceptTransactionType.add("30");

        when(businessProcessorConfiguration.getBusinessProcessName()).thenReturn("test");
        when(businessProcessorConfiguration.getAcceptingTransactionType())
                .thenReturn(acceptTransactionType);
        when(businessProcessorPartitionCalculationService
                .calculationPartitionNo(receiveMessage.getTransactionId())).thenReturn(0L);
        when(businessProcessorControllerMapper.insert(anyObject())).thenReturn(1);

        ExecutionRightAcquisitionState actual =
                businessProcessorStatusServiceImpl.acquireExecutionRight(receiveMessage);

        assertThat(actual, is(expect));

        verify(businessProcessorControllerMapper, times(1)).insert(anyObject());
    }

}
