/**
 * TransactionInquiryHelperTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.helper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.common.SalesCommonTransactionInquiryApplication;
import com.fastretailing.dcp.sales.common.dto.TransactionImportData;
import com.fastretailing.dcp.sales.common.entity.optional.TransactionInquiryOptional;
import com.fastretailing.dcp.sales.common.repository.optional.TransactionInquiryOptionalMapper;
import com.fastretailing.dcp.storecommon.util.DateUtility;

/**
 * Unit test class of Integrated order ID inquiry pattern designated service helper.
 * 
 * @param <E> List of Elements.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesCommonTransactionInquiryApplication.class)
@AutoConfigureMockMvc
public final class TransactionInquiryHelperTest<E> {

    /** Transaction inquiry sales transaction header mapper. */
    @MockBean
    private TransactionInquiryOptionalMapper mapper;

    /** Test target. */
    @Autowired
    private TransactionInquiryHelper transactionInquiryHelper;

    /**
     * No data - createTransactionImportData().
     */
    @Test
    public void testCreateTransactionImportData1NoData() throws Exception {
        // Call target.
        TransactionImportData result = transactionInquiryHelper
                .createTransactionImportData("INTEGRATED_ORDER_ID", 1, "SALES_TRANSACTION_ID");

        // Confirm result.
        assertThat(result, is(new TransactionImportData()));
    }

    /**
     * Exist data - createTransactionImportData().
     */
    @Test
    public void testCreateTransactionImportData1ExistData() throws Exception {
        // Prepare test.
        doReturn(makeInquiryEntityList()).when(mapper).selectByTransactionId(anyString(), anyInt(),
                anyString());

        // Call target.
        TransactionImportData result = transactionInquiryHelper
                .createTransactionImportData("INTEGRATED_ORDER_ID", 1, "SALES_TRANSACTION_ID");

        // Confirm result.
        assertThat(result.getTransactionList().size(), is(1));
        assertThat(result.getTransactionList().get(0).getTransactionItemDetailList().size(), is(1));
        assertThat(result.getTransactionList()
                .get(0)
                .getTransactionItemDetailList()
                .get(0)
                .getNonItemDetailListByItem()
                .size(), is(1));
        assertThat(result.getTransactionList()
                .get(0)
                .getTransactionItemDetailList()
                .get(0)
                .getNonItemDetailListByItem()
                .get(0)
                .getNonItemInfo(), notNullValue());
        assertThat(result.getTransactionList()
                .get(0)
                .getTransactionItemDetailList()
                .get(0)
                .getNonItemDetailListByItem()
                .get(0)
                .getNonItemDiscountDetailList()
                .size(), is(2));
        assertThat(result.getTransactionList()
                .get(0)
                .getTransactionItemDetailList()
                .get(0)
                .getNonItemDetailListByItem()
                .get(0)
                .getNonItemTaxDetailList()
                .size(), is(1));

        assertThat(result.getTransactionList()
                .get(0)
                .getTransactionItemDetailList()
                .get(0)
                .getItemDiscountList()
                .size(), is(1));
        assertThat(result.getTransactionList()
                .get(0)
                .getTransactionItemDetailList()
                .get(0)
                .getItemTaxDetailList()
                .size(), is(1));

        assertThat(result.getTransactionList().get(0).getNonItemDetailList().size(), is(1));
        assertThat(
                result.getTransactionList().get(0).getNonItemDetailList().get(0).getNonItemInfo(),
                notNullValue());
        assertThat(result.getTransactionList()
                .get(0)
                .getNonItemDetailList()
                .get(0)
                .getNonItemDiscountDetailList()
                .size(), is(2));
        assertThat(result.getTransactionList()
                .get(0)
                .getNonItemDetailList()
                .get(0)
                .getNonItemTaxDetailList()
                .size(), is(1));

        assertThat(result.getTransactionList().get(0).getSalesTransactionTenderList().size(),
                is(1));
        assertTrue(!Objects.isNull(result.getTransactionList()
                .get(0)
                .getSalesTransactionTenderList()
                .get(0)
                .getTenderInfoList()));

        assertThat(result.getTransactionList().get(0).getSalesTransactionTaxDetailList().size(),
                is(1));
        assertThat(result.getTransactionList().get(0).getSalesTransactionTotalList().size(), is(1));
    }

    /**
     * Not exists non item detail list.
     */
    @Test
    public void testNotExistsNonItemDetailList() throws Exception {

        List<TransactionInquiryOptional> inquiryEntityList = makeInquiryEntityList();
        inquiryEntityList.get(1).setDetailSalesTransactionId("SalesTransactionId");

        // Prepare test.
        doReturn(inquiryEntityList).when(mapper)
                .selectByIntegratedOrderIdOrderSubNumber(anyString(), anyInt());

        // Call target.
        TransactionImportData result =
                transactionInquiryHelper.createTransactionImportData("INTEGRATED_ORDER_ID", 1);

        assertThat(result.getTransactionList().get(0).getNonItemDetailList().size(), is(0));
    }

    /**
     * Exists non item detail list.
     */
    @Test
    public void testExistsNonItemDetailList() throws Exception {

        List<TransactionInquiryOptional> inquiryEntityList = makeInquiryEntityList();

        // Prepare test.
        doReturn(inquiryEntityList).when(mapper)
                .selectByIntegratedOrderIdOrderSubNumber(anyString(), anyInt());

        // Call target.
        TransactionImportData result =
                transactionInquiryHelper.createTransactionImportData("INTEGRATED_ORDER_ID", 1);

        assertThat(result.getTransactionList().get(0).getNonItemDetailList().size(), is(1));
    }

    // ----------------------------------------------------
    // Utility
    // ----------------------------------------------------
    /**
     * Make inquiry entity list.
     */
    private List<TransactionInquiryOptional> makeInquiryEntityList() {
        List<TransactionInquiryOptional> inquiryEntityList = new ArrayList<>();
        inquiryEntityList.add(makeInquiryEntity("ITEM"));
        inquiryEntityList.add(makeInquiryEntity("NMITEM"));
        return inquiryEntityList;
    }

    /**
     * Make inquiry entity list.
     */
    private TransactionInquiryOptional makeInquiryEntity(String productClass) {

        // Transaction inquiry entity.
        // Transaction inquiry order information.
        TransactionInquiryOptional inquiryEntity = new TransactionInquiryOptional();
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
        inquiryEntity.setHeaderOrderSubNumber(0);
        inquiryEntity.setHeaderSalesTransactionId("HeaderSalesTransactionId");
        inquiryEntity.setHeaderIntegratedOrderId("HeaderIntegratedOrderId");
        inquiryEntity.setHeaderSalesTransactionSubNumber(0);
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
        inquiryEntity.setDetailOrderSubNumber(0);
        inquiryEntity.setDetailSalesTransactionId("HeaderSalesTransactionId");
        inquiryEntity.setDetailDetailSubNumber(0);
        inquiryEntity.setDetailItemDetailSubNumber(0);
        inquiryEntity.setDetailSalesTransactionType("DetailSalesTransactionType");
        inquiryEntity.setDetailSystemBrandCode("DetailSystemBrandCode");
        inquiryEntity.setDetailSystemBusinessCode("DetailSystemBusinessCode");
        inquiryEntity.setDetailSystemCountryCode("DetailSystemCountryCode");
        inquiryEntity.setDetailL2ItemCode("DetailL2ItemCode");
        inquiryEntity.setDetailDisplayL2ItemCode("DetailDisplayL2ItemCode");
        inquiryEntity.setDetailL2ProductName("DetailL2ProductName");
        inquiryEntity.setDetailL3ItemCode("DetailL3ItemCode");
        inquiryEntity.setDetailL3PosProductName("DetailL3PosProductName");
        inquiryEntity.setDetailProductClassification(productClass);
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
        inquiryEntity.setDetailInfoOrderSubNumber(0);
        inquiryEntity.setDetailInfoSalesTransactionId("HeaderSalesTransactionId");
        inquiryEntity.setDetailInfoDetailSubNumber(0);
        inquiryEntity.setDetailInfoItemDetailSubNumber(0);
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
        inquiryEntity.setDiscountOrderSubNumber(0);
        inquiryEntity.setDiscountSalesTransactionId("HeaderSalesTransactionId");
        inquiryEntity.setDiscountDetailSubNumber(0);
        inquiryEntity.setDiscountPromotionType("DiscountPromotionType");
        inquiryEntity.setDiscountPromotionNo("DiscountPromotionNo");
        inquiryEntity.setDiscountStoreDiscountType("DiscountStoreDiscountType");
        inquiryEntity.setDiscountItemDiscountSubNumber(0);
        inquiryEntity.setDiscountQuantityCode("DiscountQuantityCode");
        inquiryEntity.setDiscountDiscountQuantity(5555);
        inquiryEntity.setDiscountDiscountAmountTaxExcludedCurrencyCode("JPY");
        inquiryEntity.setDiscountDiscountAmountTaxExcluded(new BigDecimal("5.1"));
        inquiryEntity.setDiscountDiscountAmountTaxIncludedCurrencyCode("CAD");
        inquiryEntity.setDiscountDiscountAmountTaxIncluded(new BigDecimal("5.2"));

        // Transaction inquiry sales transaction tax.
        inquiryEntity.setTaxOrderSubNumber(0);
        inquiryEntity.setTaxSalesTransactionId("HeaderSalesTransactionId");
        inquiryEntity.setTaxDetailSubNumber(0);
        inquiryEntity.setTaxTaxGroup("TaxTaxGroup");
        inquiryEntity.setTaxTaxName("TaxTaxName");
        inquiryEntity.setTaxTaxSubNumber(1);
        inquiryEntity.setTaxTaxAmountSign("TaxTaxAmountSign");
        inquiryEntity.setTaxTaxAmountCurrencyCode("USD");
        inquiryEntity.setTaxTaxAmountValue(new BigDecimal("6.1"));
        inquiryEntity.setTaxTaxRate(new BigDecimal("6.2"));

        // Transaction inquiry sales transaction tender.
        inquiryEntity.setTenderOrderSubNumber(0);
        inquiryEntity.setTenderSalesTransactionId("HeaderSalesTransactionId");
        inquiryEntity.setTenderTenderGroup("TenderGroup");
        inquiryEntity.setTenderTenderId("TenderId");
        inquiryEntity.setTenderTenderSubNumber(0);
        inquiryEntity.setTenderPaymentSign("TenderPaymentSign");
        inquiryEntity.setTenderTaxIncludedPaymentAmountCurrencyCode("BRL");
        inquiryEntity.setTenderTaxIncludedPaymentAmountValue(new BigDecimal("7.1"));

        // Transaction inquiry sales transaction tender information.
        inquiryEntity.setTenderInfoOrderSubNumber(0);
        inquiryEntity.setTenderInfoSalesTransactionId("HeaderSalesTransactionId");
        inquiryEntity.setTenderInfoTenderGroup("TenderGroup");
        inquiryEntity.setTenderInfoTenderId("TenderId");
        inquiryEntity.setTenderInfoTenderSubNumber(0);
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
        inquiryEntity.setTotalOrderSubNumber(0);
        inquiryEntity.setTotalSalesTransactionId("HeaderSalesTransactionId");
        inquiryEntity.setTotalTotalType("TotalTotalType");
        inquiryEntity.setTotalTotalAmountSubNumber(0);
        inquiryEntity.setTotalTotalAmountTaxExcludedCurrencyCode("CHF");
        inquiryEntity.setTotalTotalAmountTaxExcludedValue(new BigDecimal("9.1"));
        inquiryEntity.setTotalTotalAmountTaxIncludedCurrencyCode("CLP");
        inquiryEntity.setTotalTotalAmountTaxIncludedValue(new BigDecimal("9.2"));
        inquiryEntity.setTotalTaxRate(new BigDecimal("93"));
        inquiryEntity.setTotalSalesTransactionInformation1("TotalSalesTransactionInformation1");
        inquiryEntity.setTotalSalesTransactionInformation2("TotalSalesTransactionInformation2");
        inquiryEntity.setTotalSalesTransactionInformation3("TotalSalesTransactionInformation3");

        return inquiryEntity;
    }
}
