/**
 * @(#)ItemDetailTax.java
 *
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * Item detail tax.
 */
@Data
@JsonPropertyOrder({"transaction_id", "order_sub_number", "sales_transaction_id",
        "detail_sub_number", "tax_group", "tax_sub_number", "tax_amount_sign",
        "tax_amount_currency_code", "tax_amount_value", "tax_rate", "tax_name"})
public class ItemDetailTax {

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

    /** Tax group. */
    @JsonProperty("tax_group")
    private String taxGroup;

    /** Tax sub number. */
    @JsonProperty("tax_sub_number")
    private String taxSubNumber;

    /** Tax amount sign. */
    @JsonProperty("tax_amount_sign")
    private String taxAmountSign;

    /** Tax amount currency code. */
    @JsonProperty("tax_amount_currency_code")
    private String taxAmountCurrencyCode;

    /** Tax amount value. */
    @JsonProperty("tax_amount_value")
    private String taxAmountValue;

    /** Tax rate. */
    @JsonProperty("tax_rate")
    private String taxRate;

    /** Tax name. */
    @JsonProperty("tax_name")
    private String taxName;

}
