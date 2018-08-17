package com.fastretailing.dcp.storecommon.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * PlatformShortNameTest.
 */
public class PlatformShortNameTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(PlatformShortName.valueOf("PRICE"), theInstance(PlatformShortName.PRICE));
        assertThat(PlatformShortName.valueOf("SALES"), theInstance(PlatformShortName.SALES));
        assertThat(PlatformShortName.valueOf("STORE_INVENTORY"),
                theInstance(PlatformShortName.STORE_INVENTORY));
        assertThat(PlatformShortName.valueOf("STORE_ADMIN"),
                theInstance(PlatformShortName.STORE_ADMIN));
        assertThat(PlatformShortName.valueOf("EMPLOYEE"), theInstance(PlatformShortName.EMPLOYEE));
        assertThat(PlatformShortName.valueOf("EDGE"), theInstance(PlatformShortName.EDGE));
    }

    /**
     * /** Test values method.
     */
    @Test
    public void testValues() {
        assertThat(PlatformShortName.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new PlatformShortName[] {
                        PlatformShortName.PRICE, PlatformShortName.SALES, PlatformShortName.STORE_INVENTORY,
                        PlatformShortName.STORE_ADMIN, PlatformShortName.EMPLOYEE, PlatformShortName.EDGE}));
}}
