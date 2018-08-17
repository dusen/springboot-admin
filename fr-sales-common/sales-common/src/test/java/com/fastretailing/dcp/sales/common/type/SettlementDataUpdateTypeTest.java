package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * UpdateTypeTest.
 */
public class SettlementDataUpdateTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(UpdateType.valueOf("CORRECTION"), theInstance(UpdateType.CORRECTION));
        assertThat(UpdateType.valueOf("DELETE"), theInstance(UpdateType.DELETE));
        assertThat(UpdateType.valueOf("INSERT"), theInstance(UpdateType.INSERT));
        assertThat(UpdateType.valueOf("UPDATE"), theInstance(UpdateType.UPDATE));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(UpdateType.values(),
                IsArrayContainingInAnyOrder
                        .arrayContainingInAnyOrder(new UpdateType[] {UpdateType.CORRECTION,
                                UpdateType.DELETE, UpdateType.INSERT, UpdateType.UPDATE}));
    }
}
