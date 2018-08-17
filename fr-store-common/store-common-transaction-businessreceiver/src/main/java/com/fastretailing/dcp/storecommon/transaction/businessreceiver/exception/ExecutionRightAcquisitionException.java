/**
 * @(#)ExecutionRightAcquisitionException.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.transaction.businessreceiver.exception;

/**
 * Thrown to indicate that execution rights could not be acquisitioned.
 */
public class ExecutionRightAcquisitionException extends RuntimeException {

    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1869508279087280319L;

    /**
     * Constructs an ExecutionRightAcquisitionException with the specified detail message.
     * 
     * @param message the detail message.
     */
    public ExecutionRightAcquisitionException(String message) {
        super(message);
    }

}
