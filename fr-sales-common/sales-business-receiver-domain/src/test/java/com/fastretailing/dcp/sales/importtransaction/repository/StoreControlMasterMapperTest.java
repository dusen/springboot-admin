package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.Before;
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
import com.fastretailing.dcp.sales.importtransaction.entity.StoreControlMaster;
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
public class StoreControlMasterMapperTest {

    @Autowired
    private StoreControlMasterMapper storeControlEntityMapper;

    private StoreControlMaster storeControlEntity;

    @Before
    public void setUp() {
        storeControlEntity = new StoreControlMaster();
        storeControlEntity.setStoreCode("1");
        storeControlEntity.setSystemBrandCode("1");
        storeControlEntity.setSystemBusinessCode("1");
        storeControlEntity.setSystemCountryCode("1");
        storeControlEntity.setFcStoreType("1");
        storeControlEntity.setBusinessDate("1");
        storeControlEntity.setClosingStoreTime("1");
        storeControlEntity.setLanguageType("1");
        storeControlEntity.setBusinessEndOfDate("1");
        storeControlEntity.setTaxRate(new BigDecimal(1));
        storeControlEntity.setCreateUserId("1");
        storeControlEntity.setCreateDatetime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        storeControlEntity.setCreateProgramId("1");
        storeControlEntity.setUpdateUserId("1");
        storeControlEntity.setUpdateDatetime(LocalDateTime.of(2018, 03, 15, 00, 00, 00));
        storeControlEntity.setUpdateProgramId("1");
    }

    @Test
    @DatabaseSetup("MStoreControlEntityMapperTest.xml")
    public void testSelectByPrimaryKeyOneRecord() {
        StoreControlMaster mStoreControlEntity = storeControlEntityMapper.selectByPrimaryKey("1");
        assertEquals(mStoreControlEntity.getStoreCode(), "1");
        assertEquals(mStoreControlEntity.getSystemBrandCode(), "1");
        assertEquals(mStoreControlEntity.getSystemBusinessCode(), "1");
        assertEquals(mStoreControlEntity.getSystemCountryCode(), "1");

    }

    @Test
    @DatabaseSetup("MStoreControlEntityMapperTest.xml")
    public void testSelectByPrimaryKeyNoRecord() {
        StoreControlMaster mStoreControlEntity = storeControlEntityMapper.selectByPrimaryKey("5");
        assertEquals(mStoreControlEntity, null);

    }

}
