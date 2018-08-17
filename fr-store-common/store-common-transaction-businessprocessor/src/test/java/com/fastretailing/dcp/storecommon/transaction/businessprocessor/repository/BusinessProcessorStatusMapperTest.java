/**
 * @(#)BusinessProcessorStatusMapperTest.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor.repository;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.dto.TBusinessProcessorStatus;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * BusinessProcessorStatusMapper test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_processor.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_processor.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class BusinessProcessorStatusMapperTest {

    /** Test class. */
    @Autowired
    private BusinessProcessorStatusMapper mapper;

    /**
     * Insert method test.
     */
    @Test
    @DatabaseSetup("BusinessProcessorStatusMapperTest_init.xml")
    @ExpectedDatabase(value = "BusinessProcessorStatusMapperTest_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertValid() {

        TBusinessProcessorStatus entity = new TBusinessProcessorStatus();
        entity.setTransactionId("1000002");
        entity.setBusinessProcessName("businessprocess2");
        entity.setCreateUser("2");
        entity.setRetryCount(1);
        long count = mapper.insert(entity);

        assertEquals(1L, count);
    }
}
