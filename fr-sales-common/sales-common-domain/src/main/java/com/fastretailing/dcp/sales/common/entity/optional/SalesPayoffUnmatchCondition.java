/**
 * @(#)SalesPayoffUnmatchCondition.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

/**
 * Sales payoff unmatch condition.
 */
@Data
public class SalesPayoffUnmatchCondition {
    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System country code.
     */
    private String systemCountryCode;

    /**
     * View store code.
     */
    private String viewStoreCode;

    /**
     * Cash register no.
     */
    private Integer cashRegisterNo;

    /**
     * Payoff date from.
     */
    private String payoffDateFrom;

    /**
     * Payoff date to.
     */
    private String payoffDateTo;

    /**
     * Integrity check type.
     */
    private String integrityCheckType;
}
