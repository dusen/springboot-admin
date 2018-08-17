/**
 * @(#)S3TestUtility.java
 *
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.aws.s3.util;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * Utility class of S3 client test.
 */
public class S3TestUtility {

    /**
     * Create ObjectListing data.
     * 
     * @param isTruncated If the object listing is not complete.
     * @param marker Next marker.
     * @param summaries Object summaries.
     * @return ObjectListing data.
     */
    public static ObjectListing createObjectListing(boolean isTruncated, String marker,
            S3ObjectSummary... summaries) {
        ObjectListing listing = new ObjectListing();
        listing.setTruncated(isTruncated);
        listing.setNextMarker(marker);
        for (S3ObjectSummary summary : summaries) {
            listing.getObjectSummaries().add(summary);
        }
        return listing;
    }

    /**
     * Create S3ObjectSummary data.
     * 
     * @param key S3 key.
     * @param size Size.
     * @return S3ObjectSummary data.
     */
    public static S3ObjectSummary createS3ObjectSummary(String key, long size) {
        S3ObjectSummary summary = new S3ObjectSummary();
        summary.setKey(key);
        summary.setSize(size);
        return summary;
    }
}
