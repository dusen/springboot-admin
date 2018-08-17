/**
 * @(#)ReportCreateStatus.java
 * 
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is entity of report create status.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportCreateStatus {

    /**
     * Receipt number.
     */
    @NotBlank
    @Size(max = 50)
    private String receiptNumber;

    /**
     * Report id.
     */
    @Size(max = 14)
    private String reportId;

    /**
     * Report type.
     */
    @Size(max = 5)
    private String reportType;

    /**
     * Store code.
     */
    @NotBlank
    @Size(max = 10)
    private String storeCode;

    /**
     * Create report status.
     */
    @Min(0)
    @Max(3)
    private Integer createReportStatus;

    /**
     * Auto print status.
     */
    @Min(0)
    @Max(3)
    private Integer autoPrintStatus;

    /**
     * S3 BucketName.
     */
    @Size(max = 256)
    private String createdReportBucketName;

    /**
     * S3 Key.
     */
    @Size(max = 256)
    private String createdReportKeyName;

    /**
     * Create report business day.
     */
    private LocalDate createReportBusinessDay;

    /**
     * Delete report business day.
     */
    private LocalDate deleteReportBusinessDay;

    /**
     * Printer name.
     */
    @Size(max = 50)
    private String printerName;

    /**
     * Outer command execute flag.
     */
    private Boolean outerCommandExecuteFlag;

    /**
     * Create datetime.
     */
    private LocalDateTime createDatetime;

    /**
     * Update datetime.
     */
    private LocalDateTime updateDatetime;

}
