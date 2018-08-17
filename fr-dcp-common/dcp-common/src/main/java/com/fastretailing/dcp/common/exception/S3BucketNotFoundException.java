/**
 * @(#)S3BucketNotFoundException.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.exception;

import com.fastretailing.dcp.common.model.ResultObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Exception of AmazonS3's bucket does not found.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class S3BucketNotFoundException extends RuntimeException {

    /**
     * error json object.
     */
    private ResultObject resultObject;

}
