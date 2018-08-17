/**
 * @(#)PayoffType.java
 *
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Pay off type.
 */
@Data
public class PayOffType {

    /** Pay off type code. */
    @JsonProperty("payoff_type_code")
    private String payoffTypeCode = null;

    /** Pay off type sub number code. */
    @JsonProperty("payoff_type_sub_number_code")
    private String payoffTypeSubNumberCode = null;

    /** Amount code. */
    @JsonProperty("amount_code")
    private String amountCode = null;

    /** Pay off amount. */
    @JsonProperty("payoff_amount")
    private PayOffAmount payoffAmount;

    /** Pay off quantity. */
    @JsonProperty("payoff_quantity")
    private Integer payOffQuantity = null;

    /** Quantity code. */
    @JsonProperty("quantity_code")
    private String quantityCode = null;
}
