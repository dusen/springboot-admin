/**
 * @(#)EAISendStatus.java
 * 
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the EAI send status.
 *
 */
public enum EAISendStatus {

    /** Value of new. */
    ONE("1");

    /**
     * String representation of the EAI send status.
     */
    @Getter
    private String eaiSendStatus;

    /**
     * Set the string representation of the the EAI send status.
     * 
     * @param value EAI send status.
     */
    private EAISendStatus(String value) {
        this.eaiSendStatus = value;
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param sourceEaiSendStatus EAI send status.
     * @param eaiSendStatus EAI send status.
     * @return True if the given object represents a String equivalent to this string, false
     *         otherwise.
     */
    public static boolean compare(EAISendStatus sourceEaiSendStatus, final String eaiSendStatus) {
        if (eaiSendStatus == null) {
            return false;
        }
        return sourceEaiSendStatus.toString().equals(eaiSendStatus);
    }
}
