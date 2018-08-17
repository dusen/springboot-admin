package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * EAISendStatusTest.
 */
public class EAISendStatusTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(EAISendStatus.valueOf("ONE"), theInstance(EAISendStatus.ONE));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(EAISendStatus.values(), IsArrayContainingInAnyOrder
                .arrayContainingInAnyOrder(new EAISendStatus[] {EAISendStatus.ONE}));
    }
}
