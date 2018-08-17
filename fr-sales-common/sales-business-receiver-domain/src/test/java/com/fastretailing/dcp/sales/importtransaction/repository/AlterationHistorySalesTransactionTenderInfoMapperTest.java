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
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTenderInfo;
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
public class AlterationHistorySalesTransactionTenderInfoMapperTest {

    @Autowired
    private AlterationHistorySalesTransactionTenderInfoMapper alterationHistorySalesTransactionTenderInfoEntityMapper;

    private AlterationHistorySalesTransactionTenderInfo alterationHistorySalesTransactionTenderInfoEntity;

    @Before
    public void setUp() {
        alterationHistorySalesTransactionTenderInfoEntity =
                new AlterationHistorySalesTransactionTenderInfo();
   
        alterationHistorySalesTransactionTenderInfoEntity.setTransactionId("1");
        alterationHistorySalesTransactionTenderInfoEntity.setOrderSubNumber(1);
        alterationHistorySalesTransactionTenderInfoEntity.setSalesTransactionId("1");
        alterationHistorySalesTransactionTenderInfoEntity.setTenderGroup("1");
        alterationHistorySalesTransactionTenderInfoEntity.setTenderId("1");
        alterationHistorySalesTransactionTenderInfoEntity.setHistoryType(1);
        alterationHistorySalesTransactionTenderInfoEntity.setSalesTransactionErrorId("1");
        alterationHistorySalesTransactionTenderInfoEntity.setDiscountValueCurrencyCode("1");
        alterationHistorySalesTransactionTenderInfoEntity.setDiscountValue(new BigDecimal(1));
        alterationHistorySalesTransactionTenderInfoEntity.setDiscountRate(new BigDecimal(1));
        alterationHistorySalesTransactionTenderInfoEntity.setDiscountCodeIdCorporateId("1");
        alterationHistorySalesTransactionTenderInfoEntity.setCouponType("1");
        alterationHistorySalesTransactionTenderInfoEntity.setCouponDiscountAmountSettingCurrencyCode("1");
        alterationHistorySalesTransactionTenderInfoEntity.setCouponDiscountAmountSettingValue(new BigDecimal(1));
        alterationHistorySalesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdCurrencyCode("1");
        alterationHistorySalesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdValue(new BigDecimal(1));
        alterationHistorySalesTransactionTenderInfoEntity.setCouponUserId("1");
        alterationHistorySalesTransactionTenderInfoEntity.setCardNo("1");
        alterationHistorySalesTransactionTenderInfoEntity.setCreditApprovalCode("1");
        alterationHistorySalesTransactionTenderInfoEntity.setCreditProcessingSerialNumber("1");
        alterationHistorySalesTransactionTenderInfoEntity.setCreditPaymentType("1");
        alterationHistorySalesTransactionTenderInfoEntity.setCreditPaymentCount(1);
        alterationHistorySalesTransactionTenderInfoEntity.setCreditAffiliatedStoreNumber("1");
        alterationHistorySalesTransactionTenderInfoEntity.setCreateUserId("1");
        alterationHistorySalesTransactionTenderInfoEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistorySalesTransactionTenderInfoEntity.setCreateProgramId("1");
        alterationHistorySalesTransactionTenderInfoEntity.setUpdateUserId("1");
        alterationHistorySalesTransactionTenderInfoEntity.setUpdateDatetime(LocalDateTime.of(2018,03,15,00,00,00));
        alterationHistorySalesTransactionTenderInfoEntity.setUpdateProgramId("1");
        alterationHistorySalesTransactionTenderInfoEntity.setTenderSubNumber(1);

    }


    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(
            value = "TAlterationHistorySalesTransactionTenderInfoEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
     int result= alterationHistorySalesTransactionTenderInfoEntityMapper
                .insertSelective(alterationHistorySalesTransactionTenderInfoEntity);
     assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(
            value = "TAlterationHistorySalesTransactionTenderInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        alterationHistorySalesTransactionTenderInfoEntity.setTransactionId("0");
        alterationHistorySalesTransactionTenderInfoEntity.setOrderSubNumber(0);
        alterationHistorySalesTransactionTenderInfoEntity.setSalesTransactionId("0");
        alterationHistorySalesTransactionTenderInfoEntity.setTenderGroup("0");
        alterationHistorySalesTransactionTenderInfoEntity.setTenderId("0");
        alterationHistorySalesTransactionTenderInfoEntity.setHistoryType(0);
        alterationHistorySalesTransactionTenderInfoEntity.setTenderSubNumber(0);
     int result= alterationHistorySalesTransactionTenderInfoEntityMapper
                .insertSelective(alterationHistorySalesTransactionTenderInfoEntity);
     assertEquals(0, result);
    }

}
