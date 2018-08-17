/**
 * @(#)CodeLevel.java
 * 
 *                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the code level.
 *
 */
public enum CodeLevel {

    /** Decimal code level. */
    DECIMAL("DECIMAL"),

    /** Fraction code level. */
    FRACTION("FRACTION");

    /**
     * Code level value.
     */
    @Getter
    private String value;

    /**
     * Constructor.
     *
     * @param value Code level value.
     */
    private CodeLevel(String value) {
        this.value = value;
    }

    /**
     * Check same value.
     *
     * @param value Check value.
     * @return Compare result.
     */
    public boolean is(String value) {
        if (value == null) {
            return false;
        }
        return this.value.equals(value);
    }
}
