/**
 * @(#)ImportNonItemTaxDetail.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Correction;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Insert;
import com.fastretailing.dcp.sales.salestransactioncorrection.common.DeleteCheckFlag;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Import sales transaction tax detail.
 * 
 */
@Data
public class ImportSalesTransactionTaxDetail {

    /** Delete check flag. */
    private String deleteCheckFlag = DeleteCheckFlag.NORMAL.getValue();

    /** Tax sub number. */
    @JsonProperty("tax_sub_number")
    @Max(9999)
    private Integer taxSubNumber;

    /** Tax group. */
    @JsonProperty("tax_group")
    @NotBlank(groups = {Insert.class, Correction.class})
    @Size(max = 10)
    @Alphanumeric
    private String taxGroup;

    /** Tax amount sign. */
    @JsonProperty("tax_amount_sign")
    @NotBlank(groups = {Insert.class, Correction.class})
    @Size(max = 1)
    @Alphanumeric
    private String taxAmountSign;

    /** Tax amount. */
    @JsonProperty("tax_amount")
    @NotNull(groups = {Insert.class, Correction.class})
    @Valid
    private Price taxAmount;

    /** Tax rate. */
    @JsonProperty("tax_rate")
    @NotNull(groups = {Insert.class, Correction.class})
    @Digits(fraction = 4, integer = 12)
    private BigDecimal taxRate;

}
