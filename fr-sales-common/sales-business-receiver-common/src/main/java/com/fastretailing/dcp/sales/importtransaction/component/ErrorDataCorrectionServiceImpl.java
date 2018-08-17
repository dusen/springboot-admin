/**
 * @(#)ErrorDataCorrectionServiceImpl.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.common.type.UpdateType;
import com.fastretailing.dcp.sales.importtransaction.converter.CommonDataProcessor;
import com.fastretailing.dcp.sales.importtransaction.converter.HistoryTableDataConverter;
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
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistoryOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformationCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetailCondition;
import com.fastretailing.dcp.sales.importtransaction.exception.ErrorEvacuationException;
import com.fastretailing.dcp.sales.importtransaction.exception.InvalidDataException;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistoryOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionDetailInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionDiscountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTaxMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTenderInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTenderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTotalAmountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionErrorDetailMapper;

/**
 * Transaction correct service implements.
 * 
 */
@Service
public class ErrorDataCorrectionServiceImpl implements ErrorDataCorrectionService {

    /** Item id table id. */
    private static final String ITEM_ID_TABLE_ID = "table_name";

    /** Table t_sales_transaction_error_detail. */
    public static final String TABLE_NAME_T_SALES_TRANSACTION_ERROR_DETAIL =
            "t_sales_transaction_error_detail";

    /**
     * Component for operating DB operations on the history order information table.
     */
    @Autowired
    private AlterationHistoryOrderInformationMapper alterationHistoryOrderInformationEntityMapper;

    /**
     * Component for operating DB operations on the history sales transaction header table.
     */
    @Autowired
    private AlterationHistorySalesTransactionHeaderMapper alterationHistorySalesTransactionHeaderEntityMapper;

    /**
     * Component for operating DB operations on the history sales transaction detail table.
     */
    @Autowired
    private AlterationHistorySalesTransactionDetailMapper alterationHistorySalesTransactionDetailEntityMapper;

    /**
     * Component for operating DB operations on the history sales transaction detail info table.
     */
    @Autowired
    private AlterationHistorySalesTransactionDetailInfoMapper alterationHistorySalesTransactionDetailInfoEntityMapper;

    /**
     * Component for operating DB operations on the history sales transaction discount table.
     */
    @Autowired
    private AlterationHistorySalesTransactionDiscountMapper alterationHistorySalesTransactionDiscountEntityMapper;

    /**
     * Component for operating DB operations on the history sales transaction tax table.
     */
    @Autowired
    private AlterationHistorySalesTransactionTaxMapper alterationHistorySalesTransactionTaxEntityMapper;

    /**
     * Component for operating DB operations on the history sales transaction tender table.
     */
    @Autowired
    private AlterationHistorySalesTransactionTenderMapper alterationHistorySalesTransactionTenderEntityMapper;

    /** DB access parts. */
    @Autowired
    private AlterationHistorySalesTransactionTenderInfoMapper alterationHistorySalesTransactionTenderInfoEntityMapper;

    /**
     * Component for operating DB operations on the history sales transaction total amount table.
     */
    @Autowired
    private AlterationHistorySalesTransactionTotalAmountMapper alterationHistorySalesTransactionTotalAmountEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction error detail table.
     */
    @Autowired
    private SalesTransactionErrorDetailMapper salesTransactionErrorDetailEntityMapper;

    /**
     * The converter for history table.
     */
    @Autowired
    private HistoryTableDataConverter historyTableDataConverter;

    /** Common data processor parts. */
    @Autowired
    private CommonDataProcessor commonDataProcessor;

    /** Model tools parts. */
    @Autowired
    private ModelMapper modelMapper;

    /** Insert sales core table service parts. */
    @Autowired
    private InsertSalesCoreService insertSalesCoreTableService;

    /**
     * The Helper for data processor.
     */
    @Autowired
    private DataProcessorHelper dataProcessorHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void correctErrorData(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId) throws ErrorEvacuationException {

        SalesErrorSalesOrderInformationCondition salesErrorSalesOrderInformationEntityCondition =
                new SalesErrorSalesOrderInformationCondition();
        salesErrorSalesOrderInformationEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());

        SalesTransactionErrorDetailCondition salesTransactionErrorDetailCondition =
                new SalesTransactionErrorDetailCondition();
        salesTransactionErrorDetailCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId())
                .andDataAlterationStatusTypeEqualTo("0");
        SalesTransactionErrorDetail salesTransactionErrorDetail =
                salesTransactionErrorDetailEntityMapper
                        .selectByCondition(salesTransactionErrorDetailCondition)
                        .stream()
                        .findFirst()
                        .orElse(null);

        if (salesTransactionErrorDetail == null) {
            // throw new BusinessException.
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                    new SalesTransactionErrorDetail();
            convertTSalesTransactionErrorDetailEntityForInvalidData(transactionImportData,
                    salesTransactionErrorDetailEntity, salesTransactionErrorId, userId);
            throw new InvalidDataException(salesTransactionErrorDetailEntity);
        }
        correctSalesErrorOrderInformation(transactionImportData, salesTransactionErrorId, userId);
        updateSalesTransactionErrorDetail(userId, salesTransactionErrorDetail,
                salesTransactionErrorDetailCondition);
    }

    /**
     * Update table sales transaction error detail.
     * 
     * @param userId User id.
     * @param salesTransactionErrorDetail Sales transaction error detail.
     * @param salesTransactionErrorDetailCondition Sales transaction error detail example.
     */
    private void updateSalesTransactionErrorDetail(String userId,
            SalesTransactionErrorDetail salesTransactionErrorDetail,
            SalesTransactionErrorDetailCondition salesTransactionErrorDetailCondition) {
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForUpdate(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetail);
        salesTransactionErrorDetail.setDataAlterationStatusType("1");
        salesTransactionErrorDetailEntityMapper.updateByConditionSelective(
                salesTransactionErrorDetail, salesTransactionErrorDetailCondition);
    }

    /**
     * Updates alteration history, and corrects transaction information.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param userId User id.
     * @param salesErrorOrderInfo Sales error order info.
     * @throws Error evacuation exception.
     */
    private void correctSalesErrorOrderInformation(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId) throws ErrorEvacuationException {

        // Updates alteration history.
        updateAlterationHistory(transactionImportData, userId, salesTransactionErrorId);

        String importSalesTransactionErrorId = transactionImportData.getSalesTransactionErrorId();
        AtomicInteger transactionCount = new AtomicInteger(0);

        // Corrects the transaction error.
        // Loop for transaction data.
        if (CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())) {
            for (Transaction transaction : transactionImportData.getTransactionList()) {
                correctTransactionError(transaction, userId, importSalesTransactionErrorId,
                        salesTransactionErrorId, transactionCount, new AtomicInteger(0));
            }
        }

        dataProcessorHelper.insertByTransactionId(transactionImportData, userId);
        // Delete table by transaction id.
        if (!UpdateType.INSERT.is(transactionImportData.getUpdateType())
                || null == transactionImportData.getDataCorrectionEditingFlag()
                || !transactionImportData.getDataCorrectionEditingFlag()) {
            dataProcessorHelper.deleteTableByTransactionId(transactionImportData);
        }

        // Insert transaction import data.
        if (UpdateType.CORRECTION.is(transactionImportData.getUpdateType())) {
            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);
        }

    }

    /**
     * Updates alteration history.
     * 
     * @param transactionImportData Transaction import data.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction error id.
     */
    private void updateAlterationHistory(TransactionImportData transactionImportData, String userId,
            String salesTransactionErrorId) {
        AlterationHistoryOrderInformation alterationHistoryOrderInformationBeforeEntity = null;

        alterationHistoryOrderInformationBeforeEntity = historyTableDataConverter
                .convertTAlterationHistoryOrderInformationEntityForInsertAfterEdit(
                        transactionImportData, userId, salesTransactionErrorId,
                        transactionImportData.getSalesTransactionErrorId());
        alterationHistoryOrderInformationEntityMapper
                .insertSelective(alterationHistoryOrderInformationBeforeEntity);

    }

    /**
     * Main process for correcting transaction information.
     * 
     * @param transaction Transaction.
     * @param userId User id.
     * @param originalSalesTransactionErrorId Original sales transaction error id.
     * @param salesTransactionErrorId Transaction error id..
     * @param detailCount Detail number.
     */
    private void correctTransactionError(Transaction transaction, String userId,
            String originalSalesTransactionErrorId, String salesTransactionErrorId,
            AtomicInteger transactionCount, AtomicInteger detailCount) {

        AlterationHistorySalesTransactionHeader alterationHistorySalesTransactionHeaderEntity =
                null;

        alterationHistorySalesTransactionHeaderEntity = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionHeaderEntityForInsertAfterEdit(
                        transaction, userId, salesTransactionErrorId,
                        originalSalesTransactionErrorId, transactionCount.incrementAndGet());
        alterationHistorySalesTransactionHeaderEntityMapper
                .insertSelective(alterationHistorySalesTransactionHeaderEntity);

        // Item list.
        // Loop for item detail.
        if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
            AtomicInteger itemDetailCount = new AtomicInteger(0);
            for (TransactionItemDetail itemDetail : transaction.getTransactionItemDetailList()) {
                correctItemError(itemDetail, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        itemDetailCount, detailCount, originalSalesTransactionErrorId);
            }
        }

        // Non-item list.
        // Loop for non item detail.
        if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
            for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                correctNonItemError(nonItemDetail, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId, null,
                        new AtomicInteger(0), detailCount, originalSalesTransactionErrorId);
            }
        }

        // Purchase list.
        // Loop for pay detail.
        if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTenderList())) {
            AtomicInteger payDetailCount = new AtomicInteger(0);
            for (SalesTransactionTender payDetail : transaction.getSalesTransactionTenderList()) {
                correctPaymentError(payDetail, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        payDetailCount, originalSalesTransactionErrorId);
            }
        }

        // Tax list.
        // Loop for tax detail.
        if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTaxDetailList())) {
            AtomicInteger taxDetailCount = new AtomicInteger(0);
            for (SalesTransactionTaxDetail transactionTax : transaction
                    .getSalesTransactionTaxDetailList()) {
                correctTaxError(transactionTax, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        new AtomicInteger(0), taxDetailCount, originalSalesTransactionErrorId);
            }
        }

        // Total amount list.
        // Loop for total list.
        if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTotalList())) {
            AtomicInteger totalCount = new AtomicInteger(0);
            for (SalesTransactionTotal transactionTotal : transaction
                    .getSalesTransactionTotalList()) {
                correctTotalAmountError(transactionTotal, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        totalCount, originalSalesTransactionErrorId);
            }
        }
    }

    /**
     * Corrects item information for transaction, and updates history.
     * 
     * @param transactionItem Transaction item.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param itemDetailCount Item detail number.
     * @param detailCount Detail number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     */
    private void correctItemError(TransactionItemDetail transactionItem, String salesTransactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger itemDetailCount, AtomicInteger detailCount,
            String originalSalesTransactionErrorId) {

        detailCount.incrementAndGet();

        AlterationHistorySalesTransactionDetail alterationHistorySalesTransactionDetailEntity =
                null;

        alterationHistorySalesTransactionDetailEntity = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionDetailEntityForInsertAfterEdit(
                        transactionItem, userId, salesTransactionId, orderSubNumber,
                        salesTransactionErrorId, originalSalesTransactionErrorId, detailCount.get(),
                        itemDetailCount.incrementAndGet());
        alterationHistorySalesTransactionDetailEntityMapper
                .insertSelective(alterationHistorySalesTransactionDetailEntity);

        // Non-item detail.
        // Loop for non item detail to item detail.
        if (transactionItem.getNonItemDetailListByItem() != null) {
            for (NonItemDetail nonItemDetail : transactionItem.getNonItemDetailListByItem()) {
                correctNonItemError(nonItemDetail, salesTransactionId, orderSubNumber, userId,
                        salesTransactionErrorId,
                        transactionItem.getDetailListSalesTransactionType(), itemDetailCount,
                        detailCount, originalSalesTransactionErrorId);
            }
        }

        // Correct discount error for item.
        // Loop for discount list.
        if (CollectionUtils.isNotEmpty(transactionItem.getItemDiscountList())) {
            AtomicInteger itemDiscountCount = new AtomicInteger(0);
            for (ItemDiscount itemDiscount : transactionItem.getItemDiscountList()) {
                correctItemDiscountError(itemDiscount, salesTransactionId, orderSubNumber, userId,
                        salesTransactionErrorId, itemDiscountCount, detailCount,
                        originalSalesTransactionErrorId);
            }
        }

        // Correct tax detail error for item.
        // Loop for tax detail list.
        if (CollectionUtils.isNotEmpty(transactionItem.getItemTaxDetailList())) {
            AtomicInteger taxDetailCount = new AtomicInteger(0);
            for (ItemTaxDetail itemTaxDetail : transactionItem.getItemTaxDetailList()) {
                correctItemTaxError(itemTaxDetail, salesTransactionId, orderSubNumber, userId,
                        salesTransactionErrorId, taxDetailCount, detailCount,
                        originalSalesTransactionErrorId);
            }
        }

    }

    /**
     * Corrects discount information for item, and updates history.
     * 
     * @param itemDiscount Item discount.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param itemDiscountCount Item Discount number.
     * @param detailCount Detail number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     */
    private void correctItemDiscountError(ItemDiscount itemDiscount, String salesTransactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger itemDiscountCount, AtomicInteger detailCount,
            String originalSalesTransactionErrorId) {
        AlterationHistorySalesTransactionDiscount alterationHistorySalesTransactionDiscountEntity =
                null;

        alterationHistorySalesTransactionDiscountEntity = historyTableDataConverter
                .converttAlterationHistorySalesTransactionDiscountEntityForInsertAfterEdit(
                        itemDiscount, userId, salesTransactionId, orderSubNumber,
                        salesTransactionErrorId, originalSalesTransactionErrorId, detailCount.get(),
                        itemDiscountCount.incrementAndGet());
        alterationHistorySalesTransactionDiscountEntityMapper
                .insertSelective(alterationHistorySalesTransactionDiscountEntity);
    }

    /**
     * Corrects tax information for item, and updates history.
     * 
     * @param itemTax Item Tax.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param itemTaxCount Item Tax number.
     * @param detailCount Detail number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     */
    private void correctItemTaxError(ItemTaxDetail itemTax, String salesTransactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger itemTaxCount, AtomicInteger detailCount,
            String originalSalesTransactionErrorId) {
        AlterationHistorySalesTransactionTax alterationHistorySalesTransactionTaxEntity = null;

        alterationHistorySalesTransactionTaxEntity = historyTableDataConverter
                .converttAlterationHistorySalesTransactionTaxEntityForInsertAfterEdit(itemTax,
                        userId, salesTransactionId, orderSubNumber, salesTransactionErrorId,
                        originalSalesTransactionErrorId, detailCount.get(),
                        itemTaxCount.incrementAndGet());
        alterationHistorySalesTransactionTaxEntityMapper
                .insertSelective(alterationHistorySalesTransactionTaxEntity);

    }

    /**
     * Corrects non item information, and updates history.
     * 
     * @param nonItem Non item.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param salesTransactionType Sales transaction type.
     * @param itemDetailCount Item detail number.
     * @param detailCount Detail number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     */
    private void correctNonItemError(NonItemDetail nonItem, String salesTransactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            String salesTransactionType, AtomicInteger itemDetailCount, AtomicInteger detailCount,
            String originalSalesTransactionErrorId) {

        detailCount.incrementAndGet();

        AlterationHistorySalesTransactionDetail alterationHistorySalesTransactionDetailEntity =
                null;

        alterationHistorySalesTransactionDetailEntity = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionDetailEntityForInsertOutsideAfterEdit(
                        nonItem, userId, salesTransactionId, orderSubNumber,
                        salesTransactionErrorId, originalSalesTransactionErrorId,
                        salesTransactionType, detailCount.get(), itemDetailCount.get());
        alterationHistorySalesTransactionDetailEntityMapper
                .insertSelective(alterationHistorySalesTransactionDetailEntity);

        // Correct non-item info error.
        if (nonItem.getNonItemInfo() != null) {
            correctNonItemInfoError(nonItem.getNonItemInfo(), salesTransactionId, orderSubNumber,
                    userId, salesTransactionErrorId, itemDetailCount, detailCount,
                    originalSalesTransactionErrorId);
        }

        // Correct discount error for non-item.
        // Loop for item discount list.
        if (CollectionUtils.isNotEmpty(nonItem.getNonItemDiscountDetailList())) {
            AtomicInteger itemDiscountCount = new AtomicInteger(0);
            for (NonItemDiscountDetail nonItemDiscountDetail : nonItem
                    .getNonItemDiscountDetailList()) {
                correctNonItemDiscountError(nonItemDiscountDetail, salesTransactionId,
                        orderSubNumber, userId, salesTransactionErrorId, itemDiscountCount,
                        detailCount, originalSalesTransactionErrorId);
            }
        }

        // Correct tax detail error for non-item.
        // Loop for item tax detail list.
        if (CollectionUtils.isNotEmpty(nonItem.getNonItemTaxDetailList())) {
            AtomicInteger itemTaxCount = new AtomicInteger(0);
            for (NonItemTaxDetail nonItemTaxDetail : nonItem.getNonItemTaxDetailList()) {
                correctNonItemTaxError(nonItemTaxDetail, salesTransactionId, orderSubNumber, userId,
                        salesTransactionErrorId, itemTaxCount, detailCount,
                        originalSalesTransactionErrorId);
            }
        }
    }

    /**
     * Correct non item information error, and updates history.
     * 
     * @param nonItemInfo Non item info.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param itemDetailCount Item detail number.
     * @param detailCount Detail number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     */
    private void correctNonItemInfoError(NonItemInfo nonItemInfo, String salesTransactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger itemDetailCount, AtomicInteger detailCount,
            String originalSalesTransactionErrorId) {
        AlterationHistorySalesTransactionDetailInfo alterationHistorySalesTransactionDetailInfoEntity =
                null;

        alterationHistorySalesTransactionDetailInfoEntity = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionDetailInfoEntityForInsertAfterEdit(
                        nonItemInfo, userId, salesTransactionId, orderSubNumber,
                        salesTransactionErrorId, originalSalesTransactionErrorId, detailCount.get(),
                        itemDetailCount.get());
        alterationHistorySalesTransactionDetailInfoEntityMapper
                .insertSelective(alterationHistorySalesTransactionDetailInfoEntity);
    }

    /**
     * Corrects discount information for non-item, and updates history.
     * 
     * @param nonItemDiscount Non item discount.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param itemDiscountCount Item discount number.
     * @param detailCount Detail number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     */
    private void correctNonItemDiscountError(NonItemDiscountDetail nonItemDiscount,
            String salesTransactionId, int orderSubNumber, String userId,
            String salesTransactionErrorId, AtomicInteger itemDiscountCount,
            AtomicInteger detailCount, String originalSalesTransactionErrorId) {

        AlterationHistorySalesTransactionDiscount alterationHistorySalesTransactionDiscountEntity =
                null;

        alterationHistorySalesTransactionDiscountEntity = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionDiscountEntityForInsertNonAfterEdit(
                        nonItemDiscount, userId, salesTransactionId, orderSubNumber,
                        salesTransactionErrorId, originalSalesTransactionErrorId, detailCount.get(),
                        itemDiscountCount.incrementAndGet());
        alterationHistorySalesTransactionDiscountEntityMapper
                .insertSelective(alterationHistorySalesTransactionDiscountEntity);

    }

    /**
     * Corrects tax information for non-item, and updates history.
     * 
     * @param nonItemTax Non item tax.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param detailSubNumber Detail sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param itemTaxCount Item tax number.
     * @param detailCount Detail number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     */
    private void correctNonItemTaxError(NonItemTaxDetail nonItemTax, String salesTransactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger itemTaxCount, AtomicInteger detailCount,
            String originalSalesTransactionErrorId) {

        AlterationHistorySalesTransactionTax alterationHistorySalesTransactionTaxEntity = null;

        alterationHistorySalesTransactionTaxEntity = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionTaxEntityForInsertNonAfterEdit(nonItemTax,
                        userId, salesTransactionId, orderSubNumber, salesTransactionErrorId,
                        originalSalesTransactionErrorId, detailCount.get(),
                        itemTaxCount.incrementAndGet());
        alterationHistorySalesTransactionTaxEntityMapper
                .insertSelective(alterationHistorySalesTransactionTaxEntity);

    }

    /**
     * Corrects payment information for transaction, and updates history.
     * 
     * @param transactionPayment Transaction payment.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param payDetailCount Pay detail number.
     * @param originalSalesTransactionErrorId Original transaction error id.
     */
    private void correctPaymentError(SalesTransactionTender transactionPayment,
            String salesTransactionId, int orderSubNumber, String userId,
            String salesTransactionErrorId, AtomicInteger payDetailCount,
            String originalSalesTransactionErrorId) {
        AlterationHistorySalesTransactionTender alterationHistorySalesTransactionTenderEntity =
                null;

        alterationHistorySalesTransactionTenderEntity = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionTenderEntityForInsertAfterEdit(
                        transactionPayment, userId, salesTransactionId, orderSubNumber,
                        salesTransactionErrorId, originalSalesTransactionErrorId,
                        payDetailCount.incrementAndGet());
        alterationHistorySalesTransactionTenderEntityMapper
                .insertSelective(alterationHistorySalesTransactionTenderEntity);

        // Correct tender info error for tender info list.
        // Loop for tender info list.
        if (transactionPayment.getTenderInfoList() != null) {
            correctPaymentDetailError(transactionPayment.getTenderInfoList(), salesTransactionId,
                    orderSubNumber, userId, salesTransactionErrorId,
                    originalSalesTransactionErrorId, transactionPayment.getTenderId(),
                    transactionPayment.getTenderGroupId(), payDetailCount.get());
        }

    }

    /**
     * Corrects payment detail information for transaction, and updates history.
     * 
     * @param paymentDetail Payment detail.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param originalSalesTransactionErrorId Original transaction error id.
     * @param tenderId Tender id.
     * @param tenderGroupId Tender group id.
     * @param tenderSubNumber Tender sub number.
     */
    private void correctPaymentDetailError(TenderInfo paymentDetail, String salesTransactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            String originalSalesTransactionErrorId, String tenderId, String tenderGroupId,
            int tenderSubNumber) {
        AlterationHistorySalesTransactionTenderInfo alterationHistorySalesTransactionTenderInfoEntity =
                historyTableDataConverter
                        .convertTAlterationHistorySalesTransactionTenderInfoEntityForInsertAfterEdit(
                                paymentDetail, userId, salesTransactionId, orderSubNumber,
                                salesTransactionErrorId, originalSalesTransactionErrorId, tenderId,
                                tenderGroupId, tenderSubNumber);
        alterationHistorySalesTransactionTenderInfoEntityMapper
                .insertSelective(alterationHistorySalesTransactionTenderInfoEntity);
    }


    /**
     * Corrects tax information for transaction, and updates history.
     * 
     * @param transactionTax Transaction Tax.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param detailCount Detail number.
     * @param taxDetailCount Tax detail number.
     * @param originalSalesTransactionErrorId original transaction error id.
     */
    private void correctTaxError(SalesTransactionTaxDetail transactionTax,
            String salesTransactionId, int orderSubNumber, String userId,
            String salesTransactionErrorId, AtomicInteger detailCount, AtomicInteger taxDetailCount,
            String originalSalesTransactionErrorId) {
        AlterationHistorySalesTransactionTax alterationHistorySalesTransactionTaxEntity = null;

        alterationHistorySalesTransactionTaxEntity = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionTaxEntityForInsertTransactionAfterEdit(
                        transactionTax, userId, salesTransactionId, orderSubNumber,
                        salesTransactionErrorId, originalSalesTransactionErrorId, detailCount.get(),
                        taxDetailCount.incrementAndGet());
        alterationHistorySalesTransactionTaxEntityMapper
                .insertSelective(alterationHistorySalesTransactionTaxEntity);

    }

    /**
     * Correct total amount error, and updates history.
     * 
     * @param transactionTotal Transaction total.
     * @param salesTransactionId Sales transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User Id.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param totalCount Total number.
     * @param originalSalesTransactionErrorId Origianl transaction error id.
     */
    private void correctTotalAmountError(SalesTransactionTotal transactionTotal,
            String salesTransactionId, int orderSubNumber, String userId,
            String salesTransactionErrorId, AtomicInteger totalCount,
            String originalSalesTransactionErrorId) {
        AlterationHistorySalesTransactionTotalAmount alterationHistorySalesTransactionTotalAmountEntity =
                null;
        alterationHistorySalesTransactionTotalAmountEntity = historyTableDataConverter
                .convertTAlterationHistorySalesTransactionTotalAmountEntityForInsertAfterEdit(
                        transactionTotal, userId, salesTransactionId, orderSubNumber,
                        salesTransactionErrorId, originalSalesTransactionErrorId,
                        totalCount.incrementAndGet());
        alterationHistorySalesTransactionTotalAmountEntityMapper
                .insertSelective(alterationHistorySalesTransactionTotalAmountEntity);
    }


    /**
     * Convert transactionImportData to salesTransactionErrorDetailEntity when error occurred on
     * invalid data.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @param salesTransactionErrorId Transaction data error id.
     * @param userId User id.
     */
    private void convertTSalesTransactionErrorDetailEntityForInvalidData(
            TransactionImportData transactionImportData,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            String salesTransactionErrorId, String userId) {
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);
        modelMapper.map(transactionImportData, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(salesTransactionErrorId);
        salesTransactionErrorDetailEntity.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ITEM_ID_TABLE_ID);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(TABLE_NAME_T_SALES_TRANSACTION_ERROR_DETAIL);
    }

}
