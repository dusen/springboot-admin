/**
 * @(#)TransactionPublishingServiceImplTest.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fastretailing.dcp.storecommon.transaction.TransactionMessageConverter;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.ApplicationBusinessReceiverTest;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.config.BusinessReceiverConfiguration;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.type.TransactionPublishState;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;

/**
 * TransactionPublishingServiceImpl test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBusinessReceiverTest.class)
public class TransactionPublishingServiceImplTest {

    /** Create a mock of parts for publish to Redis. */
    @Mock
    private StringRedisTemplate template;

    /** Create a mock of string and object converter. */
    @Mock
    private TransactionMessageConverter transactionMessageConverter;

    /** Create a mock of current batch configuration. */
    @Mock
    private BusinessReceiverConfiguration businessReceiverConfiguration;

    /** Exception instance for expected. */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /** Test class. */
    @InjectMocks
    private TransactionPublishingServiceImpl transactionPublishingServiceImpl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * test publish method when message is null.
     * 
     * @see TransactionPublishingServiceImpl#publish(ReceiveMessage)
     */
    @Test
    public void testPublishMessageNull() {

        TransactionPublishState expect = TransactionPublishState.EMPTYDATA;

        TransactionPublishState actual = transactionPublishingServiceImpl.publish(null);

        assertThat(actual, is(expect));

        // verify if the serializeReceiveMessage method is called only once successfully.
        verify(transactionMessageConverter, times(1)).serializeReceiveMessage(anyObject());

        // verify if the convertAndSend method is not called.
        verify(template, times(0)).convertAndSend(anyString(), anyString());
    }

    /**
     * test publish method when message is empty.
     * 
     * @see TransactionPublishingServiceImpl#publish(ReceiveMessage)
     */
    @Test
    public void testPublishMessageEmpty() {

        TransactionPublishState expect = TransactionPublishState.EMPTYDATA;

        TransactionPublishState actual =
                transactionPublishingServiceImpl.publish(new ReceiveMessage());

        assertThat(actual, is(expect));

        // verify if the serializeReceiveMessage method is called only once successfully.
        verify(transactionMessageConverter, times(1)).serializeReceiveMessage(anyObject());

        // verify if the convertAndSend method is not called.
        verify(template, times(0)).convertAndSend(anyString(), anyString());
    }

    /**
     * test publish method when exception occupied in StringRedisTemplate's convertAndSend method.
     * 
     * @see TransactionPublishingServiceImpl#publish(ReceiveMessage)
     */
    @Test
    public void testPublishExcepitonInConvertAndSend() {

        TransactionPublishState expect = TransactionPublishState.FAILED;

        String message =
                "{\"transactionId\":\"1512469280164f0fba887-b601-481b-8a9c-321b581ecf72\",\"transactionData\":{\"requestHeaderMap\":{\"accept-encoding\":\"gzip,deflate\",\"content-type\":\"application/json\",\"content-length\":\"628\",\"host\":\"localhost:8080\",\"connection\":\"Keep-Alive\",\"user-agent\":\"Apache-HttpClient/4.1.1 (java 1.5)\"},\"resourceMap\":{\"TRANSACTION_TYPE\":\"55\",\"LANGUAGE\":\"en\",\"REGION\":\"ca\",\"VERSION\":\"v1\",\"BRAND\":\"uq\"},\"businessTransactionData\":\"{\\n\\n\\\"businessDate\\\":\\\"15520000\\\"\\n\\n,\\\"posNo\\\":\\\"003\\\"\\n\\n, \\\"receiptNo\\\":\\\"00063\\\"\\n\\n, \\\"salesTime\\\":\\\"220300123\\\"\\n\\n, \\\"transactionType\\\":\\\"10\\\"\\n\\n, \\\"detail\\\":\\n\\n[\\n\\n{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10001\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n,{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10002\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n,{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10003\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n]\\n\\n}\\n\",\"transactionDispatchControlInformation\":{\"retryCount\":1}}}";
        when(transactionMessageConverter.serializeReceiveMessage(anyObject())).thenReturn(message);
        doThrow(new RuntimeException("Error occupied at publish")).when(template)
                .convertAndSend(anyString(), anyObject());
        TransactionPublishState actual =
                transactionPublishingServiceImpl.publish(new ReceiveMessage());

        assertThat(actual, is(expect));

        // verify if the serializeReceiveMessage method is called only once successfully.
        verify(transactionMessageConverter, times(1)).serializeReceiveMessage(anyObject());

    }

    /**
     * test publish method when success.
     * 
     * @see TransactionPublishingServiceImpl#publish(ReceiveMessage)
     */
    @Test
    public void testPublishSuccess() {

        TransactionPublishState expect = TransactionPublishState.PUBLISHED;

        String message =
                "{\"transactionId\":\"1512469280164f0fba887-b601-481b-8a9c-321b581ecf72\",\"transactionData\":{\"requestHeaderMap\":{\"accept-encoding\":\"gzip,deflate\",\"content-type\":\"application/json\",\"content-length\":\"628\",\"host\":\"localhost:8080\",\"connection\":\"Keep-Alive\",\"user-agent\":\"Apache-HttpClient/4.1.1 (java 1.5)\"},\"resourceMap\":{\"TRANSACTION_TYPE\":\"55\",\"LANGUAGE\":\"en\",\"REGION\":\"ca\",\"VERSION\":\"v1\",\"BRAND\":\"uq\"},\"businessTransactionData\":\"{\\n\\n\\\"businessDate\\\":\\\"15520000\\\"\\n\\n,\\\"posNo\\\":\\\"003\\\"\\n\\n, \\\"receiptNo\\\":\\\"00063\\\"\\n\\n, \\\"salesTime\\\":\\\"220300123\\\"\\n\\n, \\\"transactionType\\\":\\\"10\\\"\\n\\n, \\\"detail\\\":\\n\\n[\\n\\n{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10001\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n,{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10002\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n,{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10003\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n]\\n\\n}\\n\",\"transactionDispatchControlInformation\":{\"retryCount\":1}}}";
        when(transactionMessageConverter.serializeReceiveMessage(anyObject())).thenReturn(message);
        
        String channelCode = "TESTCHANNEL"; 
        when(businessReceiverConfiguration.getChannelCode()).thenReturn(channelCode);

        TransactionPublishState actual =
                transactionPublishingServiceImpl.publish(new ReceiveMessage());

        assertThat(actual, is(expect));

        // verify if the serializeReceiveMessage method is called only once successfully.
        verify(transactionMessageConverter, times(1)).serializeReceiveMessage(anyObject());

        // verify if the convertAndSend method is not called.
        verify(template, times(1)).convertAndSend(channelCode, message);

    }

}
