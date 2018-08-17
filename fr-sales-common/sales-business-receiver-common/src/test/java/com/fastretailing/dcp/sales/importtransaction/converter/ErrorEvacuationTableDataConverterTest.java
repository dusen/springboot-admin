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
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTotalAmount;

/**
 * ErrorEvacuationTableDataConverter JUnit Test class.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ErrorEvacuationTableDataConverterTest {

    @Autowired
    private ErrorEvacuationTableDataConverter errorEvacuationTableDataConverter;

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
     * ConvertTErrorEvacuationSalesOrderInformationEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesOrderInformationEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId01";
        String salesTransactionErrorId = "salesTransactionErrorId01";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user01");
        tableCommonItem.setCreateProgramId("SLS0300101");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesOrderInformation expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesOrderInformation();

        ErrorEvacuationSalesOrderInformation result = errorEvacuationTableDataConverter
                .convertTErrorEvacuationSalesOrderInformationEntityForInsert(transactionImportData,
                        userId, salesTransactionErrorId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTErrorEvacuationSalesOrderInformationEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesOrderInformationEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId01";
        String salesTransactionErrorId = "salesTransactionErrorId01";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user01");
        tableCommonItem.setCreateProgramId("SLS0300101");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesOrderInformation expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesOrderInformation();
        expect.setDataAlterationEditingFlag(false);
        expect.setOrderConfirmationDateTime(null);

        transactionImportData.setDataCorrectionEditingFlag(null);
        transactionImportData.setOrderConfirmationDateTime(null);
        ErrorEvacuationSalesOrderInformation result = errorEvacuationTableDataConverter
                .convertTErrorEvacuationSalesOrderInformationEntityForInsert(transactionImportData,
                        userId, salesTransactionErrorId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionHeaderEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionHeaderEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId02";
        String salesTransactionErrorId = "salesTransactionErrorId02";
        String orignialSalesTransactionErrorId = "orignialSalesTransactionErrorId02";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 22);
        tableCommonItem.setCreateUserId("user02");
        tableCommonItem.setCreateProgramId("SLS0300102");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user02");
        tableCommonItem.setUpdateProgramId("SLS0300102");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionHeader expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionHeader();

        transactionImportData.getTransactionList().forEach(transaction -> {
            ErrorEvacuationSalesTransactionHeader result = errorEvacuationTableDataConverter
                    .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(transaction,
                            userId, salesTransactionErrorId, orignialSalesTransactionErrorId);

            System.out.println(result);
            System.out.println(expect);
            assertEquals(expect, result);
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionHeaderEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionHeaderEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId02";
        String salesTransactionErrorId = "salesTransactionErrorId02";
        String orignialSalesTransactionErrorId = "orignialSalesTransactionErrorId02";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 22);
        tableCommonItem.setCreateUserId("user02");
        tableCommonItem.setCreateProgramId("SLS0300102");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user02");
        tableCommonItem.setUpdateProgramId("SLS0300102");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionHeader expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionHeader();
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

            ErrorEvacuationSalesTransactionHeader result = errorEvacuationTableDataConverter
                    .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(transaction,
                            userId, salesTransactionErrorId, orignialSalesTransactionErrorId);

            System.out.println(result);
            System.out.println(expect);
            assertEquals(expect, result);
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionDetailEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionDetailEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId03";
        String salesTransactionId = "salesTransactionId03";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId03";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId03";
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        tableCommonItem.setCreateUserId("user03");
        tableCommonItem.setCreateProgramId("SLS0300103");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user03");
        tableCommonItem.setUpdateProgramId("SLS0300103");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionDetail expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionDetail();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                ErrorEvacuationSalesTransactionDetail result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
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
        ErrorEvacuationSalesTransactionDetail result = errorEvacuationTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsert(transactionItemDetail,
                        userId, salesTransactionId, orderSubNumber, salesTransactionErrorId,
                        originalSalesTransactionErrorId, detailCount, itemDetailCount);
        expect.setStoreBundleSaleFlag(false);
        assertEquals(expect, result);
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionDetailEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionDetailEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();
        String userId = "userId03";
        String salesTransactionId = "salesTransactionId03";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId03";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId03";
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        tableCommonItem.setCreateUserId("user03");
        tableCommonItem.setCreateProgramId("SLS0300103");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user03");
        tableCommonItem.setUpdateProgramId("SLS0300103");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionDetail expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionDetail();
        expect.setOrderStatusLastUpdateDateTime(null);
        expect.setMajorCategoryCode(null);
        expect.setDetailQuantity(null);
        expect.setBundlePurchaseApplicableQuantity(null);
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
                transactionItemDetail.setItemCost(null);
                transactionItemDetail.setInitialSellingPrice(null);
                transactionItemDetail.setBItemSellingPrice(null);
                transactionItemDetail.setItemNewPrice(null);
                transactionItemDetail.setItemUnitPriceTaxExcluded(null);
                transactionItemDetail.setItemUnitPriceTaxIncluded(null);
                transactionItemDetail.setItemSalesAmtTaxIncluded(null);
                transactionItemDetail.setItemSalesAmtTaxExcluded(null);
                transactionItemDetail.setBundlePurchasePrice(null);
                transactionItemDetail.setItemDiscountAmount(null);
                transactionItemDetail.setBundleSalesPrice(null);
                ErrorEvacuationSalesTransactionDetail result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                                transactionItemDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId,
                                detailCount, itemDetailCount);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId04";
        String salesTransactionId = "salesTransactionId04";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId04";
        String salesTransactionType = "salesTransactionType04";
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId04";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 24);
        tableCommonItem.setCreateUserId("user04");
        tableCommonItem.setCreateProgramId("SLS0300104");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user04");
        tableCommonItem.setUpdateProgramId("SLS0300104");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionDetail expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionDetailForInsertOutside();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                ErrorEvacuationSalesTransactionDetail result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                                nonItemDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, salesTransactionType, detailCount,
                                itemDetailCount, originalSalesTransactionErrorId);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionDetailEntityForInsertOutsideNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId04";
        String salesTransactionId = "salesTransactionId04";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId04";
        String salesTransactionType = "salesTransactionType04";
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId04";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 24);
        tableCommonItem.setCreateUserId("user04");
        tableCommonItem.setCreateProgramId("SLS0300104");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user04");
        tableCommonItem.setUpdateProgramId("SLS0300104");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionDetail expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionDetailForInsertOutside();
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

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setOrderStatusLastUpdateDateTime(null);
                nonItemDetail.setNonItemQty(null);
                nonItemDetail.setNonItemNewPrice(null);
                nonItemDetail.setNonItemUnitPriceTaxExcluded(null);
                nonItemDetail.setNonItemUnitPriceTaxIncluded(null);
                nonItemDetail.setNonItemSalesAmtTaxExcluded(null);
                nonItemDetail.setNonItemSalesAmtTaxIncluded(null);
                ErrorEvacuationSalesTransactionDetail result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                                nonItemDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, salesTransactionType, detailCount,
                                itemDetailCount, originalSalesTransactionErrorId);

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

        String userId = "userId04";
        String salesTransactionId = "salesTransactionId04";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId04";
        String salesTransactionType = null;
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId04";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 24);
        tableCommonItem.setCreateUserId("user04");
        tableCommonItem.setCreateProgramId("SLS0300104");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user04");
        tableCommonItem.setUpdateProgramId("SLS0300104");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        String expect = "c00001";

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                ErrorEvacuationSalesTransactionDetail result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                                nonItemDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, salesTransactionType, detailCount,
                                itemDetailCount, originalSalesTransactionErrorId);

                assertEquals(expect, result.getSalesTransactionType());
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId05";
        String salesTransactionId = "salesTransactionId05";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId05";
        Integer detailCount = 301;
        Integer itemDetailCount = 9901;
        String orignialSalesTransactionErrorId = "orignialSalesTransactionErrorId05";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 25);
        tableCommonItem.setCreateUserId("user05");
        tableCommonItem.setCreateProgramId("SLS0300105");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user05");
        tableCommonItem.setUpdateProgramId("SLS0300105");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionDetailInfo expect =
                ErrorEvacuationTableDataConverterTestHelper
                        .makeErrorEvacuationSalesTransactionDetailInfo();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                NonItemInfo nonItemInfo = nonItemDetail.getNonItemInfo();
                ErrorEvacuationSalesTransactionDetailInfo result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                                nonItemInfo, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, detailCount, itemDetailCount,
                                orignialSalesTransactionErrorId);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId06";
        String salesTransactionId = "salesTransactionId06";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId06";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId06";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 26);
        tableCommonItem.setCreateUserId("user06");
        tableCommonItem.setCreateProgramId("SLS0300106");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user06");
        tableCommonItem.setUpdateProgramId("SLS0300106");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionDiscount expect = ErrorEvacuationTableDataConverterTestHelper
                .makeEvacuationSalesTransactionDiscount();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                    ErrorEvacuationSalesTransactionDiscount result =
                            errorEvacuationTableDataConverter
                                    .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                                            nonItemDiscountDetail, userId, salesTransactionId,
                                            orderSubNumber, salesTransactionErrorId, detailCount,
                                            itemDiscountSubNumber, originalSalesTransactionErrorId);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionDiscountEntityForInsertNonNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId06";
        String salesTransactionId = "salesTransactionId06";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId06";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId06";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 26);
        tableCommonItem.setCreateUserId("user06");
        tableCommonItem.setCreateProgramId("SLS0300106");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user06");
        tableCommonItem.setUpdateProgramId("SLS0300106");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionDiscount expect = ErrorEvacuationTableDataConverterTestHelper
                .makeEvacuationSalesTransactionDiscount();
        expect.setDiscountAmountTaxExcludedCurrencyCode(null);
        expect.setDiscountAmountTaxExcluded(null);
        expect.setDiscountAmountTaxIncludedCurrencyCode(null);
        expect.setDiscountAmountTaxIncluded(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                    nonItemDiscountDetail.setNonItemDiscountAmtTaxExcluded(null);
                    nonItemDiscountDetail.setNonItemDiscountAmtTaxIncluded(null);
                    ErrorEvacuationSalesTransactionDiscount result =
                            errorEvacuationTableDataConverter
                                    .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                                            nonItemDiscountDetail, userId, salesTransactionId,
                                            orderSubNumber, salesTransactionErrorId, detailCount,
                                            itemDiscountSubNumber, originalSalesTransactionErrorId);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon_PromotionNo.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon_PromotionNo() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId06";
        String salesTransactionId = "salesTransactionId06";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId06";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId06";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 26);
        tableCommonItem.setCreateUserId("user06");
        tableCommonItem.setCreateProgramId("SLS0300106");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user06");
        tableCommonItem.setUpdateProgramId("SLS0300106");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionDiscount expect = ErrorEvacuationTableDataConverterTestHelper
                .makeEvacuationSalesTransactionDiscount();
        expect.setPromotionNo("9");

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                    nonItemDiscountDetail.setNonItemPromotionNumber("9");
                    ErrorEvacuationSalesTransactionDiscount result =
                            errorEvacuationTableDataConverter
                                    .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                                            nonItemDiscountDetail, userId, salesTransactionId,
                                            orderSubNumber, salesTransactionErrorId, detailCount,
                                            itemDiscountSubNumber, originalSalesTransactionErrorId);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTaxEntityForInsertNon.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTaxEntityForInsertNon() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId07";
        String salesTransactionId = "salesTransactionId07";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId07";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId07";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 27);
        tableCommonItem.setCreateUserId("user07");
        tableCommonItem.setCreateProgramId("SLS0300107");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user07");
        tableCommonItem.setUpdateProgramId("SLS0300107");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTax expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionTax();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemTaxDetailList().forEach(nonItemTaxDetail -> {
                    ErrorEvacuationSalesTransactionTax result = errorEvacuationTableDataConverter
                            .convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                                    nonItemTaxDetail, userId, salesTransactionId, orderSubNumber,
                                    salesTransactionErrorId, detailCount, taxSubNumber,
                                    originalSalesTransactionErrorId);
                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTaxEntityForInsertNon.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTaxEntityForInsertNonNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId07";
        String salesTransactionId = "salesTransactionId07";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId07";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId07";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 27);
        tableCommonItem.setCreateUserId("user07");
        tableCommonItem.setCreateProgramId("SLS0300107");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user07");
        tableCommonItem.setUpdateProgramId("SLS0300107");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTax expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionTax();
        expect.setTaxAmountCurrencyCode(null);
        expect.setTaxAmountValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemTaxDetailList().forEach(nonItemTaxDetail -> {
                    nonItemTaxDetail.setNonItemTaxAmt(null);

                    ErrorEvacuationSalesTransactionTax result = errorEvacuationTableDataConverter
                            .convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                                    nonItemTaxDetail, userId, salesTransactionId, orderSubNumber,
                                    salesTransactionErrorId, detailCount, taxSubNumber,
                                    originalSalesTransactionErrorId);
                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionDiscountEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionDiscountEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId08";
        String salesTransactionId = "salesTransactionId08";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId08";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId08";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 28);
        tableCommonItem.setCreateUserId("user08");
        tableCommonItem.setCreateProgramId("SLS0300108");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user08");
        tableCommonItem.setUpdateProgramId("SLS0300108");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionDiscount expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionDiscountForInsert();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemDiscountList().forEach(itemDiscount -> {
                    ErrorEvacuationSalesTransactionDiscount result =
                            errorEvacuationTableDataConverter
                                    .convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                                            itemDiscount, userId, salesTransactionId,
                                            orderSubNumber, salesTransactionErrorId, detailCount,
                                            itemDiscountSubNumber, originalSalesTransactionErrorId);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionDiscountEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionDiscountEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId08";
        String salesTransactionId = "salesTransactionId08";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId08";
        Integer detailCount = 301;
        Integer itemDiscountSubNumber = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId08";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 28);
        tableCommonItem.setCreateUserId("user08");
        tableCommonItem.setCreateProgramId("SLS0300108");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user08");
        tableCommonItem.setUpdateProgramId("SLS0300108");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionDiscount expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionDiscountForInsert();
        expect.setDiscountAmountTaxExcludedCurrencyCode(null);
        expect.setDiscountAmountTaxExcluded(null);
        expect.setDiscountAmountTaxIncludedCurrencyCode(null);
        expect.setDiscountAmountTaxIncluded(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemDiscountList().forEach(itemDiscount -> {
                    itemDiscount.setItemDiscountAmtTaxExcluded(null);
                    itemDiscount.setItemDiscountAmtTaxIncluded(null);
                    ErrorEvacuationSalesTransactionDiscount result =
                            errorEvacuationTableDataConverter
                                    .convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                                            itemDiscount, userId, salesTransactionId,
                                            orderSubNumber, salesTransactionErrorId, detailCount,
                                            itemDiscountSubNumber, originalSalesTransactionErrorId);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTaxEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTaxEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId09";
        String salesTransactionId = "salesTransactionId09";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId09";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId09";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        tableCommonItem.setCreateUserId("user09");
        tableCommonItem.setCreateProgramId("SLS0300109");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user09");
        tableCommonItem.setUpdateProgramId("SLS0300109");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTax expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionTaxEntityForInsert();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemTaxDetailList().forEach(itemTaxDetail -> {
                    ErrorEvacuationSalesTransactionTax result = errorEvacuationTableDataConverter
                            .convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                                    itemTaxDetail, userId, salesTransactionId, orderSubNumber,
                                    salesTransactionErrorId, detailCount, taxSubNumber,
                                    originalSalesTransactionErrorId);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTaxEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTaxEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId09";
        String salesTransactionId = "salesTransactionId09";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId09";
        Integer detailCount = 301;
        Integer taxSubNumber = 9901;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId09";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        tableCommonItem.setCreateUserId("user09");
        tableCommonItem.setCreateProgramId("SLS0300109");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user09");
        tableCommonItem.setUpdateProgramId("SLS0300109");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTax expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionTaxEntityForInsert();
        expect.setTaxAmountCurrencyCode(null);
        expect.setTaxAmountValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemTaxDetailList().forEach(itemTaxDetail -> {
                    itemTaxDetail.setItemTaxAmt(null);
                    ErrorEvacuationSalesTransactionTax result = errorEvacuationTableDataConverter
                            .convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                                    itemTaxDetail, userId, salesTransactionId, orderSubNumber,
                                    salesTransactionErrorId, detailCount, taxSubNumber,
                                    originalSalesTransactionErrorId);

                    System.out.println(result);
                    System.out.println(expect);
                    assertEquals(expect, result);
                });
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTenderEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTenderEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId10";
        String salesTransactionId = "salesTransactionId10";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId10";
        Integer tenderSubNumber = 301;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId10";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 30);
        tableCommonItem.setCreateUserId("user10");
        tableCommonItem.setCreateProgramId("SLS0300110");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user10");
        tableCommonItem.setUpdateProgramId("SLS0300110");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTender expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionTenderForInsert();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(salesTransactionTender -> {
                ErrorEvacuationSalesTransactionTender result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                                salesTransactionTender, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, tenderSubNumber,
                                originalSalesTransactionErrorId);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTenderEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTenderEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId10";
        String salesTransactionId = "salesTransactionId10";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId10";
        Integer tenderSubNumber = 301;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId10";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 30);
        tableCommonItem.setCreateUserId("user10");
        tableCommonItem.setCreateProgramId("SLS0300110");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user10");
        tableCommonItem.setUpdateProgramId("SLS0300110");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTender expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionTenderForInsert();
        expect.setTaxIncludedPaymentAmountCurrencyCode(null);
        expect.setTaxIncludedPaymentAmountValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(salesTransactionTender -> {
                salesTransactionTender.setTaxIncludedPaymentAmount(null);
                ErrorEvacuationSalesTransactionTender result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                                salesTransactionTender, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, tenderSubNumber,
                                originalSalesTransactionErrorId);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId11";
        String salesTransactionId = "salesTransactionId11";
        Integer orderSubNumber = 201;
        String tenderGroupId = "tenderGroupId11";
        String tenderId = "301";
        String salesTransactionErrorId = "salesTransactionErrorId11";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId11";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 31);
        tableCommonItem.setCreateUserId("user11");
        tableCommonItem.setCreateProgramId("SLS0300111");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user11");
        tableCommonItem.setUpdateProgramId("SLS0300111");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTenderInfo expect =
                ErrorEvacuationTableDataConverterTestHelper
                        .makeErrorEvacuationSalesTransactionTenderInfoForInsert();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(salesTransactionTender -> {
                ErrorEvacuationSalesTransactionTenderInfo result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                                salesTransactionTender.getTenderInfoList(), userId,
                                salesTransactionId, orderSubNumber, tenderGroupId, tenderId,
                                salesTransactionErrorId, originalSalesTransactionErrorId, 1);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTenderInfoEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId11";
        String salesTransactionId = "salesTransactionId11";
        Integer orderSubNumber = 201;
        String tenderGroupId = "tenderGroupId11";
        String tenderId = "301";
        String salesTransactionErrorId = "salesTransactionErrorId11";
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId11";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 31);
        tableCommonItem.setCreateUserId("user11");
        tableCommonItem.setCreateProgramId("SLS0300111");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user11");
        tableCommonItem.setUpdateProgramId("SLS0300111");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTenderInfo expect =
                ErrorEvacuationTableDataConverterTestHelper
                        .makeErrorEvacuationSalesTransactionTenderInfoForInsert();
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
                ErrorEvacuationSalesTransactionTenderInfo result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                                salesTransactionTender.getTenderInfoList(), userId,
                                salesTransactionId, orderSubNumber, tenderGroupId, tenderId,
                                salesTransactionErrorId, originalSalesTransactionErrorId, 1);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId12";
        String salesTransactionId = "salesTransactionId12";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId12";
        Integer detailCount = 301;
        Integer taxSubNumber = 401;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId12";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("user12");
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user12");
        tableCommonItem.setUpdateProgramId("SLS0300112");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTax expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionTaxForInsert();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTaxDetailList().forEach(itemTaxDetail -> {
                itemTaxDetail.setTaxAmountSign(null);
                itemTaxDetail.setTaxGroup(null);
                itemTaxDetail.setTaxRate(null);
                ErrorEvacuationSalesTransactionTax result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                                itemTaxDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, detailCount, taxSubNumber,
                                originalSalesTransactionErrorId);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTaxEntityForInsertTransactionNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId12";
        String salesTransactionId = "salesTransactionId12";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId12";
        Integer detailCount = 301;
        Integer taxSubNumber = 401;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId12";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("user12");
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user12");
        tableCommonItem.setUpdateProgramId("SLS0300112");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTax expect = ErrorEvacuationTableDataConverterTestHelper
                .makeErrorEvacuationSalesTransactionTaxForInsert();
        expect.setTaxAmountCurrencyCode(null);
        expect.setTaxAmountValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTaxDetailList().forEach(itemTaxDetail -> {
                itemTaxDetail.setTaxAmount(null);
                itemTaxDetail.setTaxAmountSign(null);
                itemTaxDetail.setTaxGroup(null);
                itemTaxDetail.setTaxRate(null);
                ErrorEvacuationSalesTransactionTax result = errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                                itemTaxDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, detailCount, taxSubNumber,
                                originalSalesTransactionErrorId);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId13";
        String salesTransactionId = "salesTransactionId13";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId13";
        Integer totalAmountSubNumber = 301;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId13";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 33);
        tableCommonItem.setCreateUserId("user13");
        tableCommonItem.setCreateProgramId("SLS0300113");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user13");
        tableCommonItem.setUpdateProgramId("SLS0300113");

        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTotalAmount expect =
                ErrorEvacuationTableDataConverterTestHelper
                        .makeErrorEvacuationSalesTransactionTotalAmountForInsert();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTotalList().forEach(salesTransactionTotal -> {
                ErrorEvacuationSalesTransactionTotalAmount result =
                        errorEvacuationTableDataConverter
                                .convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                                        salesTransactionTotal, userId, salesTransactionId,
                                        orderSubNumber, salesTransactionErrorId,
                                        totalAmountSubNumber, originalSalesTransactionErrorId);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

    /**
     * ConvertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert.
     */
    @Test
    public void testConvertTErrorEvacuationSalesTransactionTotalAmountEntityForInsertNull() {
        TransactionImportData transactionImportData =
                ErrorEvacuationTableDataConverterTestHelper.makeImportData();

        String userId = "userId13";
        String salesTransactionId = "salesTransactionId13";
        Integer orderSubNumber = 201;
        String salesTransactionErrorId = "salesTransactionErrorId13";
        Integer totalAmountSubNumber = 301;
        String originalSalesTransactionErrorId = "originalSalesTransactionErrorId13";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 33);
        tableCommonItem.setCreateUserId("user13");
        tableCommonItem.setCreateProgramId("SLS0300113");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user13");
        tableCommonItem.setUpdateProgramId("SLS0300113");

        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        ErrorEvacuationSalesTransactionTotalAmount expect =
                ErrorEvacuationTableDataConverterTestHelper
                        .makeErrorEvacuationSalesTransactionTotalAmountForInsert();
        expect.setTotalAmountTaxExcludedCurrencyCode(null);
        expect.setTotalAmountTaxExcludedValue(null);
        expect.setTotalAmountTaxIncludedCurrencyCode(null);
        expect.setTotalAmountTaxIncludedValue(null);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTotalList().forEach(salesTransactionTotal -> {
                salesTransactionTotal.setTotalAmountTaxExcluded(null);
                salesTransactionTotal.setTotalAmountTaxIncluded(null);
                ErrorEvacuationSalesTransactionTotalAmount result =
                        errorEvacuationTableDataConverter
                                .convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                                        salesTransactionTotal, userId, salesTransactionId,
                                        orderSubNumber, salesTransactionErrorId,
                                        totalAmountSubNumber, originalSalesTransactionErrorId);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);

            });
        });
    }

}
