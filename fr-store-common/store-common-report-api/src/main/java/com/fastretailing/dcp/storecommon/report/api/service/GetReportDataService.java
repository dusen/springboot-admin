/**
 * @(#)GetReportDataService.java
 * 
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import java.io.ByteArrayOutputStream;

/**
 * Interface class for get report data.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public interface GetReportDataService {

    /**
     * @deprecated use {@link getReportDownloadUrl} instead.
     * 
     * @param receiptNumber Receipt number of report to get.
     * @return Report data.
     */
    @Deprecated
    ByteArrayOutputStream getReportData(String receiptNumber);

    /**
     * Method for getting report download URL.
     * 
     * @param receiptNumber Receipt number of report to get.
     * @return URL for downloading report data.
     */
    String getReportDownloadUrl(String receiptNumber);
}
