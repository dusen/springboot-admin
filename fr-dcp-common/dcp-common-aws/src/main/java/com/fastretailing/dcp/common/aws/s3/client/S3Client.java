/**
 * @(#)S3Client.java
 *
 *                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3.client;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import com.fastretailing.dcp.common.aws.exception.S3ClientSystemException;
import com.fastretailing.dcp.common.aws.exception.S3MultiObjectDeleteException;
import com.fastretailing.dcp.common.aws.exception.S3ObjectAlreadyExistsException;
import com.fastretailing.dcp.common.aws.exception.S3ObjectNotFoundException;

/**
 * Common AWS S3 client.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public interface S3Client {

    /**
     * Check if the specified AWS S3 key is exists.
     * 
     * @param bucketName S3 bucket name.
     * @param s3Key S3 key.
     * 
     * @return true when exist | false when not exist
     * 
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    boolean doesObjectExists(String bucketName, String s3Key) throws S3ClientSystemException;

    /**
     * Upload an input stream data to AWS S3 storage.
     * 
     * @param bucketName S3 bucket name.
     * @param s3Key S3 key.
     * @param input Input stream data to be uploaded.
     * @param size Byte size of upload data.
     * @param overwrite If true, the object that has a same key will be overwritten.
     * @param tags Put object request tags.
     * 
     * @return ETag of uploaded object.
     * 
     * @throws S3ObjectAlreadyExistsException If overwrite option is false and the object already
     *         exists.
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    String upload(String bucketName, String s3Key, InputStream input, long size, boolean overwrite,
            Map<String, String> tags)
            throws S3ObjectAlreadyExistsException, S3ClientSystemException;

    /**
     * Upload an input stream data to AWS S3 storage.
     * 
     * @param bucketName S3 bucket name.
     * @param s3Key S3 key.
     * @param input Input stream data to be uploaded.
     * @param size Byte size of upload data.
     * @param overwrite If true, the object that has a same key will be overwritten.
     * 
     * @return ETag of uploaded object.
     * 
     * @throws S3ObjectAlreadyExistsException If overwrite option is false and the object already
     *         exists.
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    String upload(String bucketName, String s3Key, InputStream input, long size, boolean overwrite)
            throws S3ObjectAlreadyExistsException, S3ClientSystemException;

    /**
     * Upload an input stream data to AWS S3 storage.
     * 
     * <p>
     * If the S3 object that has a same key already exists, it will be overwritten by uploaded data.
     * 
     * @param bucketName S3 bucket name.
     * @param s3Key S3 key.
     * @param input Input stream data to be uploaded.
     * @param size Byte size of upload data.
     * 
     * @return ETag of uploaded object.
     * 
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    String upload(String bucketName, String s3Key, InputStream input, long size)
            throws S3ClientSystemException;

    /**
     * Upload binary data to AWS S3 storage.
     * 
     * @param bucketName S3 bucket name.
     * @param s3Key S3 key.
     * @param data Binary data to be uploaded.
     * @param overwrite If true, the object that has a same key will be overwritten.
     * @param tags Put object request tags.
     * 
     * @return ETag of uploaded object.
     * 
     * @throws S3ObjectAlreadyExistsException If overwrite option is false and the object already
     *         exists.
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    String upload(String bucketName, String s3Key, byte[] data, boolean overwrite,
            Map<String, String> tags)
            throws S3ObjectAlreadyExistsException, S3ClientSystemException;

    /**
     * Upload binary data to AWS S3 storage.
     * 
     * @param bucketName S3 bucket name.
     * @param s3Key S3 key.
     * @param data Binary data to be uploaded.
     * @param overwrite If true, the object that has a same key will be overwritten.
     * 
     * @return ETag of uploaded object.
     * 
     * @throws S3ObjectAlreadyExistsException If overwrite option is false and the object already
     *         exists.
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    String upload(String bucketName, String s3Key, byte[] data, boolean overwrite)
            throws S3ObjectAlreadyExistsException, S3ClientSystemException;

    /**
     * Upload binary data to AWS S3 storage.
     * 
     * <p>
     * If the S3 object that has a same key already exists, it will be overwritten by uploaded data.
     * 
     * @param bucketName S3 bucket name.
     * @param s3Key S3 key.
     * @param data Binary data to be uploaded.
     * 
     * @return ETag of uploaded object.
     * 
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    String upload(String bucketName, String s3Key, byte[] data) throws S3ClientSystemException;

    /**
     * Download an S3 object as byte array.
     * 
     * @param bucketName S3 bucket name.
     * @param s3Key S3 key.
     * 
     * @return Download data.
     * 
     * @throws S3ObjectNotFoundException If the object does not exist.
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    byte[] downloadAsByteArray(String bucketName, String s3Key)
            throws S3ObjectNotFoundException, S3ClientSystemException;

    /**
     * Download an S3 object as input stream.
     * 
     * @param bucketName S3 bucket name.
     * @param s3Key S3 key.
     * 
     * @return Download data.
     * 
     * @throws S3ObjectNotFoundException If the object does not exist.
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    InputStream downloadAsInputStream(String bucketName, String s3Key)
            throws S3ObjectNotFoundException, S3ClientSystemException;

    /**
     * Delete an S3 object from S3 storage.
     * 
     * @param bucketName S3 bucket name.
     * @param s3Key S3 key.
     * 
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    void delete(String bucketName, String s3Key) throws S3ClientSystemException;

    /**
     * Delete an S3 object from S3 storage.
     * 
     * <p>
     * <b>Caution:</b> this operation is not atomic. If some of objects are deleted and some are
     * not, throws {@link S3MultiObjectDeleteException}.
     * 
     * @param bucketName S3 bucket name.
     * @param s3Keys S3 key.
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     * @throws S3MultiObjectDeleteException If any errors occurred during deletion.
     */
    void deleteMultiObjects(String bucketName, String... s3Keys)
            throws S3ClientSystemException, S3MultiObjectDeleteException;

    /**
     * Move an S3 object to another S3 bucket.
     * 
     * @param sourceBucketName Source bucket name.
     * @param sourceS3Key Source S3 key.
     * @param destinationBucketName Destination bucket name.
     * @param destinationS3Key Destination S3 key.
     * @param overwrite If true, the object that has a same key will be overwritten.
     * 
     * @throws S3ObjectNotFoundException If the object does not exist.
     * @throws S3ObjectAlreadyExistsException If overwrite option is false and the object already
     *         exists.
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    void moveToOtherBucket(String sourceBucketName, String sourceS3Key,
            String destinationBucketName, String destinationS3Key, boolean overwrite)
            throws S3ObjectNotFoundException, S3ObjectAlreadyExistsException,
            S3ClientSystemException;

    /**
     * Move an S3 object to another S3 bucket.
     * 
     * @param sourceBucketName Source bucket name.
     * @param sourceS3Key Source S3 key.
     * @param destinationBucketName Destination bucket name.
     * @param destinationS3Key Destination S3 key.
     * 
     * @throws S3ObjectNotFoundException If the object does not exist.
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    void moveToOtherBucket(String sourceBucketName, String sourceS3Key,
            String destinationBucketName, String destinationS3Key)
            throws S3ObjectNotFoundException, S3ClientSystemException;

    /**
     * Move an S3 object to same S3 bucket.
     * 
     * @param bucketName S3 bucket.
     * @param sourceS3Key Source S3 key.
     * @param destinationS3Key Destination S3 key.
     * @param overwrite If true, the object that has a same key will be overwritten.
     * 
     * @throws S3ObjectNotFoundException If the object does not exist.
     * @throws S3ObjectAlreadyExistsException If overwrite option is false and the object already
     *         exists.
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    void move(String bucketName, String sourceS3Key, String destinationS3Key, boolean overwrite)
            throws S3ObjectNotFoundException, S3ObjectAlreadyExistsException,
            S3ClientSystemException;

    /**
     * Move an S3 object to same S3 bucket.
     * 
     * @param bucketName S3 bucket.
     * @param sourceS3Key Source S3 key.
     * @param destinationS3Key Destination S3 key.
     * 
     * @throws S3ObjectNotFoundException If the object does not exist.
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    void move(String bucketName, String sourceS3Key, String destinationS3Key)
            throws S3ObjectNotFoundException, S3ClientSystemException;

    /**
     * Generate Pre-Signed URL.
     *
     * @param bucketName S3 bucket.
     * @param s3Key S3 key.
     * @param expirationSeconds Number of seconds before expiration.
     * 
     * @return Pre-Signed URL.
     * 
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    String generatePreSignedUrl(String bucketName, String s3Key, long expirationSeconds)
            throws S3ClientSystemException;

    /**
     * Generate Pre-Signed URL using default expiration seconds.
     * 
     * @param bucketName S3 bucket.
     * @param s3Key S3 key.
     * 
     * @return Pre-Signed URL.
     * 
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    String generatePreSignedUrl(String bucketName, String s3Key)
            throws S3ClientSystemException;

    /**
     * Search objects from AWS S3 storage.
     * 
     * @param bucketName S3 bucket.
     * @param prefix The specified prefix.("foo/")<br>
     *        If it is null, objects in root of bucket will be returned.
     * 
     * @return List of {@link S3ObjectSummary}.
     * 
     * @throws S3ClientSystemException If any errors occurred in Amazon SDK.
     */
    List<S3ObjectSummary> searchObjects(String bucketName, String prefix)
            throws S3ClientSystemException;
}
