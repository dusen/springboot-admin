/**
 * @(#)TenderPaymentOptional.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import lombok.Data;

/**
 * Tender payment optional.
 */
@Data
public class TenderPaymentOptional {

    /**
     * Payoff type code.
     */
    private String payoffTypeCode;

    /**
     * Tender name.
     */
    private String tenderName;

    /**
     * Payoff type sub number code.
     */
    private String payoffTypeSubNumberCode;

    /**
     * Payoff amount currency code.
     */
    private String payoffAmountCurrencyCode;

    /**
     * Payoff amount.
     */
    private BigDecimal payoffAmount;
}
