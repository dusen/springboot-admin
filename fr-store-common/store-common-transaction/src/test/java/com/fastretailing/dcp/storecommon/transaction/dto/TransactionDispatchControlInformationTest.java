/**
 * @(#)TransactionDispatchControlInformationTest.java
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
public class TransactionDispatchControlInformationTest {

    /**
     * Test equals.
     */
    @Test
    public void testEquals() {
        TransactionDispatchControlInformation x = new TransactionDispatchControlInformation();
        x.setRetryCount(10);

        TransactionDispatchControlInformation y = new TransactionDispatchControlInformation();
        y.setRetryCount(10);

        assertTrue(x.equals(y));

        TransactionDispatchControlInformation z = new TransactionDispatchControlInformation();
        assertFalse(x.equals(z));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashcode() {
        TransactionDispatchControlInformation x = new TransactionDispatchControlInformation();
        x.setRetryCount(10);

        TransactionDispatchControlInformation y = new TransactionDispatchControlInformation();
        y.setRetryCount(10);

        assertTrue(x.hashCode() == y.hashCode());

        TransactionDispatchControlInformation z = new TransactionDispatchControlInformation();
        assertFalse(x.hashCode() == z.hashCode());
    }
}
