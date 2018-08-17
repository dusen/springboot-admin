/**
 * @(#)UpdateReportStatusService.java
 * 
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import com.fastretailing.dcp.storecommon.report.dto.UpdateReportStatusRequest;

/**
 * Interface class for update create report status table.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public interface UpdateReportStatusService {

    /**
     * Updates create report status table.
     * 
     * @param request report Create StatusDto.
     * @return update result.
     */
    int updateCreateStatus(UpdateReportStatusRequest request);

    /**
     * Updates auto print status table.
     * 
     * @param request Request for updating auto print status.
     * @return update result.
     */
    int updateAutoPrintStatus(UpdateReportStatusRequest request);
}
