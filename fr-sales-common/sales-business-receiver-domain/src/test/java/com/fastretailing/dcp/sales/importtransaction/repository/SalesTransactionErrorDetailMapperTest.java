package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.junit.Assert.assertEquals;
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
import com.fastretailing.dcp.sales.common.type.DataAlterationStatus;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
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
public class SalesTransactionErrorDetailMapperTest {
    
    @Autowired
    private SalesTransactionErrorDetailMapper salesTransactionErrorDetailEntityMapper;
    
    private SalesTransactionErrorDetail salesTransactionErrorDetailEntity;
    
    @Before
    public void setUp() {
        salesTransactionErrorDetailEntity=new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity.setSalesTransactionErrorId("1");
        salesTransactionErrorDetailEntity.setIntegratedOrderId("1");
        salesTransactionErrorDetailEntity.setSalesTransactionId("1");
        salesTransactionErrorDetailEntity.setStoreCode("1");
        salesTransactionErrorDetailEntity.setSystemBrandCode("1");
        salesTransactionErrorDetailEntity.setSystemBusinessCode("1");
        salesTransactionErrorDetailEntity.setSystemCountryCode("1");
        salesTransactionErrorDetailEntity.setErrorType("1");
        salesTransactionErrorDetailEntity.setDailySummaryErrorNotificationFlag(true);
        salesTransactionErrorDetailEntity.setRegularTimeErrorNotificationFlag(true);
        salesTransactionErrorDetailEntity
                .setDataAlterationStatusType(DataAlterationStatus.ALTERER_FINISH.getValue());
        salesTransactionErrorDetailEntity.setKeyInfo1("1");
        salesTransactionErrorDetailEntity.setKeyInfo2("1");
        salesTransactionErrorDetailEntity.setKeyInfo3("1");
        salesTransactionErrorDetailEntity.setKeyInfo4("1");
        salesTransactionErrorDetailEntity.setKeyInfo5("1");
        salesTransactionErrorDetailEntity.setKeyInfo6("1");
        salesTransactionErrorDetailEntity.setKeyInfo7("1");
        salesTransactionErrorDetailEntity.setKeyInfo8("1");
        salesTransactionErrorDetailEntity.setErrorItemId1("1");
        salesTransactionErrorDetailEntity.setErrorItemValue1("1");
        salesTransactionErrorDetailEntity.setErrorItemId2("1");
        salesTransactionErrorDetailEntity.setErrorItemValue2("1");
        salesTransactionErrorDetailEntity.setErrorItemId3("1");
        salesTransactionErrorDetailEntity.setErrorItemValue3("1");
        salesTransactionErrorDetailEntity.setErrorItemId4("1");
        salesTransactionErrorDetailEntity.setErrorItemValue4("1");
        salesTransactionErrorDetailEntity.setErrorItemId5("1");
        salesTransactionErrorDetailEntity.setErrorItemValue5("1");
        salesTransactionErrorDetailEntity.setCreateUserId("1");
        salesTransactionErrorDetailEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesTransactionErrorDetailEntity.setCreateProgramId("1");
        salesTransactionErrorDetailEntity.setUpdateUserId("1");
        salesTransactionErrorDetailEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesTransactionErrorDetailEntity.setUpdateProgramId("1");
        
    }
    @Test
    @DatabaseSetup("TSalesTransactionErrorDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionErrorDetailEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesTransactionErrorDetailEntity.setSalesTransactionErrorId("2");
        int result=salesTransactionErrorDetailEntityMapper.insertSelective(salesTransactionErrorDetailEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionErrorDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionErrorDetailEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesTransactionErrorDetailEntityMapper.insertSelective(salesTransactionErrorDetailEntity);
        assertEquals(result, 0);
    }

}
