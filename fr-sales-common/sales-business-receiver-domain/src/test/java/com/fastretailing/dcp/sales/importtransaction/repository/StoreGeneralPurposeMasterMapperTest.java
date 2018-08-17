package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.junit.Assert.assertEquals;
import java.util.List;
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
import com.fastretailing.dcp.sales.importtransaction.entity.StoreGeneralPurposeMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.StoreGeneralPurposeMasterCondition;
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
public class StoreGeneralPurposeMasterMapperTest {

    @Autowired
    private StoreGeneralPurposeMasterMapper storeGeneralPurposeMasterMapper;

    @Test
    @DatabaseSetup("StoreGeneralPurposeMasterMapperTest.xml")
    public void testSelectByConditionExisted() {
        StoreGeneralPurposeMasterCondition condition = new StoreGeneralPurposeMasterCondition();
        condition.createCriteria().andStoreCodeEqualTo("99").andGeneralPurposeTypeEqualTo(
                "time_zone");

        List<StoreGeneralPurposeMaster> result =
                storeGeneralPurposeMasterMapper.selectByCondition(condition);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getStoreCode(), "99");
        assertEquals(result.get(0).getGeneralPurposeType(), "time_zone");
    }

    @Test
    @DatabaseSetup("StoreGeneralPurposeMasterMapperTest.xml")
    public void testSelectByConditionNotExisted() {
        StoreGeneralPurposeMasterCondition condition = new StoreGeneralPurposeMasterCondition();
        condition.createCriteria().andStoreCodeEqualTo("1").andGeneralPurposeTypeEqualTo(
                "time_zone23");

        List<StoreGeneralPurposeMaster> result =
                storeGeneralPurposeMasterMapper.selectByCondition(condition);
        assertEquals(result.size(), 0);
    }

    @Test
    @DatabaseSetup("StoreGeneralPurposeMasterMapperTest.xml")
    public void testCountByConditionExisted() {
        StoreGeneralPurposeMasterCondition condition = new StoreGeneralPurposeMasterCondition();
        condition.createCriteria()
                .andStoreCodeEqualTo("2")
                .andGeneralPurposeTypeEqualTo("ec_flag")
                .andCodeEqualTo("1");
        Long result = storeGeneralPurposeMasterMapper.countByCondition(condition);
        assertEquals(result, Long.valueOf(2L));
    }

    @Test
    @DatabaseSetup("StoreGeneralPurposeMasterMapperTest.xml")
    public void testCountByConditionNotExisted() {
        StoreGeneralPurposeMasterCondition condition = new StoreGeneralPurposeMasterCondition();
        condition.createCriteria()
                .andStoreCodeEqualTo("1")
                .andGeneralPurposeTypeEqualTo("ec_flag")
                .andCodeEqualTo("1");
        Long result = storeGeneralPurposeMasterMapper.countByCondition(condition);
        assertEquals(result, Long.valueOf(0L));
    }
}
