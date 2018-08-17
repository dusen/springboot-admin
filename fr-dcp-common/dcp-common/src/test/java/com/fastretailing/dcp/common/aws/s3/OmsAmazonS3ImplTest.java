/**
 * @(#)CombinationRequireTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.fastretailing.dcp.common.aws.s3.entity.DownloadResult;
import com.fastretailing.dcp.common.aws.s3.entity.UploadResult;
import com.fastretailing.dcp.common.aws.s3.service.OmsAmazonS3;
import com.fastretailing.dcp.common.aws.s3.service.OmsAmazonS3Impl;
import com.fastretailing.dcp.common.exception.S3BucketNotFoundException;
import com.fastretailing.dcp.common.exception.S3FileNotFoundException;
import com.fastretailing.dcp.common.exception.S3SystemException;
import com.fastretailing.dcp.common.threadlocal.BrandAndRegionHolder;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doThrow;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration("classpath*:com/fastretailing/dcp/common/aws/s3/test-context.xml")
})
@Slf4j
public class OmsAmazonS3ImplTest {


    @InjectMocks
    @Autowired
    OmsAmazonS3 target;

    @Mock
    private AmazonS3 client;

    private File mockFile;

    @Before
    public void setup() throws IOException{
        MockitoAnnotations.initMocks(this);

        mockFile = File.createTempFile("java", "txt");

        ReflectionTestUtils.setField(target, "client", client);

        // save the brand and region to thread local
        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(BrandAndRegionHolder.KEY_BRAND, "uq");
        pathVariableMap.put(BrandAndRegionHolder.KEY_REGION, "ca");
        BrandAndRegionHolder.setBrandAndRegionMap(pathVariableMap);
    }

    @After
    public void tearDown() throws Exception {
        mockFile.delete();
    }

    /**
     * check whether can successfully upload
     */
    @Test
    public void successfullyUploadWithFileTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        File file = new File("classpath:com/fastretailing/dcp/common/aws/s3/app.properties");
        UploadResult upload = target.upload("bucketName", "fileKey", mockFile);

        Assert.assertEquals(upload.getBucketName(), "bucketName");

    }

    /**
     * check whether can successfully upload
     */
    @Test
    public void successfullyUploadWithFileTest1() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        File file = new File("classpath:com/fastretailing/dcp/common/aws/s3/app.properties");
        UploadResult upload = target.upload("fileKey", mockFile);

        Assert.assertEquals(upload.getBucketName(), "bucketName");

    }

    /**
     * check whether IOException occurs.
     */
    @Test(expected = S3SystemException.class)
    public void uploadIOException() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        File file = new File("classpath:com/fastretailing/dcp/common/aws/s3/app.properties");
        UploadResult upload = target.upload("bucketName", "fileKey", file);

        Assert.assertEquals(upload.getBucketName(), "bucketName");

    }
    /**
     * check Exception If Bucket Is Not Exists.
     */
    @Test(expected = S3BucketNotFoundException.class)
    public void uploadExceptionIfBucketIsNotExists() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(false);
        File file = new File("classpath:com/fastretailing/dcp/common/aws/s3/app.properties");
        UploadResult upload = target.upload("bucketName", "fileKey", file);

    }

    /**
     * check whether can successfully upload
     */
    @Test
    public void successfullyUploadWithStreamTest() throws IOException {
        log.debug("clientConfiguration.isValid01");

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        UploadResult upload = target.upload("bucketName", "fileKey", new FileInputStream(mockFile));

        Assert.assertEquals(upload.getBucketName(), "bucketName");

    }
    /**
     * check whether can successfully upload
     */
    @Test
    public void successfullyUploadWithStreamTest1() throws IOException {
        log.debug("clientConfiguration.isValid01");

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        UploadResult upload = target.upload("fileKey", new FileInputStream(mockFile));

        Assert.assertEquals(upload.getBucketName(), "bucketName");

    }

    /**
     * check whether IOException occurs.
     */
    @Test(expected = S3SystemException.class)
    public void uploadSdkClientExceptionWithStream() throws IOException {
        log.debug("clientConfiguration.isValid01");

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.any())).thenThrow(SdkClientException.class);
        UploadResult upload = target.upload("bucketName", "fileKey", new FileInputStream(mockFile));

        Assert.assertEquals(upload.getBucketName(), "bucketName");

    }
    /**
     * check Exception If Bucket Is Not Exists.
     */
    @Test(expected = S3BucketNotFoundException.class)
    public void uploadExceptionIfBucketIsNotExistsWithStream() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(false);
        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(false);
        UploadResult upload = target.upload("bucketName", "fileKey", new FileInputStream(mockFile));

    }

    /**
     * check whether can successfully upload.
     */
    @Test
    public void successfullyUploadWithContentTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        UploadResult upload = target.upload("bucketName", "fileKey", "content");

        Assert.assertEquals(upload.getBucketName(), "bucketName");

    }
    /**
     * check whether can successfully upload.
     */
    @Test
    public void successfullyUploadWithContentTest1() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        UploadResult upload = target.upload("fileKey", "content");

        Assert.assertEquals(upload.getBucketName(), "bucketName");

    }

    /**
     * check whether S3SystemException occurs.
     */
    @Test(expected = S3SystemException.class)
    public void uploadSdkClientExceptionWithContentTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.any())).thenThrow(SdkClientException.class);
        UploadResult upload = target.upload("bucketName", "fileKey", "content");

    }

    /**
     * check whether S3SystemException occurs.
     */
    @Test
    public void downloadSuccessTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);
        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(new FileInputStream(mockFile));
        Mockito.when(client.getObject(Mockito.anyString(), Mockito.anyString())).thenReturn(s3Object);

        DownloadResult downloadResult = target.download("bucketName", "fileKey");

        Assert.assertEquals(downloadResult.getBucketName(), "bucketName");

    }

    /**
     * check whether S3SystemException occurs.
     */
    @Test
    public void downloadSuccessTest1() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);
        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(new FileInputStream(mockFile));
        Mockito.when(client.getObject(Mockito.anyString(), Mockito.anyString())).thenReturn(s3Object);

        DownloadResult downloadResult = target.download("fileKey");

        Assert.assertEquals(downloadResult.getBucketName(), "bucketName");

    }

    /**
     * check whether SdkClientException occurs.
     */
    @Test(expected = S3FileNotFoundException.class)
    public void downloadS3BucketNotFoundExceptionTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);
        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(new FileInputStream(mockFile));
        Mockito.when(client.getObject(Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);

        DownloadResult downloadResult = target.download("bucketName", "fileKey");
    }
    /**
     * check whether SdkClientException occurs.
     */
    @Test(expected = S3SystemException.class)
    public void downloadS3SystemExceptionTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);
        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(new FileInputStream(mockFile));
        Mockito.when(client.getObject(Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);

        DownloadResult downloadResult = target.download("bucketName", "fileKey");
    }


    /**
     * check move success.
     */
    @Test
    public void moveSuccessTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);
        S3Object s3Object = new S3Object();
        s3Object.setObjectMetadata(new ObjectMetadata());
        Mockito.when(client.getObject(Mockito.anyString(), Mockito.anyString())).thenReturn(s3Object);

        boolean result = target.move("bucketName", "fileKey", "destBucketName", "destFileKey");

        Assert.assertTrue(result);
    }
    /**
     * check move success.
     */
    @Test
    public void moveSuccessTest1() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);
        S3Object s3Object = new S3Object();
        s3Object.setObjectMetadata(new ObjectMetadata());
        Mockito.when(client.getObject(Mockito.anyString(), Mockito.anyString())).thenReturn(s3Object);

        boolean result = target.move("fileKey", "destBucketName", "destFileKey");

        Assert.assertTrue(result);
    }

    /**
     * check move success.
     */
    @Test
    public void moveSuccessTest2() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);
        S3Object s3Object = new S3Object();
        s3Object.setObjectMetadata(new ObjectMetadata());
        Mockito.when(client.getObject(Mockito.anyString(), Mockito.anyString())).thenReturn(s3Object);

        boolean result = target.move("fileKey", "destFileKey");

        Assert.assertTrue(result);
    }

    /**
     * check SdkClientException.
     */
    @Test(expected = S3SystemException.class)
    public void moveS3SystemExceptionTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);
        S3Object s3Object = new S3Object();
        s3Object.setObjectMetadata(new ObjectMetadata());
        Mockito.when(client.getObject(Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);

        boolean result = target.move("bucketName", "fileKey", "destBucketName", "destFileKey");

    }


    /**
     * check delete success.
     */
    @Test
    public void deleteSuccessTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);

        boolean result = target.delete("bucketName", "fileKey");

        Assert.assertTrue(result);
    }
    /**
     * check delete success.
     */
    @Test
    public void deleteSuccessTest1() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);

        boolean result = target.delete("fileKey");

        Assert.assertTrue(result);
    }
    /**
     * check SdkClientException.
     */
    @Test(expected = S3SystemException.class)
    public void deleteS3SystemExceptionTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        doThrow(SdkClientException.class).when(client).deleteObject(Mockito.anyString(), Mockito.anyString());

        ReflectionTestUtils.setField(target, "client", client);

        boolean result = target.delete("bucketName", "fileKey");

    }
    /**
     * check delete success.
     */
    @Test
    public void getPreSignedUrlSuccessTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);

        URL url = target.getPreSignedUrl("bucketName", "fileKey");

        Assert.assertNull(url);
    }
    /**
     * check delete success.
     */
    @Test
    public void getPreSignedUrlSuccessTest1() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.putObject(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SdkClientException.class);

        URL url = target.getPreSignedUrl("fileKey");

        Assert.assertNull(url);
    }
    /**
     * check SdkClientException.
     */
    @Test(expected = S3SystemException.class)
    public void getPreSignedUrlS3SystemExceptionTest() throws IOException {

        Mockito.when(client.doesBucketExistV2(Mockito.anyString())).thenReturn(true);
        Mockito.when(client.doesObjectExist(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(client.generatePresignedUrl(Mockito.any())).thenThrow(SdkClientException.class);

        ReflectionTestUtils.setField(target, "client", client);

        URL url = target.getPreSignedUrl("bucketName", "fileKey");

    }

    /**
     * check convertFileKey.
     */
    @Test
    public void testConvertFileKey01() throws Exception {

        Method method = OmsAmazonS3Impl.class.getDeclaredMethod("convertFileKey",
                String.class);

        // Execute.
        method.setAccessible(true);
        Object reVal = method.invoke(target, "");

        MatcherAssert.assertThat(reVal.toString(), CoreMatchers.is(""));

    }

    /**
     * check convertFileKey.
     */
    @Test
    public void testConvertFileKey02() throws Exception {

        Method method = OmsAmazonS3Impl.class.getDeclaredMethod("convertFileKey",
                String.class);

        // Execute.
        method.setAccessible(true);
        String str = null;
        Object reVal = method.invoke(target, str);

        MatcherAssert.assertThat(reVal, CoreMatchers.is(CoreMatchers.nullValue()));

    }

    /**
     * check convertFileKey.
     */
    @Test
    public void testConvertFileKey03() throws Exception {

        Method method = OmsAmazonS3Impl.class.getDeclaredMethod("convertFileKey",
                String.class);

        // Execute.
        method.setAccessible(true);
        Object reVal = method.invoke(target, "{brand}/{region}/xx/test.txt");

        MatcherAssert.assertThat(reVal.toString(), CoreMatchers.is( "uq/ca/xx/test.txt"));

    }

    /**
     * check convertFileKey.
     */
    @Test
    public void testConvertFileKey04() throws Exception {

        Method method = OmsAmazonS3Impl.class.getDeclaredMethod("convertFileKey",
                String.class);

        // Execute.
        method.setAccessible(true);
        Object reVal = method.invoke(target, "{brand}/{region}/xx/sss{region}/{brand}test.txt");

        MatcherAssert.assertThat(reVal.toString(), CoreMatchers.is("uq/ca/xx/sssca/uqtest.txt"));

    }

    /**
     * check convertFileKey.
     */
    @Test
    public void testConvertFileKey05() throws Exception {

        Method method = OmsAmazonS3Impl.class.getDeclaredMethod("convertFileKey",
                String.class);

        // Execute.
        method.setAccessible(true);
        Object reVal = method.invoke(target, "abc/bcd/test.txt");

        MatcherAssert.assertThat(reVal.toString(), CoreMatchers.is("abc/bcd/test.txt"));

    }

}
