/**
 * @(#)SalesPayoffUnmatchListService.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffunmatchlist.service;

import com.fastretailing.dcp.sales.common.entity.AlterationExclusionControl;
import com.fastretailing.dcp.sales.salespayoffunmatchlist.form.SalesPayoffUnmatchListForm;

/**
 * Sales payoff unmatch list service.
 */
public interface SalesPayoffUnmatchListService {

    /**
     * Get initialize information.
     * 
     * @param salesPayoffUnmatchListFormParam Get sales transaction history list form.
     *
     * @return Sales payoff unmatch list form data.
     */
    SalesPayoffUnmatchListForm getInitializeInformation(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm);

    /**
     * Get sales payoff unmatch list.
     *
     * @param salesPayoffUnmatchListForm Sales payoff unmatch list form.
     * @return Sales payoff unmatch list form data.
     */
    SalesPayoffUnmatchListForm getSalesPayoffUnmatchList(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm);

    /**
     * Get sorted sales transaction history list.
     *
     * @param salesPayoffUnmatchListForm Sales payoff unmatch list form.
     * @return Sales payoff unmatch list form data.
     */
    SalesPayoffUnmatchListForm pressAudit(SalesPayoffUnmatchListForm salesPayoffUnmatchListForm);

    /**
     * Check alteration exclusion control.
     *
     * @param salesPayoffUnmatchListForm Sales payoff unmatch list form.
     * @return Alteration exclusion control data.
     */
    AlterationExclusionControl checkAlterationExclusionControl(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm);

    /**
     * Insert alteration exclusion control.
     *
     * @param salesPayoffUnmatchListForm Sales payoff unmatch list form.
     */
    void insertAlterationExclusionControl(SalesPayoffUnmatchListForm salesPayoffUnmatchListForm);

    /**
     * Update alteration exclusion control.
     *
     * @param salesPayoffUnmatchListForm Sales payoff unmatch list form.
     */
    void updateAlterationExclusionControl(SalesPayoffUnmatchListForm salesPayoffUnmatchListForm);
}
