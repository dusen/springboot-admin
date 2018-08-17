/**
 * @(#)S3ClientImpl.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.Tag;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.fastretailing.dcp.common.aws.exception.S3ClientSystemException;
import com.fastretailing.dcp.common.aws.exception.S3MultiObjectDeleteException;
import com.fastretailing.dcp.common.aws.exception.S3ObjectAlreadyExistsException;
import com.fastretailing.dcp.common.aws.exception.S3ObjectNotFoundException;

/**
 * Implementation of {@link S3Client}.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class S3ClientImpl implements S3Client {

    /** Delimiter. */
    private static final String AWS_S3_DELIMITER = "/";

    /** S3 Client of aws-sdk. */
    private final AmazonS3 amazonS3;

    /** Transfer manager. */
    private final TransferManager transferManager;

    /** The number of seconds before Pre-Signed URL expires. */
    private final long defaultExpirationSeconds;

    /**
     * Constructor.
     * 
     * @param amazonS3 Amazon S3.
     * @param transferManager Transfer manager.
     * @param defaultExpirationSeconds The number of seconds before Pre-Signed URL expires.
     */
    public S3ClientImpl(AmazonS3 amazonS3, TransferManager transferManager,
            long defaultExpirationSeconds) {
        this.amazonS3 = amazonS3;
        this.transferManager = transferManager;
        this.defaultExpirationSeconds = defaultExpirationSeconds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean doesObjectExists(String bucketName, String s3Key)
            throws S3ClientSystemException {

        if (StringUtils.isAnyEmpty(bucketName, s3Key)) {
            throw new IllegalArgumentException(String.format(
                    "Bucket name and S3 key must not be empty. bucket name: %s, S3 key: %s",
                    bucketName, s3Key));
        }
        try {
            return amazonS3.doesObjectExist(bucketName, s3Key);
        } catch (SdkClientException e) {
            throw new S3ClientSystemException(
                    "Error has occurred during checking existence of the S3 object.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String upload(String bucketName, String s3Key, InputStream input, long size,
            boolean overwrite, Map<String, String> tags)
            throws S3ObjectAlreadyExistsException, S3ClientSystemException {

        if (StringUtils.isAnyEmpty(bucketName, s3Key)) {
            throw new IllegalArgumentException(String.format(
                    "Bucket name and S3 key must not be empty. bucket name: %s, S3 key: %s",
                    bucketName, s3Key));
        }
        if (input == null) {
            throw new IllegalArgumentException(
                    String.format("Upload data must not be null. bucket name: %s, S3 key: %s",
                            bucketName, s3Key));
        }
        if (!overwrite && doesObjectExists(bucketName, s3Key)) {
            throw new S3ObjectAlreadyExistsException(String.format(
                    "S3 object already exists.  bucket name: %s, S3 key: %s", bucketName, s3Key));
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        objectMetadata.setContentLength(size);
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, s3Key, input, objectMetadata);

        if (MapUtils.isNotEmpty(tags)) {
            List<Tag> s3ObjectTags = tags.entrySet()
                    .stream()
                    .map(e -> new Tag(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());
            putObjectRequest.setTagging(new ObjectTagging(s3ObjectTags));
        }

        try {
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            return uploadResult.getETag();
        } catch (SdkClientException | InterruptedException e) {
            throw new S3ClientSystemException("Error has occurred during upload.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String upload(String bucketName, String s3Key, InputStream input, long size,
            boolean overwrite) throws S3ObjectAlreadyExistsException, S3ClientSystemException {
        return upload(bucketName, s3Key, input, size, overwrite, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String upload(String bucketName, String s3Key, InputStream input, long size)
            throws S3ClientSystemException {

        if (StringUtils.isAnyEmpty(bucketName, s3Key)) {
            throw new IllegalArgumentException(String.format(
                    "Bucket name and S3 key must not be empty. bucket name: %s, S3 key: %s",
                    bucketName, s3Key));
        }
        if (input == null) {
            throw new IllegalArgumentException(
                    String.format("Upload data must not be null. bucket name: %s, S3 key: %s",
                            bucketName, s3Key));
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        objectMetadata.setContentLength(size);
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, s3Key, input, objectMetadata);

        try {
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            return uploadResult.getETag();
        } catch (SdkClientException | InterruptedException e) {
            throw new S3ClientSystemException("Error has occurred during upload.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String upload(String bucketName, String s3Key, byte[] data, boolean overwrite,
            Map<String, String> tags)
            throws S3ObjectAlreadyExistsException, S3ClientSystemException {
        if (data == null) {
            throw new IllegalArgumentException(
                    String.format("Upload data must not be null. bucket name: %s, S3 key: %s",
                            bucketName, s3Key));
        }
        InputStream input = new ByteArrayInputStream(data);
        return upload(bucketName, s3Key, input, data.length, overwrite, tags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String upload(String bucketName, String s3Key, byte[] data, boolean overwrite)
            throws S3ObjectAlreadyExistsException, S3ClientSystemException {
        return upload(bucketName, s3Key, data, overwrite, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String upload(String bucketName, String s3Key, byte[] data)
            throws S3ClientSystemException {

        if (StringUtils.isAnyEmpty(bucketName, s3Key)) {
            throw new IllegalArgumentException(String.format(
                    "Bucket name and S3 key must not be empty. bucket name: %s, S3 key: %s",
                    bucketName, s3Key));
        }
        
        if (data == null) {
            throw new IllegalArgumentException(
                    String.format("Upload data must not be null. bucket name: %s, S3 key: %s",
                            bucketName, s3Key));
        }
        
        long size = data.length;
        InputStream input = new ByteArrayInputStream(data);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        objectMetadata.setContentLength(size);
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, s3Key, input, objectMetadata);

        try {
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            return uploadResult.getETag();
        } catch (SdkClientException | InterruptedException e) {
            throw new S3ClientSystemException("Error has occurred during upload.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] downloadAsByteArray(String bucketName, String s3Key)
            throws S3ObjectNotFoundException, S3ClientSystemException {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            try (InputStream inputStream = downloadAsInputStream(bucketName, s3Key)) {
                IOUtils.copy(inputStream, buffer);
            }
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new S3ClientSystemException("Error has occurred during download.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream downloadAsInputStream(String bucketName, String s3Key)
            throws S3ObjectNotFoundException, S3ClientSystemException {

        if (StringUtils.isAnyEmpty(bucketName, s3Key)) {
            throw new IllegalArgumentException(String.format(
                    "Bucket name and S3 key must not be empty. bucket name: %s, S3 key: %s",
                    bucketName, s3Key));
        }
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, s3Key);
        try {
            S3Object s3Object = amazonS3.getObject(getObjectRequest);
            if (s3Object == null) {
                throw new S3ObjectNotFoundException(String.format(
                        "S3 object not found. bucket name: %s, S3 key: %s", bucketName, s3Key));
            }

            return s3Object.getObjectContent();
        } catch (SdkClientException e) {
            throw new S3ClientSystemException("Error has occurred during download.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String bucketName, String s3Key) throws S3ClientSystemException {

        if (StringUtils.isAnyEmpty(bucketName, s3Key)) {
            throw new IllegalArgumentException(String.format(
                    "Bucket name and S3 key must not be empty. bucket name: %s, S3 key: %s",
                    bucketName, s3Key));
        }

        try {
            amazonS3.deleteObject(bucketName, s3Key);
        } catch (SdkClientException e) {
            throw new S3ClientSystemException("Error has occurred during delete.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMultiObjects(String bucketName, String... s3Keys)
            throws S3ClientSystemException, S3MultiObjectDeleteException {

        if (StringUtils.isEmpty(bucketName)) {
            throw new IllegalArgumentException(
                    String.format("Bucket name must not be empty. bucket name: %s", bucketName));
        }

        if (ArrayUtils.isEmpty(s3Keys)) {
            return;
        }

        if (StringUtils.isAnyEmpty(s3Keys)) {
            throw new IllegalArgumentException(String.format(
                    "S3 key must not be empty. bucket name: %s, S3 keys: %s",
                    bucketName, Arrays.asList(s3Keys)));
        }

        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);

        try {
            amazonS3.deleteObjects(deleteObjectsRequest.withKeys(s3Keys));
        } catch (MultiObjectDeleteException e) {
            throw new S3MultiObjectDeleteException("Error has occurred during delete.", e);
        } catch (SdkClientException e) {
            throw new S3ClientSystemException("Error has occurred during delete.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(String bucketName, String sourceS3Key, String destinationS3Key)
            throws S3ObjectNotFoundException, S3ClientSystemException {

        if (StringUtils.isAnyEmpty(bucketName, sourceS3Key, destinationS3Key)) {
            throw new IllegalArgumentException(String.format(
                    "Bucket name and S3 key must not be empty. bucket name: %s, source S3 key: %s, destination S3 key: %s",
                    bucketName, sourceS3Key, destinationS3Key));
        }
        moveToOtherBucket(bucketName, sourceS3Key, bucketName, destinationS3Key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(String bucketName, String sourceS3Key, String destinationS3Key,
            boolean overwrite) throws S3ObjectNotFoundException, S3ObjectAlreadyExistsException,
            S3ClientSystemException {

        if (StringUtils.isAnyEmpty(bucketName, sourceS3Key, destinationS3Key)) {
            throw new IllegalArgumentException(String.format(
                    "Bucket name and S3 key must not be empty. bucket name: %s, source S3 key: %s, destination S3 key: %s",
                    bucketName, sourceS3Key, destinationS3Key));
        }
        moveToOtherBucket(bucketName, sourceS3Key, bucketName, destinationS3Key, overwrite);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveToOtherBucket(String sourceBucketName, String sourceS3Key,
            String destinationBucketName, String destinationS3Key)
            throws S3ObjectNotFoundException, S3ClientSystemException {

        if (StringUtils.isAnyEmpty(sourceBucketName, sourceS3Key, destinationBucketName,
                destinationS3Key)) {
            throw new IllegalArgumentException(String.format(
                    "Bucket name and S3 key must not be empty. source bucket name: %s, source S3 key: %s, destination bucket name: %s, destination S3 key: %s",
                    sourceBucketName, sourceS3Key, destinationBucketName, destinationS3Key));
        }

        if (!doesObjectExists(sourceBucketName, sourceS3Key)) {
            throw new S3ObjectNotFoundException(
                    String.format("Source S3 object not found.  bucket name: %s, S3 key: %s",
                            sourceBucketName, sourceS3Key));
        }

        try {
            AccessControlList accessControlList =
                    amazonS3.getObjectAcl(sourceBucketName, sourceS3Key);
            ObjectMetadata objectMetadata =
                    amazonS3.getObjectMetadata(sourceBucketName, sourceS3Key);
            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(sourceBucketName,
                    sourceS3Key, destinationBucketName, destinationS3Key);
            copyObjectRequest.setAccessControlList(accessControlList);
            copyObjectRequest.setNewObjectMetadata(objectMetadata);

            amazonS3.copyObject(copyObjectRequest);
            amazonS3.deleteObject(sourceBucketName, sourceS3Key);
        } catch (SdkClientException e) {
            throw new S3ClientSystemException("Error has occurred during move file.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveToOtherBucket(String sourceBucketName, String sourceS3Key,
            String destinationBucketName, String destinationS3Key, boolean overwrite)
            throws S3ObjectNotFoundException, S3ObjectAlreadyExistsException,
            S3ClientSystemException {

        if (StringUtils.isAnyEmpty(sourceBucketName, sourceS3Key, destinationBucketName,
                destinationS3Key)) {
            throw new IllegalArgumentException(String.format(
                    "Bucket name and S3 key must not be empty. source bucket name: %s, source S3 key: %s, destination bucket name: %s, destination S3 key: %s",
                    sourceBucketName, sourceS3Key, destinationBucketName, destinationS3Key));
        }

        if (!doesObjectExists(sourceBucketName, sourceS3Key)) {
            throw new S3ObjectNotFoundException(
                    String.format("Source S3 object not found.  bucket name: %s, S3 key: %s",
                            sourceBucketName, sourceS3Key));
        }

        if (!overwrite && doesObjectExists(destinationBucketName, destinationS3Key)) {
            throw new S3ObjectAlreadyExistsException(String.format(
                    "Destination S3 object already exists.  bucket name: %s, S3 key: %s",
                    destinationBucketName, destinationS3Key));
        }

        try {
            AccessControlList accessControlList =
                    amazonS3.getObjectAcl(sourceBucketName, sourceS3Key);
            ObjectMetadata objectMetadata =
                    amazonS3.getObjectMetadata(sourceBucketName, sourceS3Key);
            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(sourceBucketName,
                    sourceS3Key, destinationBucketName, destinationS3Key);
            copyObjectRequest.setAccessControlList(accessControlList);
            copyObjectRequest.setNewObjectMetadata(objectMetadata);

            amazonS3.copyObject(copyObjectRequest);
            amazonS3.deleteObject(sourceBucketName, sourceS3Key);
        } catch (SdkClientException e) {
            throw new S3ClientSystemException("Error has occurred during move file.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generatePreSignedUrl(String bucketName, String s3Key, long expirationSeconds)
            throws S3ClientSystemException {

        if (StringUtils.isAnyEmpty(bucketName, s3Key)) {
            throw new IllegalArgumentException(String.format(
                    "Bucket name and S3 key must not be empty. bucket name: %s, S3 key: %s",
                    bucketName, s3Key));
        }

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, s3Key)
                .withExpiration(new Date(System.currentTimeMillis() + 1000 * expirationSeconds));
        try {
            URL url = amazonS3.generatePresignedUrl(request);
            return url.toString();
        } catch (SdkClientException e) {
            throw new S3ClientSystemException("Error has occurred during generate pre-signed URL.",
                    e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generatePreSignedUrl(String bucketName, String s3Key)
            throws S3ClientSystemException {

        return generatePreSignedUrl(bucketName, s3Key, defaultExpirationSeconds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<S3ObjectSummary> searchObjects(String bucketName, String prefix)
            throws S3ClientSystemException {

        if (StringUtils.isEmpty(bucketName)) {
            throw new IllegalArgumentException(
                    String.format("Bucket name must not be empty. bucket name: %s", bucketName));
        }

        List<S3ObjectSummary> s3ObjectSummaries = new ArrayList<>();

        String marker = null;
        boolean isTruncated = true;

        try {
            while (isTruncated) {
                ListObjectsRequest listObjectsRequest =
                        new ListObjectsRequest(bucketName, prefix, marker, AWS_S3_DELIMITER, null);
                ObjectListing objectListing = amazonS3.listObjects(listObjectsRequest);

                List<S3ObjectSummary> summaries = objectListing.getObjectSummaries().stream()
                                .map(s3ObjectSummary -> new S3ObjectSummary(
                                                s3ObjectSummary.getKey(),
                                                s3ObjectSummary.getSize()))
                                .collect(Collectors.toList());
                s3ObjectSummaries.addAll(summaries);

                isTruncated = objectListing.isTruncated();
                marker = objectListing.getNextMarker();
            }
        } catch (SdkClientException e) {
            throw new S3ClientSystemException("Error has occurred during search files.", e);
        }

        return s3ObjectSummaries;
    }

}
