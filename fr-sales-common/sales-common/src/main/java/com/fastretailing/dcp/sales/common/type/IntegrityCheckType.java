/**
 * @(#)IntegrityCheckType.java
 * 
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the integrity check type.
 *
 */
public enum IntegrityCheckType {

    /** Value of no check. */
    NO_CHECK("0"),

    /** Value of consistency. */
    CONSISTENCY("1"),

    /** Value of type discrepancies. */
    TYPE_DISCREPANCIES("2"),

    /** Value of balance discrepancies. */
    BALANCE_DISCREPANCIES("3");

    /**
     * Enum value.
     */
    @Getter
    private String value;

    /**
     * Constructor.
     *
     * @param value Enum value.
     */
    private IntegrityCheckType(String value) {
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
