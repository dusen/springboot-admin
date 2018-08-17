package com.fastretailing.dcp.storecommon;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * MessageTypeTest.
 */
public class MessageTypeTest {

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(MessageType.valueOf("NORMAL"), theInstance(MessageType.NORMAL));
        assertThat(MessageType.valueOf("ALERT"), theInstance(MessageType.ALERT));
        assertThat(MessageType.valueOf("VALIDATE_ERROR"), theInstance(MessageType.VALIDATE_ERROR));
        assertThat(MessageType.valueOf("BUSINESS_ERROR"), theInstance(MessageType.BUSINESS_ERROR));
        assertThat(MessageType.valueOf("SYSTEM_ERROR"), theInstance(MessageType.SYSTEM_ERROR));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(MessageType.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new MessageType[] {
                        MessageType.NORMAL, MessageType.ALERT, MessageType.VALIDATE_ERROR,
                        MessageType.BUSINESS_ERROR, MessageType.SYSTEM_ERROR}));
    }
}
