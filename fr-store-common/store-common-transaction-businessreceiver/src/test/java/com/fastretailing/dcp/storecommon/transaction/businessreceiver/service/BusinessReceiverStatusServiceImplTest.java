/**
 * @(#)BusinessReceiverStatusServiceImplTest.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
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
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.ApplicationBusinessReceiverTest;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.config.BusinessReceiverConfiguration;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.repository.BusinessReceiverStatusMapper;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionDispatchControlInformation;

/**
 * BusinessReceiverStatusServiceImpl test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBusinessReceiverTest.class)
public class BusinessReceiverStatusServiceImplTest {

    /** Create a mock of business receiver configuration. */
    @Mock
    private BusinessReceiverConfiguration businessReceiverConfiguration;

    /** Create a mock of database access mapper. */
    @Mock
    private BusinessReceiverStatusMapper businessReceiverControllerMapper;

    /** Test class. */
    @InjectMocks
    private BusinessReceiverStatusServiceImpl businessReceiverStatusServiceImpl;

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

        TransactionDispatchControlInformation transactionDispatchControlInformation =
                new TransactionDispatchControlInformation();
        transactionDispatchControlInformation.setRetryCount(2);
        transactionData
                .setTransactionDispatchControlInformation(transactionDispatchControlInformation);

        receiveMessage.setTransactionId("testTransactionId");
        receiveMessage.setTransactionData(transactionData);
    }

    /**
     * test checkExecutionRightAcquisition method when no one inserted.
     * 
     * @see BusinessReceiverStatusServiceImpl#checkExecutionRightAcquisition(ReceiveMessage)
     */
    @Test
    public void testCheckExecutionRightAcquisitionInsertZero() {

        ExecutionRightAcquisitionState expect = ExecutionRightAcquisitionState.FAILED;

        when(businessReceiverConfiguration.getBusinessReceiverName()).thenReturn("test");
        when(businessReceiverControllerMapper.insert(anyObject())).thenReturn(0);

        ExecutionRightAcquisitionState actual =
                businessReceiverStatusServiceImpl.acquireExecutionRight(receiveMessage);

        assertThat(actual, is(expect));

        // verify if the insert method is called only once successfully.
        verify(businessReceiverControllerMapper, times(1)).insert(anyObject());
    }

    /**
     * test checkExecutionRightAcquisition method when insert exception occupied.
     * 
     * @see BusinessReceiverStatusServiceImpl#checkExecutionRightAcquisition(ReceiveMessage)
     */
    @Test
    public void testCheckExecutionRightAcquisitionInsertException() {

        when(businessReceiverConfiguration.getBusinessReceiverName()).thenReturn("test");
        doThrow(new SystemException()).when(businessReceiverControllerMapper).insert(anyObject());

        thrown.expect(Exception.class);
        businessReceiverStatusServiceImpl.acquireExecutionRight(receiveMessage);

        // verify if the insert method is called only once successfully.
        verify(businessReceiverControllerMapper, times(1)).insert(anyObject());
    }

    /**
     * test checkExecutionRightAcquisition method when success.
     * 
     * @see BusinessReceiverStatusServiceImpl#checkExecutionRightAcquisition(ReceiveMessage)
     */
    @Test
    public void testCheckExecutionRightAcquisitionSuccess() {

        ExecutionRightAcquisitionState expect = ExecutionRightAcquisitionState.ACQUIRED;

        when(businessReceiverConfiguration.getBusinessReceiverName()).thenReturn("test");
        when(businessReceiverControllerMapper.insert(anyObject())).thenReturn(1);

        ExecutionRightAcquisitionState actual =
                businessReceiverStatusServiceImpl.acquireExecutionRight(receiveMessage);

        assertThat(actual, is(expect));

        // verify if the insert method is called only once successfully.
        verify(businessReceiverControllerMapper, times(1)).insert(anyObject());
    }
}
