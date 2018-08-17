/**
 * @(#)AwsS3FileNotFoundException.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util.aws;

/**
 * Checked exception thrown when an attempt is made to get a file of that name does not exists.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class AwsS3FileNotFoundException extends Exception {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    public AwsS3FileNotFoundException(String message) {
        super(message);
    }
}
