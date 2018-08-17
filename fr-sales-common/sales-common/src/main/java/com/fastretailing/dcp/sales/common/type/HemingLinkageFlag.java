/**
 * @(#)HemingLinkageFlag.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the heming linkage flag.
 */
public enum HemingLinkageFlag {

    /** On. */
    ON(1),
    /** Off. */
    OFF(0);

    /**
     * String representation of the heming linkage flag.
     */
    @Getter
    private Integer value;

    /**
     * Sets the string representation of the heming linkage flag.
     * 
     * @param value Heming linkage flag.
     */
    private HemingLinkageFlag(Integer value) {
        this.value = value;
    }
}
