/**
 * @(#)ReceiveMessageTest.java
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
public class ReceiveMessageTest {

    /**
     * Test equals.
     */
    @Test
    public void testEquals() {
        ReceiveMessage x = new ReceiveMessage();
        x.setTransactionId("test");

        ReceiveMessage y = new ReceiveMessage();
        y.setTransactionId("test");

        assertTrue(x.equals(y));

        ReceiveMessage z = new ReceiveMessage();
        assertFalse(x.equals(z));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashcode() {
        ReceiveMessage x = new ReceiveMessage();
        x.setTransactionId("test");

        ReceiveMessage y = new ReceiveMessage();
        y.setTransactionId("test");

        assertTrue(x.hashCode() == y.hashCode());

        TransactionData z = new TransactionData();
        assertFalse(x.hashCode() == z.hashCode());
    }
}
