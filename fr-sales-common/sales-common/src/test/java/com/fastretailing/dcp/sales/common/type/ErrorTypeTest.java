package com.fastretailing.dcp.sales.common.type;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * ErrorTypeTest.
 */
public class ErrorTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(ErrorType.valueOf("VALIDATION_ERROR"), theInstance(ErrorType.VALIDATION_ERROR));
        assertThat(ErrorType.valueOf("RELATION_ERROR"), theInstance(ErrorType.RELATION_ERROR));
        assertThat(ErrorType.valueOf("BUSINESS_ERROR"), theInstance(ErrorType.BUSINESS_ERROR));
        assertThat(ErrorType.valueOf("BUSINESS_DATE_ERROR"),
                theInstance(ErrorType.BUSINESS_DATE_ERROR));
        assertThat(ErrorType.valueOf("BEFORE_OPEN_DATE_ERROR"),
                theInstance(ErrorType.BEFORE_OPEN_DATE_ERROR));
        assertThat(ErrorType.valueOf("AFTER_CLOSE_DATE_ERROR"),
                theInstance(ErrorType.AFTER_CLOSE_DATE_ERROR));
        assertThat(ErrorType.valueOf("UPDATE_BUSINESS_DATE_ERROR"),
                theInstance(ErrorType.UPDATE_BUSINESS_DATE_ERROR));
        assertThat(ErrorType.valueOf("CLOSE_PROCESS_COMPLETED_DATE_ERROR"),
                theInstance(ErrorType.CLOSE_PROCESS_COMPLETED_DATE_ERROR));
        assertThat(ErrorType.valueOf("TENDER_ERROR"), theInstance(ErrorType.TENDER_ERROR));
        assertThat(ErrorType.valueOf("AMOUNT_BALANCE_ERROR"),
                theInstance(ErrorType.AMOUNT_BALANCE_ERROR));
        assertThat(ErrorType.valueOf("UNIQUE_CONSTRAINTS_ERROR"),
                theInstance(ErrorType.UNIQUE_CONSTRAINTS_ERROR));
        assertThat(ErrorType.valueOf("PAYOFF_CONFORMITY_CHECK"),
                theInstance(ErrorType.PAYOFF_CONFORMITY_CHECK));
        assertThat(ErrorType.valueOf("NO_ERROR"), theInstance(ErrorType.NO_ERROR));
    }


    /**
     * Test values method.
     */
    @Test
    public void testValues() {
        assertThat(ErrorType.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new ErrorType[] {
                        ErrorType.VALIDATION_ERROR, ErrorType.RELATION_ERROR,
                        ErrorType.BUSINESS_ERROR, ErrorType.BUSINESS_DATE_ERROR,
                        ErrorType.BEFORE_OPEN_DATE_ERROR, ErrorType.AFTER_CLOSE_DATE_ERROR,
                        ErrorType.UPDATE_BUSINESS_DATE_ERROR,
                        ErrorType.CLOSE_PROCESS_COMPLETED_DATE_ERROR, ErrorType.TENDER_ERROR,
                        ErrorType.AMOUNT_BALANCE_ERROR, ErrorType.UNIQUE_CONSTRAINTS_ERROR,
                        ErrorType.PAYOFF_CONFORMITY_CHECK, ErrorType.NO_ERROR}));
    }

}
