/**
 * @(#)SalesPayoffUnmatchListController.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffunmatchlist.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fastretailing.dcp.common.util.SystemDateTime;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.dto.SalesPayoffSharedData;
import com.fastretailing.dcp.sales.common.entity.AlterationExclusionControl;
import com.fastretailing.dcp.sales.salespayoffunmatchlist.form.SalesPayoffUnmatch;
import com.fastretailing.dcp.sales.salespayoffunmatchlist.form.SalesPayoffUnmatchListForm;
import com.fastretailing.dcp.sales.salespayoffunmatchlist.service.SalesPayoffUnmatchListService;
import com.fastretailing.dcp.storecommon.screen.exception.ScreenException;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import com.fastretailing.dcp.storecommon.screen.form.FormBuilder;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.message.MessageType;

/**
 * The controller class for sales transaction history list page.
 */
@Controller
@RequestMapping(path = "/{brand}/{region}/screen/unmatched-sales-list")
public class SalesPayoffUnmatchListController {

    /** Sales payoff unmatch list page. */
    private static final String HISTORY_LIST_PAGE = "unmatched-sales-list";

    /** Sales payoff unmatch ajax page. */
    private static final String HISTORY_LIST_CHILD_PAGE = "unmatched-sales-list-ajaxData";

    /** Sales payoff unmatch list form. */
    private static final String HISTORY_LIST_PAGE_FORM = "salesPayoffUnmatchListForm";

    /** sales-settlement-unmatch-fix page. */
    private static final String SALES_SETTLEMENT_UNMATCH_FIX = "sales-settlement-unmatch-fix";

    /** Redirect. */
    private static final String REDIRECT = "redirect:/";

    /** Sales payoff unmatch list service. */
    @Autowired
    private SalesPayoffUnmatchListService salesPayoffUnmatchListService;

    /** Component for getting message by locale. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** UTC clock component. */
    @Autowired
    private SystemDateTime systemDateTime;

    /**
     * Display the detail list page.
     *
     * @param commonBaseForm Form of common items.
     * @param model The model attribute for this page.
     * @param salesPayoffSharedData Sales payoff shared data.
     * @return The name of list page.
     */
    @RequestMapping(path = "")
    public String initialize(CommonBaseForm commonBaseForm, Model model,
            @ModelAttribute SalesPayoffSharedData salesPayoffSharedData) {

        SalesPayoffUnmatchListForm salesPayoffUnmatchListForm =
                FormBuilder.build(SalesPayoffUnmatchListForm.class, commonBaseForm);

        // Get initialize information.
        model.addAttribute(HISTORY_LIST_PAGE_FORM,
                salesPayoffUnmatchListService.getInitializeInformation(salesPayoffUnmatchListForm));

        if (salesPayoffSharedData != null) {
            transferSharedDataToForm(salesPayoffUnmatchListForm, salesPayoffSharedData);

            // Get sales payoff unmatch list.
            salesPayoffUnmatchListForm = salesPayoffUnmatchListService
                    .getSalesPayoffUnmatchList(salesPayoffUnmatchListForm);
        }

        return HISTORY_LIST_PAGE;
    }

    /**
     * Display sales payoff unmatch list page.
     *
     * @param salesPayoffUnmatchListForm The form for this page.
     * @param bindingResult Validation error.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/list")
    public String displaySalesPayoffUnmatchListPage(
            @Validated SalesPayoffUnmatchListForm salesPayoffUnmatchListForm,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();
            fieldErrorList.stream().forEach(fieldError -> {
                errorMessage.append(fieldError.getDefaultMessage());
                errorMessage.append("<br>");
            });
            throwScreenException(errorMessage.toString(), null, MessageType.ERROR.getType(), model);
        }


        if (!salesPayoffUnmatchListForm.getCashRegisterNo().isEmpty()
                && salesPayoffUnmatchListForm.getViewStoreCode().isEmpty()) {
            throwScreenException(localeMessageSource.getMessage(MessagePrefix.E_SLS_66000148),
                    MessagePrefix.E_SLS_66000148, MessageType.WARNING.getType(), model);
        }

        // Get sales payoff unmatch list.
        salesPayoffUnmatchListForm =
                salesPayoffUnmatchListService.getSalesPayoffUnmatchList(salesPayoffUnmatchListForm);

        DetailError detailError = salesPayoffUnmatchListForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, HISTORY_LIST_PAGE_FORM, HISTORY_LIST_PAGE,
                    model.asMap());
        }

        model.addAttribute(HISTORY_LIST_PAGE_FORM, salesPayoffUnmatchListForm);
        return HISTORY_LIST_CHILD_PAGE;
    }

    /**
     * Press audit button.
     *
     * @param salesPayoffUnmatchListForm The form for this page.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/audit")
    public String callSalesPayoffIntegrityCheck(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();
            fieldErrorList.stream().forEach(fieldError -> {
                errorMessage.append(fieldError.getDefaultMessage());
                errorMessage.append("<br>");
            });
            throwScreenException(errorMessage.toString(), null, MessageType.ERROR.getType(), model);
        }

        if (!salesPayoffUnmatchListForm.getCashRegisterNo().isEmpty()
                && salesPayoffUnmatchListForm.getViewStoreCode().isEmpty()) {
            throwScreenException(localeMessageSource.getMessage(MessagePrefix.E_SLS_66000148),
                    MessagePrefix.E_SLS_66000148, MessageType.WARNING.getType(), model);
        }

        salesPayoffUnmatchListForm =
                salesPayoffUnmatchListService.pressAudit(salesPayoffUnmatchListForm);

        DetailError detailError = salesPayoffUnmatchListForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, HISTORY_LIST_PAGE_FORM, HISTORY_LIST_PAGE,
                    model.asMap());
        }

        model.addAttribute(HISTORY_LIST_PAGE_FORM, salesPayoffUnmatchListForm);
        return HISTORY_LIST_CHILD_PAGE;
    }

    /**
     * Display sales unmatch list page by sorted.
     *
     * @param redirectAttributes Redirect attributes.
     * @param commonBaseForm Common base form.
     * @param salesPayoffUnmatchListForm The form for this page.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/presslink")
    public String transitSalesSettlementUnmatchFix(RedirectAttributes redirectAttributes,
            CommonBaseForm commonBaseForm, SalesPayoffUnmatchListForm salesPayoffUnmatchListForm,
            Model model) {

        salesPayoffUnmatchListForm.setLoginUserId(commonBaseForm.getLoginUserId());

        AlterationExclusionControl alterationExclusionControl = salesPayoffUnmatchListService
                .checkAlterationExclusionControl(salesPayoffUnmatchListForm);

        if (Objects.isNull(alterationExclusionControl)) {
            salesPayoffUnmatchListService
                    .insertAlterationExclusionControl(salesPayoffUnmatchListForm);
            redirectAttributes.addFlashAttribute("salesPayoffSharedData",
                    setSharedDataByScreenData(salesPayoffUnmatchListForm));
            return REDIRECT + SALES_SETTLEMENT_UNMATCH_FIX;
        }

        if (salesPayoffUnmatchListForm.getLoginUserId()
                .equals(alterationExclusionControl.getCreateUserId())) {
            salesPayoffUnmatchListService
                    .updateAlterationExclusionControl(salesPayoffUnmatchListForm);
            redirectAttributes.addFlashAttribute("salesPayoffSharedData",
                    setSharedDataByScreenData(salesPayoffUnmatchListForm));
            return REDIRECT + SALES_SETTLEMENT_UNMATCH_FIX;
        } else {
            // Get now date time.
            OffsetDateTime nowDateTime = systemDateTime.now();
            // Get update date time.
            OffsetDateTime updateDatetime = alterationExclusionControl.getUpdateDatetime();
            // Compare.
            if (nowDateTime.compareTo(updateDatetime.plusMinutes(
                    Long.valueOf(alterationExclusionControl.getExclusionValidTime()))) < 0) {
                throwScreenException(localeMessageSource.getMessage(MessagePrefix.E_SLS_66000101),
                        MessagePrefix.E_SLS_66000101, MessageType.WARNING.getType(), model);
            }
        }

        salesPayoffUnmatchListService.updateAlterationExclusionControl(salesPayoffUnmatchListForm);
        redirectAttributes.addFlashAttribute("salesPayoffSharedData",
                setSharedDataByScreenData(salesPayoffUnmatchListForm));
        return REDIRECT + SALES_SETTLEMENT_UNMATCH_FIX;
    }


    /**
     * Transfer shared data to form.
     * 
     * @param salesPayoffUnmatchListForm Sales payoff unmatch list screen data.
     * @param salesPayoffSharedData Shared data to import.
     * @return Data imported sales payoff unmatch list screen data.
     */
    private SalesPayoffUnmatchListForm transferSharedDataToForm(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm,
            SalesPayoffSharedData salesPayoffSharedData) {

        salesPayoffUnmatchListForm
                .setSystemBrandCode(salesPayoffSharedData.getSystemBrandCodeCondition());
        salesPayoffUnmatchListForm
                .setSystemBrandName(salesPayoffSharedData.getSystemBrandNameCondition());
        salesPayoffUnmatchListForm
                .setSystemCountryCode(salesPayoffSharedData.getSystemCountryCodeCondition());
        salesPayoffUnmatchListForm
                .setSystemCountryName(salesPayoffSharedData.getSystemCountryNameCondition());
        salesPayoffUnmatchListForm.setStoreCode(salesPayoffSharedData.getStoreCodeCondition());
        salesPayoffUnmatchListForm.setPayoffDateFrom(salesPayoffSharedData.getPayoffDateFrom());
        salesPayoffUnmatchListForm.setPayoffDateTo(salesPayoffSharedData.getPayoffDateTo());
        salesPayoffUnmatchListForm
                .setCashRegisterNo(salesPayoffSharedData.getCashRegisterNoCondition());
        salesPayoffUnmatchListForm
                .setErrorContentsCode(salesPayoffSharedData.getErrorContentCodeCondition());
        salesPayoffUnmatchListForm
                .setErrorContentsName(salesPayoffSharedData.getErrorContentNameCondition());

        return salesPayoffUnmatchListForm;
    }


    /**
     * Set shared data by screen data.
     * 
     * @param salesPayoffUnmatchListForm Sales payoff unmatch list screen data.
     * @return packed shared data.
     */
    private SalesPayoffSharedData setSharedDataByScreenData(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm) {

        SalesPayoffSharedData salesPayoffSharedData = new SalesPayoffSharedData();

        salesPayoffSharedData
                .setSystemBrandCodeCondition(salesPayoffUnmatchListForm.getSystemBrandCode());
        salesPayoffSharedData
                .setSystemBrandNameCondition(salesPayoffUnmatchListForm.getSystemBrandName());
        salesPayoffSharedData
                .setSystemCountryCodeCondition(salesPayoffUnmatchListForm.getSystemCountryCode());
        salesPayoffSharedData
                .setSystemCountryNameCondition(salesPayoffUnmatchListForm.getSystemCountryName());
        salesPayoffSharedData.setStoreCodeCondition(salesPayoffUnmatchListForm.getStoreCode());
        salesPayoffSharedData
                .setCashRegisterNoCondition(salesPayoffUnmatchListForm.getCashRegisterNo());
        salesPayoffSharedData.setPayoffDateFrom(salesPayoffUnmatchListForm.getPayoffDateFrom());
        salesPayoffSharedData.setPayoffDateTo(salesPayoffUnmatchListForm.getPayoffDateTo());
        salesPayoffSharedData
                .setErrorContentCodeCondition(salesPayoffUnmatchListForm.getErrorContentsCode());
        salesPayoffSharedData
                .setErrorContentNameCondition(salesPayoffUnmatchListForm.getErrorContentsName());

        SalesPayoffUnmatch salesPayoffUnmatch =
                salesPayoffUnmatchListForm.getSalesPayoffUnmatchList()
                        .get(salesPayoffUnmatchListForm.getIndexValue());

        salesPayoffSharedData.setSystemBrandCode(salesPayoffUnmatch.getBrandCode());
        salesPayoffSharedData.setSystemCountryCode(salesPayoffUnmatch.getCountryCode());
        salesPayoffSharedData.setStoreCode(salesPayoffUnmatch.getStoreCode());
        salesPayoffSharedData.setStoreName(salesPayoffUnmatch.getStoreName());
        salesPayoffSharedData.setPayoffDate(salesPayoffUnmatch.getPayoffDate());
        salesPayoffSharedData.setCashRegisterNo(salesPayoffUnmatch.getCashRegisterNo());
        salesPayoffSharedData.setErrorContents(salesPayoffUnmatch.getErrorContents());

        return salesPayoffSharedData;
    }


    /**
     * Throw ScreenException. Form name and target form fixed.
     * 
     * @param errorMessage Error message.
     * @param errorCode Error code.
     * @param messageType Message type.
     * @param model Model.
     */
    private void throwScreenException(String errorMessage, String errorCode, String messageType,
            Model model) {
        DetailError detailError = new DetailError();
        detailError.setErrorMessage(errorMessage);
        detailError.setMessageType(messageType);
        detailError.setErrorCode(errorCode);

        throw new ScreenException(detailError, HISTORY_LIST_PAGE_FORM, HISTORY_LIST_PAGE,
                model.asMap());
    }
}
