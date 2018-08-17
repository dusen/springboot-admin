/**
 * @(#)SalesRestTemplateRepositoryTest.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.common.util.ResultObjectUtility;
import com.fastretailing.dcp.sales.common.SalesCommonApplication;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.SalesRestTemplateRepository;
import com.fastretailing.dcp.sales.common.resttemplate.constant.ItemKey;
import com.fastretailing.dcp.sales.common.resttemplate.constant.MessageKey;
import com.fastretailing.dcp.sales.common.resttemplate.constant.SerialNumber;
import com.fastretailing.dcp.sales.importtransaction.dto.AlterationPayOffImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.AlterationPayOffImportMultiData;
import com.fastretailing.dcp.sales.importtransaction.dto.CreateTransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;

/**
 * Unit test class of rest template part sales API.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesCommonApplication.class)
public class SalesRestTemplateRepositoryTest {
    /**
     * Store sales rest template repository.
     */
    @Autowired
    private SalesRestTemplateRepository salesRestTemplateRepository;

    /**
     * Object mapper.
     **/
    @Autowired
    private ObjectMapper mapper;

    /**
     * Rest template.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Call sales transaction import api with success.
     * 
     */
    @Test
    public void callTransactionImportDataWithSuccesTest() {
        CreateTransactionImportData importData = new CreateTransactionImportData();

        importData.setStoreCode("111111");
        importData.setChannelCode("EC");
        importData.setIntegratedOrderId("1");
        importData.setUpdateType("INSERT");
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setOrderSubNumber(1);
        transactionList.add(transaction);
        importData.setTransactionList(transactionList);

        CommonStatus commonStatus = new CommonStatus();
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        try {
            mockServer.expect(requestTo(
                    "http://localhost:8080/sales/v1/uq/ca/transaction-import/EC/111111/1/1/INSERT"))
                    .andRespond(withSuccess(mapper.writeValueAsString(commonStatus),
                            MediaType.APPLICATION_JSON_UTF8));
        } catch (JsonProcessingException e) {
            fail();
        }

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        pathParameterMap.put(ItemKey.CHANNEL_CODE, "EC");
        pathParameterMap.put(ItemKey.STORE_CODE, "111111");
        pathParameterMap.put(ItemKey.INTEGRATED_ORDER_ID, "1");
        pathParameterMap.put(ItemKey.ORDER_SUB_NUMBER, "1");
        pathParameterMap.put(ItemKey.UPDATE_TYPE, "INSERT");
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        salesRestTemplateRepository.callTransactionImport(importData);
    }

    /**
     * Call sales transaction import api with 404.
     * 
     */
    @Test
    public void callTransactionImportWith4xxTest() {
        CreateTransactionImportData importData = new CreateTransactionImportData();

        importData.setStoreCode("111111");
        importData.setChannelCode("EC");
        importData.setIntegratedOrderId("1");
        importData.setUpdateType("INSERT");
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setOrderSubNumber(1);
        transactionList.add(transaction);
        importData.setTransactionList(transactionList);

        CommonStatus commonStatus = new CommonStatus();
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        try {

            mockServer.expect(requestTo(
                    "http://localhost:8080/sales/v1/uq/ca/transaction-import/EC/111111/1/1/INSERT"))
                    .andRespond(withSuccess(mapper.writeValueAsString(commonStatus),
                            MediaType.APPLICATION_JSON_UTF8));
        } catch (JsonProcessingException e) {
            fail();
        }

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        pathParameterMap.put(ItemKey.CHANNEL_CODE, "EC");
        pathParameterMap.put(ItemKey.STORE_CODE, "111111");
        pathParameterMap.put(ItemKey.INTEGRATED_ORDER_ID, "1");
        pathParameterMap.put(ItemKey.ORDER_SUB_NUMBER, "1");
        pathParameterMap.put(ItemKey.UPDATE_TYPE, "INSERT");
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        try {
            salesRestTemplateRepository.callTransactionImport(importData);
        } catch (BusinessException e) {
            final ResultObject resultObject = ResultObjectUtility.ResultObjectBuilder.getBuilder()
                    .withName(ErrorName.Business.BUSINESS_CHECK_ERROR)
                    .withDebugId(LogLevel.ERROR, SerialNumber.SERIAL_NUMBER_69900001)
                    .withMessage(MessageKey.BUSINESS_ERROR_MESSAGE_KEY)
                    .build();
            assertThat(e.getResultObject(), is(resultObject));
        }
    }

    /**
     * Call sales transaction import api with 5XX.
     * 
     */
    @Test
    public void callTransactionImportWith5xxTest() {
        CreateTransactionImportData importData = new CreateTransactionImportData();
        importData.setStoreCode("111111");
        importData.setChannelCode("EC");
        importData.setIntegratedOrderId("1");
        importData.setUpdateType("INSERT");
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setOrderSubNumber(1);
        transactionList.add(transaction);
        importData.setTransactionList(transactionList);

        String errorFlag = "SystemError";

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.expect(requestTo(
                "http://localhost:8080/sales/v1/uq/ca/transaction-import/EC/111111/1/1/INSERT"))
                .andRespond(withServerError());

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        pathParameterMap.put(ItemKey.CHANNEL_CODE, "EC");
        pathParameterMap.put(ItemKey.STORE_CODE, "111111");
        pathParameterMap.put(ItemKey.INTEGRATED_ORDER_ID, "1");
        pathParameterMap.put(ItemKey.ORDER_SUB_NUMBER, "1");
        pathParameterMap.put(ItemKey.UPDATE_TYPE, "INSERT");
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        try {
            salesRestTemplateRepository.callTransactionImport(importData);
        } catch (SystemException e) {
            assertEquals(errorFlag, "SystemError");
        }
    }


    /**
     * Call sales alteration pay off api with success.
     * 
     */
    @Test
    public void callAlterationPayOffDataWithSuccesTest() {
        AlterationPayOffImportData entity = new AlterationPayOffImportData();
        entity.setCashRegisterNumber(1);
        entity.setDataCorrectionUserCode("User");
        entity.setPayOffDate("2018-07-11");
        entity.setStoreCode("111111");

        AlterationPayOffImportMultiData alterationPayOffImportMultiData =
                new AlterationPayOffImportMultiData();
        ArrayList<AlterationPayOffImportData> payOffImportDataList = new ArrayList<>();
        payOffImportDataList.add(entity);
        alterationPayOffImportMultiData.setPayoffDataFormList(payOffImportDataList);

        CommonStatus commonStatus = new CommonStatus();
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        try {
            mockServer
                    .expect(requestTo(
                            "http://localhost:8080/sales/v1/uq/ca/alteration-pay-off-data-import"))
                    .andRespond(withSuccess(mapper.writeValueAsString(commonStatus),
                            MediaType.APPLICATION_JSON_UTF8));
        } catch (JsonProcessingException e) {
            fail();
        }

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        salesRestTemplateRepository.callAlterationPayOffData(alterationPayOffImportMultiData);
    }

    /**
     * Call sales alteration pay off api with 404.
     * 
     */
    @Test
    public void callAlterationPayOffWith4xxTest() {
        AlterationPayOffImportData entity = new AlterationPayOffImportData();
        entity.setCashRegisterNumber(1);
        entity.setDataCorrectionUserCode("User");
        entity.setPayOffDate("2018-07-11");
        entity.setStoreCode("111111");

        AlterationPayOffImportMultiData alterationPayOffImportMultiData =
                new AlterationPayOffImportMultiData();
        ArrayList<AlterationPayOffImportData> payOffImportDataList = new ArrayList<>();
        payOffImportDataList.add(entity);
        alterationPayOffImportMultiData.setPayoffDataFormList(payOffImportDataList);

        CommonStatus commonStatus = new CommonStatus();
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        try {

            mockServer
                    .expect(requestTo(
                            "http://localhost:8080/sales/v1/uq/ca/alteration-pay-off-data-import"))
                    .andRespond(withSuccess(mapper.writeValueAsString(commonStatus),
                            MediaType.APPLICATION_JSON_UTF8));
        } catch (JsonProcessingException e) {
            fail();
        }

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        try {
            salesRestTemplateRepository.callAlterationPayOffData(alterationPayOffImportMultiData);
        } catch (BusinessException e) {
            final ResultObject resultObject = ResultObjectUtility.ResultObjectBuilder.getBuilder()
                    .withName(ErrorName.Business.BUSINESS_CHECK_ERROR)
                    .withDebugId(LogLevel.ERROR, SerialNumber.SERIAL_NUMBER_69900001)
                    .withMessage(MessageKey.BUSINESS_ERROR_MESSAGE_KEY)
                    .build();
            assertThat(e.getResultObject(), is(resultObject));
        }
    }

    /**
     * Call sales alteration pay off api with 5XX.
     * 
     */
    @Test
    public void callAlterationPayOffWith5xxTest() {
        AlterationPayOffImportData entity = new AlterationPayOffImportData();
        entity.setCashRegisterNumber(1);
        entity.setDataCorrectionUserCode("User");
        entity.setPayOffDate("2018-07-11");
        entity.setStoreCode("111111");

        AlterationPayOffImportMultiData alterationPayOffImportMultiData =
                new AlterationPayOffImportMultiData();
        ArrayList<AlterationPayOffImportData> payOffImportDataList = new ArrayList<>();
        payOffImportDataList.add(entity);
        alterationPayOffImportMultiData.setPayoffDataFormList(payOffImportDataList);

        String errorFlag = "SystemError";

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer
                .expect(requestTo(
                        "http://localhost:8080/sales/v1/uq/ca/alteration-pay-off-data-import"))
                .andRespond(withServerError());
        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        try {
            salesRestTemplateRepository.callAlterationPayOffData(alterationPayOffImportMultiData);
        } catch (SystemException e) {
            assertEquals(errorFlag, "SystemError");
        }
    }

}
