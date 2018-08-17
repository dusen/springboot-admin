/**
 * @(#)GlobalHeaders.java
 *
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.entity.edf;

import lombok.Data;

/**
 * Global headers.
 *
 */
@Data
public class GlobalHeaders {
    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Payoff date.
     */
    private String payoffDate;
}
