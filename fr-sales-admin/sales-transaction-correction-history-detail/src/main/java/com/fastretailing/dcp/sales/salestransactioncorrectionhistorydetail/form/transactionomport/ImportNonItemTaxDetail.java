/**
 * @(#)ImportNonItemTaxDetail.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Import non item tax detail.
 * 
 */
@Data
public class ImportNonItemTaxDetail {

    /** Non item tax detail sub number. */
    @JsonProperty("non_item_tax_detail_sub_number")
    @Max(9999)
    private Integer nonItemTaxDetailSubNumber;

    /** Non item tax_discount sub number. */
    @JsonProperty("non_item_tax_discount_sub_number")
    @Max(9999)
    private Integer nonItemTaxDiscountSubNumber;

    /** Non item tax sub number. */
    @JsonProperty("non_item_tax_sub_number")
    @Max(9999)
    private Integer nonItemTaxSubNumber;

    /** Non item tax type. */
    @JsonProperty("non_item_tax_type")
    @Size(max = 10)
    @Alphanumeric
    private String nonItemTaxType;

    /** Non item tax name. */
    @JsonProperty("non_item_tax_name")
    @Size(max = 120)
    private String nonItemTaxName;

    /** Non item tax amount sign. */
    @JsonProperty("non_item_tax_amount_sign")
    @Size(max = 1)
    @Alphanumeric
    private String nonItemTaxAmountSign;

    /** Non item tax amount. */
    @JsonProperty("non_item_tax_amt")
    @Valid
    private Price nonItemTaxAmt;

    /** Non item tax rate. */
    @JsonProperty("non_item_tax_rate")
    @Digits(fraction = 4, integer = 12)
    private BigDecimal nonItemTaxRate;

}
