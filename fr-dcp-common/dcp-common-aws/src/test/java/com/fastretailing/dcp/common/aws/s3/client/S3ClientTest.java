/**
 * @(#)S3ClientTest.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.aws.s3.client;

import static com.fastretailing.dcp.common.aws.s3.util.S3TestUtility.createObjectListing;
import static com.fastretailing.dcp.common.aws.s3.util.S3TestUtility.createS3ObjectSummary;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.fastretailing.dcp.common.aws.exception.S3ClientSystemException;
import com.fastretailing.dcp.common.aws.exception.S3MultiObjectDeleteException;
import com.fastretailing.dcp.common.aws.exception.S3ObjectAlreadyExistsException;
import com.fastretailing.dcp.common.aws.exception.S3ObjectNotFoundException;
import com.fastretailing.dcp.common.aws.s3.S3ClientTestApplication;

/**
 * Test for S3 client.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=S3ClientTestApplication.class)
public class S3ClientTest {

    /** Amazon S3 mock. */
    @SpyBean
    private AmazonS3 amazonS3;

    /** Transfer manager. */
    @SpyBean
    private TransferManager transferManager;
    
    private Upload upload = Mockito.mock(Upload.class);

    /** S3 client. */
    private S3Client s3Client;

    @Before
    public void setUp() throws AmazonServiceException, AmazonClientException, InterruptedException {
        s3Client = new S3ClientImpl(amazonS3, transferManager, 300);
        UploadResult result = new UploadResult();
        result.setETag("test-etag");
        doReturn(result).when(upload).waitForUploadResult();
    }

    /**
     * Test for doesObjectExists(String, String)<br>
     * Condition : Bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testDoesObjectExists_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.doesObjectExists(null, "s3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
    }

    /**
     * Test for doesObjectExists(String, String)<br>
     * Condition : Bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testDoesObjectExists_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.doesObjectExists("", "s3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
    }

    /**
     * Test for doesObjectExists(String, String)<br>
     * Condition : S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testDoesObjectExists_s3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.doesObjectExists("bucketName", null))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
    }

    /**
     * Test for doesObjectExists(String, String)<br>
     * Condition : S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testDoesObjectExists_s3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.doesObjectExists("bucketName", ""))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
    }

    /**
     * Test for doesObjectExists(String, String)<br>
     * Condition : Success.
     * 
     * @throws Exception
     */
    @Test
    public void testDoesObjectExists_success() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        boolean result = s3Client.doesObjectExists("bucketName", "s3Key");
        assertThat(result, equalTo(true));
    }

    /**
     * Test for doesObjectExists(String, String)<br>
     * Condition : Exception occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testDoesObjectExists_exception() throws Exception {
        doThrow(SdkClientException.class).when(amazonS3).doesObjectExist(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.doesObjectExists("bucketName", "s3Key"))
                .isInstanceOf(S3ClientSystemException.class).hasMessageContaining(
                        "Error has occurred during checking existence of the S3 object.");
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean, Map<String, String>)<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload1_bucketNameNull() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload(null, "s3Key", input, 4, true, null))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean, Map<String, String>)<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload1_bucketNameEmpty() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload("", "s3Key", input, 4, true, null))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean, Map<String, String>)<br>
     * Condition : s3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload1_s3KeyNull() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload("bucketName", null, input, 4, true, null))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean, Map<String, String>)<br>
     * Condition : s3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload1_s3KeyEmpty() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload("bucketName", "", input, 4, true, null))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean, Map<String, String>)<br>
     * Condition : input data is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload1_inputNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", "s3Key", null, 0, true, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Upload data must not be null. bucket name: bucketName, S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean, Map<String, String>)<br>
     * Condition : s3 object already exists.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload1_objectExists() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload("bucketName", "s3Key", input, 4, false, null))
                    .isInstanceOf(S3ObjectAlreadyExistsException.class).hasMessage(
                            "S3 object already exists.  bucket name: bucketName, S3 key: s3Key");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean, Map<String, String>)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload1_success() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            String etag = s3Client.upload("bucketName", "s3Key", input, 4, false, null);
            assertThat(etag, equalTo("test-etag"));
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean, Map<String, String>)<br>
     * Condition : success(overwrite object).
     * 
     * @throws Exception
     */
    @Test
    public void testUpload1_successOverwrite() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            String etag = s3Client.upload("bucketName", "s3Key", input, 4, true, null);
            assertThat(etag, equalTo("test-etag"));
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean, Map<String, String>)<br>
     * Condition : success(with tags).
     * 
     * @throws Exception
     */
    @Test
    public void testUpload1_successWithTag() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());

        Map<String, String> tags = new HashMap<>();
        tags.put("tag-key", "tag-value");
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            String etag = s3Client.upload("bucketName", "s3Key", input, 4, false, tags);
            assertThat(etag, equalTo("test-etag"));
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean)<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload2_bucketNameNull() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload(null, "s3Key", input, 4, true))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean)<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload2_bucketNameEmpty() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload("", "s3Key", input, 4, true))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean)<br>
     * Condition : s3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload2_s3KeyNull() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload("bucketName", null, input, 4, true))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean)<br>
     * Condition : s3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload2_s3KeyEmpty() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload("bucketName", "", input, 4, true))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean)<br>
     * Condition : input data is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload2_inputNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", "s3Key", null, 0, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Upload data must not be null. bucket name: bucketName, S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean)<br>
     * Condition : s3 object already exists.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload2_objectExists() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload("bucketName", "s3Key", input, 4, false))
                    .isInstanceOf(S3ObjectAlreadyExistsException.class).hasMessage(
                            "S3 object already exists.  bucket name: bucketName, S3 key: s3Key");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload2_success() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            String etag = s3Client.upload("bucketName", "s3Key", input, 4, false);
            assertThat(etag, equalTo("test-etag"));
        }
    }

    /**
     * Test for upload(String, String, InputStream, long, boolean)<br>
     * Condition : success(overwrite object).
     * 
     * @throws Exception
     */
    @Test
    public void testUpload2_successOverwrite() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            String etag = s3Client.upload("bucketName", "s3Key", input, 4, true);
            assertThat(etag, equalTo("test-etag"));
        }
    }

    /**
     * Test for upload(String, String, InputStream, long)<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload3_bucketNameNull() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload(null, "s3Key", input, 4))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long)<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload3_bucketNameEmpty() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload("", "s3Key", input, 4))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long)<br>
     * Condition : s3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload3_s3KeyNull() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload("bucketName", null, input, 4))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long)<br>
     * Condition : s3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload3_s3KeyEmpty() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            assertThatThrownBy(() -> s3Client.upload("bucketName", "", input, 4))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage(
                            "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
        }
    }

    /**
     * Test for upload(String, String, InputStream, long)<br>
     * Condition : input data is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload3_inputNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", "s3Key", null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Upload data must not be null. bucket name: bucketName, S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, InputStream, long)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload3_success() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            String etag = s3Client.upload("bucketName", "s3Key", input, 4);
            assertThat(etag, equalTo("test-etag"));
        }
    }

    /**
     * Test for upload(String, String, InputStream, long)<br>
     * Condition : success(overwrite object).
     * 
     * @throws Exception
     */
    @Test
    public void testUpload3_successOverwrite() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            String etag = s3Client.upload("bucketName", "s3Key", input, 4);
            assertThat(etag, equalTo("test-etag"));
        }
    }

    /**
     * Test for upload(String, String, byte[], boolean, Map<String, String>)<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload4_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload(null, "s3Key", "test".getBytes(), true, null))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, byte[], boolean, Map<String, String>)<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload4_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("", "s3Key", "test".getBytes(), true, null))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, byte[], boolean, Map<String, String>)<br>
     * Condition : s3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload4_s3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", null, "test".getBytes(), true, null))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
    }

    /**
     * Test for upload(String, String, byte[], boolean, Map<String, String>)<br>
     * Condition : s3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload4_s3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", "", "test".getBytes(), true, null))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
    }

    /**
     * Test for upload(String, String, byte[], boolean, Map<String, String>)<br>
     * Condition : input data is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload4_inputNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", "s3Key", null, true, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Upload data must not be null. bucket name: bucketName, S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, byte[], boolean, Map<String, String>)<br>
     * Condition : s3 object already exists.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload4_objectExists() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.upload("bucketName", "s3Key", "test".getBytes(), false,
                null)).isInstanceOf(S3ObjectAlreadyExistsException.class).hasMessage(
                        "S3 object already exists.  bucket name: bucketName, S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, byte[], boolean, Map<String, String>)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload4_success() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        String etag = s3Client.upload("bucketName", "s3Key", "test".getBytes(), false, null);
        assertThat(etag, equalTo("test-etag"));
    }

    /**
     * Test for upload(String, String, byte[], boolean, Map<String, String>)<br>
     * Condition : success(overwrite object).
     * 
     * @throws Exception
     */
    @Test
    public void testUpload4_successOverwrite() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        String etag = s3Client.upload("bucketName", "s3Key", "test".getBytes(), true, null);
        assertThat(etag, equalTo("test-etag"));
    }

    /**
     * Test for upload(String, String, byte[], boolean, Map<String, String>)<br>
     * Condition : success(with tags).
     * 
     * @throws Exception
     */
    @Test
    public void testUpload4_successWithTag() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());

        Map<String, String> tags = new HashMap<>();
        tags.put("tag-key", "tag-value");
        String etag = s3Client.upload("bucketName", "s3Key", "test".getBytes(), false, tags);
        assertThat(etag, equalTo("test-etag"));
    }

    /**
     * Test for upload(String, String, byte[], boolean)<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload5_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload(null, "s3Key", "test".getBytes(), true))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, byte[], boolean)<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload5_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("", "s3Key", "test".getBytes(), true))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, byte[], boolean)<br>
     * Condition : s3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload5_s3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", null, "test".getBytes(), true))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
    }

    /**
     * Test for upload(String, String, byte[], boolean)<br>
     * Condition : s3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload5_s3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", "", "test".getBytes(), true))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
    }

    /**
     * Test for upload(String, String, byte[], boolean)<br>
     * Condition : input data is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload5_inputNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", "s3Key", null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Upload data must not be null. bucket name: bucketName, S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, byte[], boolean)<br>
     * Condition : s3 object already exists.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload5_objectExists() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.upload("bucketName", "s3Key", "test".getBytes(), false))
                .isInstanceOf(S3ObjectAlreadyExistsException.class)
                .hasMessage("S3 object already exists.  bucket name: bucketName, S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, byte[], boolean)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload5_success() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        String etag = s3Client.upload("bucketName", "s3Key", "test".getBytes(), false);
        assertThat(etag, equalTo("test-etag"));
    }

    /**
     * Test for upload(String, String, byte[], boolean)<br>
     * Condition : success(overwrite object).
     * 
     * @throws Exception
     */
    @Test
    public void testUpload5_successOverwrite() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        String etag = s3Client.upload("bucketName", "s3Key", "test".getBytes(), true);
        assertThat(etag, equalTo("test-etag"));
    }

    /**
     * Test for upload(String, String, byte[])<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload6_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload(null, "s3Key", "test".getBytes()))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, byte[])<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload6_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("", "s3Key", "test".getBytes()))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, byte[])<br>
     * Condition : s3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload6_s3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", null, "test".getBytes()))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
    }

    /**
     * Test for upload(String, String, byte[])<br>
     * Condition : s3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload6_s3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", "", "test".getBytes()))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
    }

    /**
     * Test for upload(String, String, byte[])<br>
     * Condition : input data is null.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload6_inputNull() throws Exception {
        assertThatThrownBy(() -> s3Client.upload("bucketName", "s3Key", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Upload data must not be null. bucket name: bucketName, S3 key: s3Key");
    }

    /**
     * Test for upload(String, String, byte[])<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testUpload6_success() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        String etag = s3Client.upload("bucketName", "s3Key", "test".getBytes());
        assertThat(etag, equalTo("test-etag"));
    }

    /**
     * Test for upload(String, String, byte[])<br>
     * Condition : success(overwrite object).
     * 
     * @throws Exception
     */
    @Test
    public void testUpload6_successOverwrite() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(upload).when(transferManager).upload(any());
        String etag = s3Client.upload("bucketName", "s3Key", "test".getBytes());
        assertThat(etag, equalTo("test-etag"));
    }

    /**
     * Test for downloadAsByteArray(String, String)<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsByteArray_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.downloadAsByteArray(null, "s3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
    }

    /**
     * Test for downloadAsByteArray(String, String)<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsByteArray_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.downloadAsByteArray("", "s3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
    }

    /**
     * Test for downloadAsByteArray(String, String)<br>
     * Condition : S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsByteArray_s3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.downloadAsByteArray("bucketName", null))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
    }

    /**
     * Test for downloadAsByteArray(String, String)<br>
     * Condition : S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsByteArray_s3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.downloadAsByteArray("bucketName", ""))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
    }

    /**
     * Test for downloadAsByteArray(String, String)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsByteArray_success() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            S3Object object = new S3Object();
            object.setObjectContent(input);
            doReturn(object).when(amazonS3).getObject(any());
            byte[] data = s3Client.downloadAsByteArray("bucketName", "s3Key");
            assertThat(new String(data), equalTo("test"));
        }
    }

    /**
     * Test for downloadAsByteArray(String, String)<br>
     * Condition : exception occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsByteArray_exception() throws Exception {
        doThrow(SdkClientException.class).when(amazonS3).getObject(any());
        assertThatThrownBy(() -> s3Client.downloadAsByteArray("bucketName", "s3Key"))
                .isInstanceOf(S3ClientSystemException.class)
                .hasMessage("Error has occurred during download.");
    }

    /**
     * Test for downloadAsInputStream(String, String)<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsInputStream_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.downloadAsInputStream(null, "s3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
    }

    /**
     * Test for downloadAsInputStream(String, String)<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsInputStream_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.downloadAsInputStream("", "s3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
    }

    /**
     * Test for downloadAsInputStream(String, String)<br>
     * Condition : S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsInputStream_s3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.downloadAsInputStream("bucketName", null))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
    }

    /**
     * Test for downloadAsInputStream(String, String)<br>
     * Condition : S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsInputStream_s3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.downloadAsInputStream("bucketName", ""))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
    }

    /**
     * Test for downloadAsInputStream(String, String)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsInputStream_success() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes())) {
            S3Object object = new S3Object();
            object.setObjectContent(input);
            doReturn(object).when(amazonS3).getObject(any());
            try (InputStream data = s3Client.downloadAsInputStream("bucketName", "s3Key");
                    InputStreamReader reader = new InputStreamReader(data);
                    BufferedReader bufferedReader = new BufferedReader(reader)) {
                assertThat(bufferedReader.readLine(), equalTo("test"));
            }
        }
    }

    /**
     * Test for downloadAsInputStream(String, String)<br>
     * Condition : exception occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testDownloadAsInputStream_exception() throws Exception {
        doThrow(SdkClientException.class).when(amazonS3).getObject(any());
        assertThatThrownBy(() -> s3Client.downloadAsInputStream("bucketName", "s3Key"))
                .isInstanceOf(S3ClientSystemException.class)
                .hasMessage("Error has occurred during download.");
    }

    /**
     * Test for delete(String, String)<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testDelete_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.delete(null, "s3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
    }

    /**
     * Test for delete(String, String)<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testDelete_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.delete("", "s3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
    }

    /**
     * Test for delete(String, String)<br>
     * Condition : S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testDelete_s3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.delete("bucketName", null))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
    }

    /**
     * Test for delete(String, String)<br>
     * Condition : S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testDelete_s3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.delete("bucketName", ""))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
    }

    /**
     * Test for delete(String, String)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testDelete_success() throws Exception {
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        s3Client.delete("bucketName", "s3Key");
    }

    /**
     * Test for delete(String, String)<br>
     * Condition : exception occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testDelete_exception() throws Exception {
        doThrow(SdkClientException.class).when(amazonS3).deleteObject(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.delete("bucketName", "s3Key"))
                .isInstanceOf(S3ClientSystemException.class)
                .hasMessage("Error has occurred during delete.");
    }

    /**
     * Test for deleteMultiObjects(String, String...)<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteMultiObjects_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.deleteMultiObjects(null, "s3Key"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bucket name must not be empty. bucket name: null");
    }

    /**
     * Test for deleteMultiObjects(String, String...)<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteMultiObjects_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.deleteMultiObjects("", "s3Key"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bucket name must not be empty. bucket name: ");
    }

    /**
     * Test for deleteMultiObjects(String, String...)<br>
     * Condition : no S3 key.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteMultiObjects_noS3Key() throws Exception {
        s3Client.deleteMultiObjects("bucketName");
    }

    /**
     * Test for deleteMultiObjects(String, String...)<br>
     * Condition : array of S3 keys is null.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteMultiObjects_nullS3Keys() throws Exception {
        s3Client.deleteMultiObjects("bucketName", (String[]) null);
    }

    /**
     * Test for deleteMultiObjects(String, String...)<br>
     * Condition : one of S3 keys is null.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteMultiObjects_S3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.deleteMultiObjects("bucketName", "s3Key1", null,
                "s3Key2")).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "S3 key must not be empty. bucket name: bucketName, S3 keys: [s3Key1, null, s3Key2]");
    }

    /**
     * Test for deleteMultiObjects(String, String...)<br>
     * Condition : one of S3 keys is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteMultiObjects_S3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.deleteMultiObjects("bucketName", "s3Key1", "", "s3Key2"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "S3 key must not be empty. bucket name: bucketName, S3 keys: [s3Key1, , s3Key2]");
    }

    /**
     * Test for deleteMultiObjects(String, String...)<br>
     * Condition : one S3 key.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteMultiObjects_oneS3Key() throws Exception {
        doReturn(null).when(amazonS3).deleteObjects(any());
        s3Client.deleteMultiObjects("bucketName", "s3Key");
    }

    /**
     * Test for deleteMultiObjects(String, String...)<br>
     * Condition : multi S3 keys.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteMultiObjects_multiS3Key() throws Exception {
        doReturn(null).when(amazonS3).deleteObjects(any());
        s3Client.deleteMultiObjects("bucketName", "s3Key1", "s3Key2");
    }

    /**
     * Test for deleteMultiObjects(String, String...)<br>
     * Condition : SdkClientException occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteMultiObjects_exception() throws Exception {
        doThrow(SdkClientException.class).when(amazonS3).deleteObjects(any());
        assertThatThrownBy(() -> s3Client.deleteMultiObjects("bucketName", "s3Key"))
                .isInstanceOf(S3ClientSystemException.class)
                .hasMessage("Error has occurred during delete.");
    }

    /**
     * Test for deleteMultiObjects(String, String...)<br>
     * Condition : MultiObjectDeleteException occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteMultiObjects_MultiObjectDeleteException() throws Exception {
        doThrow(MultiObjectDeleteException.class).when(amazonS3).deleteObjects(any());
        assertThatThrownBy(() -> s3Client.deleteMultiObjects("bucketName", "s3Key"))
                .isInstanceOf(S3MultiObjectDeleteException.class)
                .hasMessage("Error has occurred during delete.");
    }

    /**
     * Test for move(String, String, String)<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMove1_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.move(null, "sourceS3Key", "destinationS3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: null, source S3 key: sourceS3Key, destination S3 key: destinationS3Key");
    }

    /**
     * Test for move(String, String, String)<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMove1_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.move("", "sourceS3Key", "destinationS3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: , source S3 key: sourceS3Key, destination S3 key: destinationS3Key");
    }

    /**
     * Test for move(String, String, String)<br>
     * Condition : source S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMove1_sourceS3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.move("bucketName", null, "destinationS3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, source S3 key: null, destination S3 key: destinationS3Key");
    }

    /**
     * Test for move(String, String, String)<br>
     * Condition : source S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMove1_sourceS3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.move("bucketName", "", "destinationS3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, source S3 key: , destination S3 key: destinationS3Key");
    }

    /**
     * Test for move(String, String, String)<br>
     * Condition : destination S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMove1_destinationS3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.move("bucketName", "sourceS3Key", null))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, source S3 key: sourceS3Key, destination S3 key: null");
    }

    /**
     * Test for move(String, String, String)<br>
     * Condition : destination S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMove1_destinationS3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.move("bucketName", "sourceS3Key", ""))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, source S3 key: sourceS3Key, destination S3 key: ");
    }

    /**
     * Test for move(String, String, String)<br>
     * Condition : source object not found.
     * 
     * @throws Exception
     */
    @Test
    public void testMove1_notFound() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.move("bucketName", "sourceS3Key", "destinationS3Key"))
                .isInstanceOf(S3ObjectNotFoundException.class).hasMessage(
                        "Source S3 object not found.  bucket name: bucketName, S3 key: sourceS3Key");
    }

    /**
     * Test for move(String, String, String)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testMove1_success() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        s3Client.move("bucketName", "sourceS3Key", "destinationS3Key");
    }

    /**
     * Test for move(String, String, String)<br>
     * Condition : exception occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testMove1_exception() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doThrow(SdkClientException.class).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.move("bucketName", "sourceS3Key", "destinationS3Key"))
                .isInstanceOf(S3ClientSystemException.class)
                .hasMessage("Error has occurred during move file.");
    }

    /**
     * Test for move(String, String, String, boolean)<br>
     * Condition : bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMove2_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.move(null, "sourceS3Key", "destinationS3Key", true))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: null, source S3 key: sourceS3Key, destination S3 key: destinationS3Key");
    }

    /**
     * Test for move(String, String, String, boolean)<br>
     * Condition : bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMove2_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.move("", "sourceS3Key", "destinationS3Key", true))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: , source S3 key: sourceS3Key, destination S3 key: destinationS3Key");
    }

    /**
     * Test for move(String, String, String, boolean)<br>
     * Condition : source S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMove2_souceS3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.move("bucketName", null, "destinationS3Key", true))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, source S3 key: null, destination S3 key: destinationS3Key");
    }

    /**
     * Test for move(String, String, String, boolean)<br>
     * Condition : source S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMove2_souceS3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.move("bucketName", "", "destinationS3Key", true))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, source S3 key: , destination S3 key: destinationS3Key");
    }

    /**
     * Test for move(String, String, String, boolean)<br>
     * Condition : destination S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMove2_destinationS3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.move("bucketName", "sourceS3Key", null, true))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, source S3 key: sourceS3Key, destination S3 key: null");
    }

    /**
     * Test for move(String, String, String, boolean)<br>
     * Condition : destination S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMove2_destinationS3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.move("bucketName", "sourceS3Key", "", true))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, source S3 key: sourceS3Key, destination S3 key: ");
    }

    /**
     * Test for move(String, String, String, boolean)<br>
     * Condition : source object not found.
     * 
     * @throws Exception
     */
    @Test
    public void testMove2_notFound() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.move("bucketName", "sourceS3Key", "destinationS3Key",
                true)).isInstanceOf(S3ObjectNotFoundException.class).hasMessage(
                        "Source S3 object not found.  bucket name: bucketName, S3 key: sourceS3Key");
    }

    /**
     * Test for move(String, String, String, boolean)<br>
     * Condition : destination object already exists.
     * 
     * @throws Exception
     */
    @Test
    public void testMove2_alreadyExists() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.move("bucketName", "sourceS3Key", "destinationS3Key",
                false)).isInstanceOf(S3ObjectAlreadyExistsException.class).hasMessage(
                        "Destination S3 object already exists.  bucket name: bucketName, S3 key: destinationS3Key");
    }

    /**
     * Test for move(String, String, String, boolean)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testMove2_success() throws Exception {
        doReturn(true, false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        s3Client.move("bucketName", "sourceS3Key", "destinationS3Key", false);
    }

    /**
     * Test for move(String, String, String, boolean)<br>
     * Condition : success(overwrite destination object).
     * 
     * @throws Exception
     */
    @Test
    public void testMove2_successOverwrite() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        s3Client.move("bucketName", "sourceS3Key", "destinationS3Key", true);
    }

    /**
     * Test for move(String, String, String, boolean)<br>
     * Condition : exception occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testMove2_exception() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doThrow(SdkClientException.class).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        assertThatThrownBy(
                () -> s3Client.move("bucketName", "sourceS3Key", "destinationS3Key", true))
                        .isInstanceOf(S3ClientSystemException.class)
                        .hasMessage("Error has occurred during move file.");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String)<br>
     * Condition : source bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket1_sourceBucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket(null, "sourceS3Key",
                "destinationBucketName",
                "destinationS3Key")).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: null, source S3 key: sourceS3Key, destination bucket name: destinationBucketName, destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String)<br>
     * Condition : source bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket1_sourceBucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("", "sourceS3Key",
                "destinationBucketName",
                "destinationS3Key")).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: , source S3 key: sourceS3Key, destination bucket name: destinationBucketName, destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String)<br>
     * Condition : source S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket1_sourceS3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", null,
                "destinationBucketName",
                "destinationS3Key")).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: null, destination bucket name: destinationBucketName, destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String)<br>
     * Condition : source S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket1_sourceS3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "",
                "destinationBucketName",
                "destinationS3Key")).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: , destination bucket name: destinationBucketName, destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String)<br>
     * Condition : destination bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket1_destinationBucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "soureS3Key", null,
                "destinationS3Key")).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: soureS3Key, destination bucket name: null, destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String)<br>
     * Condition : destination bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket1_destinationBucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "soureS3Key", "",
                "destinationS3Key")).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: soureS3Key, destination bucket name: , destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String)<br>
     * Condition : destination S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket1_destinationS3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "soureS3Key",
                "destinationBucketName",
                null)).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: soureS3Key, destination bucket name: destinationBucketName, destination S3 key: null");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String)<br>
     * Condition : destination S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket1_destinationS3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "soureS3Key",
                "destinationBucketName",
                "")).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: soureS3Key, destination bucket name: destinationBucketName, destination S3 key: ");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String)<br>
     * Condition : source object not found.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket1_notFound() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "sourceS3Key",
                "destinationBucketName",
                "destinationS3Key")).isInstanceOf(S3ObjectNotFoundException.class).hasMessage(
                        "Source S3 object not found.  bucket name: sourceBucketName, S3 key: sourceS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket1_success() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        s3Client.moveToOtherBucket("sourceBucketName", "sourceS3Key", "destinationBucketName",
                "destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String)<br>
     * Condition : exception occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket1_exception() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doThrow(SdkClientException.class).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "sourceS3Key",
                "destinationBucketName", "destinationS3Key"))
                        .isInstanceOf(S3ClientSystemException.class)
                        .hasMessage("Error has occurred during move file.");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : source bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_sourceBucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket(null, "sourceS3Key",
                "destinationBucketName", "destinationS3Key",
                true)).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: null, source S3 key: sourceS3Key, destination bucket name: destinationBucketName, destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : source bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_sourceBucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("", "sourceS3Key",
                "destinationBucketName", "destinationS3Key",
                true)).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: , source S3 key: sourceS3Key, destination bucket name: destinationBucketName, destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : source S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_sourceS3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", null,
                "destinationBucketName", "destinationS3Key",
                true)).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: null, destination bucket name: destinationBucketName, destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : source S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_sourceS3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "",
                "destinationBucketName", "destinationS3Key",
                true)).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: , destination bucket name: destinationBucketName, destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : destination bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_destinationBucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "soureS3Key", null,
                "destinationS3Key", true)).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: soureS3Key, destination bucket name: null, destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : destination bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_destinationBucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "soureS3Key", "",
                "destinationS3Key", true)).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: soureS3Key, destination bucket name: , destination S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : destination S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_destinationS3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "soureS3Key",
                "destinationBucketName", null,
                true)).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: soureS3Key, destination bucket name: destinationBucketName, destination S3 key: null");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : destination S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_destinationS3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "soureS3Key",
                "destinationBucketName", "",
                true)).isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. source bucket name: sourceBucketName, source S3 key: soureS3Key, destination bucket name: destinationBucketName, destination S3 key: ");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : source object not found.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_notFound() throws Exception {
        doReturn(false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "sourceS3Key",
                "destinationBucketName", "destinationS3Key",
                true)).isInstanceOf(S3ObjectNotFoundException.class).hasMessage(
                        "Source S3 object not found.  bucket name: sourceBucketName, S3 key: sourceS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : destination object already exists.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_alreadyExists() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "sourceS3Key",
                "destinationBucketName", "destinationS3Key",
                false)).isInstanceOf(S3ObjectAlreadyExistsException.class).hasMessage(
                        "Destination S3 object already exists.  bucket name: destinationBucketName, S3 key: destinationS3Key");
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : success.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_success() throws Exception {
        doReturn(true, false).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        s3Client.moveToOtherBucket("sourceBucketName", "sourceS3Key", "destinationBucketName",
                "destinationS3Key", false);
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : success(overwrite destination object).
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_successOverwrite() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doReturn(null).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        s3Client.moveToOtherBucket("sourceBucketName", "sourceS3Key", "destinationBucketName",
                "destinationS3Key", true);
    }

    /**
     * Test for moveToOtherBucket(String, String, String, String, boolean)<br>
     * Condition : exception occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testMoveToOtherBucket2_exception() throws Exception {
        doReturn(true).when(amazonS3).doesObjectExist(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectAcl(anyString(), anyString());
        doReturn(null).when(amazonS3).getObjectMetadata(anyString(), anyString());
        doThrow(SdkClientException.class).when(amazonS3).copyObject(any());
        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        assertThatThrownBy(() -> s3Client.moveToOtherBucket("sourceBucketName", "sourceS3Key",
                "destinationBucketName", "destinationS3Key", true))
                        .isInstanceOf(S3ClientSystemException.class)
                        .hasMessage("Error has occurred during move file.");
    }

    /**
     * Test for generatePreSignedUrl(String, String, long)<br>
     * Condition : Bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl1_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.generatePreSignedUrl(null, "s3Key", 600))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
    }

    /**
     * Test for generatePreSignedUrl(String, String, long)<br>
     * Condition : Bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl1_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.generatePreSignedUrl("", "s3Key", 600))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
    }

    /**
     * Test for generatePreSignedUrl(String, String, long)<br>
     * Condition : S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl1_s3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.generatePreSignedUrl("bucketName", null, 600))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
    }

    /**
     * Test for generatePreSignedUrl(String, String, long)<br>
     * Condition : S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl1_s3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.generatePreSignedUrl("bucketName", "", 600))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
    }

    /**
     * Test for generatePreSignedUrl(String, String, long)<br>
     * Condition : Success.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl1_success() throws Exception {
        doReturn(new URL("https://test-url")).when(amazonS3).generatePresignedUrl(any());
        String result = s3Client.generatePreSignedUrl("bucketName", "s3Key", 600);
        assertThat(result, equalTo("https://test-url"));
    }

    /**
     * Test for generatePreSignedUrl(String, String, long)<br>
     * Condition : Exception occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl1_exception() throws Exception {
        doThrow(SdkClientException.class).when(amazonS3).generatePresignedUrl(any());
        assertThatThrownBy(() -> s3Client.generatePreSignedUrl("bucketName", "s3Key", 600))
                .isInstanceOf(S3ClientSystemException.class)
                .hasMessageContaining("Error has occurred during generate pre-signed URL.");
    }

    /**
     * Test for generatePreSignedUrl(String, String)<br>
     * Condition : Bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl2_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.generatePreSignedUrl(null, "s3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: null, S3 key: s3Key");
    }

    /**
     * Test for generatePreSignedUrl(String, String)<br>
     * Condition : Bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl2_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.generatePreSignedUrl("", "s3Key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: , S3 key: s3Key");
    }

    /**
     * Test for generatePreSignedUrl(String, String)<br>
     * Condition : S3 key is null.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl2_s3KeyNull() throws Exception {
        assertThatThrownBy(() -> s3Client.generatePreSignedUrl("bucketName", null))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: null");
    }

    /**
     * Test for generatePreSignedUrl(String, String)<br>
     * Condition : S3 key is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl2_s3KeyEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.generatePreSignedUrl("bucketName", ""))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(
                        "Bucket name and S3 key must not be empty. bucket name: bucketName, S3 key: ");
    }

    /**
     * Test for generatePreSignedUrl(String, String)<br>
     * Condition : Success.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl2_success() throws Exception {
        doReturn(new URL("https://test-url")).when(amazonS3).generatePresignedUrl(any());
        String result = s3Client.generatePreSignedUrl("bucketName", "s3Key");
        assertThat(result, equalTo("https://test-url"));
    }

    /**
     * Test for generatePreSignedUrl(String, String)<br>
     * Condition : Exception occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testGeneratePreSignedUrl2_exception() throws Exception {
        doThrow(SdkClientException.class).when(amazonS3).generatePresignedUrl(any());
        assertThatThrownBy(() -> s3Client.generatePreSignedUrl("bucketName", "s3Key"))
                .isInstanceOf(S3ClientSystemException.class)
                .hasMessageContaining("Error has occurred during generate pre-signed URL.");
    }

    /**
     * Test for searchObjects(String, String)<br>
     * Condition : Bucket name is null.
     * 
     * @throws Exception
     */
    @Test
    public void testSearchObjects_bucketNameNull() throws Exception {
        assertThatThrownBy(() -> s3Client.searchObjects(null, "prefix"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bucket name must not be empty. bucket name: null");
    }

    /**
     * Test for searchObjects(String, String)<br>
     * Condition : Bucket name is empty.
     * 
     * @throws Exception
     */
    @Test
    public void testSearchObjects_bucketNameEmpty() throws Exception {
        assertThatThrownBy(() -> s3Client.searchObjects("", "prefix"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bucket name must not be empty. bucket name: ");
    }

    /**
     * Test for searchObjects(String, String)<br>
     * Condition : no data.
     * 
     * @throws Exception
     */
    @Test
    public void testSearchObjects_noData() throws Exception {
        doReturn(createObjectListing(false, null)).when(amazonS3)
                .listObjects(any(ListObjectsRequest.class));
        List<S3ObjectSummary> summaryList = s3Client.searchObjects("bucketName", "prefix");
        assertTrue(summaryList.isEmpty());
    }

    /**
     * Test for searchObjects(String, String)<br>
     * Condition : one data.
     * 
     * @throws Exception
     */
    @Test
    public void testSearchObjects_oneData() throws Exception {
        doReturn(createObjectListing(false, null, createS3ObjectSummary("key1", 10))).when(amazonS3)
                .listObjects(any(ListObjectsRequest.class));
        List<S3ObjectSummary> summaryList = s3Client.searchObjects("bucketName", "prefix");
        assertThat(summaryList.size(), equalTo(1));
    }

    /**
     * Test for searchObjects(String, String)<br>
     * Condition : multi data.
     * 
     * @throws Exception
     */
    @Test
    public void testSearchObjects_multiData() throws Exception {
        doReturn(createObjectListing(false, null, createS3ObjectSummary("key1", 10),
                createS3ObjectSummary("key2", 20), createS3ObjectSummary("key3", 30)))
                        .when(amazonS3).listObjects(any(ListObjectsRequest.class));
        List<S3ObjectSummary> summaryList = s3Client.searchObjects("bucketName", "prefix");
        assertThat(summaryList.size(), equalTo(3));
    }

    /**
     * Test for searchObjects(String, String)<br>
     * Condition : truncated data.
     * 
     * @throws Exception
     */
    @Test
    public void testSearchObjects_truncatedData() throws Exception {
        doReturn(
                createObjectListing(true, "marker1", createS3ObjectSummary("key1", 10),
                        createS3ObjectSummary("key2", 20), createS3ObjectSummary("key3", 30)),
                createObjectListing(true, "marker2", createS3ObjectSummary("key4", 40),
                        createS3ObjectSummary("key5", 50), createS3ObjectSummary("key6", 60)),
                createObjectListing(false, null, createS3ObjectSummary("key7", 70))).when(amazonS3)
                        .listObjects(any(ListObjectsRequest.class));
        List<S3ObjectSummary> summaryList = s3Client.searchObjects("bucketName", "prefix");
        assertThat(summaryList.size(), equalTo(7));
    }

    /**
     * Test for searchObjects(String, String)<br>
     * Condition : Exception occurs.
     * 
     * @throws Exception
     */
    @Test
    public void testSearchObjects_exception() throws Exception {
        doThrow(SdkClientException.class).when(amazonS3).listObjects(any(ListObjectsRequest.class));
        assertThatThrownBy(() -> s3Client.searchObjects("bucketName", "prefix"))
                .isInstanceOf(S3ClientSystemException.class)
                .hasMessageContaining("Error has occurred during search files.");
    }
}
