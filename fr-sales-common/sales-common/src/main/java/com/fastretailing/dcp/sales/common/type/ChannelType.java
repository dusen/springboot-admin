/**
 * @(#)ChannelType.java
 * 
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the channel type.
 *
 */
public enum ChannelType {
    
    /** Value of store. */
    STORE("S"),

    /** Value of ec. */
    EC("E");

    /**
     * String representation of the channel type.
     */
    @Getter
    private String channelType;

    /**
     * Set the string representation of the the channel type.
     * 
     * @param value Channel type.
     */
    private ChannelType(String value) {
        this.channelType = value;
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param sourceChannelType Source channel type.
     * @param channelType Channel type.
     * @return True if the given object represents a String equivalent to this string, false
     *         otherwise.
     */
    public static boolean compare(ChannelType sourceChannelType,
            final String channelType) {
        if (channelType == null) {
            return false;
        }
        return sourceChannelType.getChannelType().equals(channelType);
    }
}
