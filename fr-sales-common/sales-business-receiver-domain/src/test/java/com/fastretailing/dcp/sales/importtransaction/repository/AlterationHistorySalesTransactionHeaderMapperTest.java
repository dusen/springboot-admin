package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
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
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionHeader;
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
public class AlterationHistorySalesTransactionHeaderMapperTest {
    @Autowired
    private AlterationHistorySalesTransactionHeaderMapper alterationHistorySalesTransactionHeaderEntityMapper;

    private AlterationHistorySalesTransactionHeader alterationHistorySalesTransactionHeaderEntity;

    @Before
    public void setUp() {
        alterationHistorySalesTransactionHeaderEntity =
                new AlterationHistorySalesTransactionHeader();
        alterationHistorySalesTransactionHeaderEntity.setTransactionId("1");
        alterationHistorySalesTransactionHeaderEntity.setOrderSubNumber(1);
        alterationHistorySalesTransactionHeaderEntity.setSalesTransactionId("1");
        alterationHistorySalesTransactionHeaderEntity.setHistoryType(1);
        alterationHistorySalesTransactionHeaderEntity.setSalesTransactionErrorId("1");
        alterationHistorySalesTransactionHeaderEntity.setIntegratedOrderId("1");
        alterationHistorySalesTransactionHeaderEntity.setSalesTransactionSubNumber(1);
        alterationHistorySalesTransactionHeaderEntity.setStoreCode("1");
        alterationHistorySalesTransactionHeaderEntity.setDataCreationDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistorySalesTransactionHeaderEntity.setDataCreationBusinessDate("1");
        alterationHistorySalesTransactionHeaderEntity.setCashRegisterNo(1);
        alterationHistorySalesTransactionHeaderEntity.setReceiptNo("1");
        alterationHistorySalesTransactionHeaderEntity.setSalesLinkageType(1);
        alterationHistorySalesTransactionHeaderEntity.setSalesTransactionType("1");
        alterationHistorySalesTransactionHeaderEntity.setReturnType(1);
        alterationHistorySalesTransactionHeaderEntity.setSystemBrandCode("1");
        alterationHistorySalesTransactionHeaderEntity.setSystemBusinessCode("1");
        alterationHistorySalesTransactionHeaderEntity.setSystemCountryCode("1");
        alterationHistorySalesTransactionHeaderEntity.setChannelCode("1");
        alterationHistorySalesTransactionHeaderEntity.setOrderStatus("1");
        alterationHistorySalesTransactionHeaderEntity.setOrderSubstatus("1");
        alterationHistorySalesTransactionHeaderEntity.setOrderStatusUpdateDate("1");
        alterationHistorySalesTransactionHeaderEntity.setOrderStatusLastUpdateDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistorySalesTransactionHeaderEntity.setOrderCancelledDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistorySalesTransactionHeaderEntity.setBookingStoreCode("1");
        alterationHistorySalesTransactionHeaderEntity.setBookingStoreSystemBrandCode("1");
        alterationHistorySalesTransactionHeaderEntity.setBookingStoreSystemBusinessCode("1");
        alterationHistorySalesTransactionHeaderEntity.setBookingStoreSystemCountryCode("1");
        alterationHistorySalesTransactionHeaderEntity.setPaymentStatus("1");
        alterationHistorySalesTransactionHeaderEntity.setPaymentStoreCode("1");
        alterationHistorySalesTransactionHeaderEntity.setPaymentStoreSystemBrandCode("1");
        alterationHistorySalesTransactionHeaderEntity.setPaymentStoreSystemBusinessCode("1");
        alterationHistorySalesTransactionHeaderEntity.setPaymentStoreSystemCountryCode("1");
        alterationHistorySalesTransactionHeaderEntity.setTransferOutStatus("1");
        alterationHistorySalesTransactionHeaderEntity.setShipmentStatus("1");
        alterationHistorySalesTransactionHeaderEntity.setShipmentStoreCode("1");
        alterationHistorySalesTransactionHeaderEntity.setShipmentStoreSystemBrandCode("1");
        alterationHistorySalesTransactionHeaderEntity.setShipmentStoreSystemBusinessCode("1");
        alterationHistorySalesTransactionHeaderEntity.setShipmentStoreSystemCountryCode("1");
        alterationHistorySalesTransactionHeaderEntity.setReceivingStatus("1");
        alterationHistorySalesTransactionHeaderEntity.setReceiptStoreCode("1");
        alterationHistorySalesTransactionHeaderEntity.setReceiptStoreSystemBrandCode("1");
        alterationHistorySalesTransactionHeaderEntity.setReceiptStoreSystemBusinessCode("1");
        alterationHistorySalesTransactionHeaderEntity.setReceiptStoreSystemCountryCode("1");
        alterationHistorySalesTransactionHeaderEntity.setCustomerId("1");
        alterationHistorySalesTransactionHeaderEntity.setOrderNumberForStorePayment("1");
        alterationHistorySalesTransactionHeaderEntity.setAdvanceReceivedStoreCode("1");
        alterationHistorySalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBrandCode("1");
        alterationHistorySalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBusinessCode("1");
        alterationHistorySalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemCountryCode("1");
        alterationHistorySalesTransactionHeaderEntity.setOperatorCode("1");
        alterationHistorySalesTransactionHeaderEntity.setOriginalTransactionId("1");
        alterationHistorySalesTransactionHeaderEntity.setOriginalCashRegisterNo(1);
        alterationHistorySalesTransactionHeaderEntity.setOriginalReceiptNo("1");
        alterationHistorySalesTransactionHeaderEntity.setDepositCurrencyCode("1");
        alterationHistorySalesTransactionHeaderEntity.setDepositValue(new BigDecimal(1));
        alterationHistorySalesTransactionHeaderEntity.setChangeCurrencyCode("1");
        alterationHistorySalesTransactionHeaderEntity.setChangeValue(new BigDecimal(1));
        alterationHistorySalesTransactionHeaderEntity.setReceiptNoForCreditCardCancellation("1");
        alterationHistorySalesTransactionHeaderEntity.setReceiptNoForCreditCard("1");
        alterationHistorySalesTransactionHeaderEntity.setReceiptIssuedFlag(true);
        alterationHistorySalesTransactionHeaderEntity.setEReceiptTargetFlag(true);
        alterationHistorySalesTransactionHeaderEntity.setProcessingCompanyCode("1");
        alterationHistorySalesTransactionHeaderEntity.setEmployeeSaleFlag(true);
        alterationHistorySalesTransactionHeaderEntity.setConsistencySalesFlag(true);
        alterationHistorySalesTransactionHeaderEntity.setCorporateId("1");
        alterationHistorySalesTransactionHeaderEntity.setSalesTransactionDiscountFlag(true);
        alterationHistorySalesTransactionHeaderEntity.setSalesTransactionDiscountAmountRateCurrencyCode("1");
        alterationHistorySalesTransactionHeaderEntity.setSalesTransactionDiscountAmountRate(new BigDecimal(1));
        alterationHistorySalesTransactionHeaderEntity.setTokenCode("1");
        alterationHistorySalesTransactionHeaderEntity.setCreateUserId("1");
        alterationHistorySalesTransactionHeaderEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistorySalesTransactionHeaderEntity.setCreateProgramId("1");
        alterationHistorySalesTransactionHeaderEntity.setUpdateUserId("1");
        alterationHistorySalesTransactionHeaderEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        alterationHistorySalesTransactionHeaderEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionHeaderEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        alterationHistorySalesTransactionHeaderEntity.setTransactionId("2");
        alterationHistorySalesTransactionHeaderEntity.setOrderSubNumber(2);
        alterationHistorySalesTransactionHeaderEntity.setSalesTransactionId("2");
        alterationHistorySalesTransactionHeaderEntity.setHistoryType(2);
        int result = alterationHistorySalesTransactionHeaderEntityMapper
                .insertSelective(alterationHistorySalesTransactionHeaderEntity);
        assertEquals(1, result);

    }

    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionHeaderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {

        int result = alterationHistorySalesTransactionHeaderEntityMapper
                .insertSelective(alterationHistorySalesTransactionHeaderEntity);
        assertEquals(0, result);

    }

}
