/**
 * @(#)DataAlterationBackboneLinkageType.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of data alteration back bone linkage type.
 */
public enum DataAlterationBackboneLinkageType {

    /** On. */
    ON(1),
    /** Off. */
    OFF(0);

    /**
     * String data alteration backbone linkage type.
     */
    @Getter
    private Integer value;

    /**
     * Sets the string data alteration backbone linkage type.
     *
     * @param value Data alteration backbone linkage type.
     */
    private DataAlterationBackboneLinkageType(Integer value) {
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
        return value.equals(this.value);
    }

}
