/**
 * @(#)AwsS3UtilityTest.java
 *
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util.aws;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.DeleteObjectsResult.DeletedObject;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.MultiObjectDeleteException.DeleteError;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.ObjectMetadataProvider;
import com.amazonaws.services.s3.transfer.ObjectTaggingProvider;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.internal.MultipleFileUploadImpl;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.fastretailing.dcp.storecommon.ApplicationTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class AwsS3UtilityTest {

    @Autowired
    private AwsS3Utility s3Utility;

    @MockBean
    private AmazonS3 awsS3Client;

    @MockBean
    private TransferManager transferManager;

    @MockBean
    private Upload upload;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fileExists_True() throws AwsS3SdkException, AwsS3InvalidParameterException {
        String bucketName = "test-bucket";
        String s3Key = "keyCheckFile";
        when(awsS3Client.doesObjectExist(bucketName, s3Key)).thenReturn(true);

        boolean result = s3Utility.fileExists(bucketName, s3Key);
        assertThat(result, is(true));
    }

    @Test
    public void fileExists_Flase() throws AwsS3SdkException, AwsS3InvalidParameterException {
        String bucketName = "test-bucket";
        String s3Key = "keyNotFound";
        when(awsS3Client.doesObjectExist(bucketName, s3Key)).thenReturn(false);

        boolean result = s3Utility.fileExists(bucketName, s3Key);
        assertThat(result, is(false));
    }

    @Test
    public void fileExists_ParameterException_bucketName()
            throws AwsS3SdkException, AwsS3InvalidParameterException {
        String bucketName = "test-bucket";
        String s3Key = "keyException";

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = null;
        s3Utility.fileExists(bucketName, s3Key);
    }

    @Test
    public void fileExists_ParameterException_s3Key()
            throws AwsS3SdkException, AwsS3InvalidParameterException {
        String bucketName = "test-bucket";
        String s3Key = "keyException";

        thrown.expect(AwsS3InvalidParameterException.class);

        s3Key = null;
        s3Utility.fileExists(bucketName, s3Key);
    }

    @Test
    public void fileExists_AwsException() throws AwsS3SdkException, AwsS3InvalidParameterException {
        String bucketName = "test-bucket";
        String s3Key = "keyException";
        doThrow(new AmazonServiceException("aws exception")).when(awsS3Client)
                .doesObjectExist(bucketName, s3Key);

        thrown.expect(AwsS3SdkException.class);
        s3Utility.fileExists(bucketName, s3Key);
    }

    @Test
    public void upload_PrameterException() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        InputStream inputData = new ByteArrayInputStream(new byte[1024]);
        boolean overwriteFlag = true;
        Map<String, String> tagsMap = new HashMap<>();

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = null;
        s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap);

        bucketName = "test-bucket";
        s3Key = null;
        s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap);

        bucketName = "test-bucket";
        s3Key = "keyUpload";
        inputData = null;
        s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap);
    }

    @Test
    public void upload_FilesExists() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException {
        String bucketName = "test-bucket";
        String s3Key = "keyUploadFileExists";
        InputStream inputData = new ByteArrayInputStream(new byte[1024]);
        boolean overwriteFlag = false;
        Map<String, String> tagsMap = new HashMap<>();

        when(s3Utility.fileExists(bucketName, s3Key)).thenReturn(true);

        thrown.expect(AwsS3FileAlreadyExistsException.class);
        s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap);
    }

    @Test
    public void upload_AwsS3SdkException() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException {
        String bucketName = "test-bucket";
        String s3Key = "keyUploadAwsS3SdkException";

        when(s3Utility.fileExists(bucketName, s3Key)).thenReturn(false);
        doThrow(new AmazonServiceException("aws exception")).when(transferManager)
                .upload(Mockito.any(PutObjectRequest.class));

        InputStream inputData = new ByteArrayInputStream(new byte[1024]);
        boolean overwriteFlag = false;
        Map<String, String> tagsMap = new HashMap<>();

        thrown.expect(AwsS3SdkException.class);
        s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap);
    }

    @Test
    public void upload_Sucess() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AmazonServiceException, AmazonClientException,
            InterruptedException {
        String bucketName = "test-bucket";
        String s3Key = "keyUploadAwsS3SdkException";

        when(s3Utility.fileExists(bucketName, s3Key)).thenReturn(false, true);

        UploadResult uploadResult = new UploadResult();
        uploadResult.setETag("123");
        when(upload.waitForUploadResult()).thenReturn(uploadResult);

        InputStream inputData = new ByteArrayInputStream(new byte[1024]);
        boolean overwriteFlag = false;
        Map<String, String> tagsMap = new HashMap<>();

        when(transferManager.upload(Mockito.any(PutObjectRequest.class))).thenReturn(upload);
        String result = s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap);
        assertThat(result, is("123"));
    }

    @Test
    public void upload_PrameterException_new() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File file = new File("uploadFileFileAlreadyExistsTest.file");
        file.createNewFile();
        FileInputStream inputData = new FileInputStream(file);
        boolean overwriteFlag = true;
        Map<String, String> tagsMap = new HashMap<>();

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = null;
        s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap, file.length());

        bucketName = "test-bucket";
        s3Key = null;
        s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap, file.length());

        bucketName = "test-bucket";
        s3Key = "keyUpload";
        inputData = null;
        s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap, file.length());
        
        file.delete();
    }

    @Test
    public void upload_FilesExists_new() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUploadFileExists";
        File file = new File("uploadFileFileAlreadyExistsTest.file");
        file.createNewFile();
        FileInputStream inputData = new FileInputStream(file);
        boolean overwriteFlag = false;
        Map<String, String> tagsMap = new HashMap<>();

        when(s3Utility.fileExists(bucketName, s3Key)).thenReturn(true);

        thrown.expect(AwsS3FileAlreadyExistsException.class);
        s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap, file.length());
        
        file.delete();
    }

    @Test
    public void upload_AwsS3SdkException_new() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUploadAwsS3SdkException";

        when(s3Utility.fileExists(bucketName, s3Key)).thenReturn(false);
        doThrow(new AmazonServiceException("aws exception")).when(transferManager)
                .upload(Mockito.any(PutObjectRequest.class));

        File file = new File("uploadFileFileAlreadyExistsTest.file");
        file.createNewFile();
        FileInputStream inputData = new FileInputStream(file);
        boolean overwriteFlag = false;
        Map<String, String> tagsMap = new HashMap<>();

        thrown.expect(AwsS3SdkException.class);
        s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap, file.length());
        
        file.delete();
    }

    @Test
    public void upload_Sucess_new() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AmazonServiceException, AmazonClientException,
            InterruptedException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUploadAwsS3SdkException";

        when(s3Utility.fileExists(bucketName, s3Key)).thenReturn(false, true);

        UploadResult uploadResult = new UploadResult();
        uploadResult.setETag("123");
        when(upload.waitForUploadResult()).thenReturn(uploadResult);

        File file = new File("uploadFileFileAlreadyExistsTest.file");
        file.createNewFile();
        FileInputStream inputData = new FileInputStream(file);
        boolean overwriteFlag = false;
        Map<String, String> tagsMap = new HashMap<>();

        when(transferManager.upload(Mockito.any(PutObjectRequest.class))).thenReturn(upload);
        String result = s3Utility.upload(bucketName, s3Key, inputData, overwriteFlag, tagsMap, file.length());
        assertThat(result, is("123"));
        
        file.delete();
    }

    @Test
    public void uploadFilePrameterException_bucketName()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File file = new File("xxx");
        boolean overwriteFlag = true;
        Map<String, String> tagsMap = new HashMap<>();

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = null;
        s3Utility.uploadFile(bucketName, s3Key, file, overwriteFlag, tagsMap);
    }

    @Test
    public void uploadFilePrameterException_s3Key()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File file = new File("xxx");
        boolean overwriteFlag = true;
        Map<String, String> tagsMap = new HashMap<>();

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = "test-bucket";
        s3Key = null;
        s3Utility.uploadFile(bucketName, s3Key, file, overwriteFlag, tagsMap);
    }

    @Test
    public void uploadFilePrameterException_file()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File file = new File("xxx");
        boolean overwriteFlag = true;
        Map<String, String> tagsMap = new HashMap<>();

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = "test-bucket";
        s3Key = "keyUpload";
        file = null;
        s3Utility.uploadFile(bucketName, s3Key, file, overwriteFlag, tagsMap);
    }

    @Test
    public void uploadFile_FileNotFound() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File file = new File("xxx");
        boolean overwriteFlag = true;
        Map<String, String> tagsMap = new HashMap<>();

        thrown.expect(AwsS3FileNotFoundException.class);

        s3Utility.uploadFile(bucketName, s3Key, file, overwriteFlag, tagsMap);
    }

    @Test
    public void uploadFile_FileAlreadyExists()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File file = new File("uploadFileFileAlreadyExistsTest.file");
        file.createNewFile();
        boolean overwriteFlag = true;

        overwriteFlag = false;
        when(s3Utility.fileExists(bucketName, s3Key)).thenReturn(true);

        thrown.expect(AwsS3FileAlreadyExistsException.class);

        Map<String, String> tagsMap = new HashMap<>();
        s3Utility.uploadFile(bucketName, s3Key, file, overwriteFlag, tagsMap);
        file.delete();
    }

    @Test
    public void uploadFile_AwsException() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File file = new File("uploadFileFileAlreadyExistsTest.file");
        file.createNewFile();

        when(s3Utility.fileExists(bucketName, s3Key)).thenReturn(false);
        doThrow(new AmazonServiceException("aws exception")).when(transferManager)
                .upload(Mockito.any(PutObjectRequest.class));

        thrown.expect(AwsS3SdkException.class);

        boolean overwriteFlag = true;
        Map<String, String> tagsMap = new HashMap<>();
        s3Utility.uploadFile(bucketName, s3Key, file, overwriteFlag, tagsMap);
        file.delete();
    }

    @Test
    public void uploadFile_Success() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException,
            AmazonServiceException, AmazonClientException, InterruptedException {
        final String bucketName = "test-bucket";
        final String s3Key = "keyUpload";
        final boolean overwriteFlag = true;
        File file = new File("uploadFileFileAlreadyExistsTest.file");
        file.createNewFile();

        UploadResult uploadResult = new UploadResult();
        uploadResult.setETag("123");
        when(upload.waitForUploadResult()).thenReturn(uploadResult);

        Map<String, String> tagsMap = new LinkedHashMap<>();
        tagsMap.put("tagkey1", "tagvalue1");
        tagsMap.put("tagkey2", "tagvalue2");

        when(transferManager.upload(Mockito.any(PutObjectRequest.class))).thenReturn(upload);

        ArgumentCaptor<PutObjectRequest> argument = ArgumentCaptor.forClass(PutObjectRequest.class);

        String result = s3Utility.uploadFile(bucketName, s3Key, file, overwriteFlag, tagsMap);
        assertThat(result, is("123"));
        verify(transferManager).upload(argument.capture());
        assertThat(argument.getValue().getTagging().getTagSet().size(), is(2));
        assertThat(argument.getValue().getTagging().getTagSet().get(0).getKey(), is("tagkey1"));
        assertThat(argument.getValue().getTagging().getTagSet().get(0).getValue(), is("tagvalue1"));
        assertThat(argument.getValue().getTagging().getTagSet().get(1).getKey(), is("tagkey2"));
        assertThat(argument.getValue().getTagging().getTagSet().get(1).getValue(), is("tagvalue2"));
        assertThat(argument.getValue().getMetadata().getSSEAlgorithm(),
                is(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION));
        file.delete();
    }

    @Test
    public void uploadFiles_PrameterException_bucketName()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File directory = new File("commonDirectory");
        List<File> files = Collections.emptyList();
        Map<String, String> tagsMap = new HashMap<>();

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = null;
        s3Utility.uploadFiles(bucketName, s3Key, directory, files, tagsMap);
    }

    @Test
    public void uploadFiles_PrameterException_bucketName_directory_null()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File directory = new File("commonDirectory");
        List<File> files = Collections.emptyList();
        Map<String, String> tagsMap = new HashMap<>();

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = "test-bucket";
        directory = null;
        s3Utility.uploadFiles(bucketName, s3Key, directory, files, tagsMap);
    }

    @Test
    public void uploadFiles_PrameterException_bucketName_directory_notExists()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File directory = new File("commonDirectory");
        List<File> files = Collections.emptyList();
        Map<String, String> tagsMap = new HashMap<>();

        try {
            bucketName = "test-bucket";
            directory = new File("commonDirectory");
            s3Utility.uploadFiles(bucketName, s3Key, directory, files, tagsMap);
            fail("no such directory, no exception was thrown!");
        } catch (AwsS3InvalidParameterException e) {
        }

        try {
            bucketName = "test-bucket";
            directory = new File("commonDirectory");
            directory.createNewFile();
            s3Utility.uploadFiles(bucketName, s3Key, directory, files, tagsMap);
            fail("not a directory, no exception was thrown!");
        } catch (AwsS3InvalidParameterException e) {
        } finally {
            directory.delete();
        }
    }

    @Test
    public void uploadFiles_PrameterException_directory_notDir()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File directory = new File("commonDirectory.file");
        List<File> files = Collections.emptyList();
        Map<String, String> tagsMap = new HashMap<>();

        thrown.expect(AwsS3InvalidParameterException.class);

        directory.deleteOnExit();
        directory.createNewFile();
        s3Utility.uploadFiles(bucketName, s3Key, directory, files, tagsMap);
        directory.delete();
    }

    @Test
    public void uploadFiles_EmptyFiles() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";
        File directory = new File("commonDirectory");
        List<File> files = Collections.emptyList();
        Map<String, String> tagsMap = new HashMap<>();

        directory.deleteOnExit();
        directory.mkdir();
        List<MultiUploadResult> uploadResults =
                s3Utility.uploadFiles(bucketName, s3Key, directory, files, tagsMap);
        assertThat(uploadResults.size(), is(0));
        directory.delete();
    }

    @Test
    public void uploadFiles() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException,
            AmazonServiceException, AmazonClientException, InterruptedException {
        final String bucketName = "test-bucket";
        final String s3Key = "keyUpload";
        final File directory = new File("commonDirectory");
        final List<File> files = new ArrayList<>();

        UploadResult uploadResult1 = new UploadResult();
        uploadResult1.setETag("123");
        uploadResult1.setKey("file1");
        UploadResult uploadResult2 = new UploadResult();
        uploadResult2.setETag("456");
        uploadResult1.setKey("file3");
        when(upload.waitForUploadResult()).thenReturn(uploadResult1, uploadResult2)
                .thenThrow(new AmazonClientException("aws exception"));

        Map<String, String> tagsMap = new LinkedHashMap<>();
        tagsMap.put("tagkey1", "tagvalue1");
        tagsMap.put("tagkey2", "tagvalue2");

        List<Upload> subTransfers = new ArrayList<>();
        Collections.addAll(subTransfers, upload, upload, upload);

        MultipleFileUpload mfu =
                new MultipleFileUploadImpl(null, null, null, s3Key, bucketName, subTransfers);

        when(transferManager.uploadFileList(Mockito.anyString(), Mockito.anyString(),
                Mockito.any(File.class), Mockito.any(), Mockito.any(ObjectMetadataProvider.class),
                Mockito.any(ObjectTaggingProvider.class))).thenReturn(mfu);

        final ArgumentCaptor<ObjectTaggingProvider> taggingArgument =
                ArgumentCaptor.forClass(ObjectTaggingProvider.class);

        directory.mkdir();
        File file1 = new File(directory, "file1");
        file1.createNewFile();
        files.add(file1);
        File file2 = new File(directory, "file2");
        file2.createNewFile();
        files.add(file2);
        File file3 = new File(directory, "file3");
        file3.createNewFile();
        files.add(file3);
        final List<MultiUploadResult> uploadResults =
                s3Utility.uploadFiles(bucketName, s3Key, directory, files, tagsMap);
        verify(transferManager).uploadFileList(Mockito.anyString(), Mockito.anyString(),
                Mockito.any(File.class), Mockito.any(), Mockito.any(ObjectMetadataProvider.class),
                taggingArgument.capture());
        assertThat(taggingArgument.getValue().provideObjectTags(null).getTagSet().size(), is(2));
        assertThat(taggingArgument.getValue().provideObjectTags(null).getTagSet().get(0).getKey(),
                is("tagkey1"));
        assertThat(taggingArgument.getValue().provideObjectTags(null).getTagSet().get(0).getValue(),
                is("tagvalue1"));
        assertThat(taggingArgument.getValue().provideObjectTags(null).getTagSet().get(1).getKey(),
                is("tagkey2"));
        assertThat(taggingArgument.getValue().provideObjectTags(null).getTagSet().get(1).getValue(),
                is("tagvalue2"));
        assertThat(uploadResults.size(), is(3));
        directory.delete();
        file1.delete();
        file2.delete();
        file3.delete();
    }

    @Test
    public void download_PrameterException_bucketName()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = null;
        s3Utility.download(bucketName, s3Key);
    }

    @Test
    public void download_PrameterException_s3Key()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = "test-bucket";
        s3Key = null;
        s3Utility.download(bucketName, s3Key);
    }

    @Test
    public void download_FileNotFound() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";

        when(awsS3Client.getObject(Mockito.any(GetObjectRequest.class))).thenReturn(null);
        thrown.expect(AwsS3FileNotFoundException.class);

        s3Utility.download(bucketName, s3Key);
    }

    @Test
    public void download_AwsException() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";

        doThrow(new AmazonServiceException("aws exception")).when(awsS3Client)
                .getObject(Mockito.any(GetObjectRequest.class));

        thrown.expect(AwsS3SdkException.class);

        s3Utility.download(bucketName, s3Key);
    }

    @Test
    public void download_Success() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String s3Key = "keyUpload";

        InputStream objectContent = new ByteArrayInputStream(new String("123").getBytes());
        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(objectContent);
        when(awsS3Client.getObject(Mockito.any(GetObjectRequest.class))).thenReturn(s3Object);

        try (ByteArrayOutputStream downloadResult = s3Utility.download(bucketName, s3Key)) {
            String resultString = new String(downloadResult.toByteArray());
            assertThat(resultString, is("123"));
        }
    }

    @Test
    public void deleteFile_PrameterException()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException {
        String bucketName = "test-bucket";
        String[] s3Key = new String[] {"keyUpload"};

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = null;
        s3Utility.deleteFile(bucketName, s3Key);
    }

    @Test
    public void deleteFile_zerokeys() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException {
        String bucketName = "test-bucket";

        List<String> deleteResult = s3Utility.deleteFile(bucketName).getDeletedKeys();
        assertThat(deleteResult.size(), is(0));
    }

    @Test
    public void deleteFile_AwsException() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        String bucketName = "test-bucket";
        String[] s3Key = new String[] {"keyUpload"};

        doThrow(new AmazonServiceException("aws exception")).when(awsS3Client)
                .deleteObjects(Mockito.any(DeleteObjectsRequest.class));

        thrown.expect(AwsS3SdkException.class);

        s3Utility.deleteFile(bucketName, s3Key);
    }

    @Test
    public void deleteFile_MultiDeleteObjectsException()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {

        DeleteError error2 = new DeleteError();
        error2.setKey("key2");
        error2.setMessage("errorMessage2");
        DeleteError error4 = new DeleteError();
        error4.setKey("key4");
        error4.setMessage("errorMessage4");
        List<DeleteError> deleteErrors = new ArrayList<>();
        Collections.addAll(deleteErrors, error2, error4);
        DeletedObject deleted1 = new DeletedObject();
        deleted1.setKey("key1");
        DeletedObject deleted3 = new DeletedObject();
        deleted3.setKey("key3");
        List<DeletedObject> deletedObjects = new ArrayList<>();
        Collections.addAll(deletedObjects, deleted1, deleted3);
        MultiObjectDeleteException multiObjectDeleteException =
                new MultiObjectDeleteException(deleteErrors, deletedObjects);

        doThrow(multiObjectDeleteException).when(awsS3Client)
                .deleteObjects(Mockito.any(DeleteObjectsRequest.class));

        String bucketName = "test-bucket";
        String[] s3Key = new String[] {"keyUpload"};
        MultiDeleteResult deleteResult = s3Utility.deleteFile(bucketName, s3Key);

        assertThat(deleteResult.getDeleteErrors().size(), is(2));
        assertThat(deleteResult.getDeleteErrors().containsKey("key2"), is(true));
        assertThat(deleteResult.getDeleteErrors().get("key2"), is("errorMessage2"));
        assertThat(deleteResult.getDeleteErrors().containsKey("key4"), is(true));
        assertThat(deleteResult.getDeleteErrors().get("key4"), is("errorMessage4"));
        assertThat(deleteResult.getDeletedKeys().size(), is(2));
        assertThat(deleteResult.getDeletedKeys().get(0), is("key1"));
        assertThat(deleteResult.getDeletedKeys().get(1), is("key3"));

    }

    @Test
    public void deleteFile_Success() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException, IOException {
        final String bucketName = "test-bucket";
        final String[] s3Key = new String[] {"key1", "key2", "key3"};

        final ArgumentCaptor<DeleteObjectsRequest> argument =
                ArgumentCaptor.forClass(DeleteObjectsRequest.class);

        final List<DeletedObject> deletedObjects = new ArrayList<>();
        DeletedObject deleted1 = new DeletedObject();
        deleted1.setKey("key1");
        DeletedObject deleted2 = new DeletedObject();
        deleted2.setKey("key2");
        DeletedObject deleted3 = new DeletedObject();
        deleted3.setKey("key3");
        Collections.addAll(deletedObjects, deleted1, deleted2, deleted3);
        DeleteObjectsResult deleteObjectsResult = new DeleteObjectsResult(deletedObjects);
        when(awsS3Client.deleteObjects(Mockito.any(DeleteObjectsRequest.class)))
                .thenReturn(deleteObjectsResult);

        final MultiDeleteResult deleteResult = s3Utility.deleteFile(bucketName, s3Key);
        final List<String> deletedKeys = deleteResult.getDeletedKeys();
        final Map<String, String> deleteErrors = deleteResult.getDeleteErrors();
        verify(awsS3Client).deleteObjects(argument.capture());
        assertThat(argument.getValue().getBucketName(), is(bucketName));
        assertThat(argument.getValue().getKeys().size(), is(3));
        assertThat(argument.getValue().getKeys().get(0).getKey(), is("key1"));
        assertThat(argument.getValue().getKeys().get(1).getKey(), is("key2"));
        assertThat(argument.getValue().getKeys().get(2).getKey(), is("key3"));
        assertThat(deleteErrors.isEmpty(), is(true));
        assertThat(deletedKeys.size(), is(3));
        assertThat(deletedKeys.get(0), is("key1"));
        assertThat(deletedKeys.get(1), is("key2"));
        assertThat(deletedKeys.get(2), is("key3"));
    }

    @Test
    public void searchFile_PrameterException()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException {
        String bucketName = "test-bucket";
        String prefix = null;
        String nextMarker = null;

        thrown.expect(AwsS3InvalidParameterException.class);

        bucketName = null;
        s3Utility.searchFiles(bucketName, prefix, nextMarker);
    }

    @Test
    public void searchFile_AwsException() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException {
        String bucketName = "test-bucket";
        String prefix = null;
        String nextMarker = null;

        doThrow(new AmazonServiceException("aws exception")).when(awsS3Client)
                .listObjects(Mockito.any(ListObjectsRequest.class));
        thrown.expect(AwsS3SdkException.class);

        s3Utility.searchFiles(bucketName, prefix, nextMarker);
    }

    @Test
    public void searchFile_nonTruncated() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException {
        final String bucketName = "test-bucket";
        final String prefix = "prefix";
        final String nextMarker = null;

        ObjectListing objectListing = new ObjectListing();
        objectListing.setTruncated(false);
        objectListing.setNextMarker(null);
        S3ObjectSummary s3ObjectSummary1 = new S3ObjectSummary();
        s3ObjectSummary1.setKey("key1");
        s3ObjectSummary1.setSize(1);
        S3ObjectSummary s3ObjectSummary2 = new S3ObjectSummary();
        s3ObjectSummary2.setKey("key2");
        s3ObjectSummary2.setSize(2);
        Collections.addAll(objectListing.getObjectSummaries(), s3ObjectSummary1, s3ObjectSummary2);

        final ArgumentCaptor<ListObjectsRequest> argument =
                ArgumentCaptor.forClass(ListObjectsRequest.class);

        when(awsS3Client.listObjects(Mockito.any(ListObjectsRequest.class)))
                .thenReturn(objectListing);

        final S3SearchResult searchResult = s3Utility.searchFiles(bucketName, prefix, nextMarker);
        verify(awsS3Client).listObjects(argument.capture());
        assertThat(argument.getValue().getBucketName(), is(bucketName));
        assertThat(argument.getValue().getPrefix(), is(prefix));
        assertThat(argument.getValue().getMarker(), is(nextMarker));
        assertThat(searchResult.getBucketName(), is(bucketName));
        assertThat(searchResult.isTruncated(), is(false));
        assertNull(searchResult.getNextMarker());
        assertThat(searchResult.getS3FileNameResults().size(), is(2));
        assertThat(searchResult.getS3FileNameResults().get(0).getKey(), is("key1"));
        assertThat(searchResult.getS3FileNameResults().get(0).getSize(), is(1L));
        assertThat(searchResult.getS3FileNameResults().get(1).getKey(), is("key2"));
        assertThat(searchResult.getS3FileNameResults().get(1).getSize(), is(2L));
    }

    @Test
    public void searchFile_truncated() throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException {
        final String bucketName = "test-bucket";
        final String prefix = "prefix";
        final String nextMarker = null;

        ObjectListing objectListing = new ObjectListing();
        objectListing.setTruncated(true);
        objectListing.setNextMarker("nextMarker");
        S3ObjectSummary s3ObjectSummary1 = new S3ObjectSummary();
        s3ObjectSummary1.setKey("key1");
        s3ObjectSummary1.setSize(1);
        S3ObjectSummary s3ObjectSummary2 = new S3ObjectSummary();
        s3ObjectSummary2.setKey("key2");
        s3ObjectSummary2.setSize(2);
        Collections.addAll(objectListing.getObjectSummaries(), s3ObjectSummary1, s3ObjectSummary2);

        final ArgumentCaptor<ListObjectsRequest> argument =
                ArgumentCaptor.forClass(ListObjectsRequest.class);

        when(awsS3Client.listObjects(Mockito.any(ListObjectsRequest.class)))
                .thenReturn(objectListing);

        final S3SearchResult searchResult = s3Utility.searchFiles(bucketName, prefix, nextMarker);
        verify(awsS3Client).listObjects(argument.capture());
        assertThat(argument.getValue().getBucketName(), is(bucketName));
        assertThat(argument.getValue().getPrefix(), is(prefix));
        assertThat(argument.getValue().getMarker(), is(nextMarker));
        assertThat(searchResult.getBucketName(), is(bucketName));
        assertThat(searchResult.isTruncated(), is(true));
        assertThat(searchResult.getNextMarker(), is("nextMarker"));
        assertThat(searchResult.getS3FileNameResults().size(), is(2));
        assertThat(searchResult.getS3FileNameResults().get(0).getKey(), is("key1"));
        assertThat(searchResult.getS3FileNameResults().get(0).getSize(), is(1L));
        assertThat(searchResult.getS3FileNameResults().get(1).getKey(), is("key2"));
        assertThat(searchResult.getS3FileNameResults().get(1).getSize(), is(2L));
    }

    @Test
    public void searchFile_WithNextMarker()
            throws AwsS3SdkException, AwsS3InvalidParameterException,
            AwsS3FileAlreadyExistsException, AwsS3FileNotFoundException {
        final String bucketName = "test-bucket";
        final String prefix = "prefix";
        final String nextMarker = "nextMarker";

        ObjectListing objectListing = new ObjectListing();
        objectListing.setTruncated(false);
        objectListing.setNextMarker(null);
        S3ObjectSummary s3ObjectSummary1 = new S3ObjectSummary();
        s3ObjectSummary1.setKey("key3");
        s3ObjectSummary1.setSize(3);
        Collections.addAll(objectListing.getObjectSummaries(), s3ObjectSummary1);

        final ArgumentCaptor<ListObjectsRequest> argument =
                ArgumentCaptor.forClass(ListObjectsRequest.class);

        when(awsS3Client.listObjects(Mockito.any(ListObjectsRequest.class)))
                .thenReturn(objectListing);

        final S3SearchResult searchResult = s3Utility.searchFiles(bucketName, prefix, nextMarker);
        verify(awsS3Client).listObjects(argument.capture());
        assertThat(argument.getValue().getBucketName(), is(bucketName));
        assertThat(argument.getValue().getPrefix(), is(prefix));
        assertThat(argument.getValue().getMarker(), is(nextMarker));
        assertThat(searchResult.getBucketName(), is(bucketName));
        assertThat(searchResult.isTruncated(), is(false));
        assertNull(searchResult.getNextMarker());
        assertThat(searchResult.getS3FileNameResults().size(), is(1));
        assertThat(searchResult.getS3FileNameResults().get(0).getKey(), is("key3"));
        assertThat(searchResult.getS3FileNameResults().get(0).getSize(), is(3L));
    }
}
