/**
 * @(#)EdfRestTemplateRepositoryImpl.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.repository.edf.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import com.fastretailing.dcp.common.api.uri.UriResolver;
import com.fastretailing.dcp.common.hmac.annotation.HmacAuthentication;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.edf.EdfLinkageRequest;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.edf.EdfLinkageResponse;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.edf.EdfRestTemplateRepository;
import com.fastretailing.dcp.sales.common.resttemplate.constant.ItemKey;
import com.fastretailing.dcp.sales.common.util.RestTemplateUtility;

/**
 * Implementation class of rest template repository for sales API.
 */
@Repository
public class EdfRestTemplateRepositoryImpl implements EdfRestTemplateRepository {

    /** Rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /** URI resolver. */
    @Autowired
    private UriResolver uriResolver;

    /** Edf linkage uri. */
    private static final String EDF_LINKAGE_URI = "client.apiConfig.edf.uri.apis.edfRest.post";

    /**
     * {@inheritDoc}
     */
    @HmacAuthentication(value = "edf")
    @Override
    public ResponseEntity<EdfLinkageResponse> callEdfLinkage(EdfLinkageRequest edfLinkageRequest) {
        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, RequestPathVariableHolder.getBrand());
        pathParameterMap.put(ItemKey.REGION, RequestPathVariableHolder.getRegion());
        pathParameterMap.put(ItemKey.STORE_CODE,
                edfLinkageRequest.getGlobalHeaders().getStoreCode());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("X-Edf-Requester-Id", "sale-api");

        HttpEntity<EdfLinkageRequest> request =
                new HttpEntity<EdfLinkageRequest>(edfLinkageRequest, headers);

        return RestTemplateUtility.postForEntity(restTemplate,
                uriResolver.getUriAccordingToServerType(EDF_LINKAGE_URI), request,
                EdfLinkageResponse.class, pathParameterMap);
    }
}
