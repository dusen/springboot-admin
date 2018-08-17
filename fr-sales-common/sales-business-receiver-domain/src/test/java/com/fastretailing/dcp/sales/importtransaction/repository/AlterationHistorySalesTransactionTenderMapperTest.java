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
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTender;
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
public class AlterationHistorySalesTransactionTenderMapperTest {

    @Autowired
    private AlterationHistorySalesTransactionTenderMapper alterationHistorySalesTransactionTenderEntityMapper;

    private AlterationHistorySalesTransactionTender alterationHistorySalesTransactionTenderEntity;

    @Before
    public void setUp() {
        alterationHistorySalesTransactionTenderEntity =
                new AlterationHistorySalesTransactionTender();
        alterationHistorySalesTransactionTenderEntity.setTransactionId("1");
        alterationHistorySalesTransactionTenderEntity.setOrderSubNumber(1);
        alterationHistorySalesTransactionTenderEntity.setSalesTransactionId("1");
        alterationHistorySalesTransactionTenderEntity.setTenderGroup("1");
        alterationHistorySalesTransactionTenderEntity.setTenderId("1");
        alterationHistorySalesTransactionTenderEntity.setHistoryType(1);
        alterationHistorySalesTransactionTenderEntity.setSalesTransactionErrorId("1");
        alterationHistorySalesTransactionTenderEntity.setPaymentSign("1");
        alterationHistorySalesTransactionTenderEntity.setTaxIncludedPaymentAmountCurrencyCode("1");
        alterationHistorySalesTransactionTenderEntity.setTaxIncludedPaymentAmountValue(new BigDecimal(1));
        alterationHistorySalesTransactionTenderEntity.setTenderSubNumber(1);
        alterationHistorySalesTransactionTenderEntity.setCreateUserId("1");
        alterationHistorySalesTransactionTenderEntity
                .setCreateDatetime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        alterationHistorySalesTransactionTenderEntity.setCreateProgramId("1");
        alterationHistorySalesTransactionTenderEntity.setUpdateUserId("1");
        alterationHistorySalesTransactionTenderEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        alterationHistorySalesTransactionTenderEntity.setUpdateProgramId("1");
    }

    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionTenderEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
      int result=  alterationHistorySalesTransactionTenderEntityMapper
                .insertSelective(alterationHistorySalesTransactionTenderEntity);
      assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionTenderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionTenderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        alterationHistorySalesTransactionTenderEntity.setTransactionId("0");
        alterationHistorySalesTransactionTenderEntity.setOrderSubNumber(0);
        alterationHistorySalesTransactionTenderEntity.setSalesTransactionId("0");
        alterationHistorySalesTransactionTenderEntity.setTenderGroup("0");
        alterationHistorySalesTransactionTenderEntity.setTenderId("0");
        alterationHistorySalesTransactionTenderEntity.setHistoryType(0);
        alterationHistorySalesTransactionTenderEntity.setTenderSubNumber(0);
      int result=  alterationHistorySalesTransactionTenderEntityMapper
                .insertSelective(alterationHistorySalesTransactionTenderEntity);
      assertEquals(0, result);
    }
            

}
