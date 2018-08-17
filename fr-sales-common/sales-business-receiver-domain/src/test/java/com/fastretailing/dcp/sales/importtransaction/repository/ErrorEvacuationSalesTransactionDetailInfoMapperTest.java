package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetailInfoCondition;
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
public class ErrorEvacuationSalesTransactionDetailInfoMapperTest {

    @Autowired
    private ErrorEvacuationSalesTransactionDetailInfoMapper errorEvacuationSalesTransactionDetailInfoEntityMapper;

    private ErrorEvacuationSalesTransactionDetailInfo errorEvacuationSalesTransactionDetailInfoEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        errorEvacuationSalesTransactionDetailInfoEntity =
                new ErrorEvacuationSalesTransactionDetailInfo();
        errorEvacuationSalesTransactionDetailInfoEntity.setTransactionId("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setOrderSubNumber(1);
        errorEvacuationSalesTransactionDetailInfoEntity.setSalesTransactionId("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setDetailSubNumber(1);
        errorEvacuationSalesTransactionDetailInfoEntity.setSalesTransactionErrorId("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setItemDetailSubNumber(1);
        errorEvacuationSalesTransactionDetailInfoEntity.setKeyCode("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setCodeValue1("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setCodeValue2("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setCodeValue3("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setCodeValue4("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setName1("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setName2("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setName3("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setName4("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setCreateUserId("1");
        errorEvacuationSalesTransactionDetailInfoEntity
                .setCreateDatetime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        errorEvacuationSalesTransactionDetailInfoEntity.setCreateProgramId("1");
        errorEvacuationSalesTransactionDetailInfoEntity.setUpdateUserId("1");
        errorEvacuationSalesTransactionDetailInfoEntity
                .setUpdateDatetime(LocalDateTime.of(2018, 03, 15, 00, 00, 00));
        errorEvacuationSalesTransactionDetailInfoEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(
            value = "TErrorEvacuationSalesTransactionDetailInfoEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        ErrorEvacuationSalesTransactionDetailInfoCondition errorEvacuationSalesTransactionDetailInfoEntityCondition =
                new ErrorEvacuationSalesTransactionDetailInfoCondition();
        errorEvacuationSalesTransactionDetailInfoEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());

        int result = errorEvacuationSalesTransactionDetailInfoEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionDetailInfoEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionDetailInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("3");
        ErrorEvacuationSalesTransactionDetailInfoCondition errorEvacuationSalesTransactionDetailInfoEntityCondition =
                new ErrorEvacuationSalesTransactionDetailInfoCondition();
        errorEvacuationSalesTransactionDetailInfoEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());

        int result = errorEvacuationSalesTransactionDetailInfoEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionDetailInfoEntityCondition);
        assertThat(result, is(0));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionDetailInfoEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelective() {

        errorEvacuationSalesTransactionDetailInfoEntity.setTransactionId("2");
        errorEvacuationSalesTransactionDetailInfoEntity.setOrderSubNumber(2);
        errorEvacuationSalesTransactionDetailInfoEntity.setSalesTransactionId("2");
        errorEvacuationSalesTransactionDetailInfoEntity.setDetailSubNumber(2);
        int result = errorEvacuationSalesTransactionDetailInfoEntityMapper
                .insertSelective(errorEvacuationSalesTransactionDetailInfoEntity);
        
        assertEquals(result, 1);
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDetailInfoEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionDetailInfoEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveZero() {
        int result = errorEvacuationSalesTransactionDetailInfoEntityMapper
                .insertSelective(errorEvacuationSalesTransactionDetailInfoEntity);
        assertEquals(result, 0);
    }
}
