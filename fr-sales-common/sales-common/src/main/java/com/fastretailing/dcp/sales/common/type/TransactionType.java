/**
 * @(#)TransactionType.java
 * 
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the transaction type.
 *
 */
public enum TransactionType {

    /** Value of sale. */
    SALE("SALE"),

    /** Value of return. */
    RETURN("RETURN"),

    /** Pvoid. */
    PVOID("PVOID");

    /**
     * String representation of the transaction type.
     */
    @Getter
    private String transactionType;

    /**
     * Set the string representation of the the transaction type.
     * 
     * @param value Update type.
     */
    private TransactionType(String value) {
        this.transactionType = value;
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param sourceTransactionType Source transaction type.
     * @param transactionType Transaction Type.
     * @return True if the given object represents a String equivalent to this string, false
     *         Otherwise.
     */
    public static boolean compare(TransactionType sourceTransactionType,
            final String transactionType) {
        if (transactionType == null) {
            return false;
        }
        return sourceTransactionType.getTransactionType().equals(transactionType);
    }
}
