/**
 * @(#)TransactionStatusKeyTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * TransactionStatusKeyT test class.
 */
public class TransactionStatusKeyTest {

    @Test
    public void testEquals() {
        TransactionStatusKey x = new TransactionStatusKey();
        x.setTransactionId("1");
        x.setBusinessStatus("2");
        TransactionStatusKey y = new TransactionStatusKey();
        y.setTransactionId("1");
        y.setBusinessStatus("2");

        assertTrue(x.equals(y));

        TransactionStatusKey z = new TransactionStatusKey();
        z.setTransactionId("1");
        z.setBusinessStatus("3");

        assertFalse(x.equals(z));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashcode() {
        TransactionStatusKey x = new TransactionStatusKey();
        x.setTransactionId("1");
        x.setBusinessStatus("2");
        TransactionStatusKey y = new TransactionStatusKey();
        y.setTransactionId("1");
        y.setBusinessStatus("2");

        assertTrue(x.hashCode() == y.hashCode());

        TransactionStatusKey z = new TransactionStatusKey();
        z.setTransactionId("1");
        z.setBusinessStatus("3");

        assertFalse(x.hashCode() == z.hashCode());
    }
}
