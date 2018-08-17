/**
 * @(#)SalesTransactionCorrectionController.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.controller;

import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fastretailing.dcp.sales.salestransactioncorrection.common.TransactionErrorDetailCheckFlag;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.SalesTransactionCorrectionForm;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.errorlist.SalesTransactionError;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.errorlist.SalesTransactionErrorListForm;
import com.fastretailing.dcp.sales.salestransactioncorrection.service.SalesTransactionCorrectionService;
import com.fastretailing.dcp.storecommon.screen.form.FormBuilder;

/**
 * The controller class for sales transaction correction page.
 */
@Controller
@RequestMapping(path = "/{brand}/{region}/screen/sales-transaction-correction")
public class SalesTransactionCorrectionController {

    /** Sales transaction correction page. */
    private static final String CORRECTION_PAGE = "sales-transaction-correction";

    /** Sales transaction correction form. */
    private static final String CORRECTION_PAGE_FORM = "salesTransactionCorrectionForm";

    /** Ajax success. */
    private static final String AJAX_SUCCESS = "AJAX_SUCCESS";

    /** Model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /** Sales transaction correction service. */
    @Autowired
    private SalesTransactionCorrectionService salesTransactionCorrectionService;

    /**
     * Display the correction page.
     *
     * @param salesTransactionErrorListForm Form of common items.
     * @param model The model attribute for this page.
     * @return The name of detail page.
     */
    @PostMapping(path = "")
    public String initialize(SalesTransactionErrorListForm salesTransactionErrorListForm,
            Model model) {
        SalesTransactionError salesTransactionError = salesTransactionErrorListForm
                .getSalesTransactionErrorList()
                .stream()
                .filter(error -> TransactionErrorDetailCheckFlag.CHECKED.is(error.getCheckFlag()))
                .collect(Collectors.toList())
                .get(0);
        SalesTransactionCorrectionForm salesTransactionCorrectionForm = FormBuilder
                .build(SalesTransactionCorrectionForm.class, salesTransactionErrorListForm);

        modelMapper.map(salesTransactionError, salesTransactionCorrectionForm);
        salesTransactionCorrectionForm.setSystemBrandCode(salesTransactionError.getBrandCode());
        salesTransactionCorrectionForm.setSystemCountryCode(salesTransactionError.getCountryCode());

        // Get initialize information.
        model.addAttribute(CORRECTION_PAGE_FORM, salesTransactionCorrectionService
                .getSalesTransactionHeaderInformation(salesTransactionCorrectionForm));
        return CORRECTION_PAGE;
    }

    /**
     * Upload.
     * 
     * @param salesTransactionCorrectionForm Sales transaction correction form.
     * @return Ajax success.
     */
    @PostMapping(path = "/upload")
    @ResponseBody
    public String upload(@Validated SalesTransactionCorrectionForm salesTransactionCorrectionForm) {

        salesTransactionCorrectionService.upload(salesTransactionCorrectionForm);
        return AJAX_SUCCESS;
    }

    /**
     * Audit.
     * 
     * @param salesTransactionCorrectionForm Sales transaction correction form.
     * @return Ajax success.
     */
    @PostMapping(path = "/audit")
    @ResponseBody
    public String audit(@Validated SalesTransactionCorrectionForm salesTransactionCorrectionForm) {
        salesTransactionCorrectionService.audit(salesTransactionCorrectionForm);
        return AJAX_SUCCESS;
    }

    /**
     * Delete.
     *
     * @param salesTransactionCorrectionForm Sales transaction correction form.
     * @return Ajax success.
     */
    @PostMapping(path = "/delete")
    @ResponseBody
    public String delete(@Validated SalesTransactionCorrectionForm salesTransactionCorrectionForm) {

        salesTransactionCorrectionService.delete(salesTransactionCorrectionForm);
        return AJAX_SUCCESS;
    }

    /**
     * Back to previous page.
     * 
     * @param salesTransactionCorrectionForm The form for this page.
     * @return Ajax success.
     */
    @PostMapping(path = "/back")
    @ResponseBody
    public String taxLink(
            @Validated SalesTransactionCorrectionForm salesTransactionCorrectionForm) {
        salesTransactionCorrectionService.back(salesTransactionCorrectionForm);
        return AJAX_SUCCESS;
    }

}
