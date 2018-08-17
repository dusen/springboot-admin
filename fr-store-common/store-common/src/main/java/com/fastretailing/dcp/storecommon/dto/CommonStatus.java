/**
 * @(#)CommonStatus.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.ResultCode;
import lombok.Data;

/**
 * This class is a common DTO for managing the status of service execution results.
 */
@Data
public class CommonStatus {

    /**
     * Result code. The processing result specify by success, abnormal, warning.
     * 
     * @see ResultCode
     */
    @JsonProperty("result_code")
    private ResultCode resultCode = ResultCode.NORMAL;

    /**
     * Error code. Code to identify the error.
     */
    @JsonProperty("error_code")
    private String errorCode;

    /**
     * Error message. This message informs the request sender of the error content or cause.
     */
    @JsonProperty("error_message")
    private String errorMessage;

}
