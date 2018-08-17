package com.fastretailing.dcp.sales.importtransaction.converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemDiscount;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDiscountDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTotal;
import com.fastretailing.dcp.sales.importtransaction.dto.TenderInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
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
import com.fastretailing.dcp.storecommon.dto.Price;
import com.fastretailing.dcp.storecommon.util.DateUtility;

/**
 * ErrorEvacuationTableDataConverter JUnit Test class.
 */
public class ErrorEvacuationTableDataConverterTestHelper {

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
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-24T11:54:21Z", "UTC"));
        importData.setDataCorrectionEditingFlag(true);
        importData.setDataCorrectionUserId("UUU000000000000000000000user01");
        List<Transaction> transactionList =
                ErrorEvacuationTableDataConverterTestHelper.makeAutoTransaction();
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
        transaction.setDeposit(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transaction.setChange(ErrorEvacuationTableDataConverterTestHelper.getPrice());
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
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transaction.setConsistencySalesFlag(true);
        transaction.setEmployeeSaleFlag(true);
        transaction.setEReceiptTargetFlag(true);
        List<TransactionItemDetail> transactionItemDetailList =
                ErrorEvacuationTableDataConverterTestHelper.makeTransactionItemDetail();
        transaction.setTransactionItemDetailList(transactionItemDetailList);
        List<NonItemDetail> nonItemDetailList =
                ErrorEvacuationTableDataConverterTestHelper.makeNonItemDetail();
        transaction.setNonItemDetailList(nonItemDetailList);
        List<SalesTransactionTender> salesTransactionTenderList =
                ErrorEvacuationTableDataConverterTestHelper.makeSalesTransactionTender();
        transaction.setSalesTransactionTenderList(salesTransactionTenderList);
        List<SalesTransactionTaxDetail> SalesTransactionTaxDetailList =
                ErrorEvacuationTableDataConverterTestHelper.makeSalesTransactionTaxDetail();
        transaction.setSalesTransactionTaxDetailList(SalesTransactionTaxDetailList);
        List<SalesTransactionTotal> SalesTransactionTotalList =
                ErrorEvacuationTableDataConverterTestHelper.makeSalesTransactionTotal();
        transaction.setSalesTransactionTotalList(SalesTransactionTotalList);

        transactionList.add(transaction);
        return transactionList;
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
        transactionItemDetail.setItemCost(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setInitialSellingPrice(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setBItemSellingPrice(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemNewPrice(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setItemUnitPriceTaxExcluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setItemUnitPriceTaxIncluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemSalesAmtTaxExcluded(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemSalesAmtTaxIncluded(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setOrderStatusUpdateDate("2018-02-27T09:27:45Z");
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
        transactionItemDetail
                .setBundlePurchasePrice(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundlePurchaseIndex(26);
        transactionItemDetail.setLimitedAmountPromotionCount(99);
        transactionItemDetail.setCalculationUnavailableType("T");
        transactionItemDetail.setItemMountDiscountType("QW");
        transactionItemDetail
                .setItemDiscountAmount(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundleSalesFlag(true);
        transactionItemDetail
                .setBundleSalesPrice(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundleSalesDetailIndex(1234);
        transactionItemDetail.setItemDetailNumber(1);
        transactionItemDetail.setItemTaxationType("a234567809");
        transactionItemDetail.setItemTaxKind("a234567808");
        List<NonItemDetail> nonItemDetailList =
                ErrorEvacuationTableDataConverterTestHelper.makeItemDetailNonItemDetail();
        transactionItemDetail.setNonItemDetailListByItem(nonItemDetailList);
        List<ItemDiscount> itemDiscountList =
                ErrorEvacuationTableDataConverterTestHelper.makeItemDetailItemDiscount();
        transactionItemDetail.setItemDiscountList(itemDiscountList);
        List<ItemTaxDetail> itemTaxDetailList =
                ErrorEvacuationTableDataConverterTestHelper.makeItemDetailItemTaxDetail();
        transactionItemDetail.setItemTaxDetailList(itemTaxDetailList);

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
        nonItemDetail.setNonItemUnitPriceTaxExcluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemUnitPriceTaxIncluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxExcluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxIncluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemNewPrice(ErrorEvacuationTableDataConverterTestHelper.getPrice());
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
        NonItemInfo nonItemInfo = new NonItemInfo();
        nonItemInfo.setDetailSubNumber(9999);
        nonItemInfo.setItemDetailSubNumber(9999);
        nonItemInfo.setKeyCode("a1234567890123456789");
        nonItemInfo.setCodeValue1("a123456789012345678value1");
        nonItemInfo.setCodeValue2("a123456789012345678value2");
        nonItemInfo.setCodeValue3("a123456789012345678value3");
        nonItemInfo.setCodeValue4("a123456789012345678value4");
        nonItemInfo.setName1("1234567890name1");
        nonItemInfo.setName2("1234567890name2");
        nonItemInfo.setName3("1234567890name3");
        nonItemInfo.setName4("1234567890name4");
        nonItemDetail.setNonItemInfo(nonItemInfo);
        List<NonItemDiscountDetail> nonItemDiscountDetailList =
                ErrorEvacuationTableDataConverterTestHelper
                        .makeItemDetailNonItemDetailNonItemDiscountDetail();
        nonItemDetail.setNonItemDiscountDetailList(nonItemDiscountDetailList);
        List<NonItemTaxDetail> nonItemTaxDetailList = ErrorEvacuationTableDataConverterTestHelper
                .makeItemDetailNonItemDetailNonItemTaxDetail();
        nonItemDetail.setNonItemTaxDetailList(nonItemTaxDetailList);

        nonItemDetailList.add(nonItemDetail);
        return nonItemDetailList;
    }

    public static List<NonItemDiscountDetail> makeItemDetailNonItemDetailNonItemDiscountDetail() {
        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();

        nonItemDiscountDetail.setNonItemPromotionType("a10001");
        nonItemDiscountDetail.setNonItemStoreDiscountType("a10002");
        nonItemDiscountDetail.setNonItemQuantityCode("n");
        nonItemDiscountDetail.setNonItemDiscountQty(45);
        nonItemDiscountDetail.setNonItemDiscountAmtTaxExcluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemDiscountDetail.setNonItemDiscountAmtTaxIncluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());

        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        return nonItemDiscountDetailList;
    }

    public static List<NonItemTaxDetail> makeItemDetailNonItemDetailNonItemTaxDetail() {
        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();

        nonItemTaxDetail.setNonItemTaxDetailSubNumber(2341);
        nonItemTaxDetail.setNonItemTaxType("a100000001");
        nonItemTaxDetail.setNonItemTaxName("a100000002");
        nonItemTaxDetail.setNonItemTaxAmountSign("s");
        nonItemTaxDetail.setNonItemTaxAmt(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemTaxDetail.setNonItemTaxRate(new BigDecimal(1568));

        nonItemTaxDetailList.add(nonItemTaxDetail);
        return nonItemTaxDetailList;
    }

    public static List<ItemDiscount> makeItemDetailItemDiscount() {
        List<ItemDiscount> itemDiscountList = new ArrayList<>();
        ItemDiscount itemDiscount = new ItemDiscount();

        itemDiscount.setItemDiscountDetailSubNumber(1501);
        itemDiscount.setItemDiscountSubNumber(1502);
        itemDiscount.setItemPromotionType("c00001");
        itemDiscount.setItemPromotionNumber("a000000001");
        itemDiscount.setItemStoreDiscountType("c00002");
        itemDiscount.setItemQuantityCode("A");
        itemDiscount.setItemDiscountQty(45612);
        itemDiscount.setItemDiscountAmtTaxExcluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        itemDiscount.setItemDiscountAmtTaxIncluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());

        itemDiscountList.add(itemDiscount);
        return itemDiscountList;
    }

    public static List<ItemTaxDetail> makeItemDetailItemTaxDetail() {
        List<ItemTaxDetail> itemTaxDetailList = new ArrayList<>();
        ItemTaxDetail itemTaxDetail = new ItemTaxDetail();

        itemTaxDetail.setItemTaxDetailSubNumber(2341);
        itemTaxDetail.setItemTaxSubNumber(2342);
        itemTaxDetail.setItemTaxType("a000000001");
        itemTaxDetail.setItemTaxName("dgsdgsdg0000ds00001");
        itemTaxDetail.setItemTaxAmountSign("W");
        itemTaxDetail.setItemTaxAmt(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        itemTaxDetail.setItemTaxRate(new BigDecimal(9999));

        itemTaxDetailList.add(itemTaxDetail);
        return itemTaxDetailList;
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
        nonItemDetail.setNonItemUnitPriceTaxExcluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemUnitPriceTaxIncluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxExcluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxIncluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemNewPrice(ErrorEvacuationTableDataConverterTestHelper.getPrice());
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
        nonItemDetail.setDetailSubNumber(2014);
        nonItemDetail.setItemDetailSubNumber(2012);
        nonItemDetail.setNonItemTaxationType("b000000005");
        nonItemDetail.setNonItemTaxKind("b000000006");
        NonItemInfo nonItemInfo = new NonItemInfo();
        nonItemInfo.setDetailSubNumber(9999);
        nonItemInfo.setItemDetailSubNumber(9999);
        nonItemInfo.setKeyCode("a1234567890123456789");
        nonItemInfo.setCodeValue1("a123456789012345678value1");
        nonItemInfo.setCodeValue2("a123456789012345678value2");
        nonItemInfo.setCodeValue3("a123456789012345678value3");
        nonItemInfo.setCodeValue4("a123456789012345678value4");
        nonItemInfo.setName1("1234567890name1");
        nonItemInfo.setName2("1234567890name2");
        nonItemInfo.setName3("1234567890name3");
        nonItemInfo.setName4("1234567890name4");
        nonItemDetail.setNonItemInfo(nonItemInfo);
        List<NonItemDiscountDetail> nonItemDiscountDetailList =
                ErrorEvacuationTableDataConverterTestHelper.makeNonItemDiscountDetail();
        nonItemDetail.setNonItemDiscountDetailList(nonItemDiscountDetailList);
        List<NonItemTaxDetail> nonItemTaxDetailList =
                ErrorEvacuationTableDataConverterTestHelper.makeNonItemTaxDetail();
        nonItemDetail.setNonItemTaxDetailList(nonItemTaxDetailList);

        nonItemDetailList.add(nonItemDetail);
        return nonItemDetailList;
    }

    public static List<NonItemDiscountDetail> makeNonItemDiscountDetail() {
        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();

        nonItemDiscountDetail.setNonItemPromotionType("a10001");
        nonItemDiscountDetail.setNonItemStoreDiscountType("a10002");
        nonItemDiscountDetail.setNonItemQuantityCode("n");
        nonItemDiscountDetail.setNonItemDiscountQty(10);
        nonItemDiscountDetail.setNonItemDiscountAmtTaxExcluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemDiscountDetail.setNonItemDiscountAmtTaxIncluded(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());

        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        return nonItemDiscountDetailList;
    }

    public static List<NonItemTaxDetail> makeNonItemTaxDetail() {
        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();

        nonItemTaxDetail.setNonItemTaxDetailSubNumber(2201);
        nonItemTaxDetail.setNonItemTaxType("a100000001");
        nonItemTaxDetail.setNonItemTaxName("a100000002");
        nonItemTaxDetail.setNonItemTaxAmountSign("s");
        nonItemTaxDetail.setNonItemTaxAmt(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        nonItemTaxDetail.setNonItemTaxRate(new BigDecimal(1221));

        nonItemTaxDetailList.add(nonItemTaxDetail);
        return nonItemTaxDetailList;
    }

    public static List<SalesTransactionTender> makeSalesTransactionTender() {
        List<SalesTransactionTender> salesTransactionTenderList = new ArrayList<>();
        SalesTransactionTender salesTransactionTender = new SalesTransactionTender();

        salesTransactionTender.setTenderSubNumber(2014);
        salesTransactionTender.setTenderGroupId("a20351");
        salesTransactionTender.setTenderId("201802");
        salesTransactionTender.setPaymentSign("c");
        salesTransactionTender.setTaxIncludedPaymentAmount(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        salesTransactionTender
                .setTenderInfoList(ErrorEvacuationTableDataConverterTestHelper.makeTenderInfo());

        salesTransactionTenderList.add(salesTransactionTender);
        return salesTransactionTenderList;
    }

    public static TenderInfo makeTenderInfo() {
        TenderInfo tenderInfo = new TenderInfo();

        tenderInfo.setDiscountAmount(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        tenderInfo.setDiscountRate(new BigDecimal(2018));
        tenderInfo.setDiscountCodeIdCorporateId("a00000000000000000000000000001");
        tenderInfo.setCouponType("a00001");
        tenderInfo.setCouponDiscountAmountSetting(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        tenderInfo.setCouponMinUsageAmountThreshold(
                ErrorEvacuationTableDataConverterTestHelper.getPrice());
        tenderInfo.setCouponUserId("a00000000000000000000000000002");
        tenderInfo.setCardNo("a00000000000000000000000000003");
        tenderInfo.setCreditApprovalCode("a00000000000000000000000000004");
        tenderInfo.setCreditProcessingSerialNumber("a00000000000000000000000000005");
        tenderInfo.setCreditPaymentType("a00000000000000000000000000006");
        tenderInfo.setCreditPaymentCount(24567);
        tenderInfo.setCreditAffiliatedStoreNumber("a00000000000000000000000000007");
        return tenderInfo;
    }

    public static List<SalesTransactionTaxDetail> makeSalesTransactionTaxDetail() {
        List<SalesTransactionTaxDetail> salesTransactionTaxDetailList = new ArrayList<>();
        SalesTransactionTaxDetail salesTransactionTaxDetail = new SalesTransactionTaxDetail();

        salesTransactionTaxDetail.setTaxSubNumber(1245);
        salesTransactionTaxDetail.setTaxGroup("a123");
        salesTransactionTaxDetail.setTaxAmountSign("b");
        salesTransactionTaxDetail.setTaxRate(new BigDecimal(12));
        salesTransactionTaxDetail
                .setTaxAmount(ErrorEvacuationTableDataConverterTestHelper.getPrice());

        salesTransactionTaxDetailList.add(salesTransactionTaxDetail);
        return salesTransactionTaxDetailList;
    }

    public static List<SalesTransactionTotal> makeSalesTransactionTotal() {
        List<SalesTransactionTotal> salesTransactionTotalList = new ArrayList<>();
        SalesTransactionTotal salesTransactionTotal = new SalesTransactionTotal();

        salesTransactionTotal.setTotalAmountSubNumber(9999);
        salesTransactionTotal.setTotalType("a123456789");
        salesTransactionTotal
                .setTotalAmountTaxExcluded(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        salesTransactionTotal
                .setTotalAmountTaxIncluded(ErrorEvacuationTableDataConverterTestHelper.getPrice());
        salesTransactionTotal.setConsumptionTaxRate(new BigDecimal(12));
        salesTransactionTotal.setSalesTransactionInformation1("z1234567890121");
        salesTransactionTotal.setSalesTransactionInformation2("z1234567890122");
        salesTransactionTotal.setSalesTransactionInformation3("z1234567890123");

        salesTransactionTotalList.add(salesTransactionTotal);
        return salesTransactionTotalList;
    }

    public static ErrorEvacuationSalesOrderInformation makeErrorEvacuationSalesOrderInformation() {
        ErrorEvacuationSalesOrderInformation errorEvacuationSalesOrderInformationEntity =
                new ErrorEvacuationSalesOrderInformation();
        errorEvacuationSalesOrderInformationEntity.setTransactionId("salesTransactionErrorId01");
        errorEvacuationSalesOrderInformationEntity
                .setIntegratedOrderId("320581198607101614abcdefghi");
        errorEvacuationSalesOrderInformationEntity.setSalesTransactionErrorId("20181212T111111000");
        errorEvacuationSalesOrderInformationEntity
                .setOrderBarcodeNumber("320581198607101614abcdefgh");
        errorEvacuationSalesOrderInformationEntity.setStoreCode("1234567890");
        errorEvacuationSalesOrderInformationEntity.setSystemBrandCode("1234");
        errorEvacuationSalesOrderInformationEntity.setSystemBusinessCode("1234");
        errorEvacuationSalesOrderInformationEntity.setSystemCountryCode("USA");
        errorEvacuationSalesOrderInformationEntity.setChannelCode("A1");
        errorEvacuationSalesOrderInformationEntity.setUpdateType("ERRORERROR");
        errorEvacuationSalesOrderInformationEntity.setCustomerId("320581198607101614123456123456");
        errorEvacuationSalesOrderInformationEntity.setOrderConfirmationBusinessDate("2017-12-12");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        errorEvacuationSalesOrderInformationEntity.setOrderConfirmationDateTime(nowDateTime);
        errorEvacuationSalesOrderInformationEntity.setErrorCheckType(9);
        errorEvacuationSalesOrderInformationEntity.setDataAlterationSalesLinkageType(9);
        errorEvacuationSalesOrderInformationEntity.setDataAlterationBackboneLinkageType(9);
        errorEvacuationSalesOrderInformationEntity.setDataAlterationEditingFlag(true);
        errorEvacuationSalesOrderInformationEntity
                .setDataAlterationUserId("UUU000000000000000000000user01");
        errorEvacuationSalesOrderInformationEntity.setCreateUserId("user01");
        errorEvacuationSalesOrderInformationEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesOrderInformationEntity.setCreateProgramId("SLS0300101");
        errorEvacuationSalesOrderInformationEntity.setUpdateUserId("user01");
        errorEvacuationSalesOrderInformationEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesOrderInformationEntity.setUpdateProgramId("SLS0300101");

        return errorEvacuationSalesOrderInformationEntity;
    }

    public static ErrorEvacuationSalesTransactionHeader makeErrorEvacuationSalesTransactionHeader() {
        ErrorEvacuationSalesTransactionHeader errorEvacuationSalesTransactionHeaderEntity =
                new ErrorEvacuationSalesTransactionHeader();
        errorEvacuationSalesTransactionHeaderEntity.setTransactionId("salesTransactionErrorId02");
        errorEvacuationSalesTransactionHeaderEntity.setOrderSubNumber(2);
        errorEvacuationSalesTransactionHeaderEntity
                .setSalesTransactionId("a00000000000000000000000000001");
        errorEvacuationSalesTransactionHeaderEntity
                .setSalesTransactionErrorId("orignialSalesTransactionErrorId02");
        errorEvacuationSalesTransactionHeaderEntity
                .setIntegratedOrderId("320581198607101614abcdefghi");
        errorEvacuationSalesTransactionHeaderEntity.setSalesTransactionSubNumber(1);
        errorEvacuationSalesTransactionHeaderEntity.setStoreCode("a123456789");
        LocalDateTime nowDateTime1 = LocalDateTime.of(2018, 02, 27, 9, 27, 45);
        errorEvacuationSalesTransactionHeaderEntity.setDataCreationDateTime(nowDateTime1);
        errorEvacuationSalesTransactionHeaderEntity.setDataCreationBusinessDate("2018-12-12");
        errorEvacuationSalesTransactionHeaderEntity.setCashRegisterNo(5);
        errorEvacuationSalesTransactionHeaderEntity.setReceiptNo("a123456789");
        errorEvacuationSalesTransactionHeaderEntity.setSalesLinkageType(3);
        errorEvacuationSalesTransactionHeaderEntity.setSalesTransactionType("a00002");
        errorEvacuationSalesTransactionHeaderEntity.setReturnType(4);
        errorEvacuationSalesTransactionHeaderEntity.setSystemBrandCode("A001");
        errorEvacuationSalesTransactionHeaderEntity.setSystemBusinessCode("A002");
        errorEvacuationSalesTransactionHeaderEntity.setSystemCountryCode("USA");
        errorEvacuationSalesTransactionHeaderEntity.setChannelCode("a1");
        errorEvacuationSalesTransactionHeaderEntity.setOrderStatus("co1");
        errorEvacuationSalesTransactionHeaderEntity.setOrderSubstatus("co2");
        errorEvacuationSalesTransactionHeaderEntity.setOrderStatusUpdateDate("2018-12-12");
        errorEvacuationSalesTransactionHeaderEntity.setOrderStatusLastUpdateDateTime(nowDateTime1);
        errorEvacuationSalesTransactionHeaderEntity.setOrderCancelledDateTime(nowDateTime1);
        errorEvacuationSalesTransactionHeaderEntity.setBookingStoreCode("123456789C");
        errorEvacuationSalesTransactionHeaderEntity.setBookingStoreSystemBrandCode("abc5");
        errorEvacuationSalesTransactionHeaderEntity.setBookingStoreSystemBusinessCode("abc6");
        errorEvacuationSalesTransactionHeaderEntity.setBookingStoreSystemCountryCode("USA");
        errorEvacuationSalesTransactionHeaderEntity.setPaymentStatus("co3");
        errorEvacuationSalesTransactionHeaderEntity.setPaymentStoreCode("b234567890");
        errorEvacuationSalesTransactionHeaderEntity.setPaymentStoreSystemBrandCode("cas1");
        errorEvacuationSalesTransactionHeaderEntity.setPaymentStoreSystemBusinessCode("cas2");
        errorEvacuationSalesTransactionHeaderEntity.setPaymentStoreSystemCountryCode("USA");
        errorEvacuationSalesTransactionHeaderEntity.setTransferOutStatus("co6");
        errorEvacuationSalesTransactionHeaderEntity.setShipmentStatus("co4");
        errorEvacuationSalesTransactionHeaderEntity.setShipmentStoreCode("123456789D");
        errorEvacuationSalesTransactionHeaderEntity.setShipmentStoreSystemBrandCode("abc7");
        errorEvacuationSalesTransactionHeaderEntity.setShipmentStoreSystemBusinessCode("abc8");
        errorEvacuationSalesTransactionHeaderEntity.setShipmentStoreSystemCountryCode("USA");
        errorEvacuationSalesTransactionHeaderEntity.setReceivingStatus("co5");
        errorEvacuationSalesTransactionHeaderEntity.setReceiptStoreCode("123456789E");
        errorEvacuationSalesTransactionHeaderEntity.setReceiptStoreSystemBrandCode("abc9");
        errorEvacuationSalesTransactionHeaderEntity.setReceiptStoreSystemBusinessCode("abd2");
        errorEvacuationSalesTransactionHeaderEntity.setReceiptStoreSystemCountryCode("USA");
        errorEvacuationSalesTransactionHeaderEntity.setCustomerId("32058119860710161412345612345a");
        errorEvacuationSalesTransactionHeaderEntity
                .setOrderNumberForStorePayment("a00000000000000001");
        errorEvacuationSalesTransactionHeaderEntity.setAdvanceReceivedStoreCode("chinaappl1");
        errorEvacuationSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBrandCode("A003");
        errorEvacuationSalesTransactionHeaderEntity
                .setAdvanceReceivedStoreSystemBusinessCode("A004");
        errorEvacuationSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemCountryCode("USA");
        errorEvacuationSalesTransactionHeaderEntity.setOperatorCode("abcdefghi2");
        errorEvacuationSalesTransactionHeaderEntity
                .setOriginalTransactionId("320581198607101614abcdef123456");
        errorEvacuationSalesTransactionHeaderEntity.setOriginalCashRegisterNo(6);
        errorEvacuationSalesTransactionHeaderEntity.setOriginalReceiptNo("abcdefghi3");
        errorEvacuationSalesTransactionHeaderEntity
                .setDepositCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionHeaderEntity.setDepositValue(new BigDecimal(1));
        errorEvacuationSalesTransactionHeaderEntity
                .setChangeCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionHeaderEntity.setChangeValue(new BigDecimal(1));
        errorEvacuationSalesTransactionHeaderEntity
                .setReceiptNoForCreditCardCancellation("abcde123456");
        errorEvacuationSalesTransactionHeaderEntity
                .setReceiptNoForCreditCard("320581198607101614abcdef123457");
        errorEvacuationSalesTransactionHeaderEntity.setReceiptIssuedFlag(true);
        errorEvacuationSalesTransactionHeaderEntity.setEReceiptTargetFlag(true);
        errorEvacuationSalesTransactionHeaderEntity
                .setProcessingCompanyCode("320581198607101614ab");
        errorEvacuationSalesTransactionHeaderEntity.setEmployeeSaleFlag(true);
        errorEvacuationSalesTransactionHeaderEntity.setConsistencySalesFlag(true);
        errorEvacuationSalesTransactionHeaderEntity.setCorporateId("320581198607101614ab");
        errorEvacuationSalesTransactionHeaderEntity.setSalesTransactionDiscountFlag(true);
        errorEvacuationSalesTransactionHeaderEntity
                .setSalesTransactionDiscountAmountRateCurrencyCode(
                        Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionHeaderEntity
                .setSalesTransactionDiscountAmountRate(new BigDecimal(1));
        errorEvacuationSalesTransactionHeaderEntity.setTokenCode("a00000000000000000000000000002");
        errorEvacuationSalesTransactionHeaderEntity.setCreateUserId("user02");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 22);
        errorEvacuationSalesTransactionHeaderEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionHeaderEntity.setCreateProgramId("SLS0300102");
        errorEvacuationSalesTransactionHeaderEntity.setUpdateUserId("user02");
        errorEvacuationSalesTransactionHeaderEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionHeaderEntity.setUpdateProgramId("SLS0300102");

        return errorEvacuationSalesTransactionHeaderEntity;
    }

    public static ErrorEvacuationSalesTransactionDetail makeErrorEvacuationSalesTransactionDetail() {
        ErrorEvacuationSalesTransactionDetail errorEvacuationSalesTransactionDetailEntity =
                new ErrorEvacuationSalesTransactionDetail();
        errorEvacuationSalesTransactionDetailEntity.setTransactionId("salesTransactionErrorId03");
        errorEvacuationSalesTransactionDetailEntity.setOrderSubNumber(201);
        errorEvacuationSalesTransactionDetailEntity.setSalesTransactionId("salesTransactionId03");
        errorEvacuationSalesTransactionDetailEntity.setDetailSubNumber(301);
        errorEvacuationSalesTransactionDetailEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId03");
        errorEvacuationSalesTransactionDetailEntity.setItemDetailSubNumber(9901);
        errorEvacuationSalesTransactionDetailEntity.setSalesTransactionType("c00001");
        errorEvacuationSalesTransactionDetailEntity.setSystemBrandCode("ab01");
        errorEvacuationSalesTransactionDetailEntity.setL2ItemCode("abcdefg000000000000000001");
        errorEvacuationSalesTransactionDetailEntity
                .setDisplayL2ItemCode("abcdefg000000000000000002");
        errorEvacuationSalesTransactionDetailEntity.setL2ProductName("a120304789adgdfgdfgdfg");
        errorEvacuationSalesTransactionDetailEntity.setL3ItemCode("abcdefg000000000000000003");
        errorEvacuationSalesTransactionDetailEntity
                .setL3PosProductName("a120304789adgdfgdfgdfdsfd");
        errorEvacuationSalesTransactionDetailEntity.setProductClassification("ITEM");
        errorEvacuationSalesTransactionDetailEntity.setNonMdType(null);
        errorEvacuationSalesTransactionDetailEntity.setNonMdCode(null);
        errorEvacuationSalesTransactionDetailEntity.setServiceCode(null);
        errorEvacuationSalesTransactionDetailEntity.setEpcCode("abcdefg000000000000000004");
        errorEvacuationSalesTransactionDetailEntity.setGDepartmentCode("c00002");
        errorEvacuationSalesTransactionDetailEntity.setMajorCategoryCode("1234");
        errorEvacuationSalesTransactionDetailEntity.setQuantityCode("A");
        errorEvacuationSalesTransactionDetailEntity.setDetailQuantity(new BigDecimal(99));
        errorEvacuationSalesTransactionDetailEntity
                .setItemCostCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity.setItemCostValue(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity.setInitialSellingPrice(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity
                .setBclassPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity.setBclassPrice(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity
                .setNewPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity
                .setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity
                .setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setCalculationUnavailableType("T");
        errorEvacuationSalesTransactionDetailEntity
                .setOrderStatusUpdateDate("2018-02-27T09:27:45Z");
        LocalDateTime nowDateTime1 = LocalDateTime.of(2018, 02, 27, 9, 27, 45);
        errorEvacuationSalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(nowDateTime1);
        errorEvacuationSalesTransactionDetailEntity.setOrderStatus("aa2");
        errorEvacuationSalesTransactionDetailEntity.setOrderSubstatus("aa3");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreCode("a234567801");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreSystemBrandCode("ab01");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreSystemBusinessCode("ab02");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreSystemCountryCode("USA");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreCode("a234567802");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreSystemBrandCode("ab03");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("ab04");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreSystemCountryCode("USB");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreCode("a234567803");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreSystemBrandCode("ab05");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("ab06");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreSystemCountryCode("USC");
        errorEvacuationSalesTransactionDetailEntity
                .setContributionSalesRepresentative("a234567804");
        errorEvacuationSalesTransactionDetailEntity.setCustomerId("a20581198607101614123456123456");
        errorEvacuationSalesTransactionDetailEntity
                .setBundlePurchaseApplicableQuantity(new BigDecimal(5672));
        errorEvacuationSalesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity
                .setBundlePurchaseApplicablePrice(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setBundlePurchaseIndex(26);
        errorEvacuationSalesTransactionDetailEntity.setLimitedAmountPromotionCount(99);
        errorEvacuationSalesTransactionDetailEntity.setStoreItemDiscountType("QW");
        errorEvacuationSalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity.setStoreItemDiscountSetting(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setStoreBundleSaleFlag(true);
        errorEvacuationSalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity.setStoreBundleSalePrice(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setSetSalesDetailIndex(1234);
        errorEvacuationSalesTransactionDetailEntity.setTaxationType("a234567809");
        errorEvacuationSalesTransactionDetailEntity.setTaxSystemType("a234567808");
        errorEvacuationSalesTransactionDetailEntity.setCreateUserId("user03");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        errorEvacuationSalesTransactionDetailEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionDetailEntity.setCreateProgramId("SLS0300103");
        errorEvacuationSalesTransactionDetailEntity.setUpdateUserId("user03");
        errorEvacuationSalesTransactionDetailEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionDetailEntity.setUpdateProgramId("SLS0300103");

        return errorEvacuationSalesTransactionDetailEntity;
    }

    public static ErrorEvacuationSalesTransactionDetail makeErrorEvacuationSalesTransactionDetailForInsertOutside() {
        ErrorEvacuationSalesTransactionDetail errorEvacuationSalesTransactionDetailEntity =
                new ErrorEvacuationSalesTransactionDetail();
        errorEvacuationSalesTransactionDetailEntity.setTransactionId("salesTransactionErrorId04");
        errorEvacuationSalesTransactionDetailEntity.setOrderSubNumber(201);
        errorEvacuationSalesTransactionDetailEntity.setSalesTransactionId("salesTransactionId04");
        errorEvacuationSalesTransactionDetailEntity.setDetailSubNumber(301);
        errorEvacuationSalesTransactionDetailEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId04");
        errorEvacuationSalesTransactionDetailEntity.setItemDetailSubNumber(9901);
        errorEvacuationSalesTransactionDetailEntity
                .setSalesTransactionType("salesTransactionType04");
        errorEvacuationSalesTransactionDetailEntity.setSystemBrandCode(null);
        errorEvacuationSalesTransactionDetailEntity.setL2ItemCode(null);
        errorEvacuationSalesTransactionDetailEntity.setDisplayL2ItemCode(null);
        errorEvacuationSalesTransactionDetailEntity.setL2ProductName(null);
        errorEvacuationSalesTransactionDetailEntity.setL3ItemCode(null);
        errorEvacuationSalesTransactionDetailEntity.setL3PosProductName("abcde2222205865656");
        errorEvacuationSalesTransactionDetailEntity.setProductClassification("NMITEM");
        errorEvacuationSalesTransactionDetailEntity.setNonMdType("c00002");
        errorEvacuationSalesTransactionDetailEntity.setNonMdCode("abcdc00002abcdc00002a0001");
        errorEvacuationSalesTransactionDetailEntity.setServiceCode("a0001");
        errorEvacuationSalesTransactionDetailEntity.setEpcCode(null);
        errorEvacuationSalesTransactionDetailEntity.setGDepartmentCode(null);
        errorEvacuationSalesTransactionDetailEntity.setMajorCategoryCode(null);
        errorEvacuationSalesTransactionDetailEntity.setQuantityCode("A");
        errorEvacuationSalesTransactionDetailEntity.setDetailQuantity(new BigDecimal(3333));
        errorEvacuationSalesTransactionDetailEntity.setItemCostCurrencyCode(null);
        errorEvacuationSalesTransactionDetailEntity.setItemCostValue(null);
        errorEvacuationSalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode(null);
        errorEvacuationSalesTransactionDetailEntity.setInitialSellingPrice(null);
        errorEvacuationSalesTransactionDetailEntity.setBclassPriceCurrencyCode(null);
        errorEvacuationSalesTransactionDetailEntity.setBclassPrice(null);
        errorEvacuationSalesTransactionDetailEntity
                .setNewPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity
                .setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity
                .setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setCalculationUnavailableType("d000000001");
        errorEvacuationSalesTransactionDetailEntity.setOrderStatusUpdateDate("2018-12-12");
        LocalDateTime nowDateTime1 = LocalDateTime.of(2018, 02, 27, 9, 27, 45);
        errorEvacuationSalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(nowDateTime1);
        errorEvacuationSalesTransactionDetailEntity.setOrderStatus("co1");
        errorEvacuationSalesTransactionDetailEntity.setOrderSubstatus("co2");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreCode("b000000001");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreSystemBrandCode("s001");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreSystemBusinessCode("s002");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreSystemCountryCode("USA");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreCode("b000000002");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreSystemBrandCode("s003");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("s004");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreSystemCountryCode("USB");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreCode("b000000003");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreSystemBrandCode("s005");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("s006");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreSystemCountryCode("USC");
        errorEvacuationSalesTransactionDetailEntity
                .setContributionSalesRepresentative("b000000004");
        errorEvacuationSalesTransactionDetailEntity.setCustomerId(null);
        errorEvacuationSalesTransactionDetailEntity.setBundlePurchaseApplicableQuantity(null);
        errorEvacuationSalesTransactionDetailEntity
                .setBundlePurchaseApplicablePriceCurrencyCode(null);
        errorEvacuationSalesTransactionDetailEntity.setBundlePurchaseApplicablePrice(null);
        errorEvacuationSalesTransactionDetailEntity.setBundlePurchaseIndex(null);
        errorEvacuationSalesTransactionDetailEntity.setLimitedAmountPromotionCount(null);
        errorEvacuationSalesTransactionDetailEntity.setStoreItemDiscountType(null);
        errorEvacuationSalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode(null);
        errorEvacuationSalesTransactionDetailEntity.setStoreItemDiscountSetting(null);
        errorEvacuationSalesTransactionDetailEntity.setStoreBundleSaleFlag(false);
        errorEvacuationSalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode(null);
        errorEvacuationSalesTransactionDetailEntity.setStoreBundleSalePrice(null);
        errorEvacuationSalesTransactionDetailEntity.setSetSalesDetailIndex(null);
        errorEvacuationSalesTransactionDetailEntity.setTaxationType("b000000005");
        errorEvacuationSalesTransactionDetailEntity.setTaxSystemType("b000000006");
        errorEvacuationSalesTransactionDetailEntity.setCreateUserId("user04");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 24);
        errorEvacuationSalesTransactionDetailEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionDetailEntity.setCreateProgramId("SLS0300104");
        errorEvacuationSalesTransactionDetailEntity.setUpdateUserId("user04");
        errorEvacuationSalesTransactionDetailEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionDetailEntity.setUpdateProgramId("SLS0300104");

        return errorEvacuationSalesTransactionDetailEntity;
    }

    public static ErrorEvacuationSalesTransactionDetailInfo makeErrorEvacuationSalesTransactionDetailInfo() {
        ErrorEvacuationSalesTransactionDetailInfo errorEvacuationSalesTransactionDetailInfoEntity =
                new ErrorEvacuationSalesTransactionDetailInfo();

        errorEvacuationSalesTransactionDetailInfoEntity
                .setTransactionId("salesTransactionErrorId05");
        errorEvacuationSalesTransactionDetailInfoEntity.setOrderSubNumber(201);
        errorEvacuationSalesTransactionDetailInfoEntity
                .setSalesTransactionId("salesTransactionId05");
        errorEvacuationSalesTransactionDetailInfoEntity.setDetailSubNumber(301);
        errorEvacuationSalesTransactionDetailInfoEntity
                .setSalesTransactionErrorId("orignialSalesTransactionErrorId05");
        errorEvacuationSalesTransactionDetailInfoEntity.setItemDetailSubNumber(9901);
        errorEvacuationSalesTransactionDetailInfoEntity.setKeyCode("a1234567890123456789");
        errorEvacuationSalesTransactionDetailInfoEntity.setCodeValue1("a123456789012345678value1");
        errorEvacuationSalesTransactionDetailInfoEntity.setCodeValue2("a123456789012345678value2");
        errorEvacuationSalesTransactionDetailInfoEntity.setCodeValue3("a123456789012345678value3");
        errorEvacuationSalesTransactionDetailInfoEntity.setCodeValue4("a123456789012345678value4");
        errorEvacuationSalesTransactionDetailInfoEntity.setName1("1234567890name1");
        errorEvacuationSalesTransactionDetailInfoEntity.setName2("1234567890name2");
        errorEvacuationSalesTransactionDetailInfoEntity.setName3("1234567890name3");
        errorEvacuationSalesTransactionDetailInfoEntity.setName4("1234567890name4");
        errorEvacuationSalesTransactionDetailInfoEntity.setCreateUserId("user05");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 25);
        errorEvacuationSalesTransactionDetailInfoEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionDetailInfoEntity.setCreateProgramId("SLS0300105");
        errorEvacuationSalesTransactionDetailInfoEntity.setUpdateUserId("user05");
        errorEvacuationSalesTransactionDetailInfoEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionDetailInfoEntity.setUpdateProgramId("SLS0300105");

        return errorEvacuationSalesTransactionDetailInfoEntity;
    }

    public static ErrorEvacuationSalesTransactionDiscount makeEvacuationSalesTransactionDiscount() {
        ErrorEvacuationSalesTransactionDiscount errorEvacuationSalesTransactionDiscountEntity =
                new ErrorEvacuationSalesTransactionDiscount();
        errorEvacuationSalesTransactionDiscountEntity.setTransactionId("salesTransactionErrorId06");
        errorEvacuationSalesTransactionDiscountEntity.setOrderSubNumber(201);
        errorEvacuationSalesTransactionDiscountEntity.setSalesTransactionId("salesTransactionId06");
        errorEvacuationSalesTransactionDiscountEntity.setDetailSubNumber(301);
        errorEvacuationSalesTransactionDiscountEntity.setPromotionType("a10001");
        errorEvacuationSalesTransactionDiscountEntity.setPromotionNo("0");
        errorEvacuationSalesTransactionDiscountEntity.setStoreDiscountType("a10002");
        errorEvacuationSalesTransactionDiscountEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId06");
        errorEvacuationSalesTransactionDiscountEntity.setItemDiscountSubNumber(9901);
        errorEvacuationSalesTransactionDiscountEntity.setQuantityCode("n");
        errorEvacuationSalesTransactionDiscountEntity.setDiscountQuantity(10);
        errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDiscountEntity
                .setDiscountAmountTaxExcluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDiscountEntity
                .setDiscountAmountTaxIncluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDiscountEntity.setCreateUserId("user06");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 26);
        errorEvacuationSalesTransactionDiscountEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionDiscountEntity.setCreateProgramId("SLS0300106");
        errorEvacuationSalesTransactionDiscountEntity.setUpdateUserId("user06");
        errorEvacuationSalesTransactionDiscountEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionDiscountEntity.setUpdateProgramId("SLS0300106");

        return errorEvacuationSalesTransactionDiscountEntity;
    }

    public static ErrorEvacuationSalesTransactionTax makeErrorEvacuationSalesTransactionTax() {
        ErrorEvacuationSalesTransactionTax errorEvacuationSalesTransactionTaxEntity =
                new ErrorEvacuationSalesTransactionTax();
        errorEvacuationSalesTransactionTaxEntity.setTransactionId("salesTransactionErrorId07");
        errorEvacuationSalesTransactionTaxEntity.setOrderSubNumber(201);
        errorEvacuationSalesTransactionTaxEntity.setSalesTransactionId("salesTransactionId07");
        errorEvacuationSalesTransactionTaxEntity.setDetailSubNumber(301);
        errorEvacuationSalesTransactionTaxEntity.setTaxGroup("a100000001");
        errorEvacuationSalesTransactionTaxEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId07");
        errorEvacuationSalesTransactionTaxEntity.setTaxSubNumber(9901);
        errorEvacuationSalesTransactionTaxEntity.setTaxAmountSign("s");
        errorEvacuationSalesTransactionTaxEntity
                .setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTaxEntity.setTaxRate(new BigDecimal(1221));
        errorEvacuationSalesTransactionTaxEntity.setTaxName("a100000002");
        errorEvacuationSalesTransactionTaxEntity.setCreateUserId("user07");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 27);
        errorEvacuationSalesTransactionTaxEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTaxEntity.setCreateProgramId("SLS0300107");
        errorEvacuationSalesTransactionTaxEntity.setUpdateUserId("user07");
        errorEvacuationSalesTransactionTaxEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTaxEntity.setUpdateProgramId("SLS0300107");

        return errorEvacuationSalesTransactionTaxEntity;
    }

    public static ErrorEvacuationSalesTransactionDiscount makeErrorEvacuationSalesTransactionDiscountForInsert() {
        ErrorEvacuationSalesTransactionDiscount errorEvacuationSalesTransactionDiscountEntity =
                new ErrorEvacuationSalesTransactionDiscount();
        errorEvacuationSalesTransactionDiscountEntity.setTransactionId("salesTransactionErrorId08");
        errorEvacuationSalesTransactionDiscountEntity.setOrderSubNumber(201);
        errorEvacuationSalesTransactionDiscountEntity.setSalesTransactionId("salesTransactionId08");
        errorEvacuationSalesTransactionDiscountEntity.setDetailSubNumber(301);
        errorEvacuationSalesTransactionDiscountEntity.setPromotionType("c00001");
        errorEvacuationSalesTransactionDiscountEntity.setPromotionNo("a000000001");
        errorEvacuationSalesTransactionDiscountEntity.setStoreDiscountType("c00002");
        errorEvacuationSalesTransactionDiscountEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId08");
        errorEvacuationSalesTransactionDiscountEntity.setItemDiscountSubNumber(9901);
        errorEvacuationSalesTransactionDiscountEntity.setQuantityCode("A");
        errorEvacuationSalesTransactionDiscountEntity.setDiscountQuantity(45612);
        errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDiscountEntity
                .setDiscountAmountTaxExcluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionDiscountEntity
                .setDiscountAmountTaxIncluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDiscountEntity.setCreateUserId("user08");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 28);
        errorEvacuationSalesTransactionDiscountEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionDiscountEntity.setCreateProgramId("SLS0300108");
        errorEvacuationSalesTransactionDiscountEntity.setUpdateUserId("user08");
        errorEvacuationSalesTransactionDiscountEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionDiscountEntity.setUpdateProgramId("SLS0300108");

        return errorEvacuationSalesTransactionDiscountEntity;
    }

    public static ErrorEvacuationSalesTransactionTax makeErrorEvacuationSalesTransactionTaxEntityForInsert() {
        ErrorEvacuationSalesTransactionTax errorEvacuationSalesTransactionTaxEntity =
                new ErrorEvacuationSalesTransactionTax();
        errorEvacuationSalesTransactionTaxEntity.setTransactionId("salesTransactionErrorId09");
        errorEvacuationSalesTransactionTaxEntity.setOrderSubNumber(201);
        errorEvacuationSalesTransactionTaxEntity.setSalesTransactionId("salesTransactionId09");
        errorEvacuationSalesTransactionTaxEntity.setDetailSubNumber(301);
        errorEvacuationSalesTransactionTaxEntity.setTaxGroup("a000000001");
        errorEvacuationSalesTransactionTaxEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId09");
        errorEvacuationSalesTransactionTaxEntity.setTaxSubNumber(9901);
        errorEvacuationSalesTransactionTaxEntity.setTaxAmountSign("W");
        errorEvacuationSalesTransactionTaxEntity
                .setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTaxEntity.setTaxRate(new BigDecimal(9999));
        errorEvacuationSalesTransactionTaxEntity.setTaxName("dgsdgsdg0000ds00001");
        errorEvacuationSalesTransactionTaxEntity.setCreateUserId("user09");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        errorEvacuationSalesTransactionTaxEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTaxEntity.setCreateProgramId("SLS0300109");
        errorEvacuationSalesTransactionTaxEntity.setUpdateUserId("user09");
        errorEvacuationSalesTransactionTaxEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTaxEntity.setUpdateProgramId("SLS0300109");

        return errorEvacuationSalesTransactionTaxEntity;
    }

    public static ErrorEvacuationSalesTransactionTender makeErrorEvacuationSalesTransactionTenderForInsert() {
        ErrorEvacuationSalesTransactionTender errorEvacuationSalesTransactionTenderEntity =
                new ErrorEvacuationSalesTransactionTender();
        errorEvacuationSalesTransactionTenderEntity.setTransactionId("salesTransactionErrorId10");
        errorEvacuationSalesTransactionTenderEntity.setOrderSubNumber(201);
        errorEvacuationSalesTransactionTenderEntity.setSalesTransactionId("salesTransactionId10");
        errorEvacuationSalesTransactionTenderEntity.setTenderGroup("a20351");
        errorEvacuationSalesTransactionTenderEntity.setTenderId("201802");
        errorEvacuationSalesTransactionTenderEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId10");
        errorEvacuationSalesTransactionTenderEntity.setTenderSubNumber(301);
        errorEvacuationSalesTransactionTenderEntity.setPaymentSign("c");
        errorEvacuationSalesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionTenderEntity
                .setTaxIncludedPaymentAmountValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTenderEntity.setCreateUserId("user10");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 30);
        errorEvacuationSalesTransactionTenderEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTenderEntity.setCreateProgramId("SLS0300110");
        errorEvacuationSalesTransactionTenderEntity.setUpdateUserId("user10");
        errorEvacuationSalesTransactionTenderEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTenderEntity.setUpdateProgramId("SLS0300110");

        return errorEvacuationSalesTransactionTenderEntity;
    }

    public static ErrorEvacuationSalesTransactionTenderInfo makeErrorEvacuationSalesTransactionTenderInfoForInsert() {
        ErrorEvacuationSalesTransactionTenderInfo errorEvacuationSalesTransactionTenderInfoEntity =
                new ErrorEvacuationSalesTransactionTenderInfo();
        errorEvacuationSalesTransactionTenderInfoEntity.setTenderSubNumber(1);
        errorEvacuationSalesTransactionTenderInfoEntity
                .setTransactionId("salesTransactionErrorId11");
        errorEvacuationSalesTransactionTenderInfoEntity.setOrderSubNumber(201);
        errorEvacuationSalesTransactionTenderInfoEntity
                .setSalesTransactionId("salesTransactionId11");
        errorEvacuationSalesTransactionTenderInfoEntity.setTenderGroup("tenderGroupId11");
        errorEvacuationSalesTransactionTenderInfoEntity.setTenderId("301");
        errorEvacuationSalesTransactionTenderInfoEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId11");
        errorEvacuationSalesTransactionTenderInfoEntity
                .setDiscountValueCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionTenderInfoEntity.setDiscountValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTenderInfoEntity.setDiscountRate(new BigDecimal(2018));
        errorEvacuationSalesTransactionTenderInfoEntity
                .setDiscountCodeIdCorporateId("a00000000000000000000000000001");
        errorEvacuationSalesTransactionTenderInfoEntity.setCouponType("a00001");
        errorEvacuationSalesTransactionTenderInfoEntity.setCouponDiscountAmountSettingCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionTenderInfoEntity
                .setCouponMinUsageAmountThresholdValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTenderInfoEntity
                .setCouponMinUsageAmountThresholdCurrencyCode(
                        Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionTenderInfoEntity
                .setCouponDiscountAmountSettingValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTenderInfoEntity
                .setCouponUserId("a00000000000000000000000000002");
        errorEvacuationSalesTransactionTenderInfoEntity.setCardNo("a00000000000000000000000000003");
        errorEvacuationSalesTransactionTenderInfoEntity
                .setCreditApprovalCode("a00000000000000000000000000004");
        errorEvacuationSalesTransactionTenderInfoEntity
                .setCreditProcessingSerialNumber("a00000000000000000000000000005");
        errorEvacuationSalesTransactionTenderInfoEntity
                .setCreditPaymentType("a00000000000000000000000000006");
        errorEvacuationSalesTransactionTenderInfoEntity.setCreditPaymentCount(24567);
        errorEvacuationSalesTransactionTenderInfoEntity
                .setCreditAffiliatedStoreNumber("a00000000000000000000000000007");
        errorEvacuationSalesTransactionTenderInfoEntity.setCreateUserId("user11");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 31);
        errorEvacuationSalesTransactionTenderInfoEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTenderInfoEntity.setCreateProgramId("SLS0300111");
        errorEvacuationSalesTransactionTenderInfoEntity.setUpdateUserId("user11");
        errorEvacuationSalesTransactionTenderInfoEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTenderInfoEntity.setUpdateProgramId("SLS0300111");

        return errorEvacuationSalesTransactionTenderInfoEntity;
    }

    public static ErrorEvacuationSalesTransactionTax makeErrorEvacuationSalesTransactionTaxForInsert() {
        ErrorEvacuationSalesTransactionTax errorEvacuationSalesTransactionTaxEntity =
                new ErrorEvacuationSalesTransactionTax();
        errorEvacuationSalesTransactionTaxEntity.setTransactionId("salesTransactionErrorId12");
        errorEvacuationSalesTransactionTaxEntity.setOrderSubNumber(201);
        errorEvacuationSalesTransactionTaxEntity.setSalesTransactionId("salesTransactionId12");
        errorEvacuationSalesTransactionTaxEntity.setDetailSubNumber(301);
        errorEvacuationSalesTransactionTaxEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId12");
        errorEvacuationSalesTransactionTaxEntity.setTaxSubNumber(401);
        errorEvacuationSalesTransactionTaxEntity
                .setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));

        errorEvacuationSalesTransactionTaxEntity.setCreateUserId("user12");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        errorEvacuationSalesTransactionTaxEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTaxEntity.setCreateProgramId("SLS0300112");
        errorEvacuationSalesTransactionTaxEntity.setUpdateUserId("user12");
        errorEvacuationSalesTransactionTaxEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTaxEntity.setUpdateProgramId("SLS0300112");

        return errorEvacuationSalesTransactionTaxEntity;
    }

    public static ErrorEvacuationSalesTransactionTotalAmount makeErrorEvacuationSalesTransactionTotalAmountForInsert() {
        ErrorEvacuationSalesTransactionTotalAmount errorEvacuationSalesTransactionTotalAmountEntity =
                new ErrorEvacuationSalesTransactionTotalAmount();
        errorEvacuationSalesTransactionTotalAmountEntity
                .setTransactionId("salesTransactionErrorId13");
        errorEvacuationSalesTransactionTotalAmountEntity.setOrderSubNumber(201);
        errorEvacuationSalesTransactionTotalAmountEntity
                .setSalesTransactionId("salesTransactionId13");
        errorEvacuationSalesTransactionTotalAmountEntity.setTotalType("a123456789");
        errorEvacuationSalesTransactionTotalAmountEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId13");
        errorEvacuationSalesTransactionTotalAmountEntity.setTotalAmountSubNumber(301);
        errorEvacuationSalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionTotalAmountEntity
                .setTotalAmountTaxExcludedValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        errorEvacuationSalesTransactionTotalAmountEntity
                .setTotalAmountTaxIncludedValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTotalAmountEntity.setTaxRate(new BigDecimal(12));
        errorEvacuationSalesTransactionTotalAmountEntity
                .setSalesTransactionInformation1("z1234567890121");
        errorEvacuationSalesTransactionTotalAmountEntity
                .setSalesTransactionInformation2("z1234567890122");
        errorEvacuationSalesTransactionTotalAmountEntity
                .setSalesTransactionInformation3("z1234567890123");
        errorEvacuationSalesTransactionTotalAmountEntity.setCreateUserId("user13");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 33);
        errorEvacuationSalesTransactionTotalAmountEntity.setCreateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTotalAmountEntity.setCreateProgramId("SLS0300113");
        errorEvacuationSalesTransactionTotalAmountEntity.setUpdateUserId("user13");
        errorEvacuationSalesTransactionTotalAmountEntity.setUpdateDatetime(nowDateTime);
        errorEvacuationSalesTransactionTotalAmountEntity.setUpdateProgramId("SLS0300113");

        return errorEvacuationSalesTransactionTotalAmountEntity;
    }

    private static Price getPrice() {
        Price price = new Price();
        price.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price.setValue(new BigDecimal(1));
        return price;
    }
}
