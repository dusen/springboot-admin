/**
 * @(#)UpdateReportStatusControllerTest.java
 * 
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fastretailing.dcp.storecommon.report.api.service.UpdateReportStatusService;
import com.fastretailing.dcp.storecommon.report.dto.UpdateReportStatusRequest;

/**
 * Unit test of UpdateReportStatusController class.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UpdateReportStatusControllerTest {
    /** Mock of Similar request to SpringMVC. */
    @Autowired
    private MockMvc mockMvc;

    /** Create UpdateReportStatusService Mock. */
    @MockBean
    private UpdateReportStatusService mockService;

    /** Object Mapper. */
    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper().registerModule(new JavaTimeModule());

    /** Test URL. */
    private static final String TARGET_PATH = "/uq/japan/stores/store001/reports/status";

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：All Parameters Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testCreateStatusNormal() throws Exception {
        // Prepare Expected Parameter
        UpdateReportStatusRequest expectedDto = new UpdateReportStatusRequest();
        expectedDto.setReceiptNumber("TestReceiptNumberAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        expectedDto.setReportId("rpid1000");
        expectedDto.setReportType("type100");
        expectedDto.setCreateReportStatus(1);
        expectedDto.setAutoPrintStatus(2);
        expectedDto.setCreatedReportBucketName(
                "TestBucketAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        expectedDto
                .setCreatedReportKeyName("TestKeyAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        expectedDto.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0, 0,
                ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));
        expectedDto.setDeleteReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0, 0,
                ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));
        expectedDto.setPrinterName("printName");
        expectedDto.setOuterCommandExecuteFlag(Boolean.TRUE);

        when((mockService).updateCreateStatus(anyObject())).thenReturn(1);

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(expectedDto);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isOk());

        // verify(mockService).update(expectedDto);
    }

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：All Parameters Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAutoPrintStatusNormal() throws Exception {
        // Prepare Expected Parameter
        UpdateReportStatusRequest expectedDto = new UpdateReportStatusRequest();
        expectedDto.setReceiptNumber("TestReceiptNumberAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        expectedDto.setReportId("rpid1000");
        expectedDto.setReportType("type100");
        expectedDto.setCreateReportStatus(null);
        expectedDto.setAutoPrintStatus(2);
        expectedDto.setCreatedReportBucketName(
                "TestBucketAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        expectedDto
                .setCreatedReportKeyName("TestKeyAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        expectedDto.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0, 0,
                ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));
        expectedDto.setDeleteReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0, 0,
                ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));
        expectedDto.setPrinterName("printName");
        expectedDto.setOuterCommandExecuteFlag(Boolean.TRUE);

        when((mockService).updateAutoPrintStatus(anyObject())).thenReturn(1);

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(expectedDto);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isOk());

        // verify(mockService).update(expectedDto);
    }

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：All Parameters Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testBothStatusNull() throws Exception {
        // Prepare Expected Parameter
        UpdateReportStatusRequest expectedDto = new UpdateReportStatusRequest();
        expectedDto.setReceiptNumber("TestReceiptNumberAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        expectedDto.setReportId("rpid1000");
        expectedDto.setReportType("type100");
        expectedDto.setCreateReportStatus(null);
        expectedDto.setAutoPrintStatus(null);
        expectedDto.setCreatedReportBucketName(
                "TestBucketAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        expectedDto
                .setCreatedReportKeyName("TestKeyAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        expectedDto.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0, 0,
                ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));
        expectedDto.setDeleteReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0, 0,
                ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));
        expectedDto.setPrinterName("printName");
        expectedDto.setOuterCommandExecuteFlag(Boolean.TRUE);


        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(expectedDto);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().is4xxClientError());

        verify(mockService, never()).updateCreateStatus(expectedDto);
        verify(mockService, never()).updateAutoPrintStatus(expectedDto);
    }

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：ReceiptNumber is null.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testReceiptNumberNull() throws Exception {
        // Prepare parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReportId("TestReportID");
        request.setReportType("PDF");
        request.setCreateReportStatus(1);
        request.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0, 0,
                ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：ReceiptNumber is blank.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testReceiptNumberBlank() throws Exception {
        // Prepare parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("");
        request.setReportId("TestReportID");
        request.setReportType("PDF");
        request.setCreateReportStatus(1);
        request.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0, 0,
                ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：ReceiptNumber is over figure(51).
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testReceiptNumberOverFigure() throws Exception {
        // Prepare parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceiptNumberAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB");
        request.setReportId("TestReportID");
        request.setReportType("PDF");
        request.setCreateReportStatus(1);
        request.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0, 0,
                ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：CreateReportStatus is under number(-1).
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testCreateReportStatusUnderNumber() throws Exception {
        // Prepare parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceiptNumber");
        request.setReportId("TestReportID");
        request.setReportType("PDF");
        request.setCreateReportStatus(-1);
        request.setAutoPrintStatus(0);
        request.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0, 0,
                ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：CreateReportStatus is over number(4).
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testCreateReportStatusOverNumber() throws Exception {
        // Prepare parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceiptNumber");
        request.setReportId("TestReportID");
        request.setReportType("PDF");
        request.setCreateReportStatus(4);
        request.setAutoPrintStatus(0);
        request.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0, 0,
                ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：AutoPrintStatus is under number(-1).
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAutoPrintStatusUnderNumber() throws Exception {
        // Prepare parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceiptNumber");
        request.setAutoPrintStatus(-1);

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：AutoPrintStatus is over number(4).
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAutoPrintStatusOverNumber() throws Exception {
        // Prepare parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceiptNumber");
        request.setAutoPrintStatus(4);

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：S3BucketName is over figure(257).
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testS3BucketNameOverFigure() throws Exception {
        // Prepare Expected Parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceiptNumber");
        request.setCreateReportStatus(2);
        request.setCreatedReportBucketName("TestBucketAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB");
        request.setCreatedReportKeyName("TestKey");

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * <UL>
     * <LI>Target method：UpdateReportStatusRequestUpdate.
     * <LI>Condition：S3KeyName is over figure(257).
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testS3KeyNameOverFigure() throws Exception {
        // Prepare Expected Parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceiptNumber");
        request.setCreateReportStatus(2);
        request.setCreatedReportBucketName("TestBucket");
        request.setCreatedReportKeyName("TestKeyAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB");

        /* Start verification */
        String json = OBJECT_MAPPER.writeValueAsString(request);
        // Call controller
        ResultActions res = mockMvc
                .perform(put(TARGET_PATH).contentType(MediaType.APPLICATION_JSON).content(json));

        /* Verification result confirmation */
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
