/**
 * @(#)GetReportDataServiceTest.java
 * 
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportCreateStatusMapper;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportReceiptNumberSeqMapper;
import com.fastretailing.dcp.storecommon.report.type.ReportStatusCode;
import com.fastretailing.dcp.storecommon.report.type.ReportType;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3FileNotFoundException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3InvalidParameterException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3SdkException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3Utility;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MergePdfReportServiceTest {

    /** Service class with update report status service. */
    @Autowired
    private MergePdfReportService mergePdfReportService;

    /** Create a mock of access DB for report create status table. */
    @MockBean
    private ReportCreateStatusMapper mockReportCreateStatusMapper;

    /**
     * Create a mock of AWS S3 utility class.
     */
    @MockBean
    private AwsS3Utility mockS3Utility;

    /**
     * Mock mapper class for getting next value from sequence.
     */
    @MockBean
    private ReportReceiptNumberSeqMapper receiptNumberSeqMapper;

    /** Merge PDF 1 FilePath. */
    private static final String MERGE_PDF_1_FILE_PATH = "merge1.pdf";

    /** Merge PDF 1 FilePath. */
    private static final String MERGE_PDF_2_FILE_PATH = "merge2.pdf";

    /**
     * Test normal.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testMergeByNumberNormal() throws Exception {
        try (FileInputStream fis1 = new FileInputStream(
                new File(this.getClass().getResource(MERGE_PDF_1_FILE_PATH).toURI()));
                ByteArrayOutputStream merge1 = new ByteArrayOutputStream();
                FileInputStream fis2 = new FileInputStream(
                        new File(this.getClass().getResource(MERGE_PDF_2_FILE_PATH).toURI()));
                ByteArrayOutputStream merge2 = new ByteArrayOutputStream();) {

            // Prepare Parameter
            byte[] byteOut = new byte[1024];
            while (fis1.read(byteOut) > 0) {
                merge1.write(byteOut);
            }
            while (fis2.read(byteOut) > 0) {
                merge2.write(byteOut);
            }

            String bucketName1 = "test bucket";
            String bucketName2 = "test bucket";
            String s3Key1 = "p1/p2/key1";
            String s3Key2 = "key2";

            // Mock for S3 utility.
            when(mockS3Utility.download(bucketName1, s3Key1)).thenReturn(merge1);
            when(mockS3Utility.download(bucketName2, s3Key2)).thenReturn(merge2);

            URL mergedUrl = this.getClass().getResource("");
            File merged = new File(mergedUrl.getPath() + "/merged.pdf");
            if (merged.exists()) {
                merged.delete();
            } else {
                merged.createNewFile();
            }

            String number1 = "system_store_report001_20180101010101000_1";
            ReportCreateStatus report1 = new ReportCreateStatus();
            report1.setCreatedReportBucketName(bucketName1);
            report1.setCreatedReportKeyName(s3Key1);
            when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number1), any()))
                    .thenReturn(report1);
            String number2 = "number2";
            ReportCreateStatus report2 = new ReportCreateStatus();
            report2.setCreatedReportBucketName(bucketName2);
            report2.setCreatedReportKeyName(s3Key2);
            when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number2), any()))
                    .thenReturn(report2);

            when(mockS3Utility.upload(anyString(), anyString(), anyObject(), eq(true), eq(null)))
                    .thenReturn("uploadresult");

            when(receiptNumberSeqMapper.nextValue()).thenReturn("seq");
            when(mockReportCreateStatusMapper.insertReportCreateStatus(any())).thenReturn(1);

            String bucketName = "bucketMerged";
            String url = "testURL";
            when(mockS3Utility.generatePreSignedUrl(eq(bucketName), any())).thenReturn(url);

            // Method execution
            String resultUrl = mergePdfReportService.mergePdfReport(Arrays.asList(number1, number2),
                    bucketName);

            assertThat(resultUrl, is(url));

            ArgumentCaptor<ReportCreateStatus> insertStatusCaptor =
                    ArgumentCaptor.forClass(ReportCreateStatus.class);
            verify(mockReportCreateStatusMapper, times(1))
                    .insertReportCreateStatus(insertStatusCaptor.capture());
            assertThat(insertStatusCaptor.getValue().getReceiptNumber().startsWith(
                    "system_store_reportMRG"), is(true));
            assertThat(insertStatusCaptor.getValue().getCreatedReportBucketName(), is(bucketName));
            assertThat(insertStatusCaptor.getValue().getCreatedReportKeyName(),
                    is("p1/p2/" + insertStatusCaptor.getValue().getReceiptNumber()));
            assertThat(insertStatusCaptor.getValue().getDeleteReportBusinessDay(),
                    is(insertStatusCaptor.getValue().getCreateReportBusinessDay().plusDays(1)));
            assertThat(insertStatusCaptor.getValue().getCreateReportStatus(),
                    is(ReportStatusCode.SUCCESS.getValue()));
            assertThat(insertStatusCaptor.getValue().getReportType(),
                    is(ReportType.PDF.getValue()));
            assertThat(insertStatusCaptor.getValue().getReportId(), is("reportMRG"));

            ArgumentCaptor<String> s3KeyCaptor = ArgumentCaptor.forClass(String.class);
            verify(mockS3Utility, times(1)).upload(eq(bucketName), s3KeyCaptor.capture(), any(),
                    eq(true), any());
            verify(mockS3Utility, times(1)).generatePreSignedUrl(eq(bucketName),
                    s3KeyCaptor.capture());

            assertThat(s3KeyCaptor.getAllValues().get(0),
                    is(insertStatusCaptor.getValue().getCreatedReportKeyName()));
            assertThat(s3KeyCaptor.getAllValues().get(1),
                    is(insertStatusCaptor.getValue().getCreatedReportKeyName()));
        }

        // verify if the searchList method is called only once successfully
        reset(mockReportCreateStatusMapper);
        reset(mockS3Utility);
    }

    /**
     * Test illegal argument.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testMergeByNumberIllegalArgument() throws Exception {

        String bucketName = "bucketMerged";
        // Method execution
        try {
            mergePdfReportService.mergePdfReport(null, bucketName);
            fail("expected an exctpion.");
        } catch (IllegalArgumentException e) {
            // success
        }

        // Method execution
        try {
            mergePdfReportService.mergePdfReport(new ArrayList<>(), bucketName);
            fail("expected an exctpion.");
        } catch (IllegalArgumentException e) {
            // success
        }

        // verify if the searchList method is called only once successfully
        reset(mockReportCreateStatusMapper);
        reset(mockS3Utility);
    }

    /**
     * Test no such report.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testMergeByNumberNoSuchReport() throws Exception {

        String number1 = "number1";
        String bucketName1 = "test bucket";
        String s3Key1 = "key1";
        ReportCreateStatus report1 = new ReportCreateStatus();
        report1.setCreatedReportBucketName(bucketName1);
        report1.setCreatedReportKeyName(s3Key1);
        when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number1), any()))
                .thenReturn(report1);

        String number2 = "number2";
        String bucketName2 = "test bucket";
        String s3Key2 = "key2";
        ReportCreateStatus report2 = new ReportCreateStatus();
        report2.setCreatedReportBucketName(bucketName2);
        report2.setCreatedReportKeyName(s3Key2);
        when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number2), any()))
                .thenReturn(null);

        when(mockS3Utility.download(bucketName1, s3Key1)).thenReturn(new ByteArrayOutputStream(1));
        when(mockS3Utility.download(bucketName2, s3Key2)).thenReturn(new ByteArrayOutputStream(2));

        String bucketName = "bucketMerged";
        // Method execution
        try {
            mergePdfReportService.mergePdfReport(Arrays.asList(number1, number2), bucketName);
            fail("expected an exctpion.");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("No such report data. ReceiptNumber=number2"));
        }

        when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number1), any()))
                .thenReturn(null);
        when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number2), any()))
                .thenReturn(report2);
        // Method execution
        try {
            mergePdfReportService.mergePdfReport(Arrays.asList(number1, number2), bucketName);
            fail("expected an exctpion.");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("No such report data. ReceiptNumber=number1"));
        }

        // verify if the searchList method is called only once successfully
        reset(mockReportCreateStatusMapper);
        reset(mockS3Utility);
    }

    /**
     * Test download error.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testMergeByNumberDownloadError() throws Exception {

        String number1 = "number1";
        String bucketName1 = "test bucket";
        String s3Key1 = "key1";
        ReportCreateStatus report1 = new ReportCreateStatus();
        report1.setReceiptNumber(number1);
        report1.setCreatedReportBucketName(bucketName1);
        report1.setCreatedReportKeyName(s3Key1);
        when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number1), any()))
                .thenReturn(report1);

        String number2 = "number2";
        String bucketName2 = "test bucket";
        String s3Key2 = "key2";
        ReportCreateStatus report2 = new ReportCreateStatus();
        report2.setReceiptNumber(number2);
        report2.setCreatedReportBucketName(bucketName2);
        report2.setCreatedReportKeyName(s3Key2);
        when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number2), any()))
                .thenReturn(report2);

        when(mockS3Utility.download(bucketName1, s3Key1))
                .thenThrow(new AwsS3InvalidParameterException(""));
        when(mockS3Utility.download(bucketName2, s3Key2)).thenReturn(new ByteArrayOutputStream(2));

        String bucketName = "bucketMerged";
        // Method execution
        try {
            mergePdfReportService.mergePdfReport(Arrays.asList(number1, number2), bucketName);
            fail("expected an exctpion.");
        } catch (SystemException e) {
            assertThat(e.getMessage(),
                    is("Error in download report from S3. ReceiptNumber=number1"));
        }

        reset(mockS3Utility);
        when(mockS3Utility.download(bucketName1, s3Key1)).thenReturn(new ByteArrayOutputStream(1));
        when(mockS3Utility.download(bucketName2, s3Key2))
                .thenThrow(new AwsS3FileNotFoundException(""));
        // Method execution
        try {
            mergePdfReportService.mergePdfReport(Arrays.asList(number1, number2), bucketName);
            fail("expected an exctpion.");
        } catch (SystemException e) {
            assertThat(e.getMessage(),
                    is("Error in download report from S3. ReceiptNumber=number2"));
        }

        reset(mockS3Utility);
        when(mockS3Utility.download(bucketName1, s3Key1)).thenReturn(new ByteArrayOutputStream(1));
        when(mockS3Utility.download(bucketName2, s3Key2))
                .thenThrow(new AwsS3SdkException("", new Throwable()));
        // Method execution
        try {
            mergePdfReportService.mergePdfReport(Arrays.asList(number1, number2), bucketName);
            fail("expected an exctpion.");
        } catch (SystemException e) {
            assertThat(e.getMessage(),
                    is("Error in download report from S3. ReceiptNumber=number2"));
        }

        // verify if the searchList method is called only once successfully
        reset(mockReportCreateStatusMapper);
        reset(mockS3Utility);
    }

    /**
     * Test upload error.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testMergeByNumberUploadError() throws Exception {
        try (FileInputStream fis1 = new FileInputStream(
                new File(this.getClass().getResource(MERGE_PDF_1_FILE_PATH).toURI()));
                ByteArrayOutputStream merge1 = new ByteArrayOutputStream();
                FileInputStream fis2 = new FileInputStream(
                        new File(this.getClass().getResource(MERGE_PDF_2_FILE_PATH).toURI()));
                ByteArrayOutputStream merge2 = new ByteArrayOutputStream();) {

            // Prepare Parameter
            byte[] byteOut = new byte[1024];
            while (fis1.read(byteOut) > 0) {
                merge1.write(byteOut);
            }
            while (fis2.read(byteOut) > 0) {
                merge2.write(byteOut);
            }

            String bucketName1 = "test bucket";
            String bucketName2 = "test bucket";
            String s3Key1 = "p1/p2/key1";
            String s3Key2 = "key2";

            // Mock for S3 utility.
            when(mockS3Utility.download(bucketName1, s3Key1)).thenReturn(merge1);
            when(mockS3Utility.download(bucketName2, s3Key2)).thenReturn(merge2);

            URL mergedUrl = this.getClass().getResource("");
            File merged = new File(mergedUrl.getPath() + "/merged.pdf");
            if (merged.exists()) {
                merged.delete();
            } else {
                merged.createNewFile();
            }

            String number1 = "system_store_report001_20180101010101000_1";
            ReportCreateStatus report1 = new ReportCreateStatus();
            report1.setCreatedReportBucketName(bucketName1);
            report1.setCreatedReportKeyName(s3Key1);
            when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number1), any()))
                    .thenReturn(report1);
            String number2 = "number2";
            ReportCreateStatus report2 = new ReportCreateStatus();
            report2.setCreatedReportBucketName(bucketName2);
            report2.setCreatedReportKeyName(s3Key2);
            when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number2), any()))
                    .thenReturn(report2);

            when(receiptNumberSeqMapper.nextValue()).thenReturn("seq");

            when(mockS3Utility.upload(anyString(), anyString(), anyObject(), eq(true), eq(null)))
                    .thenThrow(new AwsS3InvalidParameterException("test exception"));

            try {
                String bucketName = "bucketMerged";
                // Method execution
                mergePdfReportService.mergePdfReport(Arrays.asList(number1, number2), bucketName);
                fail();
            } catch (SystemException e) {
                // do nothing
            }

            reset(mockS3Utility);

            // Mock for S3 utility.
            when(mockS3Utility.download(bucketName1, s3Key1)).thenReturn(merge1);
            when(mockS3Utility.download(bucketName2, s3Key2)).thenReturn(merge2);
            when(mockS3Utility.upload(anyString(), anyString(), anyObject(), eq(true), eq(null)))
                    .thenThrow(new AwsS3SdkException("test exception", new Throwable("test")));

            try {
                String bucketName = "bucketMerged";
                // Method execution
                mergePdfReportService.mergePdfReport(Arrays.asList(number1, number2), bucketName);
                fail();
            } catch (SystemException e) {
                // do nothing
            }
        }

        // verify if the searchList method is called only once successfully
        reset(mockReportCreateStatusMapper);
        reset(mockS3Utility);
    }

    /**
     * Test generate url error.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testMergeByNumberGenerateUrlError() throws Exception {
        try (FileInputStream fis1 = new FileInputStream(
                new File(this.getClass().getResource(MERGE_PDF_1_FILE_PATH).toURI()));
                ByteArrayOutputStream merge1 = new ByteArrayOutputStream();
                FileInputStream fis2 = new FileInputStream(
                        new File(this.getClass().getResource(MERGE_PDF_2_FILE_PATH).toURI()));
                ByteArrayOutputStream merge2 = new ByteArrayOutputStream();) {

            // Prepare Parameter
            byte[] byteOut = new byte[1024];
            while (fis1.read(byteOut) > 0) {
                merge1.write(byteOut);
            }
            while (fis2.read(byteOut) > 0) {
                merge2.write(byteOut);
            }

            String bucketName1 = "test bucket";
            String bucketName2 = "test bucket";
            String s3Key1 = "p1/p2/key1";
            String s3Key2 = "key2";

            // Mock for S3 utility.
            when(mockS3Utility.download(bucketName1, s3Key1)).thenReturn(merge1);
            when(mockS3Utility.download(bucketName2, s3Key2)).thenReturn(merge2);

            URL mergedUrl = this.getClass().getResource("");
            File merged = new File(mergedUrl.getPath() + "/merged.pdf");
            if (merged.exists()) {
                merged.delete();
            } else {
                merged.createNewFile();
            }

            String number1 = "system_store_report001_20180101010101000_1";
            ReportCreateStatus report1 = new ReportCreateStatus();
            report1.setCreatedReportBucketName(bucketName1);
            report1.setCreatedReportKeyName(s3Key1);
            when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number1), any()))
                    .thenReturn(report1);
            String number2 = "number2";
            ReportCreateStatus report2 = new ReportCreateStatus();
            report2.setCreatedReportBucketName(bucketName2);
            report2.setCreatedReportKeyName(s3Key2);
            when(mockReportCreateStatusMapper.selectByReceiptNumber(eq(number2), any()))
                    .thenReturn(report2);

            when(mockS3Utility.upload(anyString(), anyString(), anyObject(), eq(true), eq(null)))
                    .thenReturn("uploadresult");

            when(receiptNumberSeqMapper.nextValue()).thenReturn("seq");
            when(mockReportCreateStatusMapper.insertReportCreateStatus(any())).thenReturn(1);

            String bucketName = "bucketMerged";
            when(mockS3Utility.generatePreSignedUrl(eq(bucketName), any()))
                    .thenThrow(new AwsS3SdkException("test exception", new Throwable("test")));

            try {
                // Method execution
                mergePdfReportService.mergePdfReport(Arrays.asList(number1, number2), bucketName);
                fail();
            } catch (SystemException e) {
                // do nothing
            }
        }

        // verify if the searchList method is called only once successfully
        reset(mockReportCreateStatusMapper);
        reset(mockS3Utility);
    }
}
