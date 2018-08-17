/**
 * @(#)EdfRestTemplateRepositoryTest.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.repository.edf;

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
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.edf.EdfLinkageRequest;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.edf.EdfLinkageResponse;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.edf.GlobalHeaders;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.edf.EdfRestTemplateRepository;
import com.fastretailing.dcp.sales.common.resttemplate.constant.ItemKey;
import com.fastretailing.dcp.sales.common.resttemplate.constant.MessageKey;
import com.fastretailing.dcp.sales.common.resttemplate.constant.SerialNumber;

/**
 * Unit test class of rest template part for store inventory API.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesCommonApplication.class)
public class EdfRestTemplateRepositoryTest {
    /**
     * Store inventory rest template repository.
     */
    @Autowired
    private EdfRestTemplateRepository edfRestTemplateRepository;

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
    public void callEdfLinkageWithSucces() throws Exception {
        EdfLinkageRequest edfLinkageRequest = new EdfLinkageRequest();
        GlobalHeaders globalHeaders = new GlobalHeaders();
        globalHeaders.setStoreCode("111111");
        edfLinkageRequest.setGlobalHeaders(globalHeaders);

        EdfLinkageResponse edfLinkageResponse = new EdfLinkageResponse();
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.expect(requestTo(
                "http://localhost:8080/kong_context_path/edf/rest/EDR/SLS-daily-sales-report-create-00004"))
                .andRespond(withSuccess(mapper.writeValueAsString(edfLinkageResponse),
                        MediaType.APPLICATION_JSON_UTF8));

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        edfRestTemplateRepository.callEdfLinkage(edfLinkageRequest);
    }

    /**
     * Call store inventory api with 404.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void callEdfLinkageWith4xx() throws Exception {
        EdfLinkageRequest edfLinkageRequest = new EdfLinkageRequest();
        GlobalHeaders globalHeaders = new GlobalHeaders();
        globalHeaders.setStoreCode("111111");
        edfLinkageRequest.setGlobalHeaders(globalHeaders);

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.expect(requestTo(
                "http://localhost:8080/kong_context_path/edf/rest/EDR/SLS-daily-sales-report-create-00004"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);

        try {
            edfRestTemplateRepository.callEdfLinkage(edfLinkageRequest);
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
    public void callEdfLinkageWith5xx() throws Exception {

        EdfLinkageRequest edfLinkageRequest = new EdfLinkageRequest();
        GlobalHeaders globalHeaders = new GlobalHeaders();
        globalHeaders.setStoreCode("111111");
        edfLinkageRequest.setGlobalHeaders(globalHeaders);
        String errorFlag = "SystemError";

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.expect(requestTo(
                "http://localhost:8080/kong_context_path/edf/rest/EDR/SLS-daily-sales-report-create-00004"))
                .andRespond(withServerError());

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, "uq");
        pathParameterMap.put(ItemKey.REGION, "ca");
        RequestPathVariableHolder.setRequestPathVariableMap(pathParameterMap);
        try {
            edfRestTemplateRepository.callEdfLinkage(edfLinkageRequest);
        } catch (SystemException e) {
            assertEquals(errorFlag, "SystemError");
        }
    }
}
