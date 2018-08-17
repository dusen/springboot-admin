package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * EAIUpdateTypeTest.
 */
public class EAIUpdateTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(EAIUpdateType.valueOf("ADD"), theInstance(EAIUpdateType.ADD));
        assertThat(EAIUpdateType.valueOf("DELETE"), theInstance(EAIUpdateType.DELETE));
        assertThat(EAIUpdateType.valueOf("NEW"), theInstance(EAIUpdateType.NEW));
        assertThat(EAIUpdateType.valueOf("ERROR"), theInstance(EAIUpdateType.ERROR));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(EAIUpdateType.values(),
                IsArrayContainingInAnyOrder
                        .arrayContainingInAnyOrder(new EAIUpdateType[] {EAIUpdateType.ADD,
                                EAIUpdateType.DELETE, EAIUpdateType.NEW, EAIUpdateType.ERROR}));
    }
}
