/**
 * @(#)SalesTransactionCorrectionHistoryDetailController.java
 *
 *                                                            Copyright (c) 2018 Fast Retailing
 *                                                            Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.common.TransactionErrorDetailCheckFlag;
import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.SalesTransactionCorrectionHistoryDetailForm;
import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.SalesTransactionHistory;
import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.SalesTransactionHistoryListForm;
import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.service.SalesTransactionCorrectionHistoryDetailService;
import com.fastretailing.dcp.storecommon.screen.form.FormBuilder;

/**
 * The controller class for sales transaction correction history detail page.
 */
@Controller
@RequestMapping(path = "/{brand}/{region}/screen/sales-transaction-correction-history-detail")
public class SalesTransactionCorrectionHistoryDetailController {

    /** Sales transaction correction history detail page. */
    private static final String CORRECTION_PAGE = "sales-transaction-correction-history-detail";

    /** Sales transaction correction history detail form. */
    private static final String CORRECTION_PAGE_FORM =
            "salesTransactionCorrectionHistoryDetailForm";

    /** Model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /** Sales transaction correction history detail service. */
    @Autowired
    private SalesTransactionCorrectionHistoryDetailService salesTransactionCorrectionHistoryDetailService;

    /**
     * Display the correction page.
     *
     * @param salesTransactionHistoryListForm Form of common items.
     * @param model The model attribute for this page.
     * @return The name of detail page.
     */
    @PostMapping(path = "")
    public String initialize(SalesTransactionHistoryListForm salesTransactionHistoryListForm,
            Model model) {

        SalesTransactionCorrectionHistoryDetailForm salesTransactionCorrectionHistoryDetailForm =
                FormBuilder.build(SalesTransactionCorrectionHistoryDetailForm.class,
                        salesTransactionHistoryListForm);
        salesTransactionHistoryListForm.getSalesTransactionHistoryList().removeIf(
                history -> TransactionErrorDetailCheckFlag.UNCHECKED.is(history.getCheckFlag()));
        SalesTransactionHistory salesTransactionHistory =
                salesTransactionHistoryListForm.getSalesTransactionHistoryList().get(0);
        modelMapper.map(salesTransactionHistoryListForm.getSalesTransactionHistoryList().get(0),
                salesTransactionCorrectionHistoryDetailForm);
        salesTransactionCorrectionHistoryDetailForm
                .setSystemBrandCode(salesTransactionHistory.getBrandCode());
        salesTransactionCorrectionHistoryDetailForm
                .setSystemCountryCode(salesTransactionHistory.getCountryCode());
        // Get initialize information.
        model.addAttribute(CORRECTION_PAGE_FORM, salesTransactionCorrectionHistoryDetailService
                .getSalesTransactionHeaderInformation(salesTransactionCorrectionHistoryDetailForm));

        return CORRECTION_PAGE;
    }

    /**
     * Display after correction sales transaction correction page.
     * 
     * @param salesTransactionCorrectionHistoryDetailForm The form for this page.
     * @param model The model attribute for this page.
     * @return The name of after correction detail page.
     */
    @PostMapping(path = "/afterCorrection")
    public String afterCorrection(
            SalesTransactionCorrectionHistoryDetailForm salesTransactionCorrectionHistoryDetailForm,
            Model model) {

        // Get initialize information.
        model.addAttribute(CORRECTION_PAGE_FORM,
                salesTransactionCorrectionHistoryDetailService
                        .getAfterCorrectionSalesTransactionHeaderInformation(
                                salesTransactionCorrectionHistoryDetailForm));

        return CORRECTION_PAGE;

    }

}
