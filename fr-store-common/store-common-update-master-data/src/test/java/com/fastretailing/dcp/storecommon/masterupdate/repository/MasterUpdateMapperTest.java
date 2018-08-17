/**
 * @(#)MasterUpdateMapperTest.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.storecommon.masterupdate.dto.GeneralMasterRequest;
import com.fastretailing.dcp.storecommon.util.DateUtility;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * JUnit test class of master data mapper .
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@SqlGroup({
        @Sql(scripts = "/junit_create_table_sales_3.sql",
                executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/junit_create_view.sql",
                executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/junit_drop_view.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(scripts = "/junit_drop_table_sales_3.sql",
                executionPhase = ExecutionPhase.AFTER_TEST_METHOD)})
public class MasterUpdateMapperTest {

    /** Db access class. */
    @Autowired
    private MasterUpdateMapper masterUpdateMapper;

    /** Request information of general master request. */
    private GeneralMasterRequest information;

    /** Data map for update. */
    Map<String, Object> dataMap;

    /**
     * Initial request parameter information.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {

        /* Initial request information. */
        information = new GeneralMasterRequest();
        information.setLotNumber("A001");
        String updateItems = "system_brand_code,system_country_code," + "code_type,code,code_name,"
                + "code_num,create_user_id,create_datetime," + "create_program_id,update_user_id,"
                + "update_datetime,update_program_id";

        List<List<Map<String, Object>>> updateDataListList = new ArrayList<>();

        // Add system brand code data list.
        List<Map<String, Object>> systemBrandCodeList = new ArrayList<>();
        Map<String, Object> systemBrandCodeMap = new HashMap<>();
        systemBrandCodeMap.put("type", "character varying");
        systemBrandCodeMap.put("value", "1");
        systemBrandCodeList.add(systemBrandCodeMap);
        updateDataListList.add(systemBrandCodeList);

        // Add system country code data list.
        List<Map<String, Object>> systemCountryCodeList = new ArrayList<>();
        Map<String, Object> systemCountryCodeMap = new HashMap<>();
        systemCountryCodeMap.put("type", "character varying");
        systemCountryCodeMap.put("value", "1");
        systemCountryCodeList.add(systemCountryCodeMap);
        updateDataListList.add(systemCountryCodeList);

        // Add code type data list.
        List<Map<String, Object>> codeTypeList = new ArrayList<>();
        Map<String, Object> codeTypeMap = new HashMap<>();
        codeTypeMap.put("type", "character varying");
        codeTypeMap.put("value", "1");
        codeTypeList.add(codeTypeMap);
        updateDataListList.add(codeTypeList);

        // Add code data list.
        List<Map<String, Object>> codeList = new ArrayList<>();
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("type", "character varying");
        codeMap.put("value", "1");
        codeList.add(codeMap);
        updateDataListList.add(codeList);

        // Add code name data list.
        List<Map<String, Object>> codeNameList = new ArrayList<>();
        Map<String, Object> codeNameMap = new HashMap<>();
        codeNameMap.put("type", "character varying");
        codeNameMap.put("value", "AAA");
        codeNameList.add(codeNameMap);
        updateDataListList.add(codeNameList);

        // Add code number data list.
        List<Map<String, Object>> codeNumList = new ArrayList<>();
        Map<String, Object> codeNumMap = new HashMap<>();
        codeNumMap.put("type", "numeric varying");
        codeNumMap.put("value", 1);
        codeNumList.add(codeNumMap);
        updateDataListList.add(codeNumList);

        // Add create user id data list.
        List<Map<String, Object>> createUserIdList = new ArrayList<>();
        Map<String, Object> createUserIdMap = new HashMap<>();
        createUserIdMap.put("type", "character varying");
        createUserIdMap.put("value", "NEC001");
        createUserIdList.add(createUserIdMap);
        updateDataListList.add(createUserIdList);

        // Get current time for create common data list.
        LocalDateTime utcBusinessDateTime = DateUtility.changeTimeZone(LocalDateTime.now(),
                TimeZone.getDefault(), TimeZone.getTimeZone("UTC"));

        // Add create date time data list.
        List<Map<String, Object>> createDatetimeList = new ArrayList<>();
        Map<String, Object> createDatetimeMap = new HashMap<>();
        createDatetimeMap.put("type", "timestamp with time zone");
        createDatetimeMap.put("value", utcBusinessDateTime);
        createDatetimeList.add(createDatetimeMap);
        updateDataListList.add(createDatetimeList);

        // Add create program id data list.
        List<Map<String, Object>> createProgramIdList = new ArrayList<>();
        Map<String, Object> createProgramIdMap = new HashMap<>();
        createProgramIdMap.put("type", "character varying");
        createProgramIdMap.put("value", "CMN9900101");
        createProgramIdList.add(createProgramIdMap);
        updateDataListList.add(createProgramIdList);

        // Add update user id data list.
        List<Map<String, Object>> updateUserIdList = new ArrayList<>();
        Map<String, Object> updateUserIdMap = new HashMap<>();
        updateUserIdMap.put("type", "character varying");
        updateUserIdMap.put("value", "NEC001");
        updateUserIdList.add(updateUserIdMap);
        updateDataListList.add(updateUserIdList);

        // Add update datetime data list.
        List<Map<String, Object>> updateDatetimeList = new ArrayList<>();
        Map<String, Object> updateDatetimeMap = new HashMap<>();
        updateDatetimeMap.put("type", "timestamp with time zone");
        updateDatetimeMap.put("value", utcBusinessDateTime);
        updateDatetimeList.add(updateDatetimeMap);
        updateDataListList.add(updateDatetimeList);

        // Add update program id data list.
        List<Map<String, Object>> updateProgramIdList = new ArrayList<>();
        Map<String, Object> updateProgramIdMap = new HashMap<>();
        updateProgramIdMap.put("type", "character varying");
        updateProgramIdMap.put("value", "CMN9900101");
        updateProgramIdList.add(updateProgramIdMap);
        updateDataListList.add(updateProgramIdList);

        dataMap = new HashMap<>();
        dataMap.put("tableName", "m_type");
        dataMap.put("constraintName", "m_type_pk");
        List<String> columnList = Arrays.asList(updateItems.split(","));
        dataMap.put("updateColumnList", columnList);
        dataMap.put("updateDataInfoList", updateDataListList);
    }

    /**
     * Target method:MasterUpdateMapper#countTableName<BR>
     * Condition: Setting table name that not existed in data base. Expected results： The count is
     * zero.
     *
     * @throws Exception Exception.
     */
    @Test
    public void testCountTableNotExist() throws Exception {
        String tableName = "m_not_exist";

        // Count data from DB.
        int count = masterUpdateMapper.countTableName(tableName);

        // Confirm test result.
        assertThat(count, is(0));
    }

    /**
     * Target method:MasterUpdateMapper#countTableName<BR>
     * Condition: Setting table name that existed in data base. Expected results： Returns 1.
     *
     * @throws Exception Exception.
     */
    @Test
    public void testCountTableExist() throws Exception {
        String tableName = "m_type";

        // Count data from DB.
        int count = masterUpdateMapper.countTableName(tableName);

        // Confirm test result.
        assertThat(count, is(1));
    }

    /**
     * Target method:MasterUpdateMapper#getColumnType<BR>
     * Condition: Setting table name and column Name. Expected results： Returns varchar.
     *
     * @throws Exception Exception.
     */
    @Test
    public void testGetColumnType() throws Exception {
        String tableName = "w_type";
        String columnName = "system_country_code";
        // Delete data from DB.
        String type = masterUpdateMapper.getColumnType(tableName, columnName);

        // Confirm test result.
        assertThat(type, is("character varying"));
    }

    /**
     * Target method:MasterUpdateMapper#countByLotNumber<BR>
     * Condition: The Parameter eai type did pass the value. Expected results ： Return 0.
     *
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("MasterUpdateMapperTest_TestTypeIf.xml")
    public void testGetInputDataCountWithEai() throws Exception {
        String inputTblName = "w_type";

        // Count data by lot number from DB.
        int count =
                masterUpdateMapper.countByLotNumber(inputTblName, information.getLotNumber(), "1");

        // Confirm test result.
        assertThat(count, is(1));
    }

    /**
     * Target method:MasterUpdateMapper#countByLotNumber<BR>
     * Condition: The Parameter eai type did pass the value. Expected results ： Return 0.
     *
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("MasterUpdateMapperTest_TestWithoutEaiType.xml")
    public void testGetInputDataCountWithoutEai() throws Exception {
        String inputTblName = "w_type";

        // Count data by lot number from DB.
        int count =
                masterUpdateMapper.countByLotNumber(inputTblName, information.getLotNumber(), null);

        // Confirm test result.
        assertThat(count, is(1));
    }

    /**
     * Target method:MasterUpdateMapper#countEaiUpdateTypeOthers<BR>
     * Condition: The table name and lot number. Expected results ： Return record count.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("MasterUpdateMapperTest_TestTypeIf.xml")
    public void testGetOtherEaiExist() throws Exception {
        String inputTblName = "w_type";

        // Count data by lot number from DB.
        int count = masterUpdateMapper.countEaiUpdateTypeOthers(inputTblName,
                information.getLotNumber());

        // Confirm test result.
        assertThat(count, is(1));
    }

    /**
     * Target method:MasterUpdateMapper#countEaiUpdateTypeOthers<BR>
     * Condition: The result is data count of input table. Expected results ： Return record count.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("MasterUpdateMapperTest_TestOtherEaiTypeNotExist.xml")
    public void testGetOtherEaiNotExist() throws Exception {
        String inputTblName = "w_type";

        // Count data by lot number from DB.
        int count = masterUpdateMapper.countEaiUpdateTypeOthers(inputTblName,
                information.getLotNumber());

        // Confirm test result.
        assertThat(count, is(0));
    }

    /**
     * Target method:MasterUpdateMapper#delete<BR>
     * Condition: Setting update target table name. Expected results ： Delete all data of update
     * target table.
     *
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("MasterUpdateMapperTest_TestWithoutEaiType.xml")
    public void testDeleteBeforeUpdate() throws Exception {
        String inputTblName = "w_type";

        // Delete data from DB.
        int result = masterUpdateMapper.delete(inputTblName);

        // Confirm test result.
        assertThat(result, is(1));
    }

    /**
     * Target method:MasterUpdateMapper#getInputData<BR>
     * Condition: The result is data count of update target table. Expected results ：Return size of
     * list. record.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("MasterUpdateMapperTest_TestTypeIf.xml")
    public void testGetInputData() throws Exception {
        String tableName = "w_type";
        String columnName = "lot_number";
        List<Map<String, Object>> expectedList = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("value", "A001");
        map.put("type", "character varying");
        expectedList.add(map);

        // Count data by condition from DB.
        List<Map<String, Object>> data =
                masterUpdateMapper.getInputData(columnName, tableName, information.getLotNumber(),
                        "character varying", 0, 100, "system_country_code,code_type,code", null);

        // Confirm test result.
        assertThat(data, is(expectedList));
    }

    /**
     * Target method:MasterUpdateMapper#getInputData<BR>
     * Condition: The result is data count of update target table. Expected results ：Return size of
     * list. record.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("MasterUpdateMapperTest_TenderDataExist.xml")
    public void testGetInputDataWithTimeZone() throws Exception {
        String tableName = "v_tender";
        String columnName = "lot_number";
        List<Map<String, Object>> expectedList = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("value", "A001");
        map.put("type", "character varying");
        map.put("timezoneid", "America/New_York");
        expectedList.add(map);

        // Count data by condition from DB.
        List<Map<String, Object>> data =
                masterUpdateMapper.getInputData(columnName, tableName, information.getLotNumber(),
                        "character varying", 0, 100, "store_code,ims_tender_id", "code");

        // Confirm test result.
        assertThat(data, is(expectedList));
    }

    /**
     * Target method:MasterUpdateMapper#getConstraintName<BR>
     * Condition: The result is data count of update target table. Expected results ：Return
     * constraint name. record.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetConstraintName() throws Exception {
        String tableName = "w_type";
        String expectedName = "w_type_pk";

        // Get constraint name from DB.
        String constraintName = masterUpdateMapper.getConstraintName(tableName);

        // Confirm test result.
        assertThat(constraintName, is(expectedName));
    }

    /**
     * Target method:MasterUpdateMapper#upsert<BR>
     * Condition: Update master data for update target table. Expected results ：Return one record.
     *
     * @throws Exception Exception.
     */
    @Test
    public void testUpsertMasterData() throws Exception {

        // Update data from DB.
        int count = masterUpdateMapper.upsert(dataMap);

        // Confirm test result.
        assertThat(count, is(1));
    }

    /**
     * Target method:MasterUpdateMapper#deleteByCondition<BR>
     * Condition: Delete master data for update target table. Expected results ：Return one record.
     * 
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("MasterUpdateMapperTest_TestDelete.xml")
    public void testDeleteMasterData() throws Exception {
        String updateTargetTable = "m_type";
        String inputTable = "v_type";
        List<String> updateKeyItmeList =
                Arrays.asList("system_brand_code,system_country_code,code_type,code".split(","));
        // Delete data from DB.
        int count = masterUpdateMapper.deleteByCondition(updateTargetTable, inputTable,
                updateKeyItmeList, information.getLotNumber());

        // Confirm test result.
        assertThat(count, is(1));
    }

    /**
     * Target method:MasterUpdateMapper#getColumnName<BR>
     * Condition: Setting table name. Expected results： Get update target table field name with
     * update target table name.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetColumnNameTable() throws Exception {
        String inputTblName = "w_type";
        Set<String> expectedColumnNameSert = new HashSet<String>();

        // Set records.
        expectedColumnNameSert.add("batch_region");
        expectedColumnNameSert.add("create_user_id");
        expectedColumnNameSert.add("eai_send_status");
        expectedColumnNameSert.add("code");
        expectedColumnNameSert.add("update_datetime");
        expectedColumnNameSert.add("eai_update_type");
        expectedColumnNameSert.add("eai_send_datetime");
        expectedColumnNameSert.add("system_country_code");
        expectedColumnNameSert.add("code_name");
        expectedColumnNameSert.add("lot_number");
        expectedColumnNameSert.add("required_type");
        expectedColumnNameSert.add("update_user_id");
        expectedColumnNameSert.add("system_business_code");
        expectedColumnNameSert.add("create_datetime");
        expectedColumnNameSert.add("eai_update_datetime");
        expectedColumnNameSert.add("update_program_id");
        expectedColumnNameSert.add("code_num");
        expectedColumnNameSert.add("create_program_id");
        expectedColumnNameSert.add("code_type");

        // Get column name list from DB.
        Set<String> columnNameSet = masterUpdateMapper.getColumnName(inputTblName);

        // Confirm test result.
        assertThat(columnNameSet, is(expectedColumnNameSert));
    }

    /**
     * Target method:MasterUpdateMapper#getColumnName<BR>
     * Condition: Setting view name. Expected results： Get input view fields name list.
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetColumnNameView() throws Exception {
        String inputTblName = "w_type";

        Set<String> expectedColumnNameSert = new HashSet<String>();

        // Set records.
        expectedColumnNameSert.add("batch_region");
        expectedColumnNameSert.add("create_user_id");
        expectedColumnNameSert.add("eai_send_status");
        expectedColumnNameSert.add("code");
        expectedColumnNameSert.add("update_datetime");
        expectedColumnNameSert.add("eai_update_type");
        expectedColumnNameSert.add("eai_send_datetime");
        expectedColumnNameSert.add("system_country_code");
        expectedColumnNameSert.add("code_name");
        expectedColumnNameSert.add("lot_number");
        expectedColumnNameSert.add("required_type");
        expectedColumnNameSert.add("update_user_id");
        expectedColumnNameSert.add("system_business_code");
        expectedColumnNameSert.add("create_datetime");
        expectedColumnNameSert.add("eai_update_datetime");
        expectedColumnNameSert.add("update_program_id");
        expectedColumnNameSert.add("code_num");
        expectedColumnNameSert.add("create_program_id");
        expectedColumnNameSert.add("code_type");

        // Get view column name from DB.
        Set<String> columnNameSet = masterUpdateMapper.getColumnName(inputTblName);

        // Confirm test result.
        assertThat(columnNameSet, is(expectedColumnNameSert));
    }

    /**
     * Target method:MasterUpdateMapper#deleteByLotNumber<BR>
     * Condition: Setting lot number and if work table name. Expected results： Returns the count of
     * deleted data for if work table.
     *
     * @throws Exception Exception.
     */
    @Test
    @DatabaseSetup("MasterUpdateMapperTest_TestTypeIf.xml")
    public void testDeleteInputData() throws Exception {
        String ifWorkTblName = "w_type";

        // Delete data from DB.
        int count = masterUpdateMapper.deleteByLotNumber(ifWorkTblName, information.getLotNumber());

        // Confirm test result.
        assertThat(count, is(2));
    }

}
