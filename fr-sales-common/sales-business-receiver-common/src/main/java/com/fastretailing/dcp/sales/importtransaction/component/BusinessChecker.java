/**
 * @(#)BusinessChecker.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.common.constants.DBItem;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.common.type.GeneralPurposeType;
import com.fastretailing.dcp.sales.common.type.IntegrityCheckType;
import com.fastretailing.dcp.sales.common.type.RtlogType;
import com.fastretailing.dcp.sales.common.type.SalesLinkageType;
import com.fastretailing.dcp.sales.common.type.TransactionType;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.BusinessCountryStateSettingMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.BusinessCountryStateSettingMasterCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.PayOffDataCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.StoreControlMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.StoreGeneralPurposeMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.StoreGeneralPurposeMasterCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.StoreMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.TranslationTenderMasterCondition;
import com.fastretailing.dcp.sales.importtransaction.repository.BusinessCountryStateSettingMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.PayOffDataMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.StoreControlMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.StoreGeneralPurposeMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.StoreMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.TranslationTenderMasterMapper;
import com.fastretailing.dcp.storecommon.dto.Price;
import com.fastretailing.dcp.storecommon.util.DateUtility;
import com.fastretailing.dcp.storecommon.util.DateUtility.DateFormat;
import com.fastretailing.dcp.storecommon.util.DateUtility.DateTimeFormat;
import com.fastretailing.dcp.storecommon.util.PriceUtils;

/**
 * The class is used to check business data.
 *
 */
@Component
public class BusinessChecker {

    /** Item id code value. */
    private static final String ITEM_ID_CODE_VALUE = "code_value";

    /** Item id balance check total amount. */
    private static final String ITEM_ID_BALANCE_CHECK_TOTAL_AMOUNT = "total_amount";

    /** Item id balance check total discount amount. */
    private static final String ITEM_ID_BALANCE_CHECK_TOTAL_DISCOUNT_AMOUNT =
            "total_discount_amount";

    /** Item id balance check total payment amount. */
    private static final String ITEM_ID_BALANCE_CHECK_TOTAL_PAYMENT_AMOUNT = "total_payment_amount";

    /** Item id consistency check business date. */
    private static final String ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE = "business_date";

    /** Item id consistency check tender code. */
    private static final String ITEM_ID_CONSISTENCY_CHECK_TENDER_CODE = "tender_id";

    /** Item id consistency check business end of date. */
    private static final String ITEM_ID_CONSISTENCY_CHECK_BUSINESS_END_OF_DATE =
            "business_end_of_date";

    /** Item id consistency check after store close date. */
    private static final String ITEM_ID_CONSISTENCY_CHECK_AFTER_STORE_CLOSE_DATE = "close_date";

    /** Item id consistency check before store open date. */
    private static final String ITEM_ID_CONSISTENCY_CHECK_BEFORE_STORE_OPEN_DATE = "open_date";

    /** Code l1 open days. */
    private static final String CODE_L1_OPEN_DAYS = "BUSINESSDATECHECK";

    /** Code l2 open days. */
    private static final String CODE_L2_OPEN_DAYS = "BEFORESTOREOPENING";

    /** Code l1 close days. */
    private static final String CODE_L1_CLOSE_DAYS = "BUSINESSDATECHECK";

    /** Code l2 close days. */
    private static final String CODE_L2_CLOSE_DAYS = "AFTERSTORECLOSING";

    /** Code l1 base date time information. */
    private static final String CODE_L1_BASE_DATE_TIME_INFORMATION = "BUSINESSDATECHECK";

    /** Code l2 base date time information. */
    private static final String CODE_L2_BASE_DATE_TIME_INFORMATION = "DATEEXCEEDING";

    /** Code l1 business end of days. */
    private static final String CODE_L1_BUSINESS_END_OF_DAYS = "BUSINESSDATECHECK";

    /** Code l2 business end of days. */
    private static final String CODE_L2_BUSINESS_END_OF_DAYS = "CLOSEDBUSINESSDATE";

    /** Code l1 tax type. */
    private static final String CODE_L1_TAX_TYPE = "TAXTYPE";

    /** Code l2 tax type. */
    private static final String CODE_L2_TAX_TYPE = "TAXTYPE";

    /** Code l3 tax type. */
    private static final String CODE_L3_TAX_TYPE = "RTLOG";

    /** Business closing day check flag on. */
    private static final String BUSINESS_CLOSING_DAY_CHECK_FLAG_ON = "0";

    /** Item id data creation business date. */
    private static final String ITEM_ID_DATA_CREATION_BUSINESS_DATE =
            "data_creation_business_date ";

    /** Item id order status update date. */
    private static final String ITEM_ID_ORDER_STATUS_UPDATE_DATE = "order_status_update_date";

    /** Item id order confirmation business date. */
    private static final String ITEM_ID_ORDER_CONFIRMATION_BUSINESS_DATE =
            "order_confirmation_business_date";

    /** Item id store code. */
    private static final String ITEM_ID_STORE_CODE = "store_code";

    /** Item id table id. */
    private static final String ITEM_ID_TABLE_ID = "table_name";

    /** Payment sign negative. */
    private static final String PAYMENT_SIGN_NEGATIVE = "N";

    /** Item id balance check tax amount. */
    private static final String ITEM_ID_BALANCE_CHECK_TAX_AMOUNT = "tax_amount";

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

    /** Checker helper. */
    @Autowired
    private CheckerHelper checkerHelper;

    /**
     * Component for operating DB operations on the store control table.
     */
    @Autowired
    private StoreControlMasterMapper storeControlEntityMapper;

    /**
     * Component for operating DB operations on the business country state setting table.
     */
    @Autowired
    private BusinessCountryStateSettingMasterMapper businessCountryStateSettingEntityMapper;

    /**
     * Component for operating DB operations on the store table.
     */
    @Autowired
    private StoreMasterMapper storeEntityMapper;

    /**
     * Component for operating DB operations on the pay off data table.
     */
    @Autowired
    private PayOffDataMapper payOffDateEntityMapper;

    /**
     * Component for operating DB operations on the store general purpose table.
     */
    @Autowired
    private StoreGeneralPurposeMasterMapper mstoreGeneralPurposeEntityMapper;

    /**
     * Component for operating DB operations on the translation tender master table.
     */
    @Autowired
    private TranslationTenderMasterMapper translationTenderMasterMapper;


    /**
     * Check data relation. Have no data in item detail and non item detail when transaction type is
     * sale or return.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    public boolean checkDataRelation(TransactionImportData transactionImportData,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        String storeCode = transactionImportData.getStoreCode();
        if (CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())) {
            for (Transaction transaction : transactionImportData.getTransactionList()) {
                if (TransactionType.compare(TransactionType.SALE, transaction.getTransactionType())
                        || TransactionType.compare(TransactionType.RETURN,
                                transaction.getTransactionType())) {
                    checkerHelper.mapLevel2Transaction(transaction,
                            salesTransactionErrorDetailEntity);
                    if (CollectionUtils.isEmpty(transaction.getTransactionItemDetailList())
                            && CollectionUtils.isEmpty(transaction.getNonItemDetailList())) {
                        salesTransactionErrorDetailEntity
                                .setErrorType(ErrorType.RELATION_ERROR.getErrorType());
                        insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                                ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, storeCode, transaction);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Check about item's date.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    public boolean checkDate(TransactionImportData transactionImportData,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        String storeCode = transactionImportData.getStoreCode();
        Transaction firstTransaction =
                CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())
                        ? transactionImportData.getTransactionList().get(0)
                        : new Transaction();

        if (!checkBusinessDate(transactionImportData, salesTransactionErrorDetailEntity,
                firstTransaction, storeCode)) {
            return false;
        }

        if (!checkStoreOpenDate(transactionImportData, salesTransactionErrorDetailEntity,
                firstTransaction, storeCode)) {
            return false;
        }

        if (!checkStoreCloseDate(transactionImportData, salesTransactionErrorDetailEntity,
                firstTransaction, storeCode)) {
            return false;
        }

        return true;
    }

    /**
     * Check about business date.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param storeCode Store code.
     * @return Result of check.
     */
    private boolean checkBusinessDate(TransactionImportData transactionImportData,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            Transaction firstTransaction, String storeCode) {
        StoreControlMaster storeControlEntity =
                storeControlEntityMapper.selectByPrimaryKey(storeCode);

        // Output error table and return error when have no data.
        if (!checkStoreControlEntity(storeControlEntity, salesTransactionErrorDetailEntity,
                storeCode, null, firstTransaction)) {
            return false;
        }

        String baseTimeInformation = getCodeValueFromSettingTable(storeCode,
                CODE_L1_BASE_DATE_TIME_INFORMATION, CODE_L2_BASE_DATE_TIME_INFORMATION);
        if (StringUtils.isEmpty(baseTimeInformation)) {
            return true;
        }
        String zoneId = getZoneId(storeCode);

        if (transactionImportData.getOrderConfirmationDateTime() != null
                && StringUtils.isNotEmpty(transactionImportData.getOrderConfirmationBusinessDate())
                && !checkBusinessDateUpdateTime(
                        transactionImportData.getOrderConfirmationDateTime(), zoneId,
                        transactionImportData.getOrderConfirmationBusinessDate(),
                        baseTimeInformation, salesTransactionErrorDetailEntity, storeCode, null,
                        firstTransaction)) {
            return false;
        }

        // Loop for transaction list
        if (CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())) {
            for (Transaction transaction : transactionImportData.getTransactionList()) {
                if (!checkTransactionBusinessDate(transaction, salesTransactionErrorDetailEntity)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check transaction business date.
     * 
     * @param transaction Transaction.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return Check result.
     */
    private boolean checkTransactionBusinessDate(Transaction transaction,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        String storeCode = transaction.getStoreCode();
        StoreControlMaster storeControlEntity =
                storeControlEntityMapper.selectByPrimaryKey(storeCode);
        checkerHelper.mapLevel2Transaction(transaction, salesTransactionErrorDetailEntity);

        // Output error table and return error when have no data.
        if (!checkStoreControlEntity(storeControlEntity, salesTransactionErrorDetailEntity,
                storeCode, ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, transaction)) {
            return false;
        }

        String baseTimeInformation = getCodeValueFromSettingTable(storeCode,
                CODE_L1_BASE_DATE_TIME_INFORMATION, CODE_L2_BASE_DATE_TIME_INFORMATION);
        if (StringUtils.isEmpty(baseTimeInformation)) {
            return true;
        }

        String businessDate = storeControlEntity.getBusinessDate();
        if (!checkFuture(transaction.getDataCreationBusinessDate(), businessDate,
                ITEM_ID_DATA_CREATION_BUSINESS_DATE, salesTransactionErrorDetailEntity, storeCode,
                ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, transaction)) {
            return false;
        }
        String zoneId = getZoneId(storeCode);
        if (transaction.getDataCreationDateTime() != null
                && StringUtils.isNotEmpty(transaction.getDataCreationBusinessDate())
                && !checkBusinessDateUpdateTime(transaction.getDataCreationDateTime(), zoneId,
                        transaction.getDataCreationBusinessDate(), baseTimeInformation,
                        salesTransactionErrorDetailEntity, storeCode,
                        ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, transaction)) {
            return false;
        }
        if (!checkFuture(transaction.getOrderStatusUpdateDate(), businessDate,
                ITEM_ID_ORDER_STATUS_UPDATE_DATE, salesTransactionErrorDetailEntity, storeCode,
                ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, transaction)) {
            return false;
        }
        if (transaction.getOrderStatusLastUpdateDateTime() != null
                && StringUtils.isNotEmpty(transaction.getOrderStatusUpdateDate())
                && !checkBusinessDateUpdateTime(transaction.getOrderStatusLastUpdateDateTime(),
                        zoneId, transaction.getOrderStatusUpdateDate(), baseTimeInformation,
                        salesTransactionErrorDetailEntity, storeCode,
                        ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, transaction)) {
            return false;
        }

        String businessEndOfDate = storeControlEntity.getBusinessEndOfDate();
        String businessEndOfDateCodeValue = getCodeValueFromSettingTable(storeCode,
                CODE_L1_BUSINESS_END_OF_DAYS, CODE_L2_BUSINESS_END_OF_DAYS);
        if (StringUtils.isEmpty(businessEndOfDateCodeValue)) {
            return true;
        }
        boolean isStore = checkerHelper.isStore(storeCode);
        if (isStore
                && !checkProcessCloseDate(transaction.getOrderStatusUpdateDate(), businessEndOfDate,
                        storeCode, businessEndOfDateCodeValue, salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, transaction)) {
            return false;
        }

        // Loop for item detail.
        if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
            for (TransactionItemDetail transactionItemDetail : transaction
                    .getTransactionItemDetailList()) {
                if (!checkTransactionItemDetailBusinessDate(transactionItemDetail,
                        salesTransactionErrorDetailEntity, businessDate, businessEndOfDate, zoneId,
                        storeCode, baseTimeInformation, businessEndOfDateCodeValue, isStore,
                        transaction)) {
                    return false;
                }
            }
        }

        if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
            for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                if (!checkLevel3NonItemDetailBusinessDate(nonItemDetail,
                        salesTransactionErrorDetailEntity, businessDate, zoneId, storeCode,
                        baseTimeInformation, businessEndOfDate, businessEndOfDateCodeValue, isStore,
                        transaction)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check store control entity.
     * 
     * @param storeControlEntity Store control entity.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param storeCode Store code.
     * @return False if store control entity or business date or business end of date is empty.
     */
    private boolean checkStoreControlEntity(StoreControlMaster storeControlEntity,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String storeCode,
            String errorOccurredListName, Transaction transaction) {
        if (storeControlEntity == null || StringUtils.isEmpty(storeControlEntity.getBusinessDate())
                || StringUtils.isEmpty(storeControlEntity.getBusinessEndOfDate())) {
            salesTransactionErrorDetailEntity.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
            salesTransactionErrorDetailEntity.setErrorItemId1(ITEM_ID_TABLE_ID);
            salesTransactionErrorDetailEntity.setErrorItemValue1(DBItem.TABLE_NAME_M_STORE_CONTROL);
            salesTransactionErrorDetailEntity.setErrorItemId2(ITEM_ID_STORE_CODE);
            salesTransactionErrorDetailEntity.setErrorItemValue2(storeCode);
            insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    errorOccurredListName, storeCode, transaction);
            return false;
        }
        return true;
    }

    /**
     * Check level3 non item detail business date.
     * 
     * @param nonItemDetail Non item detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param businessDate Business date.
     * @param zoneId Zone id.
     * @param storeCode Store code.
     * @param baseTimeInformation Base time information.
     * @param businessEndOfDate Business end of date.
     * @param businessEndOfDateCodeValue Business end of date code value.
     * @param isStore Is store.
     * @return Check result.
     */
    private boolean checkLevel3NonItemDetailBusinessDate(NonItemDetail nonItemDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String businessDate,
            String zoneId, String storeCode, String baseTimeInformation, String businessEndOfDate,
            String businessEndOfDateCodeValue, boolean isStore, Transaction transaction) {
        checkerHelper.mapLevel3NonItemDetail(nonItemDetail, salesTransactionErrorDetailEntity);
        if (!checkFuture(nonItemDetail.getOrderStatusUpdateDate(), businessDate,
                ITEM_ID_ORDER_STATUS_UPDATE_DATE, salesTransactionErrorDetailEntity, storeCode,
                ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST, transaction)) {
            return false;
        }
        if (nonItemDetail.getOrderStatusLastUpdateDateTime() != null
                && StringUtils.isNotEmpty(nonItemDetail.getOrderStatusUpdateDate())
                && !checkBusinessDateUpdateTime(nonItemDetail.getOrderStatusLastUpdateDateTime(),
                        zoneId, nonItemDetail.getOrderStatusUpdateDate(), baseTimeInformation,
                        salesTransactionErrorDetailEntity, storeCode,
                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST, transaction)) {
            return false;
        }
        if (isStore && !checkProcessCloseDate(nonItemDetail.getOrderStatusUpdateDate(),
                businessEndOfDate, storeCode, businessEndOfDateCodeValue,
                salesTransactionErrorDetailEntity, ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST,
                transaction)) {
            return false;
        }
        return true;
    }

    /**
     * Check transaction item detail business date.
     * 
     * @param transactionItemDetail Transaction item detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param businessDate Business date.
     * @param businessEndOfDate Business end of date.
     * @param zoneId Zone id.
     * @param storeCode Store code.
     * @param baseTimeInformation Base time information.
     * @param businessEndOfDateCodeValue Business end of date code value.
     * @param isStore Is store.
     * @return Check result.
     */
    private boolean checkTransactionItemDetailBusinessDate(
            TransactionItemDetail transactionItemDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String businessDate,
            String businessEndOfDate, String zoneId, String storeCode, String baseTimeInformation,
            String businessEndOfDateCodeValue, boolean isStore, Transaction transaction) {
        checkerHelper.mapLevel3TransactionItemDetail(transactionItemDetail,
                salesTransactionErrorDetailEntity);
        if (!checkFuture(transactionItemDetail.getOrderStatusUpdateDate(), businessDate,
                ITEM_ID_ORDER_STATUS_UPDATE_DATE, salesTransactionErrorDetailEntity, storeCode,
                ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST, transaction)) {
            return false;
        }
        if (transactionItemDetail.getOrderStatusLastUpdateDateTime() != null
                && StringUtils.isNotEmpty(transactionItemDetail.getOrderStatusUpdateDate())
                && !checkBusinessDateUpdateTime(
                        transactionItemDetail.getOrderStatusLastUpdateDateTime(), zoneId,
                        transactionItemDetail.getOrderStatusUpdateDate(), baseTimeInformation,
                        salesTransactionErrorDetailEntity, storeCode,
                        ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST, transaction)) {
            return false;
        }
        if (isStore && !checkProcessCloseDate(transactionItemDetail.getOrderStatusUpdateDate(),
                businessEndOfDate, storeCode, businessEndOfDateCodeValue,
                salesTransactionErrorDetailEntity,
                ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST, transaction)) {
            return false;
        }

        // Loop for non item detail list (in item detail list).
        if (CollectionUtils.isNotEmpty(transactionItemDetail.getNonItemDetailListByItem())) {
            for (NonItemDetail nonItemDetail : transactionItemDetail.getNonItemDetailListByItem()) {
                if (!checkLevel4NonItemDetailBusinessDate(nonItemDetail,
                        salesTransactionErrorDetailEntity, businessDate, zoneId, storeCode,
                        baseTimeInformation, businessEndOfDate, businessEndOfDateCodeValue, isStore,
                        transaction)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check level4 non item detail business date.
     * 
     * @param nonItemDetail Non item detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param businessDate Business date.
     * @param zoneId Zone id.
     * @param storeCode Store code.
     * @param baseTimeInformation Base time information.
     * @param businessEndOfDate Business end of date.
     * @param businessEndOfDateCodeValue Business end of date code value.
     * @param isStore Is store.
     * @return Check result.
     */
    private boolean checkLevel4NonItemDetailBusinessDate(NonItemDetail nonItemDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String businessDate,
            String zoneId, String storeCode, String baseTimeInformation, String businessEndOfDate,
            String businessEndOfDateCodeValue, boolean isStore, Transaction transaction) {
        checkerHelper.mapLevel4NonItemDetail(nonItemDetail, salesTransactionErrorDetailEntity);

        if (!checkFuture(nonItemDetail.getOrderStatusUpdateDate(), businessDate,
                ITEM_ID_ORDER_STATUS_UPDATE_DATE, salesTransactionErrorDetailEntity, storeCode,
                ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST_BY_ITEM, transaction)) {
            return false;
        }
        if (nonItemDetail.getOrderStatusLastUpdateDateTime() != null
                && StringUtils.isNotEmpty(nonItemDetail.getOrderStatusUpdateDate())
                && !checkBusinessDateUpdateTime(nonItemDetail.getOrderStatusLastUpdateDateTime(),
                        zoneId, nonItemDetail.getOrderStatusUpdateDate(), baseTimeInformation,
                        salesTransactionErrorDetailEntity, storeCode,
                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST_BY_ITEM, transaction)) {
            return false;
        }
        if (isStore && !checkProcessCloseDate(nonItemDetail.getOrderStatusUpdateDate(),
                businessEndOfDate, storeCode, businessEndOfDateCodeValue,
                salesTransactionErrorDetailEntity,
                ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST_BY_ITEM, transaction)) {
            return false;
        }
        return true;
    }

    /**
     * Get code value from business country state setting table.
     * 
     * @param storeCode Store code.
     * @param codeL1 Code level 1.
     * @param codeL2 Code level 2.
     * @return Code value.
     */
    private String getCodeValueFromSettingTable(String storeCode, String codeL1, String codeL2) {
        BusinessCountryStateSettingMasterCondition businessCountryStateSettingEntityCondition =
                new BusinessCountryStateSettingMasterCondition();
        businessCountryStateSettingEntityCondition.createCriteria()
                .andCodeL1EqualTo(codeL1)
                .andCodeL2EqualTo(codeL2)
                .andStoreCodeEqualTo(storeCode);
        List<BusinessCountryStateSettingMaster> result = businessCountryStateSettingEntityMapper
                .selectByCondition(businessCountryStateSettingEntityCondition);

        return result.stream()
                .findFirst()
                .orElse(new BusinessCountryStateSettingMaster())
                .getCodeValue();
    }

    /**
     * Get zone id by store code.
     * 
     * @param storeCode Store code.
     * @return Zone id from store general purpose table.
     */
    private String getZoneId(String storeCode) {
        StoreGeneralPurposeMasterCondition example = new StoreGeneralPurposeMasterCondition();
        example.createCriteria().andStoreCodeEqualTo(storeCode).andGeneralPurposeTypeEqualTo(
                GeneralPurposeType.TIME_ZONE.getGeneralPurposeType());
        return mstoreGeneralPurposeEntityMapper.selectByCondition(example)
                .stream()
                .findFirst()
                .orElse(new StoreGeneralPurposeMaster())
                .getCode();
    }

    /**
     * Convert order time to local time of store.
     * 
     * @param offsetDateTime Off set date time.
     * @param zoneId Store zone id.
     * @return Order time after convert.
     */
    private String convertToLocalTime(OffsetDateTime offsetDateTime, String zoneId) {
        return DateUtility.formatDateTime(
                offsetDateTime.atZoneSameInstant(ZoneId.of(zoneId)).toLocalDateTime(),
                DateTimeFormat.UUUUHMMHDDTHHQMIQSS);
    }

    /**
     * Check business date update time.
     * 
     * @param orderStatusDateTime Order status date time.
     * @param zoneId Zone id.
     * @param orderStatusDate Order status date.
     * @param baseTimeInformation Base time information.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    private boolean checkBusinessDateUpdateTime(OffsetDateTime orderStatusDateTime, String zoneId,
            String orderStatusDate, String baseTimeInformation,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String storeCode,
            String errorOccurredListName, Transaction transaction) {
        String localDateTime = convertToLocalTime(orderStatusDateTime, zoneId);
        String businessBaseDateTimeInformation =
                generateBusinessBaseDateTimeInformation(orderStatusDate, baseTimeInformation);
        if (DateUtility.getDateTimeDifference(businessBaseDateTimeInformation, localDateTime,
                DateTimeFormat.UUUUHMMHDDTHHQMIQSS) > 0) {
            salesTransactionErrorDetailEntity
                    .setErrorType(ErrorType.UPDATE_BUSINESS_DATE_ERROR.getErrorType());
            salesTransactionErrorDetailEntity
                    .setErrorItemId1(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE);
            salesTransactionErrorDetailEntity.setErrorItemValue1(orderStatusDate);
            salesTransactionErrorDetailEntity.setErrorItemId2(ITEM_ID_CODE_VALUE);
            salesTransactionErrorDetailEntity.setErrorItemValue2(baseTimeInformation);
            salesTransactionErrorDetailEntity.setErrorItemId3(ITEM_ID_ORDER_STATUS_UPDATE_DATE);
            salesTransactionErrorDetailEntity.setErrorItemValue3(localDateTime);
            insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    errorOccurredListName, storeCode, transaction);
            return false;
        }
        return true;
    }

    /**
     * Generate business base date time information.
     * 
     * @param orderStatusDate Order status date.
     * @param baseTimeInformation Base time information.
     * @return Generate business base date time information.
     */
    private String generateBusinessBaseDateTimeInformation(String orderStatusDate,
            String baseTimeInformation) {
        LocalDateTime result =
                DateUtility.parseDate(orderStatusDate, DateUtility.DateFormat.UUUUMMDD)
                        .plusDays(1)
                        .atStartOfDay();
        result = result.plusHours(Long.valueOf(baseTimeInformation));
        return DateUtility.formatDateTime(result, DateTimeFormat.UUUUHMMHDDTHHQMIQSS);
    }

    /**
     * Check future.
     * 
     * @param orderStatusDate Order status date.
     * @param businessDate Business date.
     * @param errorItemId Error item id.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    private boolean checkFuture(String orderStatusDate, String businessDate, String errorItemId,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String storeCode,
            String errorOccurredListName, Transaction transaction) {
        if (StringUtils.isNotEmpty(orderStatusDate) && DateUtility
                .getDateDifference(orderStatusDate, businessDate, DateFormat.UUUUMMDD) < 0) {
            salesTransactionErrorDetailEntity
                    .setErrorType(ErrorType.BUSINESS_DATE_ERROR.getErrorType());
            salesTransactionErrorDetailEntity
                    .setErrorItemId1(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE);
            salesTransactionErrorDetailEntity.setErrorItemValue1(businessDate);
            salesTransactionErrorDetailEntity.setErrorItemId2(errorItemId);
            salesTransactionErrorDetailEntity.setErrorItemValue2(orderStatusDate);
            insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    errorOccurredListName, storeCode, transaction);
            return false;
        }
        return true;
    }


    /**
     * Check process close date.
     * 
     * @param orderStatusDate Order status date.
     * @param businessEndOfDate Business end of date.
     * @param storeCode Store code.
     * @param codeValue Code value.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    // TODO FI18-014560
    private boolean checkProcessCloseDate(String orderStatusDate, String businessEndOfDate,
            String storeCode, String codeValue,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            String errorOccurredListName, Transaction transaction) {
        long payOffDataErrorCount = 0L;
        if (StringUtils.isNotEmpty(orderStatusDate)) {
            payOffDataErrorCount = getPayOffDataErrorCount(storeCode, orderStatusDate);
        }
        if (BUSINESS_CLOSING_DAY_CHECK_FLAG_ON.equals(codeValue)
                && StringUtils.isNotEmpty(orderStatusDate)
                && DateUtility.getDateDifference(orderStatusDate, businessEndOfDate,
                        DateUtility.DateFormat.UUUUMMDD) > 0
                && payOffDataErrorCount == 0) {
            salesTransactionErrorDetailEntity
                    .setErrorType(ErrorType.CLOSE_PROCESS_COMPLETED_DATE_ERROR.getErrorType());
            salesTransactionErrorDetailEntity
                    .setErrorItemId1(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_END_OF_DATE);
            salesTransactionErrorDetailEntity.setErrorItemValue1(businessEndOfDate);
            salesTransactionErrorDetailEntity.setErrorItemId2(ITEM_ID_ORDER_STATUS_UPDATE_DATE);
            salesTransactionErrorDetailEntity.setErrorItemValue2(orderStatusDate);
            insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    errorOccurredListName, storeCode, transaction);
            return false;
        }
        return true;
    }

    /**
     * Get pay off data error count.
     * 
     * @param storeCode Store code.
     * @param businessDate Business date.
     * @return count.
     */
    private long getPayOffDataErrorCount(String storeCode, String businessDate) {
        PayOffDataCondition payOffDataEntityCondition = new PayOffDataCondition();
        payOffDataEntityCondition.createCriteria()
                .andStoreCodeEqualTo(storeCode)
                .andPayoffDateEqualTo(businessDate)
                .andIntegrityCheckTypeEqualTo(IntegrityCheckType.CONSISTENCY.getValue());
        return payOffDateEntityMapper.countByCondition(payOffDataEntityCondition);
    }

    /**
     * Check store open date.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param firstTransaction first transaction.
     * @param storeCode Store code.
     * @return Result of check.
     */
    private boolean checkStoreOpenDate(TransactionImportData transactionImportData,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            Transaction firstTransaction, String storeCode) {
        String storeOpenSpecificationDays =
                getCodeValueFromSettingTable(storeCode, CODE_L1_OPEN_DAYS, CODE_L2_OPEN_DAYS);
        if (StringUtils.isEmpty(storeOpenSpecificationDays)) {
            return true;
        }
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.BEFORE_OPEN_DATE_ERROR.getErrorType());
        String storeOpenDate = getStoreOpenDate(storeCode);
        if (!checkStoreOpenDateEmpty(storeOpenDate, storeCode, salesTransactionErrorDetailEntity,
                null, firstTransaction)) {
            return false;
        }
        if (StringUtils.isBlank(storeOpenSpecificationDays)) {
            return true;
        }
        if (!checkStoreOpenDateDetail(transactionImportData.getOrderConfirmationBusinessDate(),
                storeOpenDate, storeOpenSpecificationDays, ITEM_ID_ORDER_CONFIRMATION_BUSINESS_DATE,
                salesTransactionErrorDetailEntity, storeCode, null, firstTransaction)) {
            return false;
        }
        // Loop for transaction list.
        if (CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())) {
            for (Transaction transaction : transactionImportData.getTransactionList()) {
                if (!checkTransactionStoreOpenDate(transaction,
                        salesTransactionErrorDetailEntity)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Store open date check.
     * 
     * @param storeOpenDate Store open date.
     * @param storeCode Store code.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return Check result.
     */
    private boolean checkStoreOpenDateEmpty(String storeOpenDate, String storeCode,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            String errorOccurredListName, Transaction transaction) {
        if (storeOpenDate == null) {
            salesTransactionErrorDetailEntity.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
            salesTransactionErrorDetailEntity.setErrorItemId1(ITEM_ID_TABLE_ID);
            salesTransactionErrorDetailEntity.setErrorItemValue1(DBItem.TABLE_NAME_M_STORE);
            salesTransactionErrorDetailEntity.setErrorItemId2(ITEM_ID_STORE_CODE);
            salesTransactionErrorDetailEntity.setErrorItemValue2(storeCode);
            insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    errorOccurredListName, storeCode, transaction);
            return false;
        }
        return true;
    }

    /**
     * Transaction store open date check.
     * 
     * @param transaction Transaction Entity.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return Check result.
     */
    private boolean checkTransactionStoreOpenDate(Transaction transaction,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        checkerHelper.mapLevel2Transaction(transaction, salesTransactionErrorDetailEntity);
        String storeCode = transaction.getStoreCode();
        String storeOpenSpecificationDays =
                getCodeValueFromSettingTable(storeCode, CODE_L1_OPEN_DAYS, CODE_L2_OPEN_DAYS);
        if (StringUtils.isEmpty(storeOpenSpecificationDays)) {
            return true;
        }
        String storeOpenDate = getStoreOpenDate(storeCode);
        if (!checkStoreOpenDateEmpty(storeOpenDate, storeCode, salesTransactionErrorDetailEntity,
                ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, transaction)) {
            return false;
        }
        if (StringUtils.isBlank(storeOpenSpecificationDays)) {
            return true;
        }
        if (!checkStoreOpenDateDetail(transaction.getDataCreationBusinessDate(), storeOpenDate,
                storeOpenSpecificationDays, ITEM_ID_DATA_CREATION_BUSINESS_DATE,
                salesTransactionErrorDetailEntity, storeCode,
                ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, transaction)) {
            return false;
        }
        if (!checkStoreOpenDateDetail(transaction.getOrderStatusUpdateDate(), storeOpenDate,
                storeOpenSpecificationDays, ITEM_ID_ORDER_STATUS_UPDATE_DATE,
                salesTransactionErrorDetailEntity, storeCode,
                ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, transaction)) {
            return false;
        }

        if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
            for (TransactionItemDetail transactionItemDetail : transaction
                    .getTransactionItemDetailList()) {
                checkerHelper.mapLevel3TransactionItemDetail(transactionItemDetail,
                        salesTransactionErrorDetailEntity);
                if (!checkStoreOpenDateDetail(transactionItemDetail.getOrderStatusUpdateDate(),
                        storeOpenDate, storeOpenSpecificationDays, ITEM_ID_ORDER_STATUS_UPDATE_DATE,
                        salesTransactionErrorDetailEntity, storeCode,
                        ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST, transaction)) {
                    return false;
                }

                if (CollectionUtils
                        .isNotEmpty(transactionItemDetail.getNonItemDetailListByItem())) {
                    for (NonItemDetail nonItemDetail : transactionItemDetail
                            .getNonItemDetailListByItem()) {
                        checkerHelper.mapLevel4NonItemDetail(nonItemDetail,
                                salesTransactionErrorDetailEntity);
                        if (!checkStoreOpenDateDetail(nonItemDetail.getOrderStatusUpdateDate(),
                                storeOpenDate, storeOpenSpecificationDays,
                                ITEM_ID_ORDER_STATUS_UPDATE_DATE, salesTransactionErrorDetailEntity,
                                storeCode, ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST_BY_ITEM,
                                transaction)) {
                            return false;
                        }
                    }
                }
            }
        }

        if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
            for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                checkerHelper.mapLevel3NonItemDetail(nonItemDetail,
                        salesTransactionErrorDetailEntity);
                if (!checkStoreOpenDateDetail(nonItemDetail.getOrderStatusUpdateDate(),
                        storeOpenDate, storeOpenSpecificationDays, ITEM_ID_ORDER_STATUS_UPDATE_DATE,
                        salesTransactionErrorDetailEntity, storeCode,
                        ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST, transaction)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get store open date.
     * 
     * @param storeCode Store code.
     * @return Formatted date.
     */
    private String getStoreOpenDate(String storeCode) {
        StoreMaster resultEntity = getMStoreEntityByStoreCode(storeCode);
        if (resultEntity == null || resultEntity.getOpenDate() == null) {
            return null;
        }
        LocalDateTime result = resultEntity.getOpenDate();
        return DateUtility.formatDate(result.toLocalDate(), DateUtility.DateFormat.UUUUMMDD);
    }

    /**
     * Get store entity by store code.
     * 
     * @param storeCode Store code.
     * @return Store entity.
     */
    private StoreMaster getMStoreEntityByStoreCode(String storeCode) {
        return storeEntityMapper.selectByPrimaryKey(storeCode);
    }

    /**
     * Check store open date detail.
     * 
     * @param orderStatusDate Order status date.
     * @param storeOpenDate Store open date.
     * @param storeOpenSpecificationDays Store open specification days.
     * @param errorItemId Error item id.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    private boolean checkStoreOpenDateDetail(String orderStatusDate, String storeOpenDate,
            String storeOpenSpecificationDays, String errorItemId,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String storeCode,
            String errorOccurredListName, Transaction transaction) {
        if (StringUtils.isNotEmpty(orderStatusDate)
                && DateUtility.getDateDifference(orderStatusDate, storeOpenDate,
                        DateFormat.UUUUMMDD) > Long.parseLong(storeOpenSpecificationDays)) {
            salesTransactionErrorDetailEntity
                    .setErrorItemId1(ITEM_ID_CONSISTENCY_CHECK_BEFORE_STORE_OPEN_DATE);
            salesTransactionErrorDetailEntity.setErrorItemValue1(storeOpenDate);
            salesTransactionErrorDetailEntity.setErrorItemId2(errorItemId);
            salesTransactionErrorDetailEntity.setErrorItemValue2(orderStatusDate);
            insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    errorOccurredListName, storeCode, transaction);
            return false;
        }
        return true;
    }

    /**
     * Check store close date.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param storeCode Store code.
     * @return Result of check.
     */
    private boolean checkStoreCloseDate(TransactionImportData transactionImportData,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            Transaction firstTransaction, String storeCode) {
        String storeCloseDate = getStoreCloseDate(storeCode);
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.AFTER_CLOSE_DATE_ERROR.getErrorType());
        String storeCloseSpecificationDays =
                getCodeValueFromSettingTable(storeCode, CODE_L1_CLOSE_DAYS, CODE_L2_CLOSE_DAYS);
        if (StringUtils.isEmpty(storeCloseSpecificationDays)) {
            return true;
        }
        if (StringUtils.isNotBlank(storeCloseDate)
                && StringUtils.isNotBlank(storeCloseSpecificationDays)) {
            if (!checkStoreCloseDateDetail(transactionImportData.getOrderConfirmationBusinessDate(),
                    storeCloseDate, storeCloseSpecificationDays,
                    ITEM_ID_ORDER_CONFIRMATION_BUSINESS_DATE, salesTransactionErrorDetailEntity,
                    storeCode, null, firstTransaction)) {
                return false;
            }

            // Loop for transaction list.
            if (CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())) {
                for (Transaction transaction : transactionImportData.getTransactionList()) {
                    if (!checkTransactionCloseDate(transaction,
                            salesTransactionErrorDetailEntity)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Check transaction close date.
     * 
     * @param transaction Transaction.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return Check result.
     */
    private boolean checkTransactionCloseDate(Transaction transaction,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        checkerHelper.mapLevel2Transaction(transaction, salesTransactionErrorDetailEntity);
        String storeCode = transaction.getStoreCode();
        String storeCloseDate = getStoreCloseDate(storeCode);
        String storeCloseSpecificationDays =
                getCodeValueFromSettingTable(storeCode, CODE_L1_CLOSE_DAYS, CODE_L2_CLOSE_DAYS);
        if (StringUtils.isEmpty(storeCloseSpecificationDays)) {
            return true;
        }
        if (StringUtils.isBlank(storeCloseDate)
                || StringUtils.isBlank(storeCloseSpecificationDays)) {
            return true;
        }
        if (!checkStoreCloseDateDetail(transaction.getOrderStatusUpdateDate(), storeCloseDate,
                storeCloseSpecificationDays, ITEM_ID_ORDER_STATUS_UPDATE_DATE,
                salesTransactionErrorDetailEntity, storeCode,
                ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, transaction)) {
            return false;
        }
        
        if (!checkStoreCloseDateDetail(transaction.getDataCreationBusinessDate(), storeCloseDate,
                storeCloseSpecificationDays, ITEM_ID_DATA_CREATION_BUSINESS_DATE,
                salesTransactionErrorDetailEntity, storeCode,
                ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST, transaction)) {
            return false;
        }

        // Loop for item detail list.
        if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
            for (TransactionItemDetail transactionItemDetail : transaction
                    .getTransactionItemDetailList()) {
                checkerHelper.mapLevel3TransactionItemDetail(transactionItemDetail,
                        salesTransactionErrorDetailEntity);
                if (!checkStoreCloseDateDetail(transactionItemDetail.getOrderStatusUpdateDate(),
                        storeCloseDate, storeCloseSpecificationDays,
                        ITEM_ID_ORDER_STATUS_UPDATE_DATE, salesTransactionErrorDetailEntity,
                        storeCode, ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST,
                        transaction)) {
                    return false;
                }

                // Loop for non item detail list (in item detail list).
                if (CollectionUtils
                        .isNotEmpty(transactionItemDetail.getNonItemDetailListByItem())) {
                    for (NonItemDetail nonItemDetail : transactionItemDetail
                            .getNonItemDetailListByItem()) {
                        checkerHelper.mapLevel4NonItemDetail(nonItemDetail,
                                salesTransactionErrorDetailEntity);
                        if (!checkStoreCloseDateDetail(nonItemDetail.getOrderStatusUpdateDate(),
                                storeCloseDate, storeCloseSpecificationDays,
                                ITEM_ID_ORDER_STATUS_UPDATE_DATE, salesTransactionErrorDetailEntity,
                                storeCode, ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST_BY_ITEM,
                                transaction)) {
                            return false;
                        }
                    }
                }
            }
        }

        // Loop for non item detail list.
        if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
            for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                checkerHelper.mapLevel3NonItemDetail(nonItemDetail,
                        salesTransactionErrorDetailEntity);
                if (!checkStoreCloseDateDetail(nonItemDetail.getOrderStatusUpdateDate(),
                        storeCloseDate, storeCloseSpecificationDays,
                        ITEM_ID_ORDER_STATUS_UPDATE_DATE, salesTransactionErrorDetailEntity,
                        storeCode, ERROR_OCCURRED_LIST_NAME_NON_ITEM_DETAIL_LIST, transaction)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Get store close date.
     * 
     * @param storeCode Store code.
     * @return Formatted date.
     */
    private String getStoreCloseDate(String storeCode) {
        LocalDateTime result = getMStoreEntityByStoreCode(storeCode).getCloseDate();
        if (result == null) {
            return null;
        }
        return DateUtility.formatDate(result.toLocalDate(), DateUtility.DateFormat.UUUUMMDD);
    }

    /**
     * Check store close date detail.
     * 
     * @param orderStatusDate Order status date.
     * @param storeCloseDate Store close date.
     * @param storeCloseSpecificationDays Store close specification days.
     * @param errorItemId Error item id.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return Result of check.
     */
    private boolean checkStoreCloseDateDetail(String orderStatusDate, String storeCloseDate,
            String storeCloseSpecificationDays, String errorItemId,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String storeCode,
            String errorOccurredListName, Transaction transaction) {
        if (StringUtils.isNotEmpty(orderStatusDate)
                && DateUtility.getDateDifference(storeCloseDate, orderStatusDate,
                        DateFormat.UUUUMMDD) > Long.parseLong(storeCloseSpecificationDays)) {
            salesTransactionErrorDetailEntity
                    .setErrorType(ErrorType.AFTER_CLOSE_DATE_ERROR.getErrorType());
            salesTransactionErrorDetailEntity
                    .setErrorItemId1(ITEM_ID_CONSISTENCY_CHECK_AFTER_STORE_CLOSE_DATE);
            salesTransactionErrorDetailEntity.setErrorItemValue1(storeCloseDate);
            salesTransactionErrorDetailEntity.setErrorItemId2(errorItemId);
            salesTransactionErrorDetailEntity.setErrorItemValue2(orderStatusDate);
            insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    errorOccurredListName, storeCode, transaction);
            return false;
        }
        return true;
    }

    /**
     * Check amount balance.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    public boolean checkAmountBalance(TransactionImportData transactionImportData,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        String storeCode = transactionImportData.getStoreCode();
        if (CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())) {
            for (Transaction transaction : transactionImportData.getTransactionList()) {
                checkerHelper.mapLevel2Transaction(transaction, salesTransactionErrorDetailEntity);
                // Tender id check.
                if (!TransactionType.compare(TransactionType.PVOID,
                        transaction.getTransactionType())
                        && !existsTenderMaster(transaction, salesTransactionErrorDetailEntity,
                                storeCode, ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST)) {
                    return false;
                }
                // Loan check.
                if (SalesLinkageType.SALES_LINKAGE.is(transaction.getSalesLinkageType())
                        && !checkBalanceDetail(transaction, salesTransactionErrorDetailEntity,
                                storeCode, ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check tender master data exist.
     * 
     * @param transaction Transaction.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param storeCode Store code.
     * @param errorOccurredListName Error occurred list name.
     * @return True normal, false error.
     */
    private boolean existsTenderMaster(Transaction transaction,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String storeCode,
            String errorOccurredListName) {
        if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTenderList())) {
            for (SalesTransactionTender salesTransactionTender : transaction
                    .getSalesTransactionTenderList()) {
                checkerHelper.mapLevel3SalesTransactionTender(salesTransactionTender,
                        salesTransactionErrorDetailEntity);
                long count = countTranslationTenderMasterByStoreCodeAndTenderCode(
                        transaction.getStoreCode(), salesTransactionTender.getTenderId(),
                        transaction.getOrderStatusLastUpdateDateTime().toLocalDateTime());
                if (0L == count) {
                    salesTransactionErrorDetailEntity
                            .setErrorType(ErrorType.TENDER_ERROR.getErrorType());
                    salesTransactionErrorDetailEntity
                            .setErrorItemId1(ITEM_ID_CONSISTENCY_CHECK_TENDER_CODE);
                    salesTransactionErrorDetailEntity.setErrorItemValue1(
                            String.valueOf(salesTransactionTender.getTenderId()));
                    insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                            errorOccurredListName, storeCode, transaction);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Count tender master by store code and tender code.
     * 
     * @param storeCode Store code.
     * @param tenderId Tender id.
     * @param statusUpdateDateTime Status update date time.
     * @return Count.
     */
    private long countTranslationTenderMasterByStoreCodeAndTenderCode(String storeCode,
            String tenderId, LocalDateTime statusUpdateDateTime) {

        TranslationTenderMasterCondition translationTenderEntityCondition =
                new TranslationTenderMasterCondition();
        translationTenderEntityCondition.createCriteria()
                .andStoreCodeEqualTo(storeCode)
                .andTenderIdEqualTo(tenderId)
                .andValidDateLessThanOrEqualTo(statusUpdateDateTime);
        return translationTenderMasterMapper.countByCondition(translationTenderEntityCondition);
    }

    /**
     * Check balance detail .
     * 
     * @param transaction Transaction.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param storeCode Store code.
     * @param errorOccurredListName Error occurred list name.
     * @return True normal, false error.
     */
    private boolean checkBalanceDetail(Transaction transaction,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String storeCode,
            String errorOccurredListName) {
        String codeValue = getTaxTypeCodeValue(transaction.getStoreCode());
        if (StringUtils.isEmpty(codeValue)) {
            salesTransactionErrorDetailEntity.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
            salesTransactionErrorDetailEntity.setErrorItemId1(ITEM_ID_TABLE_ID);
            salesTransactionErrorDetailEntity
                    .setErrorItemValue1("m_business_country_state_setting");
            salesTransactionErrorDetailEntity.setErrorItemId2("code_l1");
            salesTransactionErrorDetailEntity.setErrorItemValue2(CODE_L1_TAX_TYPE);
            insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    errorOccurredListName, storeCode, transaction);
            return false;
        }
        BigDecimal itemAmount = BigDecimal.ZERO;
        BigDecimal discountAmount = BigDecimal.ZERO;
        BigDecimal taxAmount = BigDecimal.ZERO;
        BigDecimal total = calculateTotal(transaction);
        int calculateResult = 0;
        if (RtlogType.compare(RtlogType.VALUE_ADDED_TAX, codeValue)) {
            itemAmount = calculateItemAmountTaxIncluded(transaction);
            discountAmount = calculateDiscountAmountTaxIncluded(transaction);
            calculateResult = itemAmount.subtract(discountAmount).compareTo(total);
        } else {
            itemAmount = calculateItemAmountTaxExcluded(transaction);
            discountAmount = calculateDiscountAmountTaxExcluded(transaction);
            taxAmount = calculateTaxAmount(transaction);
            calculateResult = itemAmount.subtract(discountAmount).add(taxAmount).compareTo(total);
        }

        if (0 != calculateResult) {
            salesTransactionErrorDetailEntity
                    .setErrorType(ErrorType.AMOUNT_BALANCE_ERROR.getErrorType());
            salesTransactionErrorDetailEntity.setErrorItemId1(ITEM_ID_BALANCE_CHECK_TOTAL_AMOUNT);
            salesTransactionErrorDetailEntity.setErrorItemValue1(String.valueOf(itemAmount));
            salesTransactionErrorDetailEntity
                    .setErrorItemId2(ITEM_ID_BALANCE_CHECK_TOTAL_DISCOUNT_AMOUNT);
            salesTransactionErrorDetailEntity.setErrorItemValue2(String.valueOf(discountAmount));
            salesTransactionErrorDetailEntity.setErrorItemId3(ITEM_ID_BALANCE_CHECK_TAX_AMOUNT);
            salesTransactionErrorDetailEntity.setErrorItemValue3(String.valueOf(taxAmount));
            salesTransactionErrorDetailEntity
                    .setErrorItemId4(ITEM_ID_BALANCE_CHECK_TOTAL_PAYMENT_AMOUNT);
            salesTransactionErrorDetailEntity.setErrorItemValue4(String.valueOf(total));
            insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    errorOccurredListName, storeCode, transaction);
            return false;
        }
        return true;
    }

    /**
     * Calculate discount amount tax included.
     * 
     * @param transaction Transaction.
     * @return Result of calculate.
     */
    private BigDecimal calculateDiscountAmountTaxIncluded(Transaction transaction) {
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
            for (TransactionItemDetail transactionItemDetail : transaction
                    .getTransactionItemDetailList()) {
                BigDecimal discountItemDetailAmount =
                        CollectionUtils.emptyIfNull(transactionItemDetail.getItemDiscountList())
                                .stream()
                                .map(b -> new BigDecimal(b.getItemDiscountQty())
                                        .multiply(b.getItemDiscountAmtTaxIncluded().getValue())
                                        .multiply(getSignByCode(b.getItemQuantityCode())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                discountAmount = discountAmount.add(discountItemDetailAmount);
                if (CollectionUtils
                        .isNotEmpty(transactionItemDetail.getNonItemDetailListByItem())) {
                    for (NonItemDetail nonitemDetail : transactionItemDetail
                            .getNonItemDetailListByItem()) {
                        BigDecimal discountNonItemDetailAmount = CollectionUtils
                                .emptyIfNull(nonitemDetail.getNonItemDiscountDetailList())
                                .stream()
                                .map(d -> new BigDecimal(d.getNonItemDiscountQty())
                                        .multiply(d.getNonItemDiscountAmtTaxIncluded().getValue())
                                        .multiply(getSignByCode(d.getNonItemQuantityCode())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        discountAmount = discountAmount.add(discountNonItemDetailAmount);
                    }
                }
            }
        }

        if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
            for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                BigDecimal discountNonItemDetailAmount =
                        CollectionUtils.emptyIfNull(nonItemDetail.getNonItemDiscountDetailList())
                                .stream()
                                .map(e -> new BigDecimal(e.getNonItemDiscountQty())
                                        .multiply(e.getNonItemDiscountAmtTaxIncluded().getValue())
                                        .multiply(getSignByCode(e.getNonItemQuantityCode())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                discountAmount = discountAmount.add(discountNonItemDetailAmount);
            }
        }

        return discountAmount;
    }

    /**
     * Calculate item amount tax included.
     * 
     * @param transaction Transaction.
     * @return Result of calculate.
     */
    private BigDecimal calculateItemAmountTaxIncluded(Transaction transaction) {
        BigDecimal itemAmount = BigDecimal.ZERO;
        BigDecimal itemTransactionItemDetailAmount =
                CollectionUtils.emptyIfNull(transaction.getTransactionItemDetailList())
                        .stream()
                        .map(a -> a.getItemUnitPriceTaxIncluded()
                                .getValue()
                                .multiply(new BigDecimal(a.getItemQty()))
                                .multiply(getSignByCode(a.getQuantityCode())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        itemAmount = itemAmount.add(itemTransactionItemDetailAmount);
        if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
            for (TransactionItemDetail transactionItemDetail : transaction
                    .getTransactionItemDetailList()) {
                BigDecimal itemNonItemDetailAmount = CollectionUtils
                        .emptyIfNull(transactionItemDetail.getNonItemDetailListByItem())
                        .stream()
                        .map(c -> new BigDecimal(c.getNonItemQty())
                                .multiply(c.getNonItemUnitPriceTaxIncluded().getValue())
                                .multiply(getSignByCode(c.getQuantityCode())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                itemAmount = itemAmount.add(itemNonItemDetailAmount);
            }
        }

        BigDecimal itemNonItemDetailAmount =
                CollectionUtils.emptyIfNull(transaction.getNonItemDetailList())
                        .stream()
                        .map(f -> new BigDecimal(f.getNonItemQty())
                                .multiply(f.getNonItemUnitPriceTaxIncluded().getValue())
                                .multiply(getSignByCode(f.getQuantityCode())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        itemAmount = itemAmount.add(itemNonItemDetailAmount);
        return itemAmount;
    }

    /**
     * Calculate Tax Amount.
     * 
     * @param transaction Transaction.
     * @return Result of calculate.
     */
    private BigDecimal calculateTaxAmount(Transaction transaction) {
        return CollectionUtils.emptyIfNull(transaction.getSalesTransactionTaxDetailList())
                .stream()
                .map(a -> a.getTaxAmount().getValue().multiply(getSignByCode(a.getTaxAmountSign())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Get tax type code value.
     * 
     * @param storeCode Store code.
     * @return Tax Type Code Value.
     */
    private String getTaxTypeCodeValue(String storeCode) {
        BusinessCountryStateSettingMasterCondition example =
                new BusinessCountryStateSettingMasterCondition();
        example.createCriteria()
                .andCodeL1EqualTo(CODE_L1_TAX_TYPE)
                .andCodeL2EqualTo(CODE_L2_TAX_TYPE)
                .andCodeL3EqualTo(CODE_L3_TAX_TYPE)
                .andStoreCodeEqualTo(storeCode);
        List<BusinessCountryStateSettingMaster> result =
                businessCountryStateSettingEntityMapper.selectByCondition(example);
        return result.stream()
                .findFirst()
                .orElse(new BusinessCountryStateSettingMaster())
                .getCodeValue();
    }

    /**
     * Calculate total.
     * 
     * @param transaction Transaction.
     * @return Result of calculate.
     */
    private BigDecimal calculateTotal(Transaction transaction) {
        return CollectionUtils.emptyIfNull(transaction.getSalesTransactionTenderList())
                .stream()
                .map(a -> a.getTaxIncludedPaymentAmount().getValue().multiply(
                        getSignByCode(a.getPaymentSign())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculate discount amount tax excluded.
     * 
     * @param transaction Transaction.
     * @return Result of calculate.
     */
    private BigDecimal calculateDiscountAmountTaxExcluded(Transaction transaction) {
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
            for (TransactionItemDetail transactionItemDetail : transaction
                    .getTransactionItemDetailList()) {
                BigDecimal discountItemDetailAmount =
                        CollectionUtils.emptyIfNull(transactionItemDetail.getItemDiscountList())
                                .stream()
                                .map(b -> new BigDecimal(b.getItemDiscountQty())
                                        .multiply(b.getItemDiscountAmtTaxExcluded().getValue())
                                        .multiply(getSignByCode(b.getItemQuantityCode())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                discountAmount = discountAmount.add(discountItemDetailAmount);

                if (CollectionUtils
                        .isNotEmpty(transactionItemDetail.getNonItemDetailListByItem())) {
                    for (NonItemDetail nonitemDetail : transactionItemDetail
                            .getNonItemDetailListByItem()) {
                        BigDecimal discountNonItemDetailAmount = CollectionUtils
                                .emptyIfNull(nonitemDetail.getNonItemDiscountDetailList())
                                .stream()
                                .map(d -> new BigDecimal(d.getNonItemDiscountQty())
                                        .multiply(d.getNonItemDiscountAmtTaxExcluded().getValue())
                                        .multiply(getSignByCode(d.getNonItemQuantityCode())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        discountAmount = discountAmount.add(discountNonItemDetailAmount);
                    }
                }
            }
        }

        if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
            for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                BigDecimal discountNonItemDetailAmount =
                        CollectionUtils.emptyIfNull(nonItemDetail.getNonItemDiscountDetailList())
                                .stream()
                                .map(e -> new BigDecimal(e.getNonItemDiscountQty())
                                        .multiply(e.getNonItemDiscountAmtTaxExcluded().getValue())
                                        .multiply(getSignByCode(e.getNonItemQuantityCode())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                discountAmount = discountAmount.add(discountNonItemDetailAmount);
            }
        }
        return discountAmount;
    }

    /**
     * Calculate item amount tax excluded.
     * 
     * @param transaction Transaction.
     * @return Result of calculate.
     */
    private BigDecimal calculateItemAmountTaxExcluded(Transaction transaction) {
        BigDecimal itemAmount = BigDecimal.ZERO;
        BigDecimal itemTransactionItemDetailAmount =
                CollectionUtils.emptyIfNull(transaction.getTransactionItemDetailList())
                        .stream()
                        .map(a -> a.getItemUnitPriceTaxExcluded()
                                .getValue()
                                .multiply(new BigDecimal(a.getItemQty()))
                                .multiply(getSignByCode(a.getQuantityCode())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        itemAmount = itemAmount.add(itemTransactionItemDetailAmount);
        if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
            for (TransactionItemDetail transactionItemDetail : transaction
                    .getTransactionItemDetailList()) {
                BigDecimal itemNonItemDetailAmount = CollectionUtils
                        .emptyIfNull(transactionItemDetail.getNonItemDetailListByItem())
                        .stream()
                        .map(c -> new BigDecimal(c.getNonItemQty())
                                .multiply(c.getNonItemUnitPriceTaxExcluded().getValue())
                                .multiply(getSignByCode(c.getQuantityCode())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                itemAmount = itemAmount.add(itemNonItemDetailAmount);
            }
        }

        BigDecimal itemNonItemDetailAmount =
                CollectionUtils.emptyIfNull(transaction.getNonItemDetailList())
                        .stream()
                        .map(f -> new BigDecimal(f.getNonItemQty())
                                .multiply(f.getNonItemUnitPriceTaxExcluded().getValue())
                                .multiply(getSignByCode(f.getQuantityCode())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        itemAmount = itemAmount.add(itemNonItemDetailAmount);

        return itemAmount;
    }

    /**
     * Insert sales transaction validation error.
     * 
     * @param salesTransactionErrorDetailEntity sales transaction error detail entity.
     * @param errorOccurredListName Error occurred list name.
     * @param storeCode Store code.
     * @param transaction Transaction.
     */
    public void insertSalesTransactionErrorBusiness(
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            String errorOccurredListName, String storeCode, Transaction transaction) {
        calculateSalesAmount(transaction, getTaxTypeCodeValue(storeCode),
                salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity
                .setDataCreationDateTime(transaction.getDataCreationDateTime().toLocalDateTime());
        salesTransactionErrorDetailEntity.setErrorOccurredList(errorOccurredListName);
        checkerHelper.insertSalesTransactionError(salesTransactionErrorDetailEntity);
    }

    /**
     * Calculate sales amount.
     * 
     * @param transaction Transaction.
     * @param taxType Tax type.
     * @param salesTransactionErrorDetail sales transaction error detail entity.
     */
    private void calculateSalesAmount(Transaction transaction, String taxType,
            SalesTransactionErrorDetail salesTransactionErrorDetail) {

        String currencyCode = null;
        if (RtlogType.VALUE_ADDED_TAX.getRtlogType().equals(taxType)) {
            BigDecimal itemSalesAmount = calculateItemSalesAmountTaxIncluded(transaction);
            BigDecimal nonItemSalesAmount = calculateNonItemSalesAmountTaxIncluded(transaction);
            if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTotalList())) {
                Price amountTaxPrice = transaction.getSalesTransactionTotalList()
                        .get(0)
                        .getTotalAmountTaxIncluded();
                currencyCode = PriceUtils.getCurrencyCode(amountTaxPrice);
            }

            BigDecimal totalAmount = itemSalesAmount.add(nonItemSalesAmount);
            salesTransactionErrorDetail.setTaxType(taxType);
            salesTransactionErrorDetail.setItemSalesAmount(itemSalesAmount);
            salesTransactionErrorDetail.setNonItemSalesAmount(nonItemSalesAmount);
            salesTransactionErrorDetail.setTotalAmount(totalAmount);
            salesTransactionErrorDetail.setCurrencyCode(currencyCode);
        } else if (RtlogType.SALES_TAX.getRtlogType().equals(taxType)
                || RtlogType.CONSUMPTION_TAX.getRtlogType().equals(taxType)) {
            BigDecimal taxAmount =
                    CollectionUtils.emptyIfNull(transaction.getSalesTransactionTaxDetailList())
                            .stream()
                            .map(taxDetail -> {
                                return calculateAmount(taxDetail.getTaxAmount(), null,
                                        taxDetail.getTaxAmountSign());
                            })
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal itemSalesAmount =
                    calculateItemSalesAmountTaxExcluded(transaction, taxAmount);

            BigDecimal nonItemSalesAmount =
                    calculateNonItemSalesAmountTaxExcluded(transaction, taxAmount);
            if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTotalList())) {
                Price amountTaxPrice = transaction.getSalesTransactionTotalList()
                        .get(0)
                        .getTotalAmountTaxExcluded();
                currencyCode = PriceUtils.getCurrencyCode(amountTaxPrice);
            }

            BigDecimal totalAmount = itemSalesAmount.add(nonItemSalesAmount);
            salesTransactionErrorDetail.setTaxType(taxType);
            salesTransactionErrorDetail.setItemSalesAmount(itemSalesAmount);
            salesTransactionErrorDetail.setNonItemSalesAmount(nonItemSalesAmount);
            salesTransactionErrorDetail.setTotalAmount(totalAmount);
            salesTransactionErrorDetail.setCurrencyCode(currencyCode);
        }
    }

    /**
     * Calculate non item sales amount tax excluded.
     * 
     * @param transaction Transaction.
     * @param taxAmount Tax amount.
     * @return Amount.
     */
    private BigDecimal calculateNonItemSalesAmountTaxExcluded(Transaction transaction,
            BigDecimal taxAmount) {
        return CollectionUtils.emptyIfNull(transaction.getNonItemDetailList())
                .stream()
                .map(nonItemDetail -> {
                    // Amount of non item unit.
                    BigDecimal nonItemAmount =
                            calculateAmount(nonItemDetail.getNonItemSalesAmtTaxExcluded(),
                                    nonItemDetail.getNonItemQty(), nonItemDetail.getQuantityCode());

                    // Amount of non item discount.
                    BigDecimal nonItemDiscountAmount = CollectionUtils
                            .emptyIfNull(nonItemDetail.getNonItemDiscountDetailList())
                            .stream()
                            .map(nonItemDiscount -> {
                                return calculateAmount(
                                        nonItemDiscount.getNonItemDiscountAmtTaxExcluded(),
                                        nonItemDiscount.getNonItemDiscountQty(),
                                        nonItemDiscount.getNonItemQuantityCode());
                            })
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return nonItemAmount.subtract(nonItemDiscountAmount);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(taxAmount);
    }

    /**
     * Calculate item sales amount tax excluded.
     * 
     * @param transaction Transaction.
     * @param taxAmount Tax amount.
     * @return Amount.
     */
    private BigDecimal calculateItemSalesAmountTaxExcluded(Transaction transaction,
            BigDecimal taxAmount) {
        return CollectionUtils.emptyIfNull(transaction.getTransactionItemDetailList())
                .stream()
                .map(itemDetail -> {
                    // Amount of item unit.
                    BigDecimal itemAmount =
                            calculateAmount(itemDetail.getItemUnitPriceTaxExcluded(),
                                    itemDetail.getItemQty(), itemDetail.getQuantityCode());

                    // Amount of item discount.
                    BigDecimal itemDiscountAmount =
                            CollectionUtils.emptyIfNull(itemDetail.getItemDiscountList())
                                    .stream()
                                    .map(itemDiscount -> {
                                        return calculateAmount(
                                                itemDiscount.getItemDiscountAmtTaxExcluded(),
                                                itemDiscount.getItemDiscountQty(),
                                                itemDiscount.getItemQuantityCode());
                                    })
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return itemAmount.subtract(itemDiscountAmount);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(taxAmount);
    }

    /**
     * Calculate non item sales amount tax included.
     * 
     * @param transaction Transaction.
     * @return Amount.
     */
    private BigDecimal calculateNonItemSalesAmountTaxIncluded(Transaction transaction) {
        return CollectionUtils.emptyIfNull(transaction.getNonItemDetailList())
                .stream()
                .map(nonItemDetail -> {
                    BigDecimal nonItemAmount =
                            calculateAmount(nonItemDetail.getNonItemSalesAmtTaxIncluded(),
                                    nonItemDetail.getNonItemQty(), nonItemDetail.getQuantityCode());
                    BigDecimal nonItemDiscountAmount = CollectionUtils
                            .emptyIfNull(nonItemDetail.getNonItemDiscountDetailList())
                            .stream()
                            .map(nonItemDiscount -> {
                                return calculateAmount(
                                        nonItemDiscount.getNonItemDiscountAmtTaxIncluded(),
                                        nonItemDiscount.getNonItemDiscountQty(),
                                        nonItemDiscount.getNonItemQuantityCode());
                            })
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return nonItemAmount.subtract(nonItemDiscountAmount);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculate item sales amount tax included.
     * 
     * @param transaction Transaction.
     * @return Amount.
     */
    private BigDecimal calculateItemSalesAmountTaxIncluded(Transaction transaction) {
        return CollectionUtils.emptyIfNull(transaction.getTransactionItemDetailList())
                .stream()
                .map(itemDetail -> {
                    // Amount of item unit.
                    BigDecimal itemAmount =
                            calculateAmount(itemDetail.getItemUnitPriceTaxIncluded(),
                                    itemDetail.getItemQty(), itemDetail.getQuantityCode());

                    // Amount of item discount.
                    BigDecimal itemDiscountAmount =
                            CollectionUtils.emptyIfNull(itemDetail.getItemDiscountList())
                                    .stream()
                                    .map(itemDiscount -> {
                                        return calculateAmount(
                                                itemDiscount.getItemDiscountAmtTaxIncluded(),
                                                itemDiscount.getItemDiscountQty(),
                                                itemDiscount.getItemQuantityCode());
                                    })
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return itemAmount.subtract(itemDiscountAmount);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculate amount.
     * 
     * @param price Price.
     * @param quantity Quantity.
     * @param sign Sign.
     * @return Amount.
     */
    private BigDecimal calculateAmount(Price price, Integer quantity, String sign) {
        if (null != price) {
            BigDecimal quantityBigDecimal =
                    (null == quantity) ? BigDecimal.ONE : new BigDecimal(quantity);
            return price.getValue().multiply(quantityBigDecimal).multiply(getSignByCode(sign));
        }
        return BigDecimal.ZERO;
    }

    /**
     * Get sign by code.
     * 
     * @param code Code.
     * @return -1 if code equals N, 1 if code equals P.
     */
    private BigDecimal getSignByCode(String code) {
        return code == null ? BigDecimal.ONE
                : (PAYMENT_SIGN_NEGATIVE.equals(code) ? BigDecimal.ONE.negate() : BigDecimal.ONE);
    }
}
