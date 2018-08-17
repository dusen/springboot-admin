/**
 * @(#)SalesTransactionTotalCsv.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncsv.dto;

import java.math.BigDecimal;
import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import lombok.Data;

/**
 * Sales transaction total csv.
 */
@Data
@CsvBean(header = true)
public class SalesTransactionTotalCsv {

    /** Transaction id. */
    @CsvColumn(number = 1, label = "transaction_id")
    private String transactionId;

    /** Order sub number. */
    @CsvColumn(number = 2, label = "order_sub_number")
    private Integer orderSubNumber;

    /** Sales transaction id. */
    @CsvColumn(number = 3, label = "sales_transaction_id")
    private String salesTransactionId;

    /** Total type. */
    @CsvColumn(number = 4, label = "total_type")
    private String totalType;

    /** Total Amount sub number. */
    @CsvColumn(number = 5, label = "total_amount_sub_number")
    private Integer totalAmountSubNumber;

    /** Total amount tax excluded currency code. */
    @CsvColumn(number = 6, label = "total_amount_tax_excluded_currency_code")
    private String totalAmountTaxExcludedCurrencyCode;

    /** Total amount tax excluded value. */
    @CsvColumn(number = 7, label = "total_amount_tax_excluded_value")
    private BigDecimal totalAmountTaxExcludedValue;

    /** Total amount tax included currency code. */
    @CsvColumn(number = 8, label = "total_amount_tax_included_currency_code")
    private String totalAmountTaxIncludedCurrencyCode;

    /** Total amount tax included value. */
    @CsvColumn(number = 9, label = "total_amount_tax_included_value")
    private BigDecimal totalAmountTaxIncludedValue;

}
