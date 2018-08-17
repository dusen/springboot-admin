/**
 * @(#)UpdateReportStatusServiceImpl.java
 * 
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.fastretailing.dcp.storecommon.report.dto.UpdateReportStatusRequest;
import com.fastretailing.dcp.storecommon.report.type.AutoPrintStatus;
import com.fastretailing.dcp.storecommon.report.type.ReportStatusCode;

/**
 * Service class of Update Report Status.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Service
public class UpdateReportStatusServiceImpl implements UpdateReportStatusService {

    /**
     * Common Utility.
     */
    @Autowired
    private CommonUtility commonUtility;

    /**
     * Component for operating DB operations on the t_report_create_status.
     */
    @Autowired
    private ReportCreateStatusMapper reportCreateStatusMapper;

    /**
     * Locale message source.
     */
    @Autowired
    private SystemMessageSource systemMessageSource;

    /**
     * {@inheritDoc}}.
     */
    @Transactional
    @Override
    public int updateCreateStatus(UpdateReportStatusRequest request) {

        Integer createReportStatus = request.getCreateReportStatus();

        ReportCreateStatus reportCreateStatus = new ReportCreateStatus();
        reportCreateStatus.setReceiptNumber(request.getReceiptNumber());

        // update create status.
        if (ReportStatusCode.SUCCESS.is(createReportStatus)) {

            checkS3Parameter(request);

            reportCreateStatus.setCreateReportStatus(request.getCreateReportStatus());
            reportCreateStatus.setCreatedReportBucketName(request.getCreatedReportBucketName());
            reportCreateStatus.setCreatedReportKeyName(request.getCreatedReportKeyName());

            return reportCreateStatusMapper.updateCreateStatus(reportCreateStatus);
        } else if (ReportStatusCode.FAILURE.is(createReportStatus)) {

            reportCreateStatus.setCreateReportStatus(request.getCreateReportStatus());

            return reportCreateStatusMapper.updateCreateStatus(reportCreateStatus);
        } else {
            throw new BusinessException(new ResultObject(ErrorName.Basis.VALIDATION_ERROR,
                    commonUtility.getDebugId(LogLevel.ERROR.toString(),
                            ReportApiDebugId.UPDATE_STATUS_CREATE_STATUS_INVALID.toString()),
                    systemMessageSource.getMessage(
                            ReportMessagesConstants.ERROR_CREATE_REPORT_STATUS_INVALID,
                            new Object[] {createReportStatus})));
        }
    }

    /**
     * {@inheritDoc}}.
     */
    @Transactional
    @Override
    public int updateAutoPrintStatus(UpdateReportStatusRequest request) {

        Integer autoPrintStatus = request.getAutoPrintStatus();

        ReportCreateStatus reportCreateStatus = new ReportCreateStatus();
        reportCreateStatus.setReceiptNumber(request.getReceiptNumber());

        // Update auto print status.
        if (AutoPrintStatus.SUCCESS.is(autoPrintStatus)
                || AutoPrintStatus.FAILURE.is(autoPrintStatus)) {
            reportCreateStatus.setAutoPrintStatus(request.getAutoPrintStatus());

            return reportCreateStatusMapper.updateAutoPrintStatus(reportCreateStatus);
        } else {
            throw new BusinessException(new ResultObject(ErrorName.Basis.VALIDATION_ERROR,
                    commonUtility.getDebugId(LogLevel.ERROR.toString(),
                            ReportApiDebugId.UPDATE_STATUS_AUTO_PRINT_STATUS_INVALID.toString()),
                    systemMessageSource.getMessage(
                            ReportMessagesConstants.ERROR_AUTO_PRINT_STATUS_INVALID,
                            new Object[] {autoPrintStatus})));
        }
    }

    /**
     * Check optional S3 parameters.
     * 
     * @param request Request object for updating report status.
     */
    private void checkS3Parameter(UpdateReportStatusRequest request) {

        if (StringUtils.isEmpty(request.getCreatedReportBucketName())) {
            throw new BusinessException(new ResultObject(ErrorName.Basis.VALIDATION_ERROR,
                    commonUtility.getDebugId(LogLevel.ERROR.toString(),
                            ReportApiDebugId.UPDATE_STATUS_S3_BUCKET_NAME_EMPTY.toString()),
                    systemMessageSource.getMessage(ReportMessagesConstants.ERROR_VALIDATION_API,
                            new Object[] {"s3BucketName"})));
        }

        if (StringUtils.isEmpty(request.getCreatedReportKeyName())) {
            throw new BusinessException(new ResultObject(ErrorName.Basis.VALIDATION_ERROR,
                    commonUtility.getDebugId(LogLevel.ERROR.toString(),
                            ReportApiDebugId.UPDATE_STATUS_S3_KEY_EMPTY.toString()),
                    systemMessageSource.getMessage(ReportMessagesConstants.ERROR_VALIDATION_API,
                            new Object[] {"s3Key"})));
        }
    }
}
