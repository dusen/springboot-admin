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
import com.fastretailing.dcp.sales.importtransaction.entity.BusinessCountryStateSettingMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.BusinessCountryStateSettingMasterCondition;
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
@Sql(scripts = "/junit_create_table_sales_6.sql",executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales_6.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@MapperScan(value = "com.fastretailing.dcp.sales.importtransaction.repository")
public class BusinessCountryStateSettingMasterMapperTest {

    @Autowired
    private BusinessCountryStateSettingMasterMapper businessCountryStateSettingEntityMapper;


    @Test
    @DatabaseSetup("MBusinessCountryStateSettingEntityMapperTest.xml")
    public void testSelectByConditionMultiRecord() {

        BusinessCountryStateSettingMasterCondition example =
                new BusinessCountryStateSettingMasterCondition();
        example.createCriteria()
                .andCodeL1EqualTo("1")
                .andCodeL2EqualTo("1")
                .andCodeL3EqualTo("1")
                .andStoreCodeEqualTo("1");
        List<BusinessCountryStateSettingMaster> list =
                businessCountryStateSettingEntityMapper.selectByCondition(example);
        assertEquals(list.size(), 3);

    }

    @Test
    @DatabaseSetup("MBusinessCountryStateSettingEntityMapperTest.xml")
    public void testSelectByConditionOneRecord() {

        BusinessCountryStateSettingMasterCondition example =
                new BusinessCountryStateSettingMasterCondition();
        example.createCriteria()
                .andCodeL1EqualTo("2")
                .andCodeL2EqualTo("2")
                .andCodeL3EqualTo("2")
                .andStoreCodeEqualTo("2");
        List<BusinessCountryStateSettingMaster> list =
                businessCountryStateSettingEntityMapper.selectByCondition(example);
        assertEquals(list.size(), 1);

    }

    @Test
    @DatabaseSetup("MBusinessCountryStateSettingEntityMapperTest.xml")
    public void testSelectByConditionNoRecord() {

        BusinessCountryStateSettingMasterCondition example =
                new BusinessCountryStateSettingMasterCondition();
        example.createCriteria()
                .andCodeL1EqualTo("3")
                .andCodeL2EqualTo("3")
                .andCodeL3EqualTo("3")
                .andStoreCodeEqualTo("3");
        List<BusinessCountryStateSettingMaster> list =
                businessCountryStateSettingEntityMapper.selectByCondition(example);
        assertEquals(list.size(), 0);

    }



}
