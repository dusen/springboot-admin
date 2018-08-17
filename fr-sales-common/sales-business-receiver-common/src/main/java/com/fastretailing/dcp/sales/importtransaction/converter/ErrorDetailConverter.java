/**
 * @(#)ErrorDetailConverter.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.common.constants.DBItem;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderTable;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTotalAmount;

/**
 * The class is used to convert data to sales transaction error detail table.
 */
@Component
public class ErrorDetailConverter {

    /** Common data processor. */
    @Autowired
    private CommonDataProcessor commonDataProcessor;

    /** Model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /** Error item table id. */
    private static final String ERROR_ITEM_ID_TABLE = "table_name";


    /**
     * Convert data to sales transaction error detail entity when error occurred on updateType.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorId Transaction data error id.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUpdateType(
            TransactionImportData transactionImportData, String salesTransactionErrorId,
            String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(transactionImportData, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(salesTransactionErrorId);
        salesTransactionErrorDetailEntity.setKeyInfo1(transactionImportData.getIntegratedOrderId());
        salesTransactionErrorDetailEntity.setErrorType(ErrorType.VALIDATION_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1("update_type");
        salesTransactionErrorDetailEntity.setErrorItemValue1(transactionImportData.getUpdateType());
        return salesTransactionErrorDetailEntity;
    }

    /**
     * Convert data to sales transaction error detail entity when error occurred on unique
     * constraints.
     * 
     * @param salesOrderInformationEntity Sales order information entity.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
            SalesOrderInformation salesOrderInformationEntity, String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(salesOrderInformationEntity, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity
                .setKeyInfo1(salesOrderInformationEntity.getIntegratedOrderId());
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_ORDER_INFORMATION);

        return salesTransactionErrorDetailEntity;
    }

    /**
     * Convert data to sales transaction error detail entity when error occurred on unique
     * constraints.
     * 
     * @param salesTransactionHeaderEntity Sales transaction header entity.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
            SalesTransactionHeader salesTransactionHeaderEntity, String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        modelMapper.map(salesTransactionHeaderEntity, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity
                .setKeyInfo1(salesTransactionHeaderEntity.getIntegratedOrderId());
        salesTransactionErrorDetailEntity
                .setKeyInfo2(salesTransactionHeaderEntity.getSalesTransactionId());
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_HEADER);
        return salesTransactionErrorDetailEntity;
    }

    /**
     * Convert data to sales transaction error detail entity when error occurred on unique
     * constraints.
     * 
     * @param salesTransactionDetailEntity Sale transaction detail entity.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
            SalesTransactionDetail salesTransactionDetailEntity, String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(salesTransactionDetailEntity, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity
                .setKeyInfo3(String.valueOf(salesTransactionDetailEntity.getDetailSubNumber()));
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_DETAIL);
        return salesTransactionErrorDetailEntity;
    }

    /**
     * Convert data to sales transaction error detail entity when error occurred on unique
     * constraints.
     * 
     * @param salesTransactionTenderEntity Sales transaction tender entity.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
            SalesTransactionTenderTable salesTransactionTenderEntity, String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(salesTransactionTenderEntity, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);

        salesTransactionErrorDetailEntity
                .setKeyInfo3(String.valueOf(salesTransactionTenderEntity.getTenderSubNumber()));
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_TENDER);
        return salesTransactionErrorDetailEntity;
    }

    /**
     * Convert data to sales transaction error detail entity when error occurred on unique
     * constraints.
     * 
     * @param salesTransactionTaxEntity Sales transaction tax entity.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
            SalesTransactionTax salesTransactionTaxEntity, String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(salesTransactionTaxEntity, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);

        salesTransactionErrorDetailEntity
                .setKeyInfo3(String.valueOf(salesTransactionTaxEntity.getTaxSubNumber()));
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_TAX);
        return salesTransactionErrorDetailEntity;
    }


    /**
     * Convert data to sales transaction error detail entity when error occurred on unique
     * constraints.
     * 
     * @param salesTransactionTotalAmountEntity Sales transaction total amount entity.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
            SalesTransactionTotalAmount salesTransactionTotalAmountEntity, String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(salesTransactionTotalAmountEntity, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);

        salesTransactionErrorDetailEntity.setKeyInfo3(
                String.valueOf(salesTransactionTotalAmountEntity.getTotalAmountSubNumber()));
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_TOTAL_AMOUNT);
        return salesTransactionErrorDetailEntity;
    }

    /**
     * Convert data to sales transaction error detail entity when error occurred on unique
     * constraints.
     * 
     * @param salesTransactionDiscountEntity Sales transaction discount entity.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
            SalesTransactionDiscount salesTransactionDiscountEntity, String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(salesTransactionDiscountEntity, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);

        salesTransactionErrorDetailEntity
                .setKeyInfo4(salesTransactionDiscountEntity.getPromotionType());
        salesTransactionErrorDetailEntity
                .setKeyInfo5(salesTransactionDiscountEntity.getPromotionNo());
        salesTransactionErrorDetailEntity
                .setKeyInfo6(salesTransactionDiscountEntity.getStoreDiscountType());
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_DISCOUNT);
        return salesTransactionErrorDetailEntity;
    }

    /**
     * Convert data to sales transaction error detail entity when error occurred on unique
     * constraints.
     * 
     * @param salesTransactionDetailEntity Sale transaction detail entity.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4(
            SalesTransactionDetail salesTransactionDetailEntity, String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(salesTransactionDetailEntity, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);

        salesTransactionErrorDetailEntity
                .setKeyInfo4(String.valueOf(salesTransactionDetailEntity.getDetailSubNumber()));
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_DETAIL);
        return salesTransactionErrorDetailEntity;
    }

    /**
     * Convert data to sales transaction error detail entity when error occurred on unique
     * constraints.
     * 
     * @param salesTransactionTaxEntity Sales transaction tax entity.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4(
            SalesTransactionTax salesTransactionTaxEntity, String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(salesTransactionTaxEntity, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);

        salesTransactionErrorDetailEntity
                .setKeyInfo4(String.valueOf(salesTransactionTaxEntity.getTaxSubNumber()));
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_TAX);
        return salesTransactionErrorDetailEntity;
    }

    /**
     * Convert data to sales transaction error detail entity when error occurred on unique
     * constraints.
     * 
     * @param salesTransactionTaxEntity Sales transaction tax entity.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5(
            SalesTransactionTax salesTransactionTaxEntity, String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(salesTransactionTaxEntity, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);

        salesTransactionErrorDetailEntity
                .setKeyInfo4(String.valueOf(salesTransactionTaxEntity.getDetailSubNumber()));
        salesTransactionErrorDetailEntity
                .setKeyInfo5(String.valueOf(salesTransactionTaxEntity.getTaxSubNumber()));
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_TAX);
        return salesTransactionErrorDetailEntity;
    }



    /**
     * Convert data to sales transaction error detail entity when error occurred on unique
     * constraints.
     * 
     * @param salesTransactionDiscountEntity Sales transaction discount entity.
     * @param userId User id.
     * @return TSalesTransactionErrorDetailEntity;
     */
    public SalesTransactionErrorDetail convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5(
            SalesTransactionDiscount salesTransactionDiscountEntity, String userId) {
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        modelMapper.map(salesTransactionDiscountEntity, salesTransactionErrorDetailEntity);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);


        salesTransactionErrorDetailEntity
                .setKeyInfo4(String.valueOf(salesTransactionDiscountEntity.getDetailSubNumber()));
        salesTransactionErrorDetailEntity
                .setKeyInfo5(salesTransactionDiscountEntity.getPromotionType());
        salesTransactionErrorDetailEntity
                .setKeyInfo6(salesTransactionDiscountEntity.getPromotionNo());
        salesTransactionErrorDetailEntity
                .setKeyInfo7(salesTransactionDiscountEntity.getStoreDiscountType());
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1(ERROR_ITEM_ID_TABLE);
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_DISCOUNT);
        return salesTransactionErrorDetailEntity;
    }
}
