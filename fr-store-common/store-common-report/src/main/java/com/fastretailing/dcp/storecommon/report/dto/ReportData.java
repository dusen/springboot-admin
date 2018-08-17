/**
 * @(#)ReportData.java
 * 
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is DTO of report data.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportData implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -8474191212359867740L;

    /**
     * Receipt Number.
     */
    @JsonProperty("receipt_number")
    @NotBlank
    private String receiptNumber;

    /**
     * Report ID.
     */
    @JsonProperty("report_id")
    private String reportId;

    /**
     * Report Type.
     */
    @JsonProperty("report_type")
    private String reportType;

    /**
     * Store Code.
     */
    @JsonProperty("store_code")
    private String storeCode;

    /**
     * Create Report Status.
     */
    @JsonProperty("create_report_status")
    private Integer createReportStatus;

    /**
     * Auto Print Status.
     */
    @JsonProperty("auto_print_status")
    private Integer autoPrintStatus;

    /**
     * S3 BucketName.
     */
    @JsonProperty("created_report_bucket_name")
    private String createdReportBucketName;

    /**
     * S3 Key.
     */
    @JsonProperty("created_report_key_name")
    private String createdReportKeyName;

    /**
     * Printer Name.
     */
    @JsonProperty("printer_name")
    private String printerName;

    /**
     * Outer Command Execute Flag.
     */
    @JsonProperty("outer_command_execute_flag")
    private Boolean outerCommandExecuteFlag;

}
