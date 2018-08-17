/**
 * @(#)StoreInventoryParameter.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.dto;

import lombok.Data;

/**
 * Store inventory parameter.
 */

@Data
public final class StoreInventoryParameter {

    /** Store code. */
    private String storeCode;

    /** Item code. */
    private String itemCode;

    /** Update type. */
    private String updateType;

    /** Quantity. */
    private Integer quantity;

    /** Quantity code. */
    private String quantityCode;
}

