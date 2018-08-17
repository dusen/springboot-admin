/**
 * @(#)OmsAmazonS3.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3.service;

import com.fastretailing.dcp.common.aws.s3.entity.DownloadResult;
import com.fastretailing.dcp.common.aws.s3.entity.UploadResult;
import com.fastretailing.dcp.common.exception.S3BucketNotFoundException;
import com.fastretailing.dcp.common.exception.S3FileNotFoundException;
import com.fastretailing.dcp.common.exception.S3SystemException;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * OMS AmazonS3' service interface.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public interface OmsAmazonS3 {

    /**
     * Uploads file to the AmazonS3 bucket.<br>
     *
     * @param bucketName bucket's name
     * @param fileKey file's identifier
     * @param uploadFile upload file's instance
     *
     * @return AmazonS3 upload's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    UploadResult upload(
            String bucketName, String fileKey, File uploadFile
    ) throws S3BucketNotFoundException, S3SystemException;

    /**
     * Uploads file to the AmazonS3 bucket.
     * bucket's name read from application-xxx.yml.<br>
     *
     * @param fileKey file's identifier
     * @param uploadFile upload file's instance
     *
     * @return AmazonS3 upload's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    UploadResult upload(
            String fileKey, File uploadFile
    ) throws S3BucketNotFoundException, S3SystemException;

    /**
     * Uploads file to the AmazonS3 bucket.<br>
     *
     * @param bucketName bucket's name
     * @param fileKey file's identifier
     * @param uploadInputStream upload InputStream's instance
     *
     * @return AmazonS3 upload's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    UploadResult upload(
            String bucketName, String fileKey, InputStream uploadInputStream
    ) throws S3BucketNotFoundException, S3SystemException;

    /**
     * Uploads file to the AmazonS3 bucket.
     * bucket's name read from application-xxx.yml.<br>
     *
     * @param fileKey file's identifier
     * @param uploadInputStream upload InputStream's instance
     *
     * @return AmazonS3 upload's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    UploadResult upload(
            String fileKey, InputStream uploadInputStream
    ) throws S3BucketNotFoundException, S3SystemException;

    /**
     * Uploads file to the AmazonS3 bucket.<br>
     *
     * @param bucketName bucket's name
     * @param fileKey file's identifier
     * @param content content
     *
     * @return AmazonS3 upload's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    UploadResult upload(
            String bucketName, String fileKey, String content
    ) throws S3BucketNotFoundException, S3SystemException;

    /**
     * Uploads file to the AmazonS3 bucket.
     * bucket's name read from application-xxx.yml.<br>
     *
     * @param fileKey file's identifier
     * @param content content
     *
     * @return AmazonS3 upload's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    UploadResult upload(
            String fileKey, String content
    ) throws S3BucketNotFoundException, S3SystemException;

    /**
     * Download file form AmazonS3 bucket.
     * bucket's name read from application-xxx.yml.<br>
     *
     * @param bucketName bucket's name
     * @param fileKey file's identifier
     *
     * @return AmazonS3 download's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    DownloadResult download(
            String bucketName, String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException;

    /**
     * Download file form AmazonS3 bucket.
     * bucket's name read from application-xxx.yml.<br>
     *
     * @param fileKey file's identifier
     *
     * @return AmazonS3 download's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    DownloadResult download(
            String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException;

    /**
     * Move and rename file to destination bucket and destination key.<br>
     *
     * @param srcBucketName source bucket's name
     * @param srcFileKey source file's identifier
     * @param destBucketName destination bucket's name
     * @param destFileKey destination file's identifier
     *
     * @return if true that move success
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    boolean move(
            String srcBucketName, String srcFileKey, String destBucketName, String destFileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException;

    /**
     * Move and rename file to destination bucket and destination key.
     * bucket's name read from application-xxx.yml.<br>
     *
     * @param srcFileKey source file's identifier
     * @param destBucketName destination bucket's name
     * @param destFileKey destination file's identifier
     *
     * @return if true that move success
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    boolean move(
            String srcFileKey, String destBucketName, String destFileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException;

    /**
     * Move and rename file to destination key.
     * bucket's name read from application-xxx.yml.<br>
     *
     * @param srcFileKey source file's identifier
     * @param destFileKey destination file's identifier
     *
     * @return if true that move success
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    boolean move(
            String srcFileKey, String destFileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException;


    /**
     * Deletes file in the AmazonS3 bucket.
     *
     * @param bucketName bucket's name
     * @param fileKey file's identifier
     *
     * @return if true that delete success
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    boolean delete(
            String bucketName, String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException;

    /**
     * Deletes file in the AmazonS3 bucket.
     * bucket's name read from application-xxx.yml.
     *
     * @param fileKey file's identifier
     *
     * @return if true that delete success
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    boolean delete(
            String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException;

    /**
     * Returns a pre-signed URL for accessing an Amazon S3 resource.
     *
     * @param bucketName bucket's name
     * @param fileKey file's identifier
     *
     * @return pre-signed URL instance
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    URL getPreSignedUrl(
            String bucketName, String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException;

    /**
     * Returns a pre-signed URL for accessing an Amazon S3 resource.
     * bucket's name read from application-xxx.yml.
     *
     * @param fileKey file's identifier
     *
     * @return pre-signed URL instance
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    URL getPreSignedUrl(
            String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException;

}
