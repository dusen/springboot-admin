package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * Transaction consistency error type test.
 */
public class TransactionConsistencyErrorTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(TransactionConsistencyErrorType.valueOf("AMOUNT_BALANCE_ERROR"),
                theInstance(TransactionConsistencyErrorType.AMOUNT_BALANCE_ERROR));
        assertThat(TransactionConsistencyErrorType.valueOf("VALIDATION_ERROR"),
                theInstance(TransactionConsistencyErrorType.VALIDATION_ERROR));
        assertThat(TransactionConsistencyErrorType.valueOf("UPDATE_BUSINESS_DATE_ERROR"),
                theInstance(TransactionConsistencyErrorType.UPDATE_BUSINESS_DATE_ERROR));
        assertThat(TransactionConsistencyErrorType.valueOf("RELATION_ERROR"),
                theInstance(TransactionConsistencyErrorType.RELATION_ERROR));
        assertThat(TransactionConsistencyErrorType.valueOf("TENDER_ERROR"),
                theInstance(TransactionConsistencyErrorType.TENDER_ERROR));
        assertThat(TransactionConsistencyErrorType.valueOf("AMOUNT_BALANCE_ERROR"),
                theInstance(TransactionConsistencyErrorType.AMOUNT_BALANCE_ERROR));
        assertThat(TransactionConsistencyErrorType.valueOf("OTHER_EXCEPTIONS"),
                theInstance(TransactionConsistencyErrorType.OTHER_EXCEPTIONS));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(TransactionConsistencyErrorType.values(),
                IsArrayContainingInAnyOrder
                        .arrayContainingInAnyOrder(new TransactionConsistencyErrorType[] {
                                TransactionConsistencyErrorType.AMOUNT_BALANCE_ERROR,
                                TransactionConsistencyErrorType.VALIDATION_ERROR,
                                TransactionConsistencyErrorType.UPDATE_BUSINESS_DATE_ERROR,
                                TransactionConsistencyErrorType.RELATION_ERROR,
                                TransactionConsistencyErrorType.TENDER_ERROR,
                                TransactionConsistencyErrorType.OTHER_EXCEPTIONS}));
    }

    /**
     * Test valueOf method.
     */
    @Test
    public void testMessageIdValue() {
        assertEquals("E_SLS_66000181", TransactionConsistencyErrorType
                .getMessageId(ErrorType.VALIDATION_ERROR.getErrorType()));
    }
}
