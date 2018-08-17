/**
 * @(#)SalesTransactionCorrectionConvert.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.convert;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.common.entity.optional.TenderAndTransformInformationOptional;
import com.fastretailing.dcp.sales.common.repository.optional.TenderAndTransformMasterOptionalMapper;
import com.fastretailing.dcp.sales.salestransactioncorrection.common.DeleteCheckFlag;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportItemDiscount;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportItemTaxDetail;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportNonItemDetail;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportNonItemDiscountDetail;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportNonItemTaxDetail;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportSalesTransactionTaxDetail;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportSalesTransactionTender;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportSalesTransactionTotal;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportTransaction;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportTransactionImportData;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportTransactionItemDetail;

/**
 * Sales transaction correction convert.
 */
@Component
public class SalesTransactionCorrectionConvert {

    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

    /**
     * Tender and transform master optional mapper.
     */
    @Autowired
    private TenderAndTransformMasterOptionalMapper tenderAndTransformMasterOptionalMapper;

    /**
     * Slash mark.
     */
    public static String SLASH_MARK = "/";

    /**
     * Hyphen mark.
     */
    public static String HYPHEN_MARK = "-";

    /**
     * Convert transaction import data form.
     * 
     * @param importTransactionImportData Import transaction import data.
     * @param actionFlag Action flag {true:java to web,false:web to java}.
     * @return Import transaction import data.
     */
    public ImportTransactionImportData convertTransactionImportDataForm(
            ImportTransactionImportData importTransactionImportData, boolean actionFlag) {

        // Order information convert.
        convertImportTransactionImportData(importTransactionImportData, actionFlag);

        List<ImportTransaction> transactionList = importTransactionImportData.getTransactionList();
        if (CollectionUtils.isNotEmpty(transactionList)) {
            transactionList.forEach(transaction -> {
                convertTransaction(transaction, actionFlag,
                        importTransactionImportData.getStoreCode());
            });
        }
        return importTransactionImportData;
    }

    /**
     * Convert transaction.
     * 
     * @param transaction Transaction.
     * @param actionFlag Action flag.
     * @param storeCode Store code.
     * 
     */
    public void convertTransaction(ImportTransaction transaction, boolean actionFlag,
            String storeCode) {
        // Transaction convert.
        if (actionFlag) {
            transaction.setFormatDataCreationDateTime(
                    formatOffsetDateTimeToString(transaction.getDataCreationDateTime()));
            transaction.setFormatOrderStatusLastUpdateDateTime(
                    formatOffsetDateTimeToString(transaction.getOrderStatusLastUpdateDateTime()));
            transaction.setFormatOrderCancellationDate(
                    formatOffsetDateTimeToString(transaction.getOrderCancellationDate()));

            transaction.setFormatDataCreationBusinessDate(parseStringDateByActionFlag(
                    transaction.getDataCreationBusinessDate(), actionFlag));
            transaction.setFormatOrderStatusUpdateDate(parseStringDateByActionFlag(
                    transaction.getOrderStatusUpdateDate(), actionFlag));

        } else {
            transaction.setDataCreationDateTime(
                    parseStringToOffsetDateTime(transaction.getFormatDataCreationDateTime()));
            transaction.setOrderStatusLastUpdateDateTime(parseStringToOffsetDateTime(
                    transaction.getFormatOrderStatusLastUpdateDateTime()));
            transaction.setOrderCancellationDate(
                    parseStringToOffsetDateTime(transaction.getFormatOrderCancellationDate()));

            transaction.setDataCreationBusinessDate(parseStringDateByActionFlag(
                    transaction.getFormatDataCreationBusinessDate(), actionFlag));
            transaction.setOrderStatusUpdateDate(parseStringDateByActionFlag(
                    transaction.getFormatOrderStatusUpdateDate(), actionFlag));
        }
        List<ImportTransactionItemDetail> transactionItemDetailList =
                transaction.getTransactionItemDetailList();
        if (CollectionUtils.isNotEmpty(transactionItemDetailList)) {
            transactionItemDetailList.removeIf(transactionItemDetail -> DeleteCheckFlag.DELETED
                    .is(transactionItemDetail.getDeleteCheckFlag()));
            if (CollectionUtils.isNotEmpty(transactionItemDetailList)) {
                transactionItemDetailList.forEach(transactionItemDetail -> {
                    convertTransactionItemDetail(transactionItemDetail, actionFlag);
                });
            }
        }

        List<ImportNonItemDetail> nonItemDetailList = transaction.getNonItemDetailList();
        if (CollectionUtils.isNotEmpty(nonItemDetailList)) {
            nonItemDetailList.removeIf(nonItemDetail -> DeleteCheckFlag.DELETED
                    .is(nonItemDetail.getDeleteCheckFlag()));
            if (CollectionUtils.isNotEmpty(nonItemDetailList)) {
                nonItemDetailList.removeIf(nonItemDetail -> DeleteCheckFlag.DELETED
                        .is(nonItemDetail.getDeleteCheckFlag()));
                if (CollectionUtils.isNotEmpty(nonItemDetailList)) {
                    nonItemDetailList.forEach(nonItemDetail -> {
                        convertNonItemDetail(nonItemDetail, actionFlag);
                        List<ImportNonItemDiscountDetail> discountList =
                                nonItemDetail.getNonItemDiscountDetailList();
                        if (CollectionUtils.isNotEmpty(discountList)) {
                            discountList.removeIf(discount -> DeleteCheckFlag.DELETED
                                    .is(discount.getDeleteCheckFlag()));
                        }
                        List<ImportNonItemTaxDetail> taxList =
                                nonItemDetail.getNonItemTaxDetailList();
                        if (CollectionUtils.isNotEmpty(taxList)) {
                            taxList.removeIf(
                                    tax -> DeleteCheckFlag.DELETED.is(tax.getDeleteCheckFlag()));
                        }
                    });
                }
            }
        }
        if (actionFlag) {
            List<ImportSalesTransactionTender> salesTransactionTenderList =
                    transaction.getSalesTransactionTenderList();
            if (CollectionUtils.isNotEmpty(salesTransactionTenderList)) {
                salesTransactionTenderList.forEach(salesTransactionTender -> {
                    convertSalesTransactionTender(salesTransactionTender, storeCode);
                });
            }
        } else {
            List<ImportSalesTransactionTotal> totalList =
                    transaction.getSalesTransactionTotalList();
            if (CollectionUtils.isNotEmpty(totalList)) {
                totalList.removeIf(total -> DeleteCheckFlag.DELETED.is(total.getDeleteCheckFlag()));
            }

            List<ImportSalesTransactionTender> tenderList =
                    transaction.getSalesTransactionTenderList();
            if (CollectionUtils.isNotEmpty(tenderList)) {
                tenderList.removeIf(
                        tender -> DeleteCheckFlag.DELETED.is(tender.getDeleteCheckFlag()));
            }

            List<ImportSalesTransactionTaxDetail> taxList =
                    transaction.getSalesTransactionTaxDetailList();
            if (CollectionUtils.isNotEmpty(taxList)) {
                taxList.removeIf(tax -> DeleteCheckFlag.DELETED.is(tax.getDeleteCheckFlag()));
            }
        }
    }

    /**
     * Convert sales transaction tender.
     * 
     * @param salesTransactionTender Sales transaction tender.
     * @param storeCode Store code.
     */
    public void convertSalesTransactionTender(ImportSalesTransactionTender salesTransactionTender,
            String storeCode) {
        if (StringUtils.isEmpty(salesTransactionTender.getTenderId())) {
            return;
        }
        TenderAndTransformInformationOptional tenderAndTransformInformationOptional = null;
        
        tenderAndTransformInformationOptional = tenderAndTransformMasterOptionalMapper
                .getTenderInfoByTenderId(storeCode, salesTransactionTender.getTenderId());

        if (tenderAndTransformInformationOptional != null) {
            salesTransactionTender
                    .setImsTenderGroup(tenderAndTransformInformationOptional.getImsTenderGroup());
            salesTransactionTender
                    .setImsTenderId(tenderAndTransformInformationOptional.getImsTenderId());
            salesTransactionTender
                    .setTenderName(tenderAndTransformInformationOptional.getTenderName());
        }
    }

    /**
     * Convert transaction item detail.
     * 
     * @param transactionItemDetail Transaction item detail.
     * @param actionFlag Action flag.
     */
    public void convertTransactionItemDetail(ImportTransactionItemDetail transactionItemDetail,
            boolean actionFlag) {
        if (actionFlag) {
            transactionItemDetail
                    .setFormatOrderStatusLastUpdateDateTime(formatOffsetDateTimeToString(
                            transactionItemDetail.getOrderStatusLastUpdateDateTime()));
            transactionItemDetail.setFormatOrderStatusUpdateDate(parseStringDateByActionFlag(
                    transactionItemDetail.getOrderStatusUpdateDate(), actionFlag));
        } else {
            List<ImportItemTaxDetail> importantItemTaxDetailList =
                    transactionItemDetail.getItemTaxDetailList();
            if (CollectionUtils.isNotEmpty(importantItemTaxDetailList)) {
                importantItemTaxDetailList
                        .removeIf(tax -> DeleteCheckFlag.DELETED.is(tax.getDeleteCheckFlag()));
            }
            List<ImportItemDiscount> importantItemDiscountList =
                    transactionItemDetail.getItemDiscountList();
            if (CollectionUtils.isNotEmpty(importantItemDiscountList)) {
                importantItemDiscountList.removeIf(
                        discount -> DeleteCheckFlag.DELETED.is(discount.getDeleteCheckFlag()));
            }
            transactionItemDetail.setOrderStatusLastUpdateDateTime(parseStringToOffsetDateTime(
                    transactionItemDetail.getFormatOrderStatusLastUpdateDateTime()));
            transactionItemDetail.setOrderStatusUpdateDate(parseStringDateByActionFlag(
                    transactionItemDetail.getFormatOrderStatusUpdateDate(), actionFlag));
        }
        List<ImportNonItemDetail> nonItemDetailList =
                transactionItemDetail.getNonItemDetailListByItem();
        if (CollectionUtils.isNotEmpty(nonItemDetailList)) {
            nonItemDetailList.removeIf(nonItemDetail -> DeleteCheckFlag.DELETED
                    .is(nonItemDetail.getDeleteCheckFlag()));
            if (CollectionUtils.isNotEmpty(nonItemDetailList)) {
                nonItemDetailList.forEach(nonItemDetail -> {
                    convertNonItemDetail(nonItemDetail, actionFlag);
                    List<ImportNonItemDiscountDetail> discountList =
                            nonItemDetail.getNonItemDiscountDetailList();
                    if (CollectionUtils.isNotEmpty(discountList)) {
                        discountList.removeIf(discount -> DeleteCheckFlag.DELETED
                                .is(discount.getDeleteCheckFlag()));
                    }
                    List<ImportNonItemTaxDetail> taxList = nonItemDetail.getNonItemTaxDetailList();
                    if (CollectionUtils.isNotEmpty(taxList)) {
                        taxList.removeIf(
                                tax -> DeleteCheckFlag.DELETED.is(tax.getDeleteCheckFlag()));
                    }
                });
            }
        }

    }

    /**
     * Convert non item detail.
     * 
     * @param nonItemdetail Non item detail.
     * @param actionFlag Action flag.
     */
    public void convertNonItemDetail(ImportNonItemDetail nonItemdetail, boolean actionFlag) {
        if (actionFlag) {
            nonItemdetail.setFormatOrderStatusLastUpdateDateTime(
                    formatOffsetDateTimeToString(nonItemdetail.getOrderStatusLastUpdateDateTime()));
            nonItemdetail.setFormatOrderStatusUpdateDate(parseStringDateByActionFlag(
                    nonItemdetail.getOrderStatusUpdateDate(), actionFlag));
        } else {
            nonItemdetail.setOrderStatusLastUpdateDateTime(parseStringToOffsetDateTime(
                    nonItemdetail.getFormatOrderStatusLastUpdateDateTime()));
            nonItemdetail.setOrderStatusUpdateDate(parseStringDateByActionFlag(
                    nonItemdetail.getFormatOrderStatusUpdateDate(), actionFlag));
        }
    }

    /**
     * Convert import transaction import data.
     * 
     * @param importTransactionImportData Import transaction import data.
     * @param actionFlag Action flag.
     */
    public void convertImportTransactionImportData(
            ImportTransactionImportData importTransactionImportData, boolean actionFlag) {
        if (actionFlag) {
            importTransactionImportData
                    .setFormatOrderConfirmationDateTime(formatOffsetDateTimeToString(
                            importTransactionImportData.getOrderConfirmationDateTime()));
            importTransactionImportData
                    .setFormatOrderConfirmationBusinessDate(parseStringDateByActionFlag(
                            importTransactionImportData.getOrderConfirmationBusinessDate(),
                            actionFlag));
        } else {
            importTransactionImportData.setOrderConfirmationDateTime(parseStringToOffsetDateTime(
                    importTransactionImportData.getFormatOrderConfirmationDateTime()));
            importTransactionImportData
                    .setOrderConfirmationBusinessDate(parseStringDateByActionFlag(
                            importTransactionImportData.getFormatOrderConfirmationBusinessDate(),
                            actionFlag));
        }
    }

    /**
     * Parse string date by action flag.
     * 
     * @param stringDate String date.
     * @param actionFlag Action flag {true:java to web,false:web to java}.
     * @return Formated string date.
     */
    public String parseStringDateByActionFlag(String stringDate, boolean actionFlag) {
        if (stringDate == null) {
            return null;
        }
        if (actionFlag) {
            return stringDate.replace(HYPHEN_MARK, SLASH_MARK);
        }
        return stringDate.replace(SLASH_MARK, HYPHEN_MARK);
    }


    /**
     * Format offset date time to string.
     * 
     * @param offsetDateTime Offset date time.
     * @return String of offset date time.
     */
    public String formatOffsetDateTimeToString(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return offsetDateTime.format(DATE_TIME_FORMAT);
    }

    /**
     * Parse string to offset date time.
     * 
     * @param dateTimeString Date time string.
     * @return Offset date time of string.
     */
    public OffsetDateTime parseStringToOffsetDateTime(String dateTimeString) {
        if (StringUtils.isEmpty(dateTimeString)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter =
                DATE_TIME_FORMAT.withResolverStyle(ResolverStyle.STRICT);

        return OffsetDateTime.of(LocalDateTime.parse(dateTimeString, dateTimeFormatter),
                ZoneOffset.UTC);
    }

}
