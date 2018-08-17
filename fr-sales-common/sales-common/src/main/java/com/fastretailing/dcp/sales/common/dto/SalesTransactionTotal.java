/**
 * @(#)SalesTransactionTotal.java
 * 
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Sales transaction total.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class SalesTransactionTotal {
    @JsonProperty("total_amount_sub_number")
    private Integer totalAmountSubNumber = null;

    @JsonProperty("total_type")
    private String totalType = null;

    @JsonProperty("total_amount_tax_excluded")
    private Price totalAmountTaxExcluded = null;

    @JsonProperty("total_amount_tax_included")
    private Price totalAmountTaxIncluded = null;

    @JsonProperty("consumption_tax_rate")
    private BigDecimal consumptionTaxRate = null;

    @JsonProperty("sales_transaction_information_1")
    private String salesTransactionInformation1 = null;

    @JsonProperty("sales_transaction_information_2")
    private String salesTransactionInformation2 = null;

    @JsonProperty("sales_transaction_information_3")
    private String salesTransactionInformation3 = null;

}

