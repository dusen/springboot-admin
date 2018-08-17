/**
 * @(#)GenerateReportResponse.java
 * 
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response class for generate report.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenerateReportResponse extends ResultObject implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 7247940544265995153L;

    /**
     * Receipt number.
     */
    @JsonProperty("receipt_number")
    private String receiptNumber;

    /**
     * Generate report result.
     */
    @JsonProperty("generate_report_result")
    private Integer generateReportResult;

    /**
     * Generate Report Details.
     */
    @JsonProperty("generate_report_details")
    private String generateReportDetails;

    /**
     * Created report S3 bucket name.
     */
    @JsonProperty("created_report_bucket_name")
    private String createdReportBucketName;

    /**
     * Created report S3 key.
     */
    @JsonProperty("created_report_key_name")
    private String createdReportKeyName;

}
