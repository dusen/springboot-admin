/**
 * @(#)SettlementCorrectionHistoryListController.java
 *
 *                                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.settlementcorrectionhistory.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fastretailing.dcp.sales.settlementcorrectionhistory.form.SettlementCorrectionHistoryListForm;
import com.fastretailing.dcp.sales.settlementcorrectionhistory.service.SettlementCorrectionHistoryListService;
import com.fastretailing.dcp.storecommon.screen.exception.ScreenException;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import com.fastretailing.dcp.storecommon.screen.form.FormBuilder;
import com.fastretailing.dcp.storecommon.screen.message.MessageType;

/**
 * The controller class for settlement correction history list page.
 */
@Controller
@RequestMapping(path = "/{brand}/{region}/screen/sales-settlement-correction-history")
public class SettlementCorrectionHistoryListController {

    /** Settlement correction history list page. */
    private static final String SETTLEMENT_CORRECTION_HISTORY_LIST_PAGE =
            "sales-settlement-correction-history";

    /** Settlement correction history ajax page. */
    private static final String SETTLTMENT_CORRECTION_HISTORY_LIST_CHILD_PAGE =
            "sales-settlement-correction-history-ajaxData";

    /** Settlement correction history list form. */
    private static final String SETTLTMENT_CORRECTION_HISTORY_LIST_PAGE_FORM =
            "settlementCorrectionHistoryListForm";

    /** Settlement correction history list service. */
    @Autowired
    private SettlementCorrectionHistoryListService settlementCorrectionHistoryListService;

    /**
     * Display the initialized settlement correction history list page.
     *
     * @param brand Request parameter.
     * @param region Request parameter.
     * @param commonBaseForm Form of common items.
     * @param model The model attribute for this page.
     * @return The name of list page.
     */
    @RequestMapping(path = "")
    public String initialize(@PathVariable("brand") String brand,
            @PathVariable("region") String region, CommonBaseForm commonBaseForm, Model model) {

        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                FormBuilder.build(SettlementCorrectionHistoryListForm.class, commonBaseForm);

        // Get initialize information.
        model.addAttribute(SETTLTMENT_CORRECTION_HISTORY_LIST_PAGE_FORM,
                settlementCorrectionHistoryListService
                        .getInitializeInformation(settlementCorrectionHistoryListForm));

        return SETTLEMENT_CORRECTION_HISTORY_LIST_PAGE;
    }

    /**
     * Display settlement correction history list page.
     *
     * @param settlementCorrectionHistoryListForm The form for this page.
     * @param bindingResult The binging result for this page.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/list")
    public String displaySettlementCorrectionHistoryPage(
            @Validated SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            DetailError detailError = new DetailError();
            StringBuilder errorMessage = new StringBuilder();
            fieldErrorList.stream().forEach(fieldError -> {
                errorMessage.append(fieldError.getDefaultMessage());
                errorMessage.append("<br>");
            });
            detailError.setErrorMessage(errorMessage.toString());
            detailError.setMessageType(MessageType.ERROR.getType());

            throw new ScreenException(bindingResult, SETTLTMENT_CORRECTION_HISTORY_LIST_PAGE_FORM,
                    SETTLEMENT_CORRECTION_HISTORY_LIST_PAGE, model.asMap());
        }

        // Get settlement correction history list.
        settlementCorrectionHistoryListForm = settlementCorrectionHistoryListService
                .getSettlementCorrectionHistoryList(settlementCorrectionHistoryListForm);

        DetailError detailError = settlementCorrectionHistoryListForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, SETTLTMENT_CORRECTION_HISTORY_LIST_PAGE_FORM,
                    SETTLEMENT_CORRECTION_HISTORY_LIST_PAGE, model.asMap());
        }

        model.addAttribute(SETTLTMENT_CORRECTION_HISTORY_LIST_PAGE_FORM,
                settlementCorrectionHistoryListForm);

        return SETTLTMENT_CORRECTION_HISTORY_LIST_CHILD_PAGE;
    }

    /**
     * Display settlement correction history list page by sorted.
     *
     * @param settlementCorrectionHistoryListForm The form for this page.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/sort")
    public String displaySettlementCorrectionHistoryPageBySort(
            SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm, Model model) {

        model.addAttribute(SETTLTMENT_CORRECTION_HISTORY_LIST_PAGE_FORM,
                settlementCorrectionHistoryListService.getSortedSettlementCorrectionHistoryList(
                        settlementCorrectionHistoryListForm));

        return SETTLTMENT_CORRECTION_HISTORY_LIST_CHILD_PAGE;
    }

}
