/**
 * @(#)StoreTransactionInquiryControllerTest.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionHeaderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.StoreTransactionInquiryDetail;
import com.fastretailing.dcp.sales.storetransactioninquiry.constant.DropDownItem;
import com.fastretailing.dcp.sales.storetransactioninquiry.form.StoreTransactionInquiryForm;
import com.fastretailing.dcp.sales.storetransactioninquiry.service.StoreTransactionInquiryService;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StoreTransactionInquiryControllerTest {

    /** SpringMVC request mock. */
    private MockMvc mockMvc;

    /** Request url. */
    private static final String REQUEST_URL =
            "/uq/ca/screen/112326/store-transaction-inquiry/initialize";
    /** Request url list. */
    private static final String REQUEST_URL_LIST =
            "/uq/ca/screen/112326/store-transaction-inquiry/list";
    /** Request url previous. */
    private static final String REQUEST_URL_LIST_PREVIOUS =
            "/uq/ca/screen/112326/store-transaction-inquiry/previous-list";
    /** Request url next. */
    private static final String REQUEST_URL_LIST_NEXT =
            "/uq/ca/screen/112326/store-transaction-inquiry/next-list";
    /** Request url change brand. */
    private static final String REQUEST_URL_CHANGE_BRAND =
            "/uq/ca/screen/112326/store-transaction-inquiry/view-non-merchandise-item-list";
    /** Request url change store. */
    private static final String REQUEST_URL_CHANGE_STORE =
            "/uq/ca/screen/112326/store-transaction-inquiry/view-payment-tender-group-list";
    /** Request url change store for tender id. */
    private static final String REQUEST_URL_CHANGE_STORE_FOR_TENDER_ID =
            "/uq/ca/screen/112326/store-transaction-inquiry/view-payment-tender-id-list";

    @MockBean
    private StoreTransactionInquiryService storeTransactionInquiryService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * Before.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Test case of initialize.
     */
    @Test
    public void testInitialize() throws Exception {

        StoreTransactionInquiryForm storeTransactionInquiryForm = new StoreTransactionInquiryForm();
        storeTransactionInquiryForm.setSystemBrandCode("UQ");
        storeTransactionInquiryForm.setSystemCountryCode("JP");
        storeTransactionInquiryForm.setStoreCode("112326");
        CommonBaseForm commonBaseForm = new CommonBaseForm();
        commonBaseForm.setBrandCode("uq");
        commonBaseForm.setCountryCode("017");
        commonBaseForm.setLoginStoreCode("112326");
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setImsLinkageDate("20180621");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeader =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeader.setImsLinkageDate("20180621");
        headerDetailList.add(salesReportTransactionHeader);
        storeTransactionInquiryForm.setHeaderDetail(storeTransactionInquiryDetail);
        when(storeTransactionInquiryService.displayInitPage(anyObject()))
                .thenReturn(storeTransactionInquiryForm);
        ResultActions res = mockMvc.perform(get(REQUEST_URL, commonBaseForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-transaction-inquiry")).andExpect(model().hasNoErrors());
    }

    /**
     * Test case of get detail list.
     */
    @Test
    public void testDisplayDetailListPage() throws Exception {

        StoreTransactionInquiryForm storeTransactionInquiryForm = new StoreTransactionInquiryForm();
        storeTransactionInquiryForm.setSystemBrandCode("UQ");
        storeTransactionInquiryForm.setSystemCountryCode("JP");
        storeTransactionInquiryForm.setStoreCode("112326");
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setImsLinkageDate("20180621");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeader =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeader.setImsLinkageDate("20180621");
        headerDetailList.add(salesReportTransactionHeader);
        storeTransactionInquiryForm.setHeaderDetail(storeTransactionInquiryDetail);
        when(storeTransactionInquiryService.getStoreTransactionInquiryList(anyObject()))
                .thenReturn(storeTransactionInquiryForm);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_URL_LIST, storeTransactionInquiryForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-transaction-inquiry-detail"))
                .andExpect(model().hasNoErrors());

    }

    /**
     * Test case of display detail list page with error.
     */
    @Test
    public void testDisplayDetailListPageWithError() throws Exception {

        StoreTransactionInquiryForm storeTransactionInquiryForm = new StoreTransactionInquiryForm();
        storeTransactionInquiryForm.setSystemBrandCode("");
        storeTransactionInquiryForm.setSystemCountryCode("");
        storeTransactionInquiryForm.setStoreCode("");
        DetailError detailError = new DetailError();
        detailError.setErrorCode("test");
        storeTransactionInquiryForm.setDetailError(detailError);
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setImsLinkageDate("20180621");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeader =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeader.setImsLinkageDate("20180621");
        headerDetailList.add(salesReportTransactionHeader);
        storeTransactionInquiryForm.setHeaderDetail(storeTransactionInquiryDetail);
        when(storeTransactionInquiryService.getStoreTransactionInquiryList(anyObject()))
                .thenReturn(storeTransactionInquiryForm);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_URL_LIST, storeTransactionInquiryForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-transaction-inquiry")).andExpect(model().hasErrors());

    }

    /**
     * Test case of display detail list page with detail error.
     */
    @Test
    public void testDisplayDetailListPageWithDetailError() throws Exception {

        StoreTransactionInquiryForm storeTransactionInquiryForm = new StoreTransactionInquiryForm();
        storeTransactionInquiryForm.setSystemBrandCode("uq");
        storeTransactionInquiryForm.setSystemCountryCode("jp");
        storeTransactionInquiryForm.setStoreCode("112326");
        DetailError detailError = new DetailError();
        detailError.setErrorCode("test");
        storeTransactionInquiryForm.setDetailError(detailError);
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setImsLinkageDate("20180621");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeader =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeader.setImsLinkageDate("20180621");
        headerDetailList.add(salesReportTransactionHeader);
        storeTransactionInquiryForm.setHeaderDetail(storeTransactionInquiryDetail);
        when(storeTransactionInquiryService.getStoreTransactionInquiryList(anyObject()))
                .thenReturn(storeTransactionInquiryForm);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_URL_LIST, storeTransactionInquiryForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-transaction-inquiry")).andExpect(model().hasNoErrors());

    }

    /**
     * Test case of changing brand.
     */
    @Test
    public void testChangeBrand() throws Exception {

        StoreTransactionInquiryForm storeTransactionInquiryForm = new StoreTransactionInquiryForm();
        storeTransactionInquiryForm.setSystemBrandCode("UQ");
        storeTransactionInquiryForm.setSystemCountryCode("JP");
        storeTransactionInquiryForm.setStoreCode("112326");
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setImsLinkageDate("20180621");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeader =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeader.setImsLinkageDate("20180621");
        headerDetailList.add(salesReportTransactionHeader);
        storeTransactionInquiryForm.setHeaderDetail(storeTransactionInquiryDetail);
        List<DropDownItem> dropDownItemList = new ArrayList<>();
        DropDownItem dropDown = new DropDownItem();
        dropDown.setKey("00");
        dropDown.setValue("Brand");
        dropDownItemList.add(dropDown);
        when(storeTransactionInquiryService.changeBrand(anyObject(), anyObject(), anyObject()))
                .thenReturn(dropDownItemList);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_URL_CHANGE_BRAND, storeTransactionInquiryForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("non-merchandise-item-list")).andExpect(model().hasNoErrors());

    }

    /**
     * Test case of changing country.
     */
    @Test
    public void testChangeCountry() throws Exception {

        StoreTransactionInquiryForm storeTransactionInquiryForm = new StoreTransactionInquiryForm();
        storeTransactionInquiryForm.setSystemBrandCode("UQ");
        storeTransactionInquiryForm.setSystemCountryCode("JP");
        storeTransactionInquiryForm.setStoreCode("112326");
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setImsLinkageDate("20180621");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeader =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeader.setImsLinkageDate("20180621");
        headerDetailList.add(salesReportTransactionHeader);
        storeTransactionInquiryForm.setHeaderDetail(storeTransactionInquiryDetail);
        List<DropDownItem> dropDownItemList = new ArrayList<>();
        DropDownItem dropDown = new DropDownItem();
        dropDown.setKey("00");
        dropDown.setValue("Country");
        dropDownItemList.add(dropDown);
        when(storeTransactionInquiryService.changeBrand(anyObject(), anyObject(), anyObject()))
                .thenReturn(dropDownItemList);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_URL_CHANGE_BRAND, storeTransactionInquiryForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("non-merchandise-item-list")).andExpect(model().hasNoErrors());

    }

    /**
     * Test case of changing store code for tender id.
     */
    @Test
    public void testChangeStoreCodeForTenderId() throws Exception {

        StoreTransactionInquiryForm storeTransactionInquiryForm = new StoreTransactionInquiryForm();
        storeTransactionInquiryForm.setSystemBrandCode("UQ");
        storeTransactionInquiryForm.setSystemCountryCode("JP");
        storeTransactionInquiryForm.setStoreCode("112326");
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setImsLinkageDate("20180621");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeader =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeader.setImsLinkageDate("20180621");
        headerDetailList.add(salesReportTransactionHeader);
        storeTransactionInquiryForm.setHeaderDetail(storeTransactionInquiryDetail);
        List<DropDownItem> dropDownItemList = new ArrayList<>();
        DropDownItem dropDown = new DropDownItem();
        dropDown.setKey("00");
        dropDown.setValue("Country");
        dropDownItemList.add(dropDown);
        when(storeTransactionInquiryService.changeStoreForTenderId(anyObject(), anyObject(),
                anyObject())).thenReturn(dropDownItemList);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_URL_CHANGE_STORE_FOR_TENDER_ID, storeTransactionInquiryForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("payment-tender-id")).andExpect(model().hasNoErrors());
    }

    /**
     * Test case of changing store code for tender group.
     */
    @Test
    public void testChangeStoreCodeForTenderGroup() throws Exception {

        StoreTransactionInquiryForm storeTransactionInquiryForm = new StoreTransactionInquiryForm();
        storeTransactionInquiryForm.setSystemBrandCode("UQ");
        storeTransactionInquiryForm.setSystemCountryCode("JP");
        storeTransactionInquiryForm.setStoreCode("112326");
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setImsLinkageDate("20180621");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeader =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeader.setImsLinkageDate("20180621");
        headerDetailList.add(salesReportTransactionHeader);
        storeTransactionInquiryForm.setHeaderDetail(storeTransactionInquiryDetail);
        List<DropDownItem> dropDownItemList = new ArrayList<>();
        DropDownItem dropDown = new DropDownItem();
        dropDown.setKey("00");
        dropDown.setValue("Country");
        dropDownItemList.add(dropDown);
        when(storeTransactionInquiryService.changeStoreForTenderGroup(anyObject(), anyObject(),
                anyObject())).thenReturn(dropDownItemList);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_URL_CHANGE_STORE, storeTransactionInquiryForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("payment-tender-group")).andExpect(model().hasNoErrors());
    }

    /**
     * Test case of get previous list.
     */
    @Test
    public void testPreviousList() throws Exception {

        StoreTransactionInquiryForm storeTransactionInquiryForm = new StoreTransactionInquiryForm();
        storeTransactionInquiryForm.setSystemBrandCode("UQ");
        storeTransactionInquiryForm.setSystemCountryCode("JP");
        storeTransactionInquiryForm.setStoreCode("112326");
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setImsLinkageDate("20180621");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeader =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeader.setImsLinkageDate("20180621");
        headerDetailList.add(salesReportTransactionHeader);
        storeTransactionInquiryForm.setHeaderDetail(storeTransactionInquiryDetail);
        when(storeTransactionInquiryService.getPreviousItemDetail(anyObject()))
                .thenReturn(storeTransactionInquiryForm);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_URL_LIST_PREVIOUS, storeTransactionInquiryForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-transaction-inquiry")).andExpect(model().hasNoErrors());

    }

    /**
     * Test case of get next list.
     */
    @Test
    public void testNextList() throws Exception {

        StoreTransactionInquiryForm storeTransactionInquiryForm = new StoreTransactionInquiryForm();
        storeTransactionInquiryForm.setSystemBrandCode("UQ");
        storeTransactionInquiryForm.setSystemCountryCode("JP");
        storeTransactionInquiryForm.setStoreCode("112326");
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setImsLinkageDate("20180621");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeader =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeader.setImsLinkageDate("20180621");
        headerDetailList.add(salesReportTransactionHeader);
        storeTransactionInquiryForm.setHeaderDetail(storeTransactionInquiryDetail);
        when(storeTransactionInquiryService.getNextItemDetail(anyObject()))
                .thenReturn(storeTransactionInquiryForm);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_URL_LIST_NEXT, storeTransactionInquiryForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("store-transaction-inquiry")).andExpect(model().hasNoErrors());

    }

}
