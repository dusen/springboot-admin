/**
 * @(#)DataAlterationStatus.java
 * 
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the data alteration status.
 *
 */
public enum DataAlterationStatus {

    /** Value of no alterer. */
    NO_ALTERER("0"),
    /** Value of alterer finish. */
    ALTERER_FINISH("1"),
    /** Value of alterering. */
    ALTERERING("2");

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
    private DataAlterationStatus(String value) {
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
