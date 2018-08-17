/**
 * @(#)NoAuthenticationException.java
 *
 *                                    Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.exception;

/**
 * No authentication exception.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class NoAuthenticationException extends RuntimeException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -5612582834093529111L;

    /**
     * Constructor with error message.
     * 
     * @param message Error message.
     */
    public NoAuthenticationException(String message) {
        super(message);
    }
}
