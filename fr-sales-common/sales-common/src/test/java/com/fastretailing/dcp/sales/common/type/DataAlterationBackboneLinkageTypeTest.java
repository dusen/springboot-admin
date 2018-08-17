package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * DataAlterationBackboneLinkageTypeTest.
 */
public class DataAlterationBackboneLinkageTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(DataAlterationBackboneLinkageType.valueOf("ON"),
                theInstance(DataAlterationBackboneLinkageType.ON));
        assertThat(DataAlterationBackboneLinkageType.valueOf("OFF"),
                theInstance(DataAlterationBackboneLinkageType.OFF));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(DataAlterationBackboneLinkageType.values(),
                IsArrayContainingInAnyOrder
                        .arrayContainingInAnyOrder(new DataAlterationBackboneLinkageType[] {
                                DataAlterationBackboneLinkageType.ON,
                                DataAlterationBackboneLinkageType.OFF}));
    }

    /**
     * Test is method.
     */
    @Test
    public void testIs() {
        assertFalse(DataAlterationBackboneLinkageType.ON.is(null));
        assertTrue(DataAlterationBackboneLinkageType.ON.is(1));
        assertFalse(DataAlterationBackboneLinkageType.ON.is(0));
    }

    /**
     * Test get method.
     */
    @Test
    public void testGet() {
        assertThat(DataAlterationBackboneLinkageType.ON.getValue(), is(1));
    }
}
