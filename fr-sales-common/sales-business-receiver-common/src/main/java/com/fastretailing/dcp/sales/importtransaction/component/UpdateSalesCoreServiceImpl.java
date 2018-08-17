/**
 * @(#)UpdateSalesCoreServiceImpl.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.importtransaction.converter.CommonDataProcessor;
import com.fastretailing.dcp.sales.importtransaction.converter.CoreTableDataConverter;
import com.fastretailing.dcp.sales.importtransaction.converter.ErrorEvacuationTableDataConverter;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetailCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetailCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeaderCondition;
import com.fastretailing.dcp.sales.importtransaction.exception.ErrorEvacuationException;
import com.fastretailing.dcp.sales.importtransaction.exception.InvalidDataException;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionErrorDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionHeaderMapper;

/**
 * The class is used to update transaction data.
 *
 */
@Service
public class UpdateSalesCoreServiceImpl implements UpdateSalesCoreService {

    /**
     * Component for operating DB operations on the error evacuation transaction header table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionHeaderMapper tevacuationHeaderEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation transaction detail table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionDetailMapper tevacuationDetailMapper;

    /**
     * Component for operating DB operations on the sales order information table.
     */
    @Autowired
    private SalesOrderInformationMapper tsalesOrderInformationMapper;

    /**
     * Component for operating DB operations on the sales transaction header table.
     */
    @Autowired
    private SalesTransactionHeaderMapper tsalesTransactionHeaderEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction detail table.
     */
    @Autowired
    private SalesTransactionDetailMapper tsalesTransactionDetailEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction error detail table.
     */
    @Autowired
    private SalesTransactionErrorDetailMapper salesTransactionErrorDetailEntityMapper;

    /**
     * The converter for error evacuation table.
     */
    @Autowired
    private ErrorEvacuationTableDataConverter errorEvacuationTableDataConverter;

    /**
     * The converter for sales core table.
     */
    @Autowired
    private CoreTableDataConverter coreTableDataConverter;

    /** Model tools parts. */
    @Autowired
    private ModelMapper modelMapper;

    /** Common data processor parts. */
    @Autowired
    private CommonDataProcessor commonDataProcessor;

    /**
     * {@inheritDoc}
     * @throws ErrorEvacuationException 
     * 
     */
    @Override
    public void updateSalesCoreTable(TransactionImportData transactionImportData, String userId,
            String salesTransactionErrorId) throws ErrorEvacuationException {

        SalesTransactionErrorDetailCondition salesTransactionErrorDetailEntityCondition =
                new SalesTransactionErrorDetailCondition();

        salesTransactionErrorDetailEntityCondition.createCriteria()
                .andIntegratedOrderIdEqualTo(transactionImportData.getIntegratedOrderId())
                .andDataAlterationStatusTypeEqualTo("0");

        Optional<SalesTransactionErrorDetail> optional = salesTransactionErrorDetailEntityMapper
                .selectByCondition(salesTransactionErrorDetailEntityCondition)
                .stream()
                .findFirst();

        String originalSalesTransactionErrorId = transactionImportData.getSalesTransactionErrorId();
        if (optional.isPresent()) {
            for (Transaction transaction : transactionImportData.getTransactionList()) {
                addSalesErrorEvacuation(transaction, userId, salesTransactionErrorId,
                        new AtomicInteger(0), originalSalesTransactionErrorId);
            }
            throw new ErrorEvacuationException("Error evacuation occurred.");
        } else {
            updateSalesTransactionData(transactionImportData, salesTransactionErrorId, userId);
        }
    }

    /**
     * Update transaction data sales.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param userId User id.
     */
    private void updateSalesTransactionData(TransactionImportData transactionImportData,
            String salesTransactionErrorId,
            String userId) {
        if (StringUtils.isNotEmpty(transactionImportData.getSalesTransactionErrorId())) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                    convertTSalesTransactionErrorDetailEntityForUpdateType(transactionImportData,
                            transactionImportData.getSalesTransactionErrorId(), userId);
            throw new InvalidDataException(salesTransactionErrorDetailEntity);
        }

        SalesOrderInformation tsalesOrderInformationEntity =
                getOrderInformationByOrderIntegratedId(transactionImportData);
        Optional.ofNullable(tsalesOrderInformationEntity).orElseThrow(() -> {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                    convertTSalesTransactionErrorDetailEntityForUpdateType(transactionImportData,
                            salesTransactionErrorId, userId);
            return new InvalidDataException(salesTransactionErrorDetailEntity);
        });

        for (Transaction transaction : transactionImportData.getTransactionList()) {
            updateData(transaction, userId);
        }
    }

    /**
     * Update transaction data into tables.
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     */
    private void updateData(Transaction transaction, String userId) {

        String salesTransactionId = transaction.getSalesTransactionId();
        updateSalesTransactionHeaderByTransaction(transaction, userId);

        for (TransactionItemDetail transactionItemDetail : transaction
                .getTransactionItemDetailList()) {
            updateTransactionItemDetail(transactionItemDetail, salesTransactionId, userId);
        }

        for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
            updateSalesTransactionHeaderByNonItemDetailOut(nonItemDetail, salesTransactionId,
                    userId);
        }
    }

    /**
     * Update transaction item detail.
     * 
     * @param transactionItemDetail Transaction item detail.
     * @param salesTransactionId Sales transaction id.
     * @param userId User id.
     */
    private void updateTransactionItemDetail(TransactionItemDetail transactionItemDetail,
            String salesTransactionId, String userId) {
        updateTransactionItemDetailByTransactionItemDetail(transactionItemDetail,
                salesTransactionId, userId);

        for (NonItemDetail nonItemDetail : transactionItemDetail.getNonItemDetailListByItem()) {
            updateSalesTransactionHeaderByNonItemDetail(nonItemDetail, salesTransactionId, userId);
        }
    }

    /**
     * Add sales error evacuation.
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction error id.
     * @param detailCount Detail count.
     * @param transactionCount Transaction count.
     * @param originalSalesTransactionErrorId Original sales transaction error id.
     */
    private void addSalesErrorEvacuation(Transaction transaction, String userId,
            String salesTransactionErrorId, AtomicInteger detailCount,
            String originalSalesTransactionErrorId) {
        insertSalesErrorEvacuationTransactionHeader(transaction, userId, salesTransactionErrorId,
                originalSalesTransactionErrorId);

        AtomicInteger itemDetailCount = new AtomicInteger(0);
        for (TransactionItemDetail transactionItemDetail : transaction
                .getTransactionItemDetailList()) {
            addItemDetailIntoSalesErrorEvacuationTransactionDetail(transaction,
                    transactionItemDetail, userId, salesTransactionErrorId, detailCount,
                    itemDetailCount, originalSalesTransactionErrorId);
        }

        AtomicInteger nonItemDetailCount = new AtomicInteger(0);
        for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
            insertNonItemDetailIntoSalesErrorEvacuationTransactionNonItemDetail(transaction,
                    nonItemDetail, userId, salesTransactionErrorId, detailCount, nonItemDetailCount,
                    originalSalesTransactionErrorId);
        }
    }

    /**
     * Add item detail into sales error evacuation transaction detail.
     * 
     * @param transaction Transaction data.
     * @param transactionItemDetail Transaction item detail.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @param originalSalesTransactionErrorId Original sales transaction error id.
     */
    private void addItemDetailIntoSalesErrorEvacuationTransactionDetail(Transaction transaction,
            TransactionItemDetail transactionItemDetail, String userId,
            String salesTransactionErrorId, AtomicInteger detailCount,
            AtomicInteger itemDetailCount, String originalSalesTransactionErrorId) {
        insertItemDetailIntoSalesErrorEvacuationTransactionDetail(transaction,
                transactionItemDetail, userId, salesTransactionErrorId, detailCount,
                itemDetailCount, originalSalesTransactionErrorId);

        for (NonItemDetail nonItemDetail : transactionItemDetail.getNonItemDetailListByItem()) {
            insertNonItemDetailIntoSalesErrorEvacuationTransactionDetail(transaction, nonItemDetail,
                    userId, salesTransactionErrorId, null, detailCount, itemDetailCount,
                    originalSalesTransactionErrorId);
        }
    }

    /**
     * Insert sales error evacuation transaction header.
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param originalSalesTransactionErrorId Original sales transaction error id.
     */
    private void insertSalesErrorEvacuationTransactionHeader(Transaction transaction, String userId,
            String salesTransactionErrorId, String originalSalesTransactionErrorId) {
        ErrorEvacuationSalesTransactionHeader tsalesTransactionHeaderEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(transaction,
                                userId, salesTransactionErrorId, originalSalesTransactionErrorId);
        tevacuationHeaderEntityMapper.insertSelective(tsalesTransactionHeaderEntity);
    }

    /**
     * Insert non item detail into sales error evacuation transaction detail.
     * 
     * @param transaction Transaction data.
     * @param nonItemDetail Non item detail.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param salesTransactionType Sales transaction type.
     * @param detailCount detail count.
     * @param itemDetailCount Item detail count.
     * @param originalSalesTransactionErrorId Original sales transaction error id.
     */
    private void insertNonItemDetailIntoSalesErrorEvacuationTransactionDetail(
            Transaction transaction, NonItemDetail nonItemDetail, String userId,
            String salesTransactionErrorId, String salesTransactionType, AtomicInteger detailCount,
            AtomicInteger itemDetailCount, String originalSalesTransactionErrorId) {
        ErrorEvacuationSalesTransactionDetail errorEvacuationSalesTransactionDetailEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                                nonItemDetail, userId, transaction.getSalesTransactionId(),
                                transaction.getOrderSubNumber(), salesTransactionErrorId,
                                salesTransactionType, detailCount.incrementAndGet(),
                                itemDetailCount.get(), originalSalesTransactionErrorId);

        tevacuationDetailMapper.insertSelective(errorEvacuationSalesTransactionDetailEntity);
    }

    /**
     * Insert non item detail into sales error evacuation transaction detail.
     * 
     * @param transaction Transaction data.
     * @param nonItemDetail Non item detail.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @param originalSalesTransactionErrorId Original sales transaction error id.
     */
    private void insertNonItemDetailIntoSalesErrorEvacuationTransactionNonItemDetail(
            Transaction transaction, NonItemDetail nonItemDetail, String userId,
            String salesTransactionErrorId, AtomicInteger detailCount,
            AtomicInteger nonItemDetailCount, String originalSalesTransactionErrorId) {
        ErrorEvacuationSalesTransactionDetail errorEvacuationSalesTransactionDetailEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                                nonItemDetail, userId, transaction.getSalesTransactionId(),
                                transaction.getOrderSubNumber(), salesTransactionErrorId, null,
                                detailCount.incrementAndGet(), nonItemDetailCount.incrementAndGet(),
                                originalSalesTransactionErrorId);

        tevacuationDetailMapper.insertSelective(errorEvacuationSalesTransactionDetailEntity);
    }

    /**
     * Insert item detail into sales error evacuation transaction detail.
     * 
     * @param transaction Transaction data.
     * @param transactionItemDetail Transaction item detail.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param detailCount Detail count.
     * @param itemDetailCount Item detail count.
     * @param originalSalesTransactionErrorId Original sales transaction error id.
     */
    private void insertItemDetailIntoSalesErrorEvacuationTransactionDetail(Transaction transaction,
            TransactionItemDetail transactionItemDetail, String userId,
            String salesTransactionErrorId, AtomicInteger detailCount,
            AtomicInteger itemDetailCount, String originalSalesTransactionErrorId) {
        ErrorEvacuationSalesTransactionDetail errorEvacuationSalesTransactionDetailEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                                transactionItemDetail, userId, transaction.getSalesTransactionId(),
                                transaction.getOrderSubNumber(), salesTransactionErrorId,
                                originalSalesTransactionErrorId, detailCount.incrementAndGet(),
                                itemDetailCount.incrementAndGet());
        tevacuationDetailMapper.insertSelective(errorEvacuationSalesTransactionDetailEntity);
    }

    /**
     * Get order information by order integrated id.
     * 
     * @param transactionImportData Transaction import data
     * @return Sales order information selected by order integrated id.
     */
    private SalesOrderInformation getOrderInformationByOrderIntegratedId(
            TransactionImportData transactionImportData) {
        return tsalesOrderInformationMapper
                .selectByPrimaryKey(transactionImportData.getIntegratedOrderId());
    }

    /**
     * Update transaction item detail by transaction item detail.
     * 
     * @param transactionItemDetail Transaction item detail.
     * @param salesTransactionId Sales transaction id.
     * @param userId User id.
     */
    private void updateTransactionItemDetailByTransactionItemDetail(
            TransactionItemDetail transactionItemDetail, String salesTransactionId, String userId) {

        SalesTransactionDetailCondition tsalesTransactionDetailEntityCondition =
                new SalesTransactionDetailCondition();
        tsalesTransactionDetailEntityCondition.createCriteria()
                .andSalesTransactionIdLike(getSalesTransactionIdLikeSubstring(salesTransactionId))
                .andDetailSubNumberEqualTo(transactionItemDetail.getDetailSubNumber());

        SalesTransactionDetail tsalesTransactionDetailEntity =
                coreTableDataConverter.convertTSalesTransactionDetailEntityForUpdate(
                        transactionItemDetail, salesTransactionId, userId);

        tsalesTransactionDetailEntityMapper.updateByConditionSelective(
                tsalesTransactionDetailEntity, tsalesTransactionDetailEntityCondition);
    }

    /**
     * Update sales transaction header by transaction.
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     */
    private void updateSalesTransactionHeaderByTransaction(Transaction transaction, String userId) {

        SalesTransactionHeaderCondition tsalesTransactionHeaderEntityCondition =
                new SalesTransactionHeaderCondition();
        tsalesTransactionHeaderEntityCondition.createCriteria().andSalesTransactionIdLike(
                getSalesTransactionIdLikeSubstring(transaction.getSalesTransactionId()));
        SalesTransactionHeader tsalesTransactionHeaderEntity = coreTableDataConverter
                .convertTSalesTransactionHeaderEntityForUpdate(transaction, userId);

        tsalesTransactionHeaderEntityMapper.updateByConditionSelective(
                tsalesTransactionHeaderEntity, tsalesTransactionHeaderEntityCondition);
    }

    /**
     * Update sales transaction header by non item detail.
     * 
     * @param nonItemDetail Non item detail.
     * @param salesTransactionId Sales transaction id.
     * @param userId User id.
     */
    private void updateSalesTransactionHeaderByNonItemDetail(NonItemDetail nonItemDetail,
            String salesTransactionId, String userId) {
        SalesTransactionDetailCondition tsalesTransactionDetailEntityCondition =
                new SalesTransactionDetailCondition();
        tsalesTransactionDetailEntityCondition.createCriteria()
                .andSalesTransactionIdLike(getSalesTransactionIdLikeSubstring(salesTransactionId))
                .andDetailSubNumberEqualTo(nonItemDetail.getDetailSubNumber());
        SalesTransactionDetail tsalesTransactionDetailEntity =
                coreTableDataConverter.convertTSalesTransactionDetailOutsideEntityForUpdate(
                        nonItemDetail, salesTransactionId, userId);

        tsalesTransactionDetailEntityMapper.updateByConditionSelective(
                tsalesTransactionDetailEntity, tsalesTransactionDetailEntityCondition);
    }

    /**
     * Update sales transaction header by non item detail.
     * 
     * @param nonItemDetail Non item detail.
     * @param salesTransactionId Sales transaction id.
     * @param userId User id.
     */
    private void updateSalesTransactionHeaderByNonItemDetailOut(NonItemDetail nonItemDetail,
            String salesTransactionId, String userId) {
        SalesTransactionDetailCondition tsalesTransactionDetailEntityCondition =
                new SalesTransactionDetailCondition();
        tsalesTransactionDetailEntityCondition.createCriteria()
                .andSalesTransactionIdLike(getSalesTransactionIdLikeSubstring(salesTransactionId))
                .andDetailSubNumberEqualTo(nonItemDetail.getNonItemDetailNumber());
        SalesTransactionDetail tsalesTransactionDetailEntity =
                coreTableDataConverter.convertTSalesTransactionDetailOutsideEntityForUpdate(
                        nonItemDetail, salesTransactionId, userId);

        tsalesTransactionDetailEntityMapper.updateByConditionSelective(
                tsalesTransactionDetailEntity, tsalesTransactionDetailEntityCondition);
    }


    /**
     * Convert transactionImportData to salesTransactionErrorDetailEntity when error occurred on
     * updateType.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorId Transaction data error id.
     * @param userId User id.
     * @return salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    private SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUpdateType(
            TransactionImportData transactionImportData, String salesTransactionErrorId,
            String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);
        modelMapper.map(transactionImportData, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(salesTransactionErrorId);
        salesTransactionErrorDetailEntity.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1("integrated_order_id");
        salesTransactionErrorDetailEntity.setErrorItemValue1(transactionImportData.getIntegratedOrderId());
        return salesTransactionErrorDetailEntity;
    }

    /**
     * Get sales transaction id like the string from substring.
     * 
     * @param salesTransactionId Sales transaction id.
     * @return Sales transaction id like.
     */
    private String getSalesTransactionIdLikeSubstring(String salesTransactionId) {
        return salesTransactionId.substring(0, salesTransactionId.length() - 3) + "%";
    }
}
