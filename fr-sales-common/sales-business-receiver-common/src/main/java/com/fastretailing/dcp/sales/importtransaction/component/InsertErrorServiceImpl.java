/**
 * @(#)InsertErrorServiceImpl.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.sales.importtransaction.converter.ErrorTableDataConverter;
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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionDetailInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionDiscountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionTaxMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionTenderInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionTenderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionTotalAmountMapper;

/**
 * The class is used to insert data to error table.
 *
 */
@Service
public class InsertErrorServiceImpl implements InsertErrorService {

    /**
     * The converter for error table.
     */
    @Autowired
    private ErrorTableDataConverter errorTableDataConverter;

    /**
     * Component for operating DB operations on the error order information table.
     */
    @Autowired
    private SalesErrorSalesOrderInformationMapper salesErrorSalesOrderInformationEntityMapper;

    /**
     * Component for operating DB operations on the error sales transaction header table.
     */
    @Autowired
    private SalesErrorSalesTransactionHeaderMapper salesErrorSalesTransactionHeaderEntityMapper;
    /**
     * Component for operating DB operations on the error sales transaction detail table.
     */
    @Autowired
    private SalesErrorSalesTransactionDetailMapper salesErrorSalesTransactionDetailEntityMapper;

    /**
     * Component for operating DB operations on the error sales transaction detail info table.
     */
    @Autowired
    private SalesErrorSalesTransactionDetailInfoMapper salesErrorSalesTransactionDetailInfoEntityMapper;

    /**
     * Component for operating DB operations on the error sales transaction discount table.
     */
    @Autowired
    private SalesErrorSalesTransactionDiscountMapper salesErrorSalesTransactionDiscountEntityMapper;

    /**
     * Component for operating DB operations on the error sales transaction tax table.
     */
    @Autowired
    private SalesErrorSalesTransactionTaxMapper salesErrorSalesTransactionTaxEntityMapper;

    /**
     * Component for operating DB operations on the error sales transaction tender table.
     */
    @Autowired
    private SalesErrorSalesTransactionTenderMapper salesErrorSalesTransactionTenderEntityMapper;

    /**
     * Component for operating DB operations on the error sales transaction tender info table.
     */
    @Autowired
    private SalesErrorSalesTransactionTenderInfoMapper salesErrorSalesTransactionTenderInfoEntityMapper;

    /**
     * Component for operating DB operations on the error sales transaction total amount table.
     */
    @Autowired
    private SalesErrorSalesTransactionTotalAmountMapper salesErrorSalesTransactionTotalAmountEntityMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertErrorTable(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId) {

        AtomicInteger transactionCount = new AtomicInteger(0);

        // Insert error order info .
        addErrorOrderInformation(transactionImportData, salesTransactionErrorId, userId);

        // Loop for transaction data.
        if (CollectionUtils.isNotEmpty(transactionImportData.getTransactionList())) {
            for (Transaction transaction : transactionImportData.getTransactionList()) {
                addTransaction(transaction, userId, salesTransactionErrorId, transactionCount,
                        new AtomicInteger(0));
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
    private void addErrorOrderInformation(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId) {
        SalesErrorSalesOrderInformation salesErrorSalesOrderInformationEntity =
                errorTableDataConverter.convertTSalesErrorOrderInformationEntityForInsert(
                        transactionImportData, userId, salesTransactionErrorId);
        salesErrorSalesOrderInformationEntityMapper
                .insertSelective(salesErrorSalesOrderInformationEntity);
    }

    /**
     * Add transaction data.
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param transactionCount Transaction count.
     * @param detailCount Detail number.
     */
    private void addTransaction(Transaction transaction, String userId,
            String salesTransactionErrorId, AtomicInteger transactionCount,
            AtomicInteger detailCount) {

        SalesErrorSalesTransactionHeader salesErrorSalesTransactionHeaderEntity =
                errorTableDataConverter.convertTSalesErrorSalesTransactionHeaderEntityForInsert(
                        transaction, userId, salesTransactionErrorId,
                        transactionCount.incrementAndGet());
        salesErrorSalesTransactionHeaderEntityMapper
                .insertSelective(salesErrorSalesTransactionHeaderEntity);

        // Loop for item detail.
        if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
            AtomicInteger itemDetailCount = new AtomicInteger(0);
            for (TransactionItemDetail itemDetail : transaction.getTransactionItemDetailList()) {
                addTransactionItemDetail(itemDetail, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        itemDetailCount, detailCount);
            }
        }

        // Loop for non item detail.
        if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
            for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                addNonItemDetail(nonItemDetail, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId, null,
                        new AtomicInteger(0), detailCount);
            }
        }

        // Loop for pay detail.
        if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTenderList())) {
            AtomicInteger payDetailCount = new AtomicInteger(0);
            for (SalesTransactionTender payDetail : transaction.getSalesTransactionTenderList()) {
                addSalesTransactionTender(payDetail, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        payDetailCount);
            }
        }

        // Loop for tax detail.
        if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTaxDetailList())) {
            AtomicInteger taxDetailCount = new AtomicInteger(0);
            for (SalesTransactionTaxDetail taxDetail : transaction
                    .getSalesTransactionTaxDetailList()) {
                addSalesTransactionTaxDetail(taxDetail, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        new AtomicInteger(0), taxDetailCount);
            }
        }

        // Loop for total list.
        if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTotalList())) {
            AtomicInteger totalCount = new AtomicInteger(0);
            for (SalesTransactionTotal transactionTotal : transaction
                    .getSalesTransactionTotalList()) {
                addSalesTransactionTotal(transactionTotal, transaction.getSalesTransactionId(),
                        transaction.getOrderSubNumber(), userId, salesTransactionErrorId,
                        totalCount);
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
     */
    private void addTransactionItemDetail(TransactionItemDetail transactionItemDetail,
            String transactionId, int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger itemDetailCount, AtomicInteger detailCount) {

        SalesErrorSalesTransactionDetail salesErrorSalesTransactionDetailEntity =
                errorTableDataConverter.convertTSalesErrorSalesTransactionDetailEntityForInsert(
                        transactionItemDetail, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId, detailCount.incrementAndGet(),
                        itemDetailCount.incrementAndGet());
        salesErrorSalesTransactionDetailEntityMapper
                .insertSelective(salesErrorSalesTransactionDetailEntity);

        // Loop for non item detail to item detail.
        if (transactionItemDetail.getNonItemDetailListByItem() != null) {
            for (NonItemDetail nonItemDetail : transactionItemDetail.getNonItemDetailListByItem()) {
                addItemDetail(nonItemDetail, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId,
                        transactionItemDetail.getDetailListSalesTransactionType(), itemDetailCount,
                        detailCount);
            }
        }

        // Loop for discount list.
        if (CollectionUtils.isNotEmpty(transactionItemDetail.getItemDiscountList())) {
            AtomicInteger itemDiscountCount = new AtomicInteger(0);
            for (ItemDiscount itemDiscount : transactionItemDetail.getItemDiscountList()) {
                addItemDiscount(itemDiscount, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId, detailCount, itemDiscountCount);
            }
        }

        if (CollectionUtils.isNotEmpty(transactionItemDetail.getItemTaxDetailList())) {
            // Loop for tax detail list.
            AtomicInteger taxDetailCount = new AtomicInteger(0);
            for (ItemTaxDetail itemTaxDetail : transactionItemDetail.getItemTaxDetailList()) {
                addItemTaxDetail(itemTaxDetail, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId, detailCount, taxDetailCount);
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
     * @param nonItemDetailCount Non item detail count.
     * @param detailCount Detail count.
     */
    private void addNonItemDetail(NonItemDetail nonItemDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            String salesTransactionType, AtomicInteger nonItemDetailCount,
            AtomicInteger detailCount) {
        SalesErrorSalesTransactionDetail salesErrorSalesTransactionDetailEntity =
                errorTableDataConverter
                        .convertTSalesErrorSalesTransactionDetailEntityForInsertOutside(
                                nonItemDetail, transactionId, orderSubNumber, userId,
                                salesTransactionErrorId, salesTransactionType,
                                detailCount.incrementAndGet(), nonItemDetailCount.get());
        salesErrorSalesTransactionDetailEntityMapper
                .insertSelective(salesErrorSalesTransactionDetailEntity);


        if (nonItemDetail.getNonItemInfo() != null) {
            SalesErrorSalesTransactionDetailInfo salesErrorSalesTransactionDetailInfoEntity =
                    errorTableDataConverter
                            .convertTSalesErrorSalesTransactionDetailInfoEntityForInsert(
                                    nonItemDetail.getNonItemInfo(), transactionId, orderSubNumber,
                                    userId, salesTransactionErrorId, detailCount.get(),
                                    nonItemDetailCount.get());
            salesErrorSalesTransactionDetailInfoEntityMapper
                    .insertSelective(salesErrorSalesTransactionDetailInfoEntity);
        }

        // Loop for non item discount detail list.
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemDiscountDetailList())) {
            AtomicInteger itemDiscountCount = new AtomicInteger(0);
            for (NonItemDiscountDetail nonItemDiscountDetail : nonItemDetail
                    .getNonItemDiscountDetailList()) {
                addNonItemDiscountDetail(nonItemDiscountDetail, transactionId, orderSubNumber,
                        userId, salesTransactionErrorId, detailCount, itemDiscountCount);
            }
        }

        // Loop for non item tax detail list.
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemTaxDetailList())) {
            AtomicInteger itemTaxCount = new AtomicInteger(0);
            for (NonItemTaxDetail nonItemTaxDetail : nonItemDetail.getNonItemTaxDetailList()) {
                addNonItemTaxDetail(nonItemTaxDetail, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId, detailCount, itemTaxCount);
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
     */
    private void addSalesTransactionTender(SalesTransactionTender payDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger payDetailCount) {
        SalesErrorSalesTransactionTender salesErrorSalesTransactionTenderEntity =
                errorTableDataConverter.convertTSalesErrorSalesTransactionTenderEntityForInsert(
                        payDetail, transactionId, orderSubNumber, userId, salesTransactionErrorId,
                        payDetailCount.incrementAndGet());
        salesErrorSalesTransactionTenderEntityMapper
                .insertSelective(salesErrorSalesTransactionTenderEntity);

        if (payDetail.getTenderInfoList() != null) {
            SalesErrorSalesTransactionTenderInfo salesErrorSalesTransactionTenderInfoEntity =
                    errorTableDataConverter
                            .convertTSalesErrorSalesTransactionTenderInfoEntityForInsert(
                                    payDetail.getTenderInfoList(), transactionId, orderSubNumber,
                                    payDetail.getTenderGroupId(), payDetail.getTenderId(), userId,
                                    salesTransactionErrorId, payDetailCount.get());
            salesErrorSalesTransactionTenderInfoEntityMapper
                    .insertSelective(salesErrorSalesTransactionTenderInfoEntity);
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
     */
    private void addSalesTransactionTaxDetail(SalesTransactionTaxDetail salesTransactionTaxDetail,
            String transactionId, int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger detailCount, AtomicInteger taxDetailCount) {
        SalesErrorSalesTransactionTax salesErrorSalesTransactionTaxEntity = errorTableDataConverter
                .convertTSalesErrorSalesTransactionTaxEntityForInsertTransaction(
                        salesTransactionTaxDetail, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId, detailCount.get(),
                        taxDetailCount.incrementAndGet());
        salesErrorSalesTransactionTaxEntityMapper
                .insertSelective(salesErrorSalesTransactionTaxEntity);
    }

    /**
     * Add sales transaction total.
     * 
     * @param salesTransactionTotal Sales transaction total.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param totalCount Total count.
     */
    private void addSalesTransactionTotal(SalesTransactionTotal salesTransactionTotal,
            String transactionId, int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger totalCount) {
        SalesErrorSalesTransactionTotalAmount salesErrorSalesTransactionTotalAmountEntity =
                errorTableDataConverter
                        .convertTSalesErrorSalesTransactionTotalAmountEntityForInsert(
                                salesTransactionTotal, transactionId, orderSubNumber, userId,
                                salesTransactionErrorId, totalCount.incrementAndGet());
        salesErrorSalesTransactionTotalAmountEntityMapper
                .insertSelective(salesErrorSalesTransactionTotalAmountEntity);
    }

    /**
     * Add item detail.
     * 
     * @param nonItemDetail Non item detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param salesTransactionType Sales transaction type.
     * @param itemDetailCount Item detail count.
     * @param detailCount Detail count.
     */
    private void addItemDetail(NonItemDetail nonItemDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            String salesTransactionType, AtomicInteger itemDetailCount, AtomicInteger detailCount) {

        SalesErrorSalesTransactionDetail salesErrorSalesTransactionDetailEntity =
                errorTableDataConverter
                        .convertTSalesErrorSalesTransactionDetailEntityForInsertOutside(
                                nonItemDetail, transactionId, orderSubNumber, userId,
                                salesTransactionErrorId, salesTransactionType,
                                detailCount.incrementAndGet(), itemDetailCount.get());
        salesErrorSalesTransactionDetailEntityMapper
                .insertSelective(salesErrorSalesTransactionDetailEntity);

        if (nonItemDetail.getNonItemInfo() != null) {
            SalesErrorSalesTransactionDetailInfo salesErrorSalesTransactionDetailInfoEntity =
                    errorTableDataConverter
                            .convertTSalesErrorSalesTransactionDetailInfoEntityForInsert(
                                    nonItemDetail.getNonItemInfo(), transactionId, orderSubNumber,
                                    userId, salesTransactionErrorId, detailCount.get(),
                                    itemDetailCount.get());
            salesErrorSalesTransactionDetailInfoEntityMapper
                    .insertSelective(salesErrorSalesTransactionDetailInfoEntity);

        }

        // Loop for item discount list.
        AtomicInteger itemDiscountCount = new AtomicInteger(0);
        for (NonItemDiscountDetail nonItemDiscountDetail : nonItemDetail
                .getNonItemDiscountDetailList()) {
            SalesErrorSalesTransactionDiscount salesErrorSalesTransactionDiscountEntity =
                    errorTableDataConverter
                            .convertTSalesErrorSalesTransactionDiscountEntityForInsertNon(
                                    nonItemDiscountDetail, transactionId, orderSubNumber, userId,
                                    salesTransactionErrorId, detailCount.get(),
                                    itemDiscountCount.get());
            salesErrorSalesTransactionDiscountEntityMapper
                    .insertSelective(salesErrorSalesTransactionDiscountEntity);

        }

        // Loop for item tax detail list.
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemTaxDetailList())) {
            AtomicInteger itemTaxCount = new AtomicInteger(0);
            for (NonItemTaxDetail nonItemTaxDetail : nonItemDetail.getNonItemTaxDetailList()) {
                SalesErrorSalesTransactionTax salesErrorSalesTransactionTaxEntity =
                        errorTableDataConverter
                                .convertTSalesErrorSalesTransactionTaxEntityForInsertNon(
                                        nonItemTaxDetail, transactionId, orderSubNumber, userId,
                                        salesTransactionErrorId, detailCount.get(),
                                        itemTaxCount.incrementAndGet());
                salesErrorSalesTransactionTaxEntityMapper
                        .insertSelective(salesErrorSalesTransactionTaxEntity);

            }
        }

    }

    /**
     * Add item discount.
     * 
     * @param itemDiscount Item discount.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemDiscountCount Item discount count.
     */
    private void addItemDiscount(ItemDiscount itemDiscount, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger detailCount, AtomicInteger itemDiscountCount) {
        SalesErrorSalesTransactionDiscount salesErrorSalesTransactionDiscountEntity =
                errorTableDataConverter.convertTSalesErrorSalesTransactionDiscountEntityForInsert(
                        itemDiscount, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId, detailCount.get(),
                        itemDiscountCount.incrementAndGet());
        salesErrorSalesTransactionDiscountEntityMapper
                .insertSelective(salesErrorSalesTransactionDiscountEntity);
    }

    /**
     * Add item tax detail.
     * 
     * @param itemTaxDetail Item tax detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param taxDetailCount Tax detail count.
     */
    private void addItemTaxDetail(ItemTaxDetail itemTaxDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger detailCount, AtomicInteger taxDetailCount) {
        SalesErrorSalesTransactionTax salesErrorSalesTransactionTaxEntity = errorTableDataConverter
                .convertTSalesErrorSalesTransactionTaxEntityForInsert(itemTaxDetail, transactionId,
                        orderSubNumber, userId, salesTransactionErrorId, detailCount.get(),
                        taxDetailCount.incrementAndGet());
        salesErrorSalesTransactionTaxEntityMapper
                .insertSelective(salesErrorSalesTransactionTaxEntity);

    }

    /**
     * Add non item discount detail.
     * 
     * @param nonItemDiscountDetail Non item discount detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemDiscountCount Item discount count.
     */
    private void addNonItemDiscountDetail(NonItemDiscountDetail nonItemDiscountDetail,
            String transactionId, int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger detailCount, AtomicInteger itemDiscountCount) {
        SalesErrorSalesTransactionDiscount salesErrorSalesTransactionDiscountEntity =
                errorTableDataConverter
                        .convertTSalesErrorSalesTransactionDiscountEntityForInsertNon(
                                nonItemDiscountDetail, transactionId, orderSubNumber, userId,
                                salesTransactionErrorId, detailCount.get(),
                                itemDiscountCount.incrementAndGet());
        salesErrorSalesTransactionDiscountEntityMapper
                .insertSelective(salesErrorSalesTransactionDiscountEntity);
    }

    /**
     * Add non item tax detail.
     * 
     * @param nonItemTaxDetail non item tax detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @param detailCount Detail count.
     * @param itemTaxCount Item tax count.
     */
    private void addNonItemTaxDetail(NonItemTaxDetail nonItemTaxDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionErrorId,
            AtomicInteger detailCount, AtomicInteger itemTaxCount) {
        SalesErrorSalesTransactionTax salesErrorSalesTransactionTaxEntity =
                errorTableDataConverter.convertTSalesErrorSalesTransactionTaxEntityForInsertNon(
                        nonItemTaxDetail, transactionId, orderSubNumber, userId,
                        salesTransactionErrorId, detailCount.get(), itemTaxCount.incrementAndGet());
        salesErrorSalesTransactionTaxEntityMapper
                .insertSelective(salesErrorSalesTransactionTaxEntity);

    }
}
