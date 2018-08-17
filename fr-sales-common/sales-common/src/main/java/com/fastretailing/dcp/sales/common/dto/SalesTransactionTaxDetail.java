/**
 * @(#)SalesTransactionTaxDetail.java
 * 
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Sales transaction tax detail.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class SalesTransactionTaxDetail {
    @JsonProperty("tax_sub_number")
    private Integer taxSubNumber = null;

    @JsonProperty("tax_group")
    private String taxGroup = null;

    @JsonProperty("tax_amount_sign")
    private String taxAmountSign = null;

    @JsonProperty("tax_amount")
    private Price taxAmount = null;

    @JsonProperty("tax_rate")
    private BigDecimal taxRate = null;

}

