/**
 * @(#)ReportMasterMapperTest.java
 * 
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.storecommon.report.api.PlatformReportApiApplication;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportMaster;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Unit test of ReportMasterMapper class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformReportApiApplication.class)
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_report.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_report.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ReportMasterMapperTest {

    /** DB access class. */
    @Autowired
    private ReportMasterMapper reportMasterMapper;

    /** Rules for verifying that exceptions are thrown. */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * <UL>
     * <LI>Target method：select.
     * <LI>Condition：Select One Data.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportMasterMapper_Init_Select.xml")
    public void testSelectReportMaster() throws Exception {
        // Prepare parameter
        String reportId = "TestReportId";
        String reportType = "PDF";
        String countryCode = "JPN";

        // Prepare Expected Dto
        ReportMaster expectedDto = new ReportMaster();
        expectedDto.setReportFormBucketName("S3BucketName");
        expectedDto.setReportFormKeyName("S3Key");
        expectedDto.setReportTitle("ReportTitle");
        expectedDto.setReportPreservationPeriod(8);
        expectedDto.setPrinterName("PrinterName");
        expectedDto.setOuterCommandExecuteFlag(false);
        expectedDto.setAutoPrintServerIpAddress("192.168.11.111");

        // Method execution
        ReportMaster actResult = reportMasterMapper.select(reportId, reportType, countryCode);

        // Confirm result
        assertThat(actResult, is(SamePropertyValuesAs.samePropertyValuesAs(expectedDto)));

    }

    /**
     * <UL>
     * <LI>Target method：testSelectReportMasterNodataReportId.
     * <LI>Condition：Select One Data.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportMasterMapper_Init_Select.xml")
    public void testSelectReportMasterNodataReportId() throws Exception {
        // Prepare parameter
        String reportId = "NoTestReportId";
        String reportType = "PDF";
        String countryCode = "JPN";

        // Method execution
        ReportMaster actResult = reportMasterMapper.select(reportId, reportType, countryCode);

        // Confirm result
        assertNull(actResult);
    }

    /**
     * <UL>
     * <LI>Target method：testSelectReportMasterNodataReportId.
     * <LI>Condition：Select One Data.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportMasterMapper_Init_Select.xml")
    public void testSelectReportMasterNodataReportType() throws Exception {
        // Prepare parameter
        String reportId = "TestReportId";
        String reportType = "EXCEL";
        String countryCode = "JPN";

        // Method execution
        ReportMaster actResult = reportMasterMapper.select(reportId, reportType, countryCode);

        // Confirm result
        assertNull(actResult);
    }

    /**
     * <UL>
     * <LI>Target method：testSelectReportMasterNodataReportId.
     * <LI>Condition：Select One Data.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportMasterMapper_Init_Select.xml")
    public void testSelectReportMasterNodataCountryCode() throws Exception {
        // Prepare parameter
        String reportId = "TestReportId";
        String reportType = "PDF";
        String countryCode = "USA";

        // Method execution
        ReportMaster actResult = reportMasterMapper.select(reportId, reportType, countryCode);

        // Confirm result
        assertNull(actResult);
    }

}
