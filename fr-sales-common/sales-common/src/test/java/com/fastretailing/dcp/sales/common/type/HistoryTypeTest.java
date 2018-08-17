package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * HistoryTypeTest.
 */
public class HistoryTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(HistoryType.valueOf("BEFORE"), theInstance(HistoryType.BEFORE));
        assertThat(HistoryType.valueOf("AFTER"), theInstance(HistoryType.AFTER));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(HistoryType.values(), IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(
                new HistoryType[] {HistoryType.BEFORE, HistoryType.AFTER}));
    }

    /**
     * Test is method.
     */
    @Test
    public void testIs() {
        assertFalse(HistoryType.BEFORE.is(null));
        assertTrue(HistoryType.BEFORE.is(0));
        assertFalse(HistoryType.BEFORE.is(1));
    }

    /**
     * Test get method.
     */
    @Test
    public void testGet() {
        assertThat(HistoryType.BEFORE.getValue(), is(0));
    }
}
