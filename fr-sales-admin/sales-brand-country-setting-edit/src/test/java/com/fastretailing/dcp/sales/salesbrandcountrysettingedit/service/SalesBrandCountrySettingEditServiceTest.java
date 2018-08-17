/**
 * @(#)SalesBrandCountrySettingEditServiceTest.java
 *
 *                                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.PayOffSummaryMappingMaster;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountrySettingMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountryStateSettingMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.PayOffSummaryMappingMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.BusinessCountrySettingMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.BusinessCountryStateSettingMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TranslationStoreCodeMasterOptionalMapper;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.common.ItemSetting;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.Brand;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.BrandCountrySettingEdit;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.BrandCountrySettingEditForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.Country;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.SalesIntegrityCheck;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSetting;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetail;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetailForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingForm;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.config.DevelopmentConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesBrandCountrySettingEditServiceTest {

    /** Sales brand country setting edit service. */
    @Autowired
    private SalesBrandCountrySettingEditService salesBrandCountrySettingEditService;

    /** Common code master mapper. */
    @MockBean
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Translation store code master optional mapper. */
    @MockBean
    private TranslationStoreCodeMasterOptionalMapper translationStoreCodeMasterOptionalMapper;

    /** Business country setting master optional mapper. */
    @MockBean
    private BusinessCountrySettingMasterOptionalMapper businessCountrySettingMasterOptionalMapper;

    /**
     * Business country State setting master optional mapper.
     */
    @MockBean
    private BusinessCountryStateSettingMasterOptionalMapper businessCountryStateSettingMasterOptionalMapper;

    /** Store setting form. */
    private StoreSettingForm storeSettingForm;

    /** Brand country setting edit form. */
    private BrandCountrySettingEditForm brandCountrySettingEditForm;

    /** storeSettingDetailForm. */
    private StoreSettingDetailForm storeSettingDetailForm;

    /** Pay off summary mapping master mapper. */
    @MockBean
    private PayOffSummaryMappingMasterMapper payOffSummaryMappingMasterMapper;

    /** Configuration for development. */
    @Autowired
    private DevelopmentConfiguration developmentConfiguration;

    /**
     * Setup data before test.
     */
    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        // User details for unit test.
        List<GrantedAuthority> authorityList = developmentConfiguration.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UserDetails userDetails = new UserDetails(developmentConfiguration.getStoreCode(),
                developmentConfiguration.getUserId(), "testPassword", true, true, true, true,
                authorityList);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        storeSettingForm = new StoreSettingForm();
        storeSettingForm.setStoreCode("112326");
        storeSettingForm.setStoreBrandCode("UQ");
        storeSettingForm.setStoreCountryCode("UK");
        brandCountrySettingEditForm = new BrandCountrySettingEditForm();
        brandCountrySettingEditForm.setViewStoreCode("112326");
        storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCode("001");
        storeSettingDetailForm.setBrandName("UQ");
        storeSettingDetailForm.setStoreBrandCode("0001");
        storeSettingDetailForm.setStoreCountryCode("0002");
        storeSettingDetailForm.setStoreCode("112326");
        storeSettingDetailForm.setCodeL1("L1");
    }

    /**
     * Test case of get brand list.
     */
    @Test
    public void testGetBrandList() {
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList);
        List<Brand> brandList = salesBrandCountrySettingEditService.getBrandList();
        assertEquals(1, brandList.size());
    }

    /**
     * Test case of get country list.
     */
    @Test
    public void testGetCountryList() {
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_country_code");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList);
        List<Country> countryList = salesBrandCountrySettingEditService.getCountryList();
        assertEquals(1, countryList.size());
    }

    /**
     * Test case of get brand country setting edit list.
     */
    @Test
    public void testGetBrandCountrySettingEditList() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_country_code");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList);
        CommonCodeMaster commonCodeMaster = new CommonCodeMaster();
        commonCodeMaster.setTypeId("id");
        commonCodeMaster.setTypeValue("value");
        when(commonCodeMasterMapper.selectByPrimaryKey(anyObject())).thenReturn(commonCodeMaster);
        BusinessCountrySettingMasterOptional businessCountrySettingMasterOptional =
                new BusinessCountrySettingMasterOptional();
        businessCountrySettingMasterOptional.setStoreCode("112326");
        List<BusinessCountrySettingMasterOptional> list = new ArrayList<>();
        list.add(businessCountrySettingMasterOptional);
        when(businessCountrySettingMasterOptionalMapper.selectStoreList(anyObject()))
                .thenReturn(list);
        List<BrandCountrySettingEdit> brandCountrySettingEditList =
                salesBrandCountrySettingEditService
                        .getBrandCountrySettingEditList(brandCountrySettingEditForm);
        assertEquals(1, brandCountrySettingEditList.size());
    }

    /**
     * Test case of get store setting list.
     */
    @Test
    public void testGetStoreSettingList() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_country_code");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemBrandList);
        CommonCodeMaster commonCodeMaster = new CommonCodeMaster();
        commonCodeMaster.setTypeId("id");
        commonCodeMaster.setTypeValue("value");
        when(commonCodeMasterMapper.selectByPrimaryKey(anyObject())).thenReturn(commonCodeMaster);

        List<String> storeList = new ArrayList<>();
        storeList.add("112326");
        storeList.add("112327");
        when(businessCountrySettingMasterOptionalMapper.selectCodeL1List(anyObject()))
                .thenReturn(storeList);
        List<StoreSetting> storeSettingList =
                salesBrandCountrySettingEditService.getStoreSettingList(storeSettingForm);
        assertEquals(8, storeSettingList.size());
    }

    /**
     * Test case of get brand and country.
     */
    @Test
    public void testGetBrandAndCountry() {
        CommonCodeMaster commonCodeMaster = new CommonCodeMaster();
        commonCodeMaster.setTypeId("123");
        commonCodeMaster.setTypeValue("value");
        commonCodeMaster.setName1("Name");
        when(commonCodeMasterMapper.selectByPrimaryKey(anyObject())).thenReturn(commonCodeMaster);
        String test = salesBrandCountrySettingEditService.getBrandAndCountry("123", "value");
        assertEquals("Name", test);
    }

    /**
     * Test case of get store information.
     */
    @Test
    public void testGetStoreInformation() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        when(translationStoreCodeMasterOptionalMapper.selectByPrimaryKey(anyString()))
                .thenReturn(translationStoreCodeMasterOptional);
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptionalExpect =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptionalExpect.setStoreCode("112326");
        translationStoreCodeMasterOptionalExpect.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptionalExpect.setSystemCountryCode("UK");
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptionalActual =
                salesBrandCountrySettingEditService.getStoreInformation("112326");
        assertEquals(translationStoreCodeMasterOptionalExpect,
                translationStoreCodeMasterOptionalActual);

    }

    /**
     * Test case of delete store setting list.
     */
    @Test
    public void testDeleteStoreSettingList() {
        when(businessCountryStateSettingMasterOptionalMapper.deleteByCondition(anyObject()))
                .thenReturn(1);
        int count = salesBrandCountrySettingEditService.deleteStoreSettingList(storeSettingForm);
        assertEquals(1, count);
    }

    /**
     * Test case of add brand country setting list.
     */
    @Test
    public void testAddBrandCountrySettinglist() {
        BusinessCountrySettingMasterOptional businessCountrySettingMasterOptional =
                new BusinessCountrySettingMasterOptional();
        businessCountrySettingMasterOptional.setStoreCode("112326");
        List<BusinessCountrySettingMasterOptional> list = new ArrayList<>();
        list.add(businessCountrySettingMasterOptional);
        when(businessCountrySettingMasterOptionalMapper.selectStoreCodeL1(anyObject()))
                .thenReturn(list);
        int count = salesBrandCountrySettingEditService
                .addBrandCountrySettinglist(brandCountrySettingEditForm);
        assertEquals(0, count);
    }

    /**
     * Test case of count store setting list.
     */
    @Test
    public void testCountStoreSettingList() {
        List<String> storeList = new ArrayList<>();
        storeList.add("112326");
        storeList.add("112327");
        when(businessCountrySettingMasterOptionalMapper.selectCodeL1List(anyObject()))
                .thenReturn(storeList);
        int count = salesBrandCountrySettingEditService.countStoreSettingList(storeSettingForm);
        assertEquals(2, count);
    }

    /**
     * Test case of count business setting detail list.
     */
    @Test
    public void testCountBusinessSettingDetailList() {
        when(businessCountryStateSettingMasterOptionalMapper.countByCondition(anyObject()))
                .thenReturn(2L);
        int count = salesBrandCountrySettingEditService
                .countBusinessSettingDetailList(storeSettingDetailForm);
        assertEquals(2, count);
    }

    /**
     * Test case of get detail default list.
     */
    @Test
    public void testGetDetailDefaultList() {
        BusinessCountryStateSettingMasterOptional businessCountryStateSettingMasterOptional =
                new BusinessCountryStateSettingMasterOptional();
        businessCountryStateSettingMasterOptional.setUpdateDatetime(OffsetDateTime.now());
        businessCountryStateSettingMasterOptional.setStoreCode("112326");
        List<BusinessCountryStateSettingMasterOptional> detailListExpect = new ArrayList<>();
        detailListExpect.add(businessCountryStateSettingMasterOptional);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(detailListExpect);
        List<StoreSettingDetail> detailList =
                salesBrandCountrySettingEditService.getDetailDefaultList(storeSettingDetailForm);
        assertEquals(1, detailList.size());
    }

    /**
     * Test case of get detail list.
     */
    @Test
    public void testGetDetailList() {
        BusinessCountryStateSettingMasterOptional businessCountryStateSettingMasterOptional =
                new BusinessCountryStateSettingMasterOptional();
        businessCountryStateSettingMasterOptional.setUpdateDatetime(OffsetDateTime.now());
        businessCountryStateSettingMasterOptional.setStoreCode("112326");
        List<BusinessCountryStateSettingMasterOptional> detailListExpect = new ArrayList<>();
        detailListExpect.add(businessCountryStateSettingMasterOptional);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(detailListExpect);
        List<StoreSettingDetail> detailList =
                salesBrandCountrySettingEditService.getDetailList(storeSettingDetailForm);
        assertEquals(1, detailList.size());
    }

    /**
     * Test case of get order status list.
     */
    @Test
    public void testGetOrderStatusList() {
        CommonCodeMaster commonCodeMaster = new CommonCodeMaster();
        commonCodeMaster.setTypeId("123");
        List<CommonCodeMaster> list = new ArrayList<>();
        list.add(commonCodeMaster);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(list);
        List<String> detailList = salesBrandCountrySettingEditService.getOrderStatusList("123");
        assertEquals(1, detailList.size());
    }

    /**
     * Test case of mapping pattern name.
     */
    @Test
    public void testGetMappingPatternName() {
        PayOffSummaryMappingMaster payOffSummaryMappingMaster = new PayOffSummaryMappingMaster();
        payOffSummaryMappingMaster.setMappingPattern(1);
        payOffSummaryMappingMaster.setMappingPtnName("name");
        List<PayOffSummaryMappingMaster> payOffList = new ArrayList<>();
        payOffList.add(payOffSummaryMappingMaster);
        when(payOffSummaryMappingMasterMapper.selectByCondition(anyObject()))
                .thenReturn(payOffList);
        String stringActual = salesBrandCountrySettingEditService.getMappingPatternName();
        assertEquals("name", stringActual);

    }

    /**
     * Test case of insert default detail.
     */
    @Test
    public void testInsertDefaultDetail() {
        StoreSettingDetail storeSettingDetail = new StoreSettingDetail();
        storeSettingDetail.setBrandCode("UQ");
        storeSettingDetail.setCodeL1("L1");
        List<StoreSettingDetail> brandCountrySettingEditList = new ArrayList<>();
        brandCountrySettingEditList.add(storeSettingDetail);
        storeSettingDetailForm.setBrandCountrySettingEditList(brandCountrySettingEditList);
        when(businessCountryStateSettingMasterOptionalMapper.insert(anyObject())).thenReturn(2);
        int count = salesBrandCountrySettingEditService.insertDefaultDetail(storeSettingDetailForm);
        assertEquals(2, count);
    }

    /**
     * Test case of update detail.
     */
    @Test
    public void testUpdateDetailFailed() {
        StoreSettingDetail storeSettingDetail = new StoreSettingDetail();
        storeSettingDetail.setBrandCode("UQ");
        storeSettingDetail.setCodeL1("L1");
        List<StoreSettingDetail> brandCountrySettingEditList = new ArrayList<>();
        brandCountrySettingEditList.add(storeSettingDetail);
        storeSettingDetailForm.setBrandCountrySettingEditList(brandCountrySettingEditList);
        when(businessCountryStateSettingMasterOptionalMapper.updateByConditionSelective(anyObject(),
                anyObject())).thenReturn(2);
        int count = salesBrandCountrySettingEditService.updateDetail(storeSettingDetailForm);
        assertEquals(0, count);
    }

    /**
     * Test case of update detail.
     */
    @Test
    public void testUpdateDetailSuccess() {
        StoreSettingDetail storeSettingDetail = new StoreSettingDetail();
        storeSettingDetail.setBrandCode("UQ");
        storeSettingDetail.setCodeL1("L1");
        storeSettingDetail.setCodeL2("L2");
        storeSettingDetail.setCodeL3("L3");
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setBrandCode("UQ");
        storeSettingDetail2.setCodeL1("L11");
        storeSettingDetail2.setCodeL2("L22");
        storeSettingDetail2.setCodeL3("L33");
        storeSettingDetail2.setUpdateDatetime("2018-05-31 12:15:41.884255Z");
        List<StoreSettingDetail> brandCountrySettingEditList = new ArrayList<>();
        brandCountrySettingEditList.add(storeSettingDetail);
        brandCountrySettingEditList.add(storeSettingDetail2);
        storeSettingDetailForm.setBrandCountrySettingEditList(brandCountrySettingEditList);
        when(businessCountryStateSettingMasterOptionalMapper.updateByConditionSelective(anyObject(),
                anyObject())).thenReturn(1);
        int count = salesBrandCountrySettingEditService.updateDetail(storeSettingDetailForm);
        assertEquals(1, count);
    }

    /**
     * Test case of insert add detail.
     */
    @Test
    public void testInsertAddDetail() {
        StoreSettingDetail storeSettingDetail = new StoreSettingDetail();
        storeSettingDetail.setBrandCode("UQ");
        storeSettingDetail.setCodeL1("L1");
        List<StoreSettingDetail> brandCountrySettingEditList = new ArrayList<>();
        brandCountrySettingEditList.add(storeSettingDetail);
        storeSettingDetailForm.setBrandCountrySettingEditList(brandCountrySettingEditList);
        when(businessCountryStateSettingMasterOptionalMapper.insert(anyObject())).thenReturn(2);
        int count = salesBrandCountrySettingEditService.insertAddDetail(storeSettingDetailForm);
        assertEquals(2, count);
    }

    /**
     * Test case of insert add detail.
     */
    @Test
    public void testInsertAddDetailSalesIntegrityCheckType() {
        StoreSettingDetail storeSettingDetail = new StoreSettingDetail();
        storeSettingDetail.setBrandCode("UQ");
        storeSettingDetail.setCodeL1("L1");
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setBrandCode("UQ");
        storeSettingDetail2.setCodeL1("L2");
        storeSettingDetail2.setUpdateDatetime("2018-05-31 12:15:41.884255+00");
        List<StoreSettingDetail> brandCountrySettingEditList = new ArrayList<>();
        brandCountrySettingEditList.add(storeSettingDetail);
        brandCountrySettingEditList.add(storeSettingDetail2);
        storeSettingDetailForm.setCodeL1(ItemSetting.SALES_INTEGRITY_CHECK_TYPE.getValue());
        storeSettingDetailForm.setBrandCountrySettingEditList(brandCountrySettingEditList);
        when(businessCountryStateSettingMasterOptionalMapper.insert(anyObject())).thenReturn(1);
        int count = salesBrandCountrySettingEditService.insertAddDetail(storeSettingDetailForm);
        assertEquals(1, count);
    }

    /**
     * Test case of insert add detail.
     */
    @Test
    public void testInsertAddDetailSalesIntegrityCheck() {
        StoreSettingDetail storeSettingDetail = new StoreSettingDetail();
        storeSettingDetail.setBrandCode("UQ");
        storeSettingDetail.setCodeL1("L1");
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setBrandCode("UQ");
        storeSettingDetail2.setCodeL1("L2");
        storeSettingDetail2.setUpdateDatetime("2018-05-31 12:15:41.884255+00");
        List<StoreSettingDetail> brandCountrySettingEditList = new ArrayList<>();
        brandCountrySettingEditList.add(storeSettingDetail);
        brandCountrySettingEditList.add(storeSettingDetail2);
        storeSettingDetailForm.setCodeL1(ItemSetting.SALES_INTEGRITY_CHECK.getValue());
        storeSettingDetailForm.setBrandCountrySettingEditList(brandCountrySettingEditList);
        when(businessCountryStateSettingMasterOptionalMapper.insert(anyObject())).thenReturn(1);
        int count = salesBrandCountrySettingEditService.insertAddDetail(storeSettingDetailForm);
        assertEquals(1, count);
    }

    /**
     * Test case of insert add detail.
     */
    @Test
    public void testInsertAddDetailLinkageTiming() {
        StoreSettingDetail storeSettingDetail = new StoreSettingDetail();
        storeSettingDetail.setBrandCode("UQ");
        storeSettingDetail.setCodeL1("L1");
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setBrandCode("UQ");
        storeSettingDetail2.setCodeL1("L2");
        storeSettingDetail2.setUpdateDatetime("2018-05-31 12:15:41.884255+00");
        List<StoreSettingDetail> brandCountrySettingEditList = new ArrayList<>();
        brandCountrySettingEditList.add(storeSettingDetail);
        brandCountrySettingEditList.add(storeSettingDetail2);
        storeSettingDetailForm.setCodeL1(ItemSetting.LINKAGE_TIMING.getValue());
        storeSettingDetailForm.setBrandCountrySettingEditList(brandCountrySettingEditList);
        when(businessCountryStateSettingMasterOptionalMapper.insert(anyObject())).thenReturn(1);
        int count = salesBrandCountrySettingEditService.insertAddDetail(storeSettingDetailForm);
        assertEquals(1, count);
    }

    /**
     * Test case of insert add detail.
     */
    @Test
    public void testInsertAddDetailUpdateDecimal() {
        StoreSettingDetail storeSettingDetail = new StoreSettingDetail();
        storeSettingDetail.setBrandCode("UQ");
        storeSettingDetail.setCodeL1("L1");
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setBrandCode("UQ");
        storeSettingDetail2.setCodeL1("L2");
        storeSettingDetail2.setUpdateDatetime("2018-05-31 12:15:41.884255+00");
        List<StoreSettingDetail> brandCountrySettingEditList = new ArrayList<>();
        brandCountrySettingEditList.add(storeSettingDetail);
        brandCountrySettingEditList.add(storeSettingDetail2);
        storeSettingDetailForm.setCodeL1(ItemSetting.DECIMAL.getValue());
        storeSettingDetailForm.setBrandCountrySettingEditList(brandCountrySettingEditList);
        when(businessCountryStateSettingMasterOptionalMapper.insert(anyObject())).thenReturn(1);
        int count = salesBrandCountrySettingEditService.insertAddDetail(storeSettingDetailForm);
        assertEquals(1, count);
    }

    /**
     * Test case of get sales integrity check list.
     */
    @Test
    public void testGetSalesIntegrityCheckList() {
        CommonCodeMaster commonCodeMaster = new CommonCodeMaster();
        commonCodeMaster.setTypeId("123");
        List<CommonCodeMaster> list = new ArrayList<>();
        list.add(commonCodeMaster);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(list);
        List<SalesIntegrityCheck> getSalesIntegrityCheckList =
                salesBrandCountrySettingEditService.getSalesIntegrityCheckList("123");
        assertEquals(1, getSalesIntegrityCheckList.size());
    }
}
