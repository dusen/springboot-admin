/**
 * @(#)CheckTransactionDataServiceImpl.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import java.util.Objects;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.sales.common.type.DataAlterationStatus;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.common.type.SalesLinkageType;
import com.fastretailing.dcp.sales.common.type.TransactionCheckResultType;
import com.fastretailing.dcp.sales.common.type.TransactionType;
import com.fastretailing.dcp.sales.common.type.UpdateType;
import com.fastretailing.dcp.sales.importtransaction.converter.TransactionDataConverter;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.TranslationTenderMaster;

/**
 * Data check service implements.
 *
 */
@Service
public class CheckTransactionDataServiceImpl implements CheckTransactionDataService {

    /** Error check exclude flag off. */
    private static final Integer ERROR_CHECK_TYPE_EXCLUDE_FLAG_OFF = 0;
    /** Error occurred list name sales transaction tender list. */
    private static final String ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TENDER_LIST =
            "sales_transaction_tender_list";
    /** Item id tender id. */
    private static final String ITEM_ID_TENDER_ID = "tender_id";

    /** Model tools parts. */
    @Autowired
    private ModelMapper modelMapper;

    /** Business checker. */
    @Autowired
    private BusinessChecker businessChecker;

    /** Validation checker. */
    @Autowired
    private ValidationChecker validationChecker;

    /** Transaction data converter. */
    @Autowired
    private TransactionDataConverter transactionDataConverter;

    /** Checker helper. */
    @Autowired
    private CheckerHelper checkerHelper;

    /** Initial helper. */
    @Autowired
    private InitialHelper initialHelper;

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public TransactionCheckResultType checkTransactionData(
            TransactionImportData transactionImportData, String userId,
            String salesTransactionErrorId) {

        

        String storeCode = initialHelper.getInitialStoreCode(transactionImportData);
        if (StringUtils.isEmpty(transactionImportData.getStoreCode())) {
            transactionImportData.setStoreCode(storeCode);
        }
        
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                initializedTSalesTransactionErrorDetailEntity(transactionImportData, userId,
                        salesTransactionErrorId);
        
        boolean isStore = checkerHelper.isStore(storeCode);

        if (!validationChecker.checkValidation(transactionImportData,
                salesTransactionErrorDetailEntity, isStore)) {
            return TransactionCheckResultType.VALIDATION_ERROR;
        }
        if (!validationChecker.checkAdditionalValidation(transactionImportData,
                salesTransactionErrorDetailEntity)) {
            return TransactionCheckResultType.VALIDATION_ERROR;
        }
        
        salesTransactionErrorDetailEntity =
                initializedTSalesTransactionErrorDetailEntity(transactionImportData, userId,
                        salesTransactionErrorId);
        String updateType = transactionImportData.getUpdateType();
        if (isStore) {
            if (StringUtils.isEmpty(updateType) || UpdateType.INSERT.is(updateType)) {
                Optional<Transaction> transactionFirst =
                        transactionImportData.getTransactionList().stream().filter(transaction -> {
                            String transactionType = transaction.getTransactionType();
                            return TransactionType.compare(TransactionType.SALE, transactionType)
                                    || TransactionType.compare(TransactionType.PVOID,
                                            transactionType)
                                    || TransactionType.compare(TransactionType.RETURN,
                                            transactionType);
                        }).filter(transaction -> {
                            return SalesLinkageType.SALES_LINKAGE.is(transaction.getSalesLinkageType())
                                    || Objects.isNull(transaction.getSalesLinkageType());
                        }).findFirst();
                if (transactionFirst.isPresent()
                        && !initialHelper.initialPosImportData(transactionImportData,
                                transactionFirst.get(), salesTransactionErrorDetailEntity)) {
                    return TransactionCheckResultType.BUSINESS_ERROR;
                }
            }
            if (!initialWithTranslationTenderMaster(transactionImportData,
                    salesTransactionErrorDetailEntity, storeCode)) {
                return TransactionCheckResultType.BUSINESS_ERROR;
            }
        }

        transactionDataConverter.convertTransactionImportData(transactionImportData);

        boolean isInsertOrCorrection = UpdateType.INSERT.is(transactionImportData.getUpdateType())
                || UpdateType.CORRECTION.is(transactionImportData.getUpdateType());

        // Check data relation.
        if (isInsertOrCorrection && !businessChecker.checkDataRelation(transactionImportData,
                salesTransactionErrorDetailEntity)) {
            return TransactionCheckResultType.BUSINESS_ERROR;
        }

        // Check about item's date.
        if (ERROR_CHECK_TYPE_EXCLUDE_FLAG_OFF.equals(transactionImportData.getErrorCheckType())
                && !businessChecker.checkDate(transactionImportData,
                        salesTransactionErrorDetailEntity)) {
            return TransactionCheckResultType.BUSINESS_ERROR;
        }

        // Check amount balance.
        if (isInsertOrCorrection && !businessChecker.checkAmountBalance(transactionImportData,
                salesTransactionErrorDetailEntity)) {
            return TransactionCheckResultType.BUSINESS_ERROR;
        }

        return TransactionCheckResultType.NORMAL;
    }

    /**
     * Initialize a sales transaction error detail entity.
     * 
     * @param transactionImportData Transaction import data.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @return salesTransactionErrorDetailEntity a initialized tSalesTransactionErrorDetailEntity.
     */
    private SalesTransactionErrorDetail initializedTSalesTransactionErrorDetailEntity(
            TransactionImportData transactionImportData, String userId,
            String salesTransactionErrorId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(transactionImportData, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity.setStoreCode(transactionImportData.getStoreCode());
        salesTransactionErrorDetailEntity
                .setDataAlterationStatusType(DataAlterationStatus.NO_ALTERER.getValue());

        salesTransactionErrorDetailEntity.setCreateUserId(userId);
        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(salesTransactionErrorId);
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(false);
        salesTransactionErrorDetailEntity.setKeyInfo1(transactionImportData.getIntegratedOrderId());
        return salesTransactionErrorDetailEntity;
    }

    /**
     * Initial tender with translation tender master.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param storeCode Store code.
     * @return True normal, false error.
     */
    private boolean initialWithTranslationTenderMaster(TransactionImportData transactionImportData,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity, String storeCode) {
        for (Transaction transaction : CollectionUtils
                .emptyIfNull(transactionImportData.getTransactionList())) {
            for (SalesTransactionTender tender : transaction.getSalesTransactionTenderList()) {
                Optional<TranslationTenderMaster> translationTenderMasterOptional =
                        initialHelper.findTranslationTenderMasterFirst(tender, storeCode);
                if (!translationTenderMasterOptional.isPresent()) {
                    salesTransactionErrorDetailEntity
                            .setErrorType(ErrorType.TENDER_ERROR.getErrorType());
                    salesTransactionErrorDetailEntity.setIntegratedOrderId(transaction.getIntegratedOrderId());
                    salesTransactionErrorDetailEntity.setSalesTransactionId(transaction.getSalesTransactionId());
                    salesTransactionErrorDetailEntity.setErrorItemId1(ITEM_ID_TENDER_ID);                   
                    salesTransactionErrorDetailEntity.setErrorItemValue1(tender.getTenderId());
                    salesTransactionErrorDetailEntity.setSystemBrandCode(transaction.getSystemBrandCode());
                    salesTransactionErrorDetailEntity.setSystemBusinessCode(transaction.getSystemBusinessCode());
                    salesTransactionErrorDetailEntity.setSystemCountryCode(transaction.getSystemCountryCode());
                    businessChecker.insertSalesTransactionErrorBusiness(
                            salesTransactionErrorDetailEntity,
                            ERROR_OCCURRED_LIST_NAME_SALES_TRANSACTION_TENDER_LIST, storeCode,
                            transaction);
                    return false;
                }
                TranslationTenderMaster translationTenderMaster =
                        translationTenderMasterOptional.get();
                tender.setTenderGroupId(translationTenderMaster.getTenderGroup());
                tender.setTenderId(translationTenderMaster.getTenderId());
            }
        }
        return true;
    }


}
