/**
 * @(#)SalesPayoffDataUpdateService.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffdataupdate.service;

import com.fastretailing.dcp.sales.salespayoffdataupdate.form.SalesPayoffDataUpdateForm;
import com.fastretailing.dcp.sales.salespayoffdataupdate.form.SalesPayoffDataUpdateListForm;

/**
 * Sales payoff data update service.
 */
public interface SalesPayoffDataUpdateService {

    /**
     * Get sales payoff data initialize form.
     *
     * @param salesPayoffDataUpdateForm Form of payoff data.
     * @return Sales payoff data list form data.
     */
    SalesPayoffDataUpdateListForm getInitializeInformation(
            SalesPayoffDataUpdateForm salesPayoffDataUpdateForm);

    /**
     * Update sales payoff data.
     *
     * @param salesPayoffDataUpdateListForm Form of payoff data update list.
     * @return Sales payoff data list form data.
     */
    SalesPayoffDataUpdateListForm updatePayoffData(
            SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm);

    /**
     * Return back.
     *
     * @param salesPayoffDataUpdateListForm Form of payoff data update list.
     */

    void returnBack(SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm);
}
