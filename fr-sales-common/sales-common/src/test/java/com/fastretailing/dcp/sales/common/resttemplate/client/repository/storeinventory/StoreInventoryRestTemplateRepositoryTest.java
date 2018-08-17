/**
 * @(#)StoreInventoryRestTemplateRepositoryTest.java
 *
 *                                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.repository.storeinventory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import java.util.HashMap;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
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

/**
 * Unit test class of rest template part for store inventory API.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesCommonApplication.class)
public class StoreInventoryRestTemplateRepositoryTest {
    /**
     * Store inventory rest template repository.
     */
    @Autowired
    private StoreInventoryRestTemplateRepository storeInventoryRestTemplateRepository;

    /**
     * Object mapper.
     **/
    @Autowired
    private ObjectMapper mapper;

    /**
     * Rest template.
     */
    @Autowired
    RestTemplate restTemplate;

    /**
     * Call store inventory api with success.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testRequestSalesFiguresWithSucces() throws Exception {
        SalesFiguresRequest salesFiguresRequest = new SalesFiguresRequest();
        salesFiguresRequest.setStoreCode("112326");

        InventoryInformationResponse inventoryInformationResponse =
                new InventoryInformationResponse();
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer
                .expect(requestTo(
                        "http://localhost:8080/storeinventory/v1/uq/ca/112326/sales-figures"))
                .andRespond(withSuccess(mapper.writeValueAsString(inventoryInformationResponse),
                        MediaType.APPLICATION_JSON_UTF8));

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        pathParameterMap.put(ItemKey.STORE_CODE, salesFiguresRequest.getStoreCode());
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        storeInventoryRestTemplateRepository.requestSalesFigures(salesFiguresRequest);
    }

    /**
     * Call store inventory api with 404.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void requestSalesFiguresWith4xx() throws Exception {
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
            storeInventoryRestTemplateRepository.requestSalesFigures(salesFiguresRequest);
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
     * Call store inventory api with 5XX.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void requestSalesFiguresWith5xx() throws Exception {

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
            storeInventoryRestTemplateRepository.requestSalesFigures(salesFiguresRequest);
        } catch (SystemException e) {
            assertEquals(errorFlag, "SystemError");
        }
    }
}
