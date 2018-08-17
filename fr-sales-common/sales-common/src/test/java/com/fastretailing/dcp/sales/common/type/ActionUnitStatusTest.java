package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * ActionUnitStatusTest.
 */
public class ActionUnitStatusTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(ActionUnitStatus.valueOf("INDIVIDUAL"),
                theInstance(ActionUnitStatus.INDIVIDUAL));
        assertThat(ActionUnitStatus.valueOf("TOTAL"), theInstance(ActionUnitStatus.TOTAL));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(ActionUnitStatus.values(), IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(
                new ActionUnitStatus[] {ActionUnitStatus.INDIVIDUAL, ActionUnitStatus.TOTAL}));
    }
}
