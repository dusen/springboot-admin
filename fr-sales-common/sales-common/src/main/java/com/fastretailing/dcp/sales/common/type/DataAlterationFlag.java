/**
 * @(#)DataAlterationFlag.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the data alteration flag.
 */
public enum DataAlterationFlag {

    /** On. */
    ON(1),
    /** Off. */
    OFF(0);

    /**
     * String representation of the data alteration flag.
     */
    @Getter
    private Integer value;

    /**
     * Sets the string representation of the data alteration flag.
     * 
     * @param value Data alteration flag.
     */
    private DataAlterationFlag(Integer value) {
        this.value = value;
    }
}
