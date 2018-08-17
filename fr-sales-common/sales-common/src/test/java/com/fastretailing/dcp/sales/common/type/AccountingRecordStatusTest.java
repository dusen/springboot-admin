package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * AccountingRecordStatusTest.
 */
public class AccountingRecordStatusTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(AccountingRecordStatus.valueOf("NOT_RECORDED_YET"),
                theInstance(AccountingRecordStatus.NOT_RECORDED_YET));
        assertThat(AccountingRecordStatus.valueOf("RECORDED"),
                theInstance(AccountingRecordStatus.RECORDED));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(AccountingRecordStatus.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new AccountingRecordStatus[] {
                        AccountingRecordStatus.NOT_RECORDED_YET, AccountingRecordStatus.RECORDED}));
    }
}
