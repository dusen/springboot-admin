/**
 * @(#)DisplayOrder.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.common;

import lombok.Getter;

/**
 * Display order.
 *
 */
public enum DisplayOrder {

    /** Display order one. */
    DISPLAY_ORDER_ONE(1),
    /** Display order two. */
    DISPLAY_ORDER_TWO(2),
    /** Display order three. */
    DISPLAY_ORDER_THREE(3),
    /** Display order four. */
    DISPLAY_ORDER_FOUR(4),
    /** Display order five. */
    DISPLAY_ORDER_FIVE(5),
    /** Display order six. */
    DISPLAY_ORDER_SIX(6),
    /** Display order seven. */
    DISPLAY_ORDER_SEVEN(7),
    /** Display order eight. */
    DISPLAY_ORDER_EIGHT(8);
    /**
     * Integer representation of the display order.
     */
    @Getter
    private int value;

    /**
     * Sets the string representation of the display order.
     * 
     * @param value Display order.
     */
    private DisplayOrder(int value) {
        this.value = value;
    }
}
