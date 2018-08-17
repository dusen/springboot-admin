package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * InquiryPatternTypeTest.
 */
public class InquiryPatternTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(InquiryPatternType.valueOf("BOTH"), theInstance(InquiryPatternType.BOTH));
        assertThat(InquiryPatternType.valueOf("INITIAL"), theInstance(InquiryPatternType.INITIAL));
        assertThat(InquiryPatternType.valueOf("LATEST"), theInstance(InquiryPatternType.LATEST));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(InquiryPatternType.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(
                        new InquiryPatternType[] {InquiryPatternType.BOTH,
                                InquiryPatternType.INITIAL, InquiryPatternType.LATEST}));
    }

    /**
     * Test compare method.
     */
    @Test
    public void testCompare() {
        assertFalse(InquiryPatternType.compareInquiryPatternType(InquiryPatternType.BOTH, null));
        assertTrue(InquiryPatternType.compareInquiryPatternType(InquiryPatternType.BOTH, 3));
        assertFalse(InquiryPatternType.compareInquiryPatternType(InquiryPatternType.BOTH, 0));
    }
}
