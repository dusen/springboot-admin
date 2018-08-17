/**
 * @(#)DeleteReportDataServiceImpl.java
 * 
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportCreateStatusMapper;
import com.fastretailing.dcp.storecommon.report.constant.ReportMessagesConstants;
import com.fastretailing.dcp.storecommon.report.type.ReportStatusCode;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3InvalidParameterException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3SdkException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3Utility;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class of Delete Report Data.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
@Service
public class DeleteReportDataServiceImpl implements DeleteReportDataService {

    /**
     * Component for operating DB operations on the t_report_create_status.
     */
    @Autowired
    private ReportCreateStatusMapper reportCreateStatusMapper;

    /**
     * AWS S3 file utility class.
     */
    @Autowired
    private AwsS3Utility awsS3Utility;

    /**
     * Locale message source.
     */
    @Autowired
    private SystemMessageSource systemMessageSource;

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional
    public int deleteReport(ReportCreateStatus status) {
        if (StringUtils.isNotBlank(status.getCreatedReportBucketName())) {
            deleteS3ReportData(status);
        }
        return reportCreateStatusMapper.deleteByReceiptNumber(status.getReceiptNumber());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<ReportCreateStatus> getDeleteTargetList() {
        return reportCreateStatusMapper.selectByDeleteReportBusinessDay(
                ReportStatusCode.SUCCESS.getValue(), LocalDate.now(ZoneId.of("UTC")));
    }

    /**
     * Delete S3 report file.
     * 
     * @param status Report create status.
     * @return Deleted files number.
     */
    private int deleteS3ReportData(ReportCreateStatus status) {
        try {
            return awsS3Utility
                    .deleteFile(status.getCreatedReportBucketName(),
                            status.getCreatedReportKeyName())
                    .getDeletedKeys()
                    .size();
        } catch (AwsS3InvalidParameterException | AwsS3SdkException e) {
            String message = systemMessageSource
                    .getMessage(ReportMessagesConstants.ERROR_DELETE_FILE_FAIL, new String[] {
                            status.getCreatedReportBucketName(), status.getCreatedReportKeyName()});
            log.error(message);
            throw new BusinessException(new ResultObject(ErrorName.Business.BUSINESS_CHECK_ERROR,
                    ReportMessagesConstants.ERROR_DELETE_FILE_FAIL, message));
        }
    }
}
