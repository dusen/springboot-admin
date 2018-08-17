/**
 * @(#)SystemCountryCode.java
 *
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

public enum SystemCountryCode {
    JAPAN("01"), UK("02"), CHINA("03"), USA("04"), KOREA("05"), HONGKONG("06"), FRANCE(
            "07"), SINGAPORE("08"), RUSSIA("09"), TAIWAN("10"), THAILAND("11"), PHILIPPINES(
                    "12"), INDONESIA("13"), AUSTRALIA("14"), GERMANY("15"), SPAIN(
                            "16"), CANADA("17"), MACAU("26"), MALAYSIA("27"), BELGIUM("32");

    /**
     * String representation of the system country code.
     */
    @Getter
    private String value;

    /**
     * Set the string representation of the the system country code.
     * 
     * @param value.
     */
    private SystemCountryCode(String value) {
        this.value = value;
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param sourceSystemCountryCode Source system country code.
     * @param systemCountryCode System country code.
     * @returnTrue if the given object represents a String equivalent to this string, false
     *             Otherwise.
     */
    public static boolean compare(SystemCountryCode sourceSystemCountryCode,
            final String systemCountryCode) {
        if (systemCountryCode == null) {
            return false;
        }
        return sourceSystemCountryCode.getValue().equals(systemCountryCode);
    }
}
