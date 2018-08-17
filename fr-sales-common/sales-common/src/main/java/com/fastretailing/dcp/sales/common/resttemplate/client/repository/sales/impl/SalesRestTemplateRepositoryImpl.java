/**
 * @(#)SalesRestTemplateRepositoryImpl.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import com.fastretailing.dcp.common.api.uri.UriResolver;
import com.fastretailing.dcp.common.hmac.annotation.HmacAuthentication;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.sales.CallSalesPayoffIntegrityCheckRequest;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.SalesRestTemplateRepository;
import com.fastretailing.dcp.sales.common.resttemplate.constant.ItemKey;
import com.fastretailing.dcp.sales.common.util.RestTemplateUtility;
import com.fastretailing.dcp.sales.importtransaction.dto.AlterationPayOffImportMultiData;
import com.fastretailing.dcp.sales.importtransaction.dto.CreateTransactionImportData;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;

/**
 * Implementation class of rest template repository for sales API.
 */
@Repository
public class SalesRestTemplateRepositoryImpl implements SalesRestTemplateRepository {

    /**
     * Rest template.
     */
    @Autowired
    private RestTemplate restTemplate;

    /** URI resolver. */
    @Autowired
    private UriResolver uriResolver;

    private static final String TRANSACTION_IMPORT_URI =
            "client.apiConfig.sales.uri.apis.transactionImport.post";

    private static final String ALTERATION_PAYOFF_DATA_IMPORT_URL =
            "client.apiConfig.sales.uri.apis.alterationPayOffDataImport.post";

    /** Defined position of URI of sales payoff ingegrity check. */
    private final String SALES_PAYOFF_INTEGRITY_CHECK_URI =
            "client.apiConfig.sales.uri.apis.salesPayoffIntegrityCheck.post";


    /**
     * {@inheritDoc}
     */
    @HmacAuthentication(value = "sales")
    @Override
    public CommonStatus callTransactionImport(CreateTransactionImportData transactionImportData) {
        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, RequestPathVariableHolder.getBrand());
        pathParameterMap.put(ItemKey.REGION, RequestPathVariableHolder.getRegion());
        pathParameterMap.put(ItemKey.STORE_CODE, transactionImportData.getStoreCode());
        pathParameterMap.put(ItemKey.CHANNEL_CODE, transactionImportData.getChannelCode());
        pathParameterMap.put(ItemKey.INTEGRATED_ORDER_ID,
                transactionImportData.getIntegratedOrderId());
        pathParameterMap.put(ItemKey.ORDER_SUB_NUMBER, String
                .valueOf(transactionImportData.getTransactionList().get(0).getOrderSubNumber()));
        pathParameterMap.put(ItemKey.UPDATE_TYPE, transactionImportData.getUpdateType());

        return RestTemplateUtility
                .postForEntity(restTemplate,
                        uriResolver.getUriAccordingToServerType(TRANSACTION_IMPORT_URI),
                        transactionImportData, CommonStatus.class, pathParameterMap)
                .getBody();
    }

    /**
     * {@inheritDoc}
     */
    @HmacAuthentication(value = "sales")
    @Override
    public CommonStatus callAlterationPayOffData(
            AlterationPayOffImportMultiData alterationPayOffImportMultiData) {
        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, RequestPathVariableHolder.getBrand());
        pathParameterMap.put(ItemKey.REGION, RequestPathVariableHolder.getRegion());

        return RestTemplateUtility
                .postForEntity(restTemplate,
                        uriResolver.getUriAccordingToServerType(ALTERATION_PAYOFF_DATA_IMPORT_URL),
                        alterationPayOffImportMultiData, CommonStatus.class, pathParameterMap)
                .getBody();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResultObject> callSalesPayoffIntegrityCheck(
            CallSalesPayoffIntegrityCheckRequest requestParameters) {
        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, RequestPathVariableHolder.getBrand());
        pathParameterMap.put(ItemKey.REGION, RequestPathVariableHolder.getRegion());

        return RestTemplateUtility.postForEntity(restTemplate,
                uriResolver.getUriAccordingToServerType(SALES_PAYOFF_INTEGRITY_CHECK_URI),
                requestParameters, ResultObject.class, pathParameterMap);
    }

}
