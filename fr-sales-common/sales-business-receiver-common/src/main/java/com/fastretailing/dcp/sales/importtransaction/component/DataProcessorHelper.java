/**
 * @(#)DataProcessorHelper.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.importtransaction.converter.HistoryTableDataConverter;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistoryOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesOrderInformationCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetailCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetailInfoCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDiscountCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionHeaderCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTaxCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTenderCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTenderInfoCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTotalAmountCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformationCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailInfoCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDiscountCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionHeaderCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTaxCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderInfoCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTotalAmountCondition;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistoryOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionDetailInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionDiscountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTaxMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTenderInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTenderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTotalAmountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDetailInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDiscountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTaxMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTenderInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTenderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTotalAmountMapper;
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
 * The class is used for error data correction and deletion.
 *
 */
@Component
public class DataProcessorHelper {

    /**
     * Component for operating DB operations on the sales order information table.
     */
    @Autowired
    private SalesErrorSalesOrderInformationMapper salesErrorSalesOrderInformationEntityMapper;

    /**
     * Component for operating DB operations on the sales error sales transaction header table.
     */
    @Autowired
    private SalesErrorSalesTransactionHeaderMapper salesErrorSalesTransactionHeaderEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionHeaderMapper errorEvacuationSalesTransactionHeaderEntityMapper;

    /**
     * Component for operating DB operations on the sales error sales transaction table.
     */
    @Autowired
    private SalesErrorSalesTransactionDetailMapper salesErrorSalesTransactionDetailEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionDetailMapper errorEvacuationSalesTransactionDetailEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionDetailInfoMapper errorEvacuationSalesTransactionDetailInfoEntityMapper;

    /**
     * Component for operating DB operations on the sales error sales transaction detail info table.
     */
    @Autowired
    private SalesErrorSalesTransactionDetailInfoMapper salesErrorSalesTransactionDetailInfoEntityMapper;

    /**
     * Component for operating DB operations on the error sales transaction discount table.
     */
    @Autowired
    private SalesErrorSalesTransactionDiscountMapper salesErrorSalesTransactionDiscountEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction discount
     * table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionDiscountMapper errorEvacuationSalesTransactionDiscountEntityMapper;

    /**
     * Component for operating DB operations on the sales error sales transaction table.
     */
    @Autowired
    private SalesErrorSalesTransactionTaxMapper salesErrorSalesTransactionTaxEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction tax table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionTaxMapper errorEvacuationSalesTransactionTaxEntityMapper;

    /**
     * Component for operating DB operations on the sales error sales transaction tender table.
     */
    @Autowired
    private SalesErrorSalesTransactionTenderMapper salesErrorSalesTransactionTenderEntityMapper;
    /**
     * Component for operating DB operations on the error evacuation sales transaction tender table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionTenderMapper errorEvacuationSalesTransactionTenderEntityMapper;

    /**
     * Component for operating DB operations on the sales error sales transaction tender info table.
     */
    @Autowired
    private SalesErrorSalesTransactionTenderInfoMapper salesErrorSalesTransactionTenderInfoEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction tender info
     * table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionTenderInfoMapper errorEvacuationSalesTransactionTenderInfoEntityMapper;

    /**
     * Component for operating DB operations on the sales error sales transaction total amount
     * table.
     */
    @Autowired
    private SalesErrorSalesTransactionTotalAmountMapper salesErrorSalesTransactionTotalAmountEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales transaction total amount
     * table.
     */
    @Autowired
    private ErrorEvacuationSalesTransactionTotalAmountMapper errorEvacuationSalesTransactionTotalAmountEntityMapper;

    /**
     * Component for operating DB operations on the error evacuation sales order information table.
     */
    @Autowired
    private ErrorEvacuationSalesOrderInformationMapper errorEvacuationSalesOrderInformationEntityMapper;

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
     * The converter for history table.
     */
    @Autowired
    private HistoryTableDataConverter historyTableDataConverter;

    /**
     * Delete table by transaction id.
     * 
     * @param transactionImportData Transaction import data.
     */
    public void deleteTableByTransactionId(TransactionImportData transactionImportData) {

        SalesErrorSalesOrderInformationCondition salesErrorSalesOrderInformationEntityCondition =
                new SalesErrorSalesOrderInformationCondition();
        salesErrorSalesOrderInformationEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        salesErrorSalesOrderInformationEntityMapper
                .deleteByCondition(salesErrorSalesOrderInformationEntityCondition);

        ErrorEvacuationSalesOrderInformationCondition errorEvacuationSalesOrderInformationEntityCondition =
                new ErrorEvacuationSalesOrderInformationCondition();
        errorEvacuationSalesOrderInformationEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        errorEvacuationSalesOrderInformationEntityMapper
                .deleteByCondition(errorEvacuationSalesOrderInformationEntityCondition);

        SalesErrorSalesTransactionHeaderCondition salesErrorSalesTransactionHeaderEntityCondition =
                new SalesErrorSalesTransactionHeaderCondition();
        salesErrorSalesTransactionHeaderEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        salesErrorSalesTransactionHeaderEntityMapper
                .deleteByCondition(salesErrorSalesTransactionHeaderEntityCondition);

        ErrorEvacuationSalesTransactionHeaderCondition errorEvacuationSalesTransactionHeaderEntityCondition =
                new ErrorEvacuationSalesTransactionHeaderCondition();
        errorEvacuationSalesTransactionHeaderEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        errorEvacuationSalesTransactionHeaderEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionHeaderEntityCondition);

        SalesErrorSalesTransactionDetailCondition salesErrorSalesTransactionDetailEntityCondition =
                new SalesErrorSalesTransactionDetailCondition();
        salesErrorSalesTransactionDetailEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        salesErrorSalesTransactionDetailEntityMapper
                .deleteByCondition(salesErrorSalesTransactionDetailEntityCondition);

        ErrorEvacuationSalesTransactionDetailCondition errorEvacuationSalesTransactionDetailEntityCondition =
                new ErrorEvacuationSalesTransactionDetailCondition();
        errorEvacuationSalesTransactionDetailEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        errorEvacuationSalesTransactionDetailEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionDetailEntityCondition);

        SalesErrorSalesTransactionDetailInfoCondition salesErrorSalesTransactionDetailInfoEntityCondition =
                new SalesErrorSalesTransactionDetailInfoCondition();
        salesErrorSalesTransactionDetailInfoEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        salesErrorSalesTransactionDetailInfoEntityMapper
                .deleteByCondition(salesErrorSalesTransactionDetailInfoEntityCondition);

        ErrorEvacuationSalesTransactionDetailInfoCondition errorEvacuationSalesTransactionDetailInfoEntityCondition =
                new ErrorEvacuationSalesTransactionDetailInfoCondition();
        errorEvacuationSalesTransactionDetailInfoEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        errorEvacuationSalesTransactionDetailInfoEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionDetailInfoEntityCondition);

        SalesErrorSalesTransactionDiscountCondition salesErrorSalesTransactionDiscountEntityCondition =
                new SalesErrorSalesTransactionDiscountCondition();
        salesErrorSalesTransactionDiscountEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        salesErrorSalesTransactionDiscountEntityMapper
                .deleteByCondition(salesErrorSalesTransactionDiscountEntityCondition);

        ErrorEvacuationSalesTransactionDiscountCondition errorEvacuationSalesTransactionDiscountEntityCondition =
                new ErrorEvacuationSalesTransactionDiscountCondition();
        errorEvacuationSalesTransactionDiscountEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        errorEvacuationSalesTransactionDiscountEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionDiscountEntityCondition);

        SalesErrorSalesTransactionTaxCondition salesErrorSalesTransactionTaxEntityCondition =
                new SalesErrorSalesTransactionTaxCondition();
        salesErrorSalesTransactionTaxEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        salesErrorSalesTransactionTaxEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTaxEntityCondition);

        ErrorEvacuationSalesTransactionTaxCondition errorEvacuationSalesTransactionTaxEntityCondition =
                new ErrorEvacuationSalesTransactionTaxCondition();
        errorEvacuationSalesTransactionTaxEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        errorEvacuationSalesTransactionTaxEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTaxEntityCondition);

        SalesErrorSalesTransactionTenderCondition salesErrorSalesTransactionTenderEntityCondition =
                new SalesErrorSalesTransactionTenderCondition();
        salesErrorSalesTransactionTenderEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        salesErrorSalesTransactionTenderEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTenderEntityCondition);

        ErrorEvacuationSalesTransactionTenderCondition errorEvacuationSalesTransactionTenderEntityCondition =
                new ErrorEvacuationSalesTransactionTenderCondition();
        errorEvacuationSalesTransactionTenderEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        errorEvacuationSalesTransactionTenderEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTenderEntityCondition);

        SalesErrorSalesTransactionTenderInfoCondition salesErrorSalesTransactionTenderInfoEntityCondition =
                new SalesErrorSalesTransactionTenderInfoCondition();
        salesErrorSalesTransactionTenderInfoEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        salesErrorSalesTransactionTenderInfoEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTenderInfoEntityCondition);

        ErrorEvacuationSalesTransactionTenderInfoCondition errorEvacuationSalesTransactionTenderInfoEntityCondition =
                new ErrorEvacuationSalesTransactionTenderInfoCondition();
        errorEvacuationSalesTransactionTenderInfoEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        errorEvacuationSalesTransactionTenderInfoEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTenderInfoEntityCondition);

        SalesErrorSalesTransactionTotalAmountCondition salesErrorSalesTransactionTotalAmountEntityCondition =
                new SalesErrorSalesTransactionTotalAmountCondition();
        salesErrorSalesTransactionTotalAmountEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        salesErrorSalesTransactionTotalAmountEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTotalAmountEntityCondition);

        ErrorEvacuationSalesTransactionTotalAmountCondition errorEvacuationSalesTransactionTotalAmountEntityCondition =
                new ErrorEvacuationSalesTransactionTotalAmountCondition();
        errorEvacuationSalesTransactionTotalAmountEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        errorEvacuationSalesTransactionTotalAmountEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTotalAmountEntityCondition);
    }

    /**
     * Insert data into history table by data get from error table.
     * 
     * @param transactionImportData Transaction import data.
     * @param userId User id.
     */
    public void insertByTransactionId(TransactionImportData transactionImportData, String userId) {
        // TSalesErrorSalesOrderInformationEntity
        SalesErrorSalesOrderInformationCondition salesErrorSalesOrderInformationEntityCondition =
                new SalesErrorSalesOrderInformationCondition();
        salesErrorSalesOrderInformationEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        List<SalesErrorSalesOrderInformation> salesErrorOrderInformationEntityList =
                salesErrorSalesOrderInformationEntityMapper
                        .selectByCondition(salesErrorSalesOrderInformationEntityCondition);

        if (CollectionUtils.isNotEmpty(salesErrorOrderInformationEntityList)) {
            salesErrorOrderInformationEntityList.forEach(salesErrorOrderInformationEntity -> {
                AlterationHistoryOrderInformation alterationHistoryOrderInformationBeforeEntity =
                        historyTableDataConverter
                                .convertTAlterationHistoryOrderInformationEntityForInsertBeforeEdit(
                                        salesErrorOrderInformationEntity,
                                        transactionImportData.getSalesTransactionErrorId(), userId);
                alterationHistoryOrderInformationEntityMapper
                        .insertSelective(alterationHistoryOrderInformationBeforeEntity);
            });
        }

        // TSalesErrorSalesTransactionHeaderEntity
        SalesErrorSalesTransactionHeaderCondition salesErrorSalesTransactionHeaderEntityCondition =
                new SalesErrorSalesTransactionHeaderCondition();
        salesErrorSalesTransactionHeaderEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        List<SalesErrorSalesTransactionHeader> salesErrorSalesTransactionHeaderEntityList =
                salesErrorSalesTransactionHeaderEntityMapper
                        .selectByCondition(salesErrorSalesTransactionHeaderEntityCondition);
        if (CollectionUtils.isNotEmpty(salesErrorSalesTransactionHeaderEntityList)) {
            salesErrorSalesTransactionHeaderEntityList
                    .forEach(salesErrorSalesTransactionHeaderEntity -> {
                        AlterationHistorySalesTransactionHeader alterationHistorySalesTransactionHeaderEntity =
                                historyTableDataConverter
                                        .convertTAlterationHistorySalesTransactionHeaderEntityForInsertBeforeEdit(
                                                salesErrorSalesTransactionHeaderEntity,
                                                transactionImportData.getSalesTransactionErrorId(),
                                                userId);
                        alterationHistorySalesTransactionHeaderEntityMapper
                                .insertSelective(alterationHistorySalesTransactionHeaderEntity);
                    });
        }

        // TSalesErrorSalesTransactionDetailEntity
        SalesErrorSalesTransactionDetailCondition salesErrorSalesTransactionDetailEntityCondition =
                new SalesErrorSalesTransactionDetailCondition();
        salesErrorSalesTransactionDetailEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        List<SalesErrorSalesTransactionDetail> salesErrorSalesTransactionDetailEntityList =
                salesErrorSalesTransactionDetailEntityMapper
                        .selectByCondition(salesErrorSalesTransactionDetailEntityCondition);
        if (CollectionUtils.isNotEmpty(salesErrorSalesTransactionDetailEntityList)) {
            salesErrorSalesTransactionDetailEntityList
                    .forEach(salesErrorSalesTransactionDetailEntity -> {
                        AlterationHistorySalesTransactionDetail alterationHistorySalesTransactionDetailEntity =
                                historyTableDataConverter
                                        .convertTAlterationHistorySalesTransactionDetailEntityForInsertBeforeEdit(
                                                salesErrorSalesTransactionDetailEntity,
                                                transactionImportData.getSalesTransactionErrorId(),
                                                userId);
                        alterationHistorySalesTransactionDetailEntityMapper
                                .insertSelective(alterationHistorySalesTransactionDetailEntity);
                    });
        }

        // TSalesErrorSalesTransactionDetailInfoEntity
        SalesErrorSalesTransactionDetailInfoCondition salesErrorSalesTransactionDetailInfoEntityCondition =
                new SalesErrorSalesTransactionDetailInfoCondition();
        salesErrorSalesTransactionDetailInfoEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        List<SalesErrorSalesTransactionDetailInfo> salesErrorSalesTransactionDetailInfoEntityList =
                salesErrorSalesTransactionDetailInfoEntityMapper
                        .selectByCondition(salesErrorSalesTransactionDetailInfoEntityCondition);
        if (CollectionUtils.isNotEmpty(salesErrorSalesTransactionDetailInfoEntityList)) {
            salesErrorSalesTransactionDetailInfoEntityList
                    .forEach(salesErrorSalesTransactionDetailInfoEntity -> {
                        AlterationHistorySalesTransactionDetailInfo alterationHistorySalesTransactionDetailInfoEntity =
                                historyTableDataConverter
                                        .convertTAlterationHistorySalesTransactionDetailInfoEntityForInsertBeforeEdit(
                                                salesErrorSalesTransactionDetailInfoEntity,
                                                transactionImportData.getSalesTransactionErrorId(),
                                                userId);
                        alterationHistorySalesTransactionDetailInfoEntityMapper
                                .insertSelective(alterationHistorySalesTransactionDetailInfoEntity);
                    });
        }

        // TSalesErrorSalesTransactionDiscountEntity
        SalesErrorSalesTransactionDiscountCondition salesErrorSalesTransactionDiscountEntityCondition =
                new SalesErrorSalesTransactionDiscountCondition();
        salesErrorSalesTransactionDiscountEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        List<SalesErrorSalesTransactionDiscount> salesErrorSalesTransactionDiscountEntityList =
                salesErrorSalesTransactionDiscountEntityMapper
                        .selectByCondition(salesErrorSalesTransactionDiscountEntityCondition);
        if (CollectionUtils.isNotEmpty(salesErrorSalesTransactionDiscountEntityList)) {
            salesErrorSalesTransactionDiscountEntityList
                    .forEach(salesErrorSalesTransactionDiscountEntity -> {
                        AlterationHistorySalesTransactionDiscount alterationHistorySalesTransactionDiscountEntity =
                                historyTableDataConverter
                                        .convertTAlterationHistorySalesTransactionDiscountEntityForInsertNonBeforeEdit(
                                                salesErrorSalesTransactionDiscountEntity,
                                                transactionImportData.getSalesTransactionErrorId(),
                                                userId);
                        alterationHistorySalesTransactionDiscountEntityMapper
                                .insertSelective(alterationHistorySalesTransactionDiscountEntity);
                    });
        }

        // TSalesErrorSalesTransactionTaxEntity
        SalesErrorSalesTransactionTaxCondition salesErrorSalesTransactionTaxEntityCondition =
                new SalesErrorSalesTransactionTaxCondition();
        salesErrorSalesTransactionTaxEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        List<SalesErrorSalesTransactionTax> salesErrorSalesTransactionTaxEntityList =
                salesErrorSalesTransactionTaxEntityMapper
                        .selectByCondition(salesErrorSalesTransactionTaxEntityCondition);
        if (CollectionUtils.isNotEmpty(salesErrorSalesTransactionTaxEntityList)) {
            salesErrorSalesTransactionTaxEntityList.forEach(salesErrorSalesTransactionTaxEntity -> {
                AlterationHistorySalesTransactionTax alterationHistorySalesTransactionTaxEntity =
                        historyTableDataConverter
                                .convertTAlterationHistorySalesTransactionTaxEntityForInsertNonBeforeEdit(
                                        salesErrorSalesTransactionTaxEntity,
                                        transactionImportData.getSalesTransactionErrorId(), userId);
                alterationHistorySalesTransactionTaxEntityMapper
                        .insertSelective(alterationHistorySalesTransactionTaxEntity);
            });
        }

        // TSalesErrorSalesTransactionTenderEntity
        SalesErrorSalesTransactionTenderCondition salesErrorSalesTransactionTenderEntityCondition =
                new SalesErrorSalesTransactionTenderCondition();
        salesErrorSalesTransactionTenderEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        List<SalesErrorSalesTransactionTender> salesErrorSalesTransactionTenderEntityList =
                salesErrorSalesTransactionTenderEntityMapper
                        .selectByCondition(salesErrorSalesTransactionTenderEntityCondition);
        if (CollectionUtils.isNotEmpty(salesErrorSalesTransactionTenderEntityList)) {
            salesErrorSalesTransactionTenderEntityList
                    .forEach(salesErrorSalesTransactionTenderEntity -> {
                        AlterationHistorySalesTransactionTender alterationHistorySalesTransactionTenderEntity =
                                historyTableDataConverter
                                        .convertTAlterationHistorySalesTransactionTenderEntityForInsertBeforeEdit(
                                                salesErrorSalesTransactionTenderEntity,
                                                transactionImportData.getSalesTransactionErrorId(),
                                                userId);
                        alterationHistorySalesTransactionTenderEntityMapper
                                .insertSelective(alterationHistorySalesTransactionTenderEntity);
                    });
        }

        // TSalesErrorSalesTransactionTenderInfoEntity
        SalesErrorSalesTransactionTenderInfoCondition salesErrorSalesTransactionTenderInfoEntityCondition =
                new SalesErrorSalesTransactionTenderInfoCondition();
        salesErrorSalesTransactionTenderInfoEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        List<SalesErrorSalesTransactionTenderInfo> salesErrorSalesTransactionTenderInfoEntityList =
                salesErrorSalesTransactionTenderInfoEntityMapper
                        .selectByCondition(salesErrorSalesTransactionTenderInfoEntityCondition);
        if (CollectionUtils.isNotEmpty(salesErrorSalesTransactionTenderInfoEntityList)) {
            int subNumber = 0;
            for (SalesErrorSalesTransactionTenderInfo salesErrorSalesTransactionTenderInfoEntity : salesErrorSalesTransactionTenderInfoEntityList) {
                AlterationHistorySalesTransactionTenderInfo alterationHistorySalesTransactionTenderInfoEntity =
                        historyTableDataConverter
                                .convertTAlterationHistorySalesTransactionTenderInfoEntityForInsertBeforeEdit(
                                        salesErrorSalesTransactionTenderInfoEntity,
                                        transactionImportData.getSalesTransactionErrorId(), userId,
                                        ++subNumber);
                alterationHistorySalesTransactionTenderInfoEntityMapper
                        .insertSelective(alterationHistorySalesTransactionTenderInfoEntity);
            }
        }

        // TSalesErrorSalesTransactionTotalAmountEntity
        SalesErrorSalesTransactionTotalAmountCondition salesErrorSalesTransactionTotalAmountEntityCondition =
                new SalesErrorSalesTransactionTotalAmountCondition();
        salesErrorSalesTransactionTotalAmountEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        List<SalesErrorSalesTransactionTotalAmount> salesErrorSalesTransactionTotalAmountEntityList =
                salesErrorSalesTransactionTotalAmountEntityMapper
                        .selectByCondition(salesErrorSalesTransactionTotalAmountEntityCondition);
        if (CollectionUtils.isNotEmpty(salesErrorSalesTransactionTotalAmountEntityList)) {
            salesErrorSalesTransactionTotalAmountEntityList
                    .forEach(salesErrorSalesTransactionTotalAmountEntity -> {
                        AlterationHistorySalesTransactionTotalAmount alterationHistorySalesTransactionTotalAmountEntity =
                                historyTableDataConverter
                                        .convertTAlterationHistorySalesTransactionTotalAmountEntityForInsertBeforeEdit(
                                                salesErrorSalesTransactionTotalAmountEntity,
                                                transactionImportData.getSalesTransactionErrorId(),
                                                userId);
                        alterationHistorySalesTransactionTotalAmountEntityMapper.insertSelective(
                                alterationHistorySalesTransactionTotalAmountEntity);
                    });
        }
    }
}
