package com.fastretailing.dcp.sales.salespayoffdataupdate.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffDataUpdateErrorStatusOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffDataUpdateOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffDataUpdateOptionalEntity;
import com.fastretailing.dcp.sales.common.repository.optional.SalesPayoffDataUpdateOptionalMapper;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.SalesRestTemplateRepository;
import com.fastretailing.dcp.sales.salespayoffdataupdate.form.SalesPayoffDataUpdateForm;
import com.fastretailing.dcp.sales.salespayoffdataupdate.form.SalesPayoffDataUpdateListForm;
import com.fastretailing.dcp.storecommon.ResultCode;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Unit test of SalesPayoffDataUpdateServiceTest class.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_sales.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class SalesPayoffDataUpdateServiceTest {

    /** Service class. */
    @SpyBean
    private SalesPayoffDataUpdateService salesPayoffDataUpdateService;

    /** Create a mock of access API. */
    @MockBean
    private SalesRestTemplateRepository salesRestTemplateRepository;

    /** Create a mock of access DB for common master. */
    @MockBean
    private SalesPayoffDataUpdateOptionalMapper salesPayoffDataUpdateOptionalMapper;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetInitializeInformationNormal() {
        SalesPayoffDataUpdateForm salesPayoffDataUpdateForm = new SalesPayoffDataUpdateForm();

        salesPayoffDataUpdateForm.setSystemBrandCode("systembrandcode");
        salesPayoffDataUpdateForm.setSystemCountryCode("systemcountrycode");
        salesPayoffDataUpdateForm.setStoreCode("222");
        salesPayoffDataUpdateForm.setPayoffDate("2018/05/12");
        salesPayoffDataUpdateForm.setCashRegisterNumber("134");
        salesPayoffDataUpdateForm.setIntegrityCheckType("1");
        salesPayoffDataUpdateForm.setBrandName("bn");
        salesPayoffDataUpdateForm.setCountryName("cn");
        salesPayoffDataUpdateForm.setStoreName("storename");
        salesPayoffDataUpdateForm.setErrorContent("errorContent");

        SalesPayoffDataUpdateErrorStatusOptionalCondition salesPayoffDataUpdateErrorStatusOptionalContidion =
                new SalesPayoffDataUpdateErrorStatusOptionalCondition();
        salesPayoffDataUpdateErrorStatusOptionalContidion
                .setStoreCode(salesPayoffDataUpdateForm.getStoreCode());
        salesPayoffDataUpdateErrorStatusOptionalContidion
                .setSystemBrandCode(salesPayoffDataUpdateForm.getBrandName());
        salesPayoffDataUpdateErrorStatusOptionalContidion
                .setSystemCountryCode(salesPayoffDataUpdateForm.getCountryName());
        salesPayoffDataUpdateErrorStatusOptionalContidion.setCashRegisterNo(
                Integer.valueOf(salesPayoffDataUpdateForm.getCashRegisterNumber()));
        /**
         * Mock for DB access.
         */
        when(salesPayoffDataUpdateOptionalMapper.selectSalesPayoffDataUpdateErrorStatus(
                salesPayoffDataUpdateErrorStatusOptionalContidion)).thenReturn(0);

        SalesPayoffDataUpdateOptionalCondition salesPayoffDataUpdateOptionalContidion =
                new SalesPayoffDataUpdateOptionalCondition();
        salesPayoffDataUpdateOptionalContidion
                .setStoreCode(salesPayoffDataUpdateForm.getStoreCode());
        salesPayoffDataUpdateOptionalContidion.setPayoffDate("20180512");
        salesPayoffDataUpdateOptionalContidion.setCashRegisterNo(
                Integer.valueOf(salesPayoffDataUpdateForm.getCashRegisterNumber()));

        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity1 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity1.setPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity1.setPileUpPayoffAmount(new BigDecimal(2));
        salesPayoffDataUpdateOptionalEntity1.setPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity1.setPileUpPayoffQuantity(new BigDecimal(3));
        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity2 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity2.setPayoffAmount(new BigDecimal(2));
        salesPayoffDataUpdateOptionalEntity2.setPileUpPayoffAmount(new BigDecimal(2));
        salesPayoffDataUpdateOptionalEntity2.setPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity2.setPileUpPayoffQuantity(new BigDecimal(4));
        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity3 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity3.setPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPileUpPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPayoffQuantity(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPileUpPayoffQuantity(new BigDecimal(1));
        List<SalesPayoffDataUpdateOptionalEntity> salesPayoffDataList = new ArrayList<>();
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity1);
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity2);
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity3);

        when(salesPayoffDataUpdateOptionalMapper
                .selectSalesPayoffDataUpdateList(salesPayoffDataUpdateOptionalContidion))
                        .thenReturn(salesPayoffDataList);
        /**
         * Method execution.
         */

        SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm =
                salesPayoffDataUpdateService.getInitializeInformation(salesPayoffDataUpdateForm);
        assertEquals("1", salesPayoffDataUpdateListForm.getSalesPayoffDataListHaveDataFlag());
        assertEquals("Normal", salesPayoffDataUpdateListForm.getSalesTransactionErrorStatus());
        List<SalesPayoffDataUpdateOptionalEntity> salesPayoffDataList2 =
                salesPayoffDataUpdateListForm.getSalesPayoffDataList();
        assertEquals("1", salesPayoffDataList2.get(0).getPileUpPayoffDataSameDecideFlag());
        assertEquals("1", salesPayoffDataList2.get(1).getPileUpPayoffDataSameDecideFlag());
        assertEquals(null, salesPayoffDataList2.get(2).getPileUpPayoffDataSameDecideFlag());
    }

    @Test
    public void testGetInitializeInformationErrorExists() {
        SalesPayoffDataUpdateForm salesPayoffDataUpdateForm = new SalesPayoffDataUpdateForm();

        salesPayoffDataUpdateForm.setSystemBrandCode("systembrandcode");
        salesPayoffDataUpdateForm.setSystemCountryCode("systemcountrycode");
        salesPayoffDataUpdateForm.setStoreCode("222");
        salesPayoffDataUpdateForm.setPayoffDate("2018/05/12");
        salesPayoffDataUpdateForm.setCashRegisterNumber("134");
        salesPayoffDataUpdateForm.setIntegrityCheckType("1");
        salesPayoffDataUpdateForm.setBrandName("bn");
        salesPayoffDataUpdateForm.setCountryName("cn");
        salesPayoffDataUpdateForm.setStoreName("storename");
        salesPayoffDataUpdateForm.setErrorContent("errorContent");

        SalesPayoffDataUpdateErrorStatusOptionalCondition salesPayoffDataUpdateErrorStatusOptionalContidion =
                new SalesPayoffDataUpdateErrorStatusOptionalCondition();
        salesPayoffDataUpdateErrorStatusOptionalContidion
                .setStoreCode(salesPayoffDataUpdateForm.getStoreCode());
        salesPayoffDataUpdateErrorStatusOptionalContidion
                .setSystemBrandCode(salesPayoffDataUpdateForm.getBrandName());
        salesPayoffDataUpdateErrorStatusOptionalContidion
                .setSystemCountryCode(salesPayoffDataUpdateForm.getCountryName());
        salesPayoffDataUpdateErrorStatusOptionalContidion.setCashRegisterNo(
                Integer.valueOf(salesPayoffDataUpdateForm.getCashRegisterNumber()));
        /**
         * Mock for DB access.
         */
        when(salesPayoffDataUpdateOptionalMapper.selectSalesPayoffDataUpdateErrorStatus(
                salesPayoffDataUpdateErrorStatusOptionalContidion)).thenReturn(1);

        SalesPayoffDataUpdateOptionalCondition salesPayoffDataUpdateOptionalContidion =
                new SalesPayoffDataUpdateOptionalCondition();
        salesPayoffDataUpdateOptionalContidion
                .setStoreCode(salesPayoffDataUpdateForm.getStoreCode());
        salesPayoffDataUpdateOptionalContidion
                .setPayoffDate(salesPayoffDataUpdateForm.getPayoffDate());
        salesPayoffDataUpdateOptionalContidion.setCashRegisterNo(
                Integer.valueOf(salesPayoffDataUpdateForm.getCashRegisterNumber()));

        List<SalesPayoffDataUpdateOptionalEntity> salesPayoffDataList = new ArrayList<>();

        when(salesPayoffDataUpdateOptionalMapper
                .selectSalesPayoffDataUpdateList(salesPayoffDataUpdateOptionalContidion))
                        .thenReturn(salesPayoffDataList);

        /**
         * Method execution.
         */
        SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm =
                salesPayoffDataUpdateService.getInitializeInformation(salesPayoffDataUpdateForm);
        assertEquals("0", salesPayoffDataUpdateListForm.getSalesPayoffDataListHaveDataFlag());
        assertEquals("Error Exists",
                salesPayoffDataUpdateListForm.getSalesTransactionErrorStatus());
    }

    @Test
    public void testDoUpdateCompareCheck() {
        SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm =
                new SalesPayoffDataUpdateListForm();

        salesPayoffDataUpdateListForm.setSystemBrandCode("systembrandcode");
        salesPayoffDataUpdateListForm.setSystemCountryCode("systemcountrycode");
        salesPayoffDataUpdateListForm.setStoreCode("222");
        salesPayoffDataUpdateListForm.setPayoffDate("2018/05/12");
        salesPayoffDataUpdateListForm.setCashRegisterNumber("134");
        salesPayoffDataUpdateListForm.setIntegrityCheckType("1");
        salesPayoffDataUpdateListForm.setBrandName("bn");
        salesPayoffDataUpdateListForm.setCountryName("cn");
        salesPayoffDataUpdateListForm.setStoreName("storename");
        salesPayoffDataUpdateListForm.setErrorContent("errorContent");


        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity1 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity1.setPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity1.setPileUpPayoffAmount(new BigDecimal(2));
        salesPayoffDataUpdateOptionalEntity1.setPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity1.setPileUpPayoffQuantity(new BigDecimal(3));
        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity2 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity2.setPayoffAmount(new BigDecimal(2));
        salesPayoffDataUpdateOptionalEntity2.setPileUpPayoffAmount(new BigDecimal(2));
        salesPayoffDataUpdateOptionalEntity2.setPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity2.setPileUpPayoffQuantity(new BigDecimal(4));
        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity3 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity3.setPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPileUpPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPayoffQuantity(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPileUpPayoffQuantity(new BigDecimal(1));
        List<SalesPayoffDataUpdateOptionalEntity> salesPayoffDataList = new ArrayList<>();
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity1);
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity2);
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity3);

        salesPayoffDataUpdateListForm.setSalesPayoffDataList(salesPayoffDataList);

        /**
         * Method execution.
         */

        SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm2 =
                salesPayoffDataUpdateService.updatePayoffData(salesPayoffDataUpdateListForm);
        assertEquals("Input of Payoff Amount of null of payoff type is inconsistent."
                + "<br>Input of Payoff Quantity " + "of null of payoff type is inconsistent.<br>",
                salesPayoffDataUpdateListForm2.getDetailError().getErrorMessage());

        List<SalesPayoffDataUpdateOptionalEntity> salesPayoffDataList2 =
                salesPayoffDataUpdateListForm2.getSalesPayoffDataList();
        assertEquals("1", salesPayoffDataList2.get(0).getPileUpPayoffDataSameDecideFlag());
        assertEquals("1", salesPayoffDataList2.get(1).getPileUpPayoffDataSameDecideFlag());
        assertEquals("0", salesPayoffDataList2.get(2).getPileUpPayoffDataSameDecideFlag());
    }

    @Test
    @DatabaseSetup("BusinessCountryStateSetting.xml")
    public void testDoUpdateBalanceIsInconsistent() {
        SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm =
                new SalesPayoffDataUpdateListForm();

        salesPayoffDataUpdateListForm.setSystemBrandCode("systembrandcode");
        salesPayoffDataUpdateListForm.setSystemCountryCode("systemcountrycode");
        salesPayoffDataUpdateListForm.setStoreCode("222");
        salesPayoffDataUpdateListForm.setPayoffDate("2018/05/12");
        salesPayoffDataUpdateListForm.setCashRegisterNumber("134");
        salesPayoffDataUpdateListForm.setIntegrityCheckType("1");
        salesPayoffDataUpdateListForm.setBrandName("bn");
        salesPayoffDataUpdateListForm.setCountryName("cn");
        salesPayoffDataUpdateListForm.setStoreName("storename");
        salesPayoffDataUpdateListForm.setErrorContent("errorContent");


        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity1 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity1.setPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity1.setPileUpPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity1.setPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity1.setPileUpPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity1.setPayoffTypeCode("1111");
        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity2 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity2.setPayoffAmount(new BigDecimal(2));
        salesPayoffDataUpdateOptionalEntity2.setPileUpPayoffAmount(new BigDecimal(2));
        salesPayoffDataUpdateOptionalEntity2.setPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity2.setPileUpPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity2.setPayoffTypeCode("2222");
        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity3 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity3.setPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPileUpPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPayoffQuantity(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPileUpPayoffQuantity(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPayoffTypeCode("3333");
        List<SalesPayoffDataUpdateOptionalEntity> salesPayoffDataList = new ArrayList<>();
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity1);
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity2);
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity3);

        salesPayoffDataUpdateListForm.setSalesPayoffDataList(salesPayoffDataList);

        CommonStatus commonstatus = new CommonStatus();
        commonstatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callAlterationPayOffData(anyObject()))
                .thenReturn(commonstatus);

        /**
         * Method execution.
         */
        try {
            SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm2 =
                    salesPayoffDataUpdateService.updatePayoffData(salesPayoffDataUpdateListForm);
            salesPayoffDataUpdateListForm2.setPayoffDate("2018/05/12");
        } catch (BusinessException e) {
            assertEquals("E_SLS_66000186", e.getResultObject().getDebugId());
        }
        // 仕様のロジックで、このケースが実行できません
        // assertEquals("Balance is inconsistent",
        // salesPayoffDataUpdateListForm2.getDetailError().getErrorMessage());

    }

    @Test
    @DatabaseSetup("UpdatePayoffData.xml")
    @ExpectedDatabase(value = "ExpectedUpdatePayoffData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDoUpdateNormal() {
        SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm =
                new SalesPayoffDataUpdateListForm();

        salesPayoffDataUpdateListForm.setSystemBrandCode("systembrandcode");
        salesPayoffDataUpdateListForm.setSystemCountryCode("systemcountrycode");
        salesPayoffDataUpdateListForm.setStoreCode("222");
        salesPayoffDataUpdateListForm.setPayoffDate("2018/05/12");
        salesPayoffDataUpdateListForm.setCashRegisterNumber("134");
        salesPayoffDataUpdateListForm.setIntegrityCheckType("1");
        salesPayoffDataUpdateListForm.setBrandName("bn");
        salesPayoffDataUpdateListForm.setCountryName("cn");
        salesPayoffDataUpdateListForm.setStoreName("storename");
        salesPayoffDataUpdateListForm.setErrorContent("errorContent");


        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity1 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity1.setPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity1.setPileUpPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity1.setPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity1.setPileUpPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity1.setPayoffTypeCode("1111");
        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity2 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity2.setPayoffAmount(new BigDecimal(2));
        salesPayoffDataUpdateOptionalEntity2.setPileUpPayoffAmount(new BigDecimal(2));
        salesPayoffDataUpdateOptionalEntity2.setPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity2.setPileUpPayoffQuantity(new BigDecimal(3));
        salesPayoffDataUpdateOptionalEntity2.setPayoffTypeCode("2222");
        SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity3 =
                new SalesPayoffDataUpdateOptionalEntity();
        salesPayoffDataUpdateOptionalEntity3.setPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPileUpPayoffAmount(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPayoffQuantity(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPileUpPayoffQuantity(new BigDecimal(1));
        salesPayoffDataUpdateOptionalEntity3.setPayoffTypeCode("3333");
        List<SalesPayoffDataUpdateOptionalEntity> salesPayoffDataList = new ArrayList<>();
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity1);
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity2);
        salesPayoffDataList.add(salesPayoffDataUpdateOptionalEntity3);

        salesPayoffDataUpdateListForm.setSalesPayoffDataList(salesPayoffDataList);

        CommonStatus commonstatus = new CommonStatus();
        commonstatus.setResultCode(ResultCode.NORMAL);
        when(salesRestTemplateRepository.callAlterationPayOffData(anyObject()))
                .thenReturn(commonstatus);

        /**
         * Method execution.
         */
        try {
            salesPayoffDataUpdateService.updatePayoffData(salesPayoffDataUpdateListForm);
        } catch (BusinessException e) {
            assertEquals("E_SLS_66000186", e.getResultObject().getDebugId());
        }
    }
}
