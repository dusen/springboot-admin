/**
 * @(#)DownloadResult.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * AmazonS3 download's output entity.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadResult {

    /**
     * Download file's instance.
     */
    private File resultFile;

    /**
     * Bucket's name.<br>
     */
    private String bucketName;

    /**
     * File's identifier.<br>
     */
    private String fileKey;

}
