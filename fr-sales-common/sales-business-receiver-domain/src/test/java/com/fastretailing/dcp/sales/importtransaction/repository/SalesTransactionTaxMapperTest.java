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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTax;
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
public class SalesTransactionTaxMapperTest {
    
    @Autowired
    private SalesTransactionTaxMapper salesTransactionTaxEntityMapper;
    
    private SalesTransactionTax salesTransactionTaxEntity;
    
    @Before
    public void setUp() {
        salesTransactionTaxEntity=new SalesTransactionTax();
        salesTransactionTaxEntity.setOrderSubNumber(1);
        salesTransactionTaxEntity.setSalesTransactionId("1");
        salesTransactionTaxEntity.setDetailSubNumber(1);
        salesTransactionTaxEntity.setTaxGroup("1");
        salesTransactionTaxEntity.setTaxSubNumber(1);
        salesTransactionTaxEntity.setTaxAmountSign("1");
        salesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        salesTransactionTaxEntity.setTaxAmountCurrencyCode("1");
        salesTransactionTaxEntity.setTaxRate(new BigDecimal(1));
        salesTransactionTaxEntity.setTaxName("1");
        salesTransactionTaxEntity.setCreateUserId("1");
        salesTransactionTaxEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesTransactionTaxEntity.setCreateProgramId("1");
        salesTransactionTaxEntity.setUpdateUserId("1");
        salesTransactionTaxEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesTransactionTaxEntity.setUpdateProgramId("1");
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionTaxEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesTransactionTaxEntity.setOrderSubNumber(2);
        salesTransactionTaxEntity.setSalesTransactionId("2");
        salesTransactionTaxEntity.setDetailSubNumber(2);
        salesTransactionTaxEntity.setTaxGroup("2");
        int result=salesTransactionTaxEntityMapper.insertSelective(salesTransactionTaxEntity);
        assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionTaxEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {

        int result=salesTransactionTaxEntityMapper.insertSelective(salesTransactionTaxEntity);
        assertEquals(0, result);
    }

}
