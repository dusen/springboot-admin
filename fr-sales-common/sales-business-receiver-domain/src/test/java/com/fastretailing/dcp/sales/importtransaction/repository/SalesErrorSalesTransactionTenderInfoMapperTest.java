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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderInfoCondition;
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
public class SalesErrorSalesTransactionTenderInfoMapperTest {

    @Autowired
    private SalesErrorSalesTransactionTenderInfoMapper salesErrorSalesTransactionTenderInfoEntityMapper;
    
    private SalesErrorSalesTransactionTenderInfo salesErrorSalesTransactionTenderInfoEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        salesErrorSalesTransactionTenderInfoEntity=new SalesErrorSalesTransactionTenderInfo();
        salesErrorSalesTransactionTenderInfoEntity.setTransactionId("1");
        salesErrorSalesTransactionTenderInfoEntity.setOrderSubNumber(1);
        salesErrorSalesTransactionTenderInfoEntity.setSalesTransactionId("1");
        salesErrorSalesTransactionTenderInfoEntity.setTenderGroup("1");
        salesErrorSalesTransactionTenderInfoEntity.setTenderId("1");
        salesErrorSalesTransactionTenderInfoEntity.setDiscountValueCurrencyCode("1");
        salesErrorSalesTransactionTenderInfoEntity.setDiscountValue(new BigDecimal(1));
        salesErrorSalesTransactionTenderInfoEntity.setDiscountRate(new BigDecimal(1));
        salesErrorSalesTransactionTenderInfoEntity.setDiscountCodeIdCorporateId("1");
        salesErrorSalesTransactionTenderInfoEntity.setCouponType("1");
        salesErrorSalesTransactionTenderInfoEntity.setCouponDiscountAmountSettingCurrencyCode("1");
        salesErrorSalesTransactionTenderInfoEntity.setCouponDiscountAmountSettingValue(new BigDecimal(1));
        salesErrorSalesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdCurrencyCode("1");
        salesErrorSalesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdValue(new BigDecimal(1));
        salesErrorSalesTransactionTenderInfoEntity.setCouponUserId("1");
        salesErrorSalesTransactionTenderInfoEntity.setCardNo("1");
        salesErrorSalesTransactionTenderInfoEntity.setCreditApprovalCode("1");
        salesErrorSalesTransactionTenderInfoEntity.setCreditProcessingSerialNumber("1");
        salesErrorSalesTransactionTenderInfoEntity.setCreditPaymentType("1");
        salesErrorSalesTransactionTenderInfoEntity.setCreditPaymentCount(1);
        salesErrorSalesTransactionTenderInfoEntity.setCreditAffiliatedStoreNumber("1");
        salesErrorSalesTransactionTenderInfoEntity.setCreateUserId("1");
        salesErrorSalesTransactionTenderInfoEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesErrorSalesTransactionTenderInfoEntity.setCreateProgramId("1");
        salesErrorSalesTransactionTenderInfoEntity.setUpdateUserId("1");
        salesErrorSalesTransactionTenderInfoEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesErrorSalesTransactionTenderInfoEntity.setUpdateProgramId("1");
        salesErrorSalesTransactionTenderInfoEntity.setTenderSubNumber(1);

    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTenderInfoEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        SalesErrorSalesTransactionTenderInfoCondition salesErrorSalesTransactionTenderInfoEntityCondition =
                new SalesErrorSalesTransactionTenderInfoCondition();
        salesErrorSalesTransactionTenderInfoEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionTenderInfoEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTenderInfoEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTenderInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("5");
        SalesErrorSalesTransactionTenderInfoCondition salesErrorSalesTransactionTenderInfoEntityCondition =
                new SalesErrorSalesTransactionTenderInfoCondition();
        salesErrorSalesTransactionTenderInfoEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionTenderInfoEntityMapper
                .deleteByCondition(salesErrorSalesTransactionTenderInfoEntityCondition);
        assertThat(result, is(0));
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTenderInfoEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesErrorSalesTransactionTenderInfoEntity.setTransactionId("2");
        salesErrorSalesTransactionTenderInfoEntity.setOrderSubNumber(2);
        salesErrorSalesTransactionTenderInfoEntity.setSalesTransactionId("2");
        salesErrorSalesTransactionTenderInfoEntity.setTenderGroup("2");
        salesErrorSalesTransactionTenderInfoEntity.setTenderId("2");
        salesErrorSalesTransactionTenderInfoEntity.setTenderSubNumber(2);
        int result=salesErrorSalesTransactionTenderInfoEntityMapper.insertSelective(salesErrorSalesTransactionTenderInfoEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionTenderInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesErrorSalesTransactionTenderInfoEntityMapper.insertSelective(salesErrorSalesTransactionTenderInfoEntity);
        assertEquals(result, 0);
    }

}
