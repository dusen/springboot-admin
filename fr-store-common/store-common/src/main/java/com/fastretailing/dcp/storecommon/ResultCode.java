/**
 * @(#)ResultCode.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * This class is the Enum class of the result code.
 */
public enum ResultCode {

    /**
     * Result code indicating normal.
     */
    NORMAL(0),

    /**
     * Result code indicating abnormal.
     */
    ABNORMAL(1),

    /**
     * Result code indicating warning.
     */
    WARNING(2);

    /**
     * Numeric representation of the result code.
     */
    private int resultNum;

    /**
     * Sets the numeric representation of the result code.
     * 
     * @param resultNum Numeric value to be set in the result code.
     */
    private ResultCode(int resultNum) {
        this.resultNum = resultNum;
    }

    /**
     * Return numeric representation of the result code.
     * 
     * @return Numeric representation of the result code.
     */
    @JsonValue
    public final int toValue() {
        return resultNum;
    }
}
