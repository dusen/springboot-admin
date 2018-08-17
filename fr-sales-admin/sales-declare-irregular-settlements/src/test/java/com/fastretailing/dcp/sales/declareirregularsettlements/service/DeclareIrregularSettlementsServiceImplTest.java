/**
 * @(#)DeclareIrregularSettlementsServiceImplTest.java
 *
 *                                                     Copyright (c) 2018 Fast Retailing
 *                                                     Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.declareirregularsettlements.DeclareIrregularSettlementsApplication;
import com.fastretailing.dcp.sales.declareirregularsettlements.form.DeclareIrregularSettlementsForm;
import com.fastretailing.dcp.sales.declareirregularsettlements.form.TenderPaymentInformation;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * JUnit test class of DeclareIrregularSettlementsServiceImplTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeclareIrregularSettlementsApplication.class)
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/table/junit_create_table_sales.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/table/junit_drop_table_sales.sql",
        executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class DeclareIrregularSettlementsServiceImplTest {

    /** Service access class. */
    @Autowired
    private DeclareIrregularSettlementsServiceImpl declareIrregularSettlementsServiceImpl;

    /**
     * Locale message source.
     */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** Form data class to declare irregular settlements. */
    private DeclareIrregularSettlementsForm form;

    /**
     * Initial declare irregular settlements form.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {

        form = new DeclareIrregularSettlementsForm();
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#initialize.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLog.xml")
    @DatabaseSetup("BusinessCountryStateSetting.xml")
    @DatabaseSetup("SalesReportTransactionHeader.xml")
    @DatabaseSetup("Tender.xml")
    @DatabaseSetup("PileUpPayOffData.xml")
    public void testInitialize1() {

        form.setStoreCode("sc001");
        declareIrregularSettlementsServiceImpl.initialize(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();
        formExpected.setSystemBrandCode("sbc1");
        formExpected.setSystemBrandName("brandName1");
        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setSystemCountryCode("sc1");
        formExpected.setSystemCountryName("countryName1");
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setStoreCode("sc001");
        formExpected.setDisplayStoreCode("vie1");
        formExpected.setChangeFundInteger("0");
        formExpected.setChangeFundDecimal("00");
        formExpected.setClosingBalanceInteger("0");
        formExpected.setClosingBalanceDecimal("00");
        formExpected.setRequiredBalance("");
        formExpected.setValueDiff("");
        formExpected.setEndReceiptNo("");
        formExpected.setTenderPaymentInformationList(new ArrayList<>());
        formExpected.setDecimalSize("2");
        formExpected.setCashPaymentTotal("");
        formExpected.setStoreFlag(true);
        formExpected.setNormalMessage("");

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#initialize.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMasterNoBrand.xml")
    public void testInitialize2() {

        form.setStoreCode("sc001");
        boolean exceptionExistFlag = false;

        try {
            declareIrregularSettlementsServiceImpl.initialize(form);
        } catch (BusinessException e) {
            assertEquals(MessagePrefix.E_SLS_66000128_NO_DATA_EXISTS,
                    e.getResultObject().getDebugId());

            exceptionExistFlag = true;
        }

        assertTrue(exceptionExistFlag);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#initialize.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMasterNoCountry.xml")
    public void testInitialize3() {

        form.setStoreCode("sc001");
        boolean exceptionExistFlag = false;

        try {
            declareIrregularSettlementsServiceImpl.initialize(form);
        } catch (BusinessException e) {
            assertEquals(MessagePrefix.E_SLS_66000128_NO_DATA_EXISTS,
                    e.getResultObject().getDebugId());

            exceptionExistFlag = true;
        }

        assertTrue(exceptionExistFlag);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#initialize.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCodeNoData.xml")
    public void testInitialize4() {

        form.setStoreCode("sc001");
        boolean exceptionExistFlag = false;

        try {
            declareIrregularSettlementsServiceImpl.initialize(form);
        } catch (BusinessException e) {
            assertEquals(MessagePrefix.E_SLS_66000131_NO_DATA_EXISTS,
                    e.getResultObject().getDebugId());

            exceptionExistFlag = true;
        }

        assertTrue(exceptionExistFlag);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#initialize.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCodeNoTheBrand.xml")
    public void testInitialize5() {

        form.setStoreCode("sc001");
        boolean exceptionExistFlag = false;

        try {
            declareIrregularSettlementsServiceImpl.initialize(form);
        } catch (BusinessException e) {
            assertEquals(MessagePrefix.E_SLS_66000128_NO_DATA_EXISTS,
                    e.getResultObject().getDebugId());

            exceptionExistFlag = true;
        }

        assertTrue(exceptionExistFlag);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#initialize.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCodeNoTheCountry.xml")
    public void testInitialize6() {

        form.setStoreCode("sc001");
        boolean exceptionExistFlag = false;

        try {
            declareIrregularSettlementsServiceImpl.initialize(form);
        } catch (BusinessException e) {
            assertEquals(MessagePrefix.E_SLS_66000128_NO_DATA_EXISTS,
                    e.getResultObject().getDebugId());

            exceptionExistFlag = true;
        }

        assertTrue(exceptionExistFlag);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#initialize.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    public void testInitialize7() {

        declareIrregularSettlementsServiceImpl.initialize(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();
        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setChangeFundInteger("0");
        formExpected.setChangeFundDecimal("00");
        formExpected.setClosingBalanceInteger("0");
        formExpected.setClosingBalanceDecimal("00");
        formExpected.setRequiredBalance("");
        formExpected.setValueDiff("");
        formExpected.setEndReceiptNo("");
        formExpected.setTenderPaymentInformationList(new ArrayList<>());
        formExpected.setDecimalSize("2");
        formExpected.setCashPaymentTotal("");
        formExpected.setStoreFlag(false);
        formExpected.setNormalMessage("");

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#search.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLog.xml")
    @DatabaseSetup("BusinessCountryStateSetting.xml")
    @DatabaseSetup("SalesReportTransactionHeader.xml")
    @DatabaseSetup("Tender.xml")
    @DatabaseSetup("PileUpPayOffData.xml")
    public void testSearch1() {

        form.setStoreCode("sc001");
        declareIrregularSettlementsServiceImpl.initialize(form);
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");

        declareIrregularSettlementsServiceImpl.search(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();
        formExpected.setSettlementDate("2018/06/25");
        formExpected.setSystemBrandCode("sbc1");
        formExpected.setSystemBrandName("brandName1");
        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setSystemCountryCode("sc1");
        formExpected.setSystemCountryName("countryName1");
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setStoreCode("sc001");
        formExpected.setDisplayStoreCode("vie1");
        formExpected.setCashRegisterNo("1");
        formExpected.setChangeFundInteger("0");
        formExpected.setChangeFundDecimal("");
        formExpected.setClosingBalanceInteger("0");
        formExpected.setClosingBalanceDecimal("");
        formExpected.setRequiredBalance("100");
        formExpected.setValueDiff("-100");
        formExpected.setEndReceiptNo("100");
        formExpected.setDecimalSize("0");
        formExpected.setCashPaymentTotal("100");
        formExpected.setStoreFlag(true);
        formExpected.setListHaveFlag(true);
        formExpected.setNormalMessage("");

        List<TenderPaymentInformation> tenderPaymentInformationList = new ArrayList<>();

        TenderPaymentInformation tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CASH");
        tenderPaymentInformation.setTenderId(" ");
        tenderPaymentInformation.setValue("100");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CCARD");
        tenderPaymentInformation.setTenderId("Amex");
        tenderPaymentInformation.setValue("800");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CCARD");
        tenderPaymentInformation.setTenderId("CUP");
        tenderPaymentInformation.setValue("910");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CCARD");
        tenderPaymentInformation.setTenderId("CC Unknown");
        tenderPaymentInformation.setValue("900");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("COUPN");
        tenderPaymentInformation.setTenderId("$10_Coupon");
        tenderPaymentInformation.setValue("200");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("COUPN");
        tenderPaymentInformation.setTenderId("$20_Coupon");
        tenderPaymentInformation.setValue("300");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("COUPN");
        tenderPaymentInformation.setTenderId("$30_Coupon");
        tenderPaymentInformation.setValue("400");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("EMDSC");
        tenderPaymentInformation.setTenderId("Employee discount");
        tenderPaymentInformation.setValue("993");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("GCARD");
        tenderPaymentInformation.setTenderId("Gift Card");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        formExpected.setTenderPaymentInformationList(tenderPaymentInformationList);

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#search.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLog.xml")
    @DatabaseSetup("BusinessCountryStateSetting2Code.xml")
    @DatabaseSetup("SalesReportTransactionHeader.xml")
    @DatabaseSetup("TenderLittle.xml")
    @DatabaseSetup("PileUpPayOffDataLittle.xml")
    public void testSearch2() {

        form.setStoreCode("sc001");
        declareIrregularSettlementsServiceImpl.initialize(form);
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");

        declareIrregularSettlementsServiceImpl.search(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();
        formExpected.setSettlementDate("2018/06/25");
        formExpected.setSystemBrandCode("sbc1");
        formExpected.setSystemBrandName("brandName1");
        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setSystemCountryCode("sc1");
        formExpected.setSystemCountryName("countryName1");
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setStoreCode("sc001");
        formExpected.setDisplayStoreCode("vie1");
        formExpected.setCashRegisterNo("1");
        formExpected.setChangeFundInteger("0");
        formExpected.setChangeFundDecimal("00");
        formExpected.setClosingBalanceInteger("0");
        formExpected.setClosingBalanceDecimal("00");
        formExpected.setRequiredBalance("100.44");
        formExpected.setValueDiff("-100.44");
        formExpected.setEndReceiptNo("100");
        formExpected.setDecimalSize("2");
        formExpected.setCashPaymentTotal("100.44");
        formExpected.setStoreFlag(true);
        formExpected.setListHaveFlag(true);
        formExpected.setNormalMessage("");

        List<TenderPaymentInformation> tenderPaymentInformationList = new ArrayList<>();

        TenderPaymentInformation tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CASH");
        tenderPaymentInformation.setTenderId(" ");
        tenderPaymentInformation.setValue("100.44");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CCARD");
        tenderPaymentInformation.setTenderId("Amex");
        tenderPaymentInformation.setValue("800.67");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("COUPN");
        tenderPaymentInformation.setTenderId("$10_Coupon");
        tenderPaymentInformation.setValue("200.56");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        formExpected.setTenderPaymentInformationList(tenderPaymentInformationList);

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#search.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLog.xml")
    @DatabaseSetup("BusinessCountryStateSetting5Code.xml")
    @DatabaseSetup("SalesReportTransactionHeader.xml")
    @DatabaseSetup("TenderLittle.xml")
    @DatabaseSetup("PileUpPayOffDataLittle.xml")
    public void testSearch3() {

        form.setStoreCode("sc001");
        declareIrregularSettlementsServiceImpl.initialize(form);
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");

        declareIrregularSettlementsServiceImpl.search(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();
        formExpected.setSettlementDate("2018/06/25");
        formExpected.setSystemBrandCode("sbc1");
        formExpected.setSystemBrandName("brandName1");
        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setSystemCountryCode("sc1");
        formExpected.setSystemCountryName("countryName1");
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setStoreCode("sc001");
        formExpected.setDisplayStoreCode("vie1");
        formExpected.setCashRegisterNo("1");
        formExpected.setChangeFundInteger("0");
        formExpected.setChangeFundDecimal("00000");
        formExpected.setClosingBalanceInteger("0");
        formExpected.setClosingBalanceDecimal("00000");
        formExpected.setRequiredBalance("100.44440");
        formExpected.setValueDiff("-100.44440");
        formExpected.setEndReceiptNo("100");
        formExpected.setDecimalSize("5");
        formExpected.setCashPaymentTotal("100.44440");
        formExpected.setStoreFlag(true);
        formExpected.setListHaveFlag(true);
        formExpected.setNormalMessage("");

        List<TenderPaymentInformation> tenderPaymentInformationList = new ArrayList<>();

        TenderPaymentInformation tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CASH");
        tenderPaymentInformation.setTenderId(" ");
        tenderPaymentInformation.setValue("100.44440");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CCARD");
        tenderPaymentInformation.setTenderId("Amex");
        tenderPaymentInformation.setValue("800.66660");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("COUPN");
        tenderPaymentInformation.setTenderId("$10_Coupon");
        tenderPaymentInformation.setValue("200.55550");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        formExpected.setTenderPaymentInformationList(tenderPaymentInformationList);

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#search.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLog.xml")
    @DatabaseSetup("BusinessCountryStateSetting5Code.xml")
    @DatabaseSetup("SalesReportTransactionHeader.xml")
    @DatabaseSetup("TenderLittle.xml")
    @DatabaseSetup("PileUpPayOffDataLittleComma.xml")
    public void testSearch4() {

        form.setStoreCode("sc001");
        declareIrregularSettlementsServiceImpl.initialize(form);
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");
        form.setChangeFundInteger("123");
        form.setChangeFundDecimal("456");
        form.setClosingBalanceInteger("223");
        form.setClosingBalanceDecimal("456");

        declareIrregularSettlementsServiceImpl.search(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();
        formExpected.setSettlementDate("2018/06/25");
        formExpected.setSystemBrandCode("sbc1");
        formExpected.setSystemBrandName("brandName1");
        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setSystemCountryCode("sc1");
        formExpected.setSystemCountryName("countryName1");
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setStoreCode("sc001");
        formExpected.setDisplayStoreCode("vie1");
        formExpected.setCashRegisterNo("1");
        formExpected.setChangeFundInteger("123");
        formExpected.setChangeFundDecimal("45600");
        formExpected.setClosingBalanceInteger("223");
        formExpected.setClosingBalanceDecimal("45600");
        formExpected.setRequiredBalance("123,579.90040");
        formExpected.setValueDiff("-123,356.44440");
        formExpected.setEndReceiptNo("100");
        formExpected.setDecimalSize("5");
        formExpected.setCashPaymentTotal("123456.44440");
        formExpected.setStoreFlag(true);
        formExpected.setListHaveFlag(true);
        formExpected.setNormalMessage("");

        List<TenderPaymentInformation> tenderPaymentInformationList = new ArrayList<>();

        TenderPaymentInformation tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CASH");
        tenderPaymentInformation.setTenderId(" ");
        tenderPaymentInformation.setValue("123,456.44440");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CCARD");
        tenderPaymentInformation.setTenderId("Amex");
        tenderPaymentInformation.setValue("800.66660");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("COUPN");
        tenderPaymentInformation.setTenderId("$10_Coupon");
        tenderPaymentInformation.setValue("2,234,567.55550");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        formExpected.setTenderPaymentInformationList(tenderPaymentInformationList);

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#search.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLog.xml")
    @DatabaseSetup("BusinessCountryStateSetting5Code.xml")
    @DatabaseSetup("SalesReportTransactionHeader.xml")
    @DatabaseSetup("TenderLittle.xml")
    @DatabaseSetup("PileUpPayOffDataNoData.xml")
    public void testSearch5() {

        form.setStoreCode("sc001");
        declareIrregularSettlementsServiceImpl.initialize(form);
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");
        form.setChangeFundInteger("123");
        form.setChangeFundDecimal("456");
        form.setClosingBalanceInteger("223");
        form.setClosingBalanceDecimal("456");

        declareIrregularSettlementsServiceImpl.search(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();
        formExpected.setSettlementDate("2018/06/25");
        formExpected.setSystemBrandCode("sbc1");
        formExpected.setSystemBrandName("brandName1");
        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setSystemCountryCode("sc1");
        formExpected.setSystemCountryName("countryName1");
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setStoreCode("sc001");
        formExpected.setDisplayStoreCode("vie1");
        formExpected.setCashRegisterNo("1");
        formExpected.setChangeFundInteger("");
        formExpected.setChangeFundDecimal("");
        formExpected.setClosingBalanceInteger("");
        formExpected.setClosingBalanceDecimal("");
        formExpected.setRequiredBalance("");
        formExpected.setValueDiff("");
        formExpected.setEndReceiptNo("");
        formExpected.setTenderPaymentInformationList(new ArrayList<>());
        formExpected.setDecimalSize("2");
        formExpected.setCashPaymentTotal("");
        formExpected.setStoreFlag(true);
        formExpected.setNormalMessage("");

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#search.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLog.xml")
    @DatabaseSetup("BusinessCountryStateSetting5Code.xml")
    @DatabaseSetup("SalesReportTransactionHeaderNoData.xml")
    @DatabaseSetup("TenderLittle.xml")
    @DatabaseSetup("PileUpPayOffDataNoData.xml")
    public void testSearch6() {

        form.setStoreCode("sc001");
        declareIrregularSettlementsServiceImpl.initialize(form);
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");
        form.setChangeFundInteger("123");
        form.setChangeFundDecimal("456");
        form.setClosingBalanceInteger("223");
        form.setClosingBalanceDecimal("456");

        declareIrregularSettlementsServiceImpl.search(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();
        formExpected.setSettlementDate("2018/06/25");
        formExpected.setSystemBrandCode("sbc1");
        formExpected.setSystemBrandName("brandName1");
        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setSystemCountryCode("sc1");
        formExpected.setSystemCountryName("countryName1");
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setStoreCode("sc001");
        formExpected.setDisplayStoreCode("vie1");
        formExpected.setCashRegisterNo("1");
        formExpected.setChangeFundInteger("");
        formExpected.setChangeFundDecimal("");
        formExpected.setClosingBalanceInteger("");
        formExpected.setClosingBalanceDecimal("");
        formExpected.setRequiredBalance("");
        formExpected.setValueDiff("");
        formExpected.setEndReceiptNo("");
        formExpected.setTenderPaymentInformationList(new ArrayList<>());
        formExpected.setDecimalSize("2");
        formExpected.setCashPaymentTotal("");
        formExpected.setStoreFlag(true);
        formExpected.setNormalMessage("");

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#search.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLogIsPayoff.xml")
    @DatabaseSetup("BusinessCountryStateSetting5Code.xml")
    @DatabaseSetup("SalesReportTransactionHeaderNoData.xml")
    @DatabaseSetup("TenderLittle.xml")
    @DatabaseSetup("PileUpPayOffDataNoData.xml")
    public void testSearch7() {

        form.setStoreCode("sc001");
        declareIrregularSettlementsServiceImpl.initialize(form);
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");
        form.setChangeFundInteger("123");
        form.setChangeFundDecimal("456");
        form.setClosingBalanceInteger("223");
        form.setClosingBalanceDecimal("456");

        declareIrregularSettlementsServiceImpl.search(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();
        formExpected.setSettlementDate("2018/06/25");
        formExpected.setSystemBrandCode("sbc1");
        formExpected.setSystemBrandName("brandName1");
        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setSystemCountryCode("sc1");
        formExpected.setSystemCountryName("countryName1");
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setStoreCode("sc001");
        formExpected.setDisplayStoreCode("vie1");
        formExpected.setCashRegisterNo("1");
        formExpected.setChangeFundInteger("");
        formExpected.setChangeFundDecimal("");
        formExpected.setClosingBalanceInteger("");
        formExpected.setClosingBalanceDecimal("");
        formExpected.setRequiredBalance("");
        formExpected.setValueDiff("");
        formExpected.setEndReceiptNo("");
        formExpected.setTenderPaymentInformationList(new ArrayList<>());
        formExpected.setDecimalSize("2");
        formExpected.setCashPaymentTotal("");
        formExpected.setStoreFlag(true);
        formExpected.setMessageDisplayFlag(true);
        formExpected.setNormalMessage(
                localeMessageSource.getMessage(MessagePrefix.E_SLS_66000145_PAYOFF_COMPLETE));

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#search.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLogNodata.xml")
    @DatabaseSetup("BusinessCountryStateSetting5Code.xml")
    @DatabaseSetup("SalesReportTransactionHeaderNoData.xml")
    @DatabaseSetup("TenderLittle.xml")
    @DatabaseSetup("PileUpPayOffDataNoData.xml")
    public void testSearch8() {

        form.setStoreCode("sc001");
        declareIrregularSettlementsServiceImpl.initialize(form);
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");
        form.setChangeFundInteger("123");
        form.setChangeFundDecimal("456");
        form.setClosingBalanceInteger("223");
        form.setClosingBalanceDecimal("456");

        declareIrregularSettlementsServiceImpl.search(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();
        formExpected.setSettlementDate("2018/06/25");
        formExpected.setSystemBrandCode("sbc1");
        formExpected.setSystemBrandName("brandName1");
        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setSystemCountryCode("sc1");
        formExpected.setSystemCountryName("countryName1");
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setStoreCode("sc001");
        formExpected.setDisplayStoreCode("vie1");
        formExpected.setCashRegisterNo("1");
        formExpected.setChangeFundInteger("");
        formExpected.setChangeFundDecimal("");
        formExpected.setClosingBalanceInteger("");
        formExpected.setClosingBalanceDecimal("");
        formExpected.setRequiredBalance("");
        formExpected.setValueDiff("");
        formExpected.setEndReceiptNo("");
        formExpected.setTenderPaymentInformationList(new ArrayList<>());
        formExpected.setDecimalSize("2");
        formExpected.setCashPaymentTotal("");
        formExpected.setStoreFlag(true);
        formExpected.setMessageDisplayFlag(true);
        formExpected.setNormalMessage(
                localeMessageSource.getMessage(MessagePrefix.E_SLS_66000134_NO_DATA_EXISTS));

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#search.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLogNodata.xml")
    @DatabaseSetup("BusinessCountryStateSettingNoData.xml")
    @DatabaseSetup("SalesReportTransactionHeaderNoData.xml")
    @DatabaseSetup("TenderLittle.xml")
    @DatabaseSetup("PileUpPayOffDataNoData.xml")
    public void testSearch9() {

        form.setStoreCode("sc001");
        declareIrregularSettlementsServiceImpl.initialize(form);
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");
        form.setChangeFundInteger("123");
        form.setChangeFundDecimal("456");
        form.setClosingBalanceInteger("223");
        form.setClosingBalanceDecimal("456");

        boolean exceptionExistFlag = false;
        try {
            declareIrregularSettlementsServiceImpl.search(form);
        } catch (BusinessException e) {
            assertEquals(MessagePrefix.E_SLS_66000162_NO_DATA_EXISTS,
                    e.getResultObject().getDebugId());

            exceptionExistFlag = true;
        }

        assertTrue(exceptionExistFlag);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#search.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLog.xml")
    @DatabaseSetup("BusinessCountryStateSetting2Code.xml")
    @DatabaseSetup("SalesReportTransactionHeader.xml")
    @DatabaseSetup("TenderLittle.xml")
    @DatabaseSetup("PileUpPayOffDataLittle.xml")
    public void testSearch10() {

        declareIrregularSettlementsServiceImpl.initialize(form);

        form.setDisplayStoreCode("vie1");
        form.setSystemBrandCode("sbc1");
        form.setSystemCountryCode("sc1");
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");

        declareIrregularSettlementsServiceImpl.search(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();
        formExpected.setSettlementDate("2018/06/25");
        formExpected.setSystemBrandCode("sbc1");
        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setSystemCountryCode("sc1");
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setDisplayStoreCode("vie1");
        formExpected.setCashRegisterNo("1");
        formExpected.setChangeFundInteger("0");
        formExpected.setChangeFundDecimal("00");
        formExpected.setClosingBalanceInteger("0");
        formExpected.setClosingBalanceDecimal("00");
        formExpected.setRequiredBalance("100.44");
        formExpected.setValueDiff("-100.44");
        formExpected.setEndReceiptNo("100");
        formExpected.setDecimalSize("2");
        formExpected.setCashPaymentTotal("100.44");
        formExpected.setStoreFlag(false);
        formExpected.setListHaveFlag(true);
        formExpected.setNormalMessage("");

        List<TenderPaymentInformation> tenderPaymentInformationList = new ArrayList<>();

        TenderPaymentInformation tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CASH");
        tenderPaymentInformation.setTenderId(" ");
        tenderPaymentInformation.setValue("100.44");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CCARD");
        tenderPaymentInformation.setTenderId("Amex");
        tenderPaymentInformation.setValue("800.67");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("COUPN");
        tenderPaymentInformation.setTenderId("$10_Coupon");
        tenderPaymentInformation.setValue("200.56");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        formExpected.setTenderPaymentInformationList(tenderPaymentInformationList);

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#search.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    @DatabaseSetup("TransStoreCode.xml")
    @DatabaseSetup("OpenLogNodata.xml")
    @DatabaseSetup("BusinessCountryStateSettingNoData.xml")
    @DatabaseSetup("SalesReportTransactionHeaderNoData.xml")
    @DatabaseSetup("TenderLittle.xml")
    @DatabaseSetup("PileUpPayOffDataNoData.xml")
    public void testSearch11() {

        declareIrregularSettlementsServiceImpl.initialize(form);

        form.setDisplayStoreCode("vie4");
        form.setSystemBrandCode("sbc1");
        form.setSystemCountryCode("sc1");
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");
        form.setChangeFundInteger("123");
        form.setChangeFundDecimal("456");
        form.setClosingBalanceInteger("223");
        form.setClosingBalanceDecimal("456");

        boolean exceptionExistFlag = false;
        try {
            declareIrregularSettlementsServiceImpl.search(form);
        } catch (BusinessException e) {
            assertEquals(MessagePrefix.E_SLS_66000131_NO_DATA_EXISTS,
                    e.getResultObject().getDebugId());

            exceptionExistFlag = true;
        }

        assertTrue(exceptionExistFlag);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#calculation.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testCalculation1() {

        List<TenderPaymentInformation> tenderPaymentInformationList = new ArrayList<>();

        TenderPaymentInformation tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CASH");
        tenderPaymentInformation.setTenderId(" ");
        tenderPaymentInformation.setValue("100.44");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        form.setTenderPaymentInformationList(tenderPaymentInformationList);

        form.setCashPaymentTotal("8000.444");
        form.setChangeFundInteger("7000");
        form.setChangeFundDecimal("222");
        form.setClosingBalanceInteger("25000");
        form.setClosingBalanceDecimal("888");
        form.setDecimalSize("4");

        declareIrregularSettlementsServiceImpl.calculation(form);

        assertEquals("15,000.6660", form.getRequiredBalance());
        assertEquals("10,000.2220", form.getValueDiff());
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#calculation.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testCalculation2() {

        List<TenderPaymentInformation> tenderPaymentInformationList = new ArrayList<>();

        TenderPaymentInformation tenderPaymentInformation = new TenderPaymentInformation();
        tenderPaymentInformation.setTenderGroup("CASH");
        tenderPaymentInformation.setTenderId(" ");
        tenderPaymentInformation.setValue("100.44");
        tenderPaymentInformation.setCurrencyCode("CAD");
        tenderPaymentInformationList.add(tenderPaymentInformation);

        form.setTenderPaymentInformationList(tenderPaymentInformationList);

        form.setCashPaymentTotal("8000.444");
        form.setChangeFundInteger("7000");
        form.setChangeFundDecimal("");
        form.setClosingBalanceInteger("25000");
        form.setClosingBalanceDecimal("");
        form.setDecimalSize("4");

        declareIrregularSettlementsServiceImpl.calculation(form);

        assertEquals("15,000.4440", form.getRequiredBalance());
        assertEquals("9,999.5560", form.getValueDiff());
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#setBaseInformation.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("CommonCodeMaster.xml")
    public void testSetBaseInformation() {

        declareIrregularSettlementsServiceImpl.setBaseInformation(form);

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();

        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setCountryCodeMap(countryCodeMap);

        assertEquals(formExpected, form);
    }

    /**
     * Target DeclareIrregularSettlementsServiceImpl#confirm.<BR>
     * Expected results ： The acquired data matches the expected value.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("OpenLogIsPayoff.xml")

    public void testFunctionConfirm() {

        Map<String, String> brandCodeMap = new LinkedHashMap<>();
        brandCodeMap.put("sbc1", "brandName1");
        brandCodeMap.put("sbc2", "brandName2");
        brandCodeMap.put("sbc3", "brandName3");

        Map<String, String> countryCodeMap = new LinkedHashMap<>();
        countryCodeMap.put("sc1", "countryName1");
        countryCodeMap.put("sc2", "countryName2");
        countryCodeMap.put("sc3", "countryName3");

        DeclareIrregularSettlementsForm formExpected = new DeclareIrregularSettlementsForm();

        form.setBrandCodeMap(brandCodeMap);
        form.setCountryCodeMap(countryCodeMap);
        form.setStoreCode("sc001");
        form.setSettlementDate("2018/06/25");
        form.setCashRegisterNo("1");
        form.setLoginUserId("SALES");

        declareIrregularSettlementsServiceImpl.confirm(form);

        formExpected.setBrandCodeMap(brandCodeMap);
        formExpected.setCountryCodeMap(countryCodeMap);
        formExpected.setStoreCode("sc001");
        formExpected.setSettlementDate("2018/06/25");
        formExpected.setCashRegisterNo("1");
        formExpected.setLoginUserId("SALES");
        assertEquals(formExpected, form);
    }
}
