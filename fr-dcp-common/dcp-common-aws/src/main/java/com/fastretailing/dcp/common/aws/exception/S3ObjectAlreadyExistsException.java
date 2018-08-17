/**
 * @(#)S3ObjectAlreadyExistsException.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.exception;

/**
 * Checked exception thrown when an attempt is made to upload a object of that name already exists.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class S3ObjectAlreadyExistsException extends AwsServiceException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 827071446835941569L;

    /**
     * Constructor.
     * 
     * @param message The detail message.
     */
    public S3ObjectAlreadyExistsException(String message) {
        super(message);
    }
}
