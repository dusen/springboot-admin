package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailInfoCondition;
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
public class SalesErrorSalesTransactionDetailInfoMapperTest {

    @Autowired
    private SalesErrorSalesTransactionDetailInfoMapper salesErrorSalesTransactionDetailInfoEntityMapper;
    
    private SalesErrorSalesTransactionDetailInfo salesErrorSalesTransactionDetailInfoEntity;


    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        salesErrorSalesTransactionDetailInfoEntity=new SalesErrorSalesTransactionDetailInfo();
        salesErrorSalesTransactionDetailInfoEntity.setTransactionId("1");
        salesErrorSalesTransactionDetailInfoEntity.setOrderSubNumber(1);
        salesErrorSalesTransactionDetailInfoEntity.setSalesTransactionId("1");
        salesErrorSalesTransactionDetailInfoEntity.setDetailSubNumber(1);
        salesErrorSalesTransactionDetailInfoEntity.setItemDetailSubNumber(1);
        salesErrorSalesTransactionDetailInfoEntity.setKeyCode("1");
        salesErrorSalesTransactionDetailInfoEntity.setCodeValue1("1");
        salesErrorSalesTransactionDetailInfoEntity.setCodeValue2("1");
        salesErrorSalesTransactionDetailInfoEntity.setCodeValue3("1");
        salesErrorSalesTransactionDetailInfoEntity.setCodeValue4("1");
        salesErrorSalesTransactionDetailInfoEntity.setName1("1");
        salesErrorSalesTransactionDetailInfoEntity.setName2("1");
        salesErrorSalesTransactionDetailInfoEntity.setName3("1");
        salesErrorSalesTransactionDetailInfoEntity.setName4("1");
        salesErrorSalesTransactionDetailInfoEntity.setCreateUserId("1");
        salesErrorSalesTransactionDetailInfoEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesErrorSalesTransactionDetailInfoEntity.setCreateProgramId("1");
        salesErrorSalesTransactionDetailInfoEntity.setUpdateUserId("1");
        salesErrorSalesTransactionDetailInfoEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesErrorSalesTransactionDetailInfoEntity.setUpdateProgramId("1");
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionDetailInfoEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        SalesErrorSalesTransactionDetailInfoCondition salesErrorSalesTransactionDetailInfoEntityCondition =
                new SalesErrorSalesTransactionDetailInfoCondition();
        salesErrorSalesTransactionDetailInfoEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());

        int result = salesErrorSalesTransactionDetailInfoEntityMapper
                .deleteByCondition(salesErrorSalesTransactionDetailInfoEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionDetailInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("5");
        SalesErrorSalesTransactionDetailInfoCondition salesErrorSalesTransactionDetailInfoEntityCondition =
                new SalesErrorSalesTransactionDetailInfoCondition();
        salesErrorSalesTransactionDetailInfoEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());

        int result = salesErrorSalesTransactionDetailInfoEntityMapper
                .deleteByCondition(salesErrorSalesTransactionDetailInfoEntityCondition);
        assertThat(result, is(0));
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionDetailInfoEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesErrorSalesTransactionDetailInfoEntity.setTransactionId("2");
        salesErrorSalesTransactionDetailInfoEntity.setOrderSubNumber(2);
        salesErrorSalesTransactionDetailInfoEntity.setSalesTransactionId("2");
        salesErrorSalesTransactionDetailInfoEntity.setDetailSubNumber(2);
        
        int result=salesErrorSalesTransactionDetailInfoEntityMapper.insertSelective(salesErrorSalesTransactionDetailInfoEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionDetailInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesErrorSalesTransactionDetailInfoEntityMapper.insertSelective(salesErrorSalesTransactionDetailInfoEntity);
        assertEquals(result, 0);
    }

}
