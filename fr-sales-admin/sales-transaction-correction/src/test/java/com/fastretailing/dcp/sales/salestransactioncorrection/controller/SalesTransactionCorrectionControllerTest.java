/**
 * @(#)SalesTransactionCorrectionControllerTest.java
 *
 *                                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.SalesTransactionCorrectionForm;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.errorlist.SalesTransactionError;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.errorlist.SalesTransactionErrorListForm;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportTransaction;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportTransactionImportData;
import com.fastretailing.dcp.sales.salestransactioncorrection.service.SalesTransactionCorrectionService;

/**
 * Sales transaction correction controller test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SalesTransactionCorrectionControllerTest {

    /** SpringMVC request mock. */
    private MockMvc mockMvc;

    /** Request url. */
    private static final String REQUEST_URL = "/uq/jp/screen/sales-transaction-correction";

    /** Request upload url. */
    private static final String REQUEST_UPLOAD_URL =
            "/uq/ca/screen/sales-transaction-correction/upload";

    /** Request upload url. */
    private static final String REQUEST_DELETE_URL =
            "/uq/ca/screen/sales-transaction-correction/delete";

    /** Request upload url. */
    private static final String REQUEST_AUDIT_URL =
            "/uq/ca/screen/sales-transaction-correction/audit";

    /** Sales transaction correction service. */
    @MockBean
    private SalesTransactionCorrectionService salesTransactionCorrectionService;

    /** Web application context. */
    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Test initialize.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testInitialize() throws Exception {
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        SalesTransactionErrorListForm salesTransactionErrorListForm =
                new SalesTransactionErrorListForm();
        SalesTransactionError salesTransactionError2 = new SalesTransactionError();
        salesTransactionError2.setCheckFlag(1);
        salesTransactionError2.setSalesTransactionErrorId("1721123261801221000");
        salesTransactionError2.setStoreCode("111111111");
        salesTransactionError2.setBusinessDate("20130303");
        salesTransactionError2.setCashRegisterNo("11111");
        List<SalesTransactionError> salesTransactionErrors = new ArrayList<>();
        salesTransactionErrors.add(salesTransactionError2);
        salesTransactionErrorListForm.setSalesTransactionErrorList(salesTransactionErrors);
        ImportTransaction transaction = new ImportTransaction();
        List<ImportTransaction> transationList = new ArrayList<>();
        transationList.add(transaction);
        ImportTransactionImportData transactionImportData = new ImportTransactionImportData();
        transactionImportData.setTransactionList(transationList);
        salesTransactionCorrectionForm.setTransactionImportData(transactionImportData);
        when(salesTransactionCorrectionService.getSalesTransactionHeaderInformation(any()))
                .thenReturn(salesTransactionCorrectionForm);

        ResultActions res = mockMvc.perform(post(REQUEST_URL, salesTransactionErrorListForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test upload.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testUpload() throws Exception {
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        ResultActions res =
                mockMvc.perform(post(REQUEST_UPLOAD_URL, salesTransactionCorrectionForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test upload.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testDelete() throws Exception {
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        ResultActions res =
                mockMvc.perform(post(REQUEST_DELETE_URL, salesTransactionCorrectionForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test upload.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testAudit() throws Exception {
        SalesTransactionCorrectionForm salesTransactionCorrectionForm =
                new SalesTransactionCorrectionForm();
        ResultActions res =
                mockMvc.perform(post(REQUEST_AUDIT_URL, salesTransactionCorrectionForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
