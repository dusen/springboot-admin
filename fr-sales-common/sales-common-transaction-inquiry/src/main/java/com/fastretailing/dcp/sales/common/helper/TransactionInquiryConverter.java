/**
 * @(#)TransactionInquiryConverter.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.helper;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.common.constants.CodeConstants;
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

/**
 * Converter class for transaction inquiry API's.
 * 
 */
@Component
public class TransactionInquiryConverter {

    /**
     * Conversion method to transaction import data.
     * 
     * @param from Transaction inquiry entity.
     * @return Transaction import data.
     */
    public TransactionImportData convertToTransactionImportData(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        TransactionImportData to = new TransactionImportData();
        to.setDataAlterationSalesLinkageType(from.getDataAlterationSalesLinkageType());
        to.setDataAlterationBackboneLinkageType(from.getDataAlterationBackboneLinkageType());
        to.setIntegratedOrderId(from.getIntegratedOrderId());
        to.setOrderBarcodeNumber(from.getOrderBarcodeNumber());
        to.setChannelCode(from.getChannelCode());
        to.setStoreCode(from.getStoreCode());
        to.setSystemBrandCode(from.getSystemBrandCode());
        to.setSystemBusinessCode(from.getSystemBusinessCode());
        to.setSystemCountryCode(from.getSystemCountryCode());
        to.setCustomerId(from.getCustomerId());
        to.setOrderConfirmationBusinessDate(
                dateStringFormat(from.getOrderConfirmationBusinessDate()));
        to.setOrderConfirmationDateTime(from.getOrderConfirmationDateTime());
        to.setDataCorrectionEditingFlag(BooleanUtils.toInteger(from.isDataAlterationFlag()));
        to.setDataCorrectionUserId(from.getDataAlterationUserId());

        return to;
    }

    /**
     * Convert to transaction.
     *
     * @param from Transaction inquiry entity.
     * @return Transaction.
     */
    public Transaction convertToTransaction(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        Transaction to = new Transaction();
        to.setTransactionSerialNumber(from.getHeaderSalesTransactionSubNumber());
        to.setOrderStatusUpdateDate(dateStringFormat(from.getHeaderOrderStatusUpdateDate()));
        to.setIntegratedOrderId(from.getHeaderIntegratedOrderId());
        to.setOrderSubNumber(from.getHeaderOrderSubNumber());
        to.setSalesTransactionId(from.getHeaderSalesTransactionId());
        to.setTokenCode(from.getHeaderTokenCode());
        to.setTransactionType(from.getHeaderSalesTransactionType());
        to.setSalesLinkageType(from.getHeaderSalesLinkageType());
        to.setReturnType(from.getHeaderReturnType());
        to.setSystemBrandCode(from.getHeaderSystemBrandCode());
        to.setSystemBusinessCode(from.getHeaderSystemBusinessCode());
        to.setSystemCountryCode(from.getHeaderSystemCountryCode());
        to.setStoreCode(from.getHeaderStoreCode());
        to.setChannelCode(from.getHeaderChannelCode());
        to.setDataCreationBusinessDate(dateStringFormat(from.getHeaderDataCreationBusinessDate()));
        to.setCashRegisterNo(from.getHeaderCashRegisterNo());
        to.setReceiptNo(from.getHeaderReceiptNo());
        to.setOrderNumberForStorePayment(from.getHeaderOrderNumberForStorePayment());
        to.setAdvanceReceivedStoreCode(from.getHeaderAdvanceReceivedStoreCode());
        to.setAdvanceReceivedStoreSystemBrandCode(
                from.getHeaderAdvanceReceivedStoreSystemBrandCode());
        to.setAdvanceReceivedStoreSystemBusinessCode(
                from.getHeaderAdvanceReceivedStoreSystemBusinessCode());
        to.setAdvanceReceivedStoreSystemCountryCode(
                from.getHeaderAdvanceReceivedStoreSystemCountryCode());
        to.setOperatorCode(from.getHeaderOperatorCode());
        to.setOriginalTransactionId(from.getHeaderOriginalTransactionId());
        to.setOriginalReceiptNo(from.getHeaderOriginalReceiptNo());
        to.setOriginalCashRegisterNo(from.getHeaderOriginalCashRegisterNo());
        to.setReceiptNoForCreditCardCancellation(
                from.getHeaderReceiptNoForCreditCardCancellation());
        to.setReceiptNoForCreditCard(from.getHeaderReceiptNoForCreditCard());
        to.setPaymentStoreCode(from.getHeaderPaymentStoreCode());
        to.setPaymentStoreSystemBrandCode(from.getHeaderPaymentStoreSystemBrandCode());
        to.setPaymentStoreSystemBusinessCode(from.getHeaderPaymentStoreSystemBusinessCode());
        to.setPaymentStoreSystemCountryCode(from.getHeaderPaymentStoreSystemCountryCode());
        to.setReceiptIssuedFlag(BooleanUtils.toInteger(from.isHeaderReceiptIssuedFlag()));
        to.setProcessingCompanyCode(from.getHeaderProcessingCompanyCode());
        to.setOrderSubstatus(from.getHeaderOrderSubstatus());
        to.setPaymentStatus(from.getHeaderPaymentStatus());
        to.setShipmentStatus(from.getHeaderShipmentStatus());
        to.setReceivingStatus(from.getHeaderReceivingStatus());
        to.setTransferOutStatus(from.getHeaderTransferOutStatus());
        to.setBookingStoreCode(from.getHeaderBookingStoreCode());
        to.setBookingStoreSystemBrandCode(from.getHeaderBookingStoreSystemBrandCode());
        to.setBookingStoreSystemBusinessCode(from.getHeaderBookingStoreSystemBusinessCode());
        to.setBookingStoreSystemCountryCode(from.getHeaderBookingStoreSystemCountryCode());
        to.setShipmentStoreCode(from.getHeaderShipmentStoreCode());
        to.setShipmentStoreSystemBrandCode(from.getHeaderShipmentStoreSystemBrandCode());
        to.setShipmentStoreSystemBusinessCode(from.getHeaderShipmentStoreSystemBusinessCode());
        to.setShipmentStoreSystemCountryCode(from.getHeaderShipmentStoreSystemCountryCode());
        to.setReceiptStoreCode(from.getHeaderReceiptStoreCode());
        to.setReceiptStoreSystemBrandCode(from.getHeaderReceiptStoreSystemBrandCode());
        to.setReceiptStoreSystemBusinessCode(from.getHeaderReceiptStoreSystemBusinessCode());
        to.setReceiptStoreSystemCountryCode(from.getHeaderReceiptStoreSystemCountryCode());
        to.setCustomerId(from.getHeaderCustomerId());
        to.setCustomerType(from.getHeaderCustomerType());
        to.setCorporateId(from.getHeaderCorporateId());
        to.setSalesTransactionDiscountFlag(String
                .valueOf(BooleanUtils.toInteger(from.isHeaderSalesTransactionDiscountFlag())));
        to.setSalesTransactionDiscountType(from.getHeaderSalesTransactionDiscountType());
        to.setConsistencySalesFlag(BooleanUtils.toInteger(from.isHeaderConsistencySalesFlag()));
        to.setEmployeeSaleFlag(BooleanUtils.toInteger(from.isHeaderEmployeeSaleFlag()));
        to.setDataCreationDateTime(from.getHeaderDataCreationDateTime());
        to.setOrderCancellationDate(from.getHeaderOrderCancelledDateTime());
        to.setOrderStatusLastUpdateDateTime(from.getHeaderOrderStatusLastUpdateDateTime());
        to.setOrderStatus(from.getHeaderOrderStatus());
        Price amountRate = new Price();
        if (StringUtils
                .isNotEmpty(from.getHeaderSalesTransactionDiscountAmountRateCurrencyCode())) {
            amountRate.setCurrencyCode(Currency
                    .getInstance(from.getHeaderSalesTransactionDiscountAmountRateCurrencyCode()));
        }
        amountRate.setValue(from.getHeaderSalesTransactionDiscountAmountRate());
        to.setSalesTransactionDiscountAmountRate(Objects.isNull(amountRate.getCurrencyCode())
                && Objects.isNull(amountRate.getValue()) ? null : amountRate);
        Price deposit = new Price();
        if (StringUtils.isNotEmpty(from.getHeaderDepositCurrencyCode())) {
            deposit.setCurrencyCode(Currency.getInstance(from.getHeaderDepositCurrencyCode()));
        }
        deposit.setValue(from.getHeaderDepositValue());
        to.setDeposit(
                Objects.isNull(deposit.getCurrencyCode()) && Objects.isNull(deposit.getValue())
                        ? null
                        : deposit);
        Price change = new Price();
        if (StringUtils.isNotEmpty(from.getHeaderChangeCurrencyCode())) {
            change.setCurrencyCode(Currency.getInstance(from.getHeaderChangeCurrencyCode()));
        }
        change.setValue(from.getHeaderChangeValue());
        to.setChange(
                Objects.isNull(change.getCurrencyCode()) && Objects.isNull(change.getValue()) ? null
                        : change);

        to.setEreceiptTargetFlag(BooleanUtils.toInteger(from.isHeaderEReceiptTargetFlag()));
        to.setReturnCompleteFlag(BooleanUtils.toInteger(from.isHeaderReturnCompleteFlag()));
        to.setCancelledFlag(BooleanUtils.toInteger(from.isHeaderCancelledFlag()));
        return to;
    }

    /**
     * Convert to item detail.
     *
     * @param from Transaction inquiry sales transaction detail.
     * @return Transaction item detail.
     */
    public TransactionItemDetail convertToItemDetail(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        TransactionItemDetail to = new TransactionItemDetail();
        to.setSystemBrandCode(from.getDetailSystemBrandCode());
        to.setDetailSubNumber(from.getDetailDetailSubNumber());
        to.setDetailListSalesTransactionType(from.getDetailSalesTransactionType());
        to.setL2ItemCode(from.getDetailL2ItemCode());
        to.setViewL2ItemCode(from.getDetailDisplayL2ItemCode());
        to.setL2ProductName(from.getDetailL2ProductName());
        to.setL3ItemCode(from.getDetailL3ItemCode());
        to.setL3PosProductName(from.getDetailL3PosProductName());
        to.setEpcCode(from.getDetailEpcCode());
        to.setGdepartmentCode(from.getDetailGDepartmentCode());
        if (StringUtils.isNotEmpty(from.getDetailMajorCategoryCode())) {
            to.setDeptCode(Integer.valueOf(from.getDetailMajorCategoryCode()));
        }
        to.setQuantityCode(from.getDetailQuantityCode());
        to.setItemQty(from.getDetailDetailQuantity());
        to.setOrderStatusUpdateDate(dateStringFormat(from.getDetailOrderStatusUpdateDate()));
        to.setOrderStatusLastUpdateDateTime(from.getDetailOrderStatusLastUpdateDateTime());
        to.setOrderStatus(from.getDetailOrderStatus());
        to.setOrderSubstatus(from.getDetailOrderSubstatus());
        to.setBookingStoreCode(from.getDetailBookingStoreCode());
        to.setBookingStoreSystemBrandCode(from.getDetailBookingStoreSystemBrandCode());
        to.setBookingStoreSystemBusinessCode(from.getDetailBookingStoreSystemBusinessCode());
        to.setBookingStoreSystemCountryCode(from.getDetailBookingStoreSystemCountryCode());
        to.setShipmentStoreCode(from.getDetailShipmentStoreCode());
        to.setShipmentStoreSystemBrandCode(from.getDetailShipmentStoreSystemBrandCode());
        to.setShipmentStoreSystemBusinessCode(from.getDetailShipmentStoreSystemBusinessCode());
        to.setShipmentStoreSystemCountryCode(from.getDetailShipmentStoreSystemCountryCode());
        to.setReceiptStoreCode(from.getDetailReceiptStoreCode());
        to.setReceiptStoreSystemBrandCode(from.getDetailReceiptStoreSystemBrandCode());
        to.setReceiptStoreSystemBusinessCode(from.getDetailReceiptStoreSystemBusinessCode());
        to.setReceiptStoreSystemCountryCode(from.getDetailReceiptStoreSystemCountryCode());
        to.setContributionSalesRepresentative(from.getDetailContributionSalesRepresentative());
        to.setCustomerId(from.getDetailCustomerId());
        to.setBundlePurchaseQty(from.getDetailBundlePurchaseApplicableQuantity());
        to.setBundlePurchaseIndex(from.getDetailBundlePurchaseIndex());
        to.setLimitedAmountPromotionCount(from.getDetailLimitedAmountPromotionCount());
        to.setCalculationUnavailableType(from.getDetailCalculationUnavailableType());
        to.setItemMountDiscountType(from.getDetailStoreItemDiscountType());
        to.setSetSalesFlag(
                BooleanUtils.toStringTrueFalse(from.isDetailStoreBundleSaleFlag()).toUpperCase());
        to.setSetSalesDetailIndex(from.getDetailSetSalesDetailIndex());
        to.setItemDetailNumber(from.getDetailItemDetailSubNumber());
        to.setItemTaxationType(from.getDetailTaxationType());
        to.setItemTaxKind(from.getDetailTaxSystemType());
        to.setItemReturnCompleteFlag(BooleanUtils.toInteger(from.isDetailReturnCompleteFlag()));

        Price itemCost = new Price();
        if (StringUtils.isNotEmpty(from.getDetailItemCostCurrencyCode())) {
            itemCost.setCurrencyCode(Currency.getInstance(from.getDetailItemCostCurrencyCode()));
        }
        itemCost.setValue(from.getDetailItemCostValue());
        to.setItemCost(
                Objects.isNull(itemCost.getCurrencyCode()) && Objects.isNull(itemCost.getValue())
                        ? null
                        : itemCost);

        Price initialPrice = new Price();
        if (StringUtils.isNotEmpty(from.getDetailInitialSellingPriceCurrencyCode())) {
            initialPrice.setCurrencyCode(
                    Currency.getInstance(from.getDetailInitialSellingPriceCurrencyCode()));
        }
        initialPrice.setValue(from.getDetailInitialSellingPrice());
        to.setInitialSellingPrice(Objects.isNull(initialPrice.getCurrencyCode())
                && Objects.isNull(initialPrice.getValue()) ? null : initialPrice);

        Price bItemPrice = new Price();
        if (StringUtils.isNotEmpty(from.getDetailBclassPriceCurrencyCode())) {
            bItemPrice
                    .setCurrencyCode(Currency.getInstance(from.getDetailBclassPriceCurrencyCode()));
        }
        bItemPrice.setValue(from.getDetailBclassPrice());
        to.setBItemSellingPrice(Objects.isNull(bItemPrice.getCurrencyCode())
                && Objects.isNull(bItemPrice.getValue()) ? null : bItemPrice);

        Price newPrice = new Price();
        if (StringUtils.isNotEmpty(from.getDetailNewPriceCurrencyCode())) {
            newPrice.setCurrencyCode(Currency.getInstance(from.getDetailNewPriceCurrencyCode()));
        }
        newPrice.setValue(from.getDetailNewPrice());
        to.setItemNewPrice(
                Objects.isNull(newPrice.getCurrencyCode()) && Objects.isNull(newPrice.getValue())
                        ? null
                        : newPrice);

        Price unitTaxExclude = new Price();
        if (StringUtils.isNotEmpty(from.getDetailRetailUnitPriceTaxExcludedCurrencyCode())) {
            unitTaxExclude.setCurrencyCode(
                    Currency.getInstance(from.getDetailRetailUnitPriceTaxExcludedCurrencyCode()));
        }
        unitTaxExclude.setValue(from.getDetailRetailUnitPriceTaxExcluded());
        to.setItemUnitPriceTaxExcluded(Objects.isNull(unitTaxExclude.getCurrencyCode())
                && Objects.isNull(unitTaxExclude.getValue()) ? null : unitTaxExclude);

        Price unitTaxInclude = new Price();
        if (StringUtils.isNotEmpty(from.getDetailRetailUnitPriceTaxIncludedCurrencyCode())) {
            unitTaxInclude.setCurrencyCode(
                    Currency.getInstance(from.getDetailRetailUnitPriceTaxIncludedCurrencyCode()));
        }
        unitTaxInclude.setValue(from.getDetailRetailUnitPriceTaxIncluded());
        to.setItemUnitPriceTaxIncluded(Objects.isNull(unitTaxInclude.getCurrencyCode())
                && Objects.isNull(unitTaxInclude.getValue()) ? null : unitTaxInclude);

        Price amountTaxExclude = new Price();
        if (StringUtils.isNotEmpty(from.getDetailSalesAmountTaxExcludedCurrencyCode())) {
            amountTaxExclude.setCurrencyCode(
                    Currency.getInstance(from.getDetailSalesAmountTaxExcludedCurrencyCode()));
        }
        amountTaxExclude.setValue(from.getDetailSalesAmountTaxExcluded());
        to.setItemSalesAmtTaxExcluded(Objects.isNull(amountTaxExclude.getCurrencyCode())
                && Objects.isNull(amountTaxExclude.getValue()) ? null : amountTaxExclude);

        Price amountTaxInclude = new Price();
        if (StringUtils.isNotEmpty(from.getDetailSalesAmountTaxIncludedCurrencyCode())) {
            amountTaxInclude.setCurrencyCode(
                    Currency.getInstance(from.getDetailSalesAmountTaxIncludedCurrencyCode()));
        }
        amountTaxInclude.setValue(from.getDetailSalesAmountTaxIncluded());
        to.setItemSalesAmtTaxIncluded(Objects.isNull(amountTaxInclude.getCurrencyCode())
                && Objects.isNull(amountTaxInclude.getValue()) ? null : amountTaxInclude);

        Price bundlePrice = new Price();
        if (StringUtils.isNotEmpty(from.getDetailBundlePurchaseApplicablePriceCurrencyCode())) {
            bundlePrice.setCurrencyCode(Currency
                    .getInstance(from.getDetailBundlePurchaseApplicablePriceCurrencyCode()));
        }
        bundlePrice.setValue(from.getDetailBundlePurchaseApplicablePrice());
        to.setBundlePurchasePrice(Objects.isNull(bundlePrice.getCurrencyCode())
                && Objects.isNull(bundlePrice.getValue()) ? null : bundlePrice);

        Price discountAmount = new Price();
        if (StringUtils.isNotEmpty(from.getDetailStoreItemDiscountCurrencyCode())) {
            discountAmount.setCurrencyCode(
                    Currency.getInstance(from.getDetailStoreItemDiscountCurrencyCode()));
        }
        discountAmount.setValue(from.getDetailStoreItemDiscountSetting());
        to.setItemDiscountAmount(Objects.isNull(discountAmount.getCurrencyCode())
                && Objects.isNull(discountAmount.getValue()) ? null : discountAmount);

        Price bundleSalePrice = new Price();
        if (StringUtils.isNotEmpty(from.getDetailStoreBundleSalePriceCurrencyCode())) {
            bundleSalePrice.setCurrencyCode(
                    Currency.getInstance(from.getDetailStoreBundleSalePriceCurrencyCode()));
        }
        bundleSalePrice.setValue(from.getDetailStoreBundleSalePrice());
        to.setSetSalesPrice(Objects.isNull(bundleSalePrice.getCurrencyCode())
                && Objects.isNull(bundleSalePrice.getValue()) ? null : bundleSalePrice);
        return to;
    }

    /**
     * Convert to non-item detail.
     *
     * @param from Transaction inquiry sales transaction detail.
     * @return Non-item detail.
     */
    public NonItemDetail convertToNonItemDetail(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        NonItemDetail to = new NonItemDetail();
        to.setNonMdDetailListSalesTransactionType(from.getDetailSalesTransactionType());
        to.setItemDetailSubNumber(from.getDetailItemDetailSubNumber());
        to.setDetailSubNumber(from.getDetailDetailSubNumber());
        to.setNonMdType(from.getDetailNonMdType());
        to.setNonItemCode(from.getDetailNonMdCode());
        to.setServiceCode(from.getDetailServiceCode());
        to.setPosNonItemName(from.getDetailL3PosProductName());
        to.setQuantityCode(from.getDetailQuantityCode());
        to.setNonItemQty(from.getDetailDetailQuantity());
        to.setNonCalculationNonItemType(from.getDetailCalculationUnavailableType());
        to.setOrderStatusUpdateDate(dateStringFormat(from.getDetailOrderStatusUpdateDate()));
        to.setOrderStatusLastUpdateDateTime(from.getDetailOrderStatusLastUpdateDateTime());
        to.setOrderStatus(from.getDetailOrderStatus());
        to.setOrderSubstatus(from.getDetailOrderSubstatus());
        to.setBookingStoreCode(from.getDetailBookingStoreCode());
        to.setBookingStoreSystemBrandCode(from.getDetailBookingStoreSystemBrandCode());
        to.setBookingStoreSystemBusinessCode(from.getDetailBookingStoreSystemBusinessCode());
        to.setBookingStoreSystemCountryCode(from.getDetailBookingStoreSystemCountryCode());
        to.setShipmentStoreCode(from.getDetailShipmentStoreCode());
        to.setShipmentStoreSystemBrandCode(from.getDetailShipmentStoreSystemBrandCode());
        to.setShipmentStoreSystemBusinessCode(from.getDetailShipmentStoreSystemBusinessCode());
        to.setShipmentStoreSystemCountryCode(from.getDetailShipmentStoreSystemCountryCode());
        to.setReceiptStoreCode(from.getDetailReceiptStoreCode());
        to.setReceiptStoreSystemBrandCode(from.getDetailReceiptStoreSystemBrandCode());
        to.setReceiptStoreSystemBusinessCode(from.getDetailReceiptStoreSystemBusinessCode());
        to.setReceiptStoreSystemCountryCode(from.getDetailReceiptStoreSystemCountryCode());
        to.setContributionSalesRepresentative(from.getDetailContributionSalesRepresentative());
        to.setNonItemDetailNumber(from.getDetailDetailSubNumber());
        to.setNonItemTaxationType(from.getDetailTaxationType());
        to.setNonItemTaxKind(from.getDetailTaxSystemType());

        if (Objects.nonNull(from.isDetailReturnCompleteFlag())) {
            to.setNonItemReturnCompleteFlag(
                    BooleanUtils.toInteger(from.isDetailReturnCompleteFlag()));
        }
        Price unitTaxExclude = new Price();
        if (StringUtils.isNotEmpty(from.getDetailRetailUnitPriceTaxExcludedCurrencyCode())) {
            unitTaxExclude.setCurrencyCode(
                    Currency.getInstance(from.getDetailRetailUnitPriceTaxExcludedCurrencyCode()));
        }
        unitTaxExclude.setValue(from.getDetailRetailUnitPriceTaxExcluded());
        to.setNonItemUnitPriceTaxExcluded(Objects.isNull(unitTaxExclude.getCurrencyCode())
                && Objects.isNull(unitTaxExclude.getValue()) ? null : unitTaxExclude);
        Price unitTaxInclude = new Price();
        if (StringUtils.isNotEmpty(from.getDetailRetailUnitPriceTaxIncludedCurrencyCode())) {
            unitTaxInclude.setCurrencyCode(
                    Currency.getInstance(from.getDetailRetailUnitPriceTaxIncludedCurrencyCode()));
        }
        unitTaxInclude.setValue(from.getDetailRetailUnitPriceTaxIncluded());
        to.setNonItemUnitPriceTaxIncluded(Objects.isNull(unitTaxInclude.getCurrencyCode())
                && Objects.isNull(unitTaxInclude.getValue()) ? null : unitTaxInclude);
        Price amountTaxExclude = new Price();
        if (StringUtils.isNotEmpty(from.getDetailSalesAmountTaxExcludedCurrencyCode())) {
            amountTaxExclude.setCurrencyCode(
                    Currency.getInstance(from.getDetailSalesAmountTaxExcludedCurrencyCode()));
        }
        amountTaxExclude.setValue(from.getDetailSalesAmountTaxExcluded());
        to.setNonItemSalesAmtTaxExcluded(Objects.isNull(amountTaxExclude.getCurrencyCode())
                && Objects.isNull(amountTaxExclude.getValue()) ? null : amountTaxExclude);
        Price amountTaxInclude = new Price();
        if (StringUtils.isNotEmpty(from.getDetailSalesAmountTaxIncludedCurrencyCode())) {
            amountTaxInclude.setCurrencyCode(
                    Currency.getInstance(from.getDetailSalesAmountTaxIncludedCurrencyCode()));
        }
        amountTaxInclude.setValue(from.getDetailSalesAmountTaxIncluded());
        to.setNonItemSalesAmtTaxIncluded(Objects.isNull(amountTaxInclude.getCurrencyCode())
                && Objects.isNull(amountTaxInclude.getValue()) ? null : amountTaxInclude);
        Price newPrice = new Price();
        if (StringUtils.isNotEmpty(from.getDetailNewPriceCurrencyCode())) {
            newPrice.setCurrencyCode(Currency.getInstance(from.getDetailNewPriceCurrencyCode()));
        }
        newPrice.setValue(from.getDetailNewPrice());
        to.setNonItemNewPrice(
                Objects.isNull(newPrice.getCurrencyCode()) && Objects.isNull(newPrice.getValue())
                        ? null
                        : newPrice);

        return to;
    }

    /**
     * Converter method for non-item information.
     * 
     * @param from Transaction inquiry entity.
     * @return Non-item info.
     */
    public NonItemInformation convertToNonItemInformation(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        NonItemInformation to = new NonItemInformation();
        to.setDetailSubNumber(from.getDetailInfoDetailSubNumber());
        to.setItemDetailSubNumber(from.getDetailInfoItemDetailSubNumber());
        to.setKeyCode(from.getDetailInfoKeyCode());
        to.setCodeValue1(from.getDetailInfoCodeValue1());
        to.setCodeValue2(from.getDetailInfoCodeValue2());
        to.setCodeValue3(from.getDetailInfoCodeValue3());
        to.setCodeValue4(from.getDetailInfoCodeValue4());
        to.setName1(from.getDetailInfoName1());
        to.setName2(from.getDetailInfoName2());
        to.setName3(from.getDetailInfoName3());
        to.setName4(from.getDetailInfoName4());
        return to;
    }

    /**
     * Convert to discount detail.
     *
     * @param from Transaction inquiry sales transaction discount.
     * @return Non-iem discount detail.
     */
    public NonItemDiscountDetail convertToDiscountDetail(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        NonItemDiscountDetail to = new NonItemDiscountDetail();
        to.setNonItemDiscountQty(from.getDiscountDiscountQuantity());
        to.setNonItemQuantityCode(from.getDiscountQuantityCode());
        to.setNonItemStoreDiscountType(from.getDiscountStoreDiscountType());
        to.setNonItemPromotionType(from.getDiscountPromotionType());
        to.setNonItemPromotionNumber(from.getDiscountPromotionNo());
        to.setNonItemDiscountSubNumber(from.getDiscountItemDiscountSubNumber());
        to.setNonItemDiscountDetailSubNumber(from.getDiscountDetailSubNumber());
        Price amountTaxExclude = new Price();
        if (StringUtils.isNotEmpty(from.getDiscountDiscountAmountTaxExcludedCurrencyCode())) {
            amountTaxExclude.setCurrencyCode(
                    Currency.getInstance(from.getDiscountDiscountAmountTaxExcludedCurrencyCode()));
        }
        amountTaxExclude.setValue(from.getDiscountDiscountAmountTaxExcluded());
        to.setNonItemDiscountAmtTaxExcluded(Objects.isNull(amountTaxExclude.getCurrencyCode())
                && Objects.isNull(amountTaxExclude.getValue()) ? null : amountTaxExclude);
        Price amountTaxInclude = new Price();
        if (StringUtils.isNotEmpty(from.getDiscountDiscountAmountTaxIncludedCurrencyCode())) {
            amountTaxInclude.setCurrencyCode(
                    Currency.getInstance(from.getDiscountDiscountAmountTaxIncludedCurrencyCode()));
        }
        amountTaxInclude.setValue(from.getDiscountDiscountAmountTaxIncluded());
        to.setNonItemDiscountAmtTaxIncluded(Objects.isNull(amountTaxInclude.getCurrencyCode())
                && Objects.isNull(amountTaxInclude.getValue()) ? null : amountTaxInclude);
        return to;
    }

    /**
     * Convert to non-item tax detail.
     *
     * @param from Transaction inquiry sales transaction tax.
     * @return Non-item tax detail.
     */
    public NonItemTaxDetail convertToNonItemTaxDetail(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        NonItemTaxDetail to = new NonItemTaxDetail();
        to.setNonItemTaxRate(from.getTaxTaxRate());
        to.setNonItemTaxAmountSign(from.getTaxTaxAmountSign());
        to.setNonItemTaxName(from.getTaxTaxName());
        to.setNonItemTaxType(from.getTaxTaxGroup());
        to.setNonItemTaxDiscountSubNumber(from.getTaxTaxSubNumber());
        to.setNonItemTaxDetailSubNumber(from.getTaxDetailSubNumber());
        to.setNonItemTaxDetailSubNumber(from.getTaxDetailSubNumber());
        Price taxAmount = new Price();
        if (StringUtils.isNotEmpty(from.getTaxTaxAmountCurrencyCode())) {
            taxAmount.setCurrencyCode(Currency.getInstance(from.getTaxTaxAmountCurrencyCode()));
        }
        taxAmount.setValue(from.getTaxTaxAmountValue());
        to.setNonItemTaxAmt(
                Objects.isNull(taxAmount.getCurrencyCode()) && Objects.isNull(taxAmount.getValue())
                        ? null
                        : taxAmount);
        return to;
    }

    /**
     * Convert to item discount.
     *
     * @param from Transaction inquiry sales transaction discount.
     * @return Item discount.
     */
    public ItemDiscount convertToItemDiscount(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        ItemDiscount to = new ItemDiscount();
        to.setItemStoreDiscountType(from.getDiscountStoreDiscountType());
        to.setItemPromotionNumber(from.getDiscountPromotionNo());
        to.setItemPromotionType(from.getDiscountPromotionType());
        to.setItemDiscountDetailSubNumber(from.getDiscountDetailSubNumber());
        to.setItemDiscountSubNumber(from.getDiscountItemDiscountSubNumber());
        to.setItemQuantityCode(from.getDiscountQuantityCode());
        to.setItemDiscountQty(from.getDiscountDiscountQuantity());
        Price amountTaxExclude = new Price();
        if (StringUtils.isNotEmpty(from.getDiscountDiscountAmountTaxExcludedCurrencyCode())) {
            amountTaxExclude.setCurrencyCode(
                    Currency.getInstance(from.getDiscountDiscountAmountTaxExcludedCurrencyCode()));
        }
        amountTaxExclude.setValue(from.getDiscountDiscountAmountTaxExcluded());
        to.setItemDiscountAmtTaxExcluded(Objects.isNull(amountTaxExclude.getCurrencyCode())
                && Objects.isNull(amountTaxExclude.getValue()) ? null : amountTaxExclude);
        Price amountTaxInclude = new Price();
        if (StringUtils.isNotEmpty(from.getDiscountDiscountAmountTaxIncludedCurrencyCode())) {
            amountTaxInclude.setCurrencyCode(
                    Currency.getInstance(from.getDiscountDiscountAmountTaxIncludedCurrencyCode()));
        }
        amountTaxInclude.setValue(from.getDiscountDiscountAmountTaxIncluded());
        to.setItemDiscountAmtTaxIncluded(Objects.isNull(amountTaxInclude.getCurrencyCode())
                && Objects.isNull(amountTaxInclude.getValue()) ? null : amountTaxInclude);

        return to;
    }

    /**
     * Convert to item tax detail method.
     *
     * @param from Transaction inquiry sales transaction tax.
     * @return Item tax detail.
     */
    public ItemTaxDetail convertToItemTaxDetail(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        ItemTaxDetail to = new ItemTaxDetail();
        to.setItemTaxRate(from.getTaxTaxRate());
        to.setItemTaxAmountSign(from.getTaxTaxAmountSign());
        to.setItemTaxDetailSubNumber(from.getTaxDetailSubNumber());
        to.setItemTaxSubNumber(from.getTaxTaxSubNumber());
        to.setItemTaxName(from.getTaxTaxName());
        to.setItemTaxType(from.getTaxTaxGroup());
        to.setItemTaxSubNumber(from.getTaxTaxSubNumber());
        Price taxAmount = new Price();
        if (StringUtils.isNotEmpty(from.getTaxTaxAmountCurrencyCode())) {
            taxAmount.setCurrencyCode(Currency.getInstance(from.getTaxTaxAmountCurrencyCode()));
        }
        taxAmount.setValue(from.getTaxTaxAmountValue());
        to.setItemTaxAmt(
                Objects.isNull(taxAmount.getCurrencyCode()) && Objects.isNull(taxAmount.getValue())
                        ? null
                        : taxAmount);
        return to;

    }

    /**
     * Convert to transaction tender.
     *
     * @param from Transaction inquiry sales transaction tender.
     * @return Sales transaction tender.
     */
    public SalesTransactionTender convertToTransactionTender(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        SalesTransactionTender to = new SalesTransactionTender();
        to.setTenderSubNumber(from.getTenderTenderSubNumber());
        to.setTenderGroupId(from.getTenderTenderGroup());
        to.setTenderId(from.getTenderTenderId());
        to.setPaymentSign(from.getTenderPaymentSign());
        Price paymentAmount = new Price();
        if (StringUtils.isNotEmpty(from.getTenderTaxIncludedPaymentAmountCurrencyCode())) {
            paymentAmount.setCurrencyCode(
                    Currency.getInstance(from.getTenderTaxIncludedPaymentAmountCurrencyCode()));
        }
        paymentAmount.setValue(from.getTenderTaxIncludedPaymentAmountValue());
        to.setTaxIncludedPaymentAmount(Objects.isNull(paymentAmount.getCurrencyCode())
                && Objects.isNull(paymentAmount.getValue()) ? null : paymentAmount);
        to.setTenderGroupId(from.getTenderTenderGroup());
        return to;
    }

    /**
     * Convert to tender information method.
     *
     * @param from Transaction inquiry sales transaction tender information.
     * @return Tender information.
     */
    public TenderInformation convertToTenderInfo(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        TenderInformation to = new TenderInformation();
        to.setDiscountRate(from.getTenderInfoDiscountRate());
        to.setDiscountCodeIdCorporateId(from.getTenderInfoDiscountCodeIdCorporateId());
        to.setCouponType(from.getTenderInfoCouponType());
        to.setCouponUserId(from.getTenderInfoCouponUserId());
        to.setCardNo(from.getTenderInfoCardNo());
        to.setCreditApprovalCode(from.getTenderInfoCreditApprovalCode());
        to.setCreditProcessingSerialNumber(from.getTenderInfoCreditProcessingSerialNumber());
        to.setCreditPaymentType(from.getTenderInfoCreditPaymentType());
        to.setCreditPaymentCount(from.getTenderInfoCreditPaymentCount());
        to.setCreditAffiliatedStoreNumber(from.getTenderInfoCreditAffiliatedStoreNumber());
        Price discount = new Price();
        if (StringUtils.isNotEmpty(from.getTenderInfoDiscountValueCurrencyCode())) {
            discount.setCurrencyCode(
                    Currency.getInstance(from.getTenderInfoDiscountValueCurrencyCode()));
        }
        discount.setValue(from.getTenderInfoDiscountValue());
        to.setDiscountAmount(
                Objects.isNull(discount.getCurrencyCode()) && Objects.isNull(discount.getValue())
                        ? null
                        : discount);
        Price couponDiscount = new Price();
        if (StringUtils.isNotEmpty(from.getTenderInfoCouponDiscountAmountSettingCurrencyCode())) {
            couponDiscount.setCurrencyCode(Currency
                    .getInstance(from.getTenderInfoCouponDiscountAmountSettingCurrencyCode()));
        }
        couponDiscount.setValue(from.getTenderInfoCouponDiscountAmountSettingValue());
        to.setCouponDiscountAmountSetting(Objects.isNull(couponDiscount.getCurrencyCode())
                && Objects.isNull(couponDiscount.getValue()) ? null : couponDiscount);
        Price couponMinimun = new Price();
        if (StringUtils.isNotEmpty(from.getTenderInfoCouponMinUsageAmountThresholdCurrencyCode())) {
            couponMinimun.setCurrencyCode(Currency
                    .getInstance(from.getTenderInfoCouponMinUsageAmountThresholdCurrencyCode()));
        }
        couponMinimun.setValue(from.getTenderInfoCouponMinUsageAmountThresholdValue());
        to.setCouponMinUsageAmountThreshold(Objects.isNull(couponMinimun.getCurrencyCode())
                && Objects.isNull(couponMinimun.getValue()) ? null : couponMinimun);
        return to;
    }

    /**
     * Convert to tax detail method.
     *
     * @param from Tranasction inquiry sales transaction tax.
     * @return Sales transaction tax detail
     */
    public SalesTransactionTaxDetail convertToTaxDetail(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        SalesTransactionTaxDetail to = new SalesTransactionTaxDetail();
        to.setTaxSubNumber(from.getTaxTaxSubNumber());
        to.setTaxGroup(from.getTaxTaxGroup());
        to.setTaxAmountSign(from.getTaxTaxAmountSign());
        to.setTaxRate(from.getTaxTaxRate());
        Price taxAmount = new Price();
        if (StringUtils.isNotEmpty(from.getTaxTaxAmountCurrencyCode())) {
            taxAmount.setCurrencyCode(Currency.getInstance(from.getTaxTaxAmountCurrencyCode()));
        }
        taxAmount.setValue(from.getTaxTaxAmountValue());
        to.setTaxAmount(
                Objects.isNull(taxAmount.getCurrencyCode()) && Objects.isNull(taxAmount.getValue())
                        ? null
                        : taxAmount);
        return to;
    }


    /**
     * Convert to transaction total.
     *
     * @param from Transaction inquiry sales transaction total amount.
     * @return Sales tranaction total.
     */
    public SalesTransactionTotal convertToTransactionTotal(TransactionInquiryOptional from) {
        if (Objects.isNull(from)) {
            return null;
        }
        SalesTransactionTotal to = new SalesTransactionTotal();
        to.setTotalAmountSubNumber(from.getTotalTotalAmountSubNumber());
        to.setTotalType(from.getTotalTotalType());
        to.setSalesTransactionInformation1(from.getTotalSalesTransactionInformation1());
        to.setSalesTransactionInformation2(from.getTotalSalesTransactionInformation2());
        to.setSalesTransactionInformation3(from.getTotalSalesTransactionInformation3());
        to.setConsumptionTaxRate(from.getTotalTaxRate());
        Price amountTaxExclude = new Price();
        if (StringUtils.isNotEmpty(from.getTotalTotalAmountTaxExcludedCurrencyCode())) {
            amountTaxExclude.setCurrencyCode(
                    Currency.getInstance(from.getTotalTotalAmountTaxExcludedCurrencyCode()));
        }
        amountTaxExclude.setValue(from.getTotalTotalAmountTaxExcludedValue());
        to.setTotalAmountTaxExcluded(Objects.isNull(amountTaxExclude.getCurrencyCode())
                && Objects.isNull(amountTaxExclude.getValue()) ? null : amountTaxExclude);
        Price amountTaxInclude = new Price();
        if (StringUtils.isNotEmpty(from.getTotalTotalAmountTaxIncludedCurrencyCode())) {
            amountTaxInclude.setCurrencyCode(
                    Currency.getInstance(from.getTotalTotalAmountTaxIncludedCurrencyCode()));
        }
        amountTaxInclude.setValue(from.getTotalTotalAmountTaxIncludedValue());
        to.setTotalAmountTaxIncluded(Objects.isNull(amountTaxInclude.getCurrencyCode())
                && Objects.isNull(amountTaxInclude.getValue()) ? null : amountTaxInclude);
        return to;
    }

    /**
     * Date string format conversion.
     *
     * @param dateString Date string format [yyyymmdd] source.
     * @return Date string format [yyyy-mm-dd].
     */
    private String dateStringFormat(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(dateString);
        stringBuilder = stringBuilder.insert(6, CodeConstants.HYPHEN);
        stringBuilder = stringBuilder.insert(4, CodeConstants.HYPHEN);
        return stringBuilder.toString();
    }
}
