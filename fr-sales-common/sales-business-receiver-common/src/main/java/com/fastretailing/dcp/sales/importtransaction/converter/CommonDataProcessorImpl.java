/**
 * @(#)CommonDataProcessorImpl.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.converter;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;

/**
 * Common data processor implements.
 *
 */
@Component
public class CommonDataProcessorImpl implements CommonDataProcessor {

    private static final String PROGRAM_SFCID = "SLS0300101";

    /**
     * Get the data for table common item to update table.
     * 
     * @param userId The user id of updating table.
     * @return The data structure for table common item.
     */
    @Override
    public TableCommonItem getTableCommonItemForUpdate(String userId) {
        TableCommonItem tableCommonItem = new TableCommonItem();
        tableCommonItem.setUpdateUserId(userId);
        tableCommonItem.setUpdateProgramId(PROGRAM_SFCID);
        tableCommonItem.setUpdateDatetime(LocalDateTime.now());
        return tableCommonItem;
    }

    /**
     * Get the data for table common item to insert table.
     * 
     * @param userId The user id of inserting table.
     * @return The data structure for table common item.
     */
    @Override
    public TableCommonItem getTableCommonItemForInsert(String userId) {
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.now();

        tableCommonItem.setCreateUserId(userId);
        tableCommonItem.setCreateProgramId(PROGRAM_SFCID);
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId(userId);
        tableCommonItem.setUpdateProgramId(PROGRAM_SFCID);
        tableCommonItem.setUpdateDatetime(nowDateTime);
        return tableCommonItem;
    }

}
