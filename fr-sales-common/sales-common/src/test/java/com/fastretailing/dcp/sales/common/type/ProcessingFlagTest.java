package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * ProcessingFlagTest.
 */
public class ProcessingFlagTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(ProcessingFlag.valueOf("PROCESSED"),
                theInstance(ProcessingFlag.PROCESSED));
        assertThat(ProcessingFlag.valueOf("UNPROCESSED"),
                theInstance(ProcessingFlag.UNPROCESSED));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(ProcessingFlag.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new ProcessingFlag[] {
                        ProcessingFlag.PROCESSED, ProcessingFlag.UNPROCESSED}));
    }
}
