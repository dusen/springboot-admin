package com.fastretailing.dcp.sales.settlementcorrectionhistory.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.optional.SettlementCorrectionHistoryOptional;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SettlementCorrectionHistoryOptionalMapper;
import com.fastretailing.dcp.sales.settlementcorrectionhistory.form.SettlementCorrectionHistory;
import com.fastretailing.dcp.sales.settlementcorrectionhistory.form.SettlementCorrectionHistoryListForm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SettlementCorrectionHistoryServiceListTest {

    /** Service class write off confirm. */
    @Autowired
    private SettlementCorrectionHistoryListService settlementCorrectionHistoryListService;

    /** Common code master mapper. */
    @MockBean
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Settlement correction history optional mapper. */
    @MockBean
    private SettlementCorrectionHistoryOptionalMapper settlementCorrectionHistoryOptionalMapper;

    /**
     * Test case of get initialize information success.
     */
    @Test
    public void testGetInitializeInformationSuccess() {

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("0001");
        brandCodeMaster.setName1("UQ");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("17");
        countryCodeMaster.setName1("Canada");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList)
                .thenReturn(systemCountryList);

        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                settlementCorrectionHistoryListService
                        .getInitializeInformation(new SettlementCorrectionHistoryListForm());

        assertEquals(1, settlementCorrectionHistoryListForm.getSystemBrandCodeMap().size());
        assertEquals("UQ", settlementCorrectionHistoryListForm.getSystemBrandCodeMap().get("0001"));
        assertEquals(1, settlementCorrectionHistoryListForm.getSystemCountryCodeMap().size());
        assertEquals("Canada",
                settlementCorrectionHistoryListForm.getSystemCountryCodeMap().get("17"));

    }

    /**
     * Test case of get initialize information brand list null.
     */
    @Test
    public void testGetInitializeInformationBrandListNull() throws Exception {

        ResultObject resultObject = new ResultObject();
        resultObject.setDebugId("E_SLS_66000128");
        resultObject.setMessage("No data exists in common code master.");

        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("17");
        countryCodeMaster.setName1("Canada");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(null)
                .thenReturn(systemCountryList);

        try {
            settlementCorrectionHistoryListService
                    .getInitializeInformation(new SettlementCorrectionHistoryListForm());
        } catch (BusinessException e) {
            assertEquals(resultObject, e.getResultObject());
        }

    }

    /**
     * Test case of get initialize information country list null.
     */
    @Test
    public void testGetInitializeInformationCountryListNull() {

        ResultObject resultObject = new ResultObject();
        resultObject.setDebugId("E_SLS_66000128");
        resultObject.setMessage("No data exists in common code master.");

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(null);

        try {
            settlementCorrectionHistoryListService
                    .getInitializeInformation(new SettlementCorrectionHistoryListForm());
        } catch (BusinessException e) {
            assertEquals(resultObject, e.getResultObject());
        }

    }

    /**
     * Test case of get settlement correction history list success.
     */
    @Test
    public void testGetSettlementCorrectionHistoryListSuccess() {

        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                new SettlementCorrectionHistoryListForm();

        settlementCorrectionHistoryListForm.setSystemBrandCode("0001");
        settlementCorrectionHistoryListForm.setSystemCountryCode("17");
        settlementCorrectionHistoryListForm.setViewStoreCode("9720");
        settlementCorrectionHistoryListForm.setPayoffDateFrom("2018/05/01");
        settlementCorrectionHistoryListForm.setPayoffDateTo("2018/05/30");
        settlementCorrectionHistoryListForm.setCashRegisterNo("111");
        settlementCorrectionHistoryListForm.setCorrector("mygration");
        settlementCorrectionHistoryListForm.setCorrectionDateFrom("2018/05/01");
        settlementCorrectionHistoryListForm.setCorrectionDateTo("2018/05/30");

        SettlementCorrectionHistoryOptional settlementCorrectionHistoryOptional =
                new SettlementCorrectionHistoryOptional();

        settlementCorrectionHistoryOptional.setSystemBrandCode("UQ");
        settlementCorrectionHistoryOptional.setSystemCountryCode("Canada");
        settlementCorrectionHistoryOptional.setViewStoreCode("9720");
        settlementCorrectionHistoryOptional.setStoreCode("112081");
        settlementCorrectionHistoryOptional.setStoreName("SSC");
        settlementCorrectionHistoryOptional.setCashRegisterNo(BigDecimal.valueOf(111));
        settlementCorrectionHistoryOptional.setPayoffDate("20180511");
        settlementCorrectionHistoryOptional.setPayoffTypeCode("0001");
        settlementCorrectionHistoryOptional.setPayoffTypeName("0001_name");
        settlementCorrectionHistoryOptional.setPayoffTypeSubNumberCode("0001AAA");
        settlementCorrectionHistoryOptional.setPayoffTypeSubNumberName("0001AAA\name");
        settlementCorrectionHistoryOptional.setPayoffAmountBefore(new BigDecimal("100.3500"));
        settlementCorrectionHistoryOptional.setPayoffAmountAfter(new BigDecimal("200.3500"));
        settlementCorrectionHistoryOptional.setPayoffQuantityBefore(new BigDecimal("300.3500"));
        settlementCorrectionHistoryOptional.setPayoffQuantityAfter(new BigDecimal("400.3500"));
        settlementCorrectionHistoryOptional.setUpdateUserId("mygration");
        settlementCorrectionHistoryOptional
                .setUpdateDateTime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));

        SettlementCorrectionHistoryOptional settlementCorrectionHistoryOptionalSecond =
                new SettlementCorrectionHistoryOptional();

        settlementCorrectionHistoryOptionalSecond.setSystemBrandCode("UQ");
        settlementCorrectionHistoryOptionalSecond.setSystemCountryCode("Canada");
        settlementCorrectionHistoryOptionalSecond.setViewStoreCode("9720");
        settlementCorrectionHistoryOptionalSecond.setStoreCode("112081");
        settlementCorrectionHistoryOptionalSecond.setStoreName("SSC");
        settlementCorrectionHistoryOptionalSecond.setCashRegisterNo(BigDecimal.valueOf(111));
        settlementCorrectionHistoryOptionalSecond.setPayoffDate("20180511");
        settlementCorrectionHistoryOptionalSecond.setPayoffTypeCode("0001");
        settlementCorrectionHistoryOptionalSecond.setPayoffTypeName("0001_name");
        settlementCorrectionHistoryOptionalSecond.setPayoffTypeSubNumberCode("0001AAA");
        settlementCorrectionHistoryOptionalSecond.setPayoffTypeSubNumberName("0001AAA\name");
        settlementCorrectionHistoryOptionalSecond.setPayoffAmountBefore(new BigDecimal("200.3500"));
        settlementCorrectionHistoryOptionalSecond.setPayoffAmountAfter(new BigDecimal("200.3500"));
        settlementCorrectionHistoryOptionalSecond
                .setPayoffQuantityBefore(new BigDecimal("400.3500"));
        settlementCorrectionHistoryOptionalSecond
                .setPayoffQuantityAfter(new BigDecimal("400.3500"));
        settlementCorrectionHistoryOptionalSecond.setUpdateUserId("mygration");
        settlementCorrectionHistoryOptionalSecond
                .setUpdateDateTime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));

        List<SettlementCorrectionHistoryOptional> settlementCorrectionHistoryDetail =
                new ArrayList<>();
        settlementCorrectionHistoryDetail.add(settlementCorrectionHistoryOptional);
        settlementCorrectionHistoryDetail.add(settlementCorrectionHistoryOptionalSecond);

        when(settlementCorrectionHistoryOptionalMapper
                .selectStoreCodeAndNameByViewStoreCode(anyObject()))
                        .thenReturn(settlementCorrectionHistoryDetail);

        when(settlementCorrectionHistoryOptionalMapper
                .selectSettlementCorrectionHistoryListByCondition(anyObject()))
                        .thenReturn(settlementCorrectionHistoryDetail);


        CommonCodeMaster systemBrandName = new CommonCodeMaster();

        systemBrandName.setTypeId("system_brand_code");
        systemBrandName.setTypeValue("0001");
        systemBrandName.setName1("UQ");

        CommonCodeMaster systemCountryName = new CommonCodeMaster();

        systemCountryName.setTypeId("system_country_code");
        systemCountryName.setTypeValue("17");
        systemCountryName.setName1("Canada");

        List<CommonCodeMaster> systemBrandNameList = new ArrayList<>();
        systemBrandNameList.add(systemBrandName);

        List<CommonCodeMaster> systemCountryNameList = new ArrayList<>();
        systemCountryNameList.add(systemCountryName);

        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandNameList)
                .thenReturn(systemCountryNameList);

        SettlementCorrectionHistoryListForm rtnSettlementCorrectionHistoryListForm =
                settlementCorrectionHistoryListService
                        .getSettlementCorrectionHistoryList(settlementCorrectionHistoryListForm);

        assertEquals("0001", rtnSettlementCorrectionHistoryListForm.getSystemBrandCode());
        assertEquals("17", rtnSettlementCorrectionHistoryListForm.getSystemCountryCode());
        assertEquals("9720", rtnSettlementCorrectionHistoryListForm.getViewStoreCode());

        SettlementCorrectionHistory rtnSettlementCorrectionHistoryList =
                rtnSettlementCorrectionHistoryListForm.getSettlementCorrectionHistoryList().get(0);

        assertEquals("UQ", rtnSettlementCorrectionHistoryList.getBrandCode());
        assertEquals("Canada", rtnSettlementCorrectionHistoryList.getCountryCode());
        assertEquals("9720", rtnSettlementCorrectionHistoryList.getViewStoreCode());
        assertEquals("SSC", rtnSettlementCorrectionHistoryList.getStoreName());
        assertEquals("111", rtnSettlementCorrectionHistoryList.getCashRegisterNo());
        assertEquals("2018/05/11", rtnSettlementCorrectionHistoryList.getPayoffDate());
        assertEquals("0001", rtnSettlementCorrectionHistoryList.getPayoffTypeCode());
        assertEquals("0001_name", rtnSettlementCorrectionHistoryList.getPayoffTypeCodeName());
        assertEquals("0001AAA", rtnSettlementCorrectionHistoryList.getPayoffTypeSubNumber());
        assertEquals("0001AAA\name",
                rtnSettlementCorrectionHistoryList.getPayoffTypeSubNumberName());
        assertEquals("100.3500", rtnSettlementCorrectionHistoryList.getPayoffAmountBefore());
        assertEquals("200.3500", rtnSettlementCorrectionHistoryList.getPayoffAmountAfter());
        assertEquals("1", rtnSettlementCorrectionHistoryList.getAmountDiffFlag());
        assertEquals("300.3500", rtnSettlementCorrectionHistoryList.getPayoffQuantityBefore());
        assertEquals("400.3500", rtnSettlementCorrectionHistoryList.getPayoffQuantityAfter());
        assertEquals("1", rtnSettlementCorrectionHistoryList.getQuantityDiffFlag());
        assertEquals("mygration", rtnSettlementCorrectionHistoryList.getCorrector());
        assertEquals("2018/05/11 00:00:00", rtnSettlementCorrectionHistoryList.getCorrectionDate());

        SettlementCorrectionHistory rtnSettlementCorrectionHistoryListSecond =
                rtnSettlementCorrectionHistoryListForm.getSettlementCorrectionHistoryList().get(1);

        assertEquals("0", rtnSettlementCorrectionHistoryListSecond.getAmountDiffFlag());
        assertEquals("0", rtnSettlementCorrectionHistoryListSecond.getQuantityDiffFlag());
    }

    /**
     * Test case of get settlement correction history list when store code not exist.
     */
    @Test
    public void testGetSettlementCorrectionHistoryListStoreCodeNotExist() {

        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                new SettlementCorrectionHistoryListForm();

        settlementCorrectionHistoryListForm.setSystemBrandCode("0001");
        settlementCorrectionHistoryListForm.setSystemCountryCode("17");
        settlementCorrectionHistoryListForm.setViewStoreCode("9999");

        List<SettlementCorrectionHistoryOptional> settlementCorrectionHistoryDetail =
                new ArrayList<>();

        when(settlementCorrectionHistoryOptionalMapper
                .selectStoreCodeAndNameByViewStoreCode(anyObject()))
                        .thenReturn(settlementCorrectionHistoryDetail);

        SettlementCorrectionHistoryListForm rtnSettlementCorrectionHistoryListForm =
                settlementCorrectionHistoryListService
                        .getSettlementCorrectionHistoryList(settlementCorrectionHistoryListForm);

        assertEquals("0001", rtnSettlementCorrectionHistoryListForm.getSystemBrandCode());
        assertEquals("17", rtnSettlementCorrectionHistoryListForm.getSystemCountryCode());
        assertEquals("9999", rtnSettlementCorrectionHistoryListForm.getViewStoreCode());

        assertNull(rtnSettlementCorrectionHistoryListForm.getSettlementCorrectionHistoryList());

    }

    /**
     * Test case of get settlement correction history list when corrector not exist.
     */
    @Test
    public void testGetSettlementCorrectionHistoryListCorrectorNotExist() {

        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                new SettlementCorrectionHistoryListForm();

        settlementCorrectionHistoryListForm.setSystemBrandCode("0001");
        settlementCorrectionHistoryListForm.setSystemCountryCode("17");
        settlementCorrectionHistoryListForm.setViewStoreCode("9720");
        settlementCorrectionHistoryListForm.setCorrector("AAA");

        SettlementCorrectionHistoryOptional storeCodeAndName =
                new SettlementCorrectionHistoryOptional();
        storeCodeAndName.setStoreCode("112081");
        storeCodeAndName.setStoreName("SSC");

        List<SettlementCorrectionHistoryOptional> storeCodeAndNameList = new ArrayList<>();
        storeCodeAndNameList.add(storeCodeAndName);

        when(settlementCorrectionHistoryOptionalMapper
                .selectStoreCodeAndNameByViewStoreCode(anyObject()))
                        .thenReturn(storeCodeAndNameList);

        List<SettlementCorrectionHistoryOptional> settlementCorrectionHistoryDetail =
                new ArrayList<>();

        when(settlementCorrectionHistoryOptionalMapper
                .selectSettlementCorrectionHistoryListByCondition(anyObject()))
                        .thenReturn(settlementCorrectionHistoryDetail);

        SettlementCorrectionHistoryListForm rtnSettlementCorrectionHistoryListForm =
                settlementCorrectionHistoryListService
                        .getSettlementCorrectionHistoryList(settlementCorrectionHistoryListForm);

        assertEquals("0001", rtnSettlementCorrectionHistoryListForm.getSystemBrandCode());
        assertEquals("17", rtnSettlementCorrectionHistoryListForm.getSystemCountryCode());
        assertEquals("9720", rtnSettlementCorrectionHistoryListForm.getViewStoreCode());
        assertEquals("AAA", rtnSettlementCorrectionHistoryListForm.getCorrector());

        assertNull(rtnSettlementCorrectionHistoryListForm.getSettlementCorrectionHistoryList());

    }

    /**
     * Test case of get sorted settlement correction history list success.
     */
    @Test
    public void testGetSortedSettlementCorrectionHistoryListDesc() {

        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                new SettlementCorrectionHistoryListForm();

        settlementCorrectionHistoryListForm.setSystemBrandCode("0001");
        settlementCorrectionHistoryListForm.setSystemCountryCode("17");
        settlementCorrectionHistoryListForm.setViewStoreCode("9720");
        settlementCorrectionHistoryListForm.setSortItem("payoffTypeCode");
        settlementCorrectionHistoryListForm.setOrderByClause(1);

        SettlementCorrectionHistory settlementCorrectionHistoryFirst =
                new SettlementCorrectionHistory();

        settlementCorrectionHistoryFirst.setBrandCode("UQ");
        settlementCorrectionHistoryFirst.setCountryCode("Canada");
        settlementCorrectionHistoryFirst.setViewStoreCode("9720");
        settlementCorrectionHistoryFirst.setStoreName("SSC");
        settlementCorrectionHistoryFirst.setCashRegisterNo("111");
        settlementCorrectionHistoryFirst.setPayoffDate("20180511");
        settlementCorrectionHistoryFirst.setPayoffTypeCode("0001");
        settlementCorrectionHistoryFirst.setPayoffTypeCodeName("0001_name");
        settlementCorrectionHistoryFirst.setPayoffTypeSubNumber("0001AAA");
        settlementCorrectionHistoryFirst.setPayoffTypeSubNumberName("0001AAA\name");
        settlementCorrectionHistoryFirst.setPayoffAmountBefore("100.3500");
        settlementCorrectionHistoryFirst.setPayoffAmountAfter("200.3500");
        settlementCorrectionHistoryFirst.setPayoffQuantityBefore("300.3500");
        settlementCorrectionHistoryFirst.setPayoffQuantityAfter("400.3500");
        settlementCorrectionHistoryFirst.setCorrector("mygration");
        settlementCorrectionHistoryFirst.setCorrectionDate("2018-05-11T00:00+09:00");

        SettlementCorrectionHistory settlementCorrectionHistorySecond =
                new SettlementCorrectionHistory();

        settlementCorrectionHistorySecond.setBrandCode("UQ");
        settlementCorrectionHistorySecond.setCountryCode("Canada");
        settlementCorrectionHistorySecond.setViewStoreCode("9720");
        settlementCorrectionHistorySecond.setStoreName("SSC");
        settlementCorrectionHistorySecond.setCashRegisterNo("222");
        settlementCorrectionHistorySecond.setPayoffDate("20180511");
        settlementCorrectionHistorySecond.setPayoffTypeCode("0002");
        settlementCorrectionHistorySecond.setPayoffTypeCodeName("0002_name");
        settlementCorrectionHistorySecond.setPayoffTypeSubNumber("0002AAA");
        settlementCorrectionHistorySecond.setPayoffTypeSubNumberName("0002AAA\name");
        settlementCorrectionHistorySecond.setPayoffAmountBefore("100.3500");
        settlementCorrectionHistorySecond.setPayoffAmountAfter("200.3500");
        settlementCorrectionHistorySecond.setPayoffQuantityBefore("300.3500");
        settlementCorrectionHistorySecond.setPayoffQuantityAfter("400.3500");
        settlementCorrectionHistorySecond.setCorrector("mygration");
        settlementCorrectionHistorySecond.setCorrectionDate("2018-05-11T00:00+09:00");

        List<SettlementCorrectionHistory> settlementCorrectionHistoryDetail = new ArrayList<>();
        settlementCorrectionHistoryDetail.add(settlementCorrectionHistoryFirst);
        settlementCorrectionHistoryDetail.add(settlementCorrectionHistorySecond);

        settlementCorrectionHistoryListForm
                .setSettlementCorrectionHistoryList(settlementCorrectionHistoryDetail);

        SettlementCorrectionHistoryListForm rtnSettlementCorrectionHistoryListForm =
                settlementCorrectionHistoryListService.getSortedSettlementCorrectionHistoryList(
                        settlementCorrectionHistoryListForm);

        List<SettlementCorrectionHistory> sortedSettlementCorrectionHistoryDetail =
                rtnSettlementCorrectionHistoryListForm.getSettlementCorrectionHistoryList();

        assertEquals(settlementCorrectionHistorySecond,
                sortedSettlementCorrectionHistoryDetail.get(0));
        assertEquals(settlementCorrectionHistoryFirst,
                sortedSettlementCorrectionHistoryDetail.get(1));

    }

    /**
     * Test case of get sorted settlement correction history list success.
     */
    @Test
    public void testGetSortedSettlementCorrectionHistoryListAsc() {
        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                new SettlementCorrectionHistoryListForm();

        settlementCorrectionHistoryListForm.setSystemBrandCode("0001");
        settlementCorrectionHistoryListForm.setSystemCountryCode("17");
        settlementCorrectionHistoryListForm.setViewStoreCode("9720");
        settlementCorrectionHistoryListForm.setSortItem("payoffTypeCode");
        settlementCorrectionHistoryListForm.setOrderByClause(0);

        SettlementCorrectionHistory settlementCorrectionHistoryFirst =
                new SettlementCorrectionHistory();

        settlementCorrectionHistoryFirst.setBrandCode("UQ");
        settlementCorrectionHistoryFirst.setCountryCode("Canada");
        settlementCorrectionHistoryFirst.setViewStoreCode("9720");
        settlementCorrectionHistoryFirst.setStoreName("SSC");
        settlementCorrectionHistoryFirst.setCashRegisterNo("111");
        settlementCorrectionHistoryFirst.setPayoffDate("20180511");
        settlementCorrectionHistoryFirst.setPayoffTypeCode("0001");
        settlementCorrectionHistoryFirst.setPayoffTypeCodeName("0001_name");
        settlementCorrectionHistoryFirst.setPayoffTypeSubNumber("0001AAA");
        settlementCorrectionHistoryFirst.setPayoffTypeSubNumberName("0001AAA\name");
        settlementCorrectionHistoryFirst.setPayoffAmountBefore("100.3500");
        settlementCorrectionHistoryFirst.setPayoffAmountAfter("200.3500");
        settlementCorrectionHistoryFirst.setPayoffQuantityBefore("300.3500");
        settlementCorrectionHistoryFirst.setPayoffQuantityAfter("400.3500");
        settlementCorrectionHistoryFirst.setCorrector("mygration");
        settlementCorrectionHistoryFirst.setCorrectionDate("2018-05-11T00:00+09:00");

        SettlementCorrectionHistory settlementCorrectionHistorySecond =
                new SettlementCorrectionHistory();

        settlementCorrectionHistorySecond.setBrandCode("UQ");
        settlementCorrectionHistorySecond.setCountryCode("Canada");
        settlementCorrectionHistorySecond.setViewStoreCode("9720");
        settlementCorrectionHistorySecond.setStoreName("SSC");
        settlementCorrectionHistorySecond.setCashRegisterNo("222");
        settlementCorrectionHistorySecond.setPayoffDate("20180511");
        settlementCorrectionHistorySecond.setPayoffTypeCode("0002");
        settlementCorrectionHistorySecond.setPayoffTypeCodeName("0002_name");
        settlementCorrectionHistorySecond.setPayoffTypeSubNumber("0002AAA");
        settlementCorrectionHistorySecond.setPayoffTypeSubNumberName("0002AAA\name");
        settlementCorrectionHistorySecond.setPayoffAmountBefore("100.3500");
        settlementCorrectionHistorySecond.setPayoffAmountAfter("200.3500");
        settlementCorrectionHistorySecond.setPayoffQuantityBefore("300.3500");
        settlementCorrectionHistorySecond.setPayoffQuantityAfter("400.3500");
        settlementCorrectionHistorySecond.setCorrector("mygration");
        settlementCorrectionHistorySecond.setCorrectionDate("2018-05-11T00:00+09:00");

        List<SettlementCorrectionHistory> settlementCorrectionHistoryDetail = new ArrayList<>();
        settlementCorrectionHistoryDetail.add(settlementCorrectionHistorySecond);
        settlementCorrectionHistoryDetail.add(settlementCorrectionHistoryFirst);


        settlementCorrectionHistoryListForm
                .setSettlementCorrectionHistoryList(settlementCorrectionHistoryDetail);

        SettlementCorrectionHistoryListForm rtnSettlementCorrectionHistoryListForm =
                settlementCorrectionHistoryListService.getSortedSettlementCorrectionHistoryList(
                        settlementCorrectionHistoryListForm);

        List<SettlementCorrectionHistory> sortedSettlementCorrectionHistoryDetail =
                rtnSettlementCorrectionHistoryListForm.getSettlementCorrectionHistoryList();

        assertEquals(settlementCorrectionHistoryFirst,
                sortedSettlementCorrectionHistoryDetail.get(0));
        assertEquals(settlementCorrectionHistorySecond,
                sortedSettlementCorrectionHistoryDetail.get(1));


    }

}
