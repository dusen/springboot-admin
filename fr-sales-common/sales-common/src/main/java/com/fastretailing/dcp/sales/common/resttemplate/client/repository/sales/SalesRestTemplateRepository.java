/**
 * @(#)SalesRestTemplateRepository.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales;
import org.springframework.http.ResponseEntity;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.sales.CallSalesPayoffIntegrityCheckRequest;
import com.fastretailing.dcp.sales.importtransaction.dto.AlterationPayOffImportMultiData;
import com.fastretailing.dcp.sales.importtransaction.dto.CreateTransactionImportData;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;

/**
 * Rest template repository for sales API.
 */
public interface SalesRestTemplateRepository {

    /**
     * Call transaction import API.
     * 
     * @param transactionImportData Transaction import data.
     * @return Common status.
     */
    CommonStatus callTransactionImport(CreateTransactionImportData transactionImportData);

    /**
     * Call pay off data import API.
     * 
     * @param alterationPayOffImportMultiData Alteration pay off data import data.
     * @return Common status.
     */
    CommonStatus callAlterationPayOffData(
            AlterationPayOffImportMultiData alterationPayOffImportMultiData);


    /**
     * Call sales payoff integrity check API.
     * 
     * @param request sales payoff integrity check API request parameters.
     * @return call result.
     */
    public ResponseEntity<ResultObject> callSalesPayoffIntegrityCheck(
            CallSalesPayoffIntegrityCheckRequest request);
}
