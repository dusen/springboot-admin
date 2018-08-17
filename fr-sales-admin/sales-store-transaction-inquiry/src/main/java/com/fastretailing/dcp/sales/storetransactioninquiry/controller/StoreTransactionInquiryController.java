/**
 * @(#)StoreTransactionInquiryController.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fastretailing.dcp.sales.storetransactioninquiry.form.StoreTransactionInquiryForm;
import com.fastretailing.dcp.sales.storetransactioninquiry.service.StoreTransactionInquiryService;
import com.fastretailing.dcp.storecommon.screen.exception.ScreenException;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import com.fastretailing.dcp.storecommon.screen.form.FormBuilder;

/**
 * The controller class for store transaction inquiry page.
 */
@Controller
@RequestMapping(path = "/{brand}/{region}/screen/{store_code}/store-transaction-inquiry")
public class StoreTransactionInquiryController {

    /** Store transaction inquiry page. */
    private static final String STORE_TRANSACTION_INQUIRY_PAGE = "store-transaction-inquiry";

    /** Non merchandise item list page. */
    private static final String NON_MERCHANDISE_ITEM_LIST_PAGE = "non-merchandise-item-list";

    /** Payment tender group page. */
    private static final String PAYMENT_TENDER_GROUP_PAGE = "payment-tender-group";

    /** Payment tender group id. */
    private static final String PAYMENT_TENDER_GROUP_ID = "payment-tender-id";

    /** Store transaction inquiry child page. */
    private static final String STORE_TRANSACTION_INQUIRY_CHILD_PAGE =
            "store-transaction-inquiry-detail";

    /** Store transaction inquiry list form. */
    private static final String STORE_TRANSACTION_INQUIRY_FORM = "storeTransactionInquiryForm";

    /** Store transaction inquiry service. */
    @Autowired
    private StoreTransactionInquiryService storeTransactionInquiryService;

    /**
     * Display the store transaction inquiry initial page.
     *
     * @param brand Request parameter.
     * @param region Request parameter.
     * @param storeCode Request parameter.
     * @param commonBaseForm Form of common items.
     * @param model The model attribute for this page.
     * @return The name of initial page.
     */
    @RequestMapping(path = "/initialize")
    public String initialize(@PathVariable("brand") String brand,
            @PathVariable("region") String region, @PathVariable("store_code") String storeCode,
            CommonBaseForm commonBaseForm, Model model) {
        StoreTransactionInquiryForm storeTransactionInquiryForm =
                FormBuilder.build(StoreTransactionInquiryForm.class, commonBaseForm);
        // Get initialize information.
        storeTransactionInquiryForm.setStoreCode(storeCode);
        model.addAttribute(STORE_TRANSACTION_INQUIRY_FORM,
                storeTransactionInquiryService.displayInitPage(storeTransactionInquiryForm));
        return STORE_TRANSACTION_INQUIRY_PAGE;
    }

    /**
     * Create non merchandise item name list when brand or country is changed.
     *
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of non merchandise item name list page.
     */
    @RequestMapping(path = "/view-non-merchandise-item-list")
    public String viewNonMerchandiseItemList(
            StoreTransactionInquiryForm storeTransactionInquiryForm, BindingResult bindingResult,
            Model model) {
        StoreTransactionInquiryForm storeTransactionInquiryFormResult =
                new StoreTransactionInquiryForm();
        BeanUtils.copyProperties(storeTransactionInquiryForm, storeTransactionInquiryFormResult);
        storeTransactionInquiryFormResult.setNonMerchandiseItemList(storeTransactionInquiryService
                .changeBrand(storeTransactionInquiryFormResult, bindingResult, model));
        model.addAttribute(STORE_TRANSACTION_INQUIRY_FORM, storeTransactionInquiryFormResult);
        return NON_MERCHANDISE_ITEM_LIST_PAGE;
    }

    /**
     * Create payment tender group list when store code is changed.
     *
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of payment tender group page.
     */
    @RequestMapping(path = "/view-payment-tender-group-list")
    public String viewPaymentTenderGroupList(
            StoreTransactionInquiryForm storeTransactionInquiryForm, BindingResult bindingResult,
            Model model) {
        StoreTransactionInquiryForm storeTransactionInquiryFormResult =
                new StoreTransactionInquiryForm();
        BeanUtils.copyProperties(storeTransactionInquiryForm, storeTransactionInquiryFormResult);
        storeTransactionInquiryFormResult
                .setPaymentTenderGroupList(storeTransactionInquiryService.changeStoreForTenderGroup(
                        storeTransactionInquiryFormResult, bindingResult, model));
        model.addAttribute(STORE_TRANSACTION_INQUIRY_FORM, storeTransactionInquiryFormResult);
        return PAYMENT_TENDER_GROUP_PAGE;
    }

    /**
     * Create payment tender id list when store code is changed.
     *
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of payment tender group page.
     */
    @RequestMapping(path = "/view-payment-tender-id-list")
    public String viewPaymentTenderIdList(StoreTransactionInquiryForm storeTransactionInquiryForm,
            BindingResult bindingResult, Model model) {
        StoreTransactionInquiryForm storeTransactionInquiryFormResult =
                new StoreTransactionInquiryForm();
        BeanUtils.copyProperties(storeTransactionInquiryForm, storeTransactionInquiryFormResult);
        storeTransactionInquiryFormResult.setPaymentTenderIdList(storeTransactionInquiryService
                .changeStoreForTenderId(storeTransactionInquiryFormResult, bindingResult, model));
        model.addAttribute(STORE_TRANSACTION_INQUIRY_FORM, storeTransactionInquiryFormResult);
        return PAYMENT_TENDER_GROUP_ID;
    }

    /**
     * Display the previous store transaction inquiry list.
     *
     * @param storeTransactionInquiryForm Store transaction inquiryForm.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @RequestMapping(path = "/previous-list")
    public String displayPreviousStoreTransactionInquiryList(
            StoreTransactionInquiryForm storeTransactionInquiryForm, Model model) {
        StoreTransactionInquiryForm displayForm =
                storeTransactionInquiryService.getPreviousItemDetail(storeTransactionInquiryForm);
        storeTransactionInquiryForm.setShowDetail(true);
        model.addAttribute(STORE_TRANSACTION_INQUIRY_FORM, displayForm);
        return STORE_TRANSACTION_INQUIRY_PAGE;
    }

    /**
     * Display the next store transaction inquiry list.
     *
     * @param storeTransactionInquiryForm Store transaction inquiryForm.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @RequestMapping(path = "/next-list")
    public String displayNextStoreTransactionInquiryList(
            StoreTransactionInquiryForm storeTransactionInquiryForm, Model model) {
        StoreTransactionInquiryForm displayForm =
                storeTransactionInquiryService.getNextItemDetail(storeTransactionInquiryForm);
        storeTransactionInquiryForm.setShowDetail(true);
        model.addAttribute(STORE_TRANSACTION_INQUIRY_FORM, displayForm);
        return STORE_TRANSACTION_INQUIRY_PAGE;
    }

    /**
     * Display Store transaction inquiry list page.
     *
     * @param storeTransactionInquiryForm The form for this page.
     * @param bindingResult Validation error.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/list")
    public String displayStoretransactionInquiryListPage(
            @Validated StoreTransactionInquiryForm storeTransactionInquiryForm,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            throw new ScreenException(bindingResult, STORE_TRANSACTION_INQUIRY_FORM,
                    STORE_TRANSACTION_INQUIRY_PAGE, model.asMap());
        }
        // Get Store transaction inquiry list.
        storeTransactionInquiryForm = storeTransactionInquiryService
                .getStoreTransactionInquiryList(storeTransactionInquiryForm);
        DetailError detailError = storeTransactionInquiryForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, STORE_TRANSACTION_INQUIRY_FORM,
                    STORE_TRANSACTION_INQUIRY_PAGE, model.asMap());
        }
        storeTransactionInquiryForm.setShowDetail(true);
        model.addAttribute(STORE_TRANSACTION_INQUIRY_FORM, storeTransactionInquiryForm);
        return STORE_TRANSACTION_INQUIRY_CHILD_PAGE;
    }

}
