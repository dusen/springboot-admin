/**
 * @(#)IntegrityCheckFlag.java
 * 
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the integrity check flag.
 *
 */
public enum IntegrityCheckFlag {
    /** Value of integrity complete. */
    INTEGRITY_COMPLETE(0),

    /** Value of mismatch. */
    MISMATCH(1);

    /**
     * String representation of the integrity check flag.
     */
    @Getter
    private Integer integrityCheckFlag;

    /**
     * Set the string representation of the integrity check flag.
     * 
     * @param Integrity check flag.
     */
    private IntegrityCheckFlag(Integer value) {
        this.integrityCheckFlag = value;
    }
}
