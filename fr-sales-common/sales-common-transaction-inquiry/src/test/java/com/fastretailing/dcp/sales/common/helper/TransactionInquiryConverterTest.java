/**
 * TransactionInquiryConverterTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.helper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import java.math.BigDecimal;
import java.util.Currency;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.common.SalesCommonTransactionInquiryApplication;
import com.fastretailing.dcp.sales.common.dto.ItemDiscount;
import com.fastretailing.dcp.sales.common.dto.ItemTaxDetail;
import com.fastretailing.dcp.sales.common.dto.NonItemDetail;
import com.fastretailing.dcp.sales.common.dto.NonItemDiscountDetail;
import com.fastretailing.dcp.sales.common.dto.NonItemInformation;
import com.fastretailing.dcp.sales.common.dto.NonItemTaxDetail;
import com.fastretailing.dcp.sales.common.dto.SalesTransactionTaxDetail;
import com.fastretailing.dcp.sales.common.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.common.dto.SalesTransactionTotal;
import com.fastretailing.dcp.sales.common.dto.TenderInformation;
import com.fastretailing.dcp.sales.common.dto.Transaction;
import com.fastretailing.dcp.sales.common.dto.TransactionImportData;
import com.fastretailing.dcp.sales.common.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.common.entity.optional.TransactionInquiryOptional;
import com.fastretailing.dcp.storecommon.dto.Price;
import com.fastretailing.dcp.storecommon.util.DateUtility;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesCommonTransactionInquiryApplication.class)
@AutoConfigureMockMvc
public class TransactionInquiryConverterTest<E> {

    /** Entity. */
    private TransactionInquiryOptional inquiryEntity;

    /** Test target. */
    @SpyBean
    private TransactionInquiryConverter converter;

    /**
     * Initial parameters.
     * 
     * @throws Exception occurred exception.
     */
    @Before
    public void setUp() {

        // Transaction inquiry entity.
        // Transaction inquiry order information.
        inquiryEntity = new TransactionInquiryOptional();
        inquiryEntity.setIntegratedOrderId("IntegratedOrderId");
        inquiryEntity.setOrderBarcodeNumber("OrderBarcodeNumber");
        inquiryEntity.setStoreCode("StoreCode");
        inquiryEntity.setSystemBrandCode("SystemBrandCode");
        inquiryEntity.setSystemBusinessCode("SystemBusinessCode");
        inquiryEntity.setSystemCountryCode("SystemCountryCode");
        inquiryEntity.setChannelCode("ChannelCode");
        inquiryEntity.setCustomerId("CustomerId");
        inquiryEntity.setOrderConfirmationBusinessDate("20180101");
        inquiryEntity
                .setOrderConfirmationDateTime(DateUtility
                        .parseZonedDateTime("2018-01-01T11:11:11Z",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime());
        inquiryEntity.setDataAlterationSalesLinkageType(1);
        inquiryEntity.setDataAlterationBackboneLinkageType(11);
        inquiryEntity.setDataAlterationFlag(Boolean.FALSE);
        inquiryEntity.setDataAlterationUserId("DataAlterationUserId");

        // Transaction inquiry sales transaction header.
        inquiryEntity.setHeaderOrderSubNumber(2);
        inquiryEntity.setHeaderSalesTransactionId("HeaderSalesTransactionId");
        inquiryEntity.setHeaderIntegratedOrderId("HeaderIntegratedOrderId");
        inquiryEntity.setHeaderSalesTransactionSubNumber(22);
        inquiryEntity.setHeaderStoreCode("HeaderStoreCode");
        inquiryEntity
                .setHeaderDataCreationDateTime(DateUtility
                        .parseZonedDateTime("2018-01-20T22:22:22Z",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime());
        inquiryEntity.setHeaderDataCreationBusinessDate("20180120");
        inquiryEntity.setHeaderCashRegisterNo(22);
        inquiryEntity.setHeaderReceiptNo("HeaderReceiptNo");
        inquiryEntity.setHeaderSalesLinkageType(222);
        inquiryEntity.setHeaderSalesTransactionType("HeaderSalesTransactionType");
        inquiryEntity.setHeaderReturnType(2222);
        inquiryEntity.setHeaderSystemBrandCode("HeaderSystemBrandCode");
        inquiryEntity.setHeaderSystemBusinessCode("HeaderSystemBusinessCode");
        inquiryEntity.setHeaderSystemCountryCode("HeaderSystemCountryCode");
        inquiryEntity.setHeaderChannelCode("HeaderChannelCode");
        inquiryEntity.setHeaderOrderStatus("HeaderOrderStatus");
        inquiryEntity.setHeaderOrderSubstatus("HeaderOrderSubstatus");
        inquiryEntity.setHeaderOrderStatusUpdateDate("20180121");
        inquiryEntity
                .setHeaderOrderStatusLastUpdateDateTime(DateUtility
                        .parseZonedDateTime("2018-01-21T21:21:21Z",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime());
        inquiryEntity
                .setHeaderOrderCancelledDateTime(DateUtility
                        .parseZonedDateTime("2018-01-22T22:22:22Z",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime());
        inquiryEntity.setHeaderBookingStoreCode("HeaderBookingStoreCode");
        inquiryEntity.setHeaderBookingStoreSystemBrandCode("HeaderBookingStoreSystemBrandCode");
        inquiryEntity
                .setHeaderBookingStoreSystemBusinessCode("HeaderBookingStoreSystemBusinessCode");
        inquiryEntity.setHeaderBookingStoreSystemCountryCode("HeaderBookingStoreSystemCountryCode");
        inquiryEntity.setHeaderPaymentStatus("HeaderPaymentStatus");
        inquiryEntity.setHeaderPaymentStoreCode("HeaderPaymentStoreCode");
        inquiryEntity.setHeaderPaymentStoreSystemBrandCode("HeaderPaymentStoreSystemBrandCode");
        inquiryEntity
                .setHeaderPaymentStoreSystemBusinessCode("HeaderPaymentStoreSystemBusinessCode");
        inquiryEntity.setHeaderPaymentStoreSystemCountryCode("HeaderPaymentStoreSystemCountryCode");
        inquiryEntity.setHeaderTransferOutStatus("HeaderTransferOutStatus");
        inquiryEntity.setHeaderShipmentStatus("HeaderShipmentStatus");
        inquiryEntity.setHeaderShipmentStoreCode("HeaderShipmentStoreCode");
        inquiryEntity.setHeaderShipmentStoreSystemBrandCode("HeaderShipmentStoreSystemBrandCode");
        inquiryEntity
                .setHeaderShipmentStoreSystemBusinessCode("HeaderShipmentStoreSystemBusinessCode");
        inquiryEntity
                .setHeaderShipmentStoreSystemCountryCode("HeaderShipmentStoreSystemCountryCode");
        inquiryEntity.setHeaderReceivingStatus("HeaderReceivingStatus");
        inquiryEntity.setHeaderReceiptStoreCode("HeaderReceiptStoreCode");
        inquiryEntity.setHeaderReceiptStoreSystemBrandCode("HeaderReceiptStoreSystemBrandCode");
        inquiryEntity
                .setHeaderReceiptStoreSystemBusinessCode("HeaderReceiptStoreSystemBusinessCode");
        inquiryEntity.setHeaderReceiptStoreSystemCountryCode("HeaderReceiptStoreSystemCountryCode");
        inquiryEntity.setHeaderCustomerId("HeaderCustomerId");
        inquiryEntity.setHeaderCustomerType("HeaderCustomerType");
        inquiryEntity.setHeaderOrderNumberForStorePayment("HeaderOrderNumberForStorePayment");
        inquiryEntity.setHeaderAdvanceReceivedStoreCode("HeaderAdvanceReceivedStoreCode");
        inquiryEntity.setHeaderAdvanceReceivedStoreSystemBrandCode(
                "HeaderAdvanceReceivedStoreSystemBrandCode");
        inquiryEntity.setHeaderAdvanceReceivedStoreSystemBusinessCode(
                "HeaderAdvanceReceivedStoreSystemBusinessCode");
        inquiryEntity.setHeaderAdvanceReceivedStoreSystemCountryCode(
                "HeaderAdvanceReceivedStoreSystemCountryCode");
        inquiryEntity.setHeaderOperatorCode("HeaderOperatorCode");
        inquiryEntity.setHeaderOriginalTransactionId("HeaderOriginalTransactionId");
        inquiryEntity.setHeaderOriginalCashRegisterNo(22222);
        inquiryEntity.setHeaderOriginalReceiptNo("HeaderOriginalReceiptNo");
        inquiryEntity.setHeaderDepositCurrencyCode("BGN");
        inquiryEntity.setHeaderDepositValue(new BigDecimal("2.1"));
        inquiryEntity.setHeaderChangeCurrencyCode("ARS");
        inquiryEntity.setHeaderChangeValue(new BigDecimal("2.2"));
        inquiryEntity.setHeaderReceiptNoForCreditCardCancellation(
                "HeaderReceiptNoForCreditCardCancellation");
        inquiryEntity.setHeaderReceiptNoForCreditCard("HeaderReceiptNoForCreditCard");
        inquiryEntity.setHeaderReceiptIssuedFlag(Boolean.FALSE);
        inquiryEntity.setHeaderEReceiptTargetFlag(Boolean.TRUE);
        inquiryEntity.setHeaderProcessingCompanyCode("HeaderProcessingCompanyCode");
        inquiryEntity.setHeaderEmployeeSaleFlag(Boolean.FALSE);
        inquiryEntity.setHeaderConsistencySalesFlag(Boolean.TRUE);
        inquiryEntity.setHeaderCorporateId("HeaderCorporateId");
        inquiryEntity.setHeaderSalesTransactionDiscountFlag(Boolean.FALSE);
        inquiryEntity.setHeaderSalesTransactionDiscountType("HeaderSalesTransactionDiscountType");
        inquiryEntity.setHeaderSalesTransactionDiscountAmountRateCurrencyCode("BAM");
        inquiryEntity.setHeaderSalesTransactionDiscountAmountRate(new BigDecimal("2.3"));
        inquiryEntity.setHeaderTokenCode("HeaderTokenCode");
        inquiryEntity.setHeaderReturnCompleteFlag(Boolean.TRUE);
        inquiryEntity.setHeaderCancelledFlag(Boolean.FALSE);

        // Transaction inquiry sales transaction detail.
        inquiryEntity.setDetailOrderSubNumber(3);
        inquiryEntity.setDetailSalesTransactionId("DetailSalesTransactionId");
        inquiryEntity.setDetailDetailSubNumber(33);
        inquiryEntity.setDetailItemDetailSubNumber(333);
        inquiryEntity.setDetailSalesTransactionType("DetailSalesTransactionType");
        inquiryEntity.setDetailSystemBrandCode("DetailSystemBrandCode");
        inquiryEntity.setDetailSystemBusinessCode("DetailSystemBusinessCode");
        inquiryEntity.setDetailSystemCountryCode("DetailSystemCountryCode");
        inquiryEntity.setDetailL2ItemCode("DetailL2ItemCode");
        inquiryEntity.setDetailDisplayL2ItemCode("DetailDisplayL2ItemCode");
        inquiryEntity.setDetailL2ProductName("DetailL2ProductName");
        inquiryEntity.setDetailL3ItemCode("DetailL3ItemCode");
        inquiryEntity.setDetailL3PosProductName("DetailL3PosProductName");
        inquiryEntity.setDetailProductClassification("DetailProductClassification");
        inquiryEntity.setDetailNonMdType("DetailNonMdType");
        inquiryEntity.setDetailNonMdCode("DetailNonMdCode");
        inquiryEntity.setDetailServiceCode("DetailServiceCode");
        inquiryEntity.setDetailEpcCode("DetailEpcCode");
        inquiryEntity.setDetailGDepartmentCode("DetailGDepartmentCode");
        inquiryEntity.setDetailMajorCategoryCode("3131");
        inquiryEntity.setDetailQuantityCode("DetailQuantityCode");
        inquiryEntity.setDetailDetailQuantity(3333);
        inquiryEntity.setDetailItemCostCurrencyCode("COP");
        inquiryEntity.setDetailItemCostValue(new BigDecimal("3.1"));
        inquiryEntity.setDetailInitialSellingPriceCurrencyCode("COU");
        inquiryEntity.setDetailInitialSellingPrice(new BigDecimal("3.2"));
        inquiryEntity.setDetailBclassPriceCurrencyCode("ISK");
        inquiryEntity.setDetailBclassPrice(new BigDecimal("3.3"));
        inquiryEntity.setDetailNewPriceCurrencyCode("CRC");
        inquiryEntity.setDetailNewPrice(new BigDecimal("3.4"));
        inquiryEntity.setDetailRetailUnitPriceTaxExcludedCurrencyCode("CUC");
        inquiryEntity.setDetailRetailUnitPriceTaxExcluded(new BigDecimal("3.5"));
        inquiryEntity.setDetailRetailUnitPriceTaxIncludedCurrencyCode("CUP");
        inquiryEntity.setDetailRetailUnitPriceTaxIncluded(new BigDecimal("3.6"));
        inquiryEntity.setDetailSalesAmountTaxExcludedCurrencyCode("CZK");
        inquiryEntity.setDetailSalesAmountTaxExcluded(new BigDecimal("3.7"));
        inquiryEntity.setDetailSalesAmountTaxIncludedCurrencyCode("DKK");
        inquiryEntity.setDetailSalesAmountTaxIncluded(new BigDecimal("3.8"));
        inquiryEntity.setDetailCalculationUnavailableType("DetailCalculationUnavailableType");
        inquiryEntity.setDetailOrderStatusUpdateDate("20180301");
        inquiryEntity
                .setDetailOrderStatusLastUpdateDateTime(DateUtility
                        .parseZonedDateTime("2018-03-01T03:03:03Z",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime());
        inquiryEntity.setDetailOrderStatus("DetailOrderStatus");
        inquiryEntity.setDetailOrderSubstatus("DetailOrderSubstatus");
        inquiryEntity.setDetailBookingStoreCode("DetailBookingStoreCode");
        inquiryEntity.setDetailBookingStoreSystemBrandCode("DetailBookingStoreSystemBrandCode");
        inquiryEntity
                .setDetailBookingStoreSystemBusinessCode("DetailBookingStoreSystemBusinessCode");
        inquiryEntity.setDetailBookingStoreSystemCountryCode("DetailBookingStoreSystemCountryCode");
        inquiryEntity.setDetailShipmentStoreCode("DetailShipmentStoreCode");
        inquiryEntity.setDetailShipmentStoreSystemBrandCode("DetailShipmentStoreSystemBrandCode");
        inquiryEntity
                .setDetailShipmentStoreSystemBusinessCode("DetailShipmentStoreSystemBusinessCode");
        inquiryEntity
                .setDetailShipmentStoreSystemCountryCode("DetailShipmentStoreSystemCountryCode");
        inquiryEntity.setDetailReceiptStoreCode("DetailReceiptStoreCode");
        inquiryEntity.setDetailReceiptStoreSystemBrandCode("DetailReceiptStoreSystemBrandCode");
        inquiryEntity
                .setDetailReceiptStoreSystemBusinessCode("DetailReceiptStoreSystemBusinessCode");
        inquiryEntity.setDetailReceiptStoreSystemCountryCode("DetailReceiptStoreSystemCountryCode");
        inquiryEntity
                .setDetailContributionSalesRepresentative("DetailContributionSalesRepresentative");
        inquiryEntity.setDetailCustomerId("DetailCustomerId");
        inquiryEntity.setDetailBundlePurchaseApplicableQuantity(33333);
        inquiryEntity.setDetailBundlePurchaseApplicablePriceCurrencyCode("MXN");
        inquiryEntity.setDetailBundlePurchaseApplicablePrice(new BigDecimal("3.9"));
        inquiryEntity.setDetailBundlePurchaseIndex(31);
        inquiryEntity.setDetailLimitedAmountPromotionCount(32);
        inquiryEntity.setDetailStoreItemDiscountType("DetailStoreItemDiscountType");
        inquiryEntity.setDetailStoreItemDiscountCurrencyCode("DOP");
        inquiryEntity.setDetailStoreItemDiscountSetting(new BigDecimal("3.11"));
        inquiryEntity.setDetailStoreBundleSaleFlag(Boolean.FALSE);
        inquiryEntity.setDetailStoreBundleSalePriceCurrencyCode("EGP");
        inquiryEntity.setDetailStoreBundleSalePrice(new BigDecimal("3.12"));
        inquiryEntity.setDetailSetSalesDetailIndex(34);
        inquiryEntity.setDetailTaxationType("DetailTaxationType");
        inquiryEntity.setDetailTaxSystemType("DetailTaxSystemType");
        inquiryEntity.setDetailReturnCompleteFlag(Boolean.TRUE);

        // Transaction inquiry sales transaction detail information.
        inquiryEntity.setDetailInfoOrderSubNumber(4);
        inquiryEntity.setDetailInfoSalesTransactionId("DetailInfoSalesTransactionId");
        inquiryEntity.setDetailInfoDetailSubNumber(44);
        inquiryEntity.setDetailInfoItemDetailSubNumber(444);
        inquiryEntity.setDetailInfoKeyCode("DetailInfoKeyCode");
        inquiryEntity.setDetailInfoCodeValue1("DetailInfoCodeValue1");
        inquiryEntity.setDetailInfoCodeValue2("DetailInfoCodeValue2");
        inquiryEntity.setDetailInfoCodeValue3("DetailInfoCodeValue3");
        inquiryEntity.setDetailInfoCodeValue4("DetailInfoCodeValue4");
        inquiryEntity.setDetailInfoName1("DetailInfoName1");
        inquiryEntity.setDetailInfoName2("DetailInfoName2");
        inquiryEntity.setDetailInfoName3("DetailInfoName3");
        inquiryEntity.setDetailInfoName4("DetailInfoName4");

        // Transaction inquiry sales transaction discount.
        inquiryEntity.setDiscountOrderSubNumber(5);
        inquiryEntity.setDiscountSalesTransactionId("DiscountSalesTransactionId");
        inquiryEntity.setDiscountDetailSubNumber(55);
        inquiryEntity.setDiscountPromotionType("DiscountPromotionType");
        inquiryEntity.setDiscountPromotionNo("DiscountPromotionNo");
        inquiryEntity.setDiscountStoreDiscountType("DiscountStoreDiscountType");
        inquiryEntity.setDiscountItemDiscountSubNumber(555);
        inquiryEntity.setDiscountQuantityCode("DiscountQuantityCode");
        inquiryEntity.setDiscountDiscountQuantity(5555);
        inquiryEntity.setDiscountDiscountAmountTaxExcludedCurrencyCode("JPY");
        inquiryEntity.setDiscountDiscountAmountTaxExcluded(new BigDecimal("5.1"));
        inquiryEntity.setDiscountDiscountAmountTaxIncludedCurrencyCode("CAD");
        inquiryEntity.setDiscountDiscountAmountTaxIncluded(new BigDecimal("5.2"));

        // Transaction inquiry sales transaction tax.
        inquiryEntity.setTaxOrderSubNumber(6);
        inquiryEntity.setTaxSalesTransactionId("TaxSalesTransactionId");
        inquiryEntity.setTaxDetailSubNumber(66);
        inquiryEntity.setTaxTaxGroup("TaxTaxGroup");
        inquiryEntity.setTaxTaxName("TaxTaxName");
        inquiryEntity.setTaxTaxSubNumber(666);
        inquiryEntity.setTaxTaxAmountSign("TaxTaxAmountSign");
        inquiryEntity.setTaxTaxAmountCurrencyCode("USD");
        inquiryEntity.setTaxTaxAmountValue(new BigDecimal("6.1"));
        inquiryEntity.setTaxTaxRate(new BigDecimal("6.2"));

        // Transaction inquiry sales transaction tender.
        inquiryEntity.setTenderOrderSubNumber(7);
        inquiryEntity.setTenderSalesTransactionId("TenderSalesTransactionId");
        inquiryEntity.setTenderTenderGroup("TenderTenderGroup");
        inquiryEntity.setTenderTenderId("TenderTenderId");
        inquiryEntity.setTenderTenderSubNumber(77);
        inquiryEntity.setTenderPaymentSign("TenderPaymentSign");
        inquiryEntity.setTenderTaxIncludedPaymentAmountCurrencyCode("BRL");
        inquiryEntity.setTenderTaxIncludedPaymentAmountValue(new BigDecimal("7.1"));

        // Transaction inquiry sales transaction tender information.
        inquiryEntity.setTenderInfoOrderSubNumber(8);
        inquiryEntity.setTenderInfoSalesTransactionId("TenderInfoSalesTransactionId");
        inquiryEntity.setTenderInfoTenderGroup("TenderInfoTenderGroup");
        inquiryEntity.setTenderInfoTenderId("TenderInfoTenderId");
        inquiryEntity.setTenderInfoTenderSubNumber(88);
        inquiryEntity.setTenderInfoDiscountValueCurrencyCode("EUR");
        inquiryEntity.setTenderInfoDiscountValue(new BigDecimal("8.1"));
        inquiryEntity.setTenderInfoDiscountRate(new BigDecimal("8.2"));
        inquiryEntity.setTenderInfoDiscountCodeIdCorporateId("TenderInfoDiscountCodeIdCorporateId");
        inquiryEntity.setTenderInfoCouponType("TenderInfoCouponType");
        inquiryEntity.setTenderInfoCouponDiscountAmountSettingCurrencyCode("GBP");
        inquiryEntity.setTenderInfoCouponDiscountAmountSettingValue(new BigDecimal("8.3"));
        inquiryEntity.setTenderInfoCouponMinUsageAmountThresholdCurrencyCode("KRW");
        inquiryEntity.setTenderInfoCouponMinUsageAmountThresholdValue(new BigDecimal("8.4"));
        inquiryEntity.setTenderInfoCouponUserId("TenderInfoCouponUserId");
        inquiryEntity.setTenderInfoCardNo("TenderInfoCardNo");
        inquiryEntity.setTenderInfoCreditApprovalCode("TenderInfoCreditApprovalCode");
        inquiryEntity.setTenderInfoCreditProcessingSerialNumber(
                "TenderInfoCreditProcessingSerialNumber");
        inquiryEntity.setTenderInfoCreditPaymentType("TenderInfoCreditPaymentType");
        inquiryEntity.setTenderInfoCreditPaymentCount(888);
        inquiryEntity
                .setTenderInfoCreditAffiliatedStoreNumber("TenderInfoCreditAffiliatedStoreNumber");

        // Transaction inquiry sales transaction total amount.
        inquiryEntity.setTotalOrderSubNumber(9);
        inquiryEntity.setTotalSalesTransactionId("TotalSalesTransactionId");
        inquiryEntity.setTotalTotalType("TotalTotalType");
        inquiryEntity.setTotalTotalAmountSubNumber(99);
        inquiryEntity.setTotalTotalAmountTaxExcludedCurrencyCode("CHF");
        inquiryEntity.setTotalTotalAmountTaxExcludedValue(new BigDecimal("9.1"));
        inquiryEntity.setTotalTotalAmountTaxIncludedCurrencyCode("CLP");
        inquiryEntity.setTotalTotalAmountTaxIncludedValue(new BigDecimal("9.2"));
        inquiryEntity.setTotalTaxRate(new BigDecimal("93.3"));
        inquiryEntity.setTotalSalesTransactionInformation1("TotalSalesTransactionInformation1");
        inquiryEntity.setTotalSalesTransactionInformation2("TotalSalesTransactionInformation2");
        inquiryEntity.setTotalSalesTransactionInformation3("TotalSalesTransactionInformation3");

    }

    /**
     * No data - convertToTransactionImportData().
     */
    @Test
    public void testConvertToTransactionImportDataNodata() {

        // Prepare test.

        // Call target.
        TransactionImportData result = converter.convertToTransactionImportData(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToTransactionImportData().
     */
    @Test
    public void testConvertToTransactionImportDataExistdata() {

        // Prepare test.

        // Call target.
        TransactionImportData result = converter.convertToTransactionImportData(inquiryEntity);

        // Confirm result.
        TransactionImportData importData = new TransactionImportData();
        importData.setUpdateType(null);
        importData.setErrorCheckType(null);
        importData.setDataAlterationSalesLinkageType(1);
        importData.setDataAlterationBackboneLinkageType(11);
        importData.setSalesTransactionErrorId(null);
        importData.setIntegratedOrderId("IntegratedOrderId");
        importData.setOrderBarcodeNumber("OrderBarcodeNumber");
        importData.setChannelCode("ChannelCode");
        importData.setStoreCode("StoreCode");
        importData.setSystemBrandCode("SystemBrandCode");
        importData.setSystemBusinessCode("SystemBusinessCode");
        importData.setSystemCountryCode("SystemCountryCode");
        importData.setCustomerId("CustomerId");
        importData.setOrderConfirmationBusinessDate("2018-01-01");
        importData
                .setOrderConfirmationDateTime(DateUtility
                        .parseZonedDateTime("2018-01-01T11:11:11Z",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime());
        importData.setDataCorrectionEditingFlag(0);
        importData.setDataCorrectionUserId("DataAlterationUserId");
        assertThat(result, is(importData));
    }

    /**
     * No data - convertToTransaction().
     */
    @Test
    public void testConvertToTransactionNodata() {

        // Prepare test.

        // Call target.
        Transaction result = converter.convertToTransaction(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToTransaction().
     */
    @Test
    public void testConvertToTransactionExistdata() {

        // Prepare test.

        // Call target.
        Transaction result = converter.convertToTransaction(inquiryEntity);

        // Confirm result.
        // Transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionSerialNumber(22);
        transaction.setIntegratedOrderId("HeaderIntegratedOrderId");
        transaction.setOrderSubNumber(2);
        transaction.setSalesTransactionId("HeaderSalesTransactionId");
        transaction.setTokenCode("HeaderTokenCode");
        transaction.setTransactionType("HeaderSalesTransactionType");
        transaction.setSalesLinkageType(222);
        transaction.setReturnType(2222);
        transaction.setSystemBrandCode("HeaderSystemBrandCode");
        transaction.setSystemBusinessCode("HeaderSystemBusinessCode");
        transaction.setSystemCountryCode("HeaderSystemCountryCode");
        transaction.setStoreCode("HeaderStoreCode");
        transaction.setChannelCode("HeaderChannelCode");
        transaction.setDataCreationBusinessDate("2018-01-20");
        transaction
                .setDataCreationDateTime(DateUtility
                        .parseZonedDateTime("2018-01-20T22:22:22Z",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime());
        transaction.setOrderStatusUpdateDate("2018-01-21");
        transaction
                .setOrderStatusLastUpdateDateTime(DateUtility
                        .parseZonedDateTime("2018-01-21T21:21:21Z",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime());
        transaction.setCashRegisterNo(22);
        transaction.setReceiptNo("HeaderReceiptNo");
        transaction.setOrderNumberForStorePayment("HeaderOrderNumberForStorePayment");
        transaction.setAdvanceReceivedStoreCode("HeaderAdvanceReceivedStoreCode");
        transaction.setAdvanceReceivedStoreSystemBrandCode(
                "HeaderAdvanceReceivedStoreSystemBrandCode");
        transaction.setAdvanceReceivedStoreSystemBusinessCode(
                "HeaderAdvanceReceivedStoreSystemBusinessCode");
        transaction.setAdvanceReceivedStoreSystemCountryCode(
                "HeaderAdvanceReceivedStoreSystemCountryCode");
        transaction.setOperatorCode("HeaderOperatorCode");
        transaction.setOriginalTransactionId("HeaderOriginalTransactionId");
        transaction.setOriginalReceiptNo("HeaderOriginalReceiptNo");
        transaction.setOriginalCashRegisterNo(22222);
        Price deposit = new Price();
        deposit.setCurrencyCode(Currency.getInstance("BGN"));
        deposit.setValue(new BigDecimal("2.1"));
        transaction.setDeposit(deposit);
        Price change = new Price();
        change.setCurrencyCode(Currency.getInstance("ARS"));
        change.setValue(new BigDecimal("2.2"));
        transaction.setChange(change);
        transaction
                .setReceiptNoForCreditCardCancellation("HeaderReceiptNoForCreditCardCancellation");
        transaction.setReceiptNoForCreditCard("HeaderReceiptNoForCreditCard");
        transaction.setPaymentStoreCode("HeaderPaymentStoreCode");
        transaction.setPaymentStoreSystemBrandCode("HeaderPaymentStoreSystemBrandCode");
        transaction.setPaymentStoreSystemBusinessCode("HeaderPaymentStoreSystemBusinessCode");
        transaction.setPaymentStoreSystemCountryCode("HeaderPaymentStoreSystemCountryCode");
        transaction.setReceiptIssuedFlag(0);
        transaction.setProcessingCompanyCode("HeaderProcessingCompanyCode");
        transaction
                .setOrderCancellationDate(DateUtility
                        .parseZonedDateTime("2018-01-22T22:22:22Z",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime());
        transaction.setOrderStatus("HeaderOrderStatus");
        transaction.setOrderSubstatus("HeaderOrderSubstatus");
        transaction.setPaymentStatus("HeaderPaymentStatus");
        transaction.setShipmentStatus("HeaderShipmentStatus");
        transaction.setReceivingStatus("HeaderReceivingStatus");
        transaction.setTransferOutStatus("HeaderTransferOutStatus");
        transaction.setBookingStoreCode("HeaderBookingStoreCode");
        transaction.setBookingStoreSystemBrandCode("HeaderBookingStoreSystemBrandCode");
        transaction.setBookingStoreSystemBusinessCode("HeaderBookingStoreSystemBusinessCode");
        transaction.setBookingStoreSystemCountryCode("HeaderBookingStoreSystemCountryCode");
        transaction.setShipmentStoreCode("HeaderShipmentStoreCode");
        transaction.setShipmentStoreSystemBrandCode("HeaderShipmentStoreSystemBrandCode");
        transaction.setShipmentStoreSystemBusinessCode("HeaderShipmentStoreSystemBusinessCode");
        transaction.setShipmentStoreSystemCountryCode("HeaderShipmentStoreSystemCountryCode");
        transaction.setReceiptStoreCode("HeaderReceiptStoreCode");
        transaction.setReceiptStoreSystemBrandCode("HeaderReceiptStoreSystemBrandCode");
        transaction.setReceiptStoreSystemBusinessCode("HeaderReceiptStoreSystemBusinessCode");
        transaction.setReceiptStoreSystemCountryCode("HeaderReceiptStoreSystemCountryCode");
        transaction.setCustomerId("HeaderCustomerId");
        transaction.setCustomerType("HeaderCustomerType");
        transaction.setCorporateId("HeaderCorporateId");
        transaction.setSalesTransactionDiscountFlag("0");
        transaction.setSalesTransactionDiscountType("HeaderSalesTransactionDiscountType");
        Price rate = new Price();
        rate.setCurrencyCode(Currency.getInstance("BAM"));
        rate.setValue(new BigDecimal("2.3"));
        transaction.setSalesTransactionDiscountAmountRate(rate);
        transaction.setConsistencySalesFlag(1);
        transaction.setEmployeeSaleFlag(0);
        transaction.setEreceiptTargetFlag(1);
        transaction.setReturnCompleteFlag(1);
        transaction.setCancelledFlag(0);
        assertThat(result, is(transaction));
    }

    /**
     * No data - convertToItemDetail().
     */
    @Test
    public void testConvertToItemDetailNodata() {

        // Prepare test.

        // Call target.
        TransactionItemDetail result = converter.convertToItemDetail(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToItemDetail().
     */
    @Test
    public void testConvertToItemDetailExistdata() {

        // Prepare test.

        // Call target.
        TransactionItemDetail result = converter.convertToItemDetail(inquiryEntity);

        // Confirm result.
        TransactionItemDetail itemDetail = new TransactionItemDetail();
        itemDetail.setSystemBrandCode("DetailSystemBrandCode");
        itemDetail.setDetailSubNumber(33);
        itemDetail.setDetailListSalesTransactionType("DetailSalesTransactionType");
        itemDetail.setL2ItemCode("DetailL2ItemCode");
        itemDetail.setViewL2ItemCode("DetailDisplayL2ItemCode");
        itemDetail.setL2ProductName("DetailL2ProductName");
        itemDetail.setL3ItemCode("DetailL3ItemCode");
        itemDetail.setL3PosProductName("DetailL3PosProductName");
        itemDetail.setEpcCode("DetailEpcCode");
        itemDetail.setGdepartmentCode("DetailGDepartmentCode");
        itemDetail.setDeptCode(3131);
        itemDetail.setQuantityCode("DetailQuantityCode");
        itemDetail.setItemQty(3333);
        Price cost = new Price();
        cost.setCurrencyCode(Currency.getInstance("COP"));
        cost.setValue(new BigDecimal("3.1"));
        itemDetail.setItemCost(cost);
        Price initialPrice = new Price();
        initialPrice.setCurrencyCode(Currency.getInstance("COU"));
        initialPrice.setValue(new BigDecimal("3.2"));
        itemDetail.setInitialSellingPrice(initialPrice);
        Price damagedPrice = new Price();
        damagedPrice.setCurrencyCode(Currency.getInstance("ISK"));
        damagedPrice.setValue(new BigDecimal("3.3"));
        itemDetail.setBItemSellingPrice(damagedPrice);
        Price newPrice = new Price();
        newPrice.setCurrencyCode(Currency.getInstance("CRC"));
        newPrice.setValue(new BigDecimal("3.4"));
        itemDetail.setItemNewPrice(newPrice);
        Price taxExclude = new Price();
        taxExclude.setCurrencyCode(Currency.getInstance("CUC"));
        taxExclude.setValue(new BigDecimal("3.5"));
        itemDetail.setItemUnitPriceTaxExcluded(taxExclude);
        Price taxInclude = new Price();
        taxInclude.setCurrencyCode(Currency.getInstance("CUP"));
        taxInclude.setValue(new BigDecimal("3.6"));
        itemDetail.setItemUnitPriceTaxIncluded(taxInclude);
        Price amtTaxExclude = new Price();
        amtTaxExclude.setCurrencyCode(Currency.getInstance("CZK"));
        amtTaxExclude.setValue(new BigDecimal("3.7"));
        itemDetail.setItemSalesAmtTaxExcluded(amtTaxExclude);
        Price amtTaxInclude = new Price();
        amtTaxInclude.setCurrencyCode(Currency.getInstance("DKK"));
        amtTaxInclude.setValue(new BigDecimal("3.8"));
        itemDetail.setItemSalesAmtTaxIncluded(amtTaxInclude);
        itemDetail.setOrderStatusUpdateDate("2018-03-01");
        itemDetail
                .setOrderStatusLastUpdateDateTime(DateUtility
                        .parseZonedDateTime("2018-03-01T03:03:03Z",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime());
        itemDetail.setOrderStatus("DetailOrderStatus");
        itemDetail.setOrderSubstatus("DetailOrderSubstatus");
        itemDetail.setBookingStoreCode("DetailBookingStoreCode");
        itemDetail.setBookingStoreSystemBrandCode("DetailBookingStoreSystemBrandCode");
        itemDetail.setBookingStoreSystemBusinessCode("DetailBookingStoreSystemBusinessCode");
        itemDetail.setBookingStoreSystemCountryCode("DetailBookingStoreSystemCountryCode");
        itemDetail.setShipmentStoreCode("DetailShipmentStoreCode");
        itemDetail.setShipmentStoreSystemBrandCode("DetailShipmentStoreSystemBrandCode");
        itemDetail.setShipmentStoreSystemBusinessCode("DetailShipmentStoreSystemBusinessCode");
        itemDetail.setShipmentStoreSystemCountryCode("DetailShipmentStoreSystemCountryCode");
        itemDetail.setReceiptStoreCode("DetailReceiptStoreCode");
        itemDetail.setReceiptStoreSystemBrandCode("DetailReceiptStoreSystemBrandCode");
        itemDetail.setReceiptStoreSystemBusinessCode("DetailReceiptStoreSystemBusinessCode");
        itemDetail.setReceiptStoreSystemCountryCode("DetailReceiptStoreSystemCountryCode");
        itemDetail.setContributionSalesRepresentative("DetailContributionSalesRepresentative");
        itemDetail.setCustomerId("DetailCustomerId");
        itemDetail.setBundlePurchaseQty(33333);
        Price bundle = new Price();
        bundle.setCurrencyCode(Currency.getInstance("MXN"));
        bundle.setValue(new BigDecimal("3.9"));
        itemDetail.setBundlePurchasePrice(bundle);
        itemDetail.setBundlePurchaseIndex(31);
        itemDetail.setLimitedAmountPromotionCount(32);
        itemDetail.setCalculationUnavailableType("DetailCalculationUnavailableType");
        itemDetail.setItemMountDiscountType("DetailStoreItemDiscountType");
        Price amount = new Price();
        amount.setCurrencyCode(Currency.getInstance("DOP"));
        amount.setValue(new BigDecimal("3.11"));
        itemDetail.setItemDiscountAmount(amount);
        itemDetail.setSetSalesFlag("FALSE");
        Price salesPrice = new Price();
        salesPrice.setCurrencyCode(Currency.getInstance("EGP"));
        salesPrice.setValue(new BigDecimal("3.12"));
        itemDetail.setSetSalesPrice(salesPrice);
        itemDetail.setSetSalesDetailIndex(34);
        itemDetail.setItemDetailNumber(333);
        itemDetail.setItemTaxationType("DetailTaxationType");
        itemDetail.setItemTaxKind("DetailTaxSystemType");
        itemDetail.setItemReturnCompleteFlag(1);
        assertThat(result, is(itemDetail));
    }

    /**
     * No data - convertToNonItemDetail().
     */
    @Test
    public void testConvertToNonItemDetailNodata() {

        // Prepare test.

        // Call target.
        NonItemDetail result = converter.convertToNonItemDetail(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToNonItemDetail().
     */
    @Test
    public void testConvertToNonItemDetailExistdata() {

        // Prepare test.

        // Call target.
        NonItemDetail result = converter.convertToNonItemDetail(inquiryEntity);

        // Confirm result.
        NonItemDetail nonItemDetail = new NonItemDetail();
        nonItemDetail.setNonMdDetailListSalesTransactionType("DetailSalesTransactionType");
        nonItemDetail.setItemDetailSubNumber(333);
        nonItemDetail.setDetailSubNumber(33);
        nonItemDetail.setNonMdType("DetailNonMdType");
        nonItemDetail.setNonItemCode("DetailNonMdCode");
        nonItemDetail.setServiceCode("DetailServiceCode");
        nonItemDetail.setPosNonItemName("DetailL3PosProductName");
        nonItemDetail.setQuantityCode("DetailQuantityCode");
        nonItemDetail.setNonItemQty(3333);
        Price taxExclude = new Price();
        taxExclude.setCurrencyCode(Currency.getInstance("CUC"));
        taxExclude.setValue(new BigDecimal("3.5"));
        nonItemDetail.setNonItemUnitPriceTaxExcluded(taxExclude);
        Price taxInclude = new Price();
        taxInclude.setCurrencyCode(Currency.getInstance("CUP"));
        taxInclude.setValue(new BigDecimal("3.6"));
        nonItemDetail.setNonItemUnitPriceTaxIncluded(taxInclude);
        Price amtTaxExclude = new Price();
        amtTaxExclude.setCurrencyCode(Currency.getInstance("CZK"));
        amtTaxExclude.setValue(new BigDecimal("3.7"));
        nonItemDetail.setNonItemSalesAmtTaxExcluded(amtTaxExclude);
        Price amtTaxInclude = new Price();
        amtTaxInclude.setCurrencyCode(Currency.getInstance("DKK"));
        amtTaxInclude.setValue(new BigDecimal("3.8"));
        nonItemDetail.setNonItemSalesAmtTaxIncluded(amtTaxInclude);
        Price newPrice = new Price();
        newPrice.setCurrencyCode(Currency.getInstance("CRC"));
        newPrice.setValue(new BigDecimal("3.4"));
        nonItemDetail.setNonItemNewPrice(newPrice);
        nonItemDetail.setNonCalculationNonItemType("DetailCalculationUnavailableType");
        nonItemDetail.setOrderStatusUpdateDate("2018-03-01");
        nonItemDetail
                .setOrderStatusLastUpdateDateTime(DateUtility
                        .parseZonedDateTime("2018-03-01T03:03:03Z",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime());
        nonItemDetail.setOrderStatus("DetailOrderStatus");
        nonItemDetail.setOrderSubstatus("DetailOrderSubstatus");
        nonItemDetail.setBookingStoreCode("DetailBookingStoreCode");
        nonItemDetail.setBookingStoreSystemBrandCode("DetailBookingStoreSystemBrandCode");
        nonItemDetail.setBookingStoreSystemBusinessCode("DetailBookingStoreSystemBusinessCode");
        nonItemDetail.setBookingStoreSystemCountryCode("DetailBookingStoreSystemCountryCode");
        nonItemDetail.setShipmentStoreCode("DetailShipmentStoreCode");
        nonItemDetail.setShipmentStoreSystemBrandCode("DetailShipmentStoreSystemBrandCode");
        nonItemDetail.setShipmentStoreSystemBusinessCode("DetailShipmentStoreSystemBusinessCode");
        nonItemDetail.setShipmentStoreSystemCountryCode("DetailShipmentStoreSystemCountryCode");
        nonItemDetail.setReceiptStoreCode("DetailReceiptStoreCode");
        nonItemDetail.setReceiptStoreSystemBrandCode("DetailReceiptStoreSystemBrandCode");
        nonItemDetail.setReceiptStoreSystemBusinessCode("DetailReceiptStoreSystemBusinessCode");
        nonItemDetail.setReceiptStoreSystemCountryCode("DetailReceiptStoreSystemCountryCode");
        nonItemDetail.setContributionSalesRepresentative("DetailContributionSalesRepresentative");
        nonItemDetail.setNonItemDetailNumber(33);
        nonItemDetail.setNonItemTaxationType("DetailTaxationType");
        nonItemDetail.setNonItemTaxKind("DetailTaxSystemType");
        nonItemDetail.setNonItemReturnCompleteFlag(1);
        assertThat(result, is(nonItemDetail));
    }

    /**
     * No data - convertToNonItemInfo().
     */
    @Test
    public void testConvertToNonItemInfoNodata() {

        // Prepare test.

        // Call target.
        NonItemInformation result = converter.convertToNonItemInformation(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToNonItemInfo().
     */
    @Test
    public void testConvertToNonItemInfoExistdata() {

        // Prepare test.

        // Call target.
        NonItemInformation result = converter.convertToNonItemInformation(inquiryEntity);

        // Confirm result.
        NonItemInformation nonItemInfo = new NonItemInformation();
        nonItemInfo.setDetailSubNumber(44);
        nonItemInfo.setItemDetailSubNumber(444);
        nonItemInfo.setKeyCode("DetailInfoKeyCode");
        nonItemInfo.setCodeValue1("DetailInfoCodeValue1");
        nonItemInfo.setCodeValue2("DetailInfoCodeValue2");
        nonItemInfo.setCodeValue3("DetailInfoCodeValue3");
        nonItemInfo.setCodeValue4("DetailInfoCodeValue4");
        nonItemInfo.setName1("DetailInfoName1");
        nonItemInfo.setName2("DetailInfoName2");
        nonItemInfo.setName3("DetailInfoName3");
        nonItemInfo.setName4("DetailInfoName4");
        assertThat(result, is(nonItemInfo));
    }

    /**
     * No data - convertToDiscountDetail().
     */
    @Test
    public void testConvertToDiscountDetailNodata() {

        // Prepare test.

        // Call target.
        NonItemDiscountDetail result = converter.convertToDiscountDetail(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToDiscountDetail().
     */
    @Test
    public void testConvertToDiscountDetailExistdata() {

        // Prepare test.

        // Call target.
        NonItemDiscountDetail result = converter.convertToDiscountDetail(inquiryEntity);

        // Confirm result.
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();
        nonItemDiscountDetail.setNonItemDiscountDetailSubNumber(55);
        nonItemDiscountDetail.setNonItemDiscountSubNumber(555);
        nonItemDiscountDetail.setNonItemPromotionType("DiscountPromotionType");
        nonItemDiscountDetail.setNonItemStoreDiscountType("DiscountStoreDiscountType");
        nonItemDiscountDetail.setNonItemQuantityCode("DiscountQuantityCode");
        nonItemDiscountDetail.setNonItemDiscountQty(5555);
        nonItemDiscountDetail.setNonItemPromotionNumber("DiscountPromotionNo");
        Price taxExcluded = new Price();
        taxExcluded.setCurrencyCode(Currency.getInstance("JPY"));
        taxExcluded.setValue(new BigDecimal("5.1"));
        nonItemDiscountDetail.setNonItemDiscountAmtTaxExcluded(taxExcluded);
        Price taxIncluded = new Price();
        taxIncluded.setCurrencyCode(Currency.getInstance("CAD"));
        taxIncluded.setValue(new BigDecimal("5.2"));
        nonItemDiscountDetail.setNonItemDiscountAmtTaxIncluded(taxIncluded);
        assertThat(result, is(nonItemDiscountDetail));
    }

    /**
     * No data - convertToNonItemTaxDetail().
     */
    @Test
    public void testConvertToNonItemTaxDetailNodata() {

        // Prepare test.

        // Call target.
        NonItemTaxDetail result = converter.convertToNonItemTaxDetail(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToNonItemTaxDetail().
     */
    @Test
    public void testConvertToNonItemTaxDetailExistdata() {

        // Prepare test.

        // Call target.
        NonItemTaxDetail result = converter.convertToNonItemTaxDetail(inquiryEntity);

        // Confirm result.
        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();
        nonItemTaxDetail.setNonItemTaxDetailSubNumber(66);
        nonItemTaxDetail.setNonItemTaxDiscountSubNumber(666);
        nonItemTaxDetail.setNonItemTaxType("TaxTaxGroup");
        nonItemTaxDetail.setNonItemTaxName("TaxTaxName");
        nonItemTaxDetail.setNonItemTaxAmountSign("TaxTaxAmountSign");
        Price taxAmt = new Price();
        taxAmt.setCurrencyCode(Currency.getInstance("USD"));
        taxAmt.setValue(new BigDecimal("6.1"));
        nonItemTaxDetail.setNonItemTaxAmt(taxAmt);
        nonItemTaxDetail.setNonItemTaxRate(new BigDecimal("6.2"));
        assertThat(result, is(nonItemTaxDetail));
    }

    /**
     * No data - convertToItemDiscount().
     */
    @Test
    public void testConvertToItemDiscountNodata() {

        // Prepare test.

        // Call target.
        ItemDiscount result = converter.convertToItemDiscount(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToItemDiscount().
     */
    @Test
    public void testConvertToItemDiscountExistdata() {

        // Prepare test.

        // Call target.
        ItemDiscount result = converter.convertToItemDiscount(inquiryEntity);

        // Confirm result.
        ItemDiscount itemDiscount = new ItemDiscount();
        itemDiscount.setItemDiscountDetailSubNumber(55);
        itemDiscount.setItemDiscountSubNumber(555);
        itemDiscount.setItemPromotionType("DiscountPromotionType");
        itemDiscount.setItemPromotionNumber("DiscountPromotionNo");
        itemDiscount.setItemStoreDiscountType("DiscountStoreDiscountType");
        itemDiscount.setItemQuantityCode("DiscountQuantityCode");
        itemDiscount.setItemDiscountQty(5555);
        Price taxExclude = new Price();
        taxExclude.setCurrencyCode(Currency.getInstance("JPY"));
        taxExclude.setValue(new BigDecimal("5.1"));
        itemDiscount.setItemDiscountAmtTaxExcluded(taxExclude);
        Price taxInclude = new Price();
        taxInclude.setCurrencyCode(Currency.getInstance("CAD"));
        taxInclude.setValue(new BigDecimal("5.2"));
        itemDiscount.setItemDiscountAmtTaxIncluded(taxInclude);
        assertThat(result, is(itemDiscount));
    }

    /**
     * No data - convertToItemTaxDetail().
     */
    @Test
    public void testConvertToItemTaxDetailNodata() {

        // Prepare test.

        // Call target.
        ItemTaxDetail result = converter.convertToItemTaxDetail(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToItemTaxDetail().
     */
    @Test
    public void testConvertToItemTaxDetailExistdata() {

        // Prepare test.

        // Call target.
        ItemTaxDetail result = converter.convertToItemTaxDetail(inquiryEntity);

        // Confirm result.
        ItemTaxDetail itemTaxDetail = new ItemTaxDetail();
        itemTaxDetail.setItemTaxDetailSubNumber(66);
        itemTaxDetail.setItemTaxSubNumber(666);
        itemTaxDetail.setItemTaxType("TaxTaxGroup");
        itemTaxDetail.setItemTaxName("TaxTaxName");
        itemTaxDetail.setItemTaxAmountSign("TaxTaxAmountSign");
        Price amount = new Price();
        amount.setCurrencyCode(Currency.getInstance("USD"));
        amount.setValue(new BigDecimal("6.1"));
        itemTaxDetail.setItemTaxAmt(amount);
        itemTaxDetail.setItemTaxRate(new BigDecimal("6.2"));
        assertThat(result, is(itemTaxDetail));
    }

    /**
     * No data - convertToTransactionTender().
     */
    @Test
    public void testConvertToTransactionTenderNodata() {

        // Prepare test.

        // Call target.
        SalesTransactionTender result = converter.convertToTransactionTender(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToTransactionTender().
     */
    @Test
    public void testConvertToTransactionTenderExistdata() {

        // Prepare test.

        // Call target.
        SalesTransactionTender result = converter.convertToTransactionTender(inquiryEntity);

        // Confirm result.
        SalesTransactionTender tender = new SalesTransactionTender();
        tender.setTenderSubNumber(77);
        tender.setTenderGroupId("TenderTenderGroup");
        tender.setTenderId("TenderTenderId");
        tender.setPaymentSign("TenderPaymentSign");
        Price amount = new Price();
        amount.setCurrencyCode(Currency.getInstance("BRL"));
        amount.setValue(new BigDecimal("7.1"));
        tender.setTaxIncludedPaymentAmount(amount);
        assertThat(result, is(tender));
    }

    /**
     * No data - convertToTenderInfo().
     */
    @Test
    public void testConvertToTenderInfoNodata() {

        // Prepare test.

        // Call target.
        TenderInformation result = converter.convertToTenderInfo(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToTenderInfo().
     */
    @Test
    public void testConvertToTenderInfoExistdata() {

        // Prepare test.

        // Call target.
        TenderInformation result = converter.convertToTenderInfo(inquiryEntity);

        // Confirm result.
        TenderInformation tenderInfo = new TenderInformation();
        Price amount = new Price();
        amount.setCurrencyCode(Currency.getInstance("EUR"));
        amount.setValue(new BigDecimal("8.1"));
        tenderInfo.setDiscountAmount(amount);
        tenderInfo.setDiscountRate(new BigDecimal("8.2"));
        tenderInfo.setDiscountCodeIdCorporateId("TenderInfoDiscountCodeIdCorporateId");
        tenderInfo.setCouponType("TenderInfoCouponType");
        Price setting = new Price();
        setting.setCurrencyCode(Currency.getInstance("GBP"));
        setting.setValue(new BigDecimal("8.3"));
        tenderInfo.setCouponDiscountAmountSetting(setting);
        Price threshold = new Price();
        threshold.setCurrencyCode(Currency.getInstance("KRW"));
        threshold.setValue(new BigDecimal("8.4"));
        tenderInfo.setCouponMinUsageAmountThreshold(threshold);
        tenderInfo.setCouponUserId("TenderInfoCouponUserId");
        tenderInfo.setCardNo("TenderInfoCardNo");
        tenderInfo.setCreditApprovalCode("TenderInfoCreditApprovalCode");
        tenderInfo.setCreditProcessingSerialNumber("TenderInfoCreditProcessingSerialNumber");
        tenderInfo.setCreditPaymentType("TenderInfoCreditPaymentType");
        tenderInfo.setCreditPaymentCount(888);
        tenderInfo.setCreditAffiliatedStoreNumber("TenderInfoCreditAffiliatedStoreNumber");
        assertThat(result, is(tenderInfo));
    }

    /**
     * No data - convertToTaxDetail().
     */
    @Test
    public void testConvertToTaxDetailNodata() {

        // Prepare test.

        // Call target.
        SalesTransactionTaxDetail result = converter.convertToTaxDetail(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToTaxDetail().
     */
    @Test
    public void testConvertToTaxDetailExistdata() {

        // Prepare test.

        // Call target.
        SalesTransactionTaxDetail result = converter.convertToTaxDetail(inquiryEntity);

        // Confirm result.
        SalesTransactionTaxDetail taxDetail = new SalesTransactionTaxDetail();
        taxDetail.setTaxSubNumber(666);
        taxDetail.setTaxGroup("TaxTaxGroup");
        taxDetail.setTaxAmountSign("TaxTaxAmountSign");
        Price amount = new Price();
        amount.setCurrencyCode(Currency.getInstance("USD"));
        amount.setValue(new BigDecimal("6.1"));
        taxDetail.setTaxAmount(amount);
        taxDetail.setTaxRate(new BigDecimal("6.2"));
        assertThat(result, is(taxDetail));
    }

    /**
     * No data - convertToTransactionTotal().
     */
    @Test
    public void testConvertToTransactionTotalNodata() {

        // Prepare test.

        // Call target.
        SalesTransactionTotal result = converter.convertToTransactionTotal(null);

        // Confirm result.
        assertThat(result, nullValue());
    }

    /**
     * Exist data - convertToTransactionTotal().
     */
    @Test
    public void testConvertToTransactionTotalExistdata() {

        // Prepare test.

        // Call target.
        SalesTransactionTotal result = converter.convertToTransactionTotal(inquiryEntity);

        // Confirm result.
        SalesTransactionTotal total = new SalesTransactionTotal();
        total.setTotalAmountSubNumber(99);
        total.setTotalType("TotalTotalType");
        Price taxExclude = new Price();
        taxExclude.setCurrencyCode(Currency.getInstance("CHF"));
        taxExclude.setValue(new BigDecimal("9.1"));
        total.setTotalAmountTaxExcluded(taxExclude);
        Price taxInclude = new Price();
        taxInclude.setCurrencyCode(Currency.getInstance("CLP"));
        taxInclude.setValue(new BigDecimal("9.2"));
        total.setTotalAmountTaxIncluded(taxInclude);
        total.setConsumptionTaxRate(new BigDecimal("93.3"));
        total.setSalesTransactionInformation1("TotalSalesTransactionInformation1");
        total.setSalesTransactionInformation2("TotalSalesTransactionInformation2");
        total.setSalesTransactionInformation3("TotalSalesTransactionInformation3");
        assertThat(result, is(total));
    }

}
