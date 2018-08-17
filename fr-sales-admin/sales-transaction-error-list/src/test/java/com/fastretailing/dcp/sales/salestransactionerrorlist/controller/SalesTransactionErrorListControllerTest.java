/**
 * @(#)SalesTransactionErrorListControllerTest.java
 *
 *                                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
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
import com.fastretailing.dcp.sales.salestransactionerrorlist.form.SalesTransactionErrorListForm;
import com.fastretailing.dcp.sales.salestransactionerrorlist.service.SalesTransactionErrorListService;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SalesTransactionErrorListControllerTest {

    /** SpringMVC request mock. */
    private MockMvc mockMvc;

    private static final String REQUEST_URL = "/uq/ca/screen/sales-transaction-error-list";

    private static final String DELETE_REQUEST_URL =
            "/uq/ca/screen/sales-transaction-error-list/delete";

    private static final String UPLOAD_REQUEST_URL =
            "/uq/ca/screen/sales-transaction-error-list/upload";

    private static final String LINK_REQUEST_URL =
            "/uq/ca/screen/sales-transaction-error-list/numberlink";

    private static final String REQUEST_LIST_URL =
            "/uq/ca/screen/sales-transaction-error-list/list";

    private static final String DOWNLOAD_REQUEST_URL =
            "/uq/ca/screen/sales-transaction-error-list/download";

    @MockBean
    private SalesTransactionErrorListService salesTransactionErrorListService;

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

        CommonBaseForm commonBaseForm = new CommonBaseForm();
        commonBaseForm.setBrandCode("uq");
        commonBaseForm.setCountryCode("017");

        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();

        when(salesTransactionErrorListService.getInitializeInformation(anyObject()))
                .thenReturn(salesTransactionErrorListForm);

        ResultActions res = mockMvc.perform(get(REQUEST_URL, commonBaseForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-transaction-error-list")).andExpect(model().hasNoErrors());
    }

    /**
     * Test case of initialize.
     */
    @Test
    public void testDisplaySalesTransactionErrorListPage() throws Exception {

        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();
        salesTransactionErrorListForm.setSystemBrandCode("UQ");
        salesTransactionErrorListForm.setSystemCountryCode("JP");
        salesTransactionErrorListForm.setStoreCode("002");

        when(salesTransactionErrorListService.getSalesTransactionErrorList(anyObject()))
                .thenReturn(salesTransactionErrorListForm);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_LIST_URL, salesTransactionErrorListForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-transaction-error-list-ajaxData"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of initialize.
     */
    @Test
    public void testDisplaySalesTransactionErrorListPageError() throws Exception {

        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();
        salesTransactionErrorListForm.setSystemBrandCode("UQ");
        salesTransactionErrorListForm.setSystemCountryCode("as!");

        when(salesTransactionErrorListService.getSalesTransactionErrorList(anyObject()))
                .thenReturn(salesTransactionErrorListForm);

        MockMvcRequestBuilderUtils.postForm(REQUEST_LIST_URL, salesTransactionErrorListForm);

    }

    /**
     * Test case of initialize.
     */
    @Test
    public void testUpdateSalesTransactionErrorListPage() throws Exception {

        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();
        salesTransactionErrorListForm.setSystemBrandCode("UQ");
        salesTransactionErrorListForm.setSystemCountryCode("JP");
        salesTransactionErrorListForm.setStoreCode("002");

        doNothing().when(salesTransactionErrorListService).upload(anyObject());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(UPLOAD_REQUEST_URL, salesTransactionErrorListForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(content().string("AJAX_SUCCESS"));
    }

    /**
     * Test case of initialize.
     */
    @Test
    public void testDeleteSalesTransactionErrorListPage() throws Exception {

        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();
        salesTransactionErrorListForm.setSystemBrandCode("UQ");
        salesTransactionErrorListForm.setSystemCountryCode("JP");
        salesTransactionErrorListForm.setStoreCode("002");

        doNothing().when(salesTransactionErrorListService).upload(anyObject());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(DELETE_REQUEST_URL, salesTransactionErrorListForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(content().string("AJAX_SUCCESS"));
    }

    /**
     * Test case of initialize.
     */
    @Test
    public void testLinkSalesTransactionErrorListPage() throws Exception {

        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();
        salesTransactionErrorListForm.setSystemBrandCode("UQ");
        salesTransactionErrorListForm.setSystemCountryCode("JP");
        salesTransactionErrorListForm.setStoreCode("002");

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(LINK_REQUEST_URL, salesTransactionErrorListForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(content().string("AJAX_SUCCESS"));
    }

    /**
     * Test case of initialize.
     */
    @Test
    public void testDownLoadSalesTransactionErrorListPage() throws Exception {

        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();
        salesTransactionErrorListForm.setSystemBrandCode("UQ");
        salesTransactionErrorListForm.setSystemCountryCode("JP");
        salesTransactionErrorListForm.setStoreCode("002");

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(DOWNLOAD_REQUEST_URL, salesTransactionErrorListForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
