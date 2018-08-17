/**
 * @(#)UploadResult.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3.entity;

import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AmazonS3 upload's output entity.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResult {

    /**
     * Amazon S3 's putObject.<br>
     */
    private PutObjectResult result;

    /**
     * Bucket's name.<br>
     */
    private String bucketName;

    /**
     * File's identifier.<br>
     */
    private String fileKey;


}
