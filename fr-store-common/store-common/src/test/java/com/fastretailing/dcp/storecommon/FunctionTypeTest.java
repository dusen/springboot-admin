package com.fastretailing.dcp.storecommon;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * FunctionTypeTest.
 */
public class FunctionTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(FunctionType.valueOf("BATCH"), theInstance(FunctionType.BATCH));
        assertThat(FunctionType.valueOf("API"), theInstance(FunctionType.API));
        assertThat(FunctionType.valueOf("COMMON"), theInstance(FunctionType.COMMON));
        assertThat(FunctionType.valueOf("SCREEN"), theInstance(FunctionType.SCREEN));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(FunctionType.values(),
                IsArrayContainingInAnyOrder
                        .arrayContainingInAnyOrder(new FunctionType[] {FunctionType.BATCH,
                                FunctionType.API, FunctionType.COMMON, FunctionType.SCREEN}));
    }
}
