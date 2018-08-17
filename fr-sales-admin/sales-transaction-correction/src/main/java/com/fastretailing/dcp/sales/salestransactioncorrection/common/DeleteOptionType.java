/**
 * @(#)DeleteOptionType.java
 *
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Delete option type.
 */
@AllArgsConstructor
public enum DeleteOptionType {

    /** Reflect on all. */
    REFLECT_ON_ALL(0),
    /** Reflect only on ims linkage. */
    REFLECT_ONLY_ON_IMS_LINKAGE(1),
    /** Reflect only on receipt. */
    REFLECT_ONLY_ON_RECEIPT(2);

    /**
     * Delete option type.
     */
    @Getter
    private Integer value;

    /**
     * Check same value.
     *
     * @param value Delete option type.
     * @return Compare result.
     */
    public boolean is(Integer value) {
        if (value == null) {
            return false;
        }
        return value.equals(this.value);
    }

}
