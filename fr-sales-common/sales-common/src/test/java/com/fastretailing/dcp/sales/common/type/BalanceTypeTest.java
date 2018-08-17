package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * BalanceTypeTest.
 */
public class BalanceTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(BalanceType.valueOf("ITEM_SALES"), theInstance(BalanceType.ITEM_SALES));
        assertThat(BalanceType.valueOf("PAYMENT_TENDER"), theInstance(BalanceType.PAYMENT_TENDER));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(BalanceType.values(), IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(
                new BalanceType[] {BalanceType.ITEM_SALES, BalanceType.PAYMENT_TENDER}));
    }

    /**
     * Test is method.
     */
    @Test
    public void testIs() {
        assertFalse(BalanceType.ITEM_SALES.is(null));
        assertTrue(BalanceType.ITEM_SALES.is("ITEMSALES"));
        assertFalse(BalanceType.ITEM_SALES.is("ITEM_SALES"));
    }

    /**
     * Test get method.
     */
    @Test
    public void testGet() {
        assertThat(BalanceType.ITEM_SALES.getValue(), is("ITEMSALES"));
    }
}
