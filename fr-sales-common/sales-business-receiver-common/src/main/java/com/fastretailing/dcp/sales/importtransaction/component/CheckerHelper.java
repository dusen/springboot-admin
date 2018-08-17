/**
 * @(#)CheckerHelper.java
 *
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.common.type.GeneralPurposeType;
import com.fastretailing.dcp.sales.importtransaction.converter.CommonDataProcessor;
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
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.StoreGeneralPurposeMasterCondition;
import com.fastretailing.dcp.sales.importtransaction.exception.TransactionConsistencyErrorException;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionErrorDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.StoreGeneralPurposeMasterMapper;

/**
 * The class is used for validation and business check.
 *
 */
@Component
public class CheckerHelper {

    /** Model tools parts. */
    @Autowired
    private ModelMapper modelMapper;

    /** Common data processor parts. */
    @Autowired
    private CommonDataProcessor commonDataProcessor;

    /**
     * Component for operating DB operations on the sales transaction error detail table.
     */
    @Autowired
    private SalesTransactionErrorDetailMapper salesTransactionErrorDetailEntityMapper;

    /**
     * Component for operating DB operations on the store general purpose table.
     */
    @Autowired
    private StoreGeneralPurposeMasterMapper storeGeneralPurposeMasterMapper;

    /** Code value is store. */
    private static final String CODE_VALUE_IS_STORE = "1";

    /**
     * Transaction error exception throw flag.
     */
    @Value("${sales-business-receiver.transaction-error-exception:false}")
    private boolean transactionErrorThrowExceptionFlag;

    /**
     * Insert sales transaction error.
     * 
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     * @throws TransactionConsistencyErrorException that transaction consistency error exception.
     */
    public void insertSalesTransactionError(
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity)
            throws TransactionConsistencyErrorException {
        /** Transaction error throw exception by transaction error exception setting flag. */
        if (transactionErrorThrowExceptionFlag) {
            throw new TransactionConsistencyErrorException(salesTransactionErrorDetailEntity);
        }
        TableCommonItem tableCommonItem = commonDataProcessor
                .getTableCommonItemForInsert(salesTransactionErrorDetailEntity.getCreateUserId());
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);

        salesTransactionErrorDetailEntityMapper.insertSelective(salesTransactionErrorDetailEntity);
    }



    /**
     * Map level2 transaction.
     * 
     * @param transaction Transaction.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel2Transaction(Transaction transaction,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        modelMapper.map(transaction, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity.setKeyInfo2(transaction.getSalesTransactionId());
        salesTransactionErrorDetailEntity.setKeyInfo3(null);
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Map level3 non item detail.
     * 
     * @param nonItemDetail Non item detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel3NonItemDetail(NonItemDetail nonItemDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        modelMapper.map(nonItemDetail, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity
                .setKeyInfo3(getValueOfInteger(nonItemDetail.getNonItemDetailNumber()));
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Map level3 transaction item detail.
     * 
     * @param transactionItemDetail Transaction item detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel3TransactionItemDetail(TransactionItemDetail transactionItemDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        modelMapper.map(transactionItemDetail, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity
                .setKeyInfo3(getValueOfInteger(transactionItemDetail.getDetailSubNumber()));
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Map level4 non item detail.
     * 
     * @param nonItemDetail Non item detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel4NonItemDetail(NonItemDetail nonItemDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        modelMapper.map(nonItemDetail, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity
                .setKeyInfo4(getValueOfInteger(nonItemDetail.getDetailSubNumber()));
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Map level5 non item info.
     * 
     * @param nonItemInfo Non item info.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel5NonItemInfo(NonItemInfo nonItemInfo,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        salesTransactionErrorDetailEntity
                .setKeyInfo5(getValueOfInteger(nonItemInfo.getItemDetailSubNumber()));
    }

    /**
     * Map level5 non item discount detail.
     * 
     * @param nonItemDiscountDetail Non item discount detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel5NonItemDiscountDetail(NonItemDiscountDetail nonItemDiscountDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        salesTransactionErrorDetailEntity.setKeyInfo5(
                getValueOfInteger(nonItemDiscountDetail.getNonItemDiscountDetailSubNumber()));
    }

    /**
     * Map level5 non item tax detail.
     * 
     * @param nonItemTaxDetail Non item tax detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel5NonItemTaxDetail(NonItemTaxDetail nonItemTaxDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        salesTransactionErrorDetailEntity
                .setKeyInfo5(getValueOfInteger(nonItemTaxDetail.getNonItemTaxSubNumber()));
    }

    /**
     * Map level4 item discount.
     * 
     * @param itemDiscount Item discount.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel4ItemDiscount(ItemDiscount itemDiscount,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        salesTransactionErrorDetailEntity
                .setKeyInfo4(getValueOfInteger(itemDiscount.getItemDiscountDetailSubNumber()));
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Map level4 item tax detail.
     * 
     * @param itemTaxDetail Item tax detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel4ItemTaxDetail(ItemTaxDetail itemTaxDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        salesTransactionErrorDetailEntity
                .setKeyInfo4(getValueOfInteger(itemTaxDetail.getItemTaxSubNumber()));
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Map level4 non item info.
     * 
     * @param nonItemInfo Non item info.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel4NonItemInfo(NonItemInfo nonItemInfo,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        salesTransactionErrorDetailEntity
                .setKeyInfo4(getValueOfInteger(nonItemInfo.getItemDetailSubNumber()));
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Map level4 non item discount detail.
     * 
     * @param nonItemDiscountDetail Non item discount detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel4NonItemDiscountDetail(NonItemDiscountDetail nonItemDiscountDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        salesTransactionErrorDetailEntity.setKeyInfo4(
                getValueOfInteger(nonItemDiscountDetail.getNonItemDiscountDetailSubNumber()));
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Map level4 non item tax detail.
     * 
     * @param nonItemTaxDetail Non item tax detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel4NonItemTaxDetail(NonItemTaxDetail nonItemTaxDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        salesTransactionErrorDetailEntity
                .setKeyInfo4(getValueOfInteger(nonItemTaxDetail.getNonItemTaxSubNumber()));
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Map level3 sales transaction tender.
     * 
     * @param salesTransactionTender Sales transaction tender.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel3SalesTransactionTender(SalesTransactionTender salesTransactionTender,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        salesTransactionErrorDetailEntity
                .setKeyInfo3(getValueOfInteger(salesTransactionTender.getTenderSubNumber()));
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Map level3 sales transaction tax detail.
     * 
     * @param salesTransactionTaxDetail Sales transaction tax detail.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel3SalesTransactionTaxDetail(
            SalesTransactionTaxDetail salesTransactionTaxDetail,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        salesTransactionErrorDetailEntity
                .setKeyInfo3(getValueOfInteger(salesTransactionTaxDetail.getTaxSubNumber()));
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Map level3 sales transaction total.
     * 
     * @param salesTransactionTotal Sales transaction total.
     * @param salesTransactionErrorDetailEntity Sales transaction error detail entity.
     */
    public void mapLevel3SalesTransactionTotal(SalesTransactionTotal salesTransactionTotal,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity) {
        salesTransactionErrorDetailEntity
                .setKeyInfo3(getValueOfInteger(salesTransactionTotal.getTotalAmountSubNumber()));
        salesTransactionErrorDetailEntity.setKeyInfo4(null);
        salesTransactionErrorDetailEntity.setKeyInfo5(null);
    }

    /**
     * Check is store.
     * 
     * @param storeCode Store code.
     * @return true if count equals 0, false if not.
     */
    public boolean isStore(String storeCode) {
        if (StringUtils.isEmpty(storeCode)) {
            return false;
        }
        StoreGeneralPurposeMasterCondition example = new StoreGeneralPurposeMasterCondition();
        example.createCriteria()
                .andStoreCodeEqualTo(storeCode)
                .andGeneralPurposeTypeEqualTo(GeneralPurposeType.EC_FLAG.getGeneralPurposeType())
                .andCodeEqualTo(CODE_VALUE_IS_STORE);
        if (0 == storeGeneralPurposeMasterMapper.countByCondition(example)) {
            return true;
        }
        return false;
    }

    /**
     * Get value of integer.
     * 
     * @param value Integer value.
     * @return String value of the integer.
     */
    private String getValueOfInteger(Integer value) {
        return value == null ? null : String.valueOf(value);
    }
}
