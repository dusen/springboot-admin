/**
 * @(#)SalesTransactionCorrectionHistoryDetailConvert.java
 *
 *                                                         Copyright (c) 2018 Fast Retailing
 *                                                         Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.convert;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.common.entity.optional.TenderAndTransformInformationOptional;
import com.fastretailing.dcp.sales.common.repository.optional.TenderAndTransformMasterOptionalMapper;
import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport.ImportNonItemDetail;
import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport.ImportSalesTransactionTender;
import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport.ImportTransaction;
import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport.ImportTransactionImportData;
import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport.ImportTransactionItemDetail;

/**
 * Sales transaction correction convert.
 */
@Component
public class SalesTransactionCorrectionHistoryDetailConvert {

    /** Date time format(uuuu/MM/dd HH:mm:ss). */
    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

    /** Tender and transform master optional mapper. */
    @Autowired
    private TenderAndTransformMasterOptionalMapper tenderAndTransformMasterOptionalMapper;

    /** Model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /** Slash mark. */
    private static String SLASH_MARK = "/";

    /** Parse date hyphen. */
    private static final String PARSE_DATE_HYPHEN = "hyphen";

    /** Parse date slash. */
    private static final String PARSE_DATE_SLASH = "slash";

    /** Hyphen mark. */
    private static final String HYPHEN_MARK = "-";

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
            transactionList.forEach(transaction -> convertTransaction(transaction, actionFlag,
                    importTransactionImportData.getStoreCode()));
        }
        return importTransactionImportData;
    }

    /**
     * Convert transaction.
     * 
     * @param transaction Import transaction.
     * @param actionFlag Action flag.
     * @param storeCode Store code.
     */
    private void convertTransaction(ImportTransaction transaction, boolean actionFlag,
            String storeCode) {
        // Transaction convert.
        if (actionFlag) {
            transaction.setFormatDataCreationDateTime(
                    formatOffsetDateTimeToString(transaction.getDataCreationDateTime()));
            transaction.setFormatOrderStatusLastUpdateDateTime(
                    formatOffsetDateTimeToString(transaction.getOrderStatusLastUpdateDateTime()));
            transaction.setFormatOrderCancellationDate(
                    formatOffsetDateTimeToString(transaction.getOrderCancellationDate()));

            transaction.setFormatDataCreationBusinessDate(
                    parseStringDate(transaction.getDataCreationBusinessDate(), PARSE_DATE_HYPHEN));
            transaction.setFormatOrderStatusUpdateDate(
                    parseStringDate(transaction.getOrderStatusUpdateDate(), PARSE_DATE_HYPHEN));

        } else {
            transaction.setDataCreationDateTime(
                    parseStringToOffsetDateTime(transaction.getFormatDataCreationDateTime()));
            transaction.setOrderStatusLastUpdateDateTime(parseStringToOffsetDateTime(
                    transaction.getFormatOrderStatusLastUpdateDateTime()));
            transaction.setOrderCancellationDate(
                    parseStringToOffsetDateTime(transaction.getFormatOrderCancellationDate()));

            transaction.setDataCreationBusinessDate(parseStringDate(
                    transaction.getFormatDataCreationBusinessDate(), PARSE_DATE_SLASH));
            transaction.setOrderStatusUpdateDate(parseStringDate(
                    transaction.getFormatOrderStatusUpdateDate(), PARSE_DATE_SLASH));
        }
        List<ImportTransactionItemDetail> transactionItemDetailList =
                transaction.getTransactionItemDetailList();
        if (CollectionUtils.isNotEmpty(transactionItemDetailList)) {
            transactionItemDetailList.forEach(transactionItemDetail -> convertTransactionItemDetail(
                    transactionItemDetail, actionFlag));
        }

        List<ImportNonItemDetail> nonItemDetailList = transaction.getNonItemDetailList();
        if (CollectionUtils.isNotEmpty(nonItemDetailList)) {
            nonItemDetailList
                    .forEach(nonItemDetail -> convertNonItemDetail(nonItemDetail, actionFlag));
        }
        if (actionFlag) {
            List<ImportSalesTransactionTender> salesTransactionTenderList =
                    transaction.getSalesTransactionTenderList();
            if (CollectionUtils.isNotEmpty(salesTransactionTenderList)) {
                salesTransactionTenderList
                        .forEach(salesTransactionTender -> convertSalesTransactionTender(
                                salesTransactionTender, storeCode));
            }
        }
    }

    /**
     * Convert sales transaction tender.
     * 
     * @param salesTransactiontender Import sales transaction tender.
     * @param storeCode Store code.
     */
    public void convertSalesTransactionTender(ImportSalesTransactionTender salesTransactiontender,
            String storeCode) {
        if (StringUtils.isEmpty(salesTransactiontender.getTenderId())) {
            return;
        }
        TenderAndTransformInformationOptional tenderAndTransformInfoOptional = null;

        tenderAndTransformInfoOptional = tenderAndTransformMasterOptionalMapper
                .getTenderInfoByTenderId(storeCode, salesTransactiontender.getTenderId());

        if (tenderAndTransformInfoOptional != null) {
            modelMapper.map(tenderAndTransformInfoOptional, salesTransactiontender);
        }
    }

    /**
     * Convert transaction item detail.
     * 
     * @param transactionItemDetail Transaction item detail.
     * @param actionFlag Action flag.
     */
    private void convertTransactionItemDetail(ImportTransactionItemDetail transactionItemDetail,
            boolean actionFlag) {
        if (actionFlag) {
            transactionItemDetail
                    .setFormatOrderStatusLastUpdateDateTime(formatOffsetDateTimeToString(
                            transactionItemDetail.getOrderStatusLastUpdateDateTime()));
            transactionItemDetail.setFormatOrderStatusUpdateDate(parseStringDate(
                    transactionItemDetail.getOrderStatusUpdateDate(), PARSE_DATE_HYPHEN));
        } else {
            transactionItemDetail.setOrderStatusLastUpdateDateTime(parseStringToOffsetDateTime(
                    transactionItemDetail.getFormatOrderStatusLastUpdateDateTime()));
            transactionItemDetail.setOrderStatusUpdateDate(parseStringDate(
                    transactionItemDetail.getFormatOrderStatusUpdateDate(), PARSE_DATE_SLASH));
        }
        List<ImportNonItemDetail> nonItemDetailList =
                transactionItemDetail.getNonItemDetailListByItem();
        if (CollectionUtils.isNotEmpty(nonItemDetailList)) {
            nonItemDetailList
                    .forEach(nonItemDetail -> convertNonItemDetail(nonItemDetail, actionFlag));
        }

    }

    /**
     * Convert non item detail.
     * 
     * @param nonItemdetail Non item detail.
     * @param actionFlag Action flag.
     */
    private void convertNonItemDetail(ImportNonItemDetail nonItemDetail, boolean actionFlag) {
        if (actionFlag) {
            nonItemDetail.setFormatOrderStatusLastUpdateDateTime(
                    formatOffsetDateTimeToString(nonItemDetail.getOrderStatusLastUpdateDateTime()));
            nonItemDetail.setFormatOrderStatusUpdateDate(
                    parseStringDate(nonItemDetail.getOrderStatusUpdateDate(), PARSE_DATE_HYPHEN));
        } else {
            nonItemDetail.setOrderStatusLastUpdateDateTime(parseStringToOffsetDateTime(
                    nonItemDetail.getFormatOrderStatusLastUpdateDateTime()));
            nonItemDetail.setOrderStatusUpdateDate(parseStringDate(
                    nonItemDetail.getFormatOrderStatusUpdateDate(), PARSE_DATE_SLASH));
        }
    }

    /**
     * Convert import transaction import data.
     * 
     * @param importTransactionImportData Import transaction import data.
     * @param actionFlag Action flag.
     */
    private void convertImportTransactionImportData(
            ImportTransactionImportData importTransactionImportData, boolean actionFlag) {
        if (actionFlag) {
            importTransactionImportData
                    .setFormatOrderConfirmationDateTime(formatOffsetDateTimeToString(
                            importTransactionImportData.getOrderConfirmationDateTime()));
            importTransactionImportData.setFormatOrderConfirmationBusinessDate(
                    parseStringDate(importTransactionImportData.getOrderConfirmationBusinessDate(),
                            PARSE_DATE_HYPHEN));
        } else {
            importTransactionImportData.setOrderConfirmationDateTime(parseStringToOffsetDateTime(
                    importTransactionImportData.getFormatOrderConfirmationDateTime()));
            importTransactionImportData.setOrderConfirmationBusinessDate(parseStringDate(
                    importTransactionImportData.getFormatOrderConfirmationBusinessDate(),
                    PARSE_DATE_SLASH));
        }
    }

    /**
     * Parse string date by get.
     * 
     * @param stringDate String date.
     * @param parseStringDateByAction Parse String date by action.
     * @return Formated string date.
     */
    public String parseStringDate(String stringDate, String parseStringDateByAction) {
        if (stringDate == null) {
            return null;
        }
        if (parseStringDateByAction.equals(PARSE_DATE_HYPHEN)) {
            return stringDate.replace(HYPHEN_MARK, SLASH_MARK);
        } else {
            return stringDate.replace(SLASH_MARK, HYPHEN_MARK);
        }
    }

    /**
     * Format offset date time to string.
     * 
     * @param offsetDateTime Offset date time.
     * @return String of offset date time.
     */
    private String formatOffsetDateTimeToString(OffsetDateTime offsetDateTime) {
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
    private OffsetDateTime parseStringToOffsetDateTime(String dateTimeString) {
        if (StringUtils.isEmpty(dateTimeString)) {
            return null;
        }
        return OffsetDateTime.of(LocalDateTime.parse(dateTimeString, DATE_TIME_FORMAT),
                ZoneOffset.UTC);
    }

}
