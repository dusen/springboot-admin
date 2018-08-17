/**
 * @(#)HistoryTableDataConverter.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.converter;

import java.math.BigDecimal;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.common.type.HistoryType;
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
import com.fastretailing.dcp.storecommon.dto.Price;
import com.fastretailing.dcp.storecommon.util.PriceUtils;

/**
 * The class is used to convert data to history table's entity.
 *
 */
@Component
public class HistoryTableDataConverter {

    /** Common data processor. */
    @Autowired
    private CommonDataProcessor commonDataProcessor;

    /** Model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Convert data to alteration history order information entity (for alteration).
     * 
     * @param salesErrorOrderInformationEntity TSalesErrorOrderInformation entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistoryOrderInformationEntity.
     */
    public AlterationHistoryOrderInformation convertTAlterationHistoryOrderInformationEntityForInsertBeforeEdit(
            SalesErrorSalesOrderInformation salesErrorOrderInformationEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistoryOrderInformation alterationHistoryOrderInformationEntity =
                new AlterationHistoryOrderInformation();
        modelMapper.map(salesErrorOrderInformationEntity, alterationHistoryOrderInformationEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistoryOrderInformationEntity);

        alterationHistoryOrderInformationEntity.setTransactionId(salesTransactionErrorId);
        alterationHistoryOrderInformationEntity
                .setSalesTransactionErrorId(salesErrorOrderInformationEntity.getTransactionId());
        alterationHistoryOrderInformationEntity.setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistoryOrderInformationEntity;
    }

    /**
     * Convert data to alteration history order information entity (for alteration).
     * 
     * @param transactionImportData Transaction import data.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @return TAlterationHistoryOrderInformationEntity.
     */
    public AlterationHistoryOrderInformation convertTAlterationHistoryOrderInformationEntityForInsertAfterEdit(
            TransactionImportData transactionImportData, String userId,
            String salesTransactionErrorId, String originalSalesTransactionErrorId) {
        AlterationHistoryOrderInformation alterationHistoryOrderInformationEntity =
                new AlterationHistoryOrderInformation();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistoryOrderInformationEntity);
        modelMapper.map(transactionImportData, alterationHistoryOrderInformationEntity);

        if (transactionImportData.getDataCorrectionEditingFlag() != null) {
            alterationHistoryOrderInformationEntity.setDataAlterationEditingFlag(
                    transactionImportData.getDataCorrectionEditingFlag());
        }
        alterationHistoryOrderInformationEntity
                .setDataAlterationUserId(transactionImportData.getDataCorrectionUserId());
        if (transactionImportData.getOrderConfirmationDateTime() != null) {
            alterationHistoryOrderInformationEntity.setOrderConfirmationDateTime(
                    transactionImportData.getOrderConfirmationDateTime().toLocalDateTime());
        }

        alterationHistoryOrderInformationEntity.setTransactionId(salesTransactionErrorId);
        alterationHistoryOrderInformationEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistoryOrderInformationEntity.setHistoryType(HistoryType.AFTER.getValue());
        return alterationHistoryOrderInformationEntity;
    }

    /**
     * Convert data to alteration history sales transaction header entity (for alteration).
     * 
     * @param salesErrorSalesTransactionHeaderEntity TSalesErrorSalesTransactionHeaderEntity entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionHeaderEntity.
     */
    public AlterationHistorySalesTransactionHeader convertTAlterationHistorySalesTransactionHeaderEntityForInsertBeforeEdit(
            SalesErrorSalesTransactionHeader salesErrorSalesTransactionHeaderEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistorySalesTransactionHeader alterationHistorySalesTransactionHeaderEntity =
                new AlterationHistorySalesTransactionHeader();
        modelMapper.map(salesErrorSalesTransactionHeaderEntity,
                alterationHistorySalesTransactionHeaderEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionHeaderEntity);
        alterationHistorySalesTransactionHeaderEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionHeaderEntity.setSalesTransactionErrorId(
                salesErrorSalesTransactionHeaderEntity.getTransactionId());
        alterationHistorySalesTransactionHeaderEntity.setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistorySalesTransactionHeaderEntity;
    }

    /**
     * Convert data to alteration history sales transaction header entity (for alteration).
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction id.
     * @return TAlterationHistorySalesTransactionHeaderEntity.
     */
    public AlterationHistorySalesTransactionHeader convertTAlterationHistorySalesTransactionHeaderEntityForInsertAfterEdit(
            Transaction transaction, String userId, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, int salesTransactionSubNumber) {

        AlterationHistorySalesTransactionHeader alterationHistorySalesTransactionHeaderEntity =
                new AlterationHistorySalesTransactionHeader();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionHeaderEntity);
        modelMapper.map(transaction, alterationHistorySalesTransactionHeaderEntity);

        alterationHistorySalesTransactionHeaderEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionHeaderEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionHeaderEntity.setHistoryType(HistoryType.AFTER.getValue());

        alterationHistorySalesTransactionHeaderEntity
                .setSalesTransactionSubNumber(salesTransactionSubNumber);
        alterationHistorySalesTransactionHeaderEntity
                .setSalesTransactionType(transaction.getTransactionType());

        if (transaction.getConsistencySalesFlag() != null) {
            alterationHistorySalesTransactionHeaderEntity
                    .setConsistencySalesFlag(transaction.getConsistencySalesFlag());
        }
        if (transaction.getEmployeeSaleFlag() != null) {
            alterationHistorySalesTransactionHeaderEntity
                    .setEmployeeSaleFlag(transaction.getEmployeeSaleFlag());
        }
        if (transaction.getReceiptIssuedFlag() != null) {
            alterationHistorySalesTransactionHeaderEntity
                    .setReceiptIssuedFlag(transaction.getReceiptIssuedFlag());
        }
        if (transaction.getEReceiptTargetFlag() != null) {
            alterationHistorySalesTransactionHeaderEntity
                    .setEReceiptTargetFlag(transaction.getEReceiptTargetFlag());
        }
        if (transaction.getDataCreationDateTime() != null) {
            alterationHistorySalesTransactionHeaderEntity.setDataCreationDateTime(
                    transaction.getDataCreationDateTime().toLocalDateTime());
        }
        if (transaction.getOrderStatusLastUpdateDateTime() != null) {
            alterationHistorySalesTransactionHeaderEntity.setOrderStatusLastUpdateDateTime(
                    transaction.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }
        if (transaction.getOrderCancellationDate() != null) {
            alterationHistorySalesTransactionHeaderEntity.setOrderCancelledDateTime(
                    transaction.getOrderCancellationDate().toLocalDateTime());
        }

        Price deposit = transaction.getDeposit();
        if (deposit != null) {
            alterationHistorySalesTransactionHeaderEntity
                    .setDepositCurrencyCode(PriceUtils.getCurrencyCode(deposit.getCurrencyCode()));
            alterationHistorySalesTransactionHeaderEntity.setDepositValue(deposit.getValue());
        }

        Price change = transaction.getChange();
        if (change != null) {
            alterationHistorySalesTransactionHeaderEntity
                    .setChangeCurrencyCode(PriceUtils.getCurrencyCode(change.getCurrencyCode()));
            alterationHistorySalesTransactionHeaderEntity.setChangeValue(change.getValue());
        }

        Price amountRate = transaction.getSalesTransactionDiscountAmountRate();
        if (amountRate != null) {
            alterationHistorySalesTransactionHeaderEntity
                    .setSalesTransactionDiscountAmountRateCurrencyCode(
                            PriceUtils.getCurrencyCode(amountRate.getCurrencyCode()));
            alterationHistorySalesTransactionHeaderEntity
                    .setSalesTransactionDiscountAmountRate(amountRate.getValue());
        }
        return alterationHistorySalesTransactionHeaderEntity;
    }

    /**
     * Convert data to alteration history sales transaction detail entity (for alteration).
     * 
     * @param salesErrorSalesTransactionDetailEntity TSalesErrorSalesTransactionDetailEntity entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionDetailEntity.
     */
    public AlterationHistorySalesTransactionDetail convertTAlterationHistorySalesTransactionDetailEntityForInsertBeforeEdit(
            SalesErrorSalesTransactionDetail salesErrorSalesTransactionDetailEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistorySalesTransactionDetail alterationHistorySalesTransactionDetailEntity =
                new AlterationHistorySalesTransactionDetail();
        modelMapper.map(salesErrorSalesTransactionDetailEntity,
                alterationHistorySalesTransactionDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionDetailEntity);

        alterationHistorySalesTransactionDetailEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionDetailEntity.setSalesTransactionErrorId(
                salesErrorSalesTransactionDetailEntity.getTransactionId());
        alterationHistorySalesTransactionDetailEntity.setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistorySalesTransactionDetailEntity;
    }

    /**
     * Convert data to alteration history sales transaction detail entity (for alteration).
     * 
     * @param transactionItemDetail TransactionItemDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @return TAlterationHistorySalesTransactionDetailEntity.
     */
    public AlterationHistorySalesTransactionDetail convertTAlterationHistorySalesTransactionDetailEntityForInsertAfterEdit(
            TransactionItemDetail transactionItemDetail, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, int detailCount, int itemDetailCount) {
        AlterationHistorySalesTransactionDetail alterationHistorySalesTransactionDetailEntity =
                new AlterationHistorySalesTransactionDetail();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionDetailEntity);
        modelMapper.map(transactionItemDetail, alterationHistorySalesTransactionDetailEntity);

        alterationHistorySalesTransactionDetailEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionDetailEntity.setSalesTransactionId(salesTransactionId);
        alterationHistorySalesTransactionDetailEntity.setOrderSubNumber(orderSubNumber);
        alterationHistorySalesTransactionDetailEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionDetailEntity.setDetailSubNumber(detailCount);
        alterationHistorySalesTransactionDetailEntity.setItemDetailSubNumber(itemDetailCount);
        alterationHistorySalesTransactionDetailEntity.setHistoryType(HistoryType.AFTER.getValue());

        if (transactionItemDetail.getOrderStatusLastUpdateDateTime() != null) {
            alterationHistorySalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(
                    transactionItemDetail.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }

        alterationHistorySalesTransactionDetailEntity
                .setSalesTransactionType(transactionItemDetail.getDetailListSalesTransactionType());
        alterationHistorySalesTransactionDetailEntity
                .setDisplayL2ItemCode(transactionItemDetail.getViewL2ItemCode());
        alterationHistorySalesTransactionDetailEntity
                .setProductClassification(ProductClassification.ITEM.getValue());
        if (transactionItemDetail.getDeptCode() != null) {
            alterationHistorySalesTransactionDetailEntity
                    .setMajorCategoryCode(String.valueOf(transactionItemDetail.getDeptCode()));
        }
        if (transactionItemDetail.getItemQty() != null) {
            alterationHistorySalesTransactionDetailEntity
                    .setDetailQuantity(new BigDecimal(transactionItemDetail.getItemQty()));
        }
        if (transactionItemDetail.getBundlePurchaseQty() != null) {
            alterationHistorySalesTransactionDetailEntity.setBundlePurchaseApplicableQuantity(
                    new BigDecimal(transactionItemDetail.getBundlePurchaseQty()));
        }
        if (Objects.nonNull(transactionItemDetail.getBundleSalesFlag())) {
            alterationHistorySalesTransactionDetailEntity
                    .setStoreBundleSaleFlag(transactionItemDetail.getBundleSalesFlag());
        }
        alterationHistorySalesTransactionDetailEntity
                .setSetSalesDetailIndex(transactionItemDetail.getBundleSalesDetailIndex());
        alterationHistorySalesTransactionDetailEntity
                .setStoreItemDiscountType(transactionItemDetail.getItemMountDiscountType());
        alterationHistorySalesTransactionDetailEntity
                .setTaxationType(transactionItemDetail.getItemTaxationType());
        alterationHistorySalesTransactionDetailEntity
                .setTaxSystemType(transactionItemDetail.getItemTaxKind());

        Price itemCost = transactionItemDetail.getItemCost();
        if (itemCost != null) {
            alterationHistorySalesTransactionDetailEntity.setItemCostCurrencyCode(
                    PriceUtils.getCurrencyCode(itemCost.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity.setItemCostValue(itemCost.getValue());
        }

        Price initialSellingPrice = transactionItemDetail.getInitialSellingPrice();
        if (initialSellingPrice != null) {
            alterationHistorySalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(initialSellingPrice.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setInitialSellingPrice(initialSellingPrice.getValue());
        }

        Price itemSellingPrice = transactionItemDetail.getBItemSellingPrice();
        if (itemSellingPrice != null) {
            alterationHistorySalesTransactionDetailEntity.setBclassPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSellingPrice.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setBclassPrice(itemSellingPrice.getValue());
        }

        Price itemNewPrice = transactionItemDetail.getItemNewPrice();
        if (itemNewPrice != null) {
            alterationHistorySalesTransactionDetailEntity.setNewPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(itemNewPrice.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity.setNewPrice(itemNewPrice.getValue());
        }

        Price itemUnitPriceTaxExcluded = transactionItemDetail.getItemUnitPriceTaxExcluded();
        if (itemUnitPriceTaxExcluded != null) {
            alterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemUnitPriceTaxExcluded.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setRetailUnitPriceTaxExcluded(itemUnitPriceTaxExcluded.getValue());
        }

        Price itemUnitPriceTaxIncluded = transactionItemDetail.getItemUnitPriceTaxIncluded();
        if (itemUnitPriceTaxIncluded != null) {
            alterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemUnitPriceTaxIncluded.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setRetailUnitPriceTaxIncluded(itemUnitPriceTaxIncluded.getValue());
        }

        Price itemSalesAmtTaxExcluded = transactionItemDetail.getItemSalesAmtTaxExcluded();
        if (itemSalesAmtTaxExcluded != null) {
            alterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSalesAmtTaxExcluded.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setSalesAmountTaxExcluded(itemSalesAmtTaxExcluded.getValue());
        }

        Price itemSalesAmtTaxIncluded = transactionItemDetail.getItemSalesAmtTaxIncluded();
        if (itemSalesAmtTaxIncluded != null) {
            alterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSalesAmtTaxIncluded.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setSalesAmountTaxIncluded(itemSalesAmtTaxIncluded.getValue());
        }

        Price bundlePurchasePrice = transactionItemDetail.getBundlePurchasePrice();
        if (bundlePurchasePrice != null) {
            alterationHistorySalesTransactionDetailEntity
                    .setBundlePurchaseApplicablePriceCurrencyCode(
                            PriceUtils.getCurrencyCode(bundlePurchasePrice.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setBundlePurchaseApplicablePrice(bundlePurchasePrice.getValue());
        }

        Price itemDiscountAmount = transactionItemDetail.getItemDiscountAmount();
        if (itemDiscountAmount != null) {
            alterationHistorySalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode(
                    PriceUtils.getCurrencyCode(itemDiscountAmount.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setStoreItemDiscountSetting(itemDiscountAmount.getValue());
        }

        Price setSalesPrice = transactionItemDetail.getBundleSalesPrice();
        if (setSalesPrice != null) {
            alterationHistorySalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode(
                    PriceUtils.getCurrencyCode(setSalesPrice.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setStoreBundleSalePrice(setSalesPrice.getValue());
        }
        return alterationHistorySalesTransactionDetailEntity;
    }

    /**
     * Convert data to alteration history sales transaction detail entity (for alteration).
     * 
     * @param salesErrorSalesTransactionDetailEntity TSalesErrorSalesTransactionDetailEntity entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionDetailEntity.
     */
    public AlterationHistorySalesTransactionDetail convertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideBeforeEdit(
            SalesErrorSalesTransactionDetail salesErrorSalesTransactionDetailEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistorySalesTransactionDetail alterationHistorySalesTransactionDetailEntity =
                new AlterationHistorySalesTransactionDetail();
        modelMapper.map(salesErrorSalesTransactionDetailEntity,
                alterationHistorySalesTransactionDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionDetailEntity);

        alterationHistorySalesTransactionDetailEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionDetailEntity.setSalesTransactionErrorId(
                salesErrorSalesTransactionDetailEntity.getTransactionId());
        alterationHistorySalesTransactionDetailEntity.setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistorySalesTransactionDetailEntity;
    }

    /**
     * Convert data to alteration history sales transaction detail entity (for alteration).
     * 
     * @param nonItemDetail NonItemDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param salesTransactionType Sales transaction type.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @return TAlterationHistorySalesTransactionDetailEntity.
     */
    public AlterationHistorySalesTransactionDetail convertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideAfterEdit(
            NonItemDetail nonItemDetail, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, String salesTransactionType, int detailCount,
            int itemDetailCount) {
        AlterationHistorySalesTransactionDetail alterationHistorySalesTransactionDetailEntity =
                new AlterationHistorySalesTransactionDetail();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionDetailEntity);
        modelMapper.map(nonItemDetail, alterationHistorySalesTransactionDetailEntity);

        alterationHistorySalesTransactionDetailEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionDetailEntity.setSalesTransactionId(salesTransactionId);
        alterationHistorySalesTransactionDetailEntity.setOrderSubNumber(orderSubNumber);
        alterationHistorySalesTransactionDetailEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionDetailEntity.setDetailSubNumber(detailCount);
        alterationHistorySalesTransactionDetailEntity.setItemDetailSubNumber(itemDetailCount);
        alterationHistorySalesTransactionDetailEntity.setHistoryType(HistoryType.AFTER.getValue());

        if (nonItemDetail.getOrderStatusLastUpdateDateTime() != null) {
            alterationHistorySalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(
                    nonItemDetail.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }

        if (StringUtils.isEmpty(salesTransactionType)) {
            alterationHistorySalesTransactionDetailEntity.setSalesTransactionType(
                    nonItemDetail.getNonMdDetailListSalesTransactionType());
        } else {
            alterationHistorySalesTransactionDetailEntity
                    .setSalesTransactionType(salesTransactionType);
        }


        alterationHistorySalesTransactionDetailEntity
                .setL3PosProductName(nonItemDetail.getPosNonItemName());
        alterationHistorySalesTransactionDetailEntity
                .setProductClassification(ProductClassification.NMITEM.getValue());
        alterationHistorySalesTransactionDetailEntity.setNonMdCode(nonItemDetail.getNonItemCode());
        alterationHistorySalesTransactionDetailEntity
                .setDetailQuantity(new BigDecimal(nonItemDetail.getNonItemQty()));

        alterationHistorySalesTransactionDetailEntity
                .setCalculationUnavailableType(nonItemDetail.getNonCalculationNonItemType());
        alterationHistorySalesTransactionDetailEntity
                .setTaxationType(nonItemDetail.getNonItemTaxationType());
        alterationHistorySalesTransactionDetailEntity
                .setTaxSystemType(nonItemDetail.getNonItemTaxKind());

        Price nonItemNewPrice = nonItemDetail.getNonItemNewPrice();
        if (nonItemNewPrice != null) {
            alterationHistorySalesTransactionDetailEntity.setNewPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemNewPrice.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity.setNewPrice(nonItemNewPrice.getValue());
        }

        Price nonItemUnitPriceTaxExcluded = nonItemDetail.getNonItemUnitPriceTaxExcluded();
        if (nonItemUnitPriceTaxExcluded != null) {
            alterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemUnitPriceTaxExcluded.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setRetailUnitPriceTaxExcluded(nonItemUnitPriceTaxExcluded.getValue());
        }

        Price nonItemUnitPriceTaxIncluded = nonItemDetail.getNonItemUnitPriceTaxIncluded();
        if (nonItemUnitPriceTaxIncluded != null) {
            alterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemUnitPriceTaxIncluded.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setRetailUnitPriceTaxIncluded(nonItemUnitPriceTaxIncluded.getValue());
        }

        Price nonItemSalesAmtTaxExcluded = nonItemDetail.getNonItemSalesAmtTaxExcluded();
        if (nonItemSalesAmtTaxExcluded != null) {
            alterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemSalesAmtTaxExcluded.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setSalesAmountTaxExcluded(nonItemSalesAmtTaxExcluded.getValue());
        }

        Price nonItemSalesAmtTaxIncluded = nonItemDetail.getNonItemSalesAmtTaxIncluded();
        if (nonItemSalesAmtTaxIncluded != null) {
            alterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemSalesAmtTaxIncluded.getCurrencyCode()));
            alterationHistorySalesTransactionDetailEntity
                    .setSalesAmountTaxIncluded(nonItemSalesAmtTaxIncluded.getValue());
        }
        return alterationHistorySalesTransactionDetailEntity;
    }

    /**
     * Convert data to alteration history sales transaction detail info entity (for alteration).
     * 
     * @param salesErrorSalesTransactionDetailInfoEntity TSalesErrorSalesTransactionDetailInfoEntity
     *        entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionDetailInfoEntity.
     */
    public AlterationHistorySalesTransactionDetailInfo convertTAlterationHistorySalesTransactionDetailInfoEntityForInsertBeforeEdit(
            SalesErrorSalesTransactionDetailInfo salesErrorSalesTransactionDetailInfoEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistorySalesTransactionDetailInfo alterationHistorySalesTransactionDetailInfoEntity =
                new AlterationHistorySalesTransactionDetailInfo();
        modelMapper.map(salesErrorSalesTransactionDetailInfoEntity,
                alterationHistorySalesTransactionDetailInfoEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionDetailInfoEntity);

        alterationHistorySalesTransactionDetailInfoEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionDetailInfoEntity.setSalesTransactionErrorId(
                salesErrorSalesTransactionDetailInfoEntity.getTransactionId());
        alterationHistorySalesTransactionDetailInfoEntity
                .setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistorySalesTransactionDetailInfoEntity;
    }

    /**
     * Convert data to alteration history sales transaction detail info entity (for alteration).
     * 
     * @param nonItemInfo NonItemInfo data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param detailCount Detail number.
     * @param itemDetailCount Item detail count.
     * @return TAlterationHistorySalesTransactionDetailInfoEntity.
     */
    public AlterationHistorySalesTransactionDetailInfo convertTAlterationHistorySalesTransactionDetailInfoEntityForInsertAfterEdit(
            NonItemInfo nonItemInfo, String userId, String salesTransactionId, int orderSubNumber,
            String salesTransactionErrorId, String originalSalesTransactionErrorId, int detailCount,
            int itemDetailCount) {
        AlterationHistorySalesTransactionDetailInfo alterationHistorySalesTransactionDetailInfoEntity =
                new AlterationHistorySalesTransactionDetailInfo();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionDetailInfoEntity);
        modelMapper.map(nonItemInfo, alterationHistorySalesTransactionDetailInfoEntity);

        alterationHistorySalesTransactionDetailInfoEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionDetailInfoEntity.setSalesTransactionId(salesTransactionId);
        alterationHistorySalesTransactionDetailInfoEntity.setOrderSubNumber(orderSubNumber);
        alterationHistorySalesTransactionDetailInfoEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionDetailInfoEntity.setDetailSubNumber(detailCount);
        alterationHistorySalesTransactionDetailInfoEntity.setItemDetailSubNumber(itemDetailCount);
        alterationHistorySalesTransactionDetailInfoEntity
                .setHistoryType(HistoryType.AFTER.getValue());
        return alterationHistorySalesTransactionDetailInfoEntity;
    }

    /**
     * Convert data to alteration history sales transaction discount entity (for alteration).
     * 
     * @param salesErrorSalesTransactionDiscountEntity TSalesErrorSalesTransactionDiscountEntity
     *        entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionDiscountEntity.
     */
    public AlterationHistorySalesTransactionDiscount convertTAlterationHistorySalesTransactionDiscountEntityForInsertNonBeforeEdit(
            SalesErrorSalesTransactionDiscount salesErrorSalesTransactionDiscountEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistorySalesTransactionDiscount alterationHistorySalesTransactionDiscountEntity =
                new AlterationHistorySalesTransactionDiscount();
        modelMapper.map(salesErrorSalesTransactionDiscountEntity,
                alterationHistorySalesTransactionDiscountEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionDiscountEntity);

        alterationHistorySalesTransactionDiscountEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionDiscountEntity.setSalesTransactionErrorId(
                salesErrorSalesTransactionDiscountEntity.getTransactionId());
        alterationHistorySalesTransactionDiscountEntity
                .setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistorySalesTransactionDiscountEntity;

    }

    /**
     * Convert data to alteration history sales transaction discount entity (for alteration).
     * 
     * @param nonItemDiscountDetail NonItemDiscountDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param detailCount Detail count.
     * @param itemDiscountSubNumber Item discount sub number.
     * @return TAlterationHistorySalesTransactionDiscountEntity.
     */
    public AlterationHistorySalesTransactionDiscount convertTAlterationHistorySalesTransactionDiscountEntityForInsertNonAfterEdit(
            NonItemDiscountDetail nonItemDiscountDetail, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, int detailCount, int itemDiscountSubNumber) {

        AlterationHistorySalesTransactionDiscount alterationHistorySalesTransactionDiscountEntity =
                new AlterationHistorySalesTransactionDiscount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionDiscountEntity);

        alterationHistorySalesTransactionDiscountEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionDiscountEntity.setSalesTransactionId(salesTransactionId);
        alterationHistorySalesTransactionDiscountEntity.setOrderSubNumber(orderSubNumber);
        alterationHistorySalesTransactionDiscountEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionDiscountEntity.setDetailSubNumber(detailCount);
        alterationHistorySalesTransactionDiscountEntity
                .setHistoryType(HistoryType.AFTER.getValue());

        alterationHistorySalesTransactionDiscountEntity
                .setPromotionType(nonItemDiscountDetail.getNonItemPromotionType());

        if (StringUtils.isEmpty(nonItemDiscountDetail.getNonItemPromotionNumber())) {
            alterationHistorySalesTransactionDiscountEntity.setPromotionNo("0");
        } else {
            alterationHistorySalesTransactionDiscountEntity
                    .setPromotionNo(nonItemDiscountDetail.getNonItemPromotionNumber());
        }
        alterationHistorySalesTransactionDiscountEntity
                .setStoreDiscountType(nonItemDiscountDetail.getNonItemStoreDiscountType());
        alterationHistorySalesTransactionDiscountEntity
                .setItemDiscountSubNumber(itemDiscountSubNumber);
        alterationHistorySalesTransactionDiscountEntity
                .setQuantityCode(nonItemDiscountDetail.getNonItemQuantityCode());
        alterationHistorySalesTransactionDiscountEntity
                .setDiscountQuantity(nonItemDiscountDetail.getNonItemDiscountQty());

        Price nonItemDiscountAmtTaxExcluded =
                nonItemDiscountDetail.getNonItemDiscountAmtTaxExcluded();
        if (nonItemDiscountAmtTaxExcluded != null) {
            alterationHistorySalesTransactionDiscountEntity
                    .setDiscountAmountTaxExcludedCurrencyCode(PriceUtils
                            .getCurrencyCode(nonItemDiscountAmtTaxExcluded.getCurrencyCode()));
            alterationHistorySalesTransactionDiscountEntity
                    .setDiscountAmountTaxExcluded(nonItemDiscountAmtTaxExcluded.getValue());
        }

        Price nonItemDiscountAmtTaxIncluded =
                nonItemDiscountDetail.getNonItemDiscountAmtTaxIncluded();
        if (nonItemDiscountAmtTaxIncluded != null) {
            alterationHistorySalesTransactionDiscountEntity
                    .setDiscountAmountTaxIncludedCurrencyCode(PriceUtils
                            .getCurrencyCode(nonItemDiscountAmtTaxIncluded.getCurrencyCode()));
            alterationHistorySalesTransactionDiscountEntity
                    .setDiscountAmountTaxIncluded(nonItemDiscountAmtTaxIncluded.getValue());
        }
        return alterationHistorySalesTransactionDiscountEntity;
    }

    /**
     * Convert data to alteration history sales transaction tax entity (for alteration).
     * 
     * @param salesErrorSalesTransactionTaxEntity TSalesErrorSalesTransactionTaxEntity entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionTaxEntity.
     */
    public AlterationHistorySalesTransactionTax convertTAlterationHistorySalesTransactionTaxEntityForInsertNonBeforeEdit(
            SalesErrorSalesTransactionTax salesErrorSalesTransactionTaxEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistorySalesTransactionTax alterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        modelMapper.map(salesErrorSalesTransactionTaxEntity,
                alterationHistorySalesTransactionTaxEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTaxEntity);

        alterationHistorySalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTaxEntity
                .setSalesTransactionErrorId(salesErrorSalesTransactionTaxEntity.getTransactionId());
        alterationHistorySalesTransactionTaxEntity.setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistorySalesTransactionTaxEntity;
    }

    /**
     * Convert data to alteration history sales transaction tax entity (for alteration).
     * 
     * @param nonItemTaxDetail NonItemTaxDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param detailCount Detail count.
     * @param taxSubNumber Tax sub number.
     * @return TAlterationHistorySalesTransactionTaxEntity.
     */
    public AlterationHistorySalesTransactionTax convertTAlterationHistorySalesTransactionTaxEntityForInsertNonAfterEdit(
            NonItemTaxDetail nonItemTaxDetail, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, int detailCount, int taxSubNumber) {
        AlterationHistorySalesTransactionTax alterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTaxEntity);

        alterationHistorySalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTaxEntity.setSalesTransactionId(salesTransactionId);
        alterationHistorySalesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        alterationHistorySalesTransactionTaxEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionTaxEntity.setDetailSubNumber(detailCount);
        alterationHistorySalesTransactionTaxEntity.setHistoryType(HistoryType.AFTER.getValue());

        alterationHistorySalesTransactionTaxEntity
                .setTaxGroup(nonItemTaxDetail.getNonItemTaxType());
        alterationHistorySalesTransactionTaxEntity.setTaxSubNumber(taxSubNumber);
        alterationHistorySalesTransactionTaxEntity
                .setTaxAmountSign(nonItemTaxDetail.getNonItemTaxAmountSign());
        alterationHistorySalesTransactionTaxEntity.setTaxRate(nonItemTaxDetail.getNonItemTaxRate());
        alterationHistorySalesTransactionTaxEntity.setTaxName(nonItemTaxDetail.getNonItemTaxName());

        Price nonItemTaxAmt = nonItemTaxDetail.getNonItemTaxAmt();
        if (nonItemTaxAmt != null) {
            alterationHistorySalesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemTaxAmt.getCurrencyCode()));
            alterationHistorySalesTransactionTaxEntity.setTaxAmountValue(nonItemTaxAmt.getValue());
        }
        return alterationHistorySalesTransactionTaxEntity;
    }

    /**
     * Convert data to alteration history sales transaction discount entity (for alteration).
     * 
     * @param salesErrorSalesTransactionDiscountEntity TSalesErrorSalesTransactionDiscountEntity
     *        entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionDiscountEntity.
     */
    public AlterationHistorySalesTransactionDiscount convertTAlterationHistorySalesTransactionDiscountEntityForInsertBeforeEdit(
            SalesErrorSalesTransactionDiscount salesErrorSalesTransactionDiscountEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistorySalesTransactionDiscount alterationHistorySalesTransactionDiscountEntity =
                new AlterationHistorySalesTransactionDiscount();
        modelMapper.map(salesErrorSalesTransactionDiscountEntity,
                alterationHistorySalesTransactionDiscountEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionDiscountEntity);

        alterationHistorySalesTransactionDiscountEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionDiscountEntity.setSalesTransactionErrorId(
                salesErrorSalesTransactionDiscountEntity.getTransactionId());
        alterationHistorySalesTransactionDiscountEntity
                .setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistorySalesTransactionDiscountEntity;
    }

    /**
     * Convert data to alteration history sales transaction discount entity (for alteration).
     * 
     * @param itemDiscount ItemDiscount data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param detailCount Detail count.
     * @param itemDiscountSubNumber Item discount sub number.
     * @return TAlterationHistorySalesTransactionDiscountEntity.
     */
    public AlterationHistorySalesTransactionDiscount converttAlterationHistorySalesTransactionDiscountEntityForInsertAfterEdit(
            ItemDiscount itemDiscount, String userId, String salesTransactionId, int orderSubNumber,
            String salesTransactionErrorId, String originalSalesTransactionErrorId, int detailCount,
            int itemDiscountSubNumber) {
        AlterationHistorySalesTransactionDiscount alterationHistorySalesTransactionDiscountEntity =
                new AlterationHistorySalesTransactionDiscount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionDiscountEntity);

        alterationHistorySalesTransactionDiscountEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionDiscountEntity.setSalesTransactionId(salesTransactionId);
        alterationHistorySalesTransactionDiscountEntity.setOrderSubNumber(orderSubNumber);
        alterationHistorySalesTransactionDiscountEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionDiscountEntity.setDetailSubNumber(detailCount);
        alterationHistorySalesTransactionDiscountEntity
                .setHistoryType(HistoryType.AFTER.getValue());

        alterationHistorySalesTransactionDiscountEntity
                .setPromotionType(itemDiscount.getItemPromotionType());
        alterationHistorySalesTransactionDiscountEntity
                .setPromotionNo(itemDiscount.getItemPromotionNumber());
        alterationHistorySalesTransactionDiscountEntity
                .setStoreDiscountType(itemDiscount.getItemStoreDiscountType());
        alterationHistorySalesTransactionDiscountEntity
                .setItemDiscountSubNumber(itemDiscountSubNumber);
        alterationHistorySalesTransactionDiscountEntity
                .setQuantityCode(itemDiscount.getItemQuantityCode());
        alterationHistorySalesTransactionDiscountEntity
                .setDiscountQuantity(itemDiscount.getItemDiscountQty());

        Price itemDiscountAmtTaxExcluded = itemDiscount.getItemDiscountAmtTaxExcluded();
        if (itemDiscountAmtTaxExcluded != null) {
            alterationHistorySalesTransactionDiscountEntity
                    .setDiscountAmountTaxExcludedCurrencyCode(PriceUtils
                            .getCurrencyCode(itemDiscountAmtTaxExcluded.getCurrencyCode()));
            alterationHistorySalesTransactionDiscountEntity
                    .setDiscountAmountTaxExcluded(itemDiscountAmtTaxExcluded.getValue());
        }

        Price itemDiscountAmtTaxIncluded = itemDiscount.getItemDiscountAmtTaxIncluded();
        if (itemDiscountAmtTaxIncluded != null) {
            alterationHistorySalesTransactionDiscountEntity
                    .setDiscountAmountTaxIncludedCurrencyCode(PriceUtils
                            .getCurrencyCode(itemDiscountAmtTaxIncluded.getCurrencyCode()));
            alterationHistorySalesTransactionDiscountEntity
                    .setDiscountAmountTaxIncluded(itemDiscountAmtTaxIncluded.getValue());
        }
        return alterationHistorySalesTransactionDiscountEntity;
    }

    /**
     * Convert data to alteration history sales transaction tax entity (for alteration).
     * 
     * @param salesErrorSalesTransactionTaxEntity TSalesErrorSalesTransactionTaxEntity entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionTaxEntity.
     */
    public AlterationHistorySalesTransactionTax convertTAlterationHistorySalesTransactionTaxEntityForInsertBeforeEdit(
            SalesErrorSalesTransactionTax salesErrorSalesTransactionTaxEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistorySalesTransactionTax alterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        modelMapper.map(salesErrorSalesTransactionTaxEntity,
                alterationHistorySalesTransactionTaxEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTaxEntity);

        alterationHistorySalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTaxEntity
                .setSalesTransactionErrorId(salesErrorSalesTransactionTaxEntity.getTransactionId());
        alterationHistorySalesTransactionTaxEntity.setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistorySalesTransactionTaxEntity;
    }

    /**
     * Convert data to alteration history sales transaction tax entity (for alteration).
     * 
     * @param itemTaxDetail ItemTaxDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Orginal transaction error id.
     * @param detailCount Detail count.
     * @param taxSubNumber Tax sub number.
     * @return TAlterationHistorySalesTransactionTaxEntity.
     */
    public AlterationHistorySalesTransactionTax converttAlterationHistorySalesTransactionTaxEntityForInsertAfterEdit(
            ItemTaxDetail itemTaxDetail, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, int detailCount, int taxSubNumber) {
        AlterationHistorySalesTransactionTax alterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTaxEntity);

        alterationHistorySalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTaxEntity.setSalesTransactionId(salesTransactionId);
        alterationHistorySalesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        alterationHistorySalesTransactionTaxEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionTaxEntity.setDetailSubNumber(detailCount);
        alterationHistorySalesTransactionTaxEntity.setHistoryType(HistoryType.AFTER.getValue());

        alterationHistorySalesTransactionTaxEntity.setTaxGroup(itemTaxDetail.getItemTaxType());
        alterationHistorySalesTransactionTaxEntity.setTaxSubNumber(taxSubNumber);
        alterationHistorySalesTransactionTaxEntity
                .setTaxAmountSign(itemTaxDetail.getItemTaxAmountSign());
        alterationHistorySalesTransactionTaxEntity.setTaxRate(itemTaxDetail.getItemTaxRate());
        alterationHistorySalesTransactionTaxEntity.setTaxName(itemTaxDetail.getItemTaxName());

        Price itemTaxAmt = itemTaxDetail.getItemTaxAmt();
        if (itemTaxAmt != null) {
            alterationHistorySalesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(itemTaxAmt.getCurrencyCode()));
            alterationHistorySalesTransactionTaxEntity.setTaxAmountValue(itemTaxAmt.getValue());
        }
        return alterationHistorySalesTransactionTaxEntity;
    }

    /**
     * Convert data to alteration history sales transaction tender entity (for alteration).
     * 
     * @param salesErrorSalesTransactionTenderEntity TSalesErrorSalesTransactionTenderEntity entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionTenderEntity.
     */
    public AlterationHistorySalesTransactionTender convertTAlterationHistorySalesTransactionTenderEntityForInsertBeforeEdit(
            SalesErrorSalesTransactionTender salesErrorSalesTransactionTenderEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistorySalesTransactionTender alterationHistorySalesTransactionTenderEntity =
                new AlterationHistorySalesTransactionTender();
        modelMapper.map(salesErrorSalesTransactionTenderEntity,
                alterationHistorySalesTransactionTenderEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTenderEntity);

        alterationHistorySalesTransactionTenderEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTenderEntity.setSalesTransactionErrorId(
                salesErrorSalesTransactionTenderEntity.getTransactionId());
        alterationHistorySalesTransactionTenderEntity.setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistorySalesTransactionTenderEntity;
    }

    /**
     * Convert data to alteration history sales transaction tender entity (for alteration).
     * 
     * @param salesTransactionTender SalesTransactionTender data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param tenderSubNumber Tender sub number.
     * @return TAlterationHistorySalesTransactionTenderEntity.
     */
    public AlterationHistorySalesTransactionTender convertTAlterationHistorySalesTransactionTenderEntityForInsertAfterEdit(
            SalesTransactionTender salesTransactionTender, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, int tenderSubNumber) {
        AlterationHistorySalesTransactionTender alterationHistorySalesTransactionTenderEntity =
                new AlterationHistorySalesTransactionTender();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTenderEntity);
        modelMapper.map(salesTransactionTender, alterationHistorySalesTransactionTenderEntity);

        alterationHistorySalesTransactionTenderEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTenderEntity.setSalesTransactionId(salesTransactionId);
        alterationHistorySalesTransactionTenderEntity.setOrderSubNumber(orderSubNumber);
        alterationHistorySalesTransactionTenderEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionTenderEntity.setHistoryType(HistoryType.AFTER.getValue());

        alterationHistorySalesTransactionTenderEntity.setTenderSubNumber(tenderSubNumber);

        alterationHistorySalesTransactionTenderEntity
                .setTenderGroup(salesTransactionTender.getTenderGroupId());

        Price taxIncludedPaymentAmount = salesTransactionTender.getTaxIncludedPaymentAmount();
        if (taxIncludedPaymentAmount != null) {
            alterationHistorySalesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(taxIncludedPaymentAmount.getCurrencyCode()));
            alterationHistorySalesTransactionTenderEntity
                    .setTaxIncludedPaymentAmountValue(taxIncludedPaymentAmount.getValue());
        }
        return alterationHistorySalesTransactionTenderEntity;
    }

    /**
     * Convert data to alteration history sales transaction tender info entity (for alteration).
     * 
     * @param salesErrorSalesTransactionTenderInfoEntity TSalesErrorSalesTransactionTenderInfoEntity
     *        entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionTenderInfoEntity.
     */
    public AlterationHistorySalesTransactionTenderInfo convertTAlterationHistorySalesTransactionTenderInfoEntityForInsertBeforeEdit(
            SalesErrorSalesTransactionTenderInfo salesErrorSalesTransactionTenderInfoEntity,
            String salesTransactionErrorId, String userId, int tenderSubNumber) {
        AlterationHistorySalesTransactionTenderInfo alterationHistorySalesTransactionTenderInfoEntity =
                new AlterationHistorySalesTransactionTenderInfo();
        modelMapper.map(salesErrorSalesTransactionTenderInfoEntity,
                alterationHistorySalesTransactionTenderInfoEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTenderInfoEntity);

        alterationHistorySalesTransactionTenderInfoEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTenderInfoEntity.setSalesTransactionErrorId(
                salesErrorSalesTransactionTenderInfoEntity.getTransactionId());
        alterationHistorySalesTransactionTenderInfoEntity
                .setHistoryType(HistoryType.BEFORE.getValue());
        alterationHistorySalesTransactionTenderInfoEntity.setTenderSubNumber(tenderSubNumber);
        return alterationHistorySalesTransactionTenderInfoEntity;
    }

    /**
     * Convert data to alteration history sales transaction tender info entity (for alteration).
     * 
     * @param tenderInfo TenderInfo data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param tenderId Tender id.
     * @param tenderGroupId Tender group id.
     * @return TAlterationHistorySalesTransactionTenderInfoEntity.
     */
    public AlterationHistorySalesTransactionTenderInfo convertTAlterationHistorySalesTransactionTenderInfoEntityForInsertAfterEdit(
            TenderInfo tenderInfo, String userId, String salesTransactionId, int orderSubNumber,
            String salesTransactionErrorId, String originalSalesTransactionErrorId, String tenderId,
            String tenderGroupId, int tenderSubNumber) {
        AlterationHistorySalesTransactionTenderInfo alterationHistorySalesTransactionTenderInfoEntity =
                new AlterationHistorySalesTransactionTenderInfo();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTenderInfoEntity);
        modelMapper.map(tenderInfo, alterationHistorySalesTransactionTenderInfoEntity);

        alterationHistorySalesTransactionTenderInfoEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTenderInfoEntity.setSalesTransactionId(salesTransactionId);
        alterationHistorySalesTransactionTenderInfoEntity.setOrderSubNumber(orderSubNumber);
        alterationHistorySalesTransactionTenderInfoEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionTenderInfoEntity
                .setHistoryType(HistoryType.AFTER.getValue());

        alterationHistorySalesTransactionTenderInfoEntity.setTenderId(tenderId);
        alterationHistorySalesTransactionTenderInfoEntity.setTenderGroup(tenderGroupId);
        alterationHistorySalesTransactionTenderInfoEntity.setTenderSubNumber(tenderSubNumber);

        alterationHistorySalesTransactionTenderInfoEntity
                .setDiscountRate(tenderInfo.getDiscountRate());

        Price discountAmount = tenderInfo.getDiscountAmount();
        if (discountAmount != null) {
            alterationHistorySalesTransactionTenderInfoEntity.setDiscountValueCurrencyCode(
                    PriceUtils.getCurrencyCode(discountAmount.getCurrencyCode()));
            alterationHistorySalesTransactionTenderInfoEntity
                    .setDiscountValue(discountAmount.getValue());
        }

        Price couponMinUsageAmountThreshold = tenderInfo.getCouponMinUsageAmountThreshold();
        if (couponMinUsageAmountThreshold != null) {
            alterationHistorySalesTransactionTenderInfoEntity
                    .setCouponMinUsageAmountThresholdCurrencyCode(PriceUtils
                            .getCurrencyCode(couponMinUsageAmountThreshold.getCurrencyCode()));
            alterationHistorySalesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdValue(
                    couponMinUsageAmountThreshold.getValue());
        }

        Price couponDiscountAmountSetting = tenderInfo.getCouponDiscountAmountSetting();
        if (couponDiscountAmountSetting != null) {
            alterationHistorySalesTransactionTenderInfoEntity
                    .setCouponDiscountAmountSettingCurrencyCode(PriceUtils
                            .getCurrencyCode(couponDiscountAmountSetting.getCurrencyCode()));
            alterationHistorySalesTransactionTenderInfoEntity
                    .setCouponDiscountAmountSettingValue(couponDiscountAmountSetting.getValue());
        }
        return alterationHistorySalesTransactionTenderInfoEntity;
    }

    /**
     * Convert data to alteration history sales transaction tax entity (for alteration).
     * 
     * @param salesErrorSalesTransactionTaxEntity TSalesErrorSalesTransactionTaxEntity entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionTaxEntity.
     */
    public AlterationHistorySalesTransactionTax convertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionBeforeEdit(
            SalesErrorSalesTransactionTax salesErrorSalesTransactionTaxEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistorySalesTransactionTax alterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        modelMapper.map(salesErrorSalesTransactionTaxEntity,
                alterationHistorySalesTransactionTaxEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTaxEntity);

        alterationHistorySalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTaxEntity
                .setSalesTransactionErrorId(salesErrorSalesTransactionTaxEntity.getTransactionId());
        alterationHistorySalesTransactionTaxEntity.setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistorySalesTransactionTaxEntity;
    }

    /**
     * Convert data to alteration history sales transaction tax entity (for alteration).
     * 
     * @param salesTransactionTaxDetail SalesTransactionTaxDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param detailCount Detail count.
     * @param taxSubNumber Tax sub number.
     * @return TAlterationHistorySalesTransactionTaxEntity.
     */
    public AlterationHistorySalesTransactionTax convertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionAfterEdit(
            SalesTransactionTaxDetail salesTransactionTaxDetail, String userId,
            String salesTransactionId, int orderSubNumber, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, int detailCount, int taxSubNumber) {
        AlterationHistorySalesTransactionTax alterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTaxEntity);
        modelMapper.map(salesTransactionTaxDetail, alterationHistorySalesTransactionTaxEntity);

        alterationHistorySalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTaxEntity.setSalesTransactionId(salesTransactionId);
        alterationHistorySalesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        alterationHistorySalesTransactionTaxEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionTaxEntity.setDetailSubNumber(detailCount);
        alterationHistorySalesTransactionTaxEntity.setHistoryType(HistoryType.AFTER.getValue());
        alterationHistorySalesTransactionTaxEntity.setTaxSubNumber(taxSubNumber);
        Price taxAmount = salesTransactionTaxDetail.getTaxAmount();
        if (taxAmount != null) {
            alterationHistorySalesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(taxAmount.getCurrencyCode()));
            alterationHistorySalesTransactionTaxEntity.setTaxAmountValue(taxAmount.getValue());
        }
        return alterationHistorySalesTransactionTaxEntity;
    }

    /**
     * Convert data to alteration history sales transaction total amount entity (for alteration).
     * 
     * @param salesErrorSalesTransactionTotalAmountEntity
     *        TSalesErrorSalesTransactionTotalAmountEntity entity.
     * @param salesTransactionErrorId Transaction error id.
     * @param userId User id.
     * @return TAlterationHistorySalesTransactionTotalAmountEntity.
     */
    public AlterationHistorySalesTransactionTotalAmount convertTAlterationHistorySalesTransactionTotalAmountEntityForInsertBeforeEdit(
            SalesErrorSalesTransactionTotalAmount salesErrorSalesTransactionTotalAmountEntity,
            String salesTransactionErrorId, String userId) {
        AlterationHistorySalesTransactionTotalAmount alterationHistorySalesTransactionTotalAmountEntity =
                new AlterationHistorySalesTransactionTotalAmount();
        modelMapper.map(salesErrorSalesTransactionTotalAmountEntity,
                alterationHistorySalesTransactionTotalAmountEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTotalAmountEntity);

        alterationHistorySalesTransactionTotalAmountEntity
                .setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTotalAmountEntity.setSalesTransactionErrorId(
                salesErrorSalesTransactionTotalAmountEntity.getTransactionId());
        alterationHistorySalesTransactionTotalAmountEntity
                .setHistoryType(HistoryType.BEFORE.getValue());
        return alterationHistorySalesTransactionTotalAmountEntity;
    }

    /**
     * Convert data to alteration history sales transaction total amount entity (for alteration).
     * 
     * @param salesTransactionTotal SalesTransactionTotal data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionErrorId Transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param totalAmountSubNumber Total amount sub number.
     * @return TAlterationHistorySalesTransactionTotalAmountEntity.
     */
    public AlterationHistorySalesTransactionTotalAmount convertTAlterationHistorySalesTransactionTotalAmountEntityForInsertAfterEdit(
            SalesTransactionTotal salesTransactionTotal, String userId, String salesTransactionId,
            int orderSubNumber, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, int totalAmountSubNumber) {
        AlterationHistorySalesTransactionTotalAmount alterationHistorySalesTransactionTotalAmountEntity =
                new AlterationHistorySalesTransactionTotalAmount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, alterationHistorySalesTransactionTotalAmountEntity);
        modelMapper.map(salesTransactionTotal, alterationHistorySalesTransactionTotalAmountEntity);

        alterationHistorySalesTransactionTotalAmountEntity
                .setTransactionId(salesTransactionErrorId);
        alterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionId(salesTransactionId);
        alterationHistorySalesTransactionTotalAmountEntity.setOrderSubNumber(orderSubNumber);
        alterationHistorySalesTransactionTotalAmountEntity
                .setSalesTransactionErrorId(originalSalesTransactionErrorId);
        alterationHistorySalesTransactionTotalAmountEntity
                .setHistoryType(HistoryType.AFTER.getValue());

        alterationHistorySalesTransactionTotalAmountEntity
                .setTotalAmountSubNumber(totalAmountSubNumber);
        alterationHistorySalesTransactionTotalAmountEntity
                .setTaxRate(salesTransactionTotal.getConsumptionTaxRate());

        Price totalAmountTaxExcluded = salesTransactionTotal.getTotalAmountTaxExcluded();
        if (totalAmountTaxExcluded != null) {
            alterationHistorySalesTransactionTotalAmountEntity
                    .setTotalAmountTaxExcludedCurrencyCode(
                            PriceUtils.getCurrencyCode(totalAmountTaxExcluded.getCurrencyCode()));
            alterationHistorySalesTransactionTotalAmountEntity
                    .setTotalAmountTaxExcludedValue(totalAmountTaxExcluded.getValue());
        }

        Price totalAmountTaxIncluded = salesTransactionTotal.getTotalAmountTaxIncluded();
        if (totalAmountTaxIncluded != null) {
            alterationHistorySalesTransactionTotalAmountEntity
                    .setTotalAmountTaxIncludedCurrencyCode(
                            PriceUtils.getCurrencyCode(totalAmountTaxIncluded.getCurrencyCode()));
            alterationHistorySalesTransactionTotalAmountEntity
                    .setTotalAmountTaxIncludedValue(totalAmountTaxIncluded.getValue());
        }
        return alterationHistorySalesTransactionTotalAmountEntity;
    }
}
