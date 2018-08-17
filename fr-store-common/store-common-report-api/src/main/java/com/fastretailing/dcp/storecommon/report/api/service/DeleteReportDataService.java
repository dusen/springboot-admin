/**
 * @(#)DeleteReportDataService.java
 * 
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import java.util.List;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;

/**
 * Interface class for delete report create status data.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public interface DeleteReportDataService {
    
    /**
     * Delete both report create status data and related file in S3.
     * 
     * @return Deleted records number.
     */
    int deleteReport(ReportCreateStatus status);
    
    /**
     * Gets report create status data.
     * 
     * @return Report create status list.
     */
    List<ReportCreateStatus> getDeleteTargetList();
}
