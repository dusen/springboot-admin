package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * HemingLinkageFlagTest.
 */
public class HemingLinkageFlagTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(HemingLinkageFlag.valueOf("ON"), theInstance(HemingLinkageFlag.ON));
        assertThat(HemingLinkageFlag.valueOf("OFF"), theInstance(HemingLinkageFlag.OFF));
    }


    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(HemingLinkageFlag.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(
                        new HemingLinkageFlag[] {HemingLinkageFlag.ON, HemingLinkageFlag.OFF}));
    }

}
