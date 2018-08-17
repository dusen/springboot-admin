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
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesOrderInformationCondition;
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
public class ErrorEvacuationSalesOrderInformationMapperTest {

    @Autowired
    private ErrorEvacuationSalesOrderInformationMapper errorEvacuationSalesOrderInformationEntityMapper;

    private ErrorEvacuationSalesOrderInformation errorEvacuationSalesOrderInformationEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        errorEvacuationSalesOrderInformationEntity =
                new ErrorEvacuationSalesOrderInformation();
        
        errorEvacuationSalesOrderInformationEntity.setTransactionId("1");
        errorEvacuationSalesOrderInformationEntity.setIntegratedOrderId("1");
        errorEvacuationSalesOrderInformationEntity.setSalesTransactionErrorId("1");
        errorEvacuationSalesOrderInformationEntity.setOrderBarcodeNumber("1");
        errorEvacuationSalesOrderInformationEntity.setStoreCode("1");
        errorEvacuationSalesOrderInformationEntity.setSystemBrandCode("1");
        errorEvacuationSalesOrderInformationEntity.setSystemBusinessCode("1");
        errorEvacuationSalesOrderInformationEntity.setSystemCountryCode("1");
        errorEvacuationSalesOrderInformationEntity.setChannelCode("1");
        errorEvacuationSalesOrderInformationEntity.setUpdateType("1");
        errorEvacuationSalesOrderInformationEntity.setCustomerId("1");
        errorEvacuationSalesOrderInformationEntity.setOrderConfirmationBusinessDate("1");
        errorEvacuationSalesOrderInformationEntity.setOrderConfirmationDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        errorEvacuationSalesOrderInformationEntity.setErrorCheckType(1);
        errorEvacuationSalesOrderInformationEntity.setDataAlterationSalesLinkageType(1);
        errorEvacuationSalesOrderInformationEntity.setDataAlterationBackboneLinkageType(1);
        errorEvacuationSalesOrderInformationEntity.setDataAlterationEditingFlag(true);
        errorEvacuationSalesOrderInformationEntity.setDataAlterationUserId("1");
        errorEvacuationSalesOrderInformationEntity.setCreateUserId("1");
        errorEvacuationSalesOrderInformationEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        errorEvacuationSalesOrderInformationEntity.setCreateProgramId("1");
        errorEvacuationSalesOrderInformationEntity.setUpdateUserId("1");
        errorEvacuationSalesOrderInformationEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        errorEvacuationSalesOrderInformationEntity.setUpdateProgramId("1");
    }


    @Test
    @DatabaseSetup("TErrorEvacuationSalesOrderInformationEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesOrderInformationEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        ErrorEvacuationSalesOrderInformationCondition errorEvacuationSalesOrderInformationEntityCondition =
                new ErrorEvacuationSalesOrderInformationCondition();
        errorEvacuationSalesOrderInformationEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesOrderInformationEntityMapper
                .deleteByCondition(errorEvacuationSalesOrderInformationEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesOrderInformationEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesOrderInformationEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("2");
        ErrorEvacuationSalesOrderInformationCondition errorEvacuationSalesOrderInformationEntityCondition =
                new ErrorEvacuationSalesOrderInformationCondition();
        errorEvacuationSalesOrderInformationEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesOrderInformationEntityMapper
                .deleteByCondition(errorEvacuationSalesOrderInformationEntityCondition);
        assertThat(result, is(0));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesOrderInformationEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesOrderInformationEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelective() {
        errorEvacuationSalesOrderInformationEntity.setTransactionId("2");
        errorEvacuationSalesOrderInformationEntity.setIntegratedOrderId("2");
        int result = errorEvacuationSalesOrderInformationEntityMapper
                .insertSelective(errorEvacuationSalesOrderInformationEntity);
        assertEquals(1, result);

    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesOrderInformationEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesOrderInformationEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveZero() {
        int result = errorEvacuationSalesOrderInformationEntityMapper
                .insertSelective(errorEvacuationSalesOrderInformationEntity);
        assertEquals(0, result);

    }
}
