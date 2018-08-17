/**
 * @(#)SalesPayoffIntegrityCheckOptional.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import lombok.Data;

/**
 * Sales payoff integrity check optional.
 */
@Data
public class SalesPayoffIntegrityCheckOptional {

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Payoff date.
     */
    private String payoffDate;

    /**
     * Payoff type code.
     */
    private String payoffTypeCode;

    /**
     * Payoff type sub number code.
     */
    private String payoffTypeSubNumberCode;

    /**
     * Cash register no.
     */
    private Integer cashRegisterNo;

    /**
     * Payoff amount.
     */
    private BigDecimal payoffAmount;

    /**
     * Payoff quantity.
     */
    private BigDecimal payoffQuantity;

    /**
     * Payoff type check target flag.
     */
    private String payoffTypeCheckTargetFlag;

    /**
     * Balance type.
     */
    private String balanceType;

    /**
     * Amount code.
     */
    private String amountCode;

    /**
     * Advance received store code.
     */
    private String advanceReceivedStoreCode;
}
