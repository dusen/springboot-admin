/**
 * @(#)NonItemMasterOptionalInsertCondition.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 * 
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Non item master insert condition.
 */
@Data
public class NonItemMasterOptionalInsertCondition {

    /**
     * Lot num.
     */
    private String lotNum;

    /**
     * Create user id.
     */
    private String createUserId;

    /**
     * Create date time.
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
     * Update date time.
     */
    private OffsetDateTime updateDatetime;

    /**
     * Update program id.
     */
    private String updateProgramId;
}
