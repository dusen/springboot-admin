/**
 * @(#)DeleteReportDataServiceTest.java
 * 
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportCreateStatusMapper;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3InvalidParameterException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3Utility;
import com.fastretailing.dcp.storecommon.util.aws.MultiDeleteResult;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeleteReportDataServiceTest {
    /** Create a mock of access DB for report create status table. */
    @MockBean
    private ReportCreateStatusMapper mockReportCreateStatusMapper;

    /** Create a mock of AWS S3 utility class. */
    @MockBean
    private AwsS3Utility mockS3Utility;

    /** Service class with delete report status service. */
    @SpyBean
    private DeleteReportDataService deleteReportDataService;

    /**
     * <UL>
     * <LI>Target method： getDeleteTargetList.
     * <LI>Condition：Parameter is not existed.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testGetDeleteTargetList_Normal() throws Exception {
        List<ReportCreateStatus> exceptedList = new ArrayList<>();
        ReportCreateStatus status1 = new ReportCreateStatus();
        status1.setReceiptNumber("TestReceiptNumber");
        exceptedList.add(status1);
        when(mockReportCreateStatusMapper.selectByDeleteReportBusinessDay(eq(2), Mockito.any()))
                .thenReturn(exceptedList);
        List<ReportCreateStatus> actList = deleteReportDataService.getDeleteTargetList();
        assertEquals(exceptedList, actList);
        assertEquals(1, actList.size());
    }

    /**
     * <UL>
     * <LI>Target method： deleteReport.
     * <LI>Condition：Parameter is existed.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testDeleteTargetListNormal() throws Exception {
        ReportCreateStatus status1 = new ReportCreateStatus();
        status1.setReceiptNumber("TestReceiptNumber");
        status1.setCreatedReportBucketName("TestCreatedReportBucketName");
        status1.setCreatedReportKeyName("TestCreatedReportKeyName");
        when(mockReportCreateStatusMapper.deleteByReceiptNumber(eq("TestReceiptNumber")))
                .thenReturn(1);
        List<String> deletedKeys = new ArrayList<>();
        deletedKeys.add("TestCreatedReportKeyName");
        MultiDeleteResult multiDeleteKey = new MultiDeleteResult(null, deletedKeys);
        when(mockS3Utility.deleteFile(eq("TestCreatedReportBucketName"),
                eq("TestCreatedReportKeyName"))).thenReturn(multiDeleteKey);
        int actDelete = deleteReportDataService.deleteReport(status1);
        assertEquals(1, actDelete);
    }

    /**
     * <UL>
     * <LI>Target method： deleteReport.
     * <LI>Condition：Parameter is existed.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testDeleteTargetListNormalBucketNameEmpty() throws Exception {
        ReportCreateStatus status1 = new ReportCreateStatus();
        status1.setReceiptNumber("TestReceiptNumber");
        status1.setCreatedReportBucketName("");
        status1.setCreatedReportKeyName("TestCreatedReportKeyName");
        when(mockReportCreateStatusMapper.deleteByReceiptNumber(eq("TestReceiptNumber")))
                .thenReturn(1);
        List<String> deletedKeys = new ArrayList<>();
        deletedKeys.add("TestCreatedReportKeyName");
        MultiDeleteResult multiDeleteKey = new MultiDeleteResult(null, deletedKeys);
        when(mockS3Utility.deleteFile(eq("TestCreatedReportBucketName"),
                eq("TestCreatedReportKeyName"))).thenReturn(multiDeleteKey);
        int actDelete = deleteReportDataService.deleteReport(status1);
        assertEquals(1, actDelete);
    }

    /**
     * <UL>
     * <LI>Target method： deleteReport.
     * <LI>Condition：Parameter is existed.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testDeleteTargetListError() throws Exception {
        ReportCreateStatus status1 = new ReportCreateStatus();
        status1.setReceiptNumber("TestReceiptNumber");
        status1.setCreatedReportBucketName("TestCreatedReportBucketName");
        status1.setCreatedReportKeyName("TestCreatedReportKeyName");
        when(mockReportCreateStatusMapper.deleteByReceiptNumber(eq("TestReceiptNumber")))
                .thenReturn(1);
        when(mockS3Utility.deleteFile(eq("TestCreatedReportBucketName"),
                eq("TestCreatedReportKeyName"))).thenThrow(new AwsS3InvalidParameterException(""));
        try {
            deleteReportDataService.deleteReport(status1);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BusinessException.class);
        }
    }
}
