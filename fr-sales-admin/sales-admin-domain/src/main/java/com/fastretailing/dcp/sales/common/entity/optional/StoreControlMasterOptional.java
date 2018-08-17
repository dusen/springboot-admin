/**
 * @(#)StoreControlMasterOptional.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Store control master optional.
 */
@Data
public class StoreControlMasterOptional {

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Business date.
     */
    private String businessDate;

    /**
     * Business end of date.
     */
    private String businessEndOfDate;

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
