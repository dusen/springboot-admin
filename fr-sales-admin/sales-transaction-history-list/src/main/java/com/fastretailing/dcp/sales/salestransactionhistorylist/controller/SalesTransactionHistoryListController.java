/**
 * @(#)SalesTransactionHistoryListController.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionhistorylist.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fastretailing.dcp.sales.salestransactionhistorylist.form.SalesTransactionHistoryListForm;
import com.fastretailing.dcp.sales.salestransactionhistorylist.service.SalesTransactionHistoryListService;
import com.fastretailing.dcp.storecommon.screen.exception.ScreenException;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import com.fastretailing.dcp.storecommon.screen.form.FormBuilder;
import com.fastretailing.dcp.storecommon.screen.message.MessageType;

/**
 * The controller class for sales transaction history list page.
 */
@Controller
@RequestMapping(path = "/{brand}/{region}/screen/sales-transaction-history-list")
public class SalesTransactionHistoryListController {

    /** Sales transaction history list page. */
    private static final String HISTORY_LIST_PAGE = "sales-transaction-history-list";

    /** Sales transaction history ajax page. */
    private static final String HISTORY_LIST_CHILD_PAGE = "sales-transaction-history-list-ajaxData";

    /** Sales transaction history list form. */
    private static final String HISTORY_LIST_PAGE_FORM = "salesTransactionHistoryListForm";

    /** Sales transaction history list service. */
    @Autowired
    private SalesTransactionHistoryListService salesTransactionHistoryListService;

    /**
     * Display the detail list page.
     *
     * @param commonBaseForm Form of common items.
     * @param model The model attribute for this page.
     * @return The name of list page.
     */
    @RequestMapping(path = "")
    public String initialize(CommonBaseForm commonBaseForm, Model model) {

        SalesTransactionHistoryListForm salesTransactionHistoryListForm =
                FormBuilder.build(SalesTransactionHistoryListForm.class, commonBaseForm);

        // Get initialize information.
        model.addAttribute(HISTORY_LIST_PAGE_FORM, salesTransactionHistoryListService
                .getInitializeInformation(salesTransactionHistoryListForm));

        return HISTORY_LIST_PAGE;
    }

    /**
     * Display sales transaction history list page.
     *
     * @param salesTransactionHistoryListForm The form for this page.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/list")
    public String displaySalesTransactionHistoryListPage(
            @Validated SalesTransactionHistoryListForm salesTransactionHistoryListForm,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            DetailError detailError = new DetailError();
            StringBuilder errorMessage = new StringBuilder();
            fieldErrorList.stream()
                    .forEach(fieldError -> errorMessage.append(fieldError.getDefaultMessage()));
            detailError.setErrorMessage(errorMessage.toString());
            detailError.setMessageType(MessageType.ERROR.getType());

            throw new ScreenException(detailError, HISTORY_LIST_PAGE_FORM, HISTORY_LIST_PAGE,
                    model.asMap());
        }

        // Get sales transaction history list.
        salesTransactionHistoryListForm = salesTransactionHistoryListService
                .getSalesTransactionHistoryList(salesTransactionHistoryListForm);

        DetailError detailError = salesTransactionHistoryListForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, HISTORY_LIST_PAGE_FORM, HISTORY_LIST_PAGE,
                    model.asMap());
        }

        model.addAttribute(HISTORY_LIST_PAGE_FORM, salesTransactionHistoryListForm);
        return HISTORY_LIST_CHILD_PAGE;
    }

    /**
     * Display sales transaction history list page by sorted.
     *
     * @param salesTransactionHistoryListForm The form for this page.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/sort")
    public String displaySalesTransactionHistoryListPageBySort(
            SalesTransactionHistoryListForm salesTransactionHistoryListForm, Model model) {

        model.addAttribute(HISTORY_LIST_PAGE_FORM, salesTransactionHistoryListService
                .getSortedSalesTransactionHistoryList(salesTransactionHistoryListForm));

        return HISTORY_LIST_CHILD_PAGE;
    }

}
