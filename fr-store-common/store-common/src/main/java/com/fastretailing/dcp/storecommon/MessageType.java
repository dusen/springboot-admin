/**
 * @(#)MessageType.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon;

/**
 * Message type for debug id.
 */
public enum MessageType {

    /** Normal message. */
    NORMAL("0"),
    /** Alert message. */
    ALERT("2"),
    /** Business error message(Input check). */
    VALIDATE_ERROR("4"),
    /** Business error message. */
    BUSINESS_ERROR("6"),
    /** System error message. */
    SYSTEM_ERROR("9");

    /**
     * Message type.
     */
    private String messageType;

    /**
     * Constructor.
     * 
     * @param messageType Message type.
     */
    MessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * Get message type.
     * 
     * @return Message type.
     */
    @Override
    public String toString() {
        return this.messageType;
    }
}
