/**
 * @(#)ItemDiscount.java
 * 
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Item discount.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class ItemDiscount {
    @JsonProperty("item_discount_detail_sub_number")
    private Integer itemDiscountDetailSubNumber = null;

    @JsonProperty("item_discount_sub_number")
    private Integer itemDiscountSubNumber = null;

    @JsonProperty("item_promotion_type")
    private String itemPromotionType = null;

    @JsonProperty("item_promotion_number")
    private String itemPromotionNumber = null;

    @JsonProperty("item_store_discount_type")
    private String itemStoreDiscountType = null;

    @JsonProperty("item_quantity_code")
    private String itemQuantityCode = null;

    @JsonProperty("item_discount_qty")
    private Integer itemDiscountQty = null;

    @JsonProperty("item_discount_amt_tax_excluded")
    private Price itemDiscountAmtTaxExcluded = null;

    @JsonProperty("item_discount_amt_tax_included")
    private Price itemDiscountAmtTaxIncluded = null;

}

