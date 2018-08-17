package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.junit.Assert.assertEquals;
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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHistory;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
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
public class SalesTransactionHistoryMapperTest {
    
    @Autowired
    private SalesTransactionHistoryMapper salesTransactionHistoryEntityMapper;
    
    private SalesTransactionHistory salesTransactionHistoryEntity;
    
    @Before
    public void setUp() {
        salesTransactionHistoryEntity=new SalesTransactionHistory();
        salesTransactionHistoryEntity.setTransactionId("1");
        salesTransactionHistoryEntity.setSalesTransactionData("1");
        salesTransactionHistoryEntity.setCreateUserId("1");
        salesTransactionHistoryEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesTransactionHistoryEntity.setCreateProgramId("1");
        salesTransactionHistoryEntity.setUpdateUserId("1");
        salesTransactionHistoryEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesTransactionHistoryEntity.setUpdateProgramId("1");
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionHistoryEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionHistoryEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesTransactionHistoryEntity.setTransactionId("2");
        int result=salesTransactionHistoryEntityMapper.insertSelective(salesTransactionHistoryEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionHistoryEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionHistoryEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesTransactionHistoryEntityMapper.insertSelective(salesTransactionHistoryEntity);
        assertEquals(result, 0);
    }

}
