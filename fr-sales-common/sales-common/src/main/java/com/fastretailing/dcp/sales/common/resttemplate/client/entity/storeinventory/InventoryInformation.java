/**
 * @(#)InventoryInformation.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.entity.storeinventory;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Inventory information.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-04-03T13:46:19.252+09:00")
@Data
public class InventoryInformation {
    /**
     * Aggregation code.
     */
    @JsonProperty("aggregation_code")
    @Size(max = 25)
    private String aggregationCode = null;

    /**
     * Single item code.
     */
    @JsonProperty("single_item_code")
    @Size(max = 25)
    private String singleItemCode = null;

    /**
     * Current stock quantity.
     */
    @JsonProperty("current_stock_quantity")
    @Valid
    private Long currentStockQuantity = null;

    /**
     * Delivery expected quantity.
     */
    @JsonProperty("delivery_expected_quantity")
    @Valid
    private Long deliveryExpectedQuantity = null;

    /**
     * Previcus day stock quantity.
     */
    @JsonProperty("previcus_day_stock_quantity")
    @Valid
    private Long previcusDayStockQuantity = null;
}
