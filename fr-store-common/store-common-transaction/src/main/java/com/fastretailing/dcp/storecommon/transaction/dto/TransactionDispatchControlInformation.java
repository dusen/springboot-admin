/**
 * @(#)TransactionDispatchControlInformation.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import lombok.Data;

/**
 * DTO class with information for controlling transaction dispatching function
 */
@Data
public class TransactionDispatchControlInformation {

    /**
     * Number of retries.
     */
    private int retryCount = 0;

}
