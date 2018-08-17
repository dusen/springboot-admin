/**
 * @(#)S3ObjectNotFoundException.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.exception;

/**
 * Exception of AmazonS3's bucket's file does not found.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class S3ObjectNotFoundException extends AwsServiceException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 5167563717150232796L;

    /**
     * Constructor.
     * 
     * @param message The detail message.
     */
    public S3ObjectNotFoundException(String message) {
        super(message);
    }
}
