package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeaderCondition;
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
@Sql(scripts = "/junit_create_table_sales_6.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales_6.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@MapperScan(value = "com.fastretailing.dcp.sales.importtransaction.repository")
public class SalesTransactionHeaderMapperTest {

    @Autowired
    private SalesTransactionHeaderMapper salesTransactionHeaderEntityMapper;

    private SalesTransactionHeader salesTransactionHeaderEntity;

    @Before
    public void setUp() {
        salesTransactionHeaderEntity = new SalesTransactionHeader();
        salesTransactionHeaderEntity.setSalesTransactionId("1");
        salesTransactionHeaderEntity.setOrderSubNumber(1);
        salesTransactionHeaderEntity.setIntegratedOrderId("1");
        salesTransactionHeaderEntity.setSalesTransactionSubNumber(1);
        salesTransactionHeaderEntity.setStoreCode("1");
        salesTransactionHeaderEntity
                .setDataCreationDateTime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        salesTransactionHeaderEntity.setDataCreationBusinessDate("1");
        salesTransactionHeaderEntity.setCashRegisterNo(1);
        salesTransactionHeaderEntity.setReceiptNo("1");
        salesTransactionHeaderEntity.setSalesLinkageType(1);
        salesTransactionHeaderEntity.setSalesTransactionType("1");
        salesTransactionHeaderEntity.setReturnType(1);
        salesTransactionHeaderEntity.setSystemBrandCode("1");
        salesTransactionHeaderEntity.setSystemBusinessCode("1");
        salesTransactionHeaderEntity.setSystemCountryCode("1");
        salesTransactionHeaderEntity.setChannelCode("1");
        salesTransactionHeaderEntity.setOrderStatus("1");
        salesTransactionHeaderEntity.setOrderSubstatus("1");
        salesTransactionHeaderEntity.setOrderStatusUpdateDate("1");
        salesTransactionHeaderEntity
                .setOrderStatusLastUpdateDateTime(LocalDateTime.of(2000, 03, 15, 00, 00, 00));
        salesTransactionHeaderEntity
                .setOrderCancelledDateTime(LocalDateTime.of(2000, 03, 15, 00, 00, 00));
        salesTransactionHeaderEntity.setBookingStoreCode("1");
        salesTransactionHeaderEntity.setBookingStoreSystemBrandCode("1");
        salesTransactionHeaderEntity.setBookingStoreSystemBusinessCode("1");
        salesTransactionHeaderEntity.setBookingStoreSystemCountryCode("1");
        salesTransactionHeaderEntity.setPaymentStatus("1");
        salesTransactionHeaderEntity.setPaymentStoreCode("1");
        salesTransactionHeaderEntity.setPaymentStoreSystemBrandCode("1");
        salesTransactionHeaderEntity.setPaymentStoreSystemBusinessCode("1");
        salesTransactionHeaderEntity.setPaymentStoreSystemCountryCode("1");
        salesTransactionHeaderEntity.setTransferOutStatus("1");
        salesTransactionHeaderEntity.setShipmentStatus("1");
        salesTransactionHeaderEntity.setShipmentStoreCode("1");
        salesTransactionHeaderEntity.setShipmentStoreSystemBrandCode("1");
        salesTransactionHeaderEntity.setShipmentStoreSystemBusinessCode("1");
        salesTransactionHeaderEntity.setShipmentStoreSystemCountryCode("1");
        salesTransactionHeaderEntity.setReceivingStatus("1");
        salesTransactionHeaderEntity.setReceiptStoreCode("1");
        salesTransactionHeaderEntity.setReceiptStoreSystemBrandCode("1");
        salesTransactionHeaderEntity.setReceiptStoreSystemBusinessCode("1");
        salesTransactionHeaderEntity.setReceiptStoreSystemCountryCode("1");
        salesTransactionHeaderEntity.setCustomerId("1");
        salesTransactionHeaderEntity.setOrderNumberForStorePayment("1");
        salesTransactionHeaderEntity.setAdvanceReceivedStoreCode("1");
        salesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBrandCode("1");
        salesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBusinessCode("1");
        salesTransactionHeaderEntity.setAdvanceReceivedStoreSystemCountryCode("1");
        salesTransactionHeaderEntity.setOperatorCode("1");
        salesTransactionHeaderEntity.setOriginalTransactionId("1");
        salesTransactionHeaderEntity.setOriginalCashRegisterNo(1);
        salesTransactionHeaderEntity.setOriginalReceiptNo("1");
        salesTransactionHeaderEntity.setDepositCurrencyCode("1");
        salesTransactionHeaderEntity.setDepositValue(new BigDecimal(1));
        salesTransactionHeaderEntity.setChangeCurrencyCode("1");
        salesTransactionHeaderEntity.setChangeValue(new BigDecimal(1));
        salesTransactionHeaderEntity.setReceiptNoForCreditCardCancellation("1");
        salesTransactionHeaderEntity.setReceiptNoForCreditCard("1");
        salesTransactionHeaderEntity.setReceiptIssuedFlag(true);
        salesTransactionHeaderEntity.setEReceiptTargetFlag(true);
        salesTransactionHeaderEntity.setProcessingCompanyCode("1");
        salesTransactionHeaderEntity.setEmployeeSaleFlag(true);
        salesTransactionHeaderEntity.setConsistencySalesFlag(true);
        salesTransactionHeaderEntity.setCorporateId("1");
        salesTransactionHeaderEntity.setSalesTransactionDiscountFlag(true);
        salesTransactionHeaderEntity.setSalesTransactionDiscountAmountRateCurrencyCode("1");
        salesTransactionHeaderEntity.setSalesTransactionDiscountAmountRate(new BigDecimal(1));
        salesTransactionHeaderEntity.setTokenCode("1");
        salesTransactionHeaderEntity.setCreateUserId("1");
        salesTransactionHeaderEntity.setCreateDatetime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        salesTransactionHeaderEntity.setCreateProgramId("1");
        salesTransactionHeaderEntity.setUpdateUserId("1");
        salesTransactionHeaderEntity.setUpdateDatetime(LocalDateTime.of(2018, 03, 15, 00, 00, 00));
        salesTransactionHeaderEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TSalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionHeaderEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesTransactionHeaderEntity.setSalesTransactionId("2");
        salesTransactionHeaderEntity.setOrderSubNumber(2);
        int result =
                salesTransactionHeaderEntityMapper.insertSelective(salesTransactionHeaderEntity);
        assertEquals(1, result);
    }

    @Test
    @DatabaseSetup("TSalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionHeaderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result =
                salesTransactionHeaderEntityMapper.insertSelective(salesTransactionHeaderEntity);
        assertEquals(0, result);
    }

    @Test
    @DatabaseSetup("TSalesTransactionHeaderEntityMapperTest.xml")
    public void testSelectByConditionOneRecord() {
        SalesTransactionHeaderCondition headerCondition = new SalesTransactionHeaderCondition();
        headerCondition.createCriteria().andSalesTransactionIdEqualTo("1");
        List<SalesTransactionHeader> headerList =
                salesTransactionHeaderEntityMapper.selectByCondition(headerCondition);
        assertEquals(1, headerList.size());
    }

    @Test
    @DatabaseSetup("TSalesTransactionHeaderEntityMapperTest.xml")
    public void testSelectByConditionNoRecord() {
        SalesTransactionHeaderCondition headerCondition = new SalesTransactionHeaderCondition();
        headerCondition.createCriteria().andSalesTransactionIdEqualTo("0011");
        List<SalesTransactionHeader> headerList =
                salesTransactionHeaderEntityMapper.selectByCondition(headerCondition);
        assertEquals(0, headerList.size());
    }
}
