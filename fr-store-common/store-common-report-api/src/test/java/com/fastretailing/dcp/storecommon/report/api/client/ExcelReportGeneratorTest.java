/**
 * @(#)ExcelReportGeneratorTest.java
 * 
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.client;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.validation.BindException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportMaster;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportCreateStatusMapper;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportMasterMapper;
import com.fastretailing.dcp.storecommon.report.api.util.ReportReceiptNumberComposer;
import com.fastretailing.dcp.storecommon.report.api.util.StoreLocalDateTimeConverter;
import com.fastretailing.dcp.storecommon.report.dto.GenerateExcelReportRequest;
import com.fastretailing.dcp.storecommon.report.dto.GenerateReportResponse;
import com.fastretailing.dcp.storecommon.report.dto.excel.ExcelCellFormatting;
import com.fastretailing.dcp.storecommon.report.dto.excel.ExcelCommonData;
import com.fastretailing.dcp.storecommon.report.dto.excel.ExcelDetailDefine;
import com.fastretailing.dcp.storecommon.report.dto.excel.ExcelDetailRecord;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelReportGeneratorTest {

    /** Component class to target of testing . */
    @Autowired
    private ExcelReportGenerator createExcelComponent;

    /** Rules for verifying that exceptions are thrown. */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /** ObjectMapper . */
    @Autowired
    private ObjectMapper mapper;

    /** Create a mock of access DB for report create status table. */
    @MockBean
    private ReportCreateStatusMapper mockReportCreateStatusMapper;

    /** Create a mock of access DB for report create status table. */
    @MockBean
    private ReportMasterMapper mockReportMasterMapper;

    @MockBean
    private StoreLocalDateTimeConverter storeLocalDateTimeConverter;

    @MockBean
    private ReportReceiptNumberComposer reportReceiptNumberComposer;

    private GenerateExcelReportRequest inputGenerateExcelReportEntity;

    /** RestTemplate. */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Test preProcessing. Perform initial setting of item information.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        inputGenerateExcelReportEntity = new GenerateExcelReportRequest();
        inputGenerateExcelReportEntity.setCountryCode("JAPAN");
        inputGenerateExcelReportEntity.setStoreCode("1230");
        inputGenerateExcelReportEntity.setViewStoreCode("view1230");
        inputGenerateExcelReportEntity.setStoreName("test store 1230");
        inputGenerateExcelReportEntity.setSystemId("SIV");
        inputGenerateExcelReportEntity.setReportId("12345678901234");
        inputGenerateExcelReportEntity.setReportTitle("1234567890");
        inputGenerateExcelReportEntity.setCreatedReportBucketName("1234567890");
        inputGenerateExcelReportEntity.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0,
                0, 0, 0, ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));
        inputGenerateExcelReportEntity.setCommonDataList(new ArrayList<ExcelCommonData>());
        inputGenerateExcelReportEntity.setDetailDefineList(new ArrayList<ExcelDetailDefine>());
        inputGenerateExcelReportEntity.setDetailRecordList(new ArrayList<ExcelDetailRecord>());
        inputGenerateExcelReportEntity.setCellFormattingList(new ArrayList<ExcelCellFormatting>());
    }

    /**
     * <UL>
     * <LI>Target createReprotRequest.
     * <LI>Condition：all parameter is expected.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testCreateReprotRequestNormal() throws Exception {
        final ReportMaster mockReportMasterDto = new ReportMaster();
        mockReportMasterDto.setReportFormBucketName("bucketname");
        mockReportMasterDto.setReportFormKeyName("keyname");
        mockReportMasterDto.setReportPreservationPeriod(30);

        GenerateReportResponse response = new GenerateReportResponse();
        response.setReceiptNumber("12345");
        response.setGenerateReportResult(2);
        response.setGenerateReportDetails("");
        response.setCreatedReportBucketName("BucketName");
        response.setCreatedReportKeyName("Key");

        // expected values.
        GenerateReportResponse expectOutputGenerateReportEntity = new GenerateReportResponse();
        expectOutputGenerateReportEntity
                .setGenerateReportResult(response.getGenerateReportResult());
        expectOutputGenerateReportEntity
                .setGenerateReportDetails(response.getGenerateReportDetails());

        // DB access mock.
        when(mockReportCreateStatusMapper.insertReportCreateStatus(anyObject())).thenReturn(1);
        when(mockReportMasterMapper.select(anyString(), anyString(), anyString()))
                .thenReturn(mockReportMasterDto);
        when(mockReportCreateStatusMapper.updateCreateStatus(anyObject())).thenReturn(1);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(RequestPathVariableHolder.KEY_BRAND, "uq");
        pathVariableMap.put(RequestPathVariableHolder.KEY_REGION, "japan");
        RequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);
        // RestTemplate access mock.
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer
                .expect(requestTo(
                        "http://localhost:8080/inventory/v1/uq/japan/stores/1230/reports/xlsx"))
                .andRespond(withSuccess(mapper.writeValueAsString(response),
                        MediaType.APPLICATION_JSON_UTF8));

        // Verification start
        // Calling Component.
        GenerateReportResponse resultOutputGenerateReportEntity =
                createExcelComponent.generateReport(inputGenerateExcelReportEntity);

        // Confirm result
        assertThat(resultOutputGenerateReportEntity.getGenerateReportResult(),
                is(expectOutputGenerateReportEntity.getGenerateReportResult()));
        assertThat(resultOutputGenerateReportEntity.getGenerateReportDetails(),
                is(expectOutputGenerateReportEntity.getGenerateReportDetails()));
    }

    /**
     * <UL>
     * <LI>Target createReprotRequest.
     * <LI>Condition：all parameter is expected.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testLocalecdNull() {

        inputGenerateExcelReportEntity.setCountryCode(null);
        inputGenerateExcelReportEntity.setStoreCode("1230");
        inputGenerateExcelReportEntity.setSystemId("SIV");
        inputGenerateExcelReportEntity.setReportId("12345678901234");
        inputGenerateExcelReportEntity.setReportTitle("1234567890");
        inputGenerateExcelReportEntity.setCreatedReportBucketName("1234567890");
        inputGenerateExcelReportEntity.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0,
                0, 0, 0, ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));

        // Verification start
        // Calling Component.
        try {
            createExcelComponent.generateReport(inputGenerateExcelReportEntity);
            fail("exception was not occured.");
        } catch (BindException e) {
            assertNotNull(e.getFieldError("countryCode"));
        } catch (Exception e) {
            fail("Expected exception was not occured.");
        }

    }
}
