/**
 * @(#)SalesTransactionErrorListServiceHelper.java
 *
 *                                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.util.SystemDateTime;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesOrderInformationOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesOrderInformationOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionDiscountOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionDiscountOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionHeaderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionHeaderOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionTaxOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionTaxOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionTotalAmountOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionTotalAmountOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesOrderInformationOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesOrderInformationOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionDetailAndInfo;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionDiscountOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionDiscountOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionHeaderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionHeaderOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionTaxOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionTaxOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionTenderAndInfo;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionTotalAmountOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionTotalAmountOptionalCondition;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesOrderInformationOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesTransactionDiscountOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesTransactionHeaderOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesTransactionTaxOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesTransactionTotalAmountOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesErrorSalesOrderInformationOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesErrorSalesTransactionDiscountOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesErrorSalesTransactionHeaderOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesErrorSalesTransactionTaxOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesErrorSalesTransactionTotalAmountOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionErrorDetailAndInfoMapper;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.ItemDetailCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.ItemDetailDiscountCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.ItemDetailTaxCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.OrderInformationCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.SalesTransactionHeaderCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.SalesTransactionPaymentInformationCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.SalesTransactionTaxCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.SalesTransactionTotalCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.TableCommonItem;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.util.ScreenCommonUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * Sales transaction error list service helper.
 */
@Service
@Slf4j
public class SalesTransactionErrorListServiceHelper {

    /** Order information csv name. */
    public static final String ORDER_INFORMATION_CSV_NAME = "OrderInformation#";

    /** Sales transaction header csv name. */
    public static final String SALES_TRANSACTION_HEADER_CSV_NAME = "SalesTransactionHeader#";

    /** Item detail csv name. */
    public static final String ITEM_DETAIL_CSV_NAME = "ItemDetail#";

    /** Item detail discount csv name. */
    public static final String ITEM_DETAIL_DISCOUNT_CSV_NAME = "ItemDetail_discount#";

    /** Item detail tax csv name. */
    public static final String ITEM_DETAIL_TAX_CSV_NAME = "ItemDetail_tax#";

    /** Sales transaction tax csv name. */
    public static final String SALES_TRANSACTION_TAX_CSV_NAME = "SalesTransactionTax#";

    /** Sales transaction payment csv name. */
    public static final String SALES_TRANSACTION_PAYMENT_CSV_NAME = "SalesTransactionPayment#";

    /** Sales transaction total csv name. */
    public static final String SALES_TRANSACTION_TOTAL_CSV_NAME = "SalesTransactionTotal#";

    /** Zip file name. */
    public static final String ZIP_FILE_NAME = "TransactionSalesInformationList";

    /** Locale message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** Create program id. */
    public static final String PROGRAM_ID = "SLS1300104";

    /** Sales error sales order information mapper. */
    @Autowired
    private SalesErrorSalesOrderInformationOptionalMapper salesErrorSalesOrderInformationOptionalMapper;

    /** Sales error sales transaction header mapper. */
    @Autowired
    private SalesErrorSalesTransactionHeaderOptionalMapper salesErrorSalesTransactionHeaderOptionalMapper;

    /** Sales error sales transaction detail information mapper. */
    @Autowired
    private SalesErrorSalesTransactionDiscountOptionalMapper salesErrorSalesTransactionDiscountOptionalMapper;

    /** Error evacuation sales transaction discount mapper. */
    @Autowired
    private ErrorEvacuationSalesTransactionDiscountOptionalMapper errorEvacuationSalesTransactionDiscountOptionalMapper;

    /** Sales error sales transaction detail information mapper. */
    @Autowired
    private SalesErrorSalesTransactionTaxOptionalMapper salesErrorSalesTransactionTaxListOptionalMapper;

    /** Error evacuation sales transaction tax mapper. */
    @Autowired
    private ErrorEvacuationSalesTransactionTaxOptionalMapper errorEvacuationSalesTransactionTaxOptionalMapper;

    /** Sales transaction error detail and info mapper. */
    @Autowired
    private SalesTransactionErrorDetailAndInfoMapper salesTransactionErrorDetailAndInfoMapper;

    /** Error evacuation sales order information mapper. */
    @Autowired
    private ErrorEvacuationSalesOrderInformationOptionalMapper errorEvacuationSalesOrderInformationOptionalMapper;

    /** Sales error sales transaction total amount mapper. */
    @Autowired
    private SalesErrorSalesTransactionTotalAmountOptionalMapper salesErrorSalesTransactionTotalAmountOptionalMapper;

    /** Error evacuation sales transaction total amount mapper. */
    @Autowired
    private ErrorEvacuationSalesTransactionTotalAmountOptionalMapper errorEvacuationSalesTransactionTotalAmountOptionalMapper;

    /** Error evacuation sales transaction header mapper. */
    @Autowired
    private ErrorEvacuationSalesTransactionHeaderOptionalMapper errorEvacuationSalesTransactionHeaderOptionalMapper;

    /** Model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /** UTC clock component. */
    @Autowired
    private SystemDateTime systemDateTime;

    /** Sales transaction sub number asc. */
    public static final String SALES_TRANSACTION_SUB_NUMBER_ASC =
            "sales_transaction_sub_number asc";

    /** Item discount sub number asc. */
    public static final String ITEM_DISCOUNT_SUB_NUMBER_ASC = "item_discount_sub_number asc";

    /** Tax sub number asc. */
    public static final String TAX_SUB_NUMBER_ASC = "tax_sub_number asc";

    /** Total amount sub number asc. */
    public static final String TOTAL_AMOUNT_SUB_NUMBER_ASC = "total_amount_sub_number asc";

    /** Transaction id asc. */
    public static final String TRANSACTION_ID_ASC = "transaction_id asc";

    /**
     * Create table common item for insert.
     * 
     * @param userId User id.
     * @return Table common item.
     */
    public TableCommonItem createTableCommonItemForInsert(String userId) {
        TableCommonItem tableCommonItem = new TableCommonItem();
        OffsetDateTime now = systemDateTime.now();
        tableCommonItem.setCreateUserId(userId);
        tableCommonItem.setCreateProgramId(PROGRAM_ID);
        tableCommonItem.setCreateDatetime(now);
        tableCommonItem.setUpdateUserId(userId);
        tableCommonItem.setUpdateProgramId(PROGRAM_ID);
        tableCommonItem.setUpdateDatetime(now);
        return tableCommonItem;
    }

    /**
     * Throw business exception.
     * 
     * @param messageId Message id.
     */
    public void throwBusinessException(String messageId) {
        throw new BusinessException(ScreenCommonUtility.createResultObject(localeMessageSource,
                ErrorName.Business.BUSINESS_CHECK_ERROR, messageId, messageId));
    }

    /**
     * Get order information list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Order information csv list.
     */
    public List<OrderInformationCsv> getErrorOrderInformationList(String salesTransactionErrorId) {
        SalesErrorSalesOrderInformationOptionalCondition errorInformationCondition =
                new SalesErrorSalesOrderInformationOptionalCondition();
        errorInformationCondition.createCriteria().andTransactionIdEqualTo(salesTransactionErrorId);
        List<SalesErrorSalesOrderInformationOptional> salesErrorSalesOrderInformationList =
                salesErrorSalesOrderInformationOptionalMapper
                        .selectByCondition(errorInformationCondition);
        List<OrderInformationCsv> orderInformationCsvList =
                salesErrorSalesOrderInformationList.stream().map(orderinfo -> {
                    OrderInformationCsv orderInformationCsv = new OrderInformationCsv();
                    modelMapper.map(orderinfo, orderInformationCsv);
                    orderInformationCsv.setSalesTransactionErrorId(salesTransactionErrorId);
                    return orderInformationCsv;
                }).collect(Collectors.toList());
        modelMapper.map(salesErrorSalesOrderInformationList, orderInformationCsvList);
        return orderInformationCsvList;
    }

    /**
     * Get error sales transaction header list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Sales transaction header csv list.
     */
    public List<SalesTransactionHeaderCsv> getErrorSalesTransactionHeaderList(
            String salesTransactionErrorId) {

        SalesErrorSalesTransactionHeaderOptionalCondition salesErrorSalesTransactionHeaderCondition =
                new SalesErrorSalesTransactionHeaderOptionalCondition();
        salesErrorSalesTransactionHeaderCondition.createCriteria()
                .andTransactionIdEqualTo(salesTransactionErrorId);
        salesErrorSalesTransactionHeaderCondition
                .setOrderByClause(SALES_TRANSACTION_SUB_NUMBER_ASC);

        List<SalesErrorSalesTransactionHeaderOptional> salesErrorSalesTransactionHeaderList =
                salesErrorSalesTransactionHeaderOptionalMapper
                        .selectByCondition(salesErrorSalesTransactionHeaderCondition);
        return salesErrorSalesTransactionHeaderList.stream().map(header -> {
            SalesTransactionHeaderCsv salesTransactionHeaderCsv = new SalesTransactionHeaderCsv();
            modelMapper.map(header, salesTransactionHeaderCsv);
            return salesTransactionHeaderCsv;
        }).collect(Collectors.toList());
    }

    /**
     * Get error evacuation sales transaction header list..
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Sales transaction header csv list.
     */
    public List<SalesTransactionHeaderCsv> getErrorEvacuationSalesTransactionHeaderList(
            String salesTransactionErrorId) {

        ErrorEvacuationSalesTransactionHeaderOptionalCondition errorEvacuationSalesTransactionHeaderCondition =
                new ErrorEvacuationSalesTransactionHeaderOptionalCondition();
        errorEvacuationSalesTransactionHeaderCondition.createCriteria()
                .andTransactionIdEqualTo(salesTransactionErrorId);
        errorEvacuationSalesTransactionHeaderCondition
                .setOrderByClause(SALES_TRANSACTION_SUB_NUMBER_ASC);

        List<ErrorEvacuationSalesTransactionHeaderOptional> salesErrorEvacuationSalesTransactionHeaders =
                errorEvacuationSalesTransactionHeaderOptionalMapper
                        .selectByCondition(errorEvacuationSalesTransactionHeaderCondition);
        return salesErrorEvacuationSalesTransactionHeaders.stream().map(header -> {
            SalesTransactionHeaderCsv salesTransactionHeaderCsv = new SalesTransactionHeaderCsv();
            modelMapper.map(header, salesTransactionHeaderCsv);
            return salesTransactionHeaderCsv;
        }).collect(Collectors.toList());
    }

    /**
     * Get error sales transaction detail and information list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Item detail csv list.
     */
    public List<ItemDetailCsv> getSalesErrorSalesTransactionDetailAndInformationList(
            String salesTransactionErrorId) {
        List<SalesErrorSalesTransactionDetailAndInfo> salesTransactionErrorDetailAndInfoList =
                salesTransactionErrorDetailAndInfoMapper.getDetailAndInfo(salesTransactionErrorId);
        return salesTransactionErrorDetailAndInfoList.stream().map(detail -> {
            ItemDetailCsv itemDetailCsv = new ItemDetailCsv();
            modelMapper.map(detail, itemDetailCsv);
            return itemDetailCsv;
        }).collect(Collectors.toList());
    }

    /**
     * Get error evacuation transaction detail and information list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Item detail csv list.
     */
    public List<ItemDetailCsv> getErrorEvacuationSalesTransactionDetailAndInformationList(
            String salesTransactionErrorId) {
        List<SalesErrorSalesTransactionDetailAndInfo> salesTransactionErrorDetailAndInfoList =
                salesTransactionErrorDetailAndInfoMapper
                        .getErrorEvacuationDetailAndInfo(salesTransactionErrorId);
        return salesTransactionErrorDetailAndInfoList.stream().map(detail -> {
            ItemDetailCsv itemDetailCsv = new ItemDetailCsv();
            modelMapper.map(detail, itemDetailCsv);
            return itemDetailCsv;
        }).collect(Collectors.toList());
    }

    /**
     * Get error sales transaction discount list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Item detail discount csv list.
     */
    public List<ItemDetailDiscountCsv> getSalesErrorSalesTransactionDiscountList(
            String salesTransactionErrorId) {
        SalesErrorSalesTransactionDiscountOptionalCondition salesErrorSalesTransactionDiscountCondition =
                new SalesErrorSalesTransactionDiscountOptionalCondition();
        salesErrorSalesTransactionDiscountCondition.createCriteria()
                .andTransactionIdEqualTo(salesTransactionErrorId);
        salesErrorSalesTransactionDiscountCondition.setOrderByClause(ITEM_DISCOUNT_SUB_NUMBER_ASC);
        List<SalesErrorSalesTransactionDiscountOptional> salesErrorSalesTransactionDiscountList =
                salesErrorSalesTransactionDiscountOptionalMapper
                        .selectByCondition(salesErrorSalesTransactionDiscountCondition);
        return salesErrorSalesTransactionDiscountList.stream().map(discount -> {
            ItemDetailDiscountCsv itemDetailDiscountCsv = new ItemDetailDiscountCsv();
            modelMapper.map(discount, itemDetailDiscountCsv);
            return itemDetailDiscountCsv;
        }).collect(Collectors.toList());
    }

    /**
     * Get error evacuation transaction discount list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Item detail discount csv list.
     */
    public List<ItemDetailDiscountCsv> getErrorEvacuationSalesTransactionDiscountList(
            String salesTransactionErrorId) {
        ErrorEvacuationSalesTransactionDiscountOptionalCondition errorEvacuationSalesTransactionDiscountCondition =
                new ErrorEvacuationSalesTransactionDiscountOptionalCondition();
        errorEvacuationSalesTransactionDiscountCondition.createCriteria()
                .andTransactionIdEqualTo(salesTransactionErrorId);
        errorEvacuationSalesTransactionDiscountCondition
                .setOrderByClause(ITEM_DISCOUNT_SUB_NUMBER_ASC);
        List<ErrorEvacuationSalesTransactionDiscountOptional> errorEvacuationSalesTransactionDiscountList =
                errorEvacuationSalesTransactionDiscountOptionalMapper
                        .selectByCondition(errorEvacuationSalesTransactionDiscountCondition);
        return errorEvacuationSalesTransactionDiscountList.stream().map(discount -> {
            ItemDetailDiscountCsv itemDetailDiscountCsv = new ItemDetailDiscountCsv();
            modelMapper.map(discount, itemDetailDiscountCsv);
            return itemDetailDiscountCsv;
        }).collect(Collectors.toList());
    }

    /**
     * Get error sales transaction tax list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Item detail tax csv.
     */
    public List<ItemDetailTaxCsv> getSalesErrorSalesTransactionTaxList(
            String salesTransactionErrorId) {
        SalesErrorSalesTransactionTaxOptionalCondition salesErrorSalesTransactionTaxListCondition =
                new SalesErrorSalesTransactionTaxOptionalCondition();
        salesErrorSalesTransactionTaxListCondition.createCriteria()
                .andTransactionIdEqualTo(salesTransactionErrorId)
                .andDetailSubNumberNotEqualTo(0);
        salesErrorSalesTransactionTaxListCondition.setOrderByClause(TAX_SUB_NUMBER_ASC);
        List<SalesErrorSalesTransactionTaxOptional> salesErrorSalesTransactionTaxList =
                salesErrorSalesTransactionTaxListOptionalMapper
                        .selectByCondition(salesErrorSalesTransactionTaxListCondition);
        return salesErrorSalesTransactionTaxList.stream().map(tax -> {
            ItemDetailTaxCsv itemDetailTaxCsv = new ItemDetailTaxCsv();
            modelMapper.map(tax, itemDetailTaxCsv);
            return itemDetailTaxCsv;
        }).collect(Collectors.toList());
    }

    /**
     * Get error sales transaction tax list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Item detail tax csv.
     */
    public List<ItemDetailTaxCsv> getErrorEvacuationSalesTransactionTaxList(
            String salesTransactionErrorId) {
        ErrorEvacuationSalesTransactionTaxOptionalCondition errorEvacuationSalesTransactionTaxCondition =
                new ErrorEvacuationSalesTransactionTaxOptionalCondition();
        errorEvacuationSalesTransactionTaxCondition.createCriteria()
                .andTransactionIdEqualTo(salesTransactionErrorId)
                .andDetailSubNumberNotEqualTo(0);
        errorEvacuationSalesTransactionTaxCondition.setOrderByClause(TAX_SUB_NUMBER_ASC);
        List<ErrorEvacuationSalesTransactionTaxOptional> errorEvacuationSalesTransactionTaxList =
                errorEvacuationSalesTransactionTaxOptionalMapper
                        .selectByCondition(errorEvacuationSalesTransactionTaxCondition);
        List<ItemDetailTaxCsv> salesTransactionTaxCsvList =
                errorEvacuationSalesTransactionTaxList.stream().map(tax -> {
                    ItemDetailTaxCsv itemDetailTaxCsv = new ItemDetailTaxCsv();
                    modelMapper.map(tax, itemDetailTaxCsv);
                    return itemDetailTaxCsv;
                }).collect(Collectors.toList());
        return salesTransactionTaxCsvList;
    }

    /**
     * Get error evacuation transaction tax list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Item detail tax csv list.
     */
    public List<SalesTransactionTaxCsv> getErrorEvacuationTransactionTaxList(
            String salesTransactionErrorId) {
        ErrorEvacuationSalesTransactionTaxOptionalCondition errorEvacuationSalesTransactionTaxCondition =
                new ErrorEvacuationSalesTransactionTaxOptionalCondition();
        errorEvacuationSalesTransactionTaxCondition.createCriteria()
                .andTransactionIdEqualTo(salesTransactionErrorId)
                .andDetailSubNumberEqualTo(0);
        errorEvacuationSalesTransactionTaxCondition.setOrderByClause(TAX_SUB_NUMBER_ASC);
        List<ErrorEvacuationSalesTransactionTaxOptional> errorEvacuationSalesTransactionTaxList =
                errorEvacuationSalesTransactionTaxOptionalMapper
                        .selectByCondition(errorEvacuationSalesTransactionTaxCondition);
        List<SalesTransactionTaxCsv> salesTransactionTaxCsvList =
                errorEvacuationSalesTransactionTaxList.stream().map(tax -> {
                    SalesTransactionTaxCsv salesTransactionTaxCsv = new SalesTransactionTaxCsv();
                    modelMapper.map(tax, salesTransactionTaxCsv);
                    return salesTransactionTaxCsv;
                }).collect(Collectors.toList());
        return salesTransactionTaxCsvList;
    }

    /**
     * Get sales transaction tax list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Sales transaction tax csv list.
     */
    public List<SalesTransactionTaxCsv> getTransactionTaxList(String salesTransactionErrorId) {
        SalesErrorSalesTransactionTaxOptionalCondition salesErrorSalesTransactionTaxListCondition =
                new SalesErrorSalesTransactionTaxOptionalCondition();
        salesErrorSalesTransactionTaxListCondition.createCriteria()
                .andTransactionIdEqualTo(salesTransactionErrorId)
                .andDetailSubNumberEqualTo(0);
        salesErrorSalesTransactionTaxListCondition.setOrderByClause(TAX_SUB_NUMBER_ASC);
        List<SalesErrorSalesTransactionTaxOptional> salesErrorSalesTransactionTaxList =
                salesErrorSalesTransactionTaxListOptionalMapper
                        .selectByCondition(salesErrorSalesTransactionTaxListCondition);
        List<SalesTransactionTaxCsv> salesTransactionTaxCsvList =
                salesErrorSalesTransactionTaxList.stream().map(tax -> {
                    SalesTransactionTaxCsv salesTransactionTaxCsv = new SalesTransactionTaxCsv();
                    modelMapper.map(tax, salesTransactionTaxCsv);
                    return salesTransactionTaxCsv;
                }).collect(Collectors.toList());
        return salesTransactionTaxCsvList;
    }



    /**
     * Get error sales transaction tender and information list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Sales transaction payment csv list.
     */
    public List<SalesTransactionPaymentInformationCsv> getSalesErrorSalesTransactionTenderAndInformationList(
            String salesTransactionErrorId) {
        List<SalesErrorSalesTransactionTenderAndInfo> salesTransactionErrorTenderAndInfoList =
                salesTransactionErrorDetailAndInfoMapper.getTenderAndInfo(salesTransactionErrorId);
        List<SalesTransactionPaymentInformationCsv> salesTransactionTenderCsvList =
                salesTransactionErrorTenderAndInfoList.stream().map(detail -> {
                    SalesTransactionPaymentInformationCsv salesTransactionPaymentCsv =
                            new SalesTransactionPaymentInformationCsv();
                    modelMapper.map(detail, salesTransactionPaymentCsv);
                    return salesTransactionPaymentCsv;
                }).collect(Collectors.toList());
        return salesTransactionTenderCsvList;
    }

    /**
     * Get error evacuation transaction tender and information list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Sales transaction payment csv list.
     */
    public List<SalesTransactionPaymentInformationCsv> getErrorEvacuationSalesTransactionTenderAndInformationList(
            String salesTransactionErrorId) {
        List<SalesErrorSalesTransactionTenderAndInfo> salesTransactionErrorTenderAndInfoList =
                salesTransactionErrorDetailAndInfoMapper
                        .getErrorEvacuationTenderAndInfo(salesTransactionErrorId);
        List<SalesTransactionPaymentInformationCsv> salesTransactionTenderCsvList =
                salesTransactionErrorTenderAndInfoList.stream().map(detail -> {
                    SalesTransactionPaymentInformationCsv salesTransactionPaymentCsv =
                            new SalesTransactionPaymentInformationCsv();
                    modelMapper.map(detail, salesTransactionPaymentCsv);
                    return salesTransactionPaymentCsv;
                }).collect(Collectors.toList());
        return salesTransactionTenderCsvList;
    }

    /**
     * Get error sales transaction total list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Sales transaction total csv.
     */
    public List<SalesTransactionTotalCsv> getTransactionTotalList(String salesTransactionErrorId) {
        SalesErrorSalesTransactionTotalAmountOptionalCondition salesErrorSalesTransactionTotalAmountCondition =
                new SalesErrorSalesTransactionTotalAmountOptionalCondition();
        salesErrorSalesTransactionTotalAmountCondition.createCriteria()
                .andTransactionIdEqualTo(salesTransactionErrorId);
        salesErrorSalesTransactionTotalAmountCondition
                .setOrderByClause(TOTAL_AMOUNT_SUB_NUMBER_ASC);
        List<SalesErrorSalesTransactionTotalAmountOptional> salesErrorSalesTransactionTotalList =
                salesErrorSalesTransactionTotalAmountOptionalMapper
                        .selectByCondition(salesErrorSalesTransactionTotalAmountCondition);
        List<SalesTransactionTotalCsv> salesTransactionTotalCsvList =
                salesErrorSalesTransactionTotalList.stream().map(total -> {
                    SalesTransactionTotalCsv salesTransactionTotalCsv =
                            new SalesTransactionTotalCsv();
                    modelMapper.map(total, salesTransactionTotalCsv);
                    return salesTransactionTotalCsv;
                }).collect(Collectors.toList());
        return salesTransactionTotalCsvList;
    }

    /**
     * Get error evacuation transaction total list.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Sales transaction total csv.
     */
    public List<SalesTransactionTotalCsv> getErrorEvacuationTransactionTotalList(
            String salesTransactionErrorId) {
        ErrorEvacuationSalesTransactionTotalAmountOptionalCondition errorEvacuationSalesTransactionTotalAmountCondition =
                new ErrorEvacuationSalesTransactionTotalAmountOptionalCondition();
        errorEvacuationSalesTransactionTotalAmountCondition.createCriteria()
                .andTransactionIdEqualTo(salesTransactionErrorId);
        errorEvacuationSalesTransactionTotalAmountCondition
                .setOrderByClause(TOTAL_AMOUNT_SUB_NUMBER_ASC);
        List<ErrorEvacuationSalesTransactionTotalAmountOptional> errorEvacuationSalesTransactionTotalList =
                errorEvacuationSalesTransactionTotalAmountOptionalMapper
                        .selectByCondition(errorEvacuationSalesTransactionTotalAmountCondition);
        List<SalesTransactionTotalCsv> salesTransactionTotalCsvList =
                errorEvacuationSalesTransactionTotalList.stream().map(total -> {
                    SalesTransactionTotalCsv salesTransactionTotalCsv =
                            new SalesTransactionTotalCsv();
                    modelMapper.map(total, salesTransactionTotalCsv);
                    return salesTransactionTotalCsv;
                }).collect(Collectors.toList());
        return salesTransactionTotalCsvList;
    }

    /**
     * Zip files.
     * 
     * @param srcFiles Resource files.
     * @param zipFile Zip File.
     */
    public void zipFiles(File[] srcFiles, File zipFile) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);) {
            ZipEntry zipEntry = null;
            for (int i = 0; i < srcFiles.length; i++) {
                try (FileInputStream fileInputStream = new FileInputStream(srcFiles[i])) {
                    zipEntry = new ZipEntry(
                            srcFiles[i].getName().substring(0, srcFiles[i].getName().indexOf("#"))
                                    + srcFiles[i].getName()
                                            .substring(srcFiles[i].getName().lastIndexOf(".")));
                    zipOutputStream.putNextEntry(zipEntry);
                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = fileInputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, len);
                    }
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throwBusinessException(MessagePrefix.E_SLS_66000103_DOWN_FAILED);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throwBusinessException(MessagePrefix.E_SLS_66000103_DOWN_FAILED);
        }
    }

    /**
     * Get error evacuation sales order information condition.
     * 
     * @param salesTransactionErrorId Sales transaction error id.
     * @return Error evacuation sales order information condition.
     */
    public List<ErrorEvacuationSalesOrderInformationOptional> getErrorEvacuationSalesOrderInformationCondition(
            String salesTransactionErrorId) {
        ErrorEvacuationSalesOrderInformationOptionalCondition errorEvacuationSalesOrderInformationCondition =
                new ErrorEvacuationSalesOrderInformationOptionalCondition();
        errorEvacuationSalesOrderInformationCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(salesTransactionErrorId);
        errorEvacuationSalesOrderInformationCondition.setOrderByClause(TRANSACTION_ID_ASC);
        List<ErrorEvacuationSalesOrderInformationOptional> errorEvacuationSalesOrderInformationList =
                errorEvacuationSalesOrderInformationOptionalMapper
                        .selectByCondition(errorEvacuationSalesOrderInformationCondition);
        return errorEvacuationSalesOrderInformationList;
    }
}
