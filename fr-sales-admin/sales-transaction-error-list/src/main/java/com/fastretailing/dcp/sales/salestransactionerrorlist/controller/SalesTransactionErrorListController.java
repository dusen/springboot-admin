/**
 * @(#)SalesTransactionErrorListController.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fastretailing.dcp.sales.salestransactionerrorlist.form.SalesTransactionErrorListForm;
import com.fastretailing.dcp.sales.salestransactionerrorlist.service.SalesTransactionErrorListService;
import com.fastretailing.dcp.storecommon.screen.exception.ScreenException;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import com.fastretailing.dcp.storecommon.screen.form.FormBuilder;
import com.fastretailing.dcp.storecommon.screen.message.MessageType;

/**
 * The controller class for sales transaction error list page.
 */
@Controller
@RequestMapping(path = "/{brand}/{region}/screen/sales-transaction-error-list")
public class SalesTransactionErrorListController {

    /** Sales transaction error list page. */
    private static final String ERROR_LIST_PAGE = "sales-transaction-error-list";

    /** Sales transaction error ajax page. */
    private static final String ERROR_LIST_CHILD_PAGE = "sales-transaction-error-list-ajaxData";

    /** Sales transaction error list form. */
    private static final String ERROR_LIST_PAGE_FORM = "salesTransactionErrorListForm";

    /** Ajax success. */
    private static final String AJAX_SUCCESS = "AJAX_SUCCESS";

    /** Cache control. */
    private static String CACHE_CONTROL = "Cache-Control";

    /** Cache control content. */
    private static String CACHE_CONTROL_CONTENT = "no-cache, no-store, must-revalidate";

    /** Content disposition. */
    private static String CONTENT_DISPOSITION = "Content-Disposition";

    /** Content disposition content. */
    private static String CONTENT_DISPOSITION_CONTENT =
            "attachment; filename =SalesErrorInformationList.zip";

    /** Pragma. */
    private static String PRAGMA = "Pragma";

    /** Pragma no cache. */
    private static String PRAGMA_NO_CACHE = "no-cache";

    /** Expires. */
    private static String EXPIRES = "Expires";

    /** Expires zero. */
    private static String EXPIRES_ZERO = "0";

    /** Application octet stream. */
    private static String APPLICATION_OCTET_STREAM = "application/octet-stream";

    /** Sales transaction error list service. */
    @Autowired
    private SalesTransactionErrorListService salesTransactionErrorListService;

    /**
     * Display the detail list page.
     *
     * @param commonBaseForm Form of common items.
     * @param model The model attribute for this page.
     * @return The name of list page.
     */
    @RequestMapping(path = "")
    public String initialize(CommonBaseForm commonBaseForm, Model model) {
        SalesTransactionErrorListForm salesTransactionErrorListForm =
                FormBuilder.build(SalesTransactionErrorListForm.class, commonBaseForm);

        // Get initialize information.
        model.addAttribute(ERROR_LIST_PAGE_FORM, salesTransactionErrorListService
                .getInitializeInformation(salesTransactionErrorListForm));

        return ERROR_LIST_PAGE;
    }

    /**
     * Display sales transaction error list page.
     *
     * @param salesTransactionErrorListForm The form for this page.
     * @param model The model attribute for this page.
     * @return The name of item list page.
     */
    @PostMapping(path = "/list")
    public String displaySalesTransactionErrorListPage(
            @Validated SalesTransactionErrorListForm salesTransactionErrorListForm,
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

            throw new ScreenException(detailError, ERROR_LIST_PAGE_FORM, ERROR_LIST_PAGE,
                    model.asMap());
        }
        // Get sales transaction error list.
        salesTransactionErrorListForm = salesTransactionErrorListService
                .getSalesTransactionErrorList(salesTransactionErrorListForm);

        DetailError detailError = salesTransactionErrorListForm.getDetailError();
        if (detailError != null) {
            throw new ScreenException(detailError, ERROR_LIST_PAGE_FORM, ERROR_LIST_PAGE,
                    model.asMap());
        }

        model.addAttribute(ERROR_LIST_PAGE_FORM, salesTransactionErrorListForm);
        return ERROR_LIST_CHILD_PAGE;
    }

    /**
     * Display sales transaction error list page.
     *
     * @param salesTransactionErrorListForm The form for this page.
     * @return Ajax success.
     */
    @PostMapping(path = "/numberlink")
    @ResponseBody
    public String numberlinkClick(SalesTransactionErrorListForm salesTransactionErrorListForm) {
        salesTransactionErrorListService.numberLinkAccess(salesTransactionErrorListForm);
        return AJAX_SUCCESS;
    }

    /**
     * Delete.
     *
     * @param salesTransactionErrorListForm The form for this page.
     * @return Ajax success.
     */
    @PostMapping(path = "/delete")
    @ResponseBody
    public String delete(SalesTransactionErrorListForm salesTransactionErrorListForm) {
        salesTransactionErrorListService.delete(salesTransactionErrorListForm);
        return AJAX_SUCCESS;
    }

    /**
     * Upload.
     *
     * @param salesTransactionErrorListForm The form for this page.
     * @return Ajax success.
     */
    @PostMapping(path = "/upload")
    @ResponseBody
    public String upload(SalesTransactionErrorListForm salesTransactionErrorListForm) {
        salesTransactionErrorListService.upload(salesTransactionErrorListForm);
        return AJAX_SUCCESS;
    }

    /**
     * Download.
     *
     * @param salesTransactionErrorListForm The form for this page.
     * @return Download file resource.
     * @throws IOException IOException.
     */
    @PostMapping(path = "/download")
    public ResponseEntity<InputStreamResource> download(
            SalesTransactionErrorListForm salesTransactionErrorListForm) throws IOException {
        File zipFile = salesTransactionErrorListService.download(salesTransactionErrorListForm);
        try (InputStream fileStream =
                new ByteArrayInputStream(FileUtils.readFileToByteArray(zipFile))) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(CACHE_CONTROL, CACHE_CONTROL_CONTENT);
            headers.add(CONTENT_DISPOSITION, CONTENT_DISPOSITION_CONTENT);
            headers.add(PRAGMA, PRAGMA_NO_CACHE);
            headers.add(EXPIRES, EXPIRES_ZERO);
            InputStreamResource resource = new InputStreamResource(fileStream);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(FileUtils.readFileToByteArray(zipFile).length)
                    .contentType(MediaType.parseMediaType(APPLICATION_OCTET_STREAM))
                    .body(resource);
        }
    }
}
