/**
 * @(#)AlterationDataUploadServiceTest.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.web.multipart.MultipartFile;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.entity.AlterationExclusionControl;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.common.repository.AlterationExclusionControlMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionErrorDetailOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TranslationStoreCodeMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.SalesRestTemplateRepository;
import com.fastretailing.dcp.sales.common.type.TransactionCheckResultType;
import com.fastretailing.dcp.sales.importtransaction.component.CheckTransactionDataService;
import com.fastretailing.dcp.storecommon.ResultCode;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Unit test of AlterationDataUploadServiceTest class.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_sales.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class AlterationDataUploadServiceTest {

    /** Service class. */
    @Autowired
    private AlterationDataUploadService alterationDataUploadService;

    /** Sales rest template repository class. */
    @MockBean
    private SalesRestTemplateRepository salesRestTemplateRepository;

    /** Check transaction data service class. */
    @MockBean
    private CheckTransactionDataService dataCheckerService;

    /** Create a mock of access DB for common master. */
    @MockBean
    private TranslationStoreCodeMasterOptionalMapper translationStoreCodeMasterOptionalMapper;

    /** Sales transaction error detail optional mapper. */
    @MockBean
    private SalesTransactionErrorDetailOptionalMapper salesTransactionErrorDetailOptionalMapper;

    @MockBean
    private AlterationExclusionControlMapper alterationExclusionControlOptionalMapper;

    /** Business exception. */
    private BusinessException businessException;

    /**
     * Set up.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test case of get transaction zip file information.
     */
    @Test
    public void testHandleTransactionZip() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "saleTransactionDataNormal.zip";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        doReturn(1).when(salesTransactionErrorDetailOptionalMapper)
                .updateByPrimaryKeySelective(anyObject());
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(any())).thenReturn(commonStatus);
        boolean businessStatus =
                alterationDataUploadService.handleTransactionZip(multipartFile, loginUserId);
        assertEquals(true, businessStatus);
    }

    /**
     * Test case of get transaction zip file with business error.
     */
    @Test
    public void testHandleTransactionZipBusinessError() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "saleTransactionDataNormal.zip";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.BUSINESS_ERROR);
        doReturn(1).when(salesTransactionErrorDetailOptionalMapper)
                .updateByPrimaryKeySelective(anyObject());
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(any())).thenReturn(commonStatus);
        boolean businessStatus =
                alterationDataUploadService.handleTransactionZip(multipartFile, loginUserId);
        assertEquals(true, businessStatus);
    }

    /**
     * Test case of get transaction zip file with validation error.
     */
    @Test
    public void testHandleTransactionZipValidationError() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "saleTransactionDataNormal.zip";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.VALIDATION_ERROR);
        doReturn(1).when(salesTransactionErrorDetailOptionalMapper)
                .updateByPrimaryKeySelective(anyObject());
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(any())).thenReturn(commonStatus);
        boolean businessStatus =
                alterationDataUploadService.handleTransactionZip(multipartFile, loginUserId);
        assertEquals(true, businessStatus);
    }

    /**
     * Test case of get transaction zip file call api.
     */
    @Test
    public void testHandleTransactionZipCallApi() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "saleTransactionDataNormal.zip";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        SalesTransactionErrorDetail salesTransactionErrorDetail = new SalesTransactionErrorDetail();
        salesTransactionErrorDetail.setUpdateProgramId("Test");
        salesTransactionErrorDetail.setSalesTransactionErrorId("ErrorId");
        doReturn(1).when(salesTransactionErrorDetailOptionalMapper)
                .updateByPrimaryKeySelective(anyObject());
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(any())).thenReturn(commonStatus);
        boolean businessStatus =
                alterationDataUploadService.handleTransactionZip(multipartFile, loginUserId);
        assertEquals(true, businessStatus);
    }

    /**
     * Test case of get transaction zip file call api abnormal.
     */
    @Test
    public void testHandleTransactionZipCallApiAbnormal() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "saleTransactionDataNormal.zip";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        SalesTransactionErrorDetail salesTransactionErrorDetail = new SalesTransactionErrorDetail();
        salesTransactionErrorDetail.setUpdateProgramId("Test");
        salesTransactionErrorDetail.setSalesTransactionErrorId("ErrorId");
        doReturn(1).when(salesTransactionErrorDetailOptionalMapper)
                .updateByPrimaryKeySelective(anyObject());
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.ABNORMAL);
        when(salesRestTemplateRepository.callTransactionImport(any())).thenReturn(commonStatus);
        try {
            boolean businessStatus =
                    alterationDataUploadService.handleTransactionZip(multipartFile, loginUserId);
            assertEquals(false, businessStatus);
        } catch (BusinessException e) {
            businessException = e;
        }
        assertEquals(businessException.getResultObject().getDebugId(),
                MessagePrefix.E_SLS_66000125);
    }

    /**
     * Test case of get transaction json file information.
     */
    @Test
    public void testHandleTransactionJson() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "saleTransactionDataNormal.json";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(any())).thenReturn(commonStatus);
        boolean businessStatus =
                alterationDataUploadService.handleTransactionJson(multipartFile, loginUserId);
        assertEquals(true, businessStatus);
    }

    /**
     * Test case of get transaction json with business error.
     */
    @Test
    public void testHandleTransactionJsonWithBusinessError() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "saleTransactionDataNormal.json";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);

        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.BUSINESS_ERROR);
        try {
            alterationDataUploadService.handleTransactionJson(multipartFile, loginUserId);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    /**
     * Test case of get transaction json with business error.
     */
    @Test
    public void testHandleTransactionJsonWithValidationError() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "saleTransactionDataNormal.json";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.VALIDATION_ERROR);
        try {
            alterationDataUploadService.handleTransactionJson(multipartFile, loginUserId);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }

    /**
     * Test case of get transaction json file information exception.
     */
    @Test
    public void testHandlePayoffJsonIntegrityCheckError() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "payOffData.json";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        when(translationStoreCodeMasterOptionalMapper.selectByPrimaryKey("222")).thenReturn(null);
        String handResult =
                alterationDataUploadService.handlePayoffJson(multipartFile, loginUserId);
        assertEquals("integrityCheckError", handResult);
    }

    /**
     * Test case of handle pay off json.
     */
    @Test
    public void testHandlePayoffJson() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "payOffData.json";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        String handResult =
                alterationDataUploadService.handlePayoffJson(multipartFile, loginUserId);
        assertEquals("integrityCheckError", handResult);
    }

    /**
     * Test case of handle pay off json revise.
     */
    @Test
    public void testHandlePayoffJsonRevise() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "payOffData.json";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setAreaCode("123");
        translationStoreCodeMasterOptional.setAreaName("BJ");
        translationStoreCodeMasterOptional.setCreateUserId("Test");
        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        when(translationStoreCodeMasterOptionalMapper.selectByPrimaryKey(anyString()))
                .thenReturn(translationStoreCodeMasterOptional);
        AlterationExclusionControl alterationExclusionControl = new AlterationExclusionControl();
        alterationExclusionControl.setCreateProgramId("Test");
        alterationExclusionControl
                .setUpdateDatetime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));
        alterationExclusionControl.setExclusionValidTime("20180511");
        when(alterationExclusionControlOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(alterationExclusionControl);
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callAlterationPayOffData(any())).thenReturn(commonStatus);
        String handResult =
                alterationDataUploadService.handlePayoffJson(multipartFile, loginUserId);
        assertEquals("revisedByAnotherUser", handResult);
    }

    /**
     * Test case of handle pay off json normal.
     */
    @Test
    public void testHandlePayoffJsonNormal() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "payOffData.json";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setAreaCode("123");
        translationStoreCodeMasterOptional.setAreaName("BJ");
        translationStoreCodeMasterOptional.setCreateUserId("Test");
        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        when(translationStoreCodeMasterOptionalMapper.selectByPrimaryKey(anyString()))
                .thenReturn(translationStoreCodeMasterOptional);
        AlterationExclusionControl alterationExclusionControl = new AlterationExclusionControl();
        alterationExclusionControl.setCreateProgramId("Test");
        alterationExclusionControl
                .setUpdateDatetime(OffsetDateTime.parse("1900-05-11T00:00+09:00"));
        alterationExclusionControl.setExclusionValidTime("19000811");
        when(alterationExclusionControlOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(alterationExclusionControl);
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callAlterationPayOffData(any())).thenReturn(commonStatus);
        String handResult =
                alterationDataUploadService.handlePayoffJson(multipartFile, loginUserId);
        assertEquals("success", handResult);
    }

    /**
     * Test case of get transaction json file information with same user.
     */
    @Test
    @DatabaseSetup("HandlePayoffJsonTheSameUser.xml")
    @ExpectedDatabase(value = "ExpectedHandlePayoffJsonTheSameUser.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testHandlePayoffJsonBySameUser() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "payOffData.json";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);

        /**
         * Mock for DB access.
         */
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        when(translationStoreCodeMasterOptionalMapper.selectByPrimaryKey("222"))
                .thenReturn(translationStoreCodeMasterOptional);
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callAlterationPayOffData(any())).thenReturn(commonStatus);
        String handResult =
                alterationDataUploadService.handlePayoffJson(multipartFile, loginUserId);
        assertEquals("success", handResult);
    }

    /**
     * Test case of get transaction json file information with same user not exclusion.
     */
    @Test
    @DatabaseSetup("HandlePayoffJsonSameUserNoExclusion.xml")
    @ExpectedDatabase(value = "ExpectedHandlePayoffJsonSameUserNoExclusion.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testHandlePayoffJsonSameUserNoExclusion() {
        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "payOffData.json";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callAlterationPayOffData(any())).thenReturn(commonStatus);
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        when(translationStoreCodeMasterOptionalMapper.selectByPrimaryKey("222"))
                .thenReturn(translationStoreCodeMasterOptional);
        String handResult =
                alterationDataUploadService.handlePayoffJson(multipartFile, loginUserId);
        assertEquals("success", handResult);

    }

    /**
     * Test case of get transaction json file information with different user.
     */
    @Test
    @DatabaseSetup("HandlePayoffJsonNotSameUser.xml")
    @ExpectedDatabase(value = "ExpectedHandlePayoffJsonNotSameUser.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testHandlePayoffJsonNotSameUser() {

        String loginUserId = "alteration-data-upload";

        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = "payOffData.json";
        String pathString =
                classLoader.getResource(fileName).getPath().substring(1).replace("/", "\\");
        Path path = Paths.get(pathString);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println(e.getStackTrace());
        }

        MultipartFile multipartFile =
                new MockMultipartFile(fileName, fileName, "text/plain", content);
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        when(translationStoreCodeMasterOptionalMapper.selectByPrimaryKey("222"))
                .thenReturn(translationStoreCodeMasterOptional);
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callAlterationPayOffData(any())).thenReturn(commonStatus);
        try {
            String handResult =
                    alterationDataUploadService.handlePayoffJson(multipartFile, loginUserId);
            assertEquals("success", handResult);
        } catch (BusinessException e) {
            businessException = e;
        }
    }
}
