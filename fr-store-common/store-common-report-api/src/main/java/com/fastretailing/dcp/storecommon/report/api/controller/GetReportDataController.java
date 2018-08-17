/**
 * @(#)GetReportDataController.java
 * 
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fastretailing.dcp.storecommon.report.api.service.GetReportDataService;
import com.fastretailing.dcp.storecommon.report.dto.GetReportDataResponse;

/**
 * Class to get Auto Print Report Data.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@RestController
@RequestMapping(path = "/{brand}/{region}/stores/{store_code}/reports/{receipt_number}")
public class GetReportDataController {

    /**
     * Service class for getting report data.
     */
    @Autowired
    private GetReportDataService getReportDataService;

    /**
     * Accepts request of getting report data.
     * 
     * @param receiptNumber Receipt number of report to get.
     * @return Get report data.
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReportDataResponse> getReportData(
            @PathVariable("receipt_number") String receiptNumber) {
        
        GetReportDataResponse response = new GetReportDataResponse();

        response.setReportDownloadUrl(getReportDataService.getReportDownloadUrl(receiptNumber));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
