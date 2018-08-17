/**
 * @(#)BalanceType.java
 * 
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the balance type.
 *
 */
public enum BalanceType {

    /** Value of item sales. */
    ITEM_SALES("ITEMSALES"),
    /** Value of payment tender. */
    PAYMENT_TENDER("PAYMENTTENDER");

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
    private BalanceType(String value) {
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
