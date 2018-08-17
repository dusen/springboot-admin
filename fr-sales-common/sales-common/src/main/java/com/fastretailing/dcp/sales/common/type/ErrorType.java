/**
 * @(#)ErrorType.java
 * 
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is the enumerable class of the error type.
 *
 */
public enum ErrorType {
    /** Value of validation error. */
    VALIDATION_ERROR("01"),
    
    /** Value of relation error. */
    RELATION_ERROR("02"),
    
    /** Value of business error. */
    BUSINESS_ERROR("03"),
    
    /** Value of business date error. */
    BUSINESS_DATE_ERROR("04"),
    
    /** Value of before open date error. */
    BEFORE_OPEN_DATE_ERROR("05"),
    
    /** Value of after close date error. */
    AFTER_CLOSE_DATE_ERROR("06"),
    
    /** Value of update business date error. */
    UPDATE_BUSINESS_DATE_ERROR("07"),
    
    /** Value of close process completed date error. */
    CLOSE_PROCESS_COMPLETED_DATE_ERROR("08"),
    
    /** Value of tender error. */
    TENDER_ERROR("09"),
    
    /** Value of amount balance error. */
    AMOUNT_BALANCE_ERROR("10"),
    
    /** Value of unique constraints error. */
    UNIQUE_CONSTRAINTS_ERROR("11"),
    
    /** Value of payoff conformity check. */
    PAYOFF_CONFORMITY_CHECK("12"),
    
    /** Value of no error. */
    NO_ERROR("13");
    
    /**
     * String representation of the error type.
     */
    @Getter
    @Setter
    private String errorType;
    
    /**
     * Set the string representation of the the error type.
     * 
     * @param errorType Error type.
     */
    private ErrorType(String  errorType) {
        this.errorType = errorType;
    }
    
    /**
     * Compares this object to the specified object.
     * 
     * @param sourceErrorType Source error type.
     * @param errorType Error Type.
     * @return True if the given object represents a String equivalent to this string, false
     *         Otherwise.
     */
    public static boolean compare(ErrorType sourceErrorType,
            final String errorType) {
        if (errorType == null) {
            return false;
        }
        return sourceErrorType.getErrorType().equals(errorType);
    }
}
