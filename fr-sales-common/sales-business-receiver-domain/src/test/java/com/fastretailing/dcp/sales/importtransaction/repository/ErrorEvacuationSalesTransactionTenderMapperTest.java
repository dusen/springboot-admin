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
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTenderCondition;
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
public class ErrorEvacuationSalesTransactionTenderMapperTest {

    @Autowired
    private ErrorEvacuationSalesTransactionTenderMapper errorEvacuationSalesTransactionTenderEntityMapper;
    
    private ErrorEvacuationSalesTransactionTender errorEvacuationSalesTransactionTenderEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        errorEvacuationSalesTransactionTenderEntity=new ErrorEvacuationSalesTransactionTender();
        errorEvacuationSalesTransactionTenderEntity.setTransactionId("1");
        errorEvacuationSalesTransactionTenderEntity.setOrderSubNumber(1);
        errorEvacuationSalesTransactionTenderEntity.setSalesTransactionId("1");
        errorEvacuationSalesTransactionTenderEntity.setTenderGroup("1");
        errorEvacuationSalesTransactionTenderEntity.setTenderId("1");
        errorEvacuationSalesTransactionTenderEntity.setSalesTransactionErrorId("1");
        errorEvacuationSalesTransactionTenderEntity.setTenderSubNumber(1);
        errorEvacuationSalesTransactionTenderEntity.setPaymentSign("1");
        errorEvacuationSalesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode("1");
        errorEvacuationSalesTransactionTenderEntity.setTaxIncludedPaymentAmountValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTenderEntity.setCreateUserId("1");
        errorEvacuationSalesTransactionTenderEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        errorEvacuationSalesTransactionTenderEntity.setCreateProgramId("1");
        errorEvacuationSalesTransactionTenderEntity.setUpdateUserId("1");
        errorEvacuationSalesTransactionTenderEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        errorEvacuationSalesTransactionTenderEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTenderEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        ErrorEvacuationSalesTransactionTenderCondition errorEvacuationSalesTransactionTenderEntityCondition =
                new ErrorEvacuationSalesTransactionTenderCondition();
        errorEvacuationSalesTransactionTenderEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionTenderEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTenderEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTenderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("12345");
        ErrorEvacuationSalesTransactionTenderCondition errorEvacuationSalesTransactionTenderEntityCondition =
                new ErrorEvacuationSalesTransactionTenderCondition();
        errorEvacuationSalesTransactionTenderEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionTenderEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTenderEntityCondition);
        assertThat(result, is(0));
    }
    
    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTenderEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiverSuccess() {
        errorEvacuationSalesTransactionTenderEntity.setTransactionId("2");
        errorEvacuationSalesTransactionTenderEntity.setOrderSubNumber(2);
        errorEvacuationSalesTransactionTenderEntity.setSalesTransactionId("2");
        errorEvacuationSalesTransactionTenderEntity.setTenderGroup("2");
        errorEvacuationSalesTransactionTenderEntity.setTenderId("2");
        int result=errorEvacuationSalesTransactionTenderEntityMapper.insertSelective(errorEvacuationSalesTransactionTenderEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTenderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiverFaield() {
        int result=errorEvacuationSalesTransactionTenderEntityMapper.insertSelective(errorEvacuationSalesTransactionTenderEntity);
        assertEquals(result, 0);
    }

}
