/**
 * @(#)PileUpPayOffDataOptional.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Pile up pay off data optional.
 */
@Data
public class PileUpPayOffDataOptional {

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
     * Advance received store code.
     */
    private String advanceReceivedStoreCode;

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
     * System country code.
     */
    private String systemCountryCode;

    /**
     * Create user id.
     */
    private String createUserId;

    /**
     * Create date time.
     */
    private OffsetDateTime createDatetime;

    /**
     * Create program id.
     */
    private String createProgramId;

    /**
     * Update user id.
     */
    private String updateUserId;

    /**
     * Update date time.
     */
    private OffsetDateTime updateDatetime;

    /**
     * Update program id.
     */
    private String updateProgramId;
}
