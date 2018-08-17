/**
 * @(#)GetReportListControllerTest.java
 * 
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.service.GetReportListService;
import com.fastretailing.dcp.storecommon.report.dto.GetReportListRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetReportListControllerTest {
    /** Mock of Similar request to SpringMVC. */
    @Autowired
    private MockMvc mockMvc;

    /** Create UpdateReportStatusService Mock. */
    @MockBean
    private GetReportListService mockService;

    /** Object Mapper. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** Test URL. */
    private static final String TARGET_PATH = "/uq/japan/stores/store001/reports";

    /**
     * Target method：post. Condition：All Parameters Correct. Verification result confirmation：The
     * acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testNormal() throws Exception {
        // Prepare Expected Parameter
        GetReportListRequest request = new GetReportListRequest();
        List<String> receiptNumberList = new ArrayList<>();
        receiptNumberList.add("TestReceiptNumberAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        request.setReceiptNumberList(receiptNumberList);
        request.setStoreCode("StoreAAAAA");

        ReportCreateStatus oneReportCreateStatusDto = new ReportCreateStatus();
        oneReportCreateStatusDto
                .setReceiptNumber("TestReceiptNumberAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        oneReportCreateStatusDto.setStoreCode("StoreAAAAA");
        oneReportCreateStatusDto.setCreateReportStatus(1);

        List<ReportCreateStatus> expectedResult = new ArrayList<ReportCreateStatus>();
        expectedResult.add(oneReportCreateStatusDto);

        when((mockService).getReportList(anyObject())).thenReturn(expectedResult);

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(post(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Target method：post. Condition：StoreCode is null. Verification result confirmation：The
     * acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testStoreCodeNull() throws Exception {
        // Prepare Expected Parameter
        GetReportListRequest request = new GetReportListRequest();
        List<String> receiptNumberList = new ArrayList<String>();
        receiptNumberList.add("TestReceiptNumberAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        request.setReceiptNumberList(receiptNumberList);

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(post(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Target method：post. Condition：StoreCode is blank. Verification result confirmation：The
     * acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testStoreCodeBlank() throws Exception {
        // Prepare Expected Parameter
        GetReportListRequest request = new GetReportListRequest();
        List<String> receiptNumberList = new ArrayList<String>();
        receiptNumberList.add("TestReceiptNumberAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        request.setReceiptNumberList(receiptNumberList);
        request.setStoreCode("");

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(post(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Target method：post. Condition：StoreCode is over figure(11).. Verification result
     * confirmation：The acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testStoreCodeOverFigure() throws Exception {
        // Prepare Expected Parameter
        GetReportListRequest request = new GetReportListRequest();
        List<String> receiptNumberList = new ArrayList<String>();
        receiptNumberList.add("TestReceiptNumberAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        request.setReceiptNumberList(receiptNumberList);
        request.setStoreCode("StoreAAAAAB");

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(post(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
