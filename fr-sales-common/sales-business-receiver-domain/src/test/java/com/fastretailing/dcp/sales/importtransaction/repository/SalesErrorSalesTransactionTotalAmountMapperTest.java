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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTotalAmountCondition;
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
public class SalesErrorSalesTransactionTotalAmountMapperTest {

    @Autowired
    private SalesErrorSalesTransactionTotalAmountMapper salesErrorSalesTransactionTotalAmountEntityMapper;
    
    private  SalesErrorSalesTransactionTotalAmount salesErrorSalesTransactionTotalAmountEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        salesErrorSalesTransactionTotalAmountEntity=new SalesErrorSalesTransactionTotalAmount();
        salesErrorSalesTransactionTotalAmountEntity.setTransactionId("1");
        salesErrorSalesTransactionTotalAmountEntity.setOrderSubNumber(1);
        salesErrorSalesTransactionTotalAmountEntity.setSalesTransactionId("1");
        salesErrorSalesTransactionTotalAmountEntity.setTotalType("1");
        salesErrorSalesTransactionTotalAmountEntity.setTotalAmountSubNumber(1);
        salesErrorSalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode("1");
        salesErrorSalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedValue(new BigDecimal(1));
        salesErrorSalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode("1");
        salesErrorSalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedValue(new BigDecimal(1));
        salesErrorSalesTransactionTotalAmountEntity.setTaxRate(new BigDecimal(1));
        salesErrorSalesTransactionTotalAmountEntity.setSalesTransactionInformation1("1");
        salesErrorSalesTransactionTotalAmountEntity.setSalesTransactionInformation2("1");
        salesErrorSalesTransactionTotalAmountEntity.setSalesTransactionInformation3("1");
        salesErrorSalesTransactionTotalAmountEntity.setCreateUserId("1");
        salesErrorSalesTransactionTotalAmountEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesErrorSalesTransactionTotalAmountEntity.setCreateProgramId("1");
        salesErrorSalesTransactionTotalAmountEntity.setUpdateUserId("1");
        salesErrorSalesTransactionTotalAmountEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesErrorSalesTransactionTotalAmountEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTotalAmountEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        SalesErrorSalesTransactionTotalAmountCondition salesErrorSalesTransactionTotalAmountEntityCondition =
                new SalesErrorSalesTransactionTotalAmountCondition();
        salesErrorSalesTransactionTotalAmountEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionTotalAmountEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTotalAmountEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTotalAmountEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("5");
        SalesErrorSalesTransactionTotalAmountCondition salesErrorSalesTransactionTotalAmountEntityCondition =
                new SalesErrorSalesTransactionTotalAmountCondition();
        salesErrorSalesTransactionTotalAmountEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionTotalAmountEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTotalAmountEntityCondition);
        assertThat(result, is(0));
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTotalAmountEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesErrorSalesTransactionTotalAmountEntity.setTransactionId("2");
        salesErrorSalesTransactionTotalAmountEntity.setOrderSubNumber(2);
        salesErrorSalesTransactionTotalAmountEntity.setSalesTransactionId("2");
        salesErrorSalesTransactionTotalAmountEntity.setTotalType("2");
        int result=salesErrorSalesTransactionTotalAmountEntityMapper.insertSelective(salesErrorSalesTransactionTotalAmountEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTotalAmountEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesErrorSalesTransactionTotalAmountEntityMapper.insertSelective(salesErrorSalesTransactionTotalAmountEntity);
        assertEquals(result, 0);
    }

}
