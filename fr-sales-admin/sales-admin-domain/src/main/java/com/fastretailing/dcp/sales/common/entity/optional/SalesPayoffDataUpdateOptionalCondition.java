/**
 * @(#)SalesPayoffDataUpdateOptionalContidion.java
 *
 *                                                           Copyright (c) 2018 Fast Retailing
 *                                                           Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

/**
 * Sales payoff data update condition.
 */
@Data
public class SalesPayoffDataUpdateOptionalCondition {

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Payoff date.
     */
    private String payoffDate;

    /**
     * Cash register no.
     */
    private Integer cashRegisterNo;
}
