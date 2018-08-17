/**
 * @(#)PayoffInformation.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.form;

import java.math.BigDecimal;
import lombok.Data;

/**
 * Payoff information.
 *
 */
@Data
public class PayoffInformation {

    /**
     * Payoff type code.
     */
    private String payoffTypeCode;

    /**
     * Payoff type sub number code.
     */
    private String payoffTypeSubNumberCode;

    /**
     * Amount code.
     */
    private String amountCode;

    /**
     * Payoff amount currency code.
     */
    private String payoffAmountCurrencyCode;

    /**
     * Payoff amount.
     */
    private BigDecimal payoffAmount;

    /**
     * Quantity code.
     */
    private String quantityCode;

    /**
     * Payoff quantity.
     */
    private BigDecimal payoffQuantity;
}
