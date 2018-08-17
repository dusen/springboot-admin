/**
 * @(#)ItemDetailTaxCsv.java
 *
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncsv.dto;

import java.math.BigDecimal;
import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import lombok.Data;

/**
 * Item detail tax csv.
 */
@Data
@CsvBean(header = true)
public class ItemDetailTaxCsv {

    /** Transaction id. */
    @CsvColumn(number = 1, label = "transaction_id")
    private String transactionId;

    /** Order sub number. */
    @CsvColumn(number = 2, label = "order_sub_number")
    private Integer orderSubNumber;

    /** Sales transaction id. */
    @CsvColumn(number = 3, label = "sales_transaction_id")
    private String salesTransactionId;

    /** Detail sub number. */
    @CsvColumn(number = 4, label = "detail_sub_number")
    private Integer detailSubNumber;

    /** Tax group. */
    @CsvColumn(number = 5, label = "tax_group")
    private String taxGroup;

    /** Tax sub number. */
    @CsvColumn(number = 6, label = "tax_sub_number")
    private Integer taxSubNumber;

    /** Tax amount sign. */
    @CsvColumn(number = 7, label = "tax_amount_sign")
    private String taxAmountSign;

    /** Tax amount currency code. */
    @CsvColumn(number = 8, label = "tax_amount_currency_code")
    private String taxAmountCurrencyCode;

    /** Tax amount value. */
    @CsvColumn(number = 9, label = "tax_amount_value")
    private BigDecimal taxAmountValue;

    /** Tax rate. */
    @CsvColumn(number = 10, label = "tax_rate")
    private BigDecimal taxRate;

    /** Tax name. */
    @CsvColumn(number = 11, label = "tax_name")
    private String taxName;
}
