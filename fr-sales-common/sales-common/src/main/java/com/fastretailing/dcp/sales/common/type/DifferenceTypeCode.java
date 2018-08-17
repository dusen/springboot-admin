/**
 * @(#)DifferenceTypeCode.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * The enumerable class of the difference type code.
 */
public enum DifferenceTypeCode {

    /** Color. */
    COLOR("CL"),

    /** Size. */
    SIZE("SZ"),

    /** Pattern length. */
    PATTERN_LENGTH("PL");

    /**
     * String representation of the difference type code.
     */
    @Getter
    private String value;

    /**
     * Sets the string representation of the difference type code.
     * 
     * @param value Difference type code.
     */
    private DifferenceTypeCode(String value) {
        this.value = value;
    }
}
