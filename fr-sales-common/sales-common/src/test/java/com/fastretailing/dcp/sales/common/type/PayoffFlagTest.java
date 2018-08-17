package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * PayoffFlagTest.
 */
public class PayoffFlagTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(PayoffFlag.valueOf("END"), theInstance(PayoffFlag.END));
        assertThat(PayoffFlag.valueOf("PREPARED"), theInstance(PayoffFlag.PREPARED));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(PayoffFlag.values(), IsArrayContainingInAnyOrder
                .arrayContainingInAnyOrder(new PayoffFlag[] {PayoffFlag.END, PayoffFlag.PREPARED}));
    }
}
