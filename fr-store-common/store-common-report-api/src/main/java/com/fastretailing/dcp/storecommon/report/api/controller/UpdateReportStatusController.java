/**
 * @(#)UpdateReportStatusController.java
 * 
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.report.api.service.UpdateReportStatusService;
import com.fastretailing.dcp.storecommon.report.api.type.ReportApiDebugId;
import com.fastretailing.dcp.storecommon.report.constant.ReportMessagesConstants;
import com.fastretailing.dcp.storecommon.report.dto.UpdateReportStatusRequest;

/**
 * Class to update Report Status.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@RestController
@RequestMapping(path = "/{brand}/{region}/stores/{store_code}/reports/status")
public class UpdateReportStatusController {

    /**
     * Common utility.
     */
    @Autowired
    private CommonUtility commonUtility;

    /**
     * Locale message source.
     */
    @Autowired
    private SystemMessageSource systemMessageSource;

    /**
     * Service class for updating report status.
     */
    @Autowired
    private UpdateReportStatusService updateReportStatusService;

    /**
     * Accepts request of updating report status.
     * 
     * @param request Request object.
     * @return Update report status response.
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStatus(
            @RequestBody @Validated UpdateReportStatusRequest request) {

        if (request.getCreateReportStatus() != null) {
            updateReportStatusService.updateCreateStatus(request);
        } else if (request.getAutoPrintStatus() != null) {
            updateReportStatusService.updateAutoPrintStatus(request);
        } else {
            throw new BusinessException(new ResultObject(ErrorName.Basis.VALIDATION_ERROR,
                    commonUtility.getDebugId(LogLevel.ERROR.toString(),
                            ReportApiDebugId.UPDATE_STATUS_BOTH_STATUS_NULL.toString()),
                    systemMessageSource
                            .getMessage(ReportMessagesConstants.ERROR_BOTH_STATUS_NULL)));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
