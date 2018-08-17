/**
 * @(#)MergePdfReportServiceImpl.java
 * 
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportCreateStatusMapper;
import com.fastretailing.dcp.storecommon.report.api.util.ReportReceiptNumberComposer;
import com.fastretailing.dcp.storecommon.report.type.AutoPrintStatus;
import com.fastretailing.dcp.storecommon.report.type.ReportStatusCode;
import com.fastretailing.dcp.storecommon.report.type.ReportType;
import com.fastretailing.dcp.storecommon.util.DateUtility;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3FileAlreadyExistsException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3FileNotFoundException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3InvalidParameterException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3SdkException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3Utility;

/**
 * Class for merge pdf report.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Service
public class MergePdfReportServiceImpl implements MergePdfReportService {

    /** Suffix for merged report's report id. */
    private static final String REPORT_ID_SUFFIX_MERGE = "MRG";

    /**
     * Report Create Status Component.
     */
    @Autowired
    private ReportCreateStatusMapper reportCreateStatusMapper;

    /**
     * Component for composing report number.
     */
    @Autowired
    private ReportReceiptNumberComposer receiptNumberComposer;

    /**
     * Aws S3 utility.
     */
    @Autowired
    private AwsS3Utility awsS3Utility;

    /**
     * {@inheritDoc}.
     */
    @Deprecated
    public ByteArrayOutputStream mergePdfReport(List<String> receiptNumbers) {
        throw new RuntimeException("Deprecated method, will be deleted next release.");
    }

    /**
     * {@inheritDoc}.
     */
    @Transactional
    public String mergePdfReport(List<String> receiptNumbers, String bucketName) {

        if (CollectionUtils.isEmpty(receiptNumbers)) {
            throw new IllegalArgumentException("ReceiptNumber can not be empty.");
        }

        List<ReportCreateStatus> reports = receiptNumbers.stream().map(receiptNumber -> {

            ReportCreateStatus report =
                    reportCreateStatusMapper.selectByReceiptNumber(receiptNumber, null);
            if (report == null) {
                throw new IllegalArgumentException(
                        "No such report data. ReceiptNumber=" + receiptNumber);
            }

            return report;
        }).collect(Collectors.toList());

        List<InputStream> mergeSources = reports.stream().map(report -> {

            try (ByteArrayOutputStream reportOut = awsS3Utility.download(
                    report.getCreatedReportBucketName(), report.getCreatedReportKeyName());
                    ByteArrayInputStream reportIn =
                            new ByteArrayInputStream(reportOut.toByteArray())) {
                return reportIn;
            } catch (AwsS3InvalidParameterException | AwsS3FileNotFoundException | AwsS3SdkException
                    | IOException e) {
                throw new SystemException("Error in download report from S3. ReceiptNumber="
                        + report.getReceiptNumber(), e);
            }
        }).collect(Collectors.toList());

        String s3Prefix = reports.get(0).getCreatedReportKeyName().substring(0,
                reports.get(0).getCreatedReportKeyName().lastIndexOf(
                        AwsS3Utility.AWS_S3_DELIMITER));

        try (ByteArrayOutputStream mergedOutput = new ByteArrayOutputStream()) {

            PDFMergerUtility pdfMerger = new PDFMergerUtility();
            pdfMerger.addSources(mergeSources);
            pdfMerger.setDestinationStream(mergedOutput);
            pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

            String[] splitted = receiptNumbers.get(0)
                    .split(ReportReceiptNumberComposer.REPORT_RECEIPT_NUMBER_DELIMITER);
            String reportId =
                    splitted[2].substring(0, splitted[2].length() - 3) + REPORT_ID_SUFFIX_MERGE;

            String mergedReceiptNumber =
                    receiptNumberComposer.compose(splitted[0], splitted[1], reportId);
            String s3Key =
                    String.join(AwsS3Utility.AWS_S3_DELIMITER, s3Prefix, mergedReceiptNumber);

            try (ByteArrayInputStream inputStreamToUpload =
                    new ByteArrayInputStream(mergedOutput.toByteArray())) {

                awsS3Utility.upload(bucketName, s3Key,
                        new ByteArrayInputStream(mergedOutput.toByteArray()), true, null);
            } catch (AwsS3InvalidParameterException | AwsS3FileAlreadyExistsException
                    | AwsS3SdkException e) {
                throw new SystemException("Error in upload merged pdf file to S3.", e);
            }

            ReportCreateStatus reportCreateStatus = new ReportCreateStatus();
            reportCreateStatus.setReceiptNumber(mergedReceiptNumber);
            reportCreateStatus.setReportId(reportId);
            reportCreateStatus.setReportType(ReportType.PDF.getValue());
            reportCreateStatus.setStoreCode(splitted[1]);
            reportCreateStatus.setCreatedReportBucketName(bucketName);
            reportCreateStatus.setCreatedReportKeyName(s3Key);
            reportCreateStatus.setCreateReportStatus(ReportStatusCode.SUCCESS.getValue());
            reportCreateStatus.setAutoPrintStatus(AutoPrintStatus.UNPROCESSED.getValue());
            reportCreateStatus
                    .setCreateReportBusinessDay(DateUtility.getZonedDateTimeUtc().toLocalDate());
            reportCreateStatus.setDeleteReportBusinessDay(
                    reportCreateStatus.getCreateReportBusinessDay().plusDays(1));
            reportCreateStatus
                    .setCreateDatetime(DateUtility.getZonedDateTimeUtc().toLocalDateTime());

            reportCreateStatusMapper.insertReportCreateStatus(reportCreateStatus);

            return awsS3Utility.generatePreSignedUrl(bucketName, s3Key);
        } catch (IOException e) {
            throw new SystemException("Error in merging pdf files.", e);
        } catch (AwsS3SdkException e) {
            throw new SystemException("Error in generating download URL.", e);
        }
    }
}
