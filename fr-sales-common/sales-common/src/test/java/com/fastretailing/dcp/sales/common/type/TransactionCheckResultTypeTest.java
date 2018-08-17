package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * TransactionCheckResultTypeTest.
 */
public class TransactionCheckResultTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(TransactionCheckResultType.valueOf("BUSINESS_ERROR"),
                theInstance(TransactionCheckResultType.BUSINESS_ERROR));
        assertThat(TransactionCheckResultType.valueOf("NORMAL"),
                theInstance(TransactionCheckResultType.NORMAL));
        assertThat(TransactionCheckResultType.valueOf("VALIDATION_ERROR"),
                theInstance(TransactionCheckResultType.VALIDATION_ERROR));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(TransactionCheckResultType.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(
                        new TransactionCheckResultType[] {TransactionCheckResultType.BUSINESS_ERROR,
                                TransactionCheckResultType.NORMAL,
                                TransactionCheckResultType.VALIDATION_ERROR}));
    }
}
