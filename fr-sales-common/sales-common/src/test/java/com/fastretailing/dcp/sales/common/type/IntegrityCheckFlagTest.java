package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * IntegrityCheckFlagTest.
 */
public class IntegrityCheckFlagTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(IntegrityCheckFlag.valueOf("INTEGRITY_COMPLETE"),
                theInstance(IntegrityCheckFlag.INTEGRITY_COMPLETE));
        assertThat(IntegrityCheckFlag.valueOf("MISMATCH"),
                theInstance(IntegrityCheckFlag.MISMATCH));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(IntegrityCheckFlag.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new IntegrityCheckFlag[] {
                        IntegrityCheckFlag.INTEGRITY_COMPLETE, IntegrityCheckFlag.MISMATCH}));
    }
}
