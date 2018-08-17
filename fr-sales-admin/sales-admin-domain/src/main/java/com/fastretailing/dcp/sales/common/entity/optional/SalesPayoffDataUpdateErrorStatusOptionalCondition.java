/**
 * @(#)SalesPayoffDataUpdateErrorStatusOptionalContidion.java
 *
 *                                                           Copyright (c) 2018 Fast Retailing
 *                                                           Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

/**
 * Sales payoff data update error status condition.
 */
@Data
public class SalesPayoffDataUpdateErrorStatusOptionalCondition {

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * System Brand Code.
     */
    private String systemBrandCode;

    /**
     * System Country Code.
     */
    private String systemCountryCode;

    /**
     * Cash register no.
     */
    private Integer cashRegisterNo;
}
