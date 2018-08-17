/**
 * @(#)CommonDataProcessor.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.converter;

import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;

/**
 * The class is used to manager the common processor for transaction import.
 *
 */
public interface CommonDataProcessor {

    /**
     * Get the data for table common item to update table.
     * 
     * @param userId The user id of updating table.
     * @return The data structure for table common item.
     */
    TableCommonItem getTableCommonItemForUpdate(String userId);

    /**
     * Get the data for table common item to insert table.
     * 
     * @param userId The user id of inserting table.
     * @return The data structure for table common item.
     */
    TableCommonItem getTableCommonItemForInsert(String userId);
}
