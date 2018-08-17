/**
 * @(#)TransactionDispatchDataSubscriberTest.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.transaction.BusinessStatus;
import com.fastretailing.dcp.storecommon.transaction.ExecutionRightAcquisitionState;
import com.fastretailing.dcp.storecommon.transaction.TransactionResource;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.config.BusinessProcessorConfiguration;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.service.BusinessProcessorService;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.service.BusinessProcessorStatusService;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionDispatchControlInformation;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionStatus;
import com.fastretailing.dcp.storecommon.transaction.repository.TransactionStatusDynamoRepository;
import com.fastretailing.dcp.storecommon.transaction.util.TransactionDateUtil;

/**
 * TransactionDispatchDataSubscriber test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubSubscriberApplication.class)
public class TransactionDispatchDataSubscriberTest {

    /** Create a mock of a component that retrieves messages from external resources. */
    @MockBean
    private SystemMessageSource messageSource;

    /** Create a mock of an instance of a class that performs business processing. */
    @MockBean
    private BusinessProcessorService businessProcessorService;

    /** Create a mock of transaction status management service. */
    @MockBean
    private BusinessProcessorStatusService businessProcessorStatusService;

    /** Create a mock of transaction status dynamo repository. */
    @MockBean
    private TransactionStatusDynamoRepository transactionStatusDynamoRepository;

    /** Create a mock of business processor configuration. */
    @Spy
    private BusinessProcessorConfiguration businessProcessorConfiguration;

    /** Create a mock of transaction date util. */
    @MockBean
    private TransactionDateUtil transactionDateUtil;

    /** Test class. */
    @Autowired
    private TransactionDispatchDataSubscriber transactionDispatchDataSubscriber;

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

    private String receiveMessageBase =
            "{\"transactionId\":\"1512469280164f0fba887-b601-481b-8a9c-321b581ecf72\","
                    + "\"transactionData\":{"
                    + "\"requestHeaderMap\":{\"accept-encoding\":\"gzip,deflate\",\"content-type\":\"application/json\",\"content-length\":\"628\",\"host\":\"localhost:8080\",\"connection\":\"Keep-Alive\",\"user-agent\":\"Apache-HttpClient/4.1.1 (java 1.5)\"},"
                    + "\"resourceMap\":{\"TRANSACTION_TYPE\":\"55\",\"LANGUAGE\":\"en\",\"REGION\":\"ca\",\"BRAND\":\"uq\",\"CHANNEL_CODE\":\"channel\",\"STORE_CODE\":\"store\",\"INTEGRATED_ORDER_ID\":\"order\",\"ORDER_SUB_NUMBER\":\"ordersub\",\"UPDATE_TYPE\":\"update\"},"
                    + "\"businessTransactionData\":\"{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesTime\\\":\\\"220300123\\\", \\\"transactionType\\\":\\\"10\\\", \\\"detail\\\":[{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10001\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"},{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10002\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"},{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10003\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}]}\","
                    + "\"transactionDispatchControlInformation\":{\"retryCount\":1}" + "}}";

    /**
     * test receiveMessage method when message is empty.
     * 
     * @see TransactionDispatchDataSubscriber#receiveMessage(String)
     */
    @Test
    public void testReceiveMessageEmptyMessage() {

        thrown.expect(SystemException.class);

        // message is empty
        transactionDispatchDataSubscriber.receiveMessage(StringUtils.EMPTY);

    }

    /**
     * test receiveMessage method when message is null.
     * 
     * @see TransactionDispatchDataSubscriber#receiveMessage(String)
     */
    @Test
    public void testReceiveMessageNullMessage() {

        thrown.expect(SystemException.class);

        // message is empty
        transactionDispatchDataSubscriber.receiveMessage(null);

    }

    /**
     * test receiveMessage method when message is not match ReceiveMessage's structur.
     * 
     * @see TransactionDispatchDataSubscriber#receiveMessage(String)
     */
    @Test
    public void testReceiveMessageMessageUnformed() {

        thrown.expect(SystemException.class);

        // message length is not enough
        transactionDispatchDataSubscriber.receiveMessage("message");
    }

    /**
     * test receiveMessage method when transaction type is empty.
     * 
     * @see TransactionDispatchDataSubscriber#receiveMessage(String)
     */
    @Test
    public void testReceiveMessageEmptyTransactionType() {

        ReceiveMessage receiveMessage = new ReceiveMessage();
        String transactionId = "1512469280164f0fba887-b601-481b-8a9c-321b581ecf72";
        TransactionData transactionData = new TransactionData();

        Map<TransactionResource, String> resourceMap = new HashMap<>();
        resourceMap.put(TransactionResource.TRANSACTION_TYPE, StringUtils.EMPTY);

        transactionData.setResourceMap(resourceMap);
        receiveMessage.setTransactionId(transactionId);
        receiveMessage.setTransactionData(transactionData);


        List<String> acceptTransactionType = new ArrayList<String>();
        acceptTransactionType.add("test1");
        acceptTransactionType.add("test2");
        acceptTransactionType.add("test3");

        String message = "{\"transactionId\":\"1512469280164f0fba887-b601-481b-8a9c-321b581ecf72\","
                + "\"transactionData\":{"
                + "\"requestHeaderMap\":{\"accept-encoding\":\"gzip,deflate\",\"content-type\":\"application/json\",\"content-length\":\"628\",\"host\":\"localhost:8080\",\"connection\":\"Keep-Alive\",\"user-agent\":\"Apache-HttpClient/4.1.1 (java 1.5)\",\"x-amzn-trace-id\":\"Root=1-5b519394-5a4190149818f258e9e68880\"},"
                + "\"resourceMap\":{\"LANGUAGE\":\"en\",\"REGION\":\"ca\",\"BRAND\":\"uq\",\"CHANNEL_CODE\":\"channel\",\"STORE_CODE\":\"store\",\"INTEGRATED_ORDER_ID\":\"order\",\"ORDER_SUB_NUMBER\":\"ordersub\",\"UPDATE_TYPE\":\"update\"},"
                + "\"businessTransactionData\":\"{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesTime\\\":\\\"220300123\\\", \\\"transactionType\\\":\\\"10\\\", \\\"detail\\\":[{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10001\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"},{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10002\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"},{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10003\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}]}\","
                + "\"transactionDispatchControlInformation\":{\"retryCount\":1}" + "}}";

        when(businessProcessorConfiguration.getAcceptingTransactionType())
                .thenReturn(acceptTransactionType);

        // transaction type is empty
        transactionDispatchDataSubscriber.receiveMessage(message);

        assertThat(MDC.get("X-Amzn-Trace-Id"), is("Root=1-5b519394-5a4190149818f258e9e68880"));
        // verify if the checkTransactionType method is called
        verify(businessProcessorService, times(1)).checkTransactionType(any(), any());
    }

    /**
     * test receiveMessage method when transaction type is not accept.
     * 
     * @see TransactionDispatchDataSubscriber#receiveMessage(String)
     */
    @Test
    public void testReceiveMessageTransactionTypeCheckFail() {

        ReceiveMessage receiveMessage = new ReceiveMessage();
        String transactionId = "1512469280164f0fba887-b601-481b-8a9c-321b581ecf72";
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

        String businessTransactionData =
                "{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesTime\":\"220300123\", \"transactionType\":\"10\", \"detail\":[{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10001\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10002\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10003\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"}]}";
        transactionData.setBusinessTransactionData(businessTransactionData);

        TransactionDispatchControlInformation transactionDispatchControlInformation =
                new TransactionDispatchControlInformation();
        transactionDispatchControlInformation.setRetryCount(1);
        transactionData
                .setTransactionDispatchControlInformation(transactionDispatchControlInformation);

        receiveMessage.setTransactionId(transactionId);
        receiveMessage.setTransactionData(transactionData);

        List<String> acceptTransactionType = new ArrayList<String>();
        acceptTransactionType.add("test1");
        acceptTransactionType.add("test2");
        acceptTransactionType.add("test3");

        String message = this.receiveMessageBase;

        when(businessProcessorConfiguration.getAcceptingTransactionType())
                .thenReturn(acceptTransactionType);
        when(businessProcessorService.checkTransactionType(eq(transactionId), any()))
                .thenReturn(false);

        // transaction type is not accept
        transactionDispatchDataSubscriber.receiveMessage(message);

        verify(businessProcessorService, times(1)).checkTransactionType(transactionId,
                transactionData);

        // verify if the getBusinessProcessName method is not called
        verify(businessProcessorConfiguration, never()).getBusinessProcessName();
    }

    /**
     * test receiveMessage method when execution right can not be acquired.
     * 
     * @see TransactionDispatchDataSubscriber#receiveMessage(String)
     */
    @Test
    public void testReceiveMessageExecutionRightAcquisitionFail() {

        ReceiveMessage receiveMessage = new ReceiveMessage();
        String transactionId = "1512469280164f0fba887-b601-481b-8a9c-321b581ecf72";
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

        String businessTransactionData =
                "{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesTime\":\"220300123\", \"transactionType\":\"10\", \"detail\":[{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10001\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10002\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10003\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"}]}";
        transactionData.setBusinessTransactionData(businessTransactionData);

        TransactionDispatchControlInformation transactionDispatchControlInformation =
                new TransactionDispatchControlInformation();
        transactionDispatchControlInformation.setRetryCount(1);
        transactionData
                .setTransactionDispatchControlInformation(transactionDispatchControlInformation);

        receiveMessage.setTransactionId(transactionId);
        receiveMessage.setTransactionData(transactionData);

        List<String> acceptTransactionType = new ArrayList<String>();
        acceptTransactionType.add("test1");
        acceptTransactionType.add("test2");
        acceptTransactionType.add("test3");

        String message = receiveMessageBase;
        String businessProcessName = "testProcess";

        when(businessProcessorConfiguration.getAcceptingTransactionType())
                .thenReturn(acceptTransactionType);
        when(businessProcessorService.checkTransactionType(transactionId, transactionData))
                .thenReturn(true);
        when(businessProcessorConfiguration.getBusinessProcessName())
                .thenReturn(businessProcessName);
        when(businessProcessorStatusService.acquireExecutionRight(receiveMessage))
                .thenReturn(ExecutionRightAcquisitionState.FAILED);

        // execution right can not be acquired
        transactionDispatchDataSubscriber.receiveMessage(message);

        // verify if the checkExecutionRightAcquisition method is called only once successfully.
        verify(businessProcessorStatusService, times(1)).acquireExecutionRight(receiveMessage);

        // verify if the execute method is not called
        verify(transactionStatusDynamoRepository, never()).save(any(TransactionStatus.class));
    }

    /**
     * test receiveMessage method when execution right can not be acquired.
     * 
     * @see TransactionDispatchDataSubscriber#receiveMessage(String)
     */
    @Test
    public void testReceiveMessageSuccess() {

        ReceiveMessage receiveMessage = new ReceiveMessage();
        String transactionId = "1512469280164f0fba887-b601-481b-8a9c-321b581ecf72";
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

        String businessTransactionData =
                "{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesTime\":\"220300123\", \"transactionType\":\"10\", \"detail\":[{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10001\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10002\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10003\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"}]}";
        transactionData.setBusinessTransactionData(businessTransactionData);

        TransactionDispatchControlInformation transactionDispatchControlInformation =
                new TransactionDispatchControlInformation();
        transactionDispatchControlInformation.setRetryCount(1);
        transactionData
                .setTransactionDispatchControlInformation(transactionDispatchControlInformation);

        receiveMessage.setTransactionId(transactionId);
        receiveMessage.setTransactionData(transactionData);

        List<String> acceptTransactionType = new ArrayList<String>();
        acceptTransactionType.add("test1");
        acceptTransactionType.add("test2");
        acceptTransactionType.add("test3");

        String message = receiveMessageBase;
        String businessProcessName = "testProcess";

        when(businessProcessorConfiguration.getAcceptingTransactionType())
                .thenReturn(acceptTransactionType);
        when(businessProcessorService.checkTransactionType(transactionId, transactionData))
                .thenReturn(true);
        when(businessProcessorConfiguration.getBusinessProcessName())
                .thenReturn(businessProcessName);
        when(businessProcessorStatusService.acquireExecutionRight(receiveMessage))
                .thenReturn(ExecutionRightAcquisitionState.ACQUIRED);
        when(businessProcessorService.execute(transactionId, transactionData))
                .thenReturn(BusinessStatus.SUCCESS);

        // execution right can not be acquired
        transactionDispatchDataSubscriber.receiveMessage(message);

        // verify if the save method is not called
        ArgumentCaptor<TransactionStatus> captor = ArgumentCaptor.forClass(TransactionStatus.class);
        verify(transactionStatusDynamoRepository, times(2)).save(captor.capture());
        List<TransactionStatus> saveArg = captor.getAllValues();
        assertThat(saveArg.get(1).getTransactionId(), equalTo(transactionId));
        assertThat(saveArg.get(1).getBusinessStatus().endsWith("completion"), equalTo(true));
        assertThat(saveArg.get(1).getRegistrationDate(), notNullValue());
        assertThat(Instant.now().plus(expirationDays, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS),
                is(Instant.ofEpochSecond(saveArg.get(1).getExpirationTime())
                        .truncatedTo(ChronoUnit.DAYS)));
    }

    /**
     * test receiveMessage method when execution right can not be acquired.
     * 
     * @see TransactionDispatchDataSubscriber#receiveMessage(String)
     */
    @Test
    public void testReceiveMessageError() {

        ReceiveMessage receiveMessage = new ReceiveMessage();
        String transactionId = "1512469280164f0fba887-b601-481b-8a9c-321b581ecf72";
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

        String businessTransactionData =
                "{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesTime\":\"220300123\", \"transactionType\":\"10\", \"detail\":[{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10001\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10002\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10003\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"}]}";
        transactionData.setBusinessTransactionData(businessTransactionData);

        TransactionDispatchControlInformation transactionDispatchControlInformation =
                new TransactionDispatchControlInformation();
        transactionDispatchControlInformation.setRetryCount(1);
        transactionData
                .setTransactionDispatchControlInformation(transactionDispatchControlInformation);

        receiveMessage.setTransactionId(transactionId);
        receiveMessage.setTransactionData(transactionData);

        List<String> acceptTransactionType = new ArrayList<String>();
        acceptTransactionType.add("test1");
        acceptTransactionType.add("test2");
        acceptTransactionType.add("test3");

        String message = receiveMessageBase;
        String businessProcessName = "testProcess";

        when(businessProcessorConfiguration.getAcceptingTransactionType())
                .thenReturn(acceptTransactionType);
        when(businessProcessorService.checkTransactionType(transactionId, transactionData))
                .thenReturn(true);
        when(businessProcessorConfiguration.getBusinessProcessName())
                .thenReturn(businessProcessName);
        when(businessProcessorStatusService.acquireExecutionRight(receiveMessage))
                .thenReturn(ExecutionRightAcquisitionState.ACQUIRED);
        when(businessProcessorService.execute(transactionId, transactionData))
                .thenReturn(BusinessStatus.ERROR);

        // execution right can not be acquired
        transactionDispatchDataSubscriber.receiveMessage(message);

        // verify if the save method is not called
        ArgumentCaptor<TransactionStatus> captor = ArgumentCaptor.forClass(TransactionStatus.class);
        verify(transactionStatusDynamoRepository, times(2)).save(captor.capture());
        List<TransactionStatus> saveArg = captor.getAllValues();
        assertThat(saveArg.get(1).getTransactionId(), equalTo(transactionId));
        assertThat(saveArg.get(1).getBusinessStatus().endsWith("error"), equalTo(true));
        assertThat(saveArg.get(1).getRegistrationDate(), notNullValue());
    }

    /**
     * test receiveMessage method when business exception occupied.
     * 
     * @see TransactionDispatchDataSubscriber#receiveMessage(String)
     */
    @Test
    public void testReceiveMessageBusinessException() {

        ReceiveMessage receiveMessage = new ReceiveMessage();
        String transactionId = "1512469280164f0fba887-b601-481b-8a9c-321b581ecf72";
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

        String businessTransactionData =
                "{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesTime\":\"220300123\", \"transactionType\":\"10\", \"detail\":[{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10001\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10002\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"businessDate\":\"15520000\",\"posNo\":\"003\", \"receiptNo\":\"00063\", \"salesId\":\"10003\",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"}]}";
        transactionData.setBusinessTransactionData(businessTransactionData);

        TransactionDispatchControlInformation transactionDispatchControlInformation =
                new TransactionDispatchControlInformation();
        transactionDispatchControlInformation.setRetryCount(1);
        transactionData
                .setTransactionDispatchControlInformation(transactionDispatchControlInformation);

        receiveMessage.setTransactionId(transactionId);
        receiveMessage.setTransactionData(transactionData);

        List<String> acceptTransactionType = new ArrayList<String>();
        acceptTransactionType.add("test1");
        acceptTransactionType.add("test2");
        acceptTransactionType.add("test3");

        String message = receiveMessageBase;
        String businessProcessName = "testProcess";

        when(businessProcessorConfiguration.getAcceptingTransactionType())
                .thenReturn(acceptTransactionType);
        when(businessProcessorService.checkTransactionType(transactionId, transactionData))
                .thenReturn(true);
        when(businessProcessorConfiguration.getBusinessProcessName())
                .thenReturn(businessProcessName);
        when(businessProcessorStatusService.acquireExecutionRight(receiveMessage))
                .thenReturn(ExecutionRightAcquisitionState.ACQUIRED);

        doThrow(new BusinessException(null)).when(businessProcessorService).execute(transactionId,
                transactionData);

        // error
        transactionDispatchDataSubscriber.receiveMessage(message);

        // verify if the save method is called twice successfully when error.
        ArgumentCaptor<TransactionStatus> captor = ArgumentCaptor.forClass(TransactionStatus.class);
        verify(transactionStatusDynamoRepository, times(1)).save(captor.capture());
        TransactionStatus saveArg = captor.getValue();
        assertThat(saveArg.getTransactionId(), equalTo(transactionId));
        assertThat(saveArg.getBusinessStatus().endsWith("processing"), equalTo(true));
    }
}
