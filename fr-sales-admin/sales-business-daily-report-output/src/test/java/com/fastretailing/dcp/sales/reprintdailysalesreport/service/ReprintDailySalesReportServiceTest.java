/**
 * @(#)ReprintDailySalesReportServiceTest.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.reprintdailysalesreport.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.constants.SalesFunctionId;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.optional.SalesreportManagementOptional;
import com.fastretailing.dcp.sales.common.entity.optional.StoreControlMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesreportManagementOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.StoreControlMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TranslationStoreCodeMasterOptionalMapper;
import com.fastretailing.dcp.sales.reprintdailysalesreport.form.ReprintDailySalesReportForm;
import com.fastretailing.dcp.storecommon.type.FunctionType;
import com.fastretailing.dcp.storecommon.type.MessageType;
import com.fastretailing.dcp.storecommon.type.PlatformShortName;
import com.fastretailing.dcp.storecommon.util.CommonUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReprintDailySalesReportServiceTest {

    /** Service class write off confirm. */
    @SpyBean
    private ReprintDailySalesReportService reprintDailySalesReportService;

    /** Common code master mapper. */
    @MockBean
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Translation store code optional mapper. */
    @MockBean
    private TranslationStoreCodeMasterOptionalMapper translationStoreCodeDetailOptionalMapper;

    /** Salesreport management optional mapper. */
    @MockBean
    private SalesreportManagementOptionalMapper salesreportManagementOptionalMapper;

    /** Store Control master optional mapper. */
    @MockBean
    private StoreControlMasterOptionalMapper storeControlMasterOptionalMapper;

    /**
     * Test case of get initialize information success.
     */
    @Test
    public void testInitializeSystemBrandCodeNull() {

        List<CommonCodeMaster> systemBrandList = null;

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList);

        try {
            reprintDailySalesReportService
                    .getInitializeInformation(new ReprintDailySalesReportForm());
        } catch (BusinessException actual) {
            String expect = CommonUtility.createMessageId(LogLevel.ERROR, PlatformShortName.SALES,
                    MessageType.BUSINESS_ERROR, FunctionType.SCREEN,
                    SalesFunctionId.SALES_COMMON_CODE_MASTER);
            assertEquals(actual.getResultObject().getDebugId(), expect);
        }
    }

    /**
     * Test case of get initialize information.
     */
    @Test
    public void testInitializeSystemCountryCodeNull() {

        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("USA");
        systemBrandList.add(brandCodeMaster);
        brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("000");
        brandCodeMaster.setName1("MaCao");
        systemBrandList.add(brandCodeMaster);

        List<CommonCodeMaster> systemCountryList = null;

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);

        try {
            reprintDailySalesReportService
                    .getInitializeInformation(new ReprintDailySalesReportForm());
        } catch (BusinessException actual) {
            String expect = CommonUtility.createMessageId(LogLevel.ERROR, PlatformShortName.SALES,
                    MessageType.BUSINESS_ERROR, FunctionType.SCREEN,
                    SalesFunctionId.SALES_COMMON_CODE_MASTER);
            assertEquals(actual.getResultObject().getDebugId(), expect);
        }
    }

    /**
     * Test case of get initialize information.
     */
    @Test
    public void testInitializeSuccessWithStoreCodeTranslation() {

        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JAPAN");
        systemCountryList.add(countryCodeMaster);
        countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("000");
        countryCodeMaster.setName1("CHINA");
        systemCountryList.add(countryCodeMaster);

        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("USA");
        systemBrandList.add(brandCodeMaster);
        brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("000");
        brandCodeMaster.setName1("MaCao");
        systemBrandList.add(brandCodeMaster);

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setStoreCode("0002");

        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional = null;

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);
        when(translationStoreCodeDetailOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(translationStoreCodeMasterOptional);

        try {
            reprintDailySalesReportForm = reprintDailySalesReportService
                    .getInitializeInformation(reprintDailySalesReportForm);
        } catch (BusinessException actual) {
            String expect = CommonUtility.createMessageId(LogLevel.ERROR, PlatformShortName.SALES,
                    MessageType.BUSINESS_ERROR, FunctionType.SCREEN,
                    SalesFunctionId.SALES_TRANSLATION_STORE_CODE_MASTER);
            assertEquals(expect, actual.getResultObject().getDebugId());
        }
    }

    /**
     * Test case of get initialize information.
     */
    @Test
    public void testInitializeSuccessWithStoreCode() {

        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JAPAN");
        systemCountryList.add(countryCodeMaster);
        countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("000");
        countryCodeMaster.setName1("CHINA");
        systemCountryList.add(countryCodeMaster);

        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("USA");
        systemBrandList.add(brandCodeMaster);
        brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("000");
        brandCodeMaster.setName1("MaCao");
        systemBrandList.add(brandCodeMaster);

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setStoreCode("0002");

        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("0002");
        translationStoreCodeMasterOptional.setSystemBrandCode("1234");
        translationStoreCodeMasterOptional.setSystemCountryCode("123");
        translationStoreCodeMasterOptional.setViewStoreCode("123456");

        CommonCodeMaster commonBrandCodeMaster = new CommonCodeMaster();
        commonBrandCodeMaster.setName1("systemBrandCode");
        CommonCodeMaster commonCountryCodeMaster = new CommonCodeMaster();
        commonCountryCodeMaster.setName1("systemCountryCode");

        StoreControlMasterOptional storeControlMasterOptional = new StoreControlMasterOptional();
        storeControlMasterOptional.setBusinessDate("20180621");

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);
        when(translationStoreCodeDetailOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(translationStoreCodeMasterOptional);
        when(commonCodeMasterMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(commonBrandCodeMaster)
                .thenReturn(commonCountryCodeMaster);
        when(storeControlMasterOptionalMapper.selectByPrimaryKey(anyString()))
                .thenReturn(storeControlMasterOptional);

        reprintDailySalesReportForm = reprintDailySalesReportService
                .getInitializeInformation(reprintDailySalesReportForm);

        assertEquals(2, reprintDailySalesReportForm.getCountryList().size());
        assertEquals("JAPAN", reprintDailySalesReportForm.getCountryList().get(0));
        assertEquals(2, reprintDailySalesReportForm.getBrandList().size());
        assertEquals("USA", reprintDailySalesReportForm.getBrandList().get(0));
    }

    /**
     * Test case of get initialize information.
     */
    @Test
    public void testInitializeSuccessWithCommonCode() {

        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JAPAN");
        systemCountryList.add(countryCodeMaster);
        countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("000");
        countryCodeMaster.setName1("CHINA");
        systemCountryList.add(countryCodeMaster);

        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("USA");
        systemBrandList.add(brandCodeMaster);
        brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("000");
        brandCodeMaster.setName1("MaCao");
        systemBrandList.add(brandCodeMaster);

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setStoreCode("0002");

        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("0002");
        translationStoreCodeMasterOptional.setSystemBrandCode("1234");
        translationStoreCodeMasterOptional.setSystemCountryCode("123");
        translationStoreCodeMasterOptional.setViewStoreCode("123456");

        CommonCodeMaster commonBrandCodeMaster = null;
        CommonCodeMaster commonCountryCodeMaster = new CommonCodeMaster();
        commonCountryCodeMaster.setName1("systemCountryCode");

        StoreControlMasterOptional storeControlMasterOptional = new StoreControlMasterOptional();
        storeControlMasterOptional.setBusinessDate("20180621");

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);
        when(translationStoreCodeDetailOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(translationStoreCodeMasterOptional);
        when(commonCodeMasterMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(commonBrandCodeMaster)
                .thenReturn(commonCountryCodeMaster);
        when(storeControlMasterOptionalMapper.selectByPrimaryKey(anyString()))
                .thenReturn(storeControlMasterOptional);

        try {
            reprintDailySalesReportForm = reprintDailySalesReportService
                    .getInitializeInformation(reprintDailySalesReportForm);
        } catch (BusinessException actual) {
            String expect = CommonUtility.createMessageId(LogLevel.ERROR, PlatformShortName.SALES,
                    MessageType.BUSINESS_ERROR, FunctionType.SCREEN,
                    SalesFunctionId.SALES_COMMON_CODE_MASTER);
            assertEquals(actual.getResultObject().getDebugId(), expect);
        }
    }

    /**
     * Test case of get initialize information.
     */
    @Test
    public void testInitializeSuccessWithCommonCodeNull() {

        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JAPAN");
        systemCountryList.add(countryCodeMaster);
        countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("000");
        countryCodeMaster.setName1("CHINA");
        systemCountryList.add(countryCodeMaster);

        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("USA");
        systemBrandList.add(brandCodeMaster);
        brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("000");
        brandCodeMaster.setName1("MaCao");
        systemBrandList.add(brandCodeMaster);

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setStoreCode("0002");

        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("0002");
        translationStoreCodeMasterOptional.setSystemBrandCode("1234");
        translationStoreCodeMasterOptional.setSystemCountryCode("123");
        translationStoreCodeMasterOptional.setViewStoreCode("123456");

        CommonCodeMaster commonBrandCodeMaster = new CommonCodeMaster();
        commonBrandCodeMaster.setName1("systemBrandCode");
        CommonCodeMaster commonCountryCodeMaster = null;

        StoreControlMasterOptional storeControlMasterOptional = new StoreControlMasterOptional();
        storeControlMasterOptional.setBusinessDate("20180621");

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);
        when(translationStoreCodeDetailOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(translationStoreCodeMasterOptional);
        when(commonCodeMasterMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(commonBrandCodeMaster)
                .thenReturn(commonCountryCodeMaster);
        when(storeControlMasterOptionalMapper.selectByPrimaryKey(anyString()))
                .thenReturn(storeControlMasterOptional);

        try {
            reprintDailySalesReportForm = reprintDailySalesReportService
                    .getInitializeInformation(reprintDailySalesReportForm);
        } catch (BusinessException actual) {
            String expect = CommonUtility.createMessageId(LogLevel.ERROR, PlatformShortName.SALES,
                    MessageType.BUSINESS_ERROR, FunctionType.SCREEN,
                    SalesFunctionId.SALES_COMMON_CODE_MASTER);
            assertEquals(actual.getResultObject().getDebugId(), expect);
        }
    }

    /**
     * Test case of get initialize information.
     */
    @Test
    public void testGetInitializeInformationSuccess() {

        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JAPAN");
        systemCountryList.add(countryCodeMaster);
        countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("000");
        countryCodeMaster.setName1("CHINA");
        systemCountryList.add(countryCodeMaster);

        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("USA");
        systemBrandList.add(brandCodeMaster);
        brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("000");
        brandCodeMaster.setName1("MaCao");
        systemBrandList.add(brandCodeMaster);

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);

        ReprintDailySalesReportForm reprintDailySalesReportForm = reprintDailySalesReportService
                .getInitializeInformation(new ReprintDailySalesReportForm());

        assertEquals(2, reprintDailySalesReportForm.getCountryList().size());
        assertEquals("JAPAN", reprintDailySalesReportForm.getCountryList().get(0));
        assertEquals(2, reprintDailySalesReportForm.getBrandList().size());
        assertEquals("USA", reprintDailySalesReportForm.getBrandList().get(0));
    }

    /**
     * Test case of get reprint daily sales report information.
     */
    @Test
    public void testGetReprintDailySalesReportList() {

        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JAPAN");
        systemCountryList.add(countryCodeMaster);
        countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("000");
        countryCodeMaster.setName1("CHINA");
        systemCountryList.add(countryCodeMaster);

        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("USA");
        systemBrandList.add(brandCodeMaster);
        brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("000");
        brandCodeMaster.setName1("MaCao");
        systemBrandList.add(brandCodeMaster);

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setDisplayStoreCode("0002");
        reprintDailySalesReportForm.setBusinessDate("2018/06/21");
        reprintDailySalesReportForm.setSystemBrandCode("1234");
        reprintDailySalesReportForm.setSystemCountryCode("123");

        SalesreportManagementOptional salesreportManagementOptional =
                new SalesreportManagementOptional();
        salesreportManagementOptional.setStoreCode("0002");
        salesreportManagementOptional.setBusinessDate("20180621");
        salesreportManagementOptional.setReceptionNumber("1");
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);

        when(salesreportManagementOptionalMapper.selectByPrimaryKey(anyString(), anyString()))
                .thenReturn(salesreportManagementOptional);

        reprintDailySalesReportForm = reprintDailySalesReportService
                .getReprintDailySalesReportList(reprintDailySalesReportForm);

        assertEquals("1", salesreportManagementOptional.getReceptionNumber());
    }

    /**
     * Test case of get reprint daily sales report information.
     */
    @Test
    public void testGetReprintDailySalesReportMangementNull() {

        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JAPAN");
        systemCountryList.add(countryCodeMaster);
        countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("000");
        countryCodeMaster.setName1("CHINA");
        systemCountryList.add(countryCodeMaster);

        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("USA");
        systemBrandList.add(brandCodeMaster);
        brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("000");
        brandCodeMaster.setName1("MaCao");
        systemBrandList.add(brandCodeMaster);

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setDisplayStoreCode("0002");
        reprintDailySalesReportForm.setBusinessDate("2018/06/21");
        reprintDailySalesReportForm.setSystemBrandCode("1234");
        reprintDailySalesReportForm.setSystemCountryCode("123");

        SalesreportManagementOptional salesReportManagementOptional = null;
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);

        when(salesreportManagementOptionalMapper.selectByPrimaryKey(anyString(), anyString()))
                .thenReturn(salesReportManagementOptional);

        try {
            reprintDailySalesReportForm = reprintDailySalesReportService
                    .getReprintDailySalesReportList(reprintDailySalesReportForm);
        } catch (BusinessException actual) {
            String expect = CommonUtility.createMessageId(LogLevel.ERROR, PlatformShortName.SALES,
                    MessageType.BUSINESS_ERROR, FunctionType.SCREEN,
                    SalesFunctionId.SALES_REPORT_MANAGEMENT_MASTER);
            assertEquals(expect, actual.getResultObject().getDebugId());
        }
    }

    /**
     * Test case of print reprint daily sales report information.
     */
    @Test
    public void testPrintWithBrandCodeNull() {

        List<CommonCodeMaster> systemBrandList = null;

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setDisplayStoreCode("0002");
        reprintDailySalesReportForm.setBusinessDate("2018/06/21");
        reprintDailySalesReportForm.setSystemBrandCode("1234");
        reprintDailySalesReportForm.setSystemCountryCode("123");

        SalesreportManagementOptional salesReportManagementOptional =
                new SalesreportManagementOptional();
        salesReportManagementOptional.setStoreCode("0002");
        salesReportManagementOptional.setBusinessDate("20180621");
        salesReportManagementOptional.setReceptionNumber("1");

        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("123456");
        translationStoreCodeMasterOptional.setSystemBrandCode("1234");
        translationStoreCodeMasterOptional.setSystemCountryCode("123");
        translationStoreCodeMasterOptional.setViewStoreCode("0002");

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList);
        when(salesreportManagementOptionalMapper.selectByPrimaryKey(anyString(), anyString()))
                .thenReturn(salesReportManagementOptional);
        when(translationStoreCodeDetailOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(translationStoreCodeMasterOptional);

        try {
            reprintDailySalesReportForm = reprintDailySalesReportService
                    .printReprintDailySalesReportList(reprintDailySalesReportForm);
        } catch (BusinessException actual) {
            String expect = CommonUtility.createMessageId(LogLevel.ERROR, PlatformShortName.SALES,
                    MessageType.BUSINESS_ERROR, FunctionType.SCREEN,
                    SalesFunctionId.SALES_COMMON_CODE_MASTER);
            assertEquals(actual.getResultObject().getDebugId(), expect);
        }
    }

    /**
     * Test case of print reprint daily sales report information.
     */
    @Test
    public void testPrintWithCountryCodeNull() {

        List<CommonCodeMaster> systemCountryList = null;

        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("1234");
        brandCodeMaster.setName1("USA");
        systemBrandList.add(brandCodeMaster);
        brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("000");
        brandCodeMaster.setName1("MaCao");
        systemBrandList.add(brandCodeMaster);

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setDisplayStoreCode("0002");
        reprintDailySalesReportForm.setBusinessDate("2018/06/21");
        reprintDailySalesReportForm.setSystemBrandCode("1234");
        reprintDailySalesReportForm.setSystemCountryCode("123");

        SalesreportManagementOptional salesReportManagementOptional =
                new SalesreportManagementOptional();
        salesReportManagementOptional.setStoreCode("0002");
        salesReportManagementOptional.setBusinessDate("20180621");
        salesReportManagementOptional.setReceptionNumber("1");

        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("123456");
        translationStoreCodeMasterOptional.setSystemBrandCode("1234");
        translationStoreCodeMasterOptional.setSystemCountryCode("123");
        translationStoreCodeMasterOptional.setViewStoreCode("0002");

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);
        when(salesreportManagementOptionalMapper.selectByPrimaryKey(anyString(), anyString()))
                .thenReturn(salesReportManagementOptional);
        when(translationStoreCodeDetailOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(translationStoreCodeMasterOptional);

        try {
            reprintDailySalesReportForm = reprintDailySalesReportService
                    .printReprintDailySalesReportList(reprintDailySalesReportForm);
        } catch (BusinessException actual) {
            String expect = CommonUtility.createMessageId(LogLevel.ERROR, PlatformShortName.SALES,
                    MessageType.BUSINESS_ERROR, FunctionType.SCREEN,
                    SalesFunctionId.SALES_COMMON_CODE_MASTER);
            assertEquals(actual.getResultObject().getDebugId(), expect);
        }
    }

    /**
     * Test case of get initialize information success.
     */
    @Test
    public void testPrintTranslationStoreCodeNull() {

        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("123");
        countryCodeMaster.setName1("JAPAN");
        systemCountryList.add(countryCodeMaster);
        countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("000");
        countryCodeMaster.setName1("CHINA");
        systemCountryList.add(countryCodeMaster);

        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("1234");
        brandCodeMaster.setName1("USA");
        systemBrandList.add(brandCodeMaster);
        brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("000");
        brandCodeMaster.setName1("MaCao");
        systemBrandList.add(brandCodeMaster);

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setDisplayStoreCode("0002");
        reprintDailySalesReportForm.setBusinessDate("2018/06/21");
        reprintDailySalesReportForm.setSystemBrandCode("1234");
        reprintDailySalesReportForm.setSystemCountryCode("123");

        SalesreportManagementOptional salesReportManagementOptional = null;
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional = null;

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);
        when(translationStoreCodeDetailOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(translationStoreCodeMasterOptional);
        when(salesreportManagementOptionalMapper.selectByPrimaryKey(anyString(), anyString()))
                .thenReturn(salesReportManagementOptional);

        try {
            reprintDailySalesReportForm = reprintDailySalesReportService
                    .printReprintDailySalesReportList(reprintDailySalesReportForm);
        } catch (BusinessException actual) {
            String expect = CommonUtility.createMessageId(LogLevel.ERROR, PlatformShortName.SALES,
                    MessageType.BUSINESS_ERROR, FunctionType.SCREEN,
                    SalesFunctionId.SALES_REPORT_MANAGEMENT_MASTER);
            assertEquals(expect, actual.getResultObject().getDebugId());
        }
    }

    /**
     * Test case of print reprint daily sales report information.
     */
    @Test
    public void testPrintReprintDailySalesReportList() {

        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("123");
        countryCodeMaster.setName1("JAPAN");
        systemCountryList.add(countryCodeMaster);
        countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("000");
        countryCodeMaster.setName1("CHINA");
        systemCountryList.add(countryCodeMaster);

        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("1234");
        brandCodeMaster.setName1("USA");
        systemBrandList.add(brandCodeMaster);
        brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("000");
        brandCodeMaster.setName1("MaCao");
        systemBrandList.add(brandCodeMaster);

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setDisplayStoreCode("0002");
        reprintDailySalesReportForm.setBusinessDate("2018/06/21");
        reprintDailySalesReportForm.setSystemBrandCode("1234");
        reprintDailySalesReportForm.setSystemCountryCode("123");

        SalesreportManagementOptional salesReportManagementOptional =
                new SalesreportManagementOptional();
        salesReportManagementOptional.setStoreCode("0002");
        salesReportManagementOptional.setBusinessDate("20180621");
        salesReportManagementOptional.setReceptionNumber("1");

        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("123456");
        translationStoreCodeMasterOptional.setSystemBrandCode("1234");
        translationStoreCodeMasterOptional.setSystemCountryCode("123");
        translationStoreCodeMasterOptional.setViewStoreCode("0002");

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);
        when(translationStoreCodeDetailOptionalMapper.selectByPrimaryKey(anyObject()))
                .thenReturn(translationStoreCodeMasterOptional);
        when(salesreportManagementOptionalMapper.selectByPrimaryKey(anyString(), anyString()))
                .thenReturn(salesReportManagementOptional);

        reprintDailySalesReportForm = reprintDailySalesReportService
                .printReprintDailySalesReportList(reprintDailySalesReportForm);

        assertEquals("1", salesReportManagementOptional.getReceptionNumber());
    }
}
