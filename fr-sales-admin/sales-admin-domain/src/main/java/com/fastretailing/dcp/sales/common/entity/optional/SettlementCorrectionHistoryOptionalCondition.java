/**
 * @(#)SalesSettlementCorrectionHistoryOptionalCondition.java
 *
 *                                                            Copyright (c) 2018 Fast Retailing
 *                                                            Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Sales settlement correction history condition.
 */
@Data
public class SettlementCorrectionHistoryOptionalCondition {

    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System country code.
     */
    private String systemCountryCode;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Payoff date from.
     */
    private LocalDateTime payoffDateFrom;

    /**
     * Payoff date to.
     */
    private LocalDateTime payoffDateTo;

    /**
     * Cash register no.
     */
    private Integer cashRegisterNo;

    /**
     * Corrector.
     */
    private String corrector;

    /**
     * Correction date from.
     */
    private LocalDateTime correctionDateFrom;

    /**
     * Correction date to.
     */
    private LocalDateTime correctionDateTo;

}
