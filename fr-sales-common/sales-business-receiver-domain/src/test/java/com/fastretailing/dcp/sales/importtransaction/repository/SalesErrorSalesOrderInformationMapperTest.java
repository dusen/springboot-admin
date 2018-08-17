package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.junit.Assert.assertEquals;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformationCondition;
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
public class SalesErrorSalesOrderInformationMapperTest {
    
    @Autowired
    private SalesErrorSalesOrderInformationMapper salesErrorSalesOrderInformationEntityMapper;
    
    private SalesErrorSalesOrderInformation salesErrorSalesOrderInformationEntity;
    
    @Before
    public void setUp() {
        salesErrorSalesOrderInformationEntity=new SalesErrorSalesOrderInformation();
        salesErrorSalesOrderInformationEntity.setTransactionId("1");
        salesErrorSalesOrderInformationEntity.setIntegratedOrderId("1");
        salesErrorSalesOrderInformationEntity.setOrderBarcodeNumber("1");
        salesErrorSalesOrderInformationEntity.setStoreCode("1");
        salesErrorSalesOrderInformationEntity.setSystemBrandCode("1");
        salesErrorSalesOrderInformationEntity.setSystemBusinessCode("1");
        salesErrorSalesOrderInformationEntity.setSystemCountryCode("1");
        salesErrorSalesOrderInformationEntity.setChannelCode("1");
        salesErrorSalesOrderInformationEntity.setUpdateType("1");
        salesErrorSalesOrderInformationEntity.setCustomerId("1");
        salesErrorSalesOrderInformationEntity.setOrderConfirmationBusinessDate("1");
        salesErrorSalesOrderInformationEntity.setOrderConfirmationDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesErrorSalesOrderInformationEntity.setErrorCheckType(1);
        salesErrorSalesOrderInformationEntity.setDataAlterationSalesLinkageType(1);
        salesErrorSalesOrderInformationEntity.setDataAlterationBackboneLinkageType(1);
        salesErrorSalesOrderInformationEntity.setDataAlterationEditingFlag(true);
        salesErrorSalesOrderInformationEntity.setDataAlterationUserId("1");
        salesErrorSalesOrderInformationEntity.setCreateUserId("1");
        salesErrorSalesOrderInformationEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesErrorSalesOrderInformationEntity.setCreateProgramId("1");
        salesErrorSalesOrderInformationEntity.setUpdateUserId("1");
        salesErrorSalesOrderInformationEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesErrorSalesOrderInformationEntity.setUpdateProgramId("1");
    }


    @Test
    @DatabaseSetup("TSalesErrorSalesOrderInformationEntityMapperTest.xml")
    public void testSelectByConditionMultiRecord() {
        SalesErrorSalesOrderInformationCondition tsalesErrorSalesOrderInformationEntityCondition =
                new SalesErrorSalesOrderInformationCondition();
        tsalesErrorSalesOrderInformationEntityCondition.createCriteria().andIntegratedOrderIdEqualTo("1");
        List<SalesErrorSalesOrderInformation> result = salesErrorSalesOrderInformationEntityMapper
                .selectByCondition(tsalesErrorSalesOrderInformationEntityCondition);
       assertEquals(3, result.size());
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesOrderInformationEntityMapperTest.xml")
    public void testSelectByConditionOneRecord() {
        SalesErrorSalesOrderInformationCondition tsalesErrorSalesOrderInformationEntityCondition =
                new SalesErrorSalesOrderInformationCondition();
        tsalesErrorSalesOrderInformationEntityCondition.createCriteria().andIntegratedOrderIdEqualTo("2");
        List<SalesErrorSalesOrderInformation> result = salesErrorSalesOrderInformationEntityMapper
                .selectByCondition(tsalesErrorSalesOrderInformationEntityCondition);
       assertEquals(1, result.size());
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesOrderInformationEntityMapperTest.xml")
    public void testSelectByConditionNoRecord() {
        SalesErrorSalesOrderInformationCondition tsalesErrorSalesOrderInformationEntityCondition =
                new SalesErrorSalesOrderInformationCondition();
        tsalesErrorSalesOrderInformationEntityCondition.createCriteria().andIntegratedOrderIdEqualTo("3");
        List<SalesErrorSalesOrderInformation> result = salesErrorSalesOrderInformationEntityMapper
                .selectByCondition(tsalesErrorSalesOrderInformationEntityCondition);
       assertEquals(0, result.size());
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesOrderInformationEntityMapperBeforeInsert.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesOrderInformationEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesErrorSalesOrderInformationEntity.setTransactionId("2");
        salesErrorSalesOrderInformationEntity.setIntegratedOrderId("2");
        int result=salesErrorSalesOrderInformationEntityMapper.insertSelective(salesErrorSalesOrderInformationEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesOrderInformationEntityMapperBeforeInsert.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesOrderInformationEntityMapperBeforeInsert.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesErrorSalesOrderInformationEntityMapper.insertSelective(salesErrorSalesOrderInformationEntity);
        assertEquals(result, 0);
    }
    
}
