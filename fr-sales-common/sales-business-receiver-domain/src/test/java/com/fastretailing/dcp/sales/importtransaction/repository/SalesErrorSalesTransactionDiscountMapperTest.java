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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDiscountCondition;
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
public class SalesErrorSalesTransactionDiscountMapperTest {

    @Autowired
    private SalesErrorSalesTransactionDiscountMapper salesErrorSalesTransactionDiscountEntityMapper;
    
    private SalesErrorSalesTransactionDiscount salesErrorSalesTransactionDiscountEntity;
    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        
        salesErrorSalesTransactionDiscountEntity=new SalesErrorSalesTransactionDiscount();
        salesErrorSalesTransactionDiscountEntity.setTransactionId("1");
        salesErrorSalesTransactionDiscountEntity.setOrderSubNumber(1);
        salesErrorSalesTransactionDiscountEntity.setSalesTransactionId("1");
        salesErrorSalesTransactionDiscountEntity.setDetailSubNumber(1);
        salesErrorSalesTransactionDiscountEntity.setPromotionType("1");
        salesErrorSalesTransactionDiscountEntity.setPromotionNo("1");
        salesErrorSalesTransactionDiscountEntity.setStoreDiscountType("1");
        salesErrorSalesTransactionDiscountEntity.setItemDiscountSubNumber(1);
        salesErrorSalesTransactionDiscountEntity.setQuantityCode("1");
        salesErrorSalesTransactionDiscountEntity.setDiscountQuantity(1);
        salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode("1");
        salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxExcluded(new BigDecimal(1));
        salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode("1");
        salesErrorSalesTransactionDiscountEntity.setDiscountAmountTaxIncluded(new BigDecimal(1));
        salesErrorSalesTransactionDiscountEntity.setCreateUserId("1");
        salesErrorSalesTransactionDiscountEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesErrorSalesTransactionDiscountEntity.setCreateProgramId("1");
        salesErrorSalesTransactionDiscountEntity.setUpdateUserId("1");
        salesErrorSalesTransactionDiscountEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesErrorSalesTransactionDiscountEntity.setUpdateProgramId("1");
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionDiscountEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        SalesErrorSalesTransactionDiscountCondition salesErrorSalesTransactionDiscountEntityCondition =
                new SalesErrorSalesTransactionDiscountCondition();
        salesErrorSalesTransactionDiscountEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionDiscountEntityMapper
                .deleteByCondition(salesErrorSalesTransactionDiscountEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionDiscountEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("2");
        SalesErrorSalesTransactionDiscountCondition salesErrorSalesTransactionDiscountEntityCondition =
                new SalesErrorSalesTransactionDiscountCondition();
        salesErrorSalesTransactionDiscountEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionDiscountEntityMapper
                .deleteByCondition(salesErrorSalesTransactionDiscountEntityCondition);
        assertThat(result, is(0));
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionDiscountEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesErrorSalesTransactionDiscountEntity.setTransactionId("2");
        salesErrorSalesTransactionDiscountEntity.setOrderSubNumber(2);
        salesErrorSalesTransactionDiscountEntity.setSalesTransactionId("2");
        salesErrorSalesTransactionDiscountEntity.setDetailSubNumber(2);
        salesErrorSalesTransactionDiscountEntity.setPromotionType("2");
        salesErrorSalesTransactionDiscountEntity.setPromotionNo("2");
        salesErrorSalesTransactionDiscountEntity.setStoreDiscountType("2");
        int result=salesErrorSalesTransactionDiscountEntityMapper.insertSelective(salesErrorSalesTransactionDiscountEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionDiscountEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesErrorSalesTransactionDiscountEntityMapper.insertSelective(salesErrorSalesTransactionDiscountEntity);
        assertEquals(result, 0);
    }

}
