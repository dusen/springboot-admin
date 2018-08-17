package com.fastretailing.dcp.sales.importtransaction.converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import com.fastretailing.dcp.sales.common.constants.BusinessItem;
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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderTable;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTotalAmount;
import com.fastretailing.dcp.storecommon.dto.Price;
import com.fastretailing.dcp.storecommon.util.DateUtility;

/**
 * CoreTableDataConverter JUnit Test class.
 */
public class CoreTableDataConverterTestHelper {
    public static TransactionImportData makeImportData() {
        TransactionImportData importData = new TransactionImportData();
        importData.setUpdateType("CORRECTION");
        importData.setErrorCheckType(1);
        importData.setDataAlterationSalesLinkageType(1);
        importData.setDataAlterationBackboneLinkageType(1);

        importData.setIntegratedOrderId("320581198607101614abcdefghi");
        importData.setOrderBarcodeNumber("320581198607101614abcde");
        importData.setChannelCode("1234567890");

        importData.setStoreCode("1234567890");
        importData.setSystemBrandCode("1234");
        importData.setSystemBusinessCode("1234");
        importData.setSystemCountryCode("USA");

        importData.setCustomerId("320581198607101614abcdef123456");
        importData.setOrderConfirmationBusinessDate("2017-12-12");
        importData.setOrderConfirmationDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-12-12T11:11:11Z", "UTC"));

        importData.setDataCorrectionEditingFlag(true);
        importData.setDataCorrectionUserId("woshixiaodaimao1234567890abcde");
        importData.setSalesTransactionErrorId("123456789012345678901234567890123");

        List<Transaction> transactionList = CoreTableDataConverterTestHelper.makeHeader();
        importData.setTransactionList(transactionList);
        return importData;
    }

    public static SalesOrderInformation makeOrderEntity() {
        SalesOrderInformation expectEntity = new SalesOrderInformation();
        expectEntity.setUpdateType("CORRECTION");
        expectEntity.setErrorCheckType(1);
        expectEntity.setDataAlterationSalesLinkageType(1);
        expectEntity.setDataAlterationBackboneLinkageType(1);

        expectEntity.setIntegratedOrderId("320581198607101614abcdefghi");
        expectEntity.setOrderBarcodeNumber("320581198607101614abcde");
        expectEntity.setChannelCode("1234567890");

        expectEntity.setStoreCode("1234567890");
        expectEntity.setSystemBrandCode("1234");
        expectEntity.setSystemBusinessCode("1234");
        expectEntity.setSystemCountryCode("USA");

        expectEntity.setCustomerId("320581198607101614abcdef123456");
        expectEntity.setOrderConfirmationBusinessDate("2017-12-12");
        OffsetDateTime odt = DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-12-12T11:11:11Z",
                BusinessItem.ZONE_ID_UTC);
        LocalDateTime ldt = odt.toLocalDateTime();
        expectEntity.setOrderConfirmationDateTime(ldt);

        expectEntity.setDataAlterationEditingFlag(true);
        expectEntity.setDataAlterationUserId("woshixiaodaimao1234567890abcde");

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static List<Transaction> makeHeader() {
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setSalesTransactionId("1234567890abcdefghij1234567890");
        transaction.setOrderSubNumber(1986);
        transaction.setIntegratedOrderId("320581198607101614abcdefghi");
        transaction.setTokenCode("1234567890abcdefghij1234567890");

        transaction.setSalesLinkageType(1);
        transaction.setReturnType(1);
        transaction.setChannelCode("1234567890");
        transaction.setDataCreationBusinessDate("2018-12-12");
        transaction.setOrderStatusUpdateDate("2018-12-12");
        transaction.setOrderNumberForStorePayment("320581198607101614abcdefghi");

        transaction.setOperatorCode("abcdefghij");
        transaction.setCashRegisterNo(123);
        transaction.setReceiptNo("abcd");
        transaction.setOriginalCashRegisterNo(123);
        transaction.setOriginalReceiptNo("abcd");
        transaction.setOriginalTransactionId("320581198607101614abcdef123456");

        transaction.setReceiptNoForCreditCardCancellation("abcde123456");
        transaction.setReceiptNoForCreditCard("320581198607101614abcdef123456");
        transaction.setProcessingCompanyCode("320581198607101614ab");
        transaction.setSalesTransactionDiscountFlag(true);
        transaction.setCustomerId("320581198607101614abcdef123456");
        transaction.setCorporateId("320581198607101614ab");

        String status = "1234567890abcde12345678901234567890abcde1234567890";
        transaction.setOrderStatus(status);
        transaction.setOrderSubstatus(status);
        transaction.setPaymentStatus(status);
        transaction.setShipmentStatus(status);
        transaction.setReceivingStatus(status);
        transaction.setTransferOutStatus(status);

        transaction.setStoreCode("1234567890");
        transaction.setSystemBrandCode("1234");
        transaction.setSystemBusinessCode("1234");
        transaction.setSystemCountryCode("USA");
        transaction.setBookingStoreCode("1234567890");
        transaction.setBookingStoreSystemBrandCode("1234");
        transaction.setBookingStoreSystemBusinessCode("1234");
        transaction.setBookingStoreSystemCountryCode("USA");
        transaction.setShipmentStoreCode("1234567890");
        transaction.setShipmentStoreSystemBrandCode("1234");
        transaction.setShipmentStoreSystemBusinessCode("1234");
        transaction.setShipmentStoreSystemCountryCode("USA");
        transaction.setReceiptStoreCode("1234567890");
        transaction.setReceiptStoreSystemBrandCode("1234");
        transaction.setReceiptStoreSystemBusinessCode("1234");
        transaction.setReceiptStoreSystemCountryCode("USA");
        transaction.setPaymentStoreCode("1234567890");
        transaction.setPaymentStoreSystemBrandCode("1234");
        transaction.setPaymentStoreSystemBusinessCode("1234");
        transaction.setPaymentStoreSystemCountryCode("USA");
        transaction.setAdvanceReceivedStoreCode("1234567890");
        transaction.setAdvanceReceivedStoreSystemBrandCode("1234");
        transaction.setAdvanceReceivedStoreSystemBusinessCode("1234");
        transaction.setAdvanceReceivedStoreSystemCountryCode("USA");

        transaction.setConsistencySalesFlag(true);
        transaction.setEmployeeSaleFlag(true);
        transaction.setReceiptIssuedFlag(true);
        transaction.setEReceiptTargetFlag(true);

        transaction.setDataCreationDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-12-12T11:11:11Z", "UTC"));
        transaction.setOrderStatusLastUpdateDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-12-12T11:11:11Z", "UTC"));
        transaction.setOrderCancellationDate(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-12-12T11:11:11Z", "UTC"));
        transaction.setTransactionType("credit");

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));

        transaction.setSalesTransactionDiscountAmountRate(price1);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(2));
        transaction.setDeposit(price2);

        Price price3 = getPrice();
        price3.setValue(new BigDecimal(3));
        transaction.setChange(price3);

        List<TransactionItemDetail> transactionItemDetailList =
                CoreTableDataConverterTestHelper.makeItemDetail();
        transaction.setTransactionItemDetailList(transactionItemDetailList);

        List<NonItemDetail> nonItemDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDetail();
        transaction.setNonItemDetailList(nonItemDetailList);

        List<SalesTransactionTender> tenderList = CoreTableDataConverterTestHelper.makeTender();
        transaction.setSalesTransactionTenderList(tenderList);

        List<SalesTransactionTaxDetail> salesTransactionTaxDetailList =
                CoreTableDataConverterTestHelper.makeTaxDetailOfTransaction();
        transaction.setSalesTransactionTaxDetailList(salesTransactionTaxDetailList);

        List<SalesTransactionTotal> totalList = CoreTableDataConverterTestHelper.makeTotal();
        transaction.setSalesTransactionTotalList(totalList);

        transactionList.add(transaction);
        return transactionList;
    }

    public static SalesTransactionHeader makeHeaderEntity() {
        SalesTransactionHeader expectEntity = new SalesTransactionHeader();
        expectEntity.setSalesTransactionSubNumber(1986);

        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setIntegratedOrderId("320581198607101614abcdefghi");
        expectEntity.setTokenCode("1234567890abcdefghij1234567890");

        expectEntity.setSalesLinkageType(1);
        expectEntity.setReturnType(1);
        expectEntity.setChannelCode("1234567890");
        expectEntity.setDataCreationBusinessDate("2018-12-12");
        expectEntity.setOrderStatusUpdateDate("2018-12-12");
        expectEntity.setOrderNumberForStorePayment("320581198607101614abcdefghi");

        expectEntity.setOperatorCode("abcdefghij");
        expectEntity.setCashRegisterNo(123);
        expectEntity.setReceiptNo("abcd");
        expectEntity.setOriginalCashRegisterNo(123);
        expectEntity.setOriginalReceiptNo("abcd");
        expectEntity.setOriginalTransactionId("320581198607101614abcdef123456");

        expectEntity.setReceiptNoForCreditCardCancellation("abcde123456");
        expectEntity.setReceiptNoForCreditCard("320581198607101614abcdef123456");
        expectEntity.setProcessingCompanyCode("320581198607101614ab");
        expectEntity.setSalesTransactionDiscountFlag(true);
        expectEntity.setCustomerId("320581198607101614abcdef123456");
        expectEntity.setCorporateId("320581198607101614ab");

        String status = "1234567890abcde12345678901234567890abcde1234567890";
        expectEntity.setOrderStatus(status);
        expectEntity.setOrderSubstatus(status);
        expectEntity.setPaymentStatus(status);
        expectEntity.setShipmentStatus(status);
        expectEntity.setReceivingStatus(status);
        expectEntity.setTransferOutStatus(status);

        expectEntity.setStoreCode("1234567890");
        expectEntity.setSystemBrandCode("1234");
        expectEntity.setSystemBusinessCode("1234");
        expectEntity.setSystemCountryCode("USA");
        expectEntity.setBookingStoreCode("1234567890");
        expectEntity.setBookingStoreSystemBrandCode("1234");
        expectEntity.setBookingStoreSystemBusinessCode("1234");
        expectEntity.setBookingStoreSystemCountryCode("USA");
        expectEntity.setShipmentStoreCode("1234567890");
        expectEntity.setShipmentStoreSystemBrandCode("1234");
        expectEntity.setShipmentStoreSystemBusinessCode("1234");
        expectEntity.setShipmentStoreSystemCountryCode("USA");
        expectEntity.setReceiptStoreCode("1234567890");
        expectEntity.setReceiptStoreSystemBrandCode("1234");
        expectEntity.setReceiptStoreSystemBusinessCode("1234");
        expectEntity.setReceiptStoreSystemCountryCode("USA");
        expectEntity.setPaymentStoreCode("1234567890");
        expectEntity.setPaymentStoreSystemBrandCode("1234");
        expectEntity.setPaymentStoreSystemBusinessCode("1234");
        expectEntity.setPaymentStoreSystemCountryCode("USA");
        expectEntity.setAdvanceReceivedStoreCode("1234567890");
        expectEntity.setAdvanceReceivedStoreSystemBrandCode("1234");
        expectEntity.setAdvanceReceivedStoreSystemBusinessCode("1234");
        expectEntity.setAdvanceReceivedStoreSystemCountryCode("USA");

        expectEntity.setConsistencySalesFlag(true);
        expectEntity.setEmployeeSaleFlag(true);
        expectEntity.setReceiptIssuedFlag(true);
        expectEntity.setEReceiptTargetFlag(true);

        OffsetDateTime odt = DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-12-12T11:11:11Z",
                BusinessItem.ZONE_ID_UTC);
        LocalDateTime ldt = odt.toLocalDateTime();
        expectEntity.setDataCreationDateTime(ldt);
        expectEntity.setOrderStatusLastUpdateDateTime(ldt);
        expectEntity.setOrderCancelledDateTime(ldt);
        expectEntity.setSalesTransactionType("credit");

        expectEntity.setSalesTransactionDiscountAmountRateCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setSalesTransactionDiscountAmountRate(new BigDecimal(1));
        expectEntity.setDepositCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setDepositValue(new BigDecimal(2));
        expectEntity.setChangeCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setChangeValue(new BigDecimal(3));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static List<TransactionItemDetail> makeItemDetail() {
        List<TransactionItemDetail> itemDetailList = new ArrayList<>();
        TransactionItemDetail itemDetail = new TransactionItemDetail();
        itemDetail.setSystemBrandCode("abcd");
        itemDetail.setL2ItemCode("320581198607101614ab");
        itemDetail.setL3ItemCode("320581198607101614abcdefg");

        String productName =
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890abcdefghij";
        itemDetail.setL2ProductName(productName);
        itemDetail.setL3PosProductName(productName);

        itemDetail.setEpcCode("320581198607101614abcdef");
        itemDetail.setGDepartmentCode("abcdef");

        itemDetail.setQuantityCode("A");
        itemDetail.setCalculationUnavailableType("correction");
        itemDetail.setOrderStatusUpdateDate("2018-12-12");

        String status = "1234567890abcde12345678901234567890abcde1234567890";
        itemDetail.setOrderStatus(status);
        itemDetail.setOrderSubstatus(status);

        itemDetail.setContributionSalesRepresentative("1234567890");
        itemDetail.setCustomerId("320581198607101614abcdef123456");
        itemDetail.setBundlePurchaseIndex(123);
        itemDetail.setLimitedAmountPromotionCount(99);
        itemDetail.setBundleSalesDetailIndex(123);

        itemDetail.setBookingStoreCode("1234567890");
        itemDetail.setBookingStoreSystemBrandCode("1234");
        itemDetail.setBookingStoreSystemBusinessCode("1234");
        itemDetail.setBookingStoreSystemCountryCode("USA");
        itemDetail.setShipmentStoreCode("1234567890");
        itemDetail.setShipmentStoreSystemBrandCode("1234");
        itemDetail.setShipmentStoreSystemBusinessCode("1234");
        itemDetail.setShipmentStoreSystemCountryCode("USA");
        itemDetail.setReceiptStoreCode("1234567890");
        itemDetail.setReceiptStoreSystemBrandCode("1234");
        itemDetail.setReceiptStoreSystemBusinessCode("1234");
        itemDetail.setReceiptStoreSystemCountryCode("USA");

        itemDetail.setOrderStatusLastUpdateDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-12-12T11:11:11Z", "UTC"));
        itemDetail.setDetailListSalesTransactionType("credit");
        itemDetail.setViewL2ItemCode("320581198607101614abcdefg");
        itemDetail.setDeptCode(1234);

        itemDetail.setItemQty(1234);
        itemDetail.setBundlePurchaseQty(99);
        itemDetail.setItemMountDiscountType("abcdefghij");
        itemDetail.setBundleSalesFlag(true);
        itemDetail.setItemTaxationType("abcdefghij");
        itemDetail.setItemTaxKind("abcdefghij");

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        itemDetail.setItemCost(price1);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(2));
        itemDetail.setInitialSellingPrice(price2);

        Price price3 = getPrice();
        price3.setValue(new BigDecimal(3));
        itemDetail.setBItemSellingPrice(price3);

        Price price4 = getPrice();
        price4.setValue(new BigDecimal(4));
        itemDetail.setItemNewPrice(price4);

        Price price5 = getPrice();
        price5.setValue(new BigDecimal(5));
        itemDetail.setItemUnitPriceTaxExcluded(price5);

        Price price6 = getPrice();
        price6.setValue(new BigDecimal(6));
        itemDetail.setItemUnitPriceTaxIncluded(price6);

        Price price7 = getPrice();
        price7.setValue(new BigDecimal(7));
        itemDetail.setItemSalesAmtTaxExcluded(price7);

        Price price8 = getPrice();
        price8.setValue(new BigDecimal(8));
        itemDetail.setItemSalesAmtTaxIncluded(price8);

        Price price9 = getPrice();
        price9.setValue(new BigDecimal(9));
        itemDetail.setBundlePurchasePrice(price9);

        Price price10 = getPrice();
        itemDetail.setItemDiscountAmount(price10);

        Price price11 = getPrice();
        itemDetail.setBundleSalesPrice(price11);

        List<NonItemDetail> nonItemDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDetail();
        itemDetail.setNonItemDetailListByItem(nonItemDetailList);

        List<ItemDiscount> itemDiscountList = CoreTableDataConverterTestHelper.makeItemDiscount();
        itemDetail.setItemDiscountList(itemDiscountList);

        List<ItemTaxDetail> itemTaxDetailList =
                CoreTableDataConverterTestHelper.makeItemTaxDetail();
        itemDetail.setItemTaxDetailList(itemTaxDetailList);

        itemDetailList.add(itemDetail);

        return itemDetailList;
    }

    public static SalesTransactionDetail makeItemDetailEntity() {
        SalesTransactionDetail expectEntity = new SalesTransactionDetail();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setDetailSubNumber(1986);
        expectEntity.setItemDetailSubNumber(1986);
        expectEntity.setProductClassification("ITEM");

        expectEntity.setSystemBrandCode("abcd");
        expectEntity.setL2ItemCode("320581198607101614ab");
        expectEntity.setL3ItemCode("320581198607101614abcdefg");

        String productName =
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890abcdefghij";
        expectEntity.setL2ProductName(productName);
        expectEntity.setL3PosProductName(productName);

        expectEntity.setEpcCode("320581198607101614abcdef");
        expectEntity.setGDepartmentCode("abcdef");

        expectEntity.setQuantityCode("A");
        expectEntity.setCalculationUnavailableType("correction");
        expectEntity.setOrderStatusUpdateDate("2018-12-12");

        String status = "1234567890abcde12345678901234567890abcde1234567890";
        expectEntity.setOrderStatus(status);
        expectEntity.setOrderSubstatus(status);

        expectEntity.setContributionSalesRepresentative("1234567890");
        expectEntity.setCustomerId("320581198607101614abcdef123456");
        expectEntity.setBundlePurchaseIndex(123);
        expectEntity.setLimitedAmountPromotionCount(99);
        expectEntity.setSetSalesDetailIndex(123);

        expectEntity.setBookingStoreCode("1234567890");
        expectEntity.setBookingStoreSystemBrandCode("1234");
        expectEntity.setBookingStoreSystemBusinessCode("1234");
        expectEntity.setBookingStoreSystemCountryCode("USA");
        expectEntity.setShipmentStoreCode("1234567890");
        expectEntity.setShipmentStoreSystemBrandCode("1234");
        expectEntity.setShipmentStoreSystemBusinessCode("1234");
        expectEntity.setShipmentStoreSystemCountryCode("USA");
        expectEntity.setReceiptStoreCode("1234567890");
        expectEntity.setReceiptStoreSystemBrandCode("1234");
        expectEntity.setReceiptStoreSystemBusinessCode("1234");
        expectEntity.setReceiptStoreSystemCountryCode("USA");

        OffsetDateTime odt = DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-12-12T11:11:11Z",
                BusinessItem.ZONE_ID_UTC);
        LocalDateTime ldt = odt.toLocalDateTime();
        expectEntity.setOrderStatusLastUpdateDateTime(ldt);
        expectEntity.setSalesTransactionType("credit");
        expectEntity.setDisplayL2ItemCode("320581198607101614abcdefg");
        expectEntity.setMajorCategoryCode("1234");

        expectEntity.setDetailQuantity(new BigDecimal(1234));
        expectEntity.setBundlePurchaseApplicableQuantity(new BigDecimal(99));
        expectEntity.setStoreItemDiscountType("abcdefghij");

        expectEntity.setStoreBundleSaleFlag(false);
        expectEntity.setTaxationType("abcdefghij");
        expectEntity.setTaxSystemType("abcdefghij");

        expectEntity.setItemCostCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setItemCostValue(new BigDecimal(1));
        expectEntity.setInitialSellingPriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setInitialSellingPrice(new BigDecimal(2));
        expectEntity
                .setBclassPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setBclassPrice(new BigDecimal(3));
        expectEntity.setNewPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setNewPrice(new BigDecimal(4));

        expectEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setRetailUnitPriceTaxExcluded(new BigDecimal(5));
        expectEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setRetailUnitPriceTaxIncluded(new BigDecimal(6));
        expectEntity.setSalesAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setSalesAmountTaxExcluded(new BigDecimal(7));
        expectEntity.setSalesAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setSalesAmountTaxIncluded(new BigDecimal(8));

        expectEntity.setBundlePurchaseApplicablePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setBundlePurchaseApplicablePrice(new BigDecimal(9));
        expectEntity.setStoreItemDiscountCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setStoreItemDiscountSetting(new BigDecimal(1));
        expectEntity.setStoreBundleSalePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setStoreBundleSalePrice(new BigDecimal(1));
        expectEntity.setStoreBundleSaleFlag(true);

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static List<NonItemDetail> makeNonItemDetail() {
        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = new NonItemDetail();
        nonItemDetail.setNonMdDetailListSalesTransactionType("abcdefghij");

        nonItemDetail.setNonMdType("credit");
        nonItemDetail.setServiceCode("abcde");

        nonItemDetail.setQuantityCode("A");
        nonItemDetail.setOrderStatusUpdateDate("2018-12-12");

        String status = "1234567890abcde12345678901234567890abcde1234567890";
        nonItemDetail.setOrderStatus(status);
        nonItemDetail.setOrderSubstatus(status);
        nonItemDetail.setContributionSalesRepresentative("1234567890");

        nonItemDetail.setBookingStoreCode("1234567890");
        nonItemDetail.setBookingStoreSystemBrandCode("1234");
        nonItemDetail.setBookingStoreSystemBusinessCode("1234");
        nonItemDetail.setBookingStoreSystemCountryCode("USA");
        nonItemDetail.setShipmentStoreCode("1234567890");
        nonItemDetail.setShipmentStoreSystemBrandCode("1234");
        nonItemDetail.setShipmentStoreSystemBusinessCode("1234");
        nonItemDetail.setShipmentStoreSystemCountryCode("USA");
        nonItemDetail.setReceiptStoreCode("1234567890");
        nonItemDetail.setReceiptStoreSystemBrandCode("1234");
        nonItemDetail.setReceiptStoreSystemBusinessCode("1234");
        nonItemDetail.setReceiptStoreSystemCountryCode("USA");

        nonItemDetail.setOrderStatusLastUpdateDateTime(
                DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-12-12T11:11:11Z", "UTC"));

        nonItemDetail.setNonItemCode("1234567890abcde1234567890");
        String itemName =
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890abcdefghij";
        nonItemDetail.setPosNonItemName(itemName);

        nonItemDetail.setNonItemQty(1234);
        nonItemDetail.setNonCalculationNonItemType("correction");
        nonItemDetail.setNonItemTaxationType("abcdefghij");
        nonItemDetail.setNonItemTaxKind("abcdefghij");

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
        nonItemInfo.setKeyCode("1234567890abcdefghij");

        String codeValue = "1234567890abcdefghij12345";
        nonItemInfo.setCodeValue1(codeValue);
        nonItemInfo.setCodeValue2(codeValue);
        nonItemInfo.setCodeValue3(codeValue);
        nonItemInfo.setCodeValue4(codeValue);

        String name =
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890abcdefghij";
        nonItemInfo.setName1(name);
        nonItemInfo.setName2(name);
        nonItemInfo.setName3(name);
        nonItemInfo.setName4(name);
        nonItemDetail.setNonItemInfo(nonItemInfo);

        List<NonItemDiscountDetail> nonItemDiscountDetailList =
                CoreTableDataConverterTestHelper.makeNonItemDiscountDetail();
        nonItemDetail.setNonItemDiscountDetailList(nonItemDiscountDetailList);

        List<NonItemTaxDetail> nonItemTaxDetailList =
                CoreTableDataConverterTestHelper.makeNonItemTaxDetail1();
        nonItemDetail.setNonItemTaxDetailList(nonItemTaxDetailList);

        nonItemDetailList.add(nonItemDetail);
        return nonItemDetailList;
    }

    public static SalesTransactionDetail makeNonItemDetailEntity() {
        SalesTransactionDetail expectEntity = new SalesTransactionDetail();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setDetailSubNumber(1986);
        expectEntity.setItemDetailSubNumber(1986);
        expectEntity.setProductClassification("NMITEM");

        String salesTransactionType = "correction";
        expectEntity.setSalesTransactionType(salesTransactionType);

        expectEntity.setNonMdType("credit");
        expectEntity.setServiceCode("abcde");

        expectEntity.setQuantityCode("A");
        expectEntity.setOrderStatusUpdateDate("2018-12-12");

        String status = "1234567890abcde12345678901234567890abcde1234567890";
        expectEntity.setOrderStatus(status);
        expectEntity.setOrderSubstatus(status);
        expectEntity.setContributionSalesRepresentative("1234567890");

        expectEntity.setBookingStoreCode("1234567890");
        expectEntity.setBookingStoreSystemBrandCode("1234");
        expectEntity.setBookingStoreSystemBusinessCode("1234");
        expectEntity.setBookingStoreSystemCountryCode("USA");
        expectEntity.setShipmentStoreCode("1234567890");
        expectEntity.setShipmentStoreSystemBrandCode("1234");
        expectEntity.setShipmentStoreSystemBusinessCode("1234");
        expectEntity.setShipmentStoreSystemCountryCode("USA");
        expectEntity.setReceiptStoreCode("1234567890");
        expectEntity.setReceiptStoreSystemBrandCode("1234");
        expectEntity.setReceiptStoreSystemBusinessCode("1234");
        expectEntity.setReceiptStoreSystemCountryCode("USA");

        OffsetDateTime odt = DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-12-12T11:11:11Z",
                BusinessItem.ZONE_ID_UTC);
        LocalDateTime ldt = odt.toLocalDateTime();
        expectEntity.setOrderStatusLastUpdateDateTime(ldt);

        expectEntity.setNonMdCode("1234567890abcde1234567890");
        String itemName =
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890abcdefghij";
        expectEntity.setL3PosProductName(itemName);

        expectEntity.setDetailQuantity(new BigDecimal(1234));
        expectEntity.setCalculationUnavailableType("correction");
        expectEntity.setTaxationType("abcdefghij");
        expectEntity.setTaxSystemType("abcdefghij");

        expectEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        expectEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setRetailUnitPriceTaxIncluded(new BigDecimal(2));
        expectEntity.setSalesAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setSalesAmountTaxExcluded(new BigDecimal(3));
        expectEntity.setSalesAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setSalesAmountTaxIncluded(new BigDecimal(4));
        expectEntity.setNewPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setNewPrice(new BigDecimal(5));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static SalesTransactionDetail makeNonItemDetailEntityUpdate() {
        SalesTransactionDetail expectEntity = new SalesTransactionDetail();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setDetailSubNumber(1986);

        expectEntity.setOrderStatusUpdateDate("2018-12-12");

        String status = "1234567890abcde12345678901234567890abcde1234567890";
        expectEntity.setOrderStatus(status);
        expectEntity.setOrderSubstatus(status);

        OffsetDateTime odt = DateUtility.changeUtcTimeZoneDatetimeByZoneId("2018-12-12T11:11:11Z",
                BusinessItem.ZONE_ID_UTC);
        LocalDateTime ldt = odt.toLocalDateTime();
        expectEntity.setOrderStatusLastUpdateDateTime(ldt);

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static SalesTransactionDetailInfo makeNonItemInfoEntity() {
        SalesTransactionDetailInfo expectEntity = new SalesTransactionDetailInfo();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setDetailSubNumber(1986);
        expectEntity.setItemDetailSubNumber(1986);

        expectEntity.setKeyCode("1234567890abcdefghij");

        String codeValue = "1234567890abcdefghij12345";
        expectEntity.setCodeValue1(codeValue);
        expectEntity.setCodeValue2(codeValue);
        expectEntity.setCodeValue3(codeValue);
        expectEntity.setCodeValue4(codeValue);

        String name =
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890abcdefghij";
        expectEntity.setName1(name);
        expectEntity.setName2(name);
        expectEntity.setName3(name);
        expectEntity.setName4(name);

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static List<NonItemDiscountDetail> makeNonItemDiscountDetail() {
        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();
        nonItemDiscountDetail.setNonItemPromotionType("abcd");
        nonItemDiscountDetail.setNonItemStoreDiscountType("ab");
        nonItemDiscountDetail.setNonItemQuantityCode("A");
        nonItemDiscountDetail.setNonItemDiscountQty(99);

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        nonItemDiscountDetail.setNonItemDiscountAmtTaxExcluded(price1);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(2));
        nonItemDiscountDetail.setNonItemDiscountAmtTaxIncluded(price2);

        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        return nonItemDiscountDetailList;
    }

    public static SalesTransactionDiscount makeNonItemDiscountDetailEntity() {
        SalesTransactionDiscount expectEntity = new SalesTransactionDiscount();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setDetailSubNumber(1986);
        expectEntity.setItemDiscountSubNumber(1986);
        expectEntity.setPromotionNo("0");

        expectEntity.setPromotionType("abcd");
        expectEntity.setStoreDiscountType("ab");
        expectEntity.setQuantityCode("A");
        expectEntity.setDiscountQuantity(99);

        expectEntity.setDiscountAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setDiscountAmountTaxExcluded(new BigDecimal(1));
        expectEntity.setDiscountAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setDiscountAmountTaxIncluded(new BigDecimal(2));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static List<NonItemTaxDetail> makeNonItemTaxDetail1() {
        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();
        String nonItemTaxName =
                "1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde";
        nonItemTaxDetail.setNonItemTaxName(nonItemTaxName);
        nonItemTaxDetail.setNonItemTaxAmountSign("A");
        nonItemTaxDetail.setNonItemTaxRate(new BigDecimal(1986));
        nonItemTaxDetail.setNonItemTaxType("1234567890");

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        nonItemTaxDetail.setNonItemTaxAmt(price1);

        nonItemTaxDetailList.add(nonItemTaxDetail);
        return nonItemTaxDetailList;
    }

    public static SalesTransactionTax makeNonItemTaxDetail1Entity() {
        SalesTransactionTax expectEntity = new SalesTransactionTax();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setDetailSubNumber(1986);
        expectEntity.setTaxSubNumber(1986);

        String taxName =
                "1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde";
        expectEntity.setTaxName(taxName);
        expectEntity.setTaxAmountSign("A");
        expectEntity.setTaxRate(new BigDecimal(1986));
        expectEntity.setTaxGroup("1234567890");

        expectEntity.setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setTaxAmountValue(new BigDecimal(1));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static List<ItemDiscount> makeItemDiscount() {
        List<ItemDiscount> itemDiscountList = new ArrayList<>();
        ItemDiscount itemDiscount = new ItemDiscount();
        itemDiscount.setItemPromotionType("abcd");
        itemDiscount.setItemPromotionNumber("1234567890");
        itemDiscount.setItemStoreDiscountType("ab");
        itemDiscount.setItemQuantityCode("A");
        itemDiscount.setItemDiscountQty(99);

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        itemDiscount.setItemDiscountAmtTaxExcluded(price1);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(2));
        itemDiscount.setItemDiscountAmtTaxIncluded(price2);

        itemDiscountList.add(itemDiscount);
        return itemDiscountList;
    }

    public static SalesTransactionDiscount makeItemDiscountEntity() {
        SalesTransactionDiscount expectEntity = new SalesTransactionDiscount();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setDetailSubNumber(1986);
        expectEntity.setItemDiscountSubNumber(1986);

        expectEntity.setPromotionType("abcd");
        expectEntity.setPromotionNo("1234567890");
        expectEntity.setStoreDiscountType("ab");
        expectEntity.setQuantityCode("A");
        expectEntity.setDiscountQuantity(99);

        expectEntity.setDiscountAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setDiscountAmountTaxExcluded(new BigDecimal(1));
        expectEntity.setDiscountAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setDiscountAmountTaxIncluded(new BigDecimal(2));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static List<ItemTaxDetail> makeItemTaxDetail() {
        List<ItemTaxDetail> itemTaxDetailList = new ArrayList<>();
        ItemTaxDetail itemTaxDetail = new ItemTaxDetail();
        String itemTaxName =
                "1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde";
        itemTaxDetail.setItemTaxName(itemTaxName);
        itemTaxDetail.setItemTaxAmountSign("A");
        itemTaxDetail.setItemTaxRate(new BigDecimal(99));
        itemTaxDetail.setItemTaxType("1234567890");

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        itemTaxDetail.setItemTaxAmt(price1);

        itemTaxDetailList.add(itemTaxDetail);
        return itemTaxDetailList;
    }

    public static SalesTransactionTax makeItemTaxDetailEntity() {
        SalesTransactionTax expectEntity = new SalesTransactionTax();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setDetailSubNumber(0);
        expectEntity.setTaxSubNumber(1986);

        String taxName =
                "1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde1234567890abcde";
        expectEntity.setTaxName(taxName);
        expectEntity.setTaxAmountSign("A");
        expectEntity.setTaxRate(new BigDecimal(99));
        expectEntity.setTaxGroup("1234567890");

        expectEntity.setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setTaxAmountValue(new BigDecimal(1));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static List<SalesTransactionTender> makeTender() {
        List<SalesTransactionTender> tenderList = new ArrayList<>();
        SalesTransactionTender tender = new SalesTransactionTender();
        tender.setTenderId("123456");
        tender.setPaymentSign("A");
        tender.setTenderGroupId("abcdef");

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        tender.setTaxIncludedPaymentAmount(price1);

        tender.setTenderInfoList(CoreTableDataConverterTestHelper.makeTenderInfo());

        tenderList.add(tender);
        return tenderList;
    }

    public static SalesTransactionTenderTable makeTenderEntity() {
        SalesTransactionTenderTable expectEntity = new SalesTransactionTenderTable();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setTenderSubNumber(1986);

        expectEntity.setTenderId("123456");
        expectEntity.setPaymentSign("A");
        expectEntity.setTenderGroup("abcdef");

        expectEntity.setTaxIncludedPaymentAmountCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setTaxIncludedPaymentAmountValue(new BigDecimal(1));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static TenderInfo makeTenderInfo() {
        TenderInfo tenderInfo = new TenderInfo();
        tenderInfo.setDiscountCodeIdCorporateId("1234567890abcdefghij1234567890");
        tenderInfo.setCouponType("credit");

        tenderInfo.setCouponUserId("1234567890abcdefghij1234567890");

        String str = "1234567890abcdefghij1234567890";
        tenderInfo.setCardNo(str);
        tenderInfo.setCreditApprovalCode(str);
        tenderInfo.setCreditProcessingSerialNumber(str);
        tenderInfo.setCreditPaymentType(str);
        tenderInfo.setCreditAffiliatedStoreNumber(str);
        tenderInfo.setCreditPaymentCount(12345);

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        tenderInfo.setCouponMinUsageAmountThreshold(price1);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(2));
        tenderInfo.setCouponDiscountAmountSetting(price2);

        Price price3 = getPrice();
        price3.setValue(new BigDecimal(3));
        tenderInfo.setDiscountAmount(price3);

        /*
         * It is neccessary to confirm this item wheather it is need to insert to the db. Price
         * price4 = new Price();
         * price4.setCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
         * price4.setValue(new BigDecimal(4)); tenderInfo.setCouponDiscountValue(price4);
         */

        return tenderInfo;
    }

    public static SalesTransactionTenderInfo makeTenderInfoEntity() {
        SalesTransactionTenderInfo expectEntity = new SalesTransactionTenderInfo();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setTenderId("123456");
        expectEntity.setTenderGroup("123456");
        expectEntity.setTenderSubNumber(1);

        expectEntity.setDiscountCodeIdCorporateId("1234567890abcdefghij1234567890");
        expectEntity.setCouponType("credit");
        expectEntity.setCouponUserId("1234567890abcdefghij1234567890");

        String str = "1234567890abcdefghij1234567890";
        expectEntity.setCardNo(str);
        expectEntity.setCreditApprovalCode(str);
        expectEntity.setCreditProcessingSerialNumber(str);
        expectEntity.setCreditPaymentType(str);
        expectEntity.setCreditAffiliatedStoreNumber(str);
        expectEntity.setCreditPaymentCount(12345);

        expectEntity.setCouponMinUsageAmountThresholdCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setCouponMinUsageAmountThresholdValue(new BigDecimal(1));
        expectEntity.setCouponDiscountAmountSettingCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setCouponDiscountAmountSettingValue(new BigDecimal(2));
        expectEntity
                .setDiscountValueCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setDiscountValue(new BigDecimal(3));

        /*
         * It is neccessary to confirm this item wheather it is need to insert to the db.
         * expectEntity.set expectEntity.set
         */

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static List<SalesTransactionTaxDetail> makeTaxDetailOfTransaction() {
        List<SalesTransactionTaxDetail> transactionTaxDetailList = new ArrayList<>();
        SalesTransactionTaxDetail transactionTaxDetail = new SalesTransactionTaxDetail();

        transactionTaxDetail.setTaxAmountSign("A");
        transactionTaxDetail.setTaxGroup("1234567890");
        transactionTaxDetail.setTaxRate(new BigDecimal(1986));

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        transactionTaxDetail.setTaxAmount(price1);

        transactionTaxDetailList.add(transactionTaxDetail);
        return transactionTaxDetailList;
    }

    public static SalesTransactionTax makeTaxDetailOfTransactionEntity() {
        SalesTransactionTax expectEntity = new SalesTransactionTax();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setDetailSubNumber(0);
        expectEntity.setTaxSubNumber(1986);

        expectEntity.setTaxAmountSign("A");
        expectEntity.setTaxGroup("1234567890");
        expectEntity.setTaxRate(new BigDecimal(1986));

        expectEntity.setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setTaxAmountValue(new BigDecimal(1));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    public static List<SalesTransactionTotal> makeTotal() {
        List<SalesTransactionTotal> totalList = new ArrayList<>();
        SalesTransactionTotal total = new SalesTransactionTotal();
        total.setTotalType("xiaodaimao");
        String info =
                "1234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij1234567890xiaodaimao";
        total.setSalesTransactionInformation1(info);
        total.setSalesTransactionInformation2(info);
        total.setSalesTransactionInformation3(info);
        total.setConsumptionTaxRate(new BigDecimal(1986));

        Price price1 = getPrice();
        price1.setValue(new BigDecimal(1));
        total.setTotalAmountTaxExcluded(price1);

        Price price2 = getPrice();
        price2.setValue(new BigDecimal(2));
        total.setTotalAmountTaxIncluded(price2);

        totalList.add(total);
        return totalList;
    }

    public static SalesTransactionTotalAmount makeTotalEntity() {
        SalesTransactionTotalAmount expectEntity = new SalesTransactionTotalAmount();
        expectEntity.setSalesTransactionId("1234567890abcdefghij1234567890");
        expectEntity.setOrderSubNumber(1986);
        expectEntity.setTotalAmountSubNumber(1986);

        expectEntity.setTotalType("xiaodaimao");
        String info =
                "1234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij12345678901234567890abcdefghij1234567890xiaodaimao";
        expectEntity.setSalesTransactionInformation1(info);
        expectEntity.setSalesTransactionInformation2(info);
        expectEntity.setSalesTransactionInformation3(info);
        expectEntity.setTaxRate(new BigDecimal(1986));

        expectEntity.setTotalAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setTotalAmountTaxExcludedValue(new BigDecimal(1));
        expectEntity.setTotalAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        expectEntity.setTotalAmountTaxIncludedValue(new BigDecimal(2));

        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        expectEntity.setCreateUserId("001");
        expectEntity.setCreateDatetime(nowDateTime);
        expectEntity.setCreateProgramId("SLS0300112");
        expectEntity.setUpdateUserId("001");
        expectEntity.setUpdateDatetime(nowDateTime);
        expectEntity.setUpdateProgramId("SLS0300112");
        return expectEntity;
    }

    private static Price getPrice() {
        Price price1 = new Price();
        price1.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price1.setValue(new BigDecimal(1));
        return price1;
    }
}
