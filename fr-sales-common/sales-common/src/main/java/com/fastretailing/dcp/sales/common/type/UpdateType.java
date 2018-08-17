/**
 * @(#)UpdateType.java
 * 
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the update type.
 *
 */
public enum UpdateType {

    /** Value of insert. */
    INSERT("INSERT"),
    
    /** Value of update. */
    UPDATE("UPDATE"),
    
    /** Value of delete. */
    DELETE("DELETE"),
    
    /** Value of correction. */
    CORRECTION("CORRECTION");

    /**
     * String representation of the update type.
     */
    @Getter
    private String updateType;

    /**
     * Set the string representation of the the update type.
     * 
     * @param value Update type.
     */
    private UpdateType(String value) {
        this.updateType = value;
    }
    
    /**
     * Compares this object to the specified object.
     * 
     * @param sourceUpdateType Source update type.
     * @param updateType Update Type.
     * @return True if the given object represents a String equivalent to this string, false
     *         Otherwise.
     */
    @Deprecated
    public static boolean compare(UpdateType sourceUpdateType,
            final String updateType) {
        if (updateType == null) {
            return false;
        }
        return sourceUpdateType.getUpdateType().equals(updateType);
    }
    
    /**
     * Check same value.
     *
     * @param value Check value.
     * @return Compare result.
     */
    public boolean is(String value) {
        if (value == null) {
            return false;
        }
        return this.updateType.equals(value);
    }
}
