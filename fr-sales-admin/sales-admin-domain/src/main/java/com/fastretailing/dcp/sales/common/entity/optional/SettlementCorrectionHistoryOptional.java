/**
 * @(#)SettlementCorrectionHistoryOptional.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.ToString;

/**
 * Settlement correction history.
 */
@Data
@ToString(callSuper = true)
public class SettlementCorrectionHistoryOptional {

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
     * Store code.
     */
    private String storeCode;

    /**
     * Store name.
     */
    private String storeName;

    /**
     * Payoff date.
     */
    private String payoffDate;

    /**
     * Cash register no.
     */
    private BigDecimal cashRegisterNo;

    /**
     * Payoff type code.
     */
    private String payoffTypeCode;

    /**
     * Payoff type name.
     */
    private String payoffTypeName;

    /**
     * Payoff type sub number code.
     */
    private String payoffTypeSubNumberCode;

    /**
     * Payoff type sub number name.
     */
    private String payoffTypeSubNumberName;

    /**
     * Payoff amount before.
     */
    private BigDecimal payoffAmountBefore;

    /**
     * Payoff amount after.
     */
    private BigDecimal payoffAmountAfter;

    /**
     * Payoff quantity before.
     */
    private BigDecimal payoffQuantityBefore;

    /**
     * Payoff quantity after.
     */
    private BigDecimal payoffQuantityAfter;

    /**
     * Update user id.
     */
    private String updateUserId;

    /**
     * Update date time.
     */
    private OffsetDateTime updateDateTime;

}
