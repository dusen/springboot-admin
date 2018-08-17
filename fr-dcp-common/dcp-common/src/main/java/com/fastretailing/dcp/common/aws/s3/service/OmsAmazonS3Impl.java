/**
 * @(#)OmsAmazonS3Impl.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.Md5Utils;
import com.fastretailing.dcp.common.aws.s3.entity.DownloadResult;
import com.fastretailing.dcp.common.aws.s3.entity.UploadResult;
import com.fastretailing.dcp.common.exception.S3BucketNotFoundException;
import com.fastretailing.dcp.common.exception.S3FileNotFoundException;
import com.fastretailing.dcp.common.exception.S3SystemException;
import com.fastretailing.dcp.common.threadlocal.BrandAndRegionHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
/**
 * OMS AmazonS3' service interface's implement class.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
@Component
public class OmsAmazonS3Impl implements OmsAmazonS3 {

    /**
     * amazon s3 client.
     */
    @Autowired(required = false)
    private AmazonS3 client;

    /**
     * bucket name.
     */
    @Value("${cloud.aws.bucket.name:#{null}}")
    private String defaultBucketName;

    /**
     * amazon s3 expiration(unit: seconds).<br>
     */
    @Value("${cloud.aws.expiration:#{null}}")
    private Long expiration;

    /**
     * Brand replacement identifier
     */
    private static final String BRAND_IDENTIFIER = "\\{brand\\}";

    /**
     * Region replacement identifier
     */
    private static final String REGION_IDENTIFIER = "\\{region\\}";

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
    @Override
    public UploadResult upload(
            String bucketName, String fileKey, File uploadFile
    ) throws S3BucketNotFoundException, S3SystemException {

        assert StringUtils.isNotBlank(bucketName)   : "[bucketName] must be not null.";
        assert StringUtils.isNotBlank(fileKey)      : "[fileKey] must be not null.";
        assert Objects.nonNull(uploadFile)          : "[uploadFile] must be not null.";

        String newFileKey = convertFileKey(fileKey);

        try {

            this.thrownExceptionIfBucketIsNotExists(bucketName);

            Date operateAt = new Date();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentMD5(Md5Utils.md5AsBase64(uploadFile));
            metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
            metadata.addUserMetadata("md5base64", metadata.getContentMD5());
            metadata.addUserMetadata(Headers.DATE, operateAt.toString());
            metadata.setLastModified(operateAt);

            PutObjectRequest request = new PutObjectRequest(bucketName, newFileKey, uploadFile)
                    .withMetadata(metadata);

            PutObjectResult result = this.client.putObject(request);

            return new UploadResult(result, bucketName, newFileKey);

        } catch (SdkClientException | IOException e) {
            log.error("AmazonS3 upload error.", e);
            throw new S3SystemException();
        }

    }

    /**
     * Uploads file to the AmazonS3 bucket.<br>
     *
     * @param fileKey file's identifier
     * @param uploadFile upload file's instance
     *
     * @return AmazonS3 upload's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    @Override
    public UploadResult upload(
            String fileKey, File uploadFile
    ) throws S3BucketNotFoundException, S3SystemException {

        return this.upload(this.defaultBucketName, fileKey, uploadFile);
    }

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
    @Override
    public UploadResult upload(
            String bucketName, String fileKey, InputStream uploadInputStream
    ) throws S3BucketNotFoundException, S3SystemException {

        assert StringUtils.isNotBlank(bucketName)   : "[bucketName] must be not null.";
        assert StringUtils.isNotBlank(fileKey)      : "[fileKey] must be not null.";
        assert Objects.nonNull(uploadInputStream)   : "[uploadInputStream] must be not null.";

        String newFileKey = convertFileKey(fileKey);

        try {

            this.thrownExceptionIfBucketIsNotExists(bucketName);

            Date operateAt = new Date();

            byte[] bytes = IOUtils.toByteArray(uploadInputStream);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentMD5(Md5Utils.md5AsBase64(bytes));
            metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
            metadata.addUserMetadata("md5base64", metadata.getContentMD5());
            metadata.addUserMetadata(Headers.DATE, operateAt.toString());
            metadata.setLastModified(operateAt);

            PutObjectRequest request = new PutObjectRequest(
                    bucketName, newFileKey, new ByteArrayInputStream(bytes), metadata
            );

            PutObjectResult result = this.client.putObject(request);

            return new UploadResult(result, bucketName, newFileKey);

        } catch (SdkClientException | IOException e) {
            log.error("AmazonS3 upload error.", e);
            throw new S3SystemException();
        }

    }

    /**
     * Uploads file to the AmazonS3 bucket.<br>
     *
     * @param fileKey file's identifier
     * @param uploadInputStream upload InputStream's instance
     *
     * @return AmazonS3 upload's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    @Override
    public UploadResult upload(
            String fileKey, InputStream uploadInputStream
    ) throws S3BucketNotFoundException, S3SystemException {

        return this.upload(this.defaultBucketName, fileKey, uploadInputStream);
    }

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
    @Override
    public UploadResult upload(
            String bucketName, String fileKey, String content
    ) throws S3BucketNotFoundException, S3SystemException {

        assert StringUtils.isNotBlank(bucketName)   : "[bucketName] must be not null.";
        assert StringUtils.isNotBlank(fileKey)      : "[fileKey] must be not null.";
        assert StringUtils.isNotBlank(content)      : "[content] must be not null.";

        return this.upload(this.defaultBucketName, fileKey, new ByteArrayInputStream(
                content.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Uploads file to the AmazonS3 bucket.<br>
     *
     * @param fileKey file's identifier
     * @param content content
     *
     * @return AmazonS3 upload's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    @Override
    public UploadResult upload(
            String fileKey, String content
    ) throws S3BucketNotFoundException, S3SystemException {

        return this.upload(this.defaultBucketName, fileKey, content);
    }


    /**
     * Download file form AmazonS3 bucket.<br>
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
    @Override
    public DownloadResult download(
            String bucketName, String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException {

        assert StringUtils.isNotBlank(bucketName)   : "[bucketName] must be not null.";
        assert StringUtils.isNotBlank(fileKey)      : "[fileKey] must be not null.";

        String newFileKey = convertFileKey(fileKey);

        try {

            this.thrownExceptionIfBucketIsNotExists(bucketName);
            this.thrownExceptionIfFileIsNotExists(bucketName, newFileKey);

            File resultFile = File.createTempFile(newFileKey, StringUtils.EMPTY);
            FileUtils.copyToFile(
                    this.client.getObject(bucketName, newFileKey).getObjectContent(),
                    resultFile
            );

            return new DownloadResult(resultFile, bucketName, newFileKey);

        } catch (SdkClientException | IOException e) {
            log.error("AmazonS3 download error.", e);
            throw new S3SystemException();
        }

    }

    /**
     * Download file form AmazonS3 bucket.<br>
     *
     * @param fileKey file's identifier
     *
     * @return AmazonS3 download's output entity.
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException AmazonS3 operate exception
     */
    @Override
    public DownloadResult download(
            String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException {

        return this.download(this.defaultBucketName, fileKey);

    }

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
     * @throws S3SystemException amazonS3 operate exception
     */
    @Override
    public boolean move(
            String srcBucketName, String srcFileKey, String destBucketName, String destFileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException {

        assert StringUtils.isNotBlank(srcBucketName)  : "[srcBucketName] must be not null.";
        assert StringUtils.isNotBlank(srcFileKey)     : "[srcFileKey] must be not null.";
        assert StringUtils.isNotBlank(destBucketName) : "[destBucketName] must be not null.";
        assert StringUtils.isNotBlank(destFileKey)    : "[destFileKey] must be not null.";

        String newSrcFileKey = convertFileKey(srcFileKey);

        try {

            this.thrownExceptionIfBucketIsNotExists(srcBucketName);
            this.thrownExceptionIfFileIsNotExists(srcBucketName, newSrcFileKey);
            this.thrownExceptionIfBucketIsNotExists(destBucketName);

            ObjectMetadata srcMetadata = this.client.getObject(
                    srcBucketName, newSrcFileKey
            ).getObjectMetadata();

            CopyObjectRequest copyRequest = new CopyObjectRequest(
                    srcBucketName, newSrcFileKey, destBucketName, destFileKey
            ).withNewObjectMetadata(srcMetadata);

            this.client.copyObject(copyRequest);
            this.client.deleteObject(srcBucketName, newSrcFileKey);

        } catch (SdkClientException e) {
            log.error("AmazonS3 move error.", e);
            throw new S3SystemException();
        }

        return true;
    }

    /**
     * Move and rename file to destination bucket and destination key.<br>
     *
     * @param srcFileKey source file's identifier
     * @param destBucketName destination bucket's name
     * @param destFileKey destination file's identifier
     *
     * @return if true that move success
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException amazonS3 operate exception
     */
    @Override
    public boolean move(
            String srcFileKey, String destBucketName, String destFileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException {

        return this.move(this.defaultBucketName, srcFileKey, destBucketName, destFileKey);
    }

    /**
     * Move and rename file to destination key.<br>
     *
     * @param srcFileKey source file's identifier
     * @param destFileKey destination file's identifier
     *
     * @return if true that move success
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException amazonS3 operate exception
     */
    @Override
    public boolean move(
            String srcFileKey, String destFileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException {

        return this.move(this.defaultBucketName, srcFileKey, this.defaultBucketName, destFileKey);
    }

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
     * @throws S3SystemException amazonS3 operate exception
     */
    @Override
    public boolean delete(
            String bucketName, String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException {

        assert StringUtils.isNotBlank(bucketName)  : "[bucketName] must be not null.";
        assert StringUtils.isNotBlank(fileKey)     : "[fileKey] must be not null.";

        String newFileKey = convertFileKey(fileKey);

        try {

            this.thrownExceptionIfBucketIsNotExists(bucketName);
            this.thrownExceptionIfFileIsNotExists(bucketName, newFileKey);

            this.client.deleteObject(bucketName, newFileKey);

        } catch (SdkClientException e) {
            log.error("AmazonS3 delete error.", e);
            throw new S3SystemException();
        }

        return true;
    }

    /**
     * Deletes file in the AmazonS3 bucket.
     *
     * @param fileKey file's identifier
     *
     * @return if true that delete success
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException amazonS3 operate exception
     */
    @Override
    public boolean delete(
            String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException {

        return this.delete(this.defaultBucketName, fileKey);
    }

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
     * @throws S3SystemException amazonS3 operate exception
     */
    @Override
    public URL getPreSignedUrl(
            String bucketName, String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException {

        assert StringUtils.isNotBlank(bucketName)  : "[bucketName] must be not null.";
        assert StringUtils.isNotBlank(fileKey)     : "[fileKey] must be not null.";

        String newFileKey= convertFileKey(fileKey);

        try {

            this.thrownExceptionIfBucketIsNotExists(bucketName);
            this.thrownExceptionIfFileIsNotExists(bucketName, newFileKey);

            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(
                    bucketName, newFileKey
            );

            if (Objects.nonNull(expiration)) {
                Date operateAt = new Date();
                Date expirationAt = new Date(operateAt.getTime() + expiration.longValue() * 1000);
                request.setExpiration(expirationAt);
            }

            return client.generatePresignedUrl(request);

        } catch (SdkClientException e) {
            log.error("AmazonS3 generate pre-signed URL error.", e);
            throw new S3SystemException();
        }

    }

    /**
     * Returns a pre-signed URL for accessing an Amazon S3 resource.
     *
     * @param fileKey file's identifier
     *
     * @return pre-signed URL instance
     *
     * @throws S3BucketNotFoundException AmazonS3's bucket does not found
     * @throws S3FileNotFoundException AmazonS3's bucket's file does not found
     * @throws S3SystemException amazonS3 operate exception
     */
    @Override
    public URL getPreSignedUrl(
            String fileKey
    ) throws S3BucketNotFoundException, S3FileNotFoundException, S3SystemException {

        return this.getPreSignedUrl(this.defaultBucketName, fileKey);

    }

    /**
     * If the bucket is not exists that throw S3BucketNotFoundException.<br>
     *
     * @param bucketName bucket's name
     *
     * @throws S3BucketNotFoundException S3BucketNotFoundException
     */
    private void thrownExceptionIfBucketIsNotExists(
            String bucketName
    ) throws S3BucketNotFoundException {

        if (!this.client.doesBucketExistV2(bucketName)) {
            log.error("AmazonS3 bucket does not found. Bucket Name : {}", bucketName);
            throw new S3BucketNotFoundException();
        }

    }

    /**
     * If the bucket's file is not exists that throw S3FileNotFoundException.<br>
     *
     * @param bucketName bucket's name
     * @param fileKey file's identifier
     *
     * @throws S3FileNotFoundException S3FileNotFoundException
     */
    private void thrownExceptionIfFileIsNotExists(
            String bucketName, String fileKey
    ) throws S3FileNotFoundException {

        if (!this.client.doesObjectExist(bucketName, fileKey)) {
            log.error("AmazonS3 bucket's file does not found. BucketName : {}, File Key : {}",
                    bucketName, fileKey
            );
            throw new S3FileNotFoundException();
        }

    }

    /**
     * <pre>
     * Replace {brand} and {region} in fileKey
     *
     * example:
     *     1. {brand}/{region}/xx/test.txt ⇒ uq/ca/xx/test.txt
     *     2. null ⇒ null
     *     3. "" ⇒ ""
     *</pre>
     *
     * @param fileKey convert target
     * @return Replaced fileKey
     */
    private String convertFileKey(String fileKey) {

        if (StringUtils.isEmpty(fileKey)) {
            return fileKey;
        }

        return fileKey.replaceAll(BRAND_IDENTIFIER, BrandAndRegionHolder.getBrand())
                .replaceAll(REGION_IDENTIFIER, BrandAndRegionHolder.getRegion());
    }

}
