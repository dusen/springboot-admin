package com.fastretailing.dcp.sales.importtransaction.converter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
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
public class ErrorTableDataConverterTest {

    @MockBean
    private CommonDataProcessor commonDataProcessor;

    @Autowired
    private ErrorTableDataConverter errorTableDataConverter;

    @Test
    public void testConvertTErrorSalesOrderInformationEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();
        String userId = "user01";
        String salesTransactionErrorId = "salesTransactionErrorId";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user01");
        tableCommonItem.setCreateProgramId("SLS0300101");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesOrderInformation expect =
                ErrorTableDataConverterTestHelper.makeTErrorSalesOrderInformationEntity();

        SalesErrorSalesOrderInformation result =
                errorTableDataConverter.convertTSalesErrorOrderInformationEntityForInsert(
                        transactionImportData, userId, salesTransactionErrorId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    @Test
    public void testConvertTErrorSalesOrderInformationEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();
        String userId = "user01";
        String salesTransactionErrorId = "salesTransactionErrorId";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user01");
        tableCommonItem.setCreateProgramId("SLS0300101");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesOrderInformation expect =
                ErrorTableDataConverterTestHelper.makeTErrorSalesOrderInformationEntity();
        transactionImportData.setOrderConfirmationDateTime(null);
        transactionImportData.setDataCorrectionEditingFlag(null);
        expect.setOrderConfirmationDateTime(null);
        expect.setDataAlterationEditingFlag(false);
        SalesErrorSalesOrderInformation result =
                errorTableDataConverter.convertTSalesErrorOrderInformationEntityForInsert(
                        transactionImportData, userId, salesTransactionErrorId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    @Test
    public void testConvertTErrorSalesHeaderEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();
        String userId = "user01";
        String salesTransactionErrorId = "salesTransactionErrorId";
        int transactionCount = 001;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user01");
        tableCommonItem.setCreateProgramId("SLS0300101");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionHeader expect =
                ErrorTableDataConverterTestHelper.makeTSalesErrorSalesTransactionHeaderEntity();
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
        expect.setSalesTransactionDiscountAmountRateCurrencyCode(null);
        expect.setSalesTransactionDiscountAmountRate(null);

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
            SalesErrorSalesTransactionHeader result =
                    errorTableDataConverter.convertTSalesErrorSalesTransactionHeaderEntityForInsert(
                            transaction, userId, salesTransactionErrorId, transactionCount);

            System.out.println(result);
            System.out.println(expect);
            assertEquals(expect, result);
        });
    }

    @Test
    public void testConvertTErrorSalesHeaderEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();
        String userId = "user01";
        String salesTransactionErrorId = "salesTransactionErrorId";
        int transactionCount = 001;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user01");
        tableCommonItem.setCreateProgramId("SLS0300101");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionHeader expect =
                ErrorTableDataConverterTestHelper.makeTSalesErrorSalesTransactionHeaderEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            SalesErrorSalesTransactionHeader result =
                    errorTableDataConverter.convertTSalesErrorSalesTransactionHeaderEntityForInsert(
                            transaction, userId, salesTransactionErrorId, transactionCount);

            System.out.println(result);
            System.out.println(expect);
            assertEquals(expect, result);
        });
    }

    @Test
    public void testConvertTErrorSalesTransactionDetailEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();
        String userId = "user01";
        String salesTransactionId = "salesTransactionId01";
        int orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError01";
        int detailCount = 301;
        int itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user01");
        tableCommonItem.setCreateProgramId("SLS0300101");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionDetail expect =
                ErrorTableDataConverterTestHelper.makeTSalesErrorSalesTransactionDetailEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                SalesErrorSalesTransactionDetail result = errorTableDataConverter
                        .convertTSalesErrorSalesTransactionDetailEntityForInsert(
                                transactionItemDetail, salesTransactionId, orderSubNumber, userId,
                                salesTransactionErrorId, detailCount, itemDetailCount);

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
        SalesErrorSalesTransactionDetail result =
                errorTableDataConverter.convertTSalesErrorSalesTransactionDetailEntityForInsert(
                        transactionItemDetail, salesTransactionId, orderSubNumber, userId,
                        salesTransactionErrorId, detailCount, itemDetailCount);
        expect.setStoreBundleSaleFlag(false);
        assertEquals(expect, result);
    }

    @Test
    public void testConvertTErrorSalesTransactionDetailEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();
        String userId = "user01";
        String salesTransactionId = "salesTransactionId01";
        int orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError01";
        int detailCount = 301;
        int itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user01");
        tableCommonItem.setCreateProgramId("SLS0300101");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionDetail expect =
                ErrorTableDataConverterTestHelper.makeTSalesErrorSalesTransactionDetailEntity();
        expect.setOrderStatusLastUpdateDateTime(null);
        expect.setMajorCategoryCode(null);
        expect.setDetailQuantity(null);
        expect.setBundlePurchaseApplicableQuantity(null);
        expect.setOrderStatusLastUpdateDateTime(null);
        expect.setItemCostCurrencyCode(null);
        expect.setItemCostValue(null);
        expect.setInitialSellingPriceCurrencyCode(null);
        expect.setInitialSellingPrice(null);
        expect.setBclassPriceCurrencyCode(null);
        expect.setBclassPrice(null);
        expect.setNewPriceCurrencyCode(null);
        expect.setNewPrice(null);
        expect.setRetailUnitPriceTaxExcludedCurrencyCode(null);
        expect.setRetailUnitPriceTaxExcluded(null);
        expect.setRetailUnitPriceTaxIncludedCurrencyCode(null);
        expect.setRetailUnitPriceTaxIncluded(null);
        expect.setSalesAmountTaxExcludedCurrencyCode(null);
        expect.setSalesAmountTaxExcluded(null);
        expect.setSalesAmountTaxIncludedCurrencyCode(null);
        expect.setSalesAmountTaxIncluded(null);
        expect.setBundlePurchaseApplicablePriceCurrencyCode(null);
        expect.setBundlePurchaseApplicablePrice(null);
        expect.setStoreItemDiscountCurrencyCode(null);
        expect.setStoreItemDiscountSetting(null);
        expect.setStoreBundleSalePriceCurrencyCode(null);
        expect.setStoreBundleSalePrice(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.setOrderStatusLastUpdateDateTime(null);
                transactionItemDetail.setDeptCode(null);
                transactionItemDetail.setItemQty(null);
                transactionItemDetail.setBundlePurchaseQty(null);
                transactionItemDetail.setOrderStatusLastUpdateDateTime(null);
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
                SalesErrorSalesTransactionDetail result = errorTableDataConverter
                        .convertTSalesErrorSalesTransactionDetailEntityForInsert(
                                transactionItemDetail, salesTransactionId, orderSubNumber, userId,
                                salesTransactionErrorId, detailCount, itemDetailCount);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }

    @Test
    public void testConvertTErrorSalesTransactionDetailEntityForInsertOutside() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();
        String userId = "user04";
        String salesTransactionId = "salesTransactionError04";
        int orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionId04";
        String salesTransactionType = "salesTransactionType04";
        int detailCount = 301;
        int itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user04");
        tableCommonItem.setCreateProgramId("SLS0300104");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user04");
        tableCommonItem.setUpdateProgramId("SLS0300104");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionDetail expect =
                ErrorTableDataConverterTestHelper.makeNonItemDetailEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                SalesErrorSalesTransactionDetail result = errorTableDataConverter
                        .convertTSalesErrorSalesTransactionDetailEntityForInsertOutside(
                                nonItemDetail, salesTransactionId, orderSubNumber, userId,
                                salesTransactionErrorId, salesTransactionType, detailCount,
                                itemDetailCount);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });

    }

    @Test
    public void testConvertTErrorSalesTransactionDetailEntityForInsertOutsideNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();
        String userId = "user04";
        String salesTransactionId = "salesTransactionError04";
        int orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionId04";
        String salesTransactionType = null;
        int detailCount = 301;
        int itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user04");
        tableCommonItem.setCreateProgramId("SLS0300104");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user04");
        tableCommonItem.setUpdateProgramId("SLS0300104");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionDetail expect =
                ErrorTableDataConverterTestHelper.makeNonItemDetailEntity();
        expect.setOrderStatusLastUpdateDateTime(null);
        expect.setDetailQuantity(null);
        expect.setNewPriceCurrencyCode(null);
        expect.setNewPrice(null);
        expect.setRetailUnitPriceTaxExcludedCurrencyCode(null);
        expect.setRetailUnitPriceTaxExcluded(null);
        expect.setRetailUnitPriceTaxIncludedCurrencyCode(null);
        expect.setRetailUnitPriceTaxIncluded(null);
        expect.setSalesAmountTaxExcludedCurrencyCode(null);
        expect.setSalesAmountTaxExcluded(null);
        expect.setSalesAmountTaxIncludedCurrencyCode(null);
        expect.setSalesAmountTaxIncluded(null);
        expect.setSalesTransactionType(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setOrderStatusLastUpdateDateTime(null);
                nonItemDetail.setNonItemQty(null);
                nonItemDetail.setNonItemNewPrice(null);
                nonItemDetail.setNonItemUnitPriceTaxExcluded(null);
                nonItemDetail.setNonItemUnitPriceTaxIncluded(null);
                nonItemDetail.setNonItemSalesAmtTaxExcluded(null);
                nonItemDetail.setNonItemUnitPriceTaxIncluded(null);
                nonItemDetail.setNonItemSalesAmtTaxIncluded(null);
                SalesErrorSalesTransactionDetail result = errorTableDataConverter
                        .convertTSalesErrorSalesTransactionDetailEntityForInsertOutside(
                                nonItemDetail, salesTransactionId, orderSubNumber, userId,
                                salesTransactionErrorId, salesTransactionType, detailCount,
                                itemDetailCount);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });

    }

    @Test
    public void testConvertTErrorSalesTransactionDetailInfoEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user05";
        String salesTransactionId = "salesTransactionId05";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId05";
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user05");
        tableCommonItem.setCreateProgramId("SLS0300105");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user05");
        tableCommonItem.setUpdateProgramId("SLS0300105");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionDetailInfo expect =
                ErrorTableDataConverterTestHelper.makeNonItemInfoEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                NonItemInfo nonItemInfo = nonItemDetail.getNonItemInfo();
                SalesErrorSalesTransactionDetailInfo result = errorTableDataConverter
                        .convertTSalesErrorSalesTransactionDetailInfoEntityForInsert(nonItemInfo,
                                salesTransactionId, orderSubNumber, userId, salesTransactionErrorId,
                                detailCount, itemDetailCount);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });

    }

    @Test
    public void testConvertTErrorSalesTransactionDiscountEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user06";
        String salesTransactionId = "salesTransactionId06";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError06";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user06");
        tableCommonItem.setCreateProgramId("SLS0300106");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user06");
        tableCommonItem.setUpdateProgramId("SLS0300106");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionDiscount expect =
                ErrorTableDataConverterTestHelper.makeItemDiscountEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemDiscountList().forEach(itemDiscount -> {
                    SalesErrorSalesTransactionDiscount result = errorTableDataConverter
                            .convertTSalesErrorSalesTransactionDiscountEntityForInsert(itemDiscount,
                                    salesTransactionId, orderSubNumber, userId,
                                    salesTransactionErrorId, detailCount, itemDiscountSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    @Test
    public void testConvertTErrorSalesTransactionDiscountEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user06";
        String salesTransactionId = "salesTransactionId06";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError06";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user06");
        tableCommonItem.setCreateProgramId("SLS0300106");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user06");
        tableCommonItem.setUpdateProgramId("SLS0300106");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionDiscount expect =
                ErrorTableDataConverterTestHelper.makeItemDiscountEntity();
        expect.setDiscountAmountTaxExcludedCurrencyCode(null);
        expect.setDiscountAmountTaxExcluded(null);
        expect.setDiscountAmountTaxIncludedCurrencyCode(null);
        expect.setDiscountAmountTaxIncluded(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemDiscountList().forEach(itemDiscount -> {
                    itemDiscount.setItemDiscountAmtTaxExcluded(null);
                    itemDiscount.setItemDiscountAmtTaxIncluded(null);
                    SalesErrorSalesTransactionDiscount result = errorTableDataConverter
                            .convertTSalesErrorSalesTransactionDiscountEntityForInsert(itemDiscount,
                                    salesTransactionId, orderSubNumber, userId,
                                    salesTransactionErrorId, detailCount, itemDiscountSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    @Test
    public void testConvertTErrorSalesTransactionDiscountEntityForInsertNon() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user06";
        String salesTransactionId = "salesTransactionId06";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError06";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user06");
        tableCommonItem.setCreateProgramId("SLS0300106");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user06");
        tableCommonItem.setUpdateProgramId("SLS0300106");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionDiscount expect =
                ErrorTableDataConverterTestHelper.makeNonItemDiscountDetailEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                    SalesErrorSalesTransactionDiscount result = errorTableDataConverter
                            .convertTSalesErrorSalesTransactionDiscountEntityForInsertNon(
                                    nonItemDiscountDetail, salesTransactionId, orderSubNumber,
                                    userId, salesTransactionErrorId, detailCount,
                                    itemDiscountSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });

    }

    @Test
    public void testConvertTErrorSalesTransactionDiscountEntityForInsertNonNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user06";
        String salesTransactionId = "salesTransactionId06";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError06";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user06");
        tableCommonItem.setCreateProgramId("SLS0300106");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user06");
        tableCommonItem.setUpdateProgramId("SLS0300106");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionDiscount expect =
                ErrorTableDataConverterTestHelper.makeNonItemDiscountDetailEntity();
        expect.setDiscountAmountTaxExcludedCurrencyCode(null);
        expect.setDiscountAmountTaxExcluded(null);
        expect.setDiscountAmountTaxIncludedCurrencyCode(null);
        expect.setDiscountAmountTaxIncluded(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                    nonItemDiscountDetail.setNonItemDiscountAmtTaxExcluded(null);
                    nonItemDiscountDetail.setNonItemDiscountAmtTaxIncluded(null);
                    SalesErrorSalesTransactionDiscount result = errorTableDataConverter
                            .convertTSalesErrorSalesTransactionDiscountEntityForInsertNon(
                                    nonItemDiscountDetail, salesTransactionId, orderSubNumber,
                                    userId, salesTransactionErrorId, detailCount,
                                    itemDiscountSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });

    }

    @Test
    public void testConvertTErrorSalesTransactionDiscountEntityForInsertNon_PromotionNo() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user06";
        String salesTransactionId = "salesTransactionId06";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError06";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user06");
        tableCommonItem.setCreateProgramId("SLS0300106");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user06");
        tableCommonItem.setUpdateProgramId("SLS0300106");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionDiscount expect =
                ErrorTableDataConverterTestHelper.makeNonItemDiscountDetailEntity();
        expect.setPromotionNo("9");

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                    nonItemDiscountDetail.setNonItemPromotionNumber("9");
                    SalesErrorSalesTransactionDiscount result = errorTableDataConverter
                            .convertTSalesErrorSalesTransactionDiscountEntityForInsertNon(
                                    nonItemDiscountDetail, salesTransactionId, orderSubNumber,
                                    userId, salesTransactionErrorId, detailCount,
                                    itemDiscountSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });

    }

    @Test
    public void testConvertTErrorSalesTransactionTaxEntityForInsertNon() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user08";
        String salesTransactionId = "salesTransactionId08";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError08";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user08");
        tableCommonItem.setCreateProgramId("SLS0300108");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user08");
        tableCommonItem.setUpdateProgramId("SLS0300108");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTax expect =
                ErrorTableDataConverterTestHelper.makeNonItemTaxDetailEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemTaxDetailList().forEach(nonItemTaxDetail -> {

                    SalesErrorSalesTransactionTax result = errorTableDataConverter
                            .convertTSalesErrorSalesTransactionTaxEntityForInsertNon(
                                    nonItemTaxDetail, salesTransactionId, orderSubNumber, userId,
                                    salesTransactionErrorId, detailCount, taxSubNumber);
                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    @Test
    public void testConvertTErrorSalesTransactionTaxEntityForInsertNonNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user08";
        String salesTransactionId = "salesTransactionId08";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError08";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user08");
        tableCommonItem.setCreateProgramId("SLS0300108");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user08");
        tableCommonItem.setUpdateProgramId("SLS0300108");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTax expect =
                ErrorTableDataConverterTestHelper.makeNonItemTaxDetailEntity();
        expect.setTaxAmountCurrencyCode(null);
        expect.setTaxAmountValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemTaxDetailList().forEach(nonItemTaxDetail -> {
                    nonItemTaxDetail.setNonItemTaxAmt(null);
                    SalesErrorSalesTransactionTax result = errorTableDataConverter
                            .convertTSalesErrorSalesTransactionTaxEntityForInsertNon(
                                    nonItemTaxDetail, salesTransactionId, orderSubNumber, userId,
                                    salesTransactionErrorId, detailCount, taxSubNumber);
                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    @Test
    public void testConvertTErrorSalesTransactionTaxEntityForInser() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user09";
        String salesTransactionId = "salesTransactionId09";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError09";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user09");
        tableCommonItem.setCreateProgramId("SLS0300109");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user09");
        tableCommonItem.setUpdateProgramId("SLS0300109");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTax expect =
                ErrorTableDataConverterTestHelper.makeTSalesTransactionTaxEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemTaxDetailList().forEach(itemTaxDetail -> {
                    SalesErrorSalesTransactionTax result = errorTableDataConverter
                            .convertTSalesErrorSalesTransactionTaxEntityForInsert(itemTaxDetail,
                                    salesTransactionId, orderSubNumber, userId,
                                    salesTransactionErrorId, detailCount, taxSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });

    }

    @Test
    public void testConvertTErrorSalesTransactionTaxEntityForInserNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user09";
        String salesTransactionId = "salesTransactionId09";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError09";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user09");
        tableCommonItem.setCreateProgramId("SLS0300109");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user09");
        tableCommonItem.setUpdateProgramId("SLS0300109");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTax expect =
                ErrorTableDataConverterTestHelper.makeTSalesTransactionTaxEntity();
        expect.setTaxAmountCurrencyCode(null);
        expect.setTaxAmountValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemTaxDetailList().forEach(itemTaxDetail -> {
                    itemTaxDetail.setItemTaxAmt(null);
                    SalesErrorSalesTransactionTax result = errorTableDataConverter
                            .convertTSalesErrorSalesTransactionTaxEntityForInsert(itemTaxDetail,
                                    salesTransactionId, orderSubNumber, userId,
                                    salesTransactionErrorId, detailCount, taxSubNumber);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });

    }

    @Test
    public void testConvertTErrorSalesTransactionTenderEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user10";
        String salesTransactionId = "salesTransactionId10";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId10";
        Integer tenderSubNumber = 301;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user10");
        tableCommonItem.setCreateProgramId("SLS0300110");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user10");
        tableCommonItem.setUpdateProgramId("SLS0300110");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTender expect =
                ErrorTableDataConverterTestHelper.makeTenderEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(salesTransactionTender -> {
                SalesErrorSalesTransactionTender result = errorTableDataConverter
                        .convertTSalesErrorSalesTransactionTenderEntityForInsert(
                                salesTransactionTender, salesTransactionId, orderSubNumber, userId,
                                salesTransactionErrorId, tenderSubNumber);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    @Test
    public void testConvertTErrorSalesTransactionTenderEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user10";
        String salesTransactionId = "salesTransactionId10";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId10";
        Integer tenderSubNumber = 301;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user10");
        tableCommonItem.setCreateProgramId("SLS0300110");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user10");
        tableCommonItem.setUpdateProgramId("SLS0300110");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTender expect =
                ErrorTableDataConverterTestHelper.makeTenderEntity();
        expect.setTaxIncludedPaymentAmountCurrencyCode(null);
        expect.setTaxIncludedPaymentAmountValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(salesTransactionTender -> {
                salesTransactionTender.setTaxIncludedPaymentAmount(null);
                SalesErrorSalesTransactionTender result = errorTableDataConverter
                        .convertTSalesErrorSalesTransactionTenderEntityForInsert(
                                salesTransactionTender, salesTransactionId, orderSubNumber, userId,
                                salesTransactionErrorId, tenderSubNumber);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    @Test
    public void testConvertTErrorSalesTransactionTenderInfoEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user11";
        String salesTransactionId = "salesTransactionId11";
        Integer orderSubNumber = 201;
        String tenderGroupId = "tenderGroup11";
        String tenderId = "301";
        String salesTransactionErrorId = "salesTransactionError11";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user11");
        tableCommonItem.setCreateProgramId("SLS0300111");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user11");
        tableCommonItem.setUpdateProgramId("SLS0300111");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTenderInfo expect =
                ErrorTableDataConverterTestHelper.makeTenderInfoEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(salesTransactionTender -> {
                SalesErrorSalesTransactionTenderInfo result = errorTableDataConverter
                        .convertTSalesErrorSalesTransactionTenderInfoEntityForInsert(
                                salesTransactionTender.getTenderInfoList(), salesTransactionId,
                                orderSubNumber, tenderGroupId, tenderId, userId,
                                salesTransactionErrorId, 1);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });

    }

    @Test
    public void testConvertTErrorSalesTransactionTenderInfoEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user11";
        String salesTransactionId = "salesTransactionId11";
        Integer orderSubNumber = 201;
        String tenderGroupId = "tenderGroup11";
        String tenderId = "301";
        String salesTransactionErrorId = "salesTransactionError11";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user11");
        tableCommonItem.setCreateProgramId("SLS0300111");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user11");
        tableCommonItem.setUpdateProgramId("SLS0300111");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTenderInfo expect =
                ErrorTableDataConverterTestHelper.makeTenderInfoEntity();
        expect.setDiscountValueCurrencyCode(null);
        expect.setDiscountValue(null);
        expect.setCouponMinUsageAmountThresholdCurrencyCode(null);
        expect.setCouponMinUsageAmountThresholdValue(null);
        expect.setCouponDiscountAmountSettingCurrencyCode(null);
        expect.setCouponDiscountAmountSettingValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(salesTransactionTender -> {
                salesTransactionTender.getTenderInfoList().setDiscountAmount(null);
                salesTransactionTender.getTenderInfoList().setCouponMinUsageAmountThreshold(null);
                salesTransactionTender.getTenderInfoList().setCouponDiscountAmountSetting(null);
                SalesErrorSalesTransactionTenderInfo result = errorTableDataConverter
                        .convertTSalesErrorSalesTransactionTenderInfoEntityForInsert(
                                salesTransactionTender.getTenderInfoList(), salesTransactionId,
                                orderSubNumber, tenderGroupId, tenderId, userId,
                                salesTransactionErrorId, 1);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });

    }

    @Test
    public void testConvertTErrorSalesTransactionTaxEntityForInsertTransaction() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user12";
        String salesTransactionId = "salesTransactionId12";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError12";
        Integer detailCount = 301;
        Integer taxSubNumber = 401;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user12");
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user12");
        tableCommonItem.setUpdateProgramId("SLS0300112");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTax expect =
                ErrorTableDataConverterTestHelper.makeTaxDetailOfTransactionEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTaxDetailList()
                    .forEach(salesTransactionTaxDetailList -> {
                        SalesErrorSalesTransactionTax result = errorTableDataConverter
                                .convertTSalesErrorSalesTransactionTaxEntityForInsertTransaction(
                                        salesTransactionTaxDetailList, salesTransactionId,
                                        orderSubNumber, userId, salesTransactionErrorId,
                                        detailCount, taxSubNumber);
                        System.out.println(result);
                        System.out.println(expect);
                        assertEquals(expect, result);

                    });
        });
    }

    @Test
    public void testConvertTErrorSalesTransactionTaxEntityForInsertTransactionNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user12";
        String salesTransactionId = "salesTransactionId12";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError12";
        Integer detailCount = 301;
        Integer taxSubNumber = 401;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user12");
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user12");
        tableCommonItem.setUpdateProgramId("SLS0300112");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTax expect =
                ErrorTableDataConverterTestHelper.makeTaxDetailOfTransactionEntity();
        expect.setTaxAmountCurrencyCode(null);
        expect.setTaxAmountValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTaxDetailList()
                    .forEach(salesTransactionTaxDetailList -> {
                        salesTransactionTaxDetailList.setTaxAmount(null);
                        SalesErrorSalesTransactionTax result = errorTableDataConverter
                                .convertTSalesErrorSalesTransactionTaxEntityForInsertTransaction(
                                        salesTransactionTaxDetailList, salesTransactionId,
                                        orderSubNumber, userId, salesTransactionErrorId,
                                        detailCount, taxSubNumber);
                        System.out.println(result);
                        System.out.println(expect);
                        assertEquals(expect, result);

                    });
        });
    }

    @Test
    public void testConvertTErrorSalesTransactionTotalAmountEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user13";
        String salesTransactionId = "salesTransactionId13";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError13";
        Integer totalAmountSubNumber = 301;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user13");
        tableCommonItem.setCreateProgramId("SLS0300113");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user13");
        tableCommonItem.setUpdateProgramId("SLS0300113");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTotalAmount expect = ErrorTableDataConverterTestHelper
                .makeTSalesErrorSalesTransactionTotalAmountEntity();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTotalList().forEach(salesTransactionTotal -> {
                SalesErrorSalesTransactionTotalAmount result = errorTableDataConverter
                        .convertTSalesErrorSalesTransactionTotalAmountEntityForInsert(
                                salesTransactionTotal, salesTransactionId, orderSubNumber, userId,
                                salesTransactionErrorId, totalAmountSubNumber);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });

    }

    @Test
    public void testConvertTErrorSalesTransactionTotalAmountEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorTableDataConverterTestHelper.makeTransactionImportData();

        String userId = "user13";
        String salesTransactionId = "salesTransactionId13";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionError13";
        Integer totalAmountSubNumber = 301;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user13");
        tableCommonItem.setCreateProgramId("SLS0300113");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user13");
        tableCommonItem.setUpdateProgramId("SLS0300113");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesErrorSalesTransactionTotalAmount expect = ErrorTableDataConverterTestHelper
                .makeTSalesErrorSalesTransactionTotalAmountEntity();
        expect.setTotalAmountTaxExcludedCurrencyCode(null);
        expect.setTotalAmountTaxExcludedValue(null);
        expect.setTotalAmountTaxIncludedCurrencyCode(null);
        expect.setTotalAmountTaxIncludedValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTotalList().forEach(salesTransactionTotal -> {
                salesTransactionTotal.setTotalAmountTaxExcluded(null);
                salesTransactionTotal.setTotalAmountTaxIncluded(null);
                SalesErrorSalesTransactionTotalAmount result = errorTableDataConverter
                        .convertTSalesErrorSalesTransactionTotalAmountEntityForInsert(
                                salesTransactionTotal, salesTransactionId, orderSubNumber, userId,
                                salesTransactionErrorId, totalAmountSubNumber);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });

    }

}
