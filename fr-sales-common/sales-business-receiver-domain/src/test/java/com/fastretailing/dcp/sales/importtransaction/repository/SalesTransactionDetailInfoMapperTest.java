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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetailInfo;
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
public class SalesTransactionDetailInfoMapperTest {
    
    @Autowired
    private SalesTransactionDetailInfoMapper salesTransactionDetailInfoEntityMapper;
    
    private SalesTransactionDetailInfo salesTransactionDetailInfoEntity;
    
    @Before
    public void setUp() {
        salesTransactionDetailInfoEntity=new SalesTransactionDetailInfo();
        salesTransactionDetailInfoEntity.setOrderSubNumber(1);
        salesTransactionDetailInfoEntity.setSalesTransactionId("1");
        salesTransactionDetailInfoEntity.setDetailSubNumber(1);
        salesTransactionDetailInfoEntity.setItemDetailSubNumber(1);
        salesTransactionDetailInfoEntity.setKeyCode("1");
        salesTransactionDetailInfoEntity.setCodeValue1("1");
        salesTransactionDetailInfoEntity.setCodeValue2("1");
        salesTransactionDetailInfoEntity.setCodeValue3("1");
        salesTransactionDetailInfoEntity.setCodeValue4("1");
        salesTransactionDetailInfoEntity.setName1("1");
        salesTransactionDetailInfoEntity.setName2("1");
        salesTransactionDetailInfoEntity.setName3("1");
        salesTransactionDetailInfoEntity.setName4("1");
        salesTransactionDetailInfoEntity.setCreateUserId("1");
        salesTransactionDetailInfoEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesTransactionDetailInfoEntity.setCreateProgramId("1");
        salesTransactionDetailInfoEntity.setUpdateUserId("1");
        salesTransactionDetailInfoEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesTransactionDetailInfoEntity.setUpdateProgramId("1");
        
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionDetailInfoEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesTransactionDetailInfoEntity.setOrderSubNumber(2);
        salesTransactionDetailInfoEntity.setSalesTransactionId("2");
        salesTransactionDetailInfoEntity.setDetailSubNumber(2);
        int result=salesTransactionDetailInfoEntityMapper.insertSelective(salesTransactionDetailInfoEntity);
        assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionDetailInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesTransactionDetailInfoEntityMapper.insertSelective(salesTransactionDetailInfoEntity);
        assertEquals(0, result);
    }

}
