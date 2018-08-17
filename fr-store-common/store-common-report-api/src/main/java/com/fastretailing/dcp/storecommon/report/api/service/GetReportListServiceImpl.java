/**
 * @(#)GetReportListServiceImpl.java
 * 
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
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
import com.fastretailing.dcp.storecommon.report.dto.GetReportListRequest;

/**
 * Service class of Get Report List.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Service
public class GetReportListServiceImpl implements GetReportListService {

    /**
     * Report Create Status Component.
     */
    @Autowired
    private ReportCreateStatusMapper reportCreateStatusMapper;

    /**
     * Common Utility.
     */
    @Autowired
    private CommonUtility commonUtility;

    /**
     * Locale message source.
     */
    @Autowired
    private SystemMessageSource systemMessageSource;

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<ReportCreateStatus> getReportList(GetReportListRequest request) {

        List<String> receiptNumberList = request.getReceiptNumberList();
        List<String> reportIdList = request.getReportIdList();

        if (CollectionUtils.isNotEmpty(receiptNumberList)) {
            return receiptNumberList.stream()
                    .map(receiptNumber -> reportCreateStatusMapper
                            .selectByReceiptNumber(receiptNumber, 
                                    request.getCreateReportDatetime().toLocalDateTime()))
                    .collect(Collectors.toList());
        } else if (CollectionUtils.isNotEmpty(reportIdList)) {
            return reportIdList.stream()
                    .flatMap(
                            reportId -> reportCreateStatusMapper
                                    .selectByBusinessDay(reportId, request.getStoreCode(),
                                            request.getBusinessDayFrom().toLocalDate(),
                                            request.getBusinessDayTo().toLocalDate(), 
                                            request.getCreateReportDatetime().toLocalDateTime())
                                    .stream())
                    .collect(Collectors.toList());
        } else {
            throw new BusinessException(new ResultObject(ErrorName.Basis.VALIDATION_ERROR,
                    commonUtility.getDebugId(LogLevel.ERROR.toString(),
                            ReportApiDebugId.GET_LIST_ID_NUMBER_BOTH_EMPTY.toString()),
                    systemMessageSource.getMessage(ReportMessagesConstants.ERROR_BOTH_LIST_NULL)));
        }
    }
}
