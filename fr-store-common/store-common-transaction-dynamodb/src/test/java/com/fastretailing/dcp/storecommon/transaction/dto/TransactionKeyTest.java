/**
 * @(#)TransactionKeyTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * TransactionKey test class.
 */
public class TransactionKeyTest {

    @Test
    public void testEquals() {
        TransactionKey x = new TransactionKey();
        x.setTransactionId("1");
        TransactionKey y = new TransactionKey();
        y.setTransactionId("1");

        assertTrue(x.equals(y));

        TransactionKey z = new TransactionKey();
        z.setTransactionId("2");

        assertFalse(x.equals(z));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashcode() {
        TransactionKey x = new TransactionKey();
        x.setTransactionId("1");
        TransactionKey y = new TransactionKey();
        y.setTransactionId("1");

        assertTrue(x.hashCode() == y.hashCode());

        TransactionKey z = new TransactionKey();
        z.setTransactionId("2");

        assertFalse(x.hashCode() == z.hashCode());

    }
}
