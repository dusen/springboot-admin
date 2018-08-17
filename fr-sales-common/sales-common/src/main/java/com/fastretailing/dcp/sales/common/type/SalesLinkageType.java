/**
 * @(#)SalesLinkageType.java
 *
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * Sales linkage type.
 */
public enum SalesLinkageType {
    /** Sales linkage. */
    SALES_LINKAGE(0),
    /** Not sales linkage. */
    NOT_SALES_LINKAGE(1);

    /**
     * String representation of the heming linkage flag.
     */
    @Getter
    private Integer value;

    /**
     * Sets the string representation of the sales linkage flag.
     * 
     * @param value Sales linkage flag.
     */
    private SalesLinkageType(Integer value) {
        this.value = value;
    }

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
        return this.value.equals(value);
    }
}
