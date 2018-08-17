/**
 * @(#)GenerateReportRequestBase.java
 * 
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.report.config.CreateReportBaseCheckGroup;
import lombok.Data;

/**
 * Abstract Order Request class to generate.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class GenerateReportRequestBase implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -8329557750100831988L;

    /**
     * Receipt number.
     */
    @JsonProperty("receipt_number")
    @NotBlank
    private String receiptNumber;

    /**
     * Country Code.
     */
    @JsonProperty("country_code")
    @NotBlank(groups = {Default.class, CreateReportBaseCheckGroup.class})
    private String countryCode;

    /**
     * Store Code.
     */
    @JsonProperty("store_code")
    @NotBlank(groups = {Default.class, CreateReportBaseCheckGroup.class})
    private String storeCode;

    /**
     * View Store Code.
     */
    @JsonProperty("view_store_code")
    @NotBlank(groups = {Default.class, CreateReportBaseCheckGroup.class})
    private String viewStoreCode;

    /**
     * Store Name.
     */
    @JsonProperty("store_name")
    @NotBlank(groups = {Default.class, CreateReportBaseCheckGroup.class})
    private String storeName;

    /**
     * System ID.
     */
    @JsonProperty("system_id")
    @NotBlank(groups = {Default.class, CreateReportBaseCheckGroup.class})
    private String systemId;

    /**
     * Create Report Business Day.
     */
    @JsonProperty("create_report_business_day")
    @NotNull(groups = {Default.class, CreateReportBaseCheckGroup.class})
    private OffsetDateTime createReportBusinessDay;

    /**
     * Delete Report Business Day.
     */
    @JsonProperty("delete_report_business_day")
    @NotNull
    private OffsetDateTime deleteReportBusinessDay;

    /**
     * Report ID.
     */
    @JsonProperty("report_id")
    @NotBlank(groups = {Default.class, CreateReportBaseCheckGroup.class})
    @Size(min = 1, max = 14, groups = {Default.class, CreateReportBaseCheckGroup.class})
    private String reportId;

    /**
     * Report Title.
     */
    @JsonProperty("report_title")
    @NotNull
    @Size(min = 1, max = 50)
    private String reportTitle;

    /**
     * S3 Report Form BucketName.
     */
    @JsonProperty("report_form_bucket_name")
    @NotBlank
    @Size(min = 1, max = 256)
    private String reportFormBucketName;

    /**
     * S3 Report Form key.
     */
    @JsonProperty("report_form_key_name")
    @NotBlank
    @Size(min = 1, max = 256)
    private String reportFormKeyName;

    /**
     * S3 buketName of output report.
     */
    @JsonProperty("created_report_bucket_name")
    @NotBlank
    @Size(min = 1, max = 256)
    private String createdReportBucketName;

    /**
     * Create report date time.
     */
    @JsonProperty("create_report_date_time")
    @NotNull
    private String createReportDateTime;
}
