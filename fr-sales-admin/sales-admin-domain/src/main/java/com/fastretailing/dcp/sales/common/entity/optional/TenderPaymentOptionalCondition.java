/**
 * @(#)TenderPaymentOptionalCondition.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

/**
 * Tender payment optional condition.
 */
@Data
public class TenderPaymentOptionalCondition {

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
