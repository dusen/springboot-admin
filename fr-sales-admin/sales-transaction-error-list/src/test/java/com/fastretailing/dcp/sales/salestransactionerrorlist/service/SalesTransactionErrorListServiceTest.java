/**
 * @(#)SalesTransactionErrorListServiceTest.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import java.io.File;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.entity.AlterationExclusionControl;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesOrderInformationOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionDiscountOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionHeaderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionTaxOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionTotalAmountOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesOrderInformationOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionDetailAndInfo;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionDiscountOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionHeaderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionTaxOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionTenderAndInfo;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionTotalAmountOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionHistoryOptional;
import com.fastretailing.dcp.sales.common.repository.AlterationExclusionControlMapper;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesOrderInformationOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesTransactionDiscountOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesTransactionHeaderOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesTransactionTaxOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesTransactionTotalAmountOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesErrorSalesOrderInformationOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesErrorSalesTransactionDiscountOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesErrorSalesTransactionHeaderOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesErrorSalesTransactionTaxOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesErrorSalesTransactionTotalAmountOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionErrorDetailAndInfoMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionErrorDetailOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionHistoryOptionalMapper;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.SalesRestTemplateRepository;
import com.fastretailing.dcp.sales.common.type.TransactionCheckResultType;
import com.fastretailing.dcp.sales.importtransaction.component.CheckTransactionDataService;
import com.fastretailing.dcp.sales.importtransaction.dto.CreateTransactionImportData;
import com.fastretailing.dcp.sales.salestransactionerrorlist.form.SalesTransactionError;
import com.fastretailing.dcp.sales.salestransactionerrorlist.form.SalesTransactionErrorListForm;
import com.fastretailing.dcp.storecommon.ResultCode;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesTransactionErrorListServiceTest {

    /** Service class write off confirm. */
    @SpyBean
    private SalesTransactionErrorListServiceImpl salesTransactionErrorListService;

    /** Common code master mapper. */
    @MockBean
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Sales transaction error detail optional mapper. */
    @MockBean
    private SalesTransactionErrorDetailOptionalMapper salesTransactionErrorDetailOptionalMapper;

    /** Ateration exclusion control mapper. */
    @MockBean
    private AlterationExclusionControlMapper alterationExclusionControlMapper;

    /** Sales transaction history mapper. */
    @MockBean
    private SalesTransactionHistoryOptionalMapper salesTransactionHistoryOptionalMapper;

    /** Sales rest template repository. */
    @MockBean
    private SalesRestTemplateRepository salesRestTemplateRepository;

    /** Sales error sales order information mapper. */
    @MockBean
    private SalesErrorSalesOrderInformationOptionalMapper salesErrorSalesOrderInformationMapper;

    /** Sales error sales transaction header mapper. */
    @MockBean
    private SalesErrorSalesTransactionHeaderOptionalMapper salesErrorSalesTransactionHeaderMapper;

    /** Sales error sales transaction detail information mapper. */
    @MockBean
    private SalesErrorSalesTransactionDiscountOptionalMapper salesErrorSalesTransactionDiscountMapper;

    /** Error evacuation sales transaction discount mapper. */
    @MockBean
    private ErrorEvacuationSalesTransactionDiscountOptionalMapper errorEvacuationSalesTransactionDiscountMapper;

    /** Sales error sales transaction detail information mapper. */
    @MockBean
    private SalesErrorSalesTransactionTaxOptionalMapper salesErrorSalesTransactionTaxMapper;

    /** Error evacuation sales transaction tax mapper. */
    @MockBean
    private ErrorEvacuationSalesTransactionTaxOptionalMapper errorEvacuationSalesTransactionTaxMapper;

    /** Sales transaction error detail and info mapper. */
    @MockBean
    private SalesTransactionErrorDetailAndInfoMapper salesTransactionErrorDetailAndInfoMapper;

    /** Error evacuation sales order information mapper. */
    @MockBean
    private ErrorEvacuationSalesOrderInformationOptionalMapper errorEvacuationSalesOrderInformationMapper;

    /** Sales error sales transaction total amount mapper. */
    @MockBean
    private SalesErrorSalesTransactionTotalAmountOptionalMapper salesErrorSalesTransactionTotalAmountMapper;

    /** Error evacuation sales transaction total amount mapper. */
    @MockBean
    private ErrorEvacuationSalesTransactionTotalAmountOptionalMapper errorEvacuationSalesTransactionTotalAmountMapper;

    /** Error evacuation sales transaction header mapper. */
    @MockBean
    private ErrorEvacuationSalesTransactionHeaderOptionalMapper errorEvacuationSalesTransactionHeaderMapper;

    @Autowired
    private ObjectMapper objectMapper;

    /** Check transaction data service. */
    @MockBean
    private CheckTransactionDataService dataCheckerService;

    /**
     * Test case of get initialize information success.
     */
    @Test
    public void testGetInitializeInformationSuccess() {

        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster errorContentsMaster = new CommonCodeMaster();
        errorContentsMaster.setTypeId("error_type");
        errorContentsMaster.setTypeValue("019");
        errorContentsMaster.setName1("JP1");
        List<CommonCodeMaster> errorContentsList = new ArrayList<>();
        errorContentsList.add(errorContentsMaster);

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(errorContentsList);

        SalesTransactionErrorListForm rtnSalesTransactionErrorListForm =
                salesTransactionErrorListService
                        .getInitializeInformation(new SalesTransactionErrorListForm());

        assertEquals(1, rtnSalesTransactionErrorListForm.getCountryList().size());
        assertEquals(1, rtnSalesTransactionErrorListForm.getBrandList().size());

        assertEquals("ABC",
                rtnSalesTransactionErrorListForm.getSalesTransactionTypeMap().get("018"));
        assertEquals("JP1", rtnSalesTransactionErrorListForm.getErrorContentsMap().get("019"));

    }

    /**
     * Test case of get initialize information error contents list null.
     */
    @Test(expected = BusinessException.class)
    public void testGetInitializeInformationErrorContensListNull() {

        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(null);

        salesTransactionErrorListService
                .getInitializeInformation(new SalesTransactionErrorListForm());

    }

    /**
     * Test case of get initialize information transaction type list null.
     */
    @Test(expected = BusinessException.class)
    public void testGetInitializeInformationTransactionTypeListNull() {

        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(null);

        salesTransactionErrorListService
                .getInitializeInformation(new SalesTransactionErrorListForm());

    }

    /**
     * Test case of get initialize information brand list null.
     */
    @Test(expected = BusinessException.class)
    public void testGetInitializeInformationBrandListNull() {

        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(null);

        salesTransactionErrorListService
                .getInitializeInformation(new SalesTransactionErrorListForm());

    }

    /**
     * Test case of get initialize information country list null.
     */
    @Test(expected = BusinessException.class)
    public void testGetInitializeInformationCountryListNull() {

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(null);

        salesTransactionErrorListService
                .getInitializeInformation(new SalesTransactionErrorListForm());

    }

    /**
     * Test case of get sales transaction error list success.
     */
    @Test
    public void testGetSalesTransactionErrorListSuccess() {

        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        salesTransactionErrorListForm.setSystemBrandCode("UQ");
        salesTransactionErrorListForm.setSystemCountryCode("017");
        salesTransactionErrorListForm.setStoreCode("002");
        salesTransactionErrorListForm.setReceiptNo("a001");
        salesTransactionErrorListForm.setCashRegisterNo("1001");
        salesTransactionErrorListForm.setSalesTransactionType("001");
        salesTransactionErrorListForm.setBusinessDateFrom("2017/10/10");
        salesTransactionErrorListForm.setBusinessDateTo("2017/10/11");
        salesTransactionErrorListForm.setErrorContents("business error");

        SalesTransactionErrorDetail firstSalesTransactionErrorDetail =
                new SalesTransactionErrorDetail();

        firstSalesTransactionErrorDetail.setSystemBrandCode("uq");
        firstSalesTransactionErrorDetail.setSystemCountryCode("017");
        firstSalesTransactionErrorDetail.setViewStoreCode("002");
        firstSalesTransactionErrorDetail.setStoreName("東京００２");
        firstSalesTransactionErrorDetail.setCashRegisterNo("a001");
        firstSalesTransactionErrorDetail.setReceiptNo("1001");
        firstSalesTransactionErrorDetail.setSalesTransactionType("1001");
        firstSalesTransactionErrorDetail
                .setDataCreationDateTime(OffsetDateTime.parse("2018-05-10T00:00+09:00"));
        firstSalesTransactionErrorDetail.setOrderStatusUpdateDate("20191010");
        firstSalesTransactionErrorDetail.setErrorType("1");

        SalesTransactionErrorDetail secondSalesTransactionErrorDetail =
                new SalesTransactionErrorDetail();

        secondSalesTransactionErrorDetail.setSystemBrandCode("uq");
        secondSalesTransactionErrorDetail.setSystemCountryCode("017");
        secondSalesTransactionErrorDetail.setViewStoreCode("002");
        secondSalesTransactionErrorDetail.setStoreName("東京００２");
        secondSalesTransactionErrorDetail.setCashRegisterNo("a001");
        secondSalesTransactionErrorDetail.setReceiptNo("1001");
        secondSalesTransactionErrorDetail.setSalesTransactionType("2001");
        secondSalesTransactionErrorDetail
                .setDataCreationDateTime(OffsetDateTime.parse("2018-05-10T00:00+09:00"));
        secondSalesTransactionErrorDetail.setOrderStatusUpdateDate("20191010");
        secondSalesTransactionErrorDetail.setErrorType("1");
        SalesTransactionErrorDetail thirdSalesTransactionErrorDetail =
                new SalesTransactionErrorDetail();

        thirdSalesTransactionErrorDetail.setSystemBrandCode("uq");
        thirdSalesTransactionErrorDetail.setSystemCountryCode("017");
        thirdSalesTransactionErrorDetail.setViewStoreCode("002");
        thirdSalesTransactionErrorDetail.setStoreName("東京００２");
        thirdSalesTransactionErrorDetail.setCashRegisterNo("a001");
        thirdSalesTransactionErrorDetail.setReceiptNo("1001");
        thirdSalesTransactionErrorDetail.setSalesTransactionType("3002");
        thirdSalesTransactionErrorDetail
                .setDataCreationDateTime(OffsetDateTime.parse("2018-05-10T00:00+09:00"));
        thirdSalesTransactionErrorDetail.setOrderStatusUpdateDate("20191010");
        thirdSalesTransactionErrorDetail.setErrorType("1");

        List<SalesTransactionErrorDetail> salesTransactionErrorDetailList = new ArrayList<>();
        salesTransactionErrorDetailList.add(firstSalesTransactionErrorDetail);
        salesTransactionErrorDetailList.add(secondSalesTransactionErrorDetail);
        salesTransactionErrorDetailList.add(thirdSalesTransactionErrorDetail);
        when(salesTransactionErrorDetailOptionalMapper
                .selectSalesTransactionErrorDetailByCondition(anyObject()))
                        .thenReturn(salesTransactionErrorDetailList);

        SalesTransactionErrorListForm rtnSalesTransactionErrorListForm =
                salesTransactionErrorListService
                        .getSalesTransactionErrorList(salesTransactionErrorListForm);

        assertEquals("UQ", rtnSalesTransactionErrorListForm.getSystemBrandCode());
        assertEquals("017", rtnSalesTransactionErrorListForm.getSystemCountryCode());
        assertEquals("002", rtnSalesTransactionErrorListForm.getStoreCode());
        assertEquals("1001", rtnSalesTransactionErrorListForm.getCashRegisterNo());
        assertEquals("a001", rtnSalesTransactionErrorListForm.getReceiptNo());
        assertEquals("001", rtnSalesTransactionErrorListForm.getSalesTransactionType());
        assertEquals("2017/10/10", rtnSalesTransactionErrorListForm.getBusinessDateFrom());
        assertEquals("2017/10/11", rtnSalesTransactionErrorListForm.getBusinessDateTo());
        assertEquals("business error", rtnSalesTransactionErrorListForm.getErrorContents());

        SalesTransactionError firstSalesTransactionError =
                rtnSalesTransactionErrorListForm.getSalesTransactionErrorList().get(0);
        assertEquals("uq", firstSalesTransactionError.getBrandCode());
        assertEquals("017", firstSalesTransactionError.getCountryCode());
        assertEquals("002", firstSalesTransactionError.getStoreCode());
        assertEquals("東京００２", firstSalesTransactionError.getStoreName());
        assertEquals("a001", firstSalesTransactionError.getCashRegisterNo());
        assertEquals("1001", firstSalesTransactionError.getReceiptNo());
        assertEquals("2018/05/10 00:00:00", firstSalesTransactionError.getDataCreationDate());
        assertEquals("20191010", firstSalesTransactionError.getBusinessDate());

        SalesTransactionError secondSalesTransactionError =
                rtnSalesTransactionErrorListForm.getSalesTransactionErrorList().get(1);
        assertEquals("uq", secondSalesTransactionError.getBrandCode());
        assertEquals("017", secondSalesTransactionError.getCountryCode());
        assertEquals("002", secondSalesTransactionError.getStoreCode());
        assertEquals("東京００２", secondSalesTransactionError.getStoreName());
        assertEquals("a001", secondSalesTransactionError.getCashRegisterNo());
        assertEquals("1001", secondSalesTransactionError.getReceiptNo());
        assertEquals("2018/05/10 00:00:00", secondSalesTransactionError.getDataCreationDate());
        assertEquals("20191010", secondSalesTransactionError.getBusinessDate());

        SalesTransactionError thirdSalesTransactionError =
                rtnSalesTransactionErrorListForm.getSalesTransactionErrorList().get(2);
        assertEquals("uq", thirdSalesTransactionError.getBrandCode());
        assertEquals("017", thirdSalesTransactionError.getCountryCode());
        assertEquals("002", thirdSalesTransactionError.getStoreCode());
        assertEquals("東京００２", thirdSalesTransactionError.getStoreName());
        assertEquals("a001", thirdSalesTransactionError.getCashRegisterNo());
        assertEquals("1001", thirdSalesTransactionError.getReceiptNo());
        assertEquals("2018/05/10 00:00:00", thirdSalesTransactionError.getDataCreationDate());
        assertEquals("20191010", thirdSalesTransactionError.getBusinessDate());

    }

    /**
     * Test case of get sales transaction error list success date input.
     */
    @Test
    public void testGetSalesTransactionErrorListSuccessDateInput() {

        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        salesTransactionErrorListForm.setSystemBrandCode("UQ");
        salesTransactionErrorListForm.setSystemCountryCode("017");
        salesTransactionErrorListForm.setStoreCode("002");
        salesTransactionErrorListForm.setReceiptNo("a001");
        salesTransactionErrorListForm.setCashRegisterNo("1001");
        salesTransactionErrorListForm.setSalesTransactionType("001");
        salesTransactionErrorListForm.setDataCreationDateFrom("2017/11/01");
        salesTransactionErrorListForm.setDataCreationDateTo("2017/11/02");
        salesTransactionErrorListForm.setBusinessDateFrom("2017/10/10");
        salesTransactionErrorListForm.setBusinessDateTo("2017/10/11");

        SalesTransactionErrorDetail firstSalesTransactionErrorDetail =
                new SalesTransactionErrorDetail();

        firstSalesTransactionErrorDetail.setSystemBrandCode("uq");
        firstSalesTransactionErrorDetail.setSystemCountryCode("017");
        firstSalesTransactionErrorDetail.setViewStoreCode("002");
        firstSalesTransactionErrorDetail.setStoreName("東京００２");
        firstSalesTransactionErrorDetail.setCashRegisterNo("a001");
        firstSalesTransactionErrorDetail.setReceiptNo("1001");
        firstSalesTransactionErrorDetail.setSalesTransactionType("1001");
        firstSalesTransactionErrorDetail
                .setDataCreationDateTime(OffsetDateTime.parse("2018-05-10T00:00+09:00"));
        firstSalesTransactionErrorDetail.setOrderStatusUpdateDate("20191010");
        firstSalesTransactionErrorDetail.setErrorType("1");

        List<SalesTransactionErrorDetail> salesTransactionErrorDetailList = new ArrayList<>();
        salesTransactionErrorDetailList.add(firstSalesTransactionErrorDetail);

        when(salesTransactionErrorDetailOptionalMapper
                .selectSalesTransactionErrorDetailByCondition(anyObject()))
                        .thenReturn(salesTransactionErrorDetailList);

        salesTransactionErrorListService
                .getSalesTransactionErrorList(salesTransactionErrorListForm);
    }

    @Test
    public void textCSVFileList() {
        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        SalesTransactionError salesTransactionError = new SalesTransactionError();
        salesTransactionError.setSalesTransactionErrorId("1");
        salesTransactionError.setCheckFlag(1);
        List<SalesTransactionError> salesTransactionErrors = new ArrayList<>();
        salesTransactionErrors.add(salesTransactionError);
        salesTransactionErrorListForm.setSalesTransactionErrorList(salesTransactionErrors);

        SalesErrorSalesOrderInformationOptional salesErrorSalesOrderInformation =
                new SalesErrorSalesOrderInformationOptional();
        salesErrorSalesOrderInformation.setTransactionId("1");
        salesErrorSalesOrderInformation.setCreateUserId("1");
        List<SalesErrorSalesOrderInformationOptional> salesErrorSalesOrderInformations =
                new ArrayList<>();
        salesErrorSalesOrderInformations.add(salesErrorSalesOrderInformation);
        when(salesErrorSalesOrderInformationMapper.selectByCondition(anyObject()))
                .thenReturn(salesErrorSalesOrderInformations);

        SalesErrorSalesTransactionHeaderOptional salesErrorSalesTransactionHeader =
                new SalesErrorSalesTransactionHeaderOptional();
        salesErrorSalesTransactionHeader.setTransactionId("1");
        salesErrorSalesTransactionHeader.setCreateUserId("1");
        List<SalesErrorSalesTransactionHeaderOptional> salesErrorSalesTransactionHeaders =
                new ArrayList<>();
        salesErrorSalesTransactionHeaders.add(salesErrorSalesTransactionHeader);
        when(salesErrorSalesTransactionHeaderMapper.selectByCondition(anyObject()))
                .thenReturn(salesErrorSalesTransactionHeaders);

        ErrorEvacuationSalesTransactionHeaderOptional errorEvacuationSalesTransactionHeader =
                new ErrorEvacuationSalesTransactionHeaderOptional();
        errorEvacuationSalesTransactionHeader.setTransactionId("1");
        errorEvacuationSalesTransactionHeader.setCreateUserId("1");
        List<ErrorEvacuationSalesTransactionHeaderOptional> errorEvacuationSalesTransactionHeaders =
                new ArrayList<>();
        errorEvacuationSalesTransactionHeaders.add(errorEvacuationSalesTransactionHeader);
        when(errorEvacuationSalesTransactionHeaderMapper.selectByCondition(anyObject()))
                .thenReturn(errorEvacuationSalesTransactionHeaders);

        SalesErrorSalesTransactionDiscountOptional salesErrorSalesTransactionDiscount =
                new SalesErrorSalesTransactionDiscountOptional();
        salesErrorSalesTransactionDiscount.setTransactionId("1");
        salesErrorSalesTransactionDiscount.setCreateUserId("1");
        List<SalesErrorSalesTransactionDiscountOptional> salesErrorSalesTransactionDiscounts =
                new ArrayList<>();
        salesErrorSalesTransactionDiscounts.add(salesErrorSalesTransactionDiscount);
        when(salesErrorSalesTransactionDiscountMapper.selectByCondition(anyObject()))
                .thenReturn(salesErrorSalesTransactionDiscounts);

        ErrorEvacuationSalesTransactionDiscountOptional errorEvacuationSalesTransactionDiscount =
                new ErrorEvacuationSalesTransactionDiscountOptional();
        errorEvacuationSalesTransactionDiscount.setTransactionId("1");
        errorEvacuationSalesTransactionDiscount.setCreateUserId("1");
        List<ErrorEvacuationSalesTransactionDiscountOptional> errorEvacuationSalesTransactionDiscounts =
                new ArrayList<>();
        errorEvacuationSalesTransactionDiscounts.add(errorEvacuationSalesTransactionDiscount);
        when(errorEvacuationSalesTransactionDiscountMapper.selectByCondition(anyObject()))
                .thenReturn(errorEvacuationSalesTransactionDiscounts);

        SalesErrorSalesTransactionTaxOptional salesErrorSalesTransactionTax =
                new SalesErrorSalesTransactionTaxOptional();
        salesErrorSalesTransactionTax.setTransactionId("1");
        salesErrorSalesTransactionTax.setCreateUserId("1");
        List<SalesErrorSalesTransactionTaxOptional> salesErrorSalesTransactionTaxs =
                new ArrayList<>();
        salesErrorSalesTransactionTaxs.add(salesErrorSalesTransactionTax);
        when(salesErrorSalesTransactionTaxMapper.selectByCondition(anyObject()))
                .thenReturn(salesErrorSalesTransactionTaxs);

        ErrorEvacuationSalesTransactionTaxOptional errorEvacuationSalesTransactionTax =
                new ErrorEvacuationSalesTransactionTaxOptional();
        errorEvacuationSalesTransactionTax.setTransactionId("1");
        errorEvacuationSalesTransactionTax.setCreateUserId("1");
        List<ErrorEvacuationSalesTransactionTaxOptional> errorEvacuationSalesTransactionTaxs =
                new ArrayList<>();
        errorEvacuationSalesTransactionTaxs.add(errorEvacuationSalesTransactionTax);
        when(errorEvacuationSalesTransactionTaxMapper.selectByCondition(anyObject()))
                .thenReturn(errorEvacuationSalesTransactionTaxs);

        SalesErrorSalesTransactionTotalAmountOptional salesErrorSalesTransactionTotalAmount =
                new SalesErrorSalesTransactionTotalAmountOptional();
        salesErrorSalesTransactionTotalAmount.setTransactionId("1");
        salesErrorSalesTransactionTotalAmount.setCreateUserId("1");
        List<SalesErrorSalesTransactionTotalAmountOptional> salesErrorSalesTransactionTotals =
                new ArrayList<>();
        salesErrorSalesTransactionTotals.add(salesErrorSalesTransactionTotalAmount);
        when(salesErrorSalesTransactionTotalAmountMapper.selectByCondition(anyObject()))
                .thenReturn(salesErrorSalesTransactionTotals);

        ErrorEvacuationSalesTransactionTotalAmountOptional errorEvacuationSalesTransactionTotalAmount =
                new ErrorEvacuationSalesTransactionTotalAmountOptional();
        errorEvacuationSalesTransactionTotalAmount.setTransactionId("1");
        errorEvacuationSalesTransactionTotalAmount.setCreateUserId("1");
        List<ErrorEvacuationSalesTransactionTotalAmountOptional> errorEvacuationSalesTransactionTotalAmounts =
                new ArrayList<>();
        errorEvacuationSalesTransactionTotalAmounts.add(errorEvacuationSalesTransactionTotalAmount);
        when(errorEvacuationSalesTransactionTotalAmountMapper.selectByCondition(anyObject()))
                .thenReturn(errorEvacuationSalesTransactionTotalAmounts);

        ErrorEvacuationSalesOrderInformationOptional errorEvacuationSalesOrderInformation =
                new ErrorEvacuationSalesOrderInformationOptional();
        errorEvacuationSalesOrderInformation.setTransactionId("1");
        errorEvacuationSalesOrderInformation.setSalesTransactionErrorId("1");
        List<ErrorEvacuationSalesOrderInformationOptional> errorEvacuationSalesOrderInformations =
                new ArrayList<>();
        errorEvacuationSalesOrderInformations.add(errorEvacuationSalesOrderInformation);
        when(errorEvacuationSalesOrderInformationMapper.selectByCondition(anyObject()))
                .thenReturn(errorEvacuationSalesOrderInformations);

        List<SalesErrorSalesTransactionTenderAndInfo> salesTransactionErrorTenderAndInfos =
                new ArrayList<>();

        SalesErrorSalesTransactionTenderAndInfo salesErrorSalesTransactionTenderAndInfo =
                new SalesErrorSalesTransactionTenderAndInfo();
        salesErrorSalesTransactionTenderAndInfo.setCardNo("1");
        salesErrorSalesTransactionTenderAndInfo.setTransactionId("1");
        salesTransactionErrorTenderAndInfos.add(salesErrorSalesTransactionTenderAndInfo);
        when(salesTransactionErrorDetailAndInfoMapper.getErrorEvacuationTenderAndInfo(anyObject()))
                .thenReturn(salesTransactionErrorTenderAndInfos);

        List<SalesErrorSalesTransactionDetailAndInfo> salesErrorSalesTransactionDetailAndInfos =
                new ArrayList<>();
        SalesErrorSalesTransactionDetailAndInfo salesErrorSalesTransactionDetailAndInfo =
                new SalesErrorSalesTransactionDetailAndInfo();
        salesErrorSalesTransactionDetailAndInfo.setTransactionId("1");
        salesErrorSalesTransactionDetailAndInfos.add(salesErrorSalesTransactionDetailAndInfo);

        when(salesTransactionErrorDetailAndInfoMapper.getErrorEvacuationDetailAndInfo(anyObject()))
                .thenReturn(salesErrorSalesTransactionDetailAndInfos);

        when(salesTransactionErrorDetailAndInfoMapper.getDetailAndInfo(anyObject()))
                .thenReturn(salesErrorSalesTransactionDetailAndInfos);

        when(salesTransactionErrorDetailAndInfoMapper.getTenderAndInfo(anyObject()))
                .thenReturn(salesTransactionErrorTenderAndInfos);

        File csvList = salesTransactionErrorListService.download(salesTransactionErrorListForm);
        assertNotNull(csvList);
    }

    @Test
    public void noLinkAccess() {
        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        SalesTransactionError salesTransactionError = new SalesTransactionError();
        salesTransactionError.setSalesTransactionErrorId("1");
        salesTransactionError.setCheckFlag(1);
        salesTransactionError.setCashRegisterNo("1");
        List<SalesTransactionError> salesTransactionErrors = new ArrayList<>();
        salesTransactionErrors.add(salesTransactionError);
        salesTransactionErrorListForm.setSalesTransactionErrorList(salesTransactionErrors);

        salesTransactionErrorListService.numberLinkAccess(salesTransactionErrorListForm);

    }

    @Test
    public void noLinkAccessTest() {
        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        SalesTransactionError salesTransactionError = new SalesTransactionError();
        salesTransactionError.setSalesTransactionErrorId("1");
        salesTransactionError.setCheckFlag(1);
        salesTransactionError.setCashRegisterNo("1");
        List<SalesTransactionError> salesTransactionErrors = new ArrayList<>();
        salesTransactionErrors.add(salesTransactionError);
        salesTransactionErrorListForm.setSalesTransactionErrorList(salesTransactionErrors);
        AlterationExclusionControl alterationExclusionControl = new AlterationExclusionControl();
        alterationExclusionControl.setCashRegisterNo(1);
        alterationExclusionControl.setCreateUserId("DK");
        when(alterationExclusionControlMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(alterationExclusionControl);
        salesTransactionErrorListService.numberLinkAccess(salesTransactionErrorListForm);

    }

    @Test(expected = BusinessException.class)
    public void noLinkAccessUserNot() {
        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        SalesTransactionError salesTransactionError = new SalesTransactionError();
        salesTransactionError.setSalesTransactionErrorId("1");
        salesTransactionError.setCheckFlag(1);
        salesTransactionError.setCashRegisterNo("1");
        List<SalesTransactionError> salesTransactionErrors = new ArrayList<>();
        salesTransactionErrors.add(salesTransactionError);
        salesTransactionErrorListForm.setSalesTransactionErrorList(salesTransactionErrors);
        AlterationExclusionControl alterationExclusionControl = new AlterationExclusionControl();
        alterationExclusionControl.setCashRegisterNo(1);
        alterationExclusionControl.setCreateUserId("DUS");
        alterationExclusionControl.setExclusionValidTime("60");
        alterationExclusionControl.setUpdateDatetime(OffsetDateTime.now());
        when(alterationExclusionControlMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(alterationExclusionControl);
        salesTransactionErrorListService.numberLinkAccess(salesTransactionErrorListForm);

    }

    @Test
    public void deleteTest() throws JsonProcessingException {
        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        SalesTransactionError salesTransactionError = new SalesTransactionError();
        salesTransactionError.setSalesTransactionErrorId("1");
        salesTransactionError.setCheckFlag(1);
        salesTransactionError.setCashRegisterNo("1");
        List<SalesTransactionError> salesTransactionErrors = new ArrayList<>();
        salesTransactionErrors.add(salesTransactionError);
        salesTransactionErrorListForm.setSalesTransactionErrorList(salesTransactionErrors);

        SalesTransactionHistoryOptional salesTransactionHistory =
                new SalesTransactionHistoryOptional();


        CreateTransactionImportData transactionImportData = new CreateTransactionImportData();
        transactionImportData.setCustomerId("1");
        String json = objectMapper.writeValueAsString(transactionImportData);
        salesTransactionHistory.setSalesTransactionData(json);

        when(salesTransactionHistoryOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(salesTransactionHistory);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(new CommonStatus());
        salesTransactionErrorListService.delete(salesTransactionErrorListForm);
    }

    @Test(expected = BusinessException.class)
    public void deleteTestBusinessException() throws JsonProcessingException {
        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        SalesTransactionError salesTransactionError = new SalesTransactionError();
        salesTransactionError.setSalesTransactionErrorId("1");
        salesTransactionError.setCheckFlag(1);
        salesTransactionError.setCashRegisterNo("1");
        List<SalesTransactionError> salesTransactionErrors = new ArrayList<>();
        salesTransactionErrors.add(salesTransactionError);
        salesTransactionErrorListForm.setSalesTransactionErrorList(salesTransactionErrors);

        SalesTransactionHistoryOptional salesTransactionHistory =
                new SalesTransactionHistoryOptional();


        CreateTransactionImportData transactionImportData = new CreateTransactionImportData();
        transactionImportData.setCustomerId("1");
        String json = objectMapper.writeValueAsString(transactionImportData);
        salesTransactionHistory.setSalesTransactionData(json);

        when(salesTransactionHistoryOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(salesTransactionHistory);
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.WARNING);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(commonStatus);
        salesTransactionErrorListService.delete(salesTransactionErrorListForm);
    }

    @Test(expected = BusinessException.class)
    public void uploadTestBusinessException() throws JsonProcessingException {
        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        SalesTransactionError salesTransactionError = new SalesTransactionError();
        salesTransactionError.setSalesTransactionErrorId("1");
        salesTransactionError.setCheckFlag(1);
        salesTransactionError.setCashRegisterNo("1");
        List<SalesTransactionError> salesTransactionErrors = new ArrayList<>();
        salesTransactionErrors.add(salesTransactionError);
        salesTransactionErrorListForm.setSalesTransactionErrorList(salesTransactionErrors);

        SalesTransactionHistoryOptional salesTransactionHistory =
                new SalesTransactionHistoryOptional();


        CreateTransactionImportData transactionImportData = new CreateTransactionImportData();
        transactionImportData.setCustomerId("1");
        String json = objectMapper.writeValueAsString(transactionImportData);
        salesTransactionHistory.setSalesTransactionData(json);

        when(salesTransactionHistoryOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(salesTransactionHistory);
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.WARNING);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(commonStatus);
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.VALIDATION_ERROR);

        salesTransactionErrorListService.upload(salesTransactionErrorListForm);

    }

    @Test(expected = BusinessException.class)
    public void uploadTestWraningBusinessException() throws JsonProcessingException {
        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        SalesTransactionError salesTransactionError = new SalesTransactionError();
        salesTransactionError.setSalesTransactionErrorId("1");
        salesTransactionError.setCheckFlag(1);
        salesTransactionError.setCashRegisterNo("1");
        List<SalesTransactionError> salesTransactionErrors = new ArrayList<>();
        salesTransactionErrors.add(salesTransactionError);
        salesTransactionErrorListForm.setSalesTransactionErrorList(salesTransactionErrors);

        SalesTransactionHistoryOptional salesTransactionHistory =
                new SalesTransactionHistoryOptional();


        CreateTransactionImportData transactionImportData = new CreateTransactionImportData();
        transactionImportData.setCustomerId("1");
        String json = objectMapper.writeValueAsString(transactionImportData);
        salesTransactionHistory.setSalesTransactionData(json);

        when(salesTransactionHistoryOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(salesTransactionHistory);
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.WARNING);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(commonStatus);
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);

        salesTransactionErrorListService.upload(salesTransactionErrorListForm);

    }

    @Test
    public void uploadTestBusinessExceptionSuccess() throws JsonProcessingException {
        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        SalesTransactionError salesTransactionError = new SalesTransactionError();
        salesTransactionError.setSalesTransactionErrorId("1");
        salesTransactionError.setCheckFlag(1);
        salesTransactionError.setCashRegisterNo("1");
        List<SalesTransactionError> salesTransactionErrors = new ArrayList<>();
        salesTransactionErrors.add(salesTransactionError);
        salesTransactionErrorListForm.setSalesTransactionErrorList(salesTransactionErrors);

        SalesTransactionHistoryOptional salesTransactionHistory =
                new SalesTransactionHistoryOptional();

        CreateTransactionImportData transactionImportData = new CreateTransactionImportData();
        transactionImportData.setCustomerId("1");
        String json = objectMapper.writeValueAsString(transactionImportData);
        salesTransactionHistory.setSalesTransactionData(json);

        when(salesTransactionHistoryOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(salesTransactionHistory);
        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callTransactionImport(anyObject()))
                .thenReturn(commonStatus);
        when(dataCheckerService.checkTransactionData(anyObject(), anyString(), anyString()))
                .thenReturn(TransactionCheckResultType.NORMAL);

        salesTransactionErrorListService.upload(salesTransactionErrorListForm);

    }

}
