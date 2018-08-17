/**
 * @(#)ScreenException.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.exception;

import java.util.Map;
import org.springframework.validation.BindingResult;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import lombok.Getter;
import lombok.Setter;

/**
 * The exception class for screen application.
 *
 */
public class ScreenException extends RuntimeException {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = -4237244916300556048L;

    /** The detail of error. */
    @Getter
    @Setter
    private DetailError detailError;

    /** The data bean for saving common item. */
    @Getter
    @Setter
    private String formName;

    /** The target url to transfer. */
    @Getter
    @Setter
    private String targetUrl;

    /** The model to transfer. */
    @Getter
    @Setter
    private Map<String, Object> modelMap;

    /**
     * The validation result.
     */
    @Getter
    @Setter
    private BindingResult bindResult;

    /**
     * Constructor for ScreenException.
     * 
     * @param detailError Error details.
     * @param formName The form of view.
     * @param targetUrl The url that where the message display to.
     * @param modelMap The map for model attribute.
     */
    public ScreenException(DetailError detailError, String formName, String targetUrl,
            Map<String, Object> modelMap) {
        super();
        this.detailError = detailError;
        this.formName = formName;
        this.targetUrl = targetUrl;
        this.modelMap = modelMap;
    }

    /**
     * Constructor for ScreenException.
     * 
     * @param bindResult Validation result.
     * @param formName The form of view.
     * @param targetUrl The url that where the message display to.
     * @param modelMap The map for model attribute.
     */
    public ScreenException(BindingResult bindResult, String formName, String targetUrl,
            Map<String, Object> modelMap) {

        this.formName = formName;
        this.targetUrl = targetUrl;
        this.modelMap = modelMap;
        this.bindResult = bindResult;
    }
}
