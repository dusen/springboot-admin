/**
 * @(#)AccountingRecordStatus.java
 * 
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the accounting record status.
 *
 */
public enum AccountingRecordStatus {
    /** Value of not recorded yet. */
    NOT_RECORDED_YET(0),

    /** Value of recorded. */
    RECORDED(1);
    
    /**
     * String representation of the accounting record status.
     */
    @Getter
    private Integer accountingRecordStatus;

    /**
     * Set the string representation of the accounting record status.
     * 
     * @param Accounting record status.
     */
    private AccountingRecordStatus(Integer value) {
        this.accountingRecordStatus = value;
    }
}
