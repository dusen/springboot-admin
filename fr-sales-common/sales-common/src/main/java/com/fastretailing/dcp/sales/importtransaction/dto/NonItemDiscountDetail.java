/**
 * @(#)NonItemDiscountDetail.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.dto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Correction;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Insert;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Non item discount detail.
 * 
 */
@Data
public class NonItemDiscountDetail {

    /** Non item discount detail sub number. */
    @JsonProperty("non_item_discount_detail_sub_number")
    @Max(9999)
    private Integer nonItemDiscountDetailSubNumber;

    /** Non item discount sub number. */
    @JsonProperty("non_item_discount_sub_number")
    @Max(9999)
    private Integer nonItemDiscountSubNumber;

    /** Non item promotion type. */
    @JsonProperty("non_item_promotion_type")
    @Size(max = 4)
    @Alphanumeric
    @NotBlank(groups = {Insert.class, Correction.class})
    private String nonItemPromotionType;

    /** Non item promotion number. */
    @JsonProperty("non_item_promotion_number")
    @Size(max = 6)
    @Alphanumeric
    private String nonItemPromotionNumber;

    /** Non item store discount type. */
    @JsonProperty("non_item_store_discount_type")
    @Size(max = 2)
    @Alphanumeric
    private String nonItemStoreDiscountType;

    /** Non item quantity code. */
    @JsonProperty("non_item_quantity_code")
    @Size(max = 1)
    @Alphanumeric
    @NotBlank(groups = {Insert.class, Correction.class})
    private String nonItemQuantityCode;

    /** Non item discount quantity. */
    @JsonProperty("non_item_discount_qty")
    @Max(99)
    @NotNull(groups = {Insert.class, Correction.class})
    private Integer nonItemDiscountQty;

    /** Non item discount amount tax excluded. */
    @JsonProperty("non_item_discount_amt_tax_excluded")
    @Valid
    private Price nonItemDiscountAmtTaxExcluded;

    /** Non item discount amount tax included. */
    @JsonProperty("non_item_discount_amt_tax_included")
    @Valid
    private Price nonItemDiscountAmtTaxIncluded;

}
