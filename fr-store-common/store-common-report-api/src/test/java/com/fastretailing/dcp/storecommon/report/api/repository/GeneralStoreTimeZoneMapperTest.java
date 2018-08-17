/**
 * @(#)GeneralStoreTimeZoneMapperTest.java
 * 
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
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
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Unit test of GeneralStoreTimeZoneMapper class.
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
public class GeneralStoreTimeZoneMapperTest {

    /** DB access class. */
    @Autowired
    private GeneralStoreTimeZoneMapper generalStoreTimeZoneMapper;

    /**
     * <UL>
     * <LI>Target method：select.
     * <LI>Condition：Select One Data.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("GeneralStoreTimeZoneMapper_Init_Select.xml")
    public void testSelectStoreGeneralPurpose() throws Exception {
        // Prepare parameter
        String tableName = "m_store_general_purpose";
        String targetField = "code";
        String conditionField = "general_purpose_type";
        String generalPurposeType = "time_zone";
        String storeCode = "111111";

        // Prepare Expected Dto
        String strExpect = "Europe/Rome";

        // Method execution
        String actResult = generalStoreTimeZoneMapper.selectGeneralItem(tableName, targetField,
                conditionField, generalPurposeType, storeCode);

        // Confirm result
        assertThat(actResult, is(strExpect));

    }
}
