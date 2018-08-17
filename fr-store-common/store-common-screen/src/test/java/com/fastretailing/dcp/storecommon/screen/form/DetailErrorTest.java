/**
 * @(#)DetailErrorTest.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.form;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Unit test of DetailError class.
 *
 */
public class DetailErrorTest {

    /**
     * Test equals.
     */
    @Test
    public void testEquals() {
        List<String> errorFiledList = new ArrayList<String>();
        errorFiledList.add("field1");
        errorFiledList.add("field2");

        DetailError pagenation1 = new DetailError();
        pagenation1.setErrorCode("E001");
        pagenation1.setErrorFieldList(errorFiledList);
        pagenation1.setErrorMessageTitle("ERROR TITLE");
        pagenation1.setErrorMessage("ERROR");
        DetailError pagenation2 = new DetailError();
        pagenation2.setErrorCode("E001");
        pagenation2.setErrorFieldList(errorFiledList);
        pagenation2.setErrorMessageTitle("ERROR TITLE");
        pagenation2.setErrorMessage("ERROR");

        assertTrue(pagenation1.equals(pagenation2));

        DetailError pagenation3 = new DetailError();
        pagenation3.setErrorCode("E001");
        pagenation3.setErrorFieldList(new ArrayList<String>());
        pagenation3.setErrorMessageTitle("ERROR TITLE");
        pagenation3.setErrorMessage("ERROR");

        assertFalse(pagenation1.equals(pagenation3));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashcode() {
        List<String> errorFiledList = new ArrayList<String>();
        errorFiledList.add("field1");
        errorFiledList.add("field2");

        DetailError pagenation1 = new DetailError();
        pagenation1.setErrorCode("E001");
        pagenation1.setErrorFieldList(errorFiledList);
        pagenation1.setErrorMessageTitle("ERROR TITLE");
        pagenation1.setErrorMessage("ERROR");
        DetailError pagenation2 = new DetailError();
        pagenation2.setErrorCode("E001");
        pagenation2.setErrorFieldList(errorFiledList);
        pagenation2.setErrorMessageTitle("ERROR TITLE");
        pagenation2.setErrorMessage("ERROR");

        assertTrue(pagenation1.hashCode() == pagenation2.hashCode());

        DetailError pagenation3 = new DetailError();
        pagenation3.setErrorCode("E001");
        pagenation3.setErrorFieldList(new ArrayList<String>());
        pagenation3.setErrorMessageTitle("ERROR TITLE");
        pagenation3.setErrorMessage("ERROR");

        assertFalse(pagenation1.hashCode() == pagenation3.hashCode());
    }

}
