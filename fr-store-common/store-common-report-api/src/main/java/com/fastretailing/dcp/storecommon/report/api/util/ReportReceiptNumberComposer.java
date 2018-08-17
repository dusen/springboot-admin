/**
 * @(#)ReportReceiptNumberComposer.java
 * 
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportReceiptNumberSeqMapper;
import com.fastretailing.dcp.storecommon.util.DateUtility;

/**
 * Composes a report's receipt Number.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
public class ReportReceiptNumberComposer {

    /** Delimiter for composing number. */
    public static final String REPORT_RECEIPT_NUMBER_DELIMITER = "_";

    /**
     * Mapper class for getting next value from sequence.
     */
    @Autowired
    private ReportReceiptNumberSeqMapper receiptNumberSeqMapper;

    /**
     * Composes a report's receipt Number.<br>
     * SystemId_storeCode_reportId_yyyyMMddhhmmss_[sequence no].
     * 
     * @param systemId Short name of Platform.
     * @param storeCode A store code.
     * @param reportId A report id.
     * @return Report's receipt Number.
     */
    public String compose(String systemId, String storeCode, String reportId) {

        String currentTime =
                DateUtility.formatDateTime(DateUtility.getZonedDateTimeUtc().toLocalDateTime(),
                        DateUtility.DateTimeFormat.UUUUMMDDHHMMSS);

        return String.join(REPORT_RECEIPT_NUMBER_DELIMITER, systemId, storeCode, reportId,
                currentTime, receiptNumberSeqMapper.nextValue());
    }
}
