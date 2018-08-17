/**
 * @(#)ItemDetailDiscountCsv.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncsv.dto;

import java.math.BigDecimal;
import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import lombok.Data;

/**
 * Item detail discount csv.
 */
@Data
@CsvBean(header = true)
public class ItemDetailDiscountCsv {

    /** Transaction id. */
    @CsvColumn(number = 1, label = "transaction_id")
    private String transactionId;

    /** Order sub number. */
    @CsvColumn(number = 2, label = "order_sub_number")
    private Integer orderSubNumber;

    /** Sales transaction id. */
    @CsvColumn(number = 3, label = "sales_transaction_id")
    private String salesTransactionId;

    /** Detail sub Number. */
    @CsvColumn(number = 4, label = "detail_sub_number")
    private Integer detailSubNumber;

    /** Promotion type. */
    @CsvColumn(number = 5, label = "promotion_type")
    private String promotionType;

    /** Promotion no. */
    @CsvColumn(number = 6, label = "promotion_no")
    private String promotionNo;

    /** Store discount type. */
    @CsvColumn(number = 7, label = "store_discount_type")
    private String storeDiscountType;

    /** Item discount sub number. */
    @CsvColumn(number = 8, label = "item_discount_sub_number")
    private Integer itemDiscountSubNumber;

    /** Quantity code. */
    @CsvColumn(number = 9, label = "quantity_code")
    private String quantityCode;

    /** Discount quantity. */
    @CsvColumn(number = 10, label = "discount_quantity")
    private Integer discountQuantity;

    /** Discount amount tax excluded currency code. */
    @CsvColumn(number = 11, label = "discount_amount_tax_excluded_currency_code")
    private String discountAmountTaxExcludedCurrencyCode;

    /** Discount amount tax excluded. */
    @CsvColumn(number = 12, label = "discount_amount_tax_excluded")
    private BigDecimal discountAmountTaxExcluded;

    /** Discount amount tax included currency code. */
    @CsvColumn(number = 13, label = "discount_amount_tax_included_currency_code")
    private String discountAmountTaxIncludedCurrencyCode;

    /** Discount amount tax included. */
    @CsvColumn(number = 14, label = "discount_amount_tax_included")
    private BigDecimal discountAmountTaxIncluded;
}
