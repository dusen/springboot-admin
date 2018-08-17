/**
 * @(#)GetReportDataControllerTest.java
 * 
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.storecommon.report.api.service.GetReportDataService;
import com.fastretailing.dcp.storecommon.report.dto.GetReportDataResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetReportDataControllerTest {
    /** Mock of Similar request to SpringMVC. */
    @Autowired
    private MockMvc mockMvc;

    /** Create GetReportDataService Mock. */
    @MockBean
    private GetReportDataService mockService;

    /** Object Mapper. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** Test URL. */
    private static final String TARGET_PATH = "/uq/japan/stores/store001/reports";

    /**
     * Target method：get. Condition：All Parameters Correct. Verification result confirmation：The
     * acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testNormal() throws Exception {
        // Prepare Expected Parameter

        String downloadUrlExpected = "download url";

        when((mockService).getReportDownloadUrl(anyObject())).thenReturn(downloadUrlExpected);

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString("111");
        // Call controller
        ResultActions res = mockMvc
                .perform(get(TARGET_PATH + "/testnumber").contentType(MediaType.APPLICATION_JSON)
                        .content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = res.andReturn();
        GetReportDataResponse response = OBJECT_MAPPER.readValue(
                mvcResult.getResponse().getContentAsByteArray(), GetReportDataResponse.class);
        assertThat(response.getReportDownloadUrl(), is(downloadUrlExpected));
    }

    /**
     * Target method：get. Condition：ReceptionNumber is null. Verification result confirmation：The
     * acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testReceptionNumberNull() throws Exception {

        // Call controller
        ResultActions res = mockMvc.perform(get(TARGET_PATH));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    /**
     * Target method：get. Condition：ReceptionNumber is blank. Verification result confirmation：The
     * acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testReceptionNumberBlank() throws Exception {
        // Prepare Expected Parameter
        String receiptNumber = "";
        // Call controller
        ResultActions res = mockMvc.perform(get(TARGET_PATH + "/" + receiptNumber));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
