/**
 * @(#)SettlementCorrectionHistory.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.settlementcorrectionhistory.form;

import lombok.Data;

/**
 * Settlement correction history.
 */
@Data
public class SettlementCorrectionHistory {

    /**
     * Brand code.
     */
    private String brandCode;

    /**
     * Country code.
     */
    private String countryCode;

    /**
     * View store code.
     */
    private String viewStoreCode;

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
    private String cashRegisterNo;

    /**
     * Payoff type code.
     */
    private String payoffTypeCode;

    /**
     * Payoff type code name.
     */
    private String payoffTypeCodeName;

    /**
     * Payoff type sub number.
     */
    private String payoffTypeSubNumber;

    /**
     * Payoff type sub number name.
     */
    private String payoffTypeSubNumberName;

    /**
     * Payoff amount before.
     */
    private String payoffAmountBefore;

    /**
     * Payoff amount after.
     */
    private String payoffAmountAfter;

    /**
     * Amount different or not flag.
     */
    private String amountDiffFlag;

    /**
     * Payoff quantity before.
     */
    private String payoffQuantityBefore;

    /**
     * Payoff quantity after.
     */
    private String payoffQuantityAfter;

    /**
     * Quantity different or not flag.
     */
    private String quantityDiffFlag;

    /**
     * Corrector.
     */
    private String corrector;

    /**
     * Correction date.
     */
    private String correctionDate;

}
