/**
 * @(#)MessageType.java
 * 
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.message;

import java.util.Arrays;
import lombok.Getter;

/**
 * This class is the class of message type.
 */
public enum MessageType {

    /** Error message type. */
    ERROR("E"),
    /** Warning message type. */
    WARNING("W"),
    /** Information message type. */
    INFORMATION("I");

    /**
     * String message type.
     */
    @Getter
    private String type;

    /**
     * Set the string representation of the ajax message type.
     * 
     * @param type Message type.
     */
    private MessageType(String type) {
        this.type = type;
    }

    /**
     * Find the Message type by string.
     * 
     * @param type Message type.
     * @return The enum of type.
     */
    public static MessageType getTypeByValue(String type) {

        return Arrays.stream(values())
                .filter(value -> value.getType().equals(type))
                .findFirst()
                .orElse(MessageType.ERROR);
    }
}
