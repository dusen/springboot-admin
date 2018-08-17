package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.common.ItemSetting;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.BrandCountrySettingEdit;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.BrandCountrySettingEditForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSetting;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetail;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetailAddForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetailForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.service.SalesBrandCountrySettingEditService;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.config.DevelopmentConfiguration;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SalesBrandCountrySettingEditControllerTest {

    /** SpringMVC request mock. */
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /** Create salesBrandCountrySettingEditService Mock. */
    @MockBean
    private SalesBrandCountrySettingEditService salesBrandCountrySettingEditService;

    /** Request url. */
    private static final String REQUEST_URL = "/uq/jp/screen/brand-country-setting-edit";

    /** Request search url. */
    private static final String REQUEST_SEARCH_URL =
            "/uq/jp/screen/brand-country-setting-edit/search";

    /** Request list url. */
    private static final String REQUEST_LIST_URL = "/uq/jp/screen/brand-country-setting-edit/list";

    /** Request delete url. */
    private static final String REQUEST_DELETE_URL =
            "/uq/jp/screen/brand-country-setting-edit/delete";

    /** Request delete after url. */
    private static final String REQUEST_DELETE_AFTER_URL =
            "/uq/jp/screen/brand-country-setting-edit/after";

    /** Request detail url. */
    private static final String REQUEST_DETAIL_URL =
            "/uq/jp/screen/brand-country-setting-edit/detail";

    /** Request detail url. */
    private static final String REQUEST_DETAIL_ADD_URL =
            "/uq/jp/screen/brand-country-setting-edit/add";

    /** Request detail url. */
    private static final String REQUEST_DETAIL_BACK_URL =
            "/uq/jp/screen/brand-country-setting-edit/back";

    /** Request detail url. */
    private static final String REQUEST_DETAIL_CHECK_URL =
            "/uq/jp/screen/brand-country-setting-edit/doubleCheck";

    /** Request detail url. */
    private static final String REQUEST_DETAIL_REGISTER_URL =
            "/uq/jp/screen/brand-country-setting-edit/register";

    /** Configuration for development. */
    @Autowired
    private DevelopmentConfiguration developmentConfiguration;

    /** Set up. */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
    }

    @Test
    public void testDisplayInitPage() throws Exception {

        CommonBaseForm commonBaseForm = new CommonBaseForm();
        ResultActions res = mockMvc.perform(get(REQUEST_URL, commonBaseForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("brand-country-setting-edit")).andExpect(model().hasNoErrors());

    }

    @Test
    public void testDisplaySearchPage() throws Exception {

        BrandCountrySettingEdit brandCountrySettingEdit = new BrandCountrySettingEdit();
        brandCountrySettingEdit.setBrand("0001");
        brandCountrySettingEdit.setBrandName("UQ");
        brandCountrySettingEdit.setCountry("17");
        brandCountrySettingEdit.setCountryName("Canada");
        brandCountrySettingEdit.setViewStoreCode("1234");
        brandCountrySettingEdit.setStoreName("Canada UQ");
        List<BrandCountrySettingEdit> list = new ArrayList<>();
        list.add(brandCountrySettingEdit);
        when(salesBrandCountrySettingEditService.getBrandCountrySettingEditList(any()))
                .thenReturn(list);
        BrandCountrySettingEditForm brandCountrySettingEditForm = new BrandCountrySettingEditForm();
        brandCountrySettingEditForm.setBrandCountrySettingEditList(list);
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_SEARCH_URL,
                        brandCountrySettingEditForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("brand-country-setting-edit-ajaxData"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    public void testDisplayListPage() throws Exception {

        when(salesBrandCountrySettingEditService.addBrandCountrySettinglist(any())).thenReturn(1);
        when(salesBrandCountrySettingEditService.countStoreSettingList(any())).thenReturn(1);
        String brandName = "UQ";
        when(salesBrandCountrySettingEditService.getBrandAndCountry(any(), any()))
                .thenReturn(brandName);
        String countryName = "Canada";
        when(salesBrandCountrySettingEditService.getBrandAndCountry(any(), any()))
                .thenReturn(countryName);
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        when(salesBrandCountrySettingEditService.getStoreInformation(any()))
                .thenReturn(translationStoreCodeMasterOptional);
        StoreSettingForm storeSettingForm = new StoreSettingForm();
        storeSettingForm.setStoreCountryCode("17");
        StoreSetting storeSetting = new StoreSetting();
        storeSetting.setSettingItem("LinkageTiming");
        storeSetting.setStatus("Completed");
        List<StoreSetting> list = new ArrayList<>();
        list.add(storeSetting);
        when(salesBrandCountrySettingEditService.getStoreSettingList(any())).thenReturn(list);
        storeSettingForm.setStoreSettingList(list);
        storeSettingForm.setStoreCode("112326");
        storeSettingForm.setStoreBrandCode("0001");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_LIST_URL, storeSettingForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-list")).andExpect(model().hasNoErrors());

    }

    @Test
    public void testDeletePopUpPage1() throws Exception {
        StoreSetting storeSetting = new StoreSetting();
        storeSetting.setSettingItem("LinkageTiming");
        storeSetting.setStatus("Completed");
        List<StoreSetting> list = new ArrayList<>();
        list.add(storeSetting);
        StoreSettingForm storeSettingForm = new StoreSettingForm();
        storeSettingForm.setPageData("[{\"loginUserId\":null,\"loginStoreCode\":null,"
                + "\"countryCode\":null,\"defaultLocale\":null,"
                + "\"specifyLocale\":null,\"brandCode\":null,"
                + "\"menuList\":null,\"detailError\":null," + "\"temporaryData\":null,"
                + "\"urlBasePath\":null," + "\"settingItem\":\"DECIMAL\","
                + "\"status\":\"Completed\",\"statusColor\":\"0\"}]");
        storeSettingForm.setStoreSettingList(list);
        storeSettingForm.setStoreCode("999123456");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DELETE_URL, storeSettingForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-list")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testDeletePopUpPage2() throws Exception {
        StoreSetting storeSetting = new StoreSetting();
        storeSetting.setSettingItem("LinkageTiming");
        storeSetting.setStatus("Completed");
        List<StoreSetting> list = new ArrayList<>();
        list.add(storeSetting);
        StoreSettingForm storeSettingForm = new StoreSettingForm();
        storeSettingForm.setStoreSettingList(list);
        storeSettingForm.setStoreCode("112326");
        storeSettingForm.setCount(0);
        storeSettingForm.setPageData("[{\"loginUserId\":null,\"loginStoreCode\":null,"
                + "\"countryCode\":null,\"defaultLocale\":null,"
                + "\"specifyLocale\":null,\"brandCode\":null,"
                + "\"menuList\":null,\"detailError\":null," + "\"temporaryData\":null,"
                + "\"urlBasePath\":null," + "\"settingItem\":\"DECIMAL\","
                + "\"status\":\"Completed\",\"statusColor\":\"0\"}]");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DELETE_URL, storeSettingForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-list")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testDeletePopUpPage3() throws Exception {
        StoreSetting storeSetting = new StoreSetting();
        storeSetting.setSettingItem("LinkageTiming");
        storeSetting.setStatus("Completed");
        List<StoreSetting> list = new ArrayList<>();
        list.add(storeSetting);
        StoreSettingForm storeSettingForm = new StoreSettingForm();
        storeSettingForm.setStoreSettingList(list);
        storeSettingForm.setStoreCode("112326");
        storeSettingForm.setCount(1);
        storeSettingForm.setPageData("[{\"loginUserId\":null,\"loginStoreCode\":null,"
                + "\"countryCode\":null,\"defaultLocale\":null,"
                + "\"specifyLocale\":null,\"brandCode\":null,"
                + "\"menuList\":null,\"detailError\":null," + "\"temporaryData\":null,"
                + "\"urlBasePath\":null," + "\"settingItem\":\"DECIMAL\","
                + "\"status\":\"Completed\",\"statusColor\":\"0\"}]");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DELETE_URL, storeSettingForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-list")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testDeleteStoreInformation() throws Exception {
        when(salesBrandCountrySettingEditService.deleteStoreSettingList(any())).thenReturn(1);
        StoreSettingForm storeSettingForm = new StoreSettingForm();
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DELETE_AFTER_URL, storeSettingForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-list")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testDeleteStoreInformationException() throws Exception {
        when(salesBrandCountrySettingEditService.deleteStoreSettingList(any())).thenReturn(0);
        StoreSettingForm storeSettingForm = new StoreSettingForm();
        mockMvc.perform(post(REQUEST_DELETE_AFTER_URL, storeSettingForm));
    }

    @Test
    public void testDisplayStoreSettingDetail1() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("LINKAGETIMING");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("ORDER_CancelED");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        StoreSettingDetail storeSettingDetail3 = new StoreSettingDetail();
        storeSettingDetail3.setCodeL1("LINKAGETIMING");
        storeSettingDetail3.setCodeL2("SALESLINKAGEUNIT");
        storeSettingDetail3.setCodeL3("LINKAGEUNIT");
        storeSettingDetail3.setCodeValue("0");
        detailList.add(storeSettingDetail3);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1(ItemSetting.LINKAGE_TIMING.getValue());
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-linkage-timing"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayStoreSettingDetail2() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("SALESREPORT");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-report"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayStoreSettingDetail3() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("PAYOFFDATA");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-pay-off")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayStoreSettingDetail4() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("LINKAGETIMING");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        StoreSettingDetail storeSettingDetail3 = new StoreSettingDetail();
        storeSettingDetail3.setCodeL1("LINKAGETIMING");
        storeSettingDetail3.setCodeL2("ORDERSTATUS");
        storeSettingDetail3.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail3.setCodeValue("0");
        detailList.add(storeSettingDetail3);
        StoreSettingDetail storeSettingDetail4 = new StoreSettingDetail();
        storeSettingDetail4.setCodeL1("LINKAGETIMING");
        storeSettingDetail4.setCodeL2("ORDERSTATUS");
        storeSettingDetail4.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail4.setCodeValue("0");
        detailList.add(storeSettingDetail4);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("BUSINESSDATECHECK");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-business-date"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayStoreSettingDetailSalesIntegrityCheck() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("SALESINTEGRITYCHECK");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED1");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("SALESINTEGRITYCHECK");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("SHIPMENT_ConfirmED2");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        StoreSettingDetail storeSettingDetail3 = new StoreSettingDetail();
        storeSettingDetail3.setCodeL1("SALESINTEGRITYCHECK");
        storeSettingDetail3.setCodeL2("ORDERSTATUS");
        storeSettingDetail3.setCodeL3("SHIPMENT_ConfirmED3");
        storeSettingDetail3.setCodeValue("0");
        detailList.add(storeSettingDetail3);
        StoreSettingDetail storeSettingDetail4 = new StoreSettingDetail();
        storeSettingDetail4.setCodeL1("SALESINTEGRITYCHECK");
        storeSettingDetail4.setCodeL2("ORDERSTATUS");
        storeSettingDetail4.setCodeL3("SHIPMENT_ConfirmED4");
        storeSettingDetail4.setCodeValue("0");
        detailList.add(storeSettingDetail4);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("SALESINTEGRITYCHECK");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayStoreSettingDetailSalesIntegrityCheckType() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("SALESINTEGRITYCHECKTYPE");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED1");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("SALESINTEGRITYCHECKTYPE");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("SHIPMENT_ConfirmED2");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        StoreSettingDetail storeSettingDetail3 = new StoreSettingDetail();
        storeSettingDetail3.setCodeL1("SALESINTEGRITYCHECKTYPE");
        storeSettingDetail3.setCodeL2("ORDERSTATUS");
        storeSettingDetail3.setCodeL3("SHIPMENT_ConfirmED3");
        storeSettingDetail3.setCodeValue("0");
        detailList.add(storeSettingDetail3);
        StoreSettingDetail storeSettingDetail4 = new StoreSettingDetail();
        storeSettingDetail4.setCodeL1("SALESINTEGRITYCHECKTYPE");
        storeSettingDetail4.setCodeL2("ORDERSTATUS");
        storeSettingDetail4.setCodeL3("SHIPMENT_ConfirmED4");
        storeSettingDetail4.setCodeValue("0");
        detailList.add(storeSettingDetail4);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("SALESINTEGRITYCHECKTYPE");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check-type"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayStoreSettingDetailBusinessDateCheck() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("BUSINESSDATECHECK");
        storeSettingDetail1.setCodeL2("CLOSEDBUSINESSDATE");
        storeSettingDetail1.setCodeL3("CHECK");
        storeSettingDetail1.setCodeValue("4");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("BUSINESSDATECHECK");
        storeSettingDetail2.setCodeL2("BEFORESTOREOPENING");
        storeSettingDetail2.setCodeL3("THRESHOLD");
        storeSettingDetail2.setCodeValue("2");
        detailList.add(storeSettingDetail2);
        StoreSettingDetail storeSettingDetail3 = new StoreSettingDetail();
        storeSettingDetail3.setCodeL1("BUSINESSDATECHECK");
        storeSettingDetail3.setCodeL2("DATEEXCEEDING");
        storeSettingDetail3.setCodeL3("THRESHOLD");
        storeSettingDetail3.setCodeValue("1");
        detailList.add(storeSettingDetail3);
        StoreSettingDetail storeSettingDetail4 = new StoreSettingDetail();
        storeSettingDetail4.setCodeL1("BUSINESSDATECHECK");
        storeSettingDetail4.setCodeL2("AFTERSTORECLOSING");
        storeSettingDetail4.setCodeL3("THRESHOLD");
        storeSettingDetail4.setCodeValue("3");
        detailList.add(storeSettingDetail4);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("BUSINESSDATECHECK");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        for (int i = 0; i < storeSettingDetailForm.getBrandCountrySettingEditList().size(); i++) {
            assertEquals(String.valueOf(i + 1),
                    storeSettingDetailForm.getBrandCountrySettingEditList().get(i).getCodeValue());
        }
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-business-date"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayStoreSettingDetail5() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("TAXTYPE");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-tax-type"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayStoreSettingDetail6() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("SALESINTEGRITYCHECK");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayStoreSettingDetail7() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("SALESINTEGRITYCHECKTYPE");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check-type"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayStoreSettingDetail8() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("DECIMAL");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-decimal")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayStoreSettingDetail9() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        when(salesBrandCountrySettingEditService.getDetailList(any())).thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("TEST");
        mockMvc.perform(post(REQUEST_DETAIL_URL, storeSettingDetailForm));
    }

    @Test
    public void testDisplayStoreSettingDetail10() throws Exception {
        when(salesBrandCountrySettingEditService.countBusinessSettingDetailList(any()))
                .thenReturn(0);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        when(salesBrandCountrySettingEditService.getDetailDefaultList(any()))
                .thenReturn(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("DECIMAL");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-decimal")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayAddStoreSettingDetail1() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCodeL1("LINKAGETIMING");
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_ADD_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-linkage-timing-add-popup"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayAddStoreSettingDetail2() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCodeL1("SALESINTEGRITYCHECK");
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_ADD_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check-add-popup"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDisplayAddStoreSettingDetail3() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCodeL1("SALESINTEGRITYCHECKTYPE");
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_ADD_URL, storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check-type-add-popup"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testAddStoreSettingDetail1() throws Exception {
        StoreSettingDetailAddForm storeSettingDetailAddForm = new StoreSettingDetailAddForm();
        storeSettingDetailAddForm.setCodeL1("LINKAGETIMING");
        storeSettingDetailAddForm.setCodeL2("ORDERSTATUS");
        storeSettingDetailAddForm.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetailAddForm.setCodeValue("0");
        storeSettingDetailAddForm.setTemporaryData("{\"temporaryData\":{\"loginUserId\":null,"
                + "\"loginStoreCode\":\"112326\"," + "\"countryCode\":\"\","
                + "\"defaultLocale\":\"en_US\"," + "\"specifyLocale\":\"\"," + "\"brandCode\":\"\","
                + "\"menuList\":null," + "\"detailError\":null,\"temporaryData\":null,"
                + "\"urlBasePath\":\"/sales/v1/uq/jp/screen\"," + "\"storeBrandCode\":\"0001\","
                + "\"brandName\":\"0001=UQ\",\"storeCountryCode\":\"17\","
                + "\"countryName\":\"Canada\","
                + "\"businessCode\":\"0017\",\"storeCode\":\"112326\",\"storeName\":\"UQCD_EC\","
                + "\"viewStoreCode\":\"5000\",\"stateCode\":\"071\",\"codeL1\":\"LINKAGETIMING\","
                + "\"codeL3\":null,\"checkValue\":\"0\",\"codeValue\":null,"
                + "\"pageData\":\"[{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,"
                + "\\\"countryCode\\\":null,\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,"
                + "\\\"brandCode\\\":null,\\\"menuList\\\":null,\\\"detailError\\\":null,"
                + "\\\"temporaryData\\\":null,\\\"urlBasePath\\\":null,"
                + "\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"SHIPMENT_ConfirmED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"ORDER_CancelED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"SALESLINKAGEUNIT\\\",\\\"codeL3\\\":\\\"LINKAGEUNIT\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0}]\","
                + "\"linkageTimingVariableType\":\"0\",\"linkageTimingPageData\":\""
                + "[{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"SHIPMENT_ConfirmED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"ORDER_CancelED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"SALESLINKAGEUNIT\\\",\\\"codeL3\\\":\\\"LINKAGEUNIT\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0}]\","
                + "\"count\":3,\"brandCountrySettingEditList\":[{\"loginUserId\":null,"
                + "\"loginStoreCode\":null,\"countryCode\":null,\"defaultLocale\":null,"
                + "\"specifyLocale\":null,\"brandCode\":null,\"menuList\":null,\"detailError\":null"
                + ",\"temporaryData\":null,\"urlBasePath\":null,\"codeL1\":\"LINKAGETIMING\","
                + "\"codeL2\":\"ORDERSTATUS\",\"codeL3\":\"SHIPMENT_ConfirmED\","
                + "\"codeValue\":\"0\",\"variableType\":\"0\","
                + "\"updateDatetime\":\"2018-05-31 21:15:41\"," + "\"addColor\":0}]}}");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_BACK_URL,
                        storeSettingDetailAddForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-linkage-timing"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testAddStoreSettingDetail2() throws Exception {
        StoreSettingDetailAddForm storeSettingDetailAddForm = new StoreSettingDetailAddForm();
        storeSettingDetailAddForm.setCodeL1("SALESINTEGRITYCHECK");
        storeSettingDetailAddForm.setCodeL2("ORDERSTATUS");
        storeSettingDetailAddForm.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetailAddForm.setCodeValue("0");
        storeSettingDetailAddForm.setTemporaryData("{\"temporaryData\":{\"loginUserId\":null,"
                + "\"loginStoreCode\":\"112326\"," + "\"countryCode\":\"\","
                + "\"defaultLocale\":\"en_US\"," + "\"specifyLocale\":\"\"," + "\"brandCode\":\"\","
                + "\"menuList\":null," + "\"detailError\":null,\"temporaryData\":null,"
                + "\"urlBasePath\":\"/sales/v1/uq/jp/screen\"," + "\"storeBrandCode\":\"0001\","
                + "\"brandName\":\"0001=UQ\",\"storeCountryCode\":\"17\","
                + "\"countryName\":\"Canada\","
                + "\"businessCode\":\"0017\",\"storeCode\":\"112326\",\"storeName\":\"UQCD_EC\","
                + "\"viewStoreCode\":\"5000\",\"stateCode\":\"071\",\"codeL1\":\"LINKAGETIMING\","
                + "\"codeL3\":null,\"checkValue\":\"0\",\"codeValue\":null,"
                + "\"pageData\":\"[{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,"
                + "\\\"countryCode\\\":null,\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,"
                + "\\\"brandCode\\\":null,\\\"menuList\\\":null,\\\"detailError\\\":null,"
                + "\\\"temporaryData\\\":null,\\\"urlBasePath\\\":null,"
                + "\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"SHIPMENT_ConfirmED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"ORDER_CancelED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"SALESLINKAGEUNIT\\\",\\\"codeL3\\\":\\\"LINKAGEUNIT\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0}]\","
                + "\"linkageTimingVariableType\":\"0\",\"linkageTimingPageData\":\""
                + "[{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"SHIPMENT_ConfirmED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"ORDER_CancelED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"SALESLINKAGEUNIT\\\",\\\"codeL3\\\":\\\"LINKAGEUNIT\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0}]\","
                + "\"count\":3,\"brandCountrySettingEditList\":[{\"loginUserId\":null,"
                + "\"loginStoreCode\":null,\"countryCode\":null,\"defaultLocale\":null,"
                + "\"specifyLocale\":null,\"brandCode\":null,\"menuList\":null,\"detailError\":null"
                + ",\"temporaryData\":null,\"urlBasePath\":null,\"codeL1\":\"LINKAGETIMING\","
                + "\"codeL2\":\"ORDERSTATUS\",\"codeL3\":\"SHIPMENT_ConfirmED\","
                + "\"codeValue\":\"0\",\"variableType\":\"0\","
                + "\"updateDatetime\":\"2018-05-31 21:15:41\"," + "\"addColor\":0}]}}");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_BACK_URL,
                        storeSettingDetailAddForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testAddStoreSettingDetail3() throws Exception {
        StoreSettingDetailAddForm storeSettingDetailAddForm = new StoreSettingDetailAddForm();
        storeSettingDetailAddForm.setCodeL1("SALESINTEGRITYCHECKTYPE");
        storeSettingDetailAddForm.setCodeL2("ORDERSTATUS");
        storeSettingDetailAddForm.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetailAddForm.setCodeValue("0");
        storeSettingDetailAddForm.setTemporaryData("{\"temporaryData\":{\"loginUserId\":null,"
                + "\"loginStoreCode\":\"112326\"," + "\"countryCode\":\"\","
                + "\"defaultLocale\":\"en_US\"," + "\"specifyLocale\":\"\"," + "\"brandCode\":\"\","
                + "\"menuList\":null," + "\"detailError\":null,\"temporaryData\":null,"
                + "\"urlBasePath\":\"/sales/v1/uq/jp/screen\"," + "\"storeBrandCode\":\"0001\","
                + "\"brandName\":\"0001=UQ\",\"storeCountryCode\":\"17\","
                + "\"countryName\":\"Canada\","
                + "\"businessCode\":\"0017\",\"storeCode\":\"112326\",\"storeName\":\"UQCD_EC\","
                + "\"viewStoreCode\":\"5000\",\"stateCode\":\"071\",\"codeL1\":\"LINKAGETIMING\","
                + "\"codeL3\":null,\"checkValue\":\"0\",\"codeValue\":null,"
                + "\"pageData\":\"[{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,"
                + "\\\"countryCode\\\":null,\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,"
                + "\\\"brandCode\\\":null,\\\"menuList\\\":null,\\\"detailError\\\":null,"
                + "\\\"temporaryData\\\":null,\\\"urlBasePath\\\":null,"
                + "\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"SHIPMENT_ConfirmED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"ORDER_CancelED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"SALESLINKAGEUNIT\\\",\\\"codeL3\\\":\\\"LINKAGEUNIT\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0}]\","
                + "\"linkageTimingVariableType\":\"0\",\"linkageTimingPageData\":\""
                + "[{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"SHIPMENT_ConfirmED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"ORDERSTATUS\\\",\\\"codeL3\\\":\\\"ORDER_CancelED\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0},"
                + "{\\\"loginUserId\\\":null,\\\"loginStoreCode\\\":null,\\\"countryCode\\\":null,"
                + "\\\"defaultLocale\\\":null,\\\"specifyLocale\\\":null,\\\"brandCode\\\":null,"
                + "\\\"menuList\\\":null,\\\"detailError\\\":null,\\\"temporaryData\\\":null,"
                + "\\\"urlBasePath\\\":null,\\\"codeL1\\\":\\\"LINKAGETIMING\\\","
                + "\\\"codeL2\\\":\\\"SALESLINKAGEUNIT\\\",\\\"codeL3\\\":\\\"LINKAGEUNIT\\\","
                + "\\\"codeValue\\\":\\\"0\\\",\\\"variableType\\\":\\\"0\\\","
                + "\\\"updateDatetime\\\":\\\"2018-05-31 21:15:41\\\",\\\"addColor\\\":0}]\","
                + "\"count\":3,\"brandCountrySettingEditList\":[{\"loginUserId\":null,"
                + "\"loginStoreCode\":null,\"countryCode\":null,\"defaultLocale\":null,"
                + "\"specifyLocale\":null,\"brandCode\":null,\"menuList\":null,\"detailError\":null"
                + ",\"temporaryData\":null,\"urlBasePath\":null,\"codeL1\":\"LINKAGETIMING\","
                + "\"codeL2\":\"ORDERSTATUS\",\"codeL3\":\"SHIPMENT_ConfirmED\","
                + "\"codeValue\":\"0\",\"variableType\":\"0\","
                + "\"updateDatetime\":\"2018-05-31 21:15:41\"," + "\"addColor\":0}]}}");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_BACK_URL,
                        storeSettingDetailAddForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check-type"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDoubleCheck1() throws Exception {
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("LINKAGETIMING");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_CHECK_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-linkage-timing"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDoubleCheck2() throws Exception {
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("SALESREPORT");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_CHECK_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-report"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDoubleCheck3() throws Exception {
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("PAYOFFDATA");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_CHECK_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-pay-off")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testDoubleCheck4() throws Exception {
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("LINKAGETIMING");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        StoreSettingDetail storeSettingDetail3 = new StoreSettingDetail();
        storeSettingDetail3.setCodeL1("LINKAGETIMING");
        storeSettingDetail3.setCodeL2("ORDERSTATUS");
        storeSettingDetail3.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail3.setCodeValue("0");
        detailList.add(storeSettingDetail3);
        StoreSettingDetail storeSettingDetail4 = new StoreSettingDetail();
        storeSettingDetail4.setCodeL1("LINKAGETIMING");
        storeSettingDetail4.setCodeL2("ORDERSTATUS");
        storeSettingDetail4.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail4.setCodeValue("0");
        detailList.add(storeSettingDetail4);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("BUSINESSDATECHECK");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_CHECK_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-business-date"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDoubleCheck5() throws Exception {
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("TAXTYPE");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_CHECK_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-tax-type"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDoubleCheck6() throws Exception {
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("SALESINTEGRITYCHECK");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_CHECK_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDoubleCheck7() throws Exception {
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("SALESINTEGRITYCHECKTYPE");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_CHECK_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check-type"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testDoubleCheck8() throws Exception {
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("DECIMAL");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_CHECK_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-decimal")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testDoubleCheck9() throws Exception {
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("LINKAGETIMING");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("DECIMAL");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_CHECK_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-decimal")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetail1() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(0);
        when(salesBrandCountrySettingEditService.insertDefaultDetail(any())).thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("LINKAGETIMING");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("ORDER_CancelED");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        StoreSettingDetail storeSettingDetail3 = new StoreSettingDetail();
        storeSettingDetail3.setCodeL1("LINKAGETIMING");
        storeSettingDetail3.setCodeL2("SALESLINKAGEUNIT");
        storeSettingDetail3.setCodeL3("LINKAGEUNIT");
        storeSettingDetail3.setCodeValue("0");
        detailList.add(storeSettingDetail3);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1(ItemSetting.LINKAGE_TIMING.getValue());
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-linkage-timing"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetailLinkageTiming() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(0);
        when(salesBrandCountrySettingEditService.insertDefaultDetail(any())).thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("LINKAGETIMING");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("ORDER_CancelED");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1(ItemSetting.LINKAGE_TIMING.getValue());
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        assertThat(
                storeSettingDetailForm.getBrandCountrySettingEditList().stream().filter(detail -> {
                    return "LINKAGEUNIT".equals(detail.getCodeL3());
                }).findAny().isPresent(), is(false));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-linkage-timing"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetail2() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(0);
        when(salesBrandCountrySettingEditService.insertDefaultDetail(any())).thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("SALESREPORT");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-report"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetail3() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(0);
        when(salesBrandCountrySettingEditService.insertDefaultDetail(any())).thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("PAYOFFDATA");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-pay-off")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetail4() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(0);
        when(salesBrandCountrySettingEditService.insertDefaultDetail(any())).thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("LINKAGETIMING");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        StoreSettingDetail storeSettingDetail3 = new StoreSettingDetail();
        storeSettingDetail3.setCodeL1("LINKAGETIMING");
        storeSettingDetail3.setCodeL2("ORDERSTATUS");
        storeSettingDetail3.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail3.setCodeValue("0");
        detailList.add(storeSettingDetail3);
        StoreSettingDetail storeSettingDetail4 = new StoreSettingDetail();
        storeSettingDetail4.setCodeL1("LINKAGETIMING");
        storeSettingDetail4.setCodeL2("ORDERSTATUS");
        storeSettingDetail4.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail4.setCodeValue("0");
        detailList.add(storeSettingDetail4);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("BUSINESSDATECHECK");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-business-date"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetail5() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(0);
        when(salesBrandCountrySettingEditService.insertDefaultDetail(any())).thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("TAXTYPE");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-tax-type"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetail6() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(0);
        when(salesBrandCountrySettingEditService.insertDefaultDetail(any())).thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("SALESINTEGRITYCHECK");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetail7() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(0);
        when(salesBrandCountrySettingEditService.insertDefaultDetail(any())).thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("SALESINTEGRITYCHECKTYPE");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-sales-integrity-check-type"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetail8() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(0);
        when(salesBrandCountrySettingEditService.insertDefaultDetail(any())).thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCodeL1("DECIMAL");
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-decimal")).andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetail9() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(1);
        storeSettingDetailForm.setCodeL1("LINKAGETIMING");
        storeSettingDetailForm
                .setLinkageTimingPageData("[{\"loginUserId\":null,\"loginStoreCode\":null,"
                        + "\"countryCode\":null,\"defaultLocale\":null,"
                        + "\"specifyLocale\":null,\"brandCode\":null,\"menuList\":null,"
                        + "\"detailError\":null,\"temporaryData\":null,\"urlBasePath\":null,"
                        + "\"codeL1\":\"LINKAGETIMING\",\"codeL2\":\"ORDERSTATUS\","
                        + "\"codeL3\":\"SHIPMENT_ConfirmED\",\"codeValue\":\"0\","
                        + "\"variableType\":\"0\",\"updateDatetime\":\"2018-05-31 21:15:41\","
                        + "\"addColor\":0},{\"loginUserId\":null,\"loginStoreCode\":null,"
                        + "\"countryCode\":null,\"defaultLocale\":null,\"specifyLocale\":null,"
                        + "\"brandCode\":null,\"menuList\":null,\"detailError\":null,"
                        + "\"temporaryData\":null,\"urlBasePath\":null,"
                        + "\"codeL1\":\"LINKAGETIMING\","
                        + "\"codeL2\":\"ORDERSTATUS\",\"codeL3\":\"ORDER_CancelED\","
                        + "\"codeValue\":\"0\",\"variableType\":\"0\","
                        + "\"updateDatetime\":\"2018-05-31 21:15:41\","
                        + "\"addColor\":0},{\"loginUserId\":null,"
                        + "\"loginStoreCode\":null,\"countryCode\":null,"
                        + "\"defaultLocale\":null,\"specifyLocale\":null,"
                        + "\"brandCode\":null,\"menuList\":null,"
                        + "\"detailError\":null,\"temporaryData\":null,\"urlBasePath\":null,"
                        + "\"codeL1\":\"LINKAGETIMING\",\"codeL2\":\"SALESLINKAGEUNIT\","
                        + "\"codeL3\":\"LINKAGEUNIT\",\"codeValue\":\"0\","
                        + "\"variableType\":\"0\",\"updateDatetime\":\"2018-05-31 21:15:41\","
                        + "\"addColor\":0}]");
        when(salesBrandCountrySettingEditService.updateDetail(any())).thenReturn(0);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("LINKAGETIMING");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        StoreSettingDetail storeSettingDetail3 = new StoreSettingDetail();
        storeSettingDetail3.setCodeL1("LINKAGETIMING");
        storeSettingDetail3.setCodeL2("ORDERSTATUS");
        storeSettingDetail3.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail3.setCodeValue("0");
        detailList.add(storeSettingDetail3);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-linkage-timing"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetailInsertTwoDetail() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(0);
        storeSettingDetailForm.setCodeL1("LINKAGETIMING");
        storeSettingDetailForm
                .setLinkageTimingPageData("[{\"loginUserId\":null,\"loginStoreCode\":null,"
                        + "\"countryCode\":null,\"defaultLocale\":null,"
                        + "\"specifyLocale\":null,\"brandCode\":null,\"menuList\":null,"
                        + "\"detailError\":null,\"temporaryData\":null,\"urlBasePath\":null,"
                        + "\"codeL1\":\"LINKAGETIMING\",\"codeL2\":\"ORDERSTATUS\","
                        + "\"codeL3\":\"SHIPMENT_ConfirmED\",\"codeValue\":\"0\","
                        + "\"variableType\":\"0\",\"updateDatetime\":\"2018-05-31 21:15:41\","
                        + "\"addColor\":0},{\"loginUserId\":null,\"loginStoreCode\":null,"
                        + "\"countryCode\":null,\"defaultLocale\":null,\"specifyLocale\":null,"
                        + "\"brandCode\":null,\"menuList\":null,\"detailError\":null,"
                        + "\"temporaryData\":null,\"urlBasePath\":null,"
                        + "\"codeL1\":\"LINKAGETIMING\","
                        + "\"codeL2\":\"ORDERSTATUS\",\"codeL3\":\"ORDER_CancelED\","
                        + "\"codeValue\":\"0\",\"variableType\":\"0\","
                        + "\"updateDatetime\":\"2018-05-31 21:15:41\"," + "\"addColor\":0}]");
        when(salesBrandCountrySettingEditService.updateDetail(any())).thenReturn(0);
        when(salesBrandCountrySettingEditService.insertDefaultDetail(any())).thenReturn(0);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("LINKAGETIMING");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        storeSettingDetailForm.setCheckValue("0");
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-linkage-timing"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testRegisterStoreSettingDetail10() throws Exception {
        StoreSettingDetailForm storeSettingDetailForm = new StoreSettingDetailForm();
        storeSettingDetailForm.setCount(1);
        storeSettingDetailForm.setCodeL1("PAYOFFDATA");
        when(salesBrandCountrySettingEditService.updateDetail(any())).thenReturn(1);
        StoreSettingDetail storeSettingDetail1 = new StoreSettingDetail();
        storeSettingDetail1.setCodeL1("LINKAGETIMING");
        storeSettingDetail1.setCodeL2("ORDERSTATUS");
        storeSettingDetail1.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail1.setCodeValue("0");
        List<StoreSettingDetail> detailList = new ArrayList<>();
        detailList.add(storeSettingDetail1);
        StoreSettingDetail storeSettingDetail2 = new StoreSettingDetail();
        storeSettingDetail2.setCodeL1("LINKAGETIMING");
        storeSettingDetail2.setCodeL2("ORDERSTATUS");
        storeSettingDetail2.setCodeL3("SHIPMENT_ConfirmED");
        storeSettingDetail2.setCodeValue("0");
        detailList.add(storeSettingDetail2);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        when(salesBrandCountrySettingEditService.insertAddDetail(any())).thenReturn(1);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("SHIPMENT_ConfirmED");
        orderStatusList.add("ORDER_CancelED");
        when(salesBrandCountrySettingEditService.getOrderStatusList(any()))
                .thenReturn(orderStatusList);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_DETAIL_REGISTER_URL,
                        storeSettingDetailForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-setting-detail-pay-off")).andExpect(model().hasNoErrors());
    }
}
