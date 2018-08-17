package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * TransactionTypeTest.
 */
public class TransactionTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(TransactionType.valueOf("RETURN"), theInstance(TransactionType.RETURN));
        assertThat(TransactionType.valueOf("SALE"), theInstance(TransactionType.SALE));
        assertThat(TransactionType.valueOf("PVOID"), theInstance(TransactionType.PVOID));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(TransactionType.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new TransactionType[] {
                        TransactionType.RETURN, TransactionType.SALE, TransactionType.PVOID}));
    }
}
