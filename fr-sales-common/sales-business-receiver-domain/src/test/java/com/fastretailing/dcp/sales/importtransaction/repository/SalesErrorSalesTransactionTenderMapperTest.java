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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderCondition;
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
public class SalesErrorSalesTransactionTenderMapperTest {

    @Autowired
    private SalesErrorSalesTransactionTenderMapper salesErrorSalesTransactionTenderEntityMapper;

    private SalesErrorSalesTransactionTender salesErrorSalesTransactionTenderEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {

        salesErrorSalesTransactionTenderEntity = new SalesErrorSalesTransactionTender();
        salesErrorSalesTransactionTenderEntity.setTransactionId("1");
        salesErrorSalesTransactionTenderEntity.setOrderSubNumber(1);
        salesErrorSalesTransactionTenderEntity.setSalesTransactionId("1");
        salesErrorSalesTransactionTenderEntity.setTenderGroup("1");
        salesErrorSalesTransactionTenderEntity.setTenderId("1");
        salesErrorSalesTransactionTenderEntity.setTenderSubNumber(1);
        salesErrorSalesTransactionTenderEntity.setPaymentSign("1");
        salesErrorSalesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode("1");
        salesErrorSalesTransactionTenderEntity.setTaxIncludedPaymentAmountValue(new BigDecimal(1));
        salesErrorSalesTransactionTenderEntity.setCreateUserId("1");
        salesErrorSalesTransactionTenderEntity
                .setCreateDatetime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        salesErrorSalesTransactionTenderEntity.setCreateProgramId("1");
        salesErrorSalesTransactionTenderEntity.setUpdateUserId("1");
        salesErrorSalesTransactionTenderEntity
                .setUpdateDatetime(LocalDateTime.of(2018, 03, 15, 00, 00, 00));
        salesErrorSalesTransactionTenderEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTenderEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        SalesErrorSalesTransactionTenderCondition salesErrorSalesTransactionTenderEntityCondition =
                new SalesErrorSalesTransactionTenderCondition();
        salesErrorSalesTransactionTenderEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionTenderEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTenderEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTenderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("5");
        SalesErrorSalesTransactionTenderCondition salesErrorSalesTransactionTenderEntityCondition =
                new SalesErrorSalesTransactionTenderCondition();
        salesErrorSalesTransactionTenderEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionTenderEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTenderEntityCondition);
        assertThat(result, is(0));
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTenderEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesErrorSalesTransactionTenderEntity.setTransactionId("2");
        salesErrorSalesTransactionTenderEntity.setOrderSubNumber(2);
        salesErrorSalesTransactionTenderEntity.setSalesTransactionId("2");
        salesErrorSalesTransactionTenderEntity.setTenderGroup("2");
        salesErrorSalesTransactionTenderEntity.setTenderId("2");

        int result = salesErrorSalesTransactionTenderEntityMapper
                .insertSelective(salesErrorSalesTransactionTenderEntity);
        assertEquals(result, 1);
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTenderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result = salesErrorSalesTransactionTenderEntityMapper
                .insertSelective(salesErrorSalesTransactionTenderEntity);
        assertEquals(result, 0);
    }


}
