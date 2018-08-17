/**
 * @(#)ValidationException.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon;

/**
 * This class is an exception class indicating that an error occurred during the validation
 * operation.
 */
public class ValidationException extends RuntimeException {

    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = -1434033550114370781L;

    /**
     * Constructor.
     */
    public ValidationException() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param message Error message.
     * @param cause Related throwable object.
     * @param enableSuppression Enable suppression.
     * @param writableStackTrace Stack trace.
     */
    public ValidationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructor.
     * 
     * @param message Error message.
     * @param cause Related throwable object.
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     * 
     * @param message Error message.
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param cause Related throwable object.
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
