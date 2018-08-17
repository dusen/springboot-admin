package com.fastretailing.dcp.sales.common.util;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.fastretailing.dcp.common.model.Detail;
import com.fastretailing.dcp.sales.common.util.LogMessageUtility;

/**
 * Log message utility class test.
 */
public class LogMessageUtilityTest {

    /** Line separator. */
    private static final String LINE_SEPARATOR = "\n";

    /** Detail list. */
    private List<Detail> detailList = new ArrayList<>();

    /**
     * Test empty detail list.
     */
    @Test
    public void testEmptyDetailList() {
        String result = LogMessageUtility.createLogMessage(detailList);
        assertEquals("", result);
    }

    /**
     * Test null field.
     */
    @Test
    public void testNullField() {
        detailList.add(createDetail(null, "value1", "This is issue1."));
        detailList.add(createDetail("field2", "value2", "This is issue2."));
        String result = LogMessageUtility.createLogMessage(detailList);
        String expectedResult =
                "This is issue1." + LINE_SEPARATOR + "This is issue2.(field2=value2)";
        assertEquals(expectedResult, result);
    }

    /**
     * Test null value.
     */
    @Test
    public void testNullValue() {
        detailList.add(createDetail("field1", null, "This is issue1."));
        detailList.add(createDetail("field2", "value2", "This is issue2."));
        String result = LogMessageUtility.createLogMessage(detailList);
        String expectedResult =
                "This is issue1.(field1=null)" + LINE_SEPARATOR + "This is issue2.(field2=value2)";
        assertEquals(expectedResult, result);
    }

    /**
     * Test detail list with one detail.
     */
    @Test
    public void testNormalOneDetail() {
        detailList.add(createDetail("field1", "value1", "This is issue1."));
        String result = LogMessageUtility.createLogMessage(detailList);
        String expectedResult = "This is issue1.(field1=value1)";
        assertEquals(expectedResult, result);
    }

    /**
     * Test detail list with many detail.
     */
    @Test
    public void testNormalManyDetail() {
        detailList.add(createDetail("field1", "value1", "This is issue1."));
        detailList.add(createDetail("field2", "value2", "This is issue2."));
        detailList.add(createDetail("field3", "value3", "This is issue3."));
        String result = LogMessageUtility.createLogMessage(detailList);
        String expectedResult =
                "This is issue1.(field1=value1)" + LINE_SEPARATOR + "This is issue2.(field2=value2)"
                        + LINE_SEPARATOR + "This is issue3.(field3=value3)";
        assertEquals(expectedResult, result);
    }

    /**
     * Create detail.
     * 
     * @param field Field.
     * @param value Value.
     * @param issue Issue.
     * @return Detail.
     */
    private Detail createDetail(String field, String value, String issue) {
        Detail detail = new Detail();
        detail.setField(field);
        detail.setValue(value);
        detail.setIssue(issue);
        return detail;
    }
}
