/**
 * @(#)TransactionUtility.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.util;

/**
 * Transaction utility.
 */
public class TransactionUtility {

    /**
     * Cut off the lower 3 digits of transaction id.
     * 
     * @param salesTransactionId Sales transaction id.
     * @return Sales transaction id without lower 3 digits.
     */
    public static String cutOffLower3Digits(String salesTransactionId) {
        String salesTransactionIdWithoutLower3Digits =
                salesTransactionId.substring(0, (salesTransactionId.length() - 3));
        return salesTransactionIdWithoutLower3Digits;
    }
}


