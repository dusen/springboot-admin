/**
 * @(#)AjaxScreenException.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.exception;

import java.util.List;
import org.springframework.validation.FieldError;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import com.fastretailing.dcp.storecommon.screen.message.MessageType;
import lombok.Getter;

/**
 * The exception class for ajax screen request.
 *
 */
public class AjaxScreenException extends RuntimeException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -4072210521919436661L;

    /** The detail of error. */
    @Getter
    private DetailError detailError;

    @Getter
    private List<FieldError> fieldErrors;

    /** Error type. */
    @Getter
    private String ajaxMessageType;

    /**
     * constructor for AjaxScreenException.
     * 
     * @param messageType the error type.
     * @param detailError detail error.
     */
    public AjaxScreenException(MessageType messageType, DetailError detailError) {
        this.ajaxMessageType = messageType.getType();
        this.detailError = detailError;
    }

    /**
     * constructor for AjaxScreenException.
     * 
     * @param fieldErrors the error fields.
     */
    public AjaxScreenException(List<FieldError> fieldErrors) {
        this.ajaxMessageType = MessageType.ERROR.getType();
        this.fieldErrors = fieldErrors;
    }
}
