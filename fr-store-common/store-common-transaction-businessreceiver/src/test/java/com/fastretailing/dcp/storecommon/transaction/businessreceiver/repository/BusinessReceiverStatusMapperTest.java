/**
 * @(#)BusinessReceiverStatusMapperTest.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.repository;

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
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.dto.TBusinessReceiverStatus;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * BusinessReceiverStatusMapperTest test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_receiver.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_receiver.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class BusinessReceiverStatusMapperTest {

    /** Test class. */
    @Autowired
    private BusinessReceiverStatusMapper businessReceiverStatusMapper;

    /**
     * Test insert method.
     */
    @Test
    @DatabaseSetup("BusinessReceiverStatusMapperTest_init.xml")
    @ExpectedDatabase(value = "BusinessReceiverStatusMapperTest_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertValid() {
        TBusinessReceiverStatus entity = new TBusinessReceiverStatus();
        entity.setTransactionId("1000002");
        entity.setBusinessReceiverName("testReceiverService2");
        entity.setCreateUser("2");
        entity.setRetryCount(1);
        long count = businessReceiverStatusMapper.insert(entity);

        assertEquals(count, 1L);
    }
}
