/**
 * @(#)MultiUploadResult.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util.aws;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AWS S3 file information result class.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
public class MultiUploadResult {

    /** AWS S3 file name. */
    private String key;
    /** AWS S3 file size (bytes). */
    private String etag;
    /** AWS S3 object tag list. */
    private Throwable exception;
}
