/**
 * @(#)AwsServiceException.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.exception;

/**
 * AwsServiceException is base exception class that is thrown if AWS service clients cause an error.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public abstract class AwsServiceException extends Exception {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -587426064975817082L;

    /**
     * Constructor.
     */
    public AwsServiceException() {}

    /**
     * Constructor.
     * 
     * @param message The detail message.
     */
    public AwsServiceException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param message The detail message.
     * @param t The cause.
     */
    public AwsServiceException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * Constructor.
     * 
     * @param t The cause.
     */
    public AwsServiceException(Throwable t) {
        super(t);
    }
}
