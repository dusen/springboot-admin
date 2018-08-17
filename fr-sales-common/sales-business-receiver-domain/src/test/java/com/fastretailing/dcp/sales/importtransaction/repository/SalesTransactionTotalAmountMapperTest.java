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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTotalAmount;
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
public class SalesTransactionTotalAmountMapperTest {

    @Autowired
    private SalesTransactionTotalAmountMapper salesTransactionTotalAmountEntityMapper;

    private SalesTransactionTotalAmount salesTransactionTotalAmountEntity;

    @Before
    public void setUp() {
        salesTransactionTotalAmountEntity = new SalesTransactionTotalAmount();
        
        salesTransactionTotalAmountEntity.setOrderSubNumber(1);
        salesTransactionTotalAmountEntity.setSalesTransactionId("1");
        salesTransactionTotalAmountEntity.setTotalType("1");
        salesTransactionTotalAmountEntity.setTotalAmountSubNumber(1);
        salesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode("1");
        salesTransactionTotalAmountEntity.setTotalAmountTaxExcludedValue(new BigDecimal(1));
        salesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode("1");
        salesTransactionTotalAmountEntity.setTotalAmountTaxIncludedValue(new BigDecimal(1));
        salesTransactionTotalAmountEntity.setTaxRate(new BigDecimal(1));
        salesTransactionTotalAmountEntity.setSalesTransactionInformation1("1");
        salesTransactionTotalAmountEntity.setSalesTransactionInformation2("1");
        salesTransactionTotalAmountEntity.setSalesTransactionInformation3("1");
        salesTransactionTotalAmountEntity.setCreateUserId("1");
        salesTransactionTotalAmountEntity
                .setCreateDatetime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        salesTransactionTotalAmountEntity.setCreateProgramId("1");
        salesTransactionTotalAmountEntity.setUpdateUserId("1");
        salesTransactionTotalAmountEntity
                .setUpdateDatetime(LocalDateTime.of(2018, 03, 15, 00, 00, 00));
        salesTransactionTotalAmountEntity.setUpdateProgramId("1");
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionTotalAmountEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesTransactionTotalAmountEntity.setOrderSubNumber(2);
        salesTransactionTotalAmountEntity.setSalesTransactionId("2");
        salesTransactionTotalAmountEntity.setTotalType("2");
        int result=salesTransactionTotalAmountEntityMapper.insertSelective(salesTransactionTotalAmountEntity);
        assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionTotalAmountEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {

        int result=salesTransactionTotalAmountEntityMapper.insertSelective(salesTransactionTotalAmountEntity);
        assertEquals(0, result);
    }

}
