/**
 * @(#)UpdateReportStatusRequest.java
 * 
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is DTO of report create status.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReportStatusRequest implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 3798748458287232730L;

    /**
     * Receipt Number.
     */
    @JsonProperty("receipt_number")
    @NotBlank
    @Size(max = 50)
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
     * Create Report Status.
     */
    @JsonProperty("create_report_status")
    @Min(0)
    @Max(3)
    private Integer createReportStatus;

    /**
     * Auto Print Status.
     */
    @JsonProperty("auto_print_status")
    @Min(0)
    @Max(3)
    private Integer autoPrintStatus;

    /**
     * S3 BucketName.
     */
    @JsonProperty("created_report_bucket_name")
    @Size(max = 256)
    private String createdReportBucketName;

    /**
     * S3 Key.
     */
    @JsonProperty("created_report_key_name")
    @Size(max = 256)
    private String createdReportKeyName;

    /**
     * Create Report Business Day.
     */
    @JsonProperty("create_report_business_day")
    private OffsetDateTime createReportBusinessDay;

    /**
     * Delete Report Business Day.
     */
    @JsonProperty("delete_report_business_day")
    private OffsetDateTime deleteReportBusinessDay;

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
