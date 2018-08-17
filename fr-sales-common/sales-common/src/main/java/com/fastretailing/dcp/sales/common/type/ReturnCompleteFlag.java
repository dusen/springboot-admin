/**
 * @(#)ReturnCompleteFlag.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the return complete flag.
 */
public enum ReturnCompleteFlag {

    /** On. */
    ON(1),
    /** Off. */
    OFF(0);

    /**
     * String representation of the return complete flag.
     */
    @Getter
    private Integer value;

    /**
     * Sets the string representation of the return complete flag.
     * 
     * @param value Return complete flag.
     */
    private ReturnCompleteFlag(Integer value) {
        this.value = value;
    }
}
