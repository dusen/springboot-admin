/**
 * @(#)GetReportListService.java
 * 
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import java.util.List;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.dto.GetReportListRequest;

/**
 * Interface class for get report list.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public interface GetReportListService {

    /**
     * Gets report list.
     * 
     * @param request Get report list request.
     * @return Report list.
     */
    List<ReportCreateStatus> getReportList(GetReportListRequest request);
}
