package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * ErrorNotificationPatternTest.
 */
public class ErrorNotificationPatternTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(ErrorNotificationPattern.valueOf("COUNTRY_UNIT"),
                theInstance(ErrorNotificationPattern.COUNTRY_UNIT));
        assertThat(ErrorNotificationPattern.valueOf("STORE_UNIT"),
                theInstance(ErrorNotificationPattern.STORE_UNIT));
        assertThat(ErrorNotificationPattern.valueOf("DETAILS"),
                theInstance(ErrorNotificationPattern.DETAILS));
        assertThat(ErrorNotificationPattern.valueOf("ALL_ERRORS"),
                theInstance(ErrorNotificationPattern.ALL_ERRORS));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(ErrorNotificationPattern.values(), IsArrayContainingInAnyOrder
                .arrayContainingInAnyOrder(new ErrorNotificationPattern[] {
                        ErrorNotificationPattern.COUNTRY_UNIT, ErrorNotificationPattern.STORE_UNIT,
                        ErrorNotificationPattern.DETAILS, ErrorNotificationPattern.ALL_ERRORS}));
    }
}
