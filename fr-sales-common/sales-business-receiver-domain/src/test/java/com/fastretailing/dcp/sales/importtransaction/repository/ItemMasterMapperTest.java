package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
import com.fastretailing.dcp.sales.importtransaction.entity.ItemMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.ItemMasterKey;
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
public class ItemMasterMapperTest {

    @Autowired
    private ItemMasterMapper itemMasterMapper;

    @Test
    @DatabaseSetup("ItemMasterMapperTest.xml")
    public void testSelectByPrimaryKeyExisted() {
        ItemMasterKey key = new ItemMasterKey();
        key.setItemCode("97");
        key.setSystemCountryCode("98");
        key.setSystemBrandCode("99");
        ItemMaster result = itemMasterMapper.selectByPrimaryKey(key);
        assertNotEquals(result, null);
        assertEquals(result.getItemCode(), "97");
        assertEquals(result.getSystemCountryCode(), "98");
        assertEquals(result.getSystemBrandCode(), "99");
    }

    @Test
    @DatabaseSetup("ItemMasterMapperTest.xml")
    public void testSelectByPrimaryKeyNotExisted() {
        ItemMasterKey key = new ItemMasterKey();
        key.setItemCode("1");
        key.setSystemBrandCode("1");
        key.setSystemCountryCode("1");
        ItemMaster result = itemMasterMapper.selectByPrimaryKey(key);
        assertEquals(result, null);
    }
}
