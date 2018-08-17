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
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDiscount;
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
public class AlterationHistorySalesTransactionDiscountMapperTest {

    @Autowired
    private AlterationHistorySalesTransactionDiscountMapper alterationHistorySalesTransactionDiscountEntityMapper;

    private AlterationHistorySalesTransactionDiscount alterationHistorySalesTransactionDiscountEntity;
    
    @Before
    public void setUp() {
        alterationHistorySalesTransactionDiscountEntity=new AlterationHistorySalesTransactionDiscount();
        alterationHistorySalesTransactionDiscountEntity.setTransactionId("1");
        alterationHistorySalesTransactionDiscountEntity.setOrderSubNumber(1);
        alterationHistorySalesTransactionDiscountEntity.setSalesTransactionId("1");
        alterationHistorySalesTransactionDiscountEntity.setDetailSubNumber(1);
        alterationHistorySalesTransactionDiscountEntity.setPromotionType("1");
        alterationHistorySalesTransactionDiscountEntity.setPromotionNo("1");
        alterationHistorySalesTransactionDiscountEntity.setStoreDiscountType("1");
        alterationHistorySalesTransactionDiscountEntity.setHistoryType(1);
        alterationHistorySalesTransactionDiscountEntity.setSalesTransactionErrorId("1");
        alterationHistorySalesTransactionDiscountEntity.setItemDiscountSubNumber(1);
        alterationHistorySalesTransactionDiscountEntity.setQuantityCode("1");
        alterationHistorySalesTransactionDiscountEntity.setDiscountQuantity(1);
        alterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode("1");
        alterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxExcluded(new BigDecimal(1));
        alterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode("1");
        alterationHistorySalesTransactionDiscountEntity.setDiscountAmountTaxIncluded(new BigDecimal(1));
        alterationHistorySalesTransactionDiscountEntity.setCreateUserId("1");
        alterationHistorySalesTransactionDiscountEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistorySalesTransactionDiscountEntity.setCreateProgramId("1");
        alterationHistorySalesTransactionDiscountEntity.setUpdateUserId("1");
        alterationHistorySalesTransactionDiscountEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        alterationHistorySalesTransactionDiscountEntity.setUpdateProgramId("1");
    }

    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionDiscountEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        
        int result=alterationHistorySalesTransactionDiscountEntityMapper.insertSelective(alterationHistorySalesTransactionDiscountEntity);
        assertEquals(1, result);
        
    }
    
    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionDiscountEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        alterationHistorySalesTransactionDiscountEntity.setTransactionId("0");
        alterationHistorySalesTransactionDiscountEntity.setOrderSubNumber(0);
        alterationHistorySalesTransactionDiscountEntity.setSalesTransactionId("0");
        alterationHistorySalesTransactionDiscountEntity.setDetailSubNumber(0);
        alterationHistorySalesTransactionDiscountEntity.setPromotionType("0");
        alterationHistorySalesTransactionDiscountEntity.setPromotionNo("0");
        alterationHistorySalesTransactionDiscountEntity.setStoreDiscountType("0");
        alterationHistorySalesTransactionDiscountEntity.setHistoryType(0);
        int result=alterationHistorySalesTransactionDiscountEntityMapper.insertSelective(alterationHistorySalesTransactionDiscountEntity);
        assertEquals(0, result);
        
    }
}
