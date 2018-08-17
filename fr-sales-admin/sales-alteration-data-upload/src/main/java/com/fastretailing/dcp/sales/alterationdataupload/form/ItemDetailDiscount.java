/**
 * @(#)ItemDetailDiscount.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * Item detail discount.
 */
@Data
@JsonPropertyOrder({"transaction_id", "order_sub_number", "sales_transaction_id",
        "detail_sub_number", "promotion_type", "promotion_no", "store_discount_type",
        "item_discount_sub_number", "quantity_code", "discount_quantity",
        "discount_amount_tax_excluded_currency_code", "discount_amount_tax_excluded",
        "discount_amount_tax_included_currency_code", "discount_amount_tax_included"})
public class ItemDetailDiscount {

    /** Transaction id. */
    @JsonProperty("transaction_id")
    private String transactionId;

    /** Order sub number. */
    @JsonProperty("order_sub_number")
    private String orderSubNumber;

    /** Sales transaction id. */
    @JsonProperty("sales_transaction_id")
    private String salesTransactionId;

    /** Detail sub number. */
    @JsonProperty("detail_sub_number")
    private String detailSubNumber;

    /** promotion type. */
    @JsonProperty("promotion_type")
    private String promotionType;

    /** Promotion no. */
    @JsonProperty("promotion_no")
    private String promotionNo;

    /** Store discount type. */
    @JsonProperty("store_discount_type")
    private String storeDiscountType;

    /** Item discount sub number. */
    @JsonProperty("item_discount_sub_number")
    private String itemDiscountSubNumber;

    /** Quantity code. */
    @JsonProperty("quantity_code")
    private String quantityCode;

    /** Discount quantity. */
    @JsonProperty("discount_quantity")
    private String discountQuantity;

    /** Discount amount tax excluded currency code. */
    @JsonProperty("discount_amount_tax_excluded_currency_code")
    private String discountAmountTaxExcludedCurrencyCode;

    /** Discount amount tax excluded. */
    @JsonProperty("discount_amount_tax_excluded")
    private String discountAmountTaxExcluded;

    /** Discount amount tax included currency code. */
    @JsonProperty("discount_amount_tax_included_currency_code")
    private String discountAmountTaxIncludedCurrencyCode;

    /** Discount amount tax included. */
    @JsonProperty("discount_amount_tax_included")
    private String discountAmountTaxIncluded;
}
