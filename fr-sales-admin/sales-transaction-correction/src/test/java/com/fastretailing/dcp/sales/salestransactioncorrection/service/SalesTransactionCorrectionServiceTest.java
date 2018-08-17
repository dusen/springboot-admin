
/**
 * @(#)SalesTransactionCorrectionServiceTest.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesOrderInformationOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionHeaderDetailOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionHistoryOptional;
import com.fastretailing.dcp.sales.common.repository.AlterationExclusionControlMapper;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesOrderInformationOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesTransactionHeaderDetailOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionErrorDetailOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionHistoryOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TenderAndTransformMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.SalesRestTemplateRepository;
import com.fastretailing.dcp.sales.common.type.TransactionCheckResultType;
import com.fastretailing.dcp.sales.importtransaction.component.CheckTransactionDataService;
import com.fastretailing.dcp.sales.importtransaction.dto.CreateTransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.repository.StoreGeneralPurposeMasterMapper;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.SalesTransactionCorrectionForm;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportTransactionImportData;
import com.fastretailing.dcp.storecommon.ResultCode;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;

/**
 * Sales transaction correction service test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesTransactionCorrectionServiceTest {

    /** Service class write off confirm. */
    @SpyBean
    private SalesTransactionCorrectionServiceImpl salesTransactionCorrectionService;

    /** Sales transaction history optional mapper. */
    @MockBean
    private SalesTransactionHistoryOptionalMapper salesTransactionHistoryOptionalMapper;

    /** Error evacuation sales transaction header detail optional mapper. */
    @MockBean
    private ErrorEvacuationSalesTransactionHeaderDetailOptionalMapper errorEvacuationHeaderDetailMapper;

    /** Check transaction data service. */
    @MockBean
    private CheckTransactionDataService dataCheckerService;

    /** Sales rest template repository. */
    @MockBean
    private SalesRestTemplateRepository salesRestTemplateRepository;

    /** Error evacuation sales order information optional mapper. */
    @MockBean
    private ErrorEvacuationSalesOrderInformationOptionalMapper errorEvacuationSalesOrderInformationOptionMapper;

    /** Sales transaction error detail optional mapper. */
    @MockBean
    private SalesTransactionErrorDetailOptionalMapper salesTransactionErrorDetailOptionalMapper;

    /** Sales transaction error detail optional mapper. */
    @MockBean
    private TenderAndTransformMasterOptionalMapper tenderAndTransformMasterOptionalMapper;

    /** Transaction import json. */
    private String transactionImportJson = null;

    /** Transaction import json file name. */
    public static final String TRANSACTION_IMPORT_JSON_FILE_NAME = "transactionImport.json";

    /** Local message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** Common code master mapper. */
    @MockBean
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Common code master mapper. */
    @MockBean
    private StoreGeneralPurposeMasterMapper storeGeneralPurposeMasterMapper;

    /** Alteration exclusion control mapper. */
    @MockBean
    private AlterationExclusionControlMapper alterationExclusionControlMapper;

    /**
     * Set up.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        CommonCodeMaster commonCodeMaster = new CommonCodeMaster();
        commonCodeMaster.setTypeId("asa");
        commonCodeMaster.setName1("1");
        List<CommonCodeMaster> commonCodeMasterList = new ArrayList<>();
        commonCodeMasterList.add(commonCodeMaster);
        when(commonCodeMasterMapper.selectByCondition(anyObject()))
                .thenReturn(commonCodeMasterList);
        File jsonFile = ResourceUtils
                .getFile(ResourceUtils.CLASSPATH_URL_PREFIX + TRANSACTION_IMPORT_JSON_FILE_NAME);

        transactionImportJson = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);
    }

    /**
     * Test get sales transaction header information1.
     */
    @Test
    public void testGetSalesTransactionHeaderInformation1() {
        CommonCodeMaster commonCodeMaster = new CommonCodeMaster();
        commonCodeMaster.setTypeId("asa");
        commonCodeMaster.setName1("1");
        List<CommonCodeMaster> commonCodeMasterList = new ArrayList<>();
        commonCodeMasterList.add(commonCodeMaster);
        when(commonCodeMasterMapper.selectByCondition(anyObject()))
                .thenReturn(commonCodeMasterList);
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setSalesTransactionErrorId("12");
        SalesTransactionHistoryOptional salesTransactionHistoryOptional =
                new SalesTransactionHistoryOptional();
        salesTransactionHistoryOptional.setTransactionId("123");
        salesTransactionHistoryOptional.setSalesTransactionData(transactionImportJson);
        List<SalesTransactionHistoryOptional> salesTransactionHistoryList = new ArrayList<>();
        salesTransactionHistoryList.add(salesTransactionHistoryOptional);
        when(salesTransactionHistoryOptionalMapper.selectByCondition(any()))
                .thenReturn(salesTransactionHistoryList);
        ErrorEvacuationSalesTransactionHeaderDetailOptional errorDetailOptional =
                new ErrorEvacuationSalesTransactionHeaderDetailOptional();
        errorDetailOptional.setOperatorCode("2");
        errorDetailOptional.setDataCreationBusinessDate("2018-10-26");
        errorDetailOptional
                .setOrderCancelledDateTime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));
        errorDetailOptional
                .setOrderStatusLastUpdateDateTime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));
        errorDetailOptional.setDataCreationDateTime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));
        errorDetailOptional.setOrderStatusUpdateDate("2018-10-26");
        errorDetailOptional.setSalesTransactionDiscountAmountRate(new BigDecimal(123));
        List<ErrorEvacuationSalesTransactionHeaderDetailOptional> errorEvacuationHeaderList =
                new ArrayList<>();
        errorEvacuationHeaderList.add(errorDetailOptional);
        when(errorEvacuationHeaderDetailMapper
                .selectErrorEvacuationHeaderDetailBySalesTransactionId(any()))
                        .thenReturn(errorEvacuationHeaderList);
        salesTransactionCorrectionService
                .getSalesTransactionHeaderInformation(salesTransactionCorrectionForm);
    }

    /**
     * Test get sales transaction header information2.
     */
    @Test
    public void testGetSalesTransactionHeaderInformation2() {

        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setSalesTransactionErrorId("12");
        when(salesTransactionHistoryOptionalMapper.selectByCondition(any())).thenReturn(null);
        salesTransactionCorrectionService
                .getSalesTransactionHeaderInformation(salesTransactionCorrectionForm);
    }

    /**
     * Test get sales transaction header information3.
     */
    @Test
    public void testGetSalesTransactionHeaderInformation3() {

        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setSalesTransactionErrorId("12");
        SalesTransactionHistoryOptional salesTransactionHistoryOptional =
                new SalesTransactionHistoryOptional();
        salesTransactionHistoryOptional.setTransactionId("123");
        salesTransactionHistoryOptional.setSalesTransactionData(transactionImportJson);
        List<SalesTransactionHistoryOptional> salesTransactionHistoryList = new ArrayList<>();
        salesTransactionHistoryList.add(salesTransactionHistoryOptional);
        when(salesTransactionHistoryOptionalMapper.selectByCondition(any()))
                .thenReturn(salesTransactionHistoryList);
        when(errorEvacuationHeaderDetailMapper
                .selectErrorEvacuationHeaderDetailBySalesTransactionId(any())).thenReturn(null);
        salesTransactionCorrectionService
                .getSalesTransactionHeaderInformation(salesTransactionCorrectionForm);

    }

    /**
     * Test report transaction by reflect on all.
     */
    @Test
    public void testReportTransactionByReflectOnAll() {
        ImportTransactionImportData transactionImportData = new ImportTransactionImportData();
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);

        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);

        salesTransactionCorrectionService.reportTransactionByReflectOnAll(transactionImportData,
                "12", "DK");

        verify(dataCheckerService, times(1)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(2)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(2))
                .updateDataAlterationStatusType(anyString(), anyString());
    }

    /**
     * Test report transaction by reflect only on receipt.
     */
    @Test
    public void testReportTransactionByReflectOnlyOnReceipt() {
        ImportTransactionImportData transactionImportData = new ImportTransactionImportData();
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);

        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);
        salesTransactionCorrectionService
                .reportTransactionByReflectOnlyOnReceipt(transactionImportData, "12", "DK");
        verify(dataCheckerService, times(2)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(3)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(3))
                .updateDataAlterationStatusType(anyString(), anyString());
    }

    /**
     * Test report transaction by upload without correction.
     */
    @Test
    public void textReportTransactionByUploadWithoutCorrection() {
        ImportTransactionImportData transactionImportData = new ImportTransactionImportData();
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);

        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);

        salesTransactionCorrectionService
                .reportTransactionByUploadWithoutCorrection(transactionImportData, "12", "DK");

        verify(dataCheckerService, times(1)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(2)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(2))
                .updateDataAlterationStatusType(anyString(), anyString());
    }

    /**
     * Test report transaction by reflect only on ims linkage.
     */
    @Test
    public void testReportTransactionByReflectOnlyOnIMSLinkage() {
        ImportTransactionImportData transactionImportData = new ImportTransactionImportData();
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);
        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);
        salesTransactionCorrectionService
                .reportTransactionByReflectOnlyOnIMSLinkage(transactionImportData, "12", "DK");
        verify(dataCheckerService, times(2)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(3)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(3))
                .updateDataAlterationStatusType(anyString(), anyString());
    }

    /**
     * Test get transaction id list.
     */
    @Test
    public void testGetTransactionIdList() {
        salesTransactionCorrectionService.getTransactionIdList("a");
        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(new ArrayList<>());
        verify(errorEvacuationSalesOrderInformationOptionMapper, times(1))
                .selectByCondition(anyObject());
    }

    /**
     * Test upload by reflect on all.
     */
    @Test
    public void testUploadByReflectOnAll() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setUploadOption(0);
        salesTransactionCorrectionForm.setTransactionImportData(importTransactionImportData);
        salesTransactionCorrectionForm.setSalesTransactionErrorId("12");
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);
        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);
        salesTransactionCorrectionService.upload(salesTransactionCorrectionForm);
        verify(dataCheckerService, times(1)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(2)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(2))
                .updateDataAlterationStatusType(anyString(), anyString());
    }

    /**
     * Test upload by upload without correction.
     */
    @Test
    public void testUploadByUploadWithoutCorrection() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setUploadOption(1);
        salesTransactionCorrectionForm.setTransactionImportData(importTransactionImportData);
        salesTransactionCorrectionForm.setSalesTransactionErrorId("12");
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);
        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);
        salesTransactionCorrectionService.upload(salesTransactionCorrectionForm);
        verify(dataCheckerService, times(2)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(3)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(3))
                .updateDataAlterationStatusType(anyString(), anyString());
    }

    /**
     * Test upload by reflect only on ims linkage.
     */
    @Test
    public void testByReflectOnlyOnIMSLinkage() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setUploadOption(2);
        salesTransactionCorrectionForm.setTransactionImportData(importTransactionImportData);
        salesTransactionCorrectionForm.setSalesTransactionErrorId("12");
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);
        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);
        salesTransactionCorrectionService.upload(salesTransactionCorrectionForm);
        verify(dataCheckerService, times(1)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(2)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(2))
                .updateDataAlterationStatusType(anyString(), anyString());
    }

    /**
     * Test upload by reflect only receipt.
     */
    @Test
    public void testByReflectOnlyOnReceipt() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setUploadOption(3);
        salesTransactionCorrectionForm.setTransactionImportData(importTransactionImportData);
        salesTransactionCorrectionForm.setSalesTransactionErrorId("12");
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);
        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);
        salesTransactionCorrectionService.upload(salesTransactionCorrectionForm);
        verify(dataCheckerService, times(2)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(3)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(3))
                .updateDataAlterationStatusType(anyString(), anyString());
    }

    /**
     * Test report api fail.
     */
    @Test
    public void testReportApiFail() {
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.ABNORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(commonStatus);
        CreateTransactionImportData createTransactionImportData = new CreateTransactionImportData();
        try {
            salesTransactionCorrectionService.callTransactionApi(createTransactionImportData, "12",
                    true);
        } catch (BusinessException e) {
            ResultObject resultObject = new ResultObject();
            resultObject.setDebugId(MessagePrefix.E_SLS_66000112_DATA_UPLOAD_FAILED);
            resultObject.setMessage(localeMessageSource
                    .getMessage(MessagePrefix.E_SLS_66000112_DATA_UPLOAD_FAILED));

            assertThat(e.getResultObject(), is(resultObject));
        }
    }

    /**
     * Test report api fail.
     */
    @Test
    public void testReportApiFailBydelete() {
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.ABNORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(commonStatus);
        CreateTransactionImportData createTransactionImportData = new CreateTransactionImportData();
        try {
            salesTransactionCorrectionService.callTransactionApi(createTransactionImportData, "12",
                    false);
        } catch (BusinessException e) {
            ResultObject resultObject = new ResultObject();
            resultObject.setDebugId(MessagePrefix.E_SLS_66000105_DATA_DELETE_FAILED);
            resultObject.setMessage(localeMessageSource
                    .getMessage(MessagePrefix.E_SLS_66000105_DATA_DELETE_FAILED));

            assertThat(e.getResultObject(), is(resultObject));
        }
    }

    /**
     * Test by back.
     */
    @Test
    public void back() {
        ImportTransactionImportData transactionImportData = new ImportTransactionImportData();
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setTransactionImportData(transactionImportData);
        salesTransactionCorrectionForm.setBusinessDate("20137228");
        salesTransactionCorrectionForm.setCashRegisterNo("1111");
        salesTransactionCorrectionForm.setStoreCode("123456");
        salesTransactionCorrectionService.back(salesTransactionCorrectionForm);
    }

    /**
     * Test by audit.
     */
    @Test
    public void audit() {
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        ImportTransactionImportData transactionImportData = new ImportTransactionImportData();
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setTransactionImportData(transactionImportData);
        salesTransactionCorrectionForm.setBusinessDate("20137228");
        salesTransactionCorrectionForm.setCashRegisterNo("1111");
        salesTransactionCorrectionForm.setSalesTransactionErrorId("123456");
        salesTransactionCorrectionForm.setLoginUserId("dk");
        salesTransactionCorrectionForm.setStoreCode("123456");
        salesTransactionCorrectionService.audit(salesTransactionCorrectionForm);
    }

    /**
     * Test Delete by option.
     */
    @Test
    public void testDeleteByReportTransactionByReflectOnAll() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setDeleteOption(0);
        salesTransactionCorrectionForm.setTransactionImportData(importTransactionImportData);
        salesTransactionCorrectionForm.setSalesTransactionErrorId("12");
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);
        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);
        salesTransactionCorrectionService.deleteByOption(salesTransactionCorrectionForm);
        verify(dataCheckerService, times(1)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(1)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(1))
                .updateDataAlterationStatusType(anyString(), anyString());
    }

    /**
     * Test Delete by option.
     */
    @Test
    public void testDeleteByReportTransactionByReflectOnlyOnIMSLinkage() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setDeleteOption(1);
        salesTransactionCorrectionForm.setTransactionImportData(importTransactionImportData);
        salesTransactionCorrectionForm.setSalesTransactionErrorId("12");
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);
        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);
        salesTransactionCorrectionService.deleteByOption(salesTransactionCorrectionForm);
        verify(dataCheckerService, times(1)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(2)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(2))
                .updateDataAlterationStatusType(anyString(), anyString());
    }

    /**
     * Test Delete by option.
     */
    @Test
    public void testDeleteByReportTransactionByReflectOnlyOnReceipt() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setDeleteOption(2);
        salesTransactionCorrectionForm.setTransactionImportData(importTransactionImportData);
        salesTransactionCorrectionForm.setSalesTransactionErrorId("12");
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);
        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);
        salesTransactionCorrectionService.deleteByOption(salesTransactionCorrectionForm);
        verify(dataCheckerService, times(1)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(2)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(2))
                .updateDataAlterationStatusType(anyString(), anyString());
    }

    /**
     * Test Delete by option.
     */
    @Test
    public void testDelete() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        salesTransactionCorrectionForm.setDeleteOption(0);
        salesTransactionCorrectionForm.setTransactionImportData(importTransactionImportData);
        salesTransactionCorrectionForm.setSalesTransactionErrorId("12");
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        List<ErrorEvacuationSalesOrderInformationOptional> transctionList = new ArrayList<>();
        ErrorEvacuationSalesOrderInformationOptional eesoio =
                new ErrorEvacuationSalesOrderInformationOptional();
        eesoio.setTransactionId("12");
        transctionList.add(eesoio);
        when(errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(anyObject()))
                .thenReturn(transctionList);
        salesTransactionCorrectionService.delete(salesTransactionCorrectionForm);
        verify(dataCheckerService, times(1)).checkTransactionData(anyObject(), anyString(),
                anyString());
        verify(salesRestTemplateRepository, times(1)).callTransactionImport(anyObject());
        verify(salesTransactionErrorDetailOptionalMapper, times(1))
                .updateDataAlterationStatusType(anyString(), anyString());
    }
}
