package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * ProductClassificationTest.
 */
public class ProductClassificationTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(ProductClassification.valueOf("ITEM"), theInstance(ProductClassification.ITEM));
        assertThat(ProductClassification.valueOf("NMITEM"),
                theInstance(ProductClassification.NMITEM));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(ProductClassification.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new ProductClassification[] {
                        ProductClassification.ITEM, ProductClassification.NMITEM}));
    }

    /**
     * Test compare method.
     */
    @Test
    public void testCompare() {
        assertFalse(ProductClassification.compare(ProductClassification.ITEM, null));
        assertTrue(ProductClassification.compare(ProductClassification.ITEM, "ITEM"));
        assertFalse(ProductClassification.compare(ProductClassification.ITEM, "ITEM0"));
    }
}
