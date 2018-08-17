package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTaxCondition;
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
public class SalesErrorSalesTransactionTaxMapperTest {

    @Autowired
    private SalesErrorSalesTransactionTaxMapper salesErrorSalesTransactionTaxEntityMapper;
    
    private SalesErrorSalesTransactionTax salesErrorSalesTransactionTaxEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        salesErrorSalesTransactionTaxEntity=new SalesErrorSalesTransactionTax();
        salesErrorSalesTransactionTaxEntity.setTransactionId("1");
        salesErrorSalesTransactionTaxEntity.setOrderSubNumber(1);
        salesErrorSalesTransactionTaxEntity.setSalesTransactionId("1");
        salesErrorSalesTransactionTaxEntity.setDetailSubNumber(1);
        salesErrorSalesTransactionTaxEntity.setTaxGroup("1");
        salesErrorSalesTransactionTaxEntity.setTaxSubNumber(1);
        salesErrorSalesTransactionTaxEntity.setTaxAmountSign("1");
        salesErrorSalesTransactionTaxEntity.setTaxAmountCurrencyCode("1");
        salesErrorSalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        salesErrorSalesTransactionTaxEntity.setTaxRate(new BigDecimal(1));
        salesErrorSalesTransactionTaxEntity.setTaxName("1");
        salesErrorSalesTransactionTaxEntity.setCreateUserId("1");
        salesErrorSalesTransactionTaxEntity.setCreateDatetime(LocalDateTime.of(2010,03,15,00,00,00));
        salesErrorSalesTransactionTaxEntity.setCreateProgramId("1");
        salesErrorSalesTransactionTaxEntity.setUpdateUserId("1");
        salesErrorSalesTransactionTaxEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesErrorSalesTransactionTaxEntity.setUpdateProgramId("1");
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTaxEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        SalesErrorSalesTransactionTaxCondition salesErrorSalesTransactionTaxEntityCondition =
                new SalesErrorSalesTransactionTaxCondition();
        salesErrorSalesTransactionTaxEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionTaxEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTaxEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTaxEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("5");
        SalesErrorSalesTransactionTaxCondition salesErrorSalesTransactionTaxEntityCondition =
                new SalesErrorSalesTransactionTaxCondition();
        salesErrorSalesTransactionTaxEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionTaxEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTaxEntityCondition);
        assertThat(result, is(0));
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTaxEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesErrorSalesTransactionTaxEntity.setTransactionId("2");
        salesErrorSalesTransactionTaxEntity.setOrderSubNumber(2);
        salesErrorSalesTransactionTaxEntity.setSalesTransactionId("2");
        salesErrorSalesTransactionTaxEntity.setDetailSubNumber(2);
        salesErrorSalesTransactionTaxEntity.setTaxGroup("2");
        int result=salesErrorSalesTransactionTaxEntityMapper.insertSelective(salesErrorSalesTransactionTaxEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTaxEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesErrorSalesTransactionTaxEntityMapper.insertSelective(salesErrorSalesTransactionTaxEntity);
        assertEquals(result, 0);
    }

}
