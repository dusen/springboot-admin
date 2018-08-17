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
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTax;
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
public class AlterationHistorySalesTransactionTaxMapperTest {

    @Autowired
    private AlterationHistorySalesTransactionTaxMapper alterationHistorySalesTransactionTaxEntityMapper;

    private AlterationHistorySalesTransactionTax alterationHistorySalesTransactionTaxEntity;

    @Before
    public void setUp() {
        alterationHistorySalesTransactionTaxEntity =
                new AlterationHistorySalesTransactionTax();
        alterationHistorySalesTransactionTaxEntity.setHistoryType(1);
        alterationHistorySalesTransactionTaxEntity.setTransactionId("1");
        alterationHistorySalesTransactionTaxEntity.setOrderSubNumber(1);
        alterationHistorySalesTransactionTaxEntity.setSalesTransactionId("1");
        alterationHistorySalesTransactionTaxEntity.setDetailSubNumber(1);
        alterationHistorySalesTransactionTaxEntity.setTaxGroup("1");
        alterationHistorySalesTransactionTaxEntity.setSalesTransactionErrorId("1");
        alterationHistorySalesTransactionTaxEntity.setTaxSubNumber(1);
        alterationHistorySalesTransactionTaxEntity.setTaxAmountSign("1");
        alterationHistorySalesTransactionTaxEntity.setTaxAmountCurrencyCode("1");
        alterationHistorySalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        alterationHistorySalesTransactionTaxEntity.setTaxRate(new BigDecimal(1));
        alterationHistorySalesTransactionTaxEntity.setTaxSubNumber(1);
        alterationHistorySalesTransactionTaxEntity.setTaxSubNumber(1);
        alterationHistorySalesTransactionTaxEntity.setTaxName("1");
        alterationHistorySalesTransactionTaxEntity.setCreateUserId("1");
        alterationHistorySalesTransactionTaxEntity
                .setCreateDatetime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        alterationHistorySalesTransactionTaxEntity.setCreateProgramId("1");
        alterationHistorySalesTransactionTaxEntity.setUpdateUserId("1");
        alterationHistorySalesTransactionTaxEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        alterationHistorySalesTransactionTaxEntity.setUpdateProgramId("1");
    }

    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionTaxEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
      int result=  alterationHistorySalesTransactionTaxEntityMapper
                .insertSelective(alterationHistorySalesTransactionTaxEntity);
      assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionTaxEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        alterationHistorySalesTransactionTaxEntity.setHistoryType(0);
        alterationHistorySalesTransactionTaxEntity.setTransactionId("0");
        alterationHistorySalesTransactionTaxEntity.setOrderSubNumber(0);
        alterationHistorySalesTransactionTaxEntity.setSalesTransactionId("0");
        alterationHistorySalesTransactionTaxEntity.setDetailSubNumber(0);
        alterationHistorySalesTransactionTaxEntity.setTaxGroup("0");
        alterationHistorySalesTransactionTaxEntity.setTaxSubNumber(0);
      int result=  alterationHistorySalesTransactionTaxEntityMapper
                .insertSelective(alterationHistorySalesTransactionTaxEntity);
      assertEquals(0, result);
    }

}
