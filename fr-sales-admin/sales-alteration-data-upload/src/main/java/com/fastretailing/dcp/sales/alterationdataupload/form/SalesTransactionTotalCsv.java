/**
 * @(#)SalesTransactionTotalCsv.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * Sales transaction total csv.
 */
@Data
@JsonPropertyOrder({"transaction_id", "order_sub_number", "sales_transaction_id", "total_type",
        "total_amount_sub_number", "total_amount_tax_excluded_currency_code",
        "total_amount_tax_excluded_value", "total_amount_tax_included_currency_code",
        "total_amount_tax_included_value"})
public class SalesTransactionTotalCsv {

    /** Transaction id. */
    @JsonProperty("transaction_id")
    private String transactionId;

    /** Order sub number. */
    @JsonProperty("order_sub_number")
    private String orderSubNumber;

    /** Sales transaction id. */
    @JsonProperty("sales_transaction_id")
    private String salesTransactionId;

    /** Total type. */
    @JsonProperty("total_type")
    private String totalType;

    /** Total amount sub number. */
    @JsonProperty("total_amount_sub_number")
    private String totalAmountSubNumber;

    /** Total amount tax excluded currency code. */
    @JsonProperty("total_amount_tax_excluded_currency_code")
    private String totalAmountTaxExcludedCurrencyCode;

    /** Total amount tax excluded value. */
    @JsonProperty("total_amount_tax_excluded_value")
    private String totalAmountTaxExcludedValue;

    /** Total amount tax included currency code. */
    @JsonProperty("total_amount_tax_included_currency_code")
    private String totalAmountTaxIncludedCurrencyCode;

    /** Total amount tax included value. */
    @JsonProperty("total_amount_tax_included_value")
    private String totalAmountTaxIncludedValue;

}
