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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTotalAmount;
import com.fastretailing.dcp.storecommon.dto.Price;
import com.fastretailing.dcp.storecommon.util.DateUtility;
import com.fastretailing.dcp.storecommon.util.DateUtility.DateTimeFormat;

public class ErrorTableDataConverterTestHelper {
    public static TransactionImportData makeTransactionImportData() {
        TransactionImportData transactionData = new TransactionImportData();
        transactionData.setUpdateType("qwertyuiop");
        transactionData.setErrorCheckType(1);
        transactionData.setDataAlterationSalesLinkageType(1);
        transactionData.setDataAlterationBackboneLinkageType(2);
        transactionData.setIntegratedOrderId("789456123789456123456789456");
        transactionData.setOrderBarcodeNumber("78945612378945612345678");
        transactionData.setChannelCode("123456abcd");
        transactionData.setStoreCode("123456abcd");
        transactionData.setSystemBrandCode("abcd");
        transactionData.setSystemBusinessCode("abcd");
        transactionData.setSystemCountryCode("abc");
        transactionData.setCustomerId("789456123789456123456789456abc");
        transactionData.setOrderConfirmationBusinessDate("2018-02-22");
        transactionData.setOrderConfirmationDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-22T16:53:55Z", "UTC"));
        transactionData.setDataCorrectionEditingFlag(false);
        transactionData.setDataCorrectionUserId("789456123789456123456789456abc");
        List<Transaction> transactionList = ErrorTableDataConverterTestHelper.makeTransaction();
        transactionData.setTransactionList(transactionList);
        return transactionData;
    }

    public static SalesErrorSalesOrderInformation makeTErrorSalesOrderInformationEntity() {
        SalesErrorSalesOrderInformation tErrrorOrderEntity = new SalesErrorSalesOrderInformation();
        tErrrorOrderEntity.setTransactionId("salesTransactionErrorId");
        tErrrorOrderEntity.setUpdateType("qwertyuiop");
        tErrrorOrderEntity.setErrorCheckType(1);
        tErrrorOrderEntity.setDataAlterationSalesLinkageType(1);
        tErrrorOrderEntity.setDataAlterationBackboneLinkageType(2);
        tErrrorOrderEntity.setIntegratedOrderId("789456123789456123456789456");
        tErrrorOrderEntity.setOrderBarcodeNumber("78945612378945612345678");
        tErrrorOrderEntity.setChannelCode("123456abcd");
        tErrrorOrderEntity.setStoreCode("123456abcd");
        tErrrorOrderEntity.setSystemBrandCode("abcd");
        tErrrorOrderEntity.setSystemBusinessCode("abcd");
        tErrrorOrderEntity.setSystemCountryCode("abc");
        tErrrorOrderEntity.setCustomerId("789456123789456123456789456abc");
        tErrrorOrderEntity.setOrderConfirmationBusinessDate("20180222");
        LocalDateTime ldt =
                DateUtility.parseDateTime("2018-02-22 16:53:55", DateTimeFormat.UUUUHMMHDDHHQMIQSS);
        tErrrorOrderEntity.setOrderConfirmationDateTime(ldt);
        tErrrorOrderEntity.setDataAlterationEditingFlag(false);
        tErrrorOrderEntity.setDataAlterationUserId("789456123789456123456789456abc");

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tErrrorOrderEntity.setCreateUserId("user01");
        tErrrorOrderEntity.setCreateDatetime(nowDateTime);
        tErrrorOrderEntity.setCreateProgramId("SLS0300101");
        tErrrorOrderEntity.setUpdateUserId("user01");
        tErrrorOrderEntity.setUpdateDatetime(nowDateTime);
        tErrrorOrderEntity.setUpdateProgramId("SLS0300101");
        return tErrrorOrderEntity;
    }

    public static List<Transaction> makeTransaction() {
        List<Transaction> transactionList = new ArrayList<>();

        Transaction transaction = new Transaction();
        transaction.setIntegratedOrderId("123456789123456789123456789");
        transaction.setOrderSubNumber(1234);
        transaction.setSalesTransactionId("123456789123456789123456789abc");
        transaction.setTokenCode("123456789123456789123456789abc");
        transaction.setSalesLinkageType(1);
        transaction.setReturnType(1);
        transaction.setSystemBrandCode("1234");
        transaction.setSystemBusinessCode("1234");
        transaction.setSystemCountryCode("123");
        transaction.setStoreCode("1234567890");
        transaction.setChannelCode("1234567890");
        transaction.setDataCreationBusinessDate("2018-02-22");
        transaction.setOrderStatusUpdateDate("2018-02-22");
        transaction.setCashRegisterNo(123);
        transaction.setReceiptNo("1234");
        transaction.setOrderNumberForStorePayment("123456789123456789123456789");
        transaction.setAdvanceReceivedStoreCode("1234567890");
        transaction.setAdvanceReceivedStoreSystemBrandCode("1234");
        transaction.setAdvanceReceivedStoreSystemBusinessCode("1234");
        transaction.setAdvanceReceivedStoreSystemCountryCode("123");
        transaction.setOperatorCode("1234567890");
        transaction.setOriginalTransactionId("123456789123456789123456789abc");
        transaction.setOriginalReceiptNo("1234");
        transaction.setOriginalCashRegisterNo(123);
        transaction.setReceiptNoForCreditCardCancellation("abcde123456");
        transaction.setReceiptNoForCreditCard("320581198607101614abcdef123456");
        transaction.setPaymentStoreCode("1234567890");
        transaction.setPaymentStoreSystemBrandCode("9999");
        transaction.setPaymentStoreSystemBusinessCode("1234");
        transaction.setPaymentStoreSystemCountryCode("555");
        transaction.setProcessingCompanyCode("123456789123456789ab");
        transaction.setOrderStatus("123456789123456789ab123456789123456789ab1234567890");
        transaction.setOrderSubstatus("123456789123456789ab123456789123456789ab1234567890");
        transaction.setPaymentStatus("123456789123456789ab123456789123456789ab1234567890");
        transaction.setShipmentStatus("123456789123456789ab123456789123456789ab1234567890");
        transaction.setReceivingStatus("123456789123456789ab123456789123456789ab1234567890");
        transaction.setTransferOutStatus("123456789123456789ab123456789123456789ab1234567890");
        transaction.setBookingStoreCode("1234567890");
        transaction.setBookingStoreSystemBrandCode("7777");
        transaction.setBookingStoreSystemBusinessCode("4444");
        transaction.setBookingStoreSystemCountryCode("666");
        transaction.setReceiptStoreCode("1234567890");
        transaction.setReceiptStoreSystemBrandCode("9999");
        transaction.setReceiptStoreSystemBusinessCode("8888");
        transaction.setReceiptStoreSystemCountryCode("111");
        transaction.setCustomerId("320581198607101614123456123456");
        transaction.setCorporateId("12345678901234567890");
        transaction.setSalesTransactionDiscountFlag(true);
        transaction.setConsistencySalesFlag(true);
        transaction.setEmployeeSaleFlag(true);
        transaction.setReceiptIssuedFlag(true);
        transaction.setEReceiptTargetFlag(true);
        transaction.setDataCreationDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-23T17:26:50Z", "UTC"));
        transaction.setOrderStatusLastUpdateDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-23T17:26:50Z", "UTC"));
        transaction.setTransactionType("abcdef");
        transaction.setOrderCancellationDate(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-23T17:26:50Z", "UTC"));

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1000000));
        transaction.setDeposit(price1);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(1000000));
        transaction.setChange(price2);

        Price price3 = getPrice();
        price3.setValue(new BigDecimal(1000000));
        transaction.setSalesTransactionDiscountAmountRate(price3);

        List<TransactionItemDetail> transactionItemDetailList =
                ErrorTableDataConverterTestHelper.makeItemDetail();
        transaction.setTransactionItemDetailList(transactionItemDetailList);
        List<NonItemDetail> nonItemDetailList =
                ErrorTableDataConverterTestHelper.makeNonItemDetail();
        transaction.setNonItemDetailList(nonItemDetailList);
        List<SalesTransactionTender> salesTransactionTenderList =
                ErrorTableDataConverterTestHelper.makeTender();
        transaction.setSalesTransactionTenderList(salesTransactionTenderList);
        List<SalesTransactionTaxDetail> SalesTransactionTaxDetailList =
                ErrorTableDataConverterTestHelper.makeTaxDetailOfTransaction();
        transaction.setSalesTransactionTaxDetailList(SalesTransactionTaxDetailList);
        List<SalesTransactionTotal> SalesTransactionTotalList =
                ErrorTableDataConverterTestHelper.makeTotal();
        transaction.setSalesTransactionTotalList(SalesTransactionTotalList);

        transactionList.add(transaction);
        return transactionList;
    }

    public static SalesErrorSalesTransactionHeader makeTSalesErrorSalesTransactionHeaderEntity() {

        SalesErrorSalesTransactionHeader headerEntity = new SalesErrorSalesTransactionHeader();
        headerEntity.setTransactionId("salesTransactionErrorId");
        headerEntity.setSalesTransactionSubNumber(001);

        headerEntity.setIntegratedOrderId("123456789123456789123456789");
        headerEntity.setOrderSubNumber(1234);
        headerEntity.setSalesTransactionId("123456789123456789123456789abc");
        headerEntity.setTokenCode("123456789123456789123456789abc");
        headerEntity.setSalesLinkageType(1);
        headerEntity.setReturnType(1);
        headerEntity.setSystemBrandCode("1234");
        headerEntity.setSystemBusinessCode("1234");
        headerEntity.setSystemCountryCode("123");
        headerEntity.setStoreCode("1234567890");
        headerEntity.setChannelCode("1234567890");
        headerEntity.setDataCreationBusinessDate("20180222");
        headerEntity.setOrderStatusUpdateDate("20180222");
        headerEntity.setCashRegisterNo(123);
        headerEntity.setReceiptNo("1234");
        headerEntity.setOrderNumberForStorePayment("123456789123456789123456789");
        headerEntity.setAdvanceReceivedStoreCode("1234567890");
        headerEntity.setAdvanceReceivedStoreSystemBrandCode("1234");
        headerEntity.setAdvanceReceivedStoreSystemBusinessCode("1234");
        headerEntity.setAdvanceReceivedStoreSystemCountryCode("123");
        headerEntity.setOperatorCode("1234567890");
        headerEntity.setOriginalTransactionId("123456789123456789123456789abc");
        headerEntity.setOriginalReceiptNo("1234");
        headerEntity.setOriginalCashRegisterNo(123);
        headerEntity.setReceiptNoForCreditCardCancellation("abcde123456");
        headerEntity.setReceiptNoForCreditCard("320581198607101614abcdef123456");
        headerEntity.setPaymentStoreCode("1234567890");
        headerEntity.setPaymentStoreSystemBrandCode("9999");
        headerEntity.setPaymentStoreSystemBusinessCode("1234");
        headerEntity.setPaymentStoreSystemCountryCode("555");
        headerEntity.setProcessingCompanyCode("123456789123456789ab");
        headerEntity.setOrderStatus("123456789123456789ab123456789123456789ab1234567890");
        headerEntity.setOrderSubstatus("123456789123456789ab123456789123456789ab1234567890");
        headerEntity.setPaymentStatus("123456789123456789ab123456789123456789ab1234567890");
        headerEntity.setShipmentStatus("123456789123456789ab123456789123456789ab1234567890");
        headerEntity.setReceivingStatus("123456789123456789ab123456789123456789ab1234567890");
        headerEntity.setTransferOutStatus("123456789123456789ab123456789123456789ab1234567890");
        headerEntity.setBookingStoreCode("1234567890");
        headerEntity.setBookingStoreSystemBrandCode("7777");
        headerEntity.setBookingStoreSystemBusinessCode("4444");
        headerEntity.setBookingStoreSystemCountryCode("666");
        headerEntity.setReceiptStoreCode("1234567890");
        headerEntity.setReceiptStoreSystemBrandCode("9999");
        headerEntity.setReceiptStoreSystemBusinessCode("8888");
        headerEntity.setReceiptStoreSystemCountryCode("111");
        headerEntity.setCustomerId("320581198607101614123456123456");
        headerEntity.setCorporateId("12345678901234567890");
        headerEntity.setSalesTransactionDiscountFlag(true);
        headerEntity.setConsistencySalesFlag(true);
        headerEntity.setEmployeeSaleFlag(true);
        headerEntity.setReceiptIssuedFlag(true);
        headerEntity.setEReceiptTargetFlag(true);
        LocalDateTime ldt =
                DateUtility.parseDateTime("2018-02-23 17:26:50", DateTimeFormat.UUUUHMMHDDHHQMIQSS);
        headerEntity.setDataCreationDateTime(ldt);
        headerEntity.setOrderStatusLastUpdateDateTime(ldt);
        headerEntity.setSalesTransactionType("abcdef");
        headerEntity.setOrderCancelledDateTime(ldt);

        headerEntity.setDepositCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        headerEntity.setDepositValue(new BigDecimal(1000000));

        headerEntity.setChangeCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        headerEntity.setChangeValue(new BigDecimal(1000000));

        headerEntity.setSalesTransactionDiscountAmountRateCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        headerEntity.setSalesTransactionDiscountAmountRate(new BigDecimal(1000000));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        headerEntity.setCreateUserId("user01");
        headerEntity.setCreateDatetime(nowDateTime);
        headerEntity.setCreateProgramId("SLS0300101");
        headerEntity.setUpdateUserId("user01");
        headerEntity.setUpdateDatetime(nowDateTime);
        headerEntity.setUpdateProgramId("SLS0300101");
        return headerEntity;
    }

    public static List<TransactionItemDetail> makeItemDetail() {
        List<TransactionItemDetail> transactionItemDetailList = new ArrayList<>();

        TransactionItemDetail itemDetail = new TransactionItemDetail();
        itemDetail.setSystemBrandCode("1234");
        itemDetail.setL2ItemCode("320581198607101614abcdefg");
        itemDetail.setL2ProductName(
                "123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc1234567890");
        itemDetail.setL3ItemCode("320581198607101614abcdefg");
        itemDetail.setL3PosProductName(
                "123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc1234567890");
        itemDetail.setEpcCode("320581198607101614abcdef");
        itemDetail.setGDepartmentCode("123456");
        itemDetail.setQuantityCode("A");
        itemDetail.setItemQty(1234);
        itemDetail.setOrderStatusUpdateDate("2018-02-23");
        itemDetail.setOrderStatus("320581198607101614abcdefg320581198607101614abcdefg");
        itemDetail.setOrderSubstatus("320581198607101614abcdefg320581198607101614abcdefg");
        itemDetail.setBookingStoreCode("1234567890");
        itemDetail.setBookingStoreSystemBrandCode("1234");
        itemDetail.setBookingStoreSystemBusinessCode("1234");
        itemDetail.setBookingStoreSystemCountryCode("555");
        itemDetail.setShipmentStoreCode("1234567890");
        itemDetail.setShipmentStoreSystemBrandCode("5678");
        itemDetail.setShipmentStoreSystemBusinessCode("5678");
        itemDetail.setShipmentStoreSystemCountryCode("666");
        itemDetail.setReceiptStoreCode("1234567890");
        itemDetail.setReceiptStoreSystemBrandCode("3456");
        itemDetail.setReceiptStoreSystemBusinessCode("3456");
        itemDetail.setReceiptStoreSystemCountryCode("777");
        itemDetail.setContributionSalesRepresentative("1234567890");
        itemDetail.setCustomerId("320581198607101614123456123456");
        itemDetail.setBundlePurchaseIndex(123);
        itemDetail.setLimitedAmountPromotionCount(99);
        itemDetail.setCalculationUnavailableType("1234567890");
        itemDetail.setBundleSalesDetailIndex(777);
        itemDetail.setOrderStatusLastUpdateDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-23T17:26:50Z", "UTC"));
        itemDetail.setItemTaxationType("1234567890");
        itemDetail.setItemTaxKind("1234567890");
        itemDetail.setDetailListSalesTransactionType("abcdef");
        itemDetail.setViewL2ItemCode("320581198607101614abcdefg");
        itemDetail.setBundlePurchaseQty(10);
        itemDetail.setItemMountDiscountType("1234567890");
        itemDetail.setBundleSalesFlag(true);
        itemDetail.setDeptCode(1234);
        itemDetail.setItemQty(4567);

        Price price4 = getPrice();
        price4.setValue(new BigDecimal(4));
        itemDetail.setItemCost(price4);

        Price price3 = getPrice();
        price3.setValue(new BigDecimal(3));
        itemDetail.setInitialSellingPrice(price3);

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        itemDetail.setBItemSellingPrice(price1);

        Price price6 = getPrice();
        price6.setValue(new BigDecimal(6));
        itemDetail.setItemNewPrice(price6);

        Price price9 = getPrice();
        price9.setValue(new BigDecimal(9));
        itemDetail.setItemUnitPriceTaxExcluded(price9);

        Price price10 = getPrice();
        price10.setValue(new BigDecimal(10));
        itemDetail.setItemUnitPriceTaxIncluded(price10);

        Price price13 = getPrice();
        price13.setValue(new BigDecimal(13));
        itemDetail.setItemSalesAmtTaxExcluded(price13);

        Price price14 = getPrice();
        price14.setValue(new BigDecimal(14));
        itemDetail.setItemSalesAmtTaxIncluded(price14);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(2));
        itemDetail.setBundlePurchasePrice(price2);

        Price price5 = getPrice();
        price5.setValue(new BigDecimal(5));
        itemDetail.setItemDiscountAmount(price5);

        Price price11 = getPrice();
        price11.setValue(new BigDecimal(11));
        itemDetail.setBundleSalesPrice(price11);

        List<NonItemDetail> nonItemDetailList =
                ErrorTableDataConverterTestHelper.makeNonItemDetail();
        itemDetail.setNonItemDetailListByItem(nonItemDetailList);
        List<ItemDiscount> itemDiscountList = ErrorTableDataConverterTestHelper.makeItemDiscount();
        itemDetail.setItemDiscountList(itemDiscountList);
        List<ItemTaxDetail> itemTaxDetailList =
                ErrorTableDataConverterTestHelper.makeItemTaxDetail();
        itemDetail.setItemTaxDetailList(itemTaxDetailList);
        transactionItemDetailList.add(itemDetail);
        return transactionItemDetailList;
    }

    public static SalesErrorSalesTransactionDetail makeTSalesErrorSalesTransactionDetailEntity() {
        SalesErrorSalesTransactionDetail detailEntity = new SalesErrorSalesTransactionDetail();
        detailEntity.setTransactionId("salesTransactionError01");
        detailEntity.setOrderSubNumber(201);
        detailEntity.setSalesTransactionId("salesTransactionId01");
        detailEntity.setDetailSubNumber(301);
        detailEntity.setItemDetailSubNumber(9901);

        detailEntity.setSystemBrandCode("1234");
        detailEntity.setL2ItemCode("320581198607101614abcdefg");
        detailEntity.setL2ProductName(
                "123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc1234567890");
        detailEntity.setL3ItemCode("320581198607101614abcdefg");
        detailEntity.setL3PosProductName(
                "123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc1234567890");
        detailEntity.setEpcCode("320581198607101614abcdef");

        detailEntity.setQuantityCode("A");
        detailEntity.setOrderStatusUpdateDate("20180223");
        detailEntity.setOrderStatus("320581198607101614abcdefg320581198607101614abcdefg");
        detailEntity.setOrderSubstatus("320581198607101614abcdefg320581198607101614abcdefg");
        detailEntity.setBookingStoreCode("1234567890");
        detailEntity.setBookingStoreSystemBrandCode("1234");
        detailEntity.setBookingStoreSystemBusinessCode("1234");
        detailEntity.setBookingStoreSystemCountryCode("555");
        detailEntity.setShipmentStoreCode("1234567890");
        detailEntity.setShipmentStoreSystemBrandCode("5678");
        detailEntity.setShipmentStoreSystemBusinessCode("5678");
        detailEntity.setShipmentStoreSystemCountryCode("666");
        detailEntity.setReceiptStoreCode("1234567890");
        detailEntity.setReceiptStoreSystemBrandCode("3456");
        detailEntity.setReceiptStoreSystemBusinessCode("3456");
        detailEntity.setReceiptStoreSystemCountryCode("777");
        detailEntity.setContributionSalesRepresentative("1234567890");
        detailEntity.setCustomerId("320581198607101614123456123456");
        detailEntity.setBundlePurchaseIndex(123);
        detailEntity.setLimitedAmountPromotionCount(99);
        detailEntity.setCalculationUnavailableType("1234567890");
        detailEntity.setSetSalesDetailIndex(777);
        LocalDateTime ldt =
                DateUtility.parseDateTime("2018-02-23 17:26:50", DateTimeFormat.UUUUHMMHDDHHQMIQSS);
        detailEntity.setOrderStatusLastUpdateDateTime(ldt);
        detailEntity.setTaxationType("1234567890");
        detailEntity.setTaxSystemType("1234567890");
        detailEntity.setSalesTransactionType("abcdef");
        detailEntity.setDisplayL2ItemCode("320581198607101614abcdefg");
        detailEntity.setBundlePurchaseApplicableQuantity(new BigDecimal(10));
        detailEntity.setStoreItemDiscountType("1234567890");
        detailEntity.setStoreBundleSaleFlag(false);
        detailEntity.setMajorCategoryCode("1234");
        detailEntity.setDetailQuantity(new BigDecimal(4567));

        detailEntity.setItemCostCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        detailEntity.setItemCostValue(new BigDecimal(4));

        detailEntity.setInitialSellingPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        detailEntity.setInitialSellingPrice(new BigDecimal(3));

        detailEntity.setBclassPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        detailEntity.setBclassPrice(new BigDecimal(1));

        detailEntity.setNewPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        detailEntity.setNewPrice(new BigDecimal(6));

        detailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        detailEntity.setRetailUnitPriceTaxExcluded(new BigDecimal(9));

        detailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        detailEntity.setRetailUnitPriceTaxIncluded(new BigDecimal(10));

        detailEntity.setSalesAmountTaxExcludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        detailEntity.setSalesAmountTaxExcluded(new BigDecimal(13));

        detailEntity.setSalesAmountTaxIncludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        detailEntity.setSalesAmountTaxIncluded(new BigDecimal(14));

        detailEntity.setBundlePurchaseApplicablePriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        detailEntity.setBundlePurchaseApplicablePrice(new BigDecimal(2));

        detailEntity.setStoreItemDiscountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        detailEntity.setStoreItemDiscountSetting(new BigDecimal(5));

        detailEntity.setStoreBundleSalePriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        detailEntity.setStoreBundleSalePrice(new BigDecimal(11));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        detailEntity.setCreateUserId("user01");
        detailEntity.setCreateDatetime(nowDateTime);
        detailEntity.setCreateProgramId("SLS0300101");
        detailEntity.setUpdateUserId("user01");
        detailEntity.setUpdateDatetime(nowDateTime);
        detailEntity.setUpdateProgramId("SLS0300101");

        detailEntity.setProductClassification("ITEM");
        detailEntity.setGDepartmentCode("123456");
        detailEntity.setStoreBundleSaleFlag(true);
        return detailEntity;
    }

    public static List<NonItemDetail> makeNonItemDetail() {
        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = new NonItemDetail();
        nonItemDetail.setNonMdType("credit");
        nonItemDetail.setServiceCode("abcde");

        nonItemDetail.setQuantityCode("A");
        nonItemDetail.setOrderStatusUpdateDate("2018-02-23");
        nonItemDetail.setOrderStatusLastUpdateDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-02-23T17:26:50Z", "UTC"));
        nonItemDetail.setOrderStatus("320581198607101614abcdefg320581198607101614abcdefg");
        nonItemDetail.setOrderSubstatus("320581198607101614abcdefg320581198607101614abcdefg");
        nonItemDetail.setBookingStoreCode("1234567890");
        nonItemDetail.setBookingStoreSystemBrandCode("1234");
        nonItemDetail.setBookingStoreSystemBusinessCode("1234");
        nonItemDetail.setBookingStoreSystemCountryCode("555");
        nonItemDetail.setShipmentStoreCode("1234567890");
        nonItemDetail.setShipmentStoreSystemBrandCode("5678");
        nonItemDetail.setShipmentStoreSystemBusinessCode("5678");
        nonItemDetail.setShipmentStoreSystemCountryCode("666");
        nonItemDetail.setReceiptStoreCode("1234567890");
        nonItemDetail.setReceiptStoreSystemBrandCode("3456");
        nonItemDetail.setReceiptStoreSystemBusinessCode("3456");
        nonItemDetail.setReceiptStoreSystemCountryCode("777");
        nonItemDetail.setContributionSalesRepresentative("1234567890");
        nonItemDetail.setNonItemTaxationType("1234567890");
        nonItemDetail.setNonItemTaxKind("1234567890");
        nonItemDetail.setNonItemCode("1234567890123456789012345");
        nonItemDetail.setPosNonItemName(
                "123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc1234567890");
        nonItemDetail.setNonItemQty(1234);
        nonItemDetail.setNonCalculationNonItemType("1234567890");
        nonItemDetail.setNonMdDetailListSalesTransactionType(null);

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        nonItemDetail.setNonItemUnitPriceTaxExcluded(price1);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(2));
        nonItemDetail.setNonItemUnitPriceTaxIncluded(price2);

        Price price3 = getPrice();
        price3.setValue(new BigDecimal(3));
        nonItemDetail.setNonItemSalesAmtTaxExcluded(price3);

        Price price4 = getPrice();
        price4.setValue(new BigDecimal(4));
        nonItemDetail.setNonItemSalesAmtTaxIncluded(price4);

        Price price5 = getPrice();
        price5.setValue(new BigDecimal(5));
        nonItemDetail.setNonItemNewPrice(price5);

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
                ErrorTableDataConverterTestHelper.makeNonItemDiscountDetail();
        nonItemDetail.setNonItemDiscountDetailList(nonItemDiscountDetailList);
        List<NonItemTaxDetail> nonItemTaxDetailList =
                ErrorTableDataConverterTestHelper.makeNonItemTaxDetail();
        nonItemDetail.setNonItemTaxDetailList(nonItemTaxDetailList);

        nonItemDetailList.add(nonItemDetail);
        return nonItemDetailList;
    }

    public static SalesErrorSalesTransactionDetail makeNonItemDetailEntity() {
        SalesErrorSalesTransactionDetail nonDetailEntity = new SalesErrorSalesTransactionDetail();
        nonDetailEntity.setTransactionId("salesTransactionId04");
        nonDetailEntity.setOrderSubNumber(201);
        nonDetailEntity.setSalesTransactionId("salesTransactionError04");
        nonDetailEntity.setDetailSubNumber(301);
        nonDetailEntity.setItemDetailSubNumber(9901);
        nonDetailEntity.setSalesTransactionType("salesTransactionType04");

        nonDetailEntity.setNonMdType("credit");
        nonDetailEntity.setServiceCode("abcde");

        nonDetailEntity.setQuantityCode("A");
        nonDetailEntity.setOrderStatusUpdateDate("20180223");
        LocalDateTime ldt =
                DateUtility.parseDateTime("2018-02-23 17:26:50", DateTimeFormat.UUUUHMMHDDHHQMIQSS);
        nonDetailEntity.setOrderStatusLastUpdateDateTime(ldt);
        nonDetailEntity.setOrderStatus("320581198607101614abcdefg320581198607101614abcdefg");
        nonDetailEntity.setOrderSubstatus("320581198607101614abcdefg320581198607101614abcdefg");
        nonDetailEntity.setBookingStoreCode("1234567890");
        nonDetailEntity.setBookingStoreSystemBrandCode("1234");
        nonDetailEntity.setBookingStoreSystemBusinessCode("1234");
        nonDetailEntity.setBookingStoreSystemCountryCode("555");
        nonDetailEntity.setShipmentStoreCode("1234567890");
        nonDetailEntity.setShipmentStoreSystemBrandCode("5678");
        nonDetailEntity.setShipmentStoreSystemBusinessCode("5678");
        nonDetailEntity.setShipmentStoreSystemCountryCode("666");
        nonDetailEntity.setReceiptStoreCode("1234567890");
        nonDetailEntity.setReceiptStoreSystemBrandCode("3456");
        nonDetailEntity.setReceiptStoreSystemBusinessCode("3456");
        nonDetailEntity.setReceiptStoreSystemCountryCode("777");
        nonDetailEntity.setContributionSalesRepresentative("1234567890");
        nonDetailEntity.setTaxationType("1234567890");
        nonDetailEntity.setTaxSystemType("1234567890");
        nonDetailEntity.setNonMdCode("1234567890123456789012345");
        nonDetailEntity.setL3PosProductName(
                "123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc1234567890");
        nonDetailEntity.setDetailQuantity(new BigDecimal(1234));
        nonDetailEntity.setCalculationUnavailableType("1234567890");
        nonDetailEntity.setProductClassification("NMITEM");

        nonDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        nonDetailEntity.setRetailUnitPriceTaxExcluded(new BigDecimal(1));

        nonDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        nonDetailEntity.setRetailUnitPriceTaxIncluded(new BigDecimal(2));

        nonDetailEntity.setSalesAmountTaxExcludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        nonDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(3));

        nonDetailEntity.setSalesAmountTaxIncludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        nonDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(4));

        nonDetailEntity.setNewPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        nonDetailEntity.setNewPrice(new BigDecimal(5));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        nonDetailEntity.setCreateUserId("user04");
        nonDetailEntity.setCreateDatetime(nowDateTime);
        nonDetailEntity.setCreateProgramId("SLS0300104");
        nonDetailEntity.setUpdateUserId("user04");
        nonDetailEntity.setUpdateDatetime(nowDateTime);
        nonDetailEntity.setUpdateProgramId("SLS0300104");
        return nonDetailEntity;
    }

    public static SalesErrorSalesTransactionDetailInfo makeNonItemInfoEntity() {
        SalesErrorSalesTransactionDetailInfo detailInfoEntity =
                new SalesErrorSalesTransactionDetailInfo();
        detailInfoEntity.setTransactionId("salesTransactionErrorId05");
        detailInfoEntity.setOrderSubNumber(201);
        detailInfoEntity.setSalesTransactionId("salesTransactionId05");
        detailInfoEntity.setDetailSubNumber(301);
        detailInfoEntity.setItemDetailSubNumber(9901);

        detailInfoEntity.setKeyCode("a1234567890123456789");
        detailInfoEntity.setCodeValue1("a123456789012345678value1");
        detailInfoEntity.setCodeValue2("a123456789012345678value2");
        detailInfoEntity.setCodeValue3("a123456789012345678value3");
        detailInfoEntity.setCodeValue4("a123456789012345678value4");
        detailInfoEntity.setName1("1234567890name1");
        detailInfoEntity.setName2("1234567890name2");
        detailInfoEntity.setName3("1234567890name3");
        detailInfoEntity.setName4("1234567890name4");

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        detailInfoEntity.setCreateUserId("user05");
        detailInfoEntity.setCreateDatetime(nowDateTime);
        detailInfoEntity.setCreateProgramId("SLS0300105");
        detailInfoEntity.setUpdateUserId("user05");
        detailInfoEntity.setUpdateDatetime(nowDateTime);
        detailInfoEntity.setUpdateProgramId("SLS0300105");
        return detailInfoEntity;
    }

    public static List<NonItemDiscountDetail> makeNonItemDiscountDetail() {
        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();

        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();
        nonItemDiscountDetail.setNonItemPromotionType("abcd");
        nonItemDiscountDetail.setNonItemStoreDiscountType("ab");
        nonItemDiscountDetail.setNonItemQuantityCode("A");
        nonItemDiscountDetail.setNonItemDiscountQty(10);

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        nonItemDiscountDetail.setNonItemDiscountAmtTaxExcluded(price1);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(2));
        nonItemDiscountDetail.setNonItemDiscountAmtTaxIncluded(price2);

        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        return nonItemDiscountDetailList;
    }

    public static SalesErrorSalesTransactionDiscount makeNonItemDiscountDetailEntity() {
        SalesErrorSalesTransactionDiscount discountEntity =
                new SalesErrorSalesTransactionDiscount();
        discountEntity.setTransactionId("salesTransactionError06");
        discountEntity.setOrderSubNumber(201);
        discountEntity.setSalesTransactionId("salesTransactionId06");
        discountEntity.setDetailSubNumber(301);
        discountEntity.setItemDiscountSubNumber(9901);

        discountEntity.setPromotionType("abcd");
        discountEntity.setPromotionNo("0");
        discountEntity.setStoreDiscountType("ab");
        discountEntity.setQuantityCode("A");
        discountEntity.setDiscountQuantity(10);

        discountEntity.setDiscountAmountTaxExcludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        discountEntity.setDiscountAmountTaxExcluded(new BigDecimal(1));

        discountEntity.setDiscountAmountTaxIncludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        discountEntity.setDiscountAmountTaxIncluded(new BigDecimal(2));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        discountEntity.setCreateUserId("user06");
        discountEntity.setCreateDatetime(nowDateTime);
        discountEntity.setCreateProgramId("SLS0300106");
        discountEntity.setUpdateUserId("user06");
        discountEntity.setUpdateDatetime(nowDateTime);
        discountEntity.setUpdateProgramId("SLS0300106");
        return discountEntity;
    }

    public static List<NonItemTaxDetail> makeNonItemTaxDetail() {
        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();

        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();
        nonItemTaxDetail.setNonItemTaxType("1234567890");
        nonItemTaxDetail.setNonItemTaxName(
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        nonItemTaxDetail.setNonItemTaxAmountSign("A");
        nonItemTaxDetail.setNonItemTaxRate(new BigDecimal(1986));

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        nonItemTaxDetail.setNonItemTaxAmt(price1);

        nonItemTaxDetailList.add(nonItemTaxDetail);
        return nonItemTaxDetailList;
    }

    public static SalesErrorSalesTransactionTax makeNonItemTaxDetailEntity() {
        SalesErrorSalesTransactionTax taxEntity = new SalesErrorSalesTransactionTax();
        taxEntity.setTransactionId("salesTransactionError08");
        taxEntity.setOrderSubNumber(201);
        taxEntity.setSalesTransactionId("salesTransactionId08");
        taxEntity.setDetailSubNumber(301);
        taxEntity.setTaxSubNumber(9901);

        taxEntity.setTaxGroup("1234567890");
        taxEntity.setTaxName(
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        taxEntity.setTaxAmountSign("A");
        taxEntity.setTaxRate(new BigDecimal(1986));

        taxEntity.setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        taxEntity.setTaxAmountValue(new BigDecimal(1));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        taxEntity.setCreateUserId("user08");
        taxEntity.setCreateDatetime(nowDateTime);
        taxEntity.setCreateProgramId("SLS0300108");
        taxEntity.setUpdateUserId("user08");
        taxEntity.setUpdateDatetime(nowDateTime);
        taxEntity.setUpdateProgramId("SLS0300108");
        return taxEntity;
    }

    public static List<ItemDiscount> makeItemDiscount() {
        List<ItemDiscount> itemDiscountList = new ArrayList<>();

        ItemDiscount itemDiscount = new ItemDiscount();
        itemDiscount.setItemPromotionType("abcd");
        itemDiscount.setItemPromotionNumber("1234567890");
        itemDiscount.setItemStoreDiscountType("ab");
        itemDiscount.setItemQuantityCode("A");
        itemDiscount.setItemDiscountQty(10);

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        itemDiscount.setItemDiscountAmtTaxExcluded(price1);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(2));
        itemDiscount.setItemDiscountAmtTaxIncluded(price2);

        itemDiscountList.add(itemDiscount);
        return itemDiscountList;
    }

    public static SalesErrorSalesTransactionDiscount makeItemDiscountEntity() {
        SalesErrorSalesTransactionDiscount discountEntity =
                new SalesErrorSalesTransactionDiscount();
        discountEntity.setTransactionId("salesTransactionError06");
        discountEntity.setOrderSubNumber(201);
        discountEntity.setSalesTransactionId("salesTransactionId06");
        discountEntity.setDetailSubNumber(301);
        discountEntity.setItemDiscountSubNumber(9901);

        discountEntity.setPromotionType("abcd");
        discountEntity.setPromotionNo("1234567890");
        discountEntity.setStoreDiscountType("ab");
        discountEntity.setQuantityCode("A");
        discountEntity.setDiscountQuantity(10);

        discountEntity.setDiscountAmountTaxExcludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        discountEntity.setDiscountAmountTaxExcluded(new BigDecimal(1));

        discountEntity.setDiscountAmountTaxIncludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        discountEntity.setDiscountAmountTaxIncluded(new BigDecimal(2));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        discountEntity.setCreateUserId("user06");
        discountEntity.setCreateDatetime(nowDateTime);
        discountEntity.setCreateProgramId("SLS0300106");
        discountEntity.setUpdateUserId("user06");
        discountEntity.setUpdateDatetime(nowDateTime);
        discountEntity.setUpdateProgramId("SLS0300106");
        return discountEntity;
    }

    public static List<ItemTaxDetail> makeItemTaxDetail() {
        List<ItemTaxDetail> itemTaxDetailList = new ArrayList<>();

        ItemTaxDetail itemTaxDetail = new ItemTaxDetail();
        itemTaxDetail.setItemTaxType("1234567890");
        itemTaxDetail.setItemTaxName(
                "123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc1234567890");
        itemTaxDetail.setItemTaxAmountSign("A");
        itemTaxDetail.setItemTaxRate(new BigDecimal(10));

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        itemTaxDetail.setItemTaxAmt(price1);

        itemTaxDetailList.add(itemTaxDetail);
        return itemTaxDetailList;
    }

    public static SalesErrorSalesTransactionTax makeTSalesTransactionTaxEntity() {
        SalesErrorSalesTransactionTax taxEntity = new SalesErrorSalesTransactionTax();
        taxEntity.setTransactionId("salesTransactionError09");
        taxEntity.setOrderSubNumber(201);
        taxEntity.setSalesTransactionId("salesTransactionId09");
        taxEntity.setDetailSubNumber(301);
        taxEntity.setTaxSubNumber(9901);

        taxEntity.setTaxGroup("1234567890");
        taxEntity.setTaxName(
                "123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc1234567890");
        taxEntity.setTaxAmountSign("A");
        taxEntity.setTaxRate(new BigDecimal(10));

        taxEntity.setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        taxEntity.setTaxAmountValue(new BigDecimal(1));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        taxEntity.setCreateUserId("user09");
        taxEntity.setCreateDatetime(nowDateTime);
        taxEntity.setCreateProgramId("SLS0300109");
        taxEntity.setUpdateUserId("user09");
        taxEntity.setUpdateDatetime(nowDateTime);
        taxEntity.setUpdateProgramId("SLS0300109");
        return taxEntity;
    }

    public static List<SalesTransactionTender> makeTender() {
        List<SalesTransactionTender> salesTransactionTenderList = new ArrayList<>();

        SalesTransactionTender tender = new SalesTransactionTender();
        tender.setTenderId("123456");
        tender.setTenderGroupId("123456");
        tender.setPaymentSign("S");

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        tender.setTaxIncludedPaymentAmount(price1);
        tender.setTenderInfoList(ErrorTableDataConverterTestHelper.makeTenderInfo());
        salesTransactionTenderList.add(tender);
        return salesTransactionTenderList;
    }

    public static SalesErrorSalesTransactionTender makeTenderEntity() {
        SalesErrorSalesTransactionTender tenderEntity = new SalesErrorSalesTransactionTender();
        tenderEntity.setTransactionId("salesTransactionErrorId10");
        tenderEntity.setOrderSubNumber(201);
        tenderEntity.setSalesTransactionId("salesTransactionId10");
        tenderEntity.setTenderSubNumber(301);

        tenderEntity.setTenderId("123456");
        tenderEntity.setTenderGroup("123456");
        tenderEntity.setPaymentSign("S");

        tenderEntity.setTaxIncludedPaymentAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tenderEntity.setTaxIncludedPaymentAmountValue(new BigDecimal(1));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tenderEntity.setCreateUserId("user10");
        tenderEntity.setCreateDatetime(nowDateTime);
        tenderEntity.setCreateProgramId("SLS0300110");
        tenderEntity.setUpdateUserId("user10");
        tenderEntity.setUpdateDatetime(nowDateTime);
        tenderEntity.setUpdateProgramId("SLS0300110");
        return tenderEntity;
    }

    public static TenderInfo makeTenderInfo() {
        TenderInfo tenderInfo = new TenderInfo();
        String str = "1234567890abcdefghij1234567890";
        tenderInfo.setDiscountCodeIdCorporateId(str);
        tenderInfo.setCouponType("credit");
        tenderInfo.setCouponUserId(str);
        tenderInfo.setCardNo(str);
        tenderInfo.setCreditApprovalCode(str);
        tenderInfo.setCreditProcessingSerialNumber(str);
        tenderInfo.setCreditAffiliatedStoreNumber(str);
        tenderInfo.setCreditPaymentType(str);
        tenderInfo.setCreditPaymentCount(12345);
        tenderInfo.setDiscountRate(new BigDecimal(1986));

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        tenderInfo.setDiscountAmount(price1);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(2));
        tenderInfo.setCouponMinUsageAmountThreshold(price2);

        Price price3 = getPrice();
        price3.setValue(new BigDecimal(3));
        tenderInfo.setCouponDiscountAmountSetting(price3);
        return tenderInfo;
    }

    public static SalesErrorSalesTransactionTenderInfo makeTenderInfoEntity() {
        SalesErrorSalesTransactionTenderInfo tenderInfoEntity =
                new SalesErrorSalesTransactionTenderInfo();
        String str = "1234567890abcdefghij1234567890";
        tenderInfoEntity.setTransactionId("salesTransactionError11");
        tenderInfoEntity.setOrderSubNumber(201);
        tenderInfoEntity.setSalesTransactionId("salesTransactionId11");
        tenderInfoEntity.setTenderGroup("tenderGroup11");
        tenderInfoEntity.setTenderId("301");
        tenderInfoEntity.setTenderSubNumber(1);

        tenderInfoEntity.setDiscountCodeIdCorporateId(str);
        tenderInfoEntity.setCouponType("credit");
        tenderInfoEntity.setCouponUserId(str);
        tenderInfoEntity.setCardNo(str);
        tenderInfoEntity.setCreditApprovalCode(str);
        tenderInfoEntity.setCreditProcessingSerialNumber(str);
        tenderInfoEntity.setCreditAffiliatedStoreNumber(str);
        tenderInfoEntity.setCreditPaymentType(str);
        tenderInfoEntity.setCreditPaymentCount(12345);
        tenderInfoEntity.setDiscountRate(new BigDecimal(1986));

        tenderInfoEntity.setDiscountValueCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tenderInfoEntity.setDiscountValue(new BigDecimal(1));

        tenderInfoEntity.setCouponMinUsageAmountThresholdCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tenderInfoEntity.setCouponMinUsageAmountThresholdValue(new BigDecimal(2));

        tenderInfoEntity.setCouponDiscountAmountSettingCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tenderInfoEntity.setCouponDiscountAmountSettingValue(new BigDecimal(3));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tenderInfoEntity.setCreateUserId("user11");
        tenderInfoEntity.setCreateDatetime(nowDateTime);
        tenderInfoEntity.setCreateProgramId("SLS0300111");
        tenderInfoEntity.setUpdateUserId("user11");
        tenderInfoEntity.setUpdateDatetime(nowDateTime);
        tenderInfoEntity.setUpdateProgramId("SLS0300111");
        return tenderInfoEntity;
    }

    public static List<SalesTransactionTaxDetail> makeTaxDetailOfTransaction() {
        List<SalesTransactionTaxDetail> salesTransactionTaxDetailList = new ArrayList<>();

        SalesTransactionTaxDetail taxDetail = new SalesTransactionTaxDetail();
        taxDetail.setTaxSubNumber(1234);
        taxDetail.setTaxGroup("1234");
        taxDetail.setTaxAmountSign("A");
        taxDetail.setTaxRate(new BigDecimal(1986));

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        taxDetail.setTaxAmount(price1);

        salesTransactionTaxDetailList.add(taxDetail);
        return salesTransactionTaxDetailList;
    }

    public static SalesErrorSalesTransactionTax makeTaxDetailOfTransactionEntity() {
        SalesErrorSalesTransactionTax taxEntity = new SalesErrorSalesTransactionTax();
        taxEntity.setTransactionId("salesTransactionError12");
        taxEntity.setOrderSubNumber(201);
        taxEntity.setSalesTransactionId("salesTransactionId12");
        taxEntity.setDetailSubNumber(301);
        taxEntity.setTaxSubNumber(401);

        taxEntity.setTaxGroup("1234");
        taxEntity.setTaxAmountSign("A");
        taxEntity.setTaxRate(new BigDecimal(1986));

        taxEntity.setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        taxEntity.setTaxAmountValue(new BigDecimal(1));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        taxEntity.setCreateUserId("user12");
        taxEntity.setCreateDatetime(nowDateTime);
        taxEntity.setCreateProgramId("SLS0300112");
        taxEntity.setUpdateUserId("user12");
        taxEntity.setUpdateDatetime(nowDateTime);
        taxEntity.setUpdateProgramId("SLS0300112");
        return taxEntity;
    }

    public static List<SalesTransactionTotal> makeTotal() {
        List<SalesTransactionTotal> salesTransactionTotalList = new ArrayList<>();

        SalesTransactionTotal total = new SalesTransactionTotal();
        String info =
                "123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc1234567890";
        total.setTotalType("1234567890");
        total.setSalesTransactionInformation1(info);
        total.setSalesTransactionInformation2(info);
        total.setSalesTransactionInformation3(info);
        total.setConsumptionTaxRate(new BigDecimal(1986));

        Price price1 = new Price();
        price1.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price1.setValue(new BigDecimal(1));
        total.setTotalAmountTaxExcluded(price1);

        Price price2 = new Price();
        price2.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price2.setValue(new BigDecimal(2));
        total.setTotalAmountTaxIncluded(price2);
        salesTransactionTotalList.add(total);
        return salesTransactionTotalList;
    }

    public static SalesErrorSalesTransactionTotalAmount makeTSalesErrorSalesTransactionTotalAmountEntity() {
        SalesErrorSalesTransactionTotalAmount totalAmountEntity =
                new SalesErrorSalesTransactionTotalAmount();
        String info =
                "123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc123456789123456789123456789abc1234567890";
        totalAmountEntity.setTransactionId("salesTransactionError13");
        totalAmountEntity.setOrderSubNumber(201);
        totalAmountEntity.setSalesTransactionId("salesTransactionId13");
        totalAmountEntity.setTotalAmountSubNumber(301);

        totalAmountEntity.setTotalType("1234567890");
        totalAmountEntity.setSalesTransactionInformation1(info);
        totalAmountEntity.setSalesTransactionInformation2(info);
        totalAmountEntity.setSalesTransactionInformation3(info);
        totalAmountEntity.setTaxRate(new BigDecimal(1986));

        totalAmountEntity.setTotalAmountTaxExcludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        totalAmountEntity.setTotalAmountTaxExcludedValue(new BigDecimal(1));

        totalAmountEntity.setTotalAmountTaxIncludedCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        totalAmountEntity.setTotalAmountTaxIncludedValue(new BigDecimal(2));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        totalAmountEntity.setCreateUserId("user13");
        totalAmountEntity.setCreateDatetime(nowDateTime);
        totalAmountEntity.setCreateProgramId("SLS0300113");
        totalAmountEntity.setUpdateUserId("user13");
        totalAmountEntity.setUpdateDatetime(nowDateTime);
        totalAmountEntity.setUpdateProgramId("SLS0300113");
        return totalAmountEntity;
    }
    
    private static Price getPrice() {
        Price price1 = new Price();
        price1.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price1.setValue(new BigDecimal(1));
        return price1;
    }
}