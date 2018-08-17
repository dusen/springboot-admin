
/**
 * @(#)SalesTransactionCorrectionConvertTest.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.convert;

import static org.junit.Assert.assertEquals;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.common.repository.optional.TenderAndTransformMasterOptionalMapper;
import com.fastretailing.dcp.sales.importtransaction.component.CheckerHelper;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportNonItemDetail;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportTransaction;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportTransactionImportData;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportTransactionItemDetail;

/**
 * Sales transaction correction convert test.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesTransactionCorrectionConvertTest {

    /**
     * Sales transaction correction convert.
     */
    @Autowired
    SalesTransactionCorrectionConvert salesTransactionCorrectionConvert;

    /**
     * Tender and transform master optional mapper.
     */
    @MockBean
    private TenderAndTransformMasterOptionalMapper tenderAndTransformMasterOptionalMapper;

    /**
     * Checker helper.
     */
    @MockBean
    private CheckerHelper checkerHelper;

    /**
     * Test convert transaction import data from action flag true.
     */
    @Test
    public void testConvertTransactionImportDataFormActionFlagTrue() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        importTransactionImportData
                .setOrderConfirmationDateTime(OffsetDateTime.parse("2018-05-10T00:00+09:00"));
        importTransactionImportData.setOrderConfirmationBusinessDate("2018-11-26");
        ImportTransaction transaction = new ImportTransaction();
        transaction.setDataCreationBusinessDate("2018-11-26");
        transaction.setDataCreationDateTime(OffsetDateTime.parse("2018-05-13T00:00+09:00"));
        transaction.setOrderCancellationDate(OffsetDateTime.parse("2018-05-11T00:00+09:00"));
        transaction
                .setOrderStatusLastUpdateDateTime(OffsetDateTime.parse("2018-05-12T00:00+09:00"));
        transaction.setOrderStatusUpdateDate("2018-10-26");
        ImportNonItemDetail nonItemDetail = new ImportNonItemDetail();
        nonItemDetail
                .setOrderStatusLastUpdateDateTime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));
        nonItemDetail.setOrderStatusUpdateDate("2018-10-26");
        List<ImportNonItemDetail> nonItemDetailList = new ArrayList<>();
        nonItemDetailList.add(nonItemDetail);
        transaction.setNonItemDetailList(nonItemDetailList);
        ImportTransactionItemDetail transactionItemDetail = new ImportTransactionItemDetail();
        transactionItemDetail
                .setOrderStatusLastUpdateDateTime(OffsetDateTime.parse("2018-05-11T00:00+09:00"));
        transactionItemDetail.setOrderStatusUpdateDate("2018-10-26");
        transactionItemDetail.setNonItemDetailListByItem(nonItemDetailList);
        List<ImportTransactionItemDetail> transactionItemDetailList = new ArrayList<>();
        transactionItemDetailList.add(transactionItemDetail);
        transaction.setTransactionItemDetailList(transactionItemDetailList);
        List<ImportTransaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        importTransactionImportData.setTransactionList(transactionList);
        boolean actionFlag = true;
        importTransactionImportData = salesTransactionCorrectionConvert
                .convertTransactionImportDataForm(importTransactionImportData, actionFlag);
        assertEquals("2018/05/10 00:00:00",
                importTransactionImportData.getFormatOrderConfirmationDateTime());
        assertEquals("2018/11/26",
                importTransactionImportData.getFormatOrderConfirmationBusinessDate());
        assertEquals("2018/11/26",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getFormatDataCreationBusinessDate());
        assertEquals("2018/10/26",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getFormatOrderStatusUpdateDate());
        assertEquals("2018/05/11 00:00:00",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getFormatOrderCancellationDate());
        assertEquals("2018/05/12 00:00:00",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getFormatOrderStatusLastUpdateDateTime());
        assertEquals("2018/05/13 00:00:00",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getFormatDataCreationDateTime());
        assertEquals("2018/05/11 00:00:00",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getTransactionItemDetailList()
                        .get(0)
                        .getFormatOrderStatusLastUpdateDateTime());
        assertEquals("2018/10/26",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getTransactionItemDetailList()
                        .get(0)
                        .getFormatOrderStatusUpdateDate());
        assertEquals("2018/05/11 00:00:00",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getNonItemDetailList()
                        .get(0)
                        .getFormatOrderStatusLastUpdateDateTime());
        assertEquals("2018/10/26",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getNonItemDetailList()
                        .get(0)
                        .getFormatOrderStatusUpdateDate());
        assertEquals("2018/05/11 00:00:00",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getTransactionItemDetailList()
                        .get(0)
                        .getNonItemDetailListByItem()
                        .get(0)
                        .getFormatOrderStatusLastUpdateDateTime());
        assertEquals("2018/10/26",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getTransactionItemDetailList()
                        .get(0)
                        .getNonItemDetailListByItem()
                        .get(0)
                        .getFormatOrderStatusUpdateDate());
    }

    /**
     * Test convert transaction import data from action flag false.
     */
    @Test
    public void testConvertTransactionImportDataFormActionFlagFalse() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        importTransactionImportData.setFormatOrderConfirmationDateTime("2018/05/10 00:00:00");
        importTransactionImportData.setFormatOrderConfirmationBusinessDate("2018/11/26");
        ImportTransaction transaction = new ImportTransaction();
        transaction.setFormatDataCreationBusinessDate("2018/11/26");
        transaction.setFormatDataCreationDateTime("2018/05/13 00:00:00");
        transaction.setFormatOrderCancellationDate("2018/05/11 00:00:00");
        transaction.setFormatOrderStatusLastUpdateDateTime("2018/05/12 00:00:00");
        transaction.setFormatOrderStatusUpdateDate("2018/10/26");
        ImportNonItemDetail nonItemDetail = new ImportNonItemDetail();
        nonItemDetail.setFormatOrderStatusLastUpdateDateTime("2018/05/11 00:00:00");
        nonItemDetail.setFormatOrderStatusUpdateDate("2018/10/26");
        List<ImportNonItemDetail> nonItemDetailList = new ArrayList<>();
        nonItemDetailList.add(nonItemDetail);
        transaction.setNonItemDetailList(nonItemDetailList);
        ImportTransactionItemDetail transactionItemDetail = new ImportTransactionItemDetail();
        transactionItemDetail.setFormatOrderStatusLastUpdateDateTime("2018/05/11 00:00:00");
        transactionItemDetail.setFormatOrderStatusUpdateDate("2018/10/26");
        transactionItemDetail.setNonItemDetailListByItem(nonItemDetailList);
        List<ImportTransactionItemDetail> transactionItemDetailList = new ArrayList<>();
        transactionItemDetailList.add(transactionItemDetail);
        transaction.setTransactionItemDetailList(transactionItemDetailList);
        List<ImportTransaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        importTransactionImportData.setTransactionList(transactionList);
        boolean actionFlag = false;
        importTransactionImportData = salesTransactionCorrectionConvert
                .convertTransactionImportDataForm(importTransactionImportData, actionFlag);
        assertEquals(OffsetDateTime.parse("2018-05-10T00:00Z"),
                importTransactionImportData.getOrderConfirmationDateTime());
        assertEquals("2018-11-26", importTransactionImportData.getOrderConfirmationBusinessDate());
        assertEquals("2018-11-26",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getDataCreationBusinessDate());
        assertEquals("2018-10-26",
                importTransactionImportData.getTransactionList().get(0).getOrderStatusUpdateDate());
        assertEquals(OffsetDateTime.parse("2018-05-11T00:00Z"),
                importTransactionImportData.getTransactionList().get(0).getOrderCancellationDate());
        assertEquals(OffsetDateTime.parse("2018-05-12T00:00Z"),
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getOrderStatusLastUpdateDateTime());
        assertEquals(OffsetDateTime.parse("2018-05-13T00:00Z"),
                importTransactionImportData.getTransactionList().get(0).getDataCreationDateTime());
        assertEquals(OffsetDateTime.parse("2018-05-11T00:00Z"),
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getTransactionItemDetailList()
                        .get(0)
                        .getOrderStatusLastUpdateDateTime());
        assertEquals("2018-10-26",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getTransactionItemDetailList()
                        .get(0)
                        .getOrderStatusUpdateDate());
        assertEquals(OffsetDateTime.parse("2018-05-11T00:00Z"),
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getNonItemDetailList()
                        .get(0)
                        .getOrderStatusLastUpdateDateTime());
        assertEquals("2018-10-26",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getNonItemDetailList()
                        .get(0)
                        .getOrderStatusUpdateDate());
        assertEquals(OffsetDateTime.parse("2018-05-11T00:00Z"),
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getTransactionItemDetailList()
                        .get(0)
                        .getNonItemDetailListByItem()
                        .get(0)
                        .getOrderStatusLastUpdateDateTime());
        assertEquals("2018-10-26",
                importTransactionImportData.getTransactionList()
                        .get(0)
                        .getTransactionItemDetailList()
                        .get(0)
                        .getNonItemDetailListByItem()
                        .get(0)
                        .getOrderStatusUpdateDate());
    }

    /**
     * Test convert transaction import data form parse time null.
     */
    @Test
    public void testConvertTransactionImportDataFormParseTimeNull() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        boolean actionFlag = false;
        importTransactionImportData = salesTransactionCorrectionConvert
                .convertTransactionImportDataForm(importTransactionImportData, actionFlag);
        assertEquals(null, importTransactionImportData.getOrderConfirmationBusinessDate());
    }

    /**
     * Test convert transaction import data form parse null.
     */
    @Test
    public void testConvertTransactionImportDataFormParseNull() {
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        boolean actionFlag = true;
        importTransactionImportData = salesTransactionCorrectionConvert
                .convertTransactionImportDataForm(importTransactionImportData, actionFlag);
        assertEquals(null, importTransactionImportData.getOrderConfirmationBusinessDate());
    }
}
