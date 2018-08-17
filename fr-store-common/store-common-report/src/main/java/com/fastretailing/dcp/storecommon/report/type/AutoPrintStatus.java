/**
 * @(#)AutoPrintStatus.java
 * 
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.type;

import lombok.Getter;

/**
 * Define the enum for auto print status.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public enum AutoPrintStatus {

    /** Unprocessed. */
    UNPROCESSED(0),

    /** Success. */
    SUCCESS(2),

    /** Failure. */
    FAILURE(3);

    /** Integer value of status code. */
    @Getter
    private int value;

    /**
     * Set Integer value of status code.
     * 
     * @param value Value of status code.
     */
    private AutoPrintStatus(int value) {
        this.value = value;
    }

    /**
     * Returns true if the argument are equal to value of this status and false otherwise. If
     * argument are null, false is returned.
     * 
     * @param value Integer value of status code.
     * @return True if the argument are equal to value of this status and false otherwise.
     */
    public boolean is(Integer value) {
        if (value == null) {
            return false;
        }
        return this.value == value.intValue();
    }
}
