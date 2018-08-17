/**
 * @(#)ValidationChecker.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Path.Node;
import javax.validation.Validator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.common.type.TaxSystemType;
import com.fastretailing.dcp.sales.common.type.TransactionType;
import com.fastretailing.dcp.sales.common.type.UpdateType;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemDiscount;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDiscountDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTotal;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Correction;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Delete;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Insert;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.PosInsert;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Update;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.storecommon.dto.Price;

/**
 * The class is used to validate data.
 *
 */
@Component
public class ValidationChecker {

    /** Validation check object. */
    @Autowired
    private Validator validator;

    /** Checker helper. */
    @Autowired
    private CheckerHelper checkerHelper;

    /** Object mapper. */
    @Autowired
    private ObjectMapper objectMapper;

    /** Tender group id is CREDIT. */
    private static final String TENDER_GROUP_ID_CREDIT = "CREDIT";

    /** Payment sign negative. */
    private static final String PAYMENT_SIGN_NEGATIVE = "N";

    /** Payment sign positive. */
    private static final String PAYMENT_SIGN_POSITIVE = "P";

    /** Error id quantity code. */
    private static final String ERROR_ID_QUANTITY_CODE = "quantity_code";

    /** Error id non item tax amount sign. */
    private static final String ERROR_ID_NON_ITEM_TAX_AMOUNT_SIGN_CODE = "non_item_tax_amount_sign";

    /** Error id card number. */
    private static final String ERROR_ID_CARD_NO = "card_no";

    /** Error id payment sign. */
    private static final String ERROR_ID_PAYMENT_SIGN = "payment_sign";

    /** Error id transaction type. */
    private static final String ERROR_ID_TRANSACTION_TYPE = "transaction_type";

    /** Error id tax amount sign. */
    private static final String ERROR_ID_TAX_AMOUNT_SIGN = "tax_amount_sign";

    /** Error id detail list sales transaction type. */
    private static final String ERROR_ID_DETAIL_LIST_SALES_TRANSACTION_TYPE =
            "detail_list_sales_transaction_type";

    /** Error id item unit price tax included. */
    private static final String ERROR_ID_ITEM_UNIT_PRICE_TAX_INCLUDED =
            "item_unit_price_tax_included";

    /** Error id item unit price tax excluded. */
    private static final String ERROR_ID_ITEM_UNIT_PRICE_TAX_EXCLUDED =
            "item_unit_price_tax_excluded";

    /** Error id item quantity code. */
    private static final String ERROR_ID_ITEM_QUANTITY_CODE = "item_quantity_code";

    /** Error id item discount amount tax included. */
    private static final String ERROR_ID_ITEM_DISCOUNT_AMT_TAX_INCLUDED =
            "item_discount_amt_tax_included";

    /** Error id item discount amount tax excluded. */
    private static final String ERROR_ID_ITEM_DISCOUNT_AMT_TAX_EXCLUDED =
            "item_discount_amt_tax_excluded";

    /** Error id item tax amount sign. */
    private static final String ERROR_ID_ITEM_TAX_AMOUNT_SIGN = "item_tax_amount_sign";

    /** Error id non md detail list sales transaction type. */
    private static final String ERROR_ID_NON_MD_DETAIL_LIST_SALES_TRANSACTION_TYPE =
            "non_md_detail_list_sales_transaction_type";

    /** Error id non item unit price tax included. */
    private static final String ERROR_ID_NON_ITEM_UNIT_PRICE_TAX_INCLUDED =
            "non_item_unit_price_tax_included";

    /** Error id non item unit price tax excluded. */
    private static final String ERROR_ID_NON_ITEM_UNIT_PRICE_TAX_EXCLUDED =
            "non_item_unit_price_tax_excluded";

    /** Error id non item quantity code. */
    private static final String ERROR_ID_NON_ITEM_QUANTITY_CODE = "non_item_quantity_code";

    /** Error id non item discount amount tax included. */
    private static final String ERROR_ID_NON_ITEM_DISCOUNT_AMT_TAX_INCLUDED =
            "non_item_discount_amt_tax_included";

    /** Error id non item discount amount tax excluded. */
    private static final String ERROR_ID_NON_ITEM_DISCOUNT_AMT_TAX_EXCLUDED =
            "non_item_discount_amt_tax_excluded";

    /** Error id system brand code. */
    private static final String ERROR_ID_SYSTEM_BRAND_CODE = "system_brand_code";

    /** Error id integrated order id. */
    private static final String ERROR_ID_INTEGRATED_ORDER_ID = "integrated_order_id";

    /** Error id sales transaction id. */
    private static final String ERROR_ID_SALES_TRANSACTION_ID = "sales_transaction_id";

    /** Error id store code. */
    private static final String ERROR_ID_STORE_CODE = "store_code";

    /** Error id system business code. */
    private static final String ERROR_ID_SYSTEM_BUSINESS_CODE = "system_business_code";

    /** Error id system country code. */
    private static final String ERROR_ID_SYSTEM_COUNTRY_CODE = "system_country_code";

    /** Validation group of pos insert. */
    private static final String VALIDATION_GROUP_POS_INSERT = "PosInsert";

    /** Error occurred list name transaction list. */
    private static final String ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST = "transaction_list";

    /** Error occurred list name non item detail list. */
    private static final String ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST =
            "non_item_detail_list";

    /** Error occurred list name transaction item detail list. */
    private static final String ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST =
            "transaction_item_detail_list";

    /** Error occurred list name non item detail list by item. */
    private static final String ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST_BY_ITEM =
            "non_item_detail_list_by_item";

    /** Error occurred list name sales transaction tender list. */
    private static final String ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TENDER_LIST =
            "sales_transaction_tender_list";

    /** Error occurred list name sales transaction tax detail list. */
    private static final String ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TAX_DETAIL_LIST =
            "sales_transaction_tax_detail_list";

    /** Error occurred list name non item discount detail list. */
    private static final String ERROR_OCCURRED_LIST_NAME_NON_ITEM_DISCOUNT_DETAIL_LIST =
            "non_item_discount_detail_list";

    /** Error occurred list name non item tax detail list. */
    private static final String ERROR_OCCURRED_LIST_NAME_NON_ITEM_TAX_DETAIL_LIST =
            "non_item_tax_detail_list";

    /** Error occurred list name item discount list. */
    private static final String ERROR_OCCURRED_LIST_NAME_ITEM_DISCOUNT_LIST = "item_discount_list";

    /** Error occurred list name item tax detail list. */
    private static final String ERROR_OCCURRED_LIST_NAME_ITEM_TAX_DETAIL_LIST =
            "item_tax_detail_list";

    /** Error occurred list name sales transaction total list. */
    private static final String ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TOTAL_LIST =
            "sales_transaction_total_list";

    /**
     * Check the validation of transaction import data.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param isStore Is store.
     * @return True normal, false error.
     */
    public boolean checkValidation(TransactionImportData transactionImportData,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, boolean isStore) {
        String validationGroup = transactionImportData.getUpdateType();
        if (isStore) {
            validationGroup = VALIDATION_GROUP_POS_INSERT;
        }
        if (!validateLevel(transactionImportData, validationGroup,
                salesTransactionErrorDetailEntity, null)) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())) {
            for (Transaction transaction : transactionImportData.getTransactionList()) {
                checkerHelper.mapLevel2Transaction(transaction, salesTransactionErrorDetailEntity);
                if (!validateLevel(transaction, validationGroup, salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST)) {
                    return false;
                }

                if (!TransactionType.compare(TransactionType.PVOID,
                        transaction.getTransactionType())) {
                    if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
                        for (TransactionItemDetail transactionItemDetail : transaction
                                .getTransactionItemDetailList()) {
                            if (!checkTransactionItemDetailValidation(transactionItemDetail,
                                    salesTransactionErrorDetailEntity, validationGroup)) {
                                return false;
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
                        for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                            if (!checkTransactionNonItemDetailValidation(nonItemDetail,
                                    salesTransactionErrorDetailEntity, validationGroup)) {
                                return false;
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTenderList())) {
                        for (SalesTransactionTender salesTransactionTender : transaction
                                .getSalesTransactionTenderList()) {
                            if (!checkTransactionTenderValidation(salesTransactionTender,
                                    salesTransactionErrorDetailEntity, validationGroup)) {
                                return false;
                            }
                        }
                    }
                    if (CollectionUtils
                            .isNotEmpty(transaction.getSalesTransactionTaxDetailList())) {
                        for (SalesTransactionTaxDetail salesTransactionTaxDetail : transaction
                                .getSalesTransactionTaxDetailList()) {
                            checkerHelper.mapLevel3SalesTransactionTaxDetail(
                                    salesTransactionTaxDetail, salesTransactionErrorDetailEntity);

                            if (!validateLevel(salesTransactionTaxDetail, validationGroup,
                                    salesTransactionErrorDetailEntity,
                                    ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TAX_DETAIL_LIST)) {
                                return false;
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTotalList())) {
                        for (SalesTransactionTotal salesTransactionTotal : transaction
                                .getSalesTransactionTotalList()) {
                            checkerHelper.mapLevel3SalesTransactionTotal(salesTransactionTotal,
                                    salesTransactionErrorDetailEntity);

                            if (!validateLevel(salesTransactionTotal, validationGroup,
                                    salesTransactionErrorDetailEntity,
                                    ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TOTAL_LIST)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Check additional validation of transaction import data.
     * 
     * @param transactionImportData transaction import data.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    public boolean checkAdditionalValidation(TransactionImportData transactionImportData,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        String updateType = transactionImportData.getUpdateType();
        if (CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())) {
            for (Transaction transaction : transactionImportData.getTransactionList()) {
                if (!checkTransactionAddition(transaction, salesTransactionErrorDetailEntity,
                        updateType)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check transaction type.
     * 
     * @param transactionType Transaction type.
     * @param transactionTypeId Transaction type id.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param errorOccurredListName Error occurred list name.
     * @return True normal, false error.
     */
    private boolean checkTransactionType(String transactionType, String transactionTypeId,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            String errorOccurredListName) {
        if (!(TransactionType.compare(TransactionType.SALE, transactionType)
                || TransactionType.compare(TransactionType.RETURN, transactionType)
                || TransactionType.compare(TransactionType.PVOID, transactionType))) {
            insertSalesTransactionErrorValidation(salesTransactionErrorDetailEntity,
                    transactionTypeId, transactionType, errorOccurredListName);
            return false;
        }
        return true;
    }

    /**
     * Check transaction addition.
     * 
     * @param transaction transaction data.
     * @param salesTransactionErrorDetailEntity sales transaction error detail entity.
     * @param updateType Update type.
     * @return True normal, false error.
     */
    private boolean checkTransactionAddition(Transaction transaction,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String updateType) {
        checkerHelper.mapLevel2Transaction(transaction, salesTransactionErrorDetailEntity);
        if (StringUtils.isNotEmpty(transaction.getTransactionType())
                && !checkTransactionType(transaction.getTransactionType(),
                        ERROR_ID_TRANSACTION_TYPE, salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST)) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
            for (TransactionItemDetail transactionItemDetail : transaction
                    .getTransactionItemDetailList()) {
                if (!checkTransactionItemDetailAddition(transactionItemDetail,
                        salesTransactionErrorDetailEntity, updateType)) {
                    return false;
                }
            }
            if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
                for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                    if (!checkNonItemDetailAddition(nonItemDetail,
                            salesTransactionErrorDetailEntity, updateType)) {
                        return false;
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTenderList())) {
                for (SalesTransactionTender tender : transaction.getSalesTransactionTenderList()) {
                    checkerHelper.mapLevel3SalesTransactionTender(tender,
                            salesTransactionErrorDetailEntity);
                    if (StringUtils.isNotEmpty(tender.getPaymentSign())
                            && !checkPaymentSign(tender.getPaymentSign(), ERROR_ID_PAYMENT_SIGN,
                                    salesTransactionErrorDetailEntity,
                                    ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TENDER_LIST)) {
                        return false;
                    }
                    if (!checkCardNo(updateType, tender, salesTransactionErrorDetailEntity,
                            ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TENDER_LIST)) {
                        return false;
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTaxDetailList())) {
                for (SalesTransactionTaxDetail salesTransactionTaxDetail : transaction
                        .getSalesTransactionTaxDetailList()) {
                    checkerHelper.mapLevel3SalesTransactionTaxDetail(salesTransactionTaxDetail,
                            salesTransactionErrorDetailEntity);
                    if (StringUtils.isNotEmpty(salesTransactionTaxDetail.getTaxAmountSign())
                            && !checkPaymentSign(salesTransactionTaxDetail.getTaxAmountSign(),
                                    ERROR_ID_TAX_AMOUNT_SIGN, salesTransactionErrorDetailEntity,
                                    ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TAX_DETAIL_LIST)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Check payment sign.
     * 
     * @param paymentSign Payment sign.
     * @param paymentSignId Payment sign id.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param errorOccurredListName Error occurred list name.
     * @return True normal, false error.
     */
    private boolean checkPaymentSign(String paymentSign, String paymentSignId,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            String errorOccurredListName) {
        if (StringUtils.isNotBlank(paymentSign) && !PAYMENT_SIGN_NEGATIVE.equals(paymentSign)
                && !PAYMENT_SIGN_POSITIVE.equals(paymentSign)) {
            insertSalesTransactionErrorValidation(salesTransactionErrorDetailEntity, paymentSignId,
                    paymentSign, errorOccurredListName);
            return false;
        }
        return true;
    }

    /**
     * Check card number.
     * 
     * @param updateType Update type.
     * @param tender tender.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param errorOccurredListName Error occurred list name.
     * @return True normal, false error.
     */
    private boolean checkCardNo(String updateType, SalesTransactionTender tender,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            String errorOccurredListName) {
        if ((UpdateType.CORRECTION.is(updateType) || UpdateType.INSERT.is(updateType))
                && (TENDER_GROUP_ID_CREDIT.equals(tender.getTenderGroupId())
                        && tender.getTenderInfoList() != null
                        && StringUtils.isEmpty(tender.getTenderInfoList().getCardNo()))) {
            insertSalesTransactionErrorValidation(salesTransactionErrorDetailEntity,
                    ERROR_ID_CARD_NO, null, errorOccurredListName);
            return false;
        }
        return true;
    }

    /**
     * Check transaction item detail addition.
     * 
     * @param transactionItemDetail transaction item detail.
     * @param salesTransactionErrorDetailEntity sales transaction error detail entity.
     * @param updateType Update type.
     * @return True normal, false error.
     */
    private boolean checkTransactionItemDetailAddition(TransactionItemDetail transactionItemDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String updateType) {
        checkerHelper.mapLevel3TransactionItemDetail(transactionItemDetail,
                salesTransactionErrorDetailEntity);
        if (StringUtils.isNotEmpty(transactionItemDetail.getDetailListSalesTransactionType())
                && !checkTransactionType(transactionItemDetail.getDetailListSalesTransactionType(),
                        ERROR_ID_DETAIL_LIST_SALES_TRANSACTION_TYPE,
                        salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST)) {
            return false;
        }
        if (StringUtils.isNotEmpty(transactionItemDetail.getQuantityCode())
                && !checkPaymentSign(transactionItemDetail.getQuantityCode(),
                        ERROR_ID_QUANTITY_CODE, salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST)) {
            return false;
        }
        String itemTaxKind = transactionItemDetail.getItemTaxKind();
        // Check item unit price tax.
        if (!checkTaxKind(transactionItemDetail.getItemUnitPriceTaxIncluded(),
                ERROR_ID_ITEM_UNIT_PRICE_TAX_INCLUDED,
                transactionItemDetail.getItemUnitPriceTaxExcluded(),
                ERROR_ID_ITEM_UNIT_PRICE_TAX_EXCLUDED, itemTaxKind,
                salesTransactionErrorDetailEntity, updateType,
                ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST)) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(transactionItemDetail.getNonItemDetailListByItem())) {
            for (NonItemDetail nonItemDetail : transactionItemDetail.getNonItemDetailListByItem()) {
                checkerHelper.mapLevel4NonItemDetail(nonItemDetail,
                        salesTransactionErrorDetailEntity);
                if (StringUtils.isNotEmpty(nonItemDetail.getQuantityCode())
                        && !checkPaymentSign(nonItemDetail.getQuantityCode(),
                                ERROR_ID_QUANTITY_CODE, salesTransactionErrorDetailEntity,
                                ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST_BY_ITEM)) {
                    return false;
                }
                if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemDiscountDetailList())) {
                    for (NonItemDiscountDetail nonItemDiscountDetail : nonItemDetail
                            .getNonItemDiscountDetailList()) {
                        checkerHelper.mapLevel5NonItemDiscountDetail(nonItemDiscountDetail,
                                salesTransactionErrorDetailEntity);
                        if (StringUtils.isNotEmpty(nonItemDiscountDetail.getNonItemQuantityCode())
                                && !checkPaymentSign(nonItemDiscountDetail.getNonItemQuantityCode(),
                                        ERROR_ID_NON_ITEM_QUANTITY_CODE,
                                        salesTransactionErrorDetailEntity,
                                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_DISCOUNT_DETAIL_LIST)) {
                            return false;
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemTaxDetailList())) {
                    for (NonItemTaxDetail nonItemTaxDetail : nonItemDetail
                            .getNonItemTaxDetailList()) {
                        checkerHelper.mapLevel5NonItemTaxDetail(nonItemTaxDetail,
                                salesTransactionErrorDetailEntity);
                        if (StringUtils.isNotEmpty(nonItemTaxDetail.getNonItemTaxAmountSign())
                                && !checkPaymentSign(nonItemTaxDetail.getNonItemTaxAmountSign(),
                                        ERROR_ID_NON_ITEM_TAX_AMOUNT_SIGN_CODE,
                                        salesTransactionErrorDetailEntity,
                                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_TAX_DETAIL_LIST)) {
                            return false;
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(transactionItemDetail.getItemDiscountList())) {
            for (ItemDiscount itemDiscount : transactionItemDetail.getItemDiscountList()) {
                checkerHelper.mapLevel4ItemDiscount(itemDiscount,
                        salesTransactionErrorDetailEntity);
                if (StringUtils.isNotEmpty(itemDiscount.getItemQuantityCode())
                        && !checkPaymentSign(itemDiscount.getItemQuantityCode(),
                                ERROR_ID_ITEM_QUANTITY_CODE, salesTransactionErrorDetailEntity,
                                ERROR_OCCURRED_LIST_NAME_ITEM_DISCOUNT_LIST)) {
                    return false;
                }
                // Check item discount amount tax.
                if (!checkTaxKind(itemDiscount.getItemDiscountAmtTaxIncluded(),
                        ERROR_ID_ITEM_DISCOUNT_AMT_TAX_INCLUDED,
                        itemDiscount.getItemDiscountAmtTaxExcluded(),
                        ERROR_ID_ITEM_DISCOUNT_AMT_TAX_EXCLUDED, itemTaxKind,
                        salesTransactionErrorDetailEntity, updateType,
                        ERROR_OCCURRED_LIST_NAME_ITEM_DISCOUNT_LIST)) {
                    return false;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(transactionItemDetail.getItemTaxDetailList())) {
            for (ItemTaxDetail itemTaxDetail : transactionItemDetail.getItemTaxDetailList()) {
                checkerHelper.mapLevel4ItemTaxDetail(itemTaxDetail,
                        salesTransactionErrorDetailEntity);
                if (StringUtils.isNotEmpty(itemTaxDetail.getItemTaxAmountSign())
                        && !checkPaymentSign(itemTaxDetail.getItemTaxAmountSign(),
                                ERROR_ID_ITEM_TAX_AMOUNT_SIGN, salesTransactionErrorDetailEntity,
                                ERROR_OCCURRED_LIST_NAME_ITEM_TAX_DETAIL_LIST)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check non item detail addition.
     * 
     * @param nonItemDetail non item detail.
     * @param salesTransactionErrorDetailEntity sales transaction error detail entity.
     * @param updateType Update type.
     * @return True normal, false error.
     */
    private boolean checkNonItemDetailAddition(NonItemDetail nonItemDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String updateType) {
        checkerHelper.mapLevel3NonItemDetail(nonItemDetail, salesTransactionErrorDetailEntity);
        if (StringUtils.isNotEmpty(nonItemDetail.getNonMdDetailListSalesTransactionType())
                && !checkTransactionType(nonItemDetail.getNonMdDetailListSalesTransactionType(),
                        ERROR_ID_NON_MD_DETAIL_LIST_SALES_TRANSACTION_TYPE,
                        salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST)) {
            return false;
        }
        if (StringUtils.isNotEmpty(nonItemDetail.getQuantityCode())
                && !checkPaymentSign(nonItemDetail.getQuantityCode(), ERROR_ID_QUANTITY_CODE,
                        salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST)) {
            return false;
        }
        String nonItemTaxKind = nonItemDetail.getNonItemTaxKind();
        // Check non item unit price tax.
        if (!checkTaxKind(nonItemDetail.getNonItemUnitPriceTaxIncluded(),
                ERROR_ID_NON_ITEM_UNIT_PRICE_TAX_INCLUDED,
                nonItemDetail.getNonItemUnitPriceTaxExcluded(),
                ERROR_ID_NON_ITEM_UNIT_PRICE_TAX_EXCLUDED, nonItemTaxKind,
                salesTransactionErrorDetailEntity, updateType,
                ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST)) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemDiscountDetailList())) {
            for (NonItemDiscountDetail nonItemDiscountDetail : nonItemDetail
                    .getNonItemDiscountDetailList()) {
                checkerHelper.mapLevel4NonItemDiscountDetail(nonItemDiscountDetail,
                        salesTransactionErrorDetailEntity);
                if (StringUtils.isNotEmpty(nonItemDiscountDetail.getNonItemQuantityCode())
                        && !checkPaymentSign(nonItemDiscountDetail.getNonItemQuantityCode(),
                                ERROR_ID_NON_ITEM_QUANTITY_CODE, salesTransactionErrorDetailEntity,
                                ERROR_OCCURRED_LIST_NAME_NON_ITEM_DISCOUNT_DETAIL_LIST)) {
                    return false;
                }
                // Check non item discount amount tax.
                if (!checkTaxKind(nonItemDiscountDetail.getNonItemDiscountAmtTaxIncluded(),
                        ERROR_ID_NON_ITEM_DISCOUNT_AMT_TAX_INCLUDED,
                        nonItemDiscountDetail.getNonItemDiscountAmtTaxExcluded(),
                        ERROR_ID_NON_ITEM_DISCOUNT_AMT_TAX_EXCLUDED, nonItemTaxKind,
                        salesTransactionErrorDetailEntity, updateType,
                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_DISCOUNT_DETAIL_LIST)) {
                    return false;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemTaxDetailList())) {
            for (NonItemTaxDetail nonItemTaxDetail : nonItemDetail.getNonItemTaxDetailList()) {
                checkerHelper.mapLevel4NonItemTaxDetail(nonItemTaxDetail,
                        salesTransactionErrorDetailEntity);
                if (StringUtils.isNotEmpty(nonItemTaxDetail.getNonItemTaxAmountSign())
                        && !checkPaymentSign(nonItemTaxDetail.getNonItemTaxAmountSign(),
                                ERROR_ID_NON_ITEM_TAX_AMOUNT_SIGN_CODE,
                                salesTransactionErrorDetailEntity,
                                ERROR_OCCURRED_LIST_NAME_NON_ITEM_TAX_DETAIL_LIST)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check tax kind.
     * 
     * @param taxIncluded tax included.
     * @param taxIncludedItemId tax included item id.
     * @param taxExcluded tax excluded.
     * @param taxExcludedItemId tax excluded item id.
     * @param itemTaxKind item tax kind.
     * @param salesTransactionErrorDetailEntity sales transaction error detail entity.
     * @param updateType Update type.
     * @param errorOccurredListName Error occurred list name.
     * @return True normal, false error.
     */
    private boolean checkTaxKind(Price taxIncluded, String taxIncludedItemId, Price taxExcluded,
            String taxExcludedItemId, String itemTaxKind,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String updateType,
            String errorOccurredListName) {
        if (UpdateType.CORRECTION.is(updateType) || UpdateType.INSERT.is(updateType)) {
            if (TaxSystemType.compare(TaxSystemType.IN, itemTaxKind)) {
                if (taxIncluded == null) {
                    insertSalesTransactionErrorValidation(salesTransactionErrorDetailEntity,
                            taxIncludedItemId, null, errorOccurredListName);
                    return false;
                }
            } else if (StringUtils.isEmpty(itemTaxKind)
                    || TaxSystemType.compare(TaxSystemType.OUT, itemTaxKind)) {
                if (taxExcluded == null) {
                    insertSalesTransactionErrorValidation(salesTransactionErrorDetailEntity,
                            taxExcludedItemId, null, errorOccurredListName);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Insert sales transaction validation error.
     * 
     * @param salesTransactionErrorDetailEntity sales transaction error detail entity.
     * @param errorItemId error item id.
     * @param errorItemValue error item value.
     * @param errorOccurredListName Error occurred list name.
     */
    private void insertSalesTransactionErrorValidation(
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String errorItemId,
            String errorItemValue, String errorOccurredListName) {
        salesTransactionErrorDetailEntity.setErrorType(ErrorType.VALIDATION_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(errorItemId);
        salesTransactionErrorDetailEntity.setErrorItemValue1(errorItemValue);
        salesTransactionErrorDetailEntity.setErrorOccurredList(errorOccurredListName);
        removeEntityErrorValue(salesTransactionErrorDetailEntity);
        checkerHelper.insertSalesTransactionError(salesTransactionErrorDetailEntity);
    }

    /**
     * Check Transaction tender validation.
     * 
     * @param salesTransactionTender SalesTransactionTender.
     * @param salesTransactionErrorDetailEntity SalesTransactionErrorDetail entity.
     * @param validationGroup Validation group.
     * @return True normal, false error.
     */
    private boolean checkTransactionTenderValidation(SalesTransactionTender salesTransactionTender,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String validationGroup) {
        checkerHelper.mapLevel3SalesTransactionTender(salesTransactionTender,
                salesTransactionErrorDetailEntity);

        if (!validateLevel(salesTransactionTender, validationGroup,
                salesTransactionErrorDetailEntity,
                ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TENDER_LIST)) {
            return false;
        }

        if (salesTransactionTender.getTenderInfoList() != null) {
            // same as SalesTransactionTender
            checkerHelper.mapLevel3SalesTransactionTender(salesTransactionTender,
                    salesTransactionErrorDetailEntity);

            if (!validateLevel(salesTransactionTender.getTenderInfoList(), validationGroup,
                    salesTransactionErrorDetailEntity,
                    ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TENDER_LIST)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check transaction item detail validation.
     * 
     * @param transactionItemDetail TransactionItemDetail entity.
     * @param salesTransactionErrorDetailEntity SalesTransactionErrorDetail entity.
     * @param validationGroup Validation group.
     * @return True normal, false error.
     */
    private boolean checkTransactionItemDetailValidation(
            TransactionItemDetail transactionItemDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String validationGroup) {
        checkerHelper.mapLevel3TransactionItemDetail(transactionItemDetail,
                salesTransactionErrorDetailEntity);
        if (!validateLevel(transactionItemDetail, validationGroup,
                salesTransactionErrorDetailEntity,
                ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST)) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(transactionItemDetail.getNonItemDetailListByItem())) {
            for (NonItemDetail nonItemDetail : transactionItemDetail.getNonItemDetailListByItem()) {
                if (!checkItemDetailNonItemDetailValidation(nonItemDetail,
                        salesTransactionErrorDetailEntity, validationGroup)) {
                    return false;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(transactionItemDetail.getItemDiscountList())) {
            for (ItemDiscount itemDiscount : transactionItemDetail.getItemDiscountList()) {
                checkerHelper.mapLevel4ItemDiscount(itemDiscount,
                        salesTransactionErrorDetailEntity);
                if (!validateLevel(itemDiscount, validationGroup, salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_ITEM_DISCOUNT_LIST)) {
                    return false;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(transactionItemDetail.getItemTaxDetailList())) {
            for (ItemTaxDetail itemTaxDetail : transactionItemDetail.getItemTaxDetailList()) {
                checkerHelper.mapLevel4ItemTaxDetail(itemTaxDetail,
                        salesTransactionErrorDetailEntity);
                if (!validateLevel(itemTaxDetail, validationGroup,
                        salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_ITEM_TAX_DETAIL_LIST)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check item detail non item detail validation.
     * 
     * @param nonItemDetail NonItemDetail entity.
     * @param salesTransactionErrorDetailEntity SalesTransactionErrorDetail entity.
     * @param validationGroup Validation group.
     * @return True normal, false error.
     */
    private boolean checkItemDetailNonItemDetailValidation(NonItemDetail nonItemDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String validationGroup) {
        checkerHelper.mapLevel4NonItemDetail(nonItemDetail, salesTransactionErrorDetailEntity);
        if (!validateLevel(nonItemDetail, validationGroup, salesTransactionErrorDetailEntity,
                ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST_BY_ITEM)) {
            return false;
        }
        NonItemInfo nonItemInfo = nonItemDetail.getNonItemInfo();
        if (nonItemInfo != null) {
            checkerHelper.mapLevel5NonItemInfo(nonItemInfo, salesTransactionErrorDetailEntity);
            if (!validateLevel(nonItemInfo, validationGroup, salesTransactionErrorDetailEntity,
                    ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST_BY_ITEM)) {
                return false;
            }
        }
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemDiscountDetailList())) {
            for (NonItemDiscountDetail nonItemDiscountDetail : nonItemDetail
                    .getNonItemDiscountDetailList()) {
                checkerHelper.mapLevel5NonItemDiscountDetail(nonItemDiscountDetail,
                        salesTransactionErrorDetailEntity);

                if (!validateLevel(nonItemDiscountDetail, validationGroup,
                        salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_DISCOUNT_DETAIL_LIST)) {
                    return false;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemTaxDetailList())) {
            for (NonItemTaxDetail nonItemTaxDetail : nonItemDetail.getNonItemTaxDetailList()) {
                checkerHelper.mapLevel5NonItemTaxDetail(nonItemTaxDetail,
                        salesTransactionErrorDetailEntity);
                if (!validateLevel(nonItemTaxDetail, validationGroup,
                        salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_TAX_DETAIL_LIST)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check transaction's non item detail validation.
     * 
     * @param nonItemDetail NonItemDetail entity.
     * @param salesTransactionErrorDetailEntity SalesTransactionErrorDetail entity.
     * @param validationGroup Validation group.
     * @return True normal, false error.
     */
    private boolean checkTransactionNonItemDetailValidation(NonItemDetail nonItemDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String validationGroup) {
        checkerHelper.mapLevel3NonItemDetail(nonItemDetail, salesTransactionErrorDetailEntity);
        if (!validateLevel(nonItemDetail, validationGroup, salesTransactionErrorDetailEntity,
                ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST)) {
            return false;
        }
        if (nonItemDetail.getNonItemInfo() != null) {
            NonItemInfo nonItemInfo = nonItemDetail.getNonItemInfo();
            checkerHelper.mapLevel4NonItemInfo(nonItemInfo, salesTransactionErrorDetailEntity);
            if (!validateLevel(nonItemInfo, validationGroup, salesTransactionErrorDetailEntity,
                    ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST)) {
                return false;
            }
        }
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemDiscountDetailList())) {
            for (NonItemDiscountDetail nonItemDiscountDetail : nonItemDetail
                    .getNonItemDiscountDetailList()) {
                checkerHelper.mapLevel4NonItemDiscountDetail(nonItemDiscountDetail,
                        salesTransactionErrorDetailEntity);
                if (!validateLevel(nonItemDiscountDetail, validationGroup,
                        salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_DISCOUNT_DETAIL_LIST)) {
                    return false;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemTaxDetailList())) {
            for (NonItemTaxDetail nonItemTaxDetail : nonItemDetail.getNonItemTaxDetailList()) {
                checkerHelper.mapLevel4NonItemTaxDetail(nonItemTaxDetail,
                        salesTransactionErrorDetailEntity);
                if (!validateLevel(nonItemTaxDetail, validationGroup,
                        salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_TAX_DETAIL_LIST)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Validate level.
     * 
     * @param toValid Object to be valid.
     * @param updateType Update type.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param errorOccurredListName Error occurred list name.
     * @return True normal, false error.
     */
    private boolean validateLevel(Object toValid, String updateType,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            String errorOccurredListName) {
        Set<ConstraintViolation<Object>> checkResult = new HashSet<>();
        if (UpdateType.INSERT.is(updateType)) {
            checkResult = validator.validate(toValid, Insert.class);
        } else if (UpdateType.CORRECTION.is(updateType)) {
            checkResult = validator.validate(toValid, Correction.class);
        } else if (UpdateType.UPDATE.is(updateType)) {
            checkResult = validator.validate(toValid, Update.class);
        } else if (UpdateType.DELETE.is(updateType)) {
            checkResult = validator.validate(toValid, Delete.class);
        } else if (VALIDATION_GROUP_POS_INSERT.equals(updateType)) {
            checkResult = validator.validate(toValid, PosInsert.class);
        } else {
            checkResult = validator.validate(toValid);
        }
        // Check validation group.
        if (!validateCheckResult(checkResult, errorOccurredListName,
                salesTransactionErrorDetailEntity)) {
            return false;
        }

        // Check validation without group.
        checkResult = validator.validate(toValid);
        if (!validateCheckResult(checkResult, errorOccurredListName,
                salesTransactionErrorDetailEntity)) {
            return false;
        }

        return true;
    }

    /**
     * Validate check result.
     *
     * @param checkResult check result.
     * @param errorOccurredListName Error occurred list name.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    public boolean validateCheckResult(Set<ConstraintViolation<Object>> checkResult,
            String errorOccurredListName,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        if (CollectionUtils.isNotEmpty(checkResult)) {
            if (checkResult.stream().findFirst().isPresent()) {
                ConstraintViolation<Object> errorObject = checkResult.stream().findFirst().get();
                insertSalesTransactionErrorValidation(salesTransactionErrorDetailEntity,
                        getLeafNode(errorObject),
                        errorObject.getInvalidValue() != null
                                ? errorObject.getInvalidValue().toString()
                                : null,
                        errorOccurredListName);
            }
            return false;
        }
        return true;
    }

    /**
     * Remove entity error value.
     * 
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    private void removeEntityErrorValue(
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        if (ERROR_ID_SYSTEM_BRAND_CODE
                .equals(salesTransactionErrorDetailEntity.getErrorItemId1())) {
            salesTransactionErrorDetailEntity.setSystemBrandCode(null);
        } else if (ERROR_ID_INTEGRATED_ORDER_ID
                .equals(salesTransactionErrorDetailEntity.getErrorItemId1())) {
            salesTransactionErrorDetailEntity.setIntegratedOrderId(null);
        } else if (ERROR_ID_SALES_TRANSACTION_ID
                .equals(salesTransactionErrorDetailEntity.getErrorItemId1())) {
            salesTransactionErrorDetailEntity.setSalesTransactionId(null);
        } else if (ERROR_ID_STORE_CODE
                .equals(salesTransactionErrorDetailEntity.getErrorItemId1())) {
            salesTransactionErrorDetailEntity.setStoreCode(null);
        } else if (ERROR_ID_SYSTEM_BUSINESS_CODE
                .equals(salesTransactionErrorDetailEntity.getErrorItemId1())) {
            salesTransactionErrorDetailEntity.setSystemBusinessCode(null);
        } else if (ERROR_ID_SYSTEM_COUNTRY_CODE
                .equals(salesTransactionErrorDetailEntity.getErrorItemId1())) {
            salesTransactionErrorDetailEntity.setSystemCountryCode(null);
        }
    }

    /**
     * Get check result's leaf node.
     * 
     * @param firstCheckResult First check result.
     * @return the leaf node of the error's property name.
     */
    private String getLeafNode(ConstraintViolation<Object> firstCheckResult) {
        Iterator<Node> resultIterator = firstCheckResult.getPropertyPath().iterator();
        String leafName = null;
        while (resultIterator.hasNext()) {
            leafName = resultIterator.next().toString();
        }

        JavaType type = objectMapper.getTypeFactory()
                .constructType(firstCheckResult.getLeafBean().getClass());
        BeanDescription beanDescription = objectMapper.getSerializationConfig().introspect(type);
        final String resultFinal = leafName;
        Optional<BeanPropertyDefinition> propertyOptional = beanDescription.findProperties()
                .stream()
                .filter(property -> property.getInternalName().equals(resultFinal))
                .findFirst();
        return propertyOptional.isPresent() ? propertyOptional.get().getName() : leafName;
    }
}
