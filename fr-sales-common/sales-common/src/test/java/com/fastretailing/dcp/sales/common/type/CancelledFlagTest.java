package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * CancelledFlagTest.
 */
public class CancelledFlagTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(CancelledFlag.valueOf("ON"), theInstance(CancelledFlag.ON));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(CancelledFlag.values(), IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(
                new CancelledFlag[] {CancelledFlag.ON, CancelledFlag.OFF}));
    }
}
