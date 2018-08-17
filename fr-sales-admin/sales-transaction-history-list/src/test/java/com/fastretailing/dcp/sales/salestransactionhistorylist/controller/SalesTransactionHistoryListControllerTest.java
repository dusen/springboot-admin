package com.fastretailing.dcp.sales.salestransactionhistorylist.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.fastretailing.dcp.sales.salestransactionhistorylist.form.SalesTransactionHistoryListForm;
import com.fastretailing.dcp.sales.salestransactionhistorylist.service.SalesTransactionHistoryListService;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SalesTransactionHistoryListControllerTest {

    /** SpringMVC request mock. */
    private MockMvc mockMvc;

    private static final String REQUEST_URL = "/uq/ca/screen/sales-transaction-history-list";

    private static final String REQUEST_LIST_URL =
            "/uq/ca/screen/sales-transaction-history-list/list";

    private static final String REQUEST_SORT_URL =
            "/uq/ca/screen/sales-transaction-history-list/sort";

    @MockBean
    private SalesTransactionHistoryListService salesTransactionHistoryListService;

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

        SalesTransactionHistoryListForm salesTransactionHistoryListForm =
                new SalesTransactionHistoryListForm();

        when(salesTransactionHistoryListService.getInitializeInformation(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        ResultActions res = mockMvc.perform(get(REQUEST_URL, commonBaseForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-transaction-history-list"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of initialize.
     */
    @Test
    public void testDisplaySalesTransactionHistoryListPage() throws Exception {

        SalesTransactionHistoryListForm salesTransactionHistoryListForm =
                new SalesTransactionHistoryListForm();
        salesTransactionHistoryListForm.setSystemBrandCode("UQ");
        salesTransactionHistoryListForm.setSystemCountryCode("JP");
        salesTransactionHistoryListForm.setStoreCode("002");

        when(salesTransactionHistoryListService.getSalesTransactionHistoryList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_LIST_URL, salesTransactionHistoryListForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-transaction-history-list-ajaxData"))
                .andExpect(model().hasNoErrors());

    }

    /**
     * Test case of display sales transaction history list page exception.
     */
    @Test
    public void testDisplaySalesTransactionHistoryListPageValidationError() throws Exception {

        SalesTransactionHistoryListForm salesTransactionHistoryListForm =
                new SalesTransactionHistoryListForm();

        when(salesTransactionHistoryListService.getSortedSalesTransactionHistoryList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        mockMvc.perform(post(REQUEST_LIST_URL, salesTransactionHistoryListForm));
    }

    /**
     * Test case of display sales transaction history list page exception.
     */
    @Test
    public void testDisplaySalesTransactionHistoryListPageException() throws Exception {

        SalesTransactionHistoryListForm salesTransactionHistoryListForm =
                new SalesTransactionHistoryListForm();
        salesTransactionHistoryListForm.setSystemBrandCode("UQ");
        salesTransactionHistoryListForm.setSystemCountryCode("JP");
        salesTransactionHistoryListForm.setStoreCode("002");

        DetailError detailError = new DetailError();
        detailError.setErrorCode("E_SLS_66000128");
        salesTransactionHistoryListForm.setDetailError(detailError);
        when(salesTransactionHistoryListService.getSortedSalesTransactionHistoryList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        mockMvc.perform(post(REQUEST_LIST_URL, salesTransactionHistoryListForm));
    }

    /**
     * Test case of initialize.
     */
    @Test
    public void testDisplaySalesTransactionHistoryListPageBySort() throws Exception {

        SalesTransactionHistoryListForm salesTransactionHistoryListForm =
                new SalesTransactionHistoryListForm();
        salesTransactionHistoryListForm.setSystemBrandCode("UQ");
        salesTransactionHistoryListForm.setSystemCountryCode("JP");
        salesTransactionHistoryListForm.setStoreCode("002");

        when(salesTransactionHistoryListService.getSortedSalesTransactionHistoryList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        ResultActions res =
                mockMvc.perform(post(REQUEST_SORT_URL, salesTransactionHistoryListForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-transaction-history-list-ajaxData"))
                .andExpect(model().hasNoErrors());
    }

}
