/**
 * @(#)ErrorEvacuationTableDataConverter.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.converter;

import java.math.BigDecimal;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.common.type.ProductClassification;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemDiscount;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDiscountDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTotal;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;
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
import com.fastretailing.dcp.storecommon.util.PriceUtils;

/**
 * The class is used to convert data to error evacuation table.
 *
 */
@Component
public class ErrorEvacuationTableDataConverter {

    /** Common data processor. */
    @Autowired
    private CommonDataProcessor commonDataProcessor;

    /** Model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Convert data to error evacuation sales order information entity (for insert).
     * 
     * @param transactionImportData Transaction import data.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @return TErrorEvacuationSalesOrderInformationEntity.
     */
    public ErrorEvacuationSalesOrderInformation convertTErrorEvacuationSalesOrderInformationEntityForInsert(
            TransactionImportData transactionImportData, String userId,
            String salesTransactionErrorId) {
        ErrorEvacuationSalesOrderInformation errorEvacuationSalesOrderInformationEntity =
                new ErrorEvacuationSalesOrderInformation();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesOrderInformationEntity);
        modelMapper.map(transactionImportData, errorEvacuationSalesOrderInformationEntity);
        errorEvacuationSalesOrderInformationEntity.setTransactionId(salesTransactionErrorId);
        if (transactionImportData.getDataCorrectionEditingFlag() != null) {
            errorEvacuationSalesOrderInformationEntity.setDataAlterationEditingFlag(
                    transactionImportData.getDataCorrectionEditingFlag());
        }
        errorEvacuationSalesOrderInformationEntity
                .setDataAlterationUserId(transactionImportData.getDataCorrectionUserId());
        if (transactionImportData.getOrderConfirmationDateTime() != null) {
            errorEvacuationSalesOrderInformationEntity.setOrderConfirmationDateTime(
                    transactionImportData.getOrderConfirmationDateTime().toLocalDateTime());
        }

        return errorEvacuationSalesOrderInformationEntity;
    }

    /**
     * Convert data to error evacuation sales transaction header entity (for insert).
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param orignialSalesTransactionErrorId Original transaction error id.
     * @return TErrorEvacuationSalesTransactionHeaderEntity.
     */
    public ErrorEvacuationSalesTransactionHeader convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
            Transaction transaction, String userId, String salesTransactionErrorId,
            String orignialSalesTransactionErrorId) {

        ErrorEvacuationSalesTransactionHeader errorEvacuationSalesTransactionHeaderEntity =
                new ErrorEvacuationSalesTransactionHeader();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionHeaderEntity);
        modelMapper.map(transaction, errorEvacuationSalesTransactionHeaderEntity);
        errorEvacuationSalesTransactionHeaderEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionHeaderEntity
                .setSalesTransactionErrorId(orignialSalesTransactionErrorId);
        errorEvacuationSalesTransactionHeaderEntity
                .setSalesTransactionSubNumber(transaction.getTransactionSerialNumber());
        errorEvacuationSalesTransactionHeaderEntity
                .setSalesTransactionType(transaction.getTransactionType());

        errorEvacuationSalesTransactionHeaderEntity.setCustomerType(transaction.getCustomerType());
        errorEvacuationSalesTransactionHeaderEntity
                .setSalesTransactionDiscountType(transaction.getSalesTransactionDiscountType());
        if (transaction.getConsistencySalesFlag() != null) {
            errorEvacuationSalesTransactionHeaderEntity
                    .setConsistencySalesFlag(transaction.getConsistencySalesFlag());
        }
        if (transaction.getEmployeeSaleFlag() != null) {
            errorEvacuationSalesTransactionHeaderEntity
                    .setEmployeeSaleFlag(transaction.getEmployeeSaleFlag());
        }
        if (transaction.getReceiptIssuedFlag() != null) {
            errorEvacuationSalesTransactionHeaderEntity
                    .setReceiptIssuedFlag(transaction.getReceiptIssuedFlag());
        }
        if (transaction.getEReceiptTargetFlag() != null) {
            errorEvacuationSalesTransactionHeaderEntity
                    .setEReceiptTargetFlag(transaction.getEReceiptTargetFlag());
        }
        if (transaction.getDataCreationDateTime() != null) {
            errorEvacuationSalesTransactionHeaderEntity.setDataCreationDateTime(
                    transaction.getDataCreationDateTime().toLocalDateTime());
        }
        if (transaction.getOrderStatusLastUpdateDateTime() != null) {
            errorEvacuationSalesTransactionHeaderEntity.setOrderStatusLastUpdateDateTime(
                    transaction.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }
        if (transaction.getOrderCancellationDate() != null) {
            errorEvacuationSalesTransactionHeaderEntity.setOrderCancelledDateTime(
                    transaction.getOrderCancellationDate().toLocalDateTime());
        }
        Price deposit = transaction.getDeposit();
        if (deposit != null) {
            errorEvacuationSalesTransactionHeaderEntity
                    .setDepositCurrencyCode(PriceUtils.getCurrencyCode(deposit.getCurrencyCode()));
            errorEvacuationSalesTransactionHeaderEntity.setDepositValue(deposit.getValue());
        }
        Price change = transaction.getChange();
        if (change != null) {
            errorEvacuationSalesTransactionHeaderEntity
                    .setChangeCurrencyCode(PriceUtils.getCurrencyCode(change.getCurrencyCode()));
            errorEvacuationSalesTransactionHeaderEntity.setChangeValue(change.getValue());
        }
        Price amountRate = transaction.getSalesTransactionDiscountAmountRate();
        if (amountRate != null) {
            errorEvacuationSalesTransactionHeaderEntity
                    .setSalesTransactionDiscountAmountRateCurrencyCode(
                            PriceUtils.getCurrencyCode(amountRate.getCurrencyCode()));
            errorEvacuationSalesTransactionHeaderEntity
                    .setSalesTransactionDiscountAmountRate(amountRate.getValue());
        }
        return errorEvacuationSalesTransactionHeaderEntity;
    }

    /**
     * Convert data to error evacuation sales transaction detail entity (for insert).
     * 
     * @param transactionItemDetail TransactionItemDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction data error id.
     * @param originalSalesTransactionErrorId Original transaction data id.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @return TErrorEvacuationSalesTransactionDetailEntity.
     */
    public ErrorEvacuationSalesTransactionDetail convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
            TransactionItemDetail transactionItemDetail, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, int detailCount, int itemDetailCount) {

        ErrorEvacuationSalesTransactionDetail errorEvacuationSalesTransactionDetailEntity =
                new ErrorEvacuationSalesTransactionDetail();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionDetailEntity);
        modelMapper.map(transactionItemDetail, errorEvacuationSalesTransactionDetailEntity);

        errorEvacuationSalesTransactionDetailEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionDetailEntity.setSalesTransactionId(salesTransactionId);
        errorEvacuationSalesTransactionDetailEntity.setOrderSubNumber(orderSubNumber);
        errorEvacuationSalesTransactionDetailEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        errorEvacuationSalesTransactionDetailEntity.setDetailSubNumber(detailCount);
        errorEvacuationSalesTransactionDetailEntity.setItemDetailSubNumber(itemDetailCount);

        if (transactionItemDetail.getOrderStatusLastUpdateDateTime() != null) {
            errorEvacuationSalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(
                    transactionItemDetail.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }

        errorEvacuationSalesTransactionDetailEntity
                .setSalesTransactionType(transactionItemDetail.getDetailListSalesTransactionType());
        errorEvacuationSalesTransactionDetailEntity
                .setDisplayL2ItemCode(transactionItemDetail.getViewL2ItemCode());
        errorEvacuationSalesTransactionDetailEntity
                .setProductClassification(ProductClassification.ITEM.getValue());
        if (transactionItemDetail.getDeptCode() != null) {
            errorEvacuationSalesTransactionDetailEntity
                    .setMajorCategoryCode(String.valueOf(transactionItemDetail.getDeptCode()));
        }
        if (transactionItemDetail.getItemQty() != null) {
            errorEvacuationSalesTransactionDetailEntity
                    .setDetailQuantity(new BigDecimal(transactionItemDetail.getItemQty()));
        }
        if (transactionItemDetail.getBundlePurchaseQty() != null) {
            errorEvacuationSalesTransactionDetailEntity.setBundlePurchaseApplicableQuantity(
                    new BigDecimal(transactionItemDetail.getBundlePurchaseQty()));
        }
        errorEvacuationSalesTransactionDetailEntity
                .setStoreItemDiscountType(transactionItemDetail.getItemMountDiscountType());
        if (Objects.nonNull(transactionItemDetail.getBundleSalesFlag())) {
            errorEvacuationSalesTransactionDetailEntity
                    .setStoreBundleSaleFlag(transactionItemDetail.getBundleSalesFlag());
        }
        errorEvacuationSalesTransactionDetailEntity
                .setSetSalesDetailIndex(transactionItemDetail.getBundleSalesDetailIndex());
        errorEvacuationSalesTransactionDetailEntity
                .setTaxationType(transactionItemDetail.getItemTaxationType());
        errorEvacuationSalesTransactionDetailEntity
                .setTaxSystemType(transactionItemDetail.getItemTaxKind());

        Price itemCost = transactionItemDetail.getItemCost();
        if (itemCost != null) {
            errorEvacuationSalesTransactionDetailEntity.setItemCostCurrencyCode(
                    PriceUtils.getCurrencyCode(itemCost.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity.setItemCostValue(itemCost.getValue());
        }

        Price initialSellingPrice = transactionItemDetail.getInitialSellingPrice();
        if (initialSellingPrice != null) {
            errorEvacuationSalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(initialSellingPrice.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setInitialSellingPrice(initialSellingPrice.getValue());
        }

        Price itemSellingPrice = transactionItemDetail.getBItemSellingPrice();
        if (itemSellingPrice != null) {
            errorEvacuationSalesTransactionDetailEntity.setBclassPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSellingPrice.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity.setBclassPrice(itemSellingPrice.getValue());
        }

        Price itemNewPrice = transactionItemDetail.getItemNewPrice();
        if (itemNewPrice != null) {
            errorEvacuationSalesTransactionDetailEntity.setNewPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(itemNewPrice.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity.setNewPrice(itemNewPrice.getValue());
        }

        Price itemUnitPriceTaxExcluded = transactionItemDetail.getItemUnitPriceTaxExcluded();
        if (itemUnitPriceTaxExcluded != null) {
            errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemUnitPriceTaxExcluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setRetailUnitPriceTaxExcluded(itemUnitPriceTaxExcluded.getValue());
        }

        Price itemUnitPriceTaxIncluded = transactionItemDetail.getItemUnitPriceTaxIncluded();
        if (itemUnitPriceTaxIncluded != null) {
            errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemUnitPriceTaxIncluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setRetailUnitPriceTaxIncluded(itemUnitPriceTaxIncluded.getValue());
        }

        Price itemSalesAmtTaxExcluded = transactionItemDetail.getItemSalesAmtTaxExcluded();
        if (itemSalesAmtTaxExcluded != null) {
            errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSalesAmtTaxExcluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setSalesAmountTaxExcluded(itemSalesAmtTaxExcluded.getValue());
        }

        Price itemSalesAmtTaxIncluded = transactionItemDetail.getItemSalesAmtTaxIncluded();
        if (itemSalesAmtTaxIncluded != null) {
            errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSalesAmtTaxIncluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setSalesAmountTaxIncluded(itemSalesAmtTaxIncluded.getValue());
        }

        Price bundlePurchasePrice = transactionItemDetail.getBundlePurchasePrice();
        if (bundlePurchasePrice != null) {
            errorEvacuationSalesTransactionDetailEntity
                    .setBundlePurchaseApplicablePriceCurrencyCode(
                            PriceUtils.getCurrencyCode(bundlePurchasePrice.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setBundlePurchaseApplicablePrice(bundlePurchasePrice.getValue());
        }

        Price itemDiscountAmount = transactionItemDetail.getItemDiscountAmount();
        if (itemDiscountAmount != null) {
            errorEvacuationSalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode(
                    PriceUtils.getCurrencyCode(itemDiscountAmount.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setStoreItemDiscountSetting(itemDiscountAmount.getValue());
        }

        Price setSalesPrice = transactionItemDetail.getBundleSalesPrice();
        if (setSalesPrice != null) {
            errorEvacuationSalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode(
                    PriceUtils.getCurrencyCode(setSalesPrice.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setStoreBundleSalePrice(setSalesPrice.getValue());
        }
        return errorEvacuationSalesTransactionDetailEntity;
    }

    /**
     * Convert data to error evacuation sales transaction detail entity (for insert).
     * 
     * @param nonItemDetail NonItemDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction data error id.
     * @param salesTransactionType Sales transaction type.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @return TErrorEvacuationSalesTransactionDetailEntity.
     */
    public ErrorEvacuationSalesTransactionDetail convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
            NonItemDetail nonItemDetail, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId, String salesTransactionType,
            int detailCount, int itemDetailCount, String originalSalesTransactionErrorId) {

        ErrorEvacuationSalesTransactionDetail errorEvacuationSalesTransactionDetailEntity =
                new ErrorEvacuationSalesTransactionDetail();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionDetailEntity);
        modelMapper.map(nonItemDetail, errorEvacuationSalesTransactionDetailEntity);

        errorEvacuationSalesTransactionDetailEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionDetailEntity.setSalesTransactionId(salesTransactionId);
        errorEvacuationSalesTransactionDetailEntity.setOrderSubNumber(orderSubNumber);
        errorEvacuationSalesTransactionDetailEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        errorEvacuationSalesTransactionDetailEntity.setDetailSubNumber(detailCount);
        errorEvacuationSalesTransactionDetailEntity.setItemDetailSubNumber(itemDetailCount);
        if (nonItemDetail.getOrderStatusLastUpdateDateTime() != null) {
            errorEvacuationSalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(
                    nonItemDetail.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }

        if (StringUtils.isEmpty(salesTransactionType)) {
            errorEvacuationSalesTransactionDetailEntity.setSalesTransactionType(
                    nonItemDetail.getNonMdDetailListSalesTransactionType());
        } else {
            errorEvacuationSalesTransactionDetailEntity
                    .setSalesTransactionType(salesTransactionType);
        }

        errorEvacuationSalesTransactionDetailEntity
                .setL3PosProductName(nonItemDetail.getPosNonItemName());
        errorEvacuationSalesTransactionDetailEntity
                .setProductClassification(ProductClassification.NMITEM.getValue());
        errorEvacuationSalesTransactionDetailEntity.setNonMdCode(nonItemDetail.getNonItemCode());
        if (nonItemDetail.getNonItemQty() != null) {
            errorEvacuationSalesTransactionDetailEntity
                    .setDetailQuantity(new BigDecimal(nonItemDetail.getNonItemQty()));
        }

        errorEvacuationSalesTransactionDetailEntity
                .setCalculationUnavailableType(nonItemDetail.getNonCalculationNonItemType());
        errorEvacuationSalesTransactionDetailEntity
                .setTaxationType(nonItemDetail.getNonItemTaxationType());
        errorEvacuationSalesTransactionDetailEntity
                .setTaxSystemType(nonItemDetail.getNonItemTaxKind());

        Price nonItemNewPrice = nonItemDetail.getNonItemNewPrice();
        if (nonItemNewPrice != null) {
            errorEvacuationSalesTransactionDetailEntity.setNewPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemNewPrice.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity.setNewPrice(nonItemNewPrice.getValue());
        }

        Price nonItemUnitPriceTaxExcluded = nonItemDetail.getNonItemUnitPriceTaxExcluded();
        if (nonItemUnitPriceTaxExcluded != null) {
            errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemUnitPriceTaxExcluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setRetailUnitPriceTaxExcluded(nonItemUnitPriceTaxExcluded.getValue());
        }

        Price nonItemUnitPriceTaxIncluded = nonItemDetail.getNonItemUnitPriceTaxIncluded();
        if (nonItemUnitPriceTaxIncluded != null) {
            errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemUnitPriceTaxIncluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setRetailUnitPriceTaxIncluded(nonItemUnitPriceTaxIncluded.getValue());
        }

        Price nonItemSalesAmtTaxExcluded = nonItemDetail.getNonItemSalesAmtTaxExcluded();
        if (nonItemSalesAmtTaxExcluded != null) {
            errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemSalesAmtTaxExcluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setSalesAmountTaxExcluded(nonItemSalesAmtTaxExcluded.getValue());
        }

        Price nonItemSalesAmtTaxIncluded = nonItemDetail.getNonItemSalesAmtTaxIncluded();
        if (nonItemSalesAmtTaxIncluded != null) {
            errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemSalesAmtTaxIncluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDetailEntity
                    .setSalesAmountTaxIncluded(nonItemSalesAmtTaxIncluded.getValue());
        }

        return errorEvacuationSalesTransactionDetailEntity;

    }

    /**
     * Convert data to error evacuation sales transaction detail info entity (for insert).
     * 
     * @param nonItemInfo NonItemInfo data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @param orignialSalesTransactionErrorId Original transaction error id.
     * @return TErrorEvacuationSalesTransactionDetailInfoEntity.
     */
    public ErrorEvacuationSalesTransactionDetailInfo convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
            NonItemInfo nonItemInfo, String userId, String salesTransactionId, int orderSubNumber,
            String salesTransactionErrorId, int detailCount, int itemDetailCount,
            String orignialSalesTransactionErrorId) {
        ErrorEvacuationSalesTransactionDetailInfo errorEvacuationSalesTransactionDetailInfoEntity =
                new ErrorEvacuationSalesTransactionDetailInfo();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionDetailInfoEntity);
        modelMapper.map(nonItemInfo, errorEvacuationSalesTransactionDetailInfoEntity);

        errorEvacuationSalesTransactionDetailInfoEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionDetailInfoEntity.setSalesTransactionId(salesTransactionId);
        errorEvacuationSalesTransactionDetailInfoEntity.setOrderSubNumber(orderSubNumber);
        errorEvacuationSalesTransactionDetailInfoEntity
                .setSalesTransactionErrorId(orignialSalesTransactionErrorId);
        errorEvacuationSalesTransactionDetailInfoEntity.setDetailSubNumber(detailCount);
        errorEvacuationSalesTransactionDetailInfoEntity.setItemDetailSubNumber(itemDetailCount);
        return errorEvacuationSalesTransactionDetailInfoEntity;
    }

    /**
     * Convert data to error evacuation sales transaction discount entity (for insert).
     * 
     * @param nonItemDiscountDetail NonItemDiscountDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemDiscountSubNumber Item discount sub number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @return TErrorEvacuationSalesTransactionDiscountEntity.
     */
    public ErrorEvacuationSalesTransactionDiscount convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
            NonItemDiscountDetail nonItemDiscountDetail, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId, int detailCount,
            int itemDiscountSubNumber, String originalSalesTransactionErrorId) {

        ErrorEvacuationSalesTransactionDiscount errorEvacuationSalesTransactionDiscountEntity =
                new ErrorEvacuationSalesTransactionDiscount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionDiscountEntity);

        errorEvacuationSalesTransactionDiscountEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionDiscountEntity.setSalesTransactionId(salesTransactionId);
        errorEvacuationSalesTransactionDiscountEntity.setOrderSubNumber(orderSubNumber);
        errorEvacuationSalesTransactionDiscountEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        errorEvacuationSalesTransactionDiscountEntity.setDetailSubNumber(detailCount);

        errorEvacuationSalesTransactionDiscountEntity
                .setPromotionType(nonItemDiscountDetail.getNonItemPromotionType());

        if (StringUtils.isEmpty(nonItemDiscountDetail.getNonItemPromotionNumber())) {
            errorEvacuationSalesTransactionDiscountEntity.setPromotionNo("0");
        } else {
            errorEvacuationSalesTransactionDiscountEntity
                    .setPromotionNo(nonItemDiscountDetail.getNonItemPromotionNumber());
        }

        errorEvacuationSalesTransactionDiscountEntity
                .setStoreDiscountType(nonItemDiscountDetail.getNonItemStoreDiscountType());
        errorEvacuationSalesTransactionDiscountEntity
                .setItemDiscountSubNumber(itemDiscountSubNumber);
        errorEvacuationSalesTransactionDiscountEntity
                .setQuantityCode(nonItemDiscountDetail.getNonItemQuantityCode());
        errorEvacuationSalesTransactionDiscountEntity
                .setDiscountQuantity(nonItemDiscountDetail.getNonItemDiscountQty());

        Price nonItemDiscountAmtTaxExcluded =
                nonItemDiscountDetail.getNonItemDiscountAmtTaxExcluded();
        if (nonItemDiscountAmtTaxExcluded != null) {
            errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemDiscountAmtTaxExcluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDiscountEntity
                    .setDiscountAmountTaxExcluded(nonItemDiscountAmtTaxExcluded.getValue());
        }

        Price nonItemDiscountAmtTaxIncluded =
                nonItemDiscountDetail.getNonItemDiscountAmtTaxIncluded();
        if (nonItemDiscountAmtTaxIncluded != null) {
            errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemDiscountAmtTaxIncluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDiscountEntity
                    .setDiscountAmountTaxIncluded(nonItemDiscountAmtTaxIncluded.getValue());
        }
        return errorEvacuationSalesTransactionDiscountEntity;
    }

    /**
     * Convert data to error evacuation sales transaction tax entity (for insert).
     * 
     * @param nonItemTaxDetail NonItemTaxDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param taxSubNumber Tax sub number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @return TErrorEvacuationSalesTransactionTaxEntity.
     */
    public ErrorEvacuationSalesTransactionTax convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
            NonItemTaxDetail nonItemTaxDetail, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId, int detailCount, int taxSubNumber,
            String originalSalesTransactionErrorId) {
        ErrorEvacuationSalesTransactionTax errorEvacuationSalesTransactionTaxEntity =
                new ErrorEvacuationSalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionTaxEntity);

        errorEvacuationSalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionTaxEntity.setSalesTransactionId(salesTransactionId);
        errorEvacuationSalesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        errorEvacuationSalesTransactionTaxEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        errorEvacuationSalesTransactionTaxEntity.setDetailSubNumber(detailCount);

        errorEvacuationSalesTransactionTaxEntity.setTaxGroup(nonItemTaxDetail.getNonItemTaxType());
        errorEvacuationSalesTransactionTaxEntity.setTaxSubNumber(taxSubNumber);
        errorEvacuationSalesTransactionTaxEntity
                .setTaxAmountSign(nonItemTaxDetail.getNonItemTaxAmountSign());
        errorEvacuationSalesTransactionTaxEntity.setTaxRate(nonItemTaxDetail.getNonItemTaxRate());
        errorEvacuationSalesTransactionTaxEntity.setTaxName(nonItemTaxDetail.getNonItemTaxName());

        Price nonItemTaxAmt = nonItemTaxDetail.getNonItemTaxAmt();
        if (nonItemTaxAmt != null) {
            errorEvacuationSalesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemTaxAmt.getCurrencyCode()));
            errorEvacuationSalesTransactionTaxEntity.setTaxAmountValue(nonItemTaxAmt.getValue());
        }
        return errorEvacuationSalesTransactionTaxEntity;
    }

    /**
     * Convert data to error evacuation sales transaction discount entity (for insert).
     * 
     * @param itemDiscount ItemDiscount data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemDiscountSubNumber Item discount sub number.
     * @param originalSalesTransactionErrorId Original transaction data id.
     * @return TErrorEvacuationSalesTransactionDiscountEntity.
     */
    public ErrorEvacuationSalesTransactionDiscount convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
            ItemDiscount itemDiscount, String userId, String salesTransactionId, int orderSubNumber,
            String salesTransactionErrorId, int detailCount, int itemDiscountSubNumber,
            String originalSalesTransactionErrorId) {
        ErrorEvacuationSalesTransactionDiscount errorEvacuationSalesTransactionDiscountEntity =
                new ErrorEvacuationSalesTransactionDiscount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionDiscountEntity);

        errorEvacuationSalesTransactionDiscountEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionDiscountEntity.setSalesTransactionId(salesTransactionId);
        errorEvacuationSalesTransactionDiscountEntity.setOrderSubNumber(orderSubNumber);
        errorEvacuationSalesTransactionDiscountEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        errorEvacuationSalesTransactionDiscountEntity.setDetailSubNumber(detailCount);

        errorEvacuationSalesTransactionDiscountEntity
                .setPromotionType(itemDiscount.getItemPromotionType());
        errorEvacuationSalesTransactionDiscountEntity
                .setPromotionNo(itemDiscount.getItemPromotionNumber());

        errorEvacuationSalesTransactionDiscountEntity
                .setStoreDiscountType(itemDiscount.getItemStoreDiscountType());
        errorEvacuationSalesTransactionDiscountEntity
                .setItemDiscountSubNumber(itemDiscountSubNumber);
        errorEvacuationSalesTransactionDiscountEntity
                .setQuantityCode(itemDiscount.getItemQuantityCode());
        errorEvacuationSalesTransactionDiscountEntity
                .setDiscountQuantity(itemDiscount.getItemDiscountQty());

        Price itemDiscountAmtTaxExcluded = itemDiscount.getItemDiscountAmtTaxExcluded();
        if (itemDiscountAmtTaxExcluded != null) {
            errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemDiscountAmtTaxExcluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDiscountEntity
                    .setDiscountAmountTaxExcluded(itemDiscountAmtTaxExcluded.getValue());
        }

        Price itemDiscountAmtTaxIncluded = itemDiscount.getItemDiscountAmtTaxIncluded();
        if (itemDiscountAmtTaxIncluded != null) {
            errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemDiscountAmtTaxIncluded.getCurrencyCode()));
            errorEvacuationSalesTransactionDiscountEntity
                    .setDiscountAmountTaxIncluded(itemDiscountAmtTaxIncluded.getValue());
        }
        return errorEvacuationSalesTransactionDiscountEntity;
    }

    /**
     * Convert data to error evacuation sales transaction tax entity (for insert).
     * 
     * @param itemTaxDetail ItemTaxDetail data.
     * @param userId User id.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param detailCount Detail count.
     * @param taxSubNumber Tax sub number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @return TErrorEvacuationSalesTransactionTaxEntity.
     */
    public ErrorEvacuationSalesTransactionTax convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
            ItemTaxDetail itemTaxDetail, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId, int detailCount, int taxSubNumber,
            String originalSalesTransactionErrorId) {
        ErrorEvacuationSalesTransactionTax errorEvacuationSalesTransactionTaxEntity =
                new ErrorEvacuationSalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionTaxEntity);
        errorEvacuationSalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionTaxEntity.setSalesTransactionId(salesTransactionId);
        errorEvacuationSalesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        errorEvacuationSalesTransactionTaxEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        errorEvacuationSalesTransactionTaxEntity.setDetailSubNumber(detailCount);

        errorEvacuationSalesTransactionTaxEntity.setTaxGroup(itemTaxDetail.getItemTaxType());
        errorEvacuationSalesTransactionTaxEntity.setTaxSubNumber(taxSubNumber);
        errorEvacuationSalesTransactionTaxEntity
                .setTaxAmountSign(itemTaxDetail.getItemTaxAmountSign());
        errorEvacuationSalesTransactionTaxEntity.setTaxRate(itemTaxDetail.getItemTaxRate());
        errorEvacuationSalesTransactionTaxEntity.setTaxName(itemTaxDetail.getItemTaxName());

        Price itemTaxAmt = itemTaxDetail.getItemTaxAmt();
        if (itemTaxAmt != null) {
            errorEvacuationSalesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(itemTaxAmt.getCurrencyCode()));
            errorEvacuationSalesTransactionTaxEntity.setTaxAmountValue(itemTaxAmt.getValue());
        }
        return errorEvacuationSalesTransactionTaxEntity;
    }

    /**
     * Convert data to error evacuation sales transaction tender entity (for insert).
     * 
     * @param salesTransactionTender SalesTransactionTender data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction data error id.
     * @param tenderSubNumber Tender sub number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @return TErrorEvacuationSalesTransactionTenderEntity.
     */
    public ErrorEvacuationSalesTransactionTender convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
            SalesTransactionTender salesTransactionTender, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId, int tenderSubNumber,
            String originalSalesTransactionErrorId) {
        ErrorEvacuationSalesTransactionTender errorEvacuationSalesTransactionTenderEntity =
                new ErrorEvacuationSalesTransactionTender();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionTenderEntity);
        modelMapper.map(salesTransactionTender, errorEvacuationSalesTransactionTenderEntity);

        errorEvacuationSalesTransactionTenderEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionTenderEntity.setSalesTransactionId(salesTransactionId);
        errorEvacuationSalesTransactionTenderEntity.setOrderSubNumber(orderSubNumber);
        errorEvacuationSalesTransactionTenderEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);

        errorEvacuationSalesTransactionTenderEntity.setTenderSubNumber(tenderSubNumber);

        errorEvacuationSalesTransactionTenderEntity
                .setTenderGroup(salesTransactionTender.getTenderGroupId());

        Price taxIncludedPaymentAmount = salesTransactionTender.getTaxIncludedPaymentAmount();
        if (taxIncludedPaymentAmount != null) {
            errorEvacuationSalesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(taxIncludedPaymentAmount.getCurrencyCode()));
            errorEvacuationSalesTransactionTenderEntity
                    .setTaxIncludedPaymentAmountValue(taxIncludedPaymentAmount.getValue());
        }
        return errorEvacuationSalesTransactionTenderEntity;
    }

    /**
     * Convert data to error evacuation sales transaction tender info entity (for insert).
     * 
     * @param tenderInfo TenderInfo data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param tenderGroupId Tender group id.
     * @param tenderId Tender id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param tenderSubNumber Tender sub number.
     * @return TErrorEvacuationSalesTransactionTenderInfoEntity.
     */
    public ErrorEvacuationSalesTransactionTenderInfo convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
            TenderInfo tenderInfo, String userId, String salesTransactionId, int orderSubNumber,
            String tenderGroupId, String tenderId, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, int tenderSubNumber) {
        ErrorEvacuationSalesTransactionTenderInfo errorEvacuationSalesTransactionTenderInfoEntity =
                new ErrorEvacuationSalesTransactionTenderInfo();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionTenderInfoEntity);
        modelMapper.map(tenderInfo, errorEvacuationSalesTransactionTenderInfoEntity);

        errorEvacuationSalesTransactionTenderInfoEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionTenderInfoEntity.setSalesTransactionId(salesTransactionId);
        errorEvacuationSalesTransactionTenderInfoEntity.setOrderSubNumber(orderSubNumber);
        errorEvacuationSalesTransactionTenderInfoEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        errorEvacuationSalesTransactionTenderInfoEntity.setTenderSubNumber(tenderSubNumber);

        errorEvacuationSalesTransactionTenderInfoEntity.setTenderId(tenderId);
        errorEvacuationSalesTransactionTenderInfoEntity.setTenderGroup(tenderGroupId);
        errorEvacuationSalesTransactionTenderInfoEntity
                .setDiscountRate(tenderInfo.getDiscountRate());

        Price discountAmount = tenderInfo.getDiscountAmount();
        if (discountAmount != null) {
            errorEvacuationSalesTransactionTenderInfoEntity.setDiscountValueCurrencyCode(
                    PriceUtils.getCurrencyCode(discountAmount.getCurrencyCode()));
            errorEvacuationSalesTransactionTenderInfoEntity
                    .setDiscountValue(discountAmount.getValue());
        }

        Price couponMinUsageAmountThreshold = tenderInfo.getCouponMinUsageAmountThreshold();
        if (couponMinUsageAmountThreshold != null) {
            errorEvacuationSalesTransactionTenderInfoEntity
                    .setCouponMinUsageAmountThresholdCurrencyCode(PriceUtils
                            .getCurrencyCode(couponMinUsageAmountThreshold.getCurrencyCode()));
            errorEvacuationSalesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdValue(
                    couponMinUsageAmountThreshold.getValue());
        }

        Price couponDiscountAmountSetting = tenderInfo.getCouponDiscountAmountSetting();
        if (couponDiscountAmountSetting != null) {
            errorEvacuationSalesTransactionTenderInfoEntity
                    .setCouponDiscountAmountSettingCurrencyCode(PriceUtils
                            .getCurrencyCode(couponDiscountAmountSetting.getCurrencyCode()));
            errorEvacuationSalesTransactionTenderInfoEntity
                    .setCouponDiscountAmountSettingValue(couponDiscountAmountSetting.getValue());
        }
        return errorEvacuationSalesTransactionTenderInfoEntity;
    }

    /**
     * Convert data to error evacuation sales transaction tax entity (for insert).
     * 
     * @param salesTransactionTaxDetail SalesTransactionTaxDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param taxSubNumber Tax sub number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @return TErrorEvacuationSalesTransactionTaxEntity.
     */
    public ErrorEvacuationSalesTransactionTax convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
            SalesTransactionTaxDetail salesTransactionTaxDetail, String userId,
            String salesTransactionId, int orderSubNumber, String salesTransactionErrorId,
            int detailCount, int taxSubNumber, String originalSalesTransactionErrorId) {
        ErrorEvacuationSalesTransactionTax errorEvacuationSalesTransactionTaxEntity =
                new ErrorEvacuationSalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionTaxEntity);
        modelMapper.map(salesTransactionTaxDetail, errorEvacuationSalesTransactionTaxEntity);

        errorEvacuationSalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionTaxEntity.setSalesTransactionId(salesTransactionId);
        errorEvacuationSalesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        errorEvacuationSalesTransactionTaxEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        errorEvacuationSalesTransactionTaxEntity.setDetailSubNumber(detailCount);

        errorEvacuationSalesTransactionTaxEntity.setTaxSubNumber(taxSubNumber);
        Price taxAmount = salesTransactionTaxDetail.getTaxAmount();
        if (taxAmount != null) {
            errorEvacuationSalesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(taxAmount.getCurrencyCode()));
            errorEvacuationSalesTransactionTaxEntity.setTaxAmountValue(taxAmount.getValue());
        }
        return errorEvacuationSalesTransactionTaxEntity;
    }

    /**
     * Convert data to error evacuation sales transaction total amount entity (for insert).
     * 
     * @param salesTransactionTotal SalesTransactionTotal data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction data error id.
     * @param totalAmountSubNumber Total amount sub number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @return TErrorEvacuationSalesTransactionTotalAmountEntity.
     */
    public ErrorEvacuationSalesTransactionTotalAmount convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
            SalesTransactionTotal salesTransactionTotal, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId, int totalAmountSubNumber,
            String originalSalesTransactionErrorId) {
        ErrorEvacuationSalesTransactionTotalAmount errorEvacuationSalesTransactionTotalAmountEntity =
                new ErrorEvacuationSalesTransactionTotalAmount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, errorEvacuationSalesTransactionTotalAmountEntity);
        modelMapper.map(salesTransactionTotal, errorEvacuationSalesTransactionTotalAmountEntity);

        errorEvacuationSalesTransactionTotalAmountEntity.setTransactionId(salesTransactionErrorId);
        errorEvacuationSalesTransactionTotalAmountEntity.setSalesTransactionId(salesTransactionId);
        errorEvacuationSalesTransactionTotalAmountEntity.setOrderSubNumber(orderSubNumber);
        errorEvacuationSalesTransactionTotalAmountEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);

        errorEvacuationSalesTransactionTotalAmountEntity
                .setTotalAmountSubNumber(totalAmountSubNumber);
        errorEvacuationSalesTransactionTotalAmountEntity
                .setTaxRate(salesTransactionTotal.getConsumptionTaxRate());

        Price totalAmountTaxExcluded = salesTransactionTotal.getTotalAmountTaxExcluded();
        if (totalAmountTaxExcluded != null) {
            errorEvacuationSalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(totalAmountTaxExcluded.getCurrencyCode()));
            errorEvacuationSalesTransactionTotalAmountEntity
                    .setTotalAmountTaxExcludedValue(totalAmountTaxExcluded.getValue());
        }

        Price totalAmountTaxIncluded = salesTransactionTotal.getTotalAmountTaxIncluded();
        if (totalAmountTaxIncluded != null) {
            errorEvacuationSalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(totalAmountTaxIncluded.getCurrencyCode()));
            errorEvacuationSalesTransactionTotalAmountEntity
                    .setTotalAmountTaxIncludedValue(totalAmountTaxIncluded.getValue());
        }
        return errorEvacuationSalesTransactionTotalAmountEntity;
    }
}
