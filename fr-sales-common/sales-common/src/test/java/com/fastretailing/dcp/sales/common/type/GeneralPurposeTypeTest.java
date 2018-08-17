package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * GeneralPurposeTypeTest.
 */
public class GeneralPurposeTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(GeneralPurposeType.valueOf("TIME_ZONE"),
                theInstance(GeneralPurposeType.TIME_ZONE));
        assertThat(GeneralPurposeType.valueOf("PRICE_CHANGE_TIME_DIFFERENCE"),
                theInstance(GeneralPurposeType.PRICE_CHANGE_TIME_DIFFERENCE));
        assertThat(GeneralPurposeType.valueOf("PRICE_CHANGE_TIME"),
                theInstance(GeneralPurposeType.PRICE_CHANGE_TIME));
        assertThat(GeneralPurposeType.valueOf("BRAND"), theInstance(GeneralPurposeType.BRAND));
        assertThat(GeneralPurposeType.valueOf("EC_FLAG"), theInstance(GeneralPurposeType.EC_FLAG));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(GeneralPurposeType.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(
                        new GeneralPurposeType[] {GeneralPurposeType.TIME_ZONE,
                                GeneralPurposeType.PRICE_CHANGE_TIME_DIFFERENCE,
                                GeneralPurposeType.PRICE_CHANGE_TIME, GeneralPurposeType.BRAND,
                                GeneralPurposeType.EC_FLAG}));
    }

    /**
     * Test compare method.
     */
    @Test
    public void testCompare() {
        assertFalse(GeneralPurposeType.compare(GeneralPurposeType.TIME_ZONE, null));
        assertTrue(GeneralPurposeType.compare(GeneralPurposeType.TIME_ZONE, "time_zone"));
        assertFalse(GeneralPurposeType.compare(GeneralPurposeType.TIME_ZONE, "time"));
    }
}
