/**
 * @(#)TransactionMessageConverterTest.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.ValidationException;
import com.fastretailing.dcp.storecommon.transaction.dto.OrderInfo;
import com.fastretailing.dcp.storecommon.transaction.dto.OrderInfoJSR310;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;

/**
 * TransactionMessageConverterImpl test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionMessageConverterTest {

    /** Test class. */
    @Autowired
    private TransactionMessageConverter transactionMessageConverter;

    /**
     * Test deserialize valid.
     */
    @Test
    public void testDeserializeValid() {
        String s =
                "{\"transactionId\":\"1512469280164f0fba887-b601-481b-8a9c-321b581ecf72\",\"transactionData\":{\"requestHeaderMap\":{\"accept-encoding\":\"gzip,deflate\",\"content-type\":\"application/json\",\"content-length\":\"628\",\"host\":\"localhost:8080\",\"connection\":\"Keep-Alive\",\"user-agent\":\"Apache-HttpClient/4.1.1 (java 1.5)\"},\"resourceMap\":{\"TRANSACTION_TYPE\":\"55\",\"LANGUAGE\":\"en\",\"REGION\":\"ca\",\"BRAND\":\"uq\",\"CHANNEL_CODE\":\"channel\",\"STORE_CODE\":\"store\",\"INTEGRATED_ORDER_ID\":\"order\",\"ORDER_SUB_NUMBER\":\"ordersub\",\"UPDATE_TYPE\":\"update\"},\"businessTransactionData\":\"{\\n\\n\\\"businessDate\\\":\\\"15520000\\\"\\n\\n,\\\"posNo\\\":\\\"003\\\"\\n\\n, \\\"receiptNo\\\":\\\"00063\\\"\\n\\n, \\\"salesTime\\\":\\\"220300123\\\"\\n\\n, \\\"transactionType\\\":\\\"10\\\"\\n\\n, \\\"detail\\\":\\n\\n[\\n\\n{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10001\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n,{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10002\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n,{\\\"businessDate\\\":\\\"15520000\\\",\\\"posNo\\\":\\\"003\\\", \\\"receiptNo\\\":\\\"00063\\\", \\\"salesId\\\":\\\"10003\\\",\\\"itemCode\\\":\\\"10101010\\\", \\\"salesNum\\\":\\\"5\\\", \\\"unitPrice\\\":\\\"500\\\", \\\"itemSales\\\":\\\"50000\\\"}\\n\\n]\\n\\n}\\n\",\"transactionDispatchControlInformation\":{\"retryCount\":1}}}";
        ReceiveMessage ret = transactionMessageConverter.deserialize(s, true);

        assertEquals("1512469280164f0fba887-b601-481b-8a9c-321b581ecf72", ret.getTransactionId());
        assertEquals(1,
                ret.getTransactionData()
                        .getTransactionDispatchControlInformation()
                        .getRetryCount());
    }

    /**
     * Test deserialize invalid.
     */
    @Test(expected = SystemException.class)
    public void testDeserializeInValid() {
        String s = "test";
        transactionMessageConverter.deserialize(s, true);
    }

    /**
     * Test deserialize invalid. transactiondata -> empty.
     */
    @Test(expected = ValidationException.class)
    public void testDeserialize_notransactiondata() {
        String s = "{\"transactionId\":\"1512469280164f0fba887-b601-481b-8a9c-321b581ecf72\"}";
        transactionMessageConverter.deserialize(s, true);
    }

    /**
     * Test deserialize invalid. transactiondata businessTransactionData -> empty
     */
    @Test(expected = ValidationException.class)
    public void testDeserialize_nobusinessTransactionData() {
        String s = "{\"transactionId\":\"1512469280164f0fba887-b601-481b-8a9c-321b581ecf72\","
                + "\"transactionData\":{"
                + "\"requestHeaderMap\":{\"accept-encoding\":\"gzip,deflate\",\"content-type\":\"application/json\",\"content-length\":\"628\",\"host\":\"localhost:8080\",\"connection\":\"Keep-Alive\",\"user-agent\":\"Apache-HttpClient/4.1.1 (java 1.5)\"},"
                + " \"resourceMap\":{\"TRANSACTION_TYPE\":\"55\",\"LANGUAGE\":\"en\",\"REGION\":\"ca\",\"BRAND\":\"uq\",\"CHANNEL_CODE\":\"channel\",\"STORE_CODE\":\"store\",\"INTEGRATED_ORDER_ID\":\"order\",\"ORDER_SUB_NUMBER\":\"ordersub\",\"UPDATE_TYPE\":\"update\"}"
                + "}}";
        transactionMessageConverter.deserialize(s, true);
    }

    /**
     * Test deserializeTransactionData valid.
     */
    @Test(expected = SystemException.class)
    public void testDeserializeTransactionDataInValid() {
        String s = "test";
        transactionMessageConverter.deserializeTransactionData(s, ReceiveMessage.class, true);
    }

    /**
     * Test serialize null.
     */
    @Test
    public void testDeserializeWrappingTransactionDataStringNull() {

        String s = null;
        final Object orderInfo = transactionMessageConverter.deserializeWrappingTransactionData(s,
                Object.class, true);
        assertNull(orderInfo);
    }

    /**
     * Test serialize null.
     */
    @Test
    public void testDeserializeWrappingTransactionDataTransactionMessageNull() {

        ReceiveMessage s = null;
        final Object orderInfo = transactionMessageConverter.deserializeWrappingTransactionData(s,
                Object.class, true);
        assertNull(orderInfo);
    }

    /**
     * Test serialize OrderInfo.
     */
    @Test
    public void testDeserializeWrappingTransactionDataOrderInfoValid() {

        ReceiveMessage message = new ReceiveMessage();
        message.setTransactionId("1");
        message.setTransactionData(new TransactionData());
        message.getTransactionData()
                .setBusinessTransactionData("{\"businessDate\":\"09065900\",\"posNo\":\"003\""
                        + ", \"receiptNo\":\"00175\", \"salesTime\":\"220300123\", \"transactionType\":\"10\""
                        + ", \"detail\":[{\"salesId\":\"10001\",\"itemCode\":\"10101010\", \"salesNum\":\"5\""
                        + ", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"salesId\":\"10002\""
                        + ",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\""
                        + ", \"itemSales\":\"50000\"},{\"salesId\":\"10003\",\"itemCode\":\"10101010\""
                        + ", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"}]}");

        // Map to order history from subscribed message.
        final OrderInfo orderInfo = transactionMessageConverter
                .deserializeWrappingTransactionData(message, OrderInfo.class, true);

        assertNotNull(orderInfo);
        assertEquals("09065900", orderInfo.getBusinessDate());
        assertEquals("003", orderInfo.getPosNo());
        assertEquals("00175", orderInfo.getReceiptNo());
        assertEquals("220300123", orderInfo.getSalesTime());
        assertEquals("10", orderInfo.getTransactionType());
        assertEquals(3, orderInfo.getDetail().size());

        assertEquals("10001", orderInfo.getDetail().get(0).getSalesId());
        assertEquals("10101010", orderInfo.getDetail().get(0).getItemCode());
        assertEquals(5, orderInfo.getDetail().get(0).getSalesNum());
        assertEquals(500, orderInfo.getDetail().get(0).getUnitPrice());
        assertEquals(50000, orderInfo.getDetail().get(0).getItemSales());

    }

    @Test
    public void testDeserializeTransactionData_TransactionData() {

        TransactionData transactionData = new TransactionData();
        transactionData
                .setBusinessTransactionData("{\"businessDate\":\"09065900\",\"posNo\":\"003\""
                        + ", \"receiptNo\":\"00175\", \"salesTime\":\"220300123\", \"transactionType\":\"10\""
                        + ", \"detail\":[{\"salesId\":\"10001\",\"itemCode\":\"10101010\", \"salesNum\":\"5\""
                        + ", \"unitPrice\":\"500\", \"itemSales\":\"50000\"},{\"salesId\":\"10002\""
                        + ",\"itemCode\":\"10101010\", \"salesNum\":\"5\", \"unitPrice\":\"500\""
                        + ", \"itemSales\":\"50000\"},{\"salesId\":\"10003\",\"itemCode\":\"10101010\""
                        + ", \"salesNum\":\"5\", \"unitPrice\":\"500\", \"itemSales\":\"50000\"}]}");

        // Map to order history from subscribed message.
        final OrderInfo orderInfo = transactionMessageConverter
                .deserializeTransactionData(transactionData, OrderInfo.class, true);

        assertNotNull(orderInfo);
        assertEquals("09065900", orderInfo.getBusinessDate());
        assertEquals("003", orderInfo.getPosNo());
        assertEquals("00175", orderInfo.getReceiptNo());
        assertEquals("220300123", orderInfo.getSalesTime());
        assertEquals("10", orderInfo.getTransactionType());
        assertEquals(3, orderInfo.getDetail().size());

        assertEquals("10001", orderInfo.getDetail().get(0).getSalesId());
        assertEquals("10101010", orderInfo.getDetail().get(0).getItemCode());
        assertEquals(5, orderInfo.getDetail().get(0).getSalesNum());
        assertEquals(500, orderInfo.getDetail().get(0).getUnitPrice());
        assertEquals(50000, orderInfo.getDetail().get(0).getItemSales());
    }

    @Test
    public void testDeserializeTransactionData_JSR310() {

        TransactionData transactionData = new TransactionData();
        transactionData
                .setBusinessTransactionData("{\"offsetDateTime\":\"2018-02-26T12:00:00.000Z\","
                        + "\"zonedDateTime\":\"2018-02-26T12:00:00.000Z\","
                        + "\"localDateTime\":\"2018-02-26T12:00:00.000Z\"}");

        // Map to order history from subscribed message.
        final OrderInfoJSR310 orderInfo = transactionMessageConverter
                .deserializeTransactionData(transactionData, OrderInfoJSR310.class, true);

        assertNotNull(orderInfo);

        OffsetDateTime expectedOffsetDateTime =
                OffsetDateTime.of(2018, 2, 26, 12, 0, 0, 0, ZoneOffset.UTC);
        assertEquals(expectedOffsetDateTime, orderInfo.getOffsetDateTime());

        ZonedDateTime expectedZonedDateTime =
                ZonedDateTime.of(2018, 2, 26, 12, 0, 0, 0, ZoneId.of("UTC"));
        assertEquals(expectedZonedDateTime, orderInfo.getZonedDateTime());

        LocalDateTime expectedLocalZonedDateTime = LocalDateTime.of(2018, 2, 26, 12, 0, 0, 0);
        assertEquals(expectedLocalZonedDateTime, orderInfo.getLocalDateTime());


    }

    @Test
    public void testDeserializeTransactionData_TransactionData_null() {

        TransactionData transactionData = null;
        OrderInfo orderInfo = transactionMessageConverter
                .deserializeTransactionData(transactionData, OrderInfo.class, true);
        assertNull(orderInfo);

        transactionData = new TransactionData();
        transactionData.setBusinessTransactionData(null);
        orderInfo = transactionMessageConverter.deserializeTransactionData(transactionData,
                OrderInfo.class, true);
        assertNull(orderInfo);
    }

    @Test
    public void testSerializeTransactionData() {

        String businessData = "business data string";

        TransactionData transactionData = new TransactionData();
        transactionData.setBusinessTransactionData(businessData);

        String covertedJson = transactionMessageConverter.serializeTransactionData(transactionData);

        String expectedJson = "{businessTransactionData: \"" + businessData + "\"}";
        try {
            JSONAssert.assertEquals(expectedJson, covertedJson, JSONCompareMode.LENIENT);
        } catch (JSONException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeTransactionData_null() {
        assertNull(transactionMessageConverter.serializeTransactionData(null));
    }


    @Test
    public void testSerializeReceiveMessage() {

        String businessData = "business data string";

        TransactionData transactionData = new TransactionData();
        transactionData.setBusinessTransactionData(businessData);
        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.setTransactionId("11111");
        receiveMessage.setTransactionData(transactionData);

        String covertedJson = transactionMessageConverter.serializeReceiveMessage(receiveMessage);

        String expectedTransactionIdJson = "{transactionId: \"11111\"}";
        String expectedBusinessDataJson =
                "{transactionData: {businessTransactionData: \"" + businessData + "\"}}";
        try {
            JSONAssert.assertEquals(expectedTransactionIdJson, covertedJson,
                    JSONCompareMode.LENIENT);
            JSONAssert.assertEquals(expectedBusinessDataJson, covertedJson,
                    JSONCompareMode.LENIENT);
        } catch (JSONException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeReceiveMessage_null() {
        assertNull(transactionMessageConverter.serializeReceiveMessage(null));
    }
}

