/**
 * @(#)MergePdfReportService.java
 * 
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Interface for merge pdf report.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public interface MergePdfReportService {

    /**
     * Merges pdf report by number.
     * 
     * @deprecated use {@link MergePdfReportService#mergePdfReport(List, String, String)} instead.
     * @param receiptNumbers Receipt number of report to get.
     * @return Merged report data.
     */
    @Deprecated
    ByteArrayOutputStream mergePdfReport(List<String> receiptNumbers);

    /**
     * Merges pdf report by number.<br>
     * Adds to status table.<br>
     * Uploads merged file to S3.<br>
     * Return download URL.<br>
     * 
     * @param receiptNumbers Receipt number of report to get.
     * @param bucketName Bucket name where to be uploaded to.
     * @return Merged report's download URL.
     */
    String mergePdfReport(List<String> receiptNumbers, String bucketName);
}
