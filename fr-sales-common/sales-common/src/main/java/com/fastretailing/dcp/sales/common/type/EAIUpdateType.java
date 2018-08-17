/**
 * @(#)EAIUpdateType.java
 * 
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the EAI update type.
 *
 */
public enum EAIUpdateType {

    /** Value of new. */
    NEW("1"),

    /** Value of add. */
    ADD("2"),

    /** Value of delete. */
    DELETE("3"),

    /** Value of error. */
    ERROR("9");

    /**
     * String representation of the EAI update type.
     */
    @Getter
    private String eaiUpdateType;

    /**
     * Set the string representation of the the EAI update type.
     * 
     * @param value EAI update type.
     */
    private EAIUpdateType(String value) {
        this.eaiUpdateType = value;
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param sourceEaiUpdateType EAI update type.
     * @param eaiUpdateType EAI Update Type.
     * @return True if the given object represents a String equivalent to this string, false
     *         otherwise.
     */
    public static boolean compare(EAIUpdateType sourceEaiUpdateType, final String eaiUpdateType) {
        if (eaiUpdateType == null) {
            return false;
        }
        return sourceEaiUpdateType.toString().equals(eaiUpdateType);
    }
}
