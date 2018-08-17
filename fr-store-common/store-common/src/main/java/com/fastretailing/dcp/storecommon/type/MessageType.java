/**
 * @(#)MessageType.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.type;

import lombok.Getter;

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
    @Getter
    private String value;

    /**
     * Constructor.
     * 
     * @param messageType Message type.
     */
    MessageType(String messageType) {
        this.value = messageType;
    }

    /**
     * Check same value.
     *
     * @param value Check value.
     * @return Compare result.
     */
    public boolean is(String value) {
        return this.value.equals(value);
    }
}
