package com.fastretailing.dcp.sales.salestransactionhistorylist.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionHistoryDetail;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionHistoryDetailOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.StoreGeneralPurposeMasterOptionalMapper;
import com.fastretailing.dcp.sales.salestransactionhistorylist.form.SalesTransactionHistory;
import com.fastretailing.dcp.sales.salestransactionhistorylist.form.SalesTransactionHistoryListForm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesTransactionHistoryListServiceTest {

    /** Service class write off confirm. */
    @SpyBean
    private SalesTransactionHistoryListService salesTransactionHistoryListService;

    /** Common code master mapper. */
    @MockBean
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Sales transaction history detail optional mapper. */
    @MockBean
    private SalesTransactionHistoryDetailOptionalMapper salesTransactionHistoryDetailOptionalMapper;

    /** Store general purpose master optional mapper. */
    @MockBean
    private StoreGeneralPurposeMasterOptionalMapper storeGeneralPurposeMasterOptionalMapper;

    /**
     * Test case of get initialize information success.
     */
    @Test
    public void testGetInitializeInformationSuccess() {

        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        countryCodeMaster.setName2("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        brandCodeMaster.setName2("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        transactionTypeMaster.setName2("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster errorContentsMaster = new CommonCodeMaster();
        errorContentsMaster.setTypeId("error_type");
        errorContentsMaster.setTypeValue("019");
        errorContentsMaster.setName1("JP1");
        errorContentsMaster.setName2("JP1");
        List<CommonCodeMaster> errorContentsList = new ArrayList<>();
        errorContentsList.add(errorContentsMaster);

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(errorContentsList);

        SalesTransactionHistoryListForm rtnSalesTransactionHistoryListForm =
                salesTransactionHistoryListService
                        .getInitializeInformation(new SalesTransactionHistoryListForm());

        assertEquals(1, rtnSalesTransactionHistoryListForm.getCountryList().size());
        assertEquals("017", rtnSalesTransactionHistoryListForm.getCountryList().get(0).getValue());
        assertEquals(1, rtnSalesTransactionHistoryListForm.getBrandList().size());
        assertEquals("019", rtnSalesTransactionHistoryListForm.getBrandList().get(0).getValue());

        assertEquals("ABC",
                rtnSalesTransactionHistoryListForm.getSalesTransactionTypeMap().get("018"));
        assertEquals("JP1", rtnSalesTransactionHistoryListForm.getErrorContentsMap().get("019"));

    }

    /**
     * Test case of get initialize information error contens list null.
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

        salesTransactionHistoryListService
                .getInitializeInformation(new SalesTransactionHistoryListForm());

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

        salesTransactionHistoryListService
                .getInitializeInformation(new SalesTransactionHistoryListForm());

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

        salesTransactionHistoryListService
                .getInitializeInformation(new SalesTransactionHistoryListForm());

    }

    /**
     * Test case of get initialize information country list null.
     */
    @Test(expected = BusinessException.class)
    public void testGetInitializeInformationCountryListNull() {

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(null);

        salesTransactionHistoryListService
                .getInitializeInformation(new SalesTransactionHistoryListForm());

    }

    /**
     * Test case of get sales transaction history list success.
     */
    @Test
    public void testGetSalesTransactionHistoryListSuccess() {

        when(storeGeneralPurposeMasterOptionalMapper.selectTimeZoneByViewStoreCode(any()))
                .thenReturn(Arrays.<String>asList("Asia/Tokyo"));

        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code_screen");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("Canada");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code_screen");
        brandCodeMaster.setTypeValue("0001");
        brandCodeMaster.setName1("UQ");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(systemCountryList);

        SalesTransactionHistoryListForm salesTransactionHistoryListForm =
                new SalesTransactionHistoryListForm();

        salesTransactionHistoryListForm.setSystemBrandCode("0001");
        salesTransactionHistoryListForm.setSystemCountryCode("17");
        salesTransactionHistoryListForm.setStoreCode("002");
        salesTransactionHistoryListForm.setReceiptNo("a001");
        salesTransactionHistoryListForm.setCashRegisterNo("1001");
        salesTransactionHistoryListForm.setSalesTransactionType("001");
        salesTransactionHistoryListForm.setBusinessDateFrom("2017/10/10");
        salesTransactionHistoryListForm.setBusinessDateTo("2017/10/11");
        salesTransactionHistoryListForm.setCorrectorCode("b05");
        salesTransactionHistoryListForm.setErrorContents("business error");

        SalesTransactionHistoryDetail firstSalesTransactionHistoryDetail =
                new SalesTransactionHistoryDetail();

        firstSalesTransactionHistoryDetail.setSystemBrandCode("0001");
        firstSalesTransactionHistoryDetail.setSystemCountryCode("17");
        firstSalesTransactionHistoryDetail.setViewStoreCode("002");
        firstSalesTransactionHistoryDetail.setStoreName("東京００２");
        firstSalesTransactionHistoryDetail.setCashRegisterNo("a001");
        firstSalesTransactionHistoryDetail.setReceiptNo("1001");
        firstSalesTransactionHistoryDetail.setSalesTransactionType("1001");
        firstSalesTransactionHistoryDetail
                .setDataCreationDateTime(OffsetDateTime.parse("2018-05-10T00:00+09:00"));
        firstSalesTransactionHistoryDetail.setOrderStatusUpdateDate("20191010");
        firstSalesTransactionHistoryDetail.setErrorType("1");
        firstSalesTransactionHistoryDetail.setDataAlterationUserId("user id");
        firstSalesTransactionHistoryDetail
                .setUpdateDateTime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));
        firstSalesTransactionHistoryDetail.setDataAlterationSalesLinkageType(0);
        firstSalesTransactionHistoryDetail.setDataAlterationBackboneLinkageType(0);
        firstSalesTransactionHistoryDetail.setHistoryType(1);
        firstSalesTransactionHistoryDetail.setUpdateType("INSERT");

        SalesTransactionHistoryDetail secondSalesTransactionHistoryDetail =
                new SalesTransactionHistoryDetail();

        secondSalesTransactionHistoryDetail.setSystemBrandCode("0001");
        secondSalesTransactionHistoryDetail.setSystemCountryCode("17");
        secondSalesTransactionHistoryDetail.setViewStoreCode("002");
        secondSalesTransactionHistoryDetail.setStoreName("東京００２");
        secondSalesTransactionHistoryDetail.setCashRegisterNo("a001");
        secondSalesTransactionHistoryDetail.setReceiptNo("1001");
        secondSalesTransactionHistoryDetail.setSalesTransactionType("2001");
        secondSalesTransactionHistoryDetail
                .setDataCreationDateTime(OffsetDateTime.parse("2018-05-10T00:00+09:00"));
        secondSalesTransactionHistoryDetail.setOrderStatusUpdateDate("20191010");
        secondSalesTransactionHistoryDetail.setErrorType("1");
        secondSalesTransactionHistoryDetail.setDataAlterationUserId("user id");
        secondSalesTransactionHistoryDetail
                .setUpdateDateTime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));
        secondSalesTransactionHistoryDetail.setDataAlterationSalesLinkageType(1);
        secondSalesTransactionHistoryDetail.setDataAlterationBackboneLinkageType(0);
        secondSalesTransactionHistoryDetail.setHistoryType(1);
        secondSalesTransactionHistoryDetail.setUpdateType("CORRECTION");

        SalesTransactionHistoryDetail thirdSalesTransactionHistoryDetail =
                new SalesTransactionHistoryDetail();

        thirdSalesTransactionHistoryDetail.setSystemBrandCode("0001");
        thirdSalesTransactionHistoryDetail.setSystemCountryCode("17");
        thirdSalesTransactionHistoryDetail.setViewStoreCode("002");
        thirdSalesTransactionHistoryDetail.setStoreName("東京００２");
        thirdSalesTransactionHistoryDetail.setCashRegisterNo("a001");
        thirdSalesTransactionHistoryDetail.setReceiptNo("1001");
        thirdSalesTransactionHistoryDetail.setSalesTransactionType("3002");
        thirdSalesTransactionHistoryDetail
                .setDataCreationDateTime(OffsetDateTime.parse("2018-05-10T00:00+09:00"));
        thirdSalesTransactionHistoryDetail.setOrderStatusUpdateDate("20191010");
        thirdSalesTransactionHistoryDetail.setErrorType("1");
        thirdSalesTransactionHistoryDetail.setDataAlterationUserId("user id");
        thirdSalesTransactionHistoryDetail
                .setUpdateDateTime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));
        thirdSalesTransactionHistoryDetail.setDataAlterationSalesLinkageType(0);
        thirdSalesTransactionHistoryDetail.setDataAlterationBackboneLinkageType(1);
        thirdSalesTransactionHistoryDetail.setHistoryType(0);
        thirdSalesTransactionHistoryDetail.setUpdateType("INSERT");

        List<SalesTransactionHistoryDetail> salesTransactionHistoryDetailList = new ArrayList<>();
        salesTransactionHistoryDetailList.add(firstSalesTransactionHistoryDetail);
        salesTransactionHistoryDetailList.add(secondSalesTransactionHistoryDetail);
        salesTransactionHistoryDetailList.add(thirdSalesTransactionHistoryDetail);
        when(salesTransactionHistoryDetailOptionalMapper
                .selectSalesTransactionHistoryDetailByCondition(anyObject()))
                        .thenReturn(salesTransactionHistoryDetailList);

        SalesTransactionHistoryListForm rtnSalesTransactionHistoryListForm =
                salesTransactionHistoryListService
                        .getSalesTransactionHistoryList(salesTransactionHistoryListForm);

        assertEquals("0001", rtnSalesTransactionHistoryListForm.getSystemBrandCode());
        assertEquals("17", rtnSalesTransactionHistoryListForm.getSystemCountryCode());
        assertEquals("002", rtnSalesTransactionHistoryListForm.getStoreCode());
        assertEquals("1001", rtnSalesTransactionHistoryListForm.getCashRegisterNo());
        assertEquals("a001", rtnSalesTransactionHistoryListForm.getReceiptNo());
        assertEquals("001", rtnSalesTransactionHistoryListForm.getSalesTransactionType());
        assertEquals("2017/10/10", rtnSalesTransactionHistoryListForm.getBusinessDateFrom());
        assertEquals("2017/10/11", rtnSalesTransactionHistoryListForm.getBusinessDateTo());
        assertEquals("b05", rtnSalesTransactionHistoryListForm.getCorrectorCode());
        assertEquals("business error", rtnSalesTransactionHistoryListForm.getErrorContents());

        SalesTransactionHistory firstSalesTransactionHistory =
                rtnSalesTransactionHistoryListForm.getSalesTransactionHistoryList().get(0);
        assertEquals("UQ", firstSalesTransactionHistory.getBrandCode());
        assertEquals("Canada", firstSalesTransactionHistory.getCountryCode());
        assertEquals("002", firstSalesTransactionHistory.getStoreCode());
        assertEquals("東京００２", firstSalesTransactionHistory.getStoreName());
        assertEquals("a001", firstSalesTransactionHistory.getCashRegisterNo());
        assertEquals("1001", firstSalesTransactionHistory.getReceiptNo());
        assertEquals("1001", firstSalesTransactionHistory.getSalesTransactionType());
        assertEquals("2018/05/10 00:00:00", firstSalesTransactionHistory.getDataCreationDate());
        assertEquals("20191010", firstSalesTransactionHistory.getBusinessDate());
        assertEquals(null, firstSalesTransactionHistory.getErrorContents());
        assertEquals("user id", firstSalesTransactionHistory.getCorrectorCode());
        assertEquals("2018/05/11 00:00:00", firstSalesTransactionHistory.getCorrectionDate());
        assertEquals("ALL", firstSalesTransactionHistory.getReflect());
        assertEquals("Insert", firstSalesTransactionHistory.getUpdateType());

        SalesTransactionHistory secondSalesTransactionHistory =
                rtnSalesTransactionHistoryListForm.getSalesTransactionHistoryList().get(1);
        assertEquals("UQ", secondSalesTransactionHistory.getBrandCode());
        assertEquals("Canada", secondSalesTransactionHistory.getCountryCode());
        assertEquals("002", secondSalesTransactionHistory.getStoreCode());
        assertEquals("東京００２", secondSalesTransactionHistory.getStoreName());
        assertEquals("a001", secondSalesTransactionHistory.getCashRegisterNo());
        assertEquals("1001", secondSalesTransactionHistory.getReceiptNo());
        assertEquals("2001", secondSalesTransactionHistory.getSalesTransactionType());
        assertEquals("2018/05/10 00:00:00", secondSalesTransactionHistory.getDataCreationDate());
        assertEquals("20191010", secondSalesTransactionHistory.getBusinessDate());
        assertEquals(null, secondSalesTransactionHistory.getErrorContents());
        assertEquals("user id", secondSalesTransactionHistory.getCorrectorCode());
        assertEquals("2018/05/11 00:00:00", secondSalesTransactionHistory.getCorrectionDate());
        assertEquals("only on IMS Linkage", secondSalesTransactionHistory.getReflect());
        assertEquals("Correction", secondSalesTransactionHistory.getUpdateType());

        SalesTransactionHistory thirdSalesTransactionHistory =
                rtnSalesTransactionHistoryListForm.getSalesTransactionHistoryList().get(2);
        assertEquals("UQ", thirdSalesTransactionHistory.getBrandCode());
        assertEquals("Canada", thirdSalesTransactionHistory.getCountryCode());
        assertEquals("002", thirdSalesTransactionHistory.getStoreCode());
        assertEquals("東京００２", thirdSalesTransactionHistory.getStoreName());
        assertEquals("a001", thirdSalesTransactionHistory.getCashRegisterNo());
        assertEquals("1001", thirdSalesTransactionHistory.getReceiptNo());
        assertEquals("3002", thirdSalesTransactionHistory.getSalesTransactionType());
        assertEquals("2018/05/10 00:00:00", thirdSalesTransactionHistory.getDataCreationDate());
        assertEquals("20191010", thirdSalesTransactionHistory.getBusinessDate());
        assertEquals(null, thirdSalesTransactionHistory.getErrorContents());
        assertEquals("user id", thirdSalesTransactionHistory.getCorrectorCode());
        assertEquals("2018/05/11 00:00:00", thirdSalesTransactionHistory.getCorrectionDate());
        assertEquals("only on Receipt", thirdSalesTransactionHistory.getReflect());
        assertEquals("Delete", thirdSalesTransactionHistory.getUpdateType());

    }

    /**
     * Test case of get sales transaction history list success date input.
     */
    @Test
    public void testGetSalesTransactionHistoryListSuccessDateInput() {

        SalesTransactionHistoryListForm salesTransactionHistoryListForm =
                new SalesTransactionHistoryListForm();

        salesTransactionHistoryListForm.setSystemBrandCode("UQ");
        salesTransactionHistoryListForm.setSystemCountryCode("017");
        salesTransactionHistoryListForm.setStoreCode("002");
        salesTransactionHistoryListForm.setReceiptNo("a001");
        salesTransactionHistoryListForm.setCashRegisterNo("1001");
        salesTransactionHistoryListForm.setSalesTransactionType("001");
        salesTransactionHistoryListForm.setDataCreationDateFrom("2017/11/01");
        salesTransactionHistoryListForm.setDataCreationDateTo("2017/11/02");
        salesTransactionHistoryListForm.setBusinessDateFrom("2017/10/10");
        salesTransactionHistoryListForm.setBusinessDateTo("2017/10/11");
        salesTransactionHistoryListForm.setCorrectorCode("b05");
        salesTransactionHistoryListForm.setCorrectionDateFrom("2017/12/12");
        salesTransactionHistoryListForm.setCorrectionDateTo("2018/12/12");
        salesTransactionHistoryListForm.setErrorContents("business error");

        SalesTransactionHistoryDetail firstSalesTransactionHistoryDetail =
                new SalesTransactionHistoryDetail();

        firstSalesTransactionHistoryDetail.setSystemBrandCode("uq");
        firstSalesTransactionHistoryDetail.setSystemCountryCode("017");
        firstSalesTransactionHistoryDetail.setViewStoreCode("002");
        firstSalesTransactionHistoryDetail.setStoreName("東京００２");
        firstSalesTransactionHistoryDetail.setCashRegisterNo("a001");
        firstSalesTransactionHistoryDetail.setReceiptNo("1001");
        firstSalesTransactionHistoryDetail.setSalesTransactionType("1001");
        firstSalesTransactionHistoryDetail
                .setDataCreationDateTime(OffsetDateTime.parse("2018-05-10T00:00+09:00"));
        firstSalesTransactionHistoryDetail.setOrderStatusUpdateDate("20191010");
        firstSalesTransactionHistoryDetail.setErrorType("1");
        firstSalesTransactionHistoryDetail.setDataAlterationUserId("user id");
        firstSalesTransactionHistoryDetail
                .setUpdateDateTime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));
        firstSalesTransactionHistoryDetail.setDataAlterationSalesLinkageType(0);
        firstSalesTransactionHistoryDetail.setDataAlterationBackboneLinkageType(0);
        firstSalesTransactionHistoryDetail.setHistoryType(1);
        firstSalesTransactionHistoryDetail.setUpdateType("INSERT");

        List<SalesTransactionHistoryDetail> salesTransactionHistoryDetailList = new ArrayList<>();
        salesTransactionHistoryDetailList.add(firstSalesTransactionHistoryDetail);

        when(storeGeneralPurposeMasterOptionalMapper.selectTimeZoneByViewStoreCode(any()))
                .thenReturn(Arrays.<String>asList("Asia/Tokyo"));

        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code_screen");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("Canada");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code_screen");
        brandCodeMaster.setTypeValue("0001");
        brandCodeMaster.setName1("UQ");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);

        when(salesTransactionHistoryDetailOptionalMapper
                .selectSalesTransactionHistoryDetailByCondition(anyObject()))
                        .thenReturn(salesTransactionHistoryDetailList);

        salesTransactionHistoryListService
                .getSalesTransactionHistoryList(salesTransactionHistoryListForm);

    }

    /**
     * Test case of get sorted sales transaction history list success.
     */
    @Test
    public void testGetSortedSalesTransactionHistoryListSuccess() {

        SalesTransactionHistoryListForm salesTransactionHistoryListForm =
                new SalesTransactionHistoryListForm();
        salesTransactionHistoryListForm.setOrderByClause(1);
        salesTransactionHistoryListForm.setSortItem("cashRegisterNo");
        SalesTransactionHistory salesTransactionHistory = new SalesTransactionHistory();
        salesTransactionHistory.setBrandCode("UP");
        salesTransactionHistory.setCountryCode("JP");
        salesTransactionHistory.setStoreCode("002");
        salesTransactionHistory.setStoreName("東京２号");
        salesTransactionHistory.setCashRegisterNo("001");
        salesTransactionHistory.setReceiptNo("101");
        salesTransactionHistory.setSalesTransactionType("1");
        salesTransactionHistory.setDataCreationDate("2018-05-10 00:00");
        salesTransactionHistory.setBusinessDate("2017/10/10");
        salesTransactionHistory.setErrorContents("a");
        salesTransactionHistory.setCorrectorCode("c");
        salesTransactionHistory.setReflect("r");
        salesTransactionHistory.setUpdateType("u");
        List<SalesTransactionHistory> salesTransactionHistoryList = new ArrayList<>();
        salesTransactionHistoryList.add(salesTransactionHistory);

        SalesTransactionHistory secondTransactionHistory = new SalesTransactionHistory();
        secondTransactionHistory.setBrandCode("ZA");
        secondTransactionHistory.setCountryCode("UA");
        secondTransactionHistory.setStoreCode("003");
        secondTransactionHistory.setStoreName("東京３号");
        secondTransactionHistory.setCashRegisterNo("002");
        secondTransactionHistory.setReceiptNo("102");
        secondTransactionHistory.setSalesTransactionType("2");
        secondTransactionHistory.setDataCreationDate("2018-05-11 00:00");
        secondTransactionHistory.setBusinessDate("2017/10/11");
        secondTransactionHistory.setErrorContents("b");
        secondTransactionHistory.setCorrectorCode("d");
        secondTransactionHistory.setReflect("s");
        secondTransactionHistory.setUpdateType("v");
        salesTransactionHistoryList.add(secondTransactionHistory);
        salesTransactionHistoryListForm.setSalesTransactionHistoryList(salesTransactionHistoryList);

        SalesTransactionHistoryListForm rtnSalesTransactionHistoryListForm =
                salesTransactionHistoryListService
                        .getSortedSalesTransactionHistoryList(salesTransactionHistoryListForm);

        List<SalesTransactionHistory> sortedSalesTransactionHistoryList =
                rtnSalesTransactionHistoryListForm.getSalesTransactionHistoryList();

        assertEquals(secondTransactionHistory, sortedSalesTransactionHistoryList.get(0));
        assertEquals(salesTransactionHistory, sortedSalesTransactionHistoryList.get(1));


        salesTransactionHistoryListForm.setOrderByClause(0);
        salesTransactionHistoryListForm.setSortItem("cashRegisterNo");

        rtnSalesTransactionHistoryListForm = salesTransactionHistoryListService
                .getSortedSalesTransactionHistoryList(salesTransactionHistoryListForm);

        sortedSalesTransactionHistoryList =
                rtnSalesTransactionHistoryListForm.getSalesTransactionHistoryList();

        assertEquals(salesTransactionHistory, sortedSalesTransactionHistoryList.get(0));
        assertEquals(secondTransactionHistory, sortedSalesTransactionHistoryList.get(1));

    }

}
