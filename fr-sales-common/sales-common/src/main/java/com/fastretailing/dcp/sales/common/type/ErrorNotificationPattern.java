/**
 * @(#)ErrorNotificationPattern.java
 * 
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is the enumerable class of the error notification pattern.
 *
 */
public enum ErrorNotificationPattern {

    /** Value of country unit. */
    COUNTRY_UNIT("01"),
    
    /** Value of store unit. */
    STORE_UNIT("02"),
    
    /** Value of details. */
    DETAILS("03"),
    
    /** Value of all errors. */
    ALL_ERRORS("04");
    
    /**
     * String representation of the error notification pattern.
     */
    @Getter
    @Setter
    private String errorNotificationPattern;
    
    /**
     * Set the string representation of the the error notification pattern.
     * 
     * @param errorNotificationPattern Error notification pattern.
     */
    private ErrorNotificationPattern(String  errorNotificationPattern) {
        this.errorNotificationPattern = errorNotificationPattern;
    }
}
