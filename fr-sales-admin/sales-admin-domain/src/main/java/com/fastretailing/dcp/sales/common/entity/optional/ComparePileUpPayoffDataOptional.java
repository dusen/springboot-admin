/**
 * @(#)ComparePileUpPayoffDataOptional.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import lombok.Data;

/**
 * Compare pile up payoff data optional.
 */
@Data
public class ComparePileUpPayoffDataOptional {

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Business date.
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
     * Payoff amount currency code.
     */
    private String payoffAmountCurrencyCode;

    /**
     * Payoff amount.
     */
    private BigDecimal payoffAmount;

    /**
     * Payoff quantity.
     */
    private BigDecimal payoffQuantity;

    /**
     * Payoff processing flag.
     */
    private boolean payoffProcessingFlag;

    /**
     * Batch region.
     */
    private Integer batchRegion;

    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System business code.
     */
    private String systemBusinessCode;

    /**
     * System Country code.
     */
    private String systemCountryCode;

    /**
     * Advance received store code.
     */
    private String advanceReceivedStoreCode;

    /**
     * Check store code.
     */
    private String checkStoreCode;
}
