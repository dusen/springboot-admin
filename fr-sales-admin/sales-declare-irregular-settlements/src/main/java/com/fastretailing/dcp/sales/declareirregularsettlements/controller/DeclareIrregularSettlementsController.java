/**
 * @(#)DeclareIrregularSettlementsController.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.controller;

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
import com.fastretailing.dcp.sales.declareirregularsettlements.form.DeclareIrregularSettlementsForm;
import com.fastretailing.dcp.sales.declareirregularsettlements.service.DeclareIrregularSettlementsService;
import com.fastretailing.dcp.storecommon.screen.exception.ScreenException;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import com.fastretailing.dcp.storecommon.screen.message.MessageType;

/**
 * The controller class for declare irregular settlements.
 */

@Controller
@RequestMapping(path = "/{brand}/{region}/screen")
public class DeclareIrregularSettlementsController {

    /** Declare irregular settlements initialize. */
    private static final String DECLARE_IRREGULAR_SETTLEMENTS_PAGE =
            "declare-irregular-settlements";

    /** Declare irregular settlements form. */
    private static final String DECLARE_IRREGULAR_SETTLEMENTS_FORM =
            "declareIrregularSettlementsForm";

    /** Declare irregular settlements service. */
    @Autowired
    private DeclareIrregularSettlementsService declareIrregularSettlementsService;

    /**
     * Initialize the declare irregular settlements page.
     *
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @RequestMapping(path = "/{store_code}/declare-irregular-settlements")
    public String initializeStore(@PathVariable("store_code") String storeCode, Model model) {
        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        declareIrregularSettlementsForm.setStoreCode(storeCode);
        DeclareIrregularSettlementsForm displayForm =
                declareIrregularSettlementsService.initialize(declareIrregularSettlementsForm);
        model.addAttribute(DECLARE_IRREGULAR_SETTLEMENTS_FORM, displayForm);

        return DECLARE_IRREGULAR_SETTLEMENTS_PAGE;
    }

    /**
     * Initialize the declare irregular settlements page.
     *
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @RequestMapping(path = "/declare-irregular-settlements")
    public String initializeNonStore(Model model) {
        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        DeclareIrregularSettlementsForm displayForm =
                declareIrregularSettlementsService.initialize(declareIrregularSettlementsForm);
        model.addAttribute(DECLARE_IRREGULAR_SETTLEMENTS_FORM, displayForm);

        return DECLARE_IRREGULAR_SETTLEMENTS_PAGE;
    }

    /**
     * Search declare irregular settlements page.
     *
     * @param declareIrregularSettlementsForm The form for this page.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/{store_code}/declare-irregular-settlements/list")
    public String searchStore(
            @Validated DeclareIrregularSettlementsForm declareIrregularSettlementsForm,
            BindingResult bindingResult, Model model) {

        declareIrregularSettlementsService.setBaseInformation(declareIrregularSettlementsForm);
        errorMessageBinding(bindingResult, model);
        DeclareIrregularSettlementsForm displayForm =
                declareIrregularSettlementsService.search(declareIrregularSettlementsForm);

        DetailError detailError = declareIrregularSettlementsForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, DECLARE_IRREGULAR_SETTLEMENTS_FORM,
                    DECLARE_IRREGULAR_SETTLEMENTS_PAGE, model.asMap());
        }

        model.addAttribute(DECLARE_IRREGULAR_SETTLEMENTS_FORM, displayForm);
        return DECLARE_IRREGULAR_SETTLEMENTS_PAGE;
    }

    /**
     * Search declare irregular settlements page.
     *
     * @param declareIrregularSettlementsForm The form for this page.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/declare-irregular-settlements/list")
    public String searchNonStore(
            @Validated DeclareIrregularSettlementsForm declareIrregularSettlementsForm,
            BindingResult bindingResult, Model model) {

        declareIrregularSettlementsService.setBaseInformation(declareIrregularSettlementsForm);
        errorMessageBinding(bindingResult, model);
        DeclareIrregularSettlementsForm displayForm =
                declareIrregularSettlementsService.search(declareIrregularSettlementsForm);

        DetailError detailError = declareIrregularSettlementsForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, DECLARE_IRREGULAR_SETTLEMENTS_FORM,
                    DECLARE_IRREGULAR_SETTLEMENTS_PAGE, model.asMap());
        }

        model.addAttribute(DECLARE_IRREGULAR_SETTLEMENTS_FORM, displayForm);
        return DECLARE_IRREGULAR_SETTLEMENTS_PAGE;
    }

    /**
     * Calculate declare irregular settlements page.
     *
     * @param declareIrregularSettlementsForm The form for this page.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/{store_code}/declare-irregular-settlements/calculation")
    public String calculationStore(
            @Validated DeclareIrregularSettlementsForm declareIrregularSettlementsForm,
            BindingResult bindingResult, Model model) {

        declareIrregularSettlementsService.setBaseInformation(declareIrregularSettlementsForm);
        errorMessageBinding(bindingResult, model);
        declareIrregularSettlementsService.calculation(declareIrregularSettlementsForm);

        DetailError detailError = declareIrregularSettlementsForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, DECLARE_IRREGULAR_SETTLEMENTS_FORM,
                    DECLARE_IRREGULAR_SETTLEMENTS_PAGE, model.asMap());
        }

        model.addAttribute(DECLARE_IRREGULAR_SETTLEMENTS_FORM, declareIrregularSettlementsForm);
        return DECLARE_IRREGULAR_SETTLEMENTS_PAGE;
    }

    /**
     * Calculate declare irregular settlements page.
     *
     * @param declareIrregularSettlementsForm The form for this page.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/declare-irregular-settlements/calculation")
    public String calculationNonStore(
            @Validated DeclareIrregularSettlementsForm declareIrregularSettlementsForm,
            BindingResult bindingResult, Model model) {

        declareIrregularSettlementsService.setBaseInformation(declareIrregularSettlementsForm);
        errorMessageBinding(bindingResult, model);
        declareIrregularSettlementsService.calculation(declareIrregularSettlementsForm);

        DetailError detailError = declareIrregularSettlementsForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, DECLARE_IRREGULAR_SETTLEMENTS_FORM,
                    DECLARE_IRREGULAR_SETTLEMENTS_PAGE, model.asMap());
        }
        model.addAttribute(DECLARE_IRREGULAR_SETTLEMENTS_FORM, declareIrregularSettlementsForm);
        return DECLARE_IRREGULAR_SETTLEMENTS_PAGE;
    }

    /**
     * Confirm declare irregular settlements page.
     *
     * @param declareIrregularSettlementsForm The form for this page.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/{store_code}/declare-irregular-settlements/confirm")
    public String confirm(
            @Validated DeclareIrregularSettlementsForm declareIrregularSettlementsForm,
            BindingResult bindingResult, Model model) {

        declareIrregularSettlementsService.setBaseInformation(declareIrregularSettlementsForm);
        errorMessageBinding(bindingResult, model);
        DetailError detailError = declareIrregularSettlementsForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, DECLARE_IRREGULAR_SETTLEMENTS_FORM,
                    DECLARE_IRREGULAR_SETTLEMENTS_PAGE, model.asMap());
        }
        declareIrregularSettlementsService.confirm(declareIrregularSettlementsForm);
        model.addAttribute(DECLARE_IRREGULAR_SETTLEMENTS_FORM, declareIrregularSettlementsForm);
        return DECLARE_IRREGULAR_SETTLEMENTS_PAGE;
    }

    /**
     * Confirm declare irregular settlements page.
     *
     * @param declareIrregularSettlementsForm The form for this page.
     * @param bindingResult Binding result.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/declare-irregular-settlements/confirm")
    public String confirmNonStore(
            @Validated DeclareIrregularSettlementsForm declareIrregularSettlementsForm,
            BindingResult bindingResult, Model model) {

        declareIrregularSettlementsService.setBaseInformation(declareIrregularSettlementsForm);
        errorMessageBinding(bindingResult, model);
        declareIrregularSettlementsService.confirm(declareIrregularSettlementsForm);
        DetailError detailError = declareIrregularSettlementsForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, DECLARE_IRREGULAR_SETTLEMENTS_FORM,
                    DECLARE_IRREGULAR_SETTLEMENTS_PAGE, model.asMap());
        }
        model.addAttribute(DECLARE_IRREGULAR_SETTLEMENTS_FORM, declareIrregularSettlementsForm);
        return DECLARE_IRREGULAR_SETTLEMENTS_PAGE;
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

            throw new ScreenException(detailError, DECLARE_IRREGULAR_SETTLEMENTS_FORM,
                    DECLARE_IRREGULAR_SETTLEMENTS_PAGE, model.asMap());
        }
    }
}
