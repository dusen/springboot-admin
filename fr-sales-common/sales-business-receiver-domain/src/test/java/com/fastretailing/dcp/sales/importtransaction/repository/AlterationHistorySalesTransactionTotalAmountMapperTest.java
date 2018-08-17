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
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTotalAmount;
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
public class AlterationHistorySalesTransactionTotalAmountMapperTest {

    @Autowired
    private AlterationHistorySalesTransactionTotalAmountMapper alterationHistorySalesTransactionTotalAmountEntityMapper;

    private AlterationHistorySalesTransactionTotalAmount alterationHistorySalesTransactionTotalAmountEntity;

    @Before
    public void setUp() {
        alterationHistorySalesTransactionTotalAmountEntity=new AlterationHistorySalesTransactionTotalAmount();
        alterationHistorySalesTransactionTotalAmountEntity.setTransactionId("1");
        alterationHistorySalesTransactionTotalAmountEntity.setOrderSubNumber(1);
        alterationHistorySalesTransactionTotalAmountEntity.setSalesTransactionId("1");
        alterationHistorySalesTransactionTotalAmountEntity.setTotalType("1");
        alterationHistorySalesTransactionTotalAmountEntity.setHistoryType(1);
        alterationHistorySalesTransactionTotalAmountEntity.setSalesTransactionErrorId("1");
        alterationHistorySalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode("1");
        alterationHistorySalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedValue(new BigDecimal(1));
        alterationHistorySalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode("1");
        alterationHistorySalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedValue(new BigDecimal(1));
        alterationHistorySalesTransactionTotalAmountEntity.setTaxRate(new BigDecimal(1));
        alterationHistorySalesTransactionTotalAmountEntity.setSalesTransactionInformation1("1");
        alterationHistorySalesTransactionTotalAmountEntity.setSalesTransactionInformation2("1");
        alterationHistorySalesTransactionTotalAmountEntity.setSalesTransactionInformation3("1");
        alterationHistorySalesTransactionTotalAmountEntity.setTotalAmountSubNumber(1);
        alterationHistorySalesTransactionTotalAmountEntity.setCreateUserId("1");
        alterationHistorySalesTransactionTotalAmountEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistorySalesTransactionTotalAmountEntity.setCreateProgramId("1");
        alterationHistorySalesTransactionTotalAmountEntity.setUpdateUserId("1");
        alterationHistorySalesTransactionTotalAmountEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        alterationHistorySalesTransactionTotalAmountEntity.setUpdateProgramId("1");
        
    }

    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionTotalAmountEntityMapper_INSERT.xml",
    assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
      int result= alterationHistorySalesTransactionTotalAmountEntityMapper
                .insertSelective(alterationHistorySalesTransactionTotalAmountEntity);
      assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionTotalAmountEntityMapperTest.xml",
    assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        alterationHistorySalesTransactionTotalAmountEntity.setTransactionId("0");
        alterationHistorySalesTransactionTotalAmountEntity.setOrderSubNumber(0);
        alterationHistorySalesTransactionTotalAmountEntity.setSalesTransactionId("0");
        alterationHistorySalesTransactionTotalAmountEntity.setTotalType("0");
        alterationHistorySalesTransactionTotalAmountEntity.setHistoryType(0);
      int result= alterationHistorySalesTransactionTotalAmountEntityMapper
                .insertSelective(alterationHistorySalesTransactionTotalAmountEntity);
      assertEquals(0, result);
    }


}
