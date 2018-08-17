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
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetailCondition;
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
public class ErrorEvacuationSalesTransactionDetailMapperTest {

    @Autowired
    private ErrorEvacuationSalesTransactionDetailMapper errorEvacuationSalesTransactionDetailEntityMapper;

    private ErrorEvacuationSalesTransactionDetail errorEvacuationSalesTransactionDetailEntity;
    
    @Before
    public void setUp() throws Exception {
        errorEvacuationSalesTransactionDetailEntity =
                new ErrorEvacuationSalesTransactionDetail();
        errorEvacuationSalesTransactionDetailEntity.setTransactionId("1");
        errorEvacuationSalesTransactionDetailEntity.setOrderSubNumber(1);
        errorEvacuationSalesTransactionDetailEntity.setSalesTransactionId("1");
        errorEvacuationSalesTransactionDetailEntity.setDetailSubNumber(1);
        errorEvacuationSalesTransactionDetailEntity.setSalesTransactionErrorId("1");
        errorEvacuationSalesTransactionDetailEntity.setItemDetailSubNumber(1);
        errorEvacuationSalesTransactionDetailEntity.setSalesTransactionType("1");
        errorEvacuationSalesTransactionDetailEntity.setSystemBrandCode("1");
        errorEvacuationSalesTransactionDetailEntity.setL2ItemCode("1");
        errorEvacuationSalesTransactionDetailEntity.setDisplayL2ItemCode("1");
        errorEvacuationSalesTransactionDetailEntity.setL2ProductName("1");
        errorEvacuationSalesTransactionDetailEntity.setL3ItemCode("1");
        errorEvacuationSalesTransactionDetailEntity.setL3PosProductName("1");
        errorEvacuationSalesTransactionDetailEntity.setProductClassification("1");
        errorEvacuationSalesTransactionDetailEntity.setNonMdType("1");
        errorEvacuationSalesTransactionDetailEntity.setNonMdCode("1");
        errorEvacuationSalesTransactionDetailEntity.setServiceCode("1");
        errorEvacuationSalesTransactionDetailEntity.setEpcCode("1");
        errorEvacuationSalesTransactionDetailEntity.setGDepartmentCode("1");
        errorEvacuationSalesTransactionDetailEntity.setMajorCategoryCode("1");
        errorEvacuationSalesTransactionDetailEntity.setQuantityCode("1");
        errorEvacuationSalesTransactionDetailEntity.setDetailQuantity(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setItemCostCurrencyCode("1");
        errorEvacuationSalesTransactionDetailEntity.setItemCostValue(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode("1");
        errorEvacuationSalesTransactionDetailEntity.setInitialSellingPrice(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setBclassPriceCurrencyCode("1");
        errorEvacuationSalesTransactionDetailEntity.setBclassPrice(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setNewPriceCurrencyCode("1");
        errorEvacuationSalesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode("1");
        errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode("1");
        errorEvacuationSalesTransactionDetailEntity.setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode("1");
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode("1");
        errorEvacuationSalesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setCalculationUnavailableType("1");
        errorEvacuationSalesTransactionDetailEntity.setOrderStatusUpdateDate("1");
        errorEvacuationSalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        errorEvacuationSalesTransactionDetailEntity.setOrderStatus("1");
        errorEvacuationSalesTransactionDetailEntity.setOrderSubstatus("1");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreCode("1");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreSystemBrandCode("1");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreSystemBusinessCode("1");
        errorEvacuationSalesTransactionDetailEntity.setBookingStoreSystemCountryCode("1");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreCode("1");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreSystemBrandCode("1");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("1");
        errorEvacuationSalesTransactionDetailEntity.setShipmentStoreSystemCountryCode("1");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreCode("1");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreSystemBrandCode("1");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("1");
        errorEvacuationSalesTransactionDetailEntity.setReceiptStoreSystemCountryCode("1");
        errorEvacuationSalesTransactionDetailEntity.setContributionSalesRepresentative("1");
        errorEvacuationSalesTransactionDetailEntity.setCustomerId("1");
        errorEvacuationSalesTransactionDetailEntity.setBundlePurchaseApplicableQuantity(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode("1");
        errorEvacuationSalesTransactionDetailEntity.setBundlePurchaseApplicablePrice(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setBundlePurchaseIndex(1);
        errorEvacuationSalesTransactionDetailEntity.setLimitedAmountPromotionCount(1);
        errorEvacuationSalesTransactionDetailEntity.setStoreItemDiscountType("1");
        errorEvacuationSalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode("1");
        errorEvacuationSalesTransactionDetailEntity.setStoreItemDiscountSetting(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setStoreBundleSaleFlag(true);
        errorEvacuationSalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode("1");
        errorEvacuationSalesTransactionDetailEntity.setStoreBundleSalePrice(new BigDecimal(1));
        errorEvacuationSalesTransactionDetailEntity.setSetSalesDetailIndex(1);
        errorEvacuationSalesTransactionDetailEntity.setTaxationType("1");
        errorEvacuationSalesTransactionDetailEntity.setTaxSystemType("1");
        errorEvacuationSalesTransactionDetailEntity.setCreateUserId("1");
        errorEvacuationSalesTransactionDetailEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        errorEvacuationSalesTransactionDetailEntity.setCreateProgramId("1");
        errorEvacuationSalesTransactionDetailEntity.setUpdateUserId("1");
        errorEvacuationSalesTransactionDetailEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        errorEvacuationSalesTransactionDetailEntity.setUpdateProgramId("1");

    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionDetailEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        ErrorEvacuationSalesTransactionDetailCondition errorEvacuationSalesTransactionDetailEntityCondition =
                new ErrorEvacuationSalesTransactionDetailCondition();
        errorEvacuationSalesTransactionDetailEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionDetailEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionDetailEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionDetailEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("2");
        ErrorEvacuationSalesTransactionDetailCondition errorEvacuationSalesTransactionDetailEntityCondition =
                new ErrorEvacuationSalesTransactionDetailCondition();
        errorEvacuationSalesTransactionDetailEntityCondition.createCriteria()
                .andSalesTransactionErrorIdEqualTo(
                        transactionImportData.getSalesTransactionErrorId());
        int result = errorEvacuationSalesTransactionDetailEntityMapper
                .deleteByCondition(errorEvacuationSalesTransactionDetailEntityCondition);
        assertThat(result, is(0));
    }

    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionDetailEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelective() {
        errorEvacuationSalesTransactionDetailEntity.setTransactionId("2");
        errorEvacuationSalesTransactionDetailEntity.setOrderSubNumber(2);
        errorEvacuationSalesTransactionDetailEntity.setSalesTransactionId("2");
        errorEvacuationSalesTransactionDetailEntity.setDetailSubNumber(2);
        int result = errorEvacuationSalesTransactionDetailEntityMapper
                .insertSelective(errorEvacuationSalesTransactionDetailEntity);
        assertEquals(result, 1);
    }
    @Test
    @DatabaseSetup("TErrorEvacuationSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TErrorEvacuationSalesTransactionDetailEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveZero() {
        int result = errorEvacuationSalesTransactionDetailEntityMapper
                .insertSelective(errorEvacuationSalesTransactionDetailEntity);
        assertEquals(result, 0);
    }
    
}
