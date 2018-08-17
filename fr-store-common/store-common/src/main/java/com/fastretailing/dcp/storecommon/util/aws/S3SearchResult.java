/**
 * @(#)S3SearchResult.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util.aws;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * AWS S3 file search result class.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class S3SearchResult {

    /** A list of summary information describing the objects stored in the bucket. */
    private List<S3FileSummary> s3FileNameResults = new ArrayList<S3FileSummary>();

    /** The name of the Amazon S3 bucket containing the listed objects. */
    private String bucketName;

    /**
     * The marker to use in order to request the next page of results - only
     * populated if the isTruncated member indicates that this object listing is
     * truncated.
     */
    private String nextMarker;

    /**
     * Indicates if this is a complete listing, or if the caller needs to make
     * additional requests to Amazon S3 to see the full object listing for an S3
     * bucket.
     */
    private boolean truncated;

}
