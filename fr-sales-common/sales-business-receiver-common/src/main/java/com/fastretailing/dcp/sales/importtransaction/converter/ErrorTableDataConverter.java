/**
 * @(#)ErrorTableDataConverter.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.converter;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.fastretailing.dcp.storecommon.util.PriceUtils;

/**
 * The class is used to convert data to error table's entity.
 *
 */
@Component
public class ErrorTableDataConverter {

    /** Common data processor. */
    @Autowired
    private CommonDataProcessor commonDataProcessor;

    /** Model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Convert data to sales error order information entity (for correction).
     * 
     * @param transactionImportData Transaction import data.
     * @param userId User id.
     * @param salesTransactionErrorId The error sequence no.
     * @return TSalesErrorOrderInformationEntity.
     */
    public SalesErrorSalesOrderInformation convertTSalesErrorOrderInformationEntityForInsert(
            TransactionImportData transactionImportData, String userId,
            String salesTransactionErrorId) {
        SalesErrorSalesOrderInformation salesErrorSalesOrderInformationEntity =
                new SalesErrorSalesOrderInformation();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesOrderInformationEntity);
        modelMapper.map(transactionImportData, salesErrorSalesOrderInformationEntity);
        salesErrorSalesOrderInformationEntity.setTransactionId(salesTransactionErrorId);
        if (transactionImportData.getOrderConfirmationDateTime() != null) {
            salesErrorSalesOrderInformationEntity.setOrderConfirmationDateTime(
                    transactionImportData.getOrderConfirmationDateTime().toLocalDateTime());
        }
        if (transactionImportData.getDataCorrectionEditingFlag() != null) {
            salesErrorSalesOrderInformationEntity.setDataAlterationEditingFlag(
                    transactionImportData.getDataCorrectionEditingFlag());
        }

        if (StringUtils.isNotEmpty(
                salesErrorSalesOrderInformationEntity.getOrderConfirmationBusinessDate())
                && salesErrorSalesOrderInformationEntity.getOrderConfirmationBusinessDate()
                        .length() > 8) {
            salesErrorSalesOrderInformationEntity.setOrderConfirmationBusinessDate(formatDateForDB(
                    salesErrorSalesOrderInformationEntity.getOrderConfirmationBusinessDate()));
        }
        salesErrorSalesOrderInformationEntity
                .setDataAlterationUserId(transactionImportData.getDataCorrectionUserId());
        return salesErrorSalesOrderInformationEntity;
    }

    /**
     * Convert data to sales error sales transaction header entity (for correction).
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     * @param salesTransactionErrorId The error sequence no.
     * @param transactionCount Transaction count.
     * @return TSalesErrorSalesTransactionHeaderEntity.
     */
    public SalesErrorSalesTransactionHeader convertTSalesErrorSalesTransactionHeaderEntityForInsert(
            Transaction transaction, String userId, String salesTransactionErrorId,
            int transactionCount) {
        SalesErrorSalesTransactionHeader salesErrorSalesTransactionHeaderEntity =
                new SalesErrorSalesTransactionHeader();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionHeaderEntity);
        modelMapper.map(transaction, salesErrorSalesTransactionHeaderEntity);

        salesErrorSalesTransactionHeaderEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionSubNumber(transactionCount);
        salesErrorSalesTransactionHeaderEntity
                .setSalesTransactionType(transaction.getTransactionType());
        salesErrorSalesTransactionHeaderEntity.setCustomerType(transaction.getCustomerType());
        salesErrorSalesTransactionHeaderEntity
                .setSalesTransactionDiscountType(transaction.getSalesTransactionDiscountType());
        if (transaction.getConsistencySalesFlag() != null) {
            salesErrorSalesTransactionHeaderEntity
                    .setConsistencySalesFlag(transaction.getConsistencySalesFlag());
        }
        if (transaction.getEmployeeSaleFlag() != null) {
            salesErrorSalesTransactionHeaderEntity
                    .setEmployeeSaleFlag(transaction.getEmployeeSaleFlag());
        }
        if (transaction.getReceiptIssuedFlag() != null) {
            salesErrorSalesTransactionHeaderEntity
                    .setReceiptIssuedFlag(transaction.getReceiptIssuedFlag());
        }
        if (transaction.getEReceiptTargetFlag() != null) {
            salesErrorSalesTransactionHeaderEntity
                    .setEReceiptTargetFlag(transaction.getEReceiptTargetFlag());
        }
        if (transaction.getDataCreationDateTime() != null) {
            salesErrorSalesTransactionHeaderEntity.setDataCreationDateTime(
                    transaction.getDataCreationDateTime().toLocalDateTime());
        }
        if (transaction.getOrderStatusLastUpdateDateTime() != null) {
            salesErrorSalesTransactionHeaderEntity.setOrderStatusLastUpdateDateTime(
                    transaction.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }
        if (transaction.getOrderCancellationDate() != null) {
            salesErrorSalesTransactionHeaderEntity.setOrderCancelledDateTime(
                    transaction.getOrderCancellationDate().toLocalDateTime());
        }

        Price deposit = transaction.getDeposit();
        if (deposit != null) {
            salesErrorSalesTransactionHeaderEntity
                    .setDepositCurrencyCode(PriceUtils.getCurrencyCode(deposit.getCurrencyCode()));
            salesErrorSalesTransactionHeaderEntity.setDepositValue(deposit.getValue());
        }
        Price change = transaction.getChange();
        if (change != null) {
            salesErrorSalesTransactionHeaderEntity
                    .setChangeCurrencyCode(PriceUtils.getCurrencyCode(change.getCurrencyCode()));
            salesErrorSalesTransactionHeaderEntity.setChangeValue(change.getValue());
        }
        Price salesTransactionDiscountAmountRate =
                transaction.getSalesTransactionDiscountAmountRate();
        if (salesTransactionDiscountAmountRate != null) {
            salesErrorSalesTransactionHeaderEntity
                    .setSalesTransactionDiscountAmountRateCurrencyCode(PriceUtils
                            .getCurrencyCode(salesTransactionDiscountAmountRate.getCurrencyCode()));
            salesErrorSalesTransactionHeaderEntity.setSalesTransactionDiscountAmountRate(
                    salesTransactionDiscountAmountRate.getValue());
        }

        if (StringUtils
                .isNotEmpty(salesErrorSalesTransactionHeaderEntity.getDataCreationBusinessDate())
                && salesErrorSalesTransactionHeaderEntity.getDataCreationBusinessDate()
                        .length() > 8) {
            salesErrorSalesTransactionHeaderEntity.setDataCreationBusinessDate(formatDateForDB(
                    salesErrorSalesTransactionHeaderEntity.getDataCreationBusinessDate()));
        }

        if (StringUtils
                .isNotEmpty(salesErrorSalesTransactionHeaderEntity.getOrderStatusUpdateDate())
                && salesErrorSalesTransactionHeaderEntity.getOrderStatusUpdateDate().length() > 8) {
            salesErrorSalesTransactionHeaderEntity.setOrderStatusUpdateDate(formatDateForDB(
                    salesErrorSalesTransactionHeaderEntity.getOrderStatusUpdateDate()));
        }
        return salesErrorSalesTransactionHeaderEntity;
    }

    /**
     * Convert data to sales error sales transaction detail entity (for insert).
     * 
     * @param transactionItemDetail TransactionItemDetail data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @return TSalesErrorSalesTransactionDetailEntity.
     */
    public SalesErrorSalesTransactionDetail convertTSalesErrorSalesTransactionDetailEntityForInsert(
            TransactionItemDetail transactionItemDetail, String transactionId, int orderSubNumber,
            String userId, String salesTransactionErrorId, int detailCount, int itemDetailCount) {
        SalesErrorSalesTransactionDetail salesErrorSalesTransactionDetailEntity =
                new SalesErrorSalesTransactionDetail();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionDetailEntity);
        modelMapper.map(transactionItemDetail, salesErrorSalesTransactionDetailEntity);

        salesErrorSalesTransactionDetailEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionDetailEntity.setOrderSubNumber(orderSubNumber);
        salesErrorSalesTransactionDetailEntity.setSalesTransactionId(transactionId);
        salesErrorSalesTransactionDetailEntity.setDetailSubNumber(detailCount);
        salesErrorSalesTransactionDetailEntity.setItemDetailSubNumber(itemDetailCount);

        if (transactionItemDetail.getOrderStatusLastUpdateDateTime() != null) {
            salesErrorSalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(
                    transactionItemDetail.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }

        salesErrorSalesTransactionDetailEntity
                .setSalesTransactionType(transactionItemDetail.getDetailListSalesTransactionType());
        salesErrorSalesTransactionDetailEntity
                .setDisplayL2ItemCode(transactionItemDetail.getViewL2ItemCode());
        salesErrorSalesTransactionDetailEntity
                .setProductClassification(ProductClassification.ITEM.getValue());
        if (transactionItemDetail.getDeptCode() != null) {
            salesErrorSalesTransactionDetailEntity
                    .setMajorCategoryCode(String.valueOf(transactionItemDetail.getDeptCode()));
        }
        if (transactionItemDetail.getItemQty() != null) {
            salesErrorSalesTransactionDetailEntity
                    .setDetailQuantity(new BigDecimal(transactionItemDetail.getItemQty()));
        }
        if (transactionItemDetail.getBundlePurchaseQty() != null) {
            salesErrorSalesTransactionDetailEntity.setBundlePurchaseApplicableQuantity(
                    new BigDecimal(transactionItemDetail.getBundlePurchaseQty()));
        }
        salesErrorSalesTransactionDetailEntity
                .setStoreItemDiscountType(transactionItemDetail.getItemMountDiscountType());
        if (Objects.nonNull(transactionItemDetail.getBundleSalesFlag())) {
            salesErrorSalesTransactionDetailEntity
                    .setStoreBundleSaleFlag(transactionItemDetail.getBundleSalesFlag());
        }
        salesErrorSalesTransactionDetailEntity
                .setSetSalesDetailIndex(transactionItemDetail.getBundleSalesDetailIndex());
        salesErrorSalesTransactionDetailEntity
                .setTaxationType(transactionItemDetail.getItemTaxationType());
        salesErrorSalesTransactionDetailEntity
                .setTaxSystemType(transactionItemDetail.getItemTaxKind());

        if (transactionItemDetail.getOrderStatusLastUpdateDateTime() != null) {
            salesErrorSalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(
                    transactionItemDetail.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }

        Price itemCost = transactionItemDetail.getItemCost();
        if (itemCost != null) {
            salesErrorSalesTransactionDetailEntity.setItemCostCurrencyCode(
                    PriceUtils.getCurrencyCode(itemCost.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity.setItemCostValue(itemCost.getValue());
        }

        Price initialSellingPrice = transactionItemDetail.getInitialSellingPrice();
        if (initialSellingPrice != null) {
            salesErrorSalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(initialSellingPrice.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setInitialSellingPrice(initialSellingPrice.getValue());
        }

        Price itemSellingPrice = transactionItemDetail.getBItemSellingPrice();
        if (itemSellingPrice != null) {
            salesErrorSalesTransactionDetailEntity.setBclassPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSellingPrice.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity.setBclassPrice(itemSellingPrice.getValue());
        }

        Price itemNewPrice = transactionItemDetail.getItemNewPrice();
        if (itemNewPrice != null) {
            salesErrorSalesTransactionDetailEntity.setNewPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(itemNewPrice.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity.setNewPrice(itemNewPrice.getValue());
        }

        Price itemUnitPriceTaxExcluded = transactionItemDetail.getItemUnitPriceTaxExcluded();
        if (itemUnitPriceTaxExcluded != null) {
            salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemUnitPriceTaxExcluded.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setRetailUnitPriceTaxExcluded(itemUnitPriceTaxExcluded.getValue());
        }

        Price itemUnitPriceTaxIncluded = transactionItemDetail.getItemUnitPriceTaxIncluded();
        if (itemUnitPriceTaxIncluded != null) {
            salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemUnitPriceTaxIncluded.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setRetailUnitPriceTaxIncluded(itemUnitPriceTaxIncluded.getValue());
        }

        Price itemSalesAmtTaxExcluded = transactionItemDetail.getItemSalesAmtTaxExcluded();
        if (itemSalesAmtTaxExcluded != null) {
            salesErrorSalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSalesAmtTaxExcluded.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setSalesAmountTaxExcluded(itemSalesAmtTaxExcluded.getValue());
        }

        Price itemSalesAmtTaxIncluded = transactionItemDetail.getItemSalesAmtTaxIncluded();
        if (itemSalesAmtTaxIncluded != null) {
            salesErrorSalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemSalesAmtTaxIncluded.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setSalesAmountTaxIncluded(itemSalesAmtTaxIncluded.getValue());
        }

        Price bundlePurchasePrice = transactionItemDetail.getBundlePurchasePrice();
        if (bundlePurchasePrice != null) {
            salesErrorSalesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode(
                    PriceUtils.getCurrencyCode(bundlePurchasePrice.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setBundlePurchaseApplicablePrice(bundlePurchasePrice.getValue());
        }

        Price itemDiscountAmount = transactionItemDetail.getItemDiscountAmount();
        if (itemDiscountAmount != null) {
            salesErrorSalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode(
                    PriceUtils.getCurrencyCode(itemDiscountAmount.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setStoreItemDiscountSetting(itemDiscountAmount.getValue());
        }

        Price setSalesPrice = transactionItemDetail.getBundleSalesPrice();
        if (setSalesPrice != null) {
            salesErrorSalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode(
                    PriceUtils.getCurrencyCode(setSalesPrice.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setStoreBundleSalePrice(setSalesPrice.getValue());
        }

        if (StringUtils
                .isNotEmpty(salesErrorSalesTransactionDetailEntity.getOrderStatusUpdateDate())
                && salesErrorSalesTransactionDetailEntity.getOrderStatusUpdateDate().length() > 8) {
            salesErrorSalesTransactionDetailEntity.setOrderStatusUpdateDate(formatDateForDB(
                    salesErrorSalesTransactionDetailEntity.getOrderStatusUpdateDate()));
        }
        return salesErrorSalesTransactionDetailEntity;
    }

    /**
     * Convert data to sales error sales transaction detail entity (for insert).
     * 
     * @param nonItemDetail NonItemDetail data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param salesTransactionType Sales transaction type.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @return TSalesErrorSalesTransactionDetailEntity.
     */
    public SalesErrorSalesTransactionDetail convertTSalesErrorSalesTransactionDetailEntityForInsertOutside(
            NonItemDetail nonItemDetail, String transactionId, int orderSubNumber, String userId,
            String salesTransactionErrorId, String salesTransactionType, int detailCount,
            int itemDetailCount) {

        SalesErrorSalesTransactionDetail salesErrorSalesTransactionDetailEntity =
                new SalesErrorSalesTransactionDetail();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionDetailEntity);
        modelMapper.map(nonItemDetail, salesErrorSalesTransactionDetailEntity);

        salesErrorSalesTransactionDetailEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionDetailEntity.setOrderSubNumber(orderSubNumber);
        salesErrorSalesTransactionDetailEntity.setSalesTransactionId(transactionId);
        salesErrorSalesTransactionDetailEntity.setDetailSubNumber(detailCount);
        salesErrorSalesTransactionDetailEntity.setItemDetailSubNumber(itemDetailCount);

        if (nonItemDetail.getOrderStatusLastUpdateDateTime() != null) {
            salesErrorSalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(
                    nonItemDetail.getOrderStatusLastUpdateDateTime().toLocalDateTime());
        }
        if (StringUtils.isEmpty(salesTransactionType)) {
            salesErrorSalesTransactionDetailEntity.setSalesTransactionType(
                    nonItemDetail.getNonMdDetailListSalesTransactionType());
        } else {
            salesErrorSalesTransactionDetailEntity.setSalesTransactionType(salesTransactionType);
        }

        salesErrorSalesTransactionDetailEntity
                .setL3PosProductName(nonItemDetail.getPosNonItemName());
        salesErrorSalesTransactionDetailEntity
                .setProductClassification(ProductClassification.NMITEM.getValue());
        salesErrorSalesTransactionDetailEntity.setNonMdCode(nonItemDetail.getNonItemCode());
        if (nonItemDetail.getNonItemQty() != null) {
            salesErrorSalesTransactionDetailEntity
                    .setDetailQuantity(new BigDecimal(nonItemDetail.getNonItemQty()));
        }
        salesErrorSalesTransactionDetailEntity
                .setCalculationUnavailableType(nonItemDetail.getNonCalculationNonItemType());
        salesErrorSalesTransactionDetailEntity
                .setTaxationType(nonItemDetail.getNonItemTaxationType());
        salesErrorSalesTransactionDetailEntity.setTaxSystemType(nonItemDetail.getNonItemTaxKind());

        Price nonItemNewPrice = nonItemDetail.getNonItemNewPrice();
        if (nonItemNewPrice != null) {
            salesErrorSalesTransactionDetailEntity.setNewPriceCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemNewPrice.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity.setNewPrice(nonItemNewPrice.getValue());
        }

        Price nonItemUnitPriceTaxExcluded = nonItemDetail.getNonItemUnitPriceTaxExcluded();
        if (nonItemUnitPriceTaxExcluded != null) {
            salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemUnitPriceTaxExcluded.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setRetailUnitPriceTaxExcluded(nonItemUnitPriceTaxExcluded.getValue());
        }

        Price nonItemUnitPriceTaxIncluded = nonItemDetail.getNonItemUnitPriceTaxIncluded();
        if (nonItemUnitPriceTaxIncluded != null) {
            salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemUnitPriceTaxIncluded.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setRetailUnitPriceTaxIncluded(nonItemUnitPriceTaxIncluded.getValue());
        }

        Price nonItemSalesAmtTaxExcluded = nonItemDetail.getNonItemSalesAmtTaxExcluded();
        if (nonItemSalesAmtTaxExcluded != null) {
            salesErrorSalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemSalesAmtTaxExcluded.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setSalesAmountTaxExcluded(nonItemSalesAmtTaxExcluded.getValue());
        }

        Price nonItemSalesAmtTaxIncluded = nonItemDetail.getNonItemSalesAmtTaxIncluded();
        if (nonItemSalesAmtTaxIncluded != null) {
            salesErrorSalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemSalesAmtTaxIncluded.getCurrencyCode()));
            salesErrorSalesTransactionDetailEntity
                    .setSalesAmountTaxIncluded(nonItemSalesAmtTaxIncluded.getValue());
        }

        if (StringUtils
                .isNotEmpty(salesErrorSalesTransactionDetailEntity.getOrderStatusUpdateDate())
                && salesErrorSalesTransactionDetailEntity.getOrderStatusUpdateDate().length() > 8) {
            salesErrorSalesTransactionDetailEntity.setOrderStatusUpdateDate(formatDateForDB(
                    salesErrorSalesTransactionDetailEntity.getOrderStatusUpdateDate()));
        }

        return salesErrorSalesTransactionDetailEntity;
    }

    /**
     * Convert data to sales error sales transaction detail info entity (for insert).
     * 
     * @param nonItemInfo NonItemInfo data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @return TSalesErrorSalesTransactionDetailInfoEntity.
     */
    public SalesErrorSalesTransactionDetailInfo convertTSalesErrorSalesTransactionDetailInfoEntityForInsert(
            NonItemInfo nonItemInfo, String transactionId, int orderSubNumber, String userId,
            String salesTransactionErrorId, int detailCount, int itemDetailCount) {
        SalesErrorSalesTransactionDetailInfo salesErrorSalesTransactionDetailInfoEntity =
                new SalesErrorSalesTransactionDetailInfo();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionDetailInfoEntity);
        modelMapper.map(nonItemInfo, salesErrorSalesTransactionDetailInfoEntity);

        salesErrorSalesTransactionDetailInfoEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionDetailInfoEntity.setOrderSubNumber(orderSubNumber);
        salesErrorSalesTransactionDetailInfoEntity.setSalesTransactionId(transactionId);
        salesErrorSalesTransactionDetailInfoEntity.setDetailSubNumber(detailCount);
        salesErrorSalesTransactionDetailInfoEntity.setItemDetailSubNumber(itemDetailCount);
        return salesErrorSalesTransactionDetailInfoEntity;
    }

    /**
     * Convert data to sales error sales transaction discount entity (for insert).
     * 
     * @param itemDiscount ItemDiscount data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub Number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemDiscountCount Item discount count.
     * @return TSalesErrorSalesTransactionDiscountEntity.
     */
    public SalesErrorSalesTransactionDiscount convertTSalesErrorSalesTransactionDiscountEntityForInsert(
            ItemDiscount itemDiscount, String transactionId, int orderSubNumber, String userId,
            String salesTransactionErrorId, int detailCount, int itemDiscountCount) {
        SalesErrorSalesTransactionDiscount salesErrorSalesTransactionDiscountEntity =
                new SalesErrorSalesTransactionDiscount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionDiscountEntity);

        salesErrorSalesTransactionDiscountEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionDiscountEntity.setOrderSubNumber(orderSubNumber);
        salesErrorSalesTransactionDiscountEntity.setSalesTransactionId(transactionId);
        salesErrorSalesTransactionDiscountEntity.setDetailSubNumber(detailCount);
        salesErrorSalesTransactionDiscountEntity.setItemDiscountSubNumber(itemDiscountCount);

        salesErrorSalesTransactionDiscountEntity
                .setPromotionType(itemDiscount.getItemPromotionType());
        salesErrorSalesTransactionDiscountEntity
                .setPromotionNo(itemDiscount.getItemPromotionNumber());
        salesErrorSalesTransactionDiscountEntity
                .setStoreDiscountType(itemDiscount.getItemStoreDiscountType());
        salesErrorSalesTransactionDiscountEntity
                .setQuantityCode(itemDiscount.getItemQuantityCode());
        salesErrorSalesTransactionDiscountEntity
                .setDiscountQuantity(itemDiscount.getItemDiscountQty());

        Price itemDiscountAmtTaxExcluded = itemDiscount.getItemDiscountAmtTaxExcluded();
        if (itemDiscountAmtTaxExcluded != null) {
            salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemDiscountAmtTaxExcluded.getCurrencyCode()));
            salesErrorSalesTransactionDiscountEntity
                    .setDiscountAmountTaxExcluded(itemDiscountAmtTaxExcluded.getValue());
        }

        Price itemDiscountAmtTaxIncluded = itemDiscount.getItemDiscountAmtTaxIncluded();
        if (itemDiscountAmtTaxIncluded != null) {
            salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(itemDiscountAmtTaxIncluded.getCurrencyCode()));
            salesErrorSalesTransactionDiscountEntity
                    .setDiscountAmountTaxIncluded(itemDiscountAmtTaxIncluded.getValue());
        }
        return salesErrorSalesTransactionDiscountEntity;
    }

    /**
     * Convert data to sales error sales transaction discount entity (for insert).
     * 
     * @param nonItemDiscountDetail NonItemDiscountDetail data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemDiscountCount Item discount count.
     * @return TSalesErrorSalesTransactionDiscountEntity.
     */
    public SalesErrorSalesTransactionDiscount convertTSalesErrorSalesTransactionDiscountEntityForInsertNon(
            NonItemDiscountDetail nonItemDiscountDetail, String transactionId, int orderSubNumber,
            String userId, String salesTransactionErrorId, int detailCount, int itemDiscountCount) {
        SalesErrorSalesTransactionDiscount salesErrorSalesTransactionDiscountEntity =
                new SalesErrorSalesTransactionDiscount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionDiscountEntity);

        salesErrorSalesTransactionDiscountEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionDiscountEntity.setSalesTransactionId(transactionId);
        salesErrorSalesTransactionDiscountEntity.setOrderSubNumber(orderSubNumber);
        salesErrorSalesTransactionDiscountEntity.setDetailSubNumber(detailCount);
        salesErrorSalesTransactionDiscountEntity.setItemDiscountSubNumber(itemDiscountCount);

        salesErrorSalesTransactionDiscountEntity
                .setPromotionType(nonItemDiscountDetail.getNonItemPromotionType());
        if (StringUtils.isEmpty(nonItemDiscountDetail.getNonItemPromotionNumber())) {
            salesErrorSalesTransactionDiscountEntity.setPromotionNo("0");
        } else {
            salesErrorSalesTransactionDiscountEntity
                    .setPromotionNo(nonItemDiscountDetail.getNonItemPromotionNumber());
        }
        salesErrorSalesTransactionDiscountEntity
                .setStoreDiscountType(nonItemDiscountDetail.getNonItemStoreDiscountType());
        salesErrorSalesTransactionDiscountEntity
                .setQuantityCode(nonItemDiscountDetail.getNonItemQuantityCode());
        salesErrorSalesTransactionDiscountEntity
                .setDiscountQuantity(nonItemDiscountDetail.getNonItemDiscountQty());

        Price nonItemDiscountAmtTaxExcluded =
                nonItemDiscountDetail.getNonItemDiscountAmtTaxExcluded();
        if (nonItemDiscountAmtTaxExcluded != null) {
            salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemDiscountAmtTaxExcluded.getCurrencyCode()));
            salesErrorSalesTransactionDiscountEntity
                    .setDiscountAmountTaxExcluded(nonItemDiscountAmtTaxExcluded.getValue());
        }
        Price nonItemDiscountAmtTaxIncluded =
                nonItemDiscountDetail.getNonItemDiscountAmtTaxIncluded();
        if (nonItemDiscountAmtTaxIncluded != null) {
            salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemDiscountAmtTaxIncluded.getCurrencyCode()));
            salesErrorSalesTransactionDiscountEntity
                    .setDiscountAmountTaxIncluded(nonItemDiscountAmtTaxIncluded.getValue());
        }
        return salesErrorSalesTransactionDiscountEntity;
    }

    /**
     * Convert data to sales error sales transaction tax entity (for insert).
     * 
     * @param nonItemTaxDetail NonItemTaxDetail data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemTaxCount Item tax count.
     * @return TSalesErrorSalesTransactionTaxEntity.
     */
    public SalesErrorSalesTransactionTax convertTSalesErrorSalesTransactionTaxEntityForInsertNon(
            NonItemTaxDetail nonItemTaxDetail, String transactionId, int orderSubNumber,
            String userId, String salesTransactionErrorId, int detailCount, int itemTaxCount) {
        SalesErrorSalesTransactionTax salesErrorSalesTransactionTaxEntity =
                new SalesErrorSalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionTaxEntity);

        salesErrorSalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        salesErrorSalesTransactionTaxEntity.setSalesTransactionId(transactionId);
        salesErrorSalesTransactionTaxEntity.setDetailSubNumber(detailCount);
        salesErrorSalesTransactionTaxEntity.setTaxSubNumber(itemTaxCount);

        salesErrorSalesTransactionTaxEntity.setTaxGroup(nonItemTaxDetail.getNonItemTaxType());
        salesErrorSalesTransactionTaxEntity
                .setTaxAmountSign(nonItemTaxDetail.getNonItemTaxAmountSign());
        salesErrorSalesTransactionTaxEntity.setTaxRate(nonItemTaxDetail.getNonItemTaxRate());
        salesErrorSalesTransactionTaxEntity.setTaxName(nonItemTaxDetail.getNonItemTaxName());

        Price nonItemTaxAmt = nonItemTaxDetail.getNonItemTaxAmt();
        if (nonItemTaxAmt != null) {
            salesErrorSalesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(nonItemTaxAmt.getCurrencyCode()));
            salesErrorSalesTransactionTaxEntity.setTaxAmountValue(nonItemTaxAmt.getValue());
        }
        return salesErrorSalesTransactionTaxEntity;
    }

    /**
     * Convert data to sales error sales transaction tax entity (for insert).
     * 
     * @param itemTaxDetail ItemTaxDetail data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data id.
     * @param detailCount Detail count.
     * @param itemTaxCount Item tax count.
     * @return TSalesErrorSalesTransactionTaxEntity.
     */
    public SalesErrorSalesTransactionTax convertTSalesErrorSalesTransactionTaxEntityForInsert(
            ItemTaxDetail itemTaxDetail, String transactionId, int orderSubNumber, String userId,
            String salesTransactionErrorId, int detailCount, int itemTaxCount) {
        SalesErrorSalesTransactionTax salesErrorSalesTransactionTaxEntity =
                new SalesErrorSalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionTaxEntity);

        salesErrorSalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionTaxEntity.setSalesTransactionId(transactionId);
        salesErrorSalesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        salesErrorSalesTransactionTaxEntity.setDetailSubNumber(detailCount);
        salesErrorSalesTransactionTaxEntity.setTaxSubNumber(itemTaxCount);

        salesErrorSalesTransactionTaxEntity.setTaxGroup(itemTaxDetail.getItemTaxType());
        salesErrorSalesTransactionTaxEntity.setTaxAmountSign(itemTaxDetail.getItemTaxAmountSign());
        salesErrorSalesTransactionTaxEntity.setTaxRate(itemTaxDetail.getItemTaxRate());
        salesErrorSalesTransactionTaxEntity.setTaxName(itemTaxDetail.getItemTaxName());

        Price itemTaxAmt = itemTaxDetail.getItemTaxAmt();
        if (itemTaxAmt != null) {
            salesErrorSalesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(itemTaxAmt.getCurrencyCode()));
            salesErrorSalesTransactionTaxEntity.setTaxAmountValue(itemTaxAmt.getValue());
        }
        return salesErrorSalesTransactionTaxEntity;
    }

    /**
     * Convert data to sales error sales transaction tender entity (for insert).
     * 
     * @param salesTransactionTender SalesTransactionTender data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param payDetailCount Pay detail count.
     * @return TSalesErrorSalesTransactionTenderEntity.
     */
    public SalesErrorSalesTransactionTender convertTSalesErrorSalesTransactionTenderEntityForInsert(
            SalesTransactionTender salesTransactionTender, String transactionId, int orderSubNumber,
            String userId, String salesTransactionErrorId, int payDetailCount) {
        SalesErrorSalesTransactionTender salesErrorSalesTransactionTenderEntity =
                new SalesErrorSalesTransactionTender();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionTenderEntity);
        modelMapper.map(salesTransactionTender, salesErrorSalesTransactionTenderEntity);

        salesErrorSalesTransactionTenderEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionTenderEntity.setSalesTransactionId(transactionId);
        salesErrorSalesTransactionTenderEntity.setOrderSubNumber(orderSubNumber);
        salesErrorSalesTransactionTenderEntity.setTenderSubNumber(payDetailCount);

        salesErrorSalesTransactionTenderEntity
                .setTenderGroup(salesTransactionTender.getTenderGroupId());

        Price taxIncludedPaymentAmount = salesTransactionTender.getTaxIncludedPaymentAmount();
        if (taxIncludedPaymentAmount != null) {
            salesErrorSalesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(taxIncludedPaymentAmount.getCurrencyCode()));
            salesErrorSalesTransactionTenderEntity
                    .setTaxIncludedPaymentAmountValue(taxIncludedPaymentAmount.getValue());
        }
        return salesErrorSalesTransactionTenderEntity;
    }

    /**
     * Convert data to sales error sales transaction tender info entity (for insert).
     * 
     * @param tenderInfo TenderInfo data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param tenderGroupId Tender group id.
     * @param tenderId Tender id.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param tenderSubNumber Tender sub number.
     * @return TSalesErrorSalesTransactionTenderInfoEntity.
     */
    public SalesErrorSalesTransactionTenderInfo convertTSalesErrorSalesTransactionTenderInfoEntityForInsert(
            TenderInfo tenderInfo, String transactionId, int orderSubNumber, String tenderGroupId,
            String tenderId, String userId, String salesTransactionErrorId, int tenderSubNumber) {
        SalesErrorSalesTransactionTenderInfo salesErrorSalesTransactionTenderInfoEntity =
                new SalesErrorSalesTransactionTenderInfo();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionTenderInfoEntity);
        modelMapper.map(tenderInfo, salesErrorSalesTransactionTenderInfoEntity);

        salesErrorSalesTransactionTenderInfoEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionTenderInfoEntity.setSalesTransactionId(transactionId);
        salesErrorSalesTransactionTenderInfoEntity.setOrderSubNumber(orderSubNumber);
        salesErrorSalesTransactionTenderInfoEntity.setTenderGroup(tenderGroupId);
        salesErrorSalesTransactionTenderInfoEntity.setTenderId(tenderId);
        salesErrorSalesTransactionTenderInfoEntity.setTenderSubNumber(tenderSubNumber);

        salesErrorSalesTransactionTenderInfoEntity.setDiscountRate(tenderInfo.getDiscountRate());

        Price discountAmount = tenderInfo.getDiscountAmount();
        if (discountAmount != null) {
            salesErrorSalesTransactionTenderInfoEntity.setDiscountValueCurrencyCode(
                    PriceUtils.getCurrencyCode(discountAmount.getCurrencyCode()));
            salesErrorSalesTransactionTenderInfoEntity.setDiscountValue(discountAmount.getValue());
        }

        Price couponMinUsageAmountThreshold = tenderInfo.getCouponMinUsageAmountThreshold();
        if (couponMinUsageAmountThreshold != null) {
            salesErrorSalesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdCurrencyCode(
                    PriceUtils.getCurrencyCode(couponMinUsageAmountThreshold.getCurrencyCode()));
            salesErrorSalesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdValue(
                    couponMinUsageAmountThreshold.getValue());
        }

        Price couponDiscountAmountSetting = tenderInfo.getCouponDiscountAmountSetting();
        if (couponDiscountAmountSetting != null) {
            salesErrorSalesTransactionTenderInfoEntity.setCouponDiscountAmountSettingCurrencyCode(
                    PriceUtils.getCurrencyCode(couponDiscountAmountSetting.getCurrencyCode()));
            salesErrorSalesTransactionTenderInfoEntity
                    .setCouponDiscountAmountSettingValue(couponDiscountAmountSetting.getValue());
        }
        return salesErrorSalesTransactionTenderInfoEntity;
    }


    /**
     * Convert data to sales error sales transaction tax entity (for insert).
     * 
     * @param salesTransactionTaxDetail SalesTransactionTaxDetail data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param taxDetailCount Tax detail count.
     * @return TSalesErrorSalesTransactionTaxEntity.
     */
    public SalesErrorSalesTransactionTax convertTSalesErrorSalesTransactionTaxEntityForInsertTransaction(
            SalesTransactionTaxDetail salesTransactionTaxDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId, int detailCount,
            int taxDetailCount) {

        SalesErrorSalesTransactionTax salesErrorSalesTransactionTaxEntity =
                new SalesErrorSalesTransactionTax();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionTaxEntity);
        modelMapper.map(salesTransactionTaxDetail, salesErrorSalesTransactionTaxEntity);

        salesErrorSalesTransactionTaxEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionTaxEntity.setSalesTransactionId(transactionId);
        salesErrorSalesTransactionTaxEntity.setOrderSubNumber(orderSubNumber);
        salesErrorSalesTransactionTaxEntity.setDetailSubNumber(detailCount);
        salesErrorSalesTransactionTaxEntity.setTaxSubNumber(taxDetailCount);

        Price taxAmount = salesTransactionTaxDetail.getTaxAmount();
        if (taxAmount != null) {
            salesErrorSalesTransactionTaxEntity.setTaxAmountCurrencyCode(
                    PriceUtils.getCurrencyCode(taxAmount.getCurrencyCode()));
            salesErrorSalesTransactionTaxEntity.setTaxAmountValue(taxAmount.getValue());
        }
        return salesErrorSalesTransactionTaxEntity;
    }

    /**
     * Convert data to sales error sales transaction total amount entity (for insert).
     * 
     * @param salesTransactionTotal SalesTransactionTotal data.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param totalDetailCount Total detail count.
     * @return TSalesErrorSalesTransactionTotalAmountEntity.
     */
    public SalesErrorSalesTransactionTotalAmount convertTSalesErrorSalesTransactionTotalAmountEntityForInsert(
            SalesTransactionTotal salesTransactionTotal, String transactionId, int orderSubNumber,
            String userId, String salesTransactionErrorId, int totalDetailCount) {

        SalesErrorSalesTransactionTotalAmount salesErrorSalesTransactionTotalAmountEntity =
                new SalesErrorSalesTransactionTotalAmount();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesErrorSalesTransactionTotalAmountEntity);
        modelMapper.map(salesTransactionTotal, salesErrorSalesTransactionTotalAmountEntity);

        salesErrorSalesTransactionTotalAmountEntity.setTransactionId(salesTransactionErrorId);
        salesErrorSalesTransactionTotalAmountEntity.setSalesTransactionId(transactionId);
        salesErrorSalesTransactionTotalAmountEntity.setOrderSubNumber(orderSubNumber);
        salesErrorSalesTransactionTotalAmountEntity.setTotalAmountSubNumber(totalDetailCount);

        salesErrorSalesTransactionTotalAmountEntity
                .setTaxRate(salesTransactionTotal.getConsumptionTaxRate());

        Price totalAmountTaxExcluded = salesTransactionTotal.getTotalAmountTaxExcluded();
        if (totalAmountTaxExcluded != null) {
            salesErrorSalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode(
                    PriceUtils.getCurrencyCode(totalAmountTaxExcluded.getCurrencyCode()));
            salesErrorSalesTransactionTotalAmountEntity
                    .setTotalAmountTaxExcludedValue(totalAmountTaxExcluded.getValue());
        }

        Price totalAmountTaxIncluded = salesTransactionTotal.getTotalAmountTaxIncluded();
        if (totalAmountTaxIncluded != null) {
            salesErrorSalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode(
                    PriceUtils.getCurrencyCode(totalAmountTaxIncluded.getCurrencyCode()));
            salesErrorSalesTransactionTotalAmountEntity
                    .setTotalAmountTaxIncludedValue(totalAmountTaxIncluded.getValue());
        }
        return salesErrorSalesTransactionTotalAmountEntity;
    }

    /**
     * Format date from yyyy-MM-dd to yyyyMMdd.
     * 
     * @param date The date with format 'yyyy-MM-dd'.
     * @return The date string with 'yyyyMMdd'.
     */
    private String formatDateForDB(String date) {
        if (StringUtils.isEmpty(date))
            return date;

        LocalDate localDate = DateUtility.parseDate(date, DateUtility.DateFormat.UUUUHMMHDD);
        return DateUtility.formatDate(localDate, DateUtility.DateFormat.UUUUMMDD);
    }
}
