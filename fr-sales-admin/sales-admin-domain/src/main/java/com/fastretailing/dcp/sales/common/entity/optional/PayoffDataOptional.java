/**
 * @(#)PayoffDataOptional.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Pay off data optional.
 */
@Data
public class PayoffDataOptional {

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
     * Report output count.
     */
    private Integer reportOutputCount;

    /**
     * Processing flag.
     */
    private boolean processingFlag;

    /**
     * Integrity check type.
     */
    private String integrityCheckType;

    /**
     * Accounting record status.
     */
    private Integer accountingRecordStatus;

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
     * Regular time error notification flag.
     */
    private boolean regularTimeErrorNotificationFlag;

    /**
     * Daily summary error notification flag.
     */
    private boolean dailySummaryErrorNotificationFlag;

    /**
     * Data alteration status.
     */
    private String dataAlterationStatus;

    /**
     * Close complete flag.
     */
    private boolean closeCompleteFlag;

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
