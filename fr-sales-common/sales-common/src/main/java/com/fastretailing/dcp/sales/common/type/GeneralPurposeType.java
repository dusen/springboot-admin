/**
 * @(#)UpdateType.java
 * 
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the update type.
 * 
 */
public enum GeneralPurposeType {

    /** Value of time zone. */
    TIME_ZONE("time_zone"),

    /** Value of price_change_time_difference. */
    PRICE_CHANGE_TIME_DIFFERENCE("price_change_time_difference"),

    /** Value of price_change_time. */
    PRICE_CHANGE_TIME("price_change_time"),

    /** Value of brand. */
    BRAND("brand"),

    /** Value of ec_flag. */
    EC_FLAG("ec_flag");

    /**
     * String representation of the update type.
     */
    @Getter
    private String generalPurposeType;

    /**
     * Set the string representation of the the update type.
     * 
     * @param value Update type.
     */
    private GeneralPurposeType(String generalPurposeType) {
        this.generalPurposeType = generalPurposeType;
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param sourceGeneralPurposeType Source update type.
     * @param generalPurposeType Update Type.
     * @return True if the given object represents a String equivalent to this string, false
     *         Otherwise.
     */
    public static boolean compare(GeneralPurposeType sourceGeneralPurposeType,
            final String generalPurposeType) {
        if (generalPurposeType == null) {
            return false;
        }
        return sourceGeneralPurposeType.getGeneralPurposeType().equals(generalPurposeType);
    }
}
