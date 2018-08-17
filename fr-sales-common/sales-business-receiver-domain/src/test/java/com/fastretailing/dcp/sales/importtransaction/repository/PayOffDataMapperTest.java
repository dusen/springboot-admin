package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.importtransaction.entity.PayOffDataCondition;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_sales_6.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales_6.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@MapperScan(value = "com.fastretailing.dcp.sales.importtransaction.repository")
public class PayOffDataMapperTest {

    @Autowired
    private PayOffDataMapper payOffDataEntityMapper;

    @Test
    @DatabaseSetup("TPayOffDataEntityMapperTest.xml")
    public void testCountByConditionWithRecord() {
        PayOffDataCondition payOffDataEntityCondition = new PayOffDataCondition();
        payOffDataEntityCondition.createCriteria()
                .andStoreCodeEqualTo("1")
                .andPayoffDateEqualTo("1")
                .andIntegrityCheckTypeEqualTo("1");
        long result = payOffDataEntityMapper.countByCondition(payOffDataEntityCondition);
        assertEquals(result, 3);

    }

    @Test
    @DatabaseSetup("TPayOffDataEntityMapperTest.xml")
    public void testCountByConditionWithoutRecord() {
        PayOffDataCondition payOffDataEntityCondition = new PayOffDataCondition();
        payOffDataEntityCondition.createCriteria()
                .andStoreCodeEqualTo("2")
                .andPayoffDateEqualTo("2")
                .andIntegrityCheckTypeEqualTo("1");
        long result = payOffDataEntityMapper.countByCondition(payOffDataEntityCondition);
        assertEquals(result, 0);

    }

}
