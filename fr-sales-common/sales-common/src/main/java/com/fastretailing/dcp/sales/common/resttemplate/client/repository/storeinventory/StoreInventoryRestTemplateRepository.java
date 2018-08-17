/**
 * @(#)StoreInventoryRestTemplateRepository.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.repository.storeinventory;

import com.fastretailing.dcp.common.hmac.annotation.HmacAuthentication;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.storeinventory.InventoryInformationResponse;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.storeinventory.SalesFiguresRequest;

/**
 * Rest template repository for Store Inventory API.
 */
public interface StoreInventoryRestTemplateRepository {

    /**
     * Request sales figures API.
     * 
     * @param salesFiguresRequest Sales figures request.
     * @return Inventory information response.
     */
    @HmacAuthentication(value = "storeinventory")
    InventoryInformationResponse requestSalesFigures(SalesFiguresRequest salesFiguresRequest);

}
