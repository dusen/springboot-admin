/**
 * @(#)AwsS3Utility.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util.aws;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.DeleteObjectsResult.DeletedObject;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.MultiObjectDeleteException.DeleteError;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.Tag;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.ObjectMetadataProvider;
import com.amazonaws.services.s3.transfer.ObjectTaggingProvider;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.amazonaws.util.IOUtils;

/**
 * Common component <br>
 * AWS S3 file utility class.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Lazy
public class AwsS3Utility {

    /** The delimiter for condensing common prefixes in the returned listing results. */
    public static final String AWS_S3_DELIMITER = "/";

    /** Provides the client for accessing the Amazon S3 web service. */
    @Autowired
    private AmazonS3 awsS3Client;

    /** High level utility for managing transfers to Amazon S3. */
    @Autowired
    private TransferManager transferManager;

    /**
     * Configuration properties.
     */
    @Autowired
    private AwsS3Properties awsS3Properties;

    /**
     * Check the specified AWS S3 key whether it is exists.
     * 
     * @param bucketName AWS S3 bucket name
     * @param s3Key AWS S3 key
     * @return true when exist | false when not exist
     * @throws AwsS3SdkException If any errors occurred in Amazon SDK.
     * @throws AwsS3InvalidParameterException if any errors occurred in checking parameters.
     */
    public boolean fileExists(String bucketName, String s3Key)
            throws AwsS3SdkException, AwsS3InvalidParameterException {

        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(s3Key)) {
            throw new AwsS3InvalidParameterException(
                    "S3 upload Error - bucketName, s3Key, inputData must be non-empty.");
        }

        try {
            return awsS3Client.doesObjectExist(bucketName, s3Key);
        } catch (SdkClientException exception) {
            throw new AwsS3SdkException("S3 check file error - AWS SDK Exception.", exception);
        }
    }

    /**
     * Upload an input stream data to AWS S3 storage (be care for the stream contents size before
     * upload, the stream contents will be buffered in memory and could result in out of memory
     * errors if it is vary large data).
     * 
     * @param bucketName AWS S3 bucket name.
     * @param s3Key File name to save.
     * @param inputData Input stream data.
     * @param overwriteFlag Whether overwrite when file existing.
     * @param tags Put object request tags.
     * @return MD5 of uploaded content.
     * @throws AwsS3InvalidParameterException If any errors occurred in checking parameters.
     * @throws AwsS3FileAlreadyExistsException If the file to be uploaded already exists.
     * @throws AwsS3SdkException If any errors occurred in Amazon SDK.
     */
    @Deprecated
    public String upload(String bucketName, String s3Key, InputStream inputData,
            boolean overwriteFlag, Map<String, String> tags) throws AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3SdkException {

        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(s3Key) || inputData == null) {
            throw new AwsS3InvalidParameterException(
                    "S3 upload Error - bucketName, s3Key, inputData must be non-empty.");
        }

        if (!overwriteFlag && fileExists(bucketName, s3Key)) {
            throw new AwsS3FileAlreadyExistsException("S3 upload Error - file already exists.");
        }

        try {

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3Key, inputData, objectMetadata);

            if (MapUtils.isNotEmpty(tags)) {
                List<Tag> s3ObjectTags = tags.entrySet()
                        .stream()
                        .map(tag -> new Tag(tag.getKey(), tag.getValue()))
                        .collect(Collectors.toList());

                putObjectRequest.withTagging(new ObjectTagging(s3ObjectTags));
            }

            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();

            return uploadResult.getETag();
        } catch (SdkClientException | InterruptedException exception) {
            throw new AwsS3SdkException("S3 upload Error - AWS SDK Exception.", exception);
        }
    }

    /**
     * Upload an input stream data to AWS S3 storage (be care for the stream contents size before
     * upload, the stream contents will be buffered in memory and could result in out of memory
     * errors if it is vary large data).
     * 
     * @param bucketName AWS S3 bucket name.
     * @param s3Key File name to save.
     * @param inputData Input stream data.
     * @param overwriteFlag Whether overwrite when file existing.
     * @param tags Put object request tags.
     * @param fileSize File input stream data size.
     * @return MD5 of uploaded content.
     * @throws AwsS3InvalidParameterException If any errors occurred in checking parameters.
     * @throws AwsS3FileAlreadyExistsException If the file to be uploaded already exists.
     * @throws AwsS3SdkException If any errors occurred in Amazon SDK.
     */
    public String upload(String bucketName, String s3Key, InputStream inputData,
            boolean overwriteFlag, Map<String, String> tags, long fileSize)
            throws AwsS3InvalidParameterException, AwsS3FileAlreadyExistsException,
            AwsS3SdkException {

        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(s3Key) || inputData == null) {
            throw new AwsS3InvalidParameterException(
                    "S3 upload Error - bucketName, s3Key, inputData must be non-empty.");
        }

        if (!overwriteFlag && fileExists(bucketName, s3Key)) {
            throw new AwsS3FileAlreadyExistsException("S3 upload Error - file already exists.");
        }

        try {

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
            objectMetadata.setContentLength(fileSize);
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3Key, inputData, objectMetadata);

            if (MapUtils.isNotEmpty(tags)) {
                List<Tag> s3ObjectTags = tags.entrySet()
                        .stream()
                        .map(tag -> new Tag(tag.getKey(), tag.getValue()))
                        .collect(Collectors.toList());

                putObjectRequest.withTagging(new ObjectTagging(s3ObjectTags));
            }

            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();

            return uploadResult.getETag();
        } catch (SdkClientException | InterruptedException exception) {
            throw new AwsS3SdkException("S3 upload Error - AWS SDK Exception.", exception);
        }
    }

    /**
     * upload a file to AWS S3 storage.
     * 
     * @param bucketName AWS S3 bucket name.
     * @param s3Key File name to save.
     * @param inputFile The specified upload file.
     * @param overwriteFlag Whether overwrite when file existing.
     * @param tags Put object request tags.
     * @return MD5 of uploaded content.
     * @throws AwsS3InvalidParameterException If any errors occurred in checking parameters.
     * @throws AwsS3FileNotFoundException If the file to be uploaded does not exist.
     * @throws AwsS3FileAlreadyExistsException Already exists upload file.
     * @throws AwsS3SdkException If any errors occurred in Amazon SDK.
     * @throws IOException Error in reading file.
     */
    public String uploadFile(String bucketName, String s3Key, File inputFile, boolean overwriteFlag,
            Map<String, String> tags)

            throws AwsS3InvalidParameterException, AwsS3FileNotFoundException,
            AwsS3FileAlreadyExistsException, AwsS3SdkException, IOException {

        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(s3Key) || inputFile == null) {
            throw new AwsS3InvalidParameterException(
                    "S3 upload Error - bucketName, s3Key, inputFile must be non-empty.");
        }

        if (!overwriteFlag && fileExists(bucketName, s3Key)) {
            throw new AwsS3FileAlreadyExistsException("S3 upload Error - file already exists.");
        }

        try (FileInputStream uploadObject = new FileInputStream(inputFile)) {
            return upload(bucketName, s3Key, uploadObject, overwriteFlag, tags, inputFile.length());
        } catch (FileNotFoundException e) {
            throw new AwsS3FileNotFoundException("S3 upload Error - file dose not exist.");
        }
    }

    /**
     * upload file list to AWS S3 storage.
     * 
     * @param bucketName AWS S3 bucket name.
     * @param virtualDirectoryKeyPrefix The key prefix of the virtual directory to upload to. <br>
     *        Use the null or empty string to upload files to the root of the bucket.
     * @param commonDirectory The common parent directory of files to upload.
     * @param fileList Files to upload.
     * @param tags Put object request tags.
     * @return etag list.
     * @throws AwsS3InvalidParameterException if any errors occurred in checking parameters.
     * @throws AwsS3SdkException if any errors occurred in Amazon SDK.
     */
    public List<MultiUploadResult> uploadFiles(String bucketName, String virtualDirectoryKeyPrefix,
            File commonDirectory, List<File> fileList, Map<String, String> tags)
            throws AwsS3InvalidParameterException, AwsS3SdkException {

        if (StringUtils.isEmpty(bucketName)) {
            throw new AwsS3InvalidParameterException(
                    "S3 upload Error - bucketName must be non-empty.");
        }

        if (commonDirectory == null || !commonDirectory.exists()
                || !commonDirectory.isDirectory()) {
            throw new AwsS3InvalidParameterException(
                    "S3 upload Error - Must provide a common base directory for uploaded files");
        }

        if (fileList == null || fileList.isEmpty()) {
            return Collections.emptyList();
        }

        ObjectMetadataProvider metadataProvider = (file, metadata) -> {
            metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        };
        ObjectTaggingProvider taggingProvider = null;
        if (MapUtils.isNotEmpty(tags)) {
            taggingProvider = uploadContext -> new ObjectTagging(tags.entrySet()
                    .stream()
                    .map(tag -> new Tag(tag.getKey(), tag.getValue()))
                    .collect(Collectors.toList()));
        }
        MultipleFileUpload multiUpload =
                transferManager.uploadFileList(bucketName, virtualDirectoryKeyPrefix,
                        commonDirectory, fileList, metadataProvider, taggingProvider);

        return multiUpload.getSubTransfers().stream().map(upload -> {
            try {
                UploadResult uploadResult = upload.waitForUploadResult();
                return new MultiUploadResult(uploadResult.getKey(), uploadResult.getETag(), null);

            } catch (AmazonClientException | InterruptedException e) {
                return new MultiUploadResult(null, null,
                        new AwsS3SdkException("S3 upload Error - AWS SDK Exception.", e));
            }
        }).collect(Collectors.toList());
    }

    /**
     * download an AWS S3 file, return a input stream data.
     * 
     * @param bucketName AWS S3 bucket name.
     * @param s3Key The specified S3 key.
     * @return {@link ByteArrayOutputStream}
     * @throws AwsS3InvalidParameterException if any errors occurred in checking parameters.
     * @throws AwsS3FileNotFoundException if the file to be downloaed does not exist.
     * @throws AwsS3SdkException If any errors occurred in Amazon SDK.
     */
    public ByteArrayOutputStream download(String bucketName, String s3Key)
            throws AwsS3InvalidParameterException, AwsS3FileNotFoundException, AwsS3SdkException {

        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(s3Key)) {
            throw new AwsS3InvalidParameterException(
                    "S3 download Error - bucketName, s3Key must be non-empty.");
        }

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, s3Key);
        try {

            S3Object s3Object = awsS3Client.getObject(getObjectRequest);

            if (s3Object == null) {
                throw new AwsS3FileNotFoundException(
                        "S3 download Error - download file is not found.");
            }

            ByteArrayOutputStream outputSream = new ByteArrayOutputStream();
            try (S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent()) {
                IOUtils.copy(s3ObjectInputStream, outputSream);
            }

            return outputSream;
        } catch (SdkClientException | IOException exception) {
            throw new AwsS3SdkException("S3 download Error - AWS SDK Exception.", exception);
        }
    }

    /**
     * delete an AWS S3 file from S3 storage.
     * 
     * @param bucketName AWS S3 bucket name.
     * @param s3Keys The specified S3 key args list.
     * @return Deleted keys.
     * @throws AwsS3InvalidParameterException if any errors occurred in checking parameters.
     * @throws AwsS3SdkException If any errors occurred in Amazon SDK.
     */
    public MultiDeleteResult deleteFile(String bucketName, String... s3Keys)
            throws AwsS3InvalidParameterException, AwsS3SdkException {

        if (StringUtils.isEmpty(bucketName)) {
            throw new AwsS3InvalidParameterException(
                    "S3 delete Error - bucketName must be non-empty.");
        }

        if (s3Keys.length == 0) {
            return new MultiDeleteResult(Collections.emptyMap(), Collections.emptyList());
        }

        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);

        try {
            DeleteObjectsResult deleteObjectsResult =
                    awsS3Client.deleteObjects(deleteObjectsRequest.withKeys(s3Keys));

            return new MultiDeleteResult(Collections.emptyMap(),
                    deleteObjectsResult.getDeletedObjects()
                            .stream()
                            .map(DeletedObject::getKey)
                            .collect(Collectors.toList()));
        } catch (SdkClientException exception) {
            if (exception instanceof MultiObjectDeleteException) {
                return new MultiDeleteResult(
                        ((MultiObjectDeleteException) exception).getErrors().stream().collect(
                                Collectors.toMap(DeleteError::getKey, DeleteError::getMessage)),
                        ((MultiObjectDeleteException) exception).getDeletedObjects()
                                .stream()
                                .map(DeletedObject::getKey)
                                .collect(Collectors.toList()));
            }
            throw new AwsS3SdkException("S3 delete file Error - AWS SDK Exception.", exception);
        }
    }

    /**
     * Search files from AWS S3 storage.
     * 
     * @param bucketName AWS S3 bucket name.
     * @param prefix The specified prefix.("foo/")<br>
     *        If it is null, files in root of bucket wille be returned.
     * @param nextMarker The key marker indicating where listing results should begin (null when
     *        first time).
     * @return <@link S3SearchResult>
     * @throws AwsS3InvalidParameterException if any errors occurred in checking parameters.
     * @throws AwsS3SdkException If any errors occurred in Amazon SDK.
     */
    public S3SearchResult searchFiles(String bucketName, String prefix, String nextMarker)
            throws AwsS3InvalidParameterException, AwsS3SdkException {

        if (StringUtils.isEmpty(bucketName)) {
            throw new AwsS3InvalidParameterException(
                    "S3 search file Error - bucketName, prefix must be non-empty.");
        }

        try {
            ListObjectsRequest listObjectsRequest =
                    new ListObjectsRequest(bucketName, prefix, nextMarker, AWS_S3_DELIMITER, null);
            ObjectListing objectListing = awsS3Client.listObjects(listObjectsRequest);

            List<S3FileSummary> s3FileSummaries =
                    objectListing.getObjectSummaries().stream().map(s3ObjectSummary -> {
                        return new S3FileSummary(s3ObjectSummary.getKey(),
                                s3ObjectSummary.getSize());
                    }).collect(Collectors.toList());

            S3SearchResult s3SearchResult = new S3SearchResult();
            s3SearchResult.setBucketName(bucketName);
            s3SearchResult.setS3FileNameResults(s3FileSummaries);
            s3SearchResult.setTruncated(objectListing.isTruncated());
            if (objectListing.isTruncated()) {
                s3SearchResult.setNextMarker(objectListing.getNextMarker());
            }
            return s3SearchResult;
        } catch (SdkClientException exception) {
            throw new AwsS3SdkException("S3 search file Error - AWS SDK Exception.", exception);
        }
    }

    /**
     * Generates a pre-signed URL for downloading file.
     * 
     * @param bucketName Bucket name.
     * @param key S3 key.
     * @return A pre-signed URL.
     * @throws AwsS3SdkException Exception happened in generating URL.
     */
    public String generatePreSignedUrl(String bucketName, String key) throws AwsS3SdkException {

        try {

            Date expiration = new Date();
            long milliSeconds = expiration.getTime();
            milliSeconds += 1000 * awsS3Properties.getUrlExpiration();
            expiration.setTime(milliSeconds);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, key);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            generatePresignedUrlRequest.setExpiration(expiration);

            URL url = awsS3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();
        } catch (SdkClientException exception) {
            throw new AwsS3SdkException("S3 search file Error - AWS SDK Exception.", exception);
        }
    }
}
