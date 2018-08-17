/**
s * @(#)ErrorDataDeletionServiceImpl.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.Detail;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.util.LogMessageUtility;
import com.fastretailing.dcp.sales.importtransaction.converter.CommonDataProcessor;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformationCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetailCondition;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionErrorDetailMapper;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import lombok.extern.slf4j.Slf4j;

/**
 * The class is used to delete transaction error data.
 *
 */
@Slf4j
@Service
public class ErrorDataDeletionServiceImpl implements ErrorDataDeletionService {

    /**
     * Component for operating DB operations on the sales transaction error detail table.
     */
    @Autowired
    private SalesTransactionErrorDetailMapper salesTransactionErrorDetailEntityMapper;

    /**
     * The Helper for data processor.
     */
    @Autowired
    private DataProcessorHelper dataProcessorHelper;

    /** Common data processor parts. */
    @Autowired
    private CommonDataProcessor commonDataProcessor;

    /** Model tools parts. */
    @Autowired
    private ModelMapper modelMapper;

    /** System message source. */
    @Autowired
    private SystemMessageSource systemMessageSource;

    /** Detail field sales transaction error id. */
    private static final String DETAIL_FIELD_SALES_TRANSACTION_ERROR_ID =
            "sales_transaction_error_id";

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteErrorData(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId) {

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
            ResultObject resultObject = new ResultObject();
            resultObject.setName(ErrorName.Business.BUSINESS_CHECK_ERROR);
            resultObject.setDebugId(MessagePrefix.E_SLS_64000101_INEXCUTABLE_API);
            resultObject.setMessage(
                    systemMessageSource.getMessage(MessagePrefix.E_SLS_64000101_INEXCUTABLE_API));
            Detail detail = new Detail();
            detail.setField(DETAIL_FIELD_SALES_TRANSACTION_ERROR_ID);
            detail.setValue(transactionImportData.getSalesTransactionErrorId());
            detail.setIssue(
                    systemMessageSource.getMessage(MessagePrefix.E_SLS_64000147_NO_DATA_EXISTS));
            List<Detail> detailList = new ArrayList<>();
            detailList.add(detail);
            resultObject.setDetails(detailList);

            log.error(LogMessageUtility.createLogMessage(detailList));
            throw new BusinessException(resultObject);
        } else {
            correctSalesErrorOrderInformation(transactionImportData, userId);
            updateSalesTransactionErrorDetail(userId, salesTransactionErrorDetail,
                    salesTransactionErrorDetailCondition);
        }
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
     * @param userId User id.
     */
    private void correctSalesErrorOrderInformation(TransactionImportData transactionImportData,
            String userId) {
        dataProcessorHelper.insertByTransactionId(transactionImportData, userId);

        // Delete table by transaction id.
        dataProcessorHelper.deleteTableByTransactionId(transactionImportData);
    }
}
