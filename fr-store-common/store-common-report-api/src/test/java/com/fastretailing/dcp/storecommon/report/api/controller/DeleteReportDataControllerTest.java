/**
 * @(#)DeleteReportDataControllerTest.java
 * 
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.test.web.servlet.MvcResult;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.service.DeleteReportDataService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteReportDataControllerTest {
    /** Mock of Similar request to SpringMVC. */
    @Autowired
    private MockMvc mockMvc;

    /** Create DeleteReportDataService Mock. */
    @MockBean
    private DeleteReportDataService mockService;

    /** Test URL. */
    private static final String TARGET_PATH = "/uq/japan/reports/delete";

    /**
     * Target method： delete. Condition：All Parameters Correct. Verification result confirmation：The
     * acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testNormal() throws Exception {
        List<ReportCreateStatus> expectedList = new ArrayList<>();
        ReportCreateStatus status = new ReportCreateStatus();
        status.setReceiptNumber("TestReceiptNumber");
        expectedList.add(status);
        when((mockService).getDeleteTargetList()).thenReturn(expectedList);
        when((mockService).deleteReport(status)).thenReturn(1);
        // Call controller
        MvcResult res = mockMvc.perform(delete(TARGET_PATH).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(200, res.getResponse().getStatus());
    }

    /**
     * Target method： delete. Condition：All Parameters Correct. Verification result confirmation：The
     * acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testS3DeleteError() throws Exception {
        List<ReportCreateStatus> expectedList = new ArrayList<>();
        ReportCreateStatus status = new ReportCreateStatus();
        status.setReceiptNumber("TestReceiptNumber");
        expectedList.add(status);
        when((mockService).getDeleteTargetList()).thenReturn(expectedList);
        when((mockService).deleteReport(status)).thenThrow(new BusinessException(new ResultObject(
                ErrorName.Business.BUSINESS_CHECK_ERROR, "E_tes_000000", "errorMsg")));
        // Call controller
        try {
            mockMvc.perform(delete(TARGET_PATH)).andReturn();
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BusinessException.class);
        }
    }
}
