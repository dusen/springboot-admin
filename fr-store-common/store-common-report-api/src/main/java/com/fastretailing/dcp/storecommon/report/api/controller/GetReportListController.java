/**
 * @(#)GetReportListController.java
 * 
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.service.GetReportListService;
import com.fastretailing.dcp.storecommon.report.dto.GetReportListRequest;
import com.fastretailing.dcp.storecommon.report.dto.GetReportListResponse;
import com.fastretailing.dcp.storecommon.report.dto.ReportData;

/**
 * Class to get report list.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@RestController
@RequestMapping(path = "/{brand}/{region}/stores/{store_code}/reports")
public class GetReportListController {

    /**
     * Service class for getting report list.
     */
    @Autowired
    private GetReportListService getReportListService;

    /**
     * Accepts request of getting report list.
     * 
     * @param request Get report list request.
     * @return Report list.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReportListResponse> getReportList(
            @RequestBody @Validated GetReportListRequest request) {
        GetReportListResponse response = new GetReportListResponse();

        List<ReportCreateStatus> reportCreateStatusList =
                getReportListService.getReportList(request);

        List<ReportData> reportData = reportCreateStatusList.stream()
                .map(reportCreateStatus -> new ReportData(reportCreateStatus.getReceiptNumber(),
                        reportCreateStatus.getReportId(), reportCreateStatus.getReportType(),
                        reportCreateStatus.getStoreCode(),
                        reportCreateStatus.getCreateReportStatus(),
                        reportCreateStatus.getAutoPrintStatus(),
                        reportCreateStatus.getCreatedReportBucketName(),
                        reportCreateStatus.getCreatedReportKeyName(),
                        reportCreateStatus.getPrinterName(),
                        reportCreateStatus.getOuterCommandExecuteFlag()))
                .collect(Collectors.toList());
        response.setReportData(reportData);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
