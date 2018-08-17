/**
 * @(#)TaxSystemType.java
 *
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;


import lombok.Getter;

public enum TaxSystemType {
    /** Value of in tax. */
    IN("IN"),
    
    /** Value of out tax. */
    OUT("OUT");

    /**
     * String representation of the tax system type.
     */
    @Getter
    private String value;

    /**
     * Set the string representation of the the tax system type.
     * 
     * @param value tax system type.
     */
    private TaxSystemType(String value) {
        this.value = value;
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param sourceTaxSystemType Source tax system type.
     * @param taxSystemType Tax system type.
     * @return True if the given object represents a String equivalent to this string, false
     *         Otherwise.
     */
    public static boolean compare(TaxSystemType sourceTaxSystemType, final String taxSystemType) {
        if (taxSystemType == null) {
            return false;
        }
        return sourceTaxSystemType.getValue().equals(taxSystemType);
    }
}
