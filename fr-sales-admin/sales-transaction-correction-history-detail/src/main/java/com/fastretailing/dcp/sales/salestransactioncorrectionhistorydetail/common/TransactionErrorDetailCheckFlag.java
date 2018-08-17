/**
 * @(#)TransactionErrorDetailCheckedFlag.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Transaction error details is checked flag.
 */
@AllArgsConstructor
public enum TransactionErrorDetailCheckFlag {

    /** On. */
    CHECKED(1),
    /** Off. */
    UNCHECKED(0);

    /**
     * Transaction error details is checked value.
     */
    @Getter
    private Integer value;

    /**
     * Check same value.
     *
     * @param value Check value.
     * @return Compare result.
     */
    public boolean is(Integer value) {
        if (value == null) {
            return false;
        }
        return value.equals(this.value);
    }

}
