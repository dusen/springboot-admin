/**
 * @(#)TransactionInquiryOptionalMapperTest.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.common.entity.optional.TransactionInquiryOptional;
import com.fastretailing.dcp.storecommon.util.DateUtility;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DirtiesContextTestExecutionListener.class,
                DependencyInjectionTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_sales_6.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales_6.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class TransactionInquiryOptionalMapperTest {

    /* Constant value. */
    private static final String INTEGRATED_ORDER_ID = "1721123261801221000-0000101";
    private static final Integer ORDER_SUB_NUMBER = 1;
    private static final String SALES_TRANSACTION_ID = "1721123261801221000-0000101-01";

    /** Transaction inquiry optional mapper. */
    @Autowired
    private TransactionInquiryOptionalMapper optionalMapper;

    /**
     * Target method: TransactionInquiryOptionalMapper#selectByIntegratedOrderIdInquiryPattern.<BR>
     * Condition: select condition<BR>
     * Expected result: no corresponding data.
     *
     */
    @Test
    @DatabaseSetup("TransactionInquiryOptionalMapperTest.xml")
    public void testSelectByIntegratedOrderIdInquiryPatternNoData() {
        // Call test target.
        List<TransactionInquiryOptional> result = optionalMapper
                .selectByIntegratedOrderIdInquiryPattern("INTEGRATED_ORDER_ID", "BOTH");

        // Confirm test result.
        assertThat(result, is(new ArrayList<>()));
    }

    /**
     * Target method: TransactionInquiryOptionalMapper#selectByIntegratedOrderIdOrderSubNumber.<BR>
     * Condition: select condition<BR>
     * Expected result: no corresponding data.
     *
     */
    @Test
    @DatabaseSetup("TransactionInquiryOptionalMapperTest.xml")
    public void testSelectByIntegratedOrderIdOrderSubNumberNoData() {
        // Call test target.
        List<TransactionInquiryOptional> result =
                optionalMapper.selectByIntegratedOrderIdOrderSubNumber(INTEGRATED_ORDER_ID, 5);

        // Confirm test result.
        assertThat(result, is(new ArrayList<>()));
    }

    /**
     * Target method: TransactionInquiryOptionalMapper#selectByTransactionId.<BR>
     * Condition: select condition<BR>
     * Expected result: no corresponding data.
     *
     */
    @Test
    @DatabaseSetup("TransactionInquiryOptionalMapperTest.xml")
    public void testSelectByTransactionIdNoData() {
        // Call test target.
        List<TransactionInquiryOptional> result =
                optionalMapper.selectByTransactionId(INTEGRATED_ORDER_ID, 5, SALES_TRANSACTION_ID);

        // Confirm test result.
        assertThat(result, is(new ArrayList<>()));
    }

    /**
     * Target method: TransactionInquiryOptionalMapper#selectByTransactionId.<BR>
     * Condition: select condition<BR>
     * Expected result: exist corresponding data.
     *
     */
    @Test
    @DatabaseSetup("TransactionInquiryOptionalMapperTest.xml")
    public void testSelectByTransactionIdExistData() {
        // Call test target.
        List<TransactionInquiryOptional> result = optionalMapper
                .selectByTransactionId(INTEGRATED_ORDER_ID, ORDER_SUB_NUMBER, SALES_TRANSACTION_ID);

        // Confirm test result.
        assertThat(result.get(0).getIntegratedOrderId(), is("1721123261801221000-0000101"));
        assertThat(result.get(0).getOrderBarcodeNumber(), is("11232618012210000000101"));
        assertThat(result.get(0).getStoreCode(), is("112326"));
        assertThat(result.get(0).getSystemBrandCode(), is("UQ"));
        assertThat(result.get(0).getSystemBusinessCode(), is("UQCD"));
        assertThat(result.get(0).getSystemCountryCode(), is("CA"));
        assertThat(result.get(0).getChannelCode(), is("EC"));
        assertThat(result.get(0).getCustomerId(), is("customer_id_01"));
        assertThat(result.get(0).getOrderConfirmationBusinessDate(), nullValue());
        assertThat(result.get(0).getOrderConfirmationDateTime(),
                is(DateUtility
                        .parseZonedDateTime("2018-01-22T12:00:00+09:00",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime()));
        assertThat(result.get(0).getDataAlterationSalesLinkageType(), nullValue());
        assertThat(result.get(0).getDataAlterationBackboneLinkageType(), nullValue());
        assertThat(result.get(0).isDataAlterationFlag(), is(Boolean.FALSE));
        assertThat(result.get(0).getDataAlterationUserId(), nullValue());
        assertThat(result.get(0).getHeaderOrderSubNumber(), is(1));
        assertThat(result.get(0).getHeaderSalesTransactionId(),
                is("1721123261801221000-0000101-01"));
        assertThat(result.get(0).getHeaderIntegratedOrderId(), is("1721123261801221000-0000101"));
        assertThat(result.get(0).getHeaderSalesTransactionSubNumber(), is(1));
        assertThat(result.get(0).getHeaderStoreCode(), is("112326"));
        assertThat(result.get(0).getHeaderDataCreationDateTime(),
                is(DateUtility
                        .parseZonedDateTime("2018-01-22T12:00:00+09:00",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime()));
        assertThat(result.get(0).getHeaderDataCreationBusinessDate(), is("20180515"));
        assertThat(result.get(0).getHeaderCashRegisterNo(), is(0));
        assertThat(result.get(0).getHeaderReceiptNo(), is("0"));
        assertThat(result.get(0).getHeaderSalesLinkageType(), is(0));
        assertThat(result.get(0).getHeaderSalesTransactionType(), is("SALE"));
        assertThat(result.get(0).getHeaderReturnType(), nullValue());
        assertThat(result.get(0).getHeaderSystemBrandCode(), is("UQ"));
        assertThat(result.get(0).getHeaderSystemBusinessCode(), is("UQCD"));
        assertThat(result.get(0).getHeaderSystemCountryCode(), is("CA"));
        assertThat(result.get(0).getHeaderChannelCode(), is("EC"));
        assertThat(result.get(0).getHeaderOrderStatus(), is("SHIPMENT_CONFIRMED"));
        assertThat(result.get(0).getHeaderOrderSubstatus(), is("NONE"));
        assertThat(result.get(0).getHeaderOrderStatusUpdateDate(), nullValue());
        assertThat(result.get(0).getHeaderOrderStatusLastUpdateDateTime(),
                is(DateUtility
                        .parseZonedDateTime("2018-01-22T12:00:00+09:00",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime()));
        assertThat(result.get(0).getHeaderOrderCancelledDateTime(), nullValue());
        assertThat(result.get(0).getHeaderBookingStoreCode(), nullValue());
        assertThat(result.get(0).getHeaderBookingStoreSystemBrandCode(), nullValue());
        assertThat(result.get(0).getHeaderBookingStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(0).getHeaderBookingStoreSystemCountryCode(), nullValue());
        assertThat(result.get(0).getHeaderPaymentStatus(), nullValue());
        assertThat(result.get(0).getHeaderPaymentStoreCode(), nullValue());
        assertThat(result.get(0).getHeaderPaymentStoreSystemBrandCode(), nullValue());
        assertThat(result.get(0).getHeaderPaymentStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(0).getHeaderPaymentStoreSystemCountryCode(), nullValue());
        assertThat(result.get(0).getHeaderTransferOutStatus(), nullValue());
        assertThat(result.get(0).getHeaderShipmentStatus(), nullValue());
        assertThat(result.get(0).getHeaderShipmentStoreCode(), nullValue());
        assertThat(result.get(0).getHeaderShipmentStoreSystemBrandCode(), nullValue());
        assertThat(result.get(0).getHeaderShipmentStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(0).getHeaderShipmentStoreSystemCountryCode(), nullValue());
        assertThat(result.get(0).getHeaderReceivingStatus(), nullValue());
        assertThat(result.get(0).getHeaderReceiptStoreCode(), nullValue());
        assertThat(result.get(0).getHeaderReceiptStoreSystemBrandCode(), nullValue());
        assertThat(result.get(0).getHeaderReceiptStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(0).getHeaderReceiptStoreSystemCountryCode(), nullValue());
        assertThat(result.get(0).getHeaderCustomerId(), is("customer_id_01"));
        assertThat(result.get(0).getHeaderCustomerType(), nullValue());
        assertThat(result.get(0).getHeaderOrderNumberForStorePayment(), nullValue());
        assertThat(result.get(0).getHeaderAdvanceReceivedStoreCode(), nullValue());
        assertThat(result.get(0).getHeaderAdvanceReceivedStoreSystemBrandCode(), nullValue());
        assertThat(result.get(0).getHeaderAdvanceReceivedStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(0).getHeaderAdvanceReceivedStoreSystemCountryCode(), nullValue());
        assertThat(result.get(0).getHeaderOperatorCode(), is("1"));
        assertThat(result.get(0).getHeaderOriginalTransactionId(), nullValue());
        assertThat(result.get(0).getHeaderOriginalCashRegisterNo(), nullValue());
        assertThat(result.get(0).getHeaderOriginalReceiptNo(), nullValue());
        assertThat(result.get(0).getHeaderDepositCurrencyCode(), nullValue());
        assertThat(result.get(0).getHeaderDepositValue(), nullValue());
        assertThat(result.get(0).getHeaderChangeCurrencyCode(), nullValue());
        assertThat(result.get(0).getHeaderChangeValue(), nullValue());
        assertThat(result.get(0).getHeaderReceiptNoForCreditCardCancellation(), nullValue());
        assertThat(result.get(0).getHeaderReceiptNoForCreditCard(), nullValue());
        assertThat(result.get(0).isHeaderReceiptIssuedFlag(), is(Boolean.FALSE));
        assertThat(result.get(0).isHeaderEReceiptTargetFlag(), is(Boolean.FALSE));
        assertThat(result.get(0).getHeaderProcessingCompanyCode(), nullValue());
        assertThat(result.get(0).isHeaderEmployeeSaleFlag(), is(Boolean.FALSE));
        assertThat(result.get(0).isHeaderConsistencySalesFlag(), is(Boolean.FALSE));
        assertThat(result.get(0).getHeaderCorporateId(), nullValue());
        assertThat(result.get(0).isHeaderSalesTransactionDiscountFlag(), is(Boolean.FALSE));
        assertThat(result.get(0).getHeaderSalesTransactionDiscountType(), nullValue());
        assertThat(result.get(0).getHeaderSalesTransactionDiscountAmountRateCurrencyCode(),
                nullValue());
        assertThat(result.get(0).getHeaderSalesTransactionDiscountAmountRate(), nullValue());
        assertThat(result.get(0).getHeaderTokenCode(), nullValue());
        assertThat(result.get(0).isHeaderReturnCompleteFlag(), is(Boolean.FALSE));
        assertThat(result.get(0).isHeaderCancelledFlag(), is(Boolean.FALSE));
        assertThat(result.get(0).getDetailOrderSubNumber(), is(1));
        assertThat(result.get(0).getDetailSalesTransactionId(),
                is("1721123261801221000-0000101-01"));
        assertThat(result.get(0).getDetailDetailSubNumber(), is(1));
        assertThat(result.get(0).getDetailItemDetailSubNumber(), is(1));
        assertThat(result.get(0).getDetailSalesTransactionType(), is("SALE"));
        assertThat(result.get(0).getDetailSystemBrandCode(), is("UQ"));
        assertThat(result.get(0).getDetailSystemBusinessCode(), is("UQCD"));
        assertThat(result.get(0).getDetailSystemCountryCode(), is("CA"));
        assertThat(result.get(0).getDetailL2ItemCode(), is("12345678"));
        assertThat(result.get(0).getDetailDisplayL2ItemCode(), is("12345678901234567890"));
        assertThat(result.get(0).getDetailL2ProductName(), is("L2 W's JWAPJ high rise"));
        assertThat(result.get(0).getDetailL3ItemCode(), is("2000111642326"));
        assertThat(result.get(0).getDetailL3PosProductName(), is("W's JWAPJ high rise"));
        assertThat(result.get(0).getDetailProductClassification(), is("ITEM"));
        assertThat(result.get(0).getDetailNonMdType(), nullValue());
        assertThat(result.get(0).getDetailNonMdCode(), nullValue());
        assertThat(result.get(0).getDetailServiceCode(), nullValue());
        assertThat(result.get(0).getDetailEpcCode(), nullValue());
        assertThat(result.get(0).getDetailGDepartmentCode(), is("34"));
        assertThat(result.get(0).getDetailMajorCategoryCode(), nullValue());
        assertThat(result.get(0).getDetailQuantityCode(), is("P"));
        assertThat(result.get(0).getDetailDetailQuantity(), is(1));
        assertThat(result.get(0).getDetailItemCostCurrencyCode(), is("CAD"));
        assertThat(result.get(0).getDetailItemCostValue(), is(new BigDecimal("10.0000")));
        assertThat(result.get(0).getDetailInitialSellingPriceCurrencyCode(), is("CAD"));
        assertThat(result.get(0).getDetailInitialSellingPrice(), is(new BigDecimal("10.0000")));
        assertThat(result.get(0).getDetailBclassPriceCurrencyCode(), nullValue());
        assertThat(result.get(0).getDetailBclassPrice(), nullValue());
        assertThat(result.get(0).getDetailNewPriceCurrencyCode(), nullValue());
        assertThat(result.get(0).getDetailNewPrice(), nullValue());
        assertThat(result.get(0).getDetailRetailUnitPriceTaxExcludedCurrencyCode(), is("CAD"));
        assertThat(result.get(0).getDetailRetailUnitPriceTaxExcluded(),
                is(new BigDecimal("10.0000")));
        assertThat(result.get(0).getDetailRetailUnitPriceTaxIncludedCurrencyCode(), is("CAD"));
        assertThat(result.get(0).getDetailRetailUnitPriceTaxIncluded(),
                is(new BigDecimal("10.0000")));
        assertThat(result.get(0).getDetailSalesAmountTaxExcludedCurrencyCode(), is("CAD"));
        assertThat(result.get(0).getDetailSalesAmountTaxExcluded(), is(new BigDecimal("10.0000")));
        assertThat(result.get(0).getDetailSalesAmountTaxIncludedCurrencyCode(), is("CAD"));
        assertThat(result.get(0).getDetailSalesAmountTaxIncluded(), is(new BigDecimal("10.0000")));
        assertThat(result.get(0).getDetailCalculationUnavailableType(), nullValue());
        assertThat(result.get(0).getDetailOrderStatusUpdateDate(), nullValue());
        assertThat(result.get(0).getDetailOrderStatusLastUpdateDateTime(), nullValue());
        assertThat(result.get(0).getDetailOrderStatus(), nullValue());
        assertThat(result.get(0).getDetailOrderSubstatus(), nullValue());
        assertThat(result.get(0).getDetailBookingStoreCode(), nullValue());
        assertThat(result.get(0).getDetailBookingStoreSystemBrandCode(), nullValue());
        assertThat(result.get(0).getDetailBookingStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(0).getDetailBookingStoreSystemCountryCode(), nullValue());
        assertThat(result.get(0).getDetailShipmentStoreCode(), nullValue());
        assertThat(result.get(0).getDetailShipmentStoreSystemBrandCode(), nullValue());
        assertThat(result.get(0).getDetailShipmentStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(0).getDetailShipmentStoreSystemCountryCode(), nullValue());
        assertThat(result.get(0).getDetailReceiptStoreCode(), nullValue());
        assertThat(result.get(0).getDetailReceiptStoreSystemBrandCode(), nullValue());
        assertThat(result.get(0).getDetailReceiptStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(0).getDetailReceiptStoreSystemCountryCode(), nullValue());
        assertThat(result.get(0).getDetailContributionSalesRepresentative(), nullValue());
        assertThat(result.get(0).getDetailCustomerId(), nullValue());
        assertThat(result.get(0).getDetailBundlePurchaseApplicableQuantity(), nullValue());
        assertThat(result.get(0).getDetailBundlePurchaseApplicablePriceCurrencyCode(), nullValue());
        assertThat(result.get(0).getDetailBundlePurchaseApplicablePrice(), nullValue());
        assertThat(result.get(0).getDetailBundlePurchaseIndex(), nullValue());
        assertThat(result.get(0).getDetailLimitedAmountPromotionCount(), nullValue());
        assertThat(result.get(0).getDetailStoreItemDiscountType(), nullValue());
        assertThat(result.get(0).getDetailStoreItemDiscountCurrencyCode(), nullValue());
        assertThat(result.get(0).getDetailStoreItemDiscountSetting(), nullValue());
        assertThat(result.get(0).isDetailStoreBundleSaleFlag(), is(Boolean.FALSE));
        assertThat(result.get(0).getDetailStoreBundleSalePriceCurrencyCode(), nullValue());
        assertThat(result.get(0).getDetailStoreBundleSalePrice(), nullValue());
        assertThat(result.get(0).getDetailSetSalesDetailIndex(), nullValue());
        assertThat(result.get(0).getDetailTaxationType(), is("Y"));
        assertThat(result.get(0).getDetailTaxSystemType(), is("OUT"));
        assertThat(result.get(0).isDetailReturnCompleteFlag(), is(Boolean.FALSE));
        assertThat(result.get(0).getDetailInfoOrderSubNumber(), nullValue());
        assertThat(result.get(0).getDetailInfoSalesTransactionId(), nullValue());
        assertThat(result.get(0).getDetailInfoDetailSubNumber(), nullValue());
        assertThat(result.get(0).getDetailInfoItemDetailSubNumber(), nullValue());
        assertThat(result.get(0).getDetailInfoKeyCode(), nullValue());
        assertThat(result.get(0).getDetailInfoCodeValue1(), nullValue());
        assertThat(result.get(0).getDetailInfoCodeValue2(), nullValue());
        assertThat(result.get(0).getDetailInfoCodeValue3(), nullValue());
        assertThat(result.get(0).getDetailInfoCodeValue4(), nullValue());
        assertThat(result.get(0).getDetailInfoName1(), nullValue());
        assertThat(result.get(0).getDetailInfoName2(), nullValue());
        assertThat(result.get(0).getDetailInfoName3(), nullValue());
        assertThat(result.get(0).getDetailInfoName4(), nullValue());
        assertThat(result.get(0).getDiscountOrderSubNumber(), nullValue());
        assertThat(result.get(0).getDiscountSalesTransactionId(), nullValue());
        assertThat(result.get(0).getDiscountDetailSubNumber(), nullValue());
        assertThat(result.get(0).getDiscountPromotionType(), nullValue());
        assertThat(result.get(0).getDiscountPromotionNo(), nullValue());
        assertThat(result.get(0).getDiscountStoreDiscountType(), nullValue());
        assertThat(result.get(0).getDiscountItemDiscountSubNumber(), nullValue());
        assertThat(result.get(0).getDiscountQuantityCode(), nullValue());
        assertThat(result.get(0).getDiscountDiscountQuantity(), nullValue());
        assertThat(result.get(0).getDiscountDiscountAmountTaxExcludedCurrencyCode(), nullValue());
        assertThat(result.get(0).getDiscountDiscountAmountTaxExcluded(), nullValue());
        assertThat(result.get(0).getDiscountDiscountAmountTaxIncludedCurrencyCode(), nullValue());
        assertThat(result.get(0).getDiscountDiscountAmountTaxIncluded(), nullValue());
        assertThat(result.get(0).getTaxOrderSubNumber(), is(1));
        assertThat(result.get(0).getTaxSalesTransactionId(), is("1721123261801221000-0000101-01"));
        assertThat(result.get(0).getTaxDetailSubNumber(), is(0));
        assertThat(result.get(0).getTaxTaxGroup(), is("PST"));
        assertThat(result.get(0).getTaxTaxName(), nullValue());
        assertThat(result.get(0).getTaxTaxSubNumber(), is(1));
        assertThat(result.get(0).getTaxTaxAmountSign(), is("P"));
        assertThat(result.get(0).getTaxTaxAmountCurrencyCode(), is("CAD"));
        assertThat(result.get(0).getTaxTaxAmountValue(), is(new BigDecimal("0.8000")));
        assertThat(result.get(0).getTaxTaxRate(), is(new BigDecimal("1.0800")));
        assertThat(result.get(0).getTenderOrderSubNumber(), is(1));
        assertThat(result.get(0).getTenderSalesTransactionId(),
                is("1721123261801221000-0000101-01"));
        assertThat(result.get(0).getTenderTenderGroup(), is("CREDIT"));
        assertThat(result.get(0).getTenderTenderId(), is("VISA"));
        assertThat(result.get(0).getTenderTenderSubNumber(), is(1));
        assertThat(result.get(0).getTenderPaymentSign(), is("P"));
        assertThat(result.get(0).getTenderTaxIncludedPaymentAmountCurrencyCode(), is("CAD"));
        assertThat(result.get(0).getTenderTaxIncludedPaymentAmountValue(),
                is(new BigDecimal("10.8000")));
        assertThat(result.get(0).getTenderInfoOrderSubNumber(), is(1));
        assertThat(result.get(0).getTenderInfoSalesTransactionId(),
                is("1721123261801221000-0000101-01"));
        assertThat(result.get(0).getTenderInfoTenderGroup(), is("CREDIT"));
        assertThat(result.get(0).getTenderInfoTenderId(), is("VISA"));
        assertThat(result.get(0).getTenderInfoTenderSubNumber(), is(1));
        assertThat(result.get(0).getTenderInfoDiscountValueCurrencyCode(), nullValue());
        assertThat(result.get(0).getTenderInfoDiscountValue(), nullValue());
        assertThat(result.get(0).getTenderInfoDiscountRate(), nullValue());
        assertThat(result.get(0).getTenderInfoDiscountCodeIdCorporateId(), nullValue());
        assertThat(result.get(0).getTenderInfoCouponType(), nullValue());
        assertThat(result.get(0).getTenderInfoCouponDiscountAmountSettingCurrencyCode(),
                nullValue());
        assertThat(result.get(0).getTenderInfoCouponDiscountAmountSettingValue(), nullValue());
        assertThat(result.get(0).getTenderInfoCouponMinUsageAmountThresholdCurrencyCode(),
                nullValue());
        assertThat(result.get(0).getTenderInfoCouponMinUsageAmountThresholdValue(), nullValue());
        assertThat(result.get(0).getTenderInfoCouponUserId(), nullValue());
        assertThat(result.get(0).getTenderInfoCardNo(), is("123456789xx1"));
        assertThat(result.get(0).getTenderInfoCreditApprovalCode(), is("123456789xx2"));
        assertThat(result.get(0).getTenderInfoCreditProcessingSerialNumber(), is("123456789xx3"));
        assertThat(result.get(0).getTenderInfoCreditPaymentType(), is("10"));
        assertThat(result.get(0).getTenderInfoCreditPaymentCount(), is(1));
        assertThat(result.get(0).getTenderInfoCreditAffiliatedStoreNumber(), is("123456789xx4"));
        assertThat(result.get(0).getTotalOrderSubNumber(), is(1));
        assertThat(result.get(0).getTotalSalesTransactionId(),
                is("1721123261801221000-0000101-01"));
        assertThat(result.get(0).getTotalTotalType(), is("ITEM"));
        assertThat(result.get(0).getTotalTotalAmountSubNumber(), is(1));
        assertThat(result.get(0).getTotalTotalAmountTaxExcludedCurrencyCode(), is("CAD"));
        assertThat(result.get(0).getTotalTotalAmountTaxExcludedValue(),
                is(new BigDecimal("10.0000")));
        assertThat(result.get(0).getTotalTotalAmountTaxIncludedCurrencyCode(), is("CAD"));
        assertThat(result.get(0).getTotalTotalAmountTaxIncludedValue(),
                is(new BigDecimal("10.8000")));
        assertThat(result.get(0).getTotalTaxRate(), nullValue());
        assertThat(result.get(0).getTotalSalesTransactionInformation1(), nullValue());
        assertThat(result.get(0).getTotalSalesTransactionInformation2(), nullValue());
        assertThat(result.get(0).getTotalSalesTransactionInformation3(), nullValue());

        assertThat(result.get(1).getIntegratedOrderId(), is("1721123261801221000-0000101"));
        assertThat(result.get(1).getOrderBarcodeNumber(), is("11232618012210000000101"));
        assertThat(result.get(1).getStoreCode(), is("112326"));
        assertThat(result.get(1).getSystemBrandCode(), is("UQ"));
        assertThat(result.get(1).getSystemBusinessCode(), is("UQCD"));
        assertThat(result.get(1).getSystemCountryCode(), is("CA"));
        assertThat(result.get(1).getChannelCode(), is("EC"));
        assertThat(result.get(1).getCustomerId(), is("customer_id_01"));
        assertThat(result.get(1).getOrderConfirmationBusinessDate(), nullValue());
        assertThat(result.get(1).getOrderConfirmationDateTime(),
                is(DateUtility
                        .parseZonedDateTime("2018-01-22T12:00:00+09:00",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime()));
        assertThat(result.get(1).getDataAlterationSalesLinkageType(), nullValue());
        assertThat(result.get(1).getDataAlterationBackboneLinkageType(), nullValue());
        assertThat(result.get(1).isDataAlterationFlag(), is(Boolean.FALSE));
        assertThat(result.get(1).getDataAlterationUserId(), nullValue());
        assertThat(result.get(1).getHeaderOrderSubNumber(), is(1));
        assertThat(result.get(1).getHeaderSalesTransactionId(),
                is("1721123261801221000-0000101-01"));
        assertThat(result.get(1).getHeaderIntegratedOrderId(), is("1721123261801221000-0000101"));
        assertThat(result.get(1).getHeaderSalesTransactionSubNumber(), is(1));
        assertThat(result.get(1).getHeaderStoreCode(), is("112326"));
        assertThat(result.get(1).getHeaderDataCreationDateTime(),
                is(DateUtility
                        .parseZonedDateTime("2018-01-22T12:00:00+09:00",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime()));
        assertThat(result.get(1).getHeaderDataCreationBusinessDate(), is("20180515"));
        assertThat(result.get(1).getHeaderCashRegisterNo(), is(0));
        assertThat(result.get(1).getHeaderReceiptNo(), is("0"));
        assertThat(result.get(1).getHeaderSalesLinkageType(), is(0));
        assertThat(result.get(1).getHeaderSalesTransactionType(), is("SALE"));
        assertThat(result.get(1).getHeaderReturnType(), nullValue());
        assertThat(result.get(1).getHeaderSystemBrandCode(), is("UQ"));
        assertThat(result.get(1).getHeaderSystemBusinessCode(), is("UQCD"));
        assertThat(result.get(1).getHeaderSystemCountryCode(), is("CA"));
        assertThat(result.get(1).getHeaderChannelCode(), is("EC"));
        assertThat(result.get(1).getHeaderOrderStatus(), is("SHIPMENT_CONFIRMED"));
        assertThat(result.get(1).getHeaderOrderSubstatus(), is("NONE"));
        assertThat(result.get(1).getHeaderOrderStatusUpdateDate(), nullValue());
        assertThat(result.get(1).getHeaderOrderStatusLastUpdateDateTime(),
                is(DateUtility
                        .parseZonedDateTime("2018-01-22T12:00:00+09:00",
                                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                        .toOffsetDateTime()));
        assertThat(result.get(1).getHeaderOrderCancelledDateTime(), nullValue());
        assertThat(result.get(1).getHeaderBookingStoreCode(), nullValue());
        assertThat(result.get(1).getHeaderBookingStoreSystemBrandCode(), nullValue());
        assertThat(result.get(1).getHeaderBookingStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(1).getHeaderBookingStoreSystemCountryCode(), nullValue());
        assertThat(result.get(1).getHeaderPaymentStatus(), nullValue());
        assertThat(result.get(1).getHeaderPaymentStoreCode(), nullValue());
        assertThat(result.get(1).getHeaderPaymentStoreSystemBrandCode(), nullValue());
        assertThat(result.get(1).getHeaderPaymentStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(1).getHeaderPaymentStoreSystemCountryCode(), nullValue());
        assertThat(result.get(1).getHeaderTransferOutStatus(), nullValue());
        assertThat(result.get(1).getHeaderShipmentStatus(), nullValue());
        assertThat(result.get(1).getHeaderShipmentStoreCode(), nullValue());
        assertThat(result.get(1).getHeaderShipmentStoreSystemBrandCode(), nullValue());
        assertThat(result.get(1).getHeaderShipmentStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(1).getHeaderShipmentStoreSystemCountryCode(), nullValue());
        assertThat(result.get(1).getHeaderReceivingStatus(), nullValue());
        assertThat(result.get(1).getHeaderReceiptStoreCode(), nullValue());
        assertThat(result.get(1).getHeaderReceiptStoreSystemBrandCode(), nullValue());
        assertThat(result.get(1).getHeaderReceiptStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(1).getHeaderReceiptStoreSystemCountryCode(), nullValue());
        assertThat(result.get(1).getHeaderCustomerId(), is("customer_id_01"));
        assertThat(result.get(1).getHeaderCustomerType(), nullValue());
        assertThat(result.get(1).getHeaderOrderNumberForStorePayment(), nullValue());
        assertThat(result.get(1).getHeaderAdvanceReceivedStoreCode(), nullValue());
        assertThat(result.get(1).getHeaderAdvanceReceivedStoreSystemBrandCode(), nullValue());
        assertThat(result.get(1).getHeaderAdvanceReceivedStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(1).getHeaderAdvanceReceivedStoreSystemCountryCode(), nullValue());
        assertThat(result.get(1).getHeaderOperatorCode(), is("1"));
        assertThat(result.get(1).getHeaderOriginalTransactionId(), nullValue());
        assertThat(result.get(1).getHeaderOriginalCashRegisterNo(), nullValue());
        assertThat(result.get(1).getHeaderOriginalReceiptNo(), nullValue());
        assertThat(result.get(1).getHeaderDepositCurrencyCode(), nullValue());
        assertThat(result.get(1).getHeaderDepositValue(), nullValue());
        assertThat(result.get(1).getHeaderChangeCurrencyCode(), nullValue());
        assertThat(result.get(1).getHeaderChangeValue(), nullValue());
        assertThat(result.get(1).getHeaderReceiptNoForCreditCardCancellation(), nullValue());
        assertThat(result.get(1).getHeaderReceiptNoForCreditCard(), nullValue());
        assertThat(result.get(1).isHeaderReceiptIssuedFlag(), is(Boolean.FALSE));
        assertThat(result.get(1).isHeaderEReceiptTargetFlag(), is(Boolean.FALSE));
        assertThat(result.get(1).getHeaderProcessingCompanyCode(), nullValue());
        assertThat(result.get(1).isHeaderEmployeeSaleFlag(), is(Boolean.FALSE));
        assertThat(result.get(1).isHeaderConsistencySalesFlag(), is(Boolean.FALSE));
        assertThat(result.get(1).getHeaderCorporateId(), nullValue());
        assertThat(result.get(1).isHeaderSalesTransactionDiscountFlag(), is(Boolean.FALSE));
        assertThat(result.get(1).getHeaderSalesTransactionDiscountType(), nullValue());
        assertThat(result.get(1).getHeaderSalesTransactionDiscountAmountRateCurrencyCode(),
                nullValue());
        assertThat(result.get(1).getHeaderSalesTransactionDiscountAmountRate(), nullValue());
        assertThat(result.get(1).getHeaderTokenCode(), nullValue());
        assertThat(result.get(1).isHeaderReturnCompleteFlag(), is(Boolean.FALSE));
        assertThat(result.get(1).isHeaderCancelledFlag(), is(Boolean.FALSE));
        assertThat(result.get(1).getDetailOrderSubNumber(), is(1));
        assertThat(result.get(1).getDetailSalesTransactionId(),
                is("1721123261801221000-0000101-01"));
        assertThat(result.get(1).getDetailDetailSubNumber(), is(1));
        assertThat(result.get(1).getDetailItemDetailSubNumber(), is(1));
        assertThat(result.get(1).getDetailSalesTransactionType(), is("SALE"));
        assertThat(result.get(1).getDetailSystemBrandCode(), is("UQ"));
        assertThat(result.get(1).getDetailSystemBusinessCode(), is("UQCD"));
        assertThat(result.get(1).getDetailSystemCountryCode(), is("CA"));
        assertThat(result.get(1).getDetailL2ItemCode(), is("12345678"));
        assertThat(result.get(1).getDetailDisplayL2ItemCode(), is("12345678901234567890"));
        assertThat(result.get(1).getDetailL2ProductName(), is("L2 W's JWAPJ high rise"));
        assertThat(result.get(1).getDetailL3ItemCode(), is("2000111642326"));
        assertThat(result.get(1).getDetailL3PosProductName(), is("W's JWAPJ high rise"));
        assertThat(result.get(1).getDetailProductClassification(), is("ITEM"));
        assertThat(result.get(1).getDetailNonMdType(), nullValue());
        assertThat(result.get(1).getDetailNonMdCode(), nullValue());
        assertThat(result.get(1).getDetailServiceCode(), nullValue());
        assertThat(result.get(1).getDetailEpcCode(), nullValue());
        assertThat(result.get(1).getDetailGDepartmentCode(), is("34"));
        assertThat(result.get(1).getDetailMajorCategoryCode(), nullValue());
        assertThat(result.get(1).getDetailQuantityCode(), is("P"));
        assertThat(result.get(1).getDetailDetailQuantity(), is(1));
        assertThat(result.get(1).getDetailItemCostCurrencyCode(), is("CAD"));
        assertThat(result.get(1).getDetailItemCostValue(), is(new BigDecimal("10.0000")));
        assertThat(result.get(1).getDetailInitialSellingPriceCurrencyCode(), is("CAD"));
        assertThat(result.get(1).getDetailInitialSellingPrice(), is(new BigDecimal("10.0000")));
        assertThat(result.get(1).getDetailBclassPriceCurrencyCode(), nullValue());
        assertThat(result.get(1).getDetailBclassPrice(), nullValue());
        assertThat(result.get(1).getDetailNewPriceCurrencyCode(), nullValue());
        assertThat(result.get(1).getDetailNewPrice(), nullValue());
        assertThat(result.get(1).getDetailRetailUnitPriceTaxExcludedCurrencyCode(), is("CAD"));
        assertThat(result.get(1).getDetailRetailUnitPriceTaxExcluded(),
                is(new BigDecimal("10.0000")));
        assertThat(result.get(1).getDetailRetailUnitPriceTaxIncludedCurrencyCode(), is("CAD"));
        assertThat(result.get(1).getDetailRetailUnitPriceTaxIncluded(),
                is(new BigDecimal("10.0000")));
        assertThat(result.get(1).getDetailSalesAmountTaxExcludedCurrencyCode(), is("CAD"));
        assertThat(result.get(1).getDetailSalesAmountTaxExcluded(), is(new BigDecimal("10.0000")));
        assertThat(result.get(1).getDetailSalesAmountTaxIncludedCurrencyCode(), is("CAD"));
        assertThat(result.get(1).getDetailSalesAmountTaxIncluded(), is(new BigDecimal("10.0000")));
        assertThat(result.get(1).getDetailCalculationUnavailableType(), nullValue());
        assertThat(result.get(1).getDetailOrderStatusUpdateDate(), nullValue());
        assertThat(result.get(1).getDetailOrderStatusLastUpdateDateTime(), nullValue());
        assertThat(result.get(1).getDetailOrderStatus(), nullValue());
        assertThat(result.get(1).getDetailOrderSubstatus(), nullValue());
        assertThat(result.get(1).getDetailBookingStoreCode(), nullValue());
        assertThat(result.get(1).getDetailBookingStoreSystemBrandCode(), nullValue());
        assertThat(result.get(1).getDetailBookingStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(1).getDetailBookingStoreSystemCountryCode(), nullValue());
        assertThat(result.get(1).getDetailShipmentStoreCode(), nullValue());
        assertThat(result.get(1).getDetailShipmentStoreSystemBrandCode(), nullValue());
        assertThat(result.get(1).getDetailShipmentStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(1).getDetailShipmentStoreSystemCountryCode(), nullValue());
        assertThat(result.get(1).getDetailReceiptStoreCode(), nullValue());
        assertThat(result.get(1).getDetailReceiptStoreSystemBrandCode(), nullValue());
        assertThat(result.get(1).getDetailReceiptStoreSystemBusinessCode(), nullValue());
        assertThat(result.get(1).getDetailReceiptStoreSystemCountryCode(), nullValue());
        assertThat(result.get(1).getDetailContributionSalesRepresentative(), nullValue());
        assertThat(result.get(1).getDetailCustomerId(), nullValue());
        assertThat(result.get(1).getDetailBundlePurchaseApplicableQuantity(), nullValue());
        assertThat(result.get(1).getDetailBundlePurchaseApplicablePriceCurrencyCode(), nullValue());
        assertThat(result.get(1).getDetailBundlePurchaseApplicablePrice(), nullValue());
        assertThat(result.get(1).getDetailBundlePurchaseIndex(), nullValue());
        assertThat(result.get(1).getDetailLimitedAmountPromotionCount(), nullValue());
        assertThat(result.get(1).getDetailStoreItemDiscountType(), nullValue());
        assertThat(result.get(1).getDetailStoreItemDiscountCurrencyCode(), nullValue());
        assertThat(result.get(1).getDetailStoreItemDiscountSetting(), nullValue());
        assertThat(result.get(1).isDetailStoreBundleSaleFlag(), is(Boolean.FALSE));
        assertThat(result.get(1).getDetailStoreBundleSalePriceCurrencyCode(), nullValue());
        assertThat(result.get(1).getDetailStoreBundleSalePrice(), nullValue());
        assertThat(result.get(1).getDetailSetSalesDetailIndex(), nullValue());
        assertThat(result.get(1).getDetailTaxationType(), is("Y"));
        assertThat(result.get(1).getDetailTaxSystemType(), is("OUT"));
        assertThat(result.get(1).isDetailReturnCompleteFlag(), is(Boolean.FALSE));
        assertThat(result.get(1).getDetailInfoOrderSubNumber(), nullValue());
        assertThat(result.get(1).getDetailInfoSalesTransactionId(), nullValue());
        assertThat(result.get(1).getDetailInfoDetailSubNumber(), nullValue());
        assertThat(result.get(1).getDetailInfoItemDetailSubNumber(), nullValue());
        assertThat(result.get(1).getDetailInfoKeyCode(), nullValue());
        assertThat(result.get(1).getDetailInfoCodeValue1(), nullValue());
        assertThat(result.get(1).getDetailInfoCodeValue2(), nullValue());
        assertThat(result.get(1).getDetailInfoCodeValue3(), nullValue());
        assertThat(result.get(1).getDetailInfoCodeValue4(), nullValue());
        assertThat(result.get(1).getDetailInfoName1(), nullValue());
        assertThat(result.get(1).getDetailInfoName2(), nullValue());
        assertThat(result.get(1).getDetailInfoName3(), nullValue());
        assertThat(result.get(1).getDetailInfoName4(), nullValue());
        assertThat(result.get(1).getDiscountOrderSubNumber(), nullValue());
        assertThat(result.get(1).getDiscountSalesTransactionId(), nullValue());
        assertThat(result.get(1).getDiscountDetailSubNumber(), nullValue());
        assertThat(result.get(1).getDiscountPromotionType(), nullValue());
        assertThat(result.get(1).getDiscountPromotionNo(), nullValue());
        assertThat(result.get(1).getDiscountStoreDiscountType(), nullValue());
        assertThat(result.get(1).getDiscountItemDiscountSubNumber(), nullValue());
        assertThat(result.get(1).getDiscountQuantityCode(), nullValue());
        assertThat(result.get(1).getDiscountDiscountQuantity(), nullValue());
        assertThat(result.get(1).getDiscountDiscountAmountTaxExcludedCurrencyCode(), nullValue());
        assertThat(result.get(1).getDiscountDiscountAmountTaxExcluded(), nullValue());
        assertThat(result.get(1).getDiscountDiscountAmountTaxIncludedCurrencyCode(), nullValue());
        assertThat(result.get(1).getDiscountDiscountAmountTaxIncluded(), nullValue());
        assertThat(result.get(1).getTaxOrderSubNumber(), is(1));
        assertThat(result.get(1).getTaxSalesTransactionId(), is("1721123261801221000-0000101-01"));
        assertThat(result.get(1).getTaxDetailSubNumber(), is(1));
        assertThat(result.get(1).getTaxTaxGroup(), is("PST"));
        assertThat(result.get(1).getTaxTaxName(), nullValue());
        assertThat(result.get(1).getTaxTaxSubNumber(), is(1));
        assertThat(result.get(1).getTaxTaxAmountSign(), is("P"));
        assertThat(result.get(1).getTaxTaxAmountCurrencyCode(), is("CAD"));
        assertThat(result.get(1).getTaxTaxAmountValue(), is(new BigDecimal("0.8000")));
        assertThat(result.get(1).getTaxTaxRate(), nullValue());
        assertThat(result.get(1).getTenderOrderSubNumber(), is(1));
        assertThat(result.get(1).getTenderSalesTransactionId(),
                is("1721123261801221000-0000101-01"));
        assertThat(result.get(1).getTenderTenderGroup(), is("CREDIT"));
        assertThat(result.get(1).getTenderTenderId(), is("VISA"));
        assertThat(result.get(1).getTenderTenderSubNumber(), is(1));
        assertThat(result.get(1).getTenderPaymentSign(), is("P"));
        assertThat(result.get(1).getTenderTaxIncludedPaymentAmountCurrencyCode(), is("CAD"));
        assertThat(result.get(1).getTenderTaxIncludedPaymentAmountValue(),
                is(new BigDecimal("10.8000")));
        assertThat(result.get(1).getTenderInfoOrderSubNumber(), is(1));
        assertThat(result.get(1).getTenderInfoSalesTransactionId(),
                is("1721123261801221000-0000101-01"));
        assertThat(result.get(1).getTenderInfoTenderGroup(), is("CREDIT"));
        assertThat(result.get(1).getTenderInfoTenderId(), is("VISA"));
        assertThat(result.get(1).getTenderInfoTenderSubNumber(), is(1));
        assertThat(result.get(1).getTenderInfoDiscountValueCurrencyCode(), nullValue());
        assertThat(result.get(1).getTenderInfoDiscountValue(), nullValue());
        assertThat(result.get(1).getTenderInfoDiscountRate(), nullValue());
        assertThat(result.get(1).getTenderInfoDiscountCodeIdCorporateId(), nullValue());
        assertThat(result.get(1).getTenderInfoCouponType(), nullValue());
        assertThat(result.get(1).getTenderInfoCouponDiscountAmountSettingCurrencyCode(),
                nullValue());
        assertThat(result.get(1).getTenderInfoCouponDiscountAmountSettingValue(), nullValue());
        assertThat(result.get(1).getTenderInfoCouponMinUsageAmountThresholdCurrencyCode(),
                nullValue());
        assertThat(result.get(1).getTenderInfoCouponMinUsageAmountThresholdValue(), nullValue());
        assertThat(result.get(1).getTenderInfoCouponUserId(), nullValue());
        assertThat(result.get(1).getTenderInfoCardNo(), is("123456789xx1"));
        assertThat(result.get(1).getTenderInfoCreditApprovalCode(), is("123456789xx2"));
        assertThat(result.get(1).getTenderInfoCreditProcessingSerialNumber(), is("123456789xx3"));
        assertThat(result.get(1).getTenderInfoCreditPaymentType(), is("10"));
        assertThat(result.get(1).getTenderInfoCreditPaymentCount(), is(1));
        assertThat(result.get(1).getTenderInfoCreditAffiliatedStoreNumber(), is("123456789xx4"));
        assertThat(result.get(1).getTotalOrderSubNumber(), is(1));
        assertThat(result.get(1).getTotalSalesTransactionId(),
                is("1721123261801221000-0000101-01"));
        assertThat(result.get(1).getTotalTotalType(), is("ITEM"));
        assertThat(result.get(1).getTotalTotalAmountSubNumber(), is(1));
        assertThat(result.get(1).getTotalTotalAmountTaxExcludedCurrencyCode(), is("CAD"));
        assertThat(result.get(1).getTotalTotalAmountTaxExcludedValue(),
                is(new BigDecimal("10.0000")));
        assertThat(result.get(1).getTotalTotalAmountTaxIncludedCurrencyCode(), is("CAD"));
        assertThat(result.get(1).getTotalTotalAmountTaxIncludedValue(),
                is(new BigDecimal("10.8000")));
        assertThat(result.get(1).getTotalTaxRate(), nullValue());
        assertThat(result.get(1).getTotalSalesTransactionInformation1(), nullValue());
        assertThat(result.get(1).getTotalSalesTransactionInformation2(), nullValue());
        assertThat(result.get(1).getTotalSalesTransactionInformation3(), nullValue());
    }
}
