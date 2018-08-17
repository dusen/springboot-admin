/**
 * @(#)TransactionDataTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * TransactionData test class.
 */
public class TransactionDataTest {

    /**
     * Test equals.
     */
    @Test
    public void testEquals() {
        TransactionData x = new TransactionData();
        x.setBusinessTransactionData("test");

        TransactionData y = new TransactionData();
        y.setBusinessTransactionData("test");

        assertTrue(x.equals(y));

        TransactionData z = new TransactionData();
        assertFalse(x.equals(z));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashcode() {
        TransactionData x = new TransactionData();
        x.setBusinessTransactionData("test");

        TransactionData y = new TransactionData();
        y.setBusinessTransactionData("test");

        assertTrue(x.hashCode() == y.hashCode());

        TransactionData z = new TransactionData();
        assertFalse(x.hashCode() == z.hashCode());
    }
}
