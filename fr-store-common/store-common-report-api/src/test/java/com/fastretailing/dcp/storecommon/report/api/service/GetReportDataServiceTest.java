/**
 * @(#)GetReportDataServiceTest.java
 * 
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportCreateStatusMapper;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3SdkException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3Utility;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GetReportDataServiceTest {
    /** Service class with update report status service. */
    @SpyBean
    private GetReportDataService getReportDataService;

    /** Create a mock of access DB for report create status table. */
    @MockBean
    private ReportCreateStatusMapper mockReportCreateStatusMapper;

    /**
     * Create a mock of AWS S3 utility class.
     */
    @MockBean
    private AwsS3Utility mockS3Utility;

    /** Report Create Status information. */
    private ReportCreateStatus reportCreateStatusDto = null;

    /**
     * Test preProcessing. Perform initial setting of item information.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Before
    public void setUp() throws Exception {
        // Prepare Parameter
        final String receptionNumber = "TestReceptionNumber";
        final String storeCode = "Store001";
        final String reportId = "TestReportId";
        final String reportType = "PDF";
        final Integer createReportStatus = 2;
        final Integer autoPrintStatus = 1;
        final String s3BucketName = "TestBucket";
        final String s3Key = "TestKey";
        final LocalDate createReportBusinessDay =
                ZonedDateTime.of(2017, 12, 20, 0, 0, 0, 0, ZoneId.of("UTC")).toLocalDate();

        // Set information
        reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber(receptionNumber);
        reportCreateStatusDto.setStoreCode(storeCode);
        reportCreateStatusDto.setReportId(reportId);
        reportCreateStatusDto.setReportType(reportType);
        reportCreateStatusDto.setCreateReportStatus(createReportStatus);
        reportCreateStatusDto.setAutoPrintStatus(autoPrintStatus);
        reportCreateStatusDto.setCreatedReportBucketName(s3BucketName);
        reportCreateStatusDto.setCreatedReportKeyName(s3Key);
        reportCreateStatusDto.setCreateReportBusinessDay(createReportBusinessDay);
    }

    /**
     * <UL>
     * <LI>Target method：getReportData.
     * <LI>Condition：Parameter is existed.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testNormal() throws Exception {
        // Prepare Parameter
        String receiptNumber = "TestReceptionNumber";

        String downloadUrlExpected = "download url";

        // Mock for DB access.
        when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(receiptNumber), any()))
                .thenReturn(reportCreateStatusDto);

        // Mock for S3 utility.
        when(mockS3Utility.generatePreSignedUrl(reportCreateStatusDto.getCreatedReportBucketName(),
                reportCreateStatusDto.getCreatedReportKeyName())).thenReturn(downloadUrlExpected);

        // Method execution
        String downloadUrl =
                getReportDataService.getReportDownloadUrl(receiptNumber);

        // Confirm result
        assertThat(downloadUrl, is(downloadUrlExpected));

        // verify if the searchList method is called only once successfully
        reset(mockReportCreateStatusMapper);
        reset(mockS3Utility);
    }

    /**
     * <UL>
     * <LI>Target method：getReportData.
     * <LI>Condition：Occur Exception in getting data from db.
     * <LI>Verification result confirmation：Throw BusinessException And Expected message out.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testBusinessExceptionDbNoSuchData() throws Exception {
        // Prepare Parameter
        String receptionNumber = "TestReceptionNumber";

        ResultObject result = new ResultObject();
        result.setName(ErrorName.Business.BUSINESS_CHECK_ERROR);
        result.setMessage("BusinessException Occurred.");

        // Mock for DB access.
        when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(receptionNumber), any()))
                .thenReturn(null);

        try {
            // Method execution
            getReportDataService.getReportDownloadUrl(receptionNumber);
            fail("expected exception was not occured.");
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(BusinessException.class);
        }
    }

    /**
     * <UL>
     * <LI>Target method：getReportData.
     * <LI>Condition：Occur Exception at DownloadReport.
     * <LI>Verification result confirmation：Throw BusinessException And Expected message out.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testBusinessExceptionFromDownloadReport() throws Exception {
        // Prepare Parameter
        String receptionNumber = "TestReceptionNumber";

        AwsS3SdkException expectedException =
                new AwsS3SdkException("aws exception", new RuntimeException(""));

        // Mock for DB access.
        when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(receptionNumber), any()))
                .thenReturn(reportCreateStatusDto);

        // Mock for S3 utility.
        when(mockS3Utility.generatePreSignedUrl(reportCreateStatusDto.getCreatedReportBucketName(),
                reportCreateStatusDto.getCreatedReportKeyName())).thenThrow(expectedException);

        try {
            // Method execution
            getReportDataService.getReportDownloadUrl(receptionNumber);
            fail("expected exception was not occured.");
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(BusinessException.class);
        }
    }
}
