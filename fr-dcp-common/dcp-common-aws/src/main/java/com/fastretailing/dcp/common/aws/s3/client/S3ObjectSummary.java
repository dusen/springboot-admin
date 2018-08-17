/**
 * @(#)S3ObjectSummary.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3.client;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AWS S3 object information result class.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
public class S3ObjectSummary {

    /** AWS S3 object name. */
    private String key;

    /** AWS S3 object size (bytes). */
    private Long size;
}
