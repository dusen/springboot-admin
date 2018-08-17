/**
 * @(#)NonItemTaxDetail.java
 * 
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Non item tax detail.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class NonItemTaxDetail {
    @JsonProperty("non_item_tax_detail_sub_number")
    private Integer nonItemTaxDetailSubNumber = null;

    @JsonProperty("non_item_tax_discount_sub_number")
    private Integer nonItemTaxDiscountSubNumber = null;

    @JsonProperty("non_item_tax_type")
    private String nonItemTaxType = null;

    @JsonProperty("non_item_tax_name")
    private String nonItemTaxName = null;

    @JsonProperty("non_item_tax_amount_sign")
    private String nonItemTaxAmountSign = null;

    @JsonProperty("non_item_tax_amt")
    private Price nonItemTaxAmt = null;

    @JsonProperty("non_item_tax_rate")
    private BigDecimal nonItemTaxRate = null;

}

