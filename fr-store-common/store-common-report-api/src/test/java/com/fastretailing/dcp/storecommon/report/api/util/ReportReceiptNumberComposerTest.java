/**
 * @(#)ReportReceiptNumberComposerTest.java
 * 
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportReceiptNumberSeqMapper;
import com.fastretailing.dcp.storecommon.util.DateUtility;

/**
 * Unit test of ReportReceiptNumberSeqMapper class.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReportReceiptNumberComposerTest {

    /** Test class. */
    @Autowired
    private ReportReceiptNumberComposer reportReceiptNumberComposer;

    /** Create a mock of access DB for report create status table. */
    @MockBean
    private ReportReceiptNumberSeqMapper mockReportReceiptNumberSeqMapper;

    /**
     * Compose method test.
     */
    @Test
    public void testcompose() {
        // DB access mock.
        when(mockReportReceiptNumberSeqMapper.nextValue()).thenReturn("000001");
        StringBuilder sb = new StringBuilder();
        String currentTime =
                DateUtility
                        .formatDateTime(DateUtility.getZonedDateTimeUtc().toLocalDateTime(),
                                DateUtility.DateTimeFormat.UUUUMMDDHHMMSS)
                        .substring(0, 8);
        // Expect value
        String string = sb.append("SIV_")
                .append("1230_")
                .append("12345_")
                .append(currentTime)
                .append("_000001")
                .toString();
        // Actual value
        String str = reportReceiptNumberComposer.compose("SIV", "1230", "12345");
        str = str.substring(0, 23) + "_000001";
        assertEquals(string, str);
    }
}
