package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.junit.Assert.assertEquals;
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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesOrderInformation;
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
public class SalesOrderInformationMapperTest {

    @Autowired
    private SalesOrderInformationMapper salesOrderInformationEntityMapper;
    
    private SalesOrderInformation salesOrderInformationEntity;
    
    @Before
    public void setUp() {
        salesOrderInformationEntity=new SalesOrderInformation();
        salesOrderInformationEntity.setIntegratedOrderId("2");
        salesOrderInformationEntity.setOrderBarcodeNumber("2");
        salesOrderInformationEntity.setStoreCode("2");
        salesOrderInformationEntity.setSystemBrandCode("2");
        salesOrderInformationEntity.setSystemBusinessCode("2");
        salesOrderInformationEntity.setSystemCountryCode("2");
        salesOrderInformationEntity.setChannelCode("2");
        salesOrderInformationEntity.setUpdateType("2");
        salesOrderInformationEntity.setCustomerId("2");
        salesOrderInformationEntity.setOrderConfirmationBusinessDate("2");
        salesOrderInformationEntity.setOrderConfirmationDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesOrderInformationEntity.setErrorCheckType(2);
        salesOrderInformationEntity.setDataAlterationSalesLinkageType(2);
        salesOrderInformationEntity.setDataAlterationBackboneLinkageType(2);
        salesOrderInformationEntity.setDataAlterationEditingFlag(true);
        salesOrderInformationEntity.setDataAlterationUserId("2");
        salesOrderInformationEntity.setCreateUserId("2");
        salesOrderInformationEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesOrderInformationEntity.setCreateProgramId("2");
        salesOrderInformationEntity.setUpdateUserId("2");
        salesOrderInformationEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesOrderInformationEntity.setUpdateProgramId("2");
    }

    @Test
    @DatabaseSetup("TSalesOrderInformationEntityMapperTest.xml")
    public void testSelectByPrimaryKey() {

        SalesOrderInformation salesOrderInformationEntity =
                salesOrderInformationEntityMapper.selectByPrimaryKey("1");
        
        assertEquals(salesOrderInformationEntity.getIntegratedOrderId(), "1");
        assertEquals(salesOrderInformationEntity.getOrderBarcodeNumber(),"1");
        assertEquals(salesOrderInformationEntity.getChannelCode(), "1");
    }
    
    @Test
    @DatabaseSetup("TSalesOrderInformationEntityMapperTest.xml")
    public void testSelectByPrimaryKeyNoRecord() {

        SalesOrderInformation salesOrderInformationEntity =
                salesOrderInformationEntityMapper.selectByPrimaryKey("2");
        assertEquals(salesOrderInformationEntity, null);

    }
    
    @Test
    @DatabaseSetup("TSalesOrderInformationEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesOrderInformationEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        int result=salesOrderInformationEntityMapper.insertSelective(salesOrderInformationEntity);
        assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TSalesOrderInformationEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesOrderInformationEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        salesOrderInformationEntity.setIntegratedOrderId("1");
        int result=salesOrderInformationEntityMapper.insertSelective(salesOrderInformationEntity);
        assertEquals(0, result);
        
    }
    
}
