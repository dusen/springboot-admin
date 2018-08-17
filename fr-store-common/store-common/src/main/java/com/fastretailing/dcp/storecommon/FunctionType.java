/**
 * @(#)FunctionType.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon;

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
    private String functionType;

    /**
     * Constructor.
     * 
     * @param functionType Function type.
     */
    FunctionType(String functionType) {
        this.functionType = functionType;
    }

    /**
     * Get Function type.
     * 
     * @return Function type.
     */
    @Override
    public String toString() {
        return this.functionType;
    }
}
