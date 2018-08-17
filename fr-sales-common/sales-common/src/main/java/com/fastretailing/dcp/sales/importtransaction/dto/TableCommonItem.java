/**
 * @(#)TableCommonItem.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.dto;

import java.time.LocalDateTime;
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
    private LocalDateTime createDatetime;

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
    private LocalDateTime updateDatetime;

    /**
    * Update program id.
    */
    private String updateProgramId;

}
