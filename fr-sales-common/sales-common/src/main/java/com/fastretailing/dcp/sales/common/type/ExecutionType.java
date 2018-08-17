/**
 * @(#)ExecutionType.java
 * 
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the execution type.
 *
 */
public enum ExecutionType {

    /** Value of screen. */
    SCREEN("1"),
    /** Value of closing process. */
    CLOSING_PROCESS("2");

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
    private ExecutionType(String value) {
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
