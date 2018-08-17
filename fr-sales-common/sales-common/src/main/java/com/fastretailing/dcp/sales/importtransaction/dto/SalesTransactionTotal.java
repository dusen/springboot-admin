
/**
 * @(#)SalesTransactionTotal.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.dto;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Correction;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Insert;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.PosInsert;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Sales transaction total.
 * 
 */
@Data
public class SalesTransactionTotal {

    /** Total amount sub number. */
    @JsonProperty("total_amount_sub_number")
    @Max(9999)
    private Integer totalAmountSubNumber;

    /** Total type. */
    @JsonProperty("total_type")
    @NotBlank(groups = {Insert.class, Correction.class, PosInsert.class})
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String totalType;

    /** Total amount tax excluded. */
    @JsonProperty("total_amount_tax_excluded")
    @Valid
    private Price totalAmountTaxExcluded;

    /** Total amount tax included. */
    @JsonProperty("total_amount_tax_included")
    @Valid
    private Price totalAmountTaxIncluded;

    /** Consumption tax rate. */
    @JsonProperty("consumption_tax_rate")
    @Digits(fraction = 4, integer = 12)
    private BigDecimal consumptionTaxRate;

    /** Sales transaction information 1. */
    @JsonProperty("sales_transaction_information_1")
    @Size(max = 250)
    private String salesTransactionInformation1;

    /** Sales transaction information 2. */
    @JsonProperty("sales_transaction_information_2")
    @Size(max = 250)
    private String salesTransactionInformation2;

    /** Sales transaction information 3. */
    @JsonProperty("sales_transaction_information_3")
    @Size(max = 250)
    private String salesTransactionInformation3;

}
