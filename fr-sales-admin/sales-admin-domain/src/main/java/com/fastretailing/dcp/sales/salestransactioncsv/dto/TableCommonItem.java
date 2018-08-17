/**
 * @(#)TableCommonItem.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncsv.dto;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Table common item.
 * 
 */
@Data
public class TableCommonItem {

    /**
     * Create user id.
     */
    private String createUserId;

    /**
     * Create datetime.
     */
    private OffsetDateTime createDatetime;

    /**
     * Create program id.
     */
    private String createProgramId;

    /**
     * Update user id.
     */
    private String updateUserId;

    /**
     * Update datetime.
     */
    private OffsetDateTime updateDatetime;

    /**
     * Update program id.
     */
    private String updateProgramId;

}
