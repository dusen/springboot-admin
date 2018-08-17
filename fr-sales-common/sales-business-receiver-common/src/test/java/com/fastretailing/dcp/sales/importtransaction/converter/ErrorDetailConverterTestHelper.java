package com.fastretailing.dcp.sales.importtransaction.converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import com.fastretailing.dcp.sales.common.constants.DBItem;
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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderTable;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTotalAmount;
import com.fastretailing.dcp.storecommon.dto.Price;
import com.fastretailing.dcp.storecommon.util.DateUtility;

/**
 * ErrorDetailConverter JUnit Test class.
 */
public class ErrorDetailConverterTestHelper {

    /** Error item table id. */
    private final static String ERROR_ITEM_ID_TABLE = "table_name";

    public static TransactionImportData makeImportData() {
        TransactionImportData importData = new TransactionImportData();
        importData.setUpdateType("ERRORERROR");
        importData.setErrorCheckType(7);
        importData.setDataAlterationSalesLinkageType(8);
        importData.setDataAlterationBackboneLinkageType(9);
        importData.setSalesTransactionErrorId("20181212T111111000");
        importData.setIntegratedOrderId("320581198607101614abcdefgh5");
        importData.setOrderBarcodeNumber("320581198607101614abcdefgh");
        importData.setChannelCode("A1");
        importData.setStoreCode("1234567890");
        importData.setSystemBrandCode("1239");
        importData.setSystemBusinessCode("1234");
        importData.setSystemCountryCode("USA");
        importData.setCustomerId("32058119860710161412345612345s");
        importData.setOrderConfirmationBusinessDate("2017-12-12");
        importData.setOrderConfirmationDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-24T11:54:21Z", "UTC"));
        importData.setDataCorrectionEditingFlag(true);
        importData.setDataCorrectionUserId("UUU000000000000000000000user01");
        List<Transaction> transactionList = ErrorDetailConverterTestHelper.makeAutoTransaction();
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
        transaction.setDeposit(ErrorDetailConverterTestHelper.getPrice());
        transaction.setChange(ErrorDetailConverterTestHelper.getPrice());
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
        transaction
                .setSalesTransactionDiscountAmountRate(ErrorDetailConverterTestHelper.getPrice());
        transaction.setConsistencySalesFlag(true);
        transaction.setEmployeeSaleFlag(true);
        transaction.setEReceiptTargetFlag(true);
        List<TransactionItemDetail> transactionItemDetailList =
                ErrorDetailConverterTestHelper.makeTransactionItemDetail();
        transaction.setTransactionItemDetailList(transactionItemDetailList);
        List<NonItemDetail> nonItemDetailList = ErrorDetailConverterTestHelper.makeNonItemDetail();
        transaction.setNonItemDetailList(nonItemDetailList);
        List<SalesTransactionTender> salesTransactionTenderList =
                ErrorDetailConverterTestHelper.makeSalesTransactionTender();
        transaction.setSalesTransactionTenderList(salesTransactionTenderList);
        List<SalesTransactionTaxDetail> SalesTransactionTaxDetailList =
                ErrorDetailConverterTestHelper.makeSalesTransactionTaxDetail();
        transaction.setSalesTransactionTaxDetailList(SalesTransactionTaxDetailList);
        List<SalesTransactionTotal> SalesTransactionTotalList =
                ErrorDetailConverterTestHelper.makeSalesTransactionTotal();
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
        transactionItemDetail.setItemCost(ErrorDetailConverterTestHelper.getPrice());
        transactionItemDetail.setInitialSellingPrice(ErrorDetailConverterTestHelper.getPrice());
        transactionItemDetail.setBItemSellingPrice(ErrorDetailConverterTestHelper.getPrice());
        transactionItemDetail.setItemNewPrice(ErrorDetailConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemUnitPriceTaxExcluded(ErrorDetailConverterTestHelper.getPrice());
        transactionItemDetail
                .setItemUnitPriceTaxIncluded(ErrorDetailConverterTestHelper.getPrice());
        transactionItemDetail.setItemSalesAmtTaxExcluded(ErrorDetailConverterTestHelper.getPrice());
        transactionItemDetail.setItemSalesAmtTaxIncluded(ErrorDetailConverterTestHelper.getPrice());
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
        transactionItemDetail.setBundlePurchasePrice(ErrorDetailConverterTestHelper.getPrice());
        transactionItemDetail.setBundlePurchaseIndex(26);
        transactionItemDetail.setLimitedAmountPromotionCount(99);
        transactionItemDetail.setCalculationUnavailableType("T");
        transactionItemDetail.setItemMountDiscountType("QW");
        transactionItemDetail.setItemDiscountAmount(ErrorDetailConverterTestHelper.getPrice());
        transactionItemDetail.setBundleSalesFlag(true);
        transactionItemDetail.setBundleSalesPrice(ErrorDetailConverterTestHelper.getPrice());
        transactionItemDetail.setBundleSalesDetailIndex(1234);
        transactionItemDetail.setItemDetailNumber(1);
        transactionItemDetail.setItemTaxationType("a234567809");
        transactionItemDetail.setItemTaxKind("a234567808");
        List<NonItemDetail> nonItemDetailList =
                ErrorDetailConverterTestHelper.makeItemDetailNonItemDetail();
        transactionItemDetail.setNonItemDetailListByItem(nonItemDetailList);
        List<ItemDiscount> itemDiscountList =
                ErrorDetailConverterTestHelper.makeItemDetailItemDiscount();
        transactionItemDetail.setItemDiscountList(itemDiscountList);
        List<ItemTaxDetail> itemTaxDetailList =
                ErrorDetailConverterTestHelper.makeItemDetailItemTaxDetail();
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
        nonItemDetail.setNonItemUnitPriceTaxExcluded(ErrorDetailConverterTestHelper.getPrice());
        nonItemDetail.setNonItemUnitPriceTaxIncluded(ErrorDetailConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxExcluded(ErrorDetailConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxIncluded(ErrorDetailConverterTestHelper.getPrice());
        nonItemDetail.setNonItemNewPrice(ErrorDetailConverterTestHelper.getPrice());
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
                ErrorDetailConverterTestHelper.makeItemDetailNonItemDetailNonItemDiscountDetail();
        nonItemDetail.setNonItemDiscountDetailList(nonItemDiscountDetailList);
        List<NonItemTaxDetail> nonItemTaxDetailList =
                ErrorDetailConverterTestHelper.makeItemDetailNonItemDetailNonItemTaxDetail();
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
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxExcluded(ErrorDetailConverterTestHelper.getPrice());
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxIncluded(ErrorDetailConverterTestHelper.getPrice());

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
        nonItemTaxDetail.setNonItemTaxAmt(ErrorDetailConverterTestHelper.getPrice());
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
        itemDiscount.setItemDiscountAmtTaxExcluded(ErrorDetailConverterTestHelper.getPrice());
        itemDiscount.setItemDiscountAmtTaxIncluded(ErrorDetailConverterTestHelper.getPrice());

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
        itemTaxDetail.setItemTaxAmt(ErrorDetailConverterTestHelper.getPrice());
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
        nonItemDetail.setNonItemUnitPriceTaxExcluded(ErrorDetailConverterTestHelper.getPrice());
        nonItemDetail.setNonItemUnitPriceTaxIncluded(ErrorDetailConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxExcluded(ErrorDetailConverterTestHelper.getPrice());
        nonItemDetail.setNonItemSalesAmtTaxIncluded(ErrorDetailConverterTestHelper.getPrice());
        nonItemDetail.setNonItemNewPrice(ErrorDetailConverterTestHelper.getPrice());
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
                ErrorDetailConverterTestHelper.makeNonItemDiscountDetail();
        nonItemDetail.setNonItemDiscountDetailList(nonItemDiscountDetailList);
        List<NonItemTaxDetail> nonItemTaxDetailList =
                ErrorDetailConverterTestHelper.makeNonItemTaxDetail();
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
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxExcluded(ErrorDetailConverterTestHelper.getPrice());
        nonItemDiscountDetail
                .setNonItemDiscountAmtTaxIncluded(ErrorDetailConverterTestHelper.getPrice());

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
        nonItemTaxDetail.setNonItemTaxAmt(ErrorDetailConverterTestHelper.getPrice());
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
                .setTaxIncludedPaymentAmount(ErrorDetailConverterTestHelper.getPrice());
        salesTransactionTender.setTenderInfoList(ErrorDetailConverterTestHelper.makeTenderInfo());

        salesTransactionTenderList.add(salesTransactionTender);
        return salesTransactionTenderList;
    }

    public static TenderInfo makeTenderInfo() {
        TenderInfo tenderInfo = new TenderInfo();

        tenderInfo.setDiscountAmount(ErrorDetailConverterTestHelper.getPrice());
        tenderInfo.setDiscountRate(new BigDecimal(2018));
        tenderInfo.setDiscountCodeIdCorporateId("a00000000000000000000000000001");
        tenderInfo.setCouponType("a00001");
        tenderInfo.setCouponDiscountAmountSetting(ErrorDetailConverterTestHelper.getPrice());
        tenderInfo.setCouponMinUsageAmountThreshold(ErrorDetailConverterTestHelper.getPrice());
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
        salesTransactionTaxDetail.setTaxAmount(ErrorDetailConverterTestHelper.getPrice());

        salesTransactionTaxDetailList.add(salesTransactionTaxDetail);
        return salesTransactionTaxDetailList;
    }

    public static List<SalesTransactionTotal> makeSalesTransactionTotal() {
        List<SalesTransactionTotal> salesTransactionTotalList = new ArrayList<>();
        SalesTransactionTotal salesTransactionTotal = new SalesTransactionTotal();

        salesTransactionTotal.setTotalAmountSubNumber(9999);
        salesTransactionTotal.setTotalType("a123456789");
        salesTransactionTotal.setTotalAmountTaxExcluded(ErrorDetailConverterTestHelper.getPrice());
        salesTransactionTotal.setTotalAmountTaxIncluded(ErrorDetailConverterTestHelper.getPrice());
        salesTransactionTotal.setConsumptionTaxRate(new BigDecimal(12));
        salesTransactionTotal.setSalesTransactionInformation1("z1234567890121");
        salesTransactionTotal.setSalesTransactionInformation2("z1234567890122");
        salesTransactionTotal.setSalesTransactionInformation3("z1234567890123");

        salesTransactionTotalList.add(salesTransactionTotal);
        return salesTransactionTotalList;
    }

    public static SalesOrderInformation makeTSalesOrderInformation() {
        SalesOrderInformation tSalesOrderInformationEntity = new SalesOrderInformation();

        tSalesOrderInformationEntity.setIntegratedOrderId("IntegratedOrderId01");
        tSalesOrderInformationEntity.setOrderBarcodeNumber("320581198607101614abcdefgh");
        tSalesOrderInformationEntity.setStoreCode("1234567890");
        tSalesOrderInformationEntity.setSystemBrandCode("1234");
        tSalesOrderInformationEntity.setSystemBusinessCode("1235");
        tSalesOrderInformationEntity.setSystemCountryCode("USA");
        tSalesOrderInformationEntity.setChannelCode("A1");
        tSalesOrderInformationEntity.setUpdateType("ERRORERROR");
        tSalesOrderInformationEntity.setCustomerId("320581198607101614123456123456");
        tSalesOrderInformationEntity.setOrderConfirmationBusinessDate("2017-12-12");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tSalesOrderInformationEntity.setOrderConfirmationDateTime(nowDateTime);
        tSalesOrderInformationEntity.setErrorCheckType(7);
        tSalesOrderInformationEntity.setDataAlterationSalesLinkageType(8);
        tSalesOrderInformationEntity.setDataAlterationBackboneLinkageType(9);
        tSalesOrderInformationEntity.setDataAlterationEditingFlag(true);
        tSalesOrderInformationEntity.setDataAlterationUserId("UUU000000000000000000000user01");
        tSalesOrderInformationEntity.setCreateUserId("user01");
        tSalesOrderInformationEntity.setCreateDatetime(nowDateTime);
        tSalesOrderInformationEntity.setCreateProgramId("SLS0300101");
        tSalesOrderInformationEntity.setUpdateUserId("user01");
        tSalesOrderInformationEntity.setUpdateDatetime(nowDateTime);
        tSalesOrderInformationEntity.setUpdateProgramId("SLS0300101");

        return tSalesOrderInformationEntity;
    }

    public static SalesTransactionHeader makeTSalesTransactionHeader() {
        SalesTransactionHeader tSalesTransactionHeaderEntity = new SalesTransactionHeader();

        tSalesTransactionHeaderEntity.setOrderSubNumber(203);
        tSalesTransactionHeaderEntity.setSalesTransactionId("SalesTransactionId03");
        tSalesTransactionHeaderEntity.setIntegratedOrderId("320581198607101614abcdefghi");
        tSalesTransactionHeaderEntity.setSalesTransactionSubNumber(1);
        tSalesTransactionHeaderEntity.setStoreCode("a123456789");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        tSalesTransactionHeaderEntity.setDataCreationDateTime(nowDateTime);
        tSalesTransactionHeaderEntity.setDataCreationBusinessDate("2018-12-12");
        tSalesTransactionHeaderEntity.setCashRegisterNo(5);
        tSalesTransactionHeaderEntity.setReceiptNo("a123456789");
        tSalesTransactionHeaderEntity.setSalesLinkageType(3);
        tSalesTransactionHeaderEntity.setSalesTransactionType("a00002");
        tSalesTransactionHeaderEntity.setReturnType(4);
        tSalesTransactionHeaderEntity.setSystemBrandCode("A001");
        tSalesTransactionHeaderEntity.setSystemBusinessCode("A002");
        tSalesTransactionHeaderEntity.setSystemCountryCode("USA");
        tSalesTransactionHeaderEntity.setChannelCode("a1");
        tSalesTransactionHeaderEntity.setOrderStatus("co1");
        tSalesTransactionHeaderEntity.setOrderSubstatus("co2");
        tSalesTransactionHeaderEntity.setOrderStatusUpdateDate("2018-12-12");
        tSalesTransactionHeaderEntity.setOrderStatusLastUpdateDateTime(nowDateTime);
        tSalesTransactionHeaderEntity.setOrderCancelledDateTime(nowDateTime);
        tSalesTransactionHeaderEntity.setBookingStoreCode("123456789C");
        tSalesTransactionHeaderEntity.setBookingStoreSystemBrandCode("abc5");
        tSalesTransactionHeaderEntity.setBookingStoreSystemBusinessCode("abc6");
        tSalesTransactionHeaderEntity.setBookingStoreSystemCountryCode("USA");
        tSalesTransactionHeaderEntity.setPaymentStatus("co3");
        tSalesTransactionHeaderEntity.setPaymentStoreCode("b234567890");
        tSalesTransactionHeaderEntity.setPaymentStoreSystemBrandCode("cas1");
        tSalesTransactionHeaderEntity.setPaymentStoreSystemBusinessCode("cas2");
        tSalesTransactionHeaderEntity.setPaymentStoreSystemCountryCode("USA");
        tSalesTransactionHeaderEntity.setTransferOutStatus("co6");
        tSalesTransactionHeaderEntity.setShipmentStatus("co4");
        tSalesTransactionHeaderEntity.setShipmentStoreCode("123456789D");
        tSalesTransactionHeaderEntity.setShipmentStoreSystemBrandCode("abc7");
        tSalesTransactionHeaderEntity.setShipmentStoreSystemBusinessCode("abc8");
        tSalesTransactionHeaderEntity.setShipmentStoreSystemCountryCode("USA");
        tSalesTransactionHeaderEntity.setReceivingStatus("co5");
        tSalesTransactionHeaderEntity.setReceiptStoreCode("123456789E");
        tSalesTransactionHeaderEntity.setReceiptStoreSystemBrandCode("abc9");
        tSalesTransactionHeaderEntity.setReceiptStoreSystemBusinessCode("abd2");
        tSalesTransactionHeaderEntity.setReceiptStoreSystemCountryCode("USA");
        tSalesTransactionHeaderEntity.setCustomerId("32058119860710161412345612345a");
        tSalesTransactionHeaderEntity.setOrderNumberForStorePayment("a00000000000000001");
        tSalesTransactionHeaderEntity.setAdvanceReceivedStoreCode("chinaappl1");
        tSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBrandCode("A003");
        tSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBusinessCode("A004");
        tSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemCountryCode("USA");
        tSalesTransactionHeaderEntity.setOperatorCode("abcdefghi2");
        tSalesTransactionHeaderEntity.setOriginalTransactionId("320581198607101614abcdef123456");
        tSalesTransactionHeaderEntity.setOriginalCashRegisterNo(6);
        tSalesTransactionHeaderEntity.setOriginalReceiptNo("abcdefghi3");
        tSalesTransactionHeaderEntity.setDepositCurrencyCode("RMG");
        tSalesTransactionHeaderEntity.setDepositValue(new BigDecimal(1));
        tSalesTransactionHeaderEntity.setChangeCurrencyCode("RMG");
        tSalesTransactionHeaderEntity.setChangeValue(new BigDecimal(1));
        tSalesTransactionHeaderEntity.setReceiptNoForCreditCardCancellation("abcde123456");
        tSalesTransactionHeaderEntity.setReceiptNoForCreditCard("320581198607101614abcdef123457");
        tSalesTransactionHeaderEntity.setReceiptIssuedFlag(true);
        tSalesTransactionHeaderEntity.setEReceiptTargetFlag(true);
        tSalesTransactionHeaderEntity.setProcessingCompanyCode("320581198607101614ab");
        tSalesTransactionHeaderEntity.setEmployeeSaleFlag(true);
        tSalesTransactionHeaderEntity.setConsistencySalesFlag(true);
        tSalesTransactionHeaderEntity.setCorporateId("320581198607101614ab");
        tSalesTransactionHeaderEntity.setSalesTransactionDiscountFlag(true);
        tSalesTransactionHeaderEntity.setSalesTransactionDiscountAmountRateCurrencyCode("RMG");
        tSalesTransactionHeaderEntity.setSalesTransactionDiscountAmountRate(new BigDecimal(1));
        tSalesTransactionHeaderEntity.setTokenCode("a00000000000000000000000000002");
        tSalesTransactionHeaderEntity.setCreateUserId("user02");
        tSalesTransactionHeaderEntity.setCreateDatetime(nowDateTime);
        tSalesTransactionHeaderEntity.setCreateProgramId("SLS0300102");
        tSalesTransactionHeaderEntity.setUpdateUserId("user02");
        tSalesTransactionHeaderEntity.setUpdateDatetime(nowDateTime);
        tSalesTransactionHeaderEntity.setUpdateProgramId("SLS0300102");

        return tSalesTransactionHeaderEntity;
    }

    public static SalesTransactionDetail makeTSalesTransactionDetail() {
        SalesTransactionDetail tSalesTransactionDetailEntity = new SalesTransactionDetail();

        tSalesTransactionDetailEntity.setOrderSubNumber(201);
        tSalesTransactionDetailEntity.setDetailSubNumber(301);
        tSalesTransactionDetailEntity.setSalesTransactionId("SalesTransactionId");
        tSalesTransactionDetailEntity.setItemDetailSubNumber(9901);
        tSalesTransactionDetailEntity.setSalesTransactionType("c00001");
        tSalesTransactionDetailEntity.setSystemBrandCode("ab01");
        tSalesTransactionDetailEntity.setL2ItemCode("abcdefg000000000000000001");
        tSalesTransactionDetailEntity.setDisplayL2ItemCode("abcdefg000000000000000002");
        tSalesTransactionDetailEntity.setL2ProductName("a120304789adgdfgdfgdfg");
        tSalesTransactionDetailEntity.setL3ItemCode("abcdefg000000000000000003");
        tSalesTransactionDetailEntity.setL3PosProductName("a120304789adgdfgdfgdfdsfd");
        tSalesTransactionDetailEntity.setProductClassification("ITEM");
        tSalesTransactionDetailEntity.setNonMdType("NonMdType");
        tSalesTransactionDetailEntity.setNonMdCode("NonMdCode");
        tSalesTransactionDetailEntity.setServiceCode("ServiceCode");
        tSalesTransactionDetailEntity.setEpcCode("abcdefg000000000000000004");
        tSalesTransactionDetailEntity.setGDepartmentCode("c00002");
        tSalesTransactionDetailEntity.setMajorCategoryCode("1234");
        tSalesTransactionDetailEntity.setQuantityCode("A");
        tSalesTransactionDetailEntity.setDetailQuantity(new BigDecimal(99));
        tSalesTransactionDetailEntity.setItemCostCurrencyCode("RMG");
        tSalesTransactionDetailEntity.setItemCostValue(new BigDecimal(1));
        tSalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode("RMG");
        tSalesTransactionDetailEntity.setInitialSellingPrice(new BigDecimal(1));
        tSalesTransactionDetailEntity.setBclassPriceCurrencyCode("RMG");
        tSalesTransactionDetailEntity.setBclassPrice(new BigDecimal(1));
        tSalesTransactionDetailEntity.setNewPriceCurrencyCode("RMG");
        tSalesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        tSalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode("RMG");
        tSalesTransactionDetailEntity.setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        tSalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode("RMG");
        tSalesTransactionDetailEntity.setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        tSalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode("RMG");
        tSalesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        tSalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode("RMG");
        tSalesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        tSalesTransactionDetailEntity.setCalculationUnavailableType("T");
        tSalesTransactionDetailEntity.setOrderStatusUpdateDate("2018-02-27T09:27:45Z");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        tSalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(nowDateTime);
        tSalesTransactionDetailEntity.setOrderStatus("aa2");
        tSalesTransactionDetailEntity.setOrderSubstatus("aa3");
        tSalesTransactionDetailEntity.setBookingStoreCode("a234567801");
        tSalesTransactionDetailEntity.setBookingStoreSystemBrandCode("ab01");
        tSalesTransactionDetailEntity.setBookingStoreSystemBusinessCode("ab02");
        tSalesTransactionDetailEntity.setBookingStoreSystemCountryCode("USA");
        tSalesTransactionDetailEntity.setShipmentStoreCode("a234567802");
        tSalesTransactionDetailEntity.setShipmentStoreSystemBrandCode("ab03");
        tSalesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("ab04");
        tSalesTransactionDetailEntity.setShipmentStoreSystemCountryCode("USB");
        tSalesTransactionDetailEntity.setReceiptStoreCode("a234567803");
        tSalesTransactionDetailEntity.setReceiptStoreSystemBrandCode("ab05");
        tSalesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("ab06");
        tSalesTransactionDetailEntity.setReceiptStoreSystemCountryCode("USC");
        tSalesTransactionDetailEntity.setContributionSalesRepresentative("a234567804");
        tSalesTransactionDetailEntity.setCustomerId("a20581198607101614123456123456");
        tSalesTransactionDetailEntity.setBundlePurchaseApplicableQuantity(new BigDecimal(5672));
        tSalesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode("RMG");
        tSalesTransactionDetailEntity.setBundlePurchaseApplicablePrice(new BigDecimal(1));
        tSalesTransactionDetailEntity.setBundlePurchaseIndex(26);
        tSalesTransactionDetailEntity.setLimitedAmountPromotionCount(99);
        tSalesTransactionDetailEntity.setStoreItemDiscountType("QW");
        tSalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode("RMG");
        tSalesTransactionDetailEntity.setStoreItemDiscountSetting(new BigDecimal(1));
        tSalesTransactionDetailEntity.setStoreBundleSaleFlag(true);
        tSalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode("RMG");
        tSalesTransactionDetailEntity.setStoreBundleSalePrice(new BigDecimal(1));
        tSalesTransactionDetailEntity.setSetSalesDetailIndex(1234);
        tSalesTransactionDetailEntity.setTaxationType("a234567809");
        tSalesTransactionDetailEntity.setTaxSystemType("a234567808");
        tSalesTransactionDetailEntity.setCreateUserId("user03");
        tSalesTransactionDetailEntity.setCreateDatetime(nowDateTime);
        tSalesTransactionDetailEntity.setCreateProgramId("SLS0300103");
        tSalesTransactionDetailEntity.setUpdateUserId("user03");
        tSalesTransactionDetailEntity.setUpdateDatetime(nowDateTime);
        tSalesTransactionDetailEntity.setUpdateProgramId("SLS0300103");

        return tSalesTransactionDetailEntity;
    }

    public static SalesTransactionDiscount makeTSalesTransactionDiscount() {
        SalesTransactionDiscount tSalesTransactionDiscountEntity = new SalesTransactionDiscount();

        tSalesTransactionDiscountEntity.setOrderSubNumber(402);
        tSalesTransactionDiscountEntity.setDetailSubNumber(403);
        tSalesTransactionDiscountEntity.setSalesTransactionId("SalesTransactionId11");
        tSalesTransactionDiscountEntity.setPromotionType("PromotionType11");
        tSalesTransactionDiscountEntity.setPromotionNo("5508");
        tSalesTransactionDiscountEntity.setStoreDiscountType("A");
        tSalesTransactionDiscountEntity.setItemDiscountSubNumber(9901);
        tSalesTransactionDiscountEntity.setQuantityCode("n");
        tSalesTransactionDiscountEntity.setDiscountQuantity(10);
        tSalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode("RMG");
        tSalesTransactionDiscountEntity.setDiscountAmountTaxExcluded(new BigDecimal(1));
        tSalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode("RMG");
        tSalesTransactionDiscountEntity.setDiscountAmountTaxIncluded(new BigDecimal(1));
        tSalesTransactionDiscountEntity.setCreateUserId("user06");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 26);
        tSalesTransactionDiscountEntity.setCreateDatetime(nowDateTime);
        tSalesTransactionDiscountEntity.setCreateProgramId("SLS0300106");
        tSalesTransactionDiscountEntity.setUpdateUserId("user06");
        tSalesTransactionDiscountEntity.setUpdateDatetime(nowDateTime);
        tSalesTransactionDiscountEntity.setUpdateProgramId("SLS0300106");

        return tSalesTransactionDiscountEntity;
    }

    public static SalesTransactionTax makeTSalesTransactionTax() {
        SalesTransactionTax tSalesTransactionTaxEntity = new SalesTransactionTax();

        tSalesTransactionTaxEntity.setOrderSubNumber(405);
        tSalesTransactionTaxEntity.setSalesTransactionId("SalesTransactionId");
        tSalesTransactionTaxEntity.setDetailSubNumber(406);
        tSalesTransactionTaxEntity.setTaxGroup("TaxGroup");
        tSalesTransactionTaxEntity.setTaxSubNumber(9901);
        tSalesTransactionTaxEntity.setTaxAmountSign("s");
        tSalesTransactionTaxEntity.setTaxAmountCurrencyCode("RMG");
        tSalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        tSalesTransactionTaxEntity.setTaxRate(new BigDecimal(1221));
        tSalesTransactionTaxEntity.setTaxName("a100000002");
        tSalesTransactionTaxEntity.setCreateUserId("user07");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 27);
        tSalesTransactionTaxEntity.setCreateDatetime(nowDateTime);
        tSalesTransactionTaxEntity.setCreateProgramId("SLS0300107");
        tSalesTransactionTaxEntity.setUpdateUserId("user07");
        tSalesTransactionTaxEntity.setUpdateDatetime(nowDateTime);
        tSalesTransactionTaxEntity.setUpdateProgramId("SLS0300107");

        return tSalesTransactionTaxEntity;
    }

    public static SalesTransactionTenderTable makeTSalesTransactionTender() {
        SalesTransactionTenderTable tSalesTransactionTenderEntity =
                new SalesTransactionTenderTable();

        tSalesTransactionTenderEntity.setOrderSubNumber(506);
        tSalesTransactionTenderEntity.setSalesTransactionId("SalesTransactionId19");
        tSalesTransactionTenderEntity.setTenderGroup("TenderGroup");
        tSalesTransactionTenderEntity.setTenderId("TenderId");
        tSalesTransactionTenderEntity.setTenderSubNumber(301);
        tSalesTransactionTenderEntity.setPaymentSign("c");
        tSalesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode("RMG");
        tSalesTransactionTenderEntity.setTaxIncludedPaymentAmountValue(new BigDecimal(1));
        tSalesTransactionTenderEntity.setCreateUserId("user10");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 30);
        tSalesTransactionTenderEntity.setCreateDatetime(nowDateTime);
        tSalesTransactionTenderEntity.setCreateProgramId("SLS0300110");
        tSalesTransactionTenderEntity.setUpdateUserId("user10");
        tSalesTransactionTenderEntity.setUpdateDatetime(nowDateTime);
        tSalesTransactionTenderEntity.setUpdateProgramId("SLS0300110");

        return tSalesTransactionTenderEntity;
    }

    public static SalesTransactionTotalAmount makeTSalesTransactionTotalAmount() {
        SalesTransactionTotalAmount tSalesTransactionTotalAmountEntity =
                new SalesTransactionTotalAmount();

        tSalesTransactionTotalAmountEntity.setOrderSubNumber(903);
        tSalesTransactionTotalAmountEntity.setSalesTransactionId("SalesTransactionId25");
        tSalesTransactionTotalAmountEntity.setTotalType("TotalType");
        tSalesTransactionTotalAmountEntity.setTotalAmountSubNumber(301);
        tSalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode("RMG");
        tSalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedValue(new BigDecimal(1));
        tSalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode("RMG");
        tSalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedValue(new BigDecimal(1));
        tSalesTransactionTotalAmountEntity.setTaxRate(new BigDecimal(12));
        tSalesTransactionTotalAmountEntity.setSalesTransactionInformation1("z1234567890121");
        tSalesTransactionTotalAmountEntity.setSalesTransactionInformation2("z1234567890122");
        tSalesTransactionTotalAmountEntity.setSalesTransactionInformation3("z1234567890123");
        tSalesTransactionTotalAmountEntity.setCreateUserId("user013");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 33);
        tSalesTransactionTotalAmountEntity.setCreateDatetime(nowDateTime);
        tSalesTransactionTotalAmountEntity.setCreateProgramId("SLS0300113");
        tSalesTransactionTotalAmountEntity.setUpdateUserId("user13");
        tSalesTransactionTotalAmountEntity.setUpdateDatetime(nowDateTime);
        tSalesTransactionTotalAmountEntity.setUpdateProgramId("SLS0300113");

        return tSalesTransactionTotalAmountEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail1() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId("salesTransactionErrorId01");
        salesTransactionErrorDetailEntity.setIntegratedOrderId("320581198607101614abcdefgh5");
        salesTransactionErrorDetailEntity.setSalesTransactionId(null);
        salesTransactionErrorDetailEntity.setStoreCode("1234567890");
        salesTransactionErrorDetailEntity.setSystemBrandCode("1239");
        salesTransactionErrorDetailEntity.setSystemBusinessCode("1234");
        salesTransactionErrorDetailEntity.setSystemCountryCode("USA");
        salesTransactionErrorDetailEntity.setErrorType("01");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        salesTransactionErrorDetailEntity.setKeyInfo1("320581198607101614abcdefgh5");
        salesTransactionErrorDetailEntity.setKeyInfo2(null);
        salesTransactionErrorDetailEntity.setKeyInfo3(null);
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
        salesTransactionErrorDetailEntity.setKeyInfo6(null);
        salesTransactionErrorDetailEntity.setKeyInfo7(null);
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1("update_type");
        salesTransactionErrorDetailEntity.setErrorItemValue1("ERRORERROR");
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user01");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300101");
        salesTransactionErrorDetailEntity.setUpdateUserId("user01");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300101");

        return salesTransactionErrorDetailEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail2() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(null);
        salesTransactionErrorDetailEntity.setIntegratedOrderId("IntegratedOrderId01");
        salesTransactionErrorDetailEntity.setSalesTransactionId(null);
        salesTransactionErrorDetailEntity.setStoreCode("1234567890");
        salesTransactionErrorDetailEntity.setSystemBrandCode("1234");
        salesTransactionErrorDetailEntity.setSystemBusinessCode("1235");
        salesTransactionErrorDetailEntity.setSystemCountryCode("USA");
        salesTransactionErrorDetailEntity.setErrorType("11");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        salesTransactionErrorDetailEntity.setKeyInfo1("IntegratedOrderId01");
        salesTransactionErrorDetailEntity.setKeyInfo2(null);
        salesTransactionErrorDetailEntity.setKeyInfo3(null);
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
        salesTransactionErrorDetailEntity.setKeyInfo6(null);
        salesTransactionErrorDetailEntity.setKeyInfo7(null);
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_ORDER_INFORMATION);
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user02");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300102");
        salesTransactionErrorDetailEntity.setUpdateUserId("user02");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300102");

        return salesTransactionErrorDetailEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail3() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(null);
        salesTransactionErrorDetailEntity.setIntegratedOrderId("320581198607101614abcdefghi");
        salesTransactionErrorDetailEntity.setSalesTransactionId("SalesTransactionId03");
        salesTransactionErrorDetailEntity.setStoreCode("a123456789");
        salesTransactionErrorDetailEntity.setSystemBrandCode("A001");
        salesTransactionErrorDetailEntity.setSystemBusinessCode("A002");
        salesTransactionErrorDetailEntity.setSystemCountryCode("USA");
        salesTransactionErrorDetailEntity.setErrorType("11");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        salesTransactionErrorDetailEntity.setDataCreationDateTime(nowDateTime);
        salesTransactionErrorDetailEntity.setKeyInfo1("320581198607101614abcdefghi");
        salesTransactionErrorDetailEntity.setKeyInfo2("SalesTransactionId03");
        salesTransactionErrorDetailEntity.setKeyInfo3(null);
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
        salesTransactionErrorDetailEntity.setKeyInfo6(null);
        salesTransactionErrorDetailEntity.setKeyInfo7(null);
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_HEADER);
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user03");
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300103");
        salesTransactionErrorDetailEntity.setUpdateUserId("user03");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300103");

        return salesTransactionErrorDetailEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail4() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(null);
        salesTransactionErrorDetailEntity.setIntegratedOrderId(null);
        salesTransactionErrorDetailEntity.setSalesTransactionId("SalesTransactionId");
        salesTransactionErrorDetailEntity.setStoreCode(null);
        salesTransactionErrorDetailEntity.setSystemBrandCode("ab01");
        salesTransactionErrorDetailEntity.setSystemBusinessCode(null);
        salesTransactionErrorDetailEntity.setSystemCountryCode(null);
        salesTransactionErrorDetailEntity.setErrorType("11");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        salesTransactionErrorDetailEntity.setKeyInfo1(null);
        salesTransactionErrorDetailEntity.setKeyInfo2(null);
        salesTransactionErrorDetailEntity.setKeyInfo3("301");
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
        salesTransactionErrorDetailEntity.setKeyInfo6(null);
        salesTransactionErrorDetailEntity.setKeyInfo7(null);
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_DETAIL);
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user04");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 24);
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300104");
        salesTransactionErrorDetailEntity.setUpdateUserId("user04");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300104");

        return salesTransactionErrorDetailEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail5() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(null);
        salesTransactionErrorDetailEntity.setIntegratedOrderId(null);
        salesTransactionErrorDetailEntity.setSalesTransactionId("SalesTransactionId19");
        salesTransactionErrorDetailEntity.setStoreCode(null);
        salesTransactionErrorDetailEntity.setSystemBrandCode(null);
        salesTransactionErrorDetailEntity.setSystemBusinessCode(null);
        salesTransactionErrorDetailEntity.setSystemCountryCode(null);
        salesTransactionErrorDetailEntity.setErrorType("11");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        salesTransactionErrorDetailEntity.setKeyInfo1(null);
        salesTransactionErrorDetailEntity.setKeyInfo2(null);
        salesTransactionErrorDetailEntity.setKeyInfo3("301");
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
        salesTransactionErrorDetailEntity.setKeyInfo6(null);
        salesTransactionErrorDetailEntity.setKeyInfo7(null);
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_TENDER);
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user05");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 25);
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300105");
        salesTransactionErrorDetailEntity.setUpdateUserId("user05");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300105");

        return salesTransactionErrorDetailEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail6() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(null);
        salesTransactionErrorDetailEntity.setIntegratedOrderId(null);
        salesTransactionErrorDetailEntity.setSalesTransactionId("SalesTransactionId");
        salesTransactionErrorDetailEntity.setStoreCode(null);
        salesTransactionErrorDetailEntity.setSystemBrandCode(null);
        salesTransactionErrorDetailEntity.setSystemBusinessCode(null);
        salesTransactionErrorDetailEntity.setSystemCountryCode(null);
        salesTransactionErrorDetailEntity.setErrorType("11");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        salesTransactionErrorDetailEntity.setKeyInfo1(null);
        salesTransactionErrorDetailEntity.setKeyInfo2(null);
        salesTransactionErrorDetailEntity.setKeyInfo3("9901");
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
        salesTransactionErrorDetailEntity.setKeyInfo6(null);
        salesTransactionErrorDetailEntity.setKeyInfo7(null);
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_TAX);
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user06");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 26);
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300106");
        salesTransactionErrorDetailEntity.setUpdateUserId("user06");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300106");

        return salesTransactionErrorDetailEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail7() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(null);
        salesTransactionErrorDetailEntity.setIntegratedOrderId(null);
        salesTransactionErrorDetailEntity.setSalesTransactionId("SalesTransactionId25");
        salesTransactionErrorDetailEntity.setStoreCode(null);
        salesTransactionErrorDetailEntity.setSystemBrandCode(null);
        salesTransactionErrorDetailEntity.setSystemBusinessCode(null);
        salesTransactionErrorDetailEntity.setSystemCountryCode(null);
        salesTransactionErrorDetailEntity.setErrorType("11");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        salesTransactionErrorDetailEntity.setKeyInfo1(null);
        salesTransactionErrorDetailEntity.setKeyInfo2(null);
        salesTransactionErrorDetailEntity.setKeyInfo3("301");
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
        salesTransactionErrorDetailEntity.setKeyInfo6(null);
        salesTransactionErrorDetailEntity.setKeyInfo7(null);
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_TOTAL_AMOUNT);
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user07");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 27);
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300107");
        salesTransactionErrorDetailEntity.setUpdateUserId("user07");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300107");

        return salesTransactionErrorDetailEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail8() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(null);
        salesTransactionErrorDetailEntity.setIntegratedOrderId(null);
        salesTransactionErrorDetailEntity.setSalesTransactionId("SalesTransactionId11");
        salesTransactionErrorDetailEntity.setStoreCode(null);
        salesTransactionErrorDetailEntity.setSystemBrandCode(null);
        salesTransactionErrorDetailEntity.setSystemBusinessCode(null);
        salesTransactionErrorDetailEntity.setSystemCountryCode(null);
        salesTransactionErrorDetailEntity.setErrorType("11");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        salesTransactionErrorDetailEntity.setKeyInfo1(null);
        salesTransactionErrorDetailEntity.setKeyInfo2(null);
        salesTransactionErrorDetailEntity.setKeyInfo3(null);
        salesTransactionErrorDetailEntity.setKeyInfo4("PromotionType11");
        salesTransactionErrorDetailEntity.setKeyInfo5("5508");
        salesTransactionErrorDetailEntity.setKeyInfo6("A");
        salesTransactionErrorDetailEntity.setKeyInfo7(null);
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_DISCOUNT);
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user08");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 28);
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300108");
        salesTransactionErrorDetailEntity.setUpdateUserId("user08");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300108");

        return salesTransactionErrorDetailEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail9() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(null);
        salesTransactionErrorDetailEntity.setIntegratedOrderId(null);
        salesTransactionErrorDetailEntity.setSalesTransactionId("SalesTransactionId");
        salesTransactionErrorDetailEntity.setStoreCode(null);
        salesTransactionErrorDetailEntity.setSystemBrandCode("ab01");
        salesTransactionErrorDetailEntity.setSystemBusinessCode(null);
        salesTransactionErrorDetailEntity.setSystemCountryCode(null);
        salesTransactionErrorDetailEntity.setErrorType("11");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        salesTransactionErrorDetailEntity.setKeyInfo1(null);
        salesTransactionErrorDetailEntity.setKeyInfo2(null);
        salesTransactionErrorDetailEntity.setKeyInfo3(null);
        salesTransactionErrorDetailEntity.setKeyInfo4("301");
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
        salesTransactionErrorDetailEntity.setKeyInfo6(null);
        salesTransactionErrorDetailEntity.setKeyInfo7(null);
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_DETAIL);
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user09");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300109");
        salesTransactionErrorDetailEntity.setUpdateUserId("user09");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300109");

        return salesTransactionErrorDetailEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail10() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(null);
        salesTransactionErrorDetailEntity.setIntegratedOrderId(null);
        salesTransactionErrorDetailEntity.setSalesTransactionId("SalesTransactionId");
        salesTransactionErrorDetailEntity.setStoreCode(null);
        salesTransactionErrorDetailEntity.setSystemBrandCode(null);
        salesTransactionErrorDetailEntity.setSystemBusinessCode(null);
        salesTransactionErrorDetailEntity.setSystemCountryCode(null);
        salesTransactionErrorDetailEntity.setErrorType("11");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        salesTransactionErrorDetailEntity.setKeyInfo1(null);
        salesTransactionErrorDetailEntity.setKeyInfo2(null);
        salesTransactionErrorDetailEntity.setKeyInfo3(null);
        salesTransactionErrorDetailEntity.setKeyInfo4("9901");
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
        salesTransactionErrorDetailEntity.setKeyInfo6(null);
        salesTransactionErrorDetailEntity.setKeyInfo7(null);
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_TAX);
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user10");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 30);
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300110");
        salesTransactionErrorDetailEntity.setUpdateUserId("user10");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300110");

        return salesTransactionErrorDetailEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail11() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(null);
        salesTransactionErrorDetailEntity.setIntegratedOrderId(null);
        salesTransactionErrorDetailEntity.setSalesTransactionId("SalesTransactionId");
        salesTransactionErrorDetailEntity.setStoreCode(null);
        salesTransactionErrorDetailEntity.setSystemBrandCode(null);
        salesTransactionErrorDetailEntity.setSystemBusinessCode(null);
        salesTransactionErrorDetailEntity.setSystemCountryCode(null);
        salesTransactionErrorDetailEntity.setErrorType("11");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        salesTransactionErrorDetailEntity.setKeyInfo1(null);
        salesTransactionErrorDetailEntity.setKeyInfo2(null);
        salesTransactionErrorDetailEntity.setKeyInfo3(null);
        salesTransactionErrorDetailEntity.setKeyInfo4("406");
        salesTransactionErrorDetailEntity.setKeyInfo5("9901");
        salesTransactionErrorDetailEntity.setKeyInfo6(null);
        salesTransactionErrorDetailEntity.setKeyInfo7(null);
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_TAX);
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user11");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 31);
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300111");
        salesTransactionErrorDetailEntity.setUpdateUserId("user11");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300111");

        return salesTransactionErrorDetailEntity;
    }

    public static SalesTransactionErrorDetail makeTSalesTransactionErrorDetail12() {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(null);
        salesTransactionErrorDetailEntity.setIntegratedOrderId(null);
        salesTransactionErrorDetailEntity.setSalesTransactionId("SalesTransactionId11");
        salesTransactionErrorDetailEntity.setStoreCode(null);
        salesTransactionErrorDetailEntity.setSystemBrandCode(null);
        salesTransactionErrorDetailEntity.setSystemBusinessCode(null);
        salesTransactionErrorDetailEntity.setSystemCountryCode(null);
        salesTransactionErrorDetailEntity.setErrorType("11");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);

        salesTransactionErrorDetailEntity.setKeyInfo1(null);
        salesTransactionErrorDetailEntity.setKeyInfo2(null);
        salesTransactionErrorDetailEntity.setKeyInfo3(null);
        salesTransactionErrorDetailEntity.setKeyInfo4("403");
        salesTransactionErrorDetailEntity.setKeyInfo5("PromotionType11");
        salesTransactionErrorDetailEntity.setKeyInfo6("5508");
        salesTransactionErrorDetailEntity.setKeyInfo7("A");
        salesTransactionErrorDetailEntity.setKeyInfo8(null);
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_DISCOUNT);
        salesTransactionErrorDetailEntity.setErrorItemId2(null);
        salesTransactionErrorDetailEntity.setErrorItemValue2(null);
        salesTransactionErrorDetailEntity.setErrorItemId3(null);
        salesTransactionErrorDetailEntity.setErrorItemValue3(null);
        salesTransactionErrorDetailEntity.setErrorItemId4(null);
        salesTransactionErrorDetailEntity.setErrorItemValue4(null);
        salesTransactionErrorDetailEntity.setErrorItemId5(null);
        salesTransactionErrorDetailEntity.setErrorItemValue5(null);
        salesTransactionErrorDetailEntity.setCreateUserId("user12");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        salesTransactionErrorDetailEntity.setCreateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setCreateProgramId("SLS0300112");
        salesTransactionErrorDetailEntity.setUpdateUserId("user12");
        salesTransactionErrorDetailEntity.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailEntity.setUpdateProgramId("SLS0300112");

        return salesTransactionErrorDetailEntity;
    }

    private static Price getPrice() {
        Price price = new Price();
        price.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price.setValue(new BigDecimal(1));
        return price;
    }
}
