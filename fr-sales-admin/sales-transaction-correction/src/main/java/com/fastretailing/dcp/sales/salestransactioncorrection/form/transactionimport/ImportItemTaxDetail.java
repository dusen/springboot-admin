/**
 * @(#)ImportItemTaxDetail.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.sales.salestransactioncorrection.common.DeleteCheckFlag;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Import item tax detail.
 * 
 */
@Data
public class ImportItemTaxDetail {

    /** Delete check flag. */
    private String deleteCheckFlag = DeleteCheckFlag.NORMAL.getValue();

    /** Item tax detail sub number. */
    @JsonProperty("item_tax_detail_sub_number")
    @Max(9999)
    private Integer itemTaxDetailSubNumber;

    /** Item tax sub number. */
    @JsonProperty("item_tax_sub_number")
    @Max(9999)
    private Integer itemTaxSubNumber;

    /** Item tax type. */
    @JsonProperty("item_tax_type")
    @Size(max = 10)
    @Alphanumeric
    private String itemTaxType;

    /** Item tax name. */
    @JsonProperty("item_tax_name")
    @Size(max = 120)
    private String itemTaxName;

    /** Item tax amount sign. */
    @JsonProperty("item_tax_amount_sign")
    @Size(max = 1)
    @Alphanumeric
    private String itemTaxAmountSign;

    /** Item tax amount. */
    @JsonProperty("item_tax_amt")
    @Valid
    private Price itemTaxAmt;

    /** Item tax rate. */
    @JsonProperty("item_tax_rate")
    @Digits(fraction = 4, integer = 12)
    private BigDecimal itemTaxRate;

}
