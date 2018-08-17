/**
 * @(#)ErrorNotificationFlag.java
 * 
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the error notification flag.
 *
 */
public enum ErrorNotificationFlag {

    /** Value of unsent. */
    UNSENT(0),

    /** Value of sent. */
    SENT(1);


    /**
     * String representation of the error notification flag.
     */
    @Getter
    private Integer errorNotificationFlag;

    /**
     * Set the string representation of the error notification flag.
     * 
     * @param Error notification flag.
     */
    private ErrorNotificationFlag(Integer value) {
        this.errorNotificationFlag = value;
    }
}
