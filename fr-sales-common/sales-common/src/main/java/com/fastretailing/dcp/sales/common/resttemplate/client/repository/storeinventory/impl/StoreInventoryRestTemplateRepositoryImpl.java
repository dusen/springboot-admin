/**
 * @(#)StoreInventoryRestTemplateRepositoryImpl.java
 *
 *                                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.repository.storeinventory.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import com.fastretailing.dcp.common.api.uri.UriResolver;
import com.fastretailing.dcp.common.hmac.annotation.HmacAuthentication;
import com.fastretailing.dcp.common.util.JsonUtility;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.storeinventory.InventoryInformationResponse;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.storeinventory.SalesFiguresRequest;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.storeinventory.StoreInventoryRestTemplateRepository;
import com.fastretailing.dcp.sales.common.resttemplate.constant.ItemKey;
import com.fastretailing.dcp.sales.common.util.RestTemplateUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation class of rest template repository for Store Inventory API.
 */
@Slf4j
@Component
public class StoreInventoryRestTemplateRepositoryImpl
        implements StoreInventoryRestTemplateRepository {

    /** Rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /** URI resolver. */
    @Autowired
    private UriResolver uriResolver;

    /** Sales figures uri. */
    private static final String SALES_FIGURES_URI =
            "client.apiConfig.storeinventory.uri.apis.salesFigures.get";

    /**
     * {@inheritDoc}
     */
    @HmacAuthentication(value = "storeinventory")
    @Override
    public InventoryInformationResponse requestSalesFigures(
            SalesFiguresRequest salesFiguresRequest) {

        List<String> aggregationCodeList = salesFiguresRequest.getAggregationCodeList();

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, RequestPathVariableHolder.getBrand());
        pathParameterMap.put(ItemKey.REGION, RequestPathVariableHolder.getRegion());
        pathParameterMap.put(ItemKey.STORE_CODE, salesFiguresRequest.getStoreCode());
        pathParameterMap.put(ItemKey.ITEM_LIST_HIERARCHY_CODE,
                salesFiguresRequest.getItemListHierarchyCode());
        try {
            pathParameterMap.put(ItemKey.AGGREGATION_CODE_LIST,
                    JsonUtility.toJson(aggregationCodeList));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return RestTemplateUtility
                .getForEntity(restTemplate,
                        uriResolver.getUriAccordingToServerType(SALES_FIGURES_URI),
                        InventoryInformationResponse.class, pathParameterMap)
                .getBody();
    }
}
