/**
 * @(#)UpdateReportStatusServiceTest.java
 * 
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportCreateStatusMapper;
import com.fastretailing.dcp.storecommon.report.dto.UpdateReportStatusRequest;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdateReportStatusServiceTest {
    /** Service class with update report status service. */
    @SpyBean
    private UpdateReportStatusService updateReportStatusService;

    /** Create a mock of access DB for report create status table. */
    @MockBean
    private ReportCreateStatusMapper mockReportCreateStatusMapper;

    /**
     * <UL>
     * <LI>Target method：register.
     * <LI>Condition：Parameter for insert is existed.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testUpdateReportCreateStatusNormal() throws Exception {
        // Prepare Parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceptionNumber");
        request.setCreateReportStatus(2);
        request.setCreatedReportBucketName("TestBucket");
        request.setCreatedReportKeyName("TestKey");

        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setCreateReportStatus(2);
        reportCreateStatusDto.setCreatedReportBucketName("TestBucket");
        reportCreateStatusDto.setCreatedReportKeyName("TestKey");

        // Mock for DB access.
        when(mockReportCreateStatusMapper.updateCreateStatus(reportCreateStatusDto)).thenReturn(1);

        // Method execution
        int serviceResult = updateReportStatusService.updateCreateStatus(request);

        // Confirm result
        assertEquals(1, serviceResult);

        // verify if the searchList method is called only once successfully
        reset(mockReportCreateStatusMapper);
    }

    /**
     * <UL>
     * <LI>Target method：register.
     * <LI>Condition：S3BuketName is null.
     * <LI>Verification result confirmation：Throw BusinessException And Expected message out.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testUpdateReportCreateStatusNormalS3BuketNameNull() throws Exception {
        // Prepare Parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceptionNumber");
        request.setCreateReportStatus(2);
        request.setCreatedReportKeyName("TestKey");

        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setCreateReportStatus(2);
        reportCreateStatusDto.setCreatedReportKeyName("TestKey");

        // Mock for DB access.
        when(mockReportCreateStatusMapper.updateCreateStatus(reportCreateStatusDto)).thenReturn(1);

        try {
            // Method execution
            updateReportStatusService.updateCreateStatus(request);
        } catch (BusinessException e) {
            e.printStackTrace();
            assertThat(e.getResultObject().getMessage(),
                    is("Validation error s3BucketName " + "can not be null."));
        }
    }

    /**
     * <UL>
     * <LI>Target method：register.
     * <LI>Condition：S3BuketName is null.
     * <LI>Verification result confirmation：Throw BusinessException And Expected message out.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testUpdateReportCreateStatusNormalS3KeyNull() throws Exception {
        // Prepare Parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceptionNumber");
        request.setCreateReportStatus(2);
        request.setCreatedReportBucketName("TestBucket");

        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setCreateReportStatus(2);
        reportCreateStatusDto.setCreatedReportBucketName("TestBucket");

        // Mock for DB access.
        when(mockReportCreateStatusMapper.updateCreateStatus(reportCreateStatusDto)).thenReturn(1);

        try {
            // Method execution
            updateReportStatusService.updateCreateStatus(request);
        } catch (BusinessException e) {
            e.printStackTrace();
            assertThat(e.getResultObject().getMessage(),
                    is("Validation error s3Key " + "can not be null."));
        }
    }

    /**
     * <UL>
     * <LI>Target method：register.
     * <LI>Condition：Parameter for update is existed.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testUpdateReportCreateStatusAbnormal() throws Exception {
        // Prepare Parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceptionNumber");
        request.setCreateReportStatus(3);

        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setCreateReportStatus(3);

        // Mock for DB access.
        when(mockReportCreateStatusMapper.updateCreateStatus(reportCreateStatusDto)).thenReturn(1);

        // Method execution
        int serviceResult = updateReportStatusService.updateCreateStatus(request);

        // Confirm result
        assertEquals(1, serviceResult);

        // verify if the searchList method is called only once successfully
        reset(mockReportCreateStatusMapper);
    }

    /**
     * <UL>
     * <LI>Target method：register.
     * <LI>Condition：Parameter for update is existed.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testUpdateAutoPrintStatusNormal() throws Exception {
        // Prepare Parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceptionNumber");
        request.setAutoPrintStatus(2);

        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setAutoPrintStatus(2);

        // Mock for DB access.
        when(mockReportCreateStatusMapper.updateAutoPrintStatus(reportCreateStatusDto))
                .thenReturn(1);

        // Method execution
        int serviceResult = updateReportStatusService.updateAutoPrintStatus(request);

        // Confirm result
        assertEquals(1, serviceResult);

        // verify if the searchList method is called only once successfully
        reset(mockReportCreateStatusMapper);
    }

    /**
     * <UL>
     * <LI>Target method：register.
     * <LI>Condition：Parameter for update is existed.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testUpdateAutoPrintStatusAbormal() throws Exception {
        // Prepare Parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceptionNumber");
        request.setAutoPrintStatus(3);

        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setAutoPrintStatus(3);

        // Mock for DB access.
        when(mockReportCreateStatusMapper.updateAutoPrintStatus(reportCreateStatusDto))
                .thenReturn(1);

        // Method execution
        int serviceResult = updateReportStatusService.updateAutoPrintStatus(request);

        // Confirm result
        assertEquals(1, serviceResult);

        // verify if the searchList method is called only once successfully
        reset(mockReportCreateStatusMapper);
    }

    /**
     * <UL>
     * <LI>Target method：register.
     * <LI>Condition：CreateReportStatus and AutoPrintStatus is null.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testCreateReportStatusAndAutoPrintStatusNull() throws Exception {
        // Prepare Parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceptionNumber");

        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");

        try {
            // Method execution
            updateReportStatusService.updateAutoPrintStatus(request);
        } catch (BusinessException e) {
            e.printStackTrace();
            assertThat(e.getResultObject().getMessage(),
                    is("Validation error autoPrintStatus is not expected value. "
                            + "autoPrintStatus=null."));
        }
    }

    /**
     * <UL>
     * <LI>Target method：register.
     * <LI>Condition：CreateReportStatus is not expected value.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testCreateReportStatusNotExpected() throws Exception {
        // Prepare Parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceptionNumber");
        request.setCreateReportStatus(99);

        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setCreateReportStatus(99);

        try {
            // Method execution
            updateReportStatusService.updateCreateStatus(request);
        } catch (BusinessException e) {
            e.printStackTrace();
            assertThat(e.getResultObject().getMessage(),
                    is("Validation error createReportStatus is not expected value. "
                            + "createReportStatus=99."));
        }
    }

    /**
     * <UL>
     * <LI>Target method：register.
     * <LI>Condition：AutoPrintStatus is not expected value.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testAutoPrintStatusNotExpected() throws Exception {
        // Prepare Parameter
        UpdateReportStatusRequest request = new UpdateReportStatusRequest();
        request.setReceiptNumber("TestReceptionNumber");
        request.setAutoPrintStatus(99);

        ReportCreateStatus reportCreateStatusDto = new ReportCreateStatus();
        reportCreateStatusDto.setReceiptNumber("TestReceptionNumber");
        reportCreateStatusDto.setAutoPrintStatus(99);

        try {
            // Method execution
            updateReportStatusService.updateAutoPrintStatus(request);
        } catch (BusinessException e) {
            e.printStackTrace();
            assertThat(e.getResultObject().getMessage(), is(
                    "Validation error autoPrintStatus is not expected value. autoPrintStatus=99."));
        }
    }
}
