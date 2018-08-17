/**
 * @(#)PayOffType.java
 * 
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.dto;

import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Pay off type.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class PayOffType {

    /** Pay off type code. */
    @JsonProperty("payoff_type_code")
    private String payoffTypeCode = null;

    /** Pay off type sub number code. */
    @JsonProperty("payoff_type_sub_number_code")
    private String payOffTypeSubNumberCode = null;

    /** Amount code. */
    @JsonProperty("amount_code")
    private String amountCode = null;

    /** Pay off amount. */
    @JsonProperty("payoff_amount")
    @Valid
    private PayOffAmount payOffAmount;

    /** Pay off quantity. */
    @JsonProperty("payoff_quantity")
    private Integer payOffQuantity = null;

    /** Quantity code. */
    @JsonProperty("quantity_code")
    private String quantityCode = null;

}

