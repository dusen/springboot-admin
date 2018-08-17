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
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetailCondition;
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
public class SalesTransactionDetailMapperTest {

    @Autowired
    private SalesTransactionDetailMapper salesTransactionDetailEntityMapper;
    
    private SalesTransactionDetail salesTransactionDetailEntity;
    
    @Before
    public void setUp() {
        salesTransactionDetailEntity=new SalesTransactionDetail();
        salesTransactionDetailEntity.setSalesTransactionId("1");
        salesTransactionDetailEntity.setOrderSubNumber(1);
        salesTransactionDetailEntity.setDetailSubNumber(1);
        salesTransactionDetailEntity.setItemDetailSubNumber(1);
        salesTransactionDetailEntity.setSalesTransactionType("1");
        salesTransactionDetailEntity.setSystemBrandCode("1");
        salesTransactionDetailEntity.setL2ItemCode("1");
        salesTransactionDetailEntity.setDisplayL2ItemCode("1");
        salesTransactionDetailEntity.setL2ProductName("1");
        salesTransactionDetailEntity.setL3ItemCode("1");
        salesTransactionDetailEntity.setL3PosProductName("1");
        salesTransactionDetailEntity.setProductClassification("1");
        salesTransactionDetailEntity.setNonMdType("1");
        salesTransactionDetailEntity.setNonMdCode("1");
        salesTransactionDetailEntity.setServiceCode("1");
        salesTransactionDetailEntity.setEpcCode("1");
        salesTransactionDetailEntity.setGDepartmentCode("1");
        salesTransactionDetailEntity.setMajorCategoryCode("1");
        salesTransactionDetailEntity.setQuantityCode("1");
        salesTransactionDetailEntity.setDetailQuantity(new BigDecimal(1));
        salesTransactionDetailEntity.setItemCostCurrencyCode("1");
        salesTransactionDetailEntity.setItemCostValue(new BigDecimal(1));
        salesTransactionDetailEntity.setInitialSellingPriceCurrencyCode("1");
        salesTransactionDetailEntity.setInitialSellingPrice(new BigDecimal(1));
        salesTransactionDetailEntity.setBclassPriceCurrencyCode("1");
        salesTransactionDetailEntity.setBclassPrice(new BigDecimal(1));
        salesTransactionDetailEntity.setNewPriceCurrencyCode("1");
        salesTransactionDetailEntity.setNewPrice(new BigDecimal(1));
        salesTransactionDetailEntity.setRetailUnitPriceTaxExcludedCurrencyCode("1");
        salesTransactionDetailEntity.setRetailUnitPriceTaxExcluded(new BigDecimal(1));
        salesTransactionDetailEntity.setRetailUnitPriceTaxIncludedCurrencyCode("1");
        salesTransactionDetailEntity.setRetailUnitPriceTaxIncluded(new BigDecimal(1));
        salesTransactionDetailEntity.setSalesAmountTaxExcludedCurrencyCode("1");
        salesTransactionDetailEntity.setSalesAmountTaxExcluded(new BigDecimal(1));
        salesTransactionDetailEntity.setSalesAmountTaxIncludedCurrencyCode("1");
        salesTransactionDetailEntity.setSalesAmountTaxIncluded(new BigDecimal(1));
        salesTransactionDetailEntity.setCalculationUnavailableType("1");
        salesTransactionDetailEntity.setOrderStatusUpdateDate("1");
        salesTransactionDetailEntity.setOrderStatusLastUpdateDateTime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesTransactionDetailEntity.setOrderStatus("1");
        salesTransactionDetailEntity.setOrderSubstatus("1");
        salesTransactionDetailEntity.setBookingStoreCode("1");
        salesTransactionDetailEntity.setBookingStoreSystemBrandCode("1");
        salesTransactionDetailEntity.setBookingStoreSystemBusinessCode("1");
        salesTransactionDetailEntity.setBookingStoreSystemCountryCode("1");
        salesTransactionDetailEntity.setShipmentStoreCode("1");
        salesTransactionDetailEntity.setShipmentStoreSystemBrandCode("1");
        salesTransactionDetailEntity.setShipmentStoreSystemBusinessCode("1");
        salesTransactionDetailEntity.setShipmentStoreSystemCountryCode("1");
        salesTransactionDetailEntity.setReceiptStoreCode("1");
        salesTransactionDetailEntity.setReceiptStoreSystemBrandCode("1");
        salesTransactionDetailEntity.setReceiptStoreSystemBusinessCode("1");
        salesTransactionDetailEntity.setReceiptStoreSystemCountryCode("1");
        salesTransactionDetailEntity.setContributionSalesRepresentative("1");
        salesTransactionDetailEntity.setCustomerId("1");
        salesTransactionDetailEntity.setBundlePurchaseApplicableQuantity(new BigDecimal(1));
        salesTransactionDetailEntity.setBundlePurchaseApplicablePriceCurrencyCode("1");
        salesTransactionDetailEntity.setBundlePurchaseApplicablePrice(new BigDecimal(1));
        salesTransactionDetailEntity.setBundlePurchaseIndex(1);
        salesTransactionDetailEntity.setLimitedAmountPromotionCount(1);
        salesTransactionDetailEntity.setStoreItemDiscountType("1");
        salesTransactionDetailEntity.setStoreItemDiscountCurrencyCode("1");
        salesTransactionDetailEntity.setStoreItemDiscountSetting(new BigDecimal(1));
        salesTransactionDetailEntity.setStoreBundleSaleFlag(true);
        salesTransactionDetailEntity.setStoreBundleSalePriceCurrencyCode("1");
        salesTransactionDetailEntity.setStoreBundleSalePrice(new BigDecimal(1));
        salesTransactionDetailEntity.setSetSalesDetailIndex(1);
        salesTransactionDetailEntity.setTaxationType("1");
        salesTransactionDetailEntity.setTaxSystemType("1");
        salesTransactionDetailEntity.setCreateUserId("1");
        salesTransactionDetailEntity.setCreateDatetime(LocalDateTime.of(2010, 03,15,00,00,00));
        salesTransactionDetailEntity.setCreateProgramId("1");
        salesTransactionDetailEntity.setUpdateUserId("1");
        salesTransactionDetailEntity.setUpdateDatetime(LocalDateTime.of(2018, 03,15,00,00,00));
        salesTransactionDetailEntity.setUpdateProgramId("1");
    }


    @Test
    @DatabaseSetup("TSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionDetailEntityMapper_UPDATE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdateByConditionUpdateSuccess() {

        SalesTransactionDetailCondition tsalesTransactionDetailEntityCondition =
                new SalesTransactionDetailCondition();

        tsalesTransactionDetailEntityCondition.createCriteria()
                .andOrderSubNumberEqualTo(1)
                .andSalesTransactionIdLike("1")
                .andDetailSubNumberEqualTo(1);

        NonItemDetail nonItemDetail = new NonItemDetail();
        nonItemDetail.setNonItemTaxationType("1");
        nonItemDetail.setNonItemTaxKind("1");

        int result = salesTransactionDetailEntityMapper.updateByConditionSelective(
                salesTransactionDetailEntity, tsalesTransactionDetailEntityCondition);
        assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionDetailEntityMapper_UPDATEFailed.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdateByConditionUpdateFailed() {

        SalesTransactionDetailCondition tsalesTransactionDetailEntityCondition =
                new SalesTransactionDetailCondition();

        tsalesTransactionDetailEntityCondition.createCriteria()
                .andOrderSubNumberEqualTo(1)
                .andSalesTransactionIdLike("2")
                .andDetailSubNumberEqualTo(2);

        NonItemDetail nonItemDetail = new NonItemDetail();
        nonItemDetail.setNonItemTaxationType("1");
        nonItemDetail.setNonItemTaxKind("1");

        int result = salesTransactionDetailEntityMapper.updateByConditionSelective(
                salesTransactionDetailEntity, tsalesTransactionDetailEntityCondition);
        assertEquals(0, result);
    }
    

    @Test
    @DatabaseSetup("TSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionDetailEntityMapper_UPDATE.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdateByConditionSelectiveUpdateSuccess() {

        SalesTransactionDetailCondition tsalesTransactionDetailEntityCondition =
                new SalesTransactionDetailCondition();

        tsalesTransactionDetailEntityCondition.createCriteria()
                .andOrderSubNumberEqualTo(1)
                .andSalesTransactionIdLike("1")
                .andDetailSubNumberEqualTo(1);

        NonItemDetail nonItemDetail = new NonItemDetail();
        nonItemDetail.setNonItemTaxationType("1");
        nonItemDetail.setNonItemTaxKind("1");

        int result = salesTransactionDetailEntityMapper.updateByConditionSelective(
                salesTransactionDetailEntity, tsalesTransactionDetailEntityCondition);
        assertEquals(1, result);
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionDetailEntityMapper_UPDATEFailed.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdateByConditionSelectiveUpdateFailed() {

        SalesTransactionDetailCondition tsalesTransactionDetailEntityCondition =
                new SalesTransactionDetailCondition();

        tsalesTransactionDetailEntityCondition.createCriteria()
                .andOrderSubNumberEqualTo(1)
                .andSalesTransactionIdLike("2")
                .andDetailSubNumberEqualTo(2);

        NonItemDetail nonItemDetail = new NonItemDetail();
        nonItemDetail.setNonItemTaxationType("1");
        nonItemDetail.setNonItemTaxKind("1");

        int result = salesTransactionDetailEntityMapper.updateByConditionSelective(
                salesTransactionDetailEntity, tsalesTransactionDetailEntityCondition);
        assertEquals(0, result);
    }
    
    @Test
    @DatabaseSetup("TSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionDetailEntityMapper_INSERT.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveSuccess() {
        salesTransactionDetailEntity.setOrderSubNumber(2);
        salesTransactionDetailEntity.setSalesTransactionId("2");
        salesTransactionDetailEntity.setDetailSubNumber(2);
        int result=salesTransactionDetailEntityMapper.insertSelective(salesTransactionDetailEntity);
        assertEquals(1, result);
        
    }
    
    
    @Test
    @DatabaseSetup("TSalesTransactionDetailEntityMapperTest.xml")
    @ExpectedDatabase(value = "TSalesTransactionDetailEntityMapperTest.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertSelectiveFailed() {
        int result=salesTransactionDetailEntityMapper.insertSelective(salesTransactionDetailEntity);
        assertEquals(0, result);
        
    }

}
