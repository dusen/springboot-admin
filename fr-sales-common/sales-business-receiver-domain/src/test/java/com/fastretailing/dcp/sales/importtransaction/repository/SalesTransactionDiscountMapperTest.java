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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDiscount;
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
public class SalesTransactionDiscountMapperTest {
    
    @Autowired
    private  SalesTransactionDiscountMapper salesTransactionDiscountEntityMapper;
    
    private SalesTransactionDiscount salesTransactionDiscountEntity;
    
    @Before
    public void setUp() {
        salesTransactionDiscountEntity=new SalesTransactionDiscount();
        salesTransactionDiscountEntity.setOrderSubNumber(1);
        salesTransactionDiscountEntity.setSalesTransactionId("1");
        salesTransactionDiscountEntity.setDetailSubNumber(1);
        salesTransactionDiscountEntity.setPromotionType("1");
        salesTransactionDiscountEntity.setPromotionNo("1");
        salesTransactionDiscountEntity.setStoreDiscountType("1");
        salesTransactionDiscountEntity.setItemDiscountSubNumber(1);
        salesTransactionDiscountEntity.setQuantityCode("1");
        salesTransactionDiscountEntity.setDiscountQuantity(1);
        salesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode("1");
        salesTransactionDiscountEntity.setDiscountAmountTaxExcluded(new BigDecimal(1));
        salesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode("1");
        salesTransactionDiscountEntity.setDiscountAmountTaxIncluded(new BigDecimal(1));
        salesTransactionDiscountEntity.setCreateUserId("1");
        salesTransactionDiscountEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesTransactionDiscountEntity.setCreateProgramId("1");
        salesTransactionDiscountEntity.setUpdateUserId("1");
        salesTransactionDiscountEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesTransactionDiscountEntity.setUpdateProgramId("1");
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionDiscountEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesTransactionDiscountEntity.setOrderSubNumber(2);
        salesTransactionDiscountEntity.setSalesTransactionId("2");
        salesTransactionDiscountEntity.setDetailSubNumber(2);
        salesTransactionDiscountEntity.setPromotionType("2");
        salesTransactionDiscountEntity.setPromotionNo("2");
        int result=salesTransactionDiscountEntityMapper.insertSelective(salesTransactionDiscountEntity);
        assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionDiscountEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesTransactionDiscountEntityMapper.insertSelective(salesTransactionDiscountEntity);
        assertEquals(0, result);
    }

}
