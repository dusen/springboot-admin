package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionHeaderCondition;
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
public class ErrorEvacuationSalesTransactionHeaderMapperTest {

    @Autowired
    private ErrorEvacuationSalesTransactionHeaderMapper errorEvacuationSalesTransactionHeaderEntityMapper;

    private ErrorEvacuationSalesTransactionHeader errorEvacuationSalesTransactionHeaderEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        errorEvacuationSalesTransactionHeaderEntity =
                new ErrorEvacuationSalesTransactionHeader();
        errorEvacuationSalesTransactionHeaderEntity.setTransactionId("1");
        errorEvacuationSalesTransactionHeaderEntity.setOrderSubNumber(1);
        errorEvacuationSalesTransactionHeaderEntity.setSalesTransactionId("1");
        errorEvacuationSalesTransactionHeaderEntity.setSalesTransactionErrorId("1");
        errorEvacuationSalesTransactionHeaderEntity.setIntegratedOrderId("1");
        errorEvacuationSalesTransactionHeaderEntity.setSalesTransactionSubNumber(1);
        errorEvacuationSalesTransactionHeaderEntity.setStoreCode("1");
        errorEvacuationSalesTransactionHeaderEntity
                .setDataCreationDateTime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        errorEvacuationSalesTransactionHeaderEntity.setDataCreationBusinessDate("1");
        errorEvacuationSalesTransactionHeaderEntity.setCashRegisterNo(1);
        errorEvacuationSalesTransactionHeaderEntity.setReceiptNo("1");
        errorEvacuationSalesTransactionHeaderEntity.setSalesLinkageType(1);
        errorEvacuationSalesTransactionHeaderEntity.setSalesTransactionType("1");
        errorEvacuationSalesTransactionHeaderEntity.setReturnType(1);
        errorEvacuationSalesTransactionHeaderEntity.setSystemBrandCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setSystemBusinessCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setSystemCountryCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setChannelCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setOrderStatus("1");
        errorEvacuationSalesTransactionHeaderEntity.setOrderSubstatus("1");
        errorEvacuationSalesTransactionHeaderEntity.setOrderStatusUpdateDate("1");
        errorEvacuationSalesTransactionHeaderEntity
                .setOrderStatusLastUpdateDateTime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        errorEvacuationSalesTransactionHeaderEntity
                .setOrderCancelledDateTime(LocalDateTime.of(2018, 03, 15, 00, 00, 00));
        errorEvacuationSalesTransactionHeaderEntity.setBookingStoreCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setBookingStoreSystemBrandCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setBookingStoreSystemBusinessCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setBookingStoreSystemCountryCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setPaymentStatus("1");
        errorEvacuationSalesTransactionHeaderEntity.setPaymentStoreCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setPaymentStoreSystemBrandCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setPaymentStoreSystemBusinessCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setPaymentStoreSystemCountryCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setTransferOutStatus("1");
        errorEvacuationSalesTransactionHeaderEntity.setShipmentStatus("1");
        errorEvacuationSalesTransactionHeaderEntity.setShipmentStoreCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setShipmentStoreSystemBrandCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setShipmentStoreSystemBusinessCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setShipmentStoreSystemCountryCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setReceivingStatus("1");
        errorEvacuationSalesTransactionHeaderEntity.setReceiptStoreCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setReceiptStoreSystemBrandCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setReceiptStoreSystemBusinessCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setReceiptStoreSystemCountryCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setCustomerId("1");
        errorEvacuationSalesTransactionHeaderEntity.setOrderNumberForStorePayment("1");
        errorEvacuationSalesTransactionHeaderEntity.setAdvanceReceivedStoreCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBrandCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemBusinessCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setAdvanceReceivedStoreSystemCountryCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setOperatorCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setOriginalTransactionId("1");
        errorEvacuationSalesTransactionHeaderEntity.setOriginalCashRegisterNo(1);
        errorEvacuationSalesTransactionHeaderEntity.setOriginalReceiptNo("1");
        errorEvacuationSalesTransactionHeaderEntity.setDepositCurrencyCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setDepositValue(new BigDecimal(1));
        errorEvacuationSalesTransactionHeaderEntity.setChangeCurrencyCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setChangeValue(new BigDecimal(1));
        errorEvacuationSalesTransactionHeaderEntity.setReceiptNoForCreditCardCancellation("1");
        errorEvacuationSalesTransactionHeaderEntity.setReceiptNoForCreditCard("1");
        errorEvacuationSalesTransactionHeaderEntity.setReceiptIssuedFlag(true);
        errorEvacuationSalesTransactionHeaderEntity.setEReceiptTargetFlag(true);
        errorEvacuationSalesTransactionHeaderEntity.setProcessingCompanyCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setEmployeeSaleFlag(true);
        errorEvacuationSalesTransactionHeaderEntity.setConsistencySalesFlag(true);
        errorEvacuationSalesTransactionHeaderEntity.setCorporateId("1");
        errorEvacuationSalesTransactionHeaderEntity.setSalesTransactionDiscountFlag(true);
        errorEvacuationSalesTransactionHeaderEntity
                .setSalesTransactionDiscountAmountRateCurrencyCode("1");
        errorEvacuationSalesTransactionHeaderEntity
                .setSalesTransactionDiscountAmountRate(new BigDecimal(1));
        errorEvacuationSalesTransactionHeaderEntity.setTokenCode("1");
        errorEvacuationSalesTransactionHeaderEntity.setCreateUserId("1");
        errorEvacuationSalesTransactionHeaderEntity
                .setCreateDatetime(LocalDateTime.of(2010, 03, 15, 00, 00, 00));
        errorEvacuationSalesTransactionHeaderEntity.setCreateProgramId("1");
        errorEvacuationSalesTransactionHeaderEntity.setUpdateUserId("1");
        errorEvacuationSalesTransactionHeaderEntity
                .setUpdateDatetime(LocalDateTime.of(2018, 03, 15, 00, 00, 00));
        errorEvacuationSalesTransactionHeaderEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionHeaderEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        ErrorEvacuationSalesTransactionHeaderCondition errorEvacuationSalesTransactionHeaderEntityCondition =
                new ErrorEvacuationSalesTransactionHeaderCondition();
        errorEvacuationSalesTransactionHeaderEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionHeaderEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionHeaderEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionHeaderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("5");
        ErrorEvacuationSalesTransactionHeaderCondition errorEvacuationSalesTransactionHeaderEntityCondition =
                new ErrorEvacuationSalesTransactionHeaderCondition();
        errorEvacuationSalesTransactionHeaderEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionHeaderEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionHeaderEntityCondition);
        assertThat(result, is(0));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionHeaderEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelective() {
        errorEvacuationSalesTransactionHeaderEntity.setTransactionId("2");
        errorEvacuationSalesTransactionHeaderEntity.setOrderSubNumber(2);
        errorEvacuationSalesTransactionHeaderEntity.setSalesTransactionId("2");
        int result = errorEvacuationSalesTransactionHeaderEntityMapper
                .insertSelective(errorEvacuationSalesTransactionHeaderEntity);
        assertEquals(1, result);
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionHeaderEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionHeaderEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveZero() {
        int result = errorEvacuationSalesTransactionHeaderEntityMapper
                .insertSelective(errorEvacuationSalesTransactionHeaderEntity);
        assertEquals(0, result);
    }

}
