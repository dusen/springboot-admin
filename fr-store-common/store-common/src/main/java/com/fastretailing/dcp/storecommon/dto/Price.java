/**
 * @(#)Price.java
 *
 *                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.dto;

import java.math.BigDecimal;
import java.util.Currency;
import javax.validation.constraints.Digits;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fastretailing.dcp.storecommon.deserialize.StringToCurrencyDeserializer;
import lombok.Data;

/**
 * Price.
 */
@Data
public class Price {

    /** Currency code. */
    @JsonProperty("currency_code")
    @JsonDeserialize(using = StringToCurrencyDeserializer.class)
    private Currency currencyCode;

    /** Value. */
    @JsonProperty("value")
    @Digits(fraction = 4, integer = 20)
    private BigDecimal value;
}
