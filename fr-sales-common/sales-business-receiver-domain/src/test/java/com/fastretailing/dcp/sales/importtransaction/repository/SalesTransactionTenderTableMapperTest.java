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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderTable;
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
public class SalesTransactionTenderTableMapperTest {
    
    @Autowired
    private SalesTransactionTenderTableMapper salesTransactionTenderEntityMapper;
    
    private SalesTransactionTenderTable salesTransactionTenderEntity;
    
    @Before
    public void setUp() {
        salesTransactionTenderEntity=new SalesTransactionTenderTable();
        salesTransactionTenderEntity.setOrderSubNumber(1);
        salesTransactionTenderEntity.setSalesTransactionId("1");
        salesTransactionTenderEntity.setTenderGroup("1");
        salesTransactionTenderEntity.setTenderId("1");
        salesTransactionTenderEntity.setTenderSubNumber(1);
        salesTransactionTenderEntity.setPaymentSign("1");
        salesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode("1");
        salesTransactionTenderEntity.setTaxIncludedPaymentAmountValue(new BigDecimal(1));
        salesTransactionTenderEntity.setCreateUserId("1");
        salesTransactionTenderEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesTransactionTenderEntity.setCreateProgramId("1");
        salesTransactionTenderEntity.setUpdateUserId("1");
        salesTransactionTenderEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesTransactionTenderEntity.setUpdateProgramId("1");

    }
    
    @Test
    @DatabaseSetup("TSalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionTenderEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesTransactionTenderEntity.setOrderSubNumber(2);
        salesTransactionTenderEntity.setSalesTransactionId("2");
        salesTransactionTenderEntity.setTenderGroup("2");
        salesTransactionTenderEntity.setTenderId("2");
        int result=salesTransactionTenderEntityMapper.insertSelective(salesTransactionTenderEntity);
        assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionTenderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {

        int result=salesTransactionTenderEntityMapper.insertSelective(salesTransactionTenderEntity);
        assertEquals(0, result);
    }

}
