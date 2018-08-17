/**
 * @(#)TransactionCheckResultType.java
 * 
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

/**
 * This class is the enumerable class of the transaction check result type.
 *
 */
public enum TransactionCheckResultType {
    
    /** Value of normal. */
    NORMAL,
    
    /** Value of validation error. */
    VALIDATION_ERROR,
    
    /** Value of business error. */
    BUSINESS_ERROR,
    
    /** Value of error evacuation. */
    ERROR_EVACUATION;

}
