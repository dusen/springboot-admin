/**
 * @(#)BusinessStatusTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * Business Status test class.
 *
 */
public class BusinessStatusTest {

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(BusinessStatus.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new BusinessStatus[] {
                        BusinessStatus.DUPLICATE, BusinessStatus.ERROR, BusinessStatus.SUCCESS}));
    }

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(BusinessStatus.valueOf("DUPLICATE"), theInstance(BusinessStatus.DUPLICATE));
        assertThat(BusinessStatus.valueOf("ERROR"), theInstance(BusinessStatus.ERROR));
        assertThat(BusinessStatus.valueOf("SUCCESS"), theInstance(BusinessStatus.SUCCESS));
    }
}
