/**
 * @(#)CallSalesPayoffIntegrityCheckRequest.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.sales.common.resttemplate.client.entity.sales;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Call sales payoff integrity check API request parameters.
 */
@Data
@AllArgsConstructor
public class CallSalesPayoffIntegrityCheckRequest {
    /** Store code. */
    private String storeCode;

    /** Business code. */
    private String businessCode;

    /** Execution type. */
    private String executionType;
}
