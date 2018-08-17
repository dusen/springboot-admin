/**
 * @(#)SqsMessageReceiverImplTest.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import org.jboss.logging.MDC;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.transaction.BusinessStatus;
import com.fastretailing.dcp.storecommon.transaction.ExecutionRightAcquisitionState;
import com.fastretailing.dcp.storecommon.transaction.ProcessingStatus;
import com.fastretailing.dcp.storecommon.transaction.TransactionResource;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.ApplicationBusinessReceiverTest;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.config.BusinessReceiverConfiguration;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.exception.ExecutionRightAcquisitionException;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.service.BusinessReceiverStatusService;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.service.BusinessReceiverStrategy;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.service.TransactionPublishingService;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.type.TransactionPublishState;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionDispatchControlInformation;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionStatus;
import com.fastretailing.dcp.storecommon.transaction.repository.TransactionStatusDynamoRepository;
import com.fastretailing.dcp.storecommon.transaction.util.TransactionDateUtil;

/**
 * SqsMessageReceiverImpl test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBusinessReceiverTest.class)
public class SqsMessageReceiverImplTest {

    /** Create a mock of RDS Business status table service. */
    @MockBean
    private BusinessReceiverStatusService businessReceiverStatusService;

    /** Create a mock of DynamoDB access service. */
    @MockBean
    private TransactionStatusDynamoRepository transactionStatusDynamoRepository;

    /** Create a mock of execute business processes. */
    @MockBean
    private BusinessReceiverStrategy businessReceiverStrategy;

    /** Create a mock of redis access service. */
    @MockBean
    private TransactionPublishingService transactionPublishingService;

    // /** Create a mock of transaction converter. */
    // @Mock
    // private TransactionMessageConverter transactionMessageConverter;

    /** Create a mock of message source */
    @MockBean
    private SystemMessageSource systemMessageSource;

    /** Create a mock of business receiver configuration. */
    @MockBean
    private BusinessReceiverConfiguration businessReceiverConfiguration;

    /** Test class. */
    @Autowired
    private SqsMessageReceiverImpl sqsMessageReceiverImpl;

    /** DynamoDB time to live expiration days. */
    @Value("${transaction-dispatch.dynamodb.expiration-days:60}")
    private int expirationDays;

    /** Exception instance for expected. */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * test queueListener method when exception occupied.
     * 
     * @see SqsMessageReceiverImpl#queueListener(String)
     */
    @Test(expected = SystemException.class)
    public void testQueueListener_nullMessage() {

        String message = null;
        sqsMessageReceiverImpl.receive(message);

    }

    /**
     * test queueListener method when exception occupied.
     * 
     * @see SqsMessageReceiverImpl#queueListener(String)
     */
    @Test(expected = SystemException.class)
    public void testQueueListener_unformedMessage() {

        String message = "test";
        sqsMessageReceiverImpl.receive(message);

    }

    /**
     * test queueListener method when execution right can not be acquired.
     * 
     * @see SqsMessageReceiverImpl#queueListener(String)
     */
    @Test
    public void testQueueListenerExecutionRightNotAcquired() {

        String message =
                "{\"transactionId\":\"1512469280164f0fba887-b601-481b-8a9c-321b581ecf72\",\"transactionData\":{\"requestHeaderMap\":{\"accept-encoding\":\"gzip,deflate\",\"content-type\":\"application/json\",\"content-length\":\"628\",\"host\":\"localhost:8080\",\"connection\":\"Keep-Alive\",\"user-agent\":\"Apache-HttpClient/4.1.1 (java 1.5)\"},\"resourceMap\":{\"TRANSACTION_TYPE\":\"55\",\"LANGUAGE\":\"en\",\"REGION\":\"ca\",\"BRAND\":\"uq\",\"CHANNEL_CODE\":\"channel\",\"STORE_CODE\":\"store\",\"INTEGRATED_ORDER_ID\":\"order\",\"ORDER_SUB_NUMBER\":\"ordersub\",\"UPDATE_TYPE\":\"update\"},\"businessTransactionData\":\"{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesTime\\\":\\\"220300123\\\", \\\"transactionType\\\":\\\"10\\\", \\\"detail\\\":[{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10001\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"},{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10002\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"},{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10003\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}]}\",\"transactionDispatchControlInformation\":{\"retryCount\":1}}}";

        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.setTransactionId("1512469280164f0fba887-b601-481b-8a9c-321b581ecf72");

        TransactionData transactionData = new TransactionData();
        Map<String, String> requestHeaderMap = new HashMap<>();
        requestHeaderMap.put("accept-encoding", "gzip,deflate");
        requestHeaderMap.put("content-type", "application/json");
        requestHeaderMap.put("content-length", "628");
        requestHeaderMap.put("host", "localhost:8080");
        requestHeaderMap.put("connection", "Keep-Alive");
        requestHeaderMap.put("user-agent", "Apache-HttpClient/4.1.1 (java 1.5)");
        transactionData.setRequestHeaderMap(requestHeaderMap);

        Map<TransactionResource, String> resourceMap = new HashMap<>();
        resourceMap.put(TransactionResource.TRANSACTION_TYPE, "55");
        resourceMap.put(TransactionResource.LANGUAGE, "en");
        resourceMap.put(TransactionResource.REGION, "ca");
        resourceMap.put(TransactionResource.BRAND, "uq");
        resourceMap.put(TransactionResource.CHANNEL_CODE, "channel");
        resourceMap.put(TransactionResource.STORE_CODE, "store");
        resourceMap.put(TransactionResource.INTEGRATED_ORDER_ID, "order");
        resourceMap.put(TransactionResource.ORDER_SUB_NUMBER, "ordersub");
        resourceMap.put(TransactionResource.UPDATE_TYPE, "update");
        transactionData.setResourceMap(resourceMap);

        String businessTransactionData = "{\"businessDate\":\"15520000\",\"posNo\":\"003\""
                + ", \"receiptNo\":\"00063\", \"salesTime\":\"220300123\", \"transactionType\":\"10\""
                + ", \"detail\":[{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\""
                + ", \"salesId\":\"10001\",\"itemCode\":\"10101010\", \"salesNum\":\"5\""
                + ", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\""
                + ",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10002\""
                + ",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\""
                + ", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\""
                + ", \"receiptNo\":\"00063\", \"salesId\":\"10003\",\"itemCode\":\"10101010\""
                + ", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"}]}";

        transactionData.setBusinessTransactionData(businessTransactionData);

        TransactionDispatchControlInformation transactionDispatchControlInformation =
                new TransactionDispatchControlInformation();
        transactionDispatchControlInformation.setRetryCount(1);
        transactionData
                .setTransactionDispatchControlInformation(transactionDispatchControlInformation);

        receiveMessage.setTransactionData(transactionData);

        when(businessReceiverStatusService.acquireExecutionRight(receiveMessage))
                .thenReturn(ExecutionRightAcquisitionState.FAILED);

        thrown.expect(ExecutionRightAcquisitionException.class);
        thrown.expectMessage("Cannot get right to execute transaction. transactionId = "
                + receiveMessage.getTransactionId());

        try {

            sqsMessageReceiverImpl.receive(message);

        } catch (RuntimeException e) {

            ArgumentCaptor<ReceiveMessage> expectedArgCaptor =
                    ArgumentCaptor.forClass(ReceiveMessage.class);
            verify(businessReceiverStatusService, times(1))
                    .acquireExecutionRight(expectedArgCaptor.capture());
            ReceiveMessage expectedArg = expectedArgCaptor.getValue();
            assertThat(expectedArg.getTransactionId(), equalTo(receiveMessage.getTransactionId()));

            // Verify if the getBusinessReceiverName method is not called.
            verify(businessReceiverConfiguration, never()).getBusinessReceiverName();

            // Verify if the save method is not called
            ArgumentCaptor<TransactionStatus> captor =
                    ArgumentCaptor.forClass(TransactionStatus.class);
            verify(transactionStatusDynamoRepository, never()).save(captor.capture());

            // Verify if the publish method is not called
            verify(transactionPublishingService, never()).publish(receiveMessage);

            throw e;

        }

    }

    /**
     * test queueListener method when error occupied.
     * 
     * @see SqsMessageReceiverImpl#queueListener(String)
     */
    @Test
    public void testQueueListener_businessReceiverError() {

        String message =
                "{\"transactionId\":\"1512469280164f0fba887-b601-481b-8a9c-321b581ecf72\",\"transactionData\":{\"requestHeaderMap\":{\"accept-encoding\":\"gzip,deflate\",\"content-type\":\"application/json\",\"content-length\":\"628\",\"host\":\"localhost:8080\",\"connection\":\"Keep-Alive\",\"user-agent\":\"Apache-HttpClient/4.1.1 (java 1.5)\",\"x-amzn-trace-id\":\"Root=1-5b519394-5a4190149818f258e9e68880\"},\"resourceMap\":{\"TRANSACTION_TYPE\":\"55\",\"LANGUAGE\":\"en\",\"REGION\":\"ca\",\"BRAND\":\"uq\",\"CHANNEL_CODE\":\"channel\",\"STORE_CODE\":\"store\",\"INTEGRATED_ORDER_ID\":\"order\",\"ORDER_SUB_NUMBER\":\"ordersub\",\"UPDATE_TYPE\":\"update\"},\"businessTransactionData\":\"{\\n\\n\\\"businessDate\\\":\\\"15520000\\\"\\n\\n,\\\"posNo\\\":\\\"003\\\"\\n\\n, \\\"receiptNo\\\":\\\"00063\\\"\\n\\n, \\\"salesTime\\\":\\\"220300123\\\"\\n\\n, \\\"transactionType\\\":\\\"10\\\"\\n\\n, \\\"detail\\\":\\n\\n[\\n\\n{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10001\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n,{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10002\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n,{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10003\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n]\\n\\n}\\n\",\"transactionDispatchControlInformation\":{\"retryCount\":1}}}";

        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.setTransactionId("1512469280164f0fba887-b601-481b-8a9c-321b581ecf72");

        TransactionData transactionData = new TransactionData();
        Map<String, String> requestHeaderMap = new HashMap<>();
        requestHeaderMap.put("accept-encoding", "gzip,deflate");
        requestHeaderMap.put("content-type", "application/json");
        requestHeaderMap.put("content-length", "628");
        requestHeaderMap.put("host", "localhost:8080");
        requestHeaderMap.put("connection", "Keep-Alive");
        requestHeaderMap.put("user-agent", "Apache-HttpClient/4.1.1 (java 1.5)");
        requestHeaderMap.put("x-amzn-trace-id", "Root=1-5b519394-5a4190149818f258e9e68880");
        transactionData.setRequestHeaderMap(requestHeaderMap);

        Map<TransactionResource, String> resourceMap = new HashMap<>();
        resourceMap.put(TransactionResource.TRANSACTION_TYPE, "55");
        resourceMap.put(TransactionResource.LANGUAGE, "en");
        resourceMap.put(TransactionResource.REGION, "ca");
        resourceMap.put(TransactionResource.BRAND, "uq");
        resourceMap.put(TransactionResource.CHANNEL_CODE, "channel");
        resourceMap.put(TransactionResource.STORE_CODE, "store");
        resourceMap.put(TransactionResource.INTEGRATED_ORDER_ID, "order");
        resourceMap.put(TransactionResource.ORDER_SUB_NUMBER, "ordersub");
        resourceMap.put(TransactionResource.UPDATE_TYPE, "update");
        transactionData.setResourceMap(resourceMap);

        String businessTransactionData = "{\"businessDate\":\"15520000\",\"posNo\":\"003\""
                + ", \"receiptNo\":\"00063\", \"salesTime\":\"220300123\", \"transactionType\":\"10\""
                + ", \"detail\":[{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\""
                + ", \"salesId\":\"10001\",\"itemCode\":\"10101010\", \"salesNum\":\"5\""
                + ", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\""
                + ", \"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10002\""
                + ", \"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\""
                + ", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\""
                + ", \"receiptNo\":\"00063\", \"salesId\":\"10003\",\"itemCode\":\"10101010\""
                + ", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"}]}";

        transactionData.setBusinessTransactionData(businessTransactionData);

        TransactionDispatchControlInformation transactionDispatchControlInformation =
                new TransactionDispatchControlInformation();
        transactionDispatchControlInformation.setRetryCount(1);
        transactionData
                .setTransactionDispatchControlInformation(transactionDispatchControlInformation);

        String businessReceiverName = "testProcess";

        when(businessReceiverStatusService.acquireExecutionRight(any()))
                .thenReturn(ExecutionRightAcquisitionState.ACQUIRED);
        when(businessReceiverConfiguration.getBusinessReceiverName())
                .thenReturn(businessReceiverName);
        String currentDateMinute = TransactionDateUtil.createCurrentDateMinute();
        String businessStatus = String.join(TransactionStatus.BUSINESS_STATUS_SEPARATOR,
                String.valueOf(System.currentTimeMillis()), businessReceiverName,
                ProcessingStatus.PROCESSING.toString());

        TransactionStatus transactionStatus = new TransactionStatus();
        transactionStatus.setTransactionId(receiveMessage.getTransactionId());
        transactionStatus.setBusinessStatus(businessStatus);
        transactionStatus.setRegistrationDate(currentDateMinute);

        when(transactionStatusDynamoRepository.save(transactionStatus)).thenReturn(null);
        when(businessReceiverStrategy.receive(receiveMessage)).thenReturn(BusinessStatus.ERROR);
        String businessStatusSuccess = String.join(TransactionStatus.BUSINESS_STATUS_SEPARATOR,
                String.valueOf(System.currentTimeMillis()), businessReceiverName,
                ProcessingStatus.COMPLETION.toString());

        TransactionStatus transactionStatusSuccess = new TransactionStatus();
        transactionStatusSuccess.setTransactionId(receiveMessage.getTransactionId());
        transactionStatusSuccess.setBusinessStatus(businessStatusSuccess);
        transactionStatusSuccess.setRegistrationDate(currentDateMinute);
        when(transactionStatusDynamoRepository.save(transactionStatusSuccess))
                .thenReturn(transactionStatusSuccess);

        sqsMessageReceiverImpl.receive(message);

        assertThat(MDC.get("X-Amzn-Trace-Id"), is("Root=1-5b519394-5a4190149818f258e9e68880"));


        verify(transactionPublishingService, never()).publish(any());

        // Verify if the save method is called twice successfully when error.
        ArgumentCaptor<TransactionStatus> dynamoArgCaptor =
                ArgumentCaptor.forClass(TransactionStatus.class);
        verify(transactionStatusDynamoRepository, times(2)).save(dynamoArgCaptor.capture());
        assertTrue(dynamoArgCaptor.getAllValues().get(1).getBusinessStatus().endsWith("error"));
    }

    /**
     * test queueListener method when success.
     * 
     * @see SqsMessageReceiverImpl#queueListener(String)
     */
    @Test
    public void testQueueListenerSuccess() {

        String message =
                "{\"transactionId\":\"1512469280164f0fba887-b601-481b-8a9c-321b581ecf72\",\"transactionData\":{\"requestHeaderMap\":{\"accept-encoding\":\"gzip,deflate\",\"content-type\":\"application/json\",\"content-length\":\"628\",\"host\":\"localhost:8080\",\"connection\":\"Keep-Alive\",\"user-agent\":\"Apache-HttpClient/4.1.1 (java 1.5)\"},\"resourceMap\":{\"TRANSACTION_TYPE\":\"55\",\"LANGUAGE\":\"en\",\"REGION\":\"ca\",\"BRAND\":\"uq\",\"CHANNEL_CODE\":\"channel\",\"STORE_CODE\":\"store\",\"INTEGRATED_ORDER_ID\":\"order\",\"ORDER_SUB_NUMBER\":\"ordersub\",\"UPDATE_TYPE\":\"update\"},\"businessTransactionData\":\"{\\n\\n\\\"businessDate\\\":\\\"15520000\\\"\\n\\n,\\\"posNo\\\":\\\"003\\\"\\n\\n, \\\"receiptNo\\\":\\\"00063\\\"\\n\\n, \\\"salesTime\\\":\\\"220300123\\\"\\n\\n, \\\"transactionType\\\":\\\"10\\\"\\n\\n, \\\"detail\\\":\\n\\n[\\n\\n{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10001\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n,{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10002\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n,{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10003\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n]\\n\\n}\\n\",\"transactionDispatchControlInformation\":{\"retryCount\":1}}}";

        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.setTransactionId("1512469280164f0fba887-b601-481b-8a9c-321b581ecf72");

        TransactionData transactionData = new TransactionData();
        Map<String, String> requestHeaderMap = new HashMap<>();
        requestHeaderMap.put("accept-encoding", "gzip,deflate");
        requestHeaderMap.put("content-type", "application/json");
        requestHeaderMap.put("content-length", "628");
        requestHeaderMap.put("host", "localhost:8080");
        requestHeaderMap.put("connection", "Keep-Alive");
        requestHeaderMap.put("user-agent", "Apache-HttpClient/4.1.1 (java 1.5)");
        transactionData.setRequestHeaderMap(requestHeaderMap);

        Map<TransactionResource, String> resourceMap = new HashMap<>();
        resourceMap.put(TransactionResource.TRANSACTION_TYPE, "55");
        resourceMap.put(TransactionResource.LANGUAGE, "en");
        resourceMap.put(TransactionResource.REGION, "ca");
        resourceMap.put(TransactionResource.BRAND, "uq");
        resourceMap.put(TransactionResource.CHANNEL_CODE, "channel");
        resourceMap.put(TransactionResource.STORE_CODE, "store");
        resourceMap.put(TransactionResource.INTEGRATED_ORDER_ID, "order");
        resourceMap.put(TransactionResource.ORDER_SUB_NUMBER, "ordersub");
        resourceMap.put(TransactionResource.UPDATE_TYPE, "update");
        transactionData.setResourceMap(resourceMap);

        String businessTransactionData = "{\"businessDate\":\"15520000\",\"posNo\":\"003\""
                + ", \"receiptNo\":\"00063\", \"salesTime\":\"220300123\", \"transactionType\":\"10\""
                + ", \"detail\":[{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\""
                + ", \"salesId\":\"10001\",\"itemCode\":\"10101010\", \"salesNum\":\"5\""
                + ", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\""
                + ", \"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10002\""
                + ", \"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\""
                + ", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\""
                + ", \"receiptNo\":\"00063\", \"salesId\":\"10003\",\"itemCode\":\"10101010\""
                + ", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"}]}";

        transactionData.setBusinessTransactionData(businessTransactionData);

        TransactionDispatchControlInformation transactionDispatchControlInformation =
                new TransactionDispatchControlInformation();
        transactionDispatchControlInformation.setRetryCount(1);
        transactionData
                .setTransactionDispatchControlInformation(transactionDispatchControlInformation);

        String businessReceiverName = "testProcess";

        when(businessReceiverStatusService.acquireExecutionRight(any()))
                .thenReturn(ExecutionRightAcquisitionState.ACQUIRED);
        when(businessReceiverConfiguration.getBusinessReceiverName())
                .thenReturn(businessReceiverName);
        String currentDateMinute = TransactionDateUtil.createCurrentDateMinute();
        String businessStatus = String.join(TransactionStatus.BUSINESS_STATUS_SEPARATOR,
                String.valueOf(System.currentTimeMillis()), businessReceiverName,
                ProcessingStatus.PROCESSING.toString());

        TransactionStatus transactionStatus = new TransactionStatus();
        transactionStatus.setTransactionId(receiveMessage.getTransactionId());
        transactionStatus.setBusinessStatus(businessStatus);
        transactionStatus.setRegistrationDate(currentDateMinute);

        when(transactionStatusDynamoRepository.save(transactionStatus)).thenReturn(null);
        when(businessReceiverStrategy.receive(any())).thenReturn(BusinessStatus.SUCCESS);
        String sortKeySuccess = String.join(TransactionStatus.BUSINESS_STATUS_SEPARATOR,
                String.valueOf(System.currentTimeMillis()), businessReceiverName,
                ProcessingStatus.COMPLETION.toString());

        TransactionStatus transactionStatusSuccess = new TransactionStatus();
        transactionStatusSuccess.setTransactionId(receiveMessage.getTransactionId());
        transactionStatusSuccess.setBusinessStatus(sortKeySuccess);
        transactionStatusSuccess.setRegistrationDate(currentDateMinute);
        when(transactionStatusDynamoRepository.save(transactionStatusSuccess)).thenReturn(null);

        when(transactionPublishingService.publish(receiveMessage))
                .thenReturn(TransactionPublishState.PUBLISHED);

        sqsMessageReceiverImpl.receive(message);

        // Verify if the save method is called twice successfully when error.
        ArgumentCaptor<TransactionStatus> statusCaptor =
                ArgumentCaptor.forClass(TransactionStatus.class);
        verify(transactionStatusDynamoRepository, times(2)).save(statusCaptor.capture());
        assertTrue(statusCaptor.getAllValues().get(1).getBusinessStatus().endsWith("completion"));
        assertThat(Instant.now().plus(expirationDays, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS),
                is(Instant.ofEpochSecond(statusCaptor.getAllValues().get(1).getExpirationTime())
                        .truncatedTo(ChronoUnit.DAYS)));

        // Verify if the publish method is called only once successfully.
        ArgumentCaptor<ReceiveMessage> rcvmsgCaptor = ArgumentCaptor.forClass(ReceiveMessage.class);
        verify(transactionPublishingService, times(1)).publish(rcvmsgCaptor.capture());
        assertThat(rcvmsgCaptor.getValue().getTransactionId(),
                equalTo(receiveMessage.getTransactionId()));
    }
}
