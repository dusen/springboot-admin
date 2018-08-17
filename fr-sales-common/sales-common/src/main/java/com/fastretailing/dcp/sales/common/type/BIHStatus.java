/**
 * @(#)BIHStatus.java
 * 
 *                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the BIH status.
 *
 */
public enum BIHStatus {

    /** Value of yet. */
    YET("0");

    /**
     * String representation of the BIH status.
     */
    @Getter
    private String bihStatus;

    /**
     * Set the string representation of the the channel type.
     * 
     * @param value Channel type.
     */
    private BIHStatus(String value) {
        this.bihStatus = value;
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param sourceBihStatus Source BIH status.
     * @param bihStatus BIH status.
     * @return True if the given object represents a String equivalent to this string, false
     *         otherwise.
     */
    public static boolean compare(BIHStatus sourceBihStatus, final String bihStatus) {
        if (bihStatus == null) {
            return false;
        }
        return sourceBihStatus.toString().equals(bihStatus);
    }
}
