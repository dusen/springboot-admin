package com.fastretailing.dcp.storecommon.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * ChannelCodeTest.
 */
public class ChannelCodeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(ChannelCode.valueOf("PC"), theInstance(ChannelCode.PC));
        assertThat(ChannelCode.valueOf("SMART_PHONE"), theInstance(ChannelCode.SMART_PHONE));
        assertThat(ChannelCode.valueOf("FEATURE_PHONE"), theInstance(ChannelCode.FEATURE_PHONE));
        assertThat(ChannelCode.valueOf("GENRERAL_SUSTITUTE_ORDER"),
                theInstance(ChannelCode.GENRERAL_SUSTITUTE_ORDER));
        assertThat(ChannelCode.valueOf("CUSTOMIZED_ORDER"),
                theInstance(ChannelCode.CUSTOMIZED_ORDER));
        assertThat(ChannelCode.valueOf("CORPORATE_ORDER"),
                theInstance(ChannelCode.CORPORATE_ORDER));
        assertThat(ChannelCode.valueOf("POS"), theInstance(ChannelCode.POS));
        assertThat(ChannelCode.valueOf("MOBILE_POS"), theInstance(ChannelCode.MOBILE_POS));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(ChannelCode.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new ChannelCode[] {
                        ChannelCode.PC, ChannelCode.SMART_PHONE, ChannelCode.FEATURE_PHONE,
                        ChannelCode.GENRERAL_SUSTITUTE_ORDER, ChannelCode.CUSTOMIZED_ORDER,
                        ChannelCode.CORPORATE_ORDER, ChannelCode.POS, ChannelCode.MOBILE_POS}));
    }
}
