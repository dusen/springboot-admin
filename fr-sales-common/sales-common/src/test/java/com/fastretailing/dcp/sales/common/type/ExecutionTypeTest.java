package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * ExecutionTypeTest.
 */
public class ExecutionTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(ExecutionType.valueOf("SCREEN"), theInstance(ExecutionType.SCREEN));
        assertThat(ExecutionType.valueOf("CLOSING_PROCESS"),
                theInstance(ExecutionType.CLOSING_PROCESS));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(ExecutionType.values(), IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(
                new ExecutionType[] {ExecutionType.SCREEN, ExecutionType.CLOSING_PROCESS}));
    }
}
