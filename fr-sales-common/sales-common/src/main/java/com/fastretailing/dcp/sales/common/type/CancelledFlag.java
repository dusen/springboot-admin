/**
 * @(#)CancelledFlag.java
 *
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the return cancelled flag.
 */
public enum CancelledFlag {

    /** On. */
    ON(1),
    /** Off. */
    OFF(0);

    /**
     * String representation of the cancelled flag.
     */
    @Getter
    private Integer value;

    /**
     * Sets the string representation of the cancelled flag.
     * 
     * @param value Cancelled flag.
     */
    private CancelledFlag(Integer value) {
        this.value = value;
    }
}
