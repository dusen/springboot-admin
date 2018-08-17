package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * DataAlterationSalesLinkageTypeTest.
 */
public class DataAlterationSalesLinkageTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(DataAlterationSalesLinkageType.valueOf("ON"),
                theInstance(DataAlterationSalesLinkageType.ON));
        assertThat(DataAlterationSalesLinkageType.valueOf("OFF"),
                theInstance(DataAlterationSalesLinkageType.OFF));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(DataAlterationSalesLinkageType.values(),
                IsArrayContainingInAnyOrder
                        .arrayContainingInAnyOrder(new DataAlterationSalesLinkageType[] {
                                DataAlterationSalesLinkageType.ON,
                                DataAlterationSalesLinkageType.OFF}));
    }

    /**
     * Test is method.
     */
    @Test
    public void testIs() {
        assertFalse(DataAlterationSalesLinkageType.ON.is(null));
        assertTrue(DataAlterationSalesLinkageType.ON.is(1));
        assertFalse(DataAlterationSalesLinkageType.ON.is(0));
    }

    /**
     * Test get method.
     */
    @Test
    public void testGet() {
        assertThat(DataAlterationSalesLinkageType.ON.getValue(), is(1));
    }
}
