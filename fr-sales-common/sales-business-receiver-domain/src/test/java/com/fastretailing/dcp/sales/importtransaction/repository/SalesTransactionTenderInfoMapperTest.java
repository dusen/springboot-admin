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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderInfo;
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
public class SalesTransactionTenderInfoMapperTest {
    
    @Autowired
    private SalesTransactionTenderInfoMapper salesTransactionTenderInfoEntityMapper;
    
    private SalesTransactionTenderInfo salesTransactionTenderInfoEntity;
    
    @Before
    public void setUp() {
        salesTransactionTenderInfoEntity=new SalesTransactionTenderInfo();
        salesTransactionTenderInfoEntity.setOrderSubNumber(1);
        salesTransactionTenderInfoEntity.setSalesTransactionId("1");
        salesTransactionTenderInfoEntity.setTenderGroup("1");
        salesTransactionTenderInfoEntity.setTenderId("1");
        salesTransactionTenderInfoEntity.setDiscountValueCurrencyCode("1");
        salesTransactionTenderInfoEntity.setDiscountValue(new BigDecimal(1));
        salesTransactionTenderInfoEntity.setDiscountRate(new BigDecimal(1));
        salesTransactionTenderInfoEntity.setDiscountCodeIdCorporateId("1");
        salesTransactionTenderInfoEntity.setCouponType("1");
        salesTransactionTenderInfoEntity.setCouponDiscountAmountSettingCurrencyCode("1");
        salesTransactionTenderInfoEntity.setCouponDiscountAmountSettingValue(new BigDecimal(1));
        salesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdCurrencyCode("1");
        salesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdValue(new BigDecimal(1));
        salesTransactionTenderInfoEntity.setCouponUserId("1");
        salesTransactionTenderInfoEntity.setCardNo("1");
        salesTransactionTenderInfoEntity.setCreditApprovalCode("1");
        salesTransactionTenderInfoEntity.setCreditProcessingSerialNumber("1");
        salesTransactionTenderInfoEntity.setCreditPaymentType("1");
        salesTransactionTenderInfoEntity.setCreditPaymentCount(1);
        salesTransactionTenderInfoEntity.setCreditAffiliatedStoreNumber("1");
        salesTransactionTenderInfoEntity.setCreateUserId("1");
        salesTransactionTenderInfoEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesTransactionTenderInfoEntity.setCreateProgramId("1");
        salesTransactionTenderInfoEntity.setUpdateUserId("1");
        salesTransactionTenderInfoEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesTransactionTenderInfoEntity.setUpdateProgramId("1");
        salesTransactionTenderInfoEntity.setTenderSubNumber(1);
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionTenderInfoEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelective() {
        salesTransactionTenderInfoEntity.setOrderSubNumber(2);
        salesTransactionTenderInfoEntity.setSalesTransactionId("2");
        salesTransactionTenderInfoEntity.setTenderGroup("2");
        salesTransactionTenderInfoEntity.setTenderId("2");
        salesTransactionTenderInfoEntity.setTenderSubNumber(2);
        int result=salesTransactionTenderInfoEntityMapper.insertSelective(salesTransactionTenderInfoEntity);
        assertEquals(1, result);
    }
    
    
    @Test
    @DatabaseSetup("TSalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionTenderInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesTransactionTenderInfoEntityMapper.insertSelective(salesTransactionTenderInfoEntity);
        assertEquals(0, result);
    }
    

}
