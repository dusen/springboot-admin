/**
 * @(#)ReportType.java
 * 
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.type;

import lombok.Getter;

/**
 * Define the enum for report type.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public enum ReportType {

    /** PDF. */
    PDF("PDF"),

    /** EXCEL. */
    EXCEL("EXCEL");

    /** String value. */
    @Getter
    private String value;

    /**
     * Set string value of this enum.
     * 
     * @param value Value of this enum.
     */
    private ReportType(String value) {
        this.value = value;
    }

    /**
     * Returns true if the argument are equal to value of this enum and false otherwise. If argument
     * are null, false is returned.
     * 
     * @param value String value of enum.
     * @return True if the argument are equal to value of this enum and false otherwise.
     */
    public boolean is(String value) {
        return this.value.equals(value);
    }
}
