/**
 * @(#)PdfReportGeneratorTest.java
 * 
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.client;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.validation.BindException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.report.ReportCommonException;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportMaster;
import com.fastretailing.dcp.storecommon.report.api.repository.GeneralStoreTimeZoneMapper;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportCreateStatusMapper;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportMasterMapper;
import com.fastretailing.dcp.storecommon.report.dto.GeneratePdfReportRequest;
import com.fastretailing.dcp.storecommon.report.dto.GenerateReportResponse;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

@SpringBootTest
@RunWith(SpringRunner.class)
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_report.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_report.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class PdfReportGeneratorTest {

    /** Component class to target of testing . */
    @Autowired
    private PdfReportGenerator createPdfComponent;

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

    /** Create a mock of access DB for store general purpose table. */
    @MockBean
    private GeneralStoreTimeZoneMapper generalStoreTimeZoneMapper;

    private GeneratePdfReportRequest inputGeneratePdfEntity;

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

        inputGeneratePdfEntity = new GeneratePdfReportRequest();
        inputGeneratePdfEntity.setCountryCode("JAPAN");
        inputGeneratePdfEntity.setStoreCode("111111");
        inputGeneratePdfEntity.setViewStoreCode("viewstorecode001");
        inputGeneratePdfEntity.setStoreName("StoreName");
        inputGeneratePdfEntity.setSystemId("SIV");
        inputGeneratePdfEntity.setReportId("12345678901234");
        inputGeneratePdfEntity.setReportTitle("1234567890");
        inputGeneratePdfEntity.setCreatedReportBucketName("1234567890");
        inputGeneratePdfEntity.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0,
                0, ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));
        inputGeneratePdfEntity.setAsyncSetting(false);

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
    @DatabaseSetup("GeneralStoreTimeZoneMapper_Init_Select.xml")
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
        when(mockReportMasterMapper.select(anyString(), anyString(), anyString()))
                .thenReturn(mockReportMasterDto);

        when(generalStoreTimeZoneMapper.selectGeneralItem(anyString(), anyString(), anyString(),
                anyString(), anyString())).thenReturn("Europe/Rome");

        when(mockReportCreateStatusMapper.updateCreateStatus(anyObject())).thenReturn(1);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(RequestPathVariableHolder.KEY_BRAND, "uq");
        pathVariableMap.put(RequestPathVariableHolder.KEY_REGION, "japan");
        RequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        // RestTemplate access mock.
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer
                .expect(requestTo(
                        "http://localhost:8080/inventory/v1/uq/japan/stores/111111/reports/pdf"))
                .andRespond(withSuccess(mapper.writeValueAsString(response),
                        MediaType.APPLICATION_JSON_UTF8));

        // Verification start
        // Calling Component.
        GenerateReportResponse resultOutputGenerateReportEntity =
                createPdfComponent.generateReport(inputGeneratePdfEntity);

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
    public void testGetReportResponseNg() throws Exception {

        final ReportMaster mockReportMasterDto = new ReportMaster();
        mockReportMasterDto.setReportFormBucketName("bucketname");
        mockReportMasterDto.setReportFormKeyName("keyname");
        mockReportMasterDto.setReportPreservationPeriod(30);

        // DB access mock.
        when(mockReportCreateStatusMapper.insertReportCreateStatus(anyObject())).thenReturn(1);
        when(mockReportMasterMapper.select(anyString(), anyString(), anyString())).thenReturn(null);

        try {
            // Calling Component.
            createPdfComponent.generateReport(inputGeneratePdfEntity);

            // Confirm that DB access processing is called.
            fail();
        } catch (ReportCommonException e) {
            assertThat(e.getMessage(),
                    is("There is no report master data. ReceiptNumber=12345678901234."));
        }

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
    public void testGetReportResponseHttpClientErrorException() throws Exception {

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
        expectOutputGenerateReportEntity.setGenerateReportResult(3);

        // DB access mock.
        when(mockReportCreateStatusMapper.insertReportCreateStatus(anyObject())).thenReturn(1);
        when(mockReportMasterMapper.select(anyString(), anyString(), anyString()))
                .thenReturn(mockReportMasterDto);
        when(generalStoreTimeZoneMapper.selectGeneralItem(anyString(), anyString(), anyString(),
                anyString(), anyString())).thenReturn("Europe/Rome");

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(RequestPathVariableHolder.KEY_BRAND, "uq");
        pathVariableMap.put(RequestPathVariableHolder.KEY_REGION, "japan");
        RequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        ObjectMapper objectMapper = new ObjectMapper();
        ResultObject ro =
                new ResultObject(ErrorName.Basis.VALIDATION_ERROR, "debug111", "error message");

        // RestTemplate access mock.
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer
                .expect(requestTo(
                        "http://localhost:8080/inventory/v1/uq/japan/stores/111111/reports/pdf"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(ro),
                        MediaType.APPLICATION_JSON_UTF8));

        try {
            // Verification start
            // Calling Component.
            createPdfComponent.generateReport(inputGeneratePdfEntity);
        } catch (ReportCommonException e) {
            assertNull(e.getErrorResult());
        }
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
    public void testCountryCodeNull() throws Exception {

        inputGeneratePdfEntity.setCountryCode("");
        inputGeneratePdfEntity.setStoreCode("1230");
        inputGeneratePdfEntity.setSystemId("SIV");
        inputGeneratePdfEntity.setReportId("12345678901234");
        inputGeneratePdfEntity.setReportTitle("1234567890");
        inputGeneratePdfEntity.setCreatedReportBucketName("1234567890");
        inputGeneratePdfEntity.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0,
                0, ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));
        inputGeneratePdfEntity.setAsyncSetting(false);

        // Verification start
        // Calling Component.
        try {
            createPdfComponent.generateReport(inputGeneratePdfEntity);
            fail("exception was not occured.");
        } catch (BindException e) {
            assertNotNull(e.getFieldError("countryCode"));
        } catch (Exception e) {
            fail("Expected exception was not occured.");
        }

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
    public void testStoreCodeNull() throws Exception {

        inputGeneratePdfEntity.setCountryCode("JAPAN");
        inputGeneratePdfEntity.setStoreCode("");
        inputGeneratePdfEntity.setSystemId("SIV");
        inputGeneratePdfEntity.setReportId("12345678901234");
        inputGeneratePdfEntity.setReportTitle("1234567890");
        inputGeneratePdfEntity.setCreatedReportBucketName("1234567890");
        inputGeneratePdfEntity.setCreateReportBusinessDay(OffsetDateTime.of(2017, 12, 20, 0, 0, 0,
                0, ZoneOffset.of(ZoneId.of("UTC").normalized().toString())));
        inputGeneratePdfEntity.setAsyncSetting(false);

        // Verification start
        // Calling Component.
        try {
            createPdfComponent.generateReport(inputGeneratePdfEntity);
            fail("exception was not occured.");
        } catch (BindException e) {
            assertNotNull(e.getFieldError("storeCode"));
        } catch (Exception e) {
            fail("Expected exception was not occured.");
        }
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
    public void testInputAllNull() throws Exception {

        inputGeneratePdfEntity.setCountryCode("");
        inputGeneratePdfEntity.setStoreCode("");
        inputGeneratePdfEntity.setSystemId("");
        inputGeneratePdfEntity.setReportId("");
        inputGeneratePdfEntity.setReportTitle("");
        inputGeneratePdfEntity.setCreatedReportBucketName("");
        inputGeneratePdfEntity.setCreateReportBusinessDay(null);
        inputGeneratePdfEntity.setAsyncSetting(null);

        // Verification start
        // Calling Component.
        try {
            createPdfComponent.generateReport(inputGeneratePdfEntity);
            fail("exception was not occured.");
        } catch (BindException e) {
            assertTrue(e.hasErrors());
        } catch (Exception e) {
            fail("Expected exception was not occured.");
        }
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
    public void testReportIdTooLong() throws Exception {
        inputGeneratePdfEntity.setReportId("123456789012345");

        // Verification start
        // Calling Component.
        try {
            createPdfComponent.generateReport(inputGeneratePdfEntity);
            fail("exception was not occured.");
        } catch (BindException e) {
            assertTrue(e.hasErrors());
        } catch (Exception e) {
            fail("Expected exception was not occured.");
        }
    }
}
