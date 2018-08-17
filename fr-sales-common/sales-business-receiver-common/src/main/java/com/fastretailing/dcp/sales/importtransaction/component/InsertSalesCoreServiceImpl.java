/**
 * @(#)InsertSalesCoreServiceImpl.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.sales.common.type.UpdateType;
import com.fastretailing.dcp.sales.importtransaction.converter.CoreTableDataConverter;
import com.fastretailing.dcp.sales.importtransaction.converter.ErrorDetailConverter;
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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetailCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderTable;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.exception.ErrorEvacuationException;
import com.fastretailing.dcp.sales.importtransaction.exception.UniqueConstraintsException;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionDetailInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionDiscountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionErrorDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionTaxMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionTenderInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionTenderTableMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionTotalAmountMapper;

/**
 * The class is used to insert transaction data.
 *
 */
@Service
public class InsertSalesCoreServiceImpl implements InsertSalesCoreService {

    /** Error data correction service. */
    @Autowired
    private ErrorDataCorrectionService errorDataCorrectionService;

    /**
     * The converter for sales core table.
     */
    @Autowired
    private CoreTableDataConverter coreTableDataConverter;

    /**
     * The service for insert data to error evacuation table.
     */
    @Autowired
    private InsertErrorEvacuationService insertErrorEvacuationTableService;

    /**
     * Component for operating DB operations on the order information table.
     */
    @Autowired
    private SalesOrderInformationMapper salesOrderInformationMapper;

    /**
     * Component for operating DB operations on the sales transaction header table.
     */
    @Autowired
    private SalesTransactionHeaderMapper salesTransactionHeaderEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction detail table.
     */
    @Autowired
    private SalesTransactionDetailMapper salesTransactionDetailEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction detail info table.
     */
    @Autowired
    private SalesTransactionDetailInfoMapper salesTransactionDetailInfoEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction discount table.
     */
    @Autowired
    private SalesTransactionDiscountMapper salesTransactionDiscountEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction tax table.
     */
    @Autowired
    private SalesTransactionTaxMapper salesTransactionTaxEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction tender table.
     */
    @Autowired
    private SalesTransactionTenderTableMapper salesTransactionTenderEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction tender info table.
     */
    @Autowired
    private SalesTransactionTenderInfoMapper salesTransactionTenderInfoEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction total amount table.
     */
    @Autowired
    private SalesTransactionTotalAmountMapper salesTransactionTotalAmountEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction error detail table.
     */
    @Autowired
    private SalesTransactionErrorDetailMapper salesTransactionErrorDetailEntityMapper;

    /**
     * Component for convert data to sales transaction error detail table.
     */
    @Autowired
    private ErrorDetailConverter errorDetailConverter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertSalesCoreTable(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId) throws ErrorEvacuationException {

        boolean correctExcuteFlag = false;

        if (StringUtils.isNotEmpty(transactionImportData.getSalesTransactionErrorId())) {
            correctExcuteFlag = true;
        }

        // Get data from order information.
        SalesTransactionErrorDetailCondition salesTransactionErrorDetailEntityCondition =
                new SalesTransactionErrorDetailCondition();

        salesTransactionErrorDetailEntityCondition.createCriteria()
                .andIntegratedOrderIdEqualTo(transactionImportData.getIntegratedOrderId())
                .andDataAlterationStatusTypeEqualTo("0");

        Optional<SalesTransactionErrorDetail> optional = salesTransactionErrorDetailEntityMapper
                .selectByCondition(salesTransactionErrorDetailEntityCondition)
                .stream()
                .findFirst();

        if (optional.isPresent() && !correctExcuteFlag) {
            // Insert to error evacuation table when error occurred in this order.
            insertErrorEvacuationTableService.insertErrorEvacuationTable(transactionImportData,
                    salesTransactionErrorId, userId);
            throw new ErrorEvacuationException("Error evacuation occurred.");
        } else {
            addOrderInformation(transactionImportData, userId);
            AtomicInteger transactionCount = new AtomicInteger(0);
            List<Transaction> transactionList = transactionImportData.getTransactionList();
            // Loop for transaction data.
            if (transactionList != null) {
                for (Transaction transaction : transactionList) {
                    addTransaction(transaction, userId, transactionCount, new AtomicInteger(0));
                }
            }
        }

        if (correctExcuteFlag && UpdateType.INSERT.is(transactionImportData.getUpdateType())) {
            errorDataCorrectionService.correctErrorData(transactionImportData,
                    salesTransactionErrorId, userId);
        }

    }

    /**
     * Add order information.
     * 
     * @param transactionImportData Transaction import data.
     * @param userId User id.
     */
    private void addOrderInformation(TransactionImportData transactionImportData, String userId) {
        SalesOrderInformation salesOrderInformationEntity = coreTableDataConverter
                .convertTSalesOrderInformationEntityForInsert(transactionImportData, userId);
        salesOrderInformationMapper.insertSelective(salesOrderInformationEntity);
    }

    /**
     * Add transaction data.
     * 
     * @param transaction Transaction data.
     * @param userId User id.
     * @param transactionCount Transaction data number.
     * @param detailCount Detail number.
     */
    private void addTransaction(Transaction transaction, String userId,
            AtomicInteger transactionCount, AtomicInteger detailCount) {

        SalesTransactionHeader salesTransactionHeaderEntity =
                coreTableDataConverter.convertTSalesTransactionHeaderEntityForInsert(transaction,
                        userId, transactionCount.incrementAndGet());
        if (salesTransactionHeaderEntityMapper.insertSelective(salesTransactionHeaderEntity) == 0) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity = errorDetailConverter
                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                            salesTransactionHeaderEntity, userId);
            throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
        }

        try {
            // Loop for item detail.
            if (CollectionUtils.isNotEmpty(transaction.getTransactionItemDetailList())) {
                AtomicInteger itemDetailCount = new AtomicInteger(0);
                for (TransactionItemDetail itemDetail : transaction
                        .getTransactionItemDetailList()) {
                    addTransactionItemDetail(itemDetail, transaction.getSalesTransactionId(),
                            transaction.getOrderSubNumber(), userId, itemDetailCount, detailCount);
                }
            }

            // Loop for non item detail.
            if (CollectionUtils.isNotEmpty(transaction.getNonItemDetailList())) {
                for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                    addNonItemDetail(nonItemDetail, transaction.getSalesTransactionId(),
                            transaction.getOrderSubNumber(), userId, null, new AtomicInteger(0),
                            detailCount);
                }
            }

            // Loop for pay detail.
            if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTenderList())) {
                AtomicInteger payDetailCount = new AtomicInteger(0);
                for (SalesTransactionTender payDetail : transaction
                        .getSalesTransactionTenderList()) {
                    addSalesTransactionTender(payDetail, transaction.getSalesTransactionId(),
                            transaction.getOrderSubNumber(), userId, payDetailCount);
                }
            }

            // Loop for tax detail.
            if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTaxDetailList())) {
                AtomicInteger taxDetailCount = new AtomicInteger(0);
                for (SalesTransactionTaxDetail taxDetail : transaction
                        .getSalesTransactionTaxDetailList()) {
                    addSalesTransactionTaxDetail(taxDetail, transaction.getSalesTransactionId(),
                            transaction.getOrderSubNumber(), userId, new AtomicInteger(0),
                            taxDetailCount);
                }
            }

            // Loop for total list.
            if (CollectionUtils.isNotEmpty(transaction.getSalesTransactionTotalList())) {
                AtomicInteger totalCount = new AtomicInteger(0);
                for (SalesTransactionTotal transactionTotal : transaction
                        .getSalesTransactionTotalList()) {
                    addSalesTransactionTotal(transactionTotal, transaction.getSalesTransactionId(),
                            transaction.getOrderSubNumber(), userId, totalCount);
                }
            }
        } catch (UniqueConstraintsException exception) {
            exception.getEntity().setSalesTransactionId(transaction.getSalesTransactionId());
            exception.getEntity().setStoreCode(transaction.getStoreCode());
            exception.getEntity().setSystemBrandCode(transaction.getSystemBrandCode());
            exception.getEntity().setSystemBusinessCode(transaction.getSystemBusinessCode());
            exception.getEntity().setSystemCountryCode(transaction.getSystemCountryCode());
            exception.getEntity().setKeyInfo1(transaction.getIntegratedOrderId());
            exception.getEntity().setKeyInfo2(transaction.getSalesTransactionId());
            throw exception;
        }
    }

    /**
     * Add transaction item detail.
     * 
     * @param transactionItemDetail Transaction item detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param itemDetailCount Item detail count.
     * @param detailCount Detail count.
     */
    private void addTransactionItemDetail(TransactionItemDetail transactionItemDetail,
            String transactionId, int orderSubNumber, String userId, AtomicInteger itemDetailCount,
            AtomicInteger detailCount) {
        SalesTransactionDetail salesTransactionDetailEntity =
                coreTableDataConverter.convertTSalesTransactionDetailEntityForInsert(
                        transactionItemDetail, userId, transactionId, orderSubNumber,
                        detailCount.incrementAndGet(), itemDetailCount.incrementAndGet());
        if (salesTransactionDetailEntityMapper.insertSelective(salesTransactionDetailEntity) == 0) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity = errorDetailConverter
                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                            salesTransactionDetailEntity, userId);
            throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
        }

        try {
            // Loop for non item detail to item detail.
            if (transactionItemDetail.getNonItemDetailListByItem() != null) {
                for (NonItemDetail nonItemDetail : transactionItemDetail
                        .getNonItemDetailListByItem()) {
                    addItemDetail(nonItemDetail, transactionId, orderSubNumber, userId,
                            transactionItemDetail.getDetailListSalesTransactionType(),
                            itemDetailCount, detailCount);
                }
            }

            // Loop for discount list.
            if (CollectionUtils.isNotEmpty(transactionItemDetail.getItemDiscountList())) {
                AtomicInteger itemDiscountCount = new AtomicInteger(0);
                for (ItemDiscount itemDiscount : transactionItemDetail.getItemDiscountList()) {
                    addItemDiscount(itemDiscount, transactionId, orderSubNumber, userId,
                            detailCount, itemDiscountCount);
                }
            }

            // Loop for tax detail list.
            if (CollectionUtils.isNotEmpty(transactionItemDetail.getItemTaxDetailList())) {
                AtomicInteger taxDetailCount = new AtomicInteger(0);
                for (ItemTaxDetail itemTaxDetail : transactionItemDetail.getItemTaxDetailList()) {
                    addItemTaxDetail(itemTaxDetail, transactionId, orderSubNumber, userId,
                            detailCount, taxDetailCount);
                }
            }
        } catch (UniqueConstraintsException exception) {
            exception.getEntity()
                    .setKeyInfo3(String.valueOf(salesTransactionDetailEntity.getDetailSubNumber()));
            throw exception;
        }
    }

    /**
     * Add non item detail.
     * 
     * @param nonItemDetail Non item detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionType Sales transaction type.
     * @param nonItemDetailCount Non item detail count.
     * @param detailCount Detail count.
     */
    private void addNonItemDetail(NonItemDetail nonItemDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionType,
            AtomicInteger nonItemDetailCount, AtomicInteger detailCount) {
        SalesTransactionDetail salesTransactionDetailEntity =
                coreTableDataConverter.convertTSalesTransactionDetailEntityForInsertOutside(
                        nonItemDetail, transactionId, orderSubNumber, salesTransactionType, userId,
                        detailCount.incrementAndGet(), nonItemDetailCount.get());
        if (salesTransactionDetailEntityMapper.insertSelective(salesTransactionDetailEntity) == 0) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity = errorDetailConverter
                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                            salesTransactionDetailEntity, userId);
            throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
        }

        if (nonItemDetail.getNonItemInfo() != null) {
            SalesTransactionDetailInfo salesTransactionDetailInfoEntity =
                    coreTableDataConverter.convertTSalesTransactionDetailInfoEntityForInsert(
                            nonItemDetail.getNonItemInfo(), transactionId, orderSubNumber, userId,
                            detailCount.get(), nonItemDetailCount.get());
            salesTransactionDetailInfoEntityMapper
                    .insertSelective(salesTransactionDetailInfoEntity);
        }

        try {
            // Loop for non item discount detail list.
            if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemDiscountDetailList())) {
                AtomicInteger itemDiscountCount = new AtomicInteger(0);
                for (NonItemDiscountDetail nonItemDiscountDetail : nonItemDetail
                        .getNonItemDiscountDetailList()) {
                    addNonItemDiscountDetail(nonItemDiscountDetail, transactionId, orderSubNumber,
                            userId, detailCount, itemDiscountCount);
                }
            }

            // Loop for non item tax detail list.
            if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemTaxDetailList())) {
                AtomicInteger itemTaxCount = new AtomicInteger(0);
                for (NonItemTaxDetail nonItemTaxDetail : nonItemDetail.getNonItemTaxDetailList()) {
                    addNonItemTaxDetail(nonItemTaxDetail, transactionId, orderSubNumber, userId,
                            detailCount, itemTaxCount);
                }
            }
        } catch (UniqueConstraintsException exception) {
            exception.getEntity()
                    .setKeyInfo3(String.valueOf(salesTransactionDetailEntity.getDetailSubNumber()));
            throw exception;
        }

    }

    /**
     * Add pay detail of the transaction.
     * 
     * @param payDetail Pay detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param payDetailCount Pay detail count.
     */
    private void addSalesTransactionTender(SalesTransactionTender payDetail, String transactionId,
            int orderSubNumber, String userId, AtomicInteger payDetailCount) {
        SalesTransactionTenderTable salesTransactionTenderEntity =
                coreTableDataConverter.convertTSalesTransactionTenderEntityForInsert(payDetail,
                        transactionId, orderSubNumber, userId, payDetailCount.incrementAndGet());
        if (salesTransactionTenderEntityMapper.insertSelective(salesTransactionTenderEntity) == 0) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity = errorDetailConverter
                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                            salesTransactionTenderEntity, userId);
            throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
        }

        if (payDetail.getTenderInfoList() != null) {
            SalesTransactionTenderInfo salesTransactionTenderInfoEntity =
                    coreTableDataConverter.convertTSalesTransactionTenderInfoEntityForInsert(
                            payDetail.getTenderInfoList(), transactionId, orderSubNumber,
                            payDetail.getTenderGroupId(), payDetail.getTenderId(), userId,
                            payDetailCount.get());
            salesTransactionTenderInfoEntityMapper
                    .insertSelective(salesTransactionTenderInfoEntity);
        }

    }

    /**
     * Add sales transaction tax detail.
     * 
     * @param salesTransactionTaxDetail Sales transaction tax detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param detailCount Detail count.
     * @param taxDetailCount Tax detail count.
     */
    private void addSalesTransactionTaxDetail(SalesTransactionTaxDetail salesTransactionTaxDetail,
            String transactionId, int orderSubNumber, String userId, AtomicInteger detailCount,
            AtomicInteger taxDetailCount) {
        SalesTransactionTax salesTransactionTaxEntity =
                coreTableDataConverter.convertTSalesTransactionTaxEntityForInsertTransaction(
                        salesTransactionTaxDetail, transactionId, orderSubNumber, userId,
                        detailCount.get(), taxDetailCount.incrementAndGet());
        if (salesTransactionTaxEntityMapper.insertSelective(salesTransactionTaxEntity) == 0) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity = errorDetailConverter
                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                            salesTransactionTaxEntity, userId);
            throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
        }
    }

    /**
     * Add sales transaction total.
     * 
     * @param salesTransactionTotal Sales transaction total.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param totalCount Total count.
     */
    private void addSalesTransactionTotal(SalesTransactionTotal salesTransactionTotal,
            String transactionId, int orderSubNumber, String userId, AtomicInteger totalCount) {
        SalesTransactionTotalAmount salesTransactionTotalAmountEntity = coreTableDataConverter
                .convertTSalesTransactionTotalAmountEntityForInsert(salesTransactionTotal,
                        transactionId, orderSubNumber, userId, totalCount.incrementAndGet());
        if (salesTransactionTotalAmountEntityMapper
                .insertSelective(salesTransactionTotalAmountEntity) == 0) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity = errorDetailConverter
                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                            salesTransactionTotalAmountEntity, userId);
            throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
        }
    }

    /**
     * Add item detail.
     * 
     * @param nonItemDetail Item detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param salesTransactionType Sales transaction type.
     * @param itemDetailCount Item detail count.
     * @param detailCount Detail count.
     */
    private void addItemDetail(NonItemDetail nonItemDetail, String transactionId,
            int orderSubNumber, String userId, String salesTransactionType,
            AtomicInteger itemDetailCount, AtomicInteger detailCount) {
        SalesTransactionDetail salesTransactionDetailEntity =
                coreTableDataConverter.convertTSalesTransactionDetailEntityForInsertOutside(
                        nonItemDetail, transactionId, orderSubNumber, salesTransactionType, userId,
                        detailCount.incrementAndGet(), itemDetailCount.get());
        if (salesTransactionDetailEntityMapper.insertSelective(salesTransactionDetailEntity) == 0) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity = errorDetailConverter
                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4(
                            salesTransactionDetailEntity, userId);
            throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
        }

        if (nonItemDetail.getNonItemInfo() != null) {
            SalesTransactionDetailInfo salesTransactionDetailInfoEntity =
                    coreTableDataConverter.convertTSalesTransactionDetailInfoEntityForInsert(
                            nonItemDetail.getNonItemInfo(), transactionId, orderSubNumber, userId,
                            detailCount.get(), itemDetailCount.get());
            salesTransactionDetailInfoEntityMapper
                    .insertSelective(salesTransactionDetailInfoEntity);
        }

        // Loop for item discount list.
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemDiscountDetailList())) {
            AtomicInteger itemDiscountCount = new AtomicInteger(0);
            for (NonItemDiscountDetail nonItemDiscountDetail : nonItemDetail
                    .getNonItemDiscountDetailList()) {
                SalesTransactionDiscount salesTransactionDiscountEntity =
                        coreTableDataConverter.convertTSalesTransactionDiscountEntityForInsertNon(
                                nonItemDiscountDetail, transactionId, orderSubNumber, userId,
                                detailCount.get(), itemDiscountCount.incrementAndGet());
                if (salesTransactionDiscountEntityMapper
                        .insertSelective(salesTransactionDiscountEntity) == 0) {
                    SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                            errorDetailConverter
                                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5(
                                            salesTransactionDiscountEntity, userId);
                    throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
                }
            }
        }

        // Loop for item tax detail list.
        if (CollectionUtils.isNotEmpty(nonItemDetail.getNonItemTaxDetailList())) {
            AtomicInteger itemTaxCount = new AtomicInteger(0);
            for (NonItemTaxDetail nonItemTaxDetail : nonItemDetail.getNonItemTaxDetailList()) {

                SalesTransactionTax salesTransactionTaxEntity =
                        coreTableDataConverter.convertTSalesTransactionTaxEntityForInsertNon(
                                nonItemTaxDetail, transactionId, orderSubNumber, userId,
                                detailCount.get(), itemTaxCount.incrementAndGet());
                if (salesTransactionTaxEntityMapper
                        .insertSelective(salesTransactionTaxEntity) == 0) {
                    SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                            errorDetailConverter
                                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5(
                                            salesTransactionTaxEntity, userId);
                    throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
                }
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
     * @param detailCount Detail count.
     * @param itemDiscountCount Item discount count.
     */
    private void addItemDiscount(ItemDiscount itemDiscount, String transactionId,
            int orderSubNumber, String userId, AtomicInteger detailCount,
            AtomicInteger itemDiscountCount) {
        SalesTransactionDiscount salesTransactionDiscountEntity =
                coreTableDataConverter.convertTSalesTransactionDiscountEntityForInsert(itemDiscount,
                        transactionId, orderSubNumber, userId, detailCount.get(),
                        itemDiscountCount.incrementAndGet());
        if (salesTransactionDiscountEntityMapper
                .insertSelective(salesTransactionDiscountEntity) == 0) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity = errorDetailConverter
                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                            salesTransactionDiscountEntity, userId);
            throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
        }
    }

    /**
     * Add item tax detail.
     * 
     * @param itemTaxDetail Item tax detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param detailCount Detail count.
     * @param taxDetailCount Tax detail count.
     */
    private void addItemTaxDetail(ItemTaxDetail itemTaxDetail, String transactionId,
            int orderSubNumber, String userId, AtomicInteger detailCount,
            AtomicInteger taxDetailCount) {
        SalesTransactionTax salesTransactionTaxEntity =
                coreTableDataConverter.convertTSalesTransactionTaxEntityForInsert(itemTaxDetail,
                        transactionId, orderSubNumber, userId, detailCount.get(),
                        taxDetailCount.incrementAndGet());
        if (salesTransactionTaxEntityMapper.insertSelective(salesTransactionTaxEntity) == 0) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity = errorDetailConverter
                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4(
                            salesTransactionTaxEntity, userId);
            throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
        }

    }

    /**
     * Add non item discount detail.
     * 
     * @param nonItemDiscountDetail Non item discount detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param detailCount Detail count.
     * @param itemDiscountCount Item discount count.
     */
    private void addNonItemDiscountDetail(NonItemDiscountDetail nonItemDiscountDetail,
            String transactionId, int orderSubNumber, String userId, AtomicInteger detailCount,
            AtomicInteger itemDiscountCount) {
        SalesTransactionDiscount salesTransactionDiscountEntity =
                coreTableDataConverter.convertTSalesTransactionDiscountEntityForInsertNon(
                        nonItemDiscountDetail, transactionId, orderSubNumber, userId,
                        detailCount.get(), itemDiscountCount.incrementAndGet());
        if (salesTransactionDiscountEntityMapper
                .insertSelective(salesTransactionDiscountEntity) == 0) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity = errorDetailConverter
                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                            salesTransactionDiscountEntity, userId);
            throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
        }
    }

    /**
     * Add non item tax detail.
     * 
     * @param nonItemTaxDetail Non item tax detail.
     * @param transactionId Transaction id.
     * @param orderSubNumber Order sub number.
     * @param userId User id.
     * @param detailCount Detail count.
     * @param itemTaxCount Item tax count.
     */
    private void addNonItemTaxDetail(NonItemTaxDetail nonItemTaxDetail, String transactionId,
            int orderSubNumber, String userId, AtomicInteger detailCount,
            AtomicInteger itemTaxCount) {
        SalesTransactionTax salesTransactionTaxEntity = coreTableDataConverter
                .convertTSalesTransactionTaxEntityForInsertNon(nonItemTaxDetail, transactionId,
                        orderSubNumber, userId, detailCount.get(), itemTaxCount.incrementAndGet());
        if (salesTransactionTaxEntityMapper.insertSelective(salesTransactionTaxEntity) == 0) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity = errorDetailConverter
                    .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4(
                            salesTransactionTaxEntity, userId);
            throw new UniqueConstraintsException(salesTransactionErrorDetailEntity);
        }
    }
}
