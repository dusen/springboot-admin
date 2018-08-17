package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * DataAlterationFlagTest.
 */
public class DataAlterationFlagTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(DataAlterationFlag.valueOf("ON"), theInstance(DataAlterationFlag.ON));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(DataAlterationFlag.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(
                        new DataAlterationFlag[] {DataAlterationFlag.ON, DataAlterationFlag.OFF}));
    }
}
