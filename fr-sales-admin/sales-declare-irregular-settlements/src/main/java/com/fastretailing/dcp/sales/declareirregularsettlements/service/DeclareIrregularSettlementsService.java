/**
 * @(#)DeclareIrregularSettlementsService.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.service;

import com.fastretailing.dcp.sales.declareirregularsettlements.form.DeclareIrregularSettlementsForm;

/**
 * Declare irregular settlements service.
 */
public interface DeclareIrregularSettlementsService {

    /**
     * Get declare irregular settlements form for initialize.
     *
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @return Declare irregular settlements form.
     */
    DeclareIrregularSettlementsForm initialize(
            DeclareIrregularSettlementsForm declareIrregularSettlementsForm);

    /**
     * Search declare irregular settlements form.
     *
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @return Declare irregular settlements form.
     */
    DeclareIrregularSettlementsForm search(
            DeclareIrregularSettlementsForm declareIrregularSettlementsForm);

    /**
     * Calculation declare irregular settlements form data.
     *
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     */
    public void calculation(DeclareIrregularSettlementsForm declareIrregularSettlementsForm);

    /**
     * Set base information.
     *
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     */
    public void setBaseInformation(DeclareIrregularSettlementsForm declareIrregularSettlementsForm);

    /**
     * Confirm declare irregular settlements form data.
     *
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     */
    public void confirm(DeclareIrregularSettlementsForm declareIrregularSettlementsForm);
}
