package com.fastretailing.dcp.sales.importtransaction.converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
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
public class HistoryTableDataConverterTestHelper {

    public static SalesErrorSalesOrderInformation makeTSalesErrorSalesOrderInformation() {
        SalesErrorSalesOrderInformation tSalesErrorSalesOrderInformationEntity =
                new SalesErrorSalesOrderInformation();
        tSalesErrorSalesOrderInformationEntity.setTransactionId("TransactionId01");
        tSalesErrorSalesOrderInformationEntity.setIntegratedOrderId("IntegratedOrderId01");
        tSalesErrorSalesOrderInformationEntity.setOrderBarcodeNumber("320581198607101614abcdefgh");
        tSalesErrorSalesOrderInformationEntity.setStoreCode("1234567890");
        tSalesErrorSalesOrderInformationEntity.setSystemBrandCode("1234");
        tSalesErrorSalesOrderInformationEntity.setSystemBusinessCode("1234");
        tSalesErrorSalesOrderInformationEntity.setSystemCountryCode("USA");
        tSalesErrorSalesOrderInformationEntity.setChannelCode("A1");
        tSalesErrorSalesOrderInformationEntity.setUpdateType("ERRORERROR");
        tSalesErrorSalesOrderInformationEntity.setCustomerId("320581198607101614123456123456");
        tSalesErrorSalesOrderInformationEntity.setOrderConfirmationBusinessDate("2017-12-12");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tSalesErrorSalesOrderInformationEntity.setOrderConfirmationDateTime(nowDateTime);
        tSalesErrorSalesOrderInformationEntity.setErrorCheckType(9);
        tSalesErrorSalesOrderInformationEntity.setDataAlterationSalesLinkageType(9);
        tSalesErrorSalesOrderInformationEntity.setDataAlterationBackboneLinkageType(9);
        tSalesErrorSalesOrderInformationEntity.setDataAlterationEditingFlag(true);
        tSalesErrorSalesOrderInformationEntity
                .setDataAlterationUserId("UUU000000000000000000000user01");
        tSalesErrorSalesOrderInformationEntity.setCreateUserId("user01");
        tSalesErrorSalesOrderInformationEntity.setCreateDatetime(nowDateTime);
        tSalesErrorSalesOrderInformationEntity.setCreateProgramId("SLS0300101");
        tSalesErrorSalesOrderInformationEntity.setUpdateUserId("user01");
        tSalesErrorSalesOrderInformationEntity.setUpdateDatetime(nowDateTime);
        tSalesErrorSalesOrderInformationEntity.setUpdateProgramId("SLS0300101");

        return tSalesErrorSalesOrderInformationEntity;
    }

    public static SalesErrorSalesTransactionHeader makeTSalesErrorSalesTransactionHeader() {
        SalesErrorSalesTransactionHeader salesErrorSalesTransactionHeaderEntity =
                new SalesErrorSalesTransactionHeader();

        salesErrorSalesTransactionHeaderEntity.setOrderSubNumber(203);
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionId("SalesTransactionId03");
        salesErrorSalesTransactionHeaderEntity.setIntegratedOrderId("320581198607101614abcdefghi");
        salesErrorSalesTransactionHeaderEntity.setTransactionId("TransactionId03");
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionSubNumber(1);
        salesErrorSalesTransactionHeaderEntity.setStoreCode("a123456789");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 22);
        salesErrorSalesTransactionHeaderEntity.setDataCreationDateTime(nowDateTime);
        salesErrorSalesTransactionHeaderEntity.setDataCreationBusinessDate("2018-12-12");
        salesErrorSalesTransactionHeaderEntity.setCashRegisterNo(5);
        salesErrorSalesTransactionHeaderEntity.setReceiptNo("a123456789");
        salesErrorSalesTransactionHeaderEntity.setSalesLinkageType(3);
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionType("a00002");
        salesErrorSalesTransactionHeaderEntity.setReturnType(4);
        salesErrorSalesTransactionHeaderEntity.setSystemBrandCode("A001");
        salesErrorSalesTransactionHeaderEntity.setSystemBusinessCode("A002");
        salesErrorSalesTransactionHeaderEntity.setSystemCountryCode("USA");
        salesErrorSalesTransactionHeaderEntity.setChannelCode("a1");
        salesErrorSalesTransactionHeaderEntity.setOrderStatus("co1");
        salesErrorSalesTransactionHeaderEntity.setOrderSubstatus("co2");
        salesErrorSalesTransactionHeaderEntity.setOrderStatusUpdateDate("2018-12-12");
        salesErrorSalesTransactionHeaderEntity.setOrderStatusLastUpdateDateTime(nowDateTime);
        salesErrorSalesTransactionHeaderEntity.setOrderCancelledDateTime(nowDateTime);
        salesErrorSalesTransactionHeaderEntity.setBookingStoreCode("123456789C");
        salesErrorSalesTransactionHeaderEntity.setBookingStoreSystemBrandCode("abc5");
        salesErrorSalesTransactionHeaderEntity.setBookingStoreSystemBusinessCode("abc6");
        salesErrorSalesTransactionHeaderEntity.setBookingStoreSystemCountryCode("USA");
        salesErrorSalesTransactionHeaderEntity.setPaymentStatus("co3");
        salesErrorSalesTransactionHeaderEntity.setPaymentStoreCode("b234567890");
        salesErrorSalesTransactionHeaderEntity.setPaymentStoreSystemBrandCode("cas1");
        salesErrorSalesTransactionHeaderEntity.setPaymentStoreSystemBusinessCode("cas2");
        salesErrorSalesTransactionHeaderEntity.setPaymentStoreSystemCountryCode("USA");
        salesErrorSalesTransactionHeaderEntity.setTransferOutStatus("co6");
        salesErrorSalesTransactionHeaderEntity.setShipmentStatus("co4");
        salesErrorSalesTransactionHeaderEntity.setShipmentStoreCode("123456789D");
        salesErrorSalesTransactionHeaderEntity.setShipmentStoreSystemBrandCode("abc7");
        salesErrorSalesTransactionHeaderEntity.setShipmentStoreSystemBusinessCode("abc8");
        salesErrorSalesTransactionHeaderEntity.setShipmentStoreSystemCountryCode("USA");
        salesErrorSalesTransactionHeaderEntity.setReceivingStatus("co5");
        salesErrorSalesTransactionHeaderEntity.setReceiptStoreCode("123456789E");
        salesErrorSalesTransactionHeaderEntity.setReceiptStoreSystemBrandCode("abc9");
        salesErrorSalesTransactionHeaderEntity.setReceiptStoreSystemBusinessCode("abd2");
        salesErrorSalesTransactionHeaderEntity.setReceiptStoreSystemCountryCode("USA");
        salesErrorSalesTransactionHeaderEntity.setCustomerId("32058119860710161412345612345a");
        salesErrorSalesTransactionHeaderEntity.setOrderNumberForStorePayment("a00000000000000001");
        salesErrorSalesTransactionHeaderEntity.setAdvanceReceivedStoreCode("chinaappl1");
        salesErrorSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBrandCode("A003");
        salesErrorSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBusinessCode("A004");
        salesErrorSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemCountryCode("USA");
        salesErrorSalesTransactionHeaderEntity.setOperatorCode("abcdefghi2");
        salesErrorSalesTransactionHeaderEntity
                .setOriginalTransactionId("320581198607101614abcdef123456");
        salesErrorSalesTransactionHeaderEntity.setOriginalCashRegisterNo(6);
        salesErrorSalesTransactionHeaderEntity.setOriginalReceiptNo("abcdefghi3");
        salesErrorSalesTransactionHeaderEntity
                .setDepositCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionHeaderEntity.setDepositValue(new BigDecimal(1));
        salesErrorSalesTransactionHeaderEntity
                .setChangeCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionHeaderEntity.setChangeValue(new BigDecimal(1));
        salesErrorSalesTransactionHeaderEntity.setReceiptNoForCreditCardCancellation("abcde123456");
        salesErrorSalesTransactionHeaderEntity
                .setReceiptNoForCreditCard("320581198607101614abcdef123457");
        salesErrorSalesTransactionHeaderEntity.setReceiptIssuedFlag(true);
        salesErrorSalesTransactionHeaderEntity.setEReceiptTargetFlag(true);
        salesErrorSalesTransactionHeaderEntity.setProcessingCompanyCode("320581198607101614ab");
        salesErrorSalesTransactionHeaderEntity.setEmployeeSaleFlag(true);
        salesErrorSalesTransactionHeaderEntity.setConsistencySalesFlag(true);
        salesErrorSalesTransactionHeaderEntity.setCorporateId("320581198607101614ab");
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionDiscountFlag(true);
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionDiscountAmountRateCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionHeaderEntity
                .setSalesTransactionDiscountAmountRate(new BigDecimal(1));
        salesErrorSalesTransactionHeaderEntity.setTokenCode("a00000000000000000000000000002");
        salesErrorSalesTransactionHeaderEntity.setCreateUserId("user02");
        salesErrorSalesTransactionHeaderEntity.setCreateDatetime(nowDateTime);
        salesErrorSalesTransactionHeaderEntity.setCreateProgramId("SLS0300102");
        salesErrorSalesTransactionHeaderEntity.setUpdateUserId("user02");
        salesErrorSalesTransactionHeaderEntity.setUpdateDatetime(nowDateTime);
        salesErrorSalesTransactionHeaderEntity.setUpdateProgramId("SLS0300102");

        return salesErrorSalesTransactionHeaderEntity;
    }

    public static SalesErrorSalesTransactionDetail makeTSalesErrorSalesTransactionDetail() {
        SalesErrorSalesTransactionDetail salesErrorSalesTransactionDetailEntity =
                new SalesErrorSalesTransactionDetail();

        salesErrorSalesTransactionDetailEntity.setOrderSubNumber(201);
        salesErrorSalesTransactionDetailEntity.setDetailSubNumber(301);
        salesErrorSalesTransactionDetailEntity.setSalesTransactionId("SalesTransactionId");
        salesErrorSalesTransactionDetailEntity.setTransactionId("TransactionId05");
        salesErrorSalesTransactionDetailEntity.setItemDetailSubNumber(9901);
        salesErrorSalesTransactionDetailEntity.setSalesTransactionType("c00001");
        salesErrorSalesTransactionDetailEntity.setSystemBrandCode("ab01");
        salesErrorSalesTransactionDetailEntity.setL2ItemCode("abcdefg000000000000000001");
        salesErrorSalesTransactionDetailEntity.setDisplayL2ItemCode("abcdefg000000000000000002");
        salesErrorSalesTransactionDetailEntity.setL2ProductName("a120304789adgdfgdfgdfg");
        salesErrorSalesTransactionDetailEntity.setL3ItemCode("abcdefg000000000000000003");
        salesErrorSalesTransactionDetailEntity.setL3PosProductName("a120304789adgdfgdfgdfdsfd");
        salesErrorSalesTransactionDetailEntity.setProductClassification("ITEM");
        salesErrorSalesTransactionDetailEntity.setNonMdType("NonMdType");
        salesErrorSalesTransactionDetailEntity.setNonMdCode("NonMdCode");
        salesErrorSalesTransactionDetailEntity.setServiceCode("ServiceCode");
        salesErrorSalesTransactionDetailEntity.setEpcCode("abcdefg000000000000000004");
        salesErrorSalesTransactionDetailEntity.setGDepartmentCode("c00002");
        salesErrorSalesTransactionDetailEntity.setMajorCategoryCode("1234");
        salesErrorSalesTransactionDetailEntity.setQuantityCode("A");
        salesErrorSalesTransactionDetailEntity.setDetailQuantity(new BigDecimal(99));
        salesErrorSalesTransactionDetailEntity
                .setItemCostCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDetailEntity.setItemCostValue(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDetailEntity.setInitialSellingPrice(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity
                .setBclassPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDetailEntity.setBclassPrice(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity
                .setNewPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setCalculationUnavailableType("T");
        salesErrorSalesTransactionDetailEntity.setOrderStatusUpdateDate("2018-02-27T09:27:45Z");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        salesErrorSalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(nowDateTime);
        salesErrorSalesTransactionDetailEntity.setOrderStatus("aa2");
        salesErrorSalesTransactionDetailEntity.setOrderSubstatus("aa3");
        salesErrorSalesTransactionDetailEntity.setBookingStoreCode("a234567801");
        salesErrorSalesTransactionDetailEntity.setBookingStoreSystemBrandCode("ab01");
        salesErrorSalesTransactionDetailEntity.setBookingStoreSystemBusinessCode("ab02");
        salesErrorSalesTransactionDetailEntity.setBookingStoreSystemCountryCode("USA");
        salesErrorSalesTransactionDetailEntity.setShipmentStoreCode("a234567802");
        salesErrorSalesTransactionDetailEntity.setShipmentStoreSystemBrandCode("ab03");
        salesErrorSalesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("ab04");
        salesErrorSalesTransactionDetailEntity.setShipmentStoreSystemCountryCode("USB");
        salesErrorSalesTransactionDetailEntity.setReceiptStoreCode("a234567803");
        salesErrorSalesTransactionDetailEntity.setReceiptStoreSystemBrandCode("ab05");
        salesErrorSalesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("ab06");
        salesErrorSalesTransactionDetailEntity.setReceiptStoreSystemCountryCode("USC");
        salesErrorSalesTransactionDetailEntity.setContributionSalesRepresentative("a234567804");
        salesErrorSalesTransactionDetailEntity.setCustomerId("a20581198607101614123456123456");
        salesErrorSalesTransactionDetailEntity
                .setBundlePurchaseApplicableQuantity(new BigDecimal(5672));
        salesErrorSalesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDetailEntity.setBundlePurchaseApplicablePrice(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setBundlePurchaseIndex(26);
        salesErrorSalesTransactionDetailEntity.setLimitedAmountPromotionCount(99);
        salesErrorSalesTransactionDetailEntity.setStoreItemDiscountType("QW");
        salesErrorSalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDetailEntity.setStoreItemDiscountSetting(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setStoreBundleSaleFlag(true);
        salesErrorSalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDetailEntity.setStoreBundleSalePrice(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setSetSalesDetailIndex(1234);
        salesErrorSalesTransactionDetailEntity.setTaxationType("a234567809");
        salesErrorSalesTransactionDetailEntity.setTaxSystemType("a234567808");
        salesErrorSalesTransactionDetailEntity.setCreateUserId("user03");
        salesErrorSalesTransactionDetailEntity.setCreateDatetime(nowDateTime);
        salesErrorSalesTransactionDetailEntity.setCreateProgramId("SLS0300103");
        salesErrorSalesTransactionDetailEntity.setUpdateUserId("user03");
        salesErrorSalesTransactionDetailEntity.setUpdateDatetime(nowDateTime);
        salesErrorSalesTransactionDetailEntity.setUpdateProgramId("SLS0300103");

        return salesErrorSalesTransactionDetailEntity;
    }

    public static SalesErrorSalesTransactionDetailInfo makeTSalesErrorSalesTransactionDetailInfo() {
        SalesErrorSalesTransactionDetailInfo salesErrorSalesTransactionDetailInfoEntity =
                new SalesErrorSalesTransactionDetailInfo();

        salesErrorSalesTransactionDetailInfoEntity.setOrderSubNumber(203);
        salesErrorSalesTransactionDetailInfoEntity.setSalesTransactionId("SalesTransactionId09");
        salesErrorSalesTransactionDetailInfoEntity.setDetailSubNumber(303);
        salesErrorSalesTransactionDetailInfoEntity.setTransactionId("TransactionId09");
        salesErrorSalesTransactionDetailInfoEntity.setItemDetailSubNumber(9901);
        salesErrorSalesTransactionDetailInfoEntity.setKeyCode("a1234567890123456789");
        salesErrorSalesTransactionDetailInfoEntity.setCodeValue1("a123456789012345678value1");
        salesErrorSalesTransactionDetailInfoEntity.setCodeValue2("a123456789012345678value2");
        salesErrorSalesTransactionDetailInfoEntity.setCodeValue3("a123456789012345678value3");
        salesErrorSalesTransactionDetailInfoEntity.setCodeValue4("a123456789012345678value4");
        salesErrorSalesTransactionDetailInfoEntity.setName1("1234567890name1");
        salesErrorSalesTransactionDetailInfoEntity.setName2("1234567890name2");
        salesErrorSalesTransactionDetailInfoEntity.setName3("1234567890name3");
        salesErrorSalesTransactionDetailInfoEntity.setName4("1234567890name4");
        salesErrorSalesTransactionDetailInfoEntity.setCreateUserId("user09");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 25);
        salesErrorSalesTransactionDetailInfoEntity.setCreateDatetime(nowDateTime);
        salesErrorSalesTransactionDetailInfoEntity.setCreateProgramId("SLS0300109");
        salesErrorSalesTransactionDetailInfoEntity.setUpdateUserId("user09");
        salesErrorSalesTransactionDetailInfoEntity.setUpdateDatetime(nowDateTime);
        salesErrorSalesTransactionDetailInfoEntity.setUpdateProgramId("SLS0300109");

        return salesErrorSalesTransactionDetailInfoEntity;
    }

    public static SalesErrorSalesTransactionDiscount makeTSalesErrorSalesTransactionDiscount() {
        SalesErrorSalesTransactionDiscount salesErrorSalesTransactionDiscountEntity =
                new SalesErrorSalesTransactionDiscount();

        salesErrorSalesTransactionDiscountEntity.setOrderSubNumber(402);
        salesErrorSalesTransactionDiscountEntity.setDetailSubNumber(403);
        salesErrorSalesTransactionDiscountEntity.setSalesTransactionId("SalesTransactionId11");
        salesErrorSalesTransactionDiscountEntity.setPromotionType("PromotionType11");
        salesErrorSalesTransactionDiscountEntity.setPromotionNo("5508");
        salesErrorSalesTransactionDiscountEntity.setStoreDiscountType("A");
        salesErrorSalesTransactionDiscountEntity.setTransactionId("TransactionId11");
        salesErrorSalesTransactionDiscountEntity.setItemDiscountSubNumber(9901);
        salesErrorSalesTransactionDiscountEntity.setQuantityCode("n");
        salesErrorSalesTransactionDiscountEntity.setDiscountQuantity(10);
        salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxExcluded(new BigDecimal(1));
        salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxIncluded(new BigDecimal(1));
        salesErrorSalesTransactionDiscountEntity.setCreateUserId("user06");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 26);
        salesErrorSalesTransactionDiscountEntity.setCreateDatetime(nowDateTime);
        salesErrorSalesTransactionDiscountEntity.setCreateProgramId("SLS0300106");
        salesErrorSalesTransactionDiscountEntity.setUpdateUserId("user06");
        salesErrorSalesTransactionDiscountEntity.setUpdateDatetime(nowDateTime);
        salesErrorSalesTransactionDiscountEntity.setUpdateProgramId("SLS0300106");

        return salesErrorSalesTransactionDiscountEntity;
    }

    public static SalesErrorSalesTransactionTax makeTSalesErrorSalesTransactionTax() {
        SalesErrorSalesTransactionTax tSalesErrorSalesTransactionTaxEntity =
                new SalesErrorSalesTransactionTax();

        tSalesErrorSalesTransactionTaxEntity.setOrderSubNumber(405);
        tSalesErrorSalesTransactionTaxEntity.setSalesTransactionId("SalesTransactionId");
        tSalesErrorSalesTransactionTaxEntity.setDetailSubNumber(406);
        tSalesErrorSalesTransactionTaxEntity.setTaxGroup("TaxGroup");
        tSalesErrorSalesTransactionTaxEntity.setTransactionId("TransactionId");
        tSalesErrorSalesTransactionTaxEntity.setTaxSubNumber(9901);
        tSalesErrorSalesTransactionTaxEntity.setTaxAmountSign("s");
        tSalesErrorSalesTransactionTaxEntity
                .setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tSalesErrorSalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        tSalesErrorSalesTransactionTaxEntity.setTaxRate(new BigDecimal(1221));
        tSalesErrorSalesTransactionTaxEntity.setTaxName("a100000002");
        tSalesErrorSalesTransactionTaxEntity.setCreateUserId("user07");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 27);
        tSalesErrorSalesTransactionTaxEntity.setCreateDatetime(nowDateTime);
        tSalesErrorSalesTransactionTaxEntity.setCreateProgramId("SLS0300107");
        tSalesErrorSalesTransactionTaxEntity.setUpdateUserId("user07");
        tSalesErrorSalesTransactionTaxEntity.setUpdateDatetime(nowDateTime);
        tSalesErrorSalesTransactionTaxEntity.setUpdateProgramId("SLS0300107");

        return tSalesErrorSalesTransactionTaxEntity;
    }

    public static SalesErrorSalesTransactionTender makeTSalesErrorSalesTransactionTende() {
        SalesErrorSalesTransactionTender tSalesErrorSalesTransactionTenderEntity =
                new SalesErrorSalesTransactionTender();

        tSalesErrorSalesTransactionTenderEntity.setOrderSubNumber(506);
        tSalesErrorSalesTransactionTenderEntity.setSalesTransactionId("SalesTransactionId19");
        tSalesErrorSalesTransactionTenderEntity.setTenderGroup("TenderGroup");
        tSalesErrorSalesTransactionTenderEntity.setTenderId("TenderId");
        tSalesErrorSalesTransactionTenderEntity.setTransactionId("TransactionId19");
        tSalesErrorSalesTransactionTenderEntity.setTenderSubNumber(301);
        tSalesErrorSalesTransactionTenderEntity.setPaymentSign("c");
        tSalesErrorSalesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tSalesErrorSalesTransactionTenderEntity.setTaxIncludedPaymentAmountValue(new BigDecimal(1));
        tSalesErrorSalesTransactionTenderEntity.setCreateUserId("user10");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 30);
        tSalesErrorSalesTransactionTenderEntity.setCreateDatetime(nowDateTime);
        tSalesErrorSalesTransactionTenderEntity.setCreateProgramId("SLS0300110");
        tSalesErrorSalesTransactionTenderEntity.setUpdateUserId("user10");
        tSalesErrorSalesTransactionTenderEntity.setUpdateDatetime(nowDateTime);
        tSalesErrorSalesTransactionTenderEntity.setUpdateProgramId("SLS0300110");

        return tSalesErrorSalesTransactionTenderEntity;
    }

    public static SalesErrorSalesTransactionTenderInfo makeTSalesErrorSalesTransactionTenderInfo() {
        SalesErrorSalesTransactionTenderInfo tSalesErrorSalesTransactionTenderInfoEntity =
                new SalesErrorSalesTransactionTenderInfo();

        tSalesErrorSalesTransactionTenderInfoEntity.setOrderSubNumber(602);
        tSalesErrorSalesTransactionTenderInfoEntity.setSalesTransactionId("SalesTransactionId21");
        tSalesErrorSalesTransactionTenderInfoEntity.setTenderGroup("TenderGroup");
        tSalesErrorSalesTransactionTenderInfoEntity.setTenderId("TenderId");
        tSalesErrorSalesTransactionTenderInfoEntity.setTransactionId("TransactionId21");
        tSalesErrorSalesTransactionTenderInfoEntity
                .setDiscountValueCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tSalesErrorSalesTransactionTenderInfoEntity.setDiscountValue(new BigDecimal(1));
        tSalesErrorSalesTransactionTenderInfoEntity.setDiscountRate(new BigDecimal(2018));
        tSalesErrorSalesTransactionTenderInfoEntity
                .setDiscountCodeIdCorporateId("a00000000000000000000000000001");
        tSalesErrorSalesTransactionTenderInfoEntity.setCouponType("a00001");
        tSalesErrorSalesTransactionTenderInfoEntity.setCouponDiscountAmountSettingCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tSalesErrorSalesTransactionTenderInfoEntity
                .setCouponDiscountAmountSettingValue(new BigDecimal(1));
        tSalesErrorSalesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tSalesErrorSalesTransactionTenderInfoEntity
                .setCouponMinUsageAmountThresholdValue(new BigDecimal(1));
        tSalesErrorSalesTransactionTenderInfoEntity
                .setCouponUserId("a00000000000000000000000000002");
        tSalesErrorSalesTransactionTenderInfoEntity.setCardNo("a00000000000000000000000000003");
        tSalesErrorSalesTransactionTenderInfoEntity
                .setCreditApprovalCode("a00000000000000000000000000004");
        tSalesErrorSalesTransactionTenderInfoEntity
                .setCreditProcessingSerialNumber("a00000000000000000000000000005");
        tSalesErrorSalesTransactionTenderInfoEntity
                .setCreditPaymentType("a00000000000000000000000000006");
        tSalesErrorSalesTransactionTenderInfoEntity.setCreditPaymentCount(24567);
        tSalesErrorSalesTransactionTenderInfoEntity
                .setCreditAffiliatedStoreNumber("a00000000000000000000000000007");
        tSalesErrorSalesTransactionTenderInfoEntity.setCreateUserId("user11");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 31);
        tSalesErrorSalesTransactionTenderInfoEntity.setCreateDatetime(nowDateTime);
        tSalesErrorSalesTransactionTenderInfoEntity.setCreateProgramId("SLS0300111");
        tSalesErrorSalesTransactionTenderInfoEntity.setUpdateUserId("user11");
        tSalesErrorSalesTransactionTenderInfoEntity.setUpdateDatetime(nowDateTime);
        tSalesErrorSalesTransactionTenderInfoEntity.setUpdateProgramId("SLS0300111");

        return tSalesErrorSalesTransactionTenderInfoEntity;
    }

    public static SalesErrorSalesTransactionTotalAmount makeTSalesErrorSalesTransactionTotalAmount() {
        SalesErrorSalesTransactionTotalAmount tSalesErrorSalesTransactionTotalAmountEntity =
                new SalesErrorSalesTransactionTotalAmount();

        tSalesErrorSalesTransactionTotalAmountEntity.setOrderSubNumber(903);
        tSalesErrorSalesTransactionTotalAmountEntity.setSalesTransactionId("SalesTransactionId25");
        tSalesErrorSalesTransactionTotalAmountEntity.setTotalType("TotalType");
        tSalesErrorSalesTransactionTotalAmountEntity.setTotalAmountSubNumber(301);
        tSalesErrorSalesTransactionTotalAmountEntity.setTransactionId("TransactionId25");
        tSalesErrorSalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tSalesErrorSalesTransactionTotalAmountEntity
                .setTotalAmountTaxExcludedValue(new BigDecimal(1));
        tSalesErrorSalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tSalesErrorSalesTransactionTotalAmountEntity
                .setTotalAmountTaxIncludedValue(new BigDecimal(1));
        tSalesErrorSalesTransactionTotalAmountEntity.setTaxRate(new BigDecimal(12));
        tSalesErrorSalesTransactionTotalAmountEntity
                .setSalesTransactionInformation1("z1234567890121");
        tSalesErrorSalesTransactionTotalAmountEntity
                .setSalesTransactionInformation2("z1234567890122");
        tSalesErrorSalesTransactionTotalAmountEntity
                .setSalesTransactionInformation3("z1234567890123");
        tSalesErrorSalesTransactionTotalAmountEntity.setCreateUserId("user013");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 33);
        tSalesErrorSalesTransactionTotalAmountEntity.setCreateDatetime(nowDateTime);
        tSalesErrorSalesTransactionTotalAmountEntity.setCreateProgramId("SLS0300113");
        tSalesErrorSalesTransactionTotalAmountEntity.setUpdateUserId("user13");
        tSalesErrorSalesTransactionTotalAmountEntity.setUpdateDatetime(nowDateTime);
        tSalesErrorSalesTransactionTotalAmountEntity.setUpdateProgramId("SLS0300113");

        return tSalesErrorSalesTransactionTotalAmountEntity;
    }

    public static AlterationHistoryOrderInformation makeTAlterationHistoryOrderInformationBefore() {
        AlterationHistoryOrderInformation tAlterationHistoryOrderInformationEntity =
                new AlterationHistoryOrderInformation();
        tAlterationHistoryOrderInformationEntity.setTransactionId("salesTransactionErrorId01");
        tAlterationHistoryOrderInformationEntity.setIntegratedOrderId("IntegratedOrderId01");
        tAlterationHistoryOrderInformationEntity.setHistoryType(0);
        tAlterationHistoryOrderInformationEntity.setSalesTransactionErrorId("TransactionId01");
        tAlterationHistoryOrderInformationEntity
                .setOrderBarcodeNumber("320581198607101614abcdefgh");
        tAlterationHistoryOrderInformationEntity.setStoreCode("1234567890");
        tAlterationHistoryOrderInformationEntity.setSystemBrandCode("1234");
        tAlterationHistoryOrderInformationEntity.setSystemBusinessCode("1234");
        tAlterationHistoryOrderInformationEntity.setSystemCountryCode("USA");
        tAlterationHistoryOrderInformationEntity.setChannelCode("A1");
        tAlterationHistoryOrderInformationEntity.setUpdateType("ERRORERROR");
        tAlterationHistoryOrderInformationEntity.setCustomerId("320581198607101614123456123456");
        tAlterationHistoryOrderInformationEntity.setOrderConfirmationBusinessDate("2017-12-12");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tAlterationHistoryOrderInformationEntity.setOrderConfirmationDateTime(nowDateTime);
        tAlterationHistoryOrderInformationEntity.setErrorCheckType(9);
        tAlterationHistoryOrderInformationEntity.setDataAlterationSalesLinkageType(9);
        tAlterationHistoryOrderInformationEntity.setDataAlterationBackboneLinkageType(9);
        tAlterationHistoryOrderInformationEntity.setDataAlterationEditingFlag(true);
        tAlterationHistoryOrderInformationEntity
                .setDataAlterationUserId("UUU000000000000000000000user01");
        tAlterationHistoryOrderInformationEntity.setCreateUserId("user01");
        tAlterationHistoryOrderInformationEntity.setCreateDatetime(nowDateTime);
        tAlterationHistoryOrderInformationEntity.setCreateProgramId("SLS0300101");
        tAlterationHistoryOrderInformationEntity.setUpdateUserId("user01");
        tAlterationHistoryOrderInformationEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistoryOrderInformationEntity.setUpdateProgramId("SLS0300101");

        return tAlterationHistoryOrderInformationEntity;
    }

    public static AlterationHistoryOrderInformation makeTAlterationHistoryOrderInformationAfter() {
        AlterationHistoryOrderInformation tAlterationHistoryOrderInformationEntity =
                new AlterationHistoryOrderInformation();
        tAlterationHistoryOrderInformationEntity.setTransactionId("salesTransactionErrorId02");
        tAlterationHistoryOrderInformationEntity
                .setIntegratedOrderId("320581198607101614abcdefghi");
        tAlterationHistoryOrderInformationEntity.setHistoryType(1);
        tAlterationHistoryOrderInformationEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId02");
        tAlterationHistoryOrderInformationEntity
                .setOrderBarcodeNumber("320581198607101614abcdefgh");
        tAlterationHistoryOrderInformationEntity.setStoreCode("1234567890");
        tAlterationHistoryOrderInformationEntity.setSystemBrandCode("1234");
        tAlterationHistoryOrderInformationEntity.setSystemBusinessCode("1234");
        tAlterationHistoryOrderInformationEntity.setSystemCountryCode("USA");
        tAlterationHistoryOrderInformationEntity.setChannelCode("A1");
        tAlterationHistoryOrderInformationEntity.setUpdateType("ERRORERROR");
        tAlterationHistoryOrderInformationEntity.setCustomerId("320581198607101614123456123456");
        tAlterationHistoryOrderInformationEntity.setOrderConfirmationBusinessDate("2017-12-12");
        LocalDateTime nowDateTime1 = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tAlterationHistoryOrderInformationEntity.setOrderConfirmationDateTime(nowDateTime1);
        tAlterationHistoryOrderInformationEntity.setErrorCheckType(9);
        tAlterationHistoryOrderInformationEntity.setDataAlterationSalesLinkageType(9);
        tAlterationHistoryOrderInformationEntity.setDataAlterationBackboneLinkageType(9);
        tAlterationHistoryOrderInformationEntity.setDataAlterationEditingFlag(true);
        tAlterationHistoryOrderInformationEntity
                .setDataAlterationUserId("UUU000000000000000000000user01");
        tAlterationHistoryOrderInformationEntity.setCreateUserId("user02");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 31);
        tAlterationHistoryOrderInformationEntity.setCreateDatetime(nowDateTime);
        tAlterationHistoryOrderInformationEntity.setCreateProgramId("SLS0300102");
        tAlterationHistoryOrderInformationEntity.setUpdateUserId("user02");
        tAlterationHistoryOrderInformationEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistoryOrderInformationEntity.setUpdateProgramId("SLS0300102");

        return tAlterationHistoryOrderInformationEntity;
    }

    public static AlterationHistorySalesTransactionHeader makeTAlterationHistorySalesTransactionHeaderBefore() {
        AlterationHistorySalesTransactionHeader tAlterationHistorySalesTransactionHeaderEntity =
                new AlterationHistorySalesTransactionHeader();
        tAlterationHistorySalesTransactionHeaderEntity
                .setTransactionId("orignialSalesTransactionErrorId03");
        tAlterationHistorySalesTransactionHeaderEntity.setOrderSubNumber(203);
        tAlterationHistorySalesTransactionHeaderEntity.setIntegratedOrderId("203");
        tAlterationHistorySalesTransactionHeaderEntity
                .setSalesTransactionId("SalesTransactionId03");
        tAlterationHistorySalesTransactionHeaderEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionHeaderEntity
                .setSalesTransactionErrorId("TransactionId03");
        tAlterationHistorySalesTransactionHeaderEntity
                .setIntegratedOrderId("320581198607101614abcdefghi");
        tAlterationHistorySalesTransactionHeaderEntity.setSalesTransactionSubNumber(1);
        tAlterationHistorySalesTransactionHeaderEntity.setStoreCode("a123456789");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 22);
        tAlterationHistorySalesTransactionHeaderEntity.setDataCreationDateTime(nowDateTime);
        tAlterationHistorySalesTransactionHeaderEntity.setDataCreationBusinessDate("2018-12-12");
        tAlterationHistorySalesTransactionHeaderEntity.setCashRegisterNo(5);
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptNo("a123456789");
        tAlterationHistorySalesTransactionHeaderEntity.setSalesLinkageType(3);
        tAlterationHistorySalesTransactionHeaderEntity.setSalesTransactionType("a00002");
        tAlterationHistorySalesTransactionHeaderEntity.setReturnType(4);
        tAlterationHistorySalesTransactionHeaderEntity.setSystemBrandCode("A001");
        tAlterationHistorySalesTransactionHeaderEntity.setSystemBusinessCode("A002");
        tAlterationHistorySalesTransactionHeaderEntity.setSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity.setChannelCode("a1");
        tAlterationHistorySalesTransactionHeaderEntity.setOrderStatus("co1");
        tAlterationHistorySalesTransactionHeaderEntity.setOrderSubstatus("co2");
        tAlterationHistorySalesTransactionHeaderEntity.setOrderStatusUpdateDate("2018-12-12");
        tAlterationHistorySalesTransactionHeaderEntity
                .setOrderStatusLastUpdateDateTime(nowDateTime);
        tAlterationHistorySalesTransactionHeaderEntity.setOrderCancelledDateTime(nowDateTime);
        tAlterationHistorySalesTransactionHeaderEntity.setBookingStoreCode("123456789C");
        tAlterationHistorySalesTransactionHeaderEntity.setBookingStoreSystemBrandCode("abc5");
        tAlterationHistorySalesTransactionHeaderEntity.setBookingStoreSystemBusinessCode("abc6");
        tAlterationHistorySalesTransactionHeaderEntity.setBookingStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity.setPaymentStatus("co3");
        tAlterationHistorySalesTransactionHeaderEntity.setPaymentStoreCode("b234567890");
        tAlterationHistorySalesTransactionHeaderEntity.setPaymentStoreSystemBrandCode("cas1");
        tAlterationHistorySalesTransactionHeaderEntity.setPaymentStoreSystemBusinessCode("cas2");
        tAlterationHistorySalesTransactionHeaderEntity.setPaymentStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity.setTransferOutStatus("co6");
        tAlterationHistorySalesTransactionHeaderEntity.setShipmentStatus("co4");
        tAlterationHistorySalesTransactionHeaderEntity.setShipmentStoreCode("123456789D");
        tAlterationHistorySalesTransactionHeaderEntity.setShipmentStoreSystemBrandCode("abc7");
        tAlterationHistorySalesTransactionHeaderEntity.setShipmentStoreSystemBusinessCode("abc8");
        tAlterationHistorySalesTransactionHeaderEntity.setShipmentStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity.setReceivingStatus("co5");
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptStoreCode("123456789E");
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptStoreSystemBrandCode("abc9");
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptStoreSystemBusinessCode("abd2");
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity
                .setCustomerId("32058119860710161412345612345a");
        tAlterationHistorySalesTransactionHeaderEntity
                .setOrderNumberForStorePayment("a00000000000000001");
        tAlterationHistorySalesTransactionHeaderEntity.setAdvanceReceivedStoreCode("chinaappl1");
        tAlterationHistorySalesTransactionHeaderEntity
                .setAdvanceReceivedStoreSystemBrandCode("A003");
        tAlterationHistorySalesTransactionHeaderEntity
                .setAdvanceReceivedStoreSystemBusinessCode("A004");
        tAlterationHistorySalesTransactionHeaderEntity
                .setAdvanceReceivedStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity.setOperatorCode("abcdefghi2");
        tAlterationHistorySalesTransactionHeaderEntity
                .setOriginalTransactionId("320581198607101614abcdef123456");
        tAlterationHistorySalesTransactionHeaderEntity.setOriginalCashRegisterNo(6);
        tAlterationHistorySalesTransactionHeaderEntity.setOriginalReceiptNo("abcdefghi3");
        tAlterationHistorySalesTransactionHeaderEntity
                .setDepositCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionHeaderEntity.setDepositValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionHeaderEntity
                .setChangeCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionHeaderEntity.setChangeValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionHeaderEntity
                .setReceiptNoForCreditCardCancellation("abcde123456");
        tAlterationHistorySalesTransactionHeaderEntity
                .setReceiptNoForCreditCard("320581198607101614abcdef123457");
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptIssuedFlag(true);
        tAlterationHistorySalesTransactionHeaderEntity.setEReceiptTargetFlag(true);
        tAlterationHistorySalesTransactionHeaderEntity
                .setProcessingCompanyCode("320581198607101614ab");
        tAlterationHistorySalesTransactionHeaderEntity.setEmployeeSaleFlag(true);
        tAlterationHistorySalesTransactionHeaderEntity.setConsistencySalesFlag(true);
        tAlterationHistorySalesTransactionHeaderEntity.setCorporateId("320581198607101614ab");
        tAlterationHistorySalesTransactionHeaderEntity.setSalesTransactionDiscountFlag(true);
        tAlterationHistorySalesTransactionHeaderEntity
                .setSalesTransactionDiscountAmountRateCurrencyCode(
                        Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionHeaderEntity
                .setSalesTransactionDiscountAmountRate(new BigDecimal(1));
        tAlterationHistorySalesTransactionHeaderEntity
                .setTokenCode("a00000000000000000000000000002");
        tAlterationHistorySalesTransactionHeaderEntity.setCreateUserId("user03");
        tAlterationHistorySalesTransactionHeaderEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionHeaderEntity.setCreateProgramId("SLS0300103");
        tAlterationHistorySalesTransactionHeaderEntity.setUpdateUserId("user03");
        tAlterationHistorySalesTransactionHeaderEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionHeaderEntity.setUpdateProgramId("SLS0300103");

        return tAlterationHistorySalesTransactionHeaderEntity;
    }

    public static AlterationHistorySalesTransactionHeader makeTAlterationHistorySalesTransactionHeaderAfter() {
        AlterationHistorySalesTransactionHeader tAlterationHistorySalesTransactionHeaderEntity =
                new AlterationHistorySalesTransactionHeader();

        tAlterationHistorySalesTransactionHeaderEntity
                .setTransactionId("salesTransactionErrorId04");
        tAlterationHistorySalesTransactionHeaderEntity
                .setIntegratedOrderId("320581198607101614abcdefghi");
        tAlterationHistorySalesTransactionHeaderEntity.setOrderSubNumber(2);
        tAlterationHistorySalesTransactionHeaderEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionHeaderEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId04");
        tAlterationHistorySalesTransactionHeaderEntity
                .setIntegratedOrderId("320581198607101614abcdefghi");
        tAlterationHistorySalesTransactionHeaderEntity.setSalesTransactionSubNumber(205);
        tAlterationHistorySalesTransactionHeaderEntity.setStoreCode("a123456789");
        LocalDateTime nowDateTime1 = LocalDateTime.of(2018, 02, 27, 9, 27, 45);
        tAlterationHistorySalesTransactionHeaderEntity.setDataCreationDateTime(nowDateTime1);
        tAlterationHistorySalesTransactionHeaderEntity.setDataCreationBusinessDate("2018-12-12");
        tAlterationHistorySalesTransactionHeaderEntity.setCashRegisterNo(5);
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptNo("a123456789");
        tAlterationHistorySalesTransactionHeaderEntity.setSalesLinkageType(3);
        tAlterationHistorySalesTransactionHeaderEntity.setSalesTransactionType("a00002");
        tAlterationHistorySalesTransactionHeaderEntity.setReturnType(4);
        tAlterationHistorySalesTransactionHeaderEntity.setSystemBrandCode("A001");
        tAlterationHistorySalesTransactionHeaderEntity.setSystemBusinessCode("A002");
        tAlterationHistorySalesTransactionHeaderEntity.setSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity.setChannelCode("a1");
        tAlterationHistorySalesTransactionHeaderEntity.setOrderStatus("co1");
        tAlterationHistorySalesTransactionHeaderEntity.setOrderSubstatus("co2");
        tAlterationHistorySalesTransactionHeaderEntity.setOrderStatusUpdateDate("2018-12-12");
        tAlterationHistorySalesTransactionHeaderEntity
                .setOrderStatusLastUpdateDateTime(nowDateTime1);
        tAlterationHistorySalesTransactionHeaderEntity.setOrderCancelledDateTime(nowDateTime1);
        tAlterationHistorySalesTransactionHeaderEntity.setBookingStoreCode("123456789C");
        tAlterationHistorySalesTransactionHeaderEntity.setBookingStoreSystemBrandCode("abc5");
        tAlterationHistorySalesTransactionHeaderEntity.setBookingStoreSystemBusinessCode("abc6");
        tAlterationHistorySalesTransactionHeaderEntity.setBookingStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity.setPaymentStatus("co3");
        tAlterationHistorySalesTransactionHeaderEntity.setPaymentStoreCode("b234567890");
        tAlterationHistorySalesTransactionHeaderEntity.setPaymentStoreSystemBrandCode("cas1");
        tAlterationHistorySalesTransactionHeaderEntity.setPaymentStoreSystemBusinessCode("cas2");
        tAlterationHistorySalesTransactionHeaderEntity.setPaymentStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity.setTransferOutStatus("co6");
        tAlterationHistorySalesTransactionHeaderEntity.setShipmentStatus("co4");
        tAlterationHistorySalesTransactionHeaderEntity.setShipmentStoreCode("123456789D");
        tAlterationHistorySalesTransactionHeaderEntity.setShipmentStoreSystemBrandCode("abc7");
        tAlterationHistorySalesTransactionHeaderEntity.setShipmentStoreSystemBusinessCode("abc8");
        tAlterationHistorySalesTransactionHeaderEntity.setShipmentStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity.setReceivingStatus("co5");
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptStoreCode("123456789E");
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptStoreSystemBrandCode("abc9");
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptStoreSystemBusinessCode("abd2");
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity
                .setSalesTransactionId("a00000000000000000000000000001");
        tAlterationHistorySalesTransactionHeaderEntity
                .setCustomerId("32058119860710161412345612345a");
        tAlterationHistorySalesTransactionHeaderEntity
                .setOrderNumberForStorePayment("a00000000000000001");
        tAlterationHistorySalesTransactionHeaderEntity.setAdvanceReceivedStoreCode("chinaappl1");
        tAlterationHistorySalesTransactionHeaderEntity
                .setAdvanceReceivedStoreSystemBrandCode("A003");
        tAlterationHistorySalesTransactionHeaderEntity
                .setAdvanceReceivedStoreSystemBusinessCode("A004");
        tAlterationHistorySalesTransactionHeaderEntity
                .setAdvanceReceivedStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionHeaderEntity.setOperatorCode("abcdefghi2");
        tAlterationHistorySalesTransactionHeaderEntity
                .setOriginalTransactionId("320581198607101614abcdef123456");
        tAlterationHistorySalesTransactionHeaderEntity.setOriginalCashRegisterNo(6);
        tAlterationHistorySalesTransactionHeaderEntity.setOriginalReceiptNo("abcdefghi3");
        tAlterationHistorySalesTransactionHeaderEntity
                .setDepositCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionHeaderEntity.setDepositValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionHeaderEntity
                .setChangeCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionHeaderEntity.setChangeValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionHeaderEntity
                .setReceiptNoForCreditCardCancellation("abcde123456");
        tAlterationHistorySalesTransactionHeaderEntity
                .setReceiptNoForCreditCard("320581198607101614abcdef123457");
        tAlterationHistorySalesTransactionHeaderEntity.setReceiptIssuedFlag(true);
        tAlterationHistorySalesTransactionHeaderEntity.setEReceiptTargetFlag(true);
        tAlterationHistorySalesTransactionHeaderEntity
                .setProcessingCompanyCode("320581198607101614ab");
        tAlterationHistorySalesTransactionHeaderEntity.setEmployeeSaleFlag(true);
        tAlterationHistorySalesTransactionHeaderEntity.setConsistencySalesFlag(true);
        tAlterationHistorySalesTransactionHeaderEntity.setCorporateId("320581198607101614ab");
        tAlterationHistorySalesTransactionHeaderEntity.setSalesTransactionDiscountFlag(true);
        tAlterationHistorySalesTransactionHeaderEntity
                .setSalesTransactionDiscountAmountRateCurrencyCode(
                        Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionHeaderEntity
                .setSalesTransactionDiscountAmountRate(new BigDecimal(1));
        tAlterationHistorySalesTransactionHeaderEntity
                .setTokenCode("a00000000000000000000000000002");
        tAlterationHistorySalesTransactionHeaderEntity.setCreateUserId("user04");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tAlterationHistorySalesTransactionHeaderEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionHeaderEntity.setCreateProgramId("SLS0300104");
        tAlterationHistorySalesTransactionHeaderEntity.setUpdateUserId("user04");
        tAlterationHistorySalesTransactionHeaderEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionHeaderEntity.setUpdateProgramId("SLS0300104");

        return tAlterationHistorySalesTransactionHeaderEntity;
    }

    public static AlterationHistorySalesTransactionDetail makeTAlterationHistorySalesTransactionDetailBefore() {
        AlterationHistorySalesTransactionDetail tAlterationHistorySalesTransactionDetailEntity =
                new AlterationHistorySalesTransactionDetail();
        tAlterationHistorySalesTransactionDetailEntity
                .setTransactionId("originalSalesTransactionErrorId05");
        tAlterationHistorySalesTransactionDetailEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionDetailEntity.setSalesTransactionId("SalesTransactionId");
        tAlterationHistorySalesTransactionDetailEntity.setDetailSubNumber(301);
        tAlterationHistorySalesTransactionDetailEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionDetailEntity
                .setSalesTransactionErrorId("TransactionId05");
        tAlterationHistorySalesTransactionDetailEntity.setItemDetailSubNumber(9901);
        tAlterationHistorySalesTransactionDetailEntity.setSalesTransactionType("c00001");
        tAlterationHistorySalesTransactionDetailEntity.setSystemBrandCode("ab01");
        tAlterationHistorySalesTransactionDetailEntity.setL2ItemCode("abcdefg000000000000000001");
        tAlterationHistorySalesTransactionDetailEntity
                .setDisplayL2ItemCode("abcdefg000000000000000002");
        tAlterationHistorySalesTransactionDetailEntity.setL2ProductName("a120304789adgdfgdfgdfg");
        tAlterationHistorySalesTransactionDetailEntity.setL3ItemCode("abcdefg000000000000000003");
        tAlterationHistorySalesTransactionDetailEntity
                .setL3PosProductName("a120304789adgdfgdfgdfdsfd");
        tAlterationHistorySalesTransactionDetailEntity.setProductClassification("ITEM");
        tAlterationHistorySalesTransactionDetailEntity.setNonMdType("NonMdType");
        tAlterationHistorySalesTransactionDetailEntity.setNonMdCode("NonMdCode");
        tAlterationHistorySalesTransactionDetailEntity.setServiceCode("ServiceCode");
        tAlterationHistorySalesTransactionDetailEntity.setEpcCode("abcdefg000000000000000004");
        tAlterationHistorySalesTransactionDetailEntity.setGDepartmentCode("c00002");
        tAlterationHistorySalesTransactionDetailEntity.setMajorCategoryCode("1234");
        tAlterationHistorySalesTransactionDetailEntity.setQuantityCode("A");
        tAlterationHistorySalesTransactionDetailEntity.setDetailQuantity(new BigDecimal(99));
        tAlterationHistorySalesTransactionDetailEntity
                .setItemCostCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setItemCostValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setInitialSellingPrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity
                .setBclassPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setBclassPrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity
                .setNewPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setCalculationUnavailableType("T");
        tAlterationHistorySalesTransactionDetailEntity
                .setOrderStatusUpdateDate("2018-02-27T09:27:45Z");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        tAlterationHistorySalesTransactionDetailEntity
                .setOrderStatusLastUpdateDateTime(nowDateTime);
        tAlterationHistorySalesTransactionDetailEntity.setOrderStatus("aa2");
        tAlterationHistorySalesTransactionDetailEntity.setOrderSubstatus("aa3");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreCode("a234567801");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemBrandCode("ab01");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemBusinessCode("ab02");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreCode("a234567802");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemBrandCode("ab03");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("ab04");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemCountryCode("USB");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreCode("a234567803");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemBrandCode("ab05");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("ab06");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemCountryCode("USC");
        tAlterationHistorySalesTransactionDetailEntity
                .setContributionSalesRepresentative("a234567804");
        tAlterationHistorySalesTransactionDetailEntity
                .setCustomerId("a20581198607101614123456123456");
        tAlterationHistorySalesTransactionDetailEntity
                .setBundlePurchaseApplicableQuantity(new BigDecimal(5672));
        tAlterationHistorySalesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setBundlePurchaseApplicablePrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setBundlePurchaseIndex(26);
        tAlterationHistorySalesTransactionDetailEntity.setLimitedAmountPromotionCount(99);
        tAlterationHistorySalesTransactionDetailEntity.setStoreItemDiscountType("QW");
        tAlterationHistorySalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setStoreItemDiscountSetting(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSaleFlag(true);
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSalePrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setSetSalesDetailIndex(1234);
        tAlterationHistorySalesTransactionDetailEntity.setTaxationType("a234567809");
        tAlterationHistorySalesTransactionDetailEntity.setTaxSystemType("a234567808");
        tAlterationHistorySalesTransactionDetailEntity.setCreateUserId("user05");
        tAlterationHistorySalesTransactionDetailEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailEntity.setCreateProgramId("SLS0300105");
        tAlterationHistorySalesTransactionDetailEntity.setUpdateUserId("user05");
        tAlterationHistorySalesTransactionDetailEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailEntity.setUpdateProgramId("SLS0300105");

        return tAlterationHistorySalesTransactionDetailEntity;
    }

    public static AlterationHistorySalesTransactionDetail makeTAlterationHistorySalesTransactionDetailAfter() {
        AlterationHistorySalesTransactionDetail tAlterationHistorySalesTransactionDetailEntity =
                new AlterationHistorySalesTransactionDetail();
        tAlterationHistorySalesTransactionDetailEntity
                .setTransactionId("salesTransactionErrorId06");
        tAlterationHistorySalesTransactionDetailEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionDetailEntity
                .setSalesTransactionId("salesTransactionId06");
        tAlterationHistorySalesTransactionDetailEntity.setDetailSubNumber(301);
        tAlterationHistorySalesTransactionDetailEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionDetailEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId06");
        tAlterationHistorySalesTransactionDetailEntity.setItemDetailSubNumber(9901);
        tAlterationHistorySalesTransactionDetailEntity.setSalesTransactionType("c00001");
        tAlterationHistorySalesTransactionDetailEntity.setSystemBrandCode("ab01");
        tAlterationHistorySalesTransactionDetailEntity.setL2ItemCode("abcdefg000000000000000001");
        tAlterationHistorySalesTransactionDetailEntity
                .setDisplayL2ItemCode("abcdefg000000000000000002");
        tAlterationHistorySalesTransactionDetailEntity.setL2ProductName("a120304789adgdfgdfgdfg");
        tAlterationHistorySalesTransactionDetailEntity.setL3ItemCode("abcdefg000000000000000003");
        tAlterationHistorySalesTransactionDetailEntity
                .setL3PosProductName("a120304789adgdfgdfgdfdsfd");
        tAlterationHistorySalesTransactionDetailEntity.setProductClassification("ITEM");
        tAlterationHistorySalesTransactionDetailEntity.setNonMdType(null);
        tAlterationHistorySalesTransactionDetailEntity.setNonMdCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setServiceCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setEpcCode("abcdefg000000000000000004");
        tAlterationHistorySalesTransactionDetailEntity.setGDepartmentCode("c00002");
        tAlterationHistorySalesTransactionDetailEntity.setMajorCategoryCode("1234");
        tAlterationHistorySalesTransactionDetailEntity.setQuantityCode("A");
        tAlterationHistorySalesTransactionDetailEntity.setDetailQuantity(new BigDecimal(99));
        tAlterationHistorySalesTransactionDetailEntity
                .setItemCostCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setItemCostValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setInitialSellingPrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity
                .setBclassPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setBclassPrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity
                .setNewPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setCalculationUnavailableType("T");
        tAlterationHistorySalesTransactionDetailEntity
                .setOrderStatusUpdateDate("2018-02-27T09:27:45Z");
        LocalDateTime nowDateTime1 = LocalDateTime.of(2018, 02, 27, 9, 27, 45);
        tAlterationHistorySalesTransactionDetailEntity
                .setOrderStatusLastUpdateDateTime(nowDateTime1);
        tAlterationHistorySalesTransactionDetailEntity.setOrderStatus("aa2");
        tAlterationHistorySalesTransactionDetailEntity.setOrderSubstatus("aa3");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreCode("a234567801");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemBrandCode("ab01");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemBusinessCode("ab02");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreCode("a234567802");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemBrandCode("ab03");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("ab04");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemCountryCode("USB");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreCode("a234567803");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemBrandCode("ab05");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("ab06");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemCountryCode("USC");
        tAlterationHistorySalesTransactionDetailEntity
                .setContributionSalesRepresentative("a234567804");
        tAlterationHistorySalesTransactionDetailEntity
                .setCustomerId("a20581198607101614123456123456");
        tAlterationHistorySalesTransactionDetailEntity
                .setBundlePurchaseApplicableQuantity(new BigDecimal(5672));
        tAlterationHistorySalesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setBundlePurchaseApplicablePrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setBundlePurchaseIndex(26);
        tAlterationHistorySalesTransactionDetailEntity.setLimitedAmountPromotionCount(99);
        tAlterationHistorySalesTransactionDetailEntity.setStoreItemDiscountType("QW");
        tAlterationHistorySalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setStoreItemDiscountSetting(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSaleFlag(false);
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSalePrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setSetSalesDetailIndex(1234);
        tAlterationHistorySalesTransactionDetailEntity.setTaxationType("a234567809");
        tAlterationHistorySalesTransactionDetailEntity.setTaxSystemType("a234567808");
        tAlterationHistorySalesTransactionDetailEntity.setCreateUserId("user06");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 33);
        tAlterationHistorySalesTransactionDetailEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailEntity.setCreateProgramId("SLS0300106");
        tAlterationHistorySalesTransactionDetailEntity.setUpdateUserId("user06");
        tAlterationHistorySalesTransactionDetailEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailEntity.setUpdateProgramId("SLS0300106");
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSaleFlag(true);

        return tAlterationHistorySalesTransactionDetailEntity;
    }

    public static AlterationHistorySalesTransactionDetail makeTSalesErrorSalesTransactionDetailForInsertOutsideBefore() {
        AlterationHistorySalesTransactionDetail tAlterationHistorySalesTransactionDetailEntity =
                new AlterationHistorySalesTransactionDetail();
        tAlterationHistorySalesTransactionDetailEntity
                .setTransactionId("originalSalesTransactionErrorId07");
        tAlterationHistorySalesTransactionDetailEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionDetailEntity.setSalesTransactionId("SalesTransactionId");
        tAlterationHistorySalesTransactionDetailEntity.setDetailSubNumber(301);
        tAlterationHistorySalesTransactionDetailEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionDetailEntity
                .setSalesTransactionErrorId("TransactionId05");
        tAlterationHistorySalesTransactionDetailEntity.setItemDetailSubNumber(9901);
        tAlterationHistorySalesTransactionDetailEntity.setSalesTransactionType("c00001");
        tAlterationHistorySalesTransactionDetailEntity.setSystemBrandCode("ab01");
        tAlterationHistorySalesTransactionDetailEntity.setL2ItemCode("abcdefg000000000000000001");
        tAlterationHistorySalesTransactionDetailEntity
                .setDisplayL2ItemCode("abcdefg000000000000000002");
        tAlterationHistorySalesTransactionDetailEntity.setL2ProductName("a120304789adgdfgdfgdfg");
        tAlterationHistorySalesTransactionDetailEntity.setL3ItemCode("abcdefg000000000000000003");
        tAlterationHistorySalesTransactionDetailEntity
                .setL3PosProductName("a120304789adgdfgdfgdfdsfd");
        tAlterationHistorySalesTransactionDetailEntity.setProductClassification("ITEM");
        tAlterationHistorySalesTransactionDetailEntity.setNonMdType("NonMdType");
        tAlterationHistorySalesTransactionDetailEntity.setNonMdCode("NonMdCode");
        tAlterationHistorySalesTransactionDetailEntity.setServiceCode("ServiceCode");
        tAlterationHistorySalesTransactionDetailEntity.setEpcCode("abcdefg000000000000000004");
        tAlterationHistorySalesTransactionDetailEntity.setGDepartmentCode("c00002");
        tAlterationHistorySalesTransactionDetailEntity.setMajorCategoryCode("1234");
        tAlterationHistorySalesTransactionDetailEntity.setQuantityCode("A");
        tAlterationHistorySalesTransactionDetailEntity.setDetailQuantity(new BigDecimal(99));
        tAlterationHistorySalesTransactionDetailEntity
                .setItemCostCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setItemCostValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setInitialSellingPrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity
                .setBclassPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setBclassPrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity
                .setNewPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setCalculationUnavailableType("T");
        tAlterationHistorySalesTransactionDetailEntity
                .setOrderStatusUpdateDate("2018-02-27T09:27:45Z");
        LocalDateTime nowDateTime1 = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        tAlterationHistorySalesTransactionDetailEntity
                .setOrderStatusLastUpdateDateTime(nowDateTime1);
        tAlterationHistorySalesTransactionDetailEntity.setOrderStatus("aa2");
        tAlterationHistorySalesTransactionDetailEntity.setOrderSubstatus("aa3");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreCode("a234567801");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemBrandCode("ab01");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemBusinessCode("ab02");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreCode("a234567802");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemBrandCode("ab03");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("ab04");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemCountryCode("USB");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreCode("a234567803");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemBrandCode("ab05");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("ab06");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemCountryCode("USC");
        tAlterationHistorySalesTransactionDetailEntity
                .setContributionSalesRepresentative("a234567804");
        tAlterationHistorySalesTransactionDetailEntity
                .setCustomerId("a20581198607101614123456123456");
        tAlterationHistorySalesTransactionDetailEntity
                .setBundlePurchaseApplicableQuantity(new BigDecimal(5672));
        tAlterationHistorySalesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setBundlePurchaseApplicablePrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setBundlePurchaseIndex(26);
        tAlterationHistorySalesTransactionDetailEntity.setLimitedAmountPromotionCount(99);
        tAlterationHistorySalesTransactionDetailEntity.setStoreItemDiscountType("QW");
        tAlterationHistorySalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setStoreItemDiscountSetting(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSaleFlag(true);
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSalePrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setSetSalesDetailIndex(1234);
        tAlterationHistorySalesTransactionDetailEntity.setTaxationType("a234567809");
        tAlterationHistorySalesTransactionDetailEntity.setTaxSystemType("a234567808");
        tAlterationHistorySalesTransactionDetailEntity.setCreateUserId("user07");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 24);
        tAlterationHistorySalesTransactionDetailEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailEntity.setCreateProgramId("SLS0300107");
        tAlterationHistorySalesTransactionDetailEntity.setUpdateUserId("user07");
        tAlterationHistorySalesTransactionDetailEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailEntity.setUpdateProgramId("SLS0300107");

        return tAlterationHistorySalesTransactionDetailEntity;
    }

    public static AlterationHistorySalesTransactionDetail makeTSalesErrorSalesTransactionDetailForInsertOutsideAfter() {
        AlterationHistorySalesTransactionDetail tAlterationHistorySalesTransactionDetailEntity =
                new AlterationHistorySalesTransactionDetail();
        tAlterationHistorySalesTransactionDetailEntity
                .setTransactionId("salesTransactionErrorId08");
        tAlterationHistorySalesTransactionDetailEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionDetailEntity
                .setSalesTransactionId("salesTransactionId08");
        tAlterationHistorySalesTransactionDetailEntity.setDetailSubNumber(301);
        tAlterationHistorySalesTransactionDetailEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionDetailEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId08");
        tAlterationHistorySalesTransactionDetailEntity.setItemDetailSubNumber(9901);
        tAlterationHistorySalesTransactionDetailEntity
                .setSalesTransactionType("salesTransactionType08");
        tAlterationHistorySalesTransactionDetailEntity.setSystemBrandCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setL2ItemCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setDisplayL2ItemCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setL2ProductName(null);
        tAlterationHistorySalesTransactionDetailEntity.setL3ItemCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setL3PosProductName("abcde2222205865656");
        tAlterationHistorySalesTransactionDetailEntity.setProductClassification("NMITEM");
        tAlterationHistorySalesTransactionDetailEntity.setNonMdType("c00002");
        tAlterationHistorySalesTransactionDetailEntity.setNonMdCode("abcdc00002abcdc00002a0001");
        tAlterationHistorySalesTransactionDetailEntity.setServiceCode("a0001");
        tAlterationHistorySalesTransactionDetailEntity.setEpcCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setGDepartmentCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setMajorCategoryCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setQuantityCode("A");
        tAlterationHistorySalesTransactionDetailEntity.setDetailQuantity(new BigDecimal(3333));
        tAlterationHistorySalesTransactionDetailEntity.setItemCostCurrencyCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setItemCostValue(null);
        tAlterationHistorySalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setInitialSellingPrice(null);
        tAlterationHistorySalesTransactionDetailEntity.setBclassPriceCurrencyCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setBclassPrice(null);
        tAlterationHistorySalesTransactionDetailEntity
                .setNewPriceCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity
                .setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDetailEntity.setCalculationUnavailableType("d000000001");
        tAlterationHistorySalesTransactionDetailEntity.setOrderStatusUpdateDate("2018-12-12");
        LocalDateTime nowDateTime1 = LocalDateTime.of(2018, 02, 27, 9, 27, 45);
        tAlterationHistorySalesTransactionDetailEntity
                .setOrderStatusLastUpdateDateTime(nowDateTime1);
        tAlterationHistorySalesTransactionDetailEntity.setOrderStatus("co1");
        tAlterationHistorySalesTransactionDetailEntity.setOrderSubstatus("co2");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreCode("b000000001");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemBrandCode("s001");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemBusinessCode("s002");
        tAlterationHistorySalesTransactionDetailEntity.setBookingStoreSystemCountryCode("USA");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreCode("b000000002");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemBrandCode("s003");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("s004");
        tAlterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemCountryCode("USB");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreCode("b000000003");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemBrandCode("s005");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("s006");
        tAlterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemCountryCode("USC");
        tAlterationHistorySalesTransactionDetailEntity
                .setContributionSalesRepresentative("b000000004");
        tAlterationHistorySalesTransactionDetailEntity.setCustomerId(null);
        tAlterationHistorySalesTransactionDetailEntity.setBundlePurchaseApplicableQuantity(null);
        tAlterationHistorySalesTransactionDetailEntity
                .setBundlePurchaseApplicablePriceCurrencyCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setBundlePurchaseApplicablePrice(null);
        tAlterationHistorySalesTransactionDetailEntity.setBundlePurchaseIndex(null);
        tAlterationHistorySalesTransactionDetailEntity.setLimitedAmountPromotionCount(null);
        tAlterationHistorySalesTransactionDetailEntity.setStoreItemDiscountType(null);
        tAlterationHistorySalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setStoreItemDiscountSetting(null);
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSaleFlag(false);
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode(null);
        tAlterationHistorySalesTransactionDetailEntity.setStoreBundleSalePrice(null);
        tAlterationHistorySalesTransactionDetailEntity.setSetSalesDetailIndex(null);
        tAlterationHistorySalesTransactionDetailEntity.setTaxationType("b000000005");
        tAlterationHistorySalesTransactionDetailEntity.setTaxSystemType("b000000006");
        tAlterationHistorySalesTransactionDetailEntity.setCreateUserId("user08");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 34);
        tAlterationHistorySalesTransactionDetailEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailEntity.setCreateProgramId("SLS0300108");
        tAlterationHistorySalesTransactionDetailEntity.setUpdateUserId("user08");
        tAlterationHistorySalesTransactionDetailEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailEntity.setUpdateProgramId("SLS0300108");

        return tAlterationHistorySalesTransactionDetailEntity;
    }

    public static AlterationHistorySalesTransactionDetailInfo makeTAlterationHistorySalesTransactionDetailInfoBefore() {
        AlterationHistorySalesTransactionDetailInfo tAlterationHistorySalesTransactionDetailInfoEntity =
                new AlterationHistorySalesTransactionDetailInfo();
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setTransactionId("orignialSalesTransactionErrorId09");
        tAlterationHistorySalesTransactionDetailInfoEntity.setOrderSubNumber(203);
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setSalesTransactionId("SalesTransactionId09");
        tAlterationHistorySalesTransactionDetailInfoEntity.setDetailSubNumber(303);
        tAlterationHistorySalesTransactionDetailInfoEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setSalesTransactionErrorId("TransactionId09");
        tAlterationHistorySalesTransactionDetailInfoEntity.setItemDetailSubNumber(9901);
        tAlterationHistorySalesTransactionDetailInfoEntity.setKeyCode("a1234567890123456789");
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setCodeValue1("a123456789012345678value1");
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setCodeValue2("a123456789012345678value2");
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setCodeValue3("a123456789012345678value3");
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setCodeValue4("a123456789012345678value4");
        tAlterationHistorySalesTransactionDetailInfoEntity.setName1("1234567890name1");
        tAlterationHistorySalesTransactionDetailInfoEntity.setName2("1234567890name2");
        tAlterationHistorySalesTransactionDetailInfoEntity.setName3("1234567890name3");
        tAlterationHistorySalesTransactionDetailInfoEntity.setName4("1234567890name4");
        tAlterationHistorySalesTransactionDetailInfoEntity.setCreateUserId("user09");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 25);
        tAlterationHistorySalesTransactionDetailInfoEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailInfoEntity.setCreateProgramId("SLS0300109");
        tAlterationHistorySalesTransactionDetailInfoEntity.setUpdateUserId("user09");
        tAlterationHistorySalesTransactionDetailInfoEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailInfoEntity.setUpdateProgramId("SLS0300109");

        return tAlterationHistorySalesTransactionDetailInfoEntity;
    }

    public static AlterationHistorySalesTransactionDetailInfo makeTAlterationHistorySalesTransactionDetailInfoAfter() {
        AlterationHistorySalesTransactionDetailInfo tAlterationHistorySalesTransactionDetailInfoEntity =
                new AlterationHistorySalesTransactionDetailInfo();
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setTransactionId("salesTransactionErrorId10");
        tAlterationHistorySalesTransactionDetailInfoEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setSalesTransactionId("salesTransactionId10");
        tAlterationHistorySalesTransactionDetailInfoEntity.setDetailSubNumber(301);
        tAlterationHistorySalesTransactionDetailInfoEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId10");
        tAlterationHistorySalesTransactionDetailInfoEntity.setItemDetailSubNumber(9901);
        tAlterationHistorySalesTransactionDetailInfoEntity.setKeyCode("a1234567890123456789");
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setCodeValue1("a123456789012345678value1");
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setCodeValue2("a123456789012345678value2");
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setCodeValue3("a123456789012345678value3");
        tAlterationHistorySalesTransactionDetailInfoEntity
                .setCodeValue4("a123456789012345678value4");
        tAlterationHistorySalesTransactionDetailInfoEntity.setName1("1234567890name1");
        tAlterationHistorySalesTransactionDetailInfoEntity.setName2("1234567890name2");
        tAlterationHistorySalesTransactionDetailInfoEntity.setName3("1234567890name3");
        tAlterationHistorySalesTransactionDetailInfoEntity.setName4("1234567890name4");
        tAlterationHistorySalesTransactionDetailInfoEntity.setCreateUserId("user10");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 35);
        tAlterationHistorySalesTransactionDetailInfoEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailInfoEntity.setCreateProgramId("SLS0300110");
        tAlterationHistorySalesTransactionDetailInfoEntity.setUpdateUserId("user10");
        tAlterationHistorySalesTransactionDetailInfoEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDetailInfoEntity.setUpdateProgramId("SLS0300110");

        return tAlterationHistorySalesTransactionDetailInfoEntity;
    }

    public static AlterationHistorySalesTransactionDiscount makeTAlterationHistorySalesTransactionDiscountForInsertNonBefore() {
        AlterationHistorySalesTransactionDiscount tAlterationHistorySalesTransactionDiscountEntity =
                new AlterationHistorySalesTransactionDiscount();
        tAlterationHistorySalesTransactionDiscountEntity
                .setTransactionId("originalSalesTransactionErrorId11");
        tAlterationHistorySalesTransactionDiscountEntity.setOrderSubNumber(402);
        tAlterationHistorySalesTransactionDiscountEntity
                .setSalesTransactionId("SalesTransactionId11");
        tAlterationHistorySalesTransactionDiscountEntity.setDetailSubNumber(403);
        tAlterationHistorySalesTransactionDiscountEntity.setPromotionType("PromotionType11");
        tAlterationHistorySalesTransactionDiscountEntity.setPromotionNo("5508");
        tAlterationHistorySalesTransactionDiscountEntity.setStoreDiscountType("A");
        tAlterationHistorySalesTransactionDiscountEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionDiscountEntity
                .setSalesTransactionErrorId("TransactionId11");
        tAlterationHistorySalesTransactionDiscountEntity.setItemDiscountSubNumber(9901);
        tAlterationHistorySalesTransactionDiscountEntity.setQuantityCode("n");
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountQuantity(10);
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDiscountEntity
                .setDiscountAmountTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDiscountEntity
                .setDiscountAmountTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDiscountEntity.setCreateUserId("user11");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 26);
        tAlterationHistorySalesTransactionDiscountEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDiscountEntity.setCreateProgramId("SLS0300111");
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateUserId("user11");
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateProgramId("SLS0300111");

        return tAlterationHistorySalesTransactionDiscountEntity;
    }

    public static AlterationHistorySalesTransactionDiscount makeTAlterationHistorySalesTransactionDiscountForInsertNonAfter() {
        AlterationHistorySalesTransactionDiscount tAlterationHistorySalesTransactionDiscountEntity =
                new AlterationHistorySalesTransactionDiscount();
        tAlterationHistorySalesTransactionDiscountEntity
                .setTransactionId("salesTransactionErrorId12");
        tAlterationHistorySalesTransactionDiscountEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionDiscountEntity
                .setSalesTransactionId("salesTransactionId12");
        tAlterationHistorySalesTransactionDiscountEntity.setDetailSubNumber(301);
        tAlterationHistorySalesTransactionDiscountEntity.setPromotionType("a10001");
        tAlterationHistorySalesTransactionDiscountEntity.setPromotionNo("0");
        tAlterationHistorySalesTransactionDiscountEntity.setStoreDiscountType("a10002");
        tAlterationHistorySalesTransactionDiscountEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionDiscountEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId12");
        tAlterationHistorySalesTransactionDiscountEntity.setItemDiscountSubNumber(9901);
        tAlterationHistorySalesTransactionDiscountEntity.setQuantityCode("n");
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountQuantity(10);
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDiscountEntity
                .setDiscountAmountTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDiscountEntity
                .setDiscountAmountTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDiscountEntity.setCreateUserId("user12");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 36);
        tAlterationHistorySalesTransactionDiscountEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDiscountEntity.setCreateProgramId("SLS0300112");
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateUserId("user12");
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateProgramId("SLS0300112");

        return tAlterationHistorySalesTransactionDiscountEntity;
    }

    public static AlterationHistorySalesTransactionTax makeTAlterationHistorySalesTransactionTaxForInsertNonBefore() {
        AlterationHistorySalesTransactionTax tAlterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        tAlterationHistorySalesTransactionTaxEntity
                .setTransactionId("originalSalesTransactionErrorId13");
        tAlterationHistorySalesTransactionTaxEntity.setOrderSubNumber(405);
        tAlterationHistorySalesTransactionTaxEntity.setSalesTransactionId("SalesTransactionId");
        tAlterationHistorySalesTransactionTaxEntity.setDetailSubNumber(406);
        tAlterationHistorySalesTransactionTaxEntity.setTaxGroup("TaxGroup");
        tAlterationHistorySalesTransactionTaxEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionTaxEntity.setSalesTransactionErrorId("TransactionId");
        tAlterationHistorySalesTransactionTaxEntity.setTaxSubNumber(9901);
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountSign("s");
        tAlterationHistorySalesTransactionTaxEntity
                .setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTaxEntity.setTaxRate(new BigDecimal(1221));
        tAlterationHistorySalesTransactionTaxEntity.setTaxName("a100000002");
        tAlterationHistorySalesTransactionTaxEntity.setCreateUserId("user13");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 27);
        tAlterationHistorySalesTransactionTaxEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setCreateProgramId("SLS0300113");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateUserId("user13");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setUpdateProgramId("SLS0300113");

        return tAlterationHistorySalesTransactionTaxEntity;
    }

    public static AlterationHistorySalesTransactionTax makeTAlterationHistorySalesTransactionTaxForInsertNonAfter() {
        AlterationHistorySalesTransactionTax tAlterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        tAlterationHistorySalesTransactionTaxEntity.setTransactionId("salesTransactionErrorId14");
        tAlterationHistorySalesTransactionTaxEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionTaxEntity.setSalesTransactionId("salesTransactionId14");
        tAlterationHistorySalesTransactionTaxEntity.setDetailSubNumber(301);
        tAlterationHistorySalesTransactionTaxEntity.setTaxGroup("a100000001");
        tAlterationHistorySalesTransactionTaxEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionTaxEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId14");
        tAlterationHistorySalesTransactionTaxEntity.setTaxSubNumber(9901);
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountSign("s");
        tAlterationHistorySalesTransactionTaxEntity
                .setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTaxEntity.setTaxRate(new BigDecimal(1221));
        tAlterationHistorySalesTransactionTaxEntity.setTaxName("a100000002");
        tAlterationHistorySalesTransactionTaxEntity.setCreateUserId("user14");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 37);
        tAlterationHistorySalesTransactionTaxEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setCreateProgramId("SLS0300114");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateUserId("user14");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setUpdateProgramId("SLS0300114");

        return tAlterationHistorySalesTransactionTaxEntity;
    }

    public static AlterationHistorySalesTransactionDiscount makeTAlterationHistorySalesTransactionDiscountForInsertBefore() {
        AlterationHistorySalesTransactionDiscount tAlterationHistorySalesTransactionDiscountEntity =
                new AlterationHistorySalesTransactionDiscount();
        tAlterationHistorySalesTransactionDiscountEntity
                .setTransactionId("originalSalesTransactionErrorId15");
        tAlterationHistorySalesTransactionDiscountEntity.setOrderSubNumber(402);
        tAlterationHistorySalesTransactionDiscountEntity
                .setSalesTransactionId("SalesTransactionId11");
        tAlterationHistorySalesTransactionDiscountEntity.setDetailSubNumber(403);
        tAlterationHistorySalesTransactionDiscountEntity.setPromotionType("PromotionType11");
        tAlterationHistorySalesTransactionDiscountEntity.setPromotionNo("5508");
        tAlterationHistorySalesTransactionDiscountEntity.setStoreDiscountType("A");
        tAlterationHistorySalesTransactionDiscountEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionDiscountEntity
                .setSalesTransactionErrorId("TransactionId11");
        tAlterationHistorySalesTransactionDiscountEntity.setItemDiscountSubNumber(9901);
        tAlterationHistorySalesTransactionDiscountEntity.setQuantityCode("n");
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountQuantity(10);
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDiscountEntity
                .setDiscountAmountTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDiscountEntity
                .setDiscountAmountTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDiscountEntity.setCreateUserId("user15");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 28);
        tAlterationHistorySalesTransactionDiscountEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDiscountEntity.setCreateProgramId("SLS0300115");
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateUserId("user15");
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateProgramId("SLS0300115");

        return tAlterationHistorySalesTransactionDiscountEntity;
    }

    public static AlterationHistorySalesTransactionDiscount makeTAlterationHistorySalesTransactionDiscountForInsertAfter() {
        AlterationHistorySalesTransactionDiscount tAlterationHistorySalesTransactionDiscountEntity =
                new AlterationHistorySalesTransactionDiscount();
        tAlterationHistorySalesTransactionDiscountEntity
                .setTransactionId("salesTransactionErrorId16");
        tAlterationHistorySalesTransactionDiscountEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionDiscountEntity
                .setSalesTransactionId("salesTransactionId16");
        tAlterationHistorySalesTransactionDiscountEntity.setDetailSubNumber(301);
        tAlterationHistorySalesTransactionDiscountEntity.setPromotionType("c00001");
        tAlterationHistorySalesTransactionDiscountEntity.setPromotionNo("a000000001");
        tAlterationHistorySalesTransactionDiscountEntity.setStoreDiscountType("c00002");
        tAlterationHistorySalesTransactionDiscountEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionDiscountEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId16");
        tAlterationHistorySalesTransactionDiscountEntity.setItemDiscountSubNumber(9901);
        tAlterationHistorySalesTransactionDiscountEntity.setQuantityCode("A");
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountQuantity(45612);
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDiscountEntity
                .setDiscountAmountTaxExcluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionDiscountEntity
                .setDiscountAmountTaxIncluded(new BigDecimal(1));
        tAlterationHistorySalesTransactionDiscountEntity.setCreateUserId("user16");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 38);
        tAlterationHistorySalesTransactionDiscountEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDiscountEntity.setCreateProgramId("SLS0300116");
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateUserId("user16");
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionDiscountEntity.setUpdateProgramId("SLS0300116");

        return tAlterationHistorySalesTransactionDiscountEntity;
    }

    public static AlterationHistorySalesTransactionTax makeTAlterationHistorySalesTransactionTaxForInsertBefore() {
        AlterationHistorySalesTransactionTax tAlterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        tAlterationHistorySalesTransactionTaxEntity
                .setTransactionId("originalSalesTransactionErrorId17");
        tAlterationHistorySalesTransactionTaxEntity.setOrderSubNumber(405);
        tAlterationHistorySalesTransactionTaxEntity.setSalesTransactionId("SalesTransactionId");
        tAlterationHistorySalesTransactionTaxEntity.setDetailSubNumber(406);
        tAlterationHistorySalesTransactionTaxEntity.setTaxGroup("TaxGroup");
        tAlterationHistorySalesTransactionTaxEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionTaxEntity.setSalesTransactionErrorId("TransactionId");
        tAlterationHistorySalesTransactionTaxEntity.setTaxSubNumber(9901);
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountSign("s");
        tAlterationHistorySalesTransactionTaxEntity
                .setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTaxEntity.setTaxRate(new BigDecimal(1221));
        tAlterationHistorySalesTransactionTaxEntity.setTaxName("a100000002");
        tAlterationHistorySalesTransactionTaxEntity.setCreateUserId("user17");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        tAlterationHistorySalesTransactionTaxEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setCreateProgramId("SLS0300117");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateUserId("user17");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setUpdateProgramId("SLS0300117");

        return tAlterationHistorySalesTransactionTaxEntity;
    }

    public static AlterationHistorySalesTransactionTax makeTAlterationHistorySalesTransactionTaxForInsertAfter() {
        AlterationHistorySalesTransactionTax tAlterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        tAlterationHistorySalesTransactionTaxEntity.setTransactionId("salesTransactionErrorId18");
        tAlterationHistorySalesTransactionTaxEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionTaxEntity.setSalesTransactionId("salesTransactionId18");
        tAlterationHistorySalesTransactionTaxEntity.setDetailSubNumber(301);
        tAlterationHistorySalesTransactionTaxEntity.setTaxGroup("a000000001");
        tAlterationHistorySalesTransactionTaxEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionTaxEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId18");
        tAlterationHistorySalesTransactionTaxEntity.setTaxSubNumber(9901);
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountSign("W");
        tAlterationHistorySalesTransactionTaxEntity
                .setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTaxEntity.setTaxRate(new BigDecimal(9999));
        tAlterationHistorySalesTransactionTaxEntity.setTaxName("dgsdgsdg0000ds00001");
        tAlterationHistorySalesTransactionTaxEntity.setCreateUserId("user18");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 39);
        tAlterationHistorySalesTransactionTaxEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setCreateProgramId("SLS0300118");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateUserId("user18");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setUpdateProgramId("SLS0300118");

        return tAlterationHistorySalesTransactionTaxEntity;
    }

    public static AlterationHistorySalesTransactionTender makeTAlterationHistorySalesTransactionTenderBefore() {
        AlterationHistorySalesTransactionTender tAlterationHistorySalesTransactionTenderEntity =
                new AlterationHistorySalesTransactionTender();
        tAlterationHistorySalesTransactionTenderEntity
                .setTransactionId("originalSalesTransactionErrorId19");
        tAlterationHistorySalesTransactionTenderEntity.setOrderSubNumber(506);
        tAlterationHistorySalesTransactionTenderEntity
                .setSalesTransactionId("SalesTransactionId19");
        tAlterationHistorySalesTransactionTenderEntity.setTenderGroup("TenderGroup");
        tAlterationHistorySalesTransactionTenderEntity.setTenderId("TenderId");
        tAlterationHistorySalesTransactionTenderEntity
                .setSalesTransactionErrorId("TransactionId19");
        tAlterationHistorySalesTransactionTenderEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionTenderEntity.setPaymentSign("c");
        tAlterationHistorySalesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTenderEntity
                .setTaxIncludedPaymentAmountValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTenderEntity.setTenderSubNumber(301);
        tAlterationHistorySalesTransactionTenderEntity.setCreateUserId("user19");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 30);
        tAlterationHistorySalesTransactionTenderEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTenderEntity.setCreateProgramId("SLS0300119");
        tAlterationHistorySalesTransactionTenderEntity.setUpdateUserId("user19");
        tAlterationHistorySalesTransactionTenderEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTenderEntity.setUpdateProgramId("SLS0300119");

        return tAlterationHistorySalesTransactionTenderEntity;
    }

    public static AlterationHistorySalesTransactionTender makeTAlterationHistorySalesTransactionTenderAfter() {
        AlterationHistorySalesTransactionTender tAlterationHistorySalesTransactionTenderEntity =
                new AlterationHistorySalesTransactionTender();
        tAlterationHistorySalesTransactionTenderEntity
                .setTransactionId("salesTransactionErrorId20");
        tAlterationHistorySalesTransactionTenderEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionTenderEntity
                .setSalesTransactionId("salesTransactionId20");
        tAlterationHistorySalesTransactionTenderEntity.setTenderGroup("a20351");
        tAlterationHistorySalesTransactionTenderEntity.setTenderId("201802");
        tAlterationHistorySalesTransactionTenderEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionTenderEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId20");
        tAlterationHistorySalesTransactionTenderEntity.setPaymentSign("c");
        tAlterationHistorySalesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTenderEntity
                .setTaxIncludedPaymentAmountValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTenderEntity.setTenderSubNumber(301);
        tAlterationHistorySalesTransactionTenderEntity.setCreateUserId("user20");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 40);
        tAlterationHistorySalesTransactionTenderEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTenderEntity.setCreateProgramId("SLS0300120");
        tAlterationHistorySalesTransactionTenderEntity.setUpdateUserId("user20");
        tAlterationHistorySalesTransactionTenderEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTenderEntity.setUpdateProgramId("SLS0300120");

        return tAlterationHistorySalesTransactionTenderEntity;
    }

    public static AlterationHistorySalesTransactionTenderInfo makeTAlterationHistorySalesTransactionTenderInfoBefore() {
        AlterationHistorySalesTransactionTenderInfo tAlterationHistorySalesTransactionTenderInfoEntity =
                new AlterationHistorySalesTransactionTenderInfo();
        tAlterationHistorySalesTransactionTenderInfoEntity.setTenderSubNumber(1);
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setTransactionId("originalSalesTransactionErrorId21");
        tAlterationHistorySalesTransactionTenderInfoEntity.setOrderSubNumber(602);
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setSalesTransactionId("SalesTransactionId21");
        tAlterationHistorySalesTransactionTenderInfoEntity.setTenderGroup("TenderGroup");
        tAlterationHistorySalesTransactionTenderInfoEntity.setTenderId("TenderId");
        tAlterationHistorySalesTransactionTenderInfoEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setSalesTransactionErrorId("TransactionId21");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setDiscountValueCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTenderInfoEntity.setDiscountValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTenderInfoEntity.setDiscountRate(new BigDecimal(2018));
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setDiscountCodeIdCorporateId("a00000000000000000000000000001");
        tAlterationHistorySalesTransactionTenderInfoEntity.setCouponType("a00001");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCouponDiscountAmountSettingCurrencyCode(
                        Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCouponMinUsageAmountThresholdValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCouponMinUsageAmountThresholdCurrencyCode(
                        Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCouponDiscountAmountSettingValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCouponUserId("a00000000000000000000000000002");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCardNo("a00000000000000000000000000003");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCreditApprovalCode("a00000000000000000000000000004");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCreditProcessingSerialNumber("a00000000000000000000000000005");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCreditPaymentType("a00000000000000000000000000006");
        tAlterationHistorySalesTransactionTenderInfoEntity.setCreditPaymentCount(24567);
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCreditAffiliatedStoreNumber("a00000000000000000000000000007");
        tAlterationHistorySalesTransactionTenderInfoEntity.setCreateUserId("user21");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 31);
        tAlterationHistorySalesTransactionTenderInfoEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTenderInfoEntity.setCreateProgramId("SLS0300121");
        tAlterationHistorySalesTransactionTenderInfoEntity.setUpdateUserId("user21");
        tAlterationHistorySalesTransactionTenderInfoEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTenderInfoEntity.setUpdateProgramId("SLS0300121");

        return tAlterationHistorySalesTransactionTenderInfoEntity;
    }

    public static AlterationHistorySalesTransactionTenderInfo makeTAlterationHistorySalesTransactionTenderInfoAfter() {
        AlterationHistorySalesTransactionTenderInfo tAlterationHistorySalesTransactionTenderInfoEntity =
                new AlterationHistorySalesTransactionTenderInfo();
        tAlterationHistorySalesTransactionTenderInfoEntity.setTenderSubNumber(1);
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setTransactionId("salesTransactionErrorId22");
        tAlterationHistorySalesTransactionTenderInfoEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setSalesTransactionId("salesTransactionId22");
        tAlterationHistorySalesTransactionTenderInfoEntity.setTenderGroup("tenderGroupId22");
        tAlterationHistorySalesTransactionTenderInfoEntity.setTenderId("301");
        tAlterationHistorySalesTransactionTenderInfoEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId22");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setDiscountValueCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTenderInfoEntity.setDiscountValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTenderInfoEntity.setDiscountRate(new BigDecimal(2018));
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setDiscountCodeIdCorporateId("a00000000000000000000000000001");
        tAlterationHistorySalesTransactionTenderInfoEntity.setCouponType("a00001");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCouponDiscountAmountSettingCurrencyCode(
                        Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCouponMinUsageAmountThresholdValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCouponMinUsageAmountThresholdCurrencyCode(
                        Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCouponDiscountAmountSettingValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCouponUserId("a00000000000000000000000000002");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCardNo("a00000000000000000000000000003");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCreditApprovalCode("a00000000000000000000000000004");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCreditProcessingSerialNumber("a00000000000000000000000000005");
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCreditPaymentType("a00000000000000000000000000006");
        tAlterationHistorySalesTransactionTenderInfoEntity.setCreditPaymentCount(24567);
        tAlterationHistorySalesTransactionTenderInfoEntity
                .setCreditAffiliatedStoreNumber("a00000000000000000000000000007");
        tAlterationHistorySalesTransactionTenderInfoEntity.setCreateUserId("user22");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 41);
        tAlterationHistorySalesTransactionTenderInfoEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTenderInfoEntity.setCreateProgramId("SLS0300122");
        tAlterationHistorySalesTransactionTenderInfoEntity.setUpdateUserId("user22");
        tAlterationHistorySalesTransactionTenderInfoEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTenderInfoEntity.setUpdateProgramId("SLS0300122");

        return tAlterationHistorySalesTransactionTenderInfoEntity;
    }

    public static AlterationHistorySalesTransactionTax makeTAlterationHistorySalesTransactionTaxTransactionBefore() {
        AlterationHistorySalesTransactionTax tAlterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        tAlterationHistorySalesTransactionTaxEntity
                .setTransactionId("originalSalesTransactionErrorId23");
        tAlterationHistorySalesTransactionTaxEntity.setOrderSubNumber(405);
        tAlterationHistorySalesTransactionTaxEntity.setSalesTransactionId("SalesTransactionId");
        tAlterationHistorySalesTransactionTaxEntity.setDetailSubNumber(406);
        tAlterationHistorySalesTransactionTaxEntity.setTaxGroup("TaxGroup");
        tAlterationHistorySalesTransactionTaxEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionTaxEntity.setSalesTransactionErrorId("TransactionId");
        tAlterationHistorySalesTransactionTaxEntity.setTaxSubNumber(9901);
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountSign("s");
        tAlterationHistorySalesTransactionTaxEntity
                .setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTaxEntity.setTaxRate(new BigDecimal(1221));
        tAlterationHistorySalesTransactionTaxEntity.setTaxName("a100000002");
        tAlterationHistorySalesTransactionTaxEntity.setCreateUserId("user23");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tAlterationHistorySalesTransactionTaxEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setCreateProgramId("SLS0300123");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateUserId("user23");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setUpdateProgramId("SLS0300123");

        return tAlterationHistorySalesTransactionTaxEntity;
    }

    public static AlterationHistorySalesTransactionTax makeTAlterationHistorySalesTransactionTaxTransactionAfter() {
        AlterationHistorySalesTransactionTax tAlterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        tAlterationHistorySalesTransactionTaxEntity.setTransactionId("salesTransactionErrorId24");
        tAlterationHistorySalesTransactionTaxEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionTaxEntity.setSalesTransactionId("salesTransactionId24");
        tAlterationHistorySalesTransactionTaxEntity.setDetailSubNumber(301);
        tAlterationHistorySalesTransactionTaxEntity.setTaxGroup("a123");
        tAlterationHistorySalesTransactionTaxEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionTaxEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId24");
        tAlterationHistorySalesTransactionTaxEntity.setTaxSubNumber(401);
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountSign("b");
        tAlterationHistorySalesTransactionTaxEntity
                .setTaxAmountCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTaxEntity.setTaxRate(new BigDecimal(12));
        tAlterationHistorySalesTransactionTaxEntity.setTaxName(null);
        tAlterationHistorySalesTransactionTaxEntity.setCreateUserId("user24");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 42);
        tAlterationHistorySalesTransactionTaxEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setCreateProgramId("SLS0300124");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateUserId("user24");
        tAlterationHistorySalesTransactionTaxEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTaxEntity.setUpdateProgramId("SLS0300124");

        return tAlterationHistorySalesTransactionTaxEntity;
    }

    public static AlterationHistorySalesTransactionTotalAmount makeTAlterationHistorySalesTransactionTotalAmountBefore() {
        AlterationHistorySalesTransactionTotalAmount tAlterationHistorySalesTransactionTotalAmountEntity =
                new AlterationHistorySalesTransactionTotalAmount();
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setTransactionId("originalSalesTransactionErrorId25");
        tAlterationHistorySalesTransactionTotalAmountEntity.setOrderSubNumber(903);
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionId("SalesTransactionId25");
        tAlterationHistorySalesTransactionTotalAmountEntity.setTotalType("TotalType");
        tAlterationHistorySalesTransactionTotalAmountEntity.setHistoryType(0);
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionErrorId("TransactionId25");
        tAlterationHistorySalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setTotalAmountTaxExcludedValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setTotalAmountTaxIncludedValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTotalAmountEntity.setTaxRate(new BigDecimal(12));
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionInformation1("z1234567890121");
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionInformation2("z1234567890122");
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionInformation3("z1234567890123");
        tAlterationHistorySalesTransactionTotalAmountEntity.setTotalAmountSubNumber(301);
        tAlterationHistorySalesTransactionTotalAmountEntity.setCreateUserId("user25");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 33);
        tAlterationHistorySalesTransactionTotalAmountEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTotalAmountEntity.setCreateProgramId("SLS0300125");
        tAlterationHistorySalesTransactionTotalAmountEntity.setUpdateUserId("user25");
        tAlterationHistorySalesTransactionTotalAmountEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTotalAmountEntity.setUpdateProgramId("SLS0300125");

        return tAlterationHistorySalesTransactionTotalAmountEntity;
    }

    public static AlterationHistorySalesTransactionTotalAmount makeTAlterationHistorySalesTransactionTotalAmountAfter() {
        AlterationHistorySalesTransactionTotalAmount tAlterationHistorySalesTransactionTotalAmountEntity =
                new AlterationHistorySalesTransactionTotalAmount();
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setTransactionId("salesTransactionErrorId26");
        tAlterationHistorySalesTransactionTotalAmountEntity.setOrderSubNumber(201);
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionId("salesTransactionId26");
        tAlterationHistorySalesTransactionTotalAmountEntity.setTotalType("a123456789");
        tAlterationHistorySalesTransactionTotalAmountEntity.setHistoryType(1);
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionErrorId("originalSalesTransactionErrorId26");
        tAlterationHistorySalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setTotalAmountTaxExcludedValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode(
                Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setTotalAmountTaxIncludedValue(new BigDecimal(1));
        tAlterationHistorySalesTransactionTotalAmountEntity.setTaxRate(new BigDecimal(12));
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionInformation1("z1234567890121");
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionInformation2("z1234567890122");
        tAlterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionInformation3("z1234567890123");
        tAlterationHistorySalesTransactionTotalAmountEntity.setTotalAmountSubNumber(301);
        tAlterationHistorySalesTransactionTotalAmountEntity.setCreateUserId("user26");
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 43);
        tAlterationHistorySalesTransactionTotalAmountEntity.setCreateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTotalAmountEntity.setCreateProgramId("SLS0300126");
        tAlterationHistorySalesTransactionTotalAmountEntity.setUpdateUserId("user26");
        tAlterationHistorySalesTransactionTotalAmountEntity.setUpdateDatetime(nowDateTime);
        tAlterationHistorySalesTransactionTotalAmountEntity.setUpdateProgramId("SLS0300126");

        return tAlterationHistorySalesTransactionTotalAmountEntity;
    }

}
