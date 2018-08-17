/**
 * @(#)ProcessingStatusTest.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * ProcessingStatus test class.
 */
public class ProcessingStatusTest {

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(ProcessingStatus.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new ProcessingStatus[] {
                        ProcessingStatus.PROCESSING, ProcessingStatus.DO_NOT_RECOVER,
                        ProcessingStatus.COMPLETION, ProcessingStatus.ERROR}));
    }

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(ProcessingStatus.valueOf("PROCESSING"),
                theInstance(ProcessingStatus.PROCESSING));
        assertThat(ProcessingStatus.valueOf("DO_NOT_RECOVER"),
                theInstance(ProcessingStatus.DO_NOT_RECOVER));
        assertThat(ProcessingStatus.valueOf("COMPLETION"),
                theInstance(ProcessingStatus.COMPLETION));
        assertThat(ProcessingStatus.valueOf("ERROR"), theInstance(ProcessingStatus.ERROR));
    }

    /**
     * Test equals method.
     */
    @Test
    public void testEquals() {
        assertTrue(ProcessingStatus.PROCESSING.equals("processing"));
        assertFalse(ProcessingStatus.PROCESSING.equals("error"));
        assertTrue(ProcessingStatus.ERROR.equals("error"));
        assertFalse(ProcessingStatus.ERROR.equals("processing"));
        assertTrue(ProcessingStatus.DO_NOT_RECOVER.equals("doNotRecover"));
        assertFalse(ProcessingStatus.DO_NOT_RECOVER.equals("processing"));
        assertTrue(ProcessingStatus.COMPLETION.equals("completion"));
        assertFalse(ProcessingStatus.COMPLETION.equals("processing"));
    }

    /**
     * Test containsCompletionStatus method.
     */
    @Test
    public void testContainsCompletionStatus() {

        List<String> targetStatusList = new ArrayList<>();
        targetStatusList.add("processing");
        targetStatusList.add("doNotRecover");
        targetStatusList.add("completion");
        assertTrue(ProcessingStatus.containsCompletionStatus(targetStatusList));

        targetStatusList = new ArrayList<>();
        targetStatusList.add("error");
        targetStatusList.add("processing");
        targetStatusList.add("doNotRecover");
        assertTrue(ProcessingStatus.containsCompletionStatus(targetStatusList));

        targetStatusList = new ArrayList<>();
        targetStatusList.add("error");
        targetStatusList.add("processing");
        targetStatusList.add("completion");
        targetStatusList.add("doNotRecover");
        assertTrue(ProcessingStatus.containsCompletionStatus(targetStatusList));

        targetStatusList = new ArrayList<>();
        targetStatusList.add("processing");
        targetStatusList.add("doNotRecover");
        assertFalse(ProcessingStatus.containsCompletionStatus(targetStatusList));

    }

    /**
     * Test toString method.
     */
    @Test
    public void testToString() {
        assertEquals("processing", ProcessingStatus.PROCESSING.toString());
        assertEquals("error", ProcessingStatus.ERROR.toString());
        assertEquals("doNotRecover", ProcessingStatus.DO_NOT_RECOVER.toString());
        assertEquals("completion", ProcessingStatus.COMPLETION.toString());
    }
}
