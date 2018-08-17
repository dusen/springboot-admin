
/**
 * @(#)CoreTableDataConverter.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
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
import com.fastretailing.dcp.storecommon.util.PriceUtils;

/**
 * The class is used to convert data to core table's entity.
 *
 */
@Component
public class CoreTableDataConverter {

    /** Common data processor. */
    @Autowired
    private CommonDataProcessor commonDataProcessor;

    /** Model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Convert data to sales order information entity (for insert).
     * 
     * @param transactionImportData Transaction import data.
     * @param userId User id.
     * @return TSalesOrderInformationEntity.
     */
    public SalesOrderInformation convertTSalesOrderInformationEntityForInsert(
            TransactionImportData transactionImportData, String userId) {
        SalesOrderInformation salesOrderInformationEntity = new SalesOrderInformation();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesOrderInformationEntity);
        modelMapper.map(transactionImportData, salesOrderInformationEntity);

        if (transactionImportData.getDataCorrectionEditingFlag() != null) {
            salesOrderInformationEntity.setDataAlterationEditingFlag(
                    transactionImportData.getDataCorrectionEditingFlag());
        }
        salesOrderInformationEntity
                .setDataAlterationUserId(transactionImportData.getDataCorrectionUserId());
        if (transactionImportData.getOrderConfirmationDateTime() != null) {
            salesOrderInformationEntity.setOrderConfirmationDateTime(
                    transactionImportData.getOrderConfirmationDateTime().toLocalDateTime());
        }

        return salesOrderInformationEntity;
    }

    /**
     * Convert data to sales transaction header entity (for insert).
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     * @param transactionCount Transaction count.
     * @return TSalesTransactionHeaderEntity.
     */
    public SalesTransactionHeader convertTSalesTransactionHeaderEntityForInsert(
            Transaction transaction, String userId, int transactionCount) {
        SalesTransactionHeader salesTransactionHeaderEntity = new SalesTransactionHeader();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionHeaderEntity);
        modelMapper.map(transaction, salesTransactionHeaderEntity);

        salesTransactionHeaderEntity.setSalesTransactionSubNumber(transactionCount);
        salesTransactionHeaderEntity.setSalesTransactionType(transaction.getTransactionType());
        salesTransactionHeaderEntity.setCustomerType(transaction.getCustomerType());
        salesTransactionHeaderEntity
                .setSalesTransactionDiscountType(transaction.getSalesTransactionDiscountType());
        if (transaction.getConsistencySalesFlag() != null) {
            salesTransactionHeaderEntity
                    .setConsistencySalesFlag(transaction.getConsistencySalesFlag());
        }
        if (transaction.getEmployeeSaleFlag() != null) {
            salesTransactionHeaderEntity.setEmployeeSaleFlag(transaction.getEmployeeSaleFlag());
        }
        if (transaction.getReceiptIssuedFlag() != null) {
            salesTransactionHeaderEntity.setReceiptIssuedFlag(transaction.getReceiptIssuedFlag());
        }
        if (transaction.getEReceiptTargetFlag() != null) {
            salesTransactionHeaderEntity.setEReceiptTargetFlag(transaction.getEReceiptTargetFlag());
        }
        if (transaction.getDataCreationDateTime() != null) {
            salesTransactionHeaderEntity.setDataCreationDateTime(
                    transaction.getDataCreationDateTime().toLocalDateTime());
        }
        if (transaction.getOrderStatusLastUpdateDateTime() != null) {
            salesTransactionHeaderEntity.setOrderStatusLastUpdateDateTime(
                    transaction.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }
        if (transaction.getOrderCancellationDate() != null) {
            salesTransactionHeaderEntity.setOrderCancelledDateTime(
                    transaction.getOrderCancellationDate().toLocalDateTime());
        }

        Price deposit = transaction.getDeposit();
        if (deposit != null) {
            salesTransactionHeaderEntity
                    .setDepositCurrencyCode(PriceUtils.getCurrencyCode(deposit.getCurrencyCode()));
            salesTransactionHeaderEntity.setDepositValue(deposit.getValue());
        }

        Price change = transaction.getChange();
        if (change != null) {
            salesTransactionHeaderEntity
                    .setChangeCurrencyCode(PriceUtils.getCurrencyCode(change.getCurrencyCode()));
            salesTransactionHeaderEntity.setChangeValue(change.getValue());
        }

        Price amountRate = transaction.getSalesTransactionDiscountAmountRate();
        if (amountRate != null) {
            salesTransactionHeaderEntity.setSalesTransactionDiscountAmountRateCurrencyCode(
                    PriceUtils.getCurrencyCode(amountRate.getCurrencyCode()));
            salesTransactionHeaderEntity
                    .setSalesTransactionDiscountAmountRate(amountRate.getValue());
        }
        return salesTransactionHeaderEntity;
    }

    /**
     * Convert data to sales transaction detail entity (for insert).
     * 
     * @param transactionItemDetail TransactionItemDetail data.
     * @param userId User id.
     * @param salesTransactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @return TSalesTransactionDetailEntity.
     */
    public SalesTransactionDetail convertTSalesTransactionDetailEntityForInsert(
            TransactionItemDetail transactionItemDetail, String userId, String salesTransactionId,
            int orderSubNumber, int detailCount, int itemDetailCount) {
        SalesTransactionDetail salesTransactionDetailEntity = new SalesTransactionDetail();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionDetailEntity);
        modelMapper.map(transactionItemDetail, salesTransactionDetailEntity);

        salesTransactionDetailEntity.setSalesTransactionId(salesTransactionId);
        salesTransactionDetailEntity.setOrderSubNumber(orderSubNumber);
        salesTransactionDetailEntity.setDetailSubNumber(detailCount);
        salesTransactionDetailEntity.setItemDetailSubNumber(itemDetailCount);

        if (transactionItemDetail.getOrderStatusLastUpdateDateTime() != null) {
            salesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(
                    transactionItemDetail.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }

        salesTransactionDetailEntity
                .setSalesTransactionType(transactionItemDetail.getDetailListSalesTransactionType());
        salesTransactionDetailEntity
                .setDisplayL2ItemCode(transactionItemDetail.getViewL2ItemCode());
        salesTransactionDetailEntity
                .setProductClassification(ProductClassification.ITEM.getValue());
        if (transactionItemDetail.getDeptCode() != null) {
            salesTransactionDetailEntity
                    .setMajorCategoryCode(String.valueOf(transactionItemDetail.getDeptCode()));
        }
        if (transactionItemDetail.getItemQty() != null) {
            salesTransactionDetailEntity
                    .setDetailQuantity(new BigDecimal(transactionItemDetail.getItemQty()));
        }
        if (transactionItemDetail.getBundlePurchaseQty() != null) {
            salesTransactionDetailEntity.setBundlePurchaseApplicableQuantity(
                    new BigDecimal(transactionItemDetail.getBundlePurchaseQty()));
        }
        salesTransactionDetailEntity
                .setStoreItemDiscountType(transactionItemDetail.getItemMountDiscountType());
        if (Objects.nonNull(transactionItemDetail.getBundleSalesFlag())) {
            salesTransactionDetailEntity
                    .setStoreBundleSaleFlag(transactionItemDetail.getBundleSalesFlag());
        }
        salesTransactionDetailEntity
                .setSetSalesDetailIndex(transactionItemDetail.getBundleSalesDetailIndex());
        salesTransactionDetailEntity.setTaxationType(transactionItemDetail.getItemTaxationType());
        salesTransactionDetailEntity.setTaxSystemType(transactionItemDetail.getItemTaxKind());

        Price itemCost = transactionItemDetail.getItemCost();
        if (itemCost != null) {
            salesTransactionDetailEntity.setItemCostCurrencyCode(
                    PriceUtils.getCurrencyCode(itemCost.getCurrencyCode()));
            salesTransactionDetailEntity.setItemCostValue(itemCost.getValue());
        }

        Price initialSellingPrice = transactionItemDetail.getInitialSellingPrice();
        if (initialSellingPrice != null) {
            salesTransactionDetailEntity.setInitialSellingPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(initialSellingPrice.getCurrencyCode()));
            salesTransactionDetailEntity.setInitialSellingPrice(initialSellingPrice.getValue());
        }

        Price itemSellingPrice = transactionItemDetail.getBItemSellingPrice();
        if (itemSellingPrice != null) {
            salesTransactionDetailEntity.setBclassPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSellingPrice.getCurrencyCode()));
            salesTransactionDetailEntity.setBclassPrice(itemSellingPrice.getValue());
        }

        Price itemNewPrice = transactionItemDetail.getItemNewPrice();
        if (itemNewPrice != null) {
            salesTransactionDetailEntity.setNewPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(itemNewPrice.getCurrencyCode()));
            salesTransactionDetailEntity.setNewPrice(itemNewPrice.getValue());
        }

        Price itemUnitPriceTaxExcluded = transactionItemDetail.getItemUnitPriceTaxExcluded();
        if (itemUnitPriceTaxExcluded != null) {
            salesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemUnitPriceTaxExcluded.getCurrencyCode()));
            salesTransactionDetailEntity
                    .setRetailUnitPriceTaxExcluded(itemUnitPriceTaxExcluded.getValue());
        }

        Price itemUnitPriceTaxIncluded = transactionItemDetail.getItemUnitPriceTaxIncluded();
        if (itemUnitPriceTaxIncluded != null) {
            salesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemUnitPriceTaxIncluded.getCurrencyCode()));
            salesTransactionDetailEntity
                    .setRetailUnitPriceTaxIncluded(itemUnitPriceTaxIncluded.getValue());
        }

        Price itemSalesAmtTaxExcluded = transactionItemDetail.getItemSalesAmtTaxExcluded();
        if (itemSalesAmtTaxExcluded != null) {
            salesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSalesAmtTaxExcluded.getCurrencyCode()));
            salesTransactionDetailEntity
                    .setSalesAmountTaxExcluded(itemSalesAmtTaxExcluded.getValue());
        }

        Price itemSalesAmtTaxIncluded = transactionItemDetail.getItemSalesAmtTaxIncluded();
        if (itemSalesAmtTaxIncluded != null) {
            salesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSalesAmtTaxIncluded.getCurrencyCode()));
            salesTransactionDetailEntity
                    .setSalesAmountTaxIncluded(itemSalesAmtTaxIncluded.getValue());
        }

        Price bundlePurchasePrice = transactionItemDetail.getBundlePurchasePrice();
        if (bundlePurchasePrice != null) {
            salesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode(
                    PriceUtils.getCurrencyCode(bundlePurchasePrice.getCurrencyCode()));
            salesTransactionDetailEntity
                    .setBundlePurchaseApplicablePrice(bundlePurchasePrice.getValue());
        }

        Price itemDiscountAmount = transactionItemDetail.getItemDiscountAmount();
        if (itemDiscountAmount != null) {
            salesTransactionDetailEntity.setStoreItemDiscountCurrencyCode(
                    PriceUtils.getCurrencyCode(itemDiscountAmount.getCurrencyCode()));
            salesTransactionDetailEntity.setStoreItemDiscountSetting(itemDiscountAmount.getValue());
        }

        Price setSalesPrice = transactionItemDetail.getBundleSalesPrice();
        if (setSalesPrice != null) {
            salesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode(
                    PriceUtils.getCurrencyCode(setSalesPrice.getCurrencyCode()));
            salesTransactionDetailEntity.setStoreBundleSalePrice(setSalesPrice.getValue());
        }
        return salesTransactionDetailEntity;
    }

    /**
     * Convert data to sales transaction detail entity (for insert).
     * 
     * @param nonItemDetail NonItemDetail data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionType Sales transaction type.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail
     * @return TSalesTransactionDetailEntity.
     */
    public SalesTransactionDetail convertTSalesTransactionDetailEntityForInsertOutside(
            NonItemDetail nonItemDetail, String transactionId, int orderSubNumber,
            String salesTransactionType, String userId, int detailCount, int itemDetailCount) {
        SalesTransactionDetail salesTransactionDetailEntity = new SalesTransactionDetail();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionDetailEntity);
        modelMapper.map(nonItemDetail, salesTransactionDetailEntity);

        salesTransactionDetailEntity.setSalesTransactionId(transactionId);
        salesTransactionDetailEntity.setOrderSubNumber(orderSubNumber);

        salesTransactionDetailEntity.setDetailSubNumber(detailCount);
        salesTransactionDetailEntity.setItemDetailSubNumber(itemDetailCount);

        if (nonItemDetail.getOrderStatusLastUpdateDateTime() != null) {
            salesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(
                    nonItemDetail.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }

        if (StringUtils.isEmpty(salesTransactionType)) {
            salesTransactionDetailEntity.setSalesTransactionType(
                    nonItemDetail.getNonMdDetailListSalesTransactionType());
        } else {
            salesTransactionDetailEntity.setSalesTransactionType(salesTransactionType);
        }

        salesTransactionDetailEntity.setL3PosProductName(nonItemDetail.getPosNonItemName());
        salesTransactionDetailEntity
                .setProductClassification(ProductClassification.NMITEM.getValue());
        salesTransactionDetailEntity.setNonMdCode(nonItemDetail.getNonItemCode());
        if (nonItemDetail.getNonItemQty() != null) {
            salesTransactionDetailEntity
                    .setDetailQuantity(new BigDecimal(nonItemDetail.getNonItemQty()));
        }

        salesTransactionDetailEntity
                .setCalculationUnavailableType(nonItemDetail.getNonCalculationNonItemType());
        salesTransactionDetailEntity.setTaxationType(nonItemDetail.getNonItemTaxationType());
        salesTransactionDetailEntity.setTaxSystemType(nonItemDetail.getNonItemTaxKind());

        Price nonItemNewPrice = nonItemDetail.getNonItemNewPrice();
        if (nonItemNewPrice != null) {
            salesTransactionDetailEntity.setNewPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemNewPrice.getCurrencyCode()));
            salesTransactionDetailEntity.setNewPrice(nonItemNewPrice.getValue());
        }

        Price nonItemUnitPriceTaxExcluded = nonItemDetail.getNonItemUnitPriceTaxExcluded();
        if (nonItemUnitPriceTaxExcluded != null) {
            salesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemUnitPriceTaxExcluded.getCurrencyCode()));
            salesTransactionDetailEntity
                    .setRetailUnitPriceTaxExcluded(nonItemUnitPriceTaxExcluded.getValue());
        }

        Price nonItemUnitPriceTaxIncluded = nonItemDetail.getNonItemUnitPriceTaxIncluded();
        if (nonItemUnitPriceTaxIncluded != null) {
            salesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemUnitPriceTaxIncluded.getCurrencyCode()));
            salesTransactionDetailEntity
                    .setRetailUnitPriceTaxIncluded(nonItemUnitPriceTaxIncluded.getValue());
        }

        Price nonItemSalesAmtTaxExcluded = nonItemDetail.getNonItemSalesAmtTaxExcluded();
        if (nonItemSalesAmtTaxExcluded != null) {
            salesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemSalesAmtTaxExcluded.getCurrencyCode()));
            salesTransactionDetailEntity
                    .setSalesAmountTaxExcluded(nonItemSalesAmtTaxExcluded.getValue());
        }

        Price nonItemSalesAmtTaxIncluded = nonItemDetail.getNonItemSalesAmtTaxIncluded();
        if (nonItemSalesAmtTaxIncluded != null) {
            salesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemSalesAmtTaxIncluded.getCurrencyCode()));
            salesTransactionDetailEntity
                    .setSalesAmountTaxIncluded(nonItemSalesAmtTaxIncluded.getValue());
        }
        return salesTransactionDetailEntity;
    }

    /**
     * Convert data to sales transaction detail info entity (for insert).
     * 
     * @param nonItemInfo NonItemInfo data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @return TSalesTransactionDetailInfoEntity.
     */
    public SalesTransactionDetailInfo convertTSalesTransactionDetailInfoEntityForInsert(
            NonItemInfo nonItemInfo, String transactionId, int orderSubNumber, String userId,
            int detailCount, int itemDetailCount) {
        SalesTransactionDetailInfo salesTransactionDetailInfoEntity =
                new SalesTransactionDetailInfo();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionDetailInfoEntity);
        modelMapper.map(nonItemInfo, salesTransactionDetailInfoEntity);

        salesTransactionDetailInfoEntity.setSalesTransactionId(transactionId);
        salesTransactionDetailInfoEntity.setOrderSubNumber(orderSubNumber);
        salesTransactionDetailInfoEntity.setDetailSubNumber(detailCount);
        salesTransactionDetailInfoEntity.setItemDetailSubNumber(itemDetailCount);
        return salesTransactionDetailInfoEntity;
    }

    /**
     * Convert data to sales transaction discount entity (for insert).
     * 
     * @param nonItemDiscountDetail NonItemDiscountDetail data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param detailCount Detail count.
     * @param itemDiscountCount Item discount count.
     * @return TSalesTransactionDiscountEntity.
     */
    public SalesTransactionDiscount convertTSalesTransactionDiscountEntityForInsertNon(
            NonItemDiscountDetail nonItemDiscountDetail, String transactionId, int orderSubNumber,
            String userId, int detailCount, int itemDiscountCount) {
        SalesTransactionDiscount salesTransactionDiscountEntity = new SalesTransactionDiscount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionDiscountEntity);

        salesTransactionDiscountEntity.setSalesTransactionId(transactionId);
        salesTransactionDiscountEntity.setOrderSubNumber(orderSubNumber);
        salesTransactionDiscountEntity.setDetailSubNumber(detailCount);

        salesTransactionDiscountEntity
                .setPromotionType(nonItemDiscountDetail.getNonItemPromotionType());
        if (StringUtils.isEmpty(nonItemDiscountDetail.getNonItemPromotionNumber())) {
            salesTransactionDiscountEntity.setPromotionNo("0");
        } else {
            salesTransactionDiscountEntity
                    .setPromotionNo(nonItemDiscountDetail.getNonItemPromotionNumber());
        }
        salesTransactionDiscountEntity
                .setStoreDiscountType(nonItemDiscountDetail.getNonItemStoreDiscountType());
        salesTransactionDiscountEntity.setItemDiscountSubNumber(itemDiscountCount);
        salesTransactionDiscountEntity
                .setQuantityCode(nonItemDiscountDetail.getNonItemQuantityCode());
        salesTransactionDiscountEntity
                .setDiscountQuantity(nonItemDiscountDetail.getNonItemDiscountQty());

        Price nonItemDiscountAmtTaxExcluded =
                nonItemDiscountDetail.getNonItemDiscountAmtTaxExcluded();
        if (nonItemDiscountAmtTaxExcluded != null) {
            salesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemDiscountAmtTaxExcluded.getCurrencyCode()));
            salesTransactionDiscountEntity
                    .setDiscountAmountTaxExcluded(nonItemDiscountAmtTaxExcluded.getValue());
        }

        Price nonItemDiscountAmtTaxIncluded =
                nonItemDiscountDetail.getNonItemDiscountAmtTaxIncluded();
        if (nonItemDiscountAmtTaxIncluded != null) {
            salesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemDiscountAmtTaxIncluded.getCurrencyCode()));
            salesTransactionDiscountEntity
                    .setDiscountAmountTaxIncluded(nonItemDiscountAmtTaxIncluded.getValue());
        }
        return salesTransactionDiscountEntity;
    }

    /**
     * Convert data to sales transaction tax entity (for insert).
     * 
     * @param nonItemTaxDetail NonItemTaxDetail data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param detailCount Detail count.
     * @param itemTaxCount Item tax count.
     * @return TSalesTransactionTaxEntity.
     */
    public SalesTransactionTax convertTSalesTransactionTaxEntityForInsertNon(
            NonItemTaxDetail nonItemTaxDetail, String transactionId, int orderSubNumber,
            String userId, int detailCount, int itemTaxCount) {
        SalesTransactionTax salesTransactionTaxEntity = new SalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionTaxEntity);

        salesTransactionTaxEntity.setSalesTransactionId(transactionId);
        salesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        salesTransactionTaxEntity.setDetailSubNumber(detailCount);

        salesTransactionTaxEntity.setTaxGroup(nonItemTaxDetail.getNonItemTaxType());
        salesTransactionTaxEntity.setTaxSubNumber(itemTaxCount);
        salesTransactionTaxEntity.setTaxAmountSign(nonItemTaxDetail.getNonItemTaxAmountSign());
        salesTransactionTaxEntity.setTaxRate(nonItemTaxDetail.getNonItemTaxRate());
        salesTransactionTaxEntity.setTaxName(nonItemTaxDetail.getNonItemTaxName());

        Price nonItemTaxAmt = nonItemTaxDetail.getNonItemTaxAmt();
        if (nonItemTaxAmt != null) {
            salesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemTaxAmt.getCurrencyCode()));
            salesTransactionTaxEntity.setTaxAmountValue(nonItemTaxAmt.getValue());
        }
        return salesTransactionTaxEntity;
    }

    /**
     * Convert data to TSalesTransactionTaxEntity (for insert).
     * 
     * @param itemDiscount ItemDiscount data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param detailCount Detail count.
     * @param itemDiscountCount Item discount count.
     * @return TSalesTransactionDiscountEntity.
     */
    public SalesTransactionDiscount convertTSalesTransactionDiscountEntityForInsert(
            ItemDiscount itemDiscount, String transactionId, int orderSubNumber, String userId,
            int detailCount, int itemDiscountCount) {
        SalesTransactionDiscount salesTransactionDiscountEntity = new SalesTransactionDiscount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionDiscountEntity);
        salesTransactionDiscountEntity.setSalesTransactionId(transactionId);
        salesTransactionDiscountEntity.setOrderSubNumber(orderSubNumber);
        salesTransactionDiscountEntity.setDetailSubNumber(detailCount);
        salesTransactionDiscountEntity.setPromotionType(itemDiscount.getItemPromotionType());
        salesTransactionDiscountEntity.setPromotionNo(itemDiscount.getItemPromotionNumber());
        salesTransactionDiscountEntity
                .setStoreDiscountType(itemDiscount.getItemStoreDiscountType());
        salesTransactionDiscountEntity.setItemDiscountSubNumber(itemDiscountCount);
        salesTransactionDiscountEntity.setQuantityCode(itemDiscount.getItemQuantityCode());
        salesTransactionDiscountEntity.setDiscountQuantity(itemDiscount.getItemDiscountQty());

        Price itemDiscountAmtTaxExcluded = itemDiscount.getItemDiscountAmtTaxExcluded();
        if (itemDiscountAmtTaxExcluded != null) {
            salesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemDiscountAmtTaxExcluded.getCurrencyCode()));
            salesTransactionDiscountEntity
                    .setDiscountAmountTaxExcluded(itemDiscountAmtTaxExcluded.getValue());
        }
        Price itemUnitPriceTaxIncluded = itemDiscount.getItemDiscountAmtTaxIncluded();
        if (itemUnitPriceTaxIncluded != null) {
            salesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemUnitPriceTaxIncluded.getCurrencyCode()));
            salesTransactionDiscountEntity
                    .setDiscountAmountTaxIncluded(itemUnitPriceTaxIncluded.getValue());
        }
        return salesTransactionDiscountEntity;
    }

    /**
     * Convert data to sales transaction tax entity (for insert).
     * 
     * @param itemTaxDetail ItemTaxDetail data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param detailCount Detail count.
     * @param itemTaxCount Item tax count.
     * @return TSalesTransactionTaxEntity.
     */
    public SalesTransactionTax convertTSalesTransactionTaxEntityForInsert(
            ItemTaxDetail itemTaxDetail, String transactionId, int orderSubNumber, String userId,
            int detailCount, int itemTaxCount) {
        SalesTransactionTax salesTransactionTaxEntity = new SalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionTaxEntity);

        salesTransactionTaxEntity.setSalesTransactionId(transactionId);
        salesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        salesTransactionTaxEntity.setDetailSubNumber(detailCount);
        salesTransactionTaxEntity.setTaxGroup(itemTaxDetail.getItemTaxType());
        salesTransactionTaxEntity.setTaxSubNumber(itemTaxCount);
        salesTransactionTaxEntity.setTaxAmountSign(itemTaxDetail.getItemTaxAmountSign());
        salesTransactionTaxEntity.setTaxRate(itemTaxDetail.getItemTaxRate());
        salesTransactionTaxEntity.setTaxName(itemTaxDetail.getItemTaxName());
        Price itemTaxAmt = itemTaxDetail.getItemTaxAmt();
        if (itemTaxAmt != null) {
            salesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(itemTaxAmt.getCurrencyCode()));
            salesTransactionTaxEntity.setTaxAmountValue(itemTaxAmt.getValue());
        }
        return salesTransactionTaxEntity;
    }

    /**
     * Convert data to sales transaction tender entity (for insert).
     * 
     * @param salesTransactionTender SalesTransactionTender data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param payDetailCount Pay detail count.
     * @return TSalesTransactionTenderEntity.
     */
    public SalesTransactionTenderTable convertTSalesTransactionTenderEntityForInsert(
            SalesTransactionTender salesTransactionTender, String transactionId, int orderSubNumber,
            String userId, int payDetailCount) {
        SalesTransactionTenderTable salesTransactionTenderEntity =
                new SalesTransactionTenderTable();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionTenderEntity);
        modelMapper.map(salesTransactionTender, salesTransactionTenderEntity);

        salesTransactionTenderEntity.setSalesTransactionId(transactionId);
        salesTransactionTenderEntity.setOrderSubNumber(orderSubNumber);
        salesTransactionTenderEntity.setTenderSubNumber(payDetailCount);
        salesTransactionTenderEntity.setTenderGroup(salesTransactionTender.getTenderGroupId());

        Price taxIncludedPaymentAmount = salesTransactionTender.getTaxIncludedPaymentAmount();
        if (taxIncludedPaymentAmount != null) {
            salesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(taxIncludedPaymentAmount.getCurrencyCode()));
            salesTransactionTenderEntity
                    .setTaxIncludedPaymentAmountValue(taxIncludedPaymentAmount.getValue());
        }
        return salesTransactionTenderEntity;
    }

    /**
     * Convert data to sales transaction tender info entity (for insert).
     * 
     * @param tenderInfo TenderInfo data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param tenderGroupId Tender group id.
     * @param tenderId Tender id.
     * @param userId User id.
     * @param tenderSubNumber Tender sub number.
     * @return TSalesTransactionTenderInfoEntity.
     */
    public SalesTransactionTenderInfo convertTSalesTransactionTenderInfoEntityForInsert(
            TenderInfo tenderInfo, String transactionId, int orderSubNumber, String tenderGroupId,
            String tenderId, String userId, int tenderSubNumber) {
        SalesTransactionTenderInfo salesTransactionTenderInfoEntity =
                new SalesTransactionTenderInfo();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionTenderInfoEntity);
        modelMapper.map(tenderInfo, salesTransactionTenderInfoEntity);

        salesTransactionTenderInfoEntity.setSalesTransactionId(transactionId);
        salesTransactionTenderInfoEntity.setOrderSubNumber(orderSubNumber);
        salesTransactionTenderInfoEntity.setTenderGroup(tenderGroupId);
        salesTransactionTenderInfoEntity.setTenderId(tenderId);
        salesTransactionTenderInfoEntity.setTenderSubNumber(tenderSubNumber);

        salesTransactionTenderInfoEntity.setDiscountRate(tenderInfo.getDiscountRate());

        Price discountAmount = tenderInfo.getDiscountAmount();
        if (discountAmount != null) {
            salesTransactionTenderInfoEntity.setDiscountValueCurrencyCode(
                    PriceUtils.getCurrencyCode(discountAmount.getCurrencyCode()));
            salesTransactionTenderInfoEntity.setDiscountValue(discountAmount.getValue());
        }
        Price couponMinUsageAmountThreshold = tenderInfo.getCouponMinUsageAmountThreshold();
        if (couponMinUsageAmountThreshold != null) {
            salesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdCurrencyCode(
                    PriceUtils.getCurrencyCode(couponMinUsageAmountThreshold.getCurrencyCode()));
            salesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdValue(
                    couponMinUsageAmountThreshold.getValue());
        }
        Price couponDiscountAmountSetting = tenderInfo.getCouponDiscountAmountSetting();
        if (couponDiscountAmountSetting != null) {
            salesTransactionTenderInfoEntity.setCouponDiscountAmountSettingCurrencyCode(
                    PriceUtils.getCurrencyCode(couponDiscountAmountSetting.getCurrencyCode()));
            salesTransactionTenderInfoEntity
                    .setCouponDiscountAmountSettingValue(couponDiscountAmountSetting.getValue());
        }
        return salesTransactionTenderInfoEntity;
    }

    /**
     * Convert data to sales transaction tax entity (for insert).
     * 
     * @param salesTransactionTaxDetail SalesTransactionTaxDetail data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param detailCount Detail count.
     * @param taxDetailCount Tax detail count.
     * @return TSalesTransactionTaxEntity.
     */
    public SalesTransactionTax convertTSalesTransactionTaxEntityForInsertTransaction(
            SalesTransactionTaxDetail salesTransactionTaxDetail, String transactionId,
            int orderSubNumber, String userId, int detailCount, int taxDetailCount) {
        SalesTransactionTax salesTransactionTaxEntity = new SalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionTaxEntity);
        modelMapper.map(salesTransactionTaxDetail, salesTransactionTaxEntity);
        salesTransactionTaxEntity.setSalesTransactionId(transactionId);
        salesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        salesTransactionTaxEntity.setDetailSubNumber(detailCount);
        salesTransactionTaxEntity.setTaxSubNumber(taxDetailCount);

        Price taxAmount = salesTransactionTaxDetail.getTaxAmount();
        if (taxAmount != null) {
            salesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(taxAmount.getCurrencyCode()));
            salesTransactionTaxEntity.setTaxAmountValue(taxAmount.getValue());
        }
        return salesTransactionTaxEntity;
    }

    /**
     * Convert data to sales transaction total amount entity (for insert).
     * 
     * @param salesTransactionTotal SalesTransactionTotal data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param totalDetailCount Total detail count.
     * @return TSalesTransactionTotalAmountEntity.
     */
    public SalesTransactionTotalAmount convertTSalesTransactionTotalAmountEntityForInsert(
            SalesTransactionTotal salesTransactionTotal, String transactionId, int orderSubNumber,
            String userId, int totalDetailCount) {
        SalesTransactionTotalAmount salesTransactionTotalAmountEntity =
                new SalesTransactionTotalAmount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionTotalAmountEntity);
        modelMapper.map(salesTransactionTotal, salesTransactionTotalAmountEntity);

        salesTransactionTotalAmountEntity.setSalesTransactionId(transactionId);
        salesTransactionTotalAmountEntity.setOrderSubNumber(orderSubNumber);
        salesTransactionTotalAmountEntity.setTotalAmountSubNumber(totalDetailCount);
        salesTransactionTotalAmountEntity.setTaxRate(salesTransactionTotal.getConsumptionTaxRate());
        Price totalAmountTaxExcluded = salesTransactionTotal.getTotalAmountTaxExcluded();
        if (totalAmountTaxExcluded != null) {
            salesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(totalAmountTaxExcluded.getCurrencyCode()));
            salesTransactionTotalAmountEntity
                    .setTotalAmountTaxExcludedValue(totalAmountTaxExcluded.getValue());
        }
        Price totalAmountTaxIncluded = salesTransactionTotal.getTotalAmountTaxIncluded();
        if (totalAmountTaxIncluded != null) {
            salesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(totalAmountTaxIncluded.getCurrencyCode()));
            salesTransactionTotalAmountEntity
                    .setTotalAmountTaxIncludedValue(totalAmountTaxIncluded.getValue());
        }
        return salesTransactionTotalAmountEntity;
    }


    /**
     * Convert data to sales transaction header entity (for correction).
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     * @return TSalesTransactionHeaderEntity .
     */
    public SalesTransactionHeader convertTSalesTransactionHeaderEntityForUpdate(
            Transaction transaction, String userId) {
        SalesTransactionHeader tsalesTransactionHeaderEntity = new SalesTransactionHeader();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForUpdate(userId);
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        modelMapper.map(tableCommonItem, tsalesTransactionHeaderEntity);

        tsalesTransactionHeaderEntity.setSalesTransactionId(transaction.getSalesTransactionId());

        tsalesTransactionHeaderEntity
                .setOrderStatusUpdateDate(transaction.getOrderStatusUpdateDate());
        tsalesTransactionHeaderEntity.setOrderStatus(transaction.getOrderStatus());
        tsalesTransactionHeaderEntity.setOrderSubstatus(transaction.getOrderSubstatus());
        if (transaction.getOrderStatusLastUpdateDateTime() != null) {
            tsalesTransactionHeaderEntity.setOrderStatusLastUpdateDateTime(
                    transaction.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }
        return tsalesTransactionHeaderEntity;
    }

    /**
     * Convert data to sales transaction detail entity (for evacuation).
     * 
     * @param transactionItemDetail Transaction item detail.
     * @param salesTransactionId Sales Sales transaction id.
     * @param userId User id.
     * @return TSalesTransactionDetailEntity.
     */
    public SalesTransactionDetail convertTSalesTransactionDetailEntityForUpdate(
            TransactionItemDetail transactionItemDetail, String salesTransactionId, String userId) {
        SalesTransactionDetail tsalesTransactionDetailEntity = new SalesTransactionDetail();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForUpdate(userId);
        modelMapper.map(tableCommonItem, tsalesTransactionDetailEntity);

        tsalesTransactionDetailEntity.setSalesTransactionId(salesTransactionId);
        tsalesTransactionDetailEntity
                .setDetailSubNumber(transactionItemDetail.getDetailSubNumber());

        tsalesTransactionDetailEntity
                .setOrderStatusUpdateDate(transactionItemDetail.getOrderStatusUpdateDate());
        tsalesTransactionDetailEntity.setOrderStatus(transactionItemDetail.getOrderStatus());
        tsalesTransactionDetailEntity.setOrderSubstatus(transactionItemDetail.getOrderSubstatus());
        if (transactionItemDetail.getOrderStatusLastUpdateDateTime() != null) {
            tsalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(
                    transactionItemDetail.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }
        return tsalesTransactionDetailEntity;
    }

    /**
     * Convert data to sales transaction detail entity (for evacuation).
     * 
     * @param nonItemDetail Non item detail.
     * @param salesTransactionId Sales transaction id.
     * @param userId User id.
     * @return TSalesTransactionDetailEntity.
     */
    public SalesTransactionDetail convertTSalesTransactionDetailOutsideEntityForUpdate(
            NonItemDetail nonItemDetail, String salesTransactionId, String userId) {
        SalesTransactionDetail tsalesTransactionDetailEntity = new SalesTransactionDetail();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForUpdate(userId);
        modelMapper.map(tableCommonItem, tsalesTransactionDetailEntity);


        tsalesTransactionDetailEntity.setSalesTransactionId(salesTransactionId);
        if (nonItemDetail.getDetailSubNumber() != null) {
            tsalesTransactionDetailEntity.setDetailSubNumber(nonItemDetail.getDetailSubNumber());
        } else {
            tsalesTransactionDetailEntity
                    .setDetailSubNumber(nonItemDetail.getItemDetailSubNumber());
        }

        tsalesTransactionDetailEntity
                .setOrderStatusUpdateDate(nonItemDetail.getOrderStatusUpdateDate());
        tsalesTransactionDetailEntity.setOrderStatus(nonItemDetail.getOrderStatus());
        tsalesTransactionDetailEntity.setOrderSubstatus(nonItemDetail.getOrderSubstatus());
        if (nonItemDetail.getOrderStatusLastUpdateDateTime() != null) {
            tsalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(
                    nonItemDetail.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }
        return tsalesTransactionDetailEntity;
    }


}

