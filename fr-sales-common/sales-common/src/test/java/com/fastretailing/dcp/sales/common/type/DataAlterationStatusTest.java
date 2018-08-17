package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * DataAlterationStatusTest.
 */
public class DataAlterationStatusTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(DataAlterationStatus.valueOf("NO_ALTERER"),
                theInstance(DataAlterationStatus.NO_ALTERER));
        assertThat(DataAlterationStatus.valueOf("ALTERER_FINISH"),
                theInstance(DataAlterationStatus.ALTERER_FINISH));
        assertThat(DataAlterationStatus.valueOf("ALTERERING"),
                theInstance(DataAlterationStatus.ALTERERING));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(DataAlterationStatus.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new DataAlterationStatus[] {
                        DataAlterationStatus.NO_ALTERER, DataAlterationStatus.ALTERER_FINISH,
                        DataAlterationStatus.ALTERERING}));
    }

    /**
     * Test is method.
     */
    @Test
    public void testIs() {
        assertFalse(DataAlterationStatus.NO_ALTERER.is(null));
        assertTrue(DataAlterationStatus.NO_ALTERER.is("0"));
        assertFalse(DataAlterationStatus.NO_ALTERER.is("1"));
    }

    /**
     * Test get method.
     */
    @Test
    public void testGet() {
        assertThat(DataAlterationStatus.NO_ALTERER.getValue(), is("0"));
    }
}
