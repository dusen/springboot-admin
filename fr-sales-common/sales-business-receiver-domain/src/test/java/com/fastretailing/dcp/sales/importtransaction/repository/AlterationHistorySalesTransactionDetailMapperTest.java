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
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDetail;
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
public class AlterationHistorySalesTransactionDetailMapperTest {

    @Autowired
    private AlterationHistorySalesTransactionDetailMapper alterationHistorySalesTransactionDetailEntityMapper;

    private AlterationHistorySalesTransactionDetail alterationHistorySalesTransactionDetailEntity;
    
    @Before
    public void setUp() {
        alterationHistorySalesTransactionDetailEntity=new AlterationHistorySalesTransactionDetail();
        alterationHistorySalesTransactionDetailEntity.setTransactionId("1");
        alterationHistorySalesTransactionDetailEntity.setOrderSubNumber(1);
        alterationHistorySalesTransactionDetailEntity.setSalesTransactionId("1");
        alterationHistorySalesTransactionDetailEntity.setDetailSubNumber(1);
        alterationHistorySalesTransactionDetailEntity.setHistoryType(1);
        alterationHistorySalesTransactionDetailEntity.setSalesTransactionErrorId("1");
        alterationHistorySalesTransactionDetailEntity.setItemDetailSubNumber(1);
        alterationHistorySalesTransactionDetailEntity.setSalesTransactionType("1");
        alterationHistorySalesTransactionDetailEntity.setSystemBrandCode("1");
        alterationHistorySalesTransactionDetailEntity.setL2ItemCode("1");
        alterationHistorySalesTransactionDetailEntity.setDisplayL2ItemCode("1");
        alterationHistorySalesTransactionDetailEntity.setL2ProductName("1");
        alterationHistorySalesTransactionDetailEntity.setL3ItemCode("1");
        alterationHistorySalesTransactionDetailEntity.setL3PosProductName("1");
        alterationHistorySalesTransactionDetailEntity.setProductClassification("1");
        alterationHistorySalesTransactionDetailEntity.setNonMdType("1");
        alterationHistorySalesTransactionDetailEntity.setNonMdCode("1");
        alterationHistorySalesTransactionDetailEntity.setServiceCode("1");
        alterationHistorySalesTransactionDetailEntity.setEpcCode("1");
        alterationHistorySalesTransactionDetailEntity.setGDepartmentCode("1");
        alterationHistorySalesTransactionDetailEntity.setMajorCategoryCode("1");
        alterationHistorySalesTransactionDetailEntity.setQuantityCode("1");
        alterationHistorySalesTransactionDetailEntity.setDetailQuantity(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setItemCostCurrencyCode("1");
        alterationHistorySalesTransactionDetailEntity.setItemCostValue(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode("1");
        alterationHistorySalesTransactionDetailEntity.setInitialSellingPrice(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setBclassPriceCurrencyCode("1");
        alterationHistorySalesTransactionDetailEntity.setBclassPrice(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setNewPriceCurrencyCode("1");
        alterationHistorySalesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode("1");
        alterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode("1");
        alterationHistorySalesTransactionDetailEntity.setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode("1");
        alterationHistorySalesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode("1");
        alterationHistorySalesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setCalculationUnavailableType("1");
        alterationHistorySalesTransactionDetailEntity.setOrderStatusUpdateDate("1");
        alterationHistorySalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistorySalesTransactionDetailEntity.setOrderStatus("1");
        alterationHistorySalesTransactionDetailEntity.setOrderSubstatus("1");
        alterationHistorySalesTransactionDetailEntity.setBookingStoreCode("1");
        alterationHistorySalesTransactionDetailEntity.setBookingStoreSystemBrandCode("1");
        alterationHistorySalesTransactionDetailEntity.setBookingStoreSystemBusinessCode("1");
        alterationHistorySalesTransactionDetailEntity.setBookingStoreSystemCountryCode("1");
        alterationHistorySalesTransactionDetailEntity.setShipmentStoreCode("1");
        alterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemBrandCode("1");
        alterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("1");
        alterationHistorySalesTransactionDetailEntity.setShipmentStoreSystemCountryCode("1");
        alterationHistorySalesTransactionDetailEntity.setReceiptStoreCode("1");
        alterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemBrandCode("1");
        alterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("1");
        alterationHistorySalesTransactionDetailEntity.setReceiptStoreSystemCountryCode("1");
        alterationHistorySalesTransactionDetailEntity.setContributionSalesRepresentative("1");
        alterationHistorySalesTransactionDetailEntity.setCustomerId("1");
        alterationHistorySalesTransactionDetailEntity.setBundlePurchaseApplicableQuantity(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode("1");
        alterationHistorySalesTransactionDetailEntity.setBundlePurchaseApplicablePrice(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setBundlePurchaseIndex(1);
        alterationHistorySalesTransactionDetailEntity.setLimitedAmountPromotionCount(1);
        alterationHistorySalesTransactionDetailEntity.setStoreItemDiscountType("1");
        alterationHistorySalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode("1");
        alterationHistorySalesTransactionDetailEntity.setStoreItemDiscountSetting(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setStoreBundleSaleFlag(true);
        alterationHistorySalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode("1");
        alterationHistorySalesTransactionDetailEntity.setStoreBundleSalePrice(new BigDecimal(1));
        alterationHistorySalesTransactionDetailEntity.setSetSalesDetailIndex(1);
        alterationHistorySalesTransactionDetailEntity.setTaxationType("1");
        alterationHistorySalesTransactionDetailEntity.setTaxSystemType("1");
        alterationHistorySalesTransactionDetailEntity.setCreateUserId("1");
        alterationHistorySalesTransactionDetailEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        alterationHistorySalesTransactionDetailEntity.setCreateProgramId("1");
        alterationHistorySalesTransactionDetailEntity.setUpdateUserId("1");
        alterationHistorySalesTransactionDetailEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        alterationHistorySalesTransactionDetailEntity.setUpdateProgramId("1");
    }

    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionDetailEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        int result=alterationHistorySalesTransactionDetailEntityMapper.insertSelective(alterationHistorySalesTransactionDetailEntity);
        assertEquals(1, result);
    }

    @Test
    @DatabaseSetup("TAlterationHistorySalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TAlterationHistorySalesTransactionDetailEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        alterationHistorySalesTransactionDetailEntity.setTransactionId("0");
        alterationHistorySalesTransactionDetailEntity.setOrderSubNumber(0);
        alterationHistorySalesTransactionDetailEntity.setSalesTransactionId("0");
        alterationHistorySalesTransactionDetailEntity.setDetailSubNumber(0);
        alterationHistorySalesTransactionDetailEntity.setHistoryType(0);
        int result=alterationHistorySalesTransactionDetailEntityMapper.insertSelective(alterationHistorySalesTransactionDetailEntity);
        assertEquals(0, result);
    }
}
