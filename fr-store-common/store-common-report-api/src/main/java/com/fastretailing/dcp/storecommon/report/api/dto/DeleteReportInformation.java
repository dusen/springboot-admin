/**
 * @(#)DeleteReportInformation.java
 *
 *                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class is a DTO for service execution results.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteReportInformation extends ResultObject implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -4656735326444559269L;

    /**
     * File number. The number of deleted file in AWS S3.
     */
    @JsonProperty("file_number")
    private Integer fileNumber;

    /**
     * Record number. The number of deleted records.
     */
    @JsonProperty("record_number")
    private Integer recordNumber;

}
