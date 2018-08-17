/**
 * @(#)SalesErrorSalesTransactionHeaderDetailOptionalCondition.java
 *
 *                                                                  Copyright (c) 2018 Fast
 *                                                                  Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

/**
 * Sales error sales transaction header detail optional condition.
 */
@Data
public class SalesErrorSalesTransactionHeaderDetailOptionalCondition {

    /**
     * Transaction id.
     */
    private String transactionId;

    /**
     * Sales transaction id.
     */
    private String salesTransactionId;

    /**
     * Store code.
     */
    private String storeCode;
}
