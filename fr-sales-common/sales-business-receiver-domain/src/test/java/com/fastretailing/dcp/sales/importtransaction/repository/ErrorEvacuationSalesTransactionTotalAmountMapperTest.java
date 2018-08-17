package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTotalAmountCondition;
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
public class ErrorEvacuationSalesTransactionTotalAmountMapperTest {

    @Autowired
    private ErrorEvacuationSalesTransactionTotalAmountMapper errorEvacuationSalesTransactionTotalAmountEntityMapper;
    
    private ErrorEvacuationSalesTransactionTotalAmount errorEvacuationSalesTransactionTotalAmountEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        errorEvacuationSalesTransactionTotalAmountEntity=new ErrorEvacuationSalesTransactionTotalAmount();
        errorEvacuationSalesTransactionTotalAmountEntity.setTransactionId("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setOrderSubNumber(1);
        errorEvacuationSalesTransactionTotalAmountEntity.setSalesTransactionId("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setTotalType("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setSalesTransactionErrorId("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setTotalAmountSubNumber(1);
        errorEvacuationSalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedCurrencyCode("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setTotalAmountTaxExcludedValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedCurrencyCode("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setTotalAmountTaxIncludedValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTotalAmountEntity.setTaxRate(new BigDecimal(1));
        errorEvacuationSalesTransactionTotalAmountEntity.setSalesTransactionInformation1("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setSalesTransactionInformation2("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setSalesTransactionInformation3("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setCreateUserId("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        errorEvacuationSalesTransactionTotalAmountEntity.setCreateProgramId("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setUpdateUserId("1");
        errorEvacuationSalesTransactionTotalAmountEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        errorEvacuationSalesTransactionTotalAmountEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTotalAmountEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        ErrorEvacuationSalesTransactionTotalAmountCondition errorEvacuationSalesTransactionTotalAmountEntityCondition =
                new ErrorEvacuationSalesTransactionTotalAmountCondition();
        errorEvacuationSalesTransactionTotalAmountEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionTotalAmountEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTotalAmountEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTotalAmountEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("6");
        ErrorEvacuationSalesTransactionTotalAmountCondition errorEvacuationSalesTransactionTotalAmountEntityCondition =
                new ErrorEvacuationSalesTransactionTotalAmountCondition();
        errorEvacuationSalesTransactionTotalAmountEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionTotalAmountEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTotalAmountEntityCondition);
        assertThat(result, is(0));
    }
    
    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTotalAmountEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        errorEvacuationSalesTransactionTotalAmountEntity.setTransactionId("2");
        errorEvacuationSalesTransactionTotalAmountEntity.setOrderSubNumber(2);
        errorEvacuationSalesTransactionTotalAmountEntity.setSalesTransactionId("2");
        errorEvacuationSalesTransactionTotalAmountEntity.setTotalType("2");
        int reuslt=errorEvacuationSalesTransactionTotalAmountEntityMapper.insertSelective(errorEvacuationSalesTransactionTotalAmountEntity);
        assertEquals(reuslt, 1);
    }
    
    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTotalAmountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTotalAmountEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int reuslt=errorEvacuationSalesTransactionTotalAmountEntityMapper.insertSelective(errorEvacuationSalesTransactionTotalAmountEntity);
        assertEquals(reuslt, 0);
    }
    
}
