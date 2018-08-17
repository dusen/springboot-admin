/**
 * @(#)ProductClassification.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the product classification.
 */
public enum ProductClassification {

    /** Item. */
    ITEM("ITEM"),
    /** Nmitem. */
    NMITEM("NMITEM");

    /**
     * String representation of the product classification.
     */
    @Getter
    private String value;

    /**
     * Sets the string representation of the product classification.
     * 
     * @param value Product classification.
     */
    private ProductClassification(String value) {
        this.value = value;
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param sourceProductClassification source product classification.
     * @param productClassification Product classification.
     * @return True if the given object represents a String equivalent to this string, false
     *         otherwise.
     */
    public static boolean compare(ProductClassification sourceProductClassification,
            final String productClassification) {
        if (productClassification == null) {
            return false;
        }
        return sourceProductClassification.getValue().equals(productClassification);
    }
}
