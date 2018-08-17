/**
 * @(#)DeleteReportDataController.java
 * 
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fastretailing.dcp.storecommon.report.api.dto.DeleteReportInformation;
import com.fastretailing.dcp.storecommon.report.api.service.DeleteReportDataService;

/**
 * Class to delete Report Create Status Data.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@RestController
@RequestMapping(path = "{brand}/{region}/reports")
public class DeleteReportDataController {

    /**
     * Service class for delete report data.
     */
    @Autowired
    private DeleteReportDataService deleteReportDataService;

    /**
     * Call delete report data service and return delete report information.
     * 
     * @return Delete report information.
     */
    @DeleteMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DeleteReportInformation> deleteReportData() {
        int effectedNumber = deleteReportDataService.getDeleteTargetList()
                .stream()
                .mapToInt(status -> deleteReportDataService.deleteReport(status))
                .sum();

        DeleteReportInformation result = new DeleteReportInformation();
        result.setFileNumber(effectedNumber);
        result.setRecordNumber(effectedNumber);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
