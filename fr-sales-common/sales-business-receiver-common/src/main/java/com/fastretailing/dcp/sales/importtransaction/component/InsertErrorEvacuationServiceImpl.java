/**
 * @(#)InsertErrorEvacuationServiceImpl.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.sales.importtransaction.converter.ErrorEvacuationTableDataConverter;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemDiscount;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDiscountDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTotal;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDetailInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDiscountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTaxMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTenderInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTenderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTotalAmountMapper;

/**
 * The class is used to insert data to error evacuation table.
 *
 */
@Service
public class InsertErrorEvacuationServiceImpl implements InsertErrorEvacuationService {

    /**
     * Component for error evacuation table data converter.
     */
    @Autowired
    private ErrorEvacuationTableDataConverter errorEvacuationTableDataConverter;

    /**
     * Component for operating DB operations on the error evacuation sales order information table.
     */
    @Autowired
    private ErrorEvacuationSalesOrderInformationMapper errorEvacuationSalesOrderInformationEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction header table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionHeaderMapper errorEvacuationSalesTransactionHeaderEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction detail table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionDetailMapper errorEvacuationSalesTransactionDetailEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction detail info
     * table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionDetailInfoMapper errorEvacuationSalesTransactionDetailInfoEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction discount
     * table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionDiscountMapper errorEvacuationSalesTransactionDiscountEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction tax table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionTaxMapper errorEvacuationSalesTransactionTaxEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction tender table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionTenderMapper errorEvacuationSalesTransactionTenderEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction tender info
     * table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionTenderInfoMapper errorEvacuationSalesTransactionTenderInfoEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction total amount
     * table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionTotalAmountMapper errorEvacuationSalesTransactionTotalAmountEntityMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertErrorEvacuationTable(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId) {

        String originalTransactionErrorId = transactionImportData.getSalesTransactionErrorId();

        // Insert error order info .
        addErrorEvacuationOrderInformation(transactionImportData, salesTransactionErrorId, userId);

        // Loop for transaction data.
        if (CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())) {
            for (Transaction transaction : transactionImportData.getTransactionList()) {
                addTransaction(transaction, userId, salesTransactionErrorId,
                        originalTransactionErrorId, new AtomicInteger(0));
            }
        }

    }

    /**
     * Add error order information.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorId Sales transaction error id.
     * @param userId User id.
     */
    private void addErrorEvacuationOrderInformation(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId) {
        ErrorEvacuationSalesOrderInformation errorEvacuationSalesOrderInformationEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesOrderInformationEntityForInsert(
                                transactionImportData, userId, salesTransactionErrorId);
        errorEvacuationSalesOrderInformationEntityMapper
                .insertSelective(errorEvacuationSalesOrderInformationEntity);
    }

    /**
     * Add transaction data.
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param originalTransactionErrorId Original transaction error id.
     * @param detailCount Detail number.
     */
    private void addTransaction(Transaction transaction, String userId,
            String salesTransactionErrorId, String originalTransactionErrorId,
            AtomicInteger detailCount) {
        ErrorEvacuationSalesTransactionHeader errorEvacuationSalesTransactionHeaderEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(transaction,
                                userId, salesTransactionErrorId, originalTransactionErrorId);
        errorEvacuationSalesTransactionHeaderEntityMapper
                .insertSelective(errorEvacuationSalesTransactionHeaderEntity);

        // Loop for item detail.
        if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
            AtomicInteger itemDetailCount = new AtomicInteger(0);
            for (TransactionItemDetail itemDetail : transaction.getTransactionItemDetailList()) {
                addTransactionItemDetail(itemDetail, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        itemDetailCount, detailCount, originalTransactionErrorId);
            }
        }

        // Loop for non item detail.
        if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
            for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                addNonItemDetail(nonItemDetail, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId, null,
                        new AtomicInteger(0), detailCount, originalTransactionErrorId);
            }
        }

        // Loop for pay detail.
        if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTenderList())) {
            AtomicInteger payDetailCount = new AtomicInteger(0);
            for (SalesTransactionTender payDetail : transaction.getSalesTransactionTenderList()) {
                addSalesTransactionTender(payDetail, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        payDetailCount, originalTransactionErrorId);
            }
        }

        // Loop for tax detail.
        if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTaxDetailList())) {
            AtomicInteger taxDetailCount = new AtomicInteger(0);
            for (SalesTransactionTaxDetail taxDetail : transaction
                    .getSalesTransactionTaxDetailList()) {
                addSalesTransactionTaxDetail(taxDetail, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        new AtomicInteger(0), taxDetailCount, originalTransactionErrorId);
            }
        }

        // Loop for total list.
        if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTotalList())) {
            AtomicInteger totalCount = new AtomicInteger(0);
            for (SalesTransactionTotal transactionTotal : transaction
                    .getSalesTransactionTotalList()) {
                addSalesTransactionTotal(transactionTotal, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        totalCount, originalTransactionErrorId);
            }
        }
    }

    /**
     * Add transaction item detail.
     * 
     * @param transactionItemDetail transaction item detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param itemDetailCount Item detail count.
     * @param detailCount Detail count.
     * @param originalTransactionErrorId Original transaction error id.
     */
    private void addTransactionItemDetail(TransactionItemDetail transactionItemDetail,
            String transactionId, int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger itemDetailCount, AtomicInteger detailCount,
            String originalTransactionErrorId) {

        ErrorEvacuationSalesTransactionDetail errorEvacuationSalesTransactionDetailEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                                transactionItemDetail, userId, transactionId, orderSubNumber,
                                salesTransactionErrorId, originalTransactionErrorId,
                                detailCount.incrementAndGet(), itemDetailCount.incrementAndGet());
        errorEvacuationSalesTransactionDetailEntityMapper
                .insertSelective(errorEvacuationSalesTransactionDetailEntity);

        // Loop for non item detail to item detail.
        if (transactionItemDetail.getNonItemDetailListByItem() != null) {
            for (NonItemDetail nonItemDetail : transactionItemDetail.getNonItemDetailListByItem()) {
                addItemDetail(nonItemDetail, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId,
                        transactionItemDetail.getDetailListSalesTransactionType(), itemDetailCount,
                        detailCount, originalTransactionErrorId);
            }
        }

        // Loop for discount list.
        if (CollectionUtils.isNotEmpty(transactionItemDetail.getItemDiscountList())) {
            for (ItemDiscount itemDiscount : transactionItemDetail.getItemDiscountList()) {
                AtomicInteger itemDiscountCount = new AtomicInteger(0);
                addItemDiscount(itemDiscount, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId, detailCount, itemDiscountCount,
                        originalTransactionErrorId);
            }
        }

        // Loop for tax detail list.
        if (CollectionUtils.isNotEmpty(transactionItemDetail.getItemTaxDetailList())) {
            for (ItemTaxDetail itemTaxDetail : transactionItemDetail.getItemTaxDetailList()) {
                AtomicInteger taxDetailCount = new AtomicInteger(0);
                addItemTaxDetail(itemTaxDetail, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId, detailCount, taxDetailCount,
                        originalTransactionErrorId);
            }
        }
    }

    /**
     * Add non item detail.
     * 
     * @param nonItemDetail non item detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param salesTransactionType Sales transaction type.
     * @param nonItemDetailCount Non itam detail count.
     * @param detailCount Detail count.
     * @param originalTransactionErrorId Original transaction error id.
     */
    private void addNonItemDetail(NonItemDetail nonItemDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            String salesTransactionType, AtomicInteger nonItemDetailCount,
            AtomicInteger detailCount, String originalTransactionErrorId) {
        ErrorEvacuationSalesTransactionDetail errorEvacuationSalesTransactionDetailEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                                nonItemDetail, userId, transactionId, orderSubNumber,
                                salesTransactionErrorId, salesTransactionType,
                                detailCount.incrementAndGet(), nonItemDetailCount.get(),
                                originalTransactionErrorId);
        errorEvacuationSalesTransactionDetailEntityMapper
                .insertSelective(errorEvacuationSalesTransactionDetailEntity);


        if (nonItemDetail.getNonItemInfo() != null) {
            ErrorEvacuationSalesTransactionDetailInfo errorEvacuationSalesTransactionDetailInfoEntity =
                    errorEvacuationTableDataConverter
                            .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                                    nonItemDetail.getNonItemInfo(), userId, transactionId,
                                    orderSubNumber, salesTransactionErrorId, detailCount.get(),
                                    nonItemDetailCount.get(), originalTransactionErrorId);
            errorEvacuationSalesTransactionDetailInfoEntityMapper
                    .insertSelective(errorEvacuationSalesTransactionDetailInfoEntity);
        }

        // Loop for non item discount detail list.
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemDiscountDetailList())) {
            AtomicInteger itemDiscountCount = new AtomicInteger(0);
            for (NonItemDiscountDetail nonItemDiscountDetail : nonItemDetail
                    .getNonItemDiscountDetailList()) {
                addNonItemDiscountDetail(nonItemDiscountDetail, transactionId, orderSubNumber,
                        userId, salesTransactionErrorId, detailCount, itemDiscountCount,
                        originalTransactionErrorId);
            }
        }

        // Loop for non item tax detail list.
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemTaxDetailList())) {
            AtomicInteger itemTaxCount = new AtomicInteger(0);
            for (NonItemTaxDetail nonItemTaxDetail : nonItemDetail.getNonItemTaxDetailList()) {
                addNonItemTaxDetail(nonItemTaxDetail, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId, detailCount, itemTaxCount,
                        originalTransactionErrorId);
            }
        }

    }

    /**
     * Add pay detail of the transaction.
     * 
     * @param payDetail Pay detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param payDetailCount Pay detail count.
     * @param originalTransactionErrorId Original transaction error id.
     */
    private void addSalesTransactionTender(SalesTransactionTender payDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger payDetailCount, String originalTransactionErrorId) {
        ErrorEvacuationSalesTransactionTender errorEvacuationSalesTransactionTenderEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionTenderEntityForInsert(payDetail,
                                userId, transactionId, orderSubNumber, salesTransactionErrorId,
                                payDetailCount.incrementAndGet(), originalTransactionErrorId);
        errorEvacuationSalesTransactionTenderEntityMapper
                .insertSelective(errorEvacuationSalesTransactionTenderEntity);

        if (payDetail.getTenderInfoList() != null) {
            ErrorEvacuationSalesTransactionTenderInfo errorEvacuationSalesTransactionTenderInfoEntity =
                    errorEvacuationTableDataConverter
                            .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                                    payDetail.getTenderInfoList(), userId, transactionId,
                                    orderSubNumber, payDetail.getTenderGroupId(),
                                    payDetail.getTenderId(), salesTransactionErrorId,
                                    originalTransactionErrorId, payDetailCount.get());
            errorEvacuationSalesTransactionTenderInfoEntityMapper
                    .insertSelective(errorEvacuationSalesTransactionTenderInfoEntity);
        }

    }

    /**
     * Add sales transaction tax detail.
     * 
     * @param salesTransactionTaxDetail Sales transaction tax detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param taxDetailCount Tax detail count.
     * @param originalTransactionErrorId Original transaction error id.
     */
    private void addSalesTransactionTaxDetail(SalesTransactionTaxDetail salesTransactionTaxDetail,
            String transactionId, int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger detailCount, AtomicInteger taxDetailCount,
            String originalTransactionErrorId) {
        ErrorEvacuationSalesTransactionTax errorEvacuationSalesTransactionTaxEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                                salesTransactionTaxDetail, userId, transactionId, orderSubNumber,
                                salesTransactionErrorId, detailCount.get(),
                                taxDetailCount.incrementAndGet(), originalTransactionErrorId);
        errorEvacuationSalesTransactionTaxEntityMapper
                .insertSelective(errorEvacuationSalesTransactionTaxEntity);
    }

    /**
     * Add sales transaction total.
     * 
     * @param salesTransactionTotal sales transaction total.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param totalCount Total count.
     * @param originalTransactionErrorId Original transaction error id.
     */
    private void addSalesTransactionTotal(SalesTransactionTotal salesTransactionTotal,
            String transactionId, int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger totalCount, String originalTransactionErrorId) {
        ErrorEvacuationSalesTransactionTotalAmount errorEvacuationSalesTransactionTotalAmountEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                                salesTransactionTotal, userId, transactionId, orderSubNumber,
                                salesTransactionErrorId, totalCount.incrementAndGet(),
                                originalTransactionErrorId);
        errorEvacuationSalesTransactionTotalAmountEntityMapper
                .insertSelective(errorEvacuationSalesTransactionTotalAmountEntity);
    }

    /**
     * Add item detail.
     * 
     * @param nonItemDetail item detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param salesTransactionType Sales transaction type.
     * @param itemDetailCount Item detail count.
     * @param detailCount Detail count.
     * @param originalTransactionErrorId Original transaction error id.
     */
    private void addItemDetail(NonItemDetail nonItemDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            String salesTransactionType, AtomicInteger itemDetailCount, AtomicInteger detailCount,
            String originalTransactionErrorId) {

        ErrorEvacuationSalesTransactionDetail errorEvacuationSalesTransactionDetailEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                                nonItemDetail, userId, transactionId, orderSubNumber,
                                salesTransactionErrorId, salesTransactionType,
                                detailCount.incrementAndGet(), itemDetailCount.get(),
                                originalTransactionErrorId);
        errorEvacuationSalesTransactionDetailEntityMapper
                .insertSelective(errorEvacuationSalesTransactionDetailEntity);

        if (nonItemDetail.getNonItemInfo() != null) {
            ErrorEvacuationSalesTransactionDetailInfo errorEvacuationSalesTransactionDetailInfoEntity =
                    errorEvacuationTableDataConverter
                            .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                                    nonItemDetail.getNonItemInfo(), userId, transactionId,
                                    orderSubNumber, salesTransactionErrorId, detailCount.get(),
                                    itemDetailCount.get(), originalTransactionErrorId);
            errorEvacuationSalesTransactionDetailInfoEntityMapper
                    .insertSelective(errorEvacuationSalesTransactionDetailInfoEntity);

        }

        // Loop for item discount list.
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemDiscountDetailList())) {
            AtomicInteger itemDiscountCount = new AtomicInteger(0);
            for (NonItemDiscountDetail nonItemDiscountDetail : nonItemDetail
                    .getNonItemDiscountDetailList()) {
                ErrorEvacuationSalesTransactionDiscount errorEvacuationSalesTransactionDiscountEntity =
                        errorEvacuationTableDataConverter
                                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                                        nonItemDiscountDetail, userId, transactionId,
                                        orderSubNumber, salesTransactionErrorId, detailCount.get(),
                                        itemDiscountCount.incrementAndGet(),
                                        originalTransactionErrorId);
                errorEvacuationSalesTransactionDiscountEntityMapper
                        .insertSelective(errorEvacuationSalesTransactionDiscountEntity);

            }
        }

        // Loop for item tax detail list.
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemTaxDetailList())) {
            AtomicInteger itemTaxCount = new AtomicInteger(0);
            for (NonItemTaxDetail nonItemTaxDetail : nonItemDetail.getNonItemTaxDetailList()) {
                ErrorEvacuationSalesTransactionTax errorEvacuationSalesTransactionTaxEntity =
                        errorEvacuationTableDataConverter
                                .convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                                        nonItemTaxDetail, userId, transactionId, orderSubNumber,
                                        salesTransactionErrorId, detailCount.get(),
                                        itemTaxCount.incrementAndGet(), originalTransactionErrorId);
                errorEvacuationSalesTransactionTaxEntityMapper
                        .insertSelective(errorEvacuationSalesTransactionTaxEntity);

            }
        }

    }

    /**
     * Add item discount.
     * 
     * @param itemDiscount item discount.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemDiscountCount Item discount count.
     * @param originalTransactionErrorId Original transaction error id.
     */
    private void addItemDiscount(ItemDiscount itemDiscount, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger detailCount, AtomicInteger itemDiscountCount,
            String originalTransactionErrorId) {
        ErrorEvacuationSalesTransactionDiscount errorEvacuationSalesTransactionDiscountEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                                itemDiscount, userId, transactionId, orderSubNumber,
                                salesTransactionErrorId, detailCount.get(),
                                itemDiscountCount.incrementAndGet(), originalTransactionErrorId);
        errorEvacuationSalesTransactionDiscountEntityMapper
                .insertSelective(errorEvacuationSalesTransactionDiscountEntity);
    }

    /**
     * Add item tax detail.
     * 
     * @param itemTaxDetail item tax detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param taxDetailCount Tax detail count.
     * @param originalTransactionErrorId Original transaction error id.
     */
    private void addItemTaxDetail(ItemTaxDetail itemTaxDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger detailCount, AtomicInteger taxDetailCount,
            String originalTransactionErrorId) {
        ErrorEvacuationSalesTransactionTax errorEvacuationSalesTransactionTaxEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionTaxEntityForInsert(itemTaxDetail,
                                userId, transactionId, orderSubNumber, salesTransactionErrorId,
                                detailCount.get(), taxDetailCount.incrementAndGet(),
                                originalTransactionErrorId);
        errorEvacuationSalesTransactionTaxEntityMapper
                .insertSelective(errorEvacuationSalesTransactionTaxEntity);

    }

    /**
     * Add non item discount detail.
     * 
     * @param nonItemDiscountDetail Non item discount detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount DetailCount.
     * @param itemDiscountCount Item discount count.
     * @param originalTransactionErrorId Orignal transaction error id.
     */
    private void addNonItemDiscountDetail(NonItemDiscountDetail nonItemDiscountDetail,
            String transactionId, int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger detailCount, AtomicInteger itemDiscountCount,
            String originalTransactionErrorId) {
        ErrorEvacuationSalesTransactionDiscount errorEvacuationSalesTransactionDiscountEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                                nonItemDiscountDetail, userId, transactionId, orderSubNumber,
                                salesTransactionErrorId, detailCount.get(),
                                itemDiscountCount.incrementAndGet(), originalTransactionErrorId);
        errorEvacuationSalesTransactionDiscountEntityMapper
                .insertSelective(errorEvacuationSalesTransactionDiscountEntity);
    }

    /**
     * Add non item tax detail.
     * 
     * @param nonItemTaxDetail non item tax detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount DetailCount.
     * @param itemTaxCount Item tax count.
     * @param originalTransactionErrorId Original transaction error id.
     */
    private void addNonItemTaxDetail(NonItemTaxDetail nonItemTaxDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger detailCount, AtomicInteger itemTaxCount,
            String originalTransactionErrorId) {
        ErrorEvacuationSalesTransactionTax errorEvacuationSalesTransactionTaxEntity =
                errorEvacuationTableDataConverter
                        .convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                                nonItemTaxDetail, userId, transactionId, orderSubNumber,
                                salesTransactionErrorId, detailCount.get(),
                                itemTaxCount.incrementAndGet(), originalTransactionErrorId);
        errorEvacuationSalesTransactionTaxEntityMapper
                .insertSelective(errorEvacuationSalesTransactionTaxEntity);

    }
}
