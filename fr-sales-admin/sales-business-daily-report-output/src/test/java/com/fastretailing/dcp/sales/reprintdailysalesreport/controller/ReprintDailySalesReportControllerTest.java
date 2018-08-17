/**
 * @(#)ReprintDailySalesReportControllerTest.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.reprintdailysalesreport.controller;

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
import com.fastretailing.dcp.sales.reprintdailysalesreport.form.ReprintDailySalesReportForm;
import com.fastretailing.dcp.sales.reprintdailysalesreport.service.ReprintDailySalesReportService;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReprintDailySalesReportControllerTest {

    /** SpringMVC request mock. */
    private MockMvc mockMvc;

    /** Request url. */
    private static final String REQUEST_URL = "/uq/ca/screen/sales-reprint-daily-sales-report";

    /** Request url. */
    private static final String REQUEST_STORE_URL =
            "/uq/ca/screen/123456/sales-reprint-daily-sales-report";

    /** Request url. */
    private static final String REQUEST_LIST_URL =
            "/uq/ca/screen/sales-reprint-daily-sales-report/preview";

    /** Request url. */
    private static final String REQUEST_LIST_STORE_URL =
            "/uq/ca/screen/123456/sales-reprint-daily-sales-report/preview";

    /** Request url. */
    private static final String REQUEST_SORT_URL =
            "/uq/ca/screen/sales-reprint-daily-sales-report/print";

    /** Request url. */
    private static final String REQUEST_SORT_STORE_URL =
            "/uq/ca/screen/123456/sales-reprint-daily-sales-report/print";

    /** Reprint daily sales report service. */
    @MockBean
    private ReprintDailySalesReportService reprintDailySalesReportService;

    /** Web application context. */
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

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();

        when(reprintDailySalesReportService.getInitializeInformation(anyObject()))
                .thenReturn(reprintDailySalesReportForm);

        ResultActions res = mockMvc.perform(get(REQUEST_URL, commonBaseForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-reprint-daily-sales-report"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of initialize.
     */
    @Test
    public void testInitializeWithStoreCode() throws Exception {

        CommonBaseForm commonBaseForm = new CommonBaseForm();
        commonBaseForm.setBrandCode("uq");
        commonBaseForm.setCountryCode("017");

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();

        when(reprintDailySalesReportService.getInitializeInformation(anyObject()))
                .thenReturn(reprintDailySalesReportForm);

        ResultActions res = mockMvc.perform(get(REQUEST_STORE_URL, commonBaseForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-reprint-daily-sales-report"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of get reprint daily sales report list.
     */
    @Test
    public void testDisplayPrintDailyReportPage() throws Exception {

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setSystemBrandCode("UQ");
        reprintDailySalesReportForm.setSystemCountryCode("JP");
        reprintDailySalesReportForm.setDisplayStoreCode("0002");
        reprintDailySalesReportForm.setBusinessDate("20180621");

        when(reprintDailySalesReportService.getReprintDailySalesReportList(anyObject()))
                .thenReturn(reprintDailySalesReportForm);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_LIST_URL, reprintDailySalesReportForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-reprint-daily-sales-report"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of get reprint daily sales report list..
     */
    @Test
    public void testDisplayPrintDailyReportPageWithStore() throws Exception {

        ReprintDailySalesReportForm reprintDailySalesReportForm = new ReprintDailySalesReportForm();
        reprintDailySalesReportForm.setSystemBrandCode("UQ");
        reprintDailySalesReportForm.setSystemCountryCode("JP");
        reprintDailySalesReportForm.setDisplayStoreCode("0002");
        reprintDailySalesReportForm.setBusinessDate("20180621");

        when(reprintDailySalesReportService.getReprintDailySalesReportList(anyObject()))
                .thenReturn(reprintDailySalesReportForm);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_LIST_STORE_URL, reprintDailySalesReportForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-reprint-daily-sales-report"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of display sales reprint daily sales report page exception.
     */
    @Test
    public void testDisplayRepintDailyReportPageException() throws Exception {

        ReprintDailySalesReportForm salesTransactionHistoryListForm =
                new ReprintDailySalesReportForm();
        salesTransactionHistoryListForm.setSystemBrandCode("UQ");
        salesTransactionHistoryListForm.setSystemCountryCode("JP");
        salesTransactionHistoryListForm.setDisplayStoreCode("0002");

        DetailError detailError = new DetailError();
        detailError.setErrorCode("E_SLS_66000128");
        salesTransactionHistoryListForm.setDetailError(detailError);
        when(reprintDailySalesReportService.getReprintDailySalesReportList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        mockMvc.perform(post(REQUEST_LIST_URL, salesTransactionHistoryListForm));

    }

    /**
     * Test case of display sales reprint daily sales report page exception.
     */
    @Test
    public void testDisplayRepintDailyReportPageException2() throws Exception {

        ReprintDailySalesReportForm salesTransactionHistoryListForm =
                new ReprintDailySalesReportForm();
        salesTransactionHistoryListForm.setSystemBrandCode("UQ");
        salesTransactionHistoryListForm.setSystemCountryCode("JP");
        salesTransactionHistoryListForm.setDisplayStoreCode("0002");

        when(reprintDailySalesReportService.getReprintDailySalesReportList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        mockMvc.perform(post(REQUEST_LIST_URL, salesTransactionHistoryListForm));

    }

    /**
     * Test case of display sales reprint daily sales report page exception.
     */
    @Test
    public void testDisplayRepintDailyReportPageWithStoreException() throws Exception {

        ReprintDailySalesReportForm salesTransactionHistoryListForm =
                new ReprintDailySalesReportForm();
        salesTransactionHistoryListForm.setSystemBrandCode("UQ");
        salesTransactionHistoryListForm.setSystemCountryCode("JP");
        salesTransactionHistoryListForm.setDisplayStoreCode("0002");

        DetailError detailError = new DetailError();
        detailError.setErrorCode("E_SLS_66000128");
        salesTransactionHistoryListForm.setDetailError(detailError);
        when(reprintDailySalesReportService.getReprintDailySalesReportList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        mockMvc.perform(post(REQUEST_LIST_STORE_URL, salesTransactionHistoryListForm));
    }

    /**
     * Test case of display sales reprint daily sales report page exception.
     */
    @Test
    public void testDisplayRepintDailyReportPageWithStoreException2() throws Exception {

        ReprintDailySalesReportForm salesTransactionHistoryListForm =
                new ReprintDailySalesReportForm();
        salesTransactionHistoryListForm.setSystemBrandCode("UQ");
        salesTransactionHistoryListForm.setSystemCountryCode("JP");
        salesTransactionHistoryListForm.setDisplayStoreCode("0002");

        when(reprintDailySalesReportService.getReprintDailySalesReportList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        mockMvc.perform(post(REQUEST_LIST_STORE_URL, salesTransactionHistoryListForm));
    }

    /**
     * Test case of sales reprint daily sales report page exception.
     */
    @Test
    public void testDisplaySalesTransactionHistoryListPageBySort() throws Exception {

        ReprintDailySalesReportForm salesTransactionHistoryListForm =
                new ReprintDailySalesReportForm();

        DetailError detailError = new DetailError();
        detailError.setErrorCode("E_SLS_66000128");
        salesTransactionHistoryListForm.setDetailError(detailError);
        when(reprintDailySalesReportService.getReprintDailySalesReportList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        mockMvc.perform(post(REQUEST_SORT_STORE_URL, salesTransactionHistoryListForm));
    }

    /**
     * Test case of sales reprint daily sales report page exception.
     */
    @Test
    public void testDisplaySalesTransactionHistoryListPageBySortError() throws Exception {

        ReprintDailySalesReportForm salesTransactionHistoryListForm =
                new ReprintDailySalesReportForm();

        DetailError detailError = new DetailError();
        detailError.setErrorCode("E_SLS_66000128");
        salesTransactionHistoryListForm.setDetailError(detailError);
        when(reprintDailySalesReportService.getReprintDailySalesReportList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        mockMvc.perform(post(REQUEST_SORT_URL, salesTransactionHistoryListForm));
    }

    /**
     * Test case of sales reprint daily sales report page exception.
     */
    @Test
    public void testDisplaySalesTransactionHistoryListPagError() throws Exception {

        ReprintDailySalesReportForm salesTransactionHistoryListForm =
                new ReprintDailySalesReportForm();
        salesTransactionHistoryListForm.setSystemBrandCode("UQ");
        salesTransactionHistoryListForm.setSystemBrandName("12");
        salesTransactionHistoryListForm.setSystemCountryName("123");
        salesTransactionHistoryListForm.setSystemCountryCode("JP");
        salesTransactionHistoryListForm.setDisplayStoreCode("0002");
        salesTransactionHistoryListForm.setBusinessDate("20180621");

        when(reprintDailySalesReportService.printReprintDailySalesReportList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_SORT_URL, salesTransactionHistoryListForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-reprint-daily-sales-report"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of print sales reprint daily sales report page.
     */
    @Test
    public void testDisplaySalesTransactionHistoryListPageWithStoreBySort() throws Exception {

        ReprintDailySalesReportForm salesTransactionHistoryListForm =
                new ReprintDailySalesReportForm();
        salesTransactionHistoryListForm.setSystemBrandCode("UQ");
        salesTransactionHistoryListForm.setSystemBrandName("12");
        salesTransactionHistoryListForm.setSystemCountryName("123");
        salesTransactionHistoryListForm.setSystemCountryCode("JP");
        salesTransactionHistoryListForm.setDisplayStoreCode("0002");
        salesTransactionHistoryListForm.setBusinessDate("20180621");

        when(reprintDailySalesReportService.printReprintDailySalesReportList(anyObject()))
                .thenReturn(salesTransactionHistoryListForm);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_SORT_STORE_URL, salesTransactionHistoryListForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-reprint-daily-sales-report"))
                .andExpect(model().hasNoErrors());
    }
}
