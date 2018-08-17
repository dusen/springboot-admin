/**
 * @(#)ReportCreateStatusMapperTest.java
 * 
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.repository;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.storecommon.report.api.PlatformReportApiApplication;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Unit test of ReportCreateStatusMapper class.
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
public class ReportCreateStatusMapperTest {
    /** DB access class. */
    @Autowired
    private ReportCreateStatusMapper reportCreateStatusMapper;

    /** expect value of insert,update. */
    private static final int EXPECT_VALUE_INSERT_UPDATE = 1;

    /** expect value of zero update. */
    private static final int EXPECT_VALUE_ZERO_UPDATE = 0;

    /** Rules for verifying that exceptions are thrown. */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * <UL>
     * <LI>Target method：selectByReceiptNumber.
     * <LI>Condition：Select No Data.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Select_ReceiptNumber.xml")
    public void testGetByreceiptNumberZeroData() throws Exception {
        // Prepare parameter
        String receiptNumber = "NotExistreceiptNumber";

        // Method execution
        ReportCreateStatus actResult =
                reportCreateStatusMapper.selectByReceiptNumber(receiptNumber, null);

        // Confirm result
        assertNull(actResult);
    }

    /**
     * <UL>
     * <LI>Target method：selectByReceiptNumber.
     * <LI>Condition：Select One Data.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Select_ReceiptNumber.xml")
    public void testGetByreceiptNumberNomal() throws Exception {
        // Prepare Expected Dto
        ReportCreateStatus expectedDto = new ReportCreateStatus();
        expectedDto.setReceiptNumber("TestReceptionNumber");
        expectedDto.setReportId("TestReportId");
        expectedDto.setReportType("PDF");
        expectedDto.setStoreCode("Store001");
        expectedDto.setCreateReportStatus(1);
        expectedDto.setAutoPrintStatus(1);
        expectedDto.setCreateReportBusinessDay(
                ZonedDateTime.of(2017, 12, 20, 0, 0, 0, 0, ZoneId.of("+09:00")).toLocalDate());
        expectedDto.setDeleteReportBusinessDay(
                ZonedDateTime.of(2018, 12, 20, 0, 0, 0, 0, ZoneId.of("+09:00")).toLocalDate());
        expectedDto.setPrinterName("testprinter");
        expectedDto.setOuterCommandExecuteFlag(false);

        // Prepare parameter
        String receiptNumber = "TestReceptionNumber";

        // Method execution
        ReportCreateStatus actResult =
                reportCreateStatusMapper.selectByReceiptNumber(receiptNumber, null);

        // Confirm result
        assertThat(actResult, is(SamePropertyValuesAs.samePropertyValuesAs(expectedDto)));
    }

    /**
     * <UL>
     * <LI>Target method：selectByReceiptNumber.
     * <LI>Condition：receiptNumber Unmatch.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Select_ReceiptNumber.xml")
    public void testGetByreceiptNumberUnmatchreceiptNumber() throws Exception {
        // Prepare parameter
        String receiptNumber = "NotExistreceiptNumber";

        // Method execution
        ReportCreateStatus actResult =
                reportCreateStatusMapper.selectByReceiptNumber(receiptNumber, null);

        // Confirm result
        assertNull(actResult);
    }

    /**
     * <UL>
     * <LI>Target method：selectByBusinessDay.
     * <LI>Condition：Select One Data.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Select_BusinessDay.xml")
    public void testGetByBusinessDayOneData() throws Exception {
        // Expected Dto
        ReportCreateStatus expectedDto = new ReportCreateStatus();
        expectedDto.setReceiptNumber("TestReceptionNumber");
        expectedDto.setReportId("TestReportId");
        expectedDto.setReportType("PDF");
        expectedDto.setStoreCode("Store001");
        expectedDto.setCreateReportStatus(2);
        expectedDto.setAutoPrintStatus(1);
        expectedDto.setCreatedReportBucketName("TestBucket");
        expectedDto.setCreatedReportKeyName("TestKey");
        expectedDto.setCreateReportBusinessDay(
                ZonedDateTime.of(2017, 12, 20, 0, 0, 0, 0, ZoneId.of("+09:00")).toLocalDate());
        expectedDto.setDeleteReportBusinessDay(
                ZonedDateTime.of(2018, 12, 20, 0, 0, 0, 0, ZoneId.of("+09:00")).toLocalDate());
        expectedDto.setPrinterName("testprinter");
        expectedDto.setOuterCommandExecuteFlag(false);

        // Prepare parameter
        String reportId = "TestReportId";
        String storeCode = "Store001";
        LocalDate businessDayFrom = LocalDate.of(2017, 12, 20);
        LocalDate businessDayTo = LocalDate.of(2017, 12, 20);

        // Method execution
        List<ReportCreateStatus> actResult = reportCreateStatusMapper.selectByBusinessDay(reportId,
                storeCode, businessDayFrom, businessDayTo, null);

        // Check result list size
        assertEquals(1, actResult.size());

        // Result Dto
        ReportCreateStatus resultDto = actResult.get(0);

        // Confirm result
        assertThat(resultDto, is(SamePropertyValuesAs.samePropertyValuesAs(expectedDto)));
    }

    /**
     * <UL>
     * <LI>Target method：selectByBusinessDay.
     * <LI>Condition：Select Multiple Data.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Select_BusinessDay.xml")
    public void testGetByBusinessDayMultiData() throws Exception {
        // Expected reception number list
        List<String> expectedreceiptNumberList = new ArrayList<String>();
        expectedreceiptNumberList.add("TestReceptionNumber");
        expectedreceiptNumberList.add("TestReceptionNumber4");
        expectedreceiptNumberList.add("TestReceptionNumber7");

        // Prepare parameter
        String reportId = "TestReportId";
        String storeCode = "Store001";
        LocalDate businessDayFrom = LocalDate.of(2017, 12, 18);
        LocalDate businessDayTo = LocalDate.of(2017, 12, 20);

        // Method execution
        List<ReportCreateStatus> actResult = reportCreateStatusMapper.selectByBusinessDay(reportId,
                storeCode, businessDayFrom, businessDayTo, null);

        // Check result list size
        assertEquals(3, actResult.size());

        // Confirm result
        for (ReportCreateStatus oneDto : actResult) {
            assertThat(oneDto.getReceiptNumber(), anyOf(is("TestReceptionNumber"),
                    is("TestReceptionNumber4"), is("TestReceptionNumber7")));
        }

    }

    /**
     * <UL>
     * <LI>Target method：insertReportCreateStatus.
     * <LI>Condition：Insert Data is Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Insert.xml")
    @ExpectedDatabase(value = "ReportCreateStatusMapper_Expected_Insert.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertReportCreateStatus() throws Exception {
        // Prepare parameter
        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setReportId("TestReportId");
        reportCreateStatusDto.setReportType("PDF");
        reportCreateStatusDto.setStoreCode("Store001");
        reportCreateStatusDto.setCreateReportStatus(1);
        reportCreateStatusDto.setAutoPrintStatus(0);
        reportCreateStatusDto.setCreateReportBusinessDay(
                ZonedDateTime.of(2017, 12, 20, 0, 0, 0, 0, ZoneId.of("+09:00")).toLocalDate());
        reportCreateStatusDto.setDeleteReportBusinessDay(
                ZonedDateTime.of(2018, 12, 20, 0, 0, 0, 0, ZoneId.of("+09:00")).toLocalDate());
        reportCreateStatusDto.setPrinterName("testprinter");
        reportCreateStatusDto.setOuterCommandExecuteFlag(Boolean.FALSE);
        // 2017-12-01 06:00:09
        reportCreateStatusDto.setCreateDatetime(
                ZonedDateTime.of(2017, 12, 1, 6, 0, 9, 0, ZoneId.of("+09:00")).toLocalDateTime());

        // Method execution
        int actResult = reportCreateStatusMapper.insertReportCreateStatus(reportCreateStatusDto);

        // Confirm result
        assertEquals(EXPECT_VALUE_INSERT_UPDATE, actResult);
    }

    /**
     * <UL>
     * <LI>Target method：insertReportCreateStatus.
     * <LI>Condition：Insert Data is Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Insert_Duplicated.xml")
    @ExpectedDatabase(value = "ReportCreateStatusMapper_Expected_Insert_Duplicated.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertReportCreateStatusDuplicated() throws Exception {
        // Prepare parameter
        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setReportId("TestReportId");
        reportCreateStatusDto.setReportType("PDF");
        reportCreateStatusDto.setStoreCode("Store001");
        reportCreateStatusDto.setCreateReportStatus(1);
        reportCreateStatusDto.setCreateReportBusinessDay(
                ZonedDateTime.of(2017, 12, 20, 0, 0, 0, 0, ZoneId.of("+09:00")).toLocalDate());
        reportCreateStatusDto.setDeleteReportBusinessDay(
                ZonedDateTime.of(2017, 12, 20, 0, 0, 0, 0, ZoneId.of("+09:00")).toLocalDate());
        // 2017-12-01 06:00:09
        reportCreateStatusDto.setCreateDatetime(
                ZonedDateTime.of(2017, 12, 1, 6, 0, 9, 0, ZoneId.of("+09:00")).toLocalDateTime());

        expectedException.expect(DuplicateKeyException.class);

        // Method execution
        int actResult = reportCreateStatusMapper.insertReportCreateStatus(reportCreateStatusDto);

        // Confirm result
        assertEquals(EXPECT_VALUE_ZERO_UPDATE, actResult);
    }

    /**
     * <UL>
     * <LI>Target updateReportCreateStatusNormal.
     * <LI>Condition：Update Data is Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Update.xml")
    @ExpectedDatabase(value = "ReportCreateStatusMapper_Expected_UpdateNormal.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdateReportCreateStatusNormal() throws Exception {
        // Prepare parameter
        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setStoreCode("Store001");
        reportCreateStatusDto.setCreateReportStatus(2);
        reportCreateStatusDto.setCreatedReportBucketName("TestBucket");
        reportCreateStatusDto.setCreatedReportKeyName("TestKey");
        // 2017-12-01 06:00:09
        reportCreateStatusDto.setUpdateDatetime(
                ZonedDateTime.of(2017, 12, 1, 6, 0, 9, 0, ZoneId.of("+09:00")).toLocalDateTime());

        // Method execution
        int actResult = reportCreateStatusMapper.updateCreateStatus(reportCreateStatusDto);

        // Confirm result
        assertEquals(EXPECT_VALUE_INSERT_UPDATE, actResult);
    }

    /**
     * <UL>
     * <LI>Target updateReportCreateStatusNormal.
     * <LI>Condition：Update Data Not Found.receiptNumber is Not Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Update.xml")
    @ExpectedDatabase(value = "ReportCreateStatusMapper_Expected_UpdateNormalZeroData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdateReportCreateStatusNormalZeroRecord01() throws Exception {
        // Prepare parameter
        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumberNotFound");
        reportCreateStatusDto.setStoreCode("Store001");
        reportCreateStatusDto.setCreateReportStatus(2);
        reportCreateStatusDto.setCreatedReportBucketName("TestBucket");
        reportCreateStatusDto.setCreatedReportKeyName("TestKey");

        // Method execution
        int actResult = reportCreateStatusMapper.updateCreateStatus(reportCreateStatusDto);

        // Confirm result
        assertEquals(EXPECT_VALUE_ZERO_UPDATE, actResult);
    }

    /**
     * <UL>
     * <LI>Target testUpdateReportCreateStatusAbnormal.
     * <LI>Condition：Update Data is Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Update.xml")
    @ExpectedDatabase(value = "ReportCreateStatusMapper_Expected_UpdateAbNormal.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdateReportCreateStatusAbnormal() throws Exception {
        // Prepare parameter
        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setStoreCode("Store001");
        reportCreateStatusDto.setCreateReportStatus(3);
        // 2017-12-01 06:00:09
        reportCreateStatusDto.setUpdateDatetime(
                ZonedDateTime.of(2017, 12, 1, 6, 0, 9, 0, ZoneId.of("+09:00")).toLocalDateTime());

        // Method execution
        int actResult = reportCreateStatusMapper.updateCreateStatus(reportCreateStatusDto);

        // Confirm result
        assertEquals(EXPECT_VALUE_INSERT_UPDATE, actResult);
    }

    /**
     * <UL>
     * <LI>Target testUpdateReportCreateStatusAbnormal.
     * <LI>Condition：Update Data Not Found.receiptNumber is Not Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Update.xml")
    @ExpectedDatabase(value = "ReportCreateStatusMapper_Expected_UpdateAbNormalZeroData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdateReportCreateStatusAbnormalZeroRecord01() throws Exception {
        // Prepare parameter
        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumberNotFound");
        reportCreateStatusDto.setStoreCode("Store001");
        reportCreateStatusDto.setCreateReportStatus(3);

        // Method execution
        int actResult = reportCreateStatusMapper.updateCreateStatus(reportCreateStatusDto);

        // Confirm result
        assertEquals(EXPECT_VALUE_ZERO_UPDATE, actResult);
    }

    /**
     * <UL>
     * <LI>Target updateAutoPrintStatus.
     * <LI>Condition：Update Data is Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_UpdateAutoPrintStatus.xml")
    @ExpectedDatabase(value = "ReportCreateStatusMapper_Expected_UpdateAutoPrintStatus_Normal.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdateAutoPrintStatusNormal() throws Exception {
        // Prepare parameter
        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setStoreCode("Store001");
        reportCreateStatusDto.setAutoPrintStatus(2);
        // 2017-12-01 06:00:09
        reportCreateStatusDto.setUpdateDatetime(
                ZonedDateTime.of(2017, 12, 1, 6, 0, 9, 0, ZoneId.of("+09:00")).toLocalDateTime());

        // Method execution
        int actResult = reportCreateStatusMapper.updateAutoPrintStatus(reportCreateStatusDto);

        // Confirm result
        assertEquals(EXPECT_VALUE_INSERT_UPDATE, actResult);
    }

    /**
     * <UL>
     * <LI>Target updateAutoPrintStatus.
     * <LI>Condition：Update Data is Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_UpdateAutoPrintStatus.xml")
    @ExpectedDatabase(
            value = "ReportCreateStatusMapper_Expected_UpdateAutoPrintStatus_Abnormal.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdateAutoPrintStatusAbnormal() throws Exception {
        // Prepare parameter
        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setStoreCode("Store001");
        reportCreateStatusDto.setAutoPrintStatus(3);
        // 2017-12-01 06:00:09
        reportCreateStatusDto.setUpdateDatetime(
                ZonedDateTime.of(2017, 12, 1, 6, 0, 9, 0, ZoneId.of("+09:00")).toLocalDateTime());

        // Method execution
        int actResult = reportCreateStatusMapper.updateAutoPrintStatus(reportCreateStatusDto);

        // Confirm result
        assertEquals(EXPECT_VALUE_INSERT_UPDATE, actResult);
    }

    /**
     * <UL>
     * <LI>Target updateAutoPrintStatus.
     * <LI>Condition：Update Data NotFound.receiptNumber is Not Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_UpdateAutoPrintStatus.xml")
    @ExpectedDatabase(value = "ReportCreateStatusMapper_Expected_UpdateAutoPrintStatusZeroData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdateAutoPrintStatusZeroRecord01() throws Exception {
        // Prepare parameter
        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceiptNumberNotFound");
        reportCreateStatusDto.setStoreCode("Store001");
        reportCreateStatusDto.setAutoPrintStatus(2);

        // Method execution
        int actResult = reportCreateStatusMapper.updateAutoPrintStatus(reportCreateStatusDto);

        // Confirm result
        assertEquals(EXPECT_VALUE_ZERO_UPDATE, actResult);
    }

    /**
     * <UL>
     * <LI>Target updateAutoPrintStatus.
     * <LI>Condition：Update Data NotFound.receiptNumber is Not Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Select_DeleteBusinessDay.xml")
    public void testGetByDeleteBusinessDayOneRecord() throws Exception {
        // Prepare parameter
        LocalDate businessDay = LocalDate.of(2018, 2, 17);

        // Method execution
        List<ReportCreateStatus> actResult =
                reportCreateStatusMapper.selectByDeleteReportBusinessDay(2, businessDay);

        // Check result list size
        assertEquals(1, actResult.size());

        // Confirm result
        for (ReportCreateStatus oneDto : actResult) {
            assertThat(oneDto.getReceiptNumber(), is("TestReceptionNumber5"));
        }
    }

    /**
     * <UL>
     * <LI>Target updateAutoPrintStatus.
     * <LI>Condition：Update Data NotFound.receiptNumber is Not Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Select_DeleteBusinessDay.xml")
    public void testGetByDeleteBusinessDayZeroRecord() throws Exception {
        // Prepare parameter
        LocalDate businessDay = LocalDate.of(2018, 2, 16);

        // Method execution
        List<ReportCreateStatus> actResult =
                reportCreateStatusMapper.selectByDeleteReportBusinessDay(2, businessDay);

        // Check result list size
        assertEquals(0, actResult.size());
    }

    /**
     * <UL>
     * <LI>Target updateAutoPrintStatus.
     * <LI>Condition：Update Data NotFound.receiptNumber is Not Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Select_DeleteBusinessDay.xml")
    public void testGetByDeleteBusinessDayMultRecord() throws Exception {
        // Prepare parameter
        LocalDate businessDay = LocalDate.of(2018, 2, 25);

        // Method execution
        List<ReportCreateStatus> actResult =
                reportCreateStatusMapper.selectByDeleteReportBusinessDay(2, businessDay);

        // Check result list size
        assertEquals(7, actResult.size());

        // Confirm result
        for (ReportCreateStatus oneDto : actResult) {
            assertThat(oneDto.getReceiptNumber(),
                    anyOf(is("TestReceptionNumber"), is("TestReceptionNumber2"),
                            is("TestReceptionNumber3"), is("TestReceptionNumber4"),
                            is("TestReceptionNumber5"), is("TestReceptionNumber6"),
                            is("TestReceptionNumber7")));
        }
    }

    /**
     * <UL>
     * <LI>Target updateAutoPrintStatus.
     * <LI>Condition：Update Data NotFound.receiptNumber is Not Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Delete_DeleteByReceiptNumber.xml")
    @ExpectedDatabase(value = "ReportCreateStatusMapper_Excepted_Delete_LeftOne.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByReceiptNumberSuccess() throws Exception {
        // Method execution
        int actResult = reportCreateStatusMapper.deleteByReceiptNumber("TestReceptionNumber7");

        // Check result list size
        assertEquals(EXPECT_VALUE_INSERT_UPDATE, actResult);
    }

    /**
     * <UL>
     * <LI>Target updateAutoPrintStatus.
     * <LI>Condition：Update Data NotFound.receiptNumber is Not Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Delete_DeleteByReceiptNumber.xml")
    @ExpectedDatabase(value = "ReportCreateStatusMapper_Excepted_Delete_LeftZero.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByReceiptNumberFail() throws Exception {
        // Method execution
        int actResult = reportCreateStatusMapper.deleteByReceiptNumber("TestReceptionNumber8");

        // Check result list size
        assertEquals(EXPECT_VALUE_ZERO_UPDATE, actResult);
    }

    /**
     * <UL>
     * <LI>Target updateAutoPrintStatus.
     * <LI>Condition：Update Data NotFound.receiptNumber is Not Correct.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReportCreateStatusMapper_Init_Delete_DeleteByReceiptNumber2.xml")
    public void testAddData() throws Exception {
        List<ReportCreateStatus> result =
                reportCreateStatusMapper.selectByDeleteReportBusinessDay(3, LocalDate.now());
        result.stream().mapToInt(status -> status.getCreateReportStatus()).sum();
        // Method execution
        reportCreateStatusMapper.deleteByReceiptNumber("TestReceptionNumber8");
    }
}
