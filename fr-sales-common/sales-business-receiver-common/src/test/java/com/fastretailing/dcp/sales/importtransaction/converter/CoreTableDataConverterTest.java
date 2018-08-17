package com.fastretailing.dcp.sales.importtransaction.converter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemDiscount;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDiscountDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTotal;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;
import com.fastretailing.dcp.sales.importtransaction.dto.TenderInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderTable;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTotalAmount;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CoreTableDataConverterTest {
    @Autowired
    private CoreTableDataConverter converter;

    @MockBean
    private CommonDataProcessor commonDataProcessor;

    /**
     * Parameter information setting.
     * 
     * @throws Exception occurred exception
     */
    @Before
    public void setUp() throws Exception {}

    /**
     * ConvertTSalesOrderInformationEntityForInsert.
     */
    @Test
    public void testConvertTSalesOrderInformationEntityForInsert() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesOrderInformation expect = CoreTableDataConverterTestHelper.makeOrderEntity();
        TransactionImportData importData = CoreTableDataConverterTestHelper.makeImportData();
        SalesOrderInformation actual =
                converter.convertTSalesOrderInformationEntityForInsert(importData, userId);

        System.out.println(expect);
        System.out.println(actual);
        assertEquals(expect, actual);
    }

    /**
     * ConvertTSalesOrderInformationEntityForInsert.
     */
    @Test
    public void testConvertTSalesOrderInformationEntityForInsertNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesOrderInformation expect = CoreTableDataConverterTestHelper.makeOrderEntity();
        expect.setDataAlterationEditingFlag(false);
        expect.setOrderConfirmationDateTime(null);

        TransactionImportData importData = CoreTableDataConverterTestHelper.makeImportData();
        importData.setDataCorrectionEditingFlag(null);
        importData.setOrderConfirmationDateTime(null);
        SalesOrderInformation actual =
                converter.convertTSalesOrderInformationEntityForInsert(importData, userId);

        System.out.println(expect);
        System.out.println(actual);
        assertEquals(expect, actual);
    }

    /**
     * ConvertTSalesOrderInformationEntityForAutoInsert.
     */
    @Test
    public void testConvertTSalesTransactionHeaderEntityForInsert() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionHeader expect = CoreTableDataConverterTestHelper.makeHeaderEntity();
        List<Transaction> transactionList = CoreTableDataConverterTestHelper.makeHeader();
        int transactionCount = 1986;
        transactionList.forEach(transaction -> {
            SalesTransactionHeader actual = converter.convertTSalesTransactionHeaderEntityForInsert(
                    transaction, userId, transactionCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesOrderInformationEntityForAutoInsert.
     */
    @Test
    public void testConvertTSalesTransactionHeaderEntityForInsertNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionHeader expect = CoreTableDataConverterTestHelper.makeHeaderEntity();
        expect.setConsistencySalesFlag(false);
        expect.setEmployeeSaleFlag(false);
        expect.setReceiptIssuedFlag(false);
        expect.setEReceiptTargetFlag(false);
        expect.setDataCreationDateTime(null);
        expect.setOrderStatusLastUpdateDateTime(null);
        expect.setOrderCancelledDateTime(null);
        expect.setDepositValue(null);
        expect.setDepositCurrencyCode(null);
        expect.setChangeValue(null);
        expect.setChangeCurrencyCode(null);
        expect.setSalesTransactionDiscountAmountRate(null);
        expect.setSalesTransactionDiscountAmountRateCurrencyCode(null);

        List<Transaction> transactionList = CoreTableDataConverterTestHelper.makeHeader();
        int transactionCount = 1986;
        transactionList.forEach(transaction -> {
            transaction.setConsistencySalesFlag(null);
            transaction.setEmployeeSaleFlag(null);
            transaction.setReceiptIssuedFlag(null);
            transaction.setEReceiptTargetFlag(null);
            transaction.setDataCreationDateTime(null);
            transaction.setOrderStatusLastUpdateDateTime(null);
            transaction.setOrderCancellationDate(null);
            transaction.setDeposit(null);
            transaction.setChange(null);
            transaction.setSalesTransactionDiscountAmountRate(null);
            SalesTransactionHeader actual = converter.convertTSalesTransactionHeaderEntityForInsert(
                    transaction, userId, transactionCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionDetailEntityForInsert.
     */
    @Test
    public void testConvertTSalesTransactionDetailEntityForInsert() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionDetail expect = CoreTableDataConverterTestHelper.makeItemDetailEntity();
        List<TransactionItemDetail> transactionItemDetailList =
                CoreTableDataConverterTestHelper.makeItemDetail();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemDetailSubNumber = 1986;
        transactionItemDetailList.forEach(transactionItemDetail -> {
            SalesTransactionDetail actual = converter.convertTSalesTransactionDetailEntityForInsert(
                    transactionItemDetail, userId, salesTransactionId, orderSubNumber,
                    detailSubNumber, itemDetailSubNumber);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });

        TransactionItemDetail transactionItemDetail = transactionItemDetailList.get(0);
        transactionItemDetail.setBundleSalesFlag(null);

        SalesTransactionDetail actual = converter.convertTSalesTransactionDetailEntityForInsert(
                transactionItemDetail, userId, salesTransactionId, orderSubNumber, detailSubNumber,
                itemDetailSubNumber);

        expect.setStoreBundleSaleFlag(false);
        assertEquals(expect, actual);
    }

    /**
     * ConvertTSalesTransactionDetailEntityForInsert.
     */
    @Test
    public void testConvertTSalesTransactionDetailEntityForInsertNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionDetail expect = CoreTableDataConverterTestHelper.makeItemDetailEntity();
        expect.setOrderStatusLastUpdateDateTime(null);
        expect.setMajorCategoryCode(null);
        expect.setDetailQuantity(null);
        expect.setBundlePurchaseApplicableQuantity(null);
        expect.setItemCostCurrencyCode(null);
        expect.setItemCostValue(null);
        expect.setBclassPriceCurrencyCode(null);
        expect.setBclassPrice(null);
        expect.setInitialSellingPrice(null);
        expect.setInitialSellingPriceCurrencyCode(null);
        expect.setNewPrice(null);
        expect.setNewPriceCurrencyCode(null);
        expect.setRetailUnitPriceTaxExcluded(null);
        expect.setRetailUnitPriceTaxExcludedCurrencyCode(null);
        expect.setRetailUnitPriceTaxIncluded(null);
        expect.setRetailUnitPriceTaxIncludedCurrencyCode(null);
        expect.setSalesAmountTaxExcluded(null);
        expect.setSalesAmountTaxExcludedCurrencyCode(null);
        expect.setSalesAmountTaxIncluded(null);
        expect.setSalesAmountTaxIncludedCurrencyCode(null);
        expect.setRetailUnitPriceTaxIncluded(null);
        expect.setRetailUnitPriceTaxIncludedCurrencyCode(null);
        expect.setRetailUnitPriceTaxExcluded(null);
        expect.setRetailUnitPriceTaxExcludedCurrencyCode(null);
        expect.setBundlePurchaseApplicablePrice(null);
        expect.setBundlePurchaseApplicablePriceCurrencyCode(null);
        expect.setStoreItemDiscountCurrencyCode(null);
        expect.setStoreItemDiscountSetting(null);
        expect.setStoreBundleSalePrice(null);
        expect.setStoreBundleSalePriceCurrencyCode(null);

        List<TransactionItemDetail> transactionItemDetailList =
                CoreTableDataConverterTestHelper.makeItemDetail();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemDetailSubNumber = 1986;
        transactionItemDetailList.forEach(transactionItemDetail -> {
            transactionItemDetail.setOrderStatusLastUpdateDateTime(null);
            transactionItemDetail.setDeptCode(null);
            transactionItemDetail.setItemQty(null);
            transactionItemDetail.setBundlePurchaseQty(null);
            transactionItemDetail.setItemCost(null);
            transactionItemDetail.setInitialSellingPrice(null);
            transactionItemDetail.setBItemSellingPrice(null);
            transactionItemDetail.setItemNewPrice(null);
            transactionItemDetail.setItemUnitPriceTaxExcluded(null);
            transactionItemDetail.setItemUnitPriceTaxIncluded(null);
            transactionItemDetail.setItemSalesAmtTaxExcluded(null);
            transactionItemDetail.setItemSalesAmtTaxIncluded(null);
            transactionItemDetail.setBundlePurchasePrice(null);
            transactionItemDetail.setItemDiscountAmount(null);
            transactionItemDetail.setBundleSalesPrice(null);

            SalesTransactionDetail actual = converter.convertTSalesTransactionDetailEntityForInsert(
                    transactionItemDetail, userId, salesTransactionId, orderSubNumber,
                    detailSubNumber, itemDetailSubNumber);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionDetailEntityForInsertOutside.
     */
    @Test
    public void testConvertTSalesTransactionDetailEntityForInsertOutside() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionDetail expect = CoreTableDataConverterTestHelper.makeNonItemDetailEntity();
        List<NonItemDetail> nonItemDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDetail();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemDetailSubNumber = 1986;
        String salesTransactionType = "correction";
        nonItemDetailList.forEach(nonItemDetail -> {
            SalesTransactionDetail actual =
                    converter.convertTSalesTransactionDetailEntityForInsertOutside(nonItemDetail,
                            salesTransactionId, orderSubNumber, salesTransactionType, userId,
                            detailSubNumber, itemDetailSubNumber);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    @Test
    public void testConvertTSalesTransactionDetailEntityForInsertOutsideNullCurrency() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionDetail expect = CoreTableDataConverterTestHelper.makeNonItemDetailEntity();
        List<NonItemDetail> nonItemDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDetail();
        expect.setRetailUnitPriceTaxExcludedCurrencyCode(null);
        expect.setRetailUnitPriceTaxIncludedCurrencyCode(null);
        expect.setSalesAmountTaxExcludedCurrencyCode(null);
        expect.setSalesAmountTaxIncludedCurrencyCode(null);
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemDetailSubNumber = 1986;
        String salesTransactionType = "correction";
        nonItemDetailList.forEach(nonItemDetail -> {
            nonItemDetail.getNonItemSalesAmtTaxExcluded().setCurrencyCode(null);
            nonItemDetail.getNonItemUnitPriceTaxExcluded().setCurrencyCode(null);
            nonItemDetail.getNonItemUnitPriceTaxIncluded().setCurrencyCode(null);
            nonItemDetail.getNonItemSalesAmtTaxIncluded().setCurrencyCode(null);
            SalesTransactionDetail actual =
                    converter.convertTSalesTransactionDetailEntityForInsertOutside(nonItemDetail,
                            salesTransactionId, orderSubNumber, salesTransactionType, userId,
                            detailSubNumber, itemDetailSubNumber);

            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionDetailEntityForInsertOutside.
     */
    @Test
    public void testConvertTSalesTransactionDetailEntityForInsertOutsideNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionDetail expect = CoreTableDataConverterTestHelper.makeNonItemDetailEntity();
        expect.setOrderStatusLastUpdateDateTime(null);
        expect.setDetailQuantity(null);
        expect.setNewPrice(null);
        expect.setNewPriceCurrencyCode(null);
        expect.setRetailUnitPriceTaxExcluded(null);
        expect.setRetailUnitPriceTaxExcludedCurrencyCode(null);
        expect.setSalesAmountTaxExcluded(null);
        expect.setSalesAmountTaxExcludedCurrencyCode(null);
        expect.setInitialSellingPriceCurrencyCode(null);
        expect.setRetailUnitPriceTaxIncluded(null);
        expect.setRetailUnitPriceTaxIncludedCurrencyCode(null);
        expect.setSalesAmountTaxIncluded(null);
        expect.setSalesAmountTaxIncludedCurrencyCode(null);

        List<NonItemDetail> nonItemDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDetail();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemDetailSubNumber = 1986;
        String salesTransactionType = "correction";
        nonItemDetailList.forEach(nonItemDetail -> {
            nonItemDetail.setOrderStatusLastUpdateDateTime(null);
            nonItemDetail.setNonItemQty(null);
            nonItemDetail.setNonItemNewPrice(null);
            nonItemDetail.setNonItemSalesAmtTaxExcluded(null);
            nonItemDetail.setNonItemUnitPriceTaxExcluded(null);
            nonItemDetail.setNonItemSalesAmtTaxIncluded(null);
            nonItemDetail.setNonItemUnitPriceTaxIncluded(null);
            SalesTransactionDetail actual =
                    converter.convertTSalesTransactionDetailEntityForInsertOutside(nonItemDetail,
                            salesTransactionId, orderSubNumber, salesTransactionType, userId,
                            detailSubNumber, itemDetailSubNumber);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionDetailInfoEntityForInsert.
     */
    @Test
    public void testConvertTSalesTransactionDetailInfoEntityForInsert() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionDetailInfo expect =
                CoreTableDataConverterTestHelper.makeNonItemInfoEntity();
        List<NonItemDetail> nonItemDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDetail();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemDetailSubNumber = 1986;
        nonItemDetailList.forEach(nonItemInfo -> {
            SalesTransactionDetailInfo actual =
                    converter.convertTSalesTransactionDetailInfoEntityForInsert(
                            nonItemInfo.getNonItemInfo(), salesTransactionId, orderSubNumber,
                            userId, detailSubNumber, itemDetailSubNumber);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionDiscountEntityForInsertNon.
     */
    @Test
    public void testConvertTSalesTransactionDiscountEntityForInsertNon() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionDiscount expect =
                CoreTableDataConverterTestHelper.makeNonItemDiscountDetailEntity();
        List<NonItemDiscountDetail> nonItemDiscountDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDiscountDetail();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemDiscountCount = 1986;
        nonItemDiscountDetailList.forEach(nonItemDiscountDetail -> {
            SalesTransactionDiscount actual =
                    converter.convertTSalesTransactionDiscountEntityForInsertNon(
                            nonItemDiscountDetail, salesTransactionId, orderSubNumber, userId,
                            detailSubNumber, itemDiscountCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionDiscountEntityForInsertNon.
     */
    @Test
    public void testConvertTSalesTransactionDiscountEntityForInsertNonNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionDiscount expect =
                CoreTableDataConverterTestHelper.makeNonItemDiscountDetailEntity();
        expect.setDiscountAmountTaxExcluded(null);
        expect.setDiscountAmountTaxExcludedCurrencyCode(null);
        expect.setDiscountAmountTaxIncluded(null);
        expect.setDiscountAmountTaxIncludedCurrencyCode(null);

        List<NonItemDiscountDetail> nonItemDiscountDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDiscountDetail();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemDiscountCount = 1986;
        nonItemDiscountDetailList.forEach(nonItemDiscountDetail -> {
            nonItemDiscountDetail.setNonItemDiscountAmtTaxExcluded(null);
            nonItemDiscountDetail.setNonItemDiscountAmtTaxIncluded(null);
            SalesTransactionDiscount actual =
                    converter.convertTSalesTransactionDiscountEntityForInsertNon(
                            nonItemDiscountDetail, salesTransactionId, orderSubNumber, userId,
                            detailSubNumber, itemDiscountCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    @Test
    public void testConvertTSalesTransactionDiscountEntityForInsertNon_PromotionNo() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionDiscount expect =
                CoreTableDataConverterTestHelper.makeNonItemDiscountDetailEntity();
        expect.setPromotionNo("9");
        List<NonItemDiscountDetail> nonItemDiscountDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDiscountDetail();

        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemDiscountCount = 1986;
        nonItemDiscountDetailList.forEach(nonItemDiscountDetail -> {
            nonItemDiscountDetail.setNonItemPromotionNumber("9");
            SalesTransactionDiscount actual =
                    converter.convertTSalesTransactionDiscountEntityForInsertNon(
                            nonItemDiscountDetail, salesTransactionId, orderSubNumber, userId,
                            detailSubNumber, itemDiscountCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionTaxEntityForInsertNon.
     */
    @Test
    public void testConvertTSalesTransactionTaxEntityForInsertNon() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionTax expect = CoreTableDataConverterTestHelper.makeNonItemTaxDetail1Entity();
        List<NonItemTaxDetail> nonItemTaxDetailList =
                CoreTableDataConverterTestHelper.makeNonItemTaxDetail1();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemTaxCount = 1986;
        nonItemTaxDetailList.forEach(nonItemTaxDetail -> {
            SalesTransactionTax actual = converter.convertTSalesTransactionTaxEntityForInsertNon(
                    nonItemTaxDetail, salesTransactionId, orderSubNumber, userId, detailSubNumber,
                    itemTaxCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionTaxEntityForInsertNon.
     */
    @Test
    public void testConvertTSalesTransactionTaxEntityForInsertNonNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionTax expect = CoreTableDataConverterTestHelper.makeNonItemTaxDetail1Entity();
        expect.setTaxAmountValue(null);
        expect.setTaxAmountCurrencyCode(null);

        List<NonItemTaxDetail> nonItemTaxDetailList =
                CoreTableDataConverterTestHelper.makeNonItemTaxDetail1();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemTaxCount = 1986;
        nonItemTaxDetailList.forEach(nonItemTaxDetail -> {
            nonItemTaxDetail.setNonItemTaxAmt(null);
            SalesTransactionTax actual = converter.convertTSalesTransactionTaxEntityForInsertNon(
                    nonItemTaxDetail, salesTransactionId, orderSubNumber, userId, detailSubNumber,
                    itemTaxCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionDiscountEntityForInsert.
     */
    @Test
    public void testConvertTSalesTransactionDiscountEntityForInsert() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionDiscount expect = CoreTableDataConverterTestHelper.makeItemDiscountEntity();
        List<ItemDiscount> itemDiscountList = CoreTableDataConverterTestHelper.makeItemDiscount();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemDiscountCount = 1986;
        itemDiscountList.forEach(itemDiscount -> {
            SalesTransactionDiscount actual =
                    converter.convertTSalesTransactionDiscountEntityForInsert(itemDiscount,
                            salesTransactionId, orderSubNumber, userId, detailSubNumber,
                            itemDiscountCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionDiscountEntityForInsert.
     */
    @Test
    public void testConvertTSalesTransactionDiscountEntityForInsertNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionDiscount expect = CoreTableDataConverterTestHelper.makeItemDiscountEntity();
        expect.setDiscountAmountTaxExcluded(null);
        expect.setDiscountAmountTaxExcludedCurrencyCode(null);
        expect.setDiscountAmountTaxIncluded(null);
        expect.setDiscountAmountTaxIncludedCurrencyCode(null);

        List<ItemDiscount> itemDiscountList = CoreTableDataConverterTestHelper.makeItemDiscount();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 1986;
        int itemDiscountCount = 1986;
        itemDiscountList.forEach(itemDiscount -> {
            itemDiscount.setItemDiscountAmtTaxIncluded(null);
            itemDiscount.setItemDiscountAmtTaxExcluded(null);
            SalesTransactionDiscount actual =
                    converter.convertTSalesTransactionDiscountEntityForInsert(itemDiscount,
                            salesTransactionId, orderSubNumber, userId, detailSubNumber,
                            itemDiscountCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionTaxEntityForInsert.
     */
    @Test
    public void testConvertTSalesTransactionTaxEntityForInsert() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionTax expect = CoreTableDataConverterTestHelper.makeItemTaxDetailEntity();
        List<ItemTaxDetail> itemTaxDetailList =
                CoreTableDataConverterTestHelper.makeItemTaxDetail();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 0;
        int itemTaxCount = 1986;
        itemTaxDetailList.forEach(itemTaxDetail -> {
            SalesTransactionTax actual = converter.convertTSalesTransactionTaxEntityForInsert(
                    itemTaxDetail, salesTransactionId, orderSubNumber, userId, detailSubNumber,
                    itemTaxCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionTaxEntityForInsert.
     */
    @Test
    public void testConvertTSalesTransactionTaxEntityForInsertNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionTax expect = CoreTableDataConverterTestHelper.makeItemTaxDetailEntity();
        expect.setTaxAmountValue(null);
        expect.setTaxAmountCurrencyCode(null);

        List<ItemTaxDetail> itemTaxDetailList =
                CoreTableDataConverterTestHelper.makeItemTaxDetail();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailSubNumber = 0;
        int itemTaxCount = 1986;
        itemTaxDetailList.forEach(itemTaxDetail -> {
            itemTaxDetail.setItemTaxAmt(null);
            SalesTransactionTax actual = converter.convertTSalesTransactionTaxEntityForInsert(
                    itemTaxDetail, salesTransactionId, orderSubNumber, userId, detailSubNumber,
                    itemTaxCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionTenderEntityForInsert.
     */
    @Test
    public void testConvertTSalesTransactionTenderEntityForInsert() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionTenderTable expect = CoreTableDataConverterTestHelper.makeTenderEntity();
        List<SalesTransactionTender> tenderList = CoreTableDataConverterTestHelper.makeTender();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int payDetailCount = 1986;
        tenderList.forEach(tender -> {
            SalesTransactionTenderTable actual =
                    converter.convertTSalesTransactionTenderEntityForInsert(tender,
                            salesTransactionId, orderSubNumber, userId, payDetailCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionTenderEntityForInsert.
     */
    @Test
    public void testConvertTSalesTransactionTenderEntityForInsertNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionTenderTable expect = CoreTableDataConverterTestHelper.makeTenderEntity();
        expect.setTaxIncludedPaymentAmountCurrencyCode(null);
        expect.setTaxIncludedPaymentAmountValue(null);

        List<SalesTransactionTender> tenderList = CoreTableDataConverterTestHelper.makeTender();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int payDetailCount = 1986;
        tenderList.forEach(tender -> {
            tender.setTaxIncludedPaymentAmount(null);
            SalesTransactionTenderTable actual =
                    converter.convertTSalesTransactionTenderEntityForInsert(tender,
                            salesTransactionId, orderSubNumber, userId, payDetailCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionTenderInfoEntityForInsert.
     */
    @Test
    public void testConvertTSalesTransactionTenderInfoEntityForInsert() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionTenderInfo expect = CoreTableDataConverterTestHelper.makeTenderInfoEntity();
        TenderInfo tenderInfoList = CoreTableDataConverterTestHelper.makeTenderInfo();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        String tenderId = "123456";
        String tenderGroup = "123456";
        SalesTransactionTenderInfo actual =
                converter.convertTSalesTransactionTenderInfoEntityForInsert(tenderInfoList,
                        salesTransactionId, orderSubNumber, tenderGroup, tenderId, userId, 1);

        System.out.println(expect);
        System.out.println(actual);
        assertEquals(expect, actual);
    }

    /**
     * ConvertTSalesTransactionTenderInfoEntityForInsert.
     */
    @Test
    public void testConvertTSalesTransactionTenderInfoEntityForInsertNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionTenderInfo expect = CoreTableDataConverterTestHelper.makeTenderInfoEntity();
        expect.setDiscountValueCurrencyCode(null);
        expect.setDiscountValue(null);
        expect.setCouponMinUsageAmountThresholdCurrencyCode(null);
        expect.setCouponMinUsageAmountThresholdValue(null);
        expect.setCouponDiscountAmountSettingCurrencyCode(null);
        expect.setCouponDiscountAmountSettingValue(null);

        TenderInfo tenderInfoList = CoreTableDataConverterTestHelper.makeTenderInfo();
        tenderInfoList.setDiscountAmount(null);
        tenderInfoList.setCouponMinUsageAmountThreshold(null);
        tenderInfoList.setCouponDiscountAmountSetting(null);
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        String tenderId = "123456";
        String tenderGroup = "123456";
        SalesTransactionTenderInfo actual =
                converter.convertTSalesTransactionTenderInfoEntityForInsert(tenderInfoList,
                        salesTransactionId, orderSubNumber, tenderGroup, tenderId, userId, 1);

        System.out.println(expect);
        System.out.println(actual);
        assertEquals(expect, actual);
    }

    /**
     * ConvertTSalesTransactionTaxEntityForInsertTransaction().
     */
    @Test
    public void testConvertTSalesTransactionTaxEntityForInsertTransaction() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionTax expect =
                CoreTableDataConverterTestHelper.makeTaxDetailOfTransactionEntity();
        List<SalesTransactionTaxDetail> taxDetailList =
                CoreTableDataConverterTestHelper.makeTaxDetailOfTransaction();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailCount = 0;
        int taxDetailCount = 1986;
        taxDetailList.forEach(taxDetail -> {
            SalesTransactionTax actual =
                    converter.convertTSalesTransactionTaxEntityForInsertTransaction(taxDetail,
                            salesTransactionId, orderSubNumber, userId, detailCount,
                            taxDetailCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionTaxEntityForInsertTransaction().
     */
    @Test
    public void testConvertTSalesTransactionTaxEntityForInsertTransactionNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionTax expect =
                CoreTableDataConverterTestHelper.makeTaxDetailOfTransactionEntity();
        expect.setTaxAmountCurrencyCode(null);
        expect.setTaxAmountValue(null);

        List<SalesTransactionTaxDetail> taxDetailList =
                CoreTableDataConverterTestHelper.makeTaxDetailOfTransaction();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int detailCount = 0;
        int taxDetailCount = 1986;
        taxDetailList.forEach(taxDetail -> {
            taxDetail.setTaxAmount(null);
            SalesTransactionTax actual =
                    converter.convertTSalesTransactionTaxEntityForInsertTransaction(taxDetail,
                            salesTransactionId, orderSubNumber, userId, detailCount,
                            taxDetailCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionTotalAmountEntityForInsert().
     */
    @Test
    public void testConvertTSalesTransactionTotalAmountEntityForInsert() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);

        SalesTransactionTotalAmount expect = CoreTableDataConverterTestHelper.makeTotalEntity();
        expect.setTotalAmountTaxExcludedCurrencyCode(null);
        expect.setTotalAmountTaxExcludedValue(null);
        expect.setTotalAmountTaxIncludedCurrencyCode(null);
        expect.setTotalAmountTaxIncludedValue(null);

        List<SalesTransactionTotal> totalList = CoreTableDataConverterTestHelper.makeTotal();
        String salesTransactionId = "1234567890abcdefghij1234567890";
        int orderSubNumber = 1986;
        int totalDetailCount = 1986;
        totalList.forEach(total -> {

            total.setTotalAmountTaxExcluded(null);
            total.setTotalAmountTaxIncluded(null);

            SalesTransactionTotalAmount actual =
                    converter.convertTSalesTransactionTotalAmountEntityForInsert(total,
                            salesTransactionId, orderSubNumber, userId, totalDetailCount);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionDetailOutsideEntityForUpdate().
     */
    @Test
    public void testConvertTSalesTransactionDetailOutsideEntityForUpdateNull() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForUpdate(userId)).thenReturn(tableCommonItem);

        SalesTransactionDetail expect =
                CoreTableDataConverterTestHelper.makeNonItemDetailEntityUpdate();

        List<NonItemDetail> nonItemDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDetail();
        expect.setDetailSubNumber(nonItemDetailList.get(0).getDetailSubNumber());
        expect.setOrderStatusLastUpdateDateTime(null);

        String salesTransactionId = "1234567890abcdefghij1234567890";
        nonItemDetailList.forEach(nonItemDetail -> {
            nonItemDetail.setOrderStatusLastUpdateDateTime(null);
            SalesTransactionDetail actual =
                    converter.convertTSalesTransactionDetailOutsideEntityForUpdate(nonItemDetail,
                            salesTransactionId, userId);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }

    /**
     * ConvertTSalesTransactionDetailOutsideEntityForUpdate().
     */
    @Test
    public void testConvertTSalesTransactionDetailOutsideEntityForUpdate() {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("001");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setUpdateUserId("001");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        tableCommonItem.setUpdateProgramId("SLS0300112");
        String userId = "001";
        when(commonDataProcessor.getTableCommonItemForUpdate(userId)).thenReturn(tableCommonItem);

        SalesTransactionDetail expect =
                CoreTableDataConverterTestHelper.makeNonItemDetailEntityUpdate();

        List<NonItemDetail> nonItemDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDetail();

        String salesTransactionId = "1234567890abcdefghij1234567890";
        nonItemDetailList.forEach(nonItemDetail -> {

            nonItemDetail.setDetailSubNumber(1986);
            SalesTransactionDetail actual =
                    converter.convertTSalesTransactionDetailOutsideEntityForUpdate(nonItemDetail,
                            salesTransactionId, userId);

            System.out.println(expect);
            System.out.println(actual);
            assertEquals(expect, actual);
        });
    }
}
