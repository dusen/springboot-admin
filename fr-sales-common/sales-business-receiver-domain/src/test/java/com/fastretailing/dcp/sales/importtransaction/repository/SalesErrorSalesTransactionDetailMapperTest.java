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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailCondition;
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
public class SalesErrorSalesTransactionDetailMapperTest {

    @Autowired
    private SalesErrorSalesTransactionDetailMapper salesErrorSalesTransactionDetailEntityMapper;

    private SalesErrorSalesTransactionDetail salesErrorSalesTransactionDetailEntity;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() {
        salesErrorSalesTransactionDetailEntity=new SalesErrorSalesTransactionDetail();
        salesErrorSalesTransactionDetailEntity.setTransactionId("1");
        salesErrorSalesTransactionDetailEntity.setOrderSubNumber(1);
        salesErrorSalesTransactionDetailEntity.setSalesTransactionId("1");
        salesErrorSalesTransactionDetailEntity.setDetailSubNumber(1);
        salesErrorSalesTransactionDetailEntity.setItemDetailSubNumber(1);
        salesErrorSalesTransactionDetailEntity.setSalesTransactionType("1");
        salesErrorSalesTransactionDetailEntity.setSystemBrandCode("1");
        salesErrorSalesTransactionDetailEntity.setL2ItemCode("1");
        salesErrorSalesTransactionDetailEntity.setDisplayL2ItemCode("1");
        salesErrorSalesTransactionDetailEntity.setL2ProductName("1");
        salesErrorSalesTransactionDetailEntity.setL3ItemCode("1");
        salesErrorSalesTransactionDetailEntity.setL3PosProductName("1");
        salesErrorSalesTransactionDetailEntity.setProductClassification("1");
        salesErrorSalesTransactionDetailEntity.setNonMdType("1");
        salesErrorSalesTransactionDetailEntity.setNonMdCode("1");
        salesErrorSalesTransactionDetailEntity.setServiceCode("1");
        salesErrorSalesTransactionDetailEntity.setEpcCode("1");
        salesErrorSalesTransactionDetailEntity.setGDepartmentCode("1");
        salesErrorSalesTransactionDetailEntity.setMajorCategoryCode("1");
        salesErrorSalesTransactionDetailEntity.setQuantityCode("1");
        salesErrorSalesTransactionDetailEntity.setDetailQuantity(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setItemCostCurrencyCode("1");
        salesErrorSalesTransactionDetailEntity.setItemCostValue(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setInitialSellingPriceCurrencyCode("1");
        salesErrorSalesTransactionDetailEntity.setInitialSellingPrice(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setBclassPriceCurrencyCode("1");
        salesErrorSalesTransactionDetailEntity.setBclassPrice(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setNewPriceCurrencyCode("1");
        salesErrorSalesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode("1");
        salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode("1");
        salesErrorSalesTransactionDetailEntity.setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode("1");
        salesErrorSalesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode("1");
        salesErrorSalesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setCalculationUnavailableType("1");
        salesErrorSalesTransactionDetailEntity.setOrderStatusUpdateDate("1");
        salesErrorSalesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesErrorSalesTransactionDetailEntity.setOrderStatus("1");
        salesErrorSalesTransactionDetailEntity.setOrderSubstatus("1");
        salesErrorSalesTransactionDetailEntity.setBookingStoreCode("1");
        salesErrorSalesTransactionDetailEntity.setBookingStoreSystemBrandCode("1");
        salesErrorSalesTransactionDetailEntity.setBookingStoreSystemBusinessCode("1");
        salesErrorSalesTransactionDetailEntity.setBookingStoreSystemCountryCode("1");
        salesErrorSalesTransactionDetailEntity.setShipmentStoreCode("1");
        salesErrorSalesTransactionDetailEntity.setShipmentStoreSystemBrandCode("1");
        salesErrorSalesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("1");
        salesErrorSalesTransactionDetailEntity.setShipmentStoreSystemCountryCode("1");
        salesErrorSalesTransactionDetailEntity.setReceiptStoreCode("1");
        salesErrorSalesTransactionDetailEntity.setReceiptStoreSystemBrandCode("1");
        salesErrorSalesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("1");
        salesErrorSalesTransactionDetailEntity.setReceiptStoreSystemCountryCode("1");
        salesErrorSalesTransactionDetailEntity.setContributionSalesRepresentative("1");
        salesErrorSalesTransactionDetailEntity.setCustomerId("1");
        salesErrorSalesTransactionDetailEntity.setBundlePurchaseApplicableQuantity(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode("1");
        salesErrorSalesTransactionDetailEntity.setBundlePurchaseApplicablePrice(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setBundlePurchaseIndex(1);
        salesErrorSalesTransactionDetailEntity.setLimitedAmountPromotionCount(1);
        salesErrorSalesTransactionDetailEntity.setStoreItemDiscountType("1");
        salesErrorSalesTransactionDetailEntity.setStoreItemDiscountCurrencyCode("1");
        salesErrorSalesTransactionDetailEntity.setStoreItemDiscountSetting(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setStoreBundleSaleFlag(true);
        salesErrorSalesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode("1");
        salesErrorSalesTransactionDetailEntity.setStoreBundleSalePrice(new BigDecimal(1));
        salesErrorSalesTransactionDetailEntity.setSetSalesDetailIndex(1);
        salesErrorSalesTransactionDetailEntity.setTaxationType("1");
        salesErrorSalesTransactionDetailEntity.setTaxSystemType("1");
        salesErrorSalesTransactionDetailEntity.setCreateUserId("1");
        salesErrorSalesTransactionDetailEntity.setCreateDatetime(LocalDateTime.of(2010,03,15,00,00,00));
        salesErrorSalesTransactionDetailEntity.setCreateProgramId("1");
        salesErrorSalesTransactionDetailEntity.setUpdateUserId("1");
        salesErrorSalesTransactionDetailEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesErrorSalesTransactionDetailEntity.setUpdateProgramId("1");
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TsalesErrorSalesTransactionDetailEntityMapper_DELETE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByCondition() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("1");
        SalesErrorSalesTransactionDetailCondition salesErrorSalesTransactionDetailEntityCondition =
                new SalesErrorSalesTransactionDetailCondition();
        salesErrorSalesTransactionDetailEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());

        int result = salesErrorSalesTransactionDetailEntityMapper
                .deleteByCondition(salesErrorSalesTransactionDetailEntityCondition);
        assertThat(result, is(1));
    }

    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionDetailEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDeleteByConditionZero() {
        TransactionImportData transactionImportData = new TransactionImportData();
        transactionImportData.setSalesTransactionErrorId("5");
        SalesErrorSalesTransactionDetailCondition salesErrorSalesTransactionDetailEntityCondition =
                new SalesErrorSalesTransactionDetailCondition();
        salesErrorSalesTransactionDetailEntityCondition.createCriteria()
                .andTransactionIdEqualTo(transactionImportData.getSalesTransactionErrorId());

        int result = salesErrorSalesTransactionDetailEntityMapper
                .deleteByCondition(salesErrorSalesTransactionDetailEntityCondition);
        assertThat(result, is(0));
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionDetailEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesErrorSalesTransactionDetailEntity.setTransactionId("2");
        salesErrorSalesTransactionDetailEntity.setOrderSubNumber(2);
        salesErrorSalesTransactionDetailEntity.setSalesTransactionId("2");
        salesErrorSalesTransactionDetailEntity.setDetailSubNumber(2);
        int result=salesErrorSalesTransactionDetailEntityMapper.insertSelective(salesErrorSalesTransactionDetailEntity);
        assertEquals(result, 1);
    }
    
    @Test
    @DatabaseSetup("TSalesErrorSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesErrorSalesTransactionDetailEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesErrorSalesTransactionDetailEntityMapper.insertSelective(salesErrorSalesTransactionDetailEntity);
        assertEquals(result, 0);
    }

}
