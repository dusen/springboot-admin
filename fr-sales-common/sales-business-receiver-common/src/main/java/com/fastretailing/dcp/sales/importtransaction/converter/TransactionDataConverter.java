/**
 * @(#)TransactionDataConverter.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.converter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemDiscount;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDiscountDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.storecommon.util.DateUtility;

/**
 * The class is used to convert data to inner format.
 *
 */
@Component
public class TransactionDataConverter {

    /**
     * Convert import data to inner format transaction data.
     * 
     * @param transactionImportData Transaction import data.
     */
    public void convertTransactionImportData(TransactionImportData transactionImportData) {
        transactionImportData.setOrderConfirmationBusinessDate(
                changeToDbFormatter(transactionImportData.getOrderConfirmationBusinessDate()));
        if (CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())) {
            for (Transaction transaction : transactionImportData.getTransactionList()) {
                transaction.setDataCreationBusinessDate(
                        changeToDbFormatter(transaction.getDataCreationBusinessDate()));
                transaction.setOrderStatusUpdateDate(
                        changeToDbFormatter(transaction.getOrderStatusUpdateDate()));
                if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
                    for (TransactionItemDetail itemDetail : transaction
                            .getTransactionItemDetailList()) {
                        convertItemDetail(itemDetail);
                    }
                }
                if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
                    for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                        convertNonItemDetail(nonItemDetail);
                    }
                }
            }
        }
    }

    /**
     * Change to database formatter, convert formatter from UUUUHMMHDD to UUUUMMDD.
     * 
     * @param orderDate Order date.
     * @return UUUUMMDD formatter date.
     */
    public String changeToDbFormatter(String orderDate) {
        if (StringUtils.isEmpty(orderDate)) {
            return null;
        }
        return DateUtility.formatDate(
                DateUtility.parseDate(orderDate, DateUtility.DateFormat.UUUUHMMHDD),
                DateUtility.DateFormat.UUUUMMDD);
    }

    /**
     * Change formatter convert UUUUMMDD to UUUUHMMHDD.
     * 
     * @param dbDate Db Date.
     * @return UUUUHMMHDD formatter date.
     */
    public String changeFormatter(String dbDate) {
        if (StringUtils.isEmpty(dbDate)) {
            return null;
        }
        return DateUtility.formatDate(
                DateUtility.parseDate(dbDate, DateUtility.DateFormat.UUUUMMDD),
                DateUtility.DateFormat.UUUUHMMHDD);
    }

    /**
     * Convert non item detail.
     * 
     * @param nonItemDetail non item detail.
     */
    private void convertNonItemDetail(NonItemDetail nonItemDetail) {
        nonItemDetail.setOrderStatusUpdateDate(
                changeToDbFormatter(nonItemDetail.getOrderStatusUpdateDate()));
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemDiscountDetailList())) {
            for (NonItemDiscountDetail nonItemDiscount : nonItemDetail
                    .getNonItemDiscountDetailList()) {
                if (StringUtils.isEmpty(nonItemDiscount.getNonItemPromotionNumber())) {
                    nonItemDiscount.setNonItemPromotionNumber("0");
                }
                if (StringUtils.isEmpty(nonItemDiscount.getNonItemStoreDiscountType())) {
                    nonItemDiscount.setNonItemStoreDiscountType("0");
                }
            }
        }
    }

    /**
     * Convert item detail.
     * 
     * @param itemDetail item detail.
     */
    private void convertItemDetail(TransactionItemDetail itemDetail) {
        itemDetail.setOrderStatusUpdateDate(
                changeToDbFormatter(itemDetail.getOrderStatusUpdateDate()));
        if (CollectionUtils.isNotEmpty(itemDetail.getNonItemDetailListByItem())) {
            for (NonItemDetail nonItemDetail : itemDetail.getNonItemDetailListByItem()) {
                nonItemDetail.setOrderStatusUpdateDate(
                        changeToDbFormatter(nonItemDetail.getOrderStatusUpdateDate()));
                if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemDiscountDetailList())) {
                    nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscount -> {
                        if (StringUtils.isEmpty(nonItemDiscount.getNonItemPromotionNumber())) {
                            nonItemDiscount.setNonItemPromotionNumber("0");
                        }
                        if (StringUtils.isEmpty(nonItemDiscount.getNonItemStoreDiscountType())) {
                            nonItemDiscount.setNonItemStoreDiscountType("0");
                        }
                    });
                }
            }
        }
        if (CollectionUtils.isNotEmpty(itemDetail.getItemDiscountList())) {
            for (ItemDiscount itemDiscount : itemDetail.getItemDiscountList()) {
                if (StringUtils.isEmpty(itemDiscount.getItemPromotionNumber())) {
                    itemDiscount.setItemPromotionNumber("0");
                }
                if (StringUtils.isEmpty(itemDiscount.getItemStoreDiscountType())) {
                    itemDiscount.setItemStoreDiscountType("0");
                }
            }
        }

    }

}
