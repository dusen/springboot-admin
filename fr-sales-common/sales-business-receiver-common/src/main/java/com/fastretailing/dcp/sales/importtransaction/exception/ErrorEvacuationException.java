/**
 * @(#)ErrorEvacuationException.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.sales.importtransaction.exception;

/**
 * The exception occurred when data is error evacuation.
 *
 */
public class ErrorEvacuationException extends Throwable {
    
    /** Serial version UID. */
    private static final long serialVersionUID = -8898454367673692090L;
    
    /**
     * Construct method.
     * @param message
     */
    public ErrorEvacuationException(String message) {
        super(message);
    }

}
