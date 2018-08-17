/**
 * @(#)TransactionDataValidator.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction;

/**
 * Class for validation.
 */
public interface TransactionDataValidator {

    /**
     * Validates the argument class.
     * 
     * @param <V> The type of validated data class.
     * @param validatedData Transaction data class.
     */
    public <V> void validate(V validatedData);

}
