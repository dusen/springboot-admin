/**
 * @(#)SalesPayoffDataUpdateController.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffdataupdate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fastretailing.dcp.sales.salespayoffdataupdate.form.SalesPayoffDataUpdateForm;
import com.fastretailing.dcp.sales.salespayoffdataupdate.form.SalesPayoffDataUpdateListForm;
import com.fastretailing.dcp.sales.salespayoffdataupdate.service.SalesPayoffDataUpdateService;
import com.fastretailing.dcp.storecommon.screen.exception.ScreenException;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;


/**
 * The controller class for sales payoff data update page.
 */
@Controller
@RequestMapping(path = "/{brand}/{region}/screen/sales-settlement-unmatch-fix")
public class SalesPayoffDataUpdateController {

    /** Sales payoff data update page. */
    private static final String PAYOFF_DATA_UPDATE_PAGE = "sales-payoff-data-update";

    private static final String REDIRECT_FORMER_PAGE = "redirect:/unmatched-sales-list";

    /** Sales payoff data update form. */
    private static final String PAYOFF_DATA_UPDATE_PAGE_FORM = "salesPayoffDataUpdateListForm";

    /** Sales payoff data update service. */
    @Autowired
    private SalesPayoffDataUpdateService salesPayoffDataUpdateService;

    /**
     * Display the detail list page.
     *
     * @param salesPayoffDataUpdateForm Form of common items.
     * @param model The model attribute for this page.
     * @return The name of list page.
     */
    @RequestMapping(path = "/index")
    public String initialize(SalesPayoffDataUpdateForm salesPayoffDataUpdateForm, Model model) {

        SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm =
                salesPayoffDataUpdateService.getInitializeInformation(salesPayoffDataUpdateForm);

        salesPayoffDataUpdateListForm.setPayoffDate(salesPayoffDataUpdateForm.getPayoffDate());
        salesPayoffDataUpdateListForm.setBrandName(salesPayoffDataUpdateForm.getBrandName());
        salesPayoffDataUpdateListForm.setCountryName(salesPayoffDataUpdateForm.getCountryName());
        salesPayoffDataUpdateListForm.setStoreCode(salesPayoffDataUpdateForm.getStoreCode());
        salesPayoffDataUpdateListForm.setStoreName(salesPayoffDataUpdateForm.getStoreName());
        salesPayoffDataUpdateListForm
                .setCashRegisterNumber(salesPayoffDataUpdateForm.getCashRegisterNumber());
        salesPayoffDataUpdateListForm.setErrorContent(salesPayoffDataUpdateForm.getErrorContent());

        // Get initialize information.
        model.addAttribute(PAYOFF_DATA_UPDATE_PAGE_FORM, salesPayoffDataUpdateListForm);

        return PAYOFF_DATA_UPDATE_PAGE;
    }

    /**
     * Update sales payoff data.
     *
     * @param salesPayoffDataUpdateListForm Form of payoff data.
     * @param model The model attribute for this page.
     * @return The name of list page.
     */
    @RequestMapping(path = "/update")
    public String update(RedirectAttributes redirectAttributes,
            @Validated SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            throw new ScreenException(bindingResult, PAYOFF_DATA_UPDATE_PAGE_FORM,
                    PAYOFF_DATA_UPDATE_PAGE, model.asMap());
        }
        salesPayoffDataUpdateListForm =
                salesPayoffDataUpdateService.updatePayoffData(salesPayoffDataUpdateListForm);

        if (salesPayoffDataUpdateListForm.getDetailError() != null) {
            DetailError detailError = salesPayoffDataUpdateListForm.getDetailError();
            if (detailError != null) {
                throw new ScreenException(detailError, PAYOFF_DATA_UPDATE_PAGE_FORM,
                        PAYOFF_DATA_UPDATE_PAGE, model.asMap());
            }
        }

        redirectAttributes.addFlashAttribute(salesPayoffDataUpdateListForm);

        return REDIRECT_FORMER_PAGE;
    }

    /**
     * Return back.
     *
     * @param redirectAttributes Redirect attributes.
     * @param salesPayoffDataUpdateListForm Form of payoff data.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of return back page.
     */
    @RequestMapping(path = "/returnback")
    public String returnBack(RedirectAttributes redirectAttributes,
            @Validated SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm,
            BindingResult bindingResult, Model model) {

        salesPayoffDataUpdateService.returnBack(salesPayoffDataUpdateListForm);

        redirectAttributes.addFlashAttribute(salesPayoffDataUpdateListForm);

        return REDIRECT_FORMER_PAGE;
    }
}
