/**
 * @(#)S3ClientSystemException.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.exception;

/**
 * AmazonS3's Exception.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class S3ClientSystemException extends AwsServiceException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5672229888388665396L;

    /**
     * Constructor.
     * 
     * @param message The detail message.
     */
    public S3ClientSystemException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param message The detail message.
     * @param cause The cause.
     */
    public S3ClientSystemException(String message, Throwable cause) {
        super(message, cause);
    }

}
