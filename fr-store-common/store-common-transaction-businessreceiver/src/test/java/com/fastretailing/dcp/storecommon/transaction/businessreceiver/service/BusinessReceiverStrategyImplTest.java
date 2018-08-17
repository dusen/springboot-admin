/**
 * @(#)BusinessReceiverStrategyImplTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
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
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.transaction.BusinessStatus;
import com.fastretailing.dcp.storecommon.transaction.TransactionResource;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.ApplicationBusinessReceiverTest;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionDispatchControlInformation;

/**
 * BusinessReceiverStrategyImpl test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBusinessReceiverTest.class)
public class BusinessReceiverStrategyImplTest {

    /** Create a mock of list of business receiver services. */
    @Spy
    private List<BusinessReceiverService> businessReceiverServiceList =
            new ArrayList<BusinessReceiverService>();

    /** Create a mock of system message source. */
    @Mock
    private SystemMessageSource messageSource;

    /** Test class. */
    @InjectMocks
    private BusinessReceiverStrategyImpl businessReceiverStrategyImpl;

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

        // Initialize list of business receiver services.
        BusinessReceiverService businessReceiverService1 = mock(BusinessReceiverService.class);
        BusinessReceiverService businessReceiverService2 = mock(BusinessReceiverService.class);
        BusinessReceiverService businessReceiverService3 = mock(BusinessReceiverService.class);
        businessReceiverServiceList.add(businessReceiverService1);
        businessReceiverServiceList.add(businessReceiverService2);
        businessReceiverServiceList.add(businessReceiverService3);

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
     * test receive method when message is empty.
     * 
     * @see BusinessReceiverStrategyImpl#receive(ReceiveMessage)
     */
    @Test
    public void testReceiveMessageEmpty() {

        thrown.expect(SystemException.class);

        when(businessReceiverServiceList.get(0)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(false);
        when(businessReceiverServiceList.get(0).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.SUCCESS);
        when(businessReceiverServiceList.get(1)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(false);
        when(businessReceiverServiceList.get(1).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.DUPLICATE);
        when(businessReceiverServiceList.get(2)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(false);
        when(businessReceiverServiceList.get(2).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.ERROR);

        businessReceiverStrategyImpl.receive(new ReceiveMessage());

        // verify if the business receiver service's receive method is not called.
        verify(businessReceiverServiceList.get(0), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
        verify(businessReceiverServiceList.get(1), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
        verify(businessReceiverServiceList.get(2), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
    }

    /**
     * test receive method when business receiver can not be found.
     * 
     * @see BusinessReceiverStrategyImpl#receive(ReceiveMessage)
     */
    @Test
    public void testReceiveBusinessReceiverNotFound() {

        thrown.expect(SystemException.class);

        when(businessReceiverServiceList.get(0)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(false);
        when(businessReceiverServiceList.get(0).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.SUCCESS);
        when(businessReceiverServiceList.get(1)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(false);
        when(businessReceiverServiceList.get(1).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.DUPLICATE);
        when(businessReceiverServiceList.get(2)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(false);
        when(businessReceiverServiceList.get(2).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.ERROR);

        businessReceiverStrategyImpl.receive(receiveMessage);

        // verify if the business receiver service's receive method is not called.
        verify(businessReceiverServiceList.get(0), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
        verify(businessReceiverServiceList.get(1), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
        verify(businessReceiverServiceList.get(2), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
    }

    /**
     * test receive method when duplicate.
     * 
     * @see BusinessReceiverStrategyImpl#receive(ReceiveMessage)
     */
    @Test
    public void testReceiveDuplicate() {

        BusinessStatus expectBusinessStatus = BusinessStatus.DUPLICATE;

        when(businessReceiverServiceList.get(0)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(false);
        when(businessReceiverServiceList.get(0).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.SUCCESS);
        when(businessReceiverServiceList.get(1)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(true);
        when(businessReceiverServiceList.get(1).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.DUPLICATE);
        when(businessReceiverServiceList.get(2)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(true);
        when(businessReceiverServiceList.get(2).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.ERROR);

        BusinessStatus actualBusinessStatus = businessReceiverStrategyImpl.receive(receiveMessage);

        assertThat(actualBusinessStatus, is(expectBusinessStatus));

        // verify if the first business receiver service's receive method is called only once
        // successfully.
        verify(businessReceiverServiceList.get(1), times(1))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());

        // verify if the other business receiver service's receive method is not called.
        verify(businessReceiverServiceList.get(0), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
        verify(businessReceiverServiceList.get(2), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
    }

    /**
     * test receive method when error.
     * 
     * @see BusinessReceiverStrategyImpl#receive(ReceiveMessage)
     */
    @Test
    public void testReceiveError() {

        BusinessStatus expectBusinessStatus = BusinessStatus.ERROR;

        when(businessReceiverServiceList.get(0)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(false);
        when(businessReceiverServiceList.get(0).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.SUCCESS);
        when(businessReceiverServiceList.get(1)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(false);
        when(businessReceiverServiceList.get(1).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.DUPLICATE);
        when(businessReceiverServiceList.get(2)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(true);
        when(businessReceiverServiceList.get(2).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.ERROR);

        BusinessStatus actualBusinessStatus = businessReceiverStrategyImpl.receive(receiveMessage);

        assertThat(actualBusinessStatus, is(expectBusinessStatus));

        // verify if the first business receiver service's receive method is called only once
        // successfully.
        verify(businessReceiverServiceList.get(2), times(1))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());

        // verify if the other business receiver service's receive method is not called.
        verify(businessReceiverServiceList.get(0), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
        verify(businessReceiverServiceList.get(1), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
    }


    /**
     * test receive method when success.
     * 
     * @see BusinessReceiverStrategyImpl#receive(ReceiveMessage)
     */
    @Test
    public void testReceiveSuccess() {

        BusinessStatus expectBusinessStatus = BusinessStatus.SUCCESS;

        when(businessReceiverServiceList.get(0)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(true);
        when(businessReceiverServiceList.get(0).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.SUCCESS);
        when(businessReceiverServiceList.get(1)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(false);
        when(businessReceiverServiceList.get(1).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.DUPLICATE);
        when(businessReceiverServiceList.get(2)
                .checkTransactionType(receiveMessage.getTransactionId(), receiveMessage.getTransactionData())).thenReturn(true);
        when(businessReceiverServiceList.get(2).receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData()))
                .thenReturn(BusinessStatus.ERROR);

        BusinessStatus actualBusinessStatus = businessReceiverStrategyImpl.receive(receiveMessage);

        assertThat(actualBusinessStatus, is(expectBusinessStatus));

        // verify if the first business receiver service's receive method is called only once
        // successfully.
        verify(businessReceiverServiceList.get(0), times(1))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());

        // verify if the other business receiver service's receive method is not called.
        verify(businessReceiverServiceList.get(1), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
        verify(businessReceiverServiceList.get(2), times(0))
                .receive(receiveMessage.getTransactionId(), receiveMessage.getTransactionData());
    }

}
