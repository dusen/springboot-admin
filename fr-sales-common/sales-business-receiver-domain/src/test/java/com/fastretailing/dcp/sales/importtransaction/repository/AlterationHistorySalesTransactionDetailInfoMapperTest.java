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
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDetailInfo;
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
public class AlterationHistorySalesTransactionDetailInfoMapperTest {

    @Autowired
    private AlterationHistorySalesTransactionDetailInfoMapper alterationHistorySalesTransactionDetailInfoEntityMapper;

    private AlterationHistorySalesTransactionDetailInfo alterationHistorySalesTransactionDetailInfoEntity;
    
    @Before
    public void setUp() {
        alterationHistorySalesTransactionDetailInfoEntity=new AlterationHistorySalesTransactionDetailInfo();
        alterationHistorySalesTransactionDetailInfoEntity.setTransactionId("1");
        alterationHistorySalesTransactionDetailInfoEntity.setOrderSubNumber(1);
        alterationHistorySalesTransactionDetailInfoEntity.setSalesTransactionId("1");
        alterationHistorySalesTransactionDetailInfoEntity.setDetailSubNumber(1);
        alterationHistorySalesTransactionDetailInfoEntity.setHistoryType(1);   
        alterationHistorySalesTransactionDetailInfoEntity.setSalesTransactionErrorId("1");
        alterationHistorySalesTransactionDetailInfoEntity.setItemDetailSubNumber(1);
        alterationHistorySalesTransactionDetailInfoEntity.setKeyCode("1");
        alterationHistorySalesTransactionDetailInfoEntity.setCodeValue1("1");
        alterationHistorySalesTransactionDetailInfoEntity.setCodeValue2("1");
        alterationHistorySalesTransactionDetailInfoEntity.setCodeValue3("1");
        alterationHistorySalesTransactionDetailInfoEntity.setCodeValue4("1");
        alterationHistorySalesTransactionDetailInfoEntity.setName1("1");
        alterationHistorySalesTransactionDetailInfoEntity.setName2("1");
        alterationHistorySalesTransactionDetailInfoEntity.setName3("1");
        alterationHistorySalesTransactionDetailInfoEntity.setName4("1");
        alterationHistorySalesTransactionDetailInfoEntity.setCreateUserId("1");
        alterationHistorySalesTransactionDetailInfoEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistorySalesTransactionDetailInfoEntity.setCreateProgramId("1");
        alterationHistorySalesTransactionDetailInfoEntity.setUpdateUserId("1");
        alterationHistorySalesTransactionDetailInfoEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        alterationHistorySalesTransactionDetailInfoEntity.setUpdateProgramId("1");
    }

    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionDetailInfoEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        int result=alterationHistorySalesTransactionDetailInfoEntityMapper.insertSelective(alterationHistorySalesTransactionDetailInfoEntity);
        assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionDetailInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        alterationHistorySalesTransactionDetailInfoEntity.setTransactionId("0");
        alterationHistorySalesTransactionDetailInfoEntity.setOrderSubNumber(0);
        alterationHistorySalesTransactionDetailInfoEntity.setSalesTransactionId("0");
        alterationHistorySalesTransactionDetailInfoEntity.setDetailSubNumber(0);
        alterationHistorySalesTransactionDetailInfoEntity.setHistoryType(0);   
        int result=alterationHistorySalesTransactionDetailInfoEntityMapper.insertSelective(alterationHistorySalesTransactionDetailInfoEntity);
        assertEquals(0, result);
    }
}
