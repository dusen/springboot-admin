package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * RtlogTypeTest.
 */
public class RtlogTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(RtlogType.valueOf("CONSUMPTION_TAX"), theInstance(RtlogType.CONSUMPTION_TAX));
        assertThat(RtlogType.valueOf("SALES_TAX"), theInstance(RtlogType.SALES_TAX));
        assertThat(RtlogType.valueOf("VALUE_ADDED_TAX"), theInstance(RtlogType.VALUE_ADDED_TAX));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(RtlogType.values(),
                IsArrayContainingInAnyOrder
                        .arrayContainingInAnyOrder(new RtlogType[] {RtlogType.CONSUMPTION_TAX,
                                RtlogType.SALES_TAX, RtlogType.VALUE_ADDED_TAX}));
    }
}
