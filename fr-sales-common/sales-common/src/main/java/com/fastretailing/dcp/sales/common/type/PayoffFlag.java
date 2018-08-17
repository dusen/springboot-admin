/**
 * PayoffFlag.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the payoff flag.
 */
public enum PayoffFlag {

    /** Prepared. */
    PREPARED("0"),
    /** End. */
    END("1");

    /**
     * String representation of the payoff flag.
     */
    @Getter
    private String value;

    /**
     * Sets the string representation of the payoff flag.
     * 
     * @param value Payoff flag.
     */
    private PayoffFlag(String value) {
        this.value = value;
    }
}
