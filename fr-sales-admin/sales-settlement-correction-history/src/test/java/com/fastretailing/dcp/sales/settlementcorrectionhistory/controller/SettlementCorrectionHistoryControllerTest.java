package com.fastretailing.dcp.sales.settlementcorrectionhistory.controller;

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
import com.fastretailing.dcp.sales.settlementcorrectionhistory.form.SettlementCorrectionHistoryListForm;
import com.fastretailing.dcp.sales.settlementcorrectionhistory.service.SettlementCorrectionHistoryListService;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SettlementCorrectionHistoryControllerTest {

    /** SpringMVC request mock. */
    private MockMvc mockMvc;

    private static final String REQUEST_URL = "/uq/ca/screen/sales-settlement-correction-history";

    private static final String REQUEST_LIST_URL =
            "/uq/ca/screen/sales-settlement-correction-history/list";

    private static final String REQUEST_SORT_URL =
            "/uq/ca/screen/sales-settlement-correction-history/sort";

    @MockBean
    private SettlementCorrectionHistoryListService settlementCorrectionHistoryListService;

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
        commonBaseForm.setBrandCode("0001");
        commonBaseForm.setCountryCode("17");

        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                new SettlementCorrectionHistoryListForm();

        when(settlementCorrectionHistoryListService.getInitializeInformation(anyObject()))
                .thenReturn(settlementCorrectionHistoryListForm);

        ResultActions res = mockMvc.perform(get(REQUEST_URL, commonBaseForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-settlement-correction-history"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test case of display settlement correction history page.
     */
    @Test
    public void testDisplaySettlementCorrectionHistoryPage() throws Exception {

        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                new SettlementCorrectionHistoryListForm();
        settlementCorrectionHistoryListForm.setSystemBrandCode("0001");
        settlementCorrectionHistoryListForm.setSystemCountryCode("17");
        settlementCorrectionHistoryListForm.setViewStoreCode("9720");

        when(settlementCorrectionHistoryListService.getSettlementCorrectionHistoryList(anyObject()))
                .thenReturn(settlementCorrectionHistoryListForm);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilderUtils
                .postForm(REQUEST_LIST_URL, settlementCorrectionHistoryListForm);

        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-settlement-correction-history-ajaxData"))
                .andExpect(model().hasNoErrors());

    }

    /**
     * Test case of display settlement correction history page validation error.
     */
    @Test
    public void testDisplaySettlementCorrectionHistoryageValidationError() throws Exception {

        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                new SettlementCorrectionHistoryListForm();

        when(settlementCorrectionHistoryListService.getSettlementCorrectionHistoryList(anyObject()))
                .thenReturn(settlementCorrectionHistoryListForm);

        mockMvc.perform(post(REQUEST_LIST_URL, settlementCorrectionHistoryListForm));
    }

    /**
     * Test case of display settlement correction history page exception.
     */
    @Test
    public void testDisplaySettlementCorrectionHistoryPageException() throws Exception {

        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                new SettlementCorrectionHistoryListForm();
        settlementCorrectionHistoryListForm.setSystemBrandCode("9999");
        settlementCorrectionHistoryListForm.setSystemCountryCode("17");
        settlementCorrectionHistoryListForm.setViewStoreCode("9720");

        DetailError detailError = new DetailError();
        detailError.setErrorCode("E_SLS_66000128");
        settlementCorrectionHistoryListForm.setDetailError(detailError);

        when(settlementCorrectionHistoryListService.getSettlementCorrectionHistoryList(anyObject()))
                .thenReturn(settlementCorrectionHistoryListForm);

        mockMvc.perform(post(REQUEST_LIST_URL, settlementCorrectionHistoryListForm));
    }

    /**
     * Test case of display sorted settlement correction history page.
     */
    @Test
    public void testDisplaySettlementCorrectionHistoryPageBySort() throws Exception {

        SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm =
                new SettlementCorrectionHistoryListForm();
        settlementCorrectionHistoryListForm.setSystemBrandCode("0001");
        settlementCorrectionHistoryListForm.setSystemCountryCode("17");
        settlementCorrectionHistoryListForm.setViewStoreCode("9720");

        when(settlementCorrectionHistoryListService
                .getSortedSettlementCorrectionHistoryList(anyObject()))
                        .thenReturn(settlementCorrectionHistoryListForm);

        ResultActions res =
                mockMvc.perform(post(REQUEST_SORT_URL, settlementCorrectionHistoryListForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("sales-settlement-correction-history-ajaxData"))
                .andExpect(model().hasNoErrors());
    }

}
