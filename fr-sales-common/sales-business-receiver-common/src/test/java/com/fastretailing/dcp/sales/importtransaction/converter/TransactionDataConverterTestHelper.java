package com.fastretailing.dcp.sales.importtransaction.converter;

import java.math.BigDecimal;
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
import com.fastretailing.dcp.storecommon.dto.Price;
import com.fastretailing.dcp.storecommon.util.DateUtility;

/**
 * ErrorEvacuationTableDataConverter JUnit Test class.
 */
public class TransactionDataConverterTestHelper {

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
        importData.setOrderConfirmationBusinessDate("2018-12-12");
        importData.setOrderConfirmationDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-24T11:54:21Z", "UTC"));
        importData.setDataCorrectionEditingFlag(true);
        importData.setDataCorrectionUserId("UUU000000000000000000000user01");
        List<Transaction> transactionList =
                TransactionDataConverterTestHelper.makeAutoTransaction();
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
        transaction.setDataCreationBusinessDate("2018-12-13");
        transaction.setDataCreationDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-27T09:27:45Z", "UTC"));
        transaction.setOrderStatusUpdateDate("2018-12-14");
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
        transaction.setDeposit(TransactionDataConverterTestHelper.getPrice());
        transaction.setChange(TransactionDataConverterTestHelper.getPrice());
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
                TransactionDataConverterTestHelper.getPrice());
        transaction.setConsistencySalesFlag(true);
        transaction.setEmployeeSaleFlag(true);
        transaction.setEReceiptTargetFlag(true);
        List<TransactionItemDetail> transactionItemDetailList =
                TransactionDataConverterTestHelper.makeTransactionItemDetail();
        transaction.setTransactionItemDetailList(transactionItemDetailList);
        List<NonItemDetail> nonItemDetailList =
                TransactionDataConverterTestHelper.makeNonItemDetail();
        transaction.setNonItemDetailList(nonItemDetailList);
        List<SalesTransactionTender> salesTransactionTenderList =
                TransactionDataConverterTestHelper.makeSalesTransactionTender();
        transaction.setSalesTransactionTenderList(salesTransactionTenderList);
        List<SalesTransactionTaxDetail> SalesTransactionTaxDetailList =
                TransactionDataConverterTestHelper.makeSalesTransactionTaxDetail();
        transaction.setSalesTransactionTaxDetailList(SalesTransactionTaxDetailList);
        List<SalesTransactionTotal> SalesTransactionTotalList =
                TransactionDataConverterTestHelper.makeSalesTransactionTotal();
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
        transactionItemDetail.setItemCost(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setInitialSellingPrice(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setBItemSellingPrice(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setItemNewPrice(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemUnitPriceTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemUnitPriceTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemSalesAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemSalesAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setOrderStatusUpdateDate("2018-12-15");
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
        transactionItemDetail.setBundlePurchasePrice(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundlePurchaseIndex(26);
        transactionItemDetail.setLimitedAmountPromotionCount(99);
        transactionItemDetail.setCalculationUnavailableType("T");
        transactionItemDetail.setItemMountDiscountType("QW");
        transactionItemDetail.setItemDiscountAmount(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundleSalesFlag(null);
        transactionItemDetail.setBundleSalesPrice(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundleSalesDetailIndex(1234);
        transactionItemDetail.setItemDetailNumber(1);
        transactionItemDetail.setItemTaxationType("a234567809");
        transactionItemDetail.setItemTaxKind("a234567808");
        List<NonItemDetail> nonItemDetailList =
                TransactionDataConverterTestHelper.makeItemDetailNonItemDetail();
        transactionItemDetail.setNonItemDetailListByItem(nonItemDetailList);
        List<ItemDiscount> itemDiscountList =
                TransactionDataConverterTestHelper.makeItemDetailItemDiscount();
        transactionItemDetail.setItemDiscountList(itemDiscountList);
        List<ItemTaxDetail> itemTaxDetailList =
                TransactionDataConverterTestHelper.makeItemDetailItemTaxDetail();
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
        nonItemDetail.setNonItemUnitPriceTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemUnitPriceTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemNewPrice(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonCalculationNonItemType("d000000001");
        nonItemDetail.setOrderStatusUpdateDate("2018-12-16");
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
        List<NonItemDiscountDetail> nonItemDiscountDetailList = TransactionDataConverterTestHelper
                .makeItemDetailNonItemDetailNonItemDiscountDetail();
        nonItemDetail.setNonItemDiscountDetailList(nonItemDiscountDetailList);
        List<NonItemTaxDetail> nonItemTaxDetailList =
                TransactionDataConverterTestHelper.makeItemDetailNonItemDetailNonItemTaxDetail();
        nonItemDetail.setNonItemTaxDetailList(nonItemTaxDetailList);

        nonItemDetailList.add(nonItemDetail);
        return nonItemDetailList;
    }

    public static List<NonItemDiscountDetail> makeItemDetailNonItemDetailNonItemDiscountDetail() {
        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();

        nonItemDiscountDetail.setNonItemPromotionNumber("9");
        nonItemDiscountDetail.setNonItemPromotionType("a10001");
        nonItemDiscountDetail.setNonItemStoreDiscountType("a10002");
        nonItemDiscountDetail.setNonItemQuantityCode("n");
        nonItemDiscountDetail.setNonItemDiscountQty(45);
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());

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
        nonItemTaxDetail.setNonItemTaxAmt(TransactionDataConverterTestHelper.getPrice());
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
        itemDiscount.setItemDiscountAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        itemDiscount.setItemDiscountAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());

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
        itemTaxDetail.setItemTaxAmt(TransactionDataConverterTestHelper.getPrice());
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
        nonItemDetail.setNonItemUnitPriceTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemUnitPriceTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemNewPrice(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonCalculationNonItemType("d000000001");
        nonItemDetail.setOrderStatusUpdateDate("2018-12-17");
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
                TransactionDataConverterTestHelper.makeNonItemDiscountDetail();
        nonItemDetail.setNonItemDiscountDetailList(nonItemDiscountDetailList);
        List<NonItemTaxDetail> nonItemTaxDetailList =
                TransactionDataConverterTestHelper.makeNonItemTaxDetail();
        nonItemDetail.setNonItemTaxDetailList(nonItemTaxDetailList);

        nonItemDetailList.add(nonItemDetail);
        return nonItemDetailList;
    }

    public static List<NonItemDiscountDetail> makeNonItemDiscountDetail() {
        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();

        nonItemDiscountDetail.setNonItemPromotionNumber("9");
        nonItemDiscountDetail.setNonItemPromotionType("a10001");
        nonItemDiscountDetail.setNonItemStoreDiscountType("a10002");
        nonItemDiscountDetail.setNonItemQuantityCode("n");
        nonItemDiscountDetail.setNonItemDiscountQty(10);
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());

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
        nonItemTaxDetail.setNonItemTaxAmt(TransactionDataConverterTestHelper.getPrice());
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
        salesTransactionTender
                .setTaxIncludedPaymentAmount(TransactionDataConverterTestHelper.getPrice());
        salesTransactionTender
                .setTenderInfoList(TransactionDataConverterTestHelper.makeTenderInfo());

        salesTransactionTenderList.add(salesTransactionTender);
        return salesTransactionTenderList;
    }

    public static TenderInfo makeTenderInfo() {
        TenderInfo tenderInfo = new TenderInfo();

        tenderInfo.setDiscountAmount(TransactionDataConverterTestHelper.getPrice());
        tenderInfo.setDiscountRate(new BigDecimal(2018));
        tenderInfo.setDiscountCodeIdCorporateId("a00000000000000000000000000001");
        tenderInfo.setCouponType("a00001");
        tenderInfo.setCouponDiscountAmountSetting(TransactionDataConverterTestHelper.getPrice());
        tenderInfo.setCouponMinUsageAmountThreshold(TransactionDataConverterTestHelper.getPrice());
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
        salesTransactionTaxDetail.setTaxAmount(TransactionDataConverterTestHelper.getPrice());

        salesTransactionTaxDetailList.add(salesTransactionTaxDetail);
        return salesTransactionTaxDetailList;
    }

    public static List<SalesTransactionTotal> makeSalesTransactionTotal() {
        List<SalesTransactionTotal> salesTransactionTotalList = new ArrayList<>();
        SalesTransactionTotal salesTransactionTotal = new SalesTransactionTotal();

        salesTransactionTotal.setTotalAmountSubNumber(9999);
        salesTransactionTotal.setTotalType("a123456789");
        salesTransactionTotal
                .setTotalAmountTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        salesTransactionTotal
                .setTotalAmountTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        salesTransactionTotal.setConsumptionTaxRate(new BigDecimal(12));
        salesTransactionTotal.setSalesTransactionInformation1("z1234567890121");
        salesTransactionTotal.setSalesTransactionInformation2("z1234567890122");
        salesTransactionTotal.setSalesTransactionInformation3("z1234567890123");

        salesTransactionTotalList.add(salesTransactionTotal);
        return salesTransactionTotalList;
    }

    public static TransactionImportData makeTransactionImportData() {
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
        importData.setOrderConfirmationBusinessDate("20181212");
        importData.setOrderConfirmationDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-24T11:54:21Z", "UTC"));
        importData.setDataCorrectionEditingFlag(true);
        importData.setDataCorrectionUserId("UUU000000000000000000000user01");
        List<Transaction> transactionList =
                TransactionDataConverterTestHelper.makeAutoTransactionExpect();
        importData.setTransactionList(transactionList);
        return importData;
    }

    public static List<Transaction> makeAutoTransactionExpect() {
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
        transaction.setDataCreationBusinessDate("20181213");
        transaction.setDataCreationDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-27T09:27:45Z", "UTC"));
        transaction.setOrderStatusUpdateDate("20181214");
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
        transaction.setDeposit(TransactionDataConverterTestHelper.getPrice());
        transaction.setChange(TransactionDataConverterTestHelper.getPrice());
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
                TransactionDataConverterTestHelper.getPrice());
        transaction.setConsistencySalesFlag(true);
        transaction.setEmployeeSaleFlag(true);
        transaction.setEReceiptTargetFlag(true);
        List<TransactionItemDetail> transactionItemDetailList =
                TransactionDataConverterTestHelper.makeTransactionItemDetailExpect();
        transaction.setTransactionItemDetailList(transactionItemDetailList);
        List<NonItemDetail> nonItemDetailList =
                TransactionDataConverterTestHelper.makeNonItemDetailExpect();
        transaction.setNonItemDetailList(nonItemDetailList);
        List<SalesTransactionTender> salesTransactionTenderList =
                TransactionDataConverterTestHelper.makeSalesTransactionTenderExpect();
        transaction.setSalesTransactionTenderList(salesTransactionTenderList);
        List<SalesTransactionTaxDetail> SalesTransactionTaxDetailList =
                TransactionDataConverterTestHelper.makeSalesTransactionTaxDetailExpect();
        transaction.setSalesTransactionTaxDetailList(SalesTransactionTaxDetailList);
        List<SalesTransactionTotal> SalesTransactionTotalList =
                TransactionDataConverterTestHelper.makeSalesTransactionTotalExpect();
        transaction.setSalesTransactionTotalList(SalesTransactionTotalList);

        transactionList.add(transaction);
        return transactionList;
    }

    public static List<TransactionItemDetail> makeTransactionItemDetailExpect() {
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
        transactionItemDetail.setItemCost(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setInitialSellingPrice(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setBItemSellingPrice(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setItemNewPrice(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemUnitPriceTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemUnitPriceTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemSalesAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemSalesAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setOrderStatusUpdateDate("20181215");
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
        transactionItemDetail.setBundlePurchasePrice(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundlePurchaseIndex(26);
        transactionItemDetail.setLimitedAmountPromotionCount(99);
        transactionItemDetail.setCalculationUnavailableType("T");
        transactionItemDetail.setItemMountDiscountType("QW");
        transactionItemDetail.setItemDiscountAmount(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundleSalesFlag(null);
        transactionItemDetail.setBundleSalesPrice(TransactionDataConverterTestHelper.getPrice());
        transactionItemDetail.setBundleSalesDetailIndex(1234);
        transactionItemDetail.setItemDetailNumber(1);
        transactionItemDetail.setItemTaxationType("a234567809");
        transactionItemDetail.setItemTaxKind("a234567808");
        List<NonItemDetail> nonItemDetailList =
                TransactionDataConverterTestHelper.makeItemDetailNonItemDetailExpect();
        transactionItemDetail.setNonItemDetailListByItem(nonItemDetailList);
        List<ItemDiscount> itemDiscountList =
                TransactionDataConverterTestHelper.makeItemDetailItemDiscountExpect();
        transactionItemDetail.setItemDiscountList(itemDiscountList);
        List<ItemTaxDetail> itemTaxDetailList =
                TransactionDataConverterTestHelper.makeItemDetailItemTaxDetailExpect();
        transactionItemDetail.setItemTaxDetailList(itemTaxDetailList);

        transactionItemDetailList.add(transactionItemDetail);
        return transactionItemDetailList;
    }

    public static List<NonItemDetail> makeItemDetailNonItemDetailExpect() {
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
        nonItemDetail.setNonItemUnitPriceTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemUnitPriceTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemNewPrice(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonCalculationNonItemType("d000000001");
        nonItemDetail.setOrderStatusUpdateDate("20181216");
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
        List<NonItemDiscountDetail> nonItemDiscountDetailList = TransactionDataConverterTestHelper
                .makeItemDetailNonItemDetailNonItemDiscountDetailExpect();
        nonItemDetail.setNonItemDiscountDetailList(nonItemDiscountDetailList);
        List<NonItemTaxDetail> nonItemTaxDetailList = TransactionDataConverterTestHelper
                .makeItemDetailNonItemDetailNonItemTaxDetailExpect();
        nonItemDetail.setNonItemTaxDetailList(nonItemTaxDetailList);

        nonItemDetailList.add(nonItemDetail);
        return nonItemDetailList;
    }

    public static List<NonItemDiscountDetail> makeItemDetailNonItemDetailNonItemDiscountDetailExpect() {
        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();

        nonItemDiscountDetail.setNonItemPromotionNumber("9");
        nonItemDiscountDetail.setNonItemPromotionType("a10001");
        nonItemDiscountDetail.setNonItemStoreDiscountType("a10002");
        nonItemDiscountDetail.setNonItemQuantityCode("n");
        nonItemDiscountDetail.setNonItemDiscountQty(45);
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());

        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        return nonItemDiscountDetailList;
    }

    public static List<NonItemTaxDetail> makeItemDetailNonItemDetailNonItemTaxDetailExpect() {
        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();

        nonItemTaxDetail.setNonItemTaxDetailSubNumber(2341);
        nonItemTaxDetail.setNonItemTaxType("a100000001");
        nonItemTaxDetail.setNonItemTaxName("a100000002");
        nonItemTaxDetail.setNonItemTaxAmountSign("s");
        nonItemTaxDetail.setNonItemTaxAmt(TransactionDataConverterTestHelper.getPrice());
        nonItemTaxDetail.setNonItemTaxRate(new BigDecimal(1568));

        nonItemTaxDetailList.add(nonItemTaxDetail);
        return nonItemTaxDetailList;
    }

    public static List<ItemDiscount> makeItemDetailItemDiscountExpect() {
        List<ItemDiscount> itemDiscountList = new ArrayList<>();
        ItemDiscount itemDiscount = new ItemDiscount();

        itemDiscount.setItemDiscountDetailSubNumber(1501);
        itemDiscount.setItemDiscountSubNumber(1502);
        itemDiscount.setItemPromotionType("c00001");
        itemDiscount.setItemPromotionNumber("a000000001");
        itemDiscount.setItemStoreDiscountType("c00002");
        itemDiscount.setItemQuantityCode("A");
        itemDiscount.setItemDiscountQty(45612);
        itemDiscount.setItemDiscountAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        itemDiscount.setItemDiscountAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());

        itemDiscountList.add(itemDiscount);
        return itemDiscountList;
    }

    public static List<ItemTaxDetail> makeItemDetailItemTaxDetailExpect() {
        List<ItemTaxDetail> itemTaxDetailList = new ArrayList<>();
        ItemTaxDetail itemTaxDetail = new ItemTaxDetail();

        itemTaxDetail.setItemTaxDetailSubNumber(2341);
        itemTaxDetail.setItemTaxSubNumber(2342);
        itemTaxDetail.setItemTaxType("a000000001");
        itemTaxDetail.setItemTaxName("dgsdgsdg0000ds00001");
        itemTaxDetail.setItemTaxAmountSign("W");
        itemTaxDetail.setItemTaxAmt(TransactionDataConverterTestHelper.getPrice());
        itemTaxDetail.setItemTaxRate(new BigDecimal(9999));

        itemTaxDetailList.add(itemTaxDetail);
        return itemTaxDetailList;
    }

    public static List<NonItemDetail> makeNonItemDetailExpect() {
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
        nonItemDetail.setNonItemUnitPriceTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemUnitPriceTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonItemNewPrice(TransactionDataConverterTestHelper.getPrice());
        nonItemDetail.setNonCalculationNonItemType("d000000001");
        nonItemDetail.setOrderStatusUpdateDate("20181217");
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
                TransactionDataConverterTestHelper.makeNonItemDiscountDetailExpect();
        nonItemDetail.setNonItemDiscountDetailList(nonItemDiscountDetailList);
        List<NonItemTaxDetail> nonItemTaxDetailList =
                TransactionDataConverterTestHelper.makeNonItemTaxDetailExpect();
        nonItemDetail.setNonItemTaxDetailList(nonItemTaxDetailList);

        nonItemDetailList.add(nonItemDetail);
        return nonItemDetailList;
    }

    public static List<NonItemDiscountDetail> makeNonItemDiscountDetailExpect() {
        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();

        nonItemDiscountDetail.setNonItemPromotionNumber("9");
        nonItemDiscountDetail.setNonItemPromotionType("a10001");
        nonItemDiscountDetail.setNonItemStoreDiscountType("a10002");
        nonItemDiscountDetail.setNonItemQuantityCode("n");
        nonItemDiscountDetail.setNonItemDiscountQty(10);
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxIncluded(TransactionDataConverterTestHelper.getPrice());

        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        return nonItemDiscountDetailList;
    }

    public static List<NonItemTaxDetail> makeNonItemTaxDetailExpect() {
        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();

        nonItemTaxDetail.setNonItemTaxDetailSubNumber(2201);
        nonItemTaxDetail.setNonItemTaxType("a100000001");
        nonItemTaxDetail.setNonItemTaxName("a100000002");
        nonItemTaxDetail.setNonItemTaxAmountSign("s");
        nonItemTaxDetail.setNonItemTaxAmt(TransactionDataConverterTestHelper.getPrice());
        nonItemTaxDetail.setNonItemTaxRate(new BigDecimal(1221));

        nonItemTaxDetailList.add(nonItemTaxDetail);
        return nonItemTaxDetailList;
    }

    public static List<SalesTransactionTender> makeSalesTransactionTenderExpect() {
        List<SalesTransactionTender> salesTransactionTenderList = new ArrayList<>();
        SalesTransactionTender salesTransactionTender = new SalesTransactionTender();

        salesTransactionTender.setTenderSubNumber(2014);
        salesTransactionTender.setTenderGroupId("a20351");
        salesTransactionTender.setTenderId("201802");
        salesTransactionTender.setPaymentSign("c");
        salesTransactionTender
                .setTaxIncludedPaymentAmount(TransactionDataConverterTestHelper.getPrice());
        salesTransactionTender
                .setTenderInfoList(TransactionDataConverterTestHelper.makeTenderInfoExpect());

        salesTransactionTenderList.add(salesTransactionTender);
        return salesTransactionTenderList;
    }

    public static TenderInfo makeTenderInfoExpect() {
        TenderInfo tenderInfo = new TenderInfo();

        tenderInfo.setDiscountAmount(TransactionDataConverterTestHelper.getPrice());
        tenderInfo.setDiscountRate(new BigDecimal(2018));
        tenderInfo.setDiscountCodeIdCorporateId("a00000000000000000000000000001");
        tenderInfo.setCouponType("a00001");
        tenderInfo.setCouponDiscountAmountSetting(TransactionDataConverterTestHelper.getPrice());
        tenderInfo.setCouponMinUsageAmountThreshold(TransactionDataConverterTestHelper.getPrice());
        tenderInfo.setCouponUserId("a00000000000000000000000000002");
        tenderInfo.setCardNo("a00000000000000000000000000003");
        tenderInfo.setCreditApprovalCode("a00000000000000000000000000004");
        tenderInfo.setCreditProcessingSerialNumber("a00000000000000000000000000005");
        tenderInfo.setCreditPaymentType("a00000000000000000000000000006");
        tenderInfo.setCreditPaymentCount(24567);
        tenderInfo.setCreditAffiliatedStoreNumber("a00000000000000000000000000007");

        return tenderInfo;
    }

    public static List<SalesTransactionTaxDetail> makeSalesTransactionTaxDetailExpect() {
        List<SalesTransactionTaxDetail> salesTransactionTaxDetailList = new ArrayList<>();
        SalesTransactionTaxDetail salesTransactionTaxDetail = new SalesTransactionTaxDetail();

        salesTransactionTaxDetail.setTaxSubNumber(1245);
        salesTransactionTaxDetail.setTaxGroup("a123");
        salesTransactionTaxDetail.setTaxAmountSign("b");
        salesTransactionTaxDetail.setTaxRate(new BigDecimal(12));
        salesTransactionTaxDetail.setTaxAmount(TransactionDataConverterTestHelper.getPrice());

        salesTransactionTaxDetailList.add(salesTransactionTaxDetail);
        return salesTransactionTaxDetailList;
    }

    public static List<SalesTransactionTotal> makeSalesTransactionTotalExpect() {
        List<SalesTransactionTotal> salesTransactionTotalList = new ArrayList<>();
        SalesTransactionTotal salesTransactionTotal = new SalesTransactionTotal();

        salesTransactionTotal.setTotalAmountSubNumber(9999);
        salesTransactionTotal.setTotalType("a123456789");
        salesTransactionTotal
                .setTotalAmountTaxExcluded(TransactionDataConverterTestHelper.getPrice());
        salesTransactionTotal
                .setTotalAmountTaxIncluded(TransactionDataConverterTestHelper.getPrice());
        salesTransactionTotal.setConsumptionTaxRate(new BigDecimal(12));
        salesTransactionTotal.setSalesTransactionInformation1("z1234567890121");
        salesTransactionTotal.setSalesTransactionInformation2("z1234567890122");
        salesTransactionTotal.setSalesTransactionInformation3("z1234567890123");

        salesTransactionTotalList.add(salesTransactionTotal);
        return salesTransactionTotalList;
    }

    private static Price getPrice() {
        Price price = new Price();
        price.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price.setValue(new BigDecimal(1));
        return price;
    }
}
