/**
 * @(#)PayoffAmount.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;

import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import lombok.Data;

/**
 * Pay off amount.
 */
@Data
public class PayOffAmount {

    /** Currency code. */
    @JsonProperty("currency_code")
    @Size(max = 3)
    @Alphanumeric
    private String currencyCode;

    /** Value. */
    @JsonProperty("value")
    @Digits(fraction = 4, integer = 20)
    private BigDecimal value;
}
