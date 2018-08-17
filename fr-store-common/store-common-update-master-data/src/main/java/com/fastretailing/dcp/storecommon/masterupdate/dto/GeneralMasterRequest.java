/**
 * @(#)GeneralMasterRequest.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate.dto;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import lombok.Data;

/**
 * Request data bean class.
 */
@Data
public class GeneralMasterRequest {

    @NotBlank
    @Size(max = 17)
    @Alphanumeric
    /** Out bound id of properties. */
    private String outboundId;

    @NotBlank
    @Size(max = 22)
    @Alphanumeric
    @JsonProperty(value = "lotNum")
    /** Lot number of properties. */
    private String lotNumber;
}
