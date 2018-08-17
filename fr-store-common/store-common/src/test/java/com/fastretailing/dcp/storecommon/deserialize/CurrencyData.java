/**
 * @(#)CurrencyData.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.deserialize;

import java.util.Currency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

/**
 * DTO class with Currency data for testing.
 */
@Data
@JsonIgnoreProperties
public class CurrencyData {

    /**
     * Currency code.
     */
    @JsonDeserialize(using = StringToCurrencyDeserializer.class)
    private Currency currencyCode;
}
