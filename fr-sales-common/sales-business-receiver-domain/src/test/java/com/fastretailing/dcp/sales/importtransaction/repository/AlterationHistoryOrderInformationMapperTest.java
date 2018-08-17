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
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistoryOrderInformation;
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
public class AlterationHistoryOrderInformationMapperTest {

    @Autowired
    private AlterationHistoryOrderInformationMapper alterationHistoryOrderInformationEntityMapper;

    private AlterationHistoryOrderInformation alterationHistoryOrderInformationEntity;

    @Before
    public void setUp() {
        alterationHistoryOrderInformationEntity = new AlterationHistoryOrderInformation();
        alterationHistoryOrderInformationEntity.setTransactionId("1");
        alterationHistoryOrderInformationEntity.setIntegratedOrderId("1");
        alterationHistoryOrderInformationEntity.setHistoryType(1);
        alterationHistoryOrderInformationEntity.setSalesTransactionErrorId("1");
        alterationHistoryOrderInformationEntity.setOrderBarcodeNumber("1");
        alterationHistoryOrderInformationEntity.setStoreCode("1");
        alterationHistoryOrderInformationEntity.setSystemBrandCode("1");
        alterationHistoryOrderInformationEntity.setSystemBusinessCode("1");
        alterationHistoryOrderInformationEntity.setSystemCountryCode("1");
        alterationHistoryOrderInformationEntity.setChannelCode("1");
        alterationHistoryOrderInformationEntity.setUpdateType("1");
        alterationHistoryOrderInformationEntity.setCustomerId("1");
        alterationHistoryOrderInformationEntity.setOrderConfirmationBusinessDate("1");
        alterationHistoryOrderInformationEntity.setOrderConfirmationDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistoryOrderInformationEntity.setErrorCheckType(1);
        alterationHistoryOrderInformationEntity.setDataAlterationSalesLinkageType(1);
        alterationHistoryOrderInformationEntity.setDataAlterationBackboneLinkageType(1);
        alterationHistoryOrderInformationEntity.setDataAlterationEditingFlag(true);
        alterationHistoryOrderInformationEntity.setDataAlterationUserId("1");
        alterationHistoryOrderInformationEntity.setCreateUserId("1");
        alterationHistoryOrderInformationEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistoryOrderInformationEntity.setCreateProgramId("1");
        alterationHistoryOrderInformationEntity.setUpdateUserId("1");
        alterationHistoryOrderInformationEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        alterationHistoryOrderInformationEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TAlterationHistoryOrderInformationEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistoryOrderInformationEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        alterationHistoryOrderInformationEntity.setTransactionId("2");
        alterationHistoryOrderInformationEntity.setIntegratedOrderId("2");
        alterationHistoryOrderInformationEntity.setHistoryType(2);
        int result = alterationHistoryOrderInformationEntityMapper
                .insertSelective(alterationHistoryOrderInformationEntity);
        assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TAlterationHistoryOrderInformationEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistoryOrderInformationEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result = alterationHistoryOrderInformationEntityMapper
                .insertSelective(alterationHistoryOrderInformationEntity);
        assertEquals(0, result);
    }
}
