/**
 * @(#)InventoryInformationResponse.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.entity.storeinventory;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Inventory information response.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-04-03T13:46:19.252+09:00")

@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryInformationResponse extends CommonStatus {

    /**
     * Inventory information list.
     */
    @JsonProperty("inventory_information_list")
    private List<InventoryInformation> inventoryInformationList = null;
}

