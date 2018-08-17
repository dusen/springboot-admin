/**
 * @(#)StoreTransactionInquiryService.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.service;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.fastretailing.dcp.sales.storetransactioninquiry.constant.DropDownItem;
import com.fastretailing.dcp.sales.storetransactioninquiry.form.StoreTransactionInquiryForm;

/**
 * Store transaction inquiry service.
 */
public interface StoreTransactionInquiryService {

    /**
     * Display initial page.
     *
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return Store transaction inquiry form data.
     */
    StoreTransactionInquiryForm displayInitPage(
            StoreTransactionInquiryForm storeTransactionInquiryForm);

    /**
     * Display non merchandise item list when brand is changed.
     *
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @param bindingResult Binding result.
     * @param model the model attribute for this page.
     * @return Drop down item list.
     */
    List<DropDownItem> changeBrand(StoreTransactionInquiryForm storeTransactionInquiryForm,
            BindingResult bindingResult, Model model);

    /**
     * Display payment tender group when store code is changed.
     *
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @param bindingResult Binding result.
     * @param model the model attribute for this page.
     * @return Drop down item list.
     */

    List<DropDownItem> changeStoreForTenderGroup(
            StoreTransactionInquiryForm storeTransactionInquiryForm, BindingResult bindingResult,
            Model model);

    /**
     * Display payment tender id when store code is changed.
     *
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @param bindingResult Binding result.
     * @param model the model attribute for this page.
     * @return Drop down item list.
     */

    List<DropDownItem> changeStoreForTenderId(
            StoreTransactionInquiryForm storeTransactionInquiryForm, BindingResult bindingResult,
            Model model);


    /**
     * Get store transaction inquiry list .
     *
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return Store transaction inquiry form data.
     */
    StoreTransactionInquiryForm getStoreTransactionInquiryList(
            StoreTransactionInquiryForm storeTransactionInquiryForm);

    /**
     * Get previous store transaction inquiry list .
     *
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return Store transaction inquiry form data.
     */
    StoreTransactionInquiryForm getPreviousItemDetail(
            StoreTransactionInquiryForm storeTransactionInquiryForm);

    /**
     * Get next store transaction inquiry list .
     *
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return Store transaction inquiry form data.
     */
    StoreTransactionInquiryForm getNextItemDetail(
            StoreTransactionInquiryForm storeTransactionInquiryForm);


}
