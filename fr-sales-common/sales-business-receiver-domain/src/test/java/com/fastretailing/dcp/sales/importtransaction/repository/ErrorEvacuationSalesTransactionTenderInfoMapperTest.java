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
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTenderInfoCondition;
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
public class ErrorEvacuationSalesTransactionTenderInfoMapperTest {

    @Autowired
    private ErrorEvacuationSalesTransactionTenderInfoMapper errorEvacuationSalesTransactionTenderInfoEntityMapper;
    
    private ErrorEvacuationSalesTransactionTenderInfo errorEvacuationSalesTransactionTenderInfoEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        errorEvacuationSalesTransactionTenderInfoEntity=new ErrorEvacuationSalesTransactionTenderInfo();
        errorEvacuationSalesTransactionTenderInfoEntity.setTransactionId("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setOrderSubNumber(1);
        errorEvacuationSalesTransactionTenderInfoEntity.setSalesTransactionId("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setTenderGroup("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setTenderId("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setSalesTransactionErrorId("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setDiscountValueCurrencyCode("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setDiscountValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTenderInfoEntity.setDiscountRate(new BigDecimal(1));
        errorEvacuationSalesTransactionTenderInfoEntity.setDiscountCodeIdCorporateId("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setCouponType("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setCouponDiscountAmountSettingCurrencyCode("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setCouponDiscountAmountSettingValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdCurrencyCode("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setCouponMinUsageAmountThresholdValue(new BigDecimal(1));
        errorEvacuationSalesTransactionTenderInfoEntity.setCouponUserId("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setCardNo("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setCreditApprovalCode("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setCreditProcessingSerialNumber("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setCreditPaymentType("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setCreditPaymentCount(1);
        errorEvacuationSalesTransactionTenderInfoEntity.setCreditAffiliatedStoreNumber("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setCreateUserId("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        errorEvacuationSalesTransactionTenderInfoEntity.setCreateProgramId("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setUpdateUserId("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        errorEvacuationSalesTransactionTenderInfoEntity.setUpdateProgramId("1");
        errorEvacuationSalesTransactionTenderInfoEntity.setTenderSubNumber(1);

    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTenderInfoEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        ErrorEvacuationSalesTransactionTenderInfoCondition errorEvacuationSalesTransactionTenderInfoEntityCondition =
                new ErrorEvacuationSalesTransactionTenderInfoCondition();
        errorEvacuationSalesTransactionTenderInfoEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionTenderInfoEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTenderInfoEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTenderInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("5");
        ErrorEvacuationSalesTransactionTenderInfoCondition errorEvacuationSalesTransactionTenderInfoEntityCondition =
                new ErrorEvacuationSalesTransactionTenderInfoCondition();
        errorEvacuationSalesTransactionTenderInfoEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionTenderInfoEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionTenderInfoEntityCondition);
        assertThat(result, is(0));
    }
    
    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTenderInfoEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        errorEvacuationSalesTransactionTenderInfoEntity.setTransactionId("2");
        errorEvacuationSalesTransactionTenderInfoEntity.setOrderSubNumber(2);
        errorEvacuationSalesTransactionTenderInfoEntity.setSalesTransactionId("2");
        errorEvacuationSalesTransactionTenderInfoEntity.setTenderGroup("2");
        errorEvacuationSalesTransactionTenderInfoEntity.setTenderId("2");
        errorEvacuationSalesTransactionTenderInfoEntity.setTenderSubNumber(2);
        int result=errorEvacuationSalesTransactionTenderInfoEntityMapper.insertSelective(errorEvacuationSalesTransactionTenderInfoEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionTenderInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionTenderInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=errorEvacuationSalesTransactionTenderInfoEntityMapper.insertSelective(errorEvacuationSalesTransactionTenderInfoEntity);
        assertEquals(result, 0);
    }

}
