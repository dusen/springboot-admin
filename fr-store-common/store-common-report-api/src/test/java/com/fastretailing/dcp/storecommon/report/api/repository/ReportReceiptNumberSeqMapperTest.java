/**
 * @(#)ReportReceiptNumberSeqMapperTest.java
 * 
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.storecommon.report.api.PlatformReportApiApplication;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Unit test of ReportReceiptNumberSeqMapper class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformReportApiApplication.class)
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_report.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_report.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ReportReceiptNumberSeqMapperTest {

    /** DB access class. */
    @Autowired
    private ReportReceiptNumberSeqMapper reportReceiptNumberSeqMapper;

    /** Rules for verifying that exceptions are thrown. */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * <UL>
     * <LI>Target method：testSelectReportReceiptNumberSeqNextValueNotNull.
     * <LI>Verification result confirmation：The acquired data is not null.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testSelectReportReceiptNumberSeqNextValueNotNull() throws Exception {
        // Method execution
        String strNextValue = reportReceiptNumberSeqMapper.nextValue();
        // Confirm result
        assertNotNull(strNextValue);
    }

    /**
     * <UL>
     * <LI>Target method：testSelectReportReceiptNumberSeqNextValue.
     * <LI>Verification result confirmation：The acquired data is rising.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testSelectReportReceiptNumberSeqNextValue() throws Exception {
        // Method execution
        String strNextValue = reportReceiptNumberSeqMapper.nextValue();
        // Confirm result
        assertEquals("000000001", strNextValue);
        // Method execution
        String strNextValue2 = reportReceiptNumberSeqMapper.nextValue();
        // Confirm result
        assertEquals("000000002", strNextValue2);
    }
}
