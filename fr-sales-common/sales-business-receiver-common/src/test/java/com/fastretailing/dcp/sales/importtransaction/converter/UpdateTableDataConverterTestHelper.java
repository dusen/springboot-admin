package com.fastretailing.dcp.sales.importtransaction.converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.storecommon.dto.Price;
import com.fastretailing.dcp.storecommon.util.DateUtility;
import com.fastretailing.dcp.storecommon.util.DateUtility.DateTimeFormat;

/**
 * ErrorEvacuationTableDataConverter JUnit Test class.
 */
public class UpdateTableDataConverterTestHelper {

    public static TransactionImportData makeImportData() {
        TransactionImportData importData = new TransactionImportData();
        importData.setUpdateType("ERRORERROR");
        importData.setErrorCheckType(9);
        importData.setDataAlterationSalesLinkageType(9);
        importData.setDataAlterationBackboneLinkageType(9);
        importData.setSalesTransactionErrorId("20181212T111111000");
        importData.setIntegratedOrderId("320581198607101614abcdefghi");
        importData.setOrderBarcodeNumber("320581198607101614abcdefgh");
        importData.setChannelCode("A1");
        importData.setStoreCode("1234567890");
        importData.setSystemBrandCode("1234");
        importData.setSystemBusinessCode("1234");
        importData.setSystemCountryCode("USA");
        importData.setCustomerId("320581198607101614123456123456");
        importData.setOrderConfirmationBusinessDate("2017-12-12");
        importData.setOrderConfirmationDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-27T09:27:45Z", "UTC"));
        importData.setDataCorrectionEditingFlag(true);
        importData.setDataCorrectionUserId("UUU000000000000000000000user01");
        List<Transaction> transactionList =
                UpdateTableDataConverterTestHelper.makeAutoTransaction();
        importData.setTransactionList(transactionList);
        return importData;
    }

    public static List<Transaction> makeAutoTransaction() {
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();

        transaction.setTransactionSerialNumber(1);
        transaction.setIntegratedOrderId("320581198607101614abcdefghi");
        transaction.setOrderSubNumber(2);
        transaction.setSalesTransactionId("a00000000000000000000000000001");
        transaction.setTokenCode("a00000000000000000000000000002");
        transaction.setTransactionType("a00002");
        transaction.setSalesLinkageType(3);
        transaction.setReturnType(4);
        transaction.setSystemBrandCode("A001");
        transaction.setSystemBusinessCode("A002");
        transaction.setSystemCountryCode("USA");
        transaction.setStoreCode("a123456789");
        transaction.setChannelCode("a1");
        transaction.setDataCreationBusinessDate("2018-12-12");
        transaction.setDataCreationDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-27T09:27:45Z", "UTC"));
        transaction.setOrderStatusUpdateDate("2018-12-12");
        transaction.setOrderStatusLastUpdateDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-27T09:27:45Z", "UTC"));
        transaction.setCashRegisterNo(5);
        transaction.setReceiptNo("a123456789");
        transaction.setOrderNumberForStorePayment("a00000000000000001");
        transaction.setAdvanceReceivedStoreCode("chinaappl1");
        transaction.setAdvanceReceivedStoreSystemBrandCode("A003");
        transaction.setAdvanceReceivedStoreSystemBusinessCode("A004");
        transaction.setAdvanceReceivedStoreSystemCountryCode("USA");
        transaction.setOperatorCode("abcdefghi2");
        transaction.setOriginalTransactionId("320581198607101614abcdef123456");
        transaction.setOriginalReceiptNo("abcdefghi3");
        transaction.setOriginalCashRegisterNo(6);
        transaction.setDeposit(UpdateTableDataConverterTestHelper.getPrice());
        transaction.setChange(UpdateTableDataConverterTestHelper.getPrice());
        transaction.setReceiptNoForCreditCardCancellation("abcde123456");
        transaction.setReceiptNoForCreditCard("320581198607101614abcdef123457");
        transaction.setPaymentStoreCode("b234567890");
        transaction.setPaymentStoreSystemBrandCode("cas1");
        transaction.setPaymentStoreSystemBusinessCode("cas2");
        transaction.setPaymentStoreSystemCountryCode("USA");
        transaction.setReceiptIssuedFlag(true);
        transaction.setProcessingCompanyCode("320581198607101614ab");
        transaction.setOrderCancellationDate(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-27T09:27:45Z", "UTC"));
        transaction.setOrderStatus("co1");
        transaction.setOrderSubstatus("co2");
        transaction.setPaymentStatus("co3");
        transaction.setShipmentStatus("co4");
        transaction.setReceivingStatus("co5");
        transaction.setTransferOutStatus("co6");
        transaction.setBookingStoreCode("123456789C");
        transaction.setBookingStoreSystemBrandCode("abc5");
        transaction.setBookingStoreSystemBusinessCode("abc6");
        transaction.setBookingStoreSystemCountryCode("USA");
        transaction.setShipmentStoreCode("123456789D");
        transaction.setShipmentStoreSystemBrandCode("abc7");
        transaction.setShipmentStoreSystemBusinessCode("abc8");
        transaction.setShipmentStoreSystemCountryCode("USA");
        transaction.setReceiptStoreCode("123456789E");
        transaction.setReceiptStoreSystemBrandCode("abc9");
        transaction.setReceiptStoreSystemBusinessCode("abd2");
        transaction.setReceiptStoreSystemCountryCode("USA");
        transaction.setCustomerId("32058119860710161412345612345a");
        transaction.setCorporateId("320581198607101614ab");
        transaction.setSalesTransactionDiscountFlag(true);
        transaction.setSalesTransactionDiscountAmountRate(
                UpdateTableDataConverterTestHelper.getPrice());
        transaction.setConsistencySalesFlag(true);
        transaction.setEmployeeSaleFlag(true);
        transaction.setEReceiptTargetFlag(true);
        List<TransactionItemDetail> transactionItemDetailList =
                UpdateTableDataConverterTestHelper.makeTransactionItemDetail();
        transaction.setTransactionItemDetailList(transactionItemDetailList);
        List<NonItemDetail> nonItemDetailList =
                UpdateTableDataConverterTestHelper.makeNonItemDetail();
        transaction.setNonItemDetailList(nonItemDetailList);
        transactionList.add(transaction);
        return transactionList;
    }

    public static List<NonItemDetail> makeNonItemDetail() {
        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = new NonItemDetail();

        nonItemDetail.setNonItemDetailNumber(2301);
        nonItemDetail.setNonMdDetailListSalesTransactionType("c00001");
        nonItemDetail.setNonItemDetailSalesLinkageType(2);
        nonItemDetail.setNonMdType("c00002");
        nonItemDetail.setNonItemCode("abcdc00002abcdc00002a0001");
        nonItemDetail.setServiceCode("a0001");
        nonItemDetail.setPosNonItemName("abcde2222205865656");
        nonItemDetail.setQuantityCode("A");
        nonItemDetail.setNonItemQty(3333);
        nonItemDetail.setNonItemUnitPriceTaxExcluded(UpdateTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemUnitPriceTaxIncluded(UpdateTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxExcluded(UpdateTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxIncluded(UpdateTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemNewPrice(UpdateTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonCalculationNonItemType("d000000001");
        nonItemDetail.setOrderStatusUpdateDate("2018-12-12");
        nonItemDetail.setOrderStatusLastUpdateDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-27T09:27:45Z", "UTC"));
        nonItemDetail.setOrderStatus("co1");
        nonItemDetail.setOrderSubstatus("co2");
        nonItemDetail.setBookingStoreCode("b000000001");
        nonItemDetail.setBookingStoreSystemBrandCode("s001");
        nonItemDetail.setBookingStoreSystemBusinessCode("s002");
        nonItemDetail.setBookingStoreSystemCountryCode("USA");
        nonItemDetail.setShipmentStoreCode("b000000002");
        nonItemDetail.setShipmentStoreSystemBrandCode("s003");
        nonItemDetail.setShipmentStoreSystemBusinessCode("s004");
        nonItemDetail.setShipmentStoreSystemCountryCode("USB");
        nonItemDetail.setReceiptStoreCode("b000000003");
        nonItemDetail.setReceiptStoreSystemBrandCode("s005");
        nonItemDetail.setReceiptStoreSystemBusinessCode("s006");
        nonItemDetail.setReceiptStoreSystemCountryCode("USC");
        nonItemDetail.setContributionSalesRepresentative("b000000004");
        nonItemDetail.setItemDetailSubNumber(2012);
        nonItemDetail.setNonItemTaxationType("b000000005");
        nonItemDetail.setNonItemTaxKind("b000000006");

        nonItemDetailList.add(nonItemDetail);
        return nonItemDetailList;
    }

    public static List<TransactionItemDetail> makeTransactionItemDetail() {
        List<TransactionItemDetail> transactionItemDetailList = new ArrayList<>();
        TransactionItemDetail transactionItemDetail = new TransactionItemDetail();

        transactionItemDetail.setSystemBrandCode("ab01");
        transactionItemDetail.setDetailSubNumber(152);
        transactionItemDetail.setDetailListSalesTransactionType("c00001");
        transactionItemDetail.setL2ItemCode("abcdefg000000000000000001");
        transactionItemDetail.setViewL2ItemCode("abcdefg000000000000000002");
        transactionItemDetail.setL2ProductName("a120304789adgdfgdfgdfg");
        transactionItemDetail.setL3ItemCode("abcdefg000000000000000003");
        transactionItemDetail.setL3PosProductName("a120304789adgdfgdfgdfdsfd");
        transactionItemDetail.setEpcCode("abcdefg000000000000000004");
        transactionItemDetail.setGDepartmentCode("c00002");
        transactionItemDetail.setDeptCode(1234);
        transactionItemDetail.setQuantityCode("A");
        transactionItemDetail.setItemQty(99);
        transactionItemDetail.setItemCost(UpdateTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setInitialSellingPrice(UpdateTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setBItemSellingPrice(UpdateTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setItemNewPrice(UpdateTableDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemUnitPriceTaxExcluded(UpdateTableDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemUnitPriceTaxIncluded(UpdateTableDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemSalesAmtTaxExcluded(UpdateTableDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemSalesAmtTaxIncluded(UpdateTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setOrderStatusUpdateDate("2018-12-12");
        transactionItemDetail.setOrderStatusLastUpdateDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-27T09:27:45Z", "UTC"));
        transactionItemDetail.setOrderStatus("aa2");
        transactionItemDetail.setOrderSubstatus("aa3");
        transactionItemDetail.setBookingStoreCode("a234567801");
        transactionItemDetail.setBookingStoreSystemBrandCode("ab01");
        transactionItemDetail.setBookingStoreSystemBusinessCode("ab02");
        transactionItemDetail.setBookingStoreSystemCountryCode("USA");
        transactionItemDetail.setShipmentStoreCode("a234567802");
        transactionItemDetail.setShipmentStoreSystemBrandCode("ab03");
        transactionItemDetail.setShipmentStoreSystemBusinessCode("ab04");
        transactionItemDetail.setShipmentStoreSystemCountryCode("USB");
        transactionItemDetail.setReceiptStoreCode("a234567803");
        transactionItemDetail.setReceiptStoreSystemBrandCode("ab05");
        transactionItemDetail.setReceiptStoreSystemBusinessCode("ab06");
        transactionItemDetail.setReceiptStoreSystemCountryCode("USC");
        transactionItemDetail.setContributionSalesRepresentative("a234567804");
        transactionItemDetail.setCustomerId("a20581198607101614123456123456");
        transactionItemDetail.setBundlePurchaseQty(5672);
        transactionItemDetail.setBundlePurchasePrice(UpdateTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundlePurchaseIndex(26);
        transactionItemDetail.setLimitedAmountPromotionCount(99);
        transactionItemDetail.setCalculationUnavailableType("T");
        transactionItemDetail.setItemMountDiscountType("QW");
        transactionItemDetail.setItemDiscountAmount(UpdateTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundleSalesFlag(true);
        transactionItemDetail.setBundleSalesPrice(UpdateTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundleSalesDetailIndex(1234);
        transactionItemDetail.setItemDetailNumber(1);
        transactionItemDetail.setItemTaxationType("a234567809");
        transactionItemDetail.setItemTaxKind("a234567808");
        List<NonItemDetail> nonItemDetailList =
                UpdateTableDataConverterTestHelper.makeItemDetailNonItemDetail();
        transactionItemDetail.setNonItemDetailListByItem(nonItemDetailList);

        transactionItemDetailList.add(transactionItemDetail);
        return transactionItemDetailList;
    }

    public static List<NonItemDetail> makeItemDetailNonItemDetail() {
        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = new NonItemDetail();

        nonItemDetail.setNonItemDetailNumber(4001);
        nonItemDetail.setNonMdDetailListSalesTransactionType("a00001");
        nonItemDetail.setNonItemDetailSalesLinkageType(123);
        nonItemDetail.setNonMdType("a00002");
        nonItemDetail.setNonItemCode("abcde00000000000000000001");
        nonItemDetail.setServiceCode("b0001");
        nonItemDetail.setPosNonItemName("abcde00000hhhbdss00001");
        nonItemDetail.setQuantityCode("Q");
        nonItemDetail.setNonItemQty(1249);
        nonItemDetail.setNonItemUnitPriceTaxExcluded(UpdateTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemUnitPriceTaxIncluded(UpdateTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxExcluded(UpdateTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxIncluded(UpdateTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemNewPrice(UpdateTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonCalculationNonItemType("d000000001");
        nonItemDetail.setOrderStatusUpdateDate("2018-02-27T09:27:45Z");
        nonItemDetail.setOrderStatusLastUpdateDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-27T09:27:45Z", "UTC"));
        nonItemDetail.setOrderStatus("o01");
        nonItemDetail.setOrderSubstatus("o02");
        nonItemDetail.setBookingStoreCode("d000000002");
        nonItemDetail.setBookingStoreSystemBrandCode("w001");
        nonItemDetail.setBookingStoreSystemBusinessCode("w002");
        nonItemDetail.setBookingStoreSystemCountryCode("USA");
        nonItemDetail.setShipmentStoreCode("d000000003");
        nonItemDetail.setShipmentStoreSystemBrandCode("w003");
        nonItemDetail.setShipmentStoreSystemBusinessCode("w004");
        nonItemDetail.setShipmentStoreSystemCountryCode("USB");
        nonItemDetail.setReceiptStoreCode("d000000004");
        nonItemDetail.setReceiptStoreSystemBrandCode("w005");
        nonItemDetail.setReceiptStoreSystemBusinessCode("w006");
        nonItemDetail.setReceiptStoreSystemCountryCode("USC");
        nonItemDetail.setContributionSalesRepresentative("d000000005");
        nonItemDetail.setDetailSubNumber(1289);
        nonItemDetail.setItemDetailSubNumber(1290);
        nonItemDetail.setNonItemTaxationType("d000000006");
        nonItemDetail.setNonItemTaxKind("d000000007");

        nonItemDetailList.add(nonItemDetail);
        return nonItemDetailList;
    }

    public static SalesTransactionHeader makeTSalesTransactionHeaderforUpdate() {
        SalesTransactionHeader headerEntity = new SalesTransactionHeader();

        headerEntity.setSalesTransactionId("a00000000000000000000000000001");
        headerEntity.setOrderStatusUpdateDate("2018-12-12");
        LocalDateTime ldt =
                DateUtility.parseDateTime("2018-02-27 09:27:45", DateTimeFormat.UUUUHMMHDDHHQMIQSS);
        headerEntity.setOrderStatusLastUpdateDateTime(ldt);
        headerEntity.setOrderStatus("co1");
        headerEntity.setOrderSubstatus("co2");

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        headerEntity.setUpdateUserId("user01");
        headerEntity.setUpdateProgramId("SLS0300101");
        headerEntity.setUpdateDatetime(nowDateTime);
        return headerEntity;
    }

    public static SalesTransactionDetail makeTSalesTransactionDetailforUpdate() {
        SalesTransactionDetail detailEntity = new SalesTransactionDetail();
        detailEntity.setSalesTransactionId("salesTransactionId");
        detailEntity.setDetailSubNumber(152);
        detailEntity.setOrderStatusUpdateDate("2018-12-12");
        LocalDateTime ldt =
                DateUtility.parseDateTime("2018-02-27 09:27:45", DateTimeFormat.UUUUHMMHDDHHQMIQSS);
        detailEntity.setOrderStatusLastUpdateDateTime(ldt);
        detailEntity.setOrderStatus("aa2");
        detailEntity.setOrderSubstatus("aa3");

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        detailEntity.setUpdateUserId("user01");
        detailEntity.setUpdateProgramId("SLS0300101");
        detailEntity.setUpdateDatetime(nowDateTime);
        return detailEntity;
    }

    public static SalesTransactionDetail makeTSalesTransactionDetailNonforUpdate() {
        SalesTransactionDetail detailEntity = new SalesTransactionDetail();
        detailEntity.setSalesTransactionId("salesTransactionId");
        detailEntity.setDetailSubNumber(2012);
        detailEntity.setOrderStatusUpdateDate("2018-12-12");
        LocalDateTime ldt =
                DateUtility.parseDateTime("2018-02-27 09:27:45", DateTimeFormat.UUUUHMMHDDHHQMIQSS);
        detailEntity.setOrderStatusLastUpdateDateTime(ldt);
        detailEntity.setOrderStatus("co1");
        detailEntity.setOrderSubstatus("co2");

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        detailEntity.setUpdateUserId("user01");
        detailEntity.setUpdateProgramId("SLS0300101");
        detailEntity.setUpdateDatetime(nowDateTime);
        return detailEntity;
    }

    private static Price getPrice() {
        Price price = new Price();
        price.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price.setValue(new BigDecimal(1));
        return price;
    }

}
