package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * BIHStatusTest.
 */
public class BIHStatusTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(BIHStatus.valueOf("YET"), theInstance(BIHStatus.YET));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(BIHStatus.values(), IsArrayContainingInAnyOrder
                .arrayContainingInAnyOrder(new BIHStatus[] {BIHStatus.YET}));
    }
}
