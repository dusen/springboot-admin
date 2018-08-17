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
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTaxCondition;
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
public class ErrorEvacuationSalesTransactionTaxMapperTest {

    @Autowired
    private ErrorEvacuationSalesTransactionTaxMapper errorEvacuationSalesTransactionTaxEntityMapper;

    private ErrorEvacuationSalesTransactionTax errorEvacuationSalesTransactionTaxEntity;


    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        errorEvacuationSalesTransactionTaxEntity=new ErrorEvacuationSalesTransactionTax();
        errorEvacuationSalesTransactionTaxEntity.setTransactionId("1");
        errorEvacuationSalesTransactionTaxEntity.setOrderSubNumber(1);
        errorEvacuationSalesTransactionTaxEntity.setSalesTransactionId("1");
        errorEvacuationSalesTransactionTaxEntity.setDetailSubNumber(1);
        errorEvacuationSalesTransactionTaxEntity.setTaxGroup("1");
        errorEvacuationSalesTransactionTaxEntity.setSalesTransactionErrorId("1");
        errorEvacuationSalesTransactionTaxEntity.setTaxSubNumber(1);
        errorEvacuationSalesTransactionTaxEntity.setTaxAmountSign("1");
        errorEvacuationSalesTransactionTaxEntity.setTaxAmountCurrencyCode("1");
        errorEvacuationSalesTransactionTaxEntity.setTaxAmountValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTaxEntity.setTaxRate(new BigDecimal(1));
        errorEvacuationSalesTransactionTaxEntity.setTaxName("1");
        errorEvacuationSalesTransactionTaxEntity.setCreateUserId("1");
        errorEvacuationSalesTransactionTaxEntity
                .setCreateDatetime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        errorEvacuationSalesTransactionTaxEntity.setCreateProgramId("1");
        errorEvacuationSalesTransactionTaxEntity.setUpdateUserId("1");
        errorEvacuationSalesTransactionTaxEntity
                .setUpdateDatetime(LocalDateTime.of(2018, 03, 15, 00, 00, 00));
        errorEvacuationSalesTransactionTaxEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTaxEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        ErrorEvacuationSalesTransactionTaxCondition errorEvacuationSalesTransactionTaxEntityCondition =
                new ErrorEvacuationSalesTransactionTaxCondition();
        errorEvacuationSalesTransactionTaxEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionTaxEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTaxEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTaxEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("5");
        ErrorEvacuationSalesTransactionTaxCondition errorEvacuationSalesTransactionTaxEntityCondition =
                new ErrorEvacuationSalesTransactionTaxCondition();
        errorEvacuationSalesTransactionTaxEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionTaxEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTaxEntityCondition);
        assertThat(result, is(0));
    }
    
    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTaxEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        errorEvacuationSalesTransactionTaxEntity.setTransactionId("2");
        errorEvacuationSalesTransactionTaxEntity.setOrderSubNumber(2);
        errorEvacuationSalesTransactionTaxEntity.setSalesTransactionId("2");
        errorEvacuationSalesTransactionTaxEntity.setDetailSubNumber(2);
        errorEvacuationSalesTransactionTaxEntity.setTaxGroup("2");
        int result=errorEvacuationSalesTransactionTaxEntityMapper.insertSelective(errorEvacuationSalesTransactionTaxEntity);
        assertEquals(result, 1);
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTaxEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTaxEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=errorEvacuationSalesTransactionTaxEntityMapper.insertSelective(errorEvacuationSalesTransactionTaxEntity);
        assertEquals(result, 0);
    }
}
