package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * SalesLinkageTypeTest.
 */
public class SalesLinkageTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(SalesLinkageType.valueOf("SALES_LINKAGE"),
                theInstance(SalesLinkageType.SALES_LINKAGE));
        assertThat(SalesLinkageType.valueOf("NOT_SALES_LINKAGE"),
                theInstance(SalesLinkageType.NOT_SALES_LINKAGE));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(SalesLinkageType.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new SalesLinkageType[] {
                        SalesLinkageType.SALES_LINKAGE, SalesLinkageType.NOT_SALES_LINKAGE}));
    }

    /**
     * Test is method.
     */
    @Test
    public void testIs() {
        assertFalse(SalesLinkageType.SALES_LINKAGE.is(null));
        assertTrue(SalesLinkageType.SALES_LINKAGE.is(0));
        assertFalse(SalesLinkageType.SALES_LINKAGE.is(1));
    }

    /**
     * Test get method.
     */
    @Test
    public void testGet() {
        assertThat(SalesLinkageType.SALES_LINKAGE.getValue(), is(0));
    }
}
