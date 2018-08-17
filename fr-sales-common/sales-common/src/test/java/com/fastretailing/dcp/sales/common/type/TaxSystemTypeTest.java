package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * TaxSystemTypeTest.
 */
public class TaxSystemTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(TaxSystemType.valueOf("IN"), theInstance(TaxSystemType.IN));
        assertThat(TaxSystemType.valueOf("OUT"), theInstance(TaxSystemType.OUT));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(TaxSystemType.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new TaxSystemType[] {
                        TaxSystemType.IN, TaxSystemType.OUT}));
    }
}
