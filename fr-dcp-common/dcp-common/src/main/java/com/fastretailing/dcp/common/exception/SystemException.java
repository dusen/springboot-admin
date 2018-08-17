/**
 * @(#)SystemException.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.exception;

import lombok.NoArgsConstructor;

/**
 * system exception.
 * 
 * @author Fast Retailing.
 * @version $Revision$
 */
@NoArgsConstructor
public class SystemException extends RuntimeException {

    /**
     *serialVersionUID
     */
    private static final long serialVersionUID = 5122452482322713829L;

    /**
     * Constructor for message parameter
     * @param message message
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     * Constructor for message and cause parameter
     * @param message message
     * @param cause cause
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
