/**
 * @(#)EdfRestTemplateRepository.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.repository.edf;

import org.springframework.http.ResponseEntity;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.edf.EdfLinkageRequest;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.edf.EdfLinkageResponse;

/**
 * Rest template repository for sales API.
 */
public interface EdfRestTemplateRepository {

    /**
     * Call edf linkage API.
     *
     * @param edfLinkageRequest Edf linkage request.
     * @return Edf linkage response.
     */
    ResponseEntity<EdfLinkageResponse> callEdfLinkage(EdfLinkageRequest edfLinkageRequest);

}
