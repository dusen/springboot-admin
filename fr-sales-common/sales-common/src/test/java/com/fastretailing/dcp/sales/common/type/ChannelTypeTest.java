package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * ChannelTypeTest.
 */
public class ChannelTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(ChannelType.valueOf("EC"), theInstance(ChannelType.EC));
        assertThat(ChannelType.valueOf("STORE"), theInstance(ChannelType.STORE));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(ChannelType.values(), IsArrayContainingInAnyOrder
                .arrayContainingInAnyOrder(new ChannelType[] {ChannelType.EC, ChannelType.STORE}));
    }
}
