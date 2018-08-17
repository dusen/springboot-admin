package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.math.BigDecimal;
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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionHeaderCondition;
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
public class SalesErrorSalesTransactionHeaderMapperTest {

    @Autowired
    private SalesErrorSalesTransactionHeaderMapper salesErrorSalesTransactionHeaderEntityMapper;
    
    private SalesErrorSalesTransactionHeader salesErrorSalesTransactionHeaderEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        salesErrorSalesTransactionHeaderEntity=new SalesErrorSalesTransactionHeader();
        salesErrorSalesTransactionHeaderEntity.setTransactionId("1");
        salesErrorSalesTransactionHeaderEntity.setOrderSubNumber(1);
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionId("1");
        salesErrorSalesTransactionHeaderEntity.setIntegratedOrderId("1");
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionSubNumber(1);
        salesErrorSalesTransactionHeaderEntity.setStoreCode("1");
        salesErrorSalesTransactionHeaderEntity.setDataCreationDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesErrorSalesTransactionHeaderEntity.setDataCreationBusinessDate("1");
        salesErrorSalesTransactionHeaderEntity.setCashRegisterNo(1);
        salesErrorSalesTransactionHeaderEntity.setReceiptNo("1");
        salesErrorSalesTransactionHeaderEntity.setSalesLinkageType(1);
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionType("1");
        salesErrorSalesTransactionHeaderEntity.setReturnType(1);
        salesErrorSalesTransactionHeaderEntity.setSystemBrandCode("1");
        salesErrorSalesTransactionHeaderEntity.setSystemBusinessCode("1");
        salesErrorSalesTransactionHeaderEntity.setSystemCountryCode("1");
        salesErrorSalesTransactionHeaderEntity.setChannelCode("1");
        salesErrorSalesTransactionHeaderEntity.setOrderStatus("1");
        salesErrorSalesTransactionHeaderEntity.setOrderSubstatus("1");
        salesErrorSalesTransactionHeaderEntity.setOrderStatusUpdateDate("1");
        salesErrorSalesTransactionHeaderEntity.setOrderStatusLastUpdateDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesErrorSalesTransactionHeaderEntity.setOrderCancelledDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesErrorSalesTransactionHeaderEntity.setBookingStoreCode("1");
        salesErrorSalesTransactionHeaderEntity.setBookingStoreSystemBrandCode("1");
        salesErrorSalesTransactionHeaderEntity.setBookingStoreSystemBusinessCode("1");
        salesErrorSalesTransactionHeaderEntity.setBookingStoreSystemCountryCode("1");
        salesErrorSalesTransactionHeaderEntity.setPaymentStatus("1");
        salesErrorSalesTransactionHeaderEntity.setPaymentStoreCode("1");
        salesErrorSalesTransactionHeaderEntity.setPaymentStoreSystemBrandCode("1");
        salesErrorSalesTransactionHeaderEntity.setPaymentStoreSystemBusinessCode("1");
        salesErrorSalesTransactionHeaderEntity.setPaymentStoreSystemCountryCode("1");
        salesErrorSalesTransactionHeaderEntity.setTransferOutStatus("1");
        salesErrorSalesTransactionHeaderEntity.setShipmentStatus("1");
        salesErrorSalesTransactionHeaderEntity.setShipmentStoreCode("1");
        salesErrorSalesTransactionHeaderEntity.setShipmentStoreSystemBrandCode("1");
        salesErrorSalesTransactionHeaderEntity.setShipmentStoreSystemBusinessCode("1");
        salesErrorSalesTransactionHeaderEntity.setShipmentStoreSystemCountryCode("1");
        salesErrorSalesTransactionHeaderEntity.setReceivingStatus("1");
        salesErrorSalesTransactionHeaderEntity.setReceiptStoreCode("1");
        salesErrorSalesTransactionHeaderEntity.setReceiptStoreSystemBrandCode("1");
        salesErrorSalesTransactionHeaderEntity.setReceiptStoreSystemBusinessCode("1");
        salesErrorSalesTransactionHeaderEntity.setReceiptStoreSystemCountryCode("1");
        salesErrorSalesTransactionHeaderEntity.setCustomerId("1");
        salesErrorSalesTransactionHeaderEntity.setOrderNumberForStorePayment("1");
        salesErrorSalesTransactionHeaderEntity.setAdvanceReceivedStoreCode("1");
        salesErrorSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBrandCode("1");
        salesErrorSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBusinessCode("1");
        salesErrorSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemCountryCode("1");
        salesErrorSalesTransactionHeaderEntity.setOperatorCode("1");
        salesErrorSalesTransactionHeaderEntity.setOriginalTransactionId("1");
        salesErrorSalesTransactionHeaderEntity.setOriginalCashRegisterNo(1);
        salesErrorSalesTransactionHeaderEntity.setOriginalReceiptNo("1");
        salesErrorSalesTransactionHeaderEntity.setDepositCurrencyCode("1");
        salesErrorSalesTransactionHeaderEntity.setDepositValue(new BigDecimal(1));
        salesErrorSalesTransactionHeaderEntity.setChangeCurrencyCode("1");
        salesErrorSalesTransactionHeaderEntity.setChangeValue(new BigDecimal(1));
        salesErrorSalesTransactionHeaderEntity.setReceiptNoForCreditCardCancellation("1");
        salesErrorSalesTransactionHeaderEntity.setReceiptNoForCreditCard("1");
        salesErrorSalesTransactionHeaderEntity.setReceiptIssuedFlag(true);
        salesErrorSalesTransactionHeaderEntity.setEReceiptTargetFlag(true);
        salesErrorSalesTransactionHeaderEntity.setProcessingCompanyCode("1");
        salesErrorSalesTransactionHeaderEntity.setEmployeeSaleFlag(true);
        salesErrorSalesTransactionHeaderEntity.setConsistencySalesFlag(true);
        salesErrorSalesTransactionHeaderEntity.setCorporateId("1");
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionDiscountFlag(true);
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionDiscountAmountRateCurrencyCode("1");
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionDiscountAmountRate(new BigDecimal(1));
        salesErrorSalesTransactionHeaderEntity.setTokenCode("1");
        salesErrorSalesTransactionHeaderEntity.setCreateUserId("1");
        salesErrorSalesTransactionHeaderEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesErrorSalesTransactionHeaderEntity.setCreateProgramId("1");
        salesErrorSalesTransactionHeaderEntity.setUpdateUserId("1");
        salesErrorSalesTransactionHeaderEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesErrorSalesTransactionHeaderEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionHeaderEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        SalesErrorSalesTransactionHeaderCondition salesErrorSalesTransactionHeaderEntityCondition =
                new SalesErrorSalesTransactionHeaderCondition();
        salesErrorSalesTransactionHeaderEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionHeaderEntityMapper
                .deleteByCondition(salesErrorSalesTransactionHeaderEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionHeaderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("3");
        SalesErrorSalesTransactionHeaderCondition salesErrorSalesTransactionHeaderEntityCondition =
                new SalesErrorSalesTransactionHeaderCondition();
        salesErrorSalesTransactionHeaderEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());
        int result = salesErrorSalesTransactionHeaderEntityMapper
                .deleteByCondition(salesErrorSalesTransactionHeaderEntityCondition);
        assertThat(result, is(0));
    }
    
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionHeaderEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesErrorSalesTransactionHeaderEntity.setTransactionId("2");
        salesErrorSalesTransactionHeaderEntity.setOrderSubNumber(2);
        salesErrorSalesTransactionHeaderEntity.setSalesTransactionId("2");
        int result=salesErrorSalesTransactionHeaderEntityMapper.insertSelective(salesErrorSalesTransactionHeaderEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionHeaderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesErrorSalesTransactionHeaderEntityMapper.insertSelective(salesErrorSalesTransactionHeaderEntity);
        assertEquals(result, 0);
    }
    
}
