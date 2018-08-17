/**
 * @(#)TransactionInquiryHelper.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.common.constants.TransactionInquiryConstants;
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
import com.fastretailing.dcp.sales.common.repository.optional.TransactionInquiryOptionalMapper;

/**
 * Helper class for transaction inquiry API's.
 * 
 */
@Component
public class TransactionInquiryHelper {

    /** Transaction inquiry mapper. */
    @Autowired
    private TransactionInquiryOptionalMapper transactionInquiryMapper;

    /** Transaction inquiry mapper. */
    @Autowired
    private TransactionInquiryConverter transactionInquiryConverter;

    /**
     * Method for filling transaction import data. Called by:
     * sales-information-integrated-orders(API#1)<br>
     * sales-information-order-barcodes(API#3)<br>
     * 
     * @param integratedOrderId integrated order id.
     * @param inquiryPattern Inquiry pattern.
     * @return Transaction import data as API response.
     */
    public TransactionImportData createTransactionImportData(String integratedOrderId,
            String inquiryPattern) {

        TransactionImportData transactionImportData = new TransactionImportData();
        List<TransactionInquiryOptional> databaseEntityList = transactionInquiryMapper
                .selectByIntegratedOrderIdInquiryPattern(integratedOrderId, inquiryPattern);
        if (CollectionUtils.isEmpty(databaseEntityList)) {
            return transactionImportData;
        }

        transactionImportData = transactionInquiryConverter
                .convertToTransactionImportData(databaseEntityList.get(0));
        transactionImportData.setTransactionList(createTransactionList(databaseEntityList));
        return transactionImportData;
    }

    /**
     * Method for filling transaction import data. Called by:
     * sales-information-integrated-orders-sub-numbers(API#2)<br>
     * sales-information-order-barcodes-sub-numbers(API#4)<br>
     * 
     * @param integratedOrderId integrated order id.
     * @param orderSubNumber order sub number.
     * @return Transaction import data as API response.
     */
    public TransactionImportData createTransactionImportData(String integratedOrderId,
            Integer orderSubNumber) {

        TransactionImportData transactionImportData = new TransactionImportData();
        List<TransactionInquiryOptional> databaseEntityList = transactionInquiryMapper
                .selectByIntegratedOrderIdOrderSubNumber(integratedOrderId, orderSubNumber);
        if (CollectionUtils.isEmpty(databaseEntityList)) {
            return transactionImportData;
        }

        transactionImportData = transactionInquiryConverter
                .convertToTransactionImportData(databaseEntityList.get(0));
        transactionImportData.setTransactionList(createTransactionList(databaseEntityList));
        return transactionImportData;
    }

    /**
     * Method for filling transaction import data.<br>
     * Called by: sales-information-transactions(API#5)<br>
     * sales-information-receipts(API#6)<br>
     *
     * @param integratedOrderId integrated order id.
     * @param orderSubNumber order sub number.
     * @param salesTransactionId sales transaction id.
     * @return Transaction import data as API response.
     */
    public TransactionImportData createTransactionImportData(String integratedOrderId,
            Integer orderSubNumber, String salesTransactionId) {

        TransactionImportData transactionImportData = new TransactionImportData();
        List<TransactionInquiryOptional> databaseEntityList = transactionInquiryMapper
                .selectByTransactionId(integratedOrderId, orderSubNumber, salesTransactionId);
        if (CollectionUtils.isEmpty(databaseEntityList)) {
            return transactionImportData;
        }

        transactionImportData = transactionInquiryConverter
                .convertToTransactionImportData(databaseEntityList.get(0));
        transactionImportData.setTransactionList(createTransactionList(databaseEntityList));
        return transactionImportData;
    }

    /**
     * Method for filling transaction's sub list.
     *
     * @param databaseEntityList database entity list.
     * @return transaction list.
     */
    private List<Transaction> createTransactionList(
            List<TransactionInquiryOptional> databaseEntityList) {

        List<TransactionInquiryOptional> databaseUniqueList = new ArrayList<>();
        databaseEntityList.stream()
                .collect(Collectors.groupingBy(tran -> tran.getHeaderSalesTransactionId(),
                        Collectors.toList()))
                .forEach((transactionId, list) -> {
                    databaseUniqueList.add(list.get(0));
                });

        List<TransactionInquiryOptional> sortList = databaseUniqueList.stream()
                .sorted(Comparator.comparing(TransactionInquiryOptional::getHeaderOrderSubNumber)
                        .thenComparing(
                                TransactionInquiryOptional::getHeaderSalesTransactionSubNumber))
                .collect(Collectors.toList());

        List<Transaction> transactionList = new ArrayList<>();
        for (int headerCount = 0; headerCount < sortList.size(); headerCount++) {
            Transaction transaction =
                    transactionInquiryConverter.convertToTransaction(sortList.get(headerCount));

            // Create transaction item detail list.
            List<TransactionItemDetail> transactionItemDetailList =
                    createTransactionItemDetailList(databaseEntityList,
                            transaction.getOrderSubNumber(), transaction.getSalesTransactionId());

            for (TransactionItemDetail transactionItemDetail : transactionItemDetailList) {
                // Create non item detail list by item.
                List<NonItemDetail> nonItemDetailList = createNonItemDetailListByItem(
                        databaseEntityList, transaction.getOrderSubNumber(),
                        transaction.getSalesTransactionId(),
                        transactionItemDetail.getItemDetailNumber());

                if (!CollectionUtils.isEmpty(nonItemDetailList)) {
                    for (NonItemDetail nonItemDetail : nonItemDetailList) {
                        // Create non item information.
                        nonItemDetail.setNonItemInfo(createNonItemInfo(databaseEntityList,
                                transaction.getOrderSubNumber(),
                                transaction.getSalesTransactionId(),
                                nonItemDetail.getDetailSubNumber()));

                        // Create non item discount detail list.
                        nonItemDetail.setNonItemDiscountDetailList(createNonItemDiscountDetailList(
                                databaseEntityList, transaction.getOrderSubNumber(),
                                transaction.getSalesTransactionId(),
                                nonItemDetail.getDetailSubNumber()));

                        // Create non item tax detail list.
                        nonItemDetail.setNonItemTaxDetailList(createNonItemTaxDetailList(
                                databaseEntityList, transaction.getOrderSubNumber(),
                                transaction.getSalesTransactionId(),
                                nonItemDetail.getDetailSubNumber()));
                    }
                }
                // Set non item detail list by item.
                transactionItemDetail.setNonItemDetailListByItem(nonItemDetailList);

                // Create item discount list.
                transactionItemDetail.setItemDiscountList(createItemDiscountList(databaseEntityList,
                        transaction.getOrderSubNumber(), transaction.getSalesTransactionId(),
                        transactionItemDetail.getDetailSubNumber()));

                // Create item tax detail list.
                transactionItemDetail.setItemTaxDetailList(
                        createItemTaxDetailList(databaseEntityList, transaction.getOrderSubNumber(),
                                transaction.getSalesTransactionId(),
                                transactionItemDetail.getDetailSubNumber()));
            }

            // Transaction item detail.
            transaction.setTransactionItemDetailList(transactionItemDetailList);

            // Create non item detail list.
            List<NonItemDetail> nonItemDetailList = createNonItemDetailList(databaseEntityList,
                    transaction.getOrderSubNumber(), transaction.getSalesTransactionId());

            for (NonItemDetail nonItemDetail : nonItemDetailList) {

                // Create non item information.
                nonItemDetail.setNonItemInfo(createNonItemInfo(databaseEntityList,
                        transaction.getOrderSubNumber(), transaction.getSalesTransactionId(),
                        nonItemDetail.getDetailSubNumber()));

                // Create non item discount detail list.
                nonItemDetail.setNonItemDiscountDetailList(createNonItemDiscountDetailList(
                        databaseEntityList, transaction.getOrderSubNumber(),
                        transaction.getSalesTransactionId(), nonItemDetail.getDetailSubNumber()));

                // Create non item tax detail list.
                nonItemDetail.setNonItemTaxDetailList(createNonItemTaxDetailList(databaseEntityList,
                        transaction.getOrderSubNumber(), transaction.getSalesTransactionId(),
                        nonItemDetail.getDetailSubNumber()));
            }

            // Non item detail.
            if (!CollectionUtils.isEmpty(nonItemDetailList)) {
                transaction.setNonItemDetailList(nonItemDetailList);
            }

            // Create sales transaction tender list.
            List<SalesTransactionTender> salesTransactionTenderList =
                    createSalesTransactionTenderList(databaseEntityList,
                            transaction.getOrderSubNumber(), transaction.getSalesTransactionId());

            for (SalesTransactionTender salesTransactionTender : salesTransactionTenderList) {

                // Create tender information list.
                salesTransactionTender.setTenderInfoList(createTenderInfoList(databaseEntityList,
                        transaction.getOrderSubNumber(), transaction.getSalesTransactionId(),
                        salesTransactionTender.getTenderGroupId(),
                        salesTransactionTender.getTenderId(),
                        salesTransactionTender.getTenderSubNumber()));
            }

            // Sales transaction tender.
            transaction.setSalesTransactionTenderList(salesTransactionTenderList);

            // Create sales transaction tax detail list.
            transaction.setSalesTransactionTaxDetailList(
                    createSalesTransactionTaxDetailList(databaseEntityList,
                            transaction.getOrderSubNumber(), transaction.getSalesTransactionId()));

            // Create sales transaction total list.
            transaction.setSalesTransactionTotalList(
                    createSalesTransactionTotalList(databaseEntityList,
                            transaction.getOrderSubNumber(), transaction.getSalesTransactionId()));

            transactionList.add(transaction);
        }
        return transactionList;

    }

    /**
     * Create transaction item detail list method.
     *
     * @param databaseEntityList database entity list.
     * @param databaseCount count of transaction inquiry tables.
     * @return List of transaction item detail.
     */
    private List<TransactionItemDetail> createTransactionItemDetailList(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId) {

        List<TransactionInquiryOptional> matchList = databaseEntityList.stream()
                .filter(itemDetail -> orderSubNumber.equals(itemDetail.getDetailOrderSubNumber())
                        && salesTransactionId.equals(itemDetail.getDetailSalesTransactionId())
                        && Objects.nonNull(itemDetail.getDetailDetailSubNumber())
                        && TransactionInquiryConstants.ITEM_PRODUCT_CLASSIFICATION
                                .equals(itemDetail.getDetailProductClassification()))
                .collect(Collectors.toList());

        List<TransactionInquiryOptional> uniqueList = new ArrayList<>();
        matchList.stream()
                .collect(
                        Collectors.groupingBy(
                                itemDetail -> itemDetail.getDetailOrderSubNumber()
                                        + itemDetail.getDetailSalesTransactionId()
                                        + itemDetail.getDetailDetailSubNumber(),
                                Collectors.toList()))
                .forEach((transactionId, list) -> {
                    uniqueList.add(list.get(0));
                });

        List<TransactionInquiryOptional> sortList = uniqueList.stream()
                .sorted(Comparator.comparing(TransactionInquiryOptional::getDetailDetailSubNumber))
                .collect(Collectors.toList());

        List<TransactionItemDetail> transactionItemDetailList = new ArrayList<>();
        for (TransactionInquiryOptional match : sortList) {
            transactionItemDetailList.add(transactionInquiryConverter.convertToItemDetail(match));
        }
        return transactionItemDetailList;
    }

    /**
     * Create non-item detail list method.
     *
     * @param databaseEntityList database entity list.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionId Sales transaction id.
     * @param itemDetailNumber Item detail number.
     * @return List of non-item detail.
     */
    private List<NonItemDetail> createNonItemDetailListByItem(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId, Integer itemDetailNumber) {

        List<TransactionInquiryOptional> matchList = databaseEntityList.stream()
                .filter(itemDetail -> orderSubNumber.equals(itemDetail.getDetailOrderSubNumber())
                        && salesTransactionId.equals(itemDetail.getDetailSalesTransactionId())
                        && itemDetailNumber.equals(itemDetail.getDetailItemDetailSubNumber())
                        && TransactionInquiryConstants.NON_ITEM_PRODUCT_CLASSIFICATION
                                .equals(itemDetail.getDetailProductClassification()))
                .collect(Collectors.toList());

        List<TransactionInquiryOptional> uniqueList = new ArrayList<>();
        matchList.stream()
                .collect(
                        Collectors.groupingBy(
                                itemDetail -> itemDetail.getDetailOrderSubNumber()
                                        + itemDetail.getDetailSalesTransactionId()
                                        + itemDetail.getDetailDetailSubNumber(),
                                Collectors.toList()))
                .forEach((key, list) -> {
                    uniqueList.add(list.get(0));
                });

        List<NonItemDetail> nonItemDetailListByItem = new ArrayList<>();
        if (CollectionUtils.isEmpty(uniqueList)) {
            return nonItemDetailListByItem;
        }
        List<TransactionInquiryOptional> sortList = uniqueList.stream()
                .sorted(Comparator.comparing(TransactionInquiryOptional::getDetailDetailSubNumber))
                .collect(Collectors.toList());
        for (TransactionInquiryOptional match : sortList) {
            nonItemDetailListByItem.add(transactionInquiryConverter.convertToNonItemDetail(match));
        }
        return nonItemDetailListByItem;
    }

    /**
     * Create non-item information method.
     *
     * @param databaseEntityList database entity list.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionId Sales transaction id.
     * @param detailSubNumber Detail sub number.
     * @return Non-item information.
     */
    private NonItemInformation createNonItemInfo(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId, Integer detailSubNumber) {

        List<TransactionInquiryOptional> matchList = databaseEntityList.stream()
                .filter(entity -> detailSubNumber.equals(entity.getDetailInfoDetailSubNumber())
                        && orderSubNumber.equals(entity.getDetailInfoOrderSubNumber())
                        && salesTransactionId.equals(entity.getDetailInfoSalesTransactionId()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(matchList)) {
            return null;
        }
        return transactionInquiryConverter.convertToNonItemInformation(matchList.get(0));
    }

    /**
     * Create non-item discount detail list method.
     *
     * @param databaseEntityList database entity list.
     * @param databaseCount count of transaction inquiry tables.
     * @return List of non-item discount detail.
     */
    private List<NonItemDiscountDetail> createNonItemDiscountDetailList(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId, Integer detailSubNumber) {

        List<TransactionInquiryOptional> matchList = databaseEntityList.stream()
                .filter(entity -> detailSubNumber.equals(entity.getDiscountDetailSubNumber())
                        && orderSubNumber.equals(entity.getDiscountOrderSubNumber())
                        && salesTransactionId.equals(entity.getDiscountSalesTransactionId())
                        && detailSubNumber.equals(entity.getDiscountDetailSubNumber())
                        && StringUtils.isNotEmpty(entity.getDiscountPromotionType())
                        && StringUtils.isNotEmpty(entity.getDiscountPromotionNo())
                        && StringUtils.isNotEmpty(entity.getDiscountStoreDiscountType()))
                .collect(Collectors.toList());
        List<TransactionInquiryOptional> uniqueList = new ArrayList<>();
        matchList.stream()
                .collect(Collectors.groupingBy(discount -> discount.getDiscountOrderSubNumber()
                        + discount.getDiscountSalesTransactionId()
                        + discount.getDiscountDetailSubNumber()
                        + discount.getDiscountPromotionType() + discount.getDiscountPromotionNo()
                        + discount.getDiscountStoreDiscountType(), Collectors.toList()))
                .forEach((key, list) -> {
                    uniqueList.add(list.get(0));
                });

        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        if (CollectionUtils.isEmpty(matchList)) {
            return nonItemDiscountDetailList;
        }

        for (TransactionInquiryOptional match : matchList) {
            nonItemDiscountDetailList
                    .add(transactionInquiryConverter.convertToDiscountDetail(match));
        }
        return nonItemDiscountDetailList;
    }

    /**
     * Create non-item tax detail list method.
     *
     * @param databaseEntityList database entity list.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionId Sales transaction id.
     * @param detailSubNumber Detail sub number.
     * @returnList of non-item tax detail.
     */
    private List<NonItemTaxDetail> createNonItemTaxDetailList(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId, Integer detailSubNumber) {

        List<TransactionInquiryOptional> matchList = databaseEntityList.stream()
                .filter(entity -> detailSubNumber.equals(entity.getTaxDetailSubNumber())
                        && orderSubNumber.equals(entity.getTaxOrderSubNumber())
                        && salesTransactionId.equals(entity.getTaxSalesTransactionId())
                        && Objects.nonNull(entity.getTaxDetailSubNumber())
                        && StringUtils.isNotEmpty(entity.getTaxTaxGroup())
                        && Objects.nonNull(entity.getTaxTaxSubNumber()))
                .collect(Collectors.toList());

        List<TransactionInquiryOptional> uniqueList = new ArrayList<>();
        matchList.stream()
                .collect(Collectors.groupingBy(discount -> discount.getTaxOrderSubNumber()
                        + discount.getTaxSalesTransactionId() + discount.getTaxDetailSubNumber()
                        + discount.getTaxTaxGroup() + discount.getTaxTaxSubNumber(),
                        Collectors.toList()))
                .forEach((key, list) -> {
                    uniqueList.add(list.get(0));
                });

        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        if (CollectionUtils.isEmpty(uniqueList)) {
            return nonItemTaxDetailList;
        }

        for (TransactionInquiryOptional match : uniqueList) {
            nonItemTaxDetailList.add(transactionInquiryConverter.convertToNonItemTaxDetail(match));
        }
        return nonItemTaxDetailList;
    }

    /**
     * Create item discount list method.
     *
     * @param databaseEntityList database entity list.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionId Sales transaction id.
     * @param detailSubNumber Detail sub number.
     * @return List of item discount.
     */
    private List<ItemDiscount> createItemDiscountList(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId, Integer detailSubNumber) {

        List<TransactionInquiryOptional> matchList = databaseEntityList.stream()
                .filter(entity -> detailSubNumber.equals(entity.getDiscountDetailSubNumber())
                        && orderSubNumber.equals(entity.getDiscountOrderSubNumber())
                        && salesTransactionId.equals(entity.getDiscountSalesTransactionId())
                        && StringUtils.isNotEmpty(entity.getDiscountPromotionType())
                        && StringUtils.isNotEmpty(entity.getDiscountPromotionNo())
                        && StringUtils.isNotEmpty(entity.getDiscountStoreDiscountType()))
                .collect(Collectors.toList());
        List<TransactionInquiryOptional> uniqueList = new ArrayList<>();
        matchList.stream()
                .collect(Collectors.groupingBy(discount -> discount.getDiscountOrderSubNumber()
                        + discount.getDiscountSalesTransactionId()
                        + discount.getDiscountDetailSubNumber()
                        + discount.getDiscountPromotionType() + discount.getDiscountPromotionNo()
                        + discount.getDiscountStoreDiscountType(), Collectors.toList()))
                .forEach((key, list) -> {
                    uniqueList.add(list.get(0));
                });

        List<ItemDiscount> itemDiscountList = new ArrayList<>();
        if (CollectionUtils.isEmpty(uniqueList)) {
            return itemDiscountList;
        }

        for (TransactionInquiryOptional match : uniqueList) {
            itemDiscountList.add(transactionInquiryConverter.convertToItemDiscount(match));
        }
        return itemDiscountList;
    }

    /**
     * Create item tax detail list method.
     *
     * @param databaseEntityList database entity list.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionId Sales transaction id.
     * @param detailSubNumber Detail sub number.
     * @return List of item tax detail.
     */
    private List<ItemTaxDetail> createItemTaxDetailList(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId, Integer detailSubNumber) {

        List<TransactionInquiryOptional> matchList = databaseEntityList.stream()
                .filter(entity -> detailSubNumber.equals(entity.getTaxDetailSubNumber())
                        && orderSubNumber.equals(entity.getTaxOrderSubNumber())
                        && salesTransactionId.equals(entity.getTaxSalesTransactionId())
                        && StringUtils.isNotEmpty(entity.getTaxTaxGroup())
                        && Objects.nonNull(entity.getTaxTaxSubNumber()))
                .collect(Collectors.toList());
        List<TransactionInquiryOptional> uniqueList = new ArrayList<>();
        matchList.stream()
                .collect(Collectors.groupingBy(discount -> discount.getTaxOrderSubNumber()
                        + discount.getTaxSalesTransactionId() + discount.getTaxDetailSubNumber()
                        + discount.getTaxTaxGroup() + discount.getTaxTaxSubNumber(),
                        Collectors.toList()))
                .forEach((key, list) -> {
                    uniqueList.add(list.get(0));
                });

        List<ItemTaxDetail> itemTaxDetailList = new ArrayList<>();
        if (CollectionUtils.isEmpty(uniqueList)) {
            return itemTaxDetailList;
        }

        for (TransactionInquiryOptional match : uniqueList) {
            itemTaxDetailList.add(transactionInquiryConverter.convertToItemTaxDetail(match));
        }
        return itemTaxDetailList;
    }

    /**
     * Create non-item detail list method.
     *
     * @param databaseEntityList database entity list.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionId Sales transaction id.
     * @return List of non-item detail.
     */
    private List<NonItemDetail> createNonItemDetailList(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId) {

        List<TransactionInquiryOptional> matchList = databaseEntityList.stream()
                .filter(entity -> TransactionInquiryConstants.NON_ITEM_PRODUCT_CLASSIFICATION
                        .equals(entity.getDetailProductClassification()))
                .filter(entity -> TransactionInquiryConstants.ZERO_ITEM_DETAIL_SUB_NUMBER
                        .equals(entity.getDetailItemDetailSubNumber())
                        && orderSubNumber.equals(entity.getDetailOrderSubNumber()))
                .filter(entity -> salesTransactionId.equals(entity.getDetailSalesTransactionId()))
                .collect(Collectors.toList());

        List<TransactionInquiryOptional> uniqueList = new ArrayList<>();
        matchList.stream()
                .collect(
                        Collectors
                                .groupingBy(
                                        discount -> discount.getDetailOrderSubNumber()
                                                + discount.getDetailSalesTransactionId()
                                                + discount.getDetailDetailSubNumber(),
                                        Collectors.toList()))
                .forEach((key, list) -> {
                    uniqueList.add(list.get(0));
                });

        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        if (CollectionUtils.isEmpty(uniqueList)) {
            return nonItemDetailList;
        }
        List<TransactionInquiryOptional> sortList = uniqueList.stream()
                .sorted(Comparator.comparing(TransactionInquiryOptional::getDetailDetailSubNumber))
                .collect(Collectors.toList());
        for (TransactionInquiryOptional match : sortList) {
            nonItemDetailList.add(transactionInquiryConverter.convertToNonItemDetail(match));
        }
        return nonItemDetailList;
    }


    /**
     * Create sales transaction tender list method.
     *
     * @param databaseEntityList database entity list.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionId Sales transaction id.
     * @return List of sales transaction tender.
     */
    private List<SalesTransactionTender> createSalesTransactionTenderList(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId) {

        List<SalesTransactionTender> salesTransactionTenderList = new ArrayList<>();

        List<TransactionInquiryOptional> databaseTenderList = databaseEntityList.stream()
                .filter(tender -> orderSubNumber.equals(tender.getTenderOrderSubNumber())
                        && salesTransactionId.equals(tender.getTenderSalesTransactionId())
                        && StringUtils.isNotEmpty(tender.getTenderTenderGroup())
                        && StringUtils.isNotEmpty(tender.getTenderTenderId())
                        && Objects.nonNull(tender.getTenderTenderSubNumber()))
                .collect(Collectors.toList());
        List<TransactionInquiryOptional> databaseUniqueList = new ArrayList<>();
        databaseTenderList.stream()
                .collect(Collectors.groupingBy(tender -> tender.getTenderOrderSubNumber()
                        + tender.getTenderSalesTransactionId() + tender.getTenderTenderGroup()
                        + tender.getTenderTenderId() + tender.getTenderTenderSubNumber(),
                        Collectors.toList()))
                .forEach((transactionId, list) -> {
                    databaseUniqueList.add(list.get(0));
                });
        List<TransactionInquiryOptional> sortList = databaseUniqueList.stream()
                .sorted(Comparator.comparing(TransactionInquiryOptional::getTenderTenderSubNumber))
                .collect(Collectors.toList());
        for (int tenderCount = 0; tenderCount < sortList.size(); tenderCount++) {
            salesTransactionTenderList.add(transactionInquiryConverter
                    .convertToTransactionTender(sortList.get(tenderCount)));
        }
        return salesTransactionTenderList;
    }

    /**
     * Create tender information list method.
     *
     * @param databaseEntityList database entity list.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionId Sales transaction id.
     * @param tenderGroupId Tender group id.
     * @param tenderId Tender id.
     * @param tenderSubNumber Tender sub number.
     * @return Tender information.
     */
    private TenderInformation createTenderInfoList(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId, String tenderGroupId, String tenderId,
            Integer tenderSubNumber) {

        List<TransactionInquiryOptional> matchList = databaseEntityList.stream()
                .filter(entity -> tenderGroupId.equals(entity.getTenderInfoTenderGroup())
                        && tenderId.equals(entity.getTenderInfoTenderId())
                        && tenderSubNumber.equals(entity.getTenderInfoTenderSubNumber())
                        && orderSubNumber.equals(entity.getTenderInfoOrderSubNumber())
                        && salesTransactionId.equals(entity.getTenderInfoSalesTransactionId()))
                .collect(Collectors.toList());
        TenderInformation tenderInformation = new TenderInformation();
        if (CollectionUtils.isEmpty(matchList)) {
            return tenderInformation;
        }

        return transactionInquiryConverter.convertToTenderInfo(matchList.get(0));
    }

    /**
     * Create sales transaction tax detail list method.
     *
     * @param databaseEntityList database entity list.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionId Sales transaction id.
     * @return List of sales transaction tax detail.
     */
    private List<SalesTransactionTaxDetail> createSalesTransactionTaxDetailList(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId) {

        List<TransactionInquiryOptional> matchList = databaseEntityList.stream()
                .filter(entity -> TransactionInquiryConstants.ZERO_ITEM_DETAIL_SUB_NUMBER
                        .equals(entity.getTaxDetailSubNumber())
                        && orderSubNumber.equals(entity.getTaxOrderSubNumber())
                        && salesTransactionId.equals(entity.getTaxSalesTransactionId())
                        && StringUtils.isNotEmpty(entity.getTaxTaxGroup())
                        && Objects.nonNull(entity.getTaxTaxSubNumber()))
                .collect(Collectors.toList());
        List<TransactionInquiryOptional> uniqueList = new ArrayList<>();
        matchList.stream()
                .collect(Collectors.groupingBy(discount -> discount.getTaxOrderSubNumber()
                        + discount.getTaxSalesTransactionId() + discount.getTaxDetailSubNumber()
                        + discount.getTaxTaxGroup() + discount.getTaxTaxSubNumber(),
                        Collectors.toList()))
                .forEach((key, list) -> {
                    uniqueList.add(list.get(0));
                });
        List<SalesTransactionTaxDetail> salesTransactionTaxDetailList = new ArrayList<>();
        if (CollectionUtils.isEmpty(uniqueList)) {
            return salesTransactionTaxDetailList;
        }

        for (TransactionInquiryOptional match : uniqueList) {
            salesTransactionTaxDetailList
                    .add(transactionInquiryConverter.convertToTaxDetail(match));
        }
        return salesTransactionTaxDetailList;
    }

    /**
     * Create sales transaction total list method.
     *
     * @param databaseEntityList database entity list.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionId Sales transaction id.
     * @return List of sales transaction total.
     */
    private List<SalesTransactionTotal> createSalesTransactionTotalList(
            List<TransactionInquiryOptional> databaseEntityList, Integer orderSubNumber,
            String salesTransactionId) {

        List<TransactionInquiryOptional> matchList = databaseEntityList.stream()
                .filter(entity -> TransactionInquiryConstants.ZERO_ITEM_DETAIL_SUB_NUMBER
                        .equals(entity.getTaxDetailSubNumber())
                        && orderSubNumber.equals(entity.getTotalOrderSubNumber())
                        && salesTransactionId.equals(entity.getTotalSalesTransactionId())
                        && StringUtils.isNotEmpty(entity.getTotalTotalType()))
                .collect(Collectors.toList());
        List<TransactionInquiryOptional> uniqueList = new ArrayList<>();
        matchList.stream()
                .collect(Collectors.groupingBy(discount -> discount.getTotalOrderSubNumber()
                        + discount.getTotalSalesTransactionId() + discount.getTotalTotalType(),
                        Collectors.toList()))
                .forEach((key, list) -> {
                    uniqueList.add(list.get(0));
                });

        List<SalesTransactionTotal> salesTransactionTotalList = new ArrayList<>();
        for (int totalCount = 0; totalCount < uniqueList.size(); totalCount++) {
            salesTransactionTotalList.add(transactionInquiryConverter
                    .convertToTransactionTotal(uniqueList.get(totalCount)));
        }
        return salesTransactionTotalList;
    }
}

