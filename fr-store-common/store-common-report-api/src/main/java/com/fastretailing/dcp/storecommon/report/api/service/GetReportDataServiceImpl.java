/**
 * @(#)GetReportDataServiceImpl.java
 * 
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import java.io.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportCreateStatusMapper;
import com.fastretailing.dcp.storecommon.report.api.type.ReportApiDebugId;
import com.fastretailing.dcp.storecommon.report.constant.ReportMessagesConstants;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3SdkException;
import com.fastretailing.dcp.storecommon.util.aws.AwsS3Utility;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class of Get Report Data.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
@Service
public class GetReportDataServiceImpl implements GetReportDataService {

    /**
     * Common Utility.
     */
    @Autowired
    private CommonUtility commonUtility;

    /**
     * Component for operating DB operations on the t_report_management.
     */
    @Autowired
    private ReportCreateStatusMapper reportCreateStatusMapper;

    /**
     * AWS S3 file utility class.
     */
    @Autowired
    private AwsS3Utility s3Utility;

    /**
     * Locale message source.
     */
    @Autowired
    private SystemMessageSource systemMessageSource;

    /**
     * {@inheritDoc}.
     */
    @Override
    @Deprecated
    public ByteArrayOutputStream getReportData(String receiptNumber) {
        throw new RuntimeException("Deprecated method, will be deleted next release.");
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getReportDownloadUrl(String receiptNumber) {

        ReportCreateStatus s3AccessDto =
                reportCreateStatusMapper.selectByReceiptNumber(receiptNumber, null);

        if (s3AccessDto == null) {
            throw new BusinessException(
                    new ResultObject(ErrorName.Basis.SERVICE_COMMUNICATION_ERROR,
                            commonUtility.getDebugId(LogLevel.ERROR.toString(),
                                    ReportApiDebugId.GET_DATA_STATUS_DB_NO_DATA.toString()),
                            systemMessageSource.getMessage(
                                    ReportMessagesConstants.ERROR_NO_CREATE_STATUS_DATA,
                                    new String[] {receiptNumber})));
        }

        try {
            return s3Utility.generatePreSignedUrl(s3AccessDto.getCreatedReportBucketName(),
                    s3AccessDto.getCreatedReportKeyName());
        } catch (AwsS3SdkException e) {
            log.error(systemMessageSource
                    .getMessage(ReportMessagesConstants.ERROR_FAILED_GENERATING_DOWNLOAD_URL), e);
            throw new BusinessException(
                    new ResultObject(ErrorName.Basis.SERVICE_COMMUNICATION_ERROR,
                            commonUtility.getDebugId(LogLevel.ERROR.toString(),
                                    ReportApiDebugId.GET_DATA_AWS_S3_DOWNLOAD_ERROR.toString()),
                            systemMessageSource.getMessage(
                                    ReportMessagesConstants.ERROR_FAILED_GENERATING_DOWNLOAD_URL,
                                    new String[] {e.getMessage()})));
        }
    }
}
