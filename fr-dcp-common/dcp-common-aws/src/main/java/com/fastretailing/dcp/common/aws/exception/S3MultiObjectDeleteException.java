/**
 * @(#)S3MultiObjectDeleteException.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.exception;

import com.amazonaws.services.s3.model.MultiObjectDeleteException;

/**
 * AmazonS3's multi object delete exception.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class S3MultiObjectDeleteException extends AwsServiceException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1707924747814540885L;

    /**
     * Constructor.
     * 
     * @param message The detail message.
     * @param e The cause.
     */
    public S3MultiObjectDeleteException(String message, MultiObjectDeleteException e) {
        super(message, e);
    }
}
