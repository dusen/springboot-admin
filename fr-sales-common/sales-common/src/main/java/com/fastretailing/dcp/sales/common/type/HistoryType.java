/**
 * @(#)HistoryType.java
 * 
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;


/**
 * This class is the enumerable class of the history type.
 *
 */
public enum HistoryType {
    /** Value of before edit. */
    BEFORE(0),

    /** Value of after edit. */
    AFTER(1);

    /**
     * String representation of the history type.
     */
    private Integer historyType;

    /**
     * Set the string representation of the the history type.
     * 
     * @param historyType History type.
     */
    private HistoryType(Integer historyType) {
        this.historyType = historyType;
    }

    /**
     * Enum to Integer value.
     */
    public Integer getValue() {
        return historyType;
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
        return this.historyType.equals(value);
    }
}
