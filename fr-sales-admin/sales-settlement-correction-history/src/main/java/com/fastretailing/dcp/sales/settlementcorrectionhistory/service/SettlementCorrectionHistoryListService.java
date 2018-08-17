/**
 * @(#)SettlementCorrectionHistoryListService.java
 *
 *                                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.settlementcorrectionhistory.service;

import com.fastretailing.dcp.sales.settlementcorrectionhistory.form.SettlementCorrectionHistoryListForm;

/**
 * Settlement correction history list service.
 */
public interface SettlementCorrectionHistoryListService {

    /**
     * Get initialize information.
     * 
     * @param settlementCorrectionHistoryListForm Settlement correction history list form.
     * @return Settlement correction history list form.
     */
    SettlementCorrectionHistoryListForm getInitializeInformation(
            SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm);

    /**
     * Get settlement correction history list.
     * 
     * @param settlementCorrectionHistoryListForm Settlement correction history list form.
     * @return Settlement correction history list form.
     */
    SettlementCorrectionHistoryListForm getSettlementCorrectionHistoryList(
            SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm);

    /**
     * Get sorted settlement correction history list.
     * 
     * @param settlementCorrectionHistoryListForm Settlement correction history list form.
     * @return Settlement correction history list form.
     */
    SettlementCorrectionHistoryListForm getSortedSettlementCorrectionHistoryList(
            SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm);

}
