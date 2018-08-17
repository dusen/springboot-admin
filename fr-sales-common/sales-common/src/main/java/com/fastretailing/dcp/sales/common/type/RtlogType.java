/**
 * @(#)RtlogType.java
 * 
 *                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the rtlog type.
 *
 */
public enum RtlogType {

    /** Value of sales tax. */
    SALES_TAX("SLSTX"),

    /** Value of value added tax. */
    VALUE_ADDED_TAX("VAT"),

    /** Value of consumption tax. */
    CONSUMPTION_TAX("CNSMP");

    /**
     * String representation of the rtlog type.
     */
    @Getter
    private String rtlogType;

    /**
     * Set the string representation of the the rtlog type.
     * 
     * @param value Rtlog type.
     */
    private RtlogType(String value) {
        this.rtlogType = value;
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param sourceRtlogType Source rtlog type.
     * @param rtlogType Rtlog Type.
     * @return True if the given object represents a String equivalent to this string, false
     *         Otherwise.
     */
    public static boolean compare(RtlogType sourceRtlogType, final String rtlogType) {
        if (rtlogType == null) {
            return false;
        }
        return sourceRtlogType.getRtlogType().equals(rtlogType);
    }
}
