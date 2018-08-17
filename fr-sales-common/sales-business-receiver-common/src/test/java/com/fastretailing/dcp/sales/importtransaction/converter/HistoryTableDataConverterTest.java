package com.fastretailing.dcp.sales.importtransaction.converter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistoryOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTotalAmount;

/**
 * ErrorEvacuationTableDataConverter JUnit Test class.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HistoryTableDataConverterTest {

    @Autowired
    private HistoryTableDataConverter historyTableDataConverter;

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
     * ConvertTAlterationHistoryOrderInformationEntityForInsertBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistoryOrderInformationEntityForInsertBeforeEdit() {
        SalesErrorSalesOrderInformation salesErrorOrderInformation =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesOrderInformation();
        String salesTransactionErrorId = "salesTransactionErrorId01";
        String userId = "userId01";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user01");
        tableCommonItem.setCreateProgramId("SLS0300101");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistoryOrderInformation expect =
                HistoryTableDataConverterTestHelper.makeTAlterationHistoryOrderInformationBefore();

        AlterationHistoryOrderInformation result = historyTableDataConverter
                .convertTAlterationHistoryOrderInformationEntityForInsertBeforeEdit(
                        salesErrorOrderInformation, salesTransactionErrorId, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTAlterationHistoryOrderInformationEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistoryOrderInformationEntityForInsertAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId02";
        String salesTransactionErrorId = "salesTransactionErrorId02";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId02";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 31);
        tableCommonItem.setCreateUserId("user02");
        tableCommonItem.setCreateProgramId("SLS0300102");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user02");
        tableCommonItem.setUpdateProgramId("SLS0300102");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistoryOrderInformation expect =
                HistoryTableDataConverterTestHelper.makeTAlterationHistoryOrderInformationAfter();

        AlterationHistoryOrderInformation result = historyTableDataConverter
                .convertTAlterationHistoryOrderInformationEntityForInsertAfterEdit(
                        transactionImportData, userId, salesTransactionErrorId,
                        originalSalesTransactionErrorId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTAlterationHistoryOrderInformationEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistoryOrderInformationEntityForInsertAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        transactionImportData.setOrderConfirmationDateTime(null);
        transactionImportData.setDataCorrectionEditingFlag(null);
        String userId = "userId02";
        String salesTransactionErrorId = "salesTransactionErrorId02";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId02";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 31);
        tableCommonItem.setCreateUserId("user02");
        tableCommonItem.setCreateProgramId("SLS0300102");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user02");
        tableCommonItem.setUpdateProgramId("SLS0300102");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistoryOrderInformation expect =
                HistoryTableDataConverterTestHelper.makeTAlterationHistoryOrderInformationAfter();
        expect.setDataAlterationEditingFlag(false);
        expect.setOrderConfirmationDateTime(null);

        AlterationHistoryOrderInformation result = historyTableDataConverter
                .convertTAlterationHistoryOrderInformationEntityForInsertAfterEdit(
                        transactionImportData, userId, salesTransactionErrorId,
                        originalSalesTransactionErrorId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTAlterationHistorySalesTransactionHeaderEntityForInsertBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionHeaderEntityForInsertBeforeEdit() {
        SalesErrorSalesTransactionHeader salesErrorSalesTransactionHeader =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionHeader();
        String orignialSalesTransactionErrorId = "orignialSalesTransactionErrorId03";
        String userId = "userId03";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 22);
        tableCommonItem.setCreateUserId("user03");
        tableCommonItem.setCreateProgramId("SLS0300103");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user03");
        tableCommonItem.setUpdateProgramId("SLS0300103");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionHeader expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionHeaderBefore();

        AlterationHistorySalesTransactionHeader result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionHeaderEntityForInsertBeforeEdit(
                        salesErrorSalesTransactionHeader, orignialSalesTransactionErrorId, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);

    }

    /**
     * ConvertTAlterationHistorySalesTransactionHeaderEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionHeaderEntityForInsertAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId04";
        String salesTransactionErrorId = "salesTransactionErrorId04";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId04";
        Integer salesTransactionSubNumber = 205;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("user04");
        tableCommonItem.setCreateProgramId("SLS0300104");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user04");
        tableCommonItem.setUpdateProgramId("SLS0300104");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionHeader expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionHeaderAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            AlterationHistorySalesTransactionHeader result = historyTableDataConverter
                    .convertTAlterationHistorySalesTransactionHeaderEntityForInsertAfterEdit(
                            transaction, userId, salesTransactionErrorId,
                            originalSalesTransactionErrorId, salesTransactionSubNumber);

            System.out.println(result);
            System.out.println(expect);
            assertEquals(expect, result);
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionHeaderEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionHeaderEntityForInsertAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId04";
        String salesTransactionErrorId = "salesTransactionErrorId04";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId04";
        Integer salesTransactionSubNumber = 205;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("user04");
        tableCommonItem.setCreateProgramId("SLS0300104");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user04");
        tableCommonItem.setUpdateProgramId("SLS0300104");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionHeader expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionHeaderAfter();
        expect.setConsistencySalesFlag(false);
        expect.setEmployeeSaleFlag(false);
        expect.setReceiptIssuedFlag(false);
        expect.setEReceiptTargetFlag(false);
        expect.setDataCreationDateTime(null);
        expect.setOrderStatusLastUpdateDateTime(null);
        expect.setOrderCancelledDateTime(null);
        expect.setDepositCurrencyCode(null);
        expect.setDepositValue(null);
        expect.setChangeCurrencyCode(null);
        expect.setChangeValue(null);
        expect.setSalesTransactionDiscountAmountRate(null);
        expect.setSalesTransactionDiscountAmountRateCurrencyCode(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
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

            AlterationHistorySalesTransactionHeader result = historyTableDataConverter
                    .convertTAlterationHistorySalesTransactionHeaderEntityForInsertAfterEdit(
                            transaction, userId, salesTransactionErrorId,
                            originalSalesTransactionErrorId, salesTransactionSubNumber);

            System.out.println(result);
            System.out.println(expect);
            assertEquals(expect, result);
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionDetailEntityForInsertBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDetailEntityForInsertBeforeEdit() {
        SalesErrorSalesTransactionDetail salesErrorSalesTransactionDetail =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionDetail();
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId05";
        String userId = "userId05";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        tableCommonItem.setCreateUserId("user05");
        tableCommonItem.setCreateProgramId("SLS0300105");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user05");
        tableCommonItem.setUpdateProgramId("SLS0300105");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDetail expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDetailBefore();

        AlterationHistorySalesTransactionDetail result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionDetailEntityForInsertBeforeEdit(
                        salesErrorSalesTransactionDetail, originalSalesTransactionErrorId, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);

    }

    /**
     * ConvertTAlterationHistorySalesTransactionDetailEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDetailEntityForInsertAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId06";
        String salesTransactionId = "salesTransactionId06";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId06";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId06";
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 33);
        tableCommonItem.setCreateUserId("user06");
        tableCommonItem.setCreateProgramId("SLS0300106");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user06");
        tableCommonItem.setUpdateProgramId("SLS0300106");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDetail expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDetailAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                AlterationHistorySalesTransactionDetail result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionDetailEntityForInsertAfterEdit(
                                transactionItemDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId,
                                detailCount, itemDetailCount);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
        TransactionItemDetail transactionItemDetail = transactionImportData.getTransactionList()
                .get(0)
                .getTransactionItemDetailList()
                .get(0);
        transactionItemDetail.setBundleSalesFlag(null);
        AlterationHistorySalesTransactionDetail result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionDetailEntityForInsertAfterEdit(
                        transactionItemDetail, userId, salesTransactionId, orderSubNumber,
                        salesTransactionErrorId, originalSalesTransactionErrorId, detailCount,
                        itemDetailCount);

        expect.setStoreBundleSaleFlag(false);
        assertEquals(expect, result);
    }

    /**
     * ConvertTAlterationHistorySalesTransactionDetailEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDetailEntityForInsertAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId06";
        String salesTransactionId = "salesTransactionId06";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId06";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId06";
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 33);
        tableCommonItem.setCreateUserId("user06");
        tableCommonItem.setCreateProgramId("SLS0300106");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user06");
        tableCommonItem.setUpdateProgramId("SLS0300106");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDetail expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDetailAfter();
        expect.setOrderStatusLastUpdateDateTime(null);
        expect.setMajorCategoryCode(null);
        expect.setDetailQuantity(null);
        expect.setBundlePurchaseApplicableQuantity(null);
        expect.setItemCostCurrencyCode(null);
        expect.setItemCostValue(null);
        expect.setInitialSellingPrice(null);
        expect.setInitialSellingPriceCurrencyCode(null);
        expect.setBclassPrice(null);
        expect.setBclassPriceCurrencyCode(null);
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
        expect.setBundlePurchaseApplicablePrice(null);
        expect.setBundlePurchaseApplicablePriceCurrencyCode(null);
        expect.setSalesAmountTaxIncluded(null);
        expect.setSalesAmountTaxIncludedCurrencyCode(null);
        expect.setSalesAmountTaxIncluded(null);
        expect.setSalesAmountTaxIncludedCurrencyCode(null);
        expect.setStoreItemDiscountSetting(null);
        expect.setStoreItemDiscountCurrencyCode(null);
        expect.setStoreBundleSalePrice(null);
        expect.setStoreBundleSalePriceCurrencyCode(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
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
                AlterationHistorySalesTransactionDetail result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionDetailEntityForInsertAfterEdit(
                                transactionItemDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId,
                                detailCount, itemDetailCount);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
        TransactionItemDetail transactionItemDetail = transactionImportData.getTransactionList()
                .get(0)
                .getTransactionItemDetailList()
                .get(0);
        transactionItemDetail.setBundleSalesFlag(null);
        AlterationHistorySalesTransactionDetail result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionDetailEntityForInsertAfterEdit(
                        transactionItemDetail, userId, salesTransactionId, orderSubNumber,
                        salesTransactionErrorId, originalSalesTransactionErrorId, detailCount,
                        itemDetailCount);

        expect.setStoreBundleSaleFlag(false);
        assertEquals(expect, result);
    }

    /**
     * ConvertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideBeforeEdit() {
        SalesErrorSalesTransactionDetail salesErrorSalesTransactionDetail =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionDetail();

        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId07";
        String userId = "userId07";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 24);
        tableCommonItem.setCreateUserId("user07");
        tableCommonItem.setCreateProgramId("SLS0300107");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user07");
        tableCommonItem.setUpdateProgramId("SLS0300107");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDetail expect = HistoryTableDataConverterTestHelper
                .makeTSalesErrorSalesTransactionDetailForInsertOutsideBefore();

        AlterationHistorySalesTransactionDetail result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideBeforeEdit(
                        salesErrorSalesTransactionDetail, originalSalesTransactionErrorId, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);

    }

    /**
     * ConvertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId08";
        String salesTransactionId = "salesTransactionId08";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId08";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId08";
        String salesTransactionType = "salesTransactionType08";
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 34);
        tableCommonItem.setCreateUserId("user08");
        tableCommonItem.setCreateProgramId("SLS0300108");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user08");
        tableCommonItem.setUpdateProgramId("SLS0300108");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDetail expect = HistoryTableDataConverterTestHelper
                .makeTSalesErrorSalesTransactionDetailForInsertOutsideAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                AlterationHistorySalesTransactionDetail result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideAfterEdit(
                                nonItemDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId,
                                salesTransactionType, detailCount, itemDetailCount);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId08";
        String salesTransactionId = "salesTransactionId08";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId08";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId08";
        String salesTransactionType = "salesTransactionType08";
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 34);
        tableCommonItem.setCreateUserId("user08");
        tableCommonItem.setCreateProgramId("SLS0300108");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user08");
        tableCommonItem.setUpdateProgramId("SLS0300108");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDetail expect = HistoryTableDataConverterTestHelper
                .makeTSalesErrorSalesTransactionDetailForInsertOutsideAfter();
        expect.setOrderStatusLastUpdateDateTime(null);
        expect.setNewPriceCurrencyCode(null);
        expect.setNewPrice(null);
        expect.setRetailUnitPriceTaxExcluded(null);
        expect.setRetailUnitPriceTaxExcludedCurrencyCode(null);
        expect.setRetailUnitPriceTaxIncluded(null);
        expect.setRetailUnitPriceTaxIncludedCurrencyCode(null);
        expect.setSalesAmountTaxExcluded(null);
        expect.setSalesAmountTaxExcludedCurrencyCode(null);
        expect.setSalesAmountTaxIncluded(null);
        expect.setSalesAmountTaxIncludedCurrencyCode(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setOrderStatusLastUpdateDateTime(null);
                nonItemDetail.setNonItemNewPrice(null);
                nonItemDetail.setNonItemUnitPriceTaxExcluded(null);
                nonItemDetail.setNonItemUnitPriceTaxIncluded(null);
                nonItemDetail.setNonItemSalesAmtTaxExcluded(null);
                nonItemDetail.setNonItemSalesAmtTaxIncluded(null);
                AlterationHistorySalesTransactionDetail result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideAfterEdit(
                                nonItemDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId,
                                salesTransactionType, detailCount, itemDetailCount);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }

    /**
     * TransactionTypeIsNull.
     */
    @Test
    public void testTransactionTypeIsNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId08";
        String salesTransactionId = "salesTransactionId08";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId08";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId08";
        String salesTransactionType = null;
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 34);
        tableCommonItem.setCreateUserId("user08");
        tableCommonItem.setCreateProgramId("SLS0300108");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user08");
        tableCommonItem.setUpdateProgramId("SLS0300108");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        String expect = "c00001";

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                AlterationHistorySalesTransactionDetail result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideAfterEdit(
                                nonItemDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId,
                                salesTransactionType, detailCount, itemDetailCount);

                assertEquals(expect, result.getSalesTransactionType());
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionDetailInfoEntityForInsertBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDetailInfoEntityForInsertBeforeEdit() {
        SalesErrorSalesTransactionDetailInfo salesErrorSalesTransactionDetailInfo =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionDetailInfo();
        String orignialSalesTransactionErrorId = "orignialSalesTransactionErrorId09";
        String userId = "userId09";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 25);
        tableCommonItem.setCreateUserId("user09");
        tableCommonItem.setCreateProgramId("SLS0300109");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user09");
        tableCommonItem.setUpdateProgramId("SLS0300109");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDetailInfo expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDetailInfoBefore();

        AlterationHistorySalesTransactionDetailInfo result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionDetailInfoEntityForInsertBeforeEdit(
                        salesErrorSalesTransactionDetailInfo, orignialSalesTransactionErrorId,
                        userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);

    }

    /**
     * ConvertTAlterationHistorySalesTransactionDetailInfoEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDetailInfoEntityForInsertAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId10";
        String salesTransactionId = "salesTransactionId10";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId10";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId10";
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 35);
        tableCommonItem.setCreateUserId("user10");
        tableCommonItem.setCreateProgramId("SLS0300110");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user10");
        tableCommonItem.setUpdateProgramId("SLS0300110");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDetailInfo expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDetailInfoAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                NonItemInfo nonItemInfo = nonItemDetail.getNonItemInfo();
                AlterationHistorySalesTransactionDetailInfo result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionDetailInfoEntityForInsertAfterEdit(
                                nonItemInfo, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId,
                                detailCount, itemDetailCount);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionDiscountEntityForInsertNonBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDiscountEntityForInsertNonBeforeEdit() {
        SalesErrorSalesTransactionDiscount salesErrorSalesTransactionDiscount =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionDiscount();
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId11";
        String userId = "userId11";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 26);
        tableCommonItem.setCreateUserId("user11");
        tableCommonItem.setCreateProgramId("SLS0300111");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user11");
        tableCommonItem.setUpdateProgramId("SLS0300111");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDiscount expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDiscountForInsertNonBefore();

        AlterationHistorySalesTransactionDiscount result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionDiscountEntityForInsertNonBeforeEdit(
                        salesErrorSalesTransactionDiscount, originalSalesTransactionErrorId,
                        userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);

    }

    /**
     * ConvertTAlterationHistorySalesTransactionDiscountEntityForInsertNonAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDiscountEntityForInsertNonAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId12";
        String salesTransactionId = "salesTransactionId12";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId12";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId12";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 36);
        tableCommonItem.setCreateUserId("user12");
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user12");
        tableCommonItem.setUpdateProgramId("SLS0300112");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDiscount expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDiscountForInsertNonAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                    AlterationHistorySalesTransactionDiscount result = historyTableDataConverter
                            .convertTAlterationHistorySalesTransactionDiscountEntityForInsertNonAfterEdit(
                                    nonItemDiscountDetail, userId, salesTransactionId,
                                    orderSubNumber, salesTransactionErrorId,
                                    originalSalesTransactionErrorId, detailCount,
                                    itemDiscountSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionDiscountEntityForInsertNonAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDiscountEntityForInsertNonAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId12";
        String salesTransactionId = "salesTransactionId12";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId12";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId12";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 36);
        tableCommonItem.setCreateUserId("user12");
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user12");
        tableCommonItem.setUpdateProgramId("SLS0300112");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDiscount expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDiscountForInsertNonAfter();
        expect.setDiscountAmountTaxExcluded(null);
        expect.setDiscountAmountTaxExcludedCurrencyCode(null);
        expect.setDiscountAmountTaxIncluded(null);
        expect.setDiscountAmountTaxIncludedCurrencyCode(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                    nonItemDiscountDetail.setNonItemDiscountAmtTaxExcluded(null);
                    nonItemDiscountDetail.setNonItemDiscountAmtTaxIncluded(null);
                    AlterationHistorySalesTransactionDiscount result = historyTableDataConverter
                            .convertTAlterationHistorySalesTransactionDiscountEntityForInsertNonAfterEdit(
                                    nonItemDiscountDetail, userId, salesTransactionId,
                                    orderSubNumber, salesTransactionErrorId,
                                    originalSalesTransactionErrorId, detailCount,
                                    itemDiscountSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionDiscountEntityForInsertNonAfterEdit_PromotionNo.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDiscountEntityForInsertNonAfterEdit_PromotionNo() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId12";
        String salesTransactionId = "salesTransactionId12";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId12";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId12";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 36);
        tableCommonItem.setCreateUserId("user12");
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user12");
        tableCommonItem.setUpdateProgramId("SLS0300112");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDiscount expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDiscountForInsertNonAfter();
        expect.setPromotionNo("9");

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                    nonItemDiscountDetail.setNonItemPromotionNumber("9");
                    AlterationHistorySalesTransactionDiscount result = historyTableDataConverter
                            .convertTAlterationHistorySalesTransactionDiscountEntityForInsertNonAfterEdit(
                                    nonItemDiscountDetail, userId, salesTransactionId,
                                    orderSubNumber, salesTransactionErrorId,
                                    originalSalesTransactionErrorId, detailCount,
                                    itemDiscountSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTaxEntityForInsertNonBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTaxEntityForInsertNonBeforeEdit() {
        SalesErrorSalesTransactionTax salesErrorSalesTransactionTax =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionTax();

        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId13";
        String userId = "userId13";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 27);
        tableCommonItem.setCreateUserId("user13");
        tableCommonItem.setCreateProgramId("SLS0300113");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user13");
        tableCommonItem.setUpdateProgramId("SLS0300113");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTax expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTaxForInsertNonBefore();

        AlterationHistorySalesTransactionTax result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionTaxEntityForInsertNonBeforeEdit(
                        salesErrorSalesTransactionTax, originalSalesTransactionErrorId, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTaxEntityForInsertNonAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTaxEntityForInsertNonAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId14";
        String salesTransactionId = "salesTransactionId14";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId14";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId14";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 37);
        tableCommonItem.setCreateUserId("user14");
        tableCommonItem.setCreateProgramId("SLS0300114");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user14");
        tableCommonItem.setUpdateProgramId("SLS0300114");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTax expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTaxForInsertNonAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemTaxDetailList().forEach(nonItemTaxDetail -> {

                    AlterationHistorySalesTransactionTax result = historyTableDataConverter
                            .convertTAlterationHistorySalesTransactionTaxEntityForInsertNonAfterEdit(
                                    nonItemTaxDetail, userId, salesTransactionId, orderSubNumber,
                                    salesTransactionErrorId, originalSalesTransactionErrorId,
                                    detailCount, taxSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTaxEntityForInsertNonAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTaxEntityForInsertNonAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId14";
        String salesTransactionId = "salesTransactionId14";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId14";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId14";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 37);
        tableCommonItem.setCreateUserId("user14");
        tableCommonItem.setCreateProgramId("SLS0300114");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user14");
        tableCommonItem.setUpdateProgramId("SLS0300114");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTax expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTaxForInsertNonAfter();
        expect.setTaxAmountCurrencyCode(null);
        expect.setTaxAmountValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemTaxDetailList().forEach(nonItemTaxDetail -> {
                    nonItemTaxDetail.setNonItemTaxAmt(null);

                    AlterationHistorySalesTransactionTax result = historyTableDataConverter
                            .convertTAlterationHistorySalesTransactionTaxEntityForInsertNonAfterEdit(
                                    nonItemTaxDetail, userId, salesTransactionId, orderSubNumber,
                                    salesTransactionErrorId, originalSalesTransactionErrorId,
                                    detailCount, taxSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionDiscountEntityForInsertBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionDiscountEntityForInsertBeforeEdit() {
        SalesErrorSalesTransactionDiscount salesErrorSalesTransactionDiscount =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionDiscount();

        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId15";
        String userId = "userId15";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 28);
        tableCommonItem.setCreateUserId("user15");
        tableCommonItem.setCreateProgramId("SLS0300115");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user15");
        tableCommonItem.setUpdateProgramId("SLS0300115");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDiscount expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDiscountForInsertBefore();

        AlterationHistorySalesTransactionDiscount result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionDiscountEntityForInsertBeforeEdit(
                        salesErrorSalesTransactionDiscount, originalSalesTransactionErrorId,
                        userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConverttAlterationHistorySalesTransactionDiscountEntityForInsertAfterEdit.
     */
    @Test
    public void testConverttAlterationHistorySalesTransactionDiscountEntityForInsertAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId16";
        String salesTransactionId = "salesTransactionId16";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId16";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId16";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 38);
        tableCommonItem.setCreateUserId("user16");
        tableCommonItem.setCreateProgramId("SLS0300116");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user16");
        tableCommonItem.setUpdateProgramId("SLS0300116");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDiscount expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDiscountForInsertAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemDiscountList().forEach(itemDiscount -> {
                    AlterationHistorySalesTransactionDiscount result = historyTableDataConverter
                            .converttAlterationHistorySalesTransactionDiscountEntityForInsertAfterEdit(
                                    itemDiscount, userId, salesTransactionId, orderSubNumber,
                                    salesTransactionErrorId, originalSalesTransactionErrorId,
                                    detailCount, itemDiscountSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConverttAlterationHistorySalesTransactionDiscountEntityForInsertAfterEdit.
     */
    @Test
    public void testConverttAlterationHistorySalesTransactionDiscountEntityForInsertAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId16";
        String salesTransactionId = "salesTransactionId16";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId16";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId16";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 38);
        tableCommonItem.setCreateUserId("user16");
        tableCommonItem.setCreateProgramId("SLS0300116");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user16");
        tableCommonItem.setUpdateProgramId("SLS0300116");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionDiscount expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionDiscountForInsertAfter();
        expect.setDiscountAmountTaxExcluded(null);
        expect.setDiscountAmountTaxExcludedCurrencyCode(null);
        expect.setDiscountAmountTaxIncluded(null);
        expect.setDiscountAmountTaxIncludedCurrencyCode(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemDiscountList().forEach(itemDiscount -> {
                    itemDiscount.setItemDiscountAmtTaxExcluded(null);
                    itemDiscount.setItemDiscountAmtTaxIncluded(null);
                    AlterationHistorySalesTransactionDiscount result = historyTableDataConverter
                            .converttAlterationHistorySalesTransactionDiscountEntityForInsertAfterEdit(
                                    itemDiscount, userId, salesTransactionId, orderSubNumber,
                                    salesTransactionErrorId, originalSalesTransactionErrorId,
                                    detailCount, itemDiscountSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTaxEntityForInsertBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTaxEntityForInsertBeforeEdit() {
        SalesErrorSalesTransactionTax salesErrorSalesTransactionTax =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionTax();
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId17";
        String userId = "userId17";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        tableCommonItem.setCreateUserId("user17");
        tableCommonItem.setCreateProgramId("SLS0300117");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user17");
        tableCommonItem.setUpdateProgramId("SLS0300117");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTax expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTaxForInsertBefore();

        AlterationHistorySalesTransactionTax result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionTaxEntityForInsertBeforeEdit(
                        salesErrorSalesTransactionTax, originalSalesTransactionErrorId, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);

    }

    /**
     * ConverttAlterationHistorySalesTransactionTaxEntityForInsertAfterEdit.
     */
    @Test
    public void testConverttAlterationHistorySalesTransactionTaxEntityForInsertAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId18";
        String salesTransactionId = "salesTransactionId18";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId18";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId18";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 39);
        tableCommonItem.setCreateUserId("user18");
        tableCommonItem.setCreateProgramId("SLS0300118");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user18");
        tableCommonItem.setUpdateProgramId("SLS0300118");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTax expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTaxForInsertAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemTaxDetailList().forEach(itemTaxDetail -> {
                    AlterationHistorySalesTransactionTax result = historyTableDataConverter
                            .converttAlterationHistorySalesTransactionTaxEntityForInsertAfterEdit(
                                    itemTaxDetail, userId, salesTransactionId, orderSubNumber,
                                    salesTransactionErrorId, originalSalesTransactionErrorId,
                                    detailCount, taxSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConverttAlterationHistorySalesTransactionTaxEntityForInsertAfterEdit.
     */
    @Test
    public void testConverttAlterationHistorySalesTransactionTaxEntityForInsertAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId18";
        String salesTransactionId = "salesTransactionId18";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId18";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId18";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 39);
        tableCommonItem.setCreateUserId("user18");
        tableCommonItem.setCreateProgramId("SLS0300118");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user18");
        tableCommonItem.setUpdateProgramId("SLS0300118");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTax expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTaxForInsertAfter();
        expect.setTaxAmountCurrencyCode(null);
        expect.setTaxAmountValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemTaxDetailList().forEach(itemTaxDetail -> {
                    itemTaxDetail.setItemTaxAmt(null);
                    AlterationHistorySalesTransactionTax result = historyTableDataConverter
                            .converttAlterationHistorySalesTransactionTaxEntityForInsertAfterEdit(
                                    itemTaxDetail, userId, salesTransactionId, orderSubNumber,
                                    salesTransactionErrorId, originalSalesTransactionErrorId,
                                    detailCount, taxSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTenderEntityForInsertBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTenderEntityForInsertBeforeEdit() {
        SalesErrorSalesTransactionTender salesErrorSalesTransactionTender =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionTende();
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId19";
        String userId = "userId19";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 30);
        tableCommonItem.setCreateUserId("user19");
        tableCommonItem.setCreateProgramId("SLS0300119");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user19");
        tableCommonItem.setUpdateProgramId("SLS0300119");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTender expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTenderBefore();

        AlterationHistorySalesTransactionTender result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionTenderEntityForInsertBeforeEdit(
                        salesErrorSalesTransactionTender, originalSalesTransactionErrorId, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTenderEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTenderEntityForInsertAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId20";
        String salesTransactionId = "salesTransactionId20";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId20";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId20";
        Integer tenderSubNumber = 301;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 40);
        tableCommonItem.setCreateUserId("user20");
        tableCommonItem.setCreateProgramId("SLS0300120");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user20");
        tableCommonItem.setUpdateProgramId("SLS0300120");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTender expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTenderAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(salesTransactionTender -> {
                AlterationHistorySalesTransactionTender result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionTenderEntityForInsertAfterEdit(
                                salesTransactionTender, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId,
                                tenderSubNumber);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTenderEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTenderEntityForInsertAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId20";
        String salesTransactionId = "salesTransactionId20";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId20";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId20";
        Integer tenderSubNumber = 301;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 40);
        tableCommonItem.setCreateUserId("user20");
        tableCommonItem.setCreateProgramId("SLS0300120");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user20");
        tableCommonItem.setUpdateProgramId("SLS0300120");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTender expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTenderAfter();
        expect.setTaxIncludedPaymentAmountCurrencyCode(null);
        expect.setTaxIncludedPaymentAmountValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(salesTransactionTender -> {
                salesTransactionTender.setTaxIncludedPaymentAmount(null);
                AlterationHistorySalesTransactionTender result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionTenderEntityForInsertAfterEdit(
                                salesTransactionTender, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId,
                                tenderSubNumber);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTenderInfoEntityForInsertBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTenderInfoEntityForInsertBeforeEdit() {
        SalesErrorSalesTransactionTenderInfo salesErrorSalesTransactionTenderInfo =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionTenderInfo();
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId21";
        String userId = "userId21";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 31);
        tableCommonItem.setCreateUserId("user21");
        tableCommonItem.setCreateProgramId("SLS0300121");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user21");
        tableCommonItem.setUpdateProgramId("SLS0300121");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTenderInfo expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTenderInfoBefore();

        AlterationHistorySalesTransactionTenderInfo result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionTenderInfoEntityForInsertBeforeEdit(
                        salesErrorSalesTransactionTenderInfo, originalSalesTransactionErrorId,
                        userId, 1);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTenderInfoEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTenderInfoEntityForInsertAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId22";
        String salesTransactionId = "salesTransactionId22";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId22";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId22";
        String tenderId = "301";
        String tenderGroupId = "tenderGroupId22";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 41);
        tableCommonItem.setCreateUserId("user22");
        tableCommonItem.setCreateProgramId("SLS0300122");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user22");
        tableCommonItem.setUpdateProgramId("SLS0300122");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTenderInfo expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTenderInfoAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(salesTransactionTender -> {
                AlterationHistorySalesTransactionTenderInfo result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionTenderInfoEntityForInsertAfterEdit(
                                salesTransactionTender.getTenderInfoList(), userId,
                                salesTransactionId, orderSubNumber, salesTransactionErrorId,
                                originalSalesTransactionErrorId, tenderId, tenderGroupId, 1);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTenderInfoEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTenderInfoEntityForInsertAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId22";
        String salesTransactionId = "salesTransactionId22";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId22";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId22";
        String tenderId = "301";
        String tenderGroupId = "tenderGroupId22";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 41);
        tableCommonItem.setCreateUserId("user22");
        tableCommonItem.setCreateProgramId("SLS0300122");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user22");
        tableCommonItem.setUpdateProgramId("SLS0300122");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTenderInfo expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTenderInfoAfter();
        expect.setDiscountValue(null);
        expect.setDiscountValueCurrencyCode(null);
        expect.setCouponMinUsageAmountThresholdValue(null);
        expect.setCouponMinUsageAmountThresholdCurrencyCode(null);
        expect.setCouponDiscountAmountSettingValue(null);
        expect.setCouponDiscountAmountSettingCurrencyCode(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(salesTransactionTender -> {
                salesTransactionTender.getTenderInfoList().setDiscountAmount(null);
                salesTransactionTender.getTenderInfoList().setCouponMinUsageAmountThreshold(null);
                salesTransactionTender.getTenderInfoList().setCouponDiscountAmountSetting(null);

                AlterationHistorySalesTransactionTenderInfo result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionTenderInfoEntityForInsertAfterEdit(
                                salesTransactionTender.getTenderInfoList(), userId,
                                salesTransactionId, orderSubNumber, salesTransactionErrorId,
                                originalSalesTransactionErrorId, tenderId, tenderGroupId, 1);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionBeforeEdit() {
        SalesErrorSalesTransactionTax salesErrorSalesTransactionTax =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionTax();
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId23";
        String userId = "userId23";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("user23");
        tableCommonItem.setCreateProgramId("SLS0300123");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user23");
        tableCommonItem.setUpdateProgramId("SLS0300123");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTax expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTaxTransactionBefore();

        AlterationHistorySalesTransactionTax result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionBeforeEdit(
                        salesErrorSalesTransactionTax, originalSalesTransactionErrorId, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId24";
        String salesTransactionId = "salesTransactionId24";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId24";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId24";
        Integer detailCount = 301;
        Integer taxSubNumber = 401;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 42);
        tableCommonItem.setCreateUserId("user24");
        tableCommonItem.setCreateProgramId("SLS0300124");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user24");
        tableCommonItem.setUpdateProgramId("SLS0300124");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTax expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTaxTransactionAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTaxDetailList().forEach(salesTransactionTaxDetail -> {
                AlterationHistorySalesTransactionTax result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionAfterEdit(
                                salesTransactionTaxDetail, userId, salesTransactionId,
                                orderSubNumber, salesTransactionErrorId,
                                originalSalesTransactionErrorId, detailCount, taxSubNumber);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId24";
        String salesTransactionId = "salesTransactionId24";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId24";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId24";
        Integer detailCount = 301;
        Integer taxSubNumber = 401;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 42);
        tableCommonItem.setCreateUserId("user24");
        tableCommonItem.setCreateProgramId("SLS0300124");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user24");
        tableCommonItem.setUpdateProgramId("SLS0300124");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTax expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTaxTransactionAfter();
        expect.setTaxAmountValue(null);
        expect.setTaxAmountCurrencyCode(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTaxDetailList().forEach(salesTransactionTaxDetail -> {
                salesTransactionTaxDetail.setTaxAmount(null);
                AlterationHistorySalesTransactionTax result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionAfterEdit(
                                salesTransactionTaxDetail, userId, salesTransactionId,
                                orderSubNumber, salesTransactionErrorId,
                                originalSalesTransactionErrorId, detailCount, taxSubNumber);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTotalAmountEntityForInsertBeforeEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTotalAmountEntityForInsertBeforeEdit() {
        SalesErrorSalesTransactionTotalAmount salesErrorSalesTransactionTotalAmount =
                HistoryTableDataConverterTestHelper.makeTSalesErrorSalesTransactionTotalAmount();
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId25";
        String userId = "userId25";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 33);
        tableCommonItem.setCreateUserId("user25");
        tableCommonItem.setCreateProgramId("SLS0300125");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user25");
        tableCommonItem.setUpdateProgramId("SLS0300125");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTotalAmount expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTotalAmountBefore();

        AlterationHistorySalesTransactionTotalAmount result = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionTotalAmountEntityForInsertBeforeEdit(
                        salesErrorSalesTransactionTotalAmount, originalSalesTransactionErrorId,
                        userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);

    }

    /**
     * ConvertTAlterationHistorySalesTransactionTotalAmountEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTotalAmountEntityForInsertAfterEdit() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId26";
        String salesTransactionId = "salesTransactionId26";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId26";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId26";
        Integer totalAmountSubNumber = 301;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 43);
        tableCommonItem.setCreateUserId("user26");
        tableCommonItem.setCreateProgramId("SLS0300126");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user26");
        tableCommonItem.setUpdateProgramId("SLS0300126");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTotalAmount expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTotalAmountAfter();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTotalList().forEach(salesTransactionTotal -> {
                AlterationHistorySalesTransactionTotalAmount result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionTotalAmountEntityForInsertAfterEdit(
                                salesTransactionTotal, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId,
                                totalAmountSubNumber);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    /**
     * ConvertTAlterationHistorySalesTransactionTotalAmountEntityForInsertAfterEdit.
     */
    @Test
    public void testConvertTAlterationHistorySalesTransactionTotalAmountEntityForInsertAfterEditNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId26";
        String salesTransactionId = "salesTransactionId26";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId26";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId26";
        Integer totalAmountSubNumber = 301;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 43);
        tableCommonItem.setCreateUserId("user26");
        tableCommonItem.setCreateProgramId("SLS0300126");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user26");
        tableCommonItem.setUpdateProgramId("SLS0300126");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        AlterationHistorySalesTransactionTotalAmount expect = HistoryTableDataConverterTestHelper
                .makeTAlterationHistorySalesTransactionTotalAmountAfter();
        expect.setTotalAmountTaxExcludedValue(null);
        expect.setTotalAmountTaxExcludedCurrencyCode(null);
        expect.setTotalAmountTaxIncludedValue(null);
        expect.setTotalAmountTaxIncludedCurrencyCode(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTotalList().forEach(salesTransactionTotal -> {
                salesTransactionTotal.setTotalAmountTaxExcluded(null);
                salesTransactionTotal.setTotalAmountTaxIncluded(null);
                AlterationHistorySalesTransactionTotalAmount result = historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionTotalAmountEntityForInsertAfterEdit(
                                salesTransactionTotal, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId,
                                totalAmountSubNumber);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

}
