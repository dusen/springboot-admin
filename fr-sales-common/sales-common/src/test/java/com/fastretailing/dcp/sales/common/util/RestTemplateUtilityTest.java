package com.fastretailing.dcp.sales.common.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import com.fastretailing.dcp.common.api.uri.UriResolver;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.common.util.ResultObjectUtility;
import com.fastretailing.dcp.sales.common.SalesCommonApplication;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.storeinventory.InventoryInformationResponse;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.storeinventory.SalesFiguresRequest;
import com.fastretailing.dcp.sales.common.resttemplate.constant.ItemKey;
import com.fastretailing.dcp.sales.common.resttemplate.constant.MessageKey;
import com.fastretailing.dcp.sales.common.resttemplate.constant.SerialNumber;
import com.fastretailing.dcp.sales.importtransaction.dto.CreateTransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;

/**
 * RestTemplate utility class test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesCommonApplication.class)
public class RestTemplateUtilityTest {

    /**
     * Rest template.
     */
    @Autowired
    private RestTemplate restTemplate;

    /** URI resolver. */
    @Autowired
    private UriResolver uriResolver;

    /**
     * Object mapper.
     **/
    @Autowired
    private ObjectMapper mapper;

    private static final String TRANSACTION_IMPORT_URI =
            "client.apiConfig.sales.uri.apis.transactionImport.post";

    /** Sales figures uri. */
    private static final String SALES_FIGURES_URI =
            "client.apiConfig.storeinventory.uri.apis.salesFigures.get";

    /**
     * Post for entity success.
     * 
     */
    @Test
    public void postForEntityWithSuccesTest() {
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

        RestTemplateUtility.postForEntity(restTemplate,
                uriResolver.getUriAccordingToServerType(TRANSACTION_IMPORT_URI), importData,
                CommonStatus.class, pathParameterMap);
    }

    /**
     * Post for entity api with 404.
     * 
     */
    @Test
    public void postForEntityWith4xxTest() {
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

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.expect(requestTo(
                "http://localhost:8080/sales/v1/uq/ca/transaction-import/EC/111111/1/1/INSERT"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));
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
            RestTemplateUtility.postForEntity(restTemplate,
                    uriResolver.getUriAccordingToServerType(TRANSACTION_IMPORT_URI), importData,
                    CommonStatus.class, pathParameterMap);
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
     * Post for entity api with 5XX.
     * 
     */
    @Test
    public void postForEntityWith5xxTest() {
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

        String errorFlag = "SystemError";
        try {
            RestTemplateUtility.postForEntity(restTemplate,
                    uriResolver.getUriAccordingToServerType(TRANSACTION_IMPORT_URI), importData,
                    CommonStatus.class, pathParameterMap);
        } catch (SystemException e) {
            assertEquals(errorFlag, "SystemError");
        }
    }

    /**
     * Get for entity api with success.
     * 
     */
    @Test
    public void getForEntityWithSuccesTest() {
        SalesFiguresRequest salesFiguresRequest = new SalesFiguresRequest();
        salesFiguresRequest.setStoreCode("112326");

        InventoryInformationResponse inventoryInformationResponse =
                new InventoryInformationResponse();
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        try {
            mockServer
                    .expect(requestTo(
                            "http://localhost:8080/storeinventory/v1/uq/ca/112326/sales-figures"))
                    .andRespond(withSuccess(mapper.writeValueAsString(inventoryInformationResponse),
                            MediaType.APPLICATION_JSON_UTF8));
        } catch (JsonProcessingException e) {
            fail();
        }
        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        pathParameterMap.put(ItemKey.STORE_CODE, salesFiguresRequest.getStoreCode());
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        RestTemplateUtility.getForEntity(restTemplate,
                uriResolver.getUriAccordingToServerType(SALES_FIGURES_URI), CommonStatus.class,
                pathParameterMap);
    }

    /**
     * Get for entity api with 404.
     * 
     */
    @Test
    public void rgetForEntityWith4xxTest() {
        SalesFiguresRequest salesFiguresRequest = new SalesFiguresRequest();
        salesFiguresRequest.setStoreCode("112326");

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer
                .expect(requestTo(
                        "http://localhost:8080/storeinventory/v1/uq/ca/112326/sales-figures"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        pathParameterMap.put(ItemKey.STORE_CODE, salesFiguresRequest.getStoreCode());
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        try {
            RestTemplateUtility.getForEntity(restTemplate,
                    uriResolver.getUriAccordingToServerType(SALES_FIGURES_URI), CommonStatus.class,
                    pathParameterMap);
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
     * Get for entity api with 5XX.
     * 
     */
    @Test
    public void getForEntityWith5xxTest() {

        String errorFlag = "SystemError";
        SalesFiguresRequest salesFiguresRequest = new SalesFiguresRequest();
        salesFiguresRequest.setStoreCode("112326");

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer
                .expect(requestTo(
                        "http://localhost:8080/storeinventory/v1/uq/ca/112326/sales-figures"))
                .andRespond(withServerError());

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        pathParameterMap.put(ItemKey.STORE_CODE, salesFiguresRequest.getStoreCode());
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        try {
            RestTemplateUtility.getForEntity(restTemplate,
                    uriResolver.getUriAccordingToServerType(SALES_FIGURES_URI), CommonStatus.class,
                    pathParameterMap);
        } catch (SystemException e) {
            assertEquals(errorFlag, "SystemError");
        }
    }
}
