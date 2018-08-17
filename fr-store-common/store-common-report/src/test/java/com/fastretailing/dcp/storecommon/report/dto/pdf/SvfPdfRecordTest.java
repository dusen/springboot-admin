/**
 * @(#)SvfPdfRecordTest.java
 * 
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto.pdf;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class SvfPdfRecordTest {

    /**
     * Constructor test.
     */
    @Test
    public void testConstructor() {

        ;
        SvfPdfField field1 = new SvfPdfField();
        field1.setFieldName("name");
        field1.setFieldValue("value");

        SvfPdfField field2 = new SvfPdfField();
        field2.setFieldName("name2");
        field2.setFieldValue("value2");

        List<SvfPdfField> svfPdfFieldList = new ArrayList<>();
        svfPdfFieldList.add(field1);
        svfPdfFieldList.add(field2);

        SvfPdfRecord record = new SvfPdfRecord(svfPdfFieldList);

        assertThat(record.getSvfPdfFieldList().get(0).getFieldName(), equalTo("name"));
        assertThat(record.getSvfPdfFieldList().get(0).getFieldValue(), equalTo("value"));
        assertThat(record.getSvfPdfFieldList().get(1).getFieldName(), equalTo("name2"));
        assertThat(record.getSvfPdfFieldList().get(1).getFieldValue(), equalTo("value2"));
    }

}
