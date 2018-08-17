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
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDiscountCondition;
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
public class ErrorEvacuationSalesTransactionDiscountMapperTest {

    @Autowired
    private ErrorEvacuationSalesTransactionDiscountMapper errorEvacuationSalesTransactionDiscountEntityMapper;
    
    private ErrorEvacuationSalesTransactionDiscount errorEvacuationSalesTransactionDiscountEntity;


    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        errorEvacuationSalesTransactionDiscountEntity=new ErrorEvacuationSalesTransactionDiscount();
        errorEvacuationSalesTransactionDiscountEntity.setTransactionId("1");
        errorEvacuationSalesTransactionDiscountEntity.setOrderSubNumber(1);
        errorEvacuationSalesTransactionDiscountEntity.setSalesTransactionId("1");
        errorEvacuationSalesTransactionDiscountEntity.setDetailSubNumber(1);
        errorEvacuationSalesTransactionDiscountEntity.setPromotionType("1");
        errorEvacuationSalesTransactionDiscountEntity.setPromotionNo("1");
        errorEvacuationSalesTransactionDiscountEntity.setStoreDiscountType("1");
        errorEvacuationSalesTransactionDiscountEntity.setSalesTransactionErrorId("1");
        errorEvacuationSalesTransactionDiscountEntity.setItemDiscountSubNumber(1);
        errorEvacuationSalesTransactionDiscountEntity.setQuantityCode("1");
        errorEvacuationSalesTransactionDiscountEntity.setDiscountQuantity(1);
        errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxExcludedCurrencyCode("1");
        errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxExcluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxIncludedCurrencyCode("1");
        errorEvacuationSalesTransactionDiscountEntity.setDiscountAmountTaxIncluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDiscountEntity.setCreateUserId("1");
        errorEvacuationSalesTransactionDiscountEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        errorEvacuationSalesTransactionDiscountEntity.setCreateProgramId("1");
        errorEvacuationSalesTransactionDiscountEntity.setUpdateUserId("1");
        errorEvacuationSalesTransactionDiscountEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        errorEvacuationSalesTransactionDiscountEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionDiscountEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        ErrorEvacuationSalesTransactionDiscountCondition errorEvacuationSalesTransactionDiscountEntityCondition =
                new ErrorEvacuationSalesTransactionDiscountCondition();
        errorEvacuationSalesTransactionDiscountEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionDiscountEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionDiscountEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionDiscountEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("5");
        ErrorEvacuationSalesTransactionDiscountCondition errorEvacuationSalesTransactionDiscountEntityCondition =
                new ErrorEvacuationSalesTransactionDiscountCondition();
        errorEvacuationSalesTransactionDiscountEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionDiscountEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionDiscountEntityCondition);
        assertThat(result, is(0));
    }
    
    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionDiscountEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        errorEvacuationSalesTransactionDiscountEntity.setTransactionId("2");
        errorEvacuationSalesTransactionDiscountEntity.setOrderSubNumber(2);
        errorEvacuationSalesTransactionDiscountEntity.setSalesTransactionId("2");
        errorEvacuationSalesTransactionDiscountEntity.setDetailSubNumber(2);
        errorEvacuationSalesTransactionDiscountEntity.setPromotionType("2");
        errorEvacuationSalesTransactionDiscountEntity.setPromotionNo("2");
        errorEvacuationSalesTransactionDiscountEntity.setStoreDiscountType("2");
        
        int result= errorEvacuationSalesTransactionDiscountEntityMapper.insertSelective(errorEvacuationSalesTransactionDiscountEntity);
        assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDiscountEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionDiscountEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result= errorEvacuationSalesTransactionDiscountEntityMapper.insertSelective(errorEvacuationSalesTransactionDiscountEntity);
        assertEquals(0, result);
    }

}
