/**
 * @(#)NonItemDiscountDetail.java
 * 
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Non item discount detail.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class NonItemDiscountDetail {
    @JsonProperty("non_item_discount_detail_sub_number")
    private Integer nonItemDiscountDetailSubNumber = null;

    @JsonProperty("non_item_discount_sub_number")
    private Integer nonItemDiscountSubNumber = null;

    @JsonProperty("non_item_promotion_type")
    private String nonItemPromotionType = null;

    @JsonProperty("non_item_store_discount_type")
    private String nonItemStoreDiscountType = null;

    @JsonProperty("non_item_quantity_code")
    private String nonItemQuantityCode = null;

    @JsonProperty("non_item_discount_qty")
    private Integer nonItemDiscountQty = null;

    @JsonProperty("non_item_promotion_number")
    private String nonItemPromotionNumber = null;

    @JsonProperty("non_item_discount_amt_tax_excluded")
    private Price nonItemDiscountAmtTaxExcluded = null;

    @JsonProperty("non_item_discount_amt_tax_included")
    private Price nonItemDiscountAmtTaxIncluded = null;

}

