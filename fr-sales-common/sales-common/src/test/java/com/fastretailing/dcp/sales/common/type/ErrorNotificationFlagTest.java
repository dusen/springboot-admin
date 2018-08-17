package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * ErrorNotificationFlagTest.
 */
public class ErrorNotificationFlagTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(ErrorNotificationFlag.valueOf("UNSENT"),
                theInstance(ErrorNotificationFlag.UNSENT));
        assertThat(ErrorNotificationFlag.valueOf("SENT"), theInstance(ErrorNotificationFlag.SENT));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(ErrorNotificationFlag.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new ErrorNotificationFlag[] {
                        ErrorNotificationFlag.UNSENT, ErrorNotificationFlag.SENT}));
    }
}
