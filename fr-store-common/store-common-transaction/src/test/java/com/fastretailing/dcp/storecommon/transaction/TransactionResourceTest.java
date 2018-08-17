/**
 * @(#)TransactionResourceTest.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.Test;

/**
 * TransactionResource test class.
 */
public class TransactionResourceTest {

    /**
     * Map set get test.
     */
    @Test
    public void testMapGet() {

        Map<TransactionResource, String> map = new HashMap<>();
        map.put(TransactionResource.TRANSACTION_TYPE, "test");

        assertThat(map.get(TransactionResource.TRANSACTION_TYPE), is("test"));
    }

    /**
     * Test values method.
     */
    @Test
    public void testValues() {

        assertThat(TransactionResource.values(),
                IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new TransactionResource[] {
                        TransactionResource.BRAND, TransactionResource.LANGUAGE,
                        TransactionResource.REGION, TransactionResource.TRANSACTION_TYPE,
                        TransactionResource.CHANNEL_CODE, TransactionResource.STORE_CODE,
                        TransactionResource.INTEGRATED_ORDER_ID,
                        TransactionResource.ORDER_SUB_NUMBER, TransactionResource.UPDATE_TYPE}));
    }

    /**
     * Test valueOf method.
     */
    @Test
    public void testValueOf() {
        assertThat(TransactionResource.valueOf("BRAND"), theInstance(TransactionResource.BRAND));
        assertThat(TransactionResource.valueOf("LANGUAGE"),
                theInstance(TransactionResource.LANGUAGE));
        assertThat(TransactionResource.valueOf("REGION"), theInstance(TransactionResource.REGION));
        assertThat(TransactionResource.valueOf("TRANSACTION_TYPE"),
                theInstance(TransactionResource.TRANSACTION_TYPE));
        assertThat(TransactionResource.valueOf("CHANNEL_CODE"),
                theInstance(TransactionResource.CHANNEL_CODE));
        assertThat(TransactionResource.valueOf("STORE_CODE"),
                theInstance(TransactionResource.STORE_CODE));
        assertThat(TransactionResource.valueOf("INTEGRATED_ORDER_ID"),
                theInstance(TransactionResource.INTEGRATED_ORDER_ID));
        assertThat(TransactionResource.valueOf("ORDER_SUB_NUMBER"),
                theInstance(TransactionResource.ORDER_SUB_NUMBER));
        assertThat(TransactionResource.valueOf("UPDATE_TYPE"),
                theInstance(TransactionResource.UPDATE_TYPE));
    }
}
