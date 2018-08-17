/**
 * @(#)S3FileSummary.java
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
public class S3FileSummary {

    /** AWS S3 file name. */
    private String key;
    /** AWS S3 file size (bytes). */
    private Long size;
}
