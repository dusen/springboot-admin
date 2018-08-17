/**
 * @(#)ReprintDailySalesReportController.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.reprintdailysalesreport.controller;

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
import com.fastretailing.dcp.sales.reprintdailysalesreport.form.ReprintDailySalesReportForm;
import com.fastretailing.dcp.sales.reprintdailysalesreport.service.ReprintDailySalesReportService;
import com.fastretailing.dcp.storecommon.screen.exception.ScreenException;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import com.fastretailing.dcp.storecommon.screen.form.FormBuilder;
import com.fastretailing.dcp.storecommon.screen.message.MessageType;

/**
 * The controller class for reprint daily sales report page.
 */
@Controller
@RequestMapping(path = "/{brand}/{region}/screen")
public class ReprintDailySalesReportController {

    /** Sales reprint daily sales report page. */
    private static final String REPRINT_DAILY_REPORT_PAGE = "sales-reprint-daily-sales-report";

    /** Sales reprint daily sales report form. */
    private static final String REPRINT_DAILY_REPORT_PAGE_FORM = "reprintDailySalesReportForm";

    /** Sales reprint daily sales report service. */
    @Autowired
    private ReprintDailySalesReportService reprintDailySalesReportService;

    /**
     * Initialize the detail list page.
     *
     * @param brand Brand code.
     * @param region Region code.
     * @param storeCode Store code.
     * @param commonBaseForm Form of common items.
     * @param model The model attribute for this page.
     * @return The name of list page.
     */
    @RequestMapping(path = "/{store_code}/sales-reprint-daily-sales-report")
    public String initializeWithStore(@PathVariable("brand") String brand,
            @PathVariable("region") String region, @PathVariable("store_code") String storeCode,
            CommonBaseForm commonBaseForm, Model model) {

        ReprintDailySalesReportForm reprintDailySalesReportForm =
                FormBuilder.build(ReprintDailySalesReportForm.class, commonBaseForm);

        reprintDailySalesReportForm.setStoreCode(storeCode);
        // Get initialize information.
        model.addAttribute(REPRINT_DAILY_REPORT_PAGE_FORM, reprintDailySalesReportService
                .getInitializeInformation(reprintDailySalesReportForm));

        return REPRINT_DAILY_REPORT_PAGE;
    }

    /**
     * Initialize the detail list page.
     *
     * @param commonBaseForm Form of common items.
     * @param model The model attribute for this page.
     * @return The name of list page.
     */
    @RequestMapping(path = "/sales-reprint-daily-sales-report")
    public String initializeWithNoStore(CommonBaseForm commonBaseForm, Model model) {

        ReprintDailySalesReportForm reprintDailySalesReportForm =
                FormBuilder.build(ReprintDailySalesReportForm.class, commonBaseForm);

        // Get initialize information.
        model.addAttribute(REPRINT_DAILY_REPORT_PAGE_FORM, reprintDailySalesReportService
                .getInitializeInformation(reprintDailySalesReportForm));

        return REPRINT_DAILY_REPORT_PAGE;
    }

    /**
     * Display the sales reprint daily report page.
     *
     * @param reprintDailySalesReportForm The form for this page.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/{store_code}/sales-reprint-daily-sales-report/preview")
    public String displayReprintDailySalesReportListPageWithStore(
            @Validated ReprintDailySalesReportForm reprintDailySalesReportForm,
            BindingResult bindingResult, Model model) {

        errorMessageBinding(bindingResult, model);
        // Get reprint daily report list.
        reprintDailySalesReportForm = reprintDailySalesReportService
                .getReprintDailySalesReportList(reprintDailySalesReportForm);

        DetailError detailError = reprintDailySalesReportForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, REPRINT_DAILY_REPORT_PAGE_FORM,
                    REPRINT_DAILY_REPORT_PAGE, model.asMap());
        }

        model.addAttribute(REPRINT_DAILY_REPORT_PAGE_FORM, reprintDailySalesReportForm);
        return REPRINT_DAILY_REPORT_PAGE;
    }

    /**
     * Display the sales reprint daily report page.
     *
     * @param reprintDailySalesReportForm The form for this page.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/sales-reprint-daily-sales-report/preview")
    public String displayReprintDailySalesReportListPage(
            @Validated ReprintDailySalesReportForm reprintDailySalesReportForm,
            BindingResult bindingResult, Model model) {

        errorMessageBinding(bindingResult, model);
        // Get reprint daily report list.
        reprintDailySalesReportForm = reprintDailySalesReportService
                .getReprintDailySalesReportList(reprintDailySalesReportForm);

        DetailError detailError = reprintDailySalesReportForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, REPRINT_DAILY_REPORT_PAGE_FORM,
                    REPRINT_DAILY_REPORT_PAGE, model.asMap());
        }

        model.addAttribute(REPRINT_DAILY_REPORT_PAGE_FORM, reprintDailySalesReportForm);
        return REPRINT_DAILY_REPORT_PAGE;
    }

    /**
     * Print the reprint daily report list page.
     *
     * @param reprintDailySalesReportForm The form for this page.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/{store_code}/sales-reprint-daily-sales-report/print")
    public String printReprintDailySalesReportWithStore(
            @Validated ReprintDailySalesReportForm reprintDailySalesReportForm,
            BindingResult bindingResult, Model model) {

        errorMessageBinding(bindingResult, model);
        // Get reprint daily report list.
        reprintDailySalesReportForm = reprintDailySalesReportService
                .printReprintDailySalesReportList(reprintDailySalesReportForm);

        DetailError detailError = reprintDailySalesReportForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, REPRINT_DAILY_REPORT_PAGE_FORM,
                    REPRINT_DAILY_REPORT_PAGE, model.asMap());
        }

        model.addAttribute(REPRINT_DAILY_REPORT_PAGE_FORM, reprintDailySalesReportForm);
        return REPRINT_DAILY_REPORT_PAGE;
    }

    /**
     * Print the reprint daily report list page.
     *
     * @param reprintDailySalesReportForm The form for this page.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/sales-reprint-daily-sales-report/print")
    public String printReprintDailySalesReport(
            @Validated ReprintDailySalesReportForm reprintDailySalesReportForm,
            BindingResult bindingResult, Model model) {

        errorMessageBinding(bindingResult, model);
        // Get reprint daily report list.
        reprintDailySalesReportForm = reprintDailySalesReportService
                .printReprintDailySalesReportList(reprintDailySalesReportForm);

        DetailError detailError = reprintDailySalesReportForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, REPRINT_DAILY_REPORT_PAGE_FORM,
                    REPRINT_DAILY_REPORT_PAGE, model.asMap());
        }

        model.addAttribute(REPRINT_DAILY_REPORT_PAGE_FORM, reprintDailySalesReportForm);
        return REPRINT_DAILY_REPORT_PAGE;
    }

    /**
     * Binding the error message detail.
     *
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     */
    private void errorMessageBinding(BindingResult bindingResult, Model model) {

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

            throw new ScreenException(detailError, REPRINT_DAILY_REPORT_PAGE_FORM,
                    REPRINT_DAILY_REPORT_PAGE, model.asMap());
        }
    }
}
