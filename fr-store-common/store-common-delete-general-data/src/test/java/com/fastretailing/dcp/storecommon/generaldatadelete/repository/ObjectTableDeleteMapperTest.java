package com.fastretailing.dcp.storecommon.generaldatadelete.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.time.OffsetDateTime;
import java.util.TimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.storecommon.generaldatadelete.dto.GeneralDataDeleteEntity;
import com.fastretailing.dcp.storecommon.util.DateUtility;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Unit test of objectTableDeleteMapper class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_common.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/create-tables.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup-tables.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_common.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ObjectTableDeleteMapperTest {

    /** DB access class. */
    @Autowired
    ObjectTableDeleteMapper objectTableDeleteMapper;

    /** DB access class. */
    @Autowired
    GeneralDataDeleteConfigMapper generalDataDeleteConfigMapper;

    private GeneralDataDeleteEntity generalDataDelete;

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /**
     * The preHandle method. Perform initial setting of item information.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Before
    public void setUp() throws Exception {

        generalDataDelete = new GeneralDataDeleteEntity();
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:dateItem is less than localDateTime minus retainDays.
     * <LI>Verification result confirmation:The acquired data matches the expected. value.
     * </UL>
     * 
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTest_DELETESUCCESS.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterData() {
        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setProcessingTargetType("COMMON");
        generalDataDelete.setSavedDays(365L);
        generalDataDelete.setCreateDatetime(
                OffsetDateTime.of(2015, 12, 12, 00, 00, 00, 0, OffsetDateTime.now().getOffset()));
        generalDataDelete.setDateItem("date_time");

        generalDataDelete.setDeleteTargetTable("del_config");
        generalDataDelete.setProcessingNo(1L);
        generalDataDelete.setSystemCountryCode("01");
        generalDataDelete.setSystemBrandCode("01");
        generalDataDelete.setStoreCode("01");
        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);

        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(1));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:dateItem is greater than localDateTime minus retainDays.
     * <LI>Verification result confirmation:The acquired data matches the expected. value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param platformManagement.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestMorelnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTestMorelnit.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataMore() {
        generalDataDelete.setSavedDays(365L);
        generalDataDelete.setDateItem("date_time");

        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);

        generalDataDelete.setDeleteTargetTable("del_config");
        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(0));
    }


    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:systemBrandCode is not exist.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTest_DELETESUCCESS.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingNull_systemBrandCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setProcessingNo(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setSystemBrandCode(null);
        generalDataDelete.setStoreCode("01");
        generalDataDelete.setSystemCountryCode("01");
        generalDataDelete.setDeleteTargetTable("del_config");

        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);

        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(1));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:systemBrandCode is not exist.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTest_DELETESUCCESS.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingN0_systemBrandCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setProcessingNo(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setSystemBrandCode("");
        generalDataDelete.setStoreCode("01");
        generalDataDelete.setSystemCountryCode("01");
        generalDataDelete.setDeleteTargetTable("del_config");

        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);

        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(1));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:systemBrandCode is equel.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTest_DELETESUCCESS.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingEqual_systemBrandCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setProcessingNo(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setSystemBrandCode("01");
        generalDataDelete.setDeleteTargetTable("del_config");

        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);
        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(1));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:systemBrandCode is not equel.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTestlnit.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingNotEquel_systemBrandCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setSystemBrandCode("02");
        generalDataDelete.setDeleteTargetTable("del_config");

        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);
        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(0));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:systemCountryCode is not exist.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTest_DELETESUCCESS.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingNull_systemCountryCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setSystemCountryCode(null);
        generalDataDelete.setStoreCode("01");
        generalDataDelete.setSystemBrandCode("01");
        generalDataDelete.setDeleteTargetTable("del_config");

        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);
        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(1));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:systemCountryCode is not exist.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTest_DELETESUCCESS.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingNo_systemCountryCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setSystemCountryCode("");
        generalDataDelete.setStoreCode("01");
        generalDataDelete.setSystemBrandCode("01");
        generalDataDelete.setDeleteTargetTable("del_config");

        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);
        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(1));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:systemCountryCode is equel.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTest_DELETESUCCESS.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingEqual_systemCountryCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setSystemCountryCode("01");
        generalDataDelete.setDeleteTargetTable("del_config");

        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);
        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(1));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:systemCountryCode is not equel.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTestlnit.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingNotEquel_systemCountryCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setSystemCountryCode("02");
        generalDataDelete.setDeleteTargetTable("del_config");
        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);
        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(0));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:storeCode is not exist.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTest_DELETESUCCESS.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingNull_storeCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setStoreCode(null);
        generalDataDelete.setSystemBrandCode("01");
        generalDataDelete.setSystemCountryCode("01");
        generalDataDelete.setDeleteTargetTable("del_config");
        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);
        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(1));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:storeCode is not exist.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTest_DELETESUCCESS.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingNo_storeCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setStoreCode("");
        generalDataDelete.setSystemBrandCode("01");
        generalDataDelete.setSystemCountryCode("01");
        generalDataDelete.setDeleteTargetTable("del_config");
        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);
        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(1));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:storeCode is equel.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTest_DELETESUCCESS.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingEqual_storeCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setStoreCode("01");
        generalDataDelete.setDeleteTargetTable("del_config");

        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);
        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(1));
    }

    /**
     * <UL>
     * <LI>Target method:delete.
     * <LI>Condition:storeCode is not equel.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @param generalDataDelete.
     * @param limitedDate.
     */
    @DatabaseSetup("ObjectTableDeleteMapperTestlnit.xml")
    @ExpectedDatabase(value = "ObjectTableDeleteMapperTestlnit.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void testDeleteMasterDataProcessingNotEquel_storeCode() {

        generalDataDelete = new GeneralDataDeleteEntity();
        generalDataDelete.setSavedDays(1L);
        generalDataDelete.setDateItem("date_time");
        generalDataDelete.setStoreCode("02");
        generalDataDelete.setDeleteTargetTable("del_config");

        String limitedDate = DateUtility.formatDateTime(
                DateUtility.getZonedDateTimeUtc().minusDays(generalDataDelete.getSavedDays()),
                DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);
        int result = objectTableDeleteMapper.delete(generalDataDelete, limitedDate);
        assertThat(result, is(0));
    }

}
