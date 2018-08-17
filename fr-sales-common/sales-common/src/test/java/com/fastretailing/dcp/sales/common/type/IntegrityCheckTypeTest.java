package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * IntegrityCheckTypeTest.
 */
public class IntegrityCheckTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(IntegrityCheckType.valueOf("NO_CHECK"),
                theInstance(IntegrityCheckType.NO_CHECK));
        assertThat(IntegrityCheckType.valueOf("CONSISTENCY"),
                theInstance(IntegrityCheckType.CONSISTENCY));
        assertThat(IntegrityCheckType.valueOf("TYPE_DISCREPANCIES"),
                theInstance(IntegrityCheckType.TYPE_DISCREPANCIES));
        assertThat(IntegrityCheckType.valueOf("BALANCE_DISCREPANCIES"),
                theInstance(IntegrityCheckType.BALANCE_DISCREPANCIES));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(IntegrityCheckType.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new IntegrityCheckType[] {
                        IntegrityCheckType.NO_CHECK, IntegrityCheckType.CONSISTENCY,
                        IntegrityCheckType.TYPE_DISCREPANCIES,
                        IntegrityCheckType.BALANCE_DISCREPANCIES}));
    }

    /**
     * Test is method.
     */
    @Test
    public void testIs() {
        assertFalse(IntegrityCheckType.NO_CHECK.is(null));
        assertTrue(IntegrityCheckType.NO_CHECK.is("0"));
        assertFalse(IntegrityCheckType.NO_CHECK.is("1"));
    }

    /**
     * Test get method.
     */
    @Test
    public void testGet() {
        assertThat(IntegrityCheckType.NO_CHECK.getValue(), is("0"));
    }
}
