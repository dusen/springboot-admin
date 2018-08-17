/**
 * @(#)Price.java
 *
 *                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import lombok.Data;

/**
 * Price.
 * 
 * @deprecated use {@link com.fastretailing.dcp.storecommon.dto.Price}
 */
@Data
@Deprecated
public class Price {

    /** currencyCode. */
    @JsonProperty("currency_code")
    @Size(max = 3)
    @Alphanumeric
    private String currencyCode;

    /** value. */
    @JsonProperty("value")
    @Digits(fraction = 4, integer = 20)
    private BigDecimal value;
}
