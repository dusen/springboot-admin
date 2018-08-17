/**
 * @(#)InitialHelper.java
 *
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.common.constants.CodeConstants;
import com.fastretailing.dcp.sales.common.constants.DBItem;
import com.fastretailing.dcp.sales.common.type.DataAlterationBackboneLinkageType;
import com.fastretailing.dcp.sales.common.type.DataAlterationSalesLinkageType;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.common.type.SalesLinkageType;
import com.fastretailing.dcp.sales.common.type.TransactionType;
import com.fastretailing.dcp.sales.common.type.UpdateType;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.dto.TenderInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ItemMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.ItemMasterKey;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeaderCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.StoreMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.TranslationStoreCodeMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.TranslationTenderMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.TranslationTenderMasterCondition;
import com.fastretailing.dcp.sales.importtransaction.repository.BusinessReceiverCommonCodeMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ItemMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.StoreMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.TranslationStoreCodeMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.TranslationTenderMasterMapper;
import com.fastretailing.dcp.storecommon.dto.Price;
import com.fastretailing.dcp.storecommon.util.DateUtility;
import com.fastretailing.dcp.storecommon.util.DateUtility.DateTimeFormat;

/**
 * The class is used for initial import data.
 *
 */
@Component
public class InitialHelper {

    /** Order status shipment confirmed. */
    private static final String ORDER_STATUS_SHIPMENT_CONFIRMED = "SHIPMENT_CONFIRMED";
    /** Order status order canceled. */
    private static final String ORDER_STATUS_ORDER_CANCELED = "ORDER_CANCELED";
    /** Initial business code length. */
    private static final int INITIAL_BUSINESS_CODE_LENGTH = 2;
    /** Initial channel code length. */
    private static final int INITIAL_CHANNEL_CODE_LENGTH = 1;
    /** Initial store code length. */
    private static final int INITIAL_STORE_CODE_LENGTH = 6;
    /** Initial cash register no length. */
    private static final int INITIAL_CASH_REGISTER_NO_LENGTH = 3;
    /** Initial receipt no length. */
    private static final int INITIAL_RECEIPT_NO_LENGTH = 4;
    /** Initial transaction status. */
    private static final String INITIAL_TRANSACTION_STATUS = "01";
    /** Error occurred list name transaction list. */
    private static final String ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST = "transaction_list";
    /** Error occurred list name transaction item detail list. */
    private static final String ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST =
            "transaction_item_detail_list";
    /** Table name m item. */
    private static final String TABLE_NAME_M_ITEM = "m_item";
    /** Item id table id. */
    private static final String ITEM_ID_TABLE_ID = "table_name";
    /** Item id store code. */
    private static final String ITEM_ID_STORE_CODE = "store_code";
    /** Order sub number two. */
    private static final int ORDER_SUB_NUMBER_TWO = 2;
    /** Order sub number one. */
    private static final int ORDER_SUB_NUMBER_ONE = 1;
    /** Error check type off. */
    private static final int ERROR_CHECK_TYPE_OFF = 0;
    /** Item cost default value 0. */
    private static final int ITEM_COST_DEFAULT_VALUE_ZERO = 0;
    /**
     * Item system brand code.
     */
    private static final String ITEM_SYSTEM_BRAND_CODE = "system_brand_code";
    /**
     * Item system business code.
     */
    private static final String ITEM_SYSTEM_BUSINESS_CODE = "system_business_code";
    /**
     * Item system country code.
     */
    private static final String ITEM_SYSTEM_COUNTRY_CODE = "system_country_code";
    /**
     * Component for operating DB operations on the store master table.
     */
    @Autowired
    private StoreMasterMapper storeMasterMapper;

    /**
     * Component for operating DB operations on the translation store code master table.
     */
    @Autowired
    private TranslationStoreCodeMasterMapper translationStoreCodeMasterMapper;

    /**
     * Component for operating DB operations on the item master table.
     */
    @Autowired
    private ItemMasterMapper itemMasterMapper;

    /**
     * Component for operating DB operations on the sales transaction header table.
     */
    @Autowired
    private SalesTransactionHeaderMapper salesTransactionHeaderMapper;
    /**
     * Component for operating DB operations on the sales order information table.
     */
    @Autowired
    private SalesOrderInformationMapper salesOrderInformationMapper;
    /**
     * Component for operating DB operations on the sales transaction tender master table.
     */
    @Autowired
    private TranslationTenderMasterMapper translationTenderMasterMapper;
    /**
     * Common code master mapper.
     */
    @Autowired
    private BusinessReceiverCommonCodeMasterMapper commonCodeMasterMapper;
    /** Business checker. */
    @Autowired
    private BusinessChecker businessChecker;

    /**
     * Find the first translation tender master.
     * 
     * @param tender Tender.
     * @param storeCode Store code.
     * @return Optional of translation tender master.
     */
    public Optional<TranslationTenderMaster> findTranslationTenderMasterFirst(
            SalesTransactionTender tender, String storeCode) {
        TranslationTenderMasterCondition translationTenderMasterCondition =
                new TranslationTenderMasterCondition();
        translationTenderMasterCondition.createCriteria()
                .andStoreCodeEqualTo(storeCode)
                .andImsTenderGroupEqualTo(tender.getTenderGroupId())
                .andImsTenderIdEqualTo(Integer.valueOf(tender.getTenderId()));
        return translationTenderMasterMapper.selectByCondition(translationTenderMasterCondition)
                .stream()
                .findFirst();
    }

    /**
     * Initial pos import data.
     * 
     * @param initialImportData Initial import data.
     * @param firstTransaction First transaction.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    public boolean initialPosImportData(TransactionImportData initialImportData,
            Transaction firstTransaction,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        String transactionType = firstTransaction.getTransactionType();
        if (TransactionType.compare(TransactionType.SALE, transactionType)) {
            if (!initialImportDataForSalesOrReturn(initialImportData, firstTransaction,
                    ORDER_STATUS_SHIPMENT_CONFIRMED, salesTransactionErrorDetailEntity)) {
                return false;
            }
        } else if (TransactionType.compare(TransactionType.RETURN, transactionType)) {
            if (!initialImportDataForSalesOrReturn(initialImportData, firstTransaction,
                    ORDER_STATUS_ORDER_CANCELED, salesTransactionErrorDetailEntity)) {
                return false;
            }
        } else if (TransactionType.compare(TransactionType.PVOID, transactionType)) {
            // Initial data for pvoid.
            if (!initialImportDataForPvoid(initialImportData, firstTransaction,
                    salesTransactionErrorDetailEntity)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Initial import data for pvoid.
     *
     * @param initialImportData Initial import data.
     * @param firstTransaction First transaction.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    private boolean initialImportDataForPvoid(TransactionImportData initialImportData,
            Transaction firstTransaction,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        SalesTransactionHeaderCondition headerCondition = new SalesTransactionHeaderCondition();
        headerCondition.createCriteria()
                .andSalesTransactionIdEqualTo(firstTransaction.getOriginalTransactionId());
        List<SalesTransactionHeader> headerList =
                salesTransactionHeaderMapper.selectByCondition(headerCondition);
        String storeCode = initialImportData.getStoreCode();
        if (CollectionUtils.isEmpty(headerList)) {
            salesTransactionErrorDetailEntity.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
            salesTransactionErrorDetailEntity.setErrorItemId1(ITEM_ID_TABLE_ID);
            salesTransactionErrorDetailEntity
                    .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_HEADER);
            salesTransactionErrorDetailEntity.setErrorItemId2(ITEM_ID_STORE_CODE);
            salesTransactionErrorDetailEntity.setErrorItemValue2(storeCode);
            businessChecker.insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    null, storeCode, firstTransaction);
            return false;
        }
        SalesTransactionHeader originalTransactionHeader = headerList.get(0);
        String integratedOrderId = originalTransactionHeader.getIntegratedOrderId();
        SalesOrderInformation originalSalesOrderInformation =
                salesOrderInformationMapper.selectByPrimaryKey(integratedOrderId);
        if (null == originalSalesOrderInformation) {
            salesTransactionErrorDetailEntity.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
            salesTransactionErrorDetailEntity.setErrorItemId1(ITEM_ID_TABLE_ID);
            salesTransactionErrorDetailEntity
                    .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_ORDER_INFORMATION);
            salesTransactionErrorDetailEntity.setErrorItemId2(ITEM_ID_STORE_CODE);
            salesTransactionErrorDetailEntity.setErrorItemValue2(storeCode);
            businessChecker.insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    null, storeCode, firstTransaction);
            return false;
        }
        if (!initialTransactionForPvoid(initialImportData, originalSalesOrderInformation,
                originalTransactionHeader, salesTransactionErrorDetailEntity, firstTransaction)) {
            return false;
        }
        return true;
    }

    /**
     * Initial import data for sales or return.
     *
     * @param initialImportData Initial import data.
     * @param firstTransaction First transaction.
     * @param orderStatus Order status.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @return True normal, false error.
     */
    private boolean initialImportDataForSalesOrReturn(TransactionImportData initialImportData,
            Transaction firstTransaction, String orderStatus,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        // Initial order information.
        if (StringUtils.isEmpty(initialImportData.getStoreCode())) {
            initialImportData.setStoreCode(firstTransaction.getStoreCode());
        }
        if (Objects.isNull(initialImportData.getOrderConfirmationDateTime())) {
            initialImportData
                    .setOrderConfirmationDateTime(firstTransaction.getDataCreationDateTime());
        }
        String storeCode = initialImportData.getStoreCode();
        TranslationStoreCodeMaster translationStoreCodeMaster =
                translationStoreCodeMasterMapper.selectByPrimaryKey(storeCode);
        if (!checkTranslationStoreCodeMaster(translationStoreCodeMaster,
                salesTransactionErrorDetailEntity, storeCode, firstTransaction, null)) {
            return false;
        }

        // Initial transaction list.
        StoreMaster storeMaster = storeMasterMapper.selectByPrimaryKey(storeCode);
        if (!checkStoreMaster(storeMaster, salesTransactionErrorDetailEntity, storeCode,
                firstTransaction, null)) {
            return false;
        }
        for (Transaction transaction : CollectionUtils
                .emptyIfNull(initialImportData.getTransactionList())) {
            if (!initialTransactionForSalesOrReturn(transaction, storeCode, storeMaster,
                    orderStatus, initialImportData.getOrderConfirmationDateTime(),
                    translationStoreCodeMaster.getSystemBrandCode(),
                    translationStoreCodeMaster.getSystemCountryCode(),
                    salesTransactionErrorDetailEntity, translationStoreCodeMaster)) {
                return false;
            }
        }

        if (!initialPosOrderInformation(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity, translationStoreCodeMaster)) {
            return false;
        }

        return true;
    }

    /**
     * Initial import data for pvoid.
     *
     * @param initialImportData Initial import data.
     * @param originalSalesOrderInformation Original sales order information.
     * @param originalTransactionHeader Original transaction header.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param firstTransaction First transaction.
     * @return True normal, false error.
     */
    private boolean initialTransactionForPvoid(TransactionImportData initialImportData,
            SalesOrderInformation originalSalesOrderInformation,
            SalesTransactionHeader originalTransactionHeader,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            Transaction firstTransaction) {
        initialOrderInformationForPvoid(initialImportData, originalSalesOrderInformation);
        // Initial transaction list.
        String storeCode = initialImportData.getStoreCode();
        StoreMaster storeMaster = storeMasterMapper.selectByPrimaryKey(storeCode);
        if (!checkStoreMaster(storeMaster, salesTransactionErrorDetailEntity, storeCode,
                firstTransaction, null)) {
            return false;
        }
        String storeMasterCurrencyCode = storeMaster.getCurrencyCode();
        OffsetDateTime orderConfirmationDateTime = initialImportData.getOrderConfirmationDateTime();
        for (Transaction transaction : CollectionUtils
                .emptyIfNull(initialImportData.getTransactionList())) {
            TranslationStoreCodeMaster translationStoreCodeMaster =
                    translationStoreCodeMasterMapper.selectByPrimaryKey(storeCode);
            if (!checkTranslationStoreCodeMaster(translationStoreCodeMaster,
                    salesTransactionErrorDetailEntity, storeCode, transaction,
                    ERROR_OCCURRED_LIST_NAME_TRANSACTION_LIST)) {
                return false;
            }
            if (StringUtils.isEmpty(transaction.getIntegratedOrderId())) {
                transaction.setIntegratedOrderId(originalTransactionHeader.getIntegratedOrderId());
            }
            if (null == transaction.getOrderSubNumber()) {
                transaction.setOrderSubNumber(ORDER_SUB_NUMBER_TWO);
            }
            if (StringUtils.isEmpty(transaction.getSalesTransactionId())) {
                transaction.setSalesTransactionId(
                        createSalesTransactionId(translationStoreCodeMaster.getSystemBusinessCode(),
                                transaction.getChannelCode(), storeCode, orderConfirmationDateTime,
                                transaction.getCashRegisterNo(), transaction.getReceiptNo()));
            }
            if (null == transaction.getSalesLinkageType()) {
                transaction.setSalesLinkageType(SalesLinkageType.SALES_LINKAGE.getValue());
            }
            if (StringUtils.isEmpty(transaction.getSystemBrandCode())) {
                transaction.setSystemBrandCode(commonCodeMasterMapper.selectTypeName(
                        ITEM_SYSTEM_BRAND_CODE, translationStoreCodeMaster.getSystemBrandCode()));
            }
            if (StringUtils.isEmpty(transaction.getSystemBusinessCode())) {
                transaction.setSystemBusinessCode(commonCodeMasterMapper.selectTypeName(
                        ITEM_SYSTEM_BUSINESS_CODE, translationStoreCodeMaster.getSystemBusinessCode()));
            }
            if (StringUtils.isEmpty(transaction.getSystemCountryCode())) {
                transaction.setSystemCountryCode(commonCodeMasterMapper.selectTypeName(
                        ITEM_SYSTEM_COUNTRY_CODE, translationStoreCodeMaster.getSystemCountryCode()));
            }
            if (StringUtils.isEmpty(transaction.getOriginalTransactionId())) {
                transaction.setOriginalTransactionId(
                        originalTransactionHeader.getOriginalTransactionId());
            }
            transaction.setDeposit(getInitialPrice(transaction.getDeposit(),
                    originalTransactionHeader.getDepositCurrencyCode(), null));
            transaction.setChange(getInitialPrice(transaction.getChange(),
                    originalTransactionHeader.getChangeCurrencyCode(), null));

            transaction.setReceiptIssuedFlag(getFalseIfNull(transaction.getReceiptIssuedFlag()));
            if (StringUtils.isEmpty(transaction.getOrderStatus())) {
                transaction.setOrderStatus(ORDER_STATUS_SHIPMENT_CONFIRMED);
            }
            transaction.setSalesTransactionDiscountFlag(
                    getFalseIfNull(transaction.getSalesTransactionDiscountFlag()));
            transaction.setSalesTransactionDiscountAmountRate(
                    getInitialPrice(transaction.getSalesTransactionDiscountAmountRate(),
                            storeMasterCurrencyCode, null));

            transaction
                    .setConsistencySalesFlag(getFalseIfNull(transaction.getConsistencySalesFlag()));
            transaction.setEmployeeSaleFlag(getFalseIfNull(transaction.getEmployeeSaleFlag()));
            transaction.setEReceiptTargetFlag(getFalseIfNull(transaction.getEReceiptTargetFlag()));
        }
        return true;
    }

    /**
     * Initial order information for pvoid.
     *
     * @param initialImportData Initial import data.
     * @param originalSalesOrderInformation Original sales order information.
     */
    private void initialOrderInformationForPvoid(TransactionImportData initialImportData,
            SalesOrderInformation originalSalesOrderInformation) {
        // Initial order information.
        if (StringUtils.isEmpty(initialImportData.getUpdateType())) {
            initialImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
        }
        if (null == initialImportData.getErrorCheckType()) {
            initialImportData.setErrorCheckType(ERROR_CHECK_TYPE_OFF);
        }
        if (null == initialImportData.getDataAlterationSalesLinkageType()) {
            initialImportData.setDataAlterationSalesLinkageType(
                    DataAlterationSalesLinkageType.OFF.getValue());
        }
        if (null == initialImportData.getDataAlterationBackboneLinkageType()) {
            initialImportData.setDataAlterationBackboneLinkageType(
                    DataAlterationBackboneLinkageType.OFF.getValue());
        }
        if (StringUtils.isEmpty(initialImportData.getIntegratedOrderId())) {
            initialImportData
                    .setIntegratedOrderId(originalSalesOrderInformation.getIntegratedOrderId());
        }
        if (StringUtils.isEmpty(initialImportData.getChannelCode())) {
            initialImportData.setChannelCode(originalSalesOrderInformation.getChannelCode());
        }
        if (StringUtils.isEmpty(initialImportData.getStoreCode())) {
            initialImportData.setStoreCode(originalSalesOrderInformation.getStoreCode());
        }
        if (StringUtils.isEmpty(initialImportData.getSystemBrandCode())) {
            initialImportData
                    .setSystemBrandCode(originalSalesOrderInformation.getSystemBrandCode());
        }
        if (StringUtils.isEmpty(initialImportData.getSystemBusinessCode())) {
            initialImportData
                    .setSystemBusinessCode(originalSalesOrderInformation.getSystemBusinessCode());
        }
        if (StringUtils.isEmpty(initialImportData.getSystemCountryCode())) {
            initialImportData
                    .setSystemCountryCode(originalSalesOrderInformation.getSystemCountryCode());
        }
        if (StringUtils.isEmpty(initialImportData.getCustomerId())) {
            initialImportData.setCustomerId(originalSalesOrderInformation.getCustomerId());
        }
        if (StringUtils.isEmpty(initialImportData.getOrderConfirmationBusinessDate())) {
            initialImportData.setOrderConfirmationBusinessDate(
                    originalSalesOrderInformation.getOrderConfirmationBusinessDate());
        }
        if (null == initialImportData.getOrderConfirmationDateTime()) {
            initialImportData.setOrderConfirmationDateTime(
                    originalSalesOrderInformation.getOrderConfirmationDateTime()
                            .atOffset(ZoneOffset.UTC));
        }
        initialImportData.setDataCorrectionEditingFlag(
                getFalseIfNull(initialImportData.getDataCorrectionEditingFlag()));
    }

    /**
     * Check translation store code master.
     *
     * @param translationStoreCodeMaster Translation store code master.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param storeCode Store code.
     * @param transaction Transaction.
     * @param errorOccurredListName Error occurred list name.
     * @return True normal, false error.
     */
    private boolean checkTranslationStoreCodeMaster(
            TranslationStoreCodeMaster translationStoreCodeMaster,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String storeCode,
            Transaction transaction, String errorOccurredListName) {
        if (null == translationStoreCodeMaster) {
            salesTransactionErrorDetailEntity.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
            salesTransactionErrorDetailEntity.setErrorItemId1(ITEM_ID_TABLE_ID);
            salesTransactionErrorDetailEntity
                    .setErrorItemValue1(DBItem.TABLE_NAME_M_TRANS_STORE_CODE_TABLE);
            salesTransactionErrorDetailEntity.setErrorItemId2(ITEM_ID_STORE_CODE);
            salesTransactionErrorDetailEntity.setErrorItemValue2(storeCode);
            businessChecker.insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    errorOccurredListName, storeCode, transaction);
            return false;
        }
        return true;
    }

    /**
     * Check store master.
     *
     * @param storeMaster Store master.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param storeCode Store code.
     * @param transaction Transaction.
     * @param errorOccurredListName Error occurred list name.
     * @return True normal, false error.
     */
    private boolean checkStoreMaster(StoreMaster storeMaster,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String storeCode,
            Transaction transaction, String errorOccurredListName) {
        if (null == storeMaster) {
            salesTransactionErrorDetailEntity.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
            salesTransactionErrorDetailEntity.setErrorItemId1(ITEM_ID_TABLE_ID);
            salesTransactionErrorDetailEntity.setErrorItemValue1(DBItem.TABLE_NAME_M_STORE);
            salesTransactionErrorDetailEntity.setErrorItemId2(ITEM_ID_STORE_CODE);
            salesTransactionErrorDetailEntity.setErrorItemValue2(storeCode);
            businessChecker.insertSalesTransactionErrorBusiness(salesTransactionErrorDetailEntity,
                    errorOccurredListName, storeCode, transaction);
            return false;
        }
        return true;
    }

    /**
     * Get initial price.
     *
     * @param originalPrice Original price.
     * @param initialCurrencyCode Initial currency code.
     * @param initialValue Initial value.
     * @return Initial price.
     */
    private Price getInitialPrice(Price originalPrice, String initialCurrencyCode,
            BigDecimal initialValue) {
        Price newPrice = new Price();
        if (null != originalPrice) {
            if (null == originalPrice.getCurrencyCode()) {
                newPrice.setCurrencyCode(Currency.getInstance(initialCurrencyCode));
            } else {
                newPrice.setCurrencyCode(originalPrice.getCurrencyCode());
            }
            if (null != initialValue && null == originalPrice.getValue()) {
                newPrice.setValue(initialValue);
            } else {
                newPrice.setValue(originalPrice.getValue());
            }
            return newPrice;
        } else {
            return null;
        }
    }

    /**
     * Get false if null.
     *
     * @param flag Flag.
     * @return Flag.
     */
    private Boolean getFalseIfNull(Boolean flag) {
        return null == flag ? false : flag;
    }

    /**
     * Initial pos order information.
     *
     * @param initialImportData Initial import data.
     * @param firstTransaction First transaction.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param translationStoreCodeMaster Transaction store code master.
     * @return True normal, false error.
     */
    private boolean initialPosOrderInformation(TransactionImportData initialImportData,
            Transaction firstTransaction,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            TranslationStoreCodeMaster translationStoreCodeMaster) {
        if (StringUtils.isEmpty(initialImportData.getUpdateType())) {
            initialImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
        }
        if (null == initialImportData.getErrorCheckType()) {
            initialImportData.setErrorCheckType(ERROR_CHECK_TYPE_OFF);
        }
        if (null == initialImportData.getDataAlterationSalesLinkageType()) {
            initialImportData.setDataAlterationSalesLinkageType(
                    DataAlterationSalesLinkageType.OFF.getValue());
        }
        if (null == initialImportData.getDataAlterationBackboneLinkageType()) {
            initialImportData.setDataAlterationBackboneLinkageType(
                    DataAlterationBackboneLinkageType.OFF.getValue());
        }
        if (StringUtils.isEmpty(initialImportData.getIntegratedOrderId())) {
            String salesTransactionId = firstTransaction.getSalesTransactionId();
            initialImportData.setIntegratedOrderId(
                    salesTransactionId.substring(0, salesTransactionId.length() - 3));
        }
        if (StringUtils.isEmpty(initialImportData.getChannelCode())) {
            initialImportData.setChannelCode(firstTransaction.getChannelCode());
        }
        if (StringUtils.isEmpty(initialImportData.getSystemBrandCode())) {
            initialImportData.setSystemBrandCode(commonCodeMasterMapper.selectTypeName(
                    ITEM_SYSTEM_BRAND_CODE, translationStoreCodeMaster.getSystemBrandCode()));
        }
        if (StringUtils.isEmpty(initialImportData.getSystemBusinessCode())) {
            initialImportData.setSystemBusinessCode(commonCodeMasterMapper.selectTypeName(
                    ITEM_SYSTEM_BUSINESS_CODE, translationStoreCodeMaster.getSystemBusinessCode()));
        }
        if (StringUtils.isEmpty(initialImportData.getSystemCountryCode())) {
            initialImportData.setSystemCountryCode(commonCodeMasterMapper.selectTypeName(
                    ITEM_SYSTEM_COUNTRY_CODE, translationStoreCodeMaster.getSystemCountryCode()));
        }

        if (StringUtils.isEmpty(initialImportData.getCustomerId())) {
            initialImportData.setCustomerId(firstTransaction.getCustomerId());
        }
        if (StringUtils.isEmpty(initialImportData.getOrderConfirmationBusinessDate())) {
            initialImportData.setOrderConfirmationBusinessDate(
                    firstTransaction.getDataCreationBusinessDate());
        }
        initialImportData.setDataCorrectionEditingFlag(
                getFalseIfNull(initialImportData.getDataCorrectionEditingFlag()));
        return true;
    }

    /**
     * Create integrated order id.
     *
     * @param businessCode Business code.
     * @param channelCode Channel code.
     * @param storeCode Store code.
     * @param orderConfirmationDateTime Order confirmation date time.
     * @param cashRegisterNumber Cash register number.
     * @param receiptNumber Receipt number.
     * @return Integrated order id.
     */
    private String createIntegratedOrderId(String businessCode, String channelCode,
            String storeCode, OffsetDateTime orderConfirmationDateTime, Integer cashRegisterNumber,
            String receiptNumber) {
        String targetOrderConfirmationDateTime =
                DateUtility
                        .formatDateTime(orderConfirmationDateTime.toLocalDateTime(),
                                DateTimeFormat.UUUUMMDDHHMMSS)
                        .substring(2, 12);
        return new StringBuilder()
                .append(fillStringWithZero(businessCode, INITIAL_BUSINESS_CODE_LENGTH))
                .append(fillStringWithZero(channelCode, INITIAL_CHANNEL_CODE_LENGTH))
                .append(fillStringWithZero(storeCode, INITIAL_STORE_CODE_LENGTH))
                .append(targetOrderConfirmationDateTime)
                .append(CodeConstants.HYPHEN)
                .append(fillStringWithZero(String.valueOf(cashRegisterNumber),
                        INITIAL_CASH_REGISTER_NO_LENGTH))
                .append(fillStringWithZero(receiptNumber, INITIAL_RECEIPT_NO_LENGTH))
                .toString();
    }

    /**
     * Create sales transaction id.
     *
     * @param businessCode Business code.
     * @param channelCode Channel code.
     * @param storeCode Store code.
     * @param orderConfirmationDateTime Order confirmation date time.
     * @param cashRegisterNumber Cash register number.
     * @param receiptNumber Receipt number.
     * @return Sales transaction id.
     */
    private String createSalesTransactionId(String businessCode, String channelCode,
            String storeCode, OffsetDateTime orderConfirmationDateTime, Integer cashRegisterNumber,
            String receiptNumber) {
        String targetOrderConfirmationDateTime =
                DateUtility
                        .formatDateTime(orderConfirmationDateTime.toLocalDateTime(),
                                DateTimeFormat.UUUUMMDDHHMMSS)
                        .substring(2, 12);
        return new StringBuilder()
                .append(fillStringWithZero(businessCode, INITIAL_BUSINESS_CODE_LENGTH))
                .append(fillStringWithZero(channelCode, INITIAL_CHANNEL_CODE_LENGTH))
                .append(fillStringWithZero(storeCode, INITIAL_STORE_CODE_LENGTH))
                .append(targetOrderConfirmationDateTime)
                .append(CodeConstants.HYPHEN)
                .append(fillStringWithZero(String.valueOf(cashRegisterNumber),
                        INITIAL_CASH_REGISTER_NO_LENGTH))
                .append(fillStringWithZero(receiptNumber, INITIAL_RECEIPT_NO_LENGTH))
                .append(CodeConstants.HYPHEN)
                .append(INITIAL_TRANSACTION_STATUS)
                .toString();
    }

    /**
     * Fill string with zero.
     *
     * @param originalString Original string.
     * @param expectLength Expect length.
     * @return Fixed string.
     */
    private String fillStringWithZero(String originalString, int expectLength) {
        int start =
                originalString.length() > expectLength ? originalString.length() - expectLength : 0;
        int end = originalString.length() > expectLength ? originalString.length() : expectLength;
        return StringUtils.leftPad(originalString, expectLength, "0").substring(start, end);
    }

    /**
     * Initial transaction for sales or return.
     *
     * @param transaction Transaction.
     * @param storeCode Store code.
     * @param storeMaster Store master.
     * @param orderStatus Order status.
     * @param orderConfirmationDateTime Order confirmation date time.
     * @param systemBrandCode System brand code.
     * @param systemCountryCode System country code.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param translationStoreCodeMaster Transaction store code master.
     * @return True normal, false error.
     */
    // TODO FI18-014520
    private boolean initialTransactionForSalesOrReturn(Transaction transaction, String storeCode,
            StoreMaster storeMaster, String orderStatus, OffsetDateTime orderConfirmationDateTime,
            String systemBrandCode, String systemCountryCode,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            TranslationStoreCodeMaster translationStoreCodeMaster) {
        if (StringUtils.isEmpty(transaction.getIntegratedOrderId())) {
            transaction.setIntegratedOrderId(
                    createIntegratedOrderId(translationStoreCodeMaster.getSystemBusinessCode(),
                            transaction.getChannelCode(), storeCode, orderConfirmationDateTime,
                            transaction.getCashRegisterNo(), transaction.getReceiptNo()));
        }
        if (null == transaction.getOrderSubNumber()) {
            transaction.setOrderSubNumber(ORDER_SUB_NUMBER_ONE);
        }
        if (StringUtils.isEmpty(transaction.getSalesTransactionId())) {
            transaction.setSalesTransactionId(
                    createSalesTransactionId(translationStoreCodeMaster.getSystemBusinessCode(),
                            transaction.getChannelCode(), storeCode, orderConfirmationDateTime,
                            transaction.getCashRegisterNo(), transaction.getReceiptNo()));
        }
        if (null == transaction.getSalesLinkageType()) {
            transaction.setSalesLinkageType(SalesLinkageType.SALES_LINKAGE.getValue());
        }

        if (StringUtils.isEmpty(transaction.getSystemBrandCode())) {
            transaction.setSystemBrandCode(commonCodeMasterMapper.selectTypeName(
                    ITEM_SYSTEM_BRAND_CODE, translationStoreCodeMaster.getSystemBrandCode()));
        }
        if (StringUtils.isEmpty(transaction.getSystemBusinessCode())) {
            transaction.setSystemBusinessCode(commonCodeMasterMapper.selectTypeName(
                    ITEM_SYSTEM_BUSINESS_CODE, translationStoreCodeMaster.getSystemBusinessCode()));
        }
        if (StringUtils.isEmpty(transaction.getSystemCountryCode())) {
            transaction.setSystemCountryCode(commonCodeMasterMapper.selectTypeName(
                    ITEM_SYSTEM_COUNTRY_CODE, translationStoreCodeMaster.getSystemCountryCode()));
        }

        String storeMasterCurrencyCode = storeMaster.getCurrencyCode();
        transaction.setDeposit(
                getInitialPrice(transaction.getDeposit(), storeMasterCurrencyCode, null));
        transaction
                .setChange(getInitialPrice(transaction.getChange(), storeMasterCurrencyCode, null));

        transaction.setReceiptIssuedFlag(getFalseIfNull(transaction.getReceiptIssuedFlag()));
        if (StringUtils.isEmpty(transaction.getOrderStatus())) {
            transaction.setOrderStatus(orderStatus);
        }
        transaction.setSalesTransactionDiscountFlag(
                getFalseIfNull(transaction.getSalesTransactionDiscountFlag()));
        transaction.setSalesTransactionDiscountAmountRate(
                getInitialPrice(transaction.getSalesTransactionDiscountAmountRate(),
                        storeMasterCurrencyCode, null));

        transaction.setConsistencySalesFlag(getFalseIfNull(transaction.getConsistencySalesFlag()));
        transaction.setEmployeeSaleFlag(getFalseIfNull(transaction.getEmployeeSaleFlag()));
        transaction.setEReceiptTargetFlag(getFalseIfNull(transaction.getEReceiptTargetFlag()));
        if (!initialDetailInTransactionForSalesOrReturn(transaction, storeCode, systemBrandCode,
                systemCountryCode, salesTransactionErrorDetailEntity, storeMasterCurrencyCode)) {
            return false;
        }
        return true;
    }

    /**
     * Initial detail in transaction for sales or return.
     *
     * @param transaction Transaction.
     * @param storeCode Store code.
     * @param systemBrandCode System brand code.
     * @param systemCountryCode System country code.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param storeMasterCurrencyCode Store master currency code.
     * @return True normal, false error.
     */
    private boolean initialDetailInTransactionForSalesOrReturn(Transaction transaction,
            String storeCode, String systemBrandCode, String systemCountryCode,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            String storeMasterCurrencyCode) {
        for (TransactionItemDetail transactionItemDetail : CollectionUtils
                .emptyIfNull(transaction.getTransactionItemDetailList())) {
            ItemMaster itemMaster = getItemMaster(systemBrandCode, systemCountryCode,
                    transactionItemDetail.getL3ItemCode());
            if (null == itemMaster) {
                salesTransactionErrorDetailEntity
                        .setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
                salesTransactionErrorDetailEntity.setErrorItemId1(ITEM_ID_TABLE_ID);
                salesTransactionErrorDetailEntity.setErrorItemValue1(TABLE_NAME_M_ITEM);
                salesTransactionErrorDetailEntity.setErrorItemId2(ITEM_ID_STORE_CODE);
                salesTransactionErrorDetailEntity.setErrorItemValue2(storeCode);
                businessChecker.insertSalesTransactionErrorBusiness(
                        salesTransactionErrorDetailEntity,
                        ERROR_OCCURRED_LIST_NAME_TRANSACTION_ITEM_DETAIL_LIST, storeCode,
                        transaction);
                return false;
            }
            initialTransactionItemDetailForSalesOrReturn(transactionItemDetail,
                    storeMasterCurrencyCode, itemMaster.getMajorCategoryCode());
        }
        CollectionUtils.emptyIfNull(transaction.getNonItemDetailList()).forEach(nonItemDetail -> {
            initialNonItemDetailForSalesOrReturn(nonItemDetail, storeMasterCurrencyCode);
        });
        CollectionUtils.emptyIfNull(transaction.getSalesTransactionTenderList()).forEach(tender -> {
            tender.setTaxIncludedPaymentAmount(getInitialPrice(tender.getTaxIncludedPaymentAmount(),
                    storeMasterCurrencyCode, null));
            TenderInfo tenderInfo = tender.getTenderInfoList();
            if (null != tenderInfo) {
                tenderInfo.setDiscountAmount(getInitialPrice(tenderInfo.getDiscountAmount(),
                        storeMasterCurrencyCode, null));
                tenderInfo.setCouponDiscountAmountSetting(
                        getInitialPrice(tenderInfo.getCouponDiscountAmountSetting(),
                                storeMasterCurrencyCode, null));
                tenderInfo.setCouponMinUsageAmountThreshold(
                        getInitialPrice(tenderInfo.getCouponMinUsageAmountThreshold(),
                                storeMasterCurrencyCode, null));
            }
        });
        CollectionUtils.emptyIfNull(transaction.getSalesTransactionTaxDetailList()).forEach(tax -> {
            tax.setTaxAmount(getInitialPrice(tax.getTaxAmount(), storeMasterCurrencyCode, null));
        });
        CollectionUtils.emptyIfNull(transaction.getSalesTransactionTotalList()).forEach(total -> {
            total.setTotalAmountTaxExcluded(getInitialPrice(total.getTotalAmountTaxExcluded(),
                    storeMasterCurrencyCode, null));
            total.setTotalAmountTaxIncluded(getInitialPrice(total.getTotalAmountTaxIncluded(),
                    storeMasterCurrencyCode, null));
        });
        return true;
    }

    /**
     * Get item master.
     *
     * @param systemBrandCode System brand code.
     * @param systemCountryCode System country code.
     * @param itemCode Item code.
     * @return Item master.
     */
    private ItemMaster getItemMaster(String systemBrandCode, String systemCountryCode,
            String itemCode) {
        ItemMasterKey key = new ItemMasterKey();
        key.setSystemBrandCode(systemBrandCode);
        key.setSystemCountryCode(systemCountryCode);
        key.setItemCode(itemCode);
        return itemMasterMapper.selectByPrimaryKey(key);
    }

    /**
     * Initial transaction item detail for sales or return.
     *
     * @param transactionItemDetail Transaction item detail.
     * @param storeMasterCurrencyCode Store master currency code.
     * @param majorCategoryCode Major category code.
     */
    private void initialTransactionItemDetailForSalesOrReturn(
            TransactionItemDetail transactionItemDetail, String storeMasterCurrencyCode,
            String majorCategoryCode) {
        if (null == transactionItemDetail.getDeptCode()) {
            transactionItemDetail.setDeptCode(Integer.valueOf(majorCategoryCode));
        }
        transactionItemDetail.setItemCost(getInitialPrice(transactionItemDetail.getItemCost(),
                storeMasterCurrencyCode, new BigDecimal(ITEM_COST_DEFAULT_VALUE_ZERO)));
        transactionItemDetail.setInitialSellingPrice(getInitialPrice(
                transactionItemDetail.getInitialSellingPrice(), storeMasterCurrencyCode, null));
        transactionItemDetail.setBItemSellingPrice(getInitialPrice(
                transactionItemDetail.getBItemSellingPrice(), storeMasterCurrencyCode, null));
        transactionItemDetail.setItemNewPrice(getInitialPrice(
                transactionItemDetail.getItemNewPrice(), storeMasterCurrencyCode, null));
        transactionItemDetail.setItemUnitPriceTaxExcluded(
                getInitialPrice(transactionItemDetail.getItemUnitPriceTaxExcluded(),
                        storeMasterCurrencyCode, null));
        transactionItemDetail.setItemUnitPriceTaxIncluded(
                getInitialPrice(transactionItemDetail.getItemUnitPriceTaxIncluded(),
                        storeMasterCurrencyCode, null));
        transactionItemDetail.setItemSalesAmtTaxExcluded(getInitialPrice(
                transactionItemDetail.getItemSalesAmtTaxExcluded(), storeMasterCurrencyCode, null));
        transactionItemDetail.setItemSalesAmtTaxIncluded(getInitialPrice(
                transactionItemDetail.getItemSalesAmtTaxIncluded(), storeMasterCurrencyCode, null));
        transactionItemDetail.setBundlePurchasePrice(getInitialPrice(
                transactionItemDetail.getBundlePurchasePrice(), storeMasterCurrencyCode, null));
        transactionItemDetail.setItemDiscountAmount(getInitialPrice(
                transactionItemDetail.getItemDiscountAmount(), storeMasterCurrencyCode, null));
        transactionItemDetail
                .setBundleSalesFlag(getFalseIfNull(transactionItemDetail.getBundleSalesFlag()));
        transactionItemDetail.setBundleSalesPrice(getInitialPrice(
                transactionItemDetail.getBundleSalesPrice(), storeMasterCurrencyCode, null));

        initialNonItemDetailListByItemForSalesOrReturn(transactionItemDetail,
                storeMasterCurrencyCode);
        CollectionUtils.emptyIfNull(transactionItemDetail.getItemDiscountList())
                .forEach(itemDiscount -> {
                    itemDiscount.setItemDiscountAmtTaxExcluded(
                            getInitialPrice(itemDiscount.getItemDiscountAmtTaxExcluded(),
                                    storeMasterCurrencyCode, null));
                    itemDiscount.setItemDiscountAmtTaxIncluded(
                            getInitialPrice(itemDiscount.getItemDiscountAmtTaxIncluded(),
                                    storeMasterCurrencyCode, null));
                });
        CollectionUtils.emptyIfNull(transactionItemDetail.getItemTaxDetailList())
                .forEach(itemTax -> {
                    itemTax.setItemTaxAmt(getInitialPrice(itemTax.getItemTaxAmt(),
                            storeMasterCurrencyCode, null));
                });
    }

    /**
     * Initial non item detail list by item for sales or return.
     *
     * @param transactionItemDetail Transaction item detail.
     * @param storeMasterCurrencyCode Store master currency code.
     */
    private void initialNonItemDetailListByItemForSalesOrReturn(
            TransactionItemDetail transactionItemDetail, String storeMasterCurrencyCode) {
        CollectionUtils.emptyIfNull(transactionItemDetail.getNonItemDetailListByItem())
                .forEach(nonItemDetail -> {
                    nonItemDetail.setNonItemUnitPriceTaxExcluded(
                            getInitialPrice(nonItemDetail.getNonItemUnitPriceTaxExcluded(),
                                    storeMasterCurrencyCode, null));
                    nonItemDetail.setNonItemUnitPriceTaxIncluded(
                            getInitialPrice(nonItemDetail.getNonItemUnitPriceTaxIncluded(),
                                    storeMasterCurrencyCode, null));
                    nonItemDetail.setNonItemSalesAmtTaxExcluded(
                            getInitialPrice(nonItemDetail.getNonItemSalesAmtTaxExcluded(),
                                    storeMasterCurrencyCode, null));
                    nonItemDetail.setNonItemSalesAmtTaxIncluded(
                            getInitialPrice(nonItemDetail.getNonItemSalesAmtTaxIncluded(),
                                    storeMasterCurrencyCode, null));
                    nonItemDetail.setNonItemNewPrice(getInitialPrice(
                            nonItemDetail.getNonItemNewPrice(), storeMasterCurrencyCode, null));

                    CollectionUtils.emptyIfNull(nonItemDetail.getNonItemDiscountDetailList())
                            .forEach(nonItemDiscount -> {
                                nonItemDiscount.setNonItemDiscountAmtTaxExcluded(getInitialPrice(
                                        nonItemDiscount.getNonItemDiscountAmtTaxExcluded(),
                                        storeMasterCurrencyCode, null));
                                nonItemDiscount.setNonItemDiscountAmtTaxIncluded(getInitialPrice(
                                        nonItemDiscount.getNonItemDiscountAmtTaxIncluded(),
                                        storeMasterCurrencyCode, null));
                            });
                    CollectionUtils.emptyIfNull(nonItemDetail.getNonItemTaxDetailList())
                            .forEach(nonItemTax -> {
                                nonItemTax.setNonItemTaxAmt(
                                        getInitialPrice(nonItemTax.getNonItemTaxAmt(),
                                                storeMasterCurrencyCode, null));
                            });
                });
    }

    /**
     * Initial non item detail for sales or return.
     *
     * @param nonItemDetail Non item detail.
     * @param storeMasterCurrencyCode Store master currency code.
     */
    private void initialNonItemDetailForSalesOrReturn(NonItemDetail nonItemDetail,
            String storeMasterCurrencyCode) {
        nonItemDetail.setNonItemUnitPriceTaxExcluded(getInitialPrice(
                nonItemDetail.getNonItemUnitPriceTaxExcluded(), storeMasterCurrencyCode, null));
        nonItemDetail.setNonItemUnitPriceTaxIncluded(getInitialPrice(
                nonItemDetail.getNonItemUnitPriceTaxIncluded(), storeMasterCurrencyCode, null));
        nonItemDetail.setNonItemSalesAmtTaxExcluded(getInitialPrice(
                nonItemDetail.getNonItemSalesAmtTaxExcluded(), storeMasterCurrencyCode, null));
        nonItemDetail.setNonItemSalesAmtTaxIncluded(getInitialPrice(
                nonItemDetail.getNonItemSalesAmtTaxIncluded(), storeMasterCurrencyCode, null));
        nonItemDetail.setNonItemNewPrice(
                getInitialPrice(nonItemDetail.getNonItemNewPrice(), storeMasterCurrencyCode, null));

        CollectionUtils.emptyIfNull(nonItemDetail.getNonItemDiscountDetailList())
                .forEach(nonDiscount -> {
                    nonDiscount.setNonItemDiscountAmtTaxExcluded(
                            getInitialPrice(nonDiscount.getNonItemDiscountAmtTaxExcluded(),
                                    storeMasterCurrencyCode, null));
                    nonDiscount.setNonItemDiscountAmtTaxIncluded(
                            getInitialPrice(nonDiscount.getNonItemDiscountAmtTaxIncluded(),
                                    storeMasterCurrencyCode, null));
                });
        CollectionUtils.emptyIfNull(nonItemDetail.getNonItemTaxDetailList()).forEach(nonItemTax -> {
            nonItemTax.setNonItemTaxAmt(
                    getInitialPrice(nonItemTax.getNonItemTaxAmt(), storeMasterCurrencyCode, null));
        });
    }

    /**
     * Get initial store code.
     * 
     * @param transactionImportData Transaction import data.
     * @return Store code.
     */
    public String getInitialStoreCode(TransactionImportData transactionImportData) {
        String urlStoreCode = null;

        Optional<Transaction> salseLinkageOptional =
                transactionImportData.getTransactionList().stream().filter(transaction -> {
                    return SalesLinkageType.SALES_LINKAGE.is(transaction.getSalesLinkageType())
                            || Objects.isNull(transaction.getSalesLinkageType());
                }).findFirst();
        if (salseLinkageOptional.isPresent()) {
            Transaction targetTransaction = salseLinkageOptional.get();
            if (TransactionType.compare(TransactionType.SALE,
                    targetTransaction.getTransactionType())
                    || TransactionType.compare(TransactionType.RETURN,
                            targetTransaction.getTransactionType())) {
                urlStoreCode = targetTransaction.getStoreCode();
            } else {
                SalesTransactionHeaderCondition headerCondition =
                        new SalesTransactionHeaderCondition();
                headerCondition.createCriteria()
                        .andSalesTransactionIdEqualTo(targetTransaction.getOriginalTransactionId());
                List<SalesTransactionHeader> headerList =
                        salesTransactionHeaderMapper.selectByCondition(headerCondition);
                if (CollectionUtils.isEmpty(headerList)) {
                    return urlStoreCode;
                }
                SalesTransactionHeader originalTransactionHeader = headerList.get(0);
                String integratedOrderId = originalTransactionHeader.getIntegratedOrderId();
                SalesOrderInformation originalSalesOrderInformation =
                        salesOrderInformationMapper.selectByPrimaryKey(integratedOrderId);
                if (null == originalSalesOrderInformation) {
                    return urlStoreCode;
                }
                urlStoreCode = originalSalesOrderInformation.getStoreCode();
            }
        }

        return urlStoreCode;

    }
}
