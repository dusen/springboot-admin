/**
 * @(#)InsertSalesTransactionHistoryImpl.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fastretailing.dcp.sales.importtransaction.converter.CommonDataProcessor;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHistory;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionHistoryMapper;

/**
 * The class is used to insert data to transaction history table.
 *
 */
@Service
public class InsertSalesTransactionHistoryImpl implements InsertSalesTransactionHistory {

    /**
     * Component for operating DB operations on the sales transaction history table.
     */
    @Autowired
    private SalesTransactionHistoryMapper salesTransactionHistoryEntityMapper;

    /** Common data processor parts. */
    @Autowired
    private CommonDataProcessor commonDataProcessor;

    /** Model tools parts. */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertIntoTSalesTransactionHistoryTable(String transactionImportData,
            String transactionId, String userId) {
        SalesTransactionHistory salesTransactionHistoryEntity = new SalesTransactionHistory();
        salesTransactionHistoryEntity.setTransactionId(transactionId);
        salesTransactionHistoryEntity.setSalesTransactionData(transactionImportData);
        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionHistoryEntity);
        salesTransactionHistoryEntityMapper.insertSelective(salesTransactionHistoryEntity);
    }
}
