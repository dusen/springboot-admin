/**
 * @(#)ItemTaxDetail.java
 * 
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Item tax detail.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class ItemTaxDetail {
    @JsonProperty("item_tax_detail_sub_number")
    private Integer itemTaxDetailSubNumber = null;

    @JsonProperty("item_tax_sub_number")
    private Integer itemTaxSubNumber = null;

    @JsonProperty("item_tax_type")
    private String itemTaxType = null;

    @JsonProperty("item_tax_name")
    private String itemTaxName = null;

    @JsonProperty("item_tax_amount_sign")
    private String itemTaxAmountSign = null;

    @JsonProperty("item_tax_amt")
    private Price itemTaxAmt = null;

    @JsonProperty("item_tax_rate")
    private BigDecimal itemTaxRate = null;

}

