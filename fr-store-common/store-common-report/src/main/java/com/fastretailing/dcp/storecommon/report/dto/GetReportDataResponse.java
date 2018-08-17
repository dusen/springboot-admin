/**
 * @(#)GetReportDataResponse.java
 * 
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response class for Get Auto Print Report.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetReportDataResponse extends ResultObject implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 5162706343729020243L;

    /**
     * Report's create status.
     */
    @JsonProperty("create_report_status")
    private Integer createReportStatus;

    /**
     * Binary of report.
     */
    @Deprecated
    @JsonProperty("report_binary")
    private byte[] reportBinary;

    /**
     * URL for downloading report.
     */
    @JsonProperty("report_download_url")
    private String reportDownloadUrl;
}
