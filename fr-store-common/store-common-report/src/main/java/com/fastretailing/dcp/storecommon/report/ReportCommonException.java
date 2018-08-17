/**
 * @(#)ReportCommonException.java
 * 
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report;

import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Exception class for report common errors.
 * 
 * @author Fast Retailing
 * @version $Revision$
 *
 */
public class ReportCommonException extends Exception {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -3150802245879896539L;

    /**
     * Result object return from api.
     */
    @Getter
    @Setter
    private ResultObject errorResult;

    /**
     * Default constructor.
     */
    public ReportCommonException() {
        super();
    }

    /**
     * Constructor with error message.
     * 
     * @param message error message.
     */
    public ReportCommonException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and source of exception.
     * 
     * @param message error message.
     * @param cause source of exception.
     */
    public ReportCommonException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with result object.
     * 
     * @param errorResult result object.
     */
    public ReportCommonException(String message, ResultObject errorResult) {
        super(message);
        this.errorResult = errorResult;
    }
}
