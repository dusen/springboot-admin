/**
 * @(#)DeclareIrregularSettlementsControllerTest.java
 *
 *                                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
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
import com.fastretailing.dcp.sales.declareirregularsettlements.form.DeclareIrregularSettlementsForm;
import com.fastretailing.dcp.sales.declareirregularsettlements.service.DeclareIrregularSettlementsService;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeclareIrregularSettlementsControllerTest {

    /** SpringMVC request mock. */
    private MockMvc mockMvc;

    /** Request url. */
    private static final String REQUEST_URL = "/v1/jp/screen/declare-irregular-settlements";

    /** Request url. */
    private static final String REQUEST_STORE_URL =
            "/uq/ca/screen/sc001/declare-irregular-settlements";

    /** Request url. */
    private static final String REQUEST_LIST_URL =
            "/uq/ca/screen/declare-irregular-settlements/list";

    /** Request url. */
    private static final String REQUEST_LIST_STORE_URL =
            "/uq/ca/screen/sc001/declare-irregular-settlements/list";

    /** Request url. */
    private static final String REQUEST_CALCULATION_URL =
            "/uq/ca/screen/declare-irregular-settlements/calculation";

    /** Request url. */
    private static final String REQUEST_CALCULATION_STORE_URL =
            "/uq/ca/screen/sc001/declare-irregular-settlements/calculation";

    /** Request url. */
    private static final String REQUEST_CONFIRM_URL =
            "/uq/ca/screen/declare-irregular-settlements/confirm";

    /** Request url. */
    private static final String REQUEST_CONFIRM_STORE_URL =
            "/uq/ca/screen/sc001/declare-irregular-settlements/confirm";

    /** Reprint daily sales report service. */
    @MockBean
    private DeclareIrregularSettlementsService declareIrregularSettlementsService;

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

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();

        when(declareIrregularSettlementsService.initialize(anyObject()))
                .thenReturn(declareIrregularSettlementsForm);

        ResultActions res = mockMvc.perform(get(REQUEST_URL, commonBaseForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("declare-irregular-settlements"))
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

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();

        when(declareIrregularSettlementsService.initialize(anyObject()))
                .thenReturn(declareIrregularSettlementsForm);

        ResultActions res = mockMvc.perform(get(REQUEST_STORE_URL, commonBaseForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("declare-irregular-settlements"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of get declare irregular settlements list.
     */
    @Test
    public void testSearchDeclareIrregularSettlementsPage() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        declareIrregularSettlementsForm.setSystemBrandCode("UQ");
        declareIrregularSettlementsForm.setSystemCountryCode("JP");
        declareIrregularSettlementsForm.setDisplayStoreCode("0002");
        declareIrregularSettlementsForm.setSettlementDate("20180621");
        declareIrregularSettlementsForm.setCashRegisterNo("1");
        declareIrregularSettlementsForm.setChangeFundDecimal("1");
        declareIrregularSettlementsForm.setChangeFundInteger("1");
        declareIrregularSettlementsForm.setClosingBalanceDecimal("1");
        declareIrregularSettlementsForm.setClosingBalanceInteger("1");

        when(declareIrregularSettlementsService.search(anyObject()))
                .thenReturn(declareIrregularSettlementsForm);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_LIST_URL, declareIrregularSettlementsForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("declare-irregular-settlements"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of get declare irregular settlements list.
     */
    @Test
    public void testSearchDeclareIrregularSettlementsPageWithStore() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        declareIrregularSettlementsForm.setSystemBrandCode("UQ");
        declareIrregularSettlementsForm.setSystemCountryCode("JP");
        declareIrregularSettlementsForm.setDisplayStoreCode("0002");
        declareIrregularSettlementsForm.setSettlementDate("20180621");
        declareIrregularSettlementsForm.setCashRegisterNo("1");
        declareIrregularSettlementsForm.setChangeFundDecimal("1");
        declareIrregularSettlementsForm.setChangeFundInteger("1");
        declareIrregularSettlementsForm.setClosingBalanceDecimal("1");
        declareIrregularSettlementsForm.setClosingBalanceInteger("1");

        when(declareIrregularSettlementsService.search(anyObject()))
                .thenReturn(declareIrregularSettlementsForm);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_LIST_STORE_URL, declareIrregularSettlementsForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("declare-irregular-settlements"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of search declare irregular settlements page exception.
     */
    @Test
    public void testSearchDeclareIrregularSettlementsPageException() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        declareIrregularSettlementsForm.setSystemBrandCode("UQ");
        declareIrregularSettlementsForm.setSystemCountryCode("JP");
        declareIrregularSettlementsForm.setDisplayStoreCode("0002");
        declareIrregularSettlementsForm.setSettlementDate("20180621");

        DetailError detailError = new DetailError();
        detailError.setErrorCode("E_SLS_66000128");
        declareIrregularSettlementsForm.setDetailError(detailError);
        when(declareIrregularSettlementsService.search(anyObject()))
                .thenReturn(declareIrregularSettlementsForm);

        mockMvc.perform(post(REQUEST_LIST_URL, declareIrregularSettlementsForm));
    }

    /**
     * Test case of search declare irregular settlements page exception.
     */
    @Test
    public void testSearchDeclareIrregularSettlementsPageException2() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        declareIrregularSettlementsForm.setSystemBrandCode("UQ");
        declareIrregularSettlementsForm.setSystemCountryCode("JP");
        declareIrregularSettlementsForm.setDisplayStoreCode("0002");
        declareIrregularSettlementsForm.setSettlementDate("20180621");

        when(declareIrregularSettlementsService.search(anyObject()))
                .thenReturn(declareIrregularSettlementsForm);
        mockMvc.perform(post(REQUEST_LIST_URL, declareIrregularSettlementsForm));
    }

    /**
     * Test case of search declare irregular settlements page exception.
     */
    @Test
    public void testSearchDeclareIrregularSettlementsPageWithStoreException() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        declareIrregularSettlementsForm.setSystemBrandCode("UQ");
        declareIrregularSettlementsForm.setSystemCountryCode("JP");
        declareIrregularSettlementsForm.setDisplayStoreCode("0002");
        declareIrregularSettlementsForm.setSettlementDate("20180621");

        DetailError detailError = new DetailError();
        detailError.setErrorCode("E_SLS_66000128");
        declareIrregularSettlementsForm.setDetailError(detailError);
        when(declareIrregularSettlementsService.search(anyObject()))
                .thenReturn(declareIrregularSettlementsForm);

        mockMvc.perform(post(REQUEST_LIST_STORE_URL, declareIrregularSettlementsForm));
    }

    /**
     * Test case of search declare irregular settlements page exception.
     */
    @Test
    public void testSearchDeclareIrregularSettlementsPageWithStoreException2() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        declareIrregularSettlementsForm.setSystemBrandCode("UQ");
        declareIrregularSettlementsForm.setSystemCountryCode("JP");
        declareIrregularSettlementsForm.setDisplayStoreCode("0002");
        declareIrregularSettlementsForm.setSettlementDate("20180621");

        when(declareIrregularSettlementsService.search(anyObject()))
                .thenReturn(declareIrregularSettlementsForm);

        mockMvc.perform(post(REQUEST_LIST_STORE_URL, declareIrregularSettlementsForm));
    }

    /**
     * Test case of calculation declare irregular settlements page exception.
     */
    @Test
    public void testCalculationDeclareIrregularSettlementsPageException() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();

        DetailError detailError = new DetailError();
        detailError.setErrorCode("E_SLS_66000128");
        declareIrregularSettlementsForm.setDetailError(detailError);
        doNothing().when(declareIrregularSettlementsService).calculation(anyObject());

        mockMvc.perform(post(REQUEST_CALCULATION_STORE_URL, declareIrregularSettlementsForm));
    }

    /**
     * Test case of calculation declare irregular settlements page exception.
     */
    @Test
    public void testDisplaySalesTransactionHistoryListPageBySortError() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();

        DetailError detailError = new DetailError();
        detailError.setErrorCode("E_SLS_66000128");
        declareIrregularSettlementsForm.setDetailError(detailError);
        doNothing().when(declareIrregularSettlementsService).calculation(anyObject());

        mockMvc.perform(post(REQUEST_CALCULATION_URL, declareIrregularSettlementsForm));
    }

    /**
     * Test case of calculation declare irregular settlements page exception.
     */

    @Test
    public void testCalculationSalesDeclareIrregularSettementsPagError() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        declareIrregularSettlementsForm.setSystemBrandCode("UQ");
        declareIrregularSettlementsForm.setSystemCountryCode("JP");
        declareIrregularSettlementsForm.setDisplayStoreCode("0002");
        declareIrregularSettlementsForm.setSettlementDate("20180621");
        declareIrregularSettlementsForm.setCashRegisterNo("1");
        declareIrregularSettlementsForm.setChangeFundDecimal("1");
        declareIrregularSettlementsForm.setChangeFundInteger("1");
        declareIrregularSettlementsForm.setClosingBalanceDecimal("1");
        declareIrregularSettlementsForm.setClosingBalanceInteger("1");

        doNothing().when(declareIrregularSettlementsService).calculation(anyObject());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_CALCULATION_URL, declareIrregularSettlementsForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("declare-irregular-settlements"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of calculation declare irregular settlements page.
     */
    @Test
    public void testCalculationSalesDeclareIrregularSettementsWithStoreBySort() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        declareIrregularSettlementsForm.setSystemBrandCode("UQ");
        declareIrregularSettlementsForm.setSystemCountryCode("JP");
        declareIrregularSettlementsForm.setDisplayStoreCode("0002");
        declareIrregularSettlementsForm.setSettlementDate("20180621");
        declareIrregularSettlementsForm.setCashRegisterNo("1");
        declareIrregularSettlementsForm.setChangeFundDecimal("1");
        declareIrregularSettlementsForm.setChangeFundInteger("1");
        declareIrregularSettlementsForm.setClosingBalanceDecimal("1");
        declareIrregularSettlementsForm.setClosingBalanceInteger("1");

        doNothing().when(declareIrregularSettlementsService).calculation(anyObject());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_CALCULATION_STORE_URL, declareIrregularSettlementsForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("declare-irregular-settlements"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of confirm declare irregular settlements page exception.
     */
    @Test
    public void testConfirmSalesDeclareIrregularSettementsPag() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        declareIrregularSettlementsForm.setSystemBrandCode("UQ");
        declareIrregularSettlementsForm.setSystemCountryCode("JP");
        declareIrregularSettlementsForm.setDisplayStoreCode("0002");
        declareIrregularSettlementsForm.setSettlementDate("20180621");
        declareIrregularSettlementsForm.setCashRegisterNo("1");
        declareIrregularSettlementsForm.setChangeFundDecimal("1");
        declareIrregularSettlementsForm.setChangeFundInteger("1");
        declareIrregularSettlementsForm.setClosingBalanceDecimal("1");
        declareIrregularSettlementsForm.setClosingBalanceInteger("1");

        doNothing().when(declareIrregularSettlementsService).confirm(anyObject());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_CONFIRM_URL, declareIrregularSettlementsForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("declare-irregular-settlements"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of confirm declare irregular settlements page.
     */
    @Test
    public void testConfirmSalesDeclareIrregularSettementsWithStoreCode() throws Exception {

        DeclareIrregularSettlementsForm declareIrregularSettlementsForm =
                new DeclareIrregularSettlementsForm();
        declareIrregularSettlementsForm.setSystemBrandCode("UQ");
        declareIrregularSettlementsForm.setSystemCountryCode("JP");
        declareIrregularSettlementsForm.setDisplayStoreCode("0002");
        declareIrregularSettlementsForm.setSettlementDate("20180621");
        declareIrregularSettlementsForm.setCashRegisterNo("1");
        declareIrregularSettlementsForm.setChangeFundDecimal("1");
        declareIrregularSettlementsForm.setChangeFundInteger("1");
        declareIrregularSettlementsForm.setClosingBalanceDecimal("1");
        declareIrregularSettlementsForm.setClosingBalanceInteger("1");

        doNothing().when(declareIrregularSettlementsService).confirm(anyObject());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_CONFIRM_STORE_URL, declareIrregularSettlementsForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("declare-irregular-settlements"))
                .andExpect(model().hasNoErrors());
    }
}
