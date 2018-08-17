package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * ReturnCompleteFlagTest.
 */
public class ReturnCompleteFlagTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(ReturnCompleteFlag.valueOf("ON"), theInstance(ReturnCompleteFlag.ON));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(ReturnCompleteFlag.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(
                        new ReturnCompleteFlag[] {ReturnCompleteFlag.ON, ReturnCompleteFlag.OFF}));
    }
}
