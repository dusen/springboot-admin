/**
 * @(#)FunctionType.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.type;

import lombok.Getter;

/**
 * Function type for debug id.
 */
public enum FunctionType {

    /** Batch. */
    BATCH("2"),
    /** Api. */
    API("4"),
    /** Screen. */
    SCREEN("6"),
    /** Common function. */
    COMMON("9");

    /**
     * Function type.
     */
    @Getter
    private String value;

    /**
     * Constructor.
     * 
     * @param functionType Function type.
     */
    FunctionType(String functionType) {
        this.value = functionType;
    }

    /**
     * Check same value.
     *
     * @param value Check value.
     * @return Compare result.
     */
    public boolean is(String value) {
        return this.value.equals(value);
    }
}
